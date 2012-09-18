package com.cabletech.linepatrol.appraise.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contract;
import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractBO;
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

public class AppraiseMonthAction extends AppraiseAction {
	/**
	 * 加载月考核表生成页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createMonthTableForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initArguments();
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List<Contractor> cons = contractorBO.getAllContractor(userInfo);
		request.setAttribute("minDate", DateUtil.getLastYear());
		request.getSession().setAttribute("cons", cons);
		return mapping.findForward("createAppraiseMonthTableForm");
	}

	/**
	 * 动态获得标底包号
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getContractNO(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ContractBO contractBO = (ContractBO) ctx.getBean("contractBO");
		String contractorId = request.getParameter("contractorid");
		String year = request.getParameter("appraiseTime").split("-")[0];
		Contract contract = contractBO.getContract(contractorId, year);
		String html = "";
		if (contract != null) {
			html = contract.getContractNo();
		}
		AppraiseUtil.AjaxOutHtml(response, html);
		return null;
	}

	@Override
	public Map<String, Object> getCreateTableMap(AppraiseResultBean appraiseResultBean, UserInfo userInfo,
			WebApplicationContext ctx) throws Exception {
		AppraiseTableBO appraiseTableBO = (AppraiseTableBO) ctx.getBean("appraiseTableBO");
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		AppraiseDailyBO appraiseDailyBO = getAppraiseDailyBO(ctx);

		String year = appraiseResultBean.getAppraiseTime().split("-")[0];
		AppraiseTable appraiseTable = appraiseTableBO.getTableByYear(year, appraiseType).get(0);
		appraiseResultBean.setTableId(appraiseTable.getId());

		Map<String, String> appraiseDailyNumByRule = appraiseDailyBO.getAppraiseDailyNumByRule(appraiseTable.getId(),
				appraiseResultBean, type);//查询日常考核结果需要传入考核表，考核日期，考核代维，标底包
		Map<String, Object> map = setMonthTableInformation(appraiseBO, appraiseTable, appraiseResultBean, userInfo,
				appraiseDailyNumByRule);
		return map;
	}

	@Override
	public AppraiseDailyBO getAppraiseDailyBO(WebApplicationContext ctx) {
		AppraiseDailyBO appraiseDailyBO = (AppraiseDailyBO) ctx.getBean("appraiseDailyDailyBO");//日常考核
		return appraiseDailyBO;
	}

	/**
	 * 加载月考核查询页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryAppraiseMonthListForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List cons = contractorBO.getAllContractor(userInfo);
		request.getSession().setAttribute("cons", cons);
		request.getSession().removeAttribute("appraiseResults");
		return mapping.findForward("queryAppraiseMonthListForm");
	}

	@Override
	AppraiseResultBean initViewAppraiseBean(AppraiseResult appraiseResult) {
		AppraiseResultBean appraiseResultBean = new AppraiseResultBean();
		appraiseResultBean.setContractorId(appraiseResult.getContractorId());
		appraiseResultBean.setAppraiseTime(DateUtil.DateToString(appraiseResult.getAppraiseTime(), "yyyy-MM"));
		return appraiseResultBean;
	}

	/**
	 * 月考核统计页面加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryAppraiseMonthStatForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List cons = contractorBO.getAllContractor(userInfo);
		request.getSession().setAttribute("cons", cons);
		return mapping.findForward("queryAppraiseMonthStatForm");
	}
	

	/**
	 * 月考核表所需信息
	 * @param appraiseTable 
	 * @param appraiseResultBean
	 * @param monthlyStats 代维巡检组巡检情况
	 * @param monthStatAll 巡检情况总况
	 * @param userInfo
	 * @param appraiseDailyNumByRule 考核依据数
	 * @return
	 */
	private Map<String, Object> setMonthTableInformation(AppraiseBO appraiseBO, AppraiseTable appraiseTable,
			AppraiseResultBean appraiseResultBean, UserInfo userInfo, Map<String, String> appraiseDailyNumByRule)
			throws Exception {
		List monthlyStats = appraiseBO.queryForAppraiseMonth(appraiseResultBean.getContractorId(), appraiseResultBean
				.getAppraiseTime());
		List monthStatAll = appraiseBO.queryForAppraiseMonthAll(appraiseResultBean.getContractorId(),
				appraiseResultBean.getAppraiseTime());
		List<AppraiseResult> appraiseResults = appraiseBO.getAppraiseResultByBean(appraiseResultBean);
		//获得日常考核的数据。
		Map<String, Object> appraiseRuleResults = null;
		if (appraiseResults.size() > 0) {
			flag = AppraiseConstant.FLAG_EDIT;
			appraiseRuleResults = appraiseResults.get(0).getAppraiseRuleResultForMap();
			appraiseResultBean.setId(appraiseResults.get(0).getId());
		} else {
			flag = AppraiseConstant.FLAG_IMPORT;
		}
		String contractorName = AppraiseUtil.GetContractorName(appraiseResultBean.getContractorId());
		String contractNO = appraiseResultBean.getContractNO();
		String title = contractorName + appraiseResultBean.getAppraiseTime() + "月" + contractNO + "包  月考核表";
		Map<String, Object> map = setAppraiseInformation(appraiseTable, appraiseDailyNumByRule, appraiseResultBean, userInfo, appraiseRuleResults);
		map.put("title", title);
		map.put("contractNO", contractNO);
		map.put("statDate", appraiseResultBean.getAppraiseTime());
		map.put("monthlyStats", monthlyStats);
		map.put("monthStatAlls", monthStatAll);
		return map;
	}

	@Override
	void setType() {
		type = AppraiseConstant.TYPE_DAILY;
	}

	@Override
	void setTypeName() {
		typeName = AppraiseConstant.TYPE_MONTH;
	}

	@Override
	void setAppraiseType() {
		appraiseType = AppraiseConstant.APPRAISE_MONTH;
	}

	@Override
	AppraiseBO getAppraiseBO(WebApplicationContext ctx) {
		AppraiseBO appraiseMonthBO = (AppraiseBO) ctx.getBean("appraiseMonthBO");
		return appraiseMonthBO;
	}
}
