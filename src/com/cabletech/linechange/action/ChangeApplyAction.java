package com.cabletech.linechange.action;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletResponse;
import com.cabletech.power.CheckPower;
import org.apache.struts.action.ActionForm;
import java.lang.reflect.InvocationTargetException;
import com.cabletech.linechange.bean.ChangeInfoBean;
import com.cabletech.linechange.services.ChangeApplyBOImpl;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.baseinfo.services.LineBO;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import com.cabletech.uploadfile.UploadUtil;
import java.util.ArrayList;
import org.apache.struts.upload.FormFile;
import java.util.List;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadFile;
import java.util.StringTokenizer;
import com.cabletech.linechange.dao.ExportDao;

/**
 * 
 * 处理迁改（修缮）申请 表单载入，添加、修改、删除、查看信息、查询。 1、申请阶段 系统工作：填写方案 结束标识：待审定 A 2-1、勘查阶段
 * 系统工作：填写勘查结果 结束标识：通过监理审定 B1 2-2、勘查阶段 系统工作：填写勘查结果 结束标识：通过移动审定 B2 3、准备阶段
 * 系统工作：填写转交/监理设计信息 结束标识：施工准备 C 4、委托阶段 系统工作：填写委托书 结束标识：开始施工 D 5、施工阶段 系统工作：填写施工信息
 * 结束标识：施工完毕 E 6、验收阶段 系统工作：填写验收信息 结束标识：验收完毕 F 7、归档阶段 系统工作：填写归档信息 结束标识：已经归档 G
 * 
 */
public class ChangeApplyAction extends BaseDispatchAction {
    private static Logger logger = Logger.getLogger("ChangeApplyAction");

    public ActionForward addApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50101")) {
            return mapping.findForward("powererror");
        }
        try {
            UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
            // UploadFileBO upload = new UploadFileBO();
            ChangeInfoBean bean = (ChangeInfoBean) form;
            ChangeInfo data = new ChangeInfo();
            ChangeApplyBOImpl bo = new ChangeApplyBOImpl();
            String datumid = uploadFile(form, new ArrayList());
            // bo.getChangeInfo(bean.getId());
            BeanUtil.objectCopy(bean, data);
            data.setId(super.getDbService().getSeq("changeinfo", 10));
            data.setApplyperson(userinfo.getUserID());
            data.setApplyunit(userinfo.getDeptID());
            data.setRegionid(userinfo.getRegionID());
            data.setApplydatumid(datumid);
            data.setApproveresult("待审定");
            data.setStep("A");
            data.setApplytime(new Date());
            bo.saveChangeInfo(data);
            return forwardInfoPage(mapping, request, "50102s");
        } catch (Exception e) {
            e.printStackTrace();
            return forwardInfoPage(mapping, request, "50102e");
        }
    }

    public ActionForward updateApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50104")) {
            return mapping.findForward("powererror");
        }
        // UploadFileBO upload = new UploadFileBO();
        ChangeInfoBean bean = (ChangeInfoBean) form;
        ChangeInfo data = new ChangeInfo();
        ChangeApplyBOImpl bo = new ChangeApplyBOImpl();

        ArrayList fileIdList = new ArrayList();
        String[] delfileid = request.getParameterValues("delfileid"); // 要删除的文件id号数组
        StringTokenizer st = new StringTokenizer(bean.getApplydatumid(), ",");
        while (st.hasMoreTokens()) {
            fileIdList.add(st.nextToken());
        }
        if (delfileid != null) {
            for (int i = 0; i < delfileid.length; i++) {
                fileIdList.remove(delfileid[i]);
            }
        }
        String datumid = uploadFile(form, fileIdList);
        data = bo.getChangeInfo(bean.getId());
        // request.getSession().getAttribute("changeinfo");
        data.setChangename(bean.getChangename());
        data.setChangepro(bean.getChangepro());
        data.setChangeaddr(bean.getChangeaddr());
        data.setLineclass(bean.getLineclass());
        data.setInvolvedSystem(bean.getInvolvedSystem());
        data.setChangelength(bean.getChangelength());
        data.setStartdate(DateUtil.StringToDate(bean.getStartdate()));
        data.setPlantime(bean.getPlantime());
        data.setApproveresult("待审定");
        data.setRemark(bean.getRemark());
        data.setApplydatumid(datumid);

        data.setEntrustaddr(bean.getEntrustaddr());
        data.setEntrustgrade(bean.getEntrustgrade());
        data.setEntrustperson(bean.getEntrustperson());
        data.setEntrustphone(bean.getEntrustphone());
        data.setEntrustunit(bean.getEntrustunit());
        data.setBuildunit(bean.getBuildunit());
        data.setStarttime(DateUtil.Str2UtilDate(bean.getStarttime(), "yyyy/MM/dd"));
        data.setBudget(bean.getBudget());
        data.setSuperviseUnitId(bean.getSuperviseUnitId());
        bo.updateChangeInfo(data, delfileid);

        // return forwardInfoPage(mapping, request, "50104s");
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, "50104s", backUrl);
    }

    public ActionForward getApplyInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ChangeApplyBOImpl bo = new ChangeApplyBOImpl();
        String changeid = request.getParameter("changeid");
        String type = request.getParameter("type");
        ChangeInfo changeinfo = bo.getChangeInfo(changeid);
        ChangeInfoBean bean = new ChangeInfoBean();
        BeanUtil.objectCopy(changeinfo, bean);
        request.setAttribute("changeinfo", bean);
        ContractorBO cbo = new ContractorBO();
        request.setAttribute("supervise_units", cbo.getSuperviseUnits());

        if ("edit".equals(type)) {
            request.getSession().setAttribute("changeinfo", changeinfo);
            return mapping.findForward("editForm");
        } else {
            // add by guixy 2008-12-6
            String flg = request.getParameter("flg");
            if (flg == null) {
                request.setAttribute("flg", "1");
            } else {
                request.setAttribute("flg", "2");
            }
            LineBO lbo = new LineBO();
            String lineClassName = lbo.getLineClassName((String) bean.getLineclass());
            request.setAttribute("line_class_name", lineClassName);
            if (bean.getSuperviseUnitId() != null && !bean.getSuperviseUnitId().equals("")) {
                String superviseUnitName = cbo.loadContractor(bean.getSuperviseUnitId())
                        .getContractorName();
                request.setAttribute("supervise_unit_name", superviseUnitName);
            }
            return mapping.findForward("lookForm");
        }
    }

    public ActionForward delApplyInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        ChangeApplyBOImpl bo = new ChangeApplyBOImpl();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String changeid = request.getParameter("changeid");
        boolean b = bo.removeChangeInfo(changeid);
        if (b) {
            // return forwardInfoPage(mapping, request, "50105s");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "50105s", backUrl);
        } else {
            // return forwardInfoPage(mapping, request, "50105e");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "50105e", backUrl);
        }
    }

    public ActionForward getApplyInfoList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        ChangeApplyBOImpl bo = new ChangeApplyBOImpl();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeInfoBean bean = (ChangeInfoBean) form;
        String op = request.getParameter("op");
        bean.setOp(op);
        List listchangeinfo = bo.getChangeInfo(userinfo, bean);

        if (listchangeinfo.isEmpty()) {
            listchangeinfo = null;
        }
        request.getSession().setAttribute("queryresult", listchangeinfo);
        request.getSession().setAttribute("bean", bean);
        super.setPageReset(request);
        System.out.println("xf************************************************");
        if (op == null) {
            return mapping.findForward("loadApplyList");
        } else {
            return mapping.findForward("listApplyForSurvey");
        }
    }

    public ActionForward loadAddForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ContractorBO bo = new ContractorBO();
        if (!CheckPower.checkPower(request.getSession(), "50101")) {
            return mapping.findForward("powererror");
        }
        request.setAttribute("supervise_units", bo.getSuperviseUnits());
        return mapping.findForward("loadAddForm");
    }

    public ActionForward loadQueryForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        if (!CheckPower.checkPower(request.getSession(), "50103")) {
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
        ChangeInfoBean formbean = (ChangeInfoBean) form;
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
        // ArrayList fileIdList = new ArrayList();
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

    public ActionForward exportApplyResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            ChangeInfoBean bean = null;
            if (request.getSession().getAttribute("bean") != null) {
                bean = (ChangeInfoBean) request.getSession().getAttribute("bean");
                logger.info("获得查询条件bean。。。");

                logger.info("工程名称：" + bean.getChangename());
                logger.info("工程性质：" + bean.getChangepro());
                logger.info("审定结果：" + bean.getApproveresult());
                logger.info("网络性质：" + bean.getLineclass());
                logger.info("开始时间：" + bean.getBegintime());
                logger.info("结束时间：" + bean.getEndtime());

            }
            List list = (List) request.getSession().getAttribute("queryresult");
            logger.info("得到list");
            logger.info("输出excel成功");
            ExportDao ed = new ExportDao();
            ed.exportApplyResult(list, bean, response);
            logger.info("end.....");
            return null;
        } catch (Exception e) {
            logger.error("导出信息报表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
