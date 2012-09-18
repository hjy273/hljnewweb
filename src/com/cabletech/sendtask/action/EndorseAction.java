package com.cabletech.sendtask.action;

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
import com.cabletech.power.CheckPower;
import com.cabletech.sendtask.beans.SendTaskBean;
import com.cabletech.sendtask.dao.EndorseTaskDao;
import com.cabletech.sendtask.dao.ExportDao;
import com.cabletech.sendtask.dao.SendTaskDao;
import com.cabletech.sendtask.util.SendTaskUtil;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.UploadUtil;

public class EndorseAction extends BaseDispatchAction {
    Logger logger = Logger.getLogger(this.getClass().getName());

    SendTaskDao sendDao = new SendTaskDao();

    EndorseTaskDao endorseDao = new EndorseTaskDao();

    /**
     * ��ʾ���д�ǩ�յ��ɵ�
     */
    public ActionForward showTaskToEndorse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110201")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lEndorseTask = endorseDao.getTaskToEndorse(userinfo);

            // ȡ�ô�ǩ����ͳ����Ϣ
            List countList = endorseDao.getEndorseCountList(userinfo);
            request.getSession().setAttribute("countlist", countList);
            request.getSession().setAttribute("endorselist", lEndorseTask);
            request.getSession().setAttribute("type", "2");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("��ʾ���д�ǩ�յ��ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾ���е�¼��Ա�Ĵ�ǩ�յ��ɵ�
     */
    public ActionForward showLoginUserTaskToEndorse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110201")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lEndorseTask = endorseDao.getLoginUserTaskToEndorse(userinfo);

            // ȡ�ô�ǩ����ͳ����Ϣ
            List countList = endorseDao.getEndorseCountList(userinfo);
            request.getSession().setAttribute("countlist", countList);
            request.getSession().setAttribute("endorselist", lEndorseTask);
            request.getSession().setAttribute("type", "2");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("��ʾ���д�ǩ�յ��ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾǩ���ɵ���ϸ��Ϣ
     */
    public ActionForward showEndorseTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110201")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        // ��֣�������ӱ�id 2008-12-01 add by guixy
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("type", "20");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾǩ���ɵ���ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * �����ɵ�ǩ����Ϣ
     */
    public ActionForward endorseTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110201")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            bean.setDeptid(userinfo.getDeptID());
            bean.setUserid(userinfo.getUserID());
            bean.setSendtaskid(bean.getId());

            // ��ʼ�����ϴ��ļ�================================
            // List attachments = bean.getAttachments();
            // String fileId;
            // ArrayList fileIdList = new ArrayList();
            // for( int i = 0; i < attachments.size(); i++ ){
            // UploadFile uploadFile = ( UploadFile )attachments.get( i );
            // FormFile file = uploadFile.getFile();
            // if( file == null ){
            // logger.error( "file is null" );
            // }
            // else{
            // //���ļ��洢������������·��д�����ݿ⣬���ؼ�¼ID
            // fileId = SaveUploadFile.saveFile( file );
            // if( fileId != null ){
            // fileIdList.add( fileId );
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
                // System.out.println( "FILE:" + fileIdListStr );
                bean.setAcce(fileIdListStr);
            }
            // ======================

            if (!endorseDao.saveEndorse(bean)) {
                // return forwardErrorPage(mapping, request, "110202e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "110202e", backUrl);
            }

            String sim = sendDao.getSendPhone(bean.getSenduserid());
            if (sim != null) {
                // String msg = "����ǩ��֪ͨ����ǩ����" + bean.getSendtype() + "�����Ѿ�ǩ��.";
                String msg = SendTaskUtil.SENDTASK_MODELNAME + " " + bean.getSendtopic()
                        + "�����Ѿ�ǩ�� " + userinfo.getUserName() + " " + SendSMRMI.MSG_NOTE;
                logger.info("ǩ���ɵ��Ķ�������:" + msg);
                SendSMRMI.sendNormalMessage(userinfo.getUserID(), sim, msg, "00");
            }

            log(request, "�ɵ�����", "ǩ������");
            // return forwardInfoPage(mapping, request, "110202");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "110202", backUrl);
        } catch (Exception e) {
            logger.error("�����ɵ�ǩ����Ϣ����:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * ��ʾ��ǩ�յ��ɵ�
     */
    public ActionForward showAllEndorse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110202")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lendorsetask = endorseDao.getendorseList(userinfo);
            request.getSession().setAttribute("endorselist", lendorsetask);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("��ʾ��ǩ�յ��ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾһ���ɵ�ǩ�յ���ϸ��Ϣ
     */
    public ActionForward showOneEndorse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // if( !CheckPower.checkPower( request.getSession(), "110202" ) ){
        // return mapping.findForward( "powererror" );
        // }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            SendTaskBean endorsebean = sendDao.getEendorseOfSendTask(subid);
            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("type", "10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾһ���ɵ�ǩ�յ���ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ������ѯ��ʾ
     */
    public ActionForward showQueryEndorse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110203")) {
            return mapping.findForward("powererror");
        }
        try {
            request.getSession().setAttribute("type", "3");
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("������ѯ��ʾ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ���������ɵ�
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110203")) {
            return mapping.findForward("powererror");
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List endorselist = endorseDao.queryendorseList(userinfo, bean, request.getSession());
            request.getSession().removeAttribute("endorselist");
            request.getSession().setAttribute("endorselist", endorselist);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��������ǩ���ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward doQueryAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("endorseTaskCon");
        if (sql.trim().length() != 0 && sql != null) {
            List endorselist = endorseDao.doQueryAfterMod(sql);
            request.getSession().setAttribute("endorselist", endorselist);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("����ѯ�������ز���ǩ���ɵ�����session�ѹ���");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ����ǩ���ɵ�һ����
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
    public ActionForward exportendorseResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" ����dao");
            List list = (List) request.getSession().getAttribute("endorselist");
            logger.info("�õ�list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportEndorseResult(list, response)) {
                logger.info("���excel�ɹ�");
            }
            return null;
        } catch (Exception e) {
            logger.error("����ǩ���ɵ�һ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
