package com.cabletech.linechange.action;

import com.cabletech.commons.base.BaseDispatchAction;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import com.cabletech.linechange.bean.ChangeCheckBean;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import com.cabletech.uploadfile.UploadFile;
import org.apache.struts.upload.FormFile;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadUtil;
import java.util.ArrayList;
import com.cabletech.power.CheckPower;
import com.cabletech.linechange.domainobjects.ChangeCheck;
import com.cabletech.linechange.services.CheckBOImpl;
import com.cabletech.linechange.services.PageonholeBOImpl;
import com.cabletech.commons.beans.BeanUtil;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForward;
import java.lang.reflect.InvocationTargetException;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.LineBO;

import java.util.Date;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import java.util.StringTokenizer;
import com.cabletech.linechange.services.ChangeApplyBOImpl;
import com.cabletech.linechange.dao.ExportDao;
import com.cabletech.linechange.bean.ChangeInfoBean;
import com.cabletech.linechange.services.BuildBOImpl;
import com.cabletech.linechange.domainobjects.ChangeBuild;
import com.cabletech.linechange.bean.ChangeBuildBean;

/**
 * 
 * 处理迁改（修缮）申请 表单载入，添加、修改、删除、查看信息、查询。 1、申请阶段 系统工作：填写方案 结束标识：待审定 A 2-1、勘查阶段
 * 系统工作：填写勘查结果 结束标识：通过监理审定 B1 2-2、勘查阶段 系统工作：填写勘查结果 结束标识：通过移动审定 B2 3、准备阶段
 * 系统工作：填写转交/监理设计信息 结束标识：施工准备 C 4、委托阶段 系统工作：填写委托书 结束标识：开始施工 D 5、施工阶段 系统工作：填写施工信息
 * 结束标识：施工完毕 E 6、验收阶段 系统工作：填写验收信息 结束标识：验收完毕 F 7、归档阶段 系统工作：填写归档信息 结束标识：已经归档 G
 * 
 */
public class CheckAction extends BaseDispatchAction {
    private static Logger logger = Logger.getLogger("CheckAction");

    public ActionForward addCheck(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50601")) {
            return mapping.findForward("powererror");
        }
        CheckBOImpl bo = new CheckBOImpl();
        ChangeCheck data = new ChangeCheck();
        ChangeCheckBean bean = new ChangeCheckBean();
        ChangeInfo changeinfo = new ChangeInfo();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        bean = (ChangeCheckBean) form;
        // 文件上传
        ArrayList fileIdList = new ArrayList();
        String[] delfileid = request.getParameterValues("delfileid"); // 要删除的文件id号数组
        StringTokenizer st = new StringTokenizer(bean.getCheckdatum(), ",");
        while (st.hasMoreTokens()) {
            fileIdList.add(st.nextToken());
        }
        if (delfileid != null) {
            for (int i = 0; i < delfileid.length; i++) {
                fileIdList.remove(delfileid[i]);
            }
        }
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String datumid = uploadFile(form, fileIdList);
        BeanUtil.objectCopy(bean, data);
        data.setCheckdatum(datumid);
        data.setId(super.getDbService().getSeq("changecheck", 10));
        data.setFillintime(new Date());
        data.setFillinperson(user.getUserName());
        changeinfo = cbo.getChangeInfo(bean.getChangeid());
        bo.addCheckInfo(data, changeinfo);
        if (data.getCheckresult().equals("通过验收")) {
            PageonholeBOImpl pbo = new PageonholeBOImpl();
            changeinfo.setPageonholedate(new Date());
            changeinfo.setPageonholeperson(userinfo.getUserName());
            changeinfo.setSquare(bean.getSquare());
            changeinfo.setStep("G");
            pbo.updatePageonhole(changeinfo);
        }
        // return forwardInfoPage(mapping, request, "50602s");
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, "50602s", backUrl);

    }

    public ActionForward updateCheck(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50601")) {
            return mapping.findForward("powererror");
        }

        CheckBOImpl bo = new CheckBOImpl();
        ChangeCheck data = new ChangeCheck();
        ChangeInfo changeinfo = new ChangeInfo();
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeCheckBean bean = (ChangeCheckBean) form;
        // 文件上传
        ArrayList fileIdList = new ArrayList();
        String[] delfileid = request.getParameterValues("delfileid"); // 要删除的文件id号数组
        StringTokenizer st = new StringTokenizer(bean.getCheckdatum(), ",");
        while (st.hasMoreTokens()) {
            fileIdList.add(st.nextToken());
        }
        if (delfileid != null) {
            for (int i = 0; i < delfileid.length; i++) {
                fileIdList.remove(delfileid[i]);
            }
        }
        String datumid = uploadFile(form, fileIdList);
        changeinfo = bo.getChangeInfo(bean.getChangeid());
        BeanUtil.objectCopy(bean, data);
        data.setId(super.getDbService().getSeq("changecheck", 10));
        data.setCheckdatum(datumid);
        data.setFillintime(new Date());
        data.setFillinperson(user.getUserName());
        bo.updateCheckInfo(data, changeinfo);
        // return forwardInfoPage(mapping, request, "50604s");
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, "50604s", backUrl);
    }

    public ActionForward getCheckInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        CheckBOImpl bo = new CheckBOImpl();
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeCheckBean bean = new ChangeCheckBean();
        bean = (ChangeCheckBean) form;
        List list = bo.getCheckInfoList(user, bean);
        if (list.isEmpty()) {
            list = null;
        }
        request.getSession().setAttribute("queryresult", list);
        request.getSession().setAttribute("bean", bean);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) bean.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        super.setPageReset(request);
        return mapping.findForward("loadCheckList");

    }

    public ActionForward getChangeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        CheckBOImpl bo = new CheckBOImpl();
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeCheckBean bean = null;
        List list = bo.getChangeInfo(user, bean);
        if (list.isEmpty()) {
            list = null;
        }
        request.getSession().setAttribute("queryresult", list);
        request.getSession().setAttribute("bean", bean);
        super.setPageReset(request);
        return mapping.findForward("listChange");

    }

    public ActionForward loadAddForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String changeid = request.getParameter("changeid");
        CheckBOImpl bo = new CheckBOImpl();
        ChangeCheck changecheck = bo.getChCheckInfoByChangeId(changeid);
        ChangeCheckBean bean = new ChangeCheckBean();
        //
        ChangeInfo changeinfo = new ChangeInfo();
        // ChangeInfoBean changebean = new ChangeInfoBean();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        changeinfo = cbo.getChangeInfo(changeid);
        // BeanUtil.objectCopy( changeinfo, changebean );
        request.setAttribute("changeinfo", changeinfo);

        BuildBOImpl bbo = new BuildBOImpl();
        ChangeBuild build = bbo.getBuildInfoByChangeId(changeid);
        ChangeBuildBean buildbean = new ChangeBuildBean();
        BeanUtil.objectCopy(build, buildbean);
        request.setAttribute("build", buildbean);

        try {
            request.setAttribute("changeid", changeid);
            request.setAttribute("user_name", userinfo.getUserName());
            LineBO lbo = new LineBO();
            String lineClassName = lbo.getLineClassName((String) changeinfo.getLineclass());
            request.setAttribute("line_class_name", lineClassName);
            // if (changecheck != null &&
            // "未通过".equals(changecheck.getCheckresult())) {
            // BeanUtil.objectCopy(changecheck, bean);
            // request.setAttribute("changecheck", bean);
            // return mapping.findForward("editForm");
            // } else {
            return mapping.findForward("loadAddForm");
            // }
        } catch (Exception ex) {
            logger.error("load add form Exception:" + ex.getMessage());
            return mapping.findForward("loadAddForm");
        }
    }

    public ActionForward loadEditForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        if (!CheckPower.checkPower(request.getSession(), "50604")) {
            return mapping.findForward("powererror");
        }

        String id = request.getParameter("id");
        CheckBOImpl bo = new CheckBOImpl();
        ChangeCheck data = bo.getCheckInfo(id);
        ChangeCheckBean bean = new ChangeCheckBean();
        BeanUtil.objectCopy(data, bean);
        //
        ChangeInfo changeinfo = new ChangeInfo();
        ChangeInfoBean changebean = new ChangeInfoBean();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        changeinfo = cbo.getChangeInfo(bean.getChangeid());
        BeanUtil.objectCopy(changeinfo, changebean);
        request.setAttribute("changeinfo", changebean);
        // /
        request.setAttribute("changeCheck", bean);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) changeinfo.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        return mapping.findForward("loadEditForm");
    }

    public ActionForward loadLookForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {

        String id = request.getParameter("id");
        CheckBOImpl bo = new CheckBOImpl();
        ChangeCheck data = bo.getCheckInfo(id);
        ChangeCheckBean bean = new ChangeCheckBean();
        BeanUtil.objectCopy(data, bean);
        //
        ChangeInfo changeinfo = new ChangeInfo();
        ChangeInfoBean changebean = new ChangeInfoBean();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        changeinfo = cbo.getChangeInfo(bean.getChangeid());
        BeanUtil.objectCopy(changeinfo, changebean);
        request.setAttribute("changeinfo", changebean);
        // /
        request.setAttribute("changecheck", bean);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) changebean.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        return mapping.findForward("loadLookForm");
    }

    public ActionForward loadQueryForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        if (!CheckPower.checkPower(request.getSession(), "50603")) {
            return mapping.findForward("powererror");
        }
        return mapping.findForward("loadQueryForm");
    }

    /**
     * 文件上传
     * 
     * @param form
     *            ActionForm
     * @return String
     */
    public String uploadFile(ActionForm form, ArrayList fileIdList) {
        ChangeCheckBean formbean = (ChangeCheckBean) form;
        // 开始处理上传文件================================
        // List attachments = formbean.getAttachments();
        // String fileId;
        // for (int i = 0; i < attachments.size(); i++) {
        // UploadFile uploadFile = (UploadFile) attachments.get(i);
        // FormFile file = uploadFile.getFile();
        // if (file == null) {
        // logger.info("file is null");
        // } else {
        // // 将文件存储到服务器并将路径写入数据库，返回记录ID
        // fileId = SaveUploadFile.saveFile(file);
        // if (fileId != null) {
        // fileIdList.add(fileId);
        // }
        // }
        // }
        FormFile file = null;
        String fileId;
        // 取得上传的文件
        Hashtable files = formbean.getMultipartRequestHandler().getFileElements();
        if (files != null && files.size() > 0) {
            // 得到files的keys
            Enumeration enums = files.keys();
            String fileKey = null;
            int i = 0;
            // 遍历枚举
            while (enums.hasMoreElements()) {
                // 取得key
                fileKey = (String) (enums.nextElement());
                // 初始化每一个FormFile(接口)
                file = (FormFile) files.get(fileKey);

                if (file == null) {
                    logger.error("file is null");
                } else {
                    // 将文件存储到服务器并将路径写入数据库，返回记录ID
                    fileId = SaveUploadFile.saveFile(file);
                    if (fileId != null) {
                        fileIdList.add(fileId);
                    }
                }
                i++;
            }

        }
        // 处理上传文件结束=======================================

        // 获取ID字符串列表(以逗号分隔的文件ID字符串)======================
        String fileIdListStr = UploadUtil.getFileIdList(fileIdList);
        // String fileIdListStr =processFileUpload(request);
        String datumid = "";
        if (fileIdListStr != null) {
            // logger.info( "FileIdListStr=" + fileIdListStr );
            datumid = fileIdListStr;
        }
        return datumid;
    }

    public ActionForward exportCheckResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            ChangeCheckBean bean = null;
            if (request.getSession().getAttribute("bean") != null) {
                bean = (ChangeCheckBean) request.getSession().getAttribute("bean");
                logger.info("获得查询条件bean。。。");
                logger.info("开始时间：" + bean.getBegintime());
                logger.info("结束时间：" + bean.getEndtime());

            }
            List list = (List) request.getSession().getAttribute("queryresult");
            logger.info("得到list");
            logger.info("输出excel成功");
            ExportDao ed = new ExportDao();
            ed.exportCheckResult(list, bean, response);
            logger.info("end.....");
            return null;
        } catch (Exception e) {
            logger.error("导出信息报表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward showCheckHistory(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String changeId = request.getParameter("change_id");
        String showType = request.getParameter("show_type");
        CheckBOImpl bo = new CheckBOImpl();
        List list = bo.getChangeCheckHistoryList(changeId, showType);
        request.setAttribute("check_history_list", list);
        return mapping.findForward("check_history_list");
    }
}
