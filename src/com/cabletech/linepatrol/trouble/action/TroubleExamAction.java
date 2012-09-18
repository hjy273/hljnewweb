package com.cabletech.linepatrol.trouble.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Depart;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.module.AppraiseDaily;
import com.cabletech.linepatrol.trouble.beans.TroubleExamBean;
import com.cabletech.linepatrol.trouble.module.TroubleEoms;
import com.cabletech.linepatrol.trouble.module.TroubleInfo;
import com.cabletech.linepatrol.trouble.module.TroubleReply;
import com.cabletech.linepatrol.trouble.services.TroubleExamBO;
import com.cabletech.linepatrol.trouble.services.TroubleInfoBO;
import com.cabletech.linepatrol.trouble.services.TroubleReplyApproveBO;
import com.cabletech.linepatrol.trouble.services.TroubleReplyBO;

public class TroubleExamAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 转到考核评估的界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward troubleExamForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleExamBO examBO = (TroubleExamBO) ctx.getBean("troubleExamBO");
		UserInfo user = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		List list = examBO.findWaitExamTroubles(user);
		request.getSession().setAttribute("list",list);
		return mapping.findForward("examLists");
	}
	
	
	/**
	 * 转到故障考核评估页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward examForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleInfoBO troubleBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		TroubleReplyApproveBO approveBO = (TroubleReplyApproveBO) ctx.getBean("troubleReplyApproveBO");
		String troubleid = request.getParameter("troubleid");
		UserInfo user = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		String userid = user.getUserID();
		TroubleInfo trouble = troubleBO.getTroubleById(troubleid);
		List unitlist = troubleBO.findTroubleUnitById(troubleid);
		List replys = approveBO.getWaitExamTroubleReplys(troubleid,userid);
		List<TroubleEoms> eomsList = replyBO.getEomsByTroubleId(troubleid);
		String eoms = replyBO.emosListToString(eomsList);
		UserInfo sendMan = replyBO.getUserInfoByUserId(trouble.getSendManId());
		if(sendMan!=null){
			String deptype = sendMan.getDeptype();
			String deptid = sendMan.getDeptID();
			String deptName="";
			if(deptype.equals("1")){
				Depart d = replyBO.getDepartByDepartId(deptid);
				if(d!=null){
					deptName = d.getDeptName();
				}
			}else{
				Contractor c = replyBO.getContractorByConId(deptid);
				if(c!=null){
					deptName = c.getContractorName();
				}
			}
			String sendman = sendMan.getUserName();
			request.setAttribute("deptName",deptName);
			request.setAttribute("sendman",sendman);
		}
		int eomsnum = 0;
		if(eomsList!=null){
			eomsnum = eomsList.size();
		}
		request.setAttribute("eoms",eoms);
		request.setAttribute("eomsnum",eomsnum);
		request.setAttribute("trouble", trouble);
		request.setAttribute("unitlist",unitlist);
		request.setAttribute("replys",replys);
		return mapping.findForward("examForm");
	}

	/**
	 * 转到故障考核评估主办反馈单页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward examReplyForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleReplyApproveBO bo = (TroubleReplyApproveBO) ctx.getBean("troubleReplyApproveBO");
		String replyid = request.getParameter("replyid");
		BasicDynaBean bean = bo.getTrouble(replyid);
		request.setAttribute("bean",bean) ;  
		request.setAttribute("contractorId", (String)bean.get("deptid"));
		request.setAttribute("replyid",replyid) ;
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
	/*	List cableList = replyBO.cableList(replyid);
		request.setAttribute("cableList",cableList);*/
		TroubleReply reply = replyBO.getTroubleReply(replyid);
		request.setAttribute("reply",reply);
		List trunkList = replyBO.cableList(replyid);
		String trunks = replyBO.listToString(trunkList);
		reply.setTrunkids(trunks);
		List<String> processers  = replyBO.getProcessers(reply.getId());
		String responsibles = processers.get(0);
		String testmans = processers.get(1);
		String mendmans = processers.get(2);
		request.setAttribute("responsibles",responsibles);
		request.setAttribute("testmans",testmans);
		request.setAttribute("mendmans",mendmans);
		List process = replyBO.getTroubleProcessList(reply.getTroubleId());
		request.setAttribute("process",process);
		BasicDynaBean hidden = replyBO.getAccidentByReplyId(replyid);
		request.setAttribute("hidden",hidden);
		List approves = bo.getApproveHistorys(replyid);
		request.setAttribute("approves",approves);
		String troubleReasonName = replyBO.getTroubleReasonName(reply.getTroubleId());
		request.setAttribute("troubleReasonName",troubleReasonName);
		return mapping.findForward("examReplyForm");
	}
	
	
	/**
	 * 保存考核评分
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveEvaluate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo user = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		String replyid = request.getParameter("replyid");
		String troubleid = request.getParameter("troubleid");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleExamBO bo = (TroubleExamBO) ctx.getBean("troubleExamBO");
		TroubleInfoBO ibo=(TroubleInfoBO)ctx.getBean("troubleInfoBO");
		//获得日常考核对象
		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)request.getSession().getAttribute("appraiseDailyDailyBean");
		List<AppraiseDailyBean> speicalBeans=(List<AppraiseDailyBean>)request.getSession().getAttribute("appraiseDailySpecialBean");
		try {
			bo.saveEvaluate(user, replyid,troubleid, appraiseDailyBean,speicalBeans);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(request, " 考核故障  （故障名称为："+ibo.getTroubleById(troubleid).getTroubleName()+"）", " 故障管理 ");
		return this.forwardInfoPage(mapping, request, "evaluateOK");
	}
}
