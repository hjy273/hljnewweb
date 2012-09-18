package com.cabletech.linepatrol.appraise.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.module.AppraiseResult;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
import com.cabletech.linepatrol.appraise.service.AppraiseBO;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyBO;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyMarkBO;
import com.cabletech.linepatrol.appraise.service.AppraiseTableBO;
import com.cabletech.linepatrol.appraise.util.AppraiseUtil;

public class AppraiseYearEndAction extends AppraiseAction {
	
	public ActionForward createAppraiseYearEndTableForm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List cons = contractorBO.getAllContractor(userInfo);
		String lastYear=DateUtil.getLastYear();
		request.getSession().setAttribute("cons", cons);
		request.setAttribute("lastYear", lastYear);
		return mapping.findForward("createAppraiseYearEndTableForm");
	}
	@Override
	public ActionForward createTable(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		initArguments();
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) form;
		List<AppraiseResult> appraiseResults=getAppraiseBO(ctx).getAppraiseResultByBean(appraiseResultBean);
		if(appraiseResults.size()>0){
			return forwardInfoPage(mapping, request, "appraiseYearEndRepeatError");
		}
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		
		Map<String, Object> map;
		try {
			map = getCreateTableMap(appraiseResultBean, userInfo, ctx);
		} catch (Exception e) {
			return forwardInfoPage(mapping, request, "table"+typeName+"Nonentity");
		}
		request.getSession().setAttribute("appraiseResultBean", appraiseResultBean);
		AppraiseUtil.PrintVM(response, map, AppraiseConstant.APPRAISE_GRADE_VM_PATH);
		return null;
	}
	
	public ActionForward queryAppraiseYearEndListForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List cons = contractorBO.getAllContractor(userInfo);
		request.getSession().setAttribute("cons", cons);
		request.getSession().removeAttribute("appraiseResults");
		return mapping.findForward("queryAppraiseYearEndListForm");
	} 
	
	public ActionForward queryAppraiseYearEndStatForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute("appraiseResultBean");
		return mapping.findForward("queryAppraiseYearEndStatForm");
	}

	@Override
	AppraiseBO getAppraiseBO(WebApplicationContext ctx) {
		// TODO Auto-generated method stub
		return (AppraiseBO)ctx.getBean("appraiseYearEndBO");
	}

	@Override
	AppraiseDailyBO getAppraiseDailyBO(WebApplicationContext ctx) {
		// TODO Auto-generated method stub
		return (AppraiseDailyBO)ctx.getBean("appraiseDailyYearEndBO");
	}

	@Override
	Map<String, Object> getCreateTableMap(AppraiseResultBean appraiseResultBean, UserInfo userInfo,
			WebApplicationContext ctx) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		AppraiseTableBO appraiseTableBO = (AppraiseTableBO) ctx.getBean("appraiseTableBO");
		AppraiseDailyMarkBO appraiseDailyMarkBO=(AppraiseDailyMarkBO)ctx.getBean("appraiseDailyMarkBO");
		AppraiseBO appraiseBO=getAppraiseBO(ctx);
		AppraiseDailyBO appraiseDailyBO=getAppraiseDailyBO(ctx);
		AppraiseTable appraiseTable=appraiseTableBO.getTableByYear(appraiseResultBean.getAppraiseTime(), appraiseType).get(0);
		appraiseResultBean.setTableId(appraiseTable.getId());
		ContractorBO contractorBO=new ContractorBO();
		String contractorName=contractorBO.getContractorNameByContractorById(appraiseResultBean.getContractorId());
		String title=appraiseResultBean.getAppraiseTime()+" "+contractorName+"年终检查汇总表";
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
		Map<String,String> avgMarkDeductions=appraiseDailyMarkBO.getAppraiseDailyMarkDeductionByBean("", appraiseResultBean, typeName);
		Map<String,String> appraiseDailyNumByRule=appraiseDailyBO.getAppraiseDailyNumByRule(appraiseTable.getId(), appraiseResultBean, typeName);
		map=setAppraiseInformation(appraiseTable, appraiseDailyNumByRule, appraiseResultBean, userInfo, appraiseRuleResults);
		map.put("title", title);
		map.put("contractNO", appraiseResultBean.getContractNO());
		map.put("statDate", appraiseResultBean.getAppraiseTime());
		map.put("avgMarkDeductions", avgMarkDeductions);
		return map;
	}

	@Override
	AppraiseResultBean initViewAppraiseBean(AppraiseResult appraiseResult) {
		AppraiseResultBean appraiseResultBean = new AppraiseResultBean();
		appraiseResultBean.setContractorId(appraiseResult.getContractorId());
		appraiseResultBean.setContractNO(appraiseResult.getContractNO());
		return appraiseResultBean;
	}

	@Override
	void setAppraiseType() {
		// TODO Auto-generated method stub
		this.appraiseType=AppraiseConstant.APPRAISE_YEAREND;
	}

	@Override
	void setType() {
		// TODO Auto-generated method stub
		this.type=AppraiseConstant.TYPE_YEAREND;
	}

	@Override
	void setTypeName() {
		// TODO Auto-generated method stub
		this.typeName=AppraiseConstant.TYPE_YEAREND;
	}

}
