package com.cabletech.station.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.Terminal;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.power.CheckPower;
import com.cabletech.station.beans.PlanPatrolResultBean;
import com.cabletech.station.beans.RepeaterStationBean;
import com.cabletech.station.beans.RepeaterStationPlanBean;
import com.cabletech.station.services.ExecuteCode;
import com.cabletech.station.services.PlanPatrolResultBO;
import com.cabletech.station.services.PlanPatrolResultDayBO;
import com.cabletech.station.services.PlanPatrolResultHalfYearBO;
import com.cabletech.station.services.PlanPatrolResultMonthBO;
import com.cabletech.station.services.PlanPatrolResultWeekBO;
import com.cabletech.station.services.RepeaterStationBO;
import com.cabletech.station.services.RepeaterStationPlanBO;
import com.cabletech.station.services.StationConstant;

/**
 * 中继站计划巡检执行信息填写Action
 * 
 * @author yangjun
 * 
 */
public class PlanPatrolResultAction extends BaseDispatchAction {
    private static final String CONDITION_KEY = "PATROL_RESULT_QUERY_CONDITION";

    private RepeaterStationPlanBO planBo;

    private RepeaterStationBO stationBo;

    private PlanPatrolResultBO baseBo;

    /**
     * 获取中继站计划的中继站填写信息的表单信息 操作权限：23201
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
    public ActionForward writeForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23201")) {
            return mapping.findForward("powererror");
        }

        stationBo = new RepeaterStationBO();
        planBo = new RepeaterStationPlanBO();
        String planId = request.getParameter("plan_id");

        RepeaterStationPlanBean bean = planBo.viewRepeaterStationPlan(planId);
        request.setAttribute("plan_bean", bean);

        String regionName = planBo.queryRegionName(bean.getRegionId());
        request.setAttribute("region_name", regionName);

        String userName = planBo.queryUserName(bean.getValidateUserid());
        request.setAttribute("validate_user_name", userName);

        String stationId = request.getParameter("station_id");
        RepeaterStationBean stationBean = stationBo.viewRepeaterStation(stationId);
        request.setAttribute("station_id", stationBean.getTid());
        request.setAttribute("station_name", stationBean.getStationName());

        String planType = bean.getPlanType();
        if (StationConstant.PLAN_DAY.equals(planType)) {
            baseBo = new PlanPatrolResultDayBO();
        }
        if (StationConstant.PLAN_WEEK.equals(planType)) {
            baseBo = new PlanPatrolResultWeekBO();
        }
        if (StationConstant.PLAN_MONTH.equals(planType)) {
            baseBo = new PlanPatrolResultMonthBO();
        }
        if (StationConstant.PLAN_HALF_YEAR.equals(planType)) {
            baseBo = new PlanPatrolResultHalfYearBO();
        }
        PlanPatrolResultBean patrolBean = baseBo.viewPlanStationResult(stationId, planId);
        if (patrolBean != null) {
            request.setAttribute("patrol_bean", patrolBean);
            String simId=patrolBean.getSimId();
            String condition=" and simnumber='"+simId+"'";
            List list=baseBo.queryTerminal(condition);
            if(list!=null&&list.size()>0){
                Terminal terminal=(Terminal)list.get(0);
                String patrolId=terminal.getOwnerID();
                request.setAttribute("patrol_id", patrolId);
            }
        }

        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String condition = getPatrolManCondition(user);
        List patrolmanList = planBo.queryPatrolMan(condition);
        request.setAttribute("patrolman_list", patrolmanList);

        return mapping.findForward("writeStationPatrolResultForm");
    }

    /**
     * 保存中继站计划的中继站填写信息 操作权限：23201
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
    public ActionForward write(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23201")) {
            return mapping.findForward("powererror");
        }

        planBo = new RepeaterStationPlanBO();

        PlanPatrolResultBean bean = (PlanPatrolResultBean) form;
        String planId = bean.getPlanId();
        String stationId = bean.getRepeaterStationId();
        String planType = request.getParameter("plan_type");
        if (StationConstant.PLAN_DAY.equals(planType)) {
            baseBo = new PlanPatrolResultDayBO();
        }
        if (StationConstant.PLAN_WEEK.equals(planType)) {
            baseBo = new PlanPatrolResultWeekBO();
        }
        if (StationConstant.PLAN_MONTH.equals(planType)) {
            baseBo = new PlanPatrolResultMonthBO();
        }
        if (StationConstant.PLAN_HALF_YEAR.equals(planType)) {
            baseBo = new PlanPatrolResultHalfYearBO();
        }

        String operationCode = baseBo.writePlanStationResult(bean);
        String url = request.getContextPath() + "/plan_patrol_result.do?method=";
        String url2 = request.getContextPath() + "/station_plan.do?method=";
        if (ExecuteCode.NOT_EXIST_PLAN_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistPlanError", url2
                    + "listNotFinishedPlan");
        }
        if (ExecuteCode.NOT_PASSED_PLAN_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotAllowPatrolPlanError", url2
                    + "listNotFinishedPlan");
        }
        if (ExecuteCode.FINISHED_PLAN_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "IsFinishPlanError", url2
                    + "listNotFinishedPlan");
        }
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "FailCode", url
                    + "writeForm&station_id=" + stationId + "&plan_id=" + planId);
        }
        if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
            return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode", url
                    + "viewWaitWrite&plan_id=" + planId);
        }

        return mapping.findForward("");
    }

    /**
     * 保存中继站计划的填写信息状态 操作权限：23201
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
    public ActionForward updatePlanPatrolState(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23201")) {
            return mapping.findForward("powererror");
        }

        String operationCode = "";
        String planId = request.getParameter("plan_id");
        String allFinished = request.getParameter("all_finished");
        String url = request.getContextPath() + "/plan_patrol_result.do?method=";
        String url2 = request.getContextPath() + "/station_plan.do?method=";
        if (StationConstant.ALL_FINISHED_STATE.equals(allFinished)) {
            operationCode = planBo.updateRepeaterStationPlanState(planId,
                    StationConstant.FINISHED_STATE);
            if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
                return super.forwardErrorPageWithUrl(mapping, request, "FailCode", url
                        + "viewWaitWrite&plan_id=" + planId);
            }
            if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
                return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode", url2
                        + "listNotFinishedPlan");
            }
        } else {
            operationCode = ExecuteCode.SUCCESS_CODE;
            if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
                return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode", url2
                        + "listNotFinishedPlan");
            }
        }
        return mapping.findForward("");
    }

    /**
     * 显示待填写中继站计划的信息 操作权限：23201
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
    public ActionForward viewWaitWrite(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        if (!CheckPower.checkPower(request.getSession(), "23201")) {
            return mapping.findForward("powererror");
        }

        planBo = new RepeaterStationPlanBO();
        String planId = request.getParameter("plan_id");

        RepeaterStationPlanBean bean = planBo.viewRepeaterStationPlan(planId);
        request.setAttribute("plan_bean", bean);

        String regionName = planBo.queryRegionName(bean.getRegionId());
        request.setAttribute("region_name", regionName);

        String userName = planBo.queryUserName(bean.getValidateUserid());
        request.setAttribute("validate_user_name", userName);

        String planType = bean.getPlanType();
        String tableType = "";
        if (StationConstant.PLAN_DAY.equals(planType)) {
            tableType = "day";
        }
        if (StationConstant.PLAN_WEEK.equals(planType)) {
            tableType = "week";
        }
        if (StationConstant.PLAN_MONTH.equals(planType)) {
            tableType = "month";
        }
        if (StationConstant.PLAN_HALF_YEAR.equals(planType)) {
            tableType = "half_year";
        }
        List writedStationList = planBo.getStationListForWriteInPlan(planId, tableType,
                StationConstant.WRITED_STATE);
        request.setAttribute("writed_station_list", writedStationList);
        List notWritedStationList = planBo.getStationListForWriteInPlan(planId, tableType,
                StationConstant.NOT_WRITED_STATE);
        request.setAttribute("not_writed_station_list", notWritedStationList);

        return mapping.findForward("viewRepeaterStationPlanWaitWrite");
    }

    /**
     * 查看中继站计划的中继站填写信息 操作权限：23301
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
        if (!CheckPower.checkPower(request.getSession(), "23301")) {
            return mapping.findForward("powererror");
        }

        stationBo = new RepeaterStationBO();
        String planId = request.getParameter("plan_id");

        RepeaterStationPlanBean bean = planBo.viewRepeaterStationPlan(planId);
        request.setAttribute("plan_bean", bean);

        String stationId = request.getParameter("station_id");
        RepeaterStationBean stationBean = stationBo.viewRepeaterStation(stationId);
        request.setAttribute("station_name", stationBean.getStationName());

        String planType = bean.getPlanType();
        if (StationConstant.PLAN_DAY.equals(planType)) {
            baseBo = new PlanPatrolResultDayBO();
        }
        if (StationConstant.PLAN_WEEK.equals(planType)) {
            baseBo = new PlanPatrolResultWeekBO();
        }
        if (StationConstant.PLAN_MONTH.equals(planType)) {
            baseBo = new PlanPatrolResultMonthBO();
        }
        if (StationConstant.PLAN_HALF_YEAR.equals(planType)) {
            baseBo = new PlanPatrolResultHalfYearBO();
        }
        PlanPatrolResultBean patrolBean = baseBo.viewPlanStationResult(stationId, planId);
        request.setAttribute("patrol_bean", patrolBean);
        request.setAttribute("plan_type", planType);

        return mapping.findForward("viewPlanPatrolResult");
    }

    /**
     * 显示所有中继站计划的列表 操作权限：23301
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
        if (!CheckPower.checkPower(request.getSession(), "23301")) {
            return mapping.findForward("powererror");
        }

        planBo = new RepeaterStationPlanBO();
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

        List list = planBo.queryRepeaterStationPlanList(condition);
        request.getSession().setAttribute("PLAN_LIST", list);
        request.getSession().setAttribute(CONDITION_KEY, condition);

        this.setPageReset(request);
        return mapping.findForward("listPlan");
    }

    /**
     * 进行中继站计划的查询 操作权限：23301
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
        if (!CheckPower.checkPower(request.getSession(), "23301")) {
            return mapping.findForward("powererror");
        }

        planBo = new RepeaterStationPlanBO();
        List regionList = planBo.queryRegion();
        request.setAttribute("region_list", regionList);

        // String condition = "";
        // List terminalList = planBo.queryTerminal(condition);
        // request.setAttribute("terminal_list", terminalList);

        return mapping.findForward("queryPlanPatrolResultForm");
    }

    /**
     * 根据区域编号载入中继站下拉选择列表 操作权限：无
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
    public ActionForward loadTerminalByPatrolMan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        planBo = new RepeaterStationPlanBO();
        String condition = "";
        // if (!"".equals(request.getParameter("region_id"))) {
        condition = " and ownerid='" + request.getParameter("patrolman_id") + "'";
        // }

        List list = baseBo.queryTerminal(condition);
        PrintWriter out = response.getWriter();
        out.print("<select id=\"simId\" class=\"inputtext\" name=\"simId\" style=\"width:250;\">");
        Terminal terminal;
        for (int i = 0; list != null && i < list.size(); i++) {
            terminal = (Terminal) list.get(i);
            out.print("<option value=\"" + terminal.getSimNumber() + "\">");
            out.print(terminal.getSimNumber());
            out.print("</option>");
        }
        out.print("</select>");
        out.close();

        return null;
    }

    /**
     * 根据请求的查询条件生成查询条件字符串
     * 
     * @param request
     *            HttpServletRequest 请求对象
     * @return String 生成的查询条件字符串
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
        String simId = super.parseParameter(request.getParameter("sim_id"), "sim_id", "'%", "%'");
        String beginDateStart = "";
        String beginDateEnd = "";
        String endDateStart = "";
        String endDateEnd = "";
        String patrolDateStart = "";
        String patrolDateEnd = "";
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
        if (request.getParameter("patrol_date_start") != null
                && !"".equals(request.getParameter("patrol_date_start"))) {
            patrolDateStart = super.parseParameter("to_date('"
                    + request.getParameter("patrol_date_start")
                    + " 00:00:00','yyyy-mm-dd hh24:mi:ss')", "patroldate", "", "");
        } else {
            patrolDateStart = "patrol_date";
        }
        if (request.getParameter("patrol_date_end") != null
                && !"".equals(request.getParameter("patrol_date_end"))) {
            patrolDateEnd = super.parseParameter("to_date('"
                    + request.getParameter("patrol_date_end")
                    + " 23:59:59','yyyy-mm-dd hh24:mi:ss')", "patroldate", "", "");
        } else {
            patrolDateEnd = "patrol_date";
        }

        if (!"".equals(request.getParameter("plan_type"))) {
            String tableName = "";
            if (StationConstant.PLAN_DAY.equals(request.getParameter("plan_type"))) {
                tableName = "day";
            }
            if (StationConstant.PLAN_WEEK.equals(request.getParameter("plan_type"))) {
                tableName = "week";
            }
            if (StationConstant.PLAN_MONTH.equals(request.getParameter("plan_type"))) {
                tableName = "month";
            }
            if (StationConstant.PLAN_HALF_YEAR.equals(request.getParameter("plan_type"))) {
                tableName = "half_year";
            }
            if ((request.getParameter("sim_id") != null && !"".equals(request
                    .getParameter("sim_id")))
                    || (request.getParameter("patrol_date_start") != null && !"".equals(request
                            .getParameter("patrol_date_start")))
                    || (request.getParameter("patrol_date_end") != null && !"".equals(request
                            .getParameter("patrol_date_end")))) {
                condition.append(" and p.tid in (select plan_id from plan_patrol_result_"
                        + tableName + " where sim_id like " + simId + " and patrol_date>="
                        + patrolDateStart + " and patrol_date<=" + patrolDateEnd + ") ");
            }
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

    /**
     * 根据当前用户查询所属巡检组下的巡检组查询条件
     * 
     * @param user
     *            UserInfo 当前用户信息
     * @return String 当前用户查询所属巡检组下的巡检组查询条件
     */
    private String getPatrolManCondition(UserInfo user) {
        // TODO Auto-generated method stub
        StringBuffer condition = new StringBuffer();
        if ("12".equals(user.getType())) {
            condition.append(" and regionid='");
            condition.append(user.getRegionid());
            condition.append("' ");
        }
        if ("21".equals(user.getType())) {
            condition
                    .append(" and parentid in (select contractorid from contractorinfo start with contractorid='");
            condition.append(user.getDeptID());
            condition.append("' connect by prior contractorid=parentcontractorid) ");
        }
        if ("22".equals(user.getType())) {
            condition.append(" and parentid='");
            condition.append(user.getDeptID());
            condition.append("' ");
        }
        return condition.toString();
    }

    /**
     * 根据当前用户查询所属巡检组下的SIM卡号查询条件
     * 
     * @param user
     *            UserInfo 当前用户信息
     * @return String 当前用户查询所属巡检组下的SIM卡号查询条件
     */
    private String getTerminalCondition(UserInfo user) {
        // TODO Auto-generated method stub
        StringBuffer condition = new StringBuffer();
        if ("12".equals(user.getType())) {
            condition.append(" and regionid='");
            condition.append(user.getRegionid());
            condition.append("' ");
        }
        if ("21".equals(user.getType())) {
            condition
                    .append(" and ownerid in (select patrolid from patrolmaninfo where parentid in (select contractorid from contractorinfo start with contractorid='");
            condition.append(user.getDeptID());
            condition.append("' connect by prior contractorid=parentcontractorid)) ");
        }
        if ("22".equals(user.getType())) {
            condition
                    .append(" and ownerid in (select patrolid from patrolmaninfo where parentid='");
            condition.append(user.getDeptID());
            condition.append("') ");
        }
        return condition.toString();
    }

}
