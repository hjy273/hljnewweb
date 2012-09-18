package com.cabletech.sendtask.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

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

public class SendTaskAction extends BaseDispatchAction {
    Logger logger = Logger.getLogger(this.getClass().getName());

    SendTaskDao dao = new SendTaskDao();

    EndorseTaskDao endorseDao = new EndorseTaskDao();

    ReplyDao replyDao = new ReplyDao();

    ValidateDao valiDao = new ValidateDao();

    /**
     * ��ʾ��д�ɵ�
     */
    public ActionForward showSendTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110101")) {
            return mapping.findForward("powererror");
        }
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List ldept = dao.getAcceptdept(userinfo);
            List luser = dao.getAcceptUser(userinfo);
            request.getSession().setAttribute("deptinfo", ldept);
            request.getSession().setAttribute("userinfo", luser);
            request.getSession().setAttribute("type", "2");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾ�ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * �����ɵ�
     */
    public ActionForward sendTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110101")) {
            return mapping.findForward("powererror");
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            bean.setSenduserid(userinfo.getUserID());
            bean.setSenddeptid(userinfo.getDeptID());
            bean.setRegionid(userinfo.getRegionID());

            // ��ʼ�����ϴ��ļ�================================
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

            // List attachments = bean.getAttachments();
            // String fileId;
            //            
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
            // �����ϴ��ļ�����=======================================
            // ��ȡID�ַ����б�(�Զ��ŷָ����ļ�ID�ַ���)======================
            String fileIdListStr = UploadUtil.getFileIdList(fileIdList);
            if (fileIdListStr != null) {
                System.out.println("FILE:" + fileIdListStr);
                bean.setSendacce(fileIdListStr);
            }
            // ======================

            // add by guixy 2008-11-28����˶��ɵ�����
            String[] deptId = request.getParameterValues("acceptdeptid");
            String[] userId = request.getParameterValues("acceptuserid");

            if (!dao.sendTask(bean, deptId, userId)) {
                return forwardErrorPage(mapping, request, "110102e");
            }

            // modify by guixy 2008-12-4 ���������Ӧ�þ��Ƕ�������
            for (int i = 0; i < userId.length; i++) {
                String sim = dao.getSendPhone(userId[i]);
                if (sim != null) {
                    // String msg = "����֪ͨ����һ" + bean.getSendtype() +
                    // "�����ȴ�����ǩ�պʹ���.";
                    String msg = SendTaskUtil.SENDTASK_MODELNAME + " " + bean.getSendtopic()
                            + "�����ȴ����Ĵ��� " + userinfo.getUserName() + " " + SendSMRMI.MSG_NOTE;
                    logger.info("�ɵ��Ķ�������:" + msg);
                    SendSMRMI.sendNormalMessage(userinfo.getUserID(), sim, msg, "00");
                }
            }

            log(request, "�ɵ�����", "ָ������");
            return forwardInfoPage(mapping, request, "110102");
        } catch (Exception e) {
            logger.error("�����ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾ���е��ɵ�
     */
    public ActionForward showAllSendTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), "110101")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lsendtask = dao.getSendList(userinfo);
            request.getSession().setAttribute("sendlist", lsendtask);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("��ʾ���е��ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾһ���ɵ���ϸ��Ϣ
     */
    public ActionForward showOneSendTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110101")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = dao.getOneSendTask(id, userinfo, subid);
            SendTaskBean endorsebean = dao.getEendorseOfSendTask(subid);

            // guixy by 2008-11-30
            // List deptList = dao.getAcceptDept(id);
            // ���ڱ�־�������ӱ�������ݣ����Ǿɱ�����0:��ʾ�����ݣ�1����ʾ������
            // if (deptList.size() != 0) {
            // if(((DynaBean)deptList.get(0)).get("deptid") != null) {
            // request.setAttribute("newOldFlg", "0");
            // } else {
            // request.setAttribute("newOldFlg", "1");
            // }
            // } else {
            request.setAttribute("newOldFlg", "1");

            // }
            // request.getSession().setAttribute( "deptlist", deptList);

            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("type", "10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾһ���ɵ���ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ������ѯ��ʾ
     */
    public ActionForward showQuerySendTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110103")) {
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
        if (!CheckPower.checkPower(request.getSession(), "110103")) {
            return mapping.findForward("powererror");
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lsendtask = dao.querySendList(userinfo, bean, request.getSession());
            request.getSession().setAttribute("sendlist", lsendtask);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("���������ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾ�޸ĺ�Ĳ�ѯ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("sendTaskQueryCon");
        try {
            List lsendtask = dao.queryAfterMod(sql);
            request.getSession().setAttribute("sendlist", lsendtask);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("���ز�ѯ�ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * �޸�һ���ɵ���Ϣ��ʾ
     */
    public ActionForward showOneToUp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110104")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = dao.getOneSendTask(id, userinfo, subid);

            SendTaskBean endorsebean = dao.getEendorseOfSendTask(subid);
            List ldept = dao.getAcceptdept(userinfo);
            List luser = dao.getAcceptUser(userinfo);

            // guixy by 2008-11-29
            // List accDeptList = dao.getAcceptDept(id);
            // ���ڱ�־�������ӱ�������ݣ����Ǿɱ�����0:��ʾ�����ݣ�1����ʾ������
            // if (accDeptList.size() != 0) {
            // if(((DynaBean)accDeptList.get(0)).get("deptid") != null) {
            // request.setAttribute("newOldFlg", "0");
            // } else {
            // request.setAttribute("newOldFlg", "1");
            // }
            // } else {
            request.setAttribute("newOldFlg", "1");

            // }

            // request.getSession().setAttribute( "deptList", accDeptList);

            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("deptinfo", ldept);
            request.getSession().setAttribute("userinfo", luser);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("type", "4");

            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("�޸�һ���ɵ���Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ִ���޸��ɵ�
     */
    public ActionForward upSendtask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110104")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            bean.setSenduserid(userinfo.getUserID());
            bean.setSenddeptid(userinfo.getDeptID());
            bean.setRegionid(userinfo.getRegionID());

            ArrayList fileIdList = new ArrayList();
            String[] delfileid = request.getParameterValues("delfileid"); // Ҫɾ�����ļ�id������
            // ����µĸ�����
            StringTokenizer st = new StringTokenizer(bean.getSendacce(), ",");
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
            // modify by guixy 2009-3-16
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
                bean.setSendacce(fileIdListStr);
            }
            // ======================

            // guixy add by 2008-11-28
            // String[] deptId = request.getParameterValues( "acceptdeptid" );
            // String[] userId = request.getParameterValues( "acceptuserid" );

            if (!dao.upSendTask(bean)) {
                // return forwardErrorPage(mapping, request, "110104e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "110104e", backUrl);
            }

            String sim = dao.getSendPhone(bean.getAcceptuserid());
            if (sim != null && !bean.getResult().equals("��ǩ��")) {
                String msg = "����֪ͨ����һ" + bean.getSendtype() + "�����ȴ�����ǩ�պʹ���.";
                SendSMRMI.sendNormalMessage(userinfo.getUserID(), sim, msg, "00");
            }

            log(request, "�ɵ�����", "�޸�ָ������");
            // return forwardInfoPage(mapping, request, "110104");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "110104", backUrl);
        } catch (Exception e) {
            logger.error("�޸��ɵ�����:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * �����ɵ���Ϣһ����
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
    public ActionForward exportSendTaskResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" ����dao");
            List list = (List) request.getSession().getAttribute("sendlist");
            logger.info("�õ�list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportSendTaskResult(list, response)) {
                logger.info("���excel�ɹ�");
            }
            return null;
        } catch (Exception e) {
            logger.error("�����ɵ���Ϣһ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * �򿪲�ѯͳ��ҳ��
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
    public ActionForward showQueryTotal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // if( !CheckPower.checkPower( request.getSession(), "110101" ) ){
        // return mapping.findForward( "powererror" );
        // }
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List ldept = dao.getAcceptdept(userinfo);
            List luser = dao.getAcceptUser(userinfo);
            request.getSession().setAttribute("deptinfo", ldept);
            request.getSession().setAttribute("userinfo", luser);
            request.getSession().setAttribute("type", "1");
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("��ʾ��ѯͳ��ҳ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ���в�ѯͳ�Ʋ���
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
    public ActionForward doQueryToTotal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        SendTaskBean bean = (SendTaskBean) form;
        if ("total".equals(bean.getWorkstate())) {
            request.getSession().setAttribute("overflg", "1");
        }
        try {
            List allTotalList = dao.allQueryTotalList(userinfo, bean, request.getSession());
            // ����֤�Ĵ���ʱ��ȥ���������յĴ��� 2008-12-26
            DynaBean rowBean = null;
            String replytime;
            String validatetime;
            float validovertime;
            int weeks;
            int weekNum;
            for (int i = 0; i < allTotalList.size(); i++) {
                weeks = 0;
                weekNum = 0;
                rowBean = (DynaBean) allTotalList.get(i);
                replytime = String.valueOf(rowBean.get("replytime"));
                validatetime = String.valueOf(rowBean.get("validatetime"));
                validovertime = Float.parseFloat(rowBean.get("validovertime").toString());

                // ȡ�ÿ�����
                weeks = SendTaskUtil.getWeeks(replytime, validatetime);
                if (validovertime != 0 && Math.abs(validovertime) > 48) {
                    if (weeks != 0) {
                        weekNum = SendTaskUtil.getWeekNum(replytime, validatetime);
                        if (validovertime > 0) {
                            validovertime = validovertime - 48 * weekNum;
                        } else {
                            validovertime = validovertime + 48 * weekNum;
                        }
                        rowBean.set("validovertime", new BigDecimal(
                                ((int) validovertime * 10) / 10.0));
                    }
                } else if (weeks == 1 && validovertime != 0 && Math.abs(validovertime) < 48) {
                    rowBean.set("validovertime", new BigDecimal("0"));
                }
            }
            // ����֤�Ĵ���ʱ��ȥ���������յĴ��� 2008-12-26

            request.getSession().setAttribute("totallist", allTotalList);
            request.getSession().setAttribute("type", "2");
            super.setPageReset(request);
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("��ʾ�ɵ�����:" + e.getMessage());
            e.printStackTrace();
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾһ���Ѿ�������֤���ɵ���ϸ��Ϣ
     */
    public ActionForward showOneToTatal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // if( !CheckPower.checkPower( request.getSession(), "110402" ) ){
        // return mapping.findForward( "powererror" );
        // }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        String flg = request.getParameter("flg");
        try {
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            SendTaskBean endorsebean = dao.getEendorseOfSendTask(subid);
            SendTaskBean replybean = replyDao.getOneSendTask(subid, userinfo);
            SendTaskBean valibean = valiDao.getOneValidate(subid, userinfo);
            SendTaskBean info = valiDao.getInfo(id, userinfo);
            request.getSession().setAttribute("flg", flg);
            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("replybean", replybean);
            request.getSession().setAttribute("valibean", valibean);
            request.getSession().setAttribute("info", info);

            request.getSession().setAttribute("type", "3");
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("��ʾһ���Ѿ�������֤���ɵ���ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ѯͳ�Ƶ�����Ϣһ����
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
    public ActionForward exportSendTotalResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" ����dao");
            List list = (List) request.getSession().getAttribute("totallist");
            logger.info("�õ�list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportQuerytotalResult(list, response)) {
                logger.info("���excel�ɹ�");
            }
            return null;
        } catch (Exception e) {
            logger.error("��ѯͳ�Ƶ�����Ϣһ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ���˹�����Ϣ������Ϣһ����
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
    public ActionForward exportSendPersonTotalResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" ����dao");
            List list = (List) request.getSession().getAttribute("totallist");
            String queryFlg = String.valueOf(request.getSession().getAttribute("queryflg"));
            String username = String.valueOf(request.getSession().getAttribute("username"));
            SendTaskBean bean = (SendTaskBean) request.getSession().getAttribute("querycon");
            String dataCount = String.valueOf(request.getSession().getAttribute("datacount"));
            logger.info("�õ�list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportQueryPersontotalResult(list, queryFlg, username, bean, dataCount,
                    response)) {
                logger.info("���excel�ɹ�");
            }
            return null;
        } catch (Exception e) {
            logger.error("���˹���������Ϣһ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ���˹�������ͳ�Ƶ�����Ϣһ����
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
    public ActionForward exportPersonNumTotalResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" ����dao");
            List list = (List) request.getSession().getAttribute("numtotallist");
            SendTaskBean querybean = (SendTaskBean) request.getSession().getAttribute("querycon");
            logger.info("�õ�list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportPersonnumtotalResult(list, querybean, response)) {
                logger.info("���excel�ɹ�");
            }
            return null;
        } catch (Exception e) {
            logger.error("���˹���������Ϣһ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * �򿪸��˹���ͳ��ҳ��
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
    public ActionForward showQueryPersonTotal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        // UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
        // "LOGIN_USER" );

        // 2009-1-5��ӵ�ȡ������Ϣ����Ա��Ϣ
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List ldept = dao.getAcceptdept(userinfo);
            List luser = dao.getAcceptUser(userinfo);
            request.getSession().setAttribute("deptinfo", ldept);
            request.getSession().setAttribute("userinfo", luser);

            request.getSession().setAttribute("type", "11");
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("��ʾ��ѯͳ��ҳ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ���и��˹���������ѯͳ�Ʋ���
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
    public ActionForward doQueryPersonToTotal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
        // "LOGIN_USER" );
        // SendTaskBean bean = ( SendTaskBean )form;
        //  
        // try{
        // List personList = dao.QueryPersonList(userinfo, bean,
        // request.getSession());
        // List personCountList = dao.personTotal(userinfo, bean,
        // request.getSession());
        // request.getSession().setAttribute("personcountList",
        // personCountList);
        // request.getSession().setAttribute("totallist", personList);
        // request.getSession().setAttribute( "type", "12" );
        // return mapping.findForward( "querytotal" );
        // }
        // catch( Exception e ){
        // logger.error( "��ʾ�ɵ�����:" + e.getMessage() );
        // return forwardErrorPage( mapping, request, "error" );
        // }

        try {
            SendTaskBean bean = (SendTaskBean) form;
            List personNumList = dao.personTotalNum(bean, request.getSession());
            // ����֤��ʱ��һ�������ų���Щȥ����������û�г�ʱ�� add by guixy 2008-1-8
            String overtimeva;
            DynaBean rowBean;
            String userid;
            for (int i = 0; i < personNumList.size(); i++) {
                rowBean = (DynaBean) personNumList.get(i);
                overtimeva = String.valueOf(rowBean.get("overtimevalid"));
                userid = String.valueOf(rowBean.get("userid"));
                if (!"0".equals(overtimeva) || !"null".equals(overtimeva)) {
                    // ȡ��sql��������ĳ�ʱ��¼
                    List personList = dao.getPersonTaskInfo(bean, "22", userid, request
                            .getSession());
                    DynaBean rowBean2 = null;
                    String replytime;
                    float overtimenum;
                    String workstate = null;
                    String validatetime;
                    int weeks;
                    // ȥ���м����������û�г�ʱ��
                    for (int j = 0; j < personList.size(); j++) {
                        weeks = 0;
                        rowBean2 = (DynaBean) personList.get(j);
                        replytime = String.valueOf(rowBean2.get("replytime"));
                        workstate = String.valueOf(rowBean2.get("workstate"));
                        if ("����֤".equals(workstate)) {
                            // ����֤�����
                            overtimenum = Float.parseFloat(rowBean2.get("overtimenum").toString());
                            // ȡ�ÿ�����
                            weeks = SendTaskUtil.getWeeks(replytime);
                            if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                                personList.remove(j);
                            }
                        } else {
                            // ����֤�������
                            validatetime = String.valueOf(rowBean2.get("validatetime"));
                            overtimenum = Float.parseFloat(rowBean2.get("overtimenum").toString());
                            // ȡ�ÿ�����
                            weeks = SendTaskUtil.getWeeks(replytime, validatetime);
                            if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                                personList.remove(j);
                            }
                        }
                    }

                    rowBean.set("overtimevalid", new BigDecimal(String.valueOf(personList.size())));
                }
            }
            // ����֤��ʱ��һ�������ų���Щȥ����������û�г�ʱ�� add by guixy 2008-1-8

            request.getSession().setAttribute("numtotallist", personNumList);
            request.getSession().setAttribute("querycon", bean);
            request.getSession().setAttribute("type", "112");
            super.setPageReset(request);
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("���˹���������ѯͳ�Ʋ�������:" + e.getMessage());
            e.printStackTrace();
            return forwardErrorPage(mapping, request, "error");
        }

    }

    /**
     * ��ʾ���˹�����Ϣ
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
    public ActionForward ShowPersonTaskInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // ȡ�ò�ѯͳ������
        SendTaskBean bean = (SendTaskBean) request.getSession().getAttribute("querycon");
        // ��־
        String queryFlg = request.getParameter("flg");
        String userid = request.getParameter("userid");
        String username = request.getParameter("username");

        try {
            List personList = dao.getPersonTaskInfo(bean, queryFlg, userid, request.getSession());
            // ����֤�Ĵ���ʱ��ȥ���������յĴ��� 2009-1-7
            if ("3".equals(queryFlg) || "13".equals(queryFlg) || "22".equals(queryFlg)) {
                DynaBean rowBean = null;
                String replytime;
                float overtimenum;
                String workstate = null;
                String validatetime;
                // �����ȡ����ʼ���ڵ�����ʱ��缸��
                int weeks;
                // �����ȡ����ʼ����+2������ʱ��缸�ܣ���Ϊsql�����ȡ�õĲ�ֵ�ǿ�ʼ����+2�������
                int weekNum;
                for (int i = 0; i < personList.size(); i++) {
                    weeks = 0;
                    weekNum = 0;
                    rowBean = (DynaBean) personList.get(i);
                    replytime = String.valueOf(rowBean.get("replytime"));
                    workstate = String.valueOf(rowBean.get("workstate"));
                    if ("����֤".equals(workstate)) {
                        // ����֤�����
                        overtimenum = Float.parseFloat(rowBean.get("overtimenum").toString());
                        // ȡ�ÿ�����
                        weeks = SendTaskUtil.getWeeks(replytime);
                        if (overtimenum != 0 && Math.abs(overtimenum) > 48) {
                            if (weeks != 0) {
                                weekNum = SendTaskUtil.getWeekNum(replytime);
                                if (overtimenum > 0) {
                                    overtimenum = overtimenum - 48 * weekNum;
                                } else {
                                    overtimenum = overtimenum + 48 * weekNum;
                                }
                                rowBean.set("overtimenum", new BigDecimal(String
                                        .valueOf(((int) (overtimenum * 10)) / 10.0)));
                            }
                        } else if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                            // rowBean.set("overtimenum", new BigDecimal("0"));
                            if ("22".equals(queryFlg)) {
                                personList.remove(i);
                            } else {
                                rowBean.set("overtimenum", new BigDecimal("0"));
                            }

                        }
                    } else {
                        // ����֤�������
                        validatetime = String.valueOf(rowBean.get("validatetime"));
                        overtimenum = Float.parseFloat(rowBean.get("overtimenum").toString());
                        // ȡ�ÿ�����
                        weeks = SendTaskUtil.getWeeks(replytime, validatetime);
                        if (overtimenum != 0 && Math.abs(overtimenum) > 48) {
                            if (weeks != 0) {
                                weekNum = SendTaskUtil.getWeekNum(replytime, validatetime);
                                if (overtimenum > 0) {
                                    overtimenum = overtimenum - 48 * weekNum;
                                } else {
                                    overtimenum = overtimenum + 48 * weekNum;
                                }
                                rowBean.set("overtimenum", new BigDecimal(String
                                        .valueOf(((int) (overtimenum * 10)) / 10.0)));
                            }
                        } else if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                            // rowBean.set("overtimenum", new BigDecimal("0"));
                            if ("22".equals(queryFlg)) {
                                personList.remove(i);
                            } else {
                                rowBean.set("overtimenum", new BigDecimal("0"));
                            }
                        }
                    }
                }
            }
            // ����֤�Ĵ���ʱ��ȥ���������յĴ��� 2009-1-7

            request.getSession().setAttribute("totallist", personList);
            request.getSession().setAttribute("type", "12");
            request.getSession().setAttribute("queryflg", queryFlg);
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("datacount", personList.size() + "");
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("��ʾ���˹�������:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * �򿪲��Ź���ͳ��ҳ��
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
    public ActionForward showQueryDeptTotal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List ldept = dao.getAcceptdept(userinfo);
            request.getSession().setAttribute("deptinfo", ldept);
            request.getSession().setAttribute("type", "13");
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("��ʾ��ѯͳ��ҳ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ���Ź���ͳ�Ʋ�ѯ����
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
    public ActionForward doQueryDeptTotal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        SendTaskBean bean = (SendTaskBean) form;

        try {
            List deptTotalList = dao.deptTotal(userinfo, bean, request.getSession());
            request.getSession().setAttribute("deptTotalList", deptTotalList);
            request.getSession().setAttribute("deptquerybean", bean);
            request.getSession().setAttribute("type", "14");
            super.setPageReset(request);
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("��ʾ�ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * �������Ź���ͳ����Ϣһ����
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
    public ActionForward exportDeptTotalResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" ����dao");
            List list = (List) request.getSession().getAttribute("deptTotalList");
            SendTaskBean deptquerybean = (SendTaskBean) request.getSession().getAttribute(
                    "deptquerybean");
            logger.info("�õ�list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportDepttotalResult(list, deptquerybean, response)) {
                logger.info("���excel�ɹ�");
            }
            return null;
        } catch (Exception e) {
            logger.error("�����ɵ���Ϣһ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ѯͳ�Ʋ˵���ѯ�����ϸҳ��ķ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryTotalAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        try {
            String flg = request.getParameter("flg");
            List totalList = null;
            String sql = null;
            if ("1".equals(flg)) {
                // ��ѯͳ��
                sql = (String) request.getSession().getAttribute("alltotalsql");
                totalList = dao.queryAfterMod(sql);

                // ����֤�Ĵ���ʱ��ȥ���������յĴ��� 2009-1-7
                DynaBean rowBean = null;
                String replytime;
                String validatetime;
                float validovertime;
                int weeks;
                int weekNum;
                for (int i = 0; i < totalList.size(); i++) {
                    weeks = 0;
                    weekNum = 0;
                    rowBean = (DynaBean) totalList.get(i);
                    replytime = String.valueOf(rowBean.get("replytime"));
                    validatetime = String.valueOf(rowBean.get("validatetime"));
                    validovertime = Float.parseFloat(rowBean.get("validovertime").toString());

                    // ȡ�ÿ�����
                    weeks = SendTaskUtil.getWeeks(replytime, validatetime);
                    if (validovertime != 0 && Math.abs(validovertime) > 48) {
                        if (weeks != 0) {
                            weekNum = SendTaskUtil.getWeekNum(replytime, validatetime);
                            if (validovertime > 0) {
                                validovertime = validovertime - 48 * weekNum;
                            } else {
                                validovertime = validovertime + 48 * weekNum;
                            }
                            rowBean.set("validovertime", new BigDecimal(String
                                    .valueOf(((int) (validovertime * 10)) / 10.0)));
                        }
                    } else if (weeks == 1 && validovertime != 0 && Math.abs(validovertime) < 48) {
                        rowBean.set("validovertime", new BigDecimal("0"));
                    }
                }
                // ����֤�Ĵ���ʱ��ȥ���������յĴ��� 2009-1-7

                request.getSession().setAttribute("type", "2");
                request.getSession().setAttribute("totallist", totalList);
            } else if ("2".equals(flg)) {
                // ���˹�����Ϣ
                // sql = (String)
                // request.getSession().getAttribute("persontotalsql");
                // personCountList = dao.queryAfterMod(sql);
                sql = (String) request.getSession().getAttribute("personsql");
                totalList = dao.queryAfterMod(sql);

                // ����֤�Ĵ���ʱ��ȥ���������յĴ��� 2009-1-7
                String queryFlg = String.valueOf(request.getSession().getAttribute("queryflg"));
                if ("3".equals(queryFlg) || "13".equals(queryFlg) || "22".equals(queryFlg)) {
                    DynaBean rowBean = null;
                    String replytime;
                    float overtimenum;
                    String workstate = null;
                    String validatetime;
                    int weeks;
                    int weekNum;
                    for (int i = 0; i < totalList.size(); i++) {
                        weekNum = 0;
                        weeks = 0;
                        rowBean = (DynaBean) totalList.get(i);
                        replytime = String.valueOf(rowBean.get("replytime"));
                        workstate = String.valueOf(rowBean.get("workstate"));
                        if ("����֤".equals(workstate)) {
                            // ����֤�����
                            overtimenum = Float.parseFloat(rowBean.get("overtimenum").toString());
                            // ȡ�ÿ�����
                            weeks = SendTaskUtil.getWeeks(replytime);
                            if (overtimenum != 0 && Math.abs(overtimenum) > 48) {
                                if (weeks != 0) {
                                    weekNum = SendTaskUtil.getWeekNum(replytime);
                                    if (overtimenum > 0) {
                                        overtimenum = overtimenum - 48 * weekNum;
                                    } else {
                                        overtimenum = overtimenum + 48 * weekNum;
                                    }

                                    rowBean.set("overtimenum", new BigDecimal(String
                                            .valueOf(((int) (overtimenum * 10)) / 10.0)));
                                }
                            } else if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                                if ("22".equals(queryFlg)) {
                                    totalList.remove(i);
                                } else {
                                    rowBean.set("overtimenum", new BigDecimal("0"));
                                }
                            }

                        } else {
                            // ����֤�������
                            validatetime = String.valueOf(rowBean.get("validatetime"));
                            overtimenum = Float.parseFloat(rowBean.get("overtimenum").toString());
                            // ȡ�ÿ�����
                            weeks = SendTaskUtil.getWeeks(replytime, validatetime);
                            if (overtimenum != 0 && Math.abs(overtimenum) > 48) {
                                if (weeks != 0) {
                                    weekNum = SendTaskUtil.getWeekNum(replytime, validatetime);
                                    if (overtimenum > 0) {
                                        overtimenum = overtimenum - 48 * weekNum;
                                    } else {
                                        overtimenum = overtimenum + 48 * weekNum;
                                    }

                                    rowBean.set("overtimenum", new BigDecimal(String
                                            .valueOf(((int) (overtimenum * 10)) / 10.0)));
                                }
                            } else if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                                if ("22".equals(queryFlg)) {
                                    totalList.remove(i);
                                } else {
                                    rowBean.set("overtimenum", new BigDecimal("0"));
                                }
                            }
                        }
                    }
                }
                // ����֤�Ĵ���ʱ��ȥ���������յĴ��� 2009-1-7

                request.getSession().setAttribute("type", "12");
                // request.getSession().setAttribute("personcountList",
                // personCountList);
                request.getSession().setAttribute("totallist", totalList);
            } else {
                // ���˹���ͳ��
                sql = (String) request.getSession().getAttribute("personnumtotalsql");
                totalList = dao.queryAfterMod(sql);
                SendTaskBean bean = (SendTaskBean) request.getSession().getAttribute("querycon");
                // ����֤��ʱ��һ�������ų���Щȥ����������û�г�ʱ�� add by guixy 2008-1-8
                String overtimeva;
                DynaBean rowBean;
                String userid;
                for (int i = 0; i < totalList.size(); i++) {
                    rowBean = (DynaBean) totalList.get(i);
                    overtimeva = String.valueOf(rowBean.get("overtimevalid"));
                    userid = String.valueOf(rowBean.get("userid"));
                    if (!"0".equals(overtimeva) || !"null".equals(overtimeva)) {
                        // ȡ��sql��������ĳ�ʱ��¼
                        List personList = dao.getPersonTaskInfo(bean, "22", userid, request
                                .getSession());
                        DynaBean rowBean2 = null;
                        String replytime;
                        float overtimenum;
                        String workstate = null;
                        String validatetime;
                        int weeks;
                        // ȥ���м����������û�г�ʱ��
                        for (int j = 0; j < personList.size(); j++) {
                            weeks = 0;
                            rowBean2 = (DynaBean) personList.get(j);
                            replytime = String.valueOf(rowBean2.get("replytime"));
                            workstate = String.valueOf(rowBean2.get("workstate"));
                            if ("����֤".equals(workstate)) {
                                // ����֤�����
                                overtimenum = Float.parseFloat(rowBean2.get("overtimenum")
                                        .toString());
                                // ȡ�ÿ�����
                                weeks = SendTaskUtil.getWeeks(replytime);
                                if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                                    personList.remove(j);
                                }
                            } else {
                                // ����֤�������
                                validatetime = String.valueOf(rowBean2.get("validatetime"));
                                overtimenum = Float.parseFloat(rowBean2.get("overtimenum")
                                        .toString());
                                // ȡ�ÿ�����
                                weeks = SendTaskUtil.getWeeks(replytime, validatetime);
                                if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                                    personList.remove(j);
                                }
                            }
                        }

                        rowBean.set("overtimevalid", new BigDecimal(String.valueOf(personList
                                .size())));
                    }
                }
                // ����֤��ʱ��һ�������ų���Щȥ����������û�г�ʱ�� add by guixy 2008-1-8
                request.getSession().setAttribute("type", "112");
                // request.getSession().setAttribute("personcountList",
                // personCountList);
                request.getSession().setAttribute("querycon", bean);

                request.getSession().setAttribute("numtotallist", totalList);
            }

            super.setPageReset(request);
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("���ز�ѯ�ɵ�����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public static void main(String[] args) {
        System.out.println(new BigDecimal(String.valueOf(((int) (-0.889994 * 10)))));
    }

}
