package com.cabletech.linecut.action;

import java.util.*;

import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.apache.struts.upload.*;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.linecut.beans.*;
import com.cabletech.linecut.services.*;
import com.cabletech.power.*;
import com.cabletech.uploadfile.*;

public class LineCutWorkAction extends BaseDispatchAction {
    private LineCutWorkService service = new LineCutWorkService();

    private static Logger logger = Logger.getLogger(LineCutWorkAction.class.getName());

    /**
     * ���ʩ����Ϣ��ʾ
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
    public ActionForward workShow(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), "30301")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // ������ƶ���˾�ǲ������
            return forwardErrorPage(mapping, request, "partauditerror");
        }
        try {
            List lReqInfo = service.getAllReForWork(userinfo);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "reforwork1");
            this.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("���ʩ����Ϣ��ʾ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }

    }

    /**
     * ��ʾһ�������ʩ����Ϣ�ĸ����ϸ��Ϣ
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
    public ActionForward showOneInfoForWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30301")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // ������ƶ���λ�ǲ������
            return forwardErrorPage(mapping, request, "partauditerror");
        }
        String reid = request.getParameter("reid"); // ���Ҫ���ʩ����Ϣ�����뵥id
        try {
            LineCutBean reqinfo = service.getOneReInfo(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "work2");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾһ�������ʩ����Ϣ�ĸ����ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ִ�����ʩ����Ϣ
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
    public ActionForward addWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30301")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // ������ƶ���˾�ǲ������
            // return forwardErrorPage(mapping, request, "partauditerror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "partauditerror", backUrl);
        }
        try {
            LineCutBean bean = (LineCutBean) form;
            userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
            bean.setWorkuserid(userinfo.getUserID());

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
                bean.setWorkacce(fileIdListStr);
            }
            // ======================
            if (!service.addWorkInfo(bean)) {
                // return forwardErrorPage(mapping, request, "30302e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "30302e", backUrl);
            }
            log(request, "��ӹ���", "���ʩ����Ϣ");
            // return forwardInfoPage(mapping, request, "30302");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "30302", backUrl);
        } catch (Exception e) {
            logger.error("ִ�����ʩ����Ϣ�쳣:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * ��ʾ�����Ѿ�ʩ���ĸ����Ϣ
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
    public ActionForward showAllWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30301")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List lReqInfo = null;
        try {
            if (userinfo.getDeptype().equals("2")) {
                lReqInfo = service.getAllOwnWork(request);
            } else {
                lReqInfo = service.getAllWork(request);
            }

            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "work1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾ�����Ѿ�ʩ���ĸ����Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾһ�����ʩ������ϸ��Ϣ
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
    public ActionForward showOneWorkInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        /*
         * if( !CheckPower.checkPower( request.getSession(), "30301" ) ){ return
         * mapping.findForward( "powererror" ); }
         */
        String reid = request.getParameter("reid"); // ���Ҫ��ʾ�����뵥id
        try {
            // ������뵥������Ϣ
            LineCutBean reqinfo = service.getOneWorkInfo(reid);

            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "work10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("����ʾ��ϸ�г��ִ���:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // ���ʩ���޸���ʾ
    public ActionForward showWorkUp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30304")) {
            return mapping.findForward("powererror");
        }
        String reid = request.getParameter("reid"); // ���Ҫ�޸ĵ����뵥id
        try {
            // ������뵥������Ϣ
            LineCutBean reqinfo = service.getOneWorkInfo(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "work4");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("�ڸ��ʩ���޸���ʾ�г��ִ���:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // ���ʩ���޸��ύ
    public ActionForward WorkUp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30304")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        try {
            LineCutBean bean = (LineCutBean) form;
            bean.setWorkuserid(((UserInfo) request.getSession().getAttribute("LOGIN_USER"))
                    .getUserID());
            ;
            /** *�ļ��ϴ� �޸�* */
            ArrayList fileIdList = new ArrayList();
            String[] delfileid = request.getParameterValues("delfileid"); // Ҫɾ�����ļ�id������
            // ����µĸ�����
            StringTokenizer st = new StringTokenizer(bean.getWorkacce(), ",");
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
            // int size = attachments.size();
            // for( int i = 0; i < size; i++ ){
            // UploadFile uploadFile = ( UploadFile )attachments.get( i );
            // FormFile file = uploadFile.getFile();
            //
            // if( file == null ){
            // logger.info( "file is null" );
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
            // ��ȡID�ַ����б�(�Զ��ŷָ����ļ�ID�ַ���)=================
            String fileIdListStr = UploadUtil.getFileIdList(fileIdList);
            if (fileIdListStr != null) {
                bean.setWorkacce(fileIdListStr);
            }
            // ======================
            if (!service.WorkUp(bean)) {
                // return forwardErrorPage(mapping, request, "30304e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "30304e", backUrl);
            }
            log(request, "��ӹ���", "�޸�ʩ����Ϣ");
            // return forwardInfoPage(mapping, request, "30304");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "30304", backUrl);
        } catch (Exception e) {
            logger.error("�ڸ��ʩ���޸��г��ִ���:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    // ʩ���ۺϲ�ѯ��ʾ
    public ActionForward queryShowForWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30303")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

        try {
            String deptid = userinfo.getDeptID();
            // ��ȡ��·����
            List levelList = new LineCutReService().getLineLevle();
            request.setAttribute("lineLevelList", levelList);

            // ��ø�������б�
            List lnames = service.getAllNamesForWork(deptid);
            request.setAttribute("lnames", lnames);
            // ��ø��ԭ���б�
            List lreasons = service.getAllReasonsForWork(deptid);
            request.setAttribute("lreasons", lreasons);
            // ��ø�ӵص��б�
            List laddresss = service.getAllAddresssForWork(deptid);
            request.setAttribute("laddresss", laddresss);
            // �����Ӱ��ϵͳ�б�
            List lefsystem = service.getAllEfsystemsForWork(deptid);
            request.setAttribute("lefsystem", lefsystem);
            // ����漰���¶�
            /*
             * List lsublines = service.getAllSublineidsForWork( deptid );
             * request.setAttribute( "lsublines", lsublines );
             */

            request.getSession().setAttribute("type", "work3");
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("ʩ���ۺϲ�ѯ��ʾ�쳣:" + e.getMessage());
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
    public ActionForward doQueryForWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30303")) {
            return mapping.findForward("powererror");
        }

        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        LineCutBean bean = (LineCutBean) form;
        String deptid = userinfo.getDeptID();
        bean.setDeptid(deptid);
        try {
            // List lReqInfo = service.getAllOwnReForWorkSearch( bean, userinfo
            // ,request.getSession());
            List lReqInfo = service.getConditionsReForWorkSearch(bean, userinfo, request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "work1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("ִ���ۺϲ�ѯ�쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward doQueryForWorkAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("lcwQueryCon");
        if (sql.trim().length() != 0 && sql != null) {
            List lReqInfo = service.doQueryAfterMod(sql);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "work1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("�����ۺϲ�ѯ�쳣: ԭ��session����");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // ============================�鵵���Ӳ�ѯ===================//

    /**
     * ���鵵�����Ϣ��ʾ
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
    public ActionForward waitArShow(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), "30402")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // ����Ǵ�ά��λ�ǲ������
            return forwardErrorPage(mapping, request, "partauditerror");
        }
        try {
            List lReqInfo = service.getAllForArch(request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "reforAr1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("���鵵�����Ϣ��ʾ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }

    }

    /**
     * ��ʾһ�����鵵�ĸ����ϸ��Ϣ
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
    public ActionForward showOneInfoForAr(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30402")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // ����Ǵ�ά��λ�ǲ������
            return forwardErrorPage(mapping, request, "partauditerror");
        }
        String reid = request.getParameter("reid"); // ���Ҫ���ʩ����Ϣ�����뵥id
        try {
            LineCutBean reqinfo = service.getOneForArch(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "ar2");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾһ�����鵵�ĸ����ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ִ����ӹ鵵��Ϣ
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
    public ActionForward addAr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30402")) {
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
            bean.setWorkuserid(userinfo.getUserID());

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
                bean.setArchives(fileIdListStr);
            }
            // ======================
            if (!service.addArchInfo(bean)) {
                // return forwardErrorPage(mapping, request, "30402e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "30402e", backUrl);
            }
            log(request, "��ӹ���", "��ӹ鵵��Ϣ");
            // return forwardInfoPage(mapping, request, "30402");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "30402", backUrl);
        } catch (Exception e) {
            logger.error("ִ����ӹ鵵��Ϣ�쳣:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * ��ʾ���и����Ϣ
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
    public ActionForward showAllAr(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30401")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List lReqInfo = null;
        try {
            lReqInfo = service.getCutInfo(request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "ar1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾ���и����Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾһ����ӵ���ϸ��Ϣ
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
    public ActionForward showOneWorkArInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        /*
         * if( !CheckPower.checkPower( request.getSession(), "30402" ) ){ return
         * mapping.findForward( "powererror" ); }
         */
        String reid = request.getParameter("reid"); // ���Ҫ��ʾ�����뵥id
        try {
            // ������뵥������Ϣ
            LineCutBean reqinfo = service.getOneCutAllInfo(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "ar10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾһ����ӵ���ϸ��Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ����ۺϲ�ѯ��ʾ
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
    public ActionForward queryShowForAr(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30403")) {
            return mapping.findForward("powererror");
        }
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

        try {
            String deptid = userinfo.getDeptID();

            List levelList = new LineCutReService().getLineLevle();
            request.setAttribute("linelevelList", levelList);

            // ��ø�������б�
            List lnames = service.getAllNamesForWork(deptid);
            request.setAttribute("lnames", lnames);
            // ��ø��ԭ���б�
            List lreasons = service.getAllReasonsForWork(deptid);
            request.setAttribute("lreasons", lreasons);
            // ��ø�ӵص��б�
            List laddresss = service.getAllAddresssForWork(deptid);
            request.setAttribute("laddresss", laddresss);
            // �����Ӱ��ϵͳ�б�
            List lefsystem = service.getAllEfsystemsForWork(deptid);
            request.setAttribute("lefsystem", lefsystem);

            // ����漰���¶�
            /*
             * List lsublines = service.getAllSublineidsForWork( deptid );
             * request.setAttribute( "lsublines", lsublines );
             */

            // ��ø�ӵ�λ�б�
            List lconname = service.getAllConName(deptid);
            request.setAttribute("lconname", lconname);

            request.setAttribute("depttype", userinfo.getDeptype());
            request.getSession().setAttribute("type", "au3");
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("ʩ���ۺϲ�ѯ��ʾ�쳣:" + e.getMessage());
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
    public ActionForward doQueryForAr(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30403")) {
            return mapping.findForward("powererror");
        }

        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        LineCutBean bean = (LineCutBean) form;
        String deptid = userinfo.getDeptID();
        bean.setDeptid(deptid);
        List lReqInfo = null;
        try {
            // lReqInfo = service.getCutInfoForSearch( bean, userinfo
            // ,request.getSession());
            lReqInfo = service.getConditionsCutInfoForSearch(bean, userinfo, request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "ar1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("ִ���ۺϲ�ѯ�쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward doQueryForArAfter(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("lcwArQueryCob");

        if (sql.trim().length() != 0 && sql != null) {
            List lReqInfo = service.queryAfterBack(sql);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "ar1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("ִ���ۺϲ�ѯ�쳣:session����");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ��ʾ���д���ʩ���׶εĸ����Ϣ
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
    public ActionForward showAllCutForWorking(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // ��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List lReqInfo = null;
        try {
            lReqInfo = service.getCutInfoForWorking(request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "working1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("��ʾ���и����Ϣ����:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * ���һ�����ѯ��������Excel��
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
    public ActionForward exportLineCut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {

            logger.info(" ����dao");
            List list = (List) request.getSession().getAttribute("reqinfo");
            logger.info("�õ�list");
            if (service.ExportLineCut(list, response)) {
                logger.info("���excel�ɹ�");
            }
            return null;
        } catch (Exception e) {
            logger.error("�������һ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // ************2006-07-14 pzj ��������һ����
    /**
     * ��������һ����
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
    public ActionForward exportReLineCut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" ����dao");
            List list = (List) request.getSession().getAttribute("reqinfo");
            logger.info("�õ�list");
            if (service.ExportReLineCut(list, response)) {
                logger.info("���excel�ɹ�");
            }
            return null;
        } catch (Exception e) {
            logger.error("������������һ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * �����Ѿ�ʩ�����һ����
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
    public ActionForward exportLineCutWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" ����dao");
            List list = (List) request.getSession().getAttribute("reqinfo");
            logger.info("�õ�list");
            if (service.ExportLineCutWork(list, response)) {
                logger.info("���excel�ɹ�");
            }
            return null;
        } catch (Exception e) {
            logger.error("�����Ѿ�ʩ�����һ��������쳣:" + e.getMessage());
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
    public ActionForward exportLineCutRe(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" ����dao");
            List list = (List) request.getSession().getAttribute("reqinfo");
            logger.info("�õ�list");
            if (service.ExportLineCutRe(list, response)) {
                logger.info("���excel�ɹ�");
            }
            return null;
        } catch (Exception e) {
            logger.error("�����������һ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
