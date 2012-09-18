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
 * 处理迁改（修缮）申请 表单载入，添加、修改、删除、查看信息、查询。 1、申请阶段 系统工作：填写方案 结束标识：待审定 A 2-1、勘查阶段
 * 系统工作：填写勘查结果 结束标识：通过监理审定 B1 2-2、勘查阶段 系统工作：填写勘查结果 结束标识：通过移动审定 B2 3、准备阶段
 * 系统工作：填写转交/监理设计信息 结束标识：施工准备 C 4、委托阶段 系统工作：填写委托书 结束标识：开始施工 D 5、施工阶段 系统工作：填写施工信息
 * 结束标识：施工完毕 E 6、验收阶段 系统工作：填写验收信息 结束标识：验收完毕 F 7、归档阶段 系统工作：填写归档信息 结束标识：已经归档 G
 * 
 */
public class ChangeSurveyAction extends BaseDispatchAction {
    private static Logger logger = Logger.getLogger("ChangeSurveyAction");

    public ChangeSurveyAction() {
    }

    /**
     * 添加勘查信息
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
        /** 权限校验 */
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
        /** 权限校验 */
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
            // 文件上传
            ArrayList fileIdList = new ArrayList();
            String[] delfileid = request.getParameterValues("delfileid"); // 要删除的文件id号数组
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

        // 获取申请信息
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

        // 获取申请信息
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
            if (survey != null && "未通过".equals(survey.getApproveresult())) {
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
     * 文件上传
     * 
     * @param form
     *            ActionForm
     * @return String
     */
    public String uploadFile(ActionForm form, ArrayList fileIdList) {
        ChangeSurveyBean formbean = (ChangeSurveyBean) form;
        // 开始处理上传文件================================
        // List attachments = formbean.getAttachments();
        // String fileId;
        // for (int i = 0; i < attachments.size(); i++) {
        // UploadFile uploadFile = (UploadFile) attachments.get(i);
        // FormFile file = uploadFile.getFile();
        // if (file == null) {
        // logger.info("file is null");
        // } else {
        // // 将文件存储到服务器并将路径写入数据库，返回记录ID
        // fileId = SaveUploadFile.saveFile(file);
        // if (fileId != null) {
        // fileIdList.add(fileId);
        // }
        // }
        // }
        FormFile file = null;
        String fileId;
        // 取得上传的文件
        Hashtable files = formbean.getMultipartRequestHandler().getFileElements();
        if (files != null && files.size() > 0) {
            // 得到files的keys
            Enumeration enums = files.keys();
            String fileKey = null;
            int i = 0;
            // 遍历枚举
            while (enums.hasMoreElements()) {
                // 取得key
                fileKey = (String) (enums.nextElement());
                // 初始化每一个FormFile(接口)
                file = (FormFile) files.get(fileKey);

                if (file == null) {
                    logger.error("file is null");
                } else {
                    // 将文件存储到服务器并将路径写入数据库，返回记录ID
                    fileId = SaveUploadFile.saveFile(file);
                    if (fileId != null) {
                        fileIdList.add(fileId);
                    }
                }
                i++;
            }

        }
        // 处理上传文件结束=======================================

        // 获取ID字符串列表(以逗号分隔的文件ID字符串)======================
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
                logger.info("获得查询条件bean。。。");
                logger.info("勘查负责人：" + bean.getPrincipal());
                logger.info("工程预算：" + bean.getBudget() + "~~" + bean.getMaxbudget());
                logger.info("审定结果：" + bean.getApproveresult());
                logger.info("开始时间：" + bean.getBegintime());
                logger.info("结束时间：" + bean.getEndtime());

            }
            List list = (List) request.getSession().getAttribute("queryresult");
            logger.info("得到list");
            logger.info("输出excel成功");
            ExportDao ed = new ExportDao();
            ed.exportSurveyResult(list, bean, response);
            logger.info("end.....");
            return null;
        } catch (Exception e) {
            logger.error("导出信息报表出现异常:" + e.getMessage());
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
