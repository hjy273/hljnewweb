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

    // ��ʾ�������յ�
    public ActionForward showAllAcc(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        /*
         * if( !CheckPower.checkPower( request.getSession(), "30601" ) ){ return
         * mapping.findForward( "powererror" ); }
         */
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String depType = userinfo.getDeptype();
        try {
            List lReqInfo = service.getAllOwnAcc(request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "ac1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾ�������յ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // ��ʾ���д����յĸ��ʩ��
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
            logger.error("��ʾ���д����յĸ��ʩ������:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // ��ʾһ�������յ�ʩ��
    public ActionForward showOneWorkForAcc(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30601")) {
            return mapping.findForward("powererror");
        }
        String reid = request.getParameter("reid");
        try {
            // ��ø�ӻ�����Ϣ
            LineCutWorkService service1 = new LineCutWorkService();
            LineCutBean reqinfo = service1.getOneWorkInfo(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "ac20");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾһ�������յ�ʩ������:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // ����һ��ʩ��
    public ActionForward acceptOneWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30601")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }

        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // ����Ǵ�ά��˾�ǲ������
            // return forwardErrorPage(mapping, request, "partauditerror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "partauditerror", backUrl);
        }
        try {
            LineCutBean bean = (LineCutBean) form;
            bean.setDeptid(userinfo.getDeptID());
            bean.setAudituserid(userinfo.getUserID());

            // ��ʼ�����ϴ��ļ�================================
            // List attachments = bean.getAttachments();
            // String fileId;
            // ArrayList fileIdList = new ArrayList();
            // for (int i = 0; i < attachments.size(); i++) {
            // UploadFile uploadFile = (UploadFile) attachments.get(i);
            // FormFile file = uploadFile.getFile();
            // if (file == null) {
            // logger.error("file is null");
            // } else {
            // // ���ļ��洢������������·��д�����ݿ⣬���ؼ�¼ID
            // fileId = SaveUploadFile.saveFile(file);
            // if (fileId != null) {
            // fileIdList.add(fileId);
            // }
            // }
            // }
            ArrayList fileIdList = new ArrayList();
            FormFile file = null;
            String fileId;
            // ȡ���ϴ����ļ�
            Hashtable files = bean.getMultipartRequestHandler().getFileElements();
            if (files != null && files.size() > 0) {
                // �õ�files��keys
                Enumeration enums = files.keys();
                String fileKey = null;
                int i = 0;
                // ����ö��
                while (enums.hasMoreElements()) {
                    // ȡ��key
                    fileKey = (String) (enums.nextElement());
                    // ��ʼ��ÿһ��FormFile(�ӿ�)
                    file = (FormFile) files.get(fileKey);

                    if (file == null) {
                        logger.error("file is null");
                    } else {
                        // ���ļ��洢������������·��д�����ݿ⣬���ؼ�¼ID
                        fileId = SaveUploadFile.saveFile(file);
                        if (fileId != null) {
                            fileIdList.add(fileId);
                        }
                    }
                    i++;
                }

            }
            // �����ϴ��ļ�����=======================================
            // ��ȡID�ַ����б�(�Զ��ŷָ����ļ�ID�ַ���)======================
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
            sb.append(Property.LINE_CUT_MODULE).append(linename).append("��").append(audittime)
                    .append("����").append(bean.getAuditresult()).append(" ������:").append(
                            userinfo.getUserName()).append(SendSMRMI.MSG_NOTE);
            SendSMRMI.sendNormalMessage(userinfo.getUserID(), userinfo.getUserName(),
                    sb.toString(), "00");

            log.info(sb.toString());
            log(request, "��ӹ���", "���ո��ʩ��");
            // return forwardInfoPage(mapping, request, "30602");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "30602", backUrl);
        } catch (Exception e) {
            logger.error("ִ�и�������쳣:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    // ��ʾһ�����յ���ϸ��Ϣ
    public ActionForward showOneAcc(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        /*
         * if( !CheckPower.checkPower( request.getSession(), "30601" ) ){ return
         * mapping.findForward( "powererror" ); }
         */
        String reid = request.getParameter("reid");
        try {
            // ��ø��������Ϣ
            LineCutBean reqinfo = service.getOneAccMod(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "ac10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾһ�����յ���ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // ��ʾ��ѯ��������
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

    // ִ�в�ѯ����
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
            logger.error("��ʾ�������յ�����:" + e.getMessage());
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
            logger.error("��ʾ�������յ�����:session����");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * �����������һ����
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
            logger.info(" ����dao");
            List list = (List) request.getSession().getAttribute("reqinfo");
            logger.info("�õ�list");
            LineCutWorkService lcks = new LineCutWorkService();
            if (lcks.ExportLineCutAcc(list, response)) {
                logger.info("���excel�ɹ�");
            }
            return null;
        } catch (Exception e) {
            logger.error("�������ʩ������һ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
