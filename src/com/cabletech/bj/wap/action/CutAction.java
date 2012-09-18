package com.cabletech.bj.wap.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.cut.beans.CutBean;
import com.cabletech.linepatrol.cut.services.CutQueryStatManager;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;
import com.cabletech.linepatrol.trouble.services.TroubleQueryStatBO;

public class CutAction extends BaseInfoBaseDispatchAction {
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		request.setAttribute("userInfo", userInfo);
		if ("wap".equals(env) && userInfo != null) {
			return mapping.findForward("cutIndex");
		} else {
			return mapping.findForward("relogin");
		}
	}

	public ActionForward queryCutNum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

		WebApplicationContext ctx = getWebApplicationContext();
		CutQueryStatManager manager = (CutQueryStatManager) ctx.getBean("cutQueryStatManager");
		List<Map> cutNums = manager.queryCutNumFromPDA(userInfo);
		StringBuffer cutHtml = new StringBuffer();
		if (cutNums.size() > 0) {
			setCutNumberHtml(cutNums, cutHtml);
		}
		request.setAttribute("cutNum", cutHtml);
		return mapping.findForward("queryCutNum");
	}

	public ActionForward queryCut(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

		WebApplicationContext ctx = getWebApplicationContext();
		CutQueryStatManager manager = (CutQueryStatManager) ctx.getBean("cutQueryStatManager");
		List<Map> approves = manager.queryCutApproveFromPDA(userInfo);
		List<Map> feedbacks = manager.queryFeedbackFromPDA(userInfo);
		List<Map> accecptances = manager.queryAccecptanceFromPDA(userInfo);
		StringBuffer html = new StringBuffer();
		setApprovesHtml(approves, html);
		String feedbacksTitle="待反馈申请";
		String accecptancesTitle="待审验收";
		setFeetbackCutHtml(feedbacks, html,feedbacksTitle);
		setCutHtml(accecptances, html,accecptancesTitle);
		request.setAttribute("APPROVECUT", html);
		return mapping.findForward("listCut");
	}

	private void setCutHtml(List<Map> feedbacks, StringBuffer html,String title) {
		if (feedbacks.size() > 0) {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>"+title+"（" + feedbacks.size()
					+ "条）</div><br />");
			html
					.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\"><tr align=\"center\"><td width=\"30%\">割接名称</td><td width=\"20%\">负责人</td><td width=\"30%\">反馈时间</td><td width=\"20%\">代维单位</td></tr>");
			for (Map feedback : feedbacks) {
				html.append("<tr align=\"center\" class=\"trcolor\"><td width=\"30%\">" + feedback.get("cutName")
						+ "</td><td width=\"20%\">" + feedback.get("liveChargeMan") + "</td><td width=\"30%\">"
						+ feedback.get("createTime") + "</td><td width=\"20%\">" + feedback.get("contractorName")
						+ "</td></tr>");
			}
			html.append("</table><hr />");
		} else {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>"+title+"：0</div><br />");
		}
	}
	private void setFeetbackCutHtml(List<Map> feedbacks, StringBuffer html,String title) {
		if (feedbacks.size() > 0) {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>"+title+"（" + feedbacks.size()
					+ "条）</div><br />");
			html
					.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\"><tr align=\"center\"><td width=\"30%\">割接名称</td><td width=\"20%\">负责人</td><td width=\"30%\">割接时间</td><td width=\"20%\">代维单位</td></tr>");
			for (Map feedback : feedbacks) {
				html.append("<tr align=\"center\" class=\"trcolor\"><td width=\"30%\">" + feedback.get("cutName")
						+ "</td><td width=\"20%\">" + feedback.get("liveChargeMan") + "</td><td width=\"30%\">"
						+ feedback.get("cutTime") + "</td><td width=\"20%\">" + feedback.get("contractorName")
						+ "</td></tr>");
			}
			html.append("</table><hr />");
		} else {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>"+title+"：0</div><br />");
		}
	}

	private void setApprovesHtml(List<Map> approves, StringBuffer html) {
		if (approves.size() > 0) {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>待审申请（" + approves.size()
					+ "条）</div><br />");
			html
					.append("<table width=\"100%\"  cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\" ><tr class=\"trcolor\" align=\"center\"><td width=\"30%\">割接名称</td><td width=\"15%\">负责人</td><td width=\"15%\">申请时间</td><td width=\"15%\">割接时间</td><td width=\"15%\">代维单位</td></tr>");
			for (Map approve : approves) {
				html.append("<tr  align=\"center\" class=\"trcolor\"><td width=\"30%\">" + approve.get("cutName")
						+ "</td><td width=\"15%\">" + approve.get("liveChargeMan") + "</td><td width=\"15%\">"
						+ approve.get("applyDate") + "</td><td width=\"15%\">" + approve.get("cutTime")
						+ "</td><td width=\"15%\">" + approve.get("contractorName") + "</td></tr>");
			}
			html.append("</table><hr />");
		} else {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>待审申请（0条）</div><br />");
		}
	}

	private void setCutNumberHtml(List<Map> cutNums, StringBuffer cutHtml) {
		cutHtml
				.append("<table cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\" width=\"100%\"><tr align=\"center\" class=\"trcolor\"><td width=\"40%\">代维单位</td><td width=\"15%\">当前割接数</td><td width=\"15%\">待反馈割接数</td><td width=\"15%\">当月割接申请数</td><td width=\"15%\">当月已完成割接数</td></tr>");
		for (int index = 0; index < cutNums.size() - 1; index++) {
			Map cutNum = cutNums.get(index);
			cutHtml.append("<tr class=\"trcolor\"><td>" + cutNum.get("contractorName") + "</td><td>" + cutNum.get("0")
					+ "</td><td>" + cutNum.get("1") + "</td><td>" + cutNum.get("2") + "</td><td>" + cutNum.get("3")
					+ "</td></tr>");
		}
		Map sumNum = cutNums.get(cutNums.size() - 1);
		cutHtml.append("<tr class=\"trcolor\"><td>合计</td><td>" + sumNum.get("currentCutNum") + "</td><td>"
				+ sumNum.get("waitCutNum") + "</td><td>" + sumNum.get("monthCutApproveNum") + "</td><td>"
				+ sumNum.get("finishCutNUm") + "</td></tr>");
		cutHtml.append("</table>");
	}

}
