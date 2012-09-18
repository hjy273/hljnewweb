package com.cabletech.linepatrol.appraise.action;

import java.util.Date;
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
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.beans.AppraiseYearResultBean;
import com.cabletech.linepatrol.appraise.module.AppraiseConfirmResult;
import com.cabletech.linepatrol.appraise.module.AppraiseYearResult;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;
import com.cabletech.linepatrol.appraise.service.AppraiseMonthBO;
import com.cabletech.linepatrol.appraise.service.AppraiseYearBO;
import com.cabletech.linepatrol.appraise.service.AppraiseYearResultBO;
import com.cabletech.linepatrol.trouble.module.TroubleNormGuide;
import com.cabletech.linepatrol.trouble.services.TroubleQuotaBO;

public class AppraiseYearAction extends BaseDispatchAction {
	/**
	 * 年度考核生成页面加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createAppraiseYearForm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
		ContractorBO contractorBO=new ContractorBO();
		List<Contractor> cons=contractorBO.getAllContractor(userInfo);
		request.setAttribute("cons", cons);
		request.getSession().removeAttribute("appResultBean");
		return mapping.findForward("createAppraiseYearForm");
	}
	/**
	 * 生成年度考核页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createAppraiseYear(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		WebApplicationContext ctx=getWebApplicationContext();
		AppraiseResultBean appraiseResultBean=(AppraiseResultBean)form;
		AppraiseYearResultBO appraiseYearResultBO=(AppraiseYearResultBO)ctx.getBean("appraiseYearResultBO");
		List<AppraiseYearResult> appraiseYearResults=appraiseYearResultBO.getResultByAppraiseResultBean(appraiseResultBean);
		if(appraiseYearResults.size()>0){
			return forwardInfoPage(mapping, request, "createAppraiseYearRepeatError");
		}
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
		appraiseResultBean.setAppraiser(userInfo.getUserName());
		ContractorBO contractorBO=new ContractorBO();
		AppraiseYearBO appraiseYearBO=(AppraiseYearBO)ctx.getBean("appraiseYearBO");
		Map<String,Object> results=appraiseYearBO.getYearResultByBean(appraiseResultBean);
		String contractorName=contractorBO.getContractorNameByContractorById(appraiseResultBean.getContractorId());
		request.setAttribute("results", results);
		request.getSession().setAttribute("appResultBean", appraiseResultBean);
		request.setAttribute("contractorName",contractorName);
		request.setAttribute("appraiser",userInfo.getUserName());
		request.setAttribute("appraiseDate", DateUtil.getNowDateString("yyyy-MM-dd"));
		return mapping.findForward("createAppraiseYear");
	}
	/**
	 * 查看考核依据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward appraiseYearViewBase(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
		ContractorBO contractorBO=new ContractorBO();
		Map cons=contractorBO.getContractorForMap(userInfo);
		WebApplicationContext ctx=getWebApplicationContext();
		AppraiseMonthBO appraiseMonthBO=(AppraiseMonthBO)ctx.getBean("appraiseMonthBO");
		AppraiseResultBean appraiseResultBean=(AppraiseResultBean) request.getSession().getAttribute("appResultBean");
		String type=request.getParameter("type");
		List<Map> appraiseResults=appraiseMonthBO.getApprasieResultYear(appraiseResultBean, type);
		Float totleResult=appraiseMonthBO.getTotleResult(appraiseResults);
		appraiseMonthBO.setAppraiseTimeFormat(appraiseResults, type);
		request.setAttribute("avgResult", totleResult/appraiseResults.size());
		request.setAttribute("apprasieResults", appraiseResults);
		request.setAttribute("type", type);
		request.setAttribute("cons", cons);
		return mapping.findForward("appraiseYearViewBase");
	}
	/**
	 * 保存年度考核
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveAppraiseYear(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		WebApplicationContext ctx=getWebApplicationContext();
		AppraiseYearResultBO appraiseYearResultBO=(AppraiseYearResultBO)ctx.getBean("appraiseYearResultBO");
		AppraiseYearResultBean appraiseYearResultBean=(AppraiseYearResultBean)form;
		AppraiseYearResult appraiseYearResult=new AppraiseYearResult();
		BeanUtil.copyProperties(appraiseYearResultBean,appraiseYearResult);
		appraiseYearResult.setAppraiseDate(new Date());
		appraiseYearResultBO.save(appraiseYearResult);
		return forwardInfoPage(mapping, request, "createAppraiseYearSuccess");
	}
	/**
	 * 查询年度考核页面加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryAppraiseYearForm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
		ContractorBO contractorBO=new ContractorBO();
		List<Contractor> cons=contractorBO.getAllContractor(userInfo);
		request.getSession().setAttribute("cons", cons);
		request.getSession().removeAttribute("appraiseYearResults");
		request.getSession().removeAttribute("appResultBean");
		return mapping.findForward("queryAppraiseYearForm");
	}
	/**
	 * 查询年度考核
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryAppraiseYear(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx=getWebApplicationContext();
		AppraiseResultBean appraiseResultBean=(AppraiseResultBean)form;
		AppraiseYearResultBO appraiseYearResultBO=(AppraiseYearResultBO)ctx.getBean("appraiseYearResultBO");
		List<AppraiseYearResult> appraiseYearResults=appraiseYearResultBO.getResultByAppraiseResultBean(appraiseResultBean);
		request.getSession().setAttribute("appraiseYearResults", appraiseYearResults);
		ContractorBO contractorBO=new ContractorBO();
		Map cons=contractorBO.getContractorForMap(userInfo);
		request.setAttribute("consMap", cons);
		return mapping.findForward("queryAppraiseYearForm");
	} 
	/**
	 * 查看年度考核
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewAppraiseYear(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		WebApplicationContext ctx=getWebApplicationContext();
		AppraiseYearResultBO appraiseYearResultBO=(AppraiseYearResultBO)ctx.getBean("appraiseYearResultBO");
		String id=request.getParameter("id");
		String flag=request.getParameter("flag");
		AppraiseYearResult appraiseYearResult=appraiseYearResultBO.get(id);
		ContractorBO contractorBO=new ContractorBO();
		String contractorName=contractorBO.getContractorNameByContractorById(appraiseYearResult.getContractorId());
		AppraiseResultBean appraiseResultBean=new AppraiseResultBean();
		appraiseResultBean.setAppraiseTime(appraiseYearResult.getYear());
		appraiseResultBean.setContractorId(appraiseYearResult.getContractorId());
		appraiseResultBean.setContractNO(appraiseYearResult.getContractNO());
		appraiseResultBean.setId(appraiseYearResult.getId());
		request.getSession().setAttribute("appResultBean", appraiseResultBean);
		request.setAttribute("appraiseYearResult", appraiseYearResult);
		request.setAttribute("contractorName", contractorName);
		request.setAttribute("appraiseDate",DateUtil.getNowDateString("yyyy-MM-dd"));
		request.setAttribute("flag", flag);
		if(flag!=null&&flag.equals("edit")){
			return mapping.findForward("editAppraiseYear");
		}
		return mapping.findForward("viewAppraiseYear");
	}
	public ActionForward queryAppraiseYearStatForm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		return mapping.findForward("queryAppraiseYearStatForm");
	}
	/**
	 * 查询统计年度考核
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryAppraiseYearStat(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		WebApplicationContext ctx=getWebApplicationContext();
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
		AppraiseResultBean appraiseResultBean=(AppraiseResultBean)form;
		AppraiseYearResultBO appraiseYearResultBO=(AppraiseYearResultBO)ctx.getBean("appraiseYearResultBO");
		List<Map> appraiseYearResultStat=appraiseYearResultBO.queryYearResultStatByAppraiseResultBean(appraiseResultBean);
		request.setAttribute("appraiseYearResultStat", appraiseYearResultStat);
		ContractorBO contractorBO=new ContractorBO();
		Map cons=contractorBO.getContractorForMap(userInfo);
		request.setAttribute("consMap", cons);
		return mapping.findForward("queryAppraiseYearStatForm");
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
		request.getSession().removeAttribute("appraiseYearResults");
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
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx=getWebApplicationContext();
		AppraiseResultBean appraiseResultBean=(AppraiseResultBean)form;
		appraiseResultBean.setConfirmResult(AppraiseConstant.STATE_WAIT_SEND);
		AppraiseYearResultBO appraiseYearResultBO=(AppraiseYearResultBO)ctx.getBean("appraiseYearResultBO");
		List<AppraiseYearResult> appraiseYearResults=appraiseYearResultBO.getResultByAppraiseResultBean(appraiseResultBean);
		request.getSession().setAttribute("appraiseYearResults", appraiseYearResults);
		ContractorBO contractorBO=new ContractorBO();
		Map cons=contractorBO.getContractorForMap(userInfo);
		request.setAttribute("consMap", cons);
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
		String[] check=request.getParameterValues("check");
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) form;
		if(check!=null&&check.length>0){
			appraiseResultBean.setIdsByStrs(check);
		}
		String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			appraiseResultBean.setIds("'"+id+"'");
		}
		appraiseResultBean.setConfirmResult(AppraiseConstant.STATE_WAIT_VERIFY);
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseYearResultBO appraiseYearResultBO=(AppraiseYearResultBO)ctx.getBean("appraiseYearResultBO");
		appraiseYearResultBO.updateResultConfirmResult(appraiseResultBean);
		request.getSession().removeAttribute("appraiseYearResults");
		request.getSession().removeAttribute("appResultBean");
		return forwardInfoPage(mapping, request, "sendAppraiseYearSucess");
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
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) form;
		appraiseResultBean.setType(request.getParameter("type"));
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseYearResultBO appraiseYearResultBO=(AppraiseYearResultBO)ctx.getBean("appraiseYearResultBO");
		appraiseResultBean.setConfirmResult(AppraiseConstant.STATE_WAIT_VERIFY);
		appraiseResultBean.setContractorId(userInfo.getDeptID());
		List<AppraiseYearResult> appraiseYearResults=appraiseYearResultBO.getResultByAppraiseResultBean(appraiseResultBean);
		request.getSession().setAttribute("contractor", contractorBO.getContractorForMap(userInfo));
		request.getSession().setAttribute("appResultBean", appraiseResultBean);
		request.getSession().setAttribute("appraiseYearResults", appraiseYearResults);
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
		AppraiseYearResultBO appraiseYearResultBO=(AppraiseYearResultBO)ctx.getBean("appraiseYearResultBO");
		appraiseResultBean.setIds("'"+appraiseResultBean.getId()+"'");
		appraiseYearResultBO.verifyAppraiseResult(appraiseResultBean,userInfo);
		request.getSession().removeAttribute("appraiseResults");
		request.getSession().removeAttribute("appResultBean");
		return forwardInfoPage(mapping, request, "verifyAppraiseYearSuccess");
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
		request.getSession().removeAttribute("appResultBean");
		request.getSession().removeAttribute("appraiseYearResults");
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
	public ActionForward queryVerifyResultAppraise(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response){
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) form;
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseYearResultBO appraiseYearResultBO=(AppraiseYearResultBO)ctx.getBean("appraiseYearResultBO");
		if(StringUtils.isNotBlank(appraiseResultBean.getConfirmResult())){
			appraiseResultBean.setConfirmResult("'"+appraiseResultBean.getConfirmResult()+"'");
		}else{
			appraiseResultBean.setConfirmResult("'"+AppraiseConstant.STATE_WAIT_VERIFY+"','"+AppraiseConstant.STATE_VERIFY_NOT_PASS+"','"+AppraiseConstant.STATE_VERIFY_PASS+"'");
		}
		List<AppraiseYearResult> appraiseYearResults = appraiseYearResultBO.getResultByAppraiseResultBean(appraiseResultBean);
		AppraiseMonthBO appraiseMonthBO=(AppraiseMonthBO)ctx.getBean("appraiseMonthBO");
		Map<String,Object> appConfirmResultMap=appraiseMonthBO.getAllLastConfirmMapByResultBean();
		request.getSession().setAttribute("contractor", contractorBO.getContractorForMap(userInfo));
		request.getSession().setAttribute("appResultBean", appraiseResultBean);
		request.getSession().setAttribute("appraiseYearResults", appraiseYearResults);
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
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseYearResultBO appraiseYearResultBO=(AppraiseYearResultBO)ctx.getBean("appraiseYearResultBO");
		AppraiseYearResultBean appraiseYearResultBean=(AppraiseYearResultBean)form;
		AppraiseYearResult appraiseYearResult=new AppraiseYearResult();
		BeanUtil.copyProperties(appraiseYearResultBean,appraiseYearResult);
		appraiseYearResultBO.save(appraiseYearResult);
		request.getSession().removeAttribute("appResultBean");
		return forwardInfoPage(mapping, request, "eidtAppraiseYearSuccess");
	}
	/**
	 * 故障指标详细信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statYearQuota(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppraiseResultBean appraiseResultBean=(AppraiseResultBean)request.getSession().getAttribute("appResultBean");
		ContractorBO contractorBO=new ContractorBO();
		String contractorName=contractorBO.getContractorNameByContractorById(appraiseResultBean.getContractorId());
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		String year = request.getParameter("year");
		TroubleNormGuide normGuide = bo.getQuotaByType("1");
		Map yearquotaNorm = bo.statYearTroubleMonthQuotas("1", year,normGuide);
		TroubleNormGuide mainGuide = bo.getQuotaByType("0");
		Map yearquotaMain = bo.statYearTroubleMonthQuotas("0", year,mainGuide);
		request.setAttribute("year",year);
		request.setAttribute("normGuide",normGuide);
		request.getSession().setAttribute("yearquotaNorm",yearquotaNorm);
		request.setAttribute("mainGuide",mainGuide);
		request.getSession().setAttribute("yearquotaMain",yearquotaMain);
		request.setAttribute("contractorName", contractorName);
		return mapping.findForward("viewTrouble");
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
		request.getSession().removeAttribute("appraiseYearResults");
		return mapping.findForward("queryVerifyResultAppraiseForm");
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
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		AppraiseResultBean appraiseResultBean = (AppraiseResultBean) form;
		appraiseResultBean.setContractorId(userInfo.getDeptID());
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseYearResultBO appraiseYearResultBO=(AppraiseYearResultBO)ctx.getBean("appraiseYearResultBO");
		appraiseResultBean.setConfirmResult("'"+AppraiseConstant.STATE_VERIFY_PASS+"','"+AppraiseConstant.STATE_VERIFY_NOT_PASS+"'");
		List<AppraiseYearResult> appraiseYearResults = appraiseYearResultBO.getResultByAppraiseResultBean(appraiseResultBean);
		request.getSession().setAttribute("appraiseYearResults", appraiseYearResults);
		return mapping.findForward("queryVerifyResultAppraiseForm");
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
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		AppraiseResultBean appraiseResultBean=new AppraiseResultBean();
		AppraiseYearResultBO appraiseYearResultBO=(AppraiseYearResultBO)ctx.getBean("appraiseYearResultBO");
		AppraiseMonthBO appraiseMonthBO=(AppraiseMonthBO)ctx.getBean("appraiseMonthBO");
		if(userInfo.getDeptype().equals("1")){
			appraiseResultBean.setConfirmResult("'"+AppraiseConstant.STATE_WAIT_SEND+"','"+AppraiseConstant.STATE_VERIFY_NOT_PASS+"'");
		}else{
			appraiseResultBean.setConfirmResult("'"+AppraiseConstant.STATE_WAIT_VERIFY+"'");
		}
		ContractorBO contractorBO=new ContractorBO();
		List<AppraiseYearResult> appraiseYearResults = appraiseYearResultBO.getResultByAppraiseResultBean(appraiseResultBean);
		Map confirmMap=appraiseMonthBO.getAllLastConfirmMapByResultBean();
		request.getSession().setAttribute("appraiseResults", appraiseYearResults);
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
		AppraiseYearBO appraiseBO = (AppraiseYearBO)ctx.getBean("appraiseYearBO");
		List<AppraiseConfirmResult> appraiseConfirmResults=appraiseBO.queryConfirmResultByResultId(resultId);
		request.getSession().setAttribute("confirmResults", appraiseConfirmResults);
		return mapping.findForward("viewVerifyHistory");
	}
}
