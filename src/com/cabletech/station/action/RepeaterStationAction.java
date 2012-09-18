package com.cabletech.station.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.power.CheckPower;
import com.cabletech.station.beans.RepeaterStationBean;
import com.cabletech.station.services.ExecuteCode;
import com.cabletech.station.services.RepeaterStationBO;
import com.cabletech.station.services.StationConstant;

/**
 * 中继站基本信息Action
 * 
 * @author yangjun
 * 
 */
public class RepeaterStationAction extends BaseDispatchAction {
    private static final String CONDITION_KEY = "STATION_QUERY_CONDITION";

    private RepeaterStationBO baseBo;

    /**
     * 获取要创建的中继站信息的表单 操作权限：73001
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
        if (!CheckPower.checkPower(request.getSession(), "73001")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationBO();
        List regionList = baseBo.queryRegion();
        request.setAttribute("region_list", regionList);

        return mapping.findForward("addRepeaterStationForm");
    }

    /**
     * 保存中继站信息 操作权限：73001
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
        if (!CheckPower.checkPower(request.getSession(), "73001")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationBO();
        RepeaterStationBean bean = (RepeaterStationBean) form;
        String operationCode = baseBo.insertRepeaterStation(bean);
        String url = request.getContextPath() + "/station_info.do?method=";
        if (ExecuteCode.EXIST_STATION_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "ExistStationError", url
                    + "addForm");
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
     * 获取要修改的中继站信息的表单 操作权限：73002
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
        if (!CheckPower.checkPower(request.getSession(), "73002")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationBO();
        List regionList = baseBo.queryRegion();
        request.setAttribute("region_list", regionList);

        String stationId = request.getParameter("station_id");

        RepeaterStationBean bean = baseBo.viewRepeaterStation(stationId);
        request.setAttribute("station_bean", bean);

        return mapping.findForward("modRepeaterStationForm");
    }

    /**
     * 保存修改的中继站信息 操作权限：73002
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
        if (!CheckPower.checkPower(request.getSession(), "73002")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationBO();
        RepeaterStationBean bean = (RepeaterStationBean) form;
        String operationCode = baseBo.updateRepeaterStation(bean);
        String url = request.getContextPath() + "/station_info.do?method=";
        if (ExecuteCode.NOT_EXIST_STATION_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistStationError", url
                    + "list");
        }
        if (ExecuteCode.EXIST_STATION_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "ExistStationError", url
                    + "modForm&station_id=" + bean.getTid());
        }
        // if (ExecuteCode.EXIST_PLAN_ERR_CODE.equals(operationCode)) {
        // return super.forwardErrorPageWithUrl(mapping, request,
        // "StationExistPlanError", url
        // + "list");
        // }
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "FailCode", url
                    + "modForm&station_id=" + bean.getTid());
        }
        if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
            return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode", url + "list");
        }
        return mapping.findForward("");
    }

    /**
     * 删除中继站信息 操作权限：73003
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
        if (!CheckPower.checkPower(request.getSession(), "73003")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationBO();
        RepeaterStationBean bean = new RepeaterStationBean();
        bean.setTid(request.getParameter("station_id"));
        String operationCode = baseBo.deleteRepeaterStation(bean);
        String url = request.getContextPath() + "/station_info.do?method=list";
        if (ExecuteCode.NOT_EXIST_STATION_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "NotExistStationError", url);
        }
        if (ExecuteCode.EXIST_PLAN_ERR_CODE.equals(operationCode)) {
            return super.forwardErrorPageWithUrl(mapping, request, "StationExistPlanError", url);
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
     * 获取中继站信息的列表 操作权限：73004
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
        if (!CheckPower.checkPower(request.getSession(), "73004")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationBO();
        String condition = "";
        if (request.getSession().getAttribute(CONDITION_KEY) != null) {
            condition = (String) request.getSession().getAttribute(CONDITION_KEY);
        }
        if (request.getSession().getAttribute(CONDITION_KEY) == null) {
            condition = condition + " and station_state='" + StationConstant.IS_ACTIVED + "' ";
            condition = condition + " order by station_name,tid desc";
        }
        if ("1".equals(request.getParameter("reset_query"))) {
            condition = getConditionString(request);
            condition = condition + " and station_state='" + StationConstant.IS_ACTIVED + "' ";
            condition = condition + " order by station_name,tid desc";
        }

        List list = baseBo.queryRepeaterStationList(condition);
        request.getSession().setAttribute("STATION_LIST", list);
        request.getSession().setAttribute(CONDITION_KEY, condition);
        super.setPageReset(request);
        return mapping.findForward("listRepeaterStation");
    }

    /**
     * 查看某个中继站信息 操作权限：无
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
        // if (!CheckPower.checkPower(request.getSession(), "73004")) {
        // return mapping.findForward("powererror");
        // }

        baseBo = new RepeaterStationBO();
        String stationId = request.getParameter("station_id");

        RepeaterStationBean bean = baseBo.viewRepeaterStation(stationId);
        request.setAttribute("station_bean", bean);

        String regionName = baseBo.queryRegionName(bean.getRegionId());
        request.setAttribute("region_name", regionName);

        List list = baseBo.queryRepeaterStationList(" and tid='" + stationId + "'");
        request.getSession().setAttribute("ONE_STATION", list);

        return mapping.findForward("viewRepeaterStation");
    }

    /**
     * 进行中继站信息的查询 操作权限：73004
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
        if (!CheckPower.checkPower(request.getSession(), "73004")) {
            return mapping.findForward("powererror");
        }

        baseBo = new RepeaterStationBO();
        List regionList = baseBo.queryRegion();
        request.setAttribute("region_list", regionList);
        return mapping.findForward("queryRepeaterStationForm");
    }

    /**
     * 进行中继站列表信息的导出 操作权限：无
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
        // if (!CheckPower.checkPower(request.getSession(), "73004")) {
        // return mapping.findForward("powererror");
        // }

        baseBo = new RepeaterStationBO();
        List list = (List) request.getSession().getAttribute("STATION_LIST");
        baseBo.exportRepeaterStationList(response, list);
        return null;
    }

    /**
     * 进行某个中继站信息的导出 操作权限：无
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
    public ActionForward exportOne(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        // if (!CheckPower.checkPower(request.getSession(), "73004")) {
        // return mapping.findForward("powererror");
        // }

        baseBo = new RepeaterStationBO();
        List list = (List) request.getSession().getAttribute("ONE_STATION");
        baseBo.exportRepeaterStation(response, list);
        return null;
    }

    /**
     * 根据区域编号载入中继站的选择输入列表 操作权限：无
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
    public ActionForward loadStationByRegion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        baseBo = new RepeaterStationBO();
        String condition = " and region_id='" + request.getParameter("region_id") + "'";
        condition = condition + " and station_state='" + StationConstant.IS_ACTIVED + "' ";

        List list = baseBo.queryRepeaterStationList(condition);
        request.getSession().setAttribute("list", list);

        return mapping.findForward("loadStationByRegion");
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
    public ActionForward loadStationSelectByRegion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        baseBo = new RepeaterStationBO();
        String condition = "";
        String showDeleted = request.getParameter("show_deleted");
        if (showDeleted != null && showDeleted.equals(StationConstant.IS_DELETED)) {
            condition = " and station_state=station_state ";
        } else {
            condition = " and station_state='" + StationConstant.IS_ACTIVED + "' ";
        }
        // if (!"".equals(request.getParameter("region_id"))) {
        condition = condition + " and region_id='" + request.getParameter("region_id") + "'";
        // }

        List list = baseBo.queryRepeaterStationList(condition);
        request.setAttribute("list", list);

        return mapping.findForward("loadStationSelectByRegion");
    }

    /**
     * 根据中继站名称判断这个中继站是否存在 操作权限：无
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
        baseBo = new RepeaterStationBO();
        String stationName = request.getParameter("station_name");
        String regionId = request.getParameter("region_id");
        RepeaterStationBean stationBean = new RepeaterStationBean();
        stationBean.setStationName(stationName);
        stationBean.setRegionId(regionId);

        boolean flag = baseBo.queryExistByName(stationBean);
        if (flag) {
            response.getWriter().print("1");
        } else {
            response.getWriter().print("0");
        }
        response.getWriter().close();
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

        String regionId = super.parseParameter(request.getParameter("region_id"), "s.region_id",
                "'", "'");
        String stationId = super.parseParameter(request.getParameter("station_id"), "s.tid", "'",
                "'");
        String isGroundStation = super.parseParameter(request.getParameter("is_ground_station"),
                "s.is_ground_station", "'", "'");
        String hasElectricity = super.parseParameter(request.getParameter("has_electricity"),
                "s.has_electricity", "'", "'");
        String connectElectricity = super.parseParameter(request
                .getParameter("connect_electricity"), "s.connect_electricity", "'", "'");
        String hasAirCondition = super.parseParameter(request.getParameter("has_air_condition"),
                "s.has_air_condition", "'", "'");
        String connectAirCondition = super.parseParameter(request
                .getParameter("connect_air_condition"), "s.connect_air_condition", "'", "'");
        String connectWindPowerGenerate = super.parseParameter(request
                .getParameter("connect_wind_power_generate"), "s.connect_wind_power_generate", "'",
                "'");

        condition.append(" and s.region_id=" + regionId);
        condition.append(" and s.tid=" + stationId);
        condition.append(" and s.is_ground_station=" + isGroundStation);
        condition.append(" and s.has_electricity=" + hasElectricity);
        condition.append(" and s.connect_electricity=" + connectElectricity);
        condition.append(" and s.has_air_condition=" + hasAirCondition);
        condition.append(" and s.connect_air_condition=" + connectAirCondition);
        condition.append(" and s.connect_wind_power_generate=" + connectWindPowerGenerate);
        return condition.toString();
    }

}
