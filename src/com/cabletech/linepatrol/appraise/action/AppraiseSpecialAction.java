package com.cabletech.linepatrol.appraise.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.module.AppraiseResult;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
import com.cabletech.linepatrol.appraise.service.AppraiseBO;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyBO;
import com.cabletech.linepatrol.appraise.service.AppraiseTableBO;
import com.cabletech.linepatrol.appraise.util.AppraiseUtil;

public class AppraiseSpecialAction extends AppraiseAction {
	/**
	 * 专项考核生成页面加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createSpecialTableForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initArguments();
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List<Contractor> cons = contractorBO.getAllContractor(userInfo);
		request.getSession().setAttribute("cons", cons);
		return mapping.findForward("createAppraiseSpecialTableForm");
	}

	/**
	 * ajax获得考核表的下拉列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTableOption(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseTableBO appraiseTableBO = (AppraiseTableBO) ctx.getBean("appraiseTableBO");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		List<AppraiseTable> appraiseTables = appraiseTableBO.getTableInDate(DateUtil.StringToDate(startDate,
				"yyyy-MM-dd"), DateUtil.StringToDate(endDate, "yyyy-MM-dd"), AppraiseConstant.APPRAISE_SPECIAL);
		String html = AppraiseTable.getTableOption(appraiseTables);
		AppraiseUtil.AjaxOutHtml(response, html);
		return null;
	}

	/**
	 * 生成月考核表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public ActionForward createTable(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		Map<String, Object> map=null;
		try {
			map = getCreateTableMap(appraiseResultBean, userInfo, ctx);
		} catch (Exception e) {
			return forwardInfoPage(mapping, request, "table"+typeName+"Nonentity");
		}
		if (map == null) {
			return forwardInfoPage(mapping, request, "appraiseRepeatTableError");
		}
		request.getSession().setAttribute("appraiseResultBean", appraiseResultBean);
		AppraiseUtil.PrintVM(response, map, AppraiseConstant.APPRAISE_GRADE_VM_PATH);
		return null;
	}
	/**
	 * 加载专项考核查询页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryAppraiseSpecialListForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List cons = contractorBO.getAllContractor(userInfo);
		request.getSession().setAttribute("cons", cons);
		request.getSession().removeAttribute("appraiseResults");
		return mapping.findForward("queryAppraiseSpecialListForm");
	}
	/**
	 * 加载专项考核统计页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryAppraiseSpecialStatForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List cons = contractorBO.getAllContractor(userInfo);
		request.getSession().setAttribute("cons", cons);
		request.getSession().removeAttribute("appraiseResults");
		return mapping.findForward("queryAppraiseSpecialStatForm");
	}
	
	@Override
	AppraiseDailyBO getAppraiseDailyBO(WebApplicationContext ctx) {
		AppraiseDailyBO appraiseDailyBO = (AppraiseDailyBO) ctx.getBean("appraiseDailySpecialBO");
		return appraiseDailyBO;
	}

	@Override
	Map<String, Object> getCreateTableMap(AppraiseResultBean appraiseResultBean, UserInfo userInfo,
			WebApplicationContext ctx) throws Exception {
		AppraiseTableBO appraiseTableBO = (AppraiseTableBO) ctx.getBean("appraiseTableBO");
		AppraiseDailyBO appraiseDailyBO = getAppraiseDailyBO(ctx);
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		AppraiseTable appraiseTable = appraiseTableBO.getTableById(appraiseResultBean.getTableId());
		Map<String, String> appraiseDailyNumByRule = appraiseDailyBO.getAppraiseDailyNumByRule(appraiseTable.getId(),
				appraiseResultBean, type);//查询日常考核结果需要传入考核表，考核日期，考核代维，标底包
		appraiseResultBean.setStartDate(DateUtil.DateToString(appraiseTable.getStartDate(),"yyyy-MM-dd"));
		appraiseResultBean.setEndDate(DateUtil.DateToString(appraiseTable.getEndDate(),"yyyy-MM-dd"));
		List<AppraiseResult> appraiseResults = appraiseBO.getAppraiseResultByBean(appraiseResultBean);
		//获得日常考核的数据。
		Map<String, Object> appraiseRuleResults = null;
		if (appraiseResults.size() > 0) {
			if(flag.equals(AppraiseConstant.FLAG_EDIT)){
			appraiseRuleResults = appraiseResults.get(0).getAppraiseRuleResultForMap();
			appraiseResultBean.setId(appraiseResults.get(0).getId());
			}else{
				return null;
			}
		} else {
			flag = AppraiseConstant.FLAG_IMPORT;
		}
		String contractorName = AppraiseUtil.GetContractorName(appraiseResultBean.getContractorId());
		String title = contractorName + appraiseResultBean.getStartDate() + "到" + appraiseResultBean.getEndDate()
				+ " 专项考核表";
		Map<String, Object> map = setAppraiseInformation(appraiseTable, appraiseDailyNumByRule, appraiseResultBean, userInfo, appraiseRuleResults);
		map.put("title", title);
		map.put("statDate", appraiseResultBean.getAppraiseTime());
		return map;
	}

	@Override
	AppraiseResultBean initViewAppraiseBean(AppraiseResult appraiseResult) {
		AppraiseResultBean appraiseResultBean = new AppraiseResultBean();
		appraiseResultBean.setContractorId(appraiseResult.getContractorId());
		appraiseResultBean.setStartDate(DateUtil.DateToTimeString(appraiseResult.getStartDate(), "yyyy-MM-dd"));
		appraiseResultBean.setEndDate(DateUtil.DateToTimeString(appraiseResult.getEndDate(), "yyyy-MM-dd"));
		return appraiseResultBean;
	}

	@Override
	void setAppraiseType() {
		appraiseType = AppraiseConstant.APPRAISE_SPECIAL;
	}

	@Override
	void setType() {
		type = AppraiseConstant.TYPE_SPECIAL;
	}

	@Override
	void setTypeName() {
		typeName = AppraiseConstant.TYPE_SPECIAL;

	}

	@Override
	AppraiseBO getAppraiseBO(WebApplicationContext ctx) {
		return (AppraiseBO) ctx.getBean("appraiseSpecialBO");
	}
}
