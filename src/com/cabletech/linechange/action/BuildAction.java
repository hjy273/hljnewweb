package com.cabletech.linechange.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForward;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;
import com.cabletech.power.CheckPower;
import javax.servlet.http.HttpServletResponse;
import com.cabletech.commons.base.BaseDispatchAction;
import org.apache.struts.action.ActionForm;
import java.lang.reflect.InvocationTargetException;
import com.cabletech.linechange.services.BuildBOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.LineBO;
import com.cabletech.linechange.bean.ChangeBuildBean;
import java.util.List;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linechange.domainobjects.ChangeBuild;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import org.apache.struts.upload.FormFile;
import java.util.Date;
import com.cabletech.linechange.services.ChangeApplyBOImpl;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadUtil;
import com.cabletech.linechange.dao.ExportDao;
import com.cabletech.linechange.bean.ChangeInfoBean;

/**
 * 
 * 处理迁改（修缮）申请 表单载入，添加、修改、删除、查看信息、查询。 1、申请阶段 系统工作：填写方案 结束标识：待审定 A 2-1、勘查阶段
 * 系统工作：填写勘查结果 结束标识：通过监理审定 B1 2-2、勘查阶段 系统工作：填写勘查结果 结束标识：通过移动审定 B2 3、准备阶段
 * 系统工作：填写转交/监理设计信息 结束标识：施工准备 C 4、委托阶段 系统工作：填写委托书 结束标识：开始施工 D 5、施工阶段 系统工作：填写施工信息
 * 结束标识：施工完毕 E 6、验收阶段 系统工作：填写验收信息 结束标识：验收完毕 F 7、归档阶段 系统工作：填写归档信息 结束标识：已经归档 G
 * 
 */
public class BuildAction extends BaseDispatchAction {
    private static Logger logger = Logger.getLogger("BuildAction");

    public ActionForward addBuild(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50501")) {
            return mapping.findForward("powererror");
        }
        BuildBOImpl bo = new BuildBOImpl();
        ChangeBuild data = new ChangeBuild();
        ChangeBuildBean bean = new ChangeBuildBean();
        ChangeInfo changeinfo = new ChangeInfo();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        bean = (ChangeBuildBean) form;
        // 文件上传
        ArrayList fileIdList = new ArrayList();
        String[] delfileid = request.getParameterValues("delfileid"); // 要删除的文件id号数组
        StringTokenizer st = new StringTokenizer(bean.getBuilddatum(), ",");
        while (st.hasMoreTokens()) {
            fileIdList.add(st.nextToken());
        }
        if (delfileid != null) {
            for (int i = 0; i < delfileid.length; i++) {
                fileIdList.remove(delfileid[i]);
            }
        }
        String datumid = uploadFile(form, fileIdList);
        BeanUtil.objectCopy(bean, data);
        data.setBuilddatum(datumid);
        data.setId(super.getDbService().getSeq("changebuild", 10));
        data.setFillindate(new Date());
        data.setFillinperson(user.getUserName());
        data.setFillinunit(request.getSession().getAttribute("LOGIN_USER_DEPT_NAME").toString());
        changeinfo = cbo.getChangeInfo(bean.getChangeid());
        bo.addBuildInfo(data, changeinfo);
        // return forwardInfoPage( mapping, request, "50502s" );
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, "50502s", backUrl);
    }

    public ActionForward updateBuild(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50504")) {
            return mapping.findForward("powererror");
        }
        BuildBOImpl bo = new BuildBOImpl();
        ChangeBuild data = new ChangeBuild();
        ChangeInfo changeinfo = new ChangeInfo();
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeBuildBean bean = (ChangeBuildBean) form;
        // 文件上传
        ArrayList fileIdList = new ArrayList();
        String[] delfileid = request.getParameterValues("delfileid"); // 要删除的文件id号数组
        StringTokenizer st = new StringTokenizer(bean.getBuilddatum(), ",");
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
        // data.setId( super.getDbService().getSeq( "changebuild", 10 ) );
        data.setBuilddatum(datumid);
        data.setFillindate(new Date());
        data.setFillinperson(user.getUserName());
        data.setFillinunit(request.getSession().getAttribute("LOGIN_USER_DEPT_NAME").toString());
        bo.updateBuildInfo(data, changeinfo);
        // return forwardInfoPage( mapping, request, "50504s" );
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, "50504s", backUrl);
    }

    public ActionForward getBuildInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        BuildBOImpl bo = new BuildBOImpl();
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeBuildBean bean = new ChangeBuildBean();
        bean = (ChangeBuildBean) form;
        List list = bo.getBuildInfoList(user, bean);
        if (list.isEmpty()) {
            list = null;
        }
        request.getSession().setAttribute("queryresult", list);
        request.getSession().setAttribute("bean", bean);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) bean.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        super.setPageReset(request);
        return mapping.findForward("loadBuildList");

    }

    public ActionForward getChangeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        BuildBOImpl bo = new BuildBOImpl();
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeBuildBean bean = null;
        List list = bo.getChangeInfo(user, bean);
        if (list.isEmpty()) {
            list = null;
        }
        request.getSession().setAttribute("queryresult", list);
        super.setPageReset(request);
        return mapping.findForward("listChange");

    }

    public ActionForward loadAddForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        if (!CheckPower.checkPower(request.getSession(), "50501")) {
            return mapping.findForward("powererror");
        }
        String changeid = request.getParameter("changeid");
        // BuildBOImpl bo = new BuildBOImpl();
        // ChangeInfo changeinfo = bo.getChangeInfo(id);
        // ChangeBuildBean bean = new ChangeBuildBean();
        // BeanUtil.objectCopy( changeinfo, bean );
        ChangeInfo changeinfo = new ChangeInfo();
        ChangeInfoBean changebean = new ChangeInfoBean();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        changeinfo = cbo.getChangeInfo(changeid);
        BeanUtil.objectCopy(changeinfo, changebean);
        request.setAttribute("changeinfo", changebean);

        request.setAttribute("changeid", changeid);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) changeinfo.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        return mapping.findForward("loadAddForm");
    }

    public ActionForward loadEditForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        if (!CheckPower.checkPower(request.getSession(), "50504")) {
            return mapping.findForward("powererror");
        }
        String id = request.getParameter("id");
        BuildBOImpl bo = new BuildBOImpl();
        ChangeBuild data = bo.getBuildInfo(id);
        ChangeBuildBean bean = new ChangeBuildBean();
        BeanUtil.objectCopy(data, bean);
        request.setAttribute("changebuild", bean);
        ChangeInfo changeinfo = new ChangeInfo();
        ChangeInfoBean changebean = new ChangeInfoBean();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        changeinfo = cbo.getChangeInfo(bean.getChangeid());
        BeanUtil.objectCopy(changeinfo, changebean);
        request.setAttribute("changeinfo", changebean);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) changeinfo.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        return mapping.findForward("loadEditForm");
    }

    public ActionForward loadLookForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {

        String id = request.getParameter("id");
        BuildBOImpl bo = new BuildBOImpl();
        ChangeBuild data = bo.getBuildInfo(id);
        ChangeBuildBean bean = new ChangeBuildBean();
        BeanUtil.objectCopy(data, bean);
        ChangeInfo changeinfo = new ChangeInfo();
        ChangeInfoBean changebean = new ChangeInfoBean();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        changeinfo = cbo.getChangeInfo(bean.getChangeid());
        BeanUtil.objectCopy(changeinfo, changebean);
        request.setAttribute("changeinfo", changebean);

        request.setAttribute("changebuild", bean);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) changebean.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        return mapping.findForward("loadLookForm");
    }

    public ActionForward loadQueryForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        if (!CheckPower.checkPower(request.getSession(), "50503")) {
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
        ChangeBuildBean formbean = (ChangeBuildBean) form;
        // 开始处理上传文件================================
        // List attachments = formbean.getAttachments();
        // String fileId;
        // for( int i = 0; i < attachments.size(); i++ ){
        // UploadFile uploadFile = ( UploadFile )attachments.get( i );
        // FormFile file = uploadFile.getFile();
        // if( file == null ){
        // logger.info( "file is null" );
        // }
        // else{
        // //将文件存储到服务器并将路径写入数据库，返回记录ID
        // fileId = SaveUploadFile.saveFile( file );
        // if( fileId != null ){
        // fileIdList.add( fileId );
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

    public ActionForward exportBuildResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            ChangeBuildBean bean = null;
            if (request.getSession().getAttribute("bean") != null) {
                bean = (ChangeBuildBean) request.getSession().getAttribute("bean");
                logger.info("获得查询条件bean。。。");
                logger.info("开始时间：" + bean.getStarttime());
                logger.info("结束时间：" + bean.getEndtime());

            }
            List list = (List) request.getSession().getAttribute("queryresult");
            logger.info("得到list");
            logger.info("输出excel成功");
            ExportDao ed = new ExportDao();
            ed.exportBuildResult(list, bean, response);
            logger.info("end.....");
            return null;
        } catch (Exception e) {
            logger.error("导出信息报表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
