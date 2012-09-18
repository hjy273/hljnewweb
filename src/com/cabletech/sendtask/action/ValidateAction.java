package com.cabletech.sendtask.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
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
import com.cabletech.sendtask.dao.ReplyDao;
import com.cabletech.sendtask.dao.SendTaskDao;
import com.cabletech.sendtask.dao.ValidateDao;
import com.cabletech.sendtask.util.SendTaskUtil;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.UploadUtil;

public class ValidateAction extends BaseDispatchAction {
    Logger logger = Logger.getLogger(this.getClass().getName());

    SendTaskDao sendDao = new SendTaskDao();

    EndorseTaskDao endorseDao = new EndorseTaskDao();

    ReplyDao replyDao = new ReplyDao();

    ValidateDao valiDao = new ValidateDao();

    /**
     * ��ʾ���д���֤���ɵ�
     */
    public ActionForward showTaskToValidate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110401")) {
            return mapping.findForward("powererror");
        }

        // 2009-1-5 add by guixy ���Ϊ�鿴����������
        String loginuserFlg = request.getParameter("loginuserflg");
        // 2009-1-5 add by guixy ���Ϊ�鿴����������

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List valiList = valiDao.getTaskToVali(userinfo, loginuserFlg);
            // ����֤�Ĵ���ʱ��ȥ���������յĴ��� 2008-12-18
            DynaBean rowBean = null;
            String replytime;
            float processterm;
            int weeks;
            int weekNum;
            for (int i = 0; i < valiList.size(); i++) {
                weekNum = 0;
                weeks = 0;
                rowBean = (DynaBean) valiList.get(i);
                replytime = String.valueOf(rowBean.get("replytime"));
                if (rowBean.get("processterm") != null) {
                    processterm = Float.parseFloat(rowBean.get("processterm").toString());
                    // ȡ�ÿ�����
                    weeks = SendTaskUtil.getWeeks(replytime);
                    if (processterm != 0 && Math.abs(processterm) > 48) {

                        if (weeks != 0) {
                            weekNum = SendTaskUtil.getWeekNum(replytime);
                            if (processterm > 0) {
                                processterm = processterm - 48 * weekNum;
                            } else {
                                processterm = processterm + 48 * weekNum;
                            }
                            rowBean.set("processterm", new BigDecimal(String
                                    .valueOf(((int) (processterm * 10)) / 10.0)));
                        }
                    } else if (weeks == 1 && processterm != 0 && Math.abs(processterm) < 48) {
                        rowBean.set("processterm", new BigDecimal("0"));
                    }
                }

            }
            // ����֤�Ĵ���ʱ��ȥ���������յĴ��� 2008-12-18

            // ȡ�ô��ظ�����ͳ����Ϣ
            List countList = valiDao.getValidateCountList(userinfo);
            request.getSession().setAttribute("countlist", countList);
            request.getSession().setAttribute("valiList", valiList);
            request.getSession().setAttribute("type", "2");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("��ʾ���д���֤���ɵ�����:" + e.getMessage());
            e.printStackTrace();
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾһ����֤�ɵ�����ϸ��Ϣ,������֤
     */
    public ActionForward showOneValidate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110401")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            SendTaskBean endorsebean = sendDao.getEendorseOfSendTask(subid);
            SendTaskBean replybean = replyDao.getOneSendTask(subid, userinfo);
            SendTaskBean info = valiDao.getInfo(id, userinfo);
            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("replybean", replybean);
            request.getSession().setAttribute("info", info);
            request.getSession().setAttribute("type", "20");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾһ����֤�ɵ�����ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * �����ɵ���֤��Ϣ
     */
    public ActionForward validateTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110401")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            bean.setValidateuserid(userinfo.getUserID());
            bean.setSendtaskid(bean.getId());
            bean.setReplyid(bean.getReplyid());

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
                bean.setValidateacce(fileIdListStr);
            }
            // ======================

            if (!valiDao.saveValidate(bean)) {
                // return forwardErrorPage(mapping, request, "110402e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "110402e", backUrl);
            }

            String sim = sendDao.getSendPhone(bean.getReplyuserid());
            if (sim != null) {
                // String msg = "������֤֪ͨ����ظ���" + bean.getSendtype() + "����" +
                // bean.getValidateresult() + ".";
                String msg = SendTaskUtil.SENDTASK_MODELNAME + " " + bean.getSendtopic()
                        + "�����Ѿ���֤ " + userinfo.getUserName() + " " + SendSMRMI.MSG_NOTE;
                logger.info("��֤�ɵ��Ķ�������:" + msg);
                SendSMRMI.sendNormalMessage(userinfo.getUserID(), sim, msg, "00");
            }

            log(request, "�ɵ�����", "��֤����");
            // return forwardInfoPage(mapping, request, "110402");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "110402", backUrl);
        } catch (Exception e) {
            logger.error("�����ɵ���֤��Ϣ����:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * ��ʾ�Ѿ�����֤���ɵ�
     */
    public ActionForward showAllValidate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // if( !CheckPower.checkPower( request.getSession(), "110402" ) ){
        // return mapping.findForward( "powererror" );
        // }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lvali = valiDao.getvalidateList(userinfo);
            request.getSession().setAttribute("lvali", lvali);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("��ʾ�ѻظ����ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾһ���Ѿ�������֤���ɵ���ϸ��Ϣ
     */
    public ActionForward showValidate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // if( !CheckPower.checkPower( request.getSession(), "110402" ) ){
        // return mapping.findForward( "powererror" );
        // }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            SendTaskBean endorsebean = sendDao.getEendorseOfSendTask(subid);
            SendTaskBean replybean = replyDao.getOneSendTask(subid, userinfo);
            SendTaskBean valibean = valiDao.getOneValidate(subid, userinfo);
            SendTaskBean info = valiDao.getInfo(id, userinfo);
            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("replybean", replybean);
            request.getSession().setAttribute("valibean", valibean);
            request.getSession().setAttribute("info", info);

            request.getSession().setAttribute("type", "10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾһ���Ѿ�������֤���ɵ���ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ������ѯ��ʾ
     */
    public ActionForward showQueryValidate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110403")) {
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
        if (!CheckPower.checkPower(request.getSession(), "110403")) {
            return mapping.findForward("powererror");
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lvali = valiDao.queryValiList(userinfo, bean, request.getSession());
            // ����֤�Ĵ���ʱ��ȥ���������յĴ��� 2008-12-18
            DynaBean rowBean = null;
            String replytime;
            String validatetime;
            float processterm;
            int weeks;
            int weekNum;
            for (int i = 0; i < lvali.size(); i++) {
                weeks = 0;
                weekNum = 0;
                rowBean = (DynaBean) lvali.get(i);
                replytime = String.valueOf(rowBean.get("replytime"));
                validatetime = String.valueOf(rowBean.get("validatetime"));
                processterm = Float.parseFloat(rowBean.get("processterm").toString());

                // ȡ�ÿ�����
                weeks = SendTaskUtil.getWeeks(replytime, validatetime);
                if (processterm != 0 && Math.abs(processterm) > 48) {

                    if (weeks != 0) {
                        weekNum = SendTaskUtil.getWeekNum(replytime, validatetime);
                        if (processterm > 0) {
                            processterm = processterm - 48 * weekNum;
                        } else {
                            processterm = processterm + 48 * weekNum;
                        }
                        rowBean.set("processterm", new BigDecimal(String
                                .valueOf(((int) (processterm * 10)) / 10.0)));
                    }
                } else if (weeks == 1 && processterm != 0 && Math.abs(processterm) < 48) {
                    rowBean.set("processterm", new BigDecimal("0"));
                }
            }
            // ����֤�Ĵ���ʱ��ȥ���������յĴ��� 2008-12-18

            request.getSession().setAttribute("lvali", lvali);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("����������֤�ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward doQueryAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("vlQueryCon");
        if (sql.trim().length() != 0 && sql != null) {
            List lvali = valiDao.doQueryAfterMod(sql);
            request.getSession().setAttribute("lvali", lvali);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("����������֤�ɵ�����:session����");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ������֤�ɵ�һ����
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
    public ActionForward exportValidateResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" ����dao");
            List list = (List) request.getSession().getAttribute("lvali");
            logger.info("�õ�list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportValidateResult(list, response)) {
                logger.info("���excel�ɹ�");
            }
            return null;
        } catch (Exception e) {
            logger.error("������֤�ɵ�һ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
