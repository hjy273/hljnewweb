package com.cabletech.linecut.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.linecut.beans.LineCutBean;
import com.cabletech.linecut.property.Property;
import com.cabletech.linecut.services.LineCutReService;
import com.cabletech.linecut.services.LineCutWorkService;
import com.cabletech.power.CheckPower;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.UploadUtil;

public class LineCutAcceptAction extends BaseDispatchAction {
    private LineCutReService service = new LineCutReService();

    private static Logger logger = Logger.getLogger(LineCutAcceptAction.class.getName());

    // 显示所有验收单
    public ActionForward showAllAcc(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        /*
         * if( !CheckPower.checkPower( request.getSession(), "30601" ) ){ return
         * mapping.findForward( "powererror" ); }
         */
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String depType = userinfo.getDeptype();
        try {
            List lReqInfo = service.getAllOwnAcc(request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "ac1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示所有验收单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // 显示所有待验收的割接施工
    public ActionForward showAllWorkForAcc(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30601")) {
            return mapping.findForward("powererror");
        }

        try {
            List lReqInfo = service.getAllWorkForAcc(request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "ac2");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示所有待验收的割接施工出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // 显示一个待验收的施工
    public ActionForward showOneWorkForAcc(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30601")) {
            return mapping.findForward("powererror");
        }
        String reid = request.getParameter("reid");
        try {
            // 获得割接基本信息
            LineCutWorkService service1 = new LineCutWorkService();
            LineCutBean reqinfo = service1.getOneWorkInfo(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "ac20");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示一个待验收的施工错误:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // 验收一个施工
    public ActionForward acceptOneWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30601")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }

        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // 如果是代维公司是不允许的
            // return forwardErrorPage(mapping, request, "partauditerror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "partauditerror", backUrl);
        }
        try {
            LineCutBean bean = (LineCutBean) form;
            bean.setDeptid(userinfo.getDeptID());
            bean.setAudituserid(userinfo.getUserID());

            // 开始处理上传文件================================
            // List attachments = bean.getAttachments();
            // String fileId;
            // ArrayList fileIdList = new ArrayList();
            // for (int i = 0; i < attachments.size(); i++) {
            // UploadFile uploadFile = (UploadFile) attachments.get(i);
            // FormFile file = uploadFile.getFile();
            // if (file == null) {
            // logger.error("file is null");
            // } else {
            // // 将文件存储到服务器并将路径写入数据库，返回记录ID
            // fileId = SaveUploadFile.saveFile(file);
            // if (fileId != null) {
            // fileIdList.add(fileId);
            // }
            // }
            // }
            ArrayList fileIdList = new ArrayList();
            FormFile file = null;
            String fileId;
            // 取得上传的文件
            Hashtable files = bean.getMultipartRequestHandler().getFileElements();
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
            if (fileIdListStr != null) {
                bean.setAuditacce(fileIdListStr);
            }
            // ======================
            if (!service.addAcceptInfo(bean)) {
                // return forwardErrorPage(mapping, request, "30602e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "30602e", backUrl);
            }

            String linename = bean.getName();
            String audittime = bean.getAudittime();
            StringBuffer sb = new StringBuffer();
            sb.append(Property.LINE_CUT_MODULE).append(linename).append("于").append(audittime)
                    .append("验收").append(bean.getAuditresult()).append(" 验收人:").append(
                            userinfo.getUserName()).append(SendSMRMI.MSG_NOTE);
            SendSMRMI.sendNormalMessage(userinfo.getUserID(), userinfo.getUserName(),
                    sb.toString(), "00");

            log.info(sb.toString());
            log(request, "割接管理", "验收割接施工");
            // return forwardInfoPage(mapping, request, "30602");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "30602", backUrl);
        } catch (Exception e) {
            logger.error("执行割接验收异常:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    // 显示一个验收的详细信息
    public ActionForward showOneAcc(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        /*
         * if( !CheckPower.checkPower( request.getSession(), "30601" ) ){ return
         * mapping.findForward( "powererror" ); }
         */
        String reid = request.getParameter("reid");
        try {
            // 获得割接验收信息
            LineCutBean reqinfo = service.getOneAccMod(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "ac10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示一个验收的详细信息错误:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // 显示查询验收条件
    public ActionForward showAccQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30603")) {
            return mapping.findForward("powererror");
        }
        List reConList = service.getAllCon();
        request.setAttribute("reConList", reConList);
        request.getSession().setAttribute("type", "ac3");
        return mapping.findForward("success");
    }

    // 执行查询验收
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30603")) {
            return mapping.findForward("powererror");
        }
        LineCutBean bean = (LineCutBean) form;
        bean.setDeptid(((UserInfo) request.getSession().getAttribute("LOGIN_USER")).getDeptID());
        String deptType = ((UserInfo) request.getSession().getAttribute("LOGIN_USER")).getDeptype();

        try {
            List lReqInfo = service.queryAcc(bean, deptType, request.getSession());
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "ac1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示所有验收单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward queryAccAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("lcQueryCon");
        if (sql != null && sql.trim().length() != 0) {
            List lReqInfo = service.queryAccAfterMod(sql);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "ac1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("显示所有验收单出错:session过期");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 导出割接申请一览表
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     */
    public ActionForward exportLineCutAcc(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" 创建dao");
            List list = (List) request.getSession().getAttribute("reqinfo");
            logger.info("得到list");
            LineCutWorkService lcks = new LineCutWorkService();
            if (lcks.ExportLineCutAcc(list, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("导出割接施工验收一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
