package com.cabletech.linepatrol.trouble.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.commons.services.ContractorBO;
import com.cabletech.linepatrol.trouble.beans.TroubleNormGuideBean;
import com.cabletech.linepatrol.trouble.beans.TroubleYearQuotaBean;
import com.cabletech.linepatrol.trouble.module.TroubleGuideMonth;
import com.cabletech.linepatrol.trouble.module.TroubleGuideYear;
import com.cabletech.linepatrol.trouble.module.TroubleNormGuide;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;
import com.cabletech.linepatrol.trouble.services.TroubleExportBO;
import com.cabletech.linepatrol.trouble.services.TroubleQuotaBO;

/**
 * 故障指标
 * @author Administrator
 *
 */
public class TroubleQuotaAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 转到故障指标设置的页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward troubleQuotaForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		List<TroubleNormGuide> list = bo.getQuotas();
		if(list!=null && list.size()>0){
			TroubleNormGuide quota =  bo.getQuotaByType(TroubleConstant.QUOTA);
			TroubleNormGuide quotacity = bo.getQuotaByType(TroubleConstant.QUOTA_CITY);
			request.setAttribute("quota",quota);
			request.setAttribute("quotacity",quotacity);
			return mapping.findForward("editQuotaForm");
		}
		return mapping.findForward("setQuotaForm");
	}


	/**
	 * 保存故障指标
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveQuota(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		TroubleNormGuideBean bean = (TroubleNormGuideBean)form;
		bo.saveQuotaInfo(bean);
		log(request, "  添加故障指标   （故障名称为："+bean.getGuideName()+"）", " 故障管理 ");
		return forwardInfoPage(mapping, request, "210601addok");
	}
	
	
	/**
	 * 修改故障指标
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editQuota(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		TroubleNormGuideBean bean = (TroubleNormGuideBean)form;
		bo.updateQuotaInfo(bean);
		log(request, "  修改故障指标   （故障名称为："+bean.getGuideName()+"）", " 故障管理 ");
		return forwardInfoPage(mapping, request, "210601editok");
	}


	/**
	 * 转到故障指标生成的界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createTroubleQuotaForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("monthquota",null);
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		String guideType = request.getParameter("guideType");
		if(guideType == null){
			guideType = TroubleConstant.QUOTA;
		}
		String month = Calendar.getInstance().get(Calendar.YEAR)+"/01";
		List list=bo.getExistQuotaMonthsList(guideType,month);
		request.setAttribute("guideType",guideType);
		request.setAttribute("exist_month_list", list);
		return mapping.findForward("createTroubleQuotaForm");
	}

	/**
	 * 生成/查询月故障指标
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createQuota(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		String guideType = request.getParameter("guideType");
		String isReCreate = request.getParameter("isReCreate");
		String month = request.getParameter("month");
		TroubleNormGuide guide = bo.getQuotaByType(guideType);
		if(guide==null){
			return this.forwardInfoPage(mapping, request, "210602noquota");
		}
		List list = bo.getQuotaMonth(guideType, month);
		if(list==null || list.size()==0){
			logger.info("生成尚未生成的月故障指标信息");
			bo.createMonthQuota(user, guideType, month);
		}
		if(isReCreate!=null&&"1".equals(isReCreate)){
			logger.info("重新生成月故障指标信息");
			bo.deleteMonthQuota(user, guideType, month);
			bo.createMonthQuota(user, guideType, month);
		}
		list=bo.getExistQuotaMonthsList(guideType,month);
		boolean isNotFinished=bo.isCurrentMonth(month);
		List monthquota = bo.getTroubleMonthQuotas(guideType, month,guide);
		request.setAttribute("guideType", guideType);
		request.setAttribute("month",month);
		request.setAttribute("guide",guide);
		request.setAttribute("is_not_finished",isNotFinished);
		request.setAttribute("exist_month_list", list);
		request.getSession().setAttribute("monthquota",monthquota);
		return mapping.findForward("createTroubleQuotaForm");
	}
	
	/**
	 * 转到查询年故障指标的页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward yearTroubleQuotaForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("yearquota",null);
		String guideType = request.getParameter("guideType");
		if(guideType == null){
			guideType = TroubleConstant.QUOTA;
		}
		request.setAttribute("guideType", guideType);
		return mapping.findForward("yearTroubleQuotaForm");
	}
	
	
	/**
	 * 生成年故障指标
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward statYearQuota(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		String guideType = request.getParameter("guideType");
		String year = request.getParameter("year");
		List<TroubleGuideMonth> listmonth =  bo.getMonthQuotaInYear(guideType, year);
		if(listmonth==null || listmonth.size()==0){
			return this.forwardInfoPage(mapping, request, "210603yearnoquota");
		}
		TroubleNormGuide guide = bo.getQuotaByType(guideType);
		//List yearquota = bo.statYearTroubleMonthQuotas(guideType, year,guide);
		Map yearquota = bo.statYearTroubleMonthQuotas(guideType, year,guide);
		request.setAttribute("guideType", guideType);
		request.setAttribute("year",year);
		request.setAttribute("guide",guide);
		request.getSession().setAttribute("yearquota",yearquota);
		return mapping.findForward("yearTroubleQuotaForm");
	}

	/**
	 * 导出月故障指标信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTroubleQuotaList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		String guideType = request.getParameter("guideType");
		String month = request.getParameter("month");
		TroubleNormGuide guide = bo.getQuotaByType(guideType);
		TroubleExportBO exportBO = new TroubleExportBO();
		List list = (List) request.getSession().getAttribute("monthquota");
		exportBO.exportTroubleQuota(list,guide,month, response);
		return null;
	}

	/**
	 * 导出年故障指标信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportYearTroubleQuotaList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		String guideType = request.getParameter("guideType");
		String year = request.getParameter("year");
		TroubleNormGuide guide = bo.getQuotaByType(guideType);
		TroubleExportBO exportBO = new TroubleExportBO();
		Map map = (Map) request.getSession().getAttribute("yearquota");
		exportBO.exportYearTroubleQuota(map,guide,year, response);
		return null;
	}
	
	public ActionForward queryTroubleQuotaMonthForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
		if("1".equals(userInfo.getDeptype())){
			WebApplicationContext ctx = getWebApplicationContext();
			ContractorBO contractorBO=(ContractorBO)ctx.getBean("contractorBo");
			String condition=" and regionid='"+userInfo.getRegionid()+"' ";
			List list=contractorBO.getContractorList(condition);
			String guideType = request.getParameter("guideType");
			if(guideType == null){
				guideType = TroubleConstant.QUOTA;
			}
			request.setAttribute("guideType", guideType);
			request.setAttribute("contractorList", list);
		}
		return mapping.findForward("queryTroubleQuotaMonthForm");
	}

	public ActionForward queryTroubleQuotaYearForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
		if("1".equals(userInfo.getDeptype())){
			WebApplicationContext ctx = getWebApplicationContext();
			ContractorBO contractorBO=(ContractorBO)ctx.getBean("contractorBo");
			String condition=" and regionid='"+userInfo.getRegionid()+"' ";
			List list=contractorBO.getContractorList(condition);
			request.setAttribute("contractorList", list);
		}
		return mapping.findForward("queryTroubleQuotaYearForm");
	}

	public ActionForward listTroubleQuotaMonth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		super.setPageReset(request);
		String contractorId=request.getParameter("contractorId");
		String guideType=request.getParameter("guideType");
		String beginTime=request.getParameter("beginTime");
		String endTime=request.getParameter("endTime");
		String queryType=request.getParameter("queryType");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		Map map=bo.getTimeAreaTroubleQuotaList(contractorId, guideType, beginTime, endTime);
		TroubleNormGuide guide = bo.getQuotaByType(guideType);
		request.setAttribute("guide",guide);
		request.setAttribute("beginTime",beginTime);
		request.setAttribute("endTime",endTime);
		request.getSession().setAttribute("MONTH_TROUBLE_QUOTA_RESULT", map);
		return mapping.findForward("listTroubleQuotaMonth");
	}

	public ActionForward listTroubleQuotaYear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		super.setPageReset(request);
		String contractorId=request.getParameter("contractorId");
		String guideType=request.getParameter("guideType");
		String beginTime=request.getParameter("beginTime");
		String endTime=request.getParameter("endTime");
		String queryType=request.getParameter("queryType");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		Map map=bo.getTimeAreaTroubleQuotaList(contractorId, guideType, beginTime, endTime);
		request.getSession().setAttribute("YEAR_TROUBLE_QUOTA_RESULT", map);
		return mapping.findForward("listTroubleQuotaYear");
	}

	/**
	 * 导出年故障指标信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTimeAreaTroubleQuotaList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		String contractorId=request.getParameter("contractorId");
		String guideType=request.getParameter("guideType");
		String beginTime=request.getParameter("beginTime");
		String endTime=request.getParameter("endTime");
		TroubleNormGuide guide = bo.getQuotaByType(guideType);
		TroubleExportBO exportBO = new TroubleExportBO();
		Map map = (Map) request.getSession().getAttribute("MONTH_TROUBLE_QUOTA_RESULT");
		exportBO.exportTimeAreaTroubleQuota(map,guide,beginTime,endTime, response);
		return null;
	}
	
	/**
	 * 首页故障指标呈现
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showTroubleQuotaInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		String guideType=request.getParameter("guideType");
		TroubleNormGuide guide = bo.getQuotaByType(guideType);
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		String endTime=DateUtil.DateToString(calendar.getTime(), "yyyy/MM");
		String beginTime=endTime;
		calendar.add(calendar.MONTH, -1);
		beginTime=DateUtil.DateToString(calendar.getTime(), "yyyy/MM");
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
		String contractorId="";
		if("2".equals(userInfo.getDeptype())){
			contractorId=userInfo.getDeptID();
		}
//		Map map=bo.getTimeAreaTroubleQuotaList(contractorId, guideType, year+"/01", endTime);
		//modify by xueyh for 查看上月的一干网或城域网网指标数据
//		Map map=bo.getTimeAreaTroubleQuotaList(contractorId, guideType, beginTime, endTime,"");
		Map map=bo.getTimeAreaTroubleQuotaList(contractorId, guideType, beginTime, endTime);
		request.setAttribute("guide",guide);
		request.setAttribute("guideType",guideType);
		request.setAttribute("trouble_quota_result", map);
		//"我的工作"栏目首页中增加当年的故障指标数据,	add by xueyh 20110504
		Map yearQuotaMap = bo.statYearTroubleMonthQuotas(guideType, year+"", guide);
//		Map mapYear=bo.getYearsTroubleQuotaList(contractorId, guideType, year, 0, "");
		request.setAttribute("year_trouble_quota_result", yearQuotaMap);
		return mapping.findForward("showTroubleQuotaInfo");
	}
	
	/**
	 * 更新修正值
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward updateRevise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws IOException{
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		String id=request.getParameter("id");
		
		String reviseInterdictionTime = request.getParameter("reviseInterdictionTime");
		String reviseTroubleTimes = request.getParameter("reviseTroubleTimes");
		String reviseMaintenanceLength = request.getParameter("reviseMaintenanceLength");
		String result = bo.updateReviseValueGuideMonth(id,reviseInterdictionTime,reviseTroubleTimes,reviseMaintenanceLength);
		
		PrintWriter out = response.getWriter();
		out.print(id+";"+result);
		out.close();
		return null;
	}
	
	/**
	 * 转向添加自定义年指标页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward customYearTroubleQuotaForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws IOException{
		String id = request.getParameter("id");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		if(id != null && !"".equals(id)){
			TroubleGuideYear QuotaYear = bo.getCustomYearQuota(id);
			request.setAttribute("customQuotaYear", QuotaYear);
		}
		return mapping.findForward("customYearQuotaForm");
	}
	/**
	 * 查询所有的自定义故障指标
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward querycustomYearTroubleQuota(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws IOException{
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		List<TroubleGuideYear> yearQuotaList = bo.queryCustonYearTroubleQuota();
		request.getSession().setAttribute("yearQuotaList", yearQuotaList);
		return mapping.findForward("showyearquotalist");
	}
	
	/**
	 * 保存年指标信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward saveCustomTroubleQuota(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws IOException{
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		TroubleYearQuotaBean bean = (TroubleYearQuotaBean)form;
		
		String result = bo.saveCustomYearTroubleQuota(bean);
		if("true".equals(result)){
			List<TroubleGuideYear> yearQuotaList = bo.queryCustonYearTroubleQuota();
			request.getSession().setAttribute("yearQuotaList", yearQuotaList);
			return mapping.findForward("showyearquotalist");
		}else{
			return this.forwardInfoPage(mapping, request, "customYearQuotaError");
		}
		
	}
	
}