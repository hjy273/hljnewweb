package com.cabletech.linechange.action;

import com.cabletech.commons.base.BaseDispatchAction;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import com.cabletech.linechange.bean.ChangeCheckBean;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import com.cabletech.uploadfile.UploadFile;
import org.apache.struts.upload.FormFile;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadUtil;
import java.util.ArrayList;
import com.cabletech.power.CheckPower;
import com.cabletech.linechange.domainobjects.ChangeCheck;
import com.cabletech.linechange.services.CheckBOImpl;
import com.cabletech.linechange.services.PageonholeBOImpl;
import com.cabletech.commons.beans.BeanUtil;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForward;
import java.lang.reflect.InvocationTargetException;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.LineBO;

import java.util.Date;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import java.util.StringTokenizer;
import com.cabletech.linechange.services.ChangeApplyBOImpl;
import com.cabletech.linechange.dao.ExportDao;
import com.cabletech.linechange.bean.ChangeInfoBean;
import com.cabletech.linechange.services.BuildBOImpl;
import com.cabletech.linechange.domainobjects.ChangeBuild;
import com.cabletech.linechange.bean.ChangeBuildBean;

/**
 * 
 * ����Ǩ�ģ����ɣ����� �����룬��ӡ��޸ġ�ɾ�����鿴��Ϣ����ѯ�� 1������׶� ϵͳ��������д���� ������ʶ������ A 2-1������׶�
 * ϵͳ��������д������ ������ʶ��ͨ�������� B1 2-2������׶� ϵͳ��������д������ ������ʶ��ͨ���ƶ��� B2 3��׼���׶�
 * ϵͳ��������дת��/���������Ϣ ������ʶ��ʩ��׼�� C 4��ί�н׶� ϵͳ��������дί���� ������ʶ����ʼʩ�� D 5��ʩ���׶� ϵͳ��������дʩ����Ϣ
 * ������ʶ��ʩ����� E 6�����ս׶� ϵͳ��������д������Ϣ ������ʶ��������� F 7���鵵�׶� ϵͳ��������д�鵵��Ϣ ������ʶ���Ѿ��鵵 G
 * 
 */
public class CheckAction extends BaseDispatchAction {
    private static Logger logger = Logger.getLogger("CheckAction");

    public ActionForward addCheck(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** Ȩ��У�� */
        if (!CheckPower.checkPower(request.getSession(), "50601")) {
            return mapping.findForward("powererror");
        }
        CheckBOImpl bo = new CheckBOImpl();
        ChangeCheck data = new ChangeCheck();
        ChangeCheckBean bean = new ChangeCheckBean();
        ChangeInfo changeinfo = new ChangeInfo();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        bean = (ChangeCheckBean) form;
        // �ļ��ϴ�
        ArrayList fileIdList = new ArrayList();
        String[] delfileid = request.getParameterValues("delfileid"); // Ҫɾ�����ļ�id������
        StringTokenizer st = new StringTokenizer(bean.getCheckdatum(), ",");
        while (st.hasMoreTokens()) {
            fileIdList.add(st.nextToken());
        }
        if (delfileid != null) {
            for (int i = 0; i < delfileid.length; i++) {
                fileIdList.remove(delfileid[i]);
            }
        }
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String datumid = uploadFile(form, fileIdList);
        BeanUtil.objectCopy(bean, data);
        data.setCheckdatum(datumid);
        data.setId(super.getDbService().getSeq("changecheck", 10));
        data.setFillintime(new Date());
        data.setFillinperson(user.getUserName());
        changeinfo = cbo.getChangeInfo(bean.getChangeid());
        bo.addCheckInfo(data, changeinfo);
        if (data.getCheckresult().equals("ͨ������")) {
            PageonholeBOImpl pbo = new PageonholeBOImpl();
            changeinfo.setPageonholedate(new Date());
            changeinfo.setPageonholeperson(userinfo.getUserName());
            changeinfo.setSquare(bean.getSquare());
            changeinfo.setStep("G");
            pbo.updatePageonhole(changeinfo);
        }
        // return forwardInfoPage(mapping, request, "50602s");
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, "50602s", backUrl);

    }

    public ActionForward updateCheck(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** Ȩ��У�� */
        if (!CheckPower.checkPower(request.getSession(), "50601")) {
            return mapping.findForward("powererror");
        }

        CheckBOImpl bo = new CheckBOImpl();
        ChangeCheck data = new ChangeCheck();
        ChangeInfo changeinfo = new ChangeInfo();
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeCheckBean bean = (ChangeCheckBean) form;
        // �ļ��ϴ�
        ArrayList fileIdList = new ArrayList();
        String[] delfileid = request.getParameterValues("delfileid"); // Ҫɾ�����ļ�id������
        StringTokenizer st = new StringTokenizer(bean.getCheckdatum(), ",");
        while (st.hasMoreTokens()) {
            fileIdList.add(st.nextToken());
        }
        if (delfileid != null) {
            for (int i = 0; i < delfileid.length; i++) {
                fileIdList.remove(delfileid[i]);
            }
        }
        String datumid = uploadFile(form, fileIdList);
        changeinfo = bo.getChangeInfo(bean.getChangeid());
        BeanUtil.objectCopy(bean, data);
        data.setId(super.getDbService().getSeq("changecheck", 10));
        data.setCheckdatum(datumid);
        data.setFillintime(new Date());
        data.setFillinperson(user.getUserName());
        bo.updateCheckInfo(data, changeinfo);
        // return forwardInfoPage(mapping, request, "50604s");
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, "50604s", backUrl);
    }

    public ActionForward getCheckInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        CheckBOImpl bo = new CheckBOImpl();
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeCheckBean bean = new ChangeCheckBean();
        bean = (ChangeCheckBean) form;
        List list = bo.getCheckInfoList(user, bean);
        if (list.isEmpty()) {
            list = null;
        }
        request.getSession().setAttribute("queryresult", list);
        request.getSession().setAttribute("bean", bean);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) bean.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        super.setPageReset(request);
        return mapping.findForward("loadCheckList");

    }

    public ActionForward getChangeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        CheckBOImpl bo = new CheckBOImpl();
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeCheckBean bean = null;
        List list = bo.getChangeInfo(user, bean);
        if (list.isEmpty()) {
            list = null;
        }
        request.getSession().setAttribute("queryresult", list);
        request.getSession().setAttribute("bean", bean);
        super.setPageReset(request);
        return mapping.findForward("listChange");

    }

    public ActionForward loadAddForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String changeid = request.getParameter("changeid");
        CheckBOImpl bo = new CheckBOImpl();
        ChangeCheck changecheck = bo.getChCheckInfoByChangeId(changeid);
        ChangeCheckBean bean = new ChangeCheckBean();
        //
        ChangeInfo changeinfo = new ChangeInfo();
        // ChangeInfoBean changebean = new ChangeInfoBean();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        changeinfo = cbo.getChangeInfo(changeid);
        // BeanUtil.objectCopy( changeinfo, changebean );
        request.setAttribute("changeinfo", changeinfo);

        BuildBOImpl bbo = new BuildBOImpl();
        ChangeBuild build = bbo.getBuildInfoByChangeId(changeid);
        ChangeBuildBean buildbean = new ChangeBuildBean();
        BeanUtil.objectCopy(build, buildbean);
        request.setAttribute("build", buildbean);

        try {
            request.setAttribute("changeid", changeid);
            request.setAttribute("user_name", userinfo.getUserName());
            LineBO lbo = new LineBO();
            String lineClassName = lbo.getLineClassName((String) changeinfo.getLineclass());
            request.setAttribute("line_class_name", lineClassName);
            // if (changecheck != null &&
            // "δͨ��".equals(changecheck.getCheckresult())) {
            // BeanUtil.objectCopy(changecheck, bean);
            // request.setAttribute("changecheck", bean);
            // return mapping.findForward("editForm");
            // } else {
            return mapping.findForward("loadAddForm");
            // }
        } catch (Exception ex) {
            logger.error("load add form Exception:" + ex.getMessage());
            return mapping.findForward("loadAddForm");
        }
    }

    public ActionForward loadEditForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        if (!CheckPower.checkPower(request.getSession(), "50604")) {
            return mapping.findForward("powererror");
        }

        String id = request.getParameter("id");
        CheckBOImpl bo = new CheckBOImpl();
        ChangeCheck data = bo.getCheckInfo(id);
        ChangeCheckBean bean = new ChangeCheckBean();
        BeanUtil.objectCopy(data, bean);
        //
        ChangeInfo changeinfo = new ChangeInfo();
        ChangeInfoBean changebean = new ChangeInfoBean();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        changeinfo = cbo.getChangeInfo(bean.getChangeid());
        BeanUtil.objectCopy(changeinfo, changebean);
        request.setAttribute("changeinfo", changebean);
        // /
        request.setAttribute("changeCheck", bean);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) changeinfo.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        return mapping.findForward("loadEditForm");
    }

    public ActionForward loadLookForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {

        String id = request.getParameter("id");
        CheckBOImpl bo = new CheckBOImpl();
        ChangeCheck data = bo.getCheckInfo(id);
        ChangeCheckBean bean = new ChangeCheckBean();
        BeanUtil.objectCopy(data, bean);
        //
        ChangeInfo changeinfo = new ChangeInfo();
        ChangeInfoBean changebean = new ChangeInfoBean();
        ChangeApplyBOImpl cbo = new ChangeApplyBOImpl();
        changeinfo = cbo.getChangeInfo(bean.getChangeid());
        BeanUtil.objectCopy(changeinfo, changebean);
        request.setAttribute("changeinfo", changebean);
        // /
        request.setAttribute("changecheck", bean);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) changebean.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        return mapping.findForward("loadLookForm");
    }

    public ActionForward loadQueryForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        if (!CheckPower.checkPower(request.getSession(), "50603")) {
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
        ChangeCheckBean formbean = (ChangeCheckBean) form;
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

    public ActionForward exportCheckResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            ChangeCheckBean bean = null;
            if (request.getSession().getAttribute("bean") != null) {
                bean = (ChangeCheckBean) request.getSession().getAttribute("bean");
                logger.info("��ò�ѯ����bean������");
                logger.info("��ʼʱ�䣺" + bean.getBegintime());
                logger.info("����ʱ�䣺" + bean.getEndtime());

            }
            List list = (List) request.getSession().getAttribute("queryresult");
            logger.info("�õ�list");
            logger.info("���excel�ɹ�");
            ExportDao ed = new ExportDao();
            ed.exportCheckResult(list, bean, response);
            logger.info("end.....");
            return null;
        } catch (Exception e) {
            logger.error("������Ϣ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward showCheckHistory(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String changeId = request.getParameter("change_id");
        String showType = request.getParameter("show_type");
        CheckBOImpl bo = new CheckBOImpl();
        List list = bo.getChangeCheckHistoryList(changeId, showType);
        request.setAttribute("check_history_list", list);
        return mapping.findForward("check_history_list");
    }
}
