package com.cabletech.sendtask.action;

import org.apache.log4j.Logger;
import com.cabletech.commons.base.BaseDispatchAction;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletRequest;
import com.cabletech.baseinfo.domainobjects.UserInfo;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletResponse;
import com.cabletech.power.CheckPower;
import org.apache.struts.action.ActionForm;
import com.cabletech.sendtask.dao.*;
import com.cabletech.sendtask.util.SendTaskUtil;
import com.cabletech.sendtask.beans.*;

import org.apache.struts.upload.FormFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.SaveUploadFile;
import java.util.ArrayList;
import com.cabletech.uploadfile.UploadUtil;
import com.cabletech.commons.sm.SendSMRMI;
import java.util.StringTokenizer;

public class ReplyAction extends BaseDispatchAction {
    Logger logger = Logger.getLogger(this.getClass().getName());

    SendTaskDao sendDao = new SendTaskDao();

    EndorseTaskDao endorseDao = new EndorseTaskDao();

    ReplyDao replyDao = new ReplyDao();

    /**
     * ��ʾ���д��ظ����ɵ�
     */
    public ActionForward showTaskToReply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110301")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List replyList = replyDao.getTaskToReply(userinfo);

            // ȡ�ô��ظ�����ͳ����Ϣ
            List countList = replyDao.getReplyCountList(userinfo);
            request.getSession().setAttribute("countlist", countList);
            request.getSession().setAttribute("replylist", replyList);
            request.getSession().setAttribute("type", "2");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("��ʾ���д��ظ����ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾ��¼��Ա���д��ظ����ɵ�
     */
    public ActionForward showLoginUserTaskToReply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110301")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List replyList = replyDao.getLoginUserTaskToReply(userinfo);

            // ȡ�ô��ظ�����ͳ����Ϣ
            List countList = replyDao.getReplyCountList(userinfo);
            request.getSession().setAttribute("countlist", countList);
            request.getSession().setAttribute("replylist", replyList);
            request.getSession().setAttribute("type", "2");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("��ʾ���д��ظ����ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾһ�����ظ��ɵ�����ϸ��Ϣ
     */
    public ActionForward showOneToReply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110301")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            String workState = endorseDao.getOneSendTaskState(subid);
            SendTaskBean bean = null;
            SendTaskBean endorsebean = null;
            if ("2������".equals(workState)) {
                List beanList = endorseDao.getOneSendTaskInfo(subid);
                request.getSession().setAttribute("beanList", beanList);
                request.getSession().setAttribute("state", "1");
            } else {
                request.getSession().setAttribute("state", "2");
            }
            bean = endorseDao.getOneSendTask(id, userinfo, subid);
            endorsebean = sendDao.getEendorseOfSendTask(subid);
            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("type", "20");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾһ�����ظ��ɵ�����ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * �����ɵ��ظ���Ϣ
     */
    public ActionForward replyTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110301")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            bean.setReplyuserid(userinfo.getUserID());
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
                bean.setReplyacce(fileIdListStr);
            }
            // ======================

            if (!replyDao.saveReply(bean)) {
                // return forwardErrorPage(mapping, request, "110302e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "110302e", backUrl);
            }

            String sim = sendDao.getSendPhone(bean.getSenduserid());
            if (sim != null) {
                // String msg = "�����ظ�֪ͨ����ǩ����" + bean.getSendtype() + "�����Ѿ��ظ�.";
                String msg = SendTaskUtil.SENDTASK_MODELNAME + " " + bean.getSendtopic()
                        + "�����Ѿ��ظ� " + userinfo.getUserName() + " " + SendSMRMI.MSG_NOTE;
                logger.info("�ظ��ɵ��Ķ�������:" + msg);
                SendSMRMI.sendNormalMessage(userinfo.getUserID(), sim, msg, "00");
            }

            log(request, "�ɵ�����", "�ظ�����");
            // return forwardInfoPage(mapping, request, "110302");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "110302", backUrl);
        } catch (Exception e) {
            logger.error("�����ɵ��ظ���Ϣ����:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * ��ʾ�ѻظ����ɵ�
     */
    public ActionForward showAllReply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lreply = replyDao.getreplyList(userinfo);
            request.getSession().setAttribute("lreply", lreply);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("��ʾ�ѻظ����ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾһ���ظ��ɵ�����ϸ��Ϣ
     */
    public ActionForward showOneReply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110301")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            SendTaskBean endorsebean = sendDao.getEendorseOfSendTask(subid);
            SendTaskBean replybean = replyDao.getOneSendTask(subid, userinfo);
            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("replybean", replybean);
            request.getSession().setAttribute("type", "10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾһ���ظ��ɵ�����ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾһ�����޸ĵĻظ��ɵ���ϸ��Ϣ
     */
    public ActionForward showOneToUp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110304")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            SendTaskBean endorsebean = sendDao.getEendorseOfSendTask(subid);
            SendTaskBean replybean = replyDao.getOneSendTask(subid, userinfo);
            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("replybean", replybean);
            request.getSession().setAttribute("type", "4");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾһ�����޸ĵĻظ��ɵ���ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ִ���޸��ɵ��ظ�
     */
    public ActionForward upReply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110304")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            bean.setReplyuserid(userinfo.getUserID());
            ArrayList fileIdList = new ArrayList();
            String[] delfileid = request.getParameterValues("delfileid"); // Ҫɾ�����ļ�id������
            // ����µĸ�����
            StringTokenizer st = new StringTokenizer(bean.getReplyacce(), ",");
            while (st.hasMoreTokens()) {
                fileIdList.add(st.nextToken());
            }
            if (delfileid != null) {
                for (int i = 0; i < delfileid.length; i++) {
                    fileIdList.remove(delfileid[i]);
                }
            }
            // ��ʼ�����ϴ��ļ�================================
            // List attachments = bean.getAttachments();
            // String fileId;
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
            // ArrayList fileIdList = new ArrayList();
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
                bean.setReplyacce(fileIdListStr);
            }
            // ======================

            if (!replyDao.upReply(bean)) {
                // return forwardErrorPage(mapping, request, "110304e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "110304e", backUrl);
            }

            String sim = sendDao.getSendPhone(bean.getSenduserid());
            if (sim != null && bean.getWorkstate().equals("������")) {
                String msg = "����֪ͨ����һ������" + bean.getSendtype() + "�����ȴ�������֤.";
                SendSMRMI.sendNormalMessage(userinfo.getUserID(), sim, msg, "00");
            }

            log(request, "�ɵ�����", "�޸Ļظ�");
            // return forwardInfoPage(mapping, request, "110304");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "110304", backUrl);
        } catch (Exception e) {
            logger.error("�޸Ļظ��ɵ�����:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * ������ѯ��ʾ
     */
    public ActionForward showQueryReply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110303")) {
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
        if (!CheckPower.checkPower(request.getSession(), "110303")) {
            return mapping.findForward("powererror");
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lreply = replyDao.queryReplyList(userinfo, bean, request.getSession());
            request.getSession().removeAttribute("lreply");
            request.getSession().setAttribute("lreply", lreply);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("�������һظ��ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward doQueryAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("replayQueryCon");
        if (sql.trim().length() != 0 && sql != null) {
            List lreply = replyDao.doQueryAfterMod(sql);
            request.getSession().setAttribute("lreply", lreply);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("����ѯ�������ز��һظ��ɵ�����session�ѹ���");
            return forwardErrorPage(mapping, request, "error");
        }

    }

    /**
     * �����ظ��ɵ�һ����
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
    public ActionForward exportReplyResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" ����dao");
            List list = (List) request.getSession().getAttribute("lreply");
            logger.info("�õ�list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportReplyResult(list, response)) {
                logger.info("���excel�ɹ�");
            }
            return null;
        } catch (Exception e) {
            logger.error("�����ظ��ɵ�һ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾת��ҳ��
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
    public ActionForward showTaskForward(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List ldept = sendDao.getAcceptdept(userinfo);
            List luser = sendDao.getAcceptUser(userinfo);
            request.getSession().setAttribute("deptinfo", ldept);
            request.getSession().setAttribute("userinfo", luser);
            // ȡ���ɵ�����id���ӱ�id
            String id = request.getParameter("id");
            String subid = request.getParameter("subid");
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("type", "forward");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾ�ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // showTaskForward

    /**
     * ת���ɵ�
     */
    public ActionForward forwardTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            bean.setSenduserid(userinfo.getUserID());
            bean.setSenddeptid(userinfo.getDeptID());
            bean.setRegionid(userinfo.getRegionID());

            String[] deptId = request.getParameterValues("acceptdeptid");
            String[] userId = request.getParameterValues("acceptuserid");

            // ���������ת���Ĳ���
            if (deptId == null) {
                return forwardErrorPage(mapping, request, "110301e");
            }

            if (!sendDao.sendTask(bean, deptId, userId)) {
                return forwardErrorPage(mapping, request, "110301e");
            }

            // ת������˷���������
            for (int i = 0; i < userId.length; i++) {
                String sim = sendDao.getSendPhone(userId[i]);
                if (sim != null) {
                    String msg = "����֪ͨ����һ" + bean.getSendtype() + "�����ȴ�����ǩ�պʹ���.";
                    SendSMRMI.sendNormalMessage(userinfo.getUserID(), sim, msg, "00");
                }
            }

            log(request, "�ɵ�����", "�ظ�����");
            return forwardInfoPage(mapping, request, "110301");
        } catch (Exception e) {
            logger.error("ת���ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
