package com.cabletech.linepatrol.trouble.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Depart;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.trouble.beans.TroubleQueryStatBean;
import com.cabletech.linepatrol.trouble.module.TroubleEoms;
import com.cabletech.linepatrol.trouble.module.TroubleInfo;
import com.cabletech.linepatrol.trouble.module.TroubleProcessUnit;
import com.cabletech.linepatrol.trouble.module.TroubleReply;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;
import com.cabletech.linepatrol.trouble.services.TroubleExamBO;
import com.cabletech.linepatrol.trouble.services.TroubleExportBO;
import com.cabletech.linepatrol.trouble.services.TroubleInfoBO;
import com.cabletech.linepatrol.trouble.services.TroubleQueryStatBO;
import com.cabletech.linepatrol.trouble.services.TroubleReplyApproveBO;
import com.cabletech.linepatrol.trouble.services.TroubleReplyBO;

/**
 * 故障统计
 * 
 * @author
 * 
 */
public class TroubleQueryStatAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 转到故障查询页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryTroubleForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQueryStatBO bo = (TroubleQueryStatBO) ctx
				.getBean("troubleQueryStatBO");
		List cons = bo.getContractors(user);
		request.getSession().setAttribute("cons", cons);
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("troubles");
		request.getSession().removeAttribute("otherCon");
		return mapping.findForward("queryTroubleForm");
	}

	/**
	 * 转到故障统计页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward statTroubleForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQueryStatBO bo = (TroubleQueryStatBO) ctx
				.getBean("troubleQueryStatBO");
		List cons = bo.getContractors(user);
		request.getSession().setAttribute("cons", cons);
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("stattroubles");
		request.getSession().removeAttribute("otherCon");
		return mapping.findForward("statTroubleForm");
	}

	/**
	 * 根据条件查询故障列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getTroubleInfos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TroubleQueryStatBean bean = (TroubleQueryStatBean) form;
		if (null == request.getParameter("isQuery")) {
			bean = (TroubleQueryStatBean) request.getSession().getAttribute(
					"queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition", bean);
		}
		if (bean == null) {
			bean = new TroubleQueryStatBean();
		}
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
//		String trunks=request.getParameter("trunkList");
		String trunks = request.getParameter("trunk");
		List<String> trunkList = new ArrayList<String>();
		if (StringUtils.isNotBlank(trunks)) {
			trunkList = Arrays.asList(trunks.split(","));
		}
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQueryStatBO bo = (TroubleQueryStatBO) ctx
				.getBean("troubleQueryStatBO");
		List troubles = bo.getTroubles(bean, user, trunkList, "");
		
		Map<String,String> otherCon=new HashMap<String,String>();
		otherCon.put("troubleType", setToString(bean.getTroubleType()));
		otherCon.put("termiAddr", setToString(bean.getTermiAddr()));
		otherCon.put("troublReason", setToString(bean.getTroublReason()));
		otherCon.put("trunks", trunks);
		request.getSession().setAttribute("otherCon",otherCon);
		
		request.setAttribute("task_names", bean.getTaskNames());
		request.getSession().setAttribute("troubles", troubles);
		// request.getSession().setAttribute("trunk",null);
		super.setPageReset(request);
		return mapping.findForward("listTroubles");
	}

	/**
	 * 根据条件统计故障列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward statTroubleInfos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TroubleQueryStatBean bean = (TroubleQueryStatBean) form;
		if (null == request.getParameter("isQuery")) {
			bean = (TroubleQueryStatBean) request.getSession().getAttribute(
					"queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition", bean);
		}
		if (bean == null) {
			bean = new TroubleQueryStatBean();
		}
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String trunks = request.getParameter("trunk");
		List<String> trunkList = new ArrayList<String>();
		if (StringUtils.isNotBlank(trunks)) {
			trunkList = Arrays.asList(trunks.split(","));
		}
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQueryStatBO bo = (TroubleQueryStatBO) ctx
				.getBean("troubleQueryStatBO");
		List troubles = bo.getTroubles(bean, user, trunkList, "stat");
		request.getSession().setAttribute("stattroubles", troubles);
		// request.getSession().setAttribute("trunk",null);
		super.setPageReset(request);
		Map<String,String> otherCon=new HashMap<String,String>();
		otherCon.put("troubleType", setToString(bean.getTroubleType()));
		otherCon.put("termiAddr", setToString(bean.getTermiAddr()));
		otherCon.put("troublReason", setToString(bean.getTroublReason()));
		otherCon.put("trunks", trunks);
		request.getSession().setAttribute("otherCon",otherCon);
		return mapping.findForward("statTroubles");
	}

	/**
	 * 查询一般故障
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getTroubleByGeneral(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQueryStatBO bo = (TroubleQueryStatBO) ctx
				.getBean("troubleQueryStatBO");
		// List troubles = bo.getTroubleByCondition(user,"general");
		TroubleQueryStatBean bean = new TroubleQueryStatBean();
		List<String> trunkList = new ArrayList<String>();
		List troubles = bo.getTroubles(bean, user, trunkList, "general");
		request.getSession().setAttribute("troubles", troubles);
		return mapping.findForward("generalTrouble");
	}

	/**
	 * 查询重大故障
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getTroubleByGreat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQueryStatBO bo = (TroubleQueryStatBO) ctx
				.getBean("troubleQueryStatBO");
		List<String> trunkList = new ArrayList<String>();
		TroubleQueryStatBean bean = new TroubleQueryStatBean();
		List troubles = bo.getTroubles(bean, user, trunkList, "great");
		// List troubles = bo.getTroubleByCondition(user,"great");
		request.getSession().setAttribute("troubles", troubles);
		return mapping.findForward("greatTrouble");
	}

	/**
	 * 查询城区故障
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getTroubleByCity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQueryStatBO bo = (TroubleQueryStatBO) ctx
				.getBean("troubleQueryStatBO");
		List troubles = bo.getTroubleByCondition(user, "city");
		request.getSession().setAttribute("troubles", troubles);
		return mapping.findForward("cityTrouble");
	}

	/**
	 * 查询郊区故障
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getTroubleByOutSkirt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQueryStatBO bo = (TroubleQueryStatBO) ctx
				.getBean("troubleQueryStatBO");
		List troubles = bo.getTroubleByCondition(user, "outskirt");
		request.getSession().setAttribute("troubles", troubles);
		return mapping.findForward("outSkirtTrouble");
	}

	/**
	 * 查看故障详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewTrouble(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String depttype = user.getDeptype();
		String userid = user.getUserID();
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleReplyApproveBO approveBO = (TroubleReplyApproveBO) ctx
				.getBean("troubleReplyApproveBO");
		TroubleInfoBO troubleBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		TroubleExamBO examBO = (TroubleExamBO) ctx.getBean("troubleExamBO");
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		String troubleid = request.getParameter("troubleid");
		String isread = request.getParameter("isread");
		String flowState = request.getParameter("flowState");
		TroubleInfo trouble = troubleBO.getTroubleById(troubleid);
		List replys = new ArrayList();
		if (isread != null && isread.equals("true")) {
			if (depttype.equals("2")) {
				replys = approveBO.getReplyInfos(troubleid, userid, flowState);
			} else {
				if (flowState != null
						&& (flowState.equals("dispatch") || flowState
								.equals("edit_dispatch_task"))) {
					replys = approveBO.getReplyInfos(troubleid, userid,
							flowState);
				} else {
					replys = approveBO.viewReplysByReads(user, troubleid);
				}
			}
		} else if (isread != null && isread.equals("false")) {
			replys = approveBO.viewReplysByApproves(user, troubleid);
		} else {
			replys = approveBO.viewReplys(user, troubleid);
		}
		List unitlist = troubleBO.findTroubleUnitById(troubleid);
		String reasonid = trouble.getTroubleReasonId();
		String reasonName = troubleBO.getTroubleReasonName(reasonid);
		List<TroubleEoms> eomsList = replyBO.getEomsByTroubleId(troubleid);
		String eoms = replyBO.emosListToString(eomsList);
		UserInfo sendMan = replyBO.getUserInfoByUserId(trouble.getSendManId());
		if (sendMan != null) {
			String deptype = sendMan.getDeptype();
			String deptid = sendMan.getDeptID();
			String deptName = "";
			if (deptype.equals("1")) {
				Depart d = replyBO.getDepartByDepartId(deptid);
				if (d != null) {
					deptName = d.getDeptName();
				}
			} else {
				Contractor c = replyBO.getContractorByConId(deptid);
				if (c != null) {
					deptName = c.getContractorName();
				}
			}
			String sendman = sendMan.getUserName();
			request.setAttribute("deptName", deptName);
			request.setAttribute("sendman", sendman);
		}
		int eomsnum = 0;
		if (eomsList != null) {
			eomsnum = eomsList.size();
		}
		request.setAttribute("eoms", eoms);
		request.setAttribute("eomsnum", eomsnum);
		request.setAttribute("trouble", trouble);
		request.setAttribute("replys", replys);
		request.setAttribute("unitlist", unitlist);
		request.setAttribute("reasonName", reasonName);
		request.setAttribute("isread", isread);
		if (replys != null && replys.size() > 0) {
			for (int i = 0; i < replys.size(); i++) {
				BasicDynaBean reply = (BasicDynaBean) replys.get(i);
				String replyid = (String) reply.get("id");
				String cr = (String) reply.get("cr");
				if (cr.equals(TroubleConstant.REPLY_ROLE_HOST)) {
					/*Evaluate eva = examBO.getEvaluate(replyid,
							TroubleConstant.LP_EVALUATE_TROUBLE);
					request.setAttribute("evaluate", eva);*/
					String contractorId = (String)reply.get("contractorid");
					request.setAttribute("replyid", replyid);
					request.setAttribute("contractorId", contractorId);
					break;
				}
			}
		}
		request.setAttribute("troubleId", troubleid);
		
		String queryact = request.getParameter("queryact");
		request.setAttribute("queryact", queryact);
		return mapping.findForward("viewTroubleInfoList");
	}

	/**
	 * 查看单个故障反馈单详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewTroubleReply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		TroubleReplyApproveBO bo = (TroubleReplyApproveBO) ctx
				.getBean("troubleReplyApproveBO");
		TroubleInfoBO troubleBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		String deptype = user.getDeptype();
		String replyid = request.getParameter("replyid");
		String queryact = request.getParameter("queryact");
		BasicDynaBean bean = bo.getTrouble(replyid);
		TroubleInfo troubleInfo = null;
		request.setAttribute("bean", bean);
		request.setAttribute("queryact", queryact);
		List approves = bo.getApproveHistorys(replyid);
		request.setAttribute("approves", approves);
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		TroubleReply reply = replyBO.getTroubleReply(replyid);
		TroubleProcessUnit prounit = replyBO.getProcessUnitByReplyId(replyid);
		UserInfo replyMan = replyBO.getUserInfoByUserId(reply.getReplyManId());
		if (replyMan != null) {
			Contractor c = replyBO.getContractorByConId(replyMan.getDeptID());
			if (c != null) {
				String contraName = c.getContractorName();
				request.setAttribute("contraName", contraName);
			}
			String replyman = replyMan.getUserName();
			request.setAttribute("replyman", replyman);
		}
		request.setAttribute("reply", reply);
		request.setAttribute("prounit", prounit);

		// 进行故障反馈时限的计算
		if (bean != null) {
			String troubleId = (String) bean.get("tid");
			troubleInfo = troubleBO.getTroubleById(troubleId);
		}
		int[] timeLength = replyBO.calculateReplyTimeLength(troubleInfo, reply);
		if (timeLength[0] < 0) {
			request.setAttribute("time_length_sign", "-");
		} else {
			request.setAttribute("time_length_sign", "+");
		}
		request.setAttribute("time_length_hour", Integer.toString(Math
				.abs(timeLength[1])));
		request.setAttribute("time_length_minute", Integer.toString(Math
				.abs(timeLength[2])));

		String cf = reply.getConfirmResult();
		if (cf != null && cf.equals(TroubleConstant.REPLY_ROLE_HOST)) {
			/*
			 * List cableList = replyBO.cableList(replyid);
			 * request.setAttribute("cableList",cableList);
			 */
			List trunkList = replyBO.cableList(replyid);
			String trunks = replyBO.listToString(trunkList);
			reply.setTrunkids(trunks);
			List<String> processers = replyBO.getProcessers(reply.getId());
			String responsibles = processers.get(0);
			String testmans = processers.get(1);
			String mendmans = processers.get(2);
			request.setAttribute("responsibles", responsibles);
			request.setAttribute("testmans", testmans);
			request.setAttribute("mendmans", mendmans);
			List process = replyBO.getTroubleProcessList(reply.getTroubleId());
			request.setAttribute("process", process);
			BasicDynaBean hidden = replyBO.getAccidentByReplyId(replyid);
			request.setAttribute("hidden", hidden);
			String troubleReasonName = replyBO.getTroubleReasonName(reply
					.getTroubleId());
			request.setAttribute("troubleReasonName", troubleReasonName);
		}
		if (deptype.equals("1")) {
			String userid = user.getUserID();
			String objectType = TroubleConstant.LP_TROUBLE_REPLY;
			boolean isreaded = bo.isReaded(replyid, userid, objectType);
			boolean isread = bo.isReadOnly(replyid, userid, objectType);
			request.setAttribute("isreaded", isreaded);
			request.setAttribute("isread", isread);
		}
		return mapping.findForward("viewTroubleInfoOne");
	}

	public ActionForward readReply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String replyid = request.getParameter("replyid");
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		replyBO.updateReader(user, replyid);
		log(request, "已阅反馈单", " 故障管理 ");
		return forwardInfoPage(mapping, request, "troubleReplyReaded");
	}

	/**
	 * 导出故障统计信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTroubleStatList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TroubleExportBO exportBO = new TroubleExportBO();
		List list = (List) request.getSession().getAttribute("stattroubles");
		exportBO.exportTroubleStats(list, response);
		return null;
	}
	
	/**
	 * 将字符串数组转化为字符串
	 * @param strs	字符串数组
	 * @return
	 */
	public String setToString(String[] strs) {
		StringBuffer sb = new StringBuffer();
		if (strs != null && strs.length > 0) {
			sb.append(strs[0]);
			for (int i = 1; i < strs.length; i++) {
				sb.append("," + strs[i]);
			}
		}
		return sb.toString();
	}

}
