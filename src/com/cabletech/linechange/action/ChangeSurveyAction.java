package com.cabletech.linechange.action;

import com.cabletech.commons.base.BaseDispatchAction;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForward;
import java.lang.reflect.InvocationTargetException;
import com.cabletech.power.CheckPower;
import com.cabletech.linechange.services.ChangeSurveyBOImpl;
import com.cabletech.linechange.domainobjects.ChangeSurvey;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.LineBO;

import java.util.List;
import com.cabletech.linechange.bean.ChangeSurveyBean;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import com.cabletech.linechange.services.ChangeApplyBOImpl;
import java.util.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.cabletech.uploadfile.UploadFile;
import org.apache.struts.upload.FormFile;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadUtil;
import java.util.StringTokenizer;
import com.cabletech.linechange.bean.ChangeInfoBean;
import com.cabletech.linechange.dao.ExportDao;

/**
 * 
 * ����Ǩ�ģ����ɣ����� �����룬��ӡ��޸ġ�ɾ�����鿴��Ϣ����ѯ�� 1������׶� ϵͳ��������д���� ������ʶ������ A 2-1������׶�
 * ϵͳ��������д������ ������ʶ��ͨ�������� B1 2-2������׶� ϵͳ��������д������ ������ʶ��ͨ���ƶ��� B2 3��׼���׶�
 * ϵͳ��������дת��/���������Ϣ ������ʶ��ʩ��׼�� C 4��ί�н׶� ϵͳ��������дί���� ������ʶ����ʼʩ�� D 5��ʩ���׶� ϵͳ��������дʩ����Ϣ
 * ������ʶ��ʩ����� E 6�����ս׶� ϵͳ��������д������Ϣ ������ʶ��������� F 7���鵵�׶� ϵͳ��������д�鵵��Ϣ ������ʶ���Ѿ��鵵 G
 * 
 */
public class ChangeSurveyAction extends BaseDispatchAction {
    private static Logger logger = Logger.getLogger("ChangeSurveyAction");

    public ChangeSurveyAction() {
    }

    /**
     * ��ӿ�����Ϣ
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
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public ActionForward addSurvey(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** Ȩ��У�� */
        if (!CheckPower.checkPower(request.getSession(), "50201")) {
            return mapping.findForward("powererror");
        }
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeSurveyBOImpl bo = new ChangeSurveyBOImpl();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        ChangeSurveyBean bean = (ChangeSurveyBean) form;
        ChangeSurvey data = new ChangeSurvey();
        ChangeInfo changeinfo = new ChangeInfo();
        try {
            String datumid = uploadFile(form, new ArrayList());
            changeinfo = cbo.getChangeInfo(bean.getChangeid());
            bean.setApprovedept((String) request.getSession().getAttribute("LOGIN_USER_DEPT_NAME"));
            BeanUtil.objectCopy(bean, data);
            data.setId(super.getDbService().getSeq("changesurvey", 10));
            data.setSurveydatum(datumid);
            data.setApprovedate(new Date());
            bo.saveChangeSurvey(data, changeinfo, user.getDeptype());
            // return forwardInfoPage(mapping, request, "50202s");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "50202s", backUrl);
        } catch (Exception e) {
            // return forwardInfoPage(mapping, request, "50202e");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "50202e", backUrl);
        }
    }

    public ActionForward updateSurvey(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** Ȩ��У�� */
        if (!CheckPower.checkPower(request.getSession(), "50201")) {
            return mapping.findForward("powererror");
        }
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeSurveyBOImpl bo = new ChangeSurveyBOImpl();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        ChangeSurveyBean bean = (ChangeSurveyBean) form;
        ChangeSurvey data = new ChangeSurvey();
        ChangeInfo changeinfo = new ChangeInfo();
        try {
            // �ļ��ϴ�
            ArrayList fileIdList = new ArrayList();
            String[] delfileid = request.getParameterValues("delfileid"); // Ҫɾ�����ļ�id������
            StringTokenizer st = new StringTokenizer(bean.getSurveydatum(), ",");
            while (st.hasMoreTokens()) {
                fileIdList.add(st.nextToken());
            }
            if (delfileid != null) {
                for (int i = 0; i < delfileid.length; i++) {
                    fileIdList.remove(delfileid[i]);
                }
            }

            bean.setApprovedept((String) request.getSession().getAttribute("LOGIN_USER_DEPT_NAME"));
            String datumid = uploadFile(form, fileIdList);

            changeinfo = cbo.getChangeInfo(bean.getChangeid());
            BeanUtil.objectCopy(bean, data);
            data.setId(super.getDbService().getSeq("changesurvey", 10));
            data.setSurveydatum(datumid);
            data.setApprovedate(new Date());
            bo.saveChangeSurvey(data, changeinfo, user.getDeptype());
            // return forwardInfoPage(mapping, request, "50202s");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "50202s", backUrl);
        } catch (Exception e) {
            // return forwardInfoPage(mapping, request, "50202e");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "50202e", backUrl);
        }

    }

    public ActionForward getSurveyInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        ChangeSurvey survey = new ChangeSurvey();
        ChangeSurveyBean bean = new ChangeSurveyBean();
        ChangeSurveyBOImpl bo = new ChangeSurveyBOImpl();
        String id = request.getParameter("id");
        survey = bo.getChangeSurvey(id);
        List surveyList = bo.getChangeSurveyList(survey.getChangeid());
        if (surveyList != null && surveyList.size() > 0) {
            List list = new ArrayList();
            list.add(surveyList.get(0));
            request.setAttribute("survey_list", list);
        }

        // ��ȡ������Ϣ
        ChangeInfoBean changebean = new ChangeInfoBean();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        ChangeInfo changedata = cbo.getChangeInfo(survey.getChangeid());
        BeanUtil.objectCopy(changedata, changebean);
        request.setAttribute("changeinfo", changebean);

        try {
            LineBO lbo = new LineBO();
            String lineClassName = lbo.getLineClassName((String) changedata.getLineclass());
            BeanUtil.objectCopy(survey, bean);
            request.setAttribute("changesurvey", bean);
            request.setAttribute("line_class_name", lineClassName);
            return mapping.findForward("lookForm");

        } catch (Exception ex) {
            logger.error("load add form Exception:" + ex.getMessage());
            return mapping.findForward("lookForm");
        }
    }

    public ActionForward getSurveyInfoList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        ChangeSurveyBOImpl bo = new ChangeSurveyBOImpl();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeSurveyBean bean = new ChangeSurveyBean();
        bean = (ChangeSurveyBean) form;
        List listsurveyinfo = bo.getChangeSurveyList(userinfo, bean);
        if (listsurveyinfo.isEmpty()) {
            listsurveyinfo = null;
        }
        List list = new ArrayList();
        List tempList = new ArrayList();
        for (int i = 0; listsurveyinfo != null && i < listsurveyinfo.size(); i++) {
            DynaBean oneBean = (DynaBean) listsurveyinfo.get(i);
            String changeId = (String) oneBean.get("change_id");
            if (tempList.contains(changeId)) {
                continue;
            } else {
                tempList.add(changeId);
                list.add(oneBean);
            }
        }
        request.getSession().setAttribute("queryresult", list);
        request.getSession().setAttribute("bean", bean);
        super.setPageReset(request);
        return mapping.findForward("loadSurveyList");
    }

    public ActionForward loadEditForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        ChangeSurvey survey = new ChangeSurvey();
        ChangeSurveyBean bean = new ChangeSurveyBean();
        ChangeSurveyBOImpl bo = new ChangeSurveyBOImpl();
        String id = request.getParameter("id");
        survey = bo.getChangeSurvey(id);
        List surveyList = bo.getChangeSurveyList(survey.getChangeid());
        if (surveyList != null && surveyList.size() > 0) {
            request.setAttribute("survey_list", surveyList);
        }

        // ��ȡ������Ϣ
        ChangeInfoBean changebean = new ChangeInfoBean();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        ChangeInfo changedata = cbo.getChangeInfo(survey.getChangeid());
        BeanUtil.objectCopy(changedata, changebean);
        request.setAttribute("changeinfo", changebean);

        try {
            LineBO lbo = new LineBO();
            String lineClassName = lbo.getLineClassName((String) changedata.getLineclass());
            BeanUtil.objectCopy(survey, bean);
            UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
            request.setAttribute("user_name", userinfo.getUserName());
            request.setAttribute("dept_name", userinfo.getDeptName());
            request.setAttribute("dept_type", userinfo.getDeptype());
            request.setAttribute("changesurvey", bean);
            request.setAttribute("line_class_name", lineClassName);
            return mapping.findForward("editForm");

        } catch (Exception ex) {
            logger.error("load add form Exception:" + ex.getMessage());
            return mapping.findForward("editForm");
        }
    }

    public ActionForward loadAddForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        if (!CheckPower.checkPower(request.getSession(), "50201")) {
            return mapping.findForward("powererror");
        }
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeSurvey survey = new ChangeSurvey();
        ChangeSurveyBean bean = new ChangeSurveyBean();
        ChangeInfo changeinfo = new ChangeInfo();
        ChangeInfoBean changebean = new ChangeInfoBean();
        ChangeSurveyBOImpl bo = new ChangeSurveyBOImpl();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        String changeid = request.getParameter("id");
        survey = bo.getChangeSurveyForChangeID(request.getParameter("id"));
        changeinfo = cbo.getChangeInfo(changeid);
        BeanUtil.objectCopy(changeinfo, changebean);
        try {
            request.setAttribute("changeid", changeid);
            request.setAttribute("user_name", userinfo.getUserName());
            request.setAttribute("dept_name", userinfo.getDeptName());
            request.setAttribute("dept_type", userinfo.getDeptype());
            if ("B1".equals(changeinfo.getStep())) {
                BeanUtil.objectCopy(survey, bean);
                request.setAttribute("change_survey", bean);
                request.setAttribute("approver_supervise", bean.getApprover());
            }
            if (survey != null && "δͨ��".equals(survey.getApproveresult())) {
                BeanUtil.objectCopy(survey, bean);
                request.setAttribute("changesurvey", bean);
                request.setAttribute("changeinfo", changebean);
                // return mapping.findForward("editForm");
                return mapping.findForward("loadAddForm");
            } else {
                bean.setApprover(userinfo.getUserName());
                request.setAttribute("changeinfo", changebean);
                request.setAttribute("changesurvey", bean);
                return mapping.findForward("loadAddForm");
            }
        } catch (Exception ex) {
            logger.error("load add form Exception:" + ex.getMessage());
            return mapping.findForward("loadAddForm");
        }
    }

    public ActionForward loadQueryForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        if (!CheckPower.checkPower(request.getSession(), "50203")) {
            return mapping.findForward("powererror");
        }
        return mapping.findForward("loadQueryForm");
    }

    /**
     * �ļ��ϴ�
     * 
     * @param form
     *            ActionForm
     * @return String
     */
    public String uploadFile(ActionForm form, ArrayList fileIdList) {
        ChangeSurveyBean formbean = (ChangeSurveyBean) form;
        // ��ʼ�����ϴ��ļ�================================
        // List attachments = formbean.getAttachments();
        // String fileId;
        // for (int i = 0; i < attachments.size(); i++) {
        // UploadFile uploadFile = (UploadFile) attachments.get(i);
        // FormFile file = uploadFile.getFile();
        // if (file == null) {
        // logger.info("file is null");
        // } else {
        // // ���ļ��洢������������·��д�����ݿ⣬���ؼ�¼ID
        // fileId = SaveUploadFile.saveFile(file);
        // if (fileId != null) {
        // fileIdList.add(fileId);
        // }
        // }
        // }
        FormFile file = null;
        String fileId;
        // ȡ���ϴ����ļ�
        Hashtable files = formbean.getMultipartRequestHandler().getFileElements();
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
        // String fileIdListStr =processFileUpload(request);
        String datumid = "";
        if (fileIdListStr != null) {
            // logger.info( "FileIdListStr=" + fileIdListStr );
            datumid = fileIdListStr;
        }
        return datumid;
    }

    public ActionForward exportSurveyResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            ChangeSurveyBean bean = null;
            if (request.getSession().getAttribute("bean") != null) {
                bean = (ChangeSurveyBean) request.getSession().getAttribute("bean");
                logger.info("��ò�ѯ����bean������");
                logger.info("���鸺���ˣ�" + bean.getPrincipal());
                logger.info("����Ԥ�㣺" + bean.getBudget() + "~~" + bean.getMaxbudget());
                logger.info("�󶨽����" + bean.getApproveresult());
                logger.info("��ʼʱ�䣺" + bean.getBegintime());
                logger.info("����ʱ�䣺" + bean.getEndtime());

            }
            List list = (List) request.getSession().getAttribute("queryresult");
            logger.info("�õ�list");
            logger.info("���excel�ɹ�");
            ExportDao ed = new ExportDao();
            ed.exportSurveyResult(list, bean, response);
            logger.info("end.....");
            return null;
        } catch (Exception e) {
            logger.error("������Ϣ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward showSurveyHistory(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String changeId = request.getParameter("change_id");
        String showType = request.getParameter("show_type");
        ChangeSurveyBOImpl bo = new ChangeSurveyBOImpl();
        List list = bo.getChangeSurveyHistoryList(changeId, showType);
        request.setAttribute("survey_history_list", list);
        return mapping.findForward("survey_history_list");
    }
}
