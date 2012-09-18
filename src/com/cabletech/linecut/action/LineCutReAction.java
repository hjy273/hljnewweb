package com.cabletech.linecut.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.DefaultCategoryDataset;

import com.cabletech.baseinfo.dao.UserInfoDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.linecut.beans.LineCutBean;
import com.cabletech.linecut.property.Property;
import com.cabletech.linecut.services.LineCutReService;
import com.cabletech.power.CheckPower;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.UploadUtil;

public class LineCutReAction extends BaseDispatchAction {
    private LineCutReService service = new LineCutReService();

    private static final String STAT_COUNT = "1"; // ͳ�Ƹ�Ӵ���

    private static final String STAT_TIME = "2";// ͳ�Ƹ��ʱ��

    private static final String BY_LEVEL = "1";// ����·����ͳ��

    private static final String BY_TYPE = "2";// ���������ͳ��

    private static Logger logger = Logger.getLogger(LineCutReAction.class.getName());

    /**
     * ���������ʾ
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
     * @throws Exception
     */
    public ActionForward addShow(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), "30101")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // ������ƶ���˾�ǲ������
            return forwardErrorPage(mapping, request, "partstockerror");
        }
        /*
         * List lLine = service.getLineList(userinfo); List lSubLine =
         * service.getSubLineList(userinfo); request.setAttribute("line",lLine);
         * request.setAttribute("subline",lSubLine);
         */
        request.getSession().setAttribute("type", "r2");
        return mapping.findForward("success");
    }

    /**
     * ��ȡ��·����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getLineLevle(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List lineLevelList = service.getLineLevle();
        request.setAttribute("lineLevelList", lineLevelList);
        return mapping.findForward("lineLevel");
    }

    /**
     * �����û�����·���������·
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getLineByLevelId(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/json; charset=GBK");
        String levelId = request.getParameter("levelId");
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List lineList = service.getLineByLevel(userinfo, levelId);

        JSONArray ja = JSONArray.fromObject(lineList);
        PrintWriter out = response.getWriter();
        out.write(ja.toString());
        out.flush();
        return null;
    }

    /**
     * �����û�����·�����߶�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getClewByLineId(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/json; charset=GBK");
        String lineId = request.getParameter("lineId");
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List subLineList = service.getSubLineByLineId(userinfo, lineId);
        JSONArray ja = JSONArray.fromObject(subLineList);
        PrintWriter out = response.getWriter();
        out.write(ja.toString());
        return null;
    }

    /**
     * ִ���������
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
    public ActionForward addRe(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30101")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // ������ƶ���˾�ǲ������
            return forwardErrorPage(mapping, request, "partstockerror");
        }

        try {
            LineCutBean bean = (LineCutBean) form;
            userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
            bean.setContractorid(userinfo.getDeptID());
            bean.setReuserid(userinfo.getUserID());

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
            // ArrayList fileIdList = new ArrayList();
            // for (int i = 0; i < attachments.size(); i++) {
            // UploadFile uploadFile = (UploadFile) attachments.get(i);
            // FormFile file = uploadFile.getFile();
            // if (file == null) {
            // logger.error("file is null");
            // } else {
            // //���ļ��洢������������·��д�����ݿ⣬���ؼ�¼ID
            // fileId = SaveUploadFile.saveFile(file);
            // if (fileId != null) {
            // fileIdList.add(fileId);
            // }
            // }
            // }
            // �����ϴ��ļ�����=======================================
            // ��ȡID�ַ����б�(�Զ��ŷָ����ļ�ID�ַ���)======================
            String fileIdListStr = UploadUtil.getFileIdList(fileIdList);
            if (fileIdListStr != null) {
                bean.setReacce(fileIdListStr);
            }
            // ======================
            String updoc = request.getParameter("UPDOC");
            System.out.println(bean.getCutType());
            System.out.println(updoc + "�����Ƿ���Ҫ�޸�");
            if (!service.addInfo(bean, updoc)) {
                return forwardErrorPage(mapping, request, "30102e");
            }
            // ���Ͷ���
            if (request.getSession().getAttribute("isSendSm").equals("send")) {
                String objectman = request.getParameter("phone");
                if (objectman != null && !objectman.equals("")) {
                    String msg = Property.LINE_CUT_MODULE
                            + request.getSession().getAttribute("LOGIN_USER_DEPT_NAME")
                            + "���µĸ������ȴ���������  �����ˣ�" + userinfo.getUserName() + SendSMRMI.MSG_NOTE;
                    SendSMRMI.sendNormalMessage(userinfo.getUserID(), objectman, msg, "00");
                    logger.info(msg);
                }
            }
            log(request, "��ӹ���", "��Ӹ������");
            return forwardInfoPage(mapping, request, "30102");
        } catch (Exception e) {
            logger.error("��������쳣:" + e.getMessage());
            log(request, "�����·������� ::" + e.getMessage(), "�������");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾ�������뵥
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
    public ActionForward showAllRe(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30102")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // ������ƶ���˾�ǲ������
            return forwardErrorPage(mapping, request, "partstockerror");
        }

        try {
            List lReqInfo = service.getAllOwnRe(request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "r1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾ�������뵥��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾһ�����뵥����ϸ��Ϣ
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
    public ActionForward showOneInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30101")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // ������ƶ���˾�ǲ������
            return forwardErrorPage(mapping, request, "partstockerror");
        }
        String reid = request.getParameter("reid"); // ���Ҫ�޸ĵ����뵥id
        String apphavemore = "0";
        try {
            // ������뵥������Ϣ
            LineCutBean reqinfo = new LineCutBean();
            List reApprove = service.getReApprove(reid);
            if (service.valiApprove(reid)) {// �Ѿ�������.
                reqinfo = service.getOneUseForDMod(reid);
                if (reApprove.size() > 0)// �������
                    apphavemore = "1";
            } else {// ��δ������
                reqinfo = service.getOneUseMod(reid);
            }

            request.setAttribute("apphavemore", apphavemore);
            request.setAttribute("reapprove", reApprove);

            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "r10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("����ʾ��ϸ�г��ִ���:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * �޸���ʾ
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
    public ActionForward upshow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30104")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // ������ƶ���˾�ǲ������
            return forwardErrorPage(mapping, request, "partstockerror");
        }
        LineCutBean reqinfo = new LineCutBean(); // ����ҳ������뵥������Ϣ
        String reid = request.getParameter("reid"); // ���Ҫ�޸ĵ����뵥id

        try {
            // �ܷ��޸�
            if (!service.valiCanUp(reid)) {
                return forwardErrorPage(mapping, request, "30104e");
            }
            reqinfo = service.getOneUseMod(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "r4");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("���޸���ʾ�г��ִ���:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ִ���޸�
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
    public ActionForward doUp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        // Ȩ�޼��
        if (!CheckPower.checkPower(request.getSession(), "30104")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // ������ƶ���˾�ǲ������
            // return forwardErrorPage(mapping, request, "partstockerror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "partstockerror", backUrl);
        }
        try {
            LineCutBean bean = (LineCutBean) form;
            userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
            bean.setContractorid(userinfo.getDeptID());
            bean.setReuserid(userinfo.getUserID());

            ArrayList fileIdList = new ArrayList();
            String[] delfileid = request.getParameterValues("delfileid"); // Ҫɾ�����ļ�id������
            // ����µĸ�����
            StringTokenizer st = new StringTokenizer(bean.getReacce(), ",");
            while (st.hasMoreTokens()) {
                fileIdList.add(st.nextToken());
            }
            if (delfileid != null) {
                for (int i = 0; i < delfileid.length; i++) {
                    fileIdList.remove(delfileid[i]);
                }
            }
            // ��ʼ�����ϴ��ļ�================================
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

            // List attachments = bean.getAttachments();
            // String fileId;
            // for (int i = 0; i < attachments.size(); i++) {
            // UploadFile uploadFile = (UploadFile) attachments.get(i);
            // FormFile file = uploadFile.getFile();
            // if (file == null) {
            // logger.error("file is null");
            // } else {
            // //���ļ��洢������������·��д�����ݿ⣬���ؼ�¼ID
            // fileId = SaveUploadFile.saveFile(file);
            // if (fileId != null) {
            // fileIdList.add(fileId);
            // }
            // }
            // }
            // �����ϴ��ļ�����=======================================
            // ��ȡID�ַ����б�(�Զ��ŷָ����ļ�ID�ַ���)======================
            String fileIdListStr = UploadUtil.getFileIdList(fileIdList);
            if (fileIdListStr != null) {
                bean.setReacce(fileIdListStr);
            }
            // ======================
            if (!service.doUp(delfileid, bean)) {
                // return forwardErrorPage(mapping, request, "30104ee");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "30104ee", backUrl);
            }
            log(request, "��ӹ���", "���¸������");
            // return forwardInfoPage(mapping, request, "30104");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "30104", backUrl);
        } catch (Exception e) {
            logger.error("���������쳣:" + e.getMessage());
            log(request, "������·������� ::" + e.getMessage(), "�������");
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * ִ��ɾ��һ�����뵥
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
    public ActionForward doDel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        // Ȩ�޼��
        if (!CheckPower.checkPower(request.getSession(), "30105")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // ������ƶ���˾�ǲ������
            // return forwardErrorPage(mapping, request, "partstockerror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "partstockerror", backUrl);
        }
        try {
            String reid = (String) request.getParameter("reid");
            String reacce = (String) request.getParameter("reacce");
            if (!service.valiCanUp(reid)) { // ����ܷ�ɾ��,��ɾ��
                // return forwardErrorPage(mapping, request, "30105e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "30105e", backUrl);
            }
            if (!service.doDel(reacce, reid)) {
                // return forwardErrorPage(mapping, request, "error");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
            }
            // return forwardInfoPage(mapping, request, "30105");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "30105", backUrl);
        } catch (Exception e) {
            logger.error("��ɾ�����뵥�г���:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * �ۺϲ�ѯ��ʾ
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
    public ActionForward queryShow(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30103")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // ������ƶ���˾�ǲ������
            return forwardErrorPage(mapping, request, "partstockerror");
        }
        try {
            String contractorid = (String) request.getSession().getAttribute("LOGIN_USER_DEPT_ID");
            // ��ȡ��·����
            List levelList = service.getLineLevle();
            request.setAttribute("linelevelList", levelList);

            // ��ò������б�
            List lusers = service.getAllUsers(contractorid);
            request.setAttribute("lusers", lusers);

            // ��ø�������б�
            /*
             * List lnames = service.getAllNames( contractorid );
             * request.setAttribute( "lnames", lnames );
             */

            // ��ø��ԭ���б�
            List lreasons = service.getAllReasons(contractorid);
            request.setAttribute("lreasons", lreasons);

            // ��ø�ӵص��б�
            List laddresss = service.getAllAddresss(contractorid);
            request.setAttribute("laddresss", laddresss);

            // �����Ӱ��ϵͳ�б�
            List lefsystem = service.getAllEfsystems(contractorid);
            request.setAttribute("lefsystem", lefsystem);

            // ����漰���¶�
            /*
             * List lsublines = service.getAllSublineids( contractorid );
             * request.setAttribute( "lsublines", lsublines );
             */

            request.getSession().setAttribute("type", "r3");
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("�ۺϲ�ѯ��ʾ�쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ִ���ۺϲ�ѯ
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
    public ActionForward doQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30103")) {
            return mapping.findForward("powererror");
        }

        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // ������ƶ���˾�ǲ������
            return forwardErrorPage(mapping, request, "partstockerror");
        }

        LineCutBean bean = (LineCutBean) form;
        String contractorid = (String) request.getSession().getAttribute("LOGIN_USER_DEPT_ID");
        bean.setContractorid(contractorid);
        try {

            // List lReqInfo = service.getAllOwnReForSearch(bean,
            // request.getSession());
            List lReqInfo = service.getConditionsReForSearch(bean, request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "r1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("ִ���ۺϲ�ѯ�쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward doQueryAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("lcAuQueryCon");// lcQueryCon
        if (sql != null && sql.trim().length() != 0) {
            List lReqInfo = service.doQueryAfterMod(sql);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "r1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("�����ۺϲ�ѯ����쳣:ԭ��session����");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // ===========================��������=============================//

    /**
     * ��ʾ���д��������뵥��Ϣ
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
     * @throws Exception
     */
    public ActionForward auditShow(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        LineCutBean bean = (LineCutBean) form;
        if (!CheckPower.checkPower(request.getSession(), "30201")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // ����Ǵ�ά��λ�ǲ������
            return forwardErrorPage(mapping, request, "partauditerror");
        }
        try {
            List lReqInfo = service.getAllReForAu(userinfo.getRegionID());
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "reforAu1");

            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾ���д��������뵥��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }

    }

    /**
     * ��ʾһ�������������뵥����ϸ��Ϣ
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
    public ActionForward showOneInfoForAu(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30201")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        System.out.println(userinfo.getDeptype() + "��λ���");
        if (userinfo.getDeptype().equals("2")) { // ����Ǵ�ά��λ�ǲ������
            return forwardErrorPage(mapping, request, "partauditerror");
        }
        String reid = request.getParameter("reid"); // ���Ҫ���������뵥id

        try {
            // ���Ҫ�������뵥������Ϣ
            LineCutBean reqinfo = service.getOneUseMod(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "au2");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾһ�������������뵥����ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ִ������
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
    public ActionForward addAu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30201")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // ����Ǵ�ά��λ�ǲ������
            // return forwardErrorPage(mapping, request, "partauditerror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "partauditerror", backUrl);
        }
        try {
            LineCutBean bean = (LineCutBean) form;
            userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
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
            // //���ļ��洢������������·��д�����ݿ⣬���ؼ�¼ID
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
            if (!service.addAuInfo(bean)) {
                // return forwardErrorPage(mapping, request, "30202e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "30202e", backUrl);
            }
            if (request.getSession().getAttribute("isSendSm").equals("send")) {
                String phone = new UserInfoDAOImpl().findById(
                        service.getOneUseMod(bean.getReid()).getReuserid()).getPhone();
                String objectman = phone;
                if (objectman != null && !objectman.equals("")) {
                    // String msg = "";

                    String linename = bean != null ? bean.getName() : "";
                    String audittime = bean != null ? bean.getAudittime() : "";
                    StringBuffer sb = new StringBuffer();
                    sb.append(Property.LINE_CUT_MODULE).append(linename).append("��").append(
                            audittime).append("����").append(bean.getAuditresult()).append(" ������:")
                            .append(userinfo.getUserName()).append(SendSMRMI.MSG_NOTE);
                    SendSMRMI.sendNormalMessage(userinfo.getUserID(), userinfo.getUserName(), sb
                            .toString(), "00");

                    // if (bean.getAuditresult().equals("ͨ������")) {
                    // msg = "������������ύ������ͨ������. �����ˣ�" + userinfo.getUserName();
                    // } else {
                    // msg = "������������ύ������δͨ������," + bean.getAuditremark()
                    // + " . �����ˣ�" + userinfo.getUserName();
                    // }
                    // SendSMRMI.sendNormalMessage(userinfo.getUserID(),
                    // objectman, msg+SendSMRMI.MSG_NOTE, "00");
                    logger.info("��������:" + sb.toString());

                }
            }

            log(request, "��ӹ���", "�����������");
            // return forwardInfoPage(mapping, request, "30202");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "30202", backUrl);
        } catch (Exception e) {
            logger.error("ִ�������쳣:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * ��ʾ����������
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
    public ActionForward showAllAu(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30202")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // ����Ǵ�ά��λ�ǲ������
            return forwardErrorPage(mapping, request, "partauditerror");
        }

        try {
            List lReqInfo = service.getAllOwnAu(request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "au1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾ��������������:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾһ������������ϸ��Ϣ
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
    public ActionForward showOneAuInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // if (!CheckPower.checkPower(request.getSession(), "30201")) {
        // return mapping.findForward("powererror");
        // }
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        // if (userinfo.getDeptype().equals("2")) { //����Ǵ�ά��λ�ǲ������
        // return forwardErrorPage(mapping, request, "partauditerror");
        // }
        String reid = request.getParameter("reid"); // ���Ҫ��ʾ�����뵥id
        String flg = request.getParameter("flg");
        if (flg == null) {
            request.setAttribute("flg", "1");
        } else {
            request.setAttribute("flg", "2");
        }
        try {
            // ������뵥������Ϣ
            LineCutBean reqinfo = service.getOneAuInfoMod(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "au10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("����ʾ��ϸ�г��ִ���:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * �����ۺϲ�ѯ��ʾ
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
    public ActionForward queryShowForAu(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30203")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // ����Ǵ�ά��λ�ǲ������
            return forwardErrorPage(mapping, request, "partauditerror");
        }
        try {
            String deptid = userinfo.getDeptID();

            // ��ȡ��·����
            List levelList = service.getLineLevle();
            request.setAttribute("linelevelList", levelList);

            // ��ø�������б�
            List lnames = service.getAllNamesForAu(deptid);
            request.setAttribute("lnames", lnames);
            // ��ø��ԭ���б�
            List lreasons = service.getAllReasonsForAu(deptid);
            request.setAttribute("lreasons", lreasons);
            // ��ø�ӵص��б�
            List laddresss = service.getAllAddresssForAu(deptid);
            request.setAttribute("laddresss", laddresss);
            // �����Ӱ��ϵͳ�б�
            List lefsystem = service.getAllEfsystemsForAu(deptid);
            request.setAttribute("lefsystem", lefsystem);
            // ����漰���¶�
            /*
             * List lsublines = service.getAllSublineidsForAu( deptid );
             * request.setAttribute( "lsublines", lsublines );
             */
            // ����������б�
            List lusers = service.getAllUsersForAu(deptid);
            request.setAttribute("lusers", lusers);
            request.getSession().setAttribute("type", "au3");
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("�����ۺϲ�ѯ��ʾ�쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ִ���ۺϲ�ѯ
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
    public ActionForward doQueryForAu(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30203")) {
            return mapping.findForward("powererror");
        }

        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // ����Ǵ�ά��λ�ǲ������
            return forwardErrorPage(mapping, request, "partauditerror");
        }

        LineCutBean bean = (LineCutBean) form;
        String deptid = userinfo.getDeptID();
        bean.setDeptid(deptid);
        try {
            List lReqInfo = service.getConditionsReForSearch(bean, request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "au1");
            super.setPageReset(request);

            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("ִ���ۺϲ�ѯ�쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward doQueryForAuAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("lcAuQueryCon");
        if (sql != null && sql.trim().length() != 0) {
            List lReqInfo = service.getAllOwnReForAuSearchAfterMod(sql);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "au1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("ִ���ۺϲ�ѯ�쳣:session����");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward getNameByClewId(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/json; charset=GBK");
        String sublineid = request.getParameter("sublineId");
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String deptid = userinfo.getDeptID();
        // List sublinename = service.getNameByClewId(sublineid, deptid);

        List sublinename = service.getNameByClewId(sublineid);

        JSONArray ja = JSONArray.fromObject(sublinename);
        try {
            PrintWriter out = response.getWriter();
            out.write(ja.toString());
            out.flush();
            out.close();
            System.out.println(ja.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ActionForward getNameBySublineid(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/json; charset=GBK");
        String sublineid = request.getParameter("sublineId");
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List sublinename = service.getNameBySublineid(sublineid, userinfo);
        JSONArray ja = JSONArray.fromObject(sublinename);
        try {
            PrintWriter out = response.getWriter();
            out.write(ja.toString());
            out.flush();
            out.close();
            System.out.println(ja.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ActionForward getNameBySublineidAndDeptid(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/json; charset=GBK");
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String deptid = userinfo.getDeptID();
        String sublineid = request.getParameter("sublineId");
        List sublinename = service.getNameBySublineidAndDeptid(sublineid, deptid, userinfo);
        JSONArray ja = JSONArray.fromObject(sublinename);
        try {
            PrintWriter out = response.getWriter();
            out.write(ja.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ActionForward getCutNameBySublineid(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/json; charset=GBK");
        String contractorid = (String) request.getSession().getAttribute("LOGIN_USER_DEPT_ID");
        String sublineid = request.getParameter("sublineId");
        List sublinename = service.getCutNameBySublineid(sublineid, contractorid);
        JSONArray ja = JSONArray.fromObject(sublinename);
        try {
            PrintWriter out = response.getWriter();
            out.write(ja.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ActionForward showStatQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        /*
         * List levelList = service.getLineLevle();
         * System.out.println(levelList.size());
         * request.setAttribute("line_level_list", levelList);
         */
        return mapping.findForward("showStatQuery");
    }

    // ͳ��
    public ActionForward doStatQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        String statType = request.getParameter("statType");
        String statCon = request.getParameter("statCon");
        String rest_query = request.getParameter("rest_query");
        if ("1".equals(rest_query)) {
            if (STAT_COUNT.equals(statType)) {// ���������ͳ��
                if (BY_LEVEL.equals(statCon)) {// ����ǰ���·������ͳ��
                    return this.doQueryForCountByLevel(mapping, form, request, response);
                }
                if (BY_TYPE.equals(statCon)) {// ����ǰ����������ͳ��
                    return this.doQueryForCountByType(mapping, form, request, response);
                }
            } else if (STAT_TIME.equals(statType)) {// �����ʱ��ͳ��
                if (BY_LEVEL.equals(statCon)) {// ����ǰ���·������ͳ��
                    return this.doQueryForTimeByLevel(mapping, form, request, response);
                } else if (BY_TYPE.equals(statCon)) {// ����ǰ����������ͳ��
                    return this.doQueryForTimeByType(mapping, form, request, response);
                }
            }
        }
        return null;
    }

    /**
     * ���ݸ�����ͣ����� ͳ�Ƹ�Ӵ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward doQueryForCountByType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        List queryRes = null;
        String begintime = request.getParameter("begintime");
        String endtime = request.getParameter("endtime");
        String condition = "";
        if (begintime != null && !begintime.equals("")) {
            condition = condition + " and lc.essetime >=TO_DATE('" + begintime + "','YYYY-MM-DD')";
        }
        if (endtime != null && !endtime.equals("")) {
            condition = condition + " and lc.essetime <= TO_DATE('" + endtime
                    + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
        }
        queryRes = service.doStatQueryForCountByCutType(condition);
        request.getSession().setAttribute("queryRes", queryRes);
        return mapping.findForward("typeForCount");
    }

    private void getReportForCountByLevel(List statList, HttpServletRequest request)
            throws IOException {
        DefaultCategoryDataset dataset = null;
        DynaBean bean = null;
        if (statList != null && statList.size() != 0) {
            dataset = new DefaultCategoryDataset();
            for (int i = 0; i < statList.size(); i++) {
                bean = (DynaBean) statList.get(i);
                String conname = (String) bean.get("contractorname");
                String one = String.valueOf(bean.get("one"));
                String two = String.valueOf(bean.get("two"));
                String three = String.valueOf(bean.get("three"));
                String four = String.valueOf(bean.get("four"));
                String five = String.valueOf(bean.get("five"));
                String totalnum = String.valueOf(bean.get("totalnum"));
                dataset.addValue(Integer.parseInt(one), conname, "һ��");
                dataset.addValue(Integer.parseInt(two), conname, "����");
                dataset.addValue(Integer.parseInt(three), conname, "��ۻ�");
                dataset.addValue(Integer.parseInt(four), conname, "������");
                dataset.addValue(Integer.parseInt(five), conname, "�Ǹɲ�");
                dataset.addValue(Integer.parseInt(totalnum), conname, "�������");
            }
        }
        JFreeChart chart = ChartFactory.createBarChart3D("���ͳ��ͼ",// ͼ�����
                "��·����", // Ŀ¼����ʾ��ǩ
                "��Ӵ���",// ��ֵ����ʾ��ǩ
                dataset,// ���ݼ�
                PlotOrientation.VERTICAL,// ͼ����ˮƽ����ֱ
                true,// �Ƿ�����ͼ��
                false,// �Ƿ����ɹ���
                false// �Ƿ�����URL����
                );
        String filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, null, request
                .getSession());
        String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;
        request.setAttribute("graphURL", graphURL);
        request.setAttribute("filename", filename);
    }

    /**
     * ������·�������� ͳ�Ƹ�Ӵ���
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward doQueryForCountByLevel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        List queryRes = null;
        String begintime = request.getParameter("begintime");
        String endtime = request.getParameter("endtime");
        String condition = "";
        if (begintime != null && !begintime.equals("")) {
            condition = condition + " and lc.essetime >=TO_DATE('" + begintime + "','YYYY-MM-DD')";
        }
        if (endtime != null && !endtime.equals("")) {
            condition = condition + " and lc.essetime <= TO_DATE('" + endtime
                    + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
        }
        queryRes = service.doQueryForCountByLevel(condition);
        request.getSession().setAttribute("queryRes", queryRes);
        super.setPageReset(request);
        return mapping.findForward("levelForCount");
    }

    /**
     * ������·���� ���� ͳ�Ƹ��ʱ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryForTimeByLevel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List queryRes = null;
        String begintime = request.getParameter("begintime");
        String endtime = request.getParameter("endtime");
        String condition = "";
        if (begintime != null && !begintime.equals("")) {
            condition = condition + " and lc.essetime >=TO_DATE('" + begintime + "','YYYY-MM-DD')";
        }
        if (endtime != null && !endtime.equals("")) {
            condition = condition + " and lc.essetime <= TO_DATE('" + endtime
                    + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
        }
        queryRes = service.doQueryForTimeByLevel(condition);
        request.getSession().setAttribute("queryRes", queryRes);
        super.setPageReset(request);
        return mapping.findForward("levelForTime");
    }

    /**
     * ���ݸ������ ���� ͳ�Ƹ��ʱ��(��ʱû��)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryForTimeByType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List queryRes = null;
        String begintime = request.getParameter("begintime");
        String endtime = request.getParameter("endtime");
        String condition = "";
        if (begintime != null && !begintime.equals("")) {
            condition = condition + " and lc.essetime >=TO_DATE('" + begintime + "','YYYY-MM-DD')";
        }
        if (endtime != null && !endtime.equals("")) {
            condition = condition + " and lc.essetime <= TO_DATE('" + endtime
                    + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
        }
        queryRes = service.doQueryForTimeByType(condition);
        request.getSession().setAttribute("queryRes", queryRes);
        super.setPageReset(request);
        return mapping.findForward("typeForTime");
    }

    // ����ͳ�Ƶ�excel
    // ������ ��������� ������ѯ�ĸ�Ӵ������
    public ActionForward exportCountByType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List list = (List) request.getSession().getAttribute("queryRes");
        if (service.exportCountByType(list, response)) {
            logger.info("����EXCEL�ɹ�");
        } else {
            logger.info("����EXCEL�����쳣");
        }
        return null;
    }

    // ������ ��·������ ������ѯ�ĸ�Ӵ������
    public ActionForward exportCountByLevel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List list = (List) request.getSession().getAttribute("queryRes");
        if (service.exportCountByLevel(list, response)) {
            logger.info("����EXCEL�ɹ�");
        } else {
            logger.info("����EXCEL�����쳣");
        }
        return null;
    }

    // ������ ��·������ ������ѯ�ĸ�Ӻ�ʱ���
    public ActionForward exportTimeByLevel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List list = (List) request.getSession().getAttribute("queryRes");
        if (service.exportTimeByLevel(list, response)) {
            logger.info("����EXCEL�ɹ�");
        } else {
            logger.info("����EXCEL�����쳣");
        }
        return null;
    }

    // ������������� ���� ͳ�Ƹ��ʱ��(��ʱû��)
    public ActionForward exportTimeByType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List list = (List) request.getSession().getAttribute("queryRes");
        if (service.exportTimeByType(list, response)) {
            logger.info("����EXCEL�ɹ�");
        } else {
            logger.info("����EXCEL�����쳣");
        }
        return null;
    }

    public ActionForward lineCutSearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String contractorid = (String) request.getSession().getAttribute("LOGIN_USER_DEPT_ID");
        // ��ȡ��·����
        List levelList = service.getLineLevle();
        request.setAttribute("linelevelList", levelList);

        // ��ø�������б�
        /*
         * List lnames = service.getAllNames( contractorid );
         * request.setAttribute( "lnames", lnames );
         */

        // ��ø��ԭ���б�
        List lreasons = service.getAllReasons(contractorid);
        request.setAttribute("lreasons", lreasons);

        // ��ø�ӵص��б�
        List laddresss = service.getAllAddresss(contractorid);
        request.setAttribute("laddresss", laddresss);

        // �����Ӱ��ϵͳ�б�
        List lefsystem = service.getAllEfsystems(contractorid);
        request.setAttribute("lefsystem", lefsystem);
        return mapping.findForward("lineCutSearch");
    }

    /**
     * ��Ӳ�ѯͳ�Ʋ���->��Ӳ�ѯ
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
    public ActionForward queryCutBeanStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

        String state = request.getParameter("state");

        LineCutBean bean = (LineCutBean) form;
        String contractorid = (String) request.getSession().getAttribute("LOGIN_USER_DEPT_ID");
        bean.setContractorid(contractorid);
        try {

            // List lReqInfo = service.getAllOwnReForSearch(bean,
            // request.getSession());
            List lReqInfo = null;
            if ("detail".equalsIgnoreCase(state)) {
                lReqInfo = (List) request.getSession().getAttribute("lineCutQueryList");
            } else {
                lReqInfo = service.getConditionsReForQuery(bean, request);
            }
            request.getSession().setAttribute("lineCutQueryList", lReqInfo);
            this.setPageReset(request);
            return mapping.findForward("lineCutQueryList");
        } catch (Exception e) {
            logger.error("ִ���ۺϲ�ѯ�쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // ������ѯͳ���в�ѯ��������
    public ActionForward exportQueryStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List list = (List) request.getSession().getAttribute("lineCutQueryList");
        if (service.exportQueryStat(list, response)) {
            logger.info("����EXCEL�ɹ�");
        } else {
            logger.info("����EXCEL�����쳣");
        }
        return null;
    }

    public ActionForward getOneQueryDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        String reid = request.getParameter("reid"); // ���Ҫ��ʾ�����뵥id
        try {
            // ������뵥������Ϣ
            LineCutBean detailInfo = service.getOneCutQueryInfo(reid);
            request.setAttribute("detailInfo", detailInfo);
            return mapping.findForward("lineCutDetail");
        } catch (Exception e) {
            logger.error("��ʾһ����ӵ���ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
    /*
     * public ActionForward getReasonByInput( ActionMapping mapping, ActionForm
     * form, HttpServletRequest request, HttpServletResponse response ) throws
     * Exception{ request.setCharacterEncoding("UTF-8");
     * response.setHeader("Cache-Control","no-store");
     * response.setHeader("Pragma","no-cache");
     * response.setDateHeader("Expires",0); String contractorid = ( String
     * )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" ); List
     * reasonList = null; String searchCt= request.getParameter("search");
     * System.out.println(searchCt + "!!!!!!!!!!!!"); if(searchCt != null &&
     * searchCt.trim().length() !=0) { PrintWriter out = response.getWriter();
     * String s = service.getReasonByInputMod(searchCt, contractorid);
     * System.out.println(s + "~~~~~~~~~"); out.println(s);
     * System.out.println(URLEncoder.encode(s,"utf-8")); out.flush();
     * out.close(); } return null; }
     */

    /*
     * public ActionForward getAllName(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response) {
     * 
     * return null; }
     * 
     * public ActionForward getAllReason(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response) {
     * response.setContentType("text/html; charset=utf-8"); UserInfo userinfo =
     * (UserInfo) request.getSession().getAttribute( "LOGIN_USER"); String
     * deptid = userinfo.getDeptID(); String reason =
     * service.getAllReason(deptid); PrintWriter out = null; try { out =
     * response.getWriter(); } catch (IOException e) { e.printStackTrace(); }
     * System.out.println(reason + "!"); out.write(reason); out.flush();
     * out.close(); return null; }
     * 
     * public ActionForward getAllAddress(ActionMapping mapping, ActionForm
     * form, HttpServletRequest request, HttpServletResponse response) {
     * 
     * return null; }
     * 
     * public ActionForward getAllEfsystem(ActionMapping mapping, ActionForm
     * form, HttpServletRequest request, HttpServletResponse response) {
     * 
     * return null; }
     */
}
