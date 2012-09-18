package com.cabletech.linechange.action;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletResponse;
import com.cabletech.power.CheckPower;
import org.apache.struts.action.ActionForm;
import java.lang.reflect.InvocationTargetException;
import com.cabletech.linechange.bean.ChangeInfoBean;
import com.cabletech.linechange.services.ChangeApplyBOImpl;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.baseinfo.services.LineBO;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import com.cabletech.uploadfile.UploadUtil;
import java.util.ArrayList;
import org.apache.struts.upload.FormFile;
import java.util.List;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadFile;
import java.util.StringTokenizer;
import com.cabletech.linechange.dao.ExportDao;

/**
 * 
 * ����Ǩ�ģ����ɣ����� �����룬��ӡ��޸ġ�ɾ�����鿴��Ϣ����ѯ�� 1������׶� ϵͳ��������д���� ������ʶ������ A 2-1������׶�
 * ϵͳ��������д������ ������ʶ��ͨ�������� B1 2-2������׶� ϵͳ��������д������ ������ʶ��ͨ���ƶ��� B2 3��׼���׶�
 * ϵͳ��������дת��/���������Ϣ ������ʶ��ʩ��׼�� C 4��ί�н׶� ϵͳ��������дί���� ������ʶ����ʼʩ�� D 5��ʩ���׶� ϵͳ��������дʩ����Ϣ
 * ������ʶ��ʩ����� E 6�����ս׶� ϵͳ��������д������Ϣ ������ʶ��������� F 7���鵵�׶� ϵͳ��������д�鵵��Ϣ ������ʶ���Ѿ��鵵 G
 * 
 */
public class ChangeApplyAction extends BaseDispatchAction {
    private static Logger logger = Logger.getLogger("ChangeApplyAction");

    public ActionForward addApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** Ȩ��У�� */
        if (!CheckPower.checkPower(request.getSession(), "50101")) {
            return mapping.findForward("powererror");
        }
        try {
            UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
            // UploadFileBO upload = new UploadFileBO();
            ChangeInfoBean bean = (ChangeInfoBean) form;
            ChangeInfo data = new ChangeInfo();
            ChangeApplyBOImpl bo = new ChangeApplyBOImpl();
            String datumid = uploadFile(form, new ArrayList());
            // bo.getChangeInfo(bean.getId());
            BeanUtil.objectCopy(bean, data);
            data.setId(super.getDbService().getSeq("changeinfo", 10));
            data.setApplyperson(userinfo.getUserID());
            data.setApplyunit(userinfo.getDeptID());
            data.setRegionid(userinfo.getRegionID());
            data.setApplydatumid(datumid);
            data.setApproveresult("����");
            data.setStep("A");
            data.setApplytime(new Date());
            bo.saveChangeInfo(data);
            return forwardInfoPage(mapping, request, "50102s");
        } catch (Exception e) {
            e.printStackTrace();
            return forwardInfoPage(mapping, request, "50102e");
        }
    }

    public ActionForward updateApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** Ȩ��У�� */
        if (!CheckPower.checkPower(request.getSession(), "50104")) {
            return mapping.findForward("powererror");
        }
        // UploadFileBO upload = new UploadFileBO();
        ChangeInfoBean bean = (ChangeInfoBean) form;
        ChangeInfo data = new ChangeInfo();
        ChangeApplyBOImpl bo = new ChangeApplyBOImpl();

        ArrayList fileIdList = new ArrayList();
        String[] delfileid = request.getParameterValues("delfileid"); // Ҫɾ�����ļ�id������
        StringTokenizer st = new StringTokenizer(bean.getApplydatumid(), ",");
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
        // request.getSession().getAttribute("changeinfo");
        data.setChangename(bean.getChangename());
        data.setChangepro(bean.getChangepro());
        data.setChangeaddr(bean.getChangeaddr());
        data.setLineclass(bean.getLineclass());
        data.setInvolvedSystem(bean.getInvolvedSystem());
        data.setChangelength(bean.getChangelength());
        data.setStartdate(DateUtil.StringToDate(bean.getStartdate()));
        data.setPlantime(bean.getPlantime());
        data.setApproveresult("����");
        data.setRemark(bean.getRemark());
        data.setApplydatumid(datumid);

        data.setEntrustaddr(bean.getEntrustaddr());
        data.setEntrustgrade(bean.getEntrustgrade());
        data.setEntrustperson(bean.getEntrustperson());
        data.setEntrustphone(bean.getEntrustphone());
        data.setEntrustunit(bean.getEntrustunit());
        data.setBuildunit(bean.getBuildunit());
        data.setStarttime(DateUtil.Str2UtilDate(bean.getStarttime(), "yyyy/MM/dd"));
        data.setBudget(bean.getBudget());
        data.setSuperviseUnitId(bean.getSuperviseUnitId());
        bo.updateChangeInfo(data, delfileid);

        // return forwardInfoPage(mapping, request, "50104s");
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, "50104s", backUrl);
    }

    public ActionForward getApplyInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ChangeApplyBOImpl bo = new ChangeApplyBOImpl();
        String changeid = request.getParameter("changeid");
        String type = request.getParameter("type");
        ChangeInfo changeinfo = bo.getChangeInfo(changeid);
        ChangeInfoBean bean = new ChangeInfoBean();
        BeanUtil.objectCopy(changeinfo, bean);
        request.setAttribute("changeinfo", bean);
        ContractorBO cbo = new ContractorBO();
        request.setAttribute("supervise_units", cbo.getSuperviseUnits());

        if ("edit".equals(type)) {
            request.getSession().setAttribute("changeinfo", changeinfo);
            return mapping.findForward("editForm");
        } else {
            // add by guixy 2008-12-6
            String flg = request.getParameter("flg");
            if (flg == null) {
                request.setAttribute("flg", "1");
            } else {
                request.setAttribute("flg", "2");
            }
            LineBO lbo = new LineBO();
            String lineClassName = lbo.getLineClassName((String) bean.getLineclass());
            request.setAttribute("line_class_name", lineClassName);
            if (bean.getSuperviseUnitId() != null && !bean.getSuperviseUnitId().equals("")) {
                String superviseUnitName = cbo.loadContractor(bean.getSuperviseUnitId())
                        .getContractorName();
                request.setAttribute("supervise_unit_name", superviseUnitName);
            }
            return mapping.findForward("lookForm");
        }
    }

    public ActionForward delApplyInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        ChangeApplyBOImpl bo = new ChangeApplyBOImpl();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String changeid = request.getParameter("changeid");
        boolean b = bo.removeChangeInfo(changeid);
        if (b) {
            // return forwardInfoPage(mapping, request, "50105s");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "50105s", backUrl);
        } else {
            // return forwardInfoPage(mapping, request, "50105e");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "50105e", backUrl);
        }
    }

    public ActionForward getApplyInfoList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        ChangeApplyBOImpl bo = new ChangeApplyBOImpl();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeInfoBean bean = (ChangeInfoBean) form;
        String op = request.getParameter("op");
        bean.setOp(op);
        List listchangeinfo = bo.getChangeInfo(userinfo, bean);

        if (listchangeinfo.isEmpty()) {
            listchangeinfo = null;
        }
        request.getSession().setAttribute("queryresult", listchangeinfo);
        request.getSession().setAttribute("bean", bean);
        super.setPageReset(request);
        System.out.println("xf************************************************");
        if (op == null) {
            return mapping.findForward("loadApplyList");
        } else {
            return mapping.findForward("listApplyForSurvey");
        }
    }

    public ActionForward loadAddForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ContractorBO bo = new ContractorBO();
        if (!CheckPower.checkPower(request.getSession(), "50101")) {
            return mapping.findForward("powererror");
        }
        request.setAttribute("supervise_units", bo.getSuperviseUnits());
        return mapping.findForward("loadAddForm");
    }

    public ActionForward loadQueryForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        if (!CheckPower.checkPower(request.getSession(), "50103")) {
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
        // ArrayList fileIdList = new ArrayList();
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

    public ActionForward exportApplyResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            ChangeInfoBean bean = null;
            if (request.getSession().getAttribute("bean") != null) {
                bean = (ChangeInfoBean) request.getSession().getAttribute("bean");
                logger.info("��ò�ѯ����bean������");

                logger.info("�������ƣ�" + bean.getChangename());
                logger.info("�������ʣ�" + bean.getChangepro());
                logger.info("�󶨽����" + bean.getApproveresult());
                logger.info("�������ʣ�" + bean.getLineclass());
                logger.info("��ʼʱ�䣺" + bean.getBegintime());
                logger.info("����ʱ�䣺" + bean.getEndtime());

            }
            List list = (List) request.getSession().getAttribute("queryresult");
            logger.info("�õ�list");
            logger.info("���excel�ɹ�");
            ExportDao ed = new ExportDao();
            ed.exportApplyResult(list, bean, response);
            logger.info("end.....");
            return null;
        } catch (Exception e) {
            logger.error("������Ϣ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
