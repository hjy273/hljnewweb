package com.cabletech.linechange.action;

import com.cabletech.commons.base.BaseDispatchAction;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForward;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;
import com.cabletech.power.CheckPower;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import com.cabletech.linechange.bean.ChangeInfoBean;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.ArrayList;
import org.apache.struts.upload.FormFile;
import java.util.List;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadUtil;
import com.cabletech.linechange.services.ConsignBOImpl;
import com.cabletech.linechange.services.ConsignBO;
import java.util.Date;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.LineBO;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linechange.dao.ExportDao;
import com.cabletech.linechange.bean.ChangeSurveyBean;
import com.cabletech.linechange.domainobjects.ChangeSurvey;
import com.cabletech.linechange.services.ChangeSurveyBOImpl;

public class ConsignAction extends BaseDispatchAction {
    private static Logger logger = Logger.getLogger("BuildSetoutAction");

    public ActionForward updateConsign(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        String type = request.getParameter("type");
        // System.out.println("type="+type);
        String code = "";
        if (!"edit".equals(type)) {
            /** Ȩ��У�� */
            if (!CheckPower.checkPower(request.getSession(), "50401")) {
                return mapping.findForward("powererror");
            }
            code = "50402s";
        } else {
            code = "50404s";
        }
        ChangeInfoBean bean = (ChangeInfoBean) form;
        ChangeInfo data = new ChangeInfo();
        ConsignBO bo = new ConsignBOImpl();
        ArrayList fileIdList = new ArrayList();
        String[] delfileid = request.getParameterValues("delfileid"); // Ҫɾ�����ļ�id������
        StringTokenizer st = new StringTokenizer(bean.getEntrustdatum(), ",");
        while (st.hasMoreTokens()) {
            fileIdList.add(st.nextToken());
        }
        if (delfileid != null) {
            for (int i = 0; i < delfileid.length; i++) {
                fileIdList.remove(delfileid[i]);
            }
        }
        String datumid = uploadFile(form, fileIdList);
        data = bo.getChangeInfo(bean.getId());

        data.setEntrustunit(bean.getEntrustunit());
        data.setEntrustaddr(bean.getEntrustaddr());
        data.setEntrustgrade(bean.getEntrustgrade());
        data.setEntrustnote(bean.getEntrustnote());
        data.setEntrustphone(bean.getEntrustphone());
        data.setEntrustperson(bean.getEntrustperson());
        data.setEntrustremark(bean.getEntrustremark());
        data.setEntrustdatum(datumid);
        data.setCost(bean.getCost());
        data.setConstartdate(DateUtil.StringToDate(bean.getConstartdate()));
        data.setEntrusttime(new Date());
        data.setStep("D");
        bo.addOrUpdateConsign(data);
        // return forwardInfoPage(mapping, request, code);
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, code, backUrl);
    }

    public ActionForward listChangeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** Ȩ��У�� */
        if (!CheckPower.checkPower(request.getSession(), "50401")) {
            return mapping.findForward("powererror");
        }
        ConsignBO bo = new ConsignBOImpl();
        // UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
        // "LOGIN_USER" );
        // ChangeInfoBean bean = ( ChangeInfoBean )form;
        List listchangeinfo = bo.getChangeInfo();
        if (listchangeinfo.isEmpty()) {
            listchangeinfo = null;
        }
        request.getSession().setAttribute("queryresult", listchangeinfo);
        super.setPageReset(request);
        return mapping.findForward("listChangeInfo");
    }

    public ActionForward listConsign(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** Ȩ��У�� */
        ConsignBO bo = new ConsignBOImpl();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeInfoBean bean = (ChangeInfoBean) form;
        List listchangeinfo = bo.getConsignInfo(userinfo, bean);
        if (listchangeinfo.isEmpty()) {
            listchangeinfo = null;
        }
        request.getSession().setAttribute("queryresult", listchangeinfo);
        request.getSession().setAttribute("bean", bean);
        super.setPageReset(request);
        return mapping.findForward("loadConsignList");
    }

    public ActionForward loadAddForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** Ȩ��У�� */
        if (!CheckPower.checkPower(request.getSession(), "50401")) {
            return mapping.findForward("powererror");
        }
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        ConsignBO bo = new ConsignBOImpl();
        ChangeInfo changeinfo = bo.getChangeInfo(id);
        ChangeInfoBean bean = new ChangeInfoBean();
        BeanUtil.objectCopy(changeinfo, bean);
        request.setAttribute("id", id);
        bean.setType(type);
        // ������Ϣ
        ChangeSurveyBOImpl sbo = new ChangeSurveyBOImpl();
        ChangeSurvey survey = sbo.getChangeSurveyForChangeID(id);
        ChangeSurveyBean surveybean = new ChangeSurveyBean();
        BeanUtil.objectCopy(survey, surveybean);
        request.setAttribute("survey", surveybean);

        request.setAttribute("changeinfo", bean);
        request.setAttribute("cost", bean.getCost());
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) changeinfo.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        return mapping.findForward("loadAddForm");

    }

    public ActionForward loadLookForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** Ȩ��У�� */
        // if (!CheckPower.checkPower(request.getSession(), "50401")) {
        // return mapping.findForward("powererror");
        // }
        String id = request.getParameter("id");
        ConsignBO bo = new ConsignBOImpl();
        ChangeInfo changeinfo = bo.getChangeInfo(id);
        request.setAttribute("id", id);
        request.setAttribute("changeinfo", changeinfo);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) changeinfo.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        return mapping.findForward("lookForm");

    }

    public ActionForward loadQueryForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        if (!CheckPower.checkPower(request.getSession(), "50303")) {
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
        ChangeInfoBean formbean = (ChangeInfoBean) form;
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

    public ActionForward exportConsignResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            ChangeInfoBean bean = null;
            if (request.getSession().getAttribute("bean") != null) {
                bean = (ChangeInfoBean) request.getSession().getAttribute("bean");
                logger.info("��ò�ѯ����bean������");
                logger.info("��ʼʱ�䣺" + bean.getBegintime());
                logger.info("����ʱ�䣺" + bean.getEndtime());

            }
            List list = (List) request.getSession().getAttribute("queryresult");
            logger.info("�õ�list");
            logger.info("���excel�ɹ�");
            ExportDao ed = new ExportDao();
            ed.exportConsignResult(list, bean, response);
            logger.info("end.....");
            return null;
        } catch (Exception e) {
            logger.error("������Ϣ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
