package com.cabletech.linepatrol.trouble.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Depart;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.UserInfoBO;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.tags.services.DictionaryService;
import com.cabletech.linepatrol.trouble.beans.TroubleReplyBean;
import com.cabletech.linepatrol.trouble.module.TroubleEoms;
import com.cabletech.linepatrol.trouble.module.TroubleInfo;
import com.cabletech.linepatrol.trouble.module.TroubleProcessUnit;
import com.cabletech.linepatrol.trouble.module.TroubleReply;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;
import com.cabletech.linepatrol.trouble.services.TroubleInfoBO;
import com.cabletech.linepatrol.trouble.services.TroubleQueryStatBO;
import com.cabletech.linepatrol.trouble.services.TroubleReplyApproveBO;
import com.cabletech.linepatrol.trouble.services.TroubleReplyBO;

/**
 * 故障反馈
 * 
 * @author
 * 
 */
public class TroubleReplyAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 转到代办工作页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getActWorkForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		super.setPageReset(request);
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String userid = userinfo.getUserID();
		String taskName = request.getParameter("task_name");
		request.setAttribute("task_name", taskName);
		List list = replyBO.findWaitReplys(userinfo, userid, taskName);
		request.getSession().setAttribute("waitReplys", list);
		return mapping.findForward("listWaitReply");
	}

	/**
	 * 载入故障流程图页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTroubleProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String taskName = request.getParameter("task_name");
		String taskNames = request.getParameter("task_names");
		TroubleReplyBO bo = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		String condition = "";
		List waitHandleTaskNum = bo.queryForHandleTroubleNum(condition, userInfo);
		request.setAttribute("wait_reply_num", waitHandleTaskNum.get(0));
		request.setAttribute("wait_approve_num", waitHandleTaskNum.get(1));
		request.setAttribute("wait_confirm_num", waitHandleTaskNum.get(2));
		request.setAttribute("wait_evaluate_num", waitHandleTaskNum.get(3));
		request.setAttribute("task_name", taskName);
		if (taskNames != null) {
			request.setAttribute("task_names", taskNames.split(","));
		}
		if (request.getParameter("forward") != null && !"".equals(request.getParameter("forward").trim())) {
			return mapping.findForward(request.getParameter("forward"));
		}
		return mapping.findForward("view_trouble_process");
	}

	/**
	 * 转到反馈页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addReplyForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String deptid = userinfo.getDeptID();
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		TroubleInfoBO troubleBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		String troubleid = request.getParameter("troubleid");
		String unitid = request.getParameter("unitid");
		String state = request.getParameter("state");
		TroubleInfo trouble = troubleBO.getTroubleById(troubleid);
		TroubleReply r = replyBO.getReplyByTroubleIAndContractorID(troubleid, userinfo.getDeptID());
		String deptName = replyBO.getDeptNameByUserId(trouble.getSendManId());
		List process = replyBO.getTroubleProcessByCondition(troubleid, deptid);
		String troubleXy = replyBO.getTroubleXY(process);
		request.setAttribute("troubleXY", troubleXy);
		request.setAttribute("trouble", trouble);
		request.setAttribute("deptname", deptName);
		request.setAttribute("process", process);
		request.setAttribute("unitid", unitid);
		if (state != null && state.equals("reply_task")) {// 反馈界面，判断是修改反馈单还是填写反馈单
			if (r != null) {
				String replyid = r.getId();
				String cr = r.getConfirmResult();
				if (cr != null && cr.equals(TroubleConstant.REPLY_ROLE_HOST)) {
					List<String> processers = replyBO.getProcessers(replyid);
					List trunkList = replyBO.cableList(replyid);
					String trunks = replyBO.listToString(trunkList);
					r.setTrunkids(trunks);
					String responsibles = processers.get(0);
					String testmans = processers.get(1);
					String mendmans = processers.get(2);
					BasicDynaBean hidden = replyBO.getAccidentByReplyId(replyid);
					String impressType = r.getImpressType();
					List list = replyBO.string2List(impressType);
					request.setAttribute("impressTypes", impressType);
					request.setAttribute("impressType", list);
					request.setAttribute("responsibles", responsibles);
					request.setAttribute("testmans", testmans);
					request.setAttribute("mendmans", mendmans);
					request.setAttribute("hidden", hidden);
					String troublexy = "";
					if (r.getTroubleGpsX() != null && r.getTroubleGpsY() != null) {
						troublexy = r.getTroubleGpsX() + "," + r.getTroubleGpsY();
					}
					request.setAttribute("troublexy", troublexy);
				}
				TroubleReplyApproveBO bo = (TroubleReplyApproveBO) ctx.getBean("troubleReplyApproveBO");
				List approves = bo.getApproveHistorys(replyid);
				request.setAttribute("approves", approves);
				request.setAttribute("troubleReplyBean", r);
				return mapping.findForward("editReplyForm");
			}
		}
		TroubleReply hostReply = replyBO.getReplyByTroubleId(troubleid);
		request.setAttribute("hostReply", hostReply);
		request.getSession().setAttribute("FILES", null);
		return mapping.findForward("addReplyForm");
	}

	/**
	 * 保存反馈信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveTroubleReply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TroubleReplyBean bean = (TroubleReplyBean) form;
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		String troubleid = request.getParameter("troubleid");
		String btnstate = request.getParameter("tempstate");
		bean.setTroubleId(troubleid);
		bean.setTempsave(btnstate);
		String unitid = request.getParameter("unitid");
		String confirmResult = bean.getConfirmResult();
		String approvers = request.getParameter("approvers");
		bean.setApprovers(approvers);
		String mobiles = request.getParameter("mobiles");
		bean.setMobiles(mobiles);
		try {
			if (confirmResult.equals(TroubleConstant.REPLY_ROLE_HOST)) {// 主办
				TroubleReply hostReply = replyBO.getReplyByTroubleId(troubleid);
				if (hostReply != null) {
					return this.forwardInfoPage(mapping, request, "210201replyRepeat");
				}
				String hiddentrouble = request.getParameter("hiddentrouble");
				bean.setHiddentrouble(hiddentrouble);
				String responsible = request.getParameter("responsible");
				String testman = request.getParameter("testman");
				String mendman = request.getParameter("mendman");
				String trunks = request.getParameter("trunk");
				List<String> trunkList = new ArrayList<String>();
				if (StringUtils.isNotBlank(trunks)) {
					trunkList = Arrays.asList(trunks.split(","));
				}
				bean.setTrunks(trunkList);
				List<FileItem> files = (List<FileItem>) request.getSession().getAttribute("FILES");
				replyBO.addTroubleReply(bean, userinfo, unitid, responsible, testman, mendman, files);
			}
			if (confirmResult.equals(TroubleConstant.REPLY_ROLE_JOIN)) {// 协办
				replyBO.addReply(bean, userinfo, unitid, null);
			}
			if (btnstate != null && btnstate.equals("tempsaveReply")) {
				log(request, "  临时保存故障反馈单   （故障名称为：" + bean.getTroubleName() + "）", " 故障管理 ");
				return this.forwardInfoPage(mapping, request, "210201tempsavereplyok");
			}
			log(request, "  填写故障反馈单   （故障名称为：" + bean.getTroubleName() + "）", " 故障管理 ");
			return this.forwardInfoPage(mapping, request, "210201replyok");
		} catch (Exception e) {
			logger.info("保存反馈单出现异常：" + e.getMessage());
			e.getStackTrace();
		}
		return this.forwardInfoPage(mapping, request, "210201replyfail");
	}

	/**
	 * 修改反馈信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward editTroubleReply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TroubleReplyBean bean = (TroubleReplyBean) form;
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		TroubleInfoBO troubleInfoBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		String troubleid = request.getParameter("troubleid");
		String btnstate = request.getParameter("tempstate");
		bean.setTroubleId(troubleid);
		bean.setTempsave(btnstate);
		String unitid = request.getParameter("unitid");
		String confirmResult = bean.getConfirmResult();
		String approvers = request.getParameter("approvers");
		bean.setApprovers(approvers);
		try {
			if (confirmResult.equals(TroubleConstant.REPLY_ROLE_HOST)) {// 主办
				String hiddentrouble = request.getParameter("hiddentrouble");
				bean.setHiddentrouble(hiddentrouble);
				String responsible = request.getParameter("responsible");
				String testman = request.getParameter("testman");
				String mendman = request.getParameter("mendman");
				String trunks = request.getParameter("trunk");
				List<String> trunkList = new ArrayList<String>();
				if (StringUtils.isNotBlank(trunks)) {
					trunkList = Arrays.asList(trunks.split(","));
				}
				bean.setTrunks(trunkList);
				List<FileItem> files = (List<FileItem>) request.getSession().getAttribute("FILES");
				replyBO.addTroubleReply(bean, userinfo, unitid, responsible, testman, mendman, files);
			}
			String name = troubleInfoBO.get(troubleid).getTroubleName();
			if (confirmResult.equals(TroubleConstant.REPLY_ROLE_JOIN)) {// 协办
				replyBO.addReply(bean, userinfo, unitid, null);
			}
			if (btnstate != null && btnstate.equals("tempsaveReply")) {
				log(request, "  临时保存故障反馈单   （故障名称为：" + name + "）", " 故障管理 ");
				return this.forwardInfoPage(mapping, request, "210201tempsavereplyok");
			}
			log(request, "  修改故障反馈单   （故障名称为：" + name + "）", " 故障管理 ");
			return forwardInfoPage(mapping, request, "210201editreplyok");
		} catch (Exception e) {
			logger.info("修改反馈单失败:" + e.getMessage());
			logger.error(e);
			e.getStackTrace();
		}
		return forwardInfoPage(mapping, request, "210201replyfail");
	}

	/**
	 * 根据反馈单编号查看反馈单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewReplyInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String replyid = request.getParameter("replyid");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		UserInfoBO userBO = new UserInfoBO();
		TroubleReply reply = replyBO.getTroubleReply(replyid);
		UserInfo user = userBO.loadUserInfo(reply.getReplyManId());
		if (user != null) {
			String replyman = user.getUserName();
			request.setAttribute("replyman", replyman);
		}
		request.setAttribute("reply", reply);
		return mapping.findForward("viewReply");
	}

	/**
	 * 根据故障单编号查看故障单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewTroubleInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String troubleid = request.getParameter("troubleid");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleInfoBO troubleBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		TroubleInfo troubleinfo = troubleBO.get(troubleid);
		List list = troubleBO.findTroubleUnitById(troubleid);
		request.setAttribute("unitlist", list);
		request.setAttribute("troubleinfo", troubleinfo);
		return mapping.findForward("");
	}

	/**
	 * 转到平台审核页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editDispatch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String replyid = request.getParameter("replyid");
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		DictionaryService dictService = (DictionaryService) ctx.getBean("dictionaryService");
		Map deadlineMap = dictService.loadDictByAssortment("trouble_deadline", user.getRegionid());
		request.setAttribute("deadline_map", deadlineMap);
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		TroubleInfoBO troubleBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		TroubleReply r = replyBO.getTroubleReply(replyid);
		UserInfo replyMan = replyBO.getUserInfoByUserId(r.getReplyManId());
		if (replyMan != null) {
			Contractor c = replyBO.getContractorByConId(replyMan.getDeptID());
			request.setAttribute("contractor", c);
			request.setAttribute("replyman", replyMan);
		}
		String troubleid = r.getTroubleId();
		TroubleInfo troubleinfo = troubleBO.getTroubleById(troubleid);
		TroubleProcessUnit unit = replyBO.getProcessUnitByReplyId(replyid);
		String unitid = unit.getId();
		List list = troubleBO.findTroubleUnitById(troubleid);
		List<TroubleEoms> eoms = replyBO.getEomsByTroubleId(troubleid);
		String eomsstr = replyBO.emosListToString(eoms);
		UserInfo sendMan = replyBO.getUserInfoByUserId(troubleinfo.getSendManId());
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
				Contractor con = replyBO.getContractorByConId(deptid);
				if (con != null) {
					deptName = con.getContractorName();
				}
			}
			String sendman = sendMan.getUserName();
			request.setAttribute("deptName", deptName);
			request.setAttribute("sendman", sendman);
		}
		List trunkList = replyBO.cableList(replyid);
		String trunks = replyBO.listToString(trunkList);
		r.setTrunkids(trunks);
		request.setAttribute("unitlist", list);
		request.setAttribute("trouble", troubleinfo);
		request.setAttribute("troubleReplyBean", r);
		int eomsnum = 0;
		if (eoms != null) {
			eomsnum = eoms.size();
		}
		request.setAttribute("eomsnum", eomsnum);
		request.setAttribute("eoms", eomsstr);
		List process = replyBO.getTroubleProcessList(troubleid);
		request.setAttribute("process", process);
		request.setAttribute("unitid", unitid);
		List<String> processers = replyBO.getProcessers(replyid);
		String responsibles = processers.get(0);
		String testmans = processers.get(1);
		String mendmans = processers.get(2);
		request.setAttribute("responsibles", responsibles);
		request.setAttribute("testmans", testmans);
		request.setAttribute("mendmans", mendmans);
		BasicDynaBean hidden = replyBO.getAccidentByReplyId(replyid);
		request.setAttribute("hidden", hidden);
		String troublexy = "";
		if (r.getTroubleGpsX() != null && r.getTroubleGpsY() != null) {
			troublexy = r.getTroubleGpsX() + "," + r.getTroubleGpsY();
		}
		request.setAttribute("troublexy", troublexy);
		request.getSession().setAttribute("FILES", null);
		return mapping.findForward("editDispatch");
	}

	/**
	 * 平台核准
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward dispatchApprove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TroubleReplyBean bean = (TroubleReplyBean) form;
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List<FileItem> files = (List<FileItem>) request.getSession().getAttribute("FILES");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		String unitid = request.getParameter("unitid");
		String approvers = request.getParameter("approver");
		bean.setApprovers(approvers);
		try {
			replyBO.updateTroubleAcceptTime(bean, userinfo, files, unitid);
			log(request, " 平台核准   （故障名称为：" + bean.getTroubleName() + "）", " 故障管理 ");
			return forwardInfoPage(mapping, request, "dispatchReplyOK");
		} catch (Exception e) {
			logger.info("平台审核失败:" + e.getMessage());
			logger.error(e);
			e.getStackTrace();
		}
		return forwardInfoPage(mapping, request, "210201replyfail");
	}

	/**
	 * 载入故障已办流程图页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTroubleProcessHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String taskName = request.getParameter("task_name");
		TroubleReplyBO bo = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		String condition = "";
		List handleTaskNum = bo.queryForHistoryTroubleNum(condition, userInfo);
		request.setAttribute("dispatch_num", handleTaskNum.get(0));
		request.setAttribute("reply_num", handleTaskNum.get(1));
		request.setAttribute("confirm_num", handleTaskNum.get(2));
		request.setAttribute("approve_num", handleTaskNum.get(3));
		request.setAttribute("evaluate_num", handleTaskNum.get(4));
		request.setAttribute("task_name", taskName);
		return mapping.findForward("view_trouble_process_history");
	}

	/**
	 * 查询已办工作列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getFinishHandledWork(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		super.setPageReset(request);
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQueryStatBO bo = (TroubleQueryStatBO) ctx.getBean("troubleQueryStatBO");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String taskName = request.getParameter("task_name");
		String taskOutCome = request.getParameter("task_out_come");
		List list = bo.getHandeledWorks(user, taskName, taskOutCome);
		request.getSession().setAttribute("handeledReplys", list);
		return mapping.findForward("finishHandelTroubles");
	}
}
