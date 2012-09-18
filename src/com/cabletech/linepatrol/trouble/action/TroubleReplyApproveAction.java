package com.cabletech.linepatrol.trouble.action;

import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Depart;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.trouble.beans.TroubleApproveBean;
import com.cabletech.linepatrol.trouble.module.TroubleEoms;
import com.cabletech.linepatrol.trouble.module.TroubleInfo;
import com.cabletech.linepatrol.trouble.module.TroubleReply;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;
import com.cabletech.linepatrol.trouble.services.TroubleInfoBO;
import com.cabletech.linepatrol.trouble.services.TroubleReplyApproveBO;
import com.cabletech.linepatrol.trouble.services.TroubleReplyBO;

/**
 * 故障反馈审核
 * 
 * @author
 * 
 */
public class TroubleReplyApproveAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 转到反馈单审核页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addApproveForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String userid = userinfo.getUserID();
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleReplyApproveBO approveBO = (TroubleReplyApproveBO) ctx
				.getBean("troubleReplyApproveBO");
		TroubleInfoBO troubleBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		String troubleid = request.getParameter("troubleid");
		List<TroubleEoms> eomsList = replyBO.getEomsByTroubleId(troubleid);
		String eoms = replyBO.emosListToString(eomsList);
		int eomsnum = 0;
		if (eomsList != null) {
			eomsnum = eomsList.size();
		}
		request.setAttribute("eoms", eoms);
		request.setAttribute("eomsnum", eomsnum);
		String act = request.getParameter("act");
		TroubleInfo trouble = troubleBO.getTroubleById(troubleid);
		List replys = approveBO.getReplys(troubleid, userid, act);
		List unitlist = troubleBO.findTroubleUnitById(troubleid);
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
		request.setAttribute("trouble", trouble);
		request.setAttribute("replys", replys);
		request.setAttribute("unitlist", unitlist);
		if (act != null && act.equals("dispatch")) {
			return mapping.findForward("dispatchApproveForm");
		}
		return mapping.findForward("addApproveForm");
	}

	/**
	 * 转到审核单个反馈单表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addApproveReplyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().setAttribute("FILES", null);
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleReplyApproveBO bo = (TroubleReplyApproveBO) ctx
				.getBean("troubleReplyApproveBO");
		TroubleInfoBO troubleBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		String replyid = request.getParameter("replyid");
		BasicDynaBean bean = bo.getTrouble(replyid);
		TroubleInfo troubleInfo = null;
		request.setAttribute("bean", bean);
		List approves = bo.getApproveHistorys(replyid);
		request.setAttribute("approves", approves);
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		TroubleReply reply = replyBO.getTroubleReply(replyid);

		// 进行故障反馈时限的计算
		if (bean != null) {
			String troubleId = (String) bean.get("tid");
			troubleInfo = troubleBO.getTroubleById(troubleId);
		}
		int[] timeLength = replyBO
				.calculateReplyTimeLength(troubleInfo, reply);
		if (timeLength[0] < 0) {
			request.setAttribute("time_length_sign", "-");
		} else {
			request.setAttribute("time_length_sign", "+");
		}
		request.setAttribute("time_length_hour", Integer.toString(Math.abs(timeLength[1])));
		request.setAttribute("time_length_minute", Integer.toString(Math.abs(timeLength[2])));

		String cf = reply.getConfirmResult();
		request.setAttribute("reply", reply);
		// request.setAttribute("replyMan",replyMan.getUserName());
		if (cf.equals(TroubleConstant.REPLY_ROLE_HOST)) {
			List cableList = replyBO.cableList(replyid);
			request.setAttribute("cableList", cableList);
			List trunkList = replyBO.cableList(replyid);
			String trunks = replyBO.listToString(trunkList);
			reply.setTrunkids(trunks);
			BasicDynaBean hidden = replyBO.getAccidentByReplyId(replyid);
			request.setAttribute("hidden", hidden);
			List<String> processers = replyBO.getProcessers(reply.getId());
			String responsibles = processers.get(0);
			String testmans = processers.get(1);
			String mendmans = processers.get(2);
			request.setAttribute("responsibles", responsibles);
			request.setAttribute("testmans", testmans);
			request.setAttribute("mendmans", mendmans);
			List process = replyBO.getTroubleProcessList(reply.getTroubleId());
			request.setAttribute("process", process);
		}
		String act = request.getParameter("act");
		if (act != null && act.equals("transfer")) {
			return mapping.findForward("transferApproveForm");
		}
		return mapping.findForward("addApproveReplyForm");
	}

	/**
	 * 保存审核信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveReply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleReplyApproveBO bo = (TroubleReplyApproveBO) ctx
				.getBean("troubleReplyApproveBO");
		TroubleInfoBO troubleInfoBo = (TroubleInfoBO) ctx
				.getBean("troubleInfoBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List<FileItem> files = (List<FileItem>) request.getSession()
				.getAttribute("FILES");
		TroubleApproveBean bean = (TroubleApproveBean) form;
		bean.setApproverId(userinfo.getUserID());
		bean.setObjectType("LP_TROUBLE_REPLY");
		String troubleId = request.getParameter("troubleid");
		String act = request.getParameter("act");
		if (act != null && act.equals("transfer")) {
			String approvers = request.getParameter("approver");
			String mobiles = request.getParameter("mobiles");
			bean.setTransfer(approvers);
			bean.setMobiles(mobiles);
			bean.setApproveResult(TroubleConstant.APPROVE_RESULT_TRANSFER);
		}
		try {
			bean.setApproverId(userinfo.getUserID());
			bo.addApprove(userinfo, bean, troubleId, files);
			if (act != null && act.equals("transfer")) {
				log(request, " 转审故障  （故障名称为："
						+ troubleInfoBo.getTroubleById(troubleId)
								.getTroubleName() + ")", " 故障管理 ");
				return this.forwardInfoPage(mapping, request,
						"replyTrasferApproveOK");
			}
			String approveResult = bean.getApproveResult();

			if (approveResult.equals(TroubleConstant.APPROVE_RESULT_NO)) {// 不通过
				log(request, "  审核故障不通过  （故障名称为："
						+ troubleInfoBo.getTroubleById(troubleId)
								.getTroubleName() + ")", " 故障管理 ");
				return this.forwardInfoPage(mapping, request,
						"approveReplyNoPassOK");
			}
			log(request, "  审核故障通过 （故障名称为："
					+ troubleInfoBo.getTroubleById(troubleId).getTroubleName()
					+ ")", " 故障管理 ");
			TroubleReplyBO replyBO = (TroubleReplyBO) ctx
					.getBean("troubleReplyBO");
			TroubleReply reply = replyBO.getTroubleReply(bean.getObjectId());
			String cresult = reply.getConfirmResult();
			if (cresult.equals(TroubleConstant.REPLY_ROLE_HOST)) {
				return this.forwardInfoPage(mapping, request, "approveReplyOK");
			} else {
				return this.forwardInfoPage(mapping, request,
						"approveReplyJoinOK");
			}
		} catch (Exception e) {
			logger.error("故障反馈失败:" + e.getMessage());
			e.printStackTrace();
		}
		return this.forwardErrorPage(mapping, request, "error");
	}

	/**
	 * 关闭故障
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward closeTroubleInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
					"LOGIN_USER");
			String troubleid = request.getParameter("troubleid");
			String replyid = request.getParameter("replyid");
			WebApplicationContext ctx = getWebApplicationContext();
			TroubleReplyApproveBO bo = (TroubleReplyApproveBO) ctx
					.getBean("troubleReplyApproveBO");
			String act = request.getParameter("act");
			if (act != null && act.equals("closeonereply")) {
				bo.closeOneReply(userinfo, replyid);
				return this.forwardInfoPage(mapping, request, "closReplyok");
			}
			bo.closeTrouble(userinfo, troubleid, act);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.forwardInfoPage(mapping, request, "closTroubleok");
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
	public ActionForward viewTroubleInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String troubleid = request.getParameter("troubleid");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleInfoBO troubleBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		TroubleInfo troubleinfo = troubleBO.get(troubleid);
		List list = troubleBO.findTroubleUnitById(troubleid);
		request.setAttribute("unitlist", list);
		request.setAttribute("troubleinfo", troubleinfo);
		return mapping.findForward("viewTroubleForm");
	}

}
