package com.cabletech.linepatrol.appraise.action;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.baseinfo.services.UserInfoBO;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.module.AppraiseConfirmResult;
import com.cabletech.linepatrol.appraise.module.AppraiseContent;
import com.cabletech.linepatrol.appraise.module.AppraiseItem;
import com.cabletech.linepatrol.appraise.module.AppraiseResult;
import com.cabletech.linepatrol.appraise.module.AppraiseRule;
import com.cabletech.linepatrol.appraise.module.AppraiseRuleResult;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
import com.cabletech.linepatrol.appraise.service.AppraiseBO;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyBO;
import com.cabletech.linepatrol.appraise.service.AppraiseRuleBO;
import com.cabletech.linepatrol.appraise.service.AppraiseTableBO;
import com.cabletech.linepatrol.appraise.util.AppraiseUtil;
import com.cabletech.linepatrol.commons.Constant;

public abstract class AppraiseAction extends BaseDispatchAction {
	static String flag = "";
	static String type = "";
	static String typeName = "";
	static String appraiseType = "";
	
	public void initArguments(){
		setType();
		setTypeName();
		setAppraiseType();
	}
	/**
	 * 生成考核表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createTable(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		initArguments();
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO=getAppraiseBO(ctx);
		List<AppraiseResult> appraiseResults = appraiseBO.getAppraiseResultByBean(appraiseResultBean);
		if(appraiseResults.size()>0){
			return forwardInfoPage(mapping, request, "appraise"+typeName+"RepeatError");
		}
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

	/**
	 * 生成考核表确认页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getRules(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseTableBO appraiseTableBO = (AppraiseTableBO) ctx.getBean("appraiseTableBO");
		AppraiseDailyBO appraiseDailyBO = getAppraiseDailyBO(ctx);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

		Map<String, Object> appraiseRuleResults = setAppraiseRuleResultIsMap(getResultInformation(request));
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) request.getSession().getAttribute(
				"appraiseResultBean");
		AppraiseTable appraiseTable = appraiseTableBO.get(appraiseResultBean.getTableId());
		//获得日常考核的数据。
		//查询日常考核结果需要传入考核表，考核日期，考核代维，标底包
		flag = AppraiseConstant.FLAG_SUBMIT;
		Map<String, String> appraiseDailyNumByRule = appraiseDailyBO.getAppraiseDailyNumByRule(appraiseTable.getId(),
				appraiseResultBean, type);
		AppraiseResult appraiseResult = initAppraiseResult(userInfo, getResultInformation(request), appraiseResultBean);
		request.getSession().setAttribute("appraiseResult", appraiseResult);
		request.getSession().setAttribute("appraiseResultBean", appraiseResultBean);
		Map<String, Object> map = setAppraiseInformation(appraiseTable, appraiseDailyNumByRule, appraiseResultBean, userInfo, appraiseRuleResults);
		map.put("title", "考核结果确认");
		AppraiseUtil.PrintVM(response, map, AppraiseConstant.APPRAISE_GRADE_VM_PATH);
		return null;
	}

	/**
	 * 查看考核依据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewExamBase(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) request.getSession().getAttribute(
				"appraiseResultBean");
		String ruleId = request.getParameter("ruleId");

		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseDailyBO appraiseDailyBO = getAppraiseDailyBO(ctx);
		AppraiseRuleBO appraiseRuleBO = (AppraiseRuleBO) ctx.getBean("appraiseRuleBO");
		AppraiseRule appraiseRule = appraiseRuleBO.getRulebyId(ruleId);
		Map<String, Object> baseInfo = setBaseInfo(appraiseResultBean, ruleId, appraiseDailyBO, appraiseRule);
		request.setAttribute("baseInfo", baseInfo);
		request.setAttribute("Constant", Constant.BUSINESSMODULE);
		return mapping.findForward("viewExamBase");
	}

	/**
	 * 考核查看
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewAppraiseMonth(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initArguments();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String resultId = request.getParameter("resultId");
		flag=request.getParameter("flag");
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		AppraiseTableBO appraiseTableBO = (AppraiseTableBO) ctx.getBean("appraiseTableBO");
		AppraiseDailyBO appraiseDailyBO = getAppraiseDailyBO(ctx);//日常考核
		AppraiseResult appraiseResult = appraiseBO.get(resultId);
		AppraiseResultBean appResultBean=new AppraiseResultBean();
		BeanUtil.copyProperties(appraiseResult, appResultBean);
		appResultBean.setAppraiseTime(DateUtil.DateToString(appraiseResult.getAppraiseTime(), "yyyy-MM"));
		appResultBean.setStartDate(DateUtil.DateToString(appraiseResult.getStartDate(), "yyyy-MM-dd"));
		appResultBean.setEndDate(DateUtil.DateToString(appraiseResult.getEndDate(), "yyyy-MM-dd"));
		AppraiseTable appraiseTable = appraiseTableBO.getTableById(appraiseResult.getTableId());
		//获得日常考核的数据。
		//查询日常考核结果需要传入考核表，考核日期，考核代维，标底包
		Map<String, String> appraiseDailyNumByRule = appraiseDailyBO.getAppraiseDailyNumByRule(appraiseTable.getId(),
				appResultBean, type);
		Map<String, Object> map = setAppraiseInformation(appraiseTable, appraiseDailyNumByRule, appResultBean, userInfo, appraiseResult.getAppraiseRuleResultForMap());
		map.put("title", "考核结果");
		AppraiseUtil.PrintVM(response, map, AppraiseConstant.APPRAISE_GRADE_VM_PATH);
		AppraiseResultBean appraiseResultBean = initViewAppraiseBean(appraiseResult);
		request.getSession().setAttribute("appraiseResultBean", appraiseResultBean);
		return null;

	}

	/**
	 * 设置考核依据所需的基本信息
	 * @param appraiseResultBean
	 * @param ruleId
	 * @param baseInfo
	 * @param appraiseDailyBO
	 * @param appraiseRule
	 */
	Map<String, Object> setBaseInfo(AppraiseResultBean appraiseResultBean, String ruleId,
			AppraiseDailyBO appraiseDailyBO, AppraiseRule appraiseRule) {
		Map<String, Object> baseInfo = new HashMap<String, Object>();
		AppraiseContent appraiseContent = appraiseRule.getAppraiseContent();
		AppraiseItem appraiseItem = appraiseContent.getAppraiseItem();
		baseInfo.put("item", appraiseItem.getItemName() + "（" + appraiseItem.getWeight() + "分）");
		baseInfo.put("content", appraiseContent.getContentDescription() + "（" + appraiseContent.getWeight() + "分）");
		baseInfo.put("rule", appraiseRule.getRuleDescription() + "（" + appraiseRule.getWeight() + "分）评分说明："
				+ appraiseRule.getGradeDescription());
		baseInfo.put("weight", appraiseRule.getWeight());
		List<Map<String, String>> appraiseDailyMarks = appraiseDailyBO.getDailyMarkByBean(appraiseResultBean, ruleId,
				type);
		baseInfo.put("appraiseDailyMarks", appraiseDailyMarks);
		return baseInfo;
	}

	/**
	 * 设置确认页面所需信息 包括appraiseTable,userInfo,contractorName,appraiseMonth,appraiseDailyNumByRule,flag,typeName,appraiseRuleResults
	 * @param appraiseTable 考核表
	 * @param appraiseDailyNumByRule 考核依据数
	 * @param appraiseRuleResults 考核结果的map
	 * @return
	 */
	Map<String, Object> setAppraiseInformation(AppraiseTable appraiseTable, Map<String, String> appraiseDailyNumByRule,
			AppraiseResultBean appraiseResultBean, UserInfo userInfo, Map<String, Object> appraiseRuleResults) throws Exception {
		String contractorName = AppraiseUtil.GetContractorName(appraiseResultBean.getContractorId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table", appraiseTable);
		map.put("userInfo", userInfo);
		map.put("contractorName", contractorName);
		map.put("appraiseMonth", DateUtil.DateToString(new Date(), "yyyy-MM-dd"));
		map.put("appraiseDailyNumByRule", appraiseDailyNumByRule);
		map.put("flag", flag);
		map.put("typeName", typeName);
		map.put("year", appraiseResultBean.getAppraiseTime());
		map.put("resultId",appraiseResultBean.getId());
		map.put("appraiseRuleResults", appraiseRuleResults);
		return map;
	}
	/**
	 * 保存考核结果
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveAppraiseRuleResult(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AppraiseResult appraiseResult = (AppraiseResult) request.getSession().getAttribute("appraiseResult");
		request.getSession().removeAttribute("appraiseResult");
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		appraiseBO.saveAppraiseMonth(appraiseResult);
		ContractorBO contractorBO = new ContractorBO();
		Contractor contractor = contractorBO.loadContractor(appraiseResult.getContractorId());
		if(!appraiseResult.getConfirmResult().equals(AppraiseConstant.STATE_WAIT_VERIFY)){
			return forwardInfoPage(mapping, request, "appraise"+typeName+"Success", contractor.getContractorName());
		}else{
			return forwardInfoPage(mapping, request, "editAppraise"+typeName+"Success", contractor.getContractorName());
		}
	}
	/**
	 * 初始化appraiseResult
	 * @param userInfo
	 * @param results
	 * @param rules
	 * @param remarks
	 * @param appraiseResultBean
	 * @throws ParseException
	 */
	AppraiseResult initAppraiseResult(UserInfo userInfo, Map<String, String[]> map,
			AppraiseResultBean appraiseResultBean) throws ParseException {
		AppraiseResult appraiseResult = new AppraiseResult();
		appraiseResult.setId(appraiseResultBean.getId());
		appraiseResult.setAppraiseDate(new Date());
		if(StringUtils.isNotBlank(appraiseResultBean.getConfirmResult())&&appraiseResultBean.getConfirmResult().equals(AppraiseConstant.STATE_WAIT_VERIFY)){
			appraiseResult.setAppraiser(appraiseResultBean.getAppraiser());
		}else{
			appraiseResult.setAppraiser(userInfo.getUserName());
		}
		setAppraiseRuleResult(map, appraiseResult);
		if(StringUtils.isNotBlank(appraiseResultBean.getAppraiseTime())&&appraiseResultBean.getAppraiseTime().split("-").length>1){
			appraiseResult.setAppraiseTime(DateUtil.StringToDate(appraiseResultBean.getAppraiseTime(), "yyyy-MM"));
		}else{
			appraiseResult.setAppraiseTime(DateUtil.StringToDate(appraiseResultBean.getAppraiseTime(), "yyyy"));
		}
		appraiseResult.setStartDate(DateUtil.StringToDate(appraiseResultBean.getStartDate(), "yyyy-MM-dd"));
		appraiseResult.setEndDate(DateUtil.StringToDate(appraiseResultBean.getEndDate(), "yyyy-MM-dd"));
		appraiseResult.setContractNO(appraiseResultBean.getContractNO());
		appraiseResult.setContractorId(appraiseResultBean.getContractorId());
		appraiseResult.setTableId(appraiseResultBean.getTableId());
		appraiseResult.setConfirmResult(appraiseResultBean.getConfirmResult());
		return appraiseResult;
	}


	/**
	 * 考核查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryAppraiseList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) form;
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		List<AppraiseResult> appraiseResults = appraiseBO.getAppraiseResultByBean(appraiseResultBean);
		request.getSession().setAttribute("contractor", contractorBO.getContractorForMap(userInfo));
//		request.getSession().setAttribute("appraiseResultBean", appraiseResultBean);
		request.getSession().setAttribute("appraiseResults", appraiseResults);
		return mapping.findForward("queryAppraiseListForm");
	}
	
	/**
	 * 考核统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryAppraiseStat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		List monthStat = appraiseBO.queryStat(appraiseResultBean, userInfo);
		request.setAttribute("stat", monthStat);
		return mapping.findForward("queryAppraiseStatForm");
	}
	/**
	 * 考核结果下发查询页面加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward querySendAppraiseForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initArguments();
		request.getSession().removeAttribute("appraiseResultBean");
		request.getSession().removeAttribute("appraiseResults");
		return mapping.findForward("querySendAppraiseForm");
	}
	/**
	 * 考核结果下发查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward querySendAppraise(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initArguments();
		UserInfoBO userInfoBO=new UserInfoBO();
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) form;
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		appraiseResultBean.setConfirmResult(AppraiseConstant.STATE_WAIT_SEND);
		List<AppraiseResult> appraiseResults = appraiseBO.getAppraiseResultByBean(appraiseResultBean);
		request.getSession().setAttribute("contractor", contractorBO.getContractorForMap(userInfo));
		request.getSession().setAttribute("appraiseResultBean", appraiseResultBean);
		request.getSession().setAttribute("appraiseResults", appraiseResults);
		request.getSession().setAttribute("userInfoMap", userInfoBO.loadAllUserIdAndName());
		return mapping.findForward("querySendAppraiseForm");
	}
	
	/**
	 * 考核结果下发
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward sendAppraise(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) form;
		String[] check=request.getParameterValues("check");
		if(check!=null&&check.length>0){
			appraiseResultBean.setIdsByStrs(check);
		}
		String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			appraiseResultBean.setIds("'"+id+"'");
		}
		appraiseResultBean.setConfirmResult(AppraiseConstant.STATE_WAIT_VERIFY);
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		appraiseBO.updateResultStateAndMark(appraiseResultBean);
		request.getSession().removeAttribute("appraiseResults");
		request.getSession().removeAttribute("appraiseResultBean");
		return forwardInfoPage(mapping, request, "send"+typeName+"AppraiseSucess");
	}
	
	/**
	 * 考核结果确认列表页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward verifyAppraiseList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initArguments();
		UserInfoBO userInfoBO=new UserInfoBO();
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) form;
		appraiseResultBean.setType(request.getParameter("type"));
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		appraiseResultBean.setConfirmResult("'"+AppraiseConstant.STATE_WAIT_VERIFY+"'");
		appraiseResultBean.setContractorId(userInfo.getDeptID());
		List<AppraiseResult> appraiseResults = appraiseBO.getAppraiseResultByBean(appraiseResultBean);
		request.getSession().setAttribute("contractor", contractorBO.getContractorForMap(userInfo));
		request.getSession().setAttribute("appraiseResultBean", appraiseResultBean);
		request.getSession().setAttribute("appraiseResults", appraiseResults);
		request.getSession().setAttribute("userInfoMap", userInfoBO.loadAllUserIdAndName());
		return mapping.findForward("verifyAppraiseList");
	}
	/**
	 * 考核结果确认
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward verifyAppraise(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		AppraiseResultBean appraiseResultBean=(AppraiseResultBean)form;
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		appraiseResultBean.setIds("'"+appraiseResultBean.getId()+"'");
		appraiseBO.verifyAppraiseResult(appraiseResultBean,userInfo);
		request.getSession().removeAttribute("appraiseResults");
		request.getSession().removeAttribute("appraiseResultBean");
		return forwardInfoPage(mapping, request, "verifyAppraise"+typeName+"Success");
	}
	/**
	 * 代维确认结果查询页面加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryVerifyResultAppraiseForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response){
		initArguments();
		request.getSession().removeAttribute("appraiseResultBean");
		request.getSession().removeAttribute("appraiseResults");
		return mapping.findForward("verifyResultAppraise");
	}
	/**
	 * 代维确认结果查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryVerifyResultAppraise(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception{
		initArguments();
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) form;
		AppraiseResultBean appResultBean=new AppraiseResultBean();
		BeanUtil.copyProperties(appraiseResultBean, appResultBean);
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		if(StringUtils.isNotBlank(appraiseResultBean.getConfirmResult())){
			appResultBean.setConfirmResult("'"+appraiseResultBean.getConfirmResult()+"'");
		}else{
			appResultBean.setConfirmResult("'"+AppraiseConstant.STATE_WAIT_VERIFY+"','"+AppraiseConstant.STATE_VERIFY_NOT_PASS+"','"+AppraiseConstant.STATE_VERIFY_PASS+"'");
		}
		List<AppraiseResult> appraiseResults = appraiseBO.getAppraiseResultByBean(appResultBean);
		Map<String,Object> appConfirmResultMap=appraiseBO.getAllLastConfirmMapByResultBean();
		request.getSession().setAttribute("contractor", contractorBO.getContractorForMap(userInfo));
		request.getSession().setAttribute("appraiseResultBean", appraiseResultBean);
		request.getSession().setAttribute("appraiseResults", appraiseResults);
		request.getSession().setAttribute("appConfirmResultMap", appConfirmResultMap);
		return mapping.findForward("verifyResultAppraise");
	}
	
	/**
	 * 编辑考核表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editAppraise(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initArguments();
		flag=AppraiseConstant.FLAG_EDIT;
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO=getAppraiseBO(ctx);
		String resultId=request.getParameter("resultId");
		AppraiseResult appraiseResult=appraiseBO.get(resultId);
		AppraiseResultBean appraiseResultBean=new AppraiseResultBean();
		BeanUtil.copyProperties(appraiseResult, appraiseResultBean);
		if(type.equals(AppraiseConstant.TYPE_YEAREND)){
			appraiseResultBean.setAppraiseTime(DateUtil.DateToString(appraiseResult.getAppraiseTime(), "yyyy"));
		}else{
			appraiseResultBean.setAppraiseTime(DateUtil.DateToString(appraiseResult.getAppraiseTime(), "yyyy-MM"));
		}
		appraiseResultBean.setStartDate(DateUtil.DateToString(appraiseResult.getStartDate(), "yyyy-MM-dd"));
		appraiseResultBean.setEndDate(DateUtil.DateToString(appraiseResult.getEndDate(), "yyyy-MM-dd"));
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		Map<String, Object> map = getCreateTableMap(appraiseResultBean, userInfo, ctx);
		appraiseResultBean.setConfirmResult(AppraiseConstant.STATE_WAIT_VERIFY);
		request.getSession().setAttribute("appraiseResultBean", appraiseResultBean);
		AppraiseUtil.PrintVM(response, map, AppraiseConstant.APPRAISE_GRADE_VM_PATH);
		return null;
	}
	/**
	 * 代维查询确认结果
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryVerifyResult(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		initArguments();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) form;
		appraiseResultBean.setContractorId(userInfo.getDeptID());
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		appraiseResultBean.setConfirmResult("'"+AppraiseConstant.STATE_VERIFY_PASS+"','"+AppraiseConstant.STATE_VERIFY_NOT_PASS+"'");
		List<AppraiseResult> appraiseResults = appraiseBO.getAppraiseResultByBean(appraiseResultBean);
		request.getSession().setAttribute("appraiseResults", appraiseResults);
		return mapping.findForward("queryVerifyResultAppraiseForm");
	}
	/**
	 * 代维查询确认结果页面加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryVerifyResultForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		request.getSession().removeAttribute("appraiseResults");
		return mapping.findForward("queryVerifyResultAppraiseForm");
	}
	/**
	 * 导出考核结果
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTable(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		String id = request.getParameter("resultId");
		appraiseBO.exprotTable(id, response);
		return null;
	}
	/**
	 * 待办工作
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward waitToDoWork(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response){
		ContractorBO contractorBO=new ContractorBO();
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		initArguments();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		AppraiseResultBean appraiseResultBean=new AppraiseResultBean();
		appraiseResultBean.setType(appraiseType);
		if(userInfo.getDeptype().equals("1")){
			appraiseResultBean.setConfirmResult("'"+AppraiseConstant.STATE_WAIT_SEND+"','"+AppraiseConstant.STATE_VERIFY_NOT_PASS+"'");
		}else{
			appraiseResultBean.setConfirmResult("'"+AppraiseConstant.STATE_WAIT_VERIFY+"'");
			appraiseResultBean.setContractorId(userInfo.getDeptID());
		}
		List<AppraiseResult> appraiseResults=appraiseBO.getAppraiseResultByBean(appraiseResultBean);
		Map confirmMap=appraiseBO.getAllLastConfirmMapByResultBean();
		request.getSession().setAttribute("appraiseResults", appraiseResults);
		request.getSession().setAttribute("appConfirmResultMap", confirmMap);
		request.getSession().setAttribute("contractor", contractorBO.getContractorForMap(userInfo));
		return mapping.findForward("waitToDoWork");
	}
	/**
	 * 查看考核确认历史
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewVerifyHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response){
		String resultId=request.getParameter("resultId");
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseBO appraiseBO = getAppraiseBO(ctx);
		List<AppraiseConfirmResult> appraiseConfirmResults=appraiseBO.queryConfirmResultByResultId(resultId);
		request.getSession().setAttribute("confirmResults", appraiseConfirmResults);
		return mapping.findForward("viewVerifyHistory");
	}
	/**
	 * 将考核信息存储到map中
	 * @param results
	 * @param rules
	 * @param remarks
	 * @return
	 */
	public Map<String, Object> setAppraiseRuleResultIsMap(Map<String, String[]> map) {
		float result = 0;
		Map<String, Object> appraiseRuleResults = new HashMap<String, Object>();
		for (int i = 0; i < map.get("results").length; i++) {
			AppraiseRuleResult appraiseRuleResult = new AppraiseRuleResult();
			initAppraiseRuleResult(map, i, appraiseRuleResult);
			result = getResult(map, result, i);
			appraiseRuleResults.put(map.get("rules")[i], appraiseRuleResult);

		}
		appraiseRuleResults.put("result", result);
		return appraiseRuleResults;
	}

	/**
	 * 将考核信息存储到list中
	 * @param results
	 * @param rules
	 * @param remarks
	 * @return
	 */
	public void setAppraiseRuleResult(Map<String, String[]> map, AppraiseResult appraiseResult) {
		float result = 0;
		for (int i = 0; i < map.get("results").length; i++) {
			AppraiseRuleResult appraiseRuleResult = new AppraiseRuleResult();
			initAppraiseRuleResult(map, i, appraiseRuleResult);
			result = getResult(map, result, i);
			appraiseRuleResult.setAppraiseResult(appraiseResult);
			appraiseResult.addAppraiseRuleResult(appraiseRuleResult);

		}
		appraiseResult.setResult(result);
	}

	/**
	 * 得到考核结果
	 * @param map
	 * @param result
	 * @param i
	 * @return
	 */
	float getResult(Map<String, String[]> map, float result, int i) {
		result += Float.valueOf(map.get("results")[i]);
		return result;
	}

	/**
	 * 初始化appraiseRuleResult中的ruleId，result，remark
	 * @param map
	 * @param i
	 * @param appraiseRuleResult
	 */
	void initAppraiseRuleResult(Map<String, String[]> map, int i, AppraiseRuleResult appraiseRuleResult) {
		appraiseRuleResult.setRuleId(map.get("rules")[i]);
		appraiseRuleResult.setResult(Float.valueOf(map.get("results")[i]));
		appraiseRuleResult.setRemark(map.get("remarks")[i]);
	}

	/**
	 * 从页面获得考核结果信息
	 * @param request
	 * @return
	 */
	Map<String, String[]> getResultInformation(HttpServletRequest request) {
		String[] results = request.getParameterValues("results");
		String[] rules = request.getParameterValues("rules");
		String[] remarks = request.getParameterValues("remarks");
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("results", results);
		map.put("rules", rules);
		map.put("remarks", remarks);
		return map;
	}
	
	abstract AppraiseBO getAppraiseBO(WebApplicationContext ctx);

	abstract AppraiseResultBean initViewAppraiseBean(AppraiseResult appraiseResult);
	/**
	 * 将模板所需要的信息添加到map中，包括title，contractNO，statDate和其他特有信息，同时调用setAppraiseInformation添加其他信息，还要再该方法中修改flag的值
	 * @param appraiseResultBean
	 * @param userInfo
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	abstract Map<String, Object> getCreateTableMap(AppraiseResultBean appraiseResultBean, UserInfo userInfo,
			WebApplicationContext ctx) throws Exception;

	abstract AppraiseDailyBO getAppraiseDailyBO(WebApplicationContext ctx);
	/**
	 * 设置type的值，例如Special
	 */
	abstract void setType();
	/**
	 * 设置typeName的值，例如Special
	 */
	abstract void setTypeName();
	/**
	 * 设置appraiseType的值，例如2
	 */
	abstract void setAppraiseType();
}
