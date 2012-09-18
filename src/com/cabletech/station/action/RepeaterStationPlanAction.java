package com.cabletech.station.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.power.CheckPower;
import com.cabletech.station.beans.FlowWorkInfoBean;
import com.cabletech.station.beans.RepeaterStationPlanBean;
import com.cabletech.station.services.ExecuteCode;
import com.cabletech.station.services.RepeaterStationPlanBO;
import com.cabletech.station.services.StationConstant;

/**
 * �м�վ�ƻ���ϢAction
 * 
 * @author yangjun
 * 
 */
public class RepeaterStationPlanAction extends BaseDispatchAction {
    private static final String CONDITION_KEY = "STATION_PLAN_QUERY_CONDITION";

    private RepeaterStationPlanBO baseBo;

    /**
     * ��ȡҪ�������м�վ�ƻ��ı� ����Ȩ�ޣ�23001
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward addForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23001")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationPlanBO();
        List regionList = baseBo.queryRegion();
        request.setAttribute("region_list", regionList);

        return mapping.findForward("addRepeaterStationPlanForm");
    }

    /**
     * ���洴�����м�վ�ƻ���Ϣ ����Ȩ�ޣ�23001
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23001")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationPlanBO();
        RepeaterStationPlanBean plan = (RepeaterStationPlanBean) form;
        List stationListInPlan = getStationListInPlan(request);

        String submitAuditing = request.getParameter("submit_auditing");
        if (ExecuteCode.SUBMIT_ACTION.equals(submitAuditing)) {
            plan.setPlanState(StationConstant.WAIT_AUDITING_STATE);
        }
        if (ExecuteCode.NOT_SUBMIT_ACTION.equals(submitAuditing)) {
            plan.setPlanState(StationConstant.WAIT_SUBMIT_STATE);
        }

        String operationCode = baseBo.insertRepeaterStationPlan(plan, stationListInPlan);
        String url = request.getContextPath() + "/station_plan.do?method=";
        if (ExecuteCode.EXIST_PLAN_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "ExistPlanError", url
                    + "addForm");
        }
        if (ExecuteCode.NOT_EXIST_STATION_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistPlanStationError", url
                    + "list");
        }
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "FailCode", url + "addForm");
        }
        if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
            return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode", url
                    + "list&reset_query=1");
        }

        return mapping.findForward("");
    }

    /**
     * ��ȡҪ�޸ĵ��м�վ�ƻ���Ϣ�ı� ����Ȩ�ޣ�23002
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward modForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23002")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationPlanBO();
        List regionList = baseBo.queryRegion();
        request.setAttribute("region_list", regionList);

        String planId = request.getParameter("plan_id");

        RepeaterStationPlanBean bean = baseBo.viewRepeaterStationPlan(planId);
        request.setAttribute("plan_bean", bean);

        List stationList = baseBo.getStationListInPlan(planId);
        request.setAttribute("station_list", stationList);

        return mapping.findForward("modRepeaterStationPlanForm");
    }

    /**
     * �����޸ĵ��м�վ�ƻ���Ϣ ����Ȩ�ޣ�23002
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward mod(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23002")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationPlanBO();
        RepeaterStationPlanBean plan = (RepeaterStationPlanBean) form;
        List stationListInPlan = getStationListInPlan(request);

        String submitAuditing = request.getParameter("submit_auditing");
        if (ExecuteCode.SUBMIT_ACTION.equals(submitAuditing)) {
            plan.setPlanState(StationConstant.WAIT_AUDITING_STATE);
        }
        if (ExecuteCode.NOT_SUBMIT_ACTION.equals(submitAuditing)) {
            plan.setPlanState(StationConstant.WAIT_SUBMIT_STATE);
        }

        String operationCode = baseBo.updateRepeaterStationPlan(plan, stationListInPlan);
        String url = request.getContextPath() + "/station_plan.do?method=";
        if (ExecuteCode.NOT_EXIST_PLAN_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistPlanError", url
                    + "list");
        }
        if (ExecuteCode.EXIST_PLAN_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "ExistPlanError", url
                    + "modForm&plan_id=" + plan.getTid());
        }
        if (ExecuteCode.NOT_EDIT_PLAN_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotAllowEditPlanError", url
                    + "list");
        }
        if (ExecuteCode.NOT_EXIST_STATION_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistPlanStationError", url
                    + "list");
        }
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "FailCode", url
                    + "modForm&plan_id=" + plan.getTid());
        }
        if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
            return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode", url + "list");
        }

        return mapping.findForward("");
    }

    /**
     * ��ȡ�м�վ�ƻ��ĸ��Ʊ� ����Ȩ�ޣ�23001
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward copyForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23001")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationPlanBO();

        String planId = request.getParameter("plan_id");

        RepeaterStationPlanBean bean = baseBo.viewRepeaterStationPlan(planId);
        request.setAttribute("plan_bean", bean);

        String regionName = baseBo.queryRegionName(bean.getRegionId());
        request.setAttribute("region_name", regionName);

        List stationList = baseBo.getStationListInPlan(planId);
        request.setAttribute("station_list", stationList);

        return mapping.findForward("copyRepeaterStationPlanForm");
    }

    /**
     * ���渴�Ƶ��м�վ�ƻ���Ϣ ����Ȩ�ޣ�23001
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward copy(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23001")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationPlanBO();

        String planId = request.getParameter("planId");

        RepeaterStationPlanBean plan = baseBo.viewRepeaterStationPlan(planId);
        List stationListInPlan = getStationListInPlan(baseBo.getStationListInPlan(planId));

        plan.setPlanName(request.getParameter("planName"));
        plan.setBeginDate(request.getParameter("beginDate"));
        plan.setEndDate(request.getParameter("endDate"));

        String submitAuditing = request.getParameter("submit_auditing");
        if (ExecuteCode.SUBMIT_ACTION.equals(submitAuditing)) {
            plan.setPlanState(StationConstant.WAIT_AUDITING_STATE);
        }
        if (ExecuteCode.NOT_SUBMIT_ACTION.equals(submitAuditing)) {
            plan.setPlanState(StationConstant.WAIT_SUBMIT_STATE);
        }

        String operationCode = baseBo.insertRepeaterStationPlan(plan, stationListInPlan);
        String url = request.getContextPath() + "/station_plan.do?method=";
        if (ExecuteCode.EXIST_PLAN_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "ExistPlanError", url
                    + "copyForm&plan_id=" + planId);
        }
        if (ExecuteCode.NOT_EXIST_STATION_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistPlanStationError", url
                    + "list");
        }
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "FailCode", url
                    + "copyForm&plan_id=" + planId);
        }
        if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
            return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode", url + "list");
        }

        return mapping.findForward("");
    }

    /**
     * ɾ���м�վ�ƻ���Ϣ ����Ȩ�ޣ�23003
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward del(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23003")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationPlanBO();
        RepeaterStationPlanBean plan = (RepeaterStationPlanBean) form;
        plan.setTid(request.getParameter("plan_id"));

        String operationCode = baseBo.deleteRepeaterStationPlan(plan);
        String url = request.getContextPath() + "/station_plan.do?method=list";
        if (ExecuteCode.NOT_EXIST_PLAN_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistPlanError", url);
        }
        if (ExecuteCode.NOT_EDIT_PLAN_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotAllowEditPlanError", url);
        }
        if (ExecuteCode.NOT_EXIST_STATION_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistPlanStationError", url);
        }
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "FailCode", url);
        }
        if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
            return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode", url);
        }

        return mapping.findForward("");
    }

    /**
     * ��ȡ�м�վ�ƻ�����˱� ����Ȩ�ޣ�23101
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward auditingForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23101")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationPlanBO();

        String planId = request.getParameter("plan_id");

        RepeaterStationPlanBean bean = baseBo.viewRepeaterStationPlan(planId);
        request.setAttribute("plan_bean", bean);

        String regionName = baseBo.queryRegionName(bean.getRegionId());
        request.setAttribute("region_name", regionName);

        List stationList = baseBo.getStationListInPlan(planId);
        request.setAttribute("station_list", stationList);

        List auditingHistoryList = baseBo.getAuditingHistoryList(planId);
        request.setAttribute("auditing_history", auditingHistoryList);

        String method = request.getParameter("query_method");
        request.setAttribute("query_method", method);

        return mapping.findForward("auditingRepeaterStationPlanForm");
    }

    /**
     * �����м�վ�ƻ��������Ϣ ����Ȩ�ޣ�23101
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward auditing(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23101")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationPlanBO();
        RepeaterStationPlanBean auditingPlanBean = (RepeaterStationPlanBean) form;
        FlowWorkInfoBean auditingHistoryBean = new FlowWorkInfoBean();
        BeanUtil.objectCopy(auditingPlanBean, auditingHistoryBean);
        auditingHistoryBean.setObjectId(request.getParameter("objectId"));

        String planId = auditingHistoryBean.getObjectId();
        String method = request.getParameter("query_method");

        String operationCode = baseBo.auditingPlan(planId, auditingHistoryBean);
        String url = request.getContextPath() + "/station_plan.do?method=";
        if (ExecuteCode.NOT_EXIST_PLAN_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistPlanError", url
                    + method);
        }
        if (ExecuteCode.NOT_AUDITING_PLAN_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotAllowAuditingPlanError", url
                    + method);
        }
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "FailCode", url
                    + "auditingForm&plan_id=" + planId);
        }
        if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
            return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode", url + method);
        }

        return mapping.findForward("");
    }

    /**
     * ��ȡ����˵��м�վ�ƻ���Ϣ�б� ����Ȩ�ޣ�23101
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listWaitAuditingPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23101")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationPlanBO();
        String condition = " and plan_state='" + StationConstant.WAIT_AUDITING_STATE
                + "' order by end_date desc,begin_date,tid desc";
        List list = baseBo.queryRepeaterStationPlanList(condition);
        request.getSession().setAttribute("PLAN_LIST", list);
        this.setPageReset(request);
        return mapping.findForward("listWaitAuditingPlan");
    }

    /**
     * ��ʾ����д���м�վ�ƻ��б� ����Ȩ�ޣ�23201
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward listNotFinishedPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23201")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationPlanBO();

        String condition = " and plan_state='" + StationConstant.PASSED_STATE
                + "' order by end_date desc,begin_date,tid desc";
        List list = baseBo.queryRepeaterStationPlanList(condition);
        request.getSession().setAttribute("PLAN_LIST", list);
        return mapping.findForward("listNotFinishedPlan");
    }

    /**
     * ��ȡ�м�վ�ƻ���Ϣ�б� ����Ȩ�ޣ�23004
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23004")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationPlanBO();
        String condition = "";
        if (request.getSession().getAttribute(CONDITION_KEY) != null) {
            condition = (String) request.getSession().getAttribute(CONDITION_KEY);
        }
        if (request.getSession().getAttribute(CONDITION_KEY) == null) {
            condition = condition + " order by end_date desc,begin_date,tid desc";
        }
        if ("1".equals(request.getParameter("reset_query"))) {
            condition = getConditionString(request);
            condition = condition + " order by end_date desc,begin_date,tid desc";
        }

        List list = baseBo.queryRepeaterStationPlanList(condition);
        request.getSession().setAttribute("PLAN_LIST", list);
        request.getSession().setAttribute(CONDITION_KEY, condition);

        return mapping.findForward("listRepeaterStationPlan");
    }

    /**
     * �鿴ĳ���м�վ�ƻ���Ϣ ����Ȩ�ޣ���
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        // if (!CheckPower.checkPower(request.getSession(), "23004")) {
        // return mapping.findForward("powererror");
        // }
        String listMethod = "list";
        if (request.getParameter("list_method") != null
                && !"".equals(request.getParameter("list_method"))) {
            listMethod = request.getParameter("list_method");
        }

        baseBo = new RepeaterStationPlanBO();
        String planId = request.getParameter("plan_id");

        RepeaterStationPlanBean bean = baseBo.viewRepeaterStationPlan(planId);
        request.setAttribute("plan_bean", bean);

        String regionName = baseBo.queryRegionName(bean.getRegionId());
        request.setAttribute("region_name", regionName);

        List stationList = baseBo.getStationListInPlan(planId);
        request.setAttribute("station_list", stationList);

        List auditingHistoryList = baseBo.getAuditingHistoryList(planId);
        request.setAttribute("auditing_history", auditingHistoryList);

        request.setAttribute("list_method", listMethod);

        return mapping.findForward("viewRepeaterStationPlan");
    }

    /**
     * ��ȡĳ���м�վ�ƻ���������Ϣ ����Ȩ�ޣ���
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward viewAll(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        // if (!CheckPower.checkPower(request.getSession(), "23301")) {
        // return mapping.findForward("powererror");
        // }

        baseBo = new RepeaterStationPlanBO();
        String planId = request.getParameter("plan_id");

        RepeaterStationPlanBean bean = baseBo.viewRepeaterStationPlan(planId);
        request.setAttribute("plan_bean", bean);

        String regionName = baseBo.queryRegionName(bean.getRegionId());
        request.setAttribute("region_name", regionName);

        List stationList = baseBo.getStationListInPlan(planId);
        request.setAttribute("station_list", stationList);

        List auditingHistoryList = baseBo.getAuditingHistoryList(planId);
        request.setAttribute("auditing_history", auditingHistoryList);

        return mapping.findForward("viewRepeaterStationPlanAllInfo");
    }

    /**
     * �����м�վ�ƻ��Ĳ�ѯ ����Ȩ�ޣ�23004
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward queryForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23004")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationPlanBO();
        List regionList = baseBo.queryRegion();
        request.setAttribute("region_list", regionList);

        return mapping.findForward("queryRepeaterStationPlanForm");
    }

    /**
     * �����м�վ�ƻ���Ϣ�ĵ��� ����Ȩ�ޣ�23004
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward exportList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        // if (!CheckPower.checkPower(request.getSession(), "23004")) {
        // return mapping.findForward("powererror");
        // }

        baseBo = new RepeaterStationPlanBO();
        List list = (List) request.getSession().getAttribute("PLAN_LIST");
        baseBo.exportRepeaterStationPlanList(response, list);
        return null;
    }

    /**
     * �����м�վ�ƻ������ж�����м�վ�ƻ��Ƿ���� ����Ȩ�ޣ���
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward judgeExist(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        baseBo = new RepeaterStationPlanBO();
        String planName = request.getParameter("plan_name");

        boolean flag = baseBo.queryExistByName("RepeaterStationPlan", "plan_name", planName);
        if (flag) {
            response.getWriter().print("1");
        } else {
            response.getWriter().print("0");
        }
        response.getWriter().close();
        return null;
    }

    /**
     * �������е��м�վ�б������ŵ��м�վ����б�List��
     * 
     * @param request
     *            HttpServletRequest �������
     * @return List �����м�վ����б�List
     */
    private List getStationListInPlan(HttpServletRequest request) {
        List stationListInPlan = new ArrayList();
        String[] stationInPlan = request.getParameterValues("station_in_plan");
        for (int i = 0; stationInPlan != null && i < stationInPlan.length; i++) {
            stationListInPlan.add(stationInPlan[i]);
        }
        return stationListInPlan;
    }

    /**
     * ���м�վ�б�List�е��м�վ��ŵ��м�վ����б�List��
     * 
     * @param stationListPlanItem
     *            List �м�վ�б�List
     * @return List �����м�վ����б�List
     */
    private List getStationListInPlan(List stationListPlanItem) {
        // TODO Auto-generated method stub
        List stationListInPlan = new ArrayList();
        DynaBean bean;
        for (int i = 0; stationListPlanItem != null && i < stationListPlanItem.size(); i++) {
            bean = (DynaBean) stationListPlanItem.get(i);
            stationListInPlan.add(bean.get("repeater_station_id"));
        }
        return stationListInPlan;
    }

    /**
     * ��������Ĳ�ѯ�������ɲ�ѯ�����ַ���
     * 
     * @param request
     *            HttpServletRequest �������
     * @return String ���ɵĲ�ѯ�����ַ���
     */
    private String getConditionString(HttpServletRequest request) {
        // TODO Auto-generated method stub
        StringBuffer condition = new StringBuffer("");

        String regionId = super.parseParameter(request.getParameter("region_id"), "p.region_id",
                "'", "'");
        String stationId = super.parseParameter(request.getParameter("station_id"),
                "repeater_station_id", "'", "'");
        String planState = super.parseParameter(request.getParameter("plan_state"), "p.plan_state",
                "'", "'");
        String planType = super.parseParameter(request.getParameter("plan_type"), "p.plan_type",
                "'", "'");
        String beginDateStart = "";
        String beginDateEnd = "";
        String endDateStart = "";
        String endDateEnd = "";
        if (request.getParameter("begin_date_start") != null
                && !"".equals(request.getParameter("begin_date_start"))) {
            beginDateStart = super.parseParameter("to_date('"
                    + request.getParameter("begin_date_start")
                    + " 00:00:00','yyyy-mm-dd hh24:mi:ss')", "p.begin_date", "", "");
        } else {
            beginDateStart = "p.begin_date";
        }
        if (request.getParameter("begin_date_end") != null
                && !"".equals(request.getParameter("begin_date_end"))) {
            beginDateEnd = super.parseParameter("to_date('"
                    + request.getParameter("begin_date_end")
                    + " 23:59:59','yyyy-mm-dd hh24:mi:ss')", "p.begin_date", "", "");
        } else {
            beginDateEnd = "p.begin_date";
        }
        if (request.getParameter("end_date_start") != null
                && !"".equals(request.getParameter("end_date_start"))) {
            endDateStart = super.parseParameter("to_date('"
                    + request.getParameter("end_date_start")
                    + " 00:00:00','yyyy-mm-dd hh24:mi:ss')", "p.end_date", "", "");
        } else {
            endDateStart = "p.end_date";
        }
        if (request.getParameter("end_date_end") != null
                && !"".equals(request.getParameter("end_date_end"))) {
            endDateEnd = super.parseParameter("to_date('" + request.getParameter("end_date_end")
                    + " 23:59:59','yyyy-mm-dd hh24:mi:ss')", "p.end_date", "", "");
        } else {
            endDateEnd = "p.end_date";
        }

        condition
                .append(" and p.tid in (select plan_id from repeater_station_plan_item where repeater_station_id="
                        + stationId + ")");
        condition.append(" and p.region_id=" + regionId);
        condition.append(" and p.plan_state=" + planState);
        condition.append(" and p.plan_type=" + planType);
        condition.append(" and p.begin_date>=" + beginDateStart);
        condition.append(" and p.begin_date<=" + beginDateEnd);
        condition.append(" and p.end_date>=" + endDateStart);
        condition.append(" and p.end_date<=" + endDateEnd);
        return condition.toString();
    }

}
