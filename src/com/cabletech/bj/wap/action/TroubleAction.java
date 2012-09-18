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
import com.cabletech.linepatrol.trouble.services.TroubleConstant;
import com.cabletech.linepatrol.trouble.services.TroubleQueryStatBO;

public class TroubleAction extends BaseInfoBaseDispatchAction {
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		request.setAttribute("userInfo", userInfo);
		if ("wap".equals(env) && userInfo != null) {
			return mapping.findForward("troubleIndex");
		} else {
			return mapping.findForward("relogin");
		}
	}

	public ActionForward queryTroubleNum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQueryStatBO bo = (TroubleQueryStatBO) ctx.getBean("troubleQueryStatBO");
		List<Map> troubleNums = bo.queryTroubleNumFromPDA(userInfo);
		StringBuffer troubleHtml = new StringBuffer();
		setTroubleNumberHtml(troubleNums, troubleHtml);
		request.setAttribute("troubleNum", troubleHtml);
		return mapping.findForward("queryTroubleNum");
	}

	/**
	 * 查询待审核故障
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward queryApprove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQueryStatBO bo = (TroubleQueryStatBO) ctx.getBean("troubleQueryStatBO");
		StringBuffer html = new StringBuffer();
		Map contractor = bo.loadContractor();
		List<Map> eomsTroubles = bo.queryEOMSTroubleFromPDA(userInfo);
		List<Map> troubles = bo.queryTroubleFromPDA(userInfo);
		List<Map> approvalTroubles = bo.queryApprovalTroubleFromPDA(userInfo);
		List<Map> approveTroubles = bo.queryApproveTroubleFromPDA(userInfo);
		String approveTroubleTitle = "待审核故障";
		String approvalTroubleTitle = "待核准故障";
		setTroubleHtml(html, contractor, troubles);
		setTroubleHtml(approvalTroubles, html, contractor, approvalTroubleTitle);
		setTroubleHtml(approveTroubles, html, contractor, approveTroubleTitle);
		setEOMSTroubleHtml(html, eomsTroubles);
		request.setAttribute("APPROVETROUBLE", html);
		return mapping.findForward("listApproveTrouble");
	}

	private void setTroubleNumberHtml(List<Map> num, StringBuffer troubleHtml) {
		troubleHtml
				.append("<table cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\" width=\"100%\"><tr align=\"center\" class=\"trcolor\"><td width=\"40%\">代维单位</td><td width=\"15%\">当前故障数</td><td width=\"15%\">处理中的故障数</td><td width=\"15%\">当月发生的故障数</td><td width=\"15%\">当月处理完成的故障数</td></tr>");
		for (int index = 0; index < num.size() - 1; index++) {
			Map troubleNum = num.get(index);
			troubleHtml.append("<tr class=\"trcolor\"><td>" + troubleNum.get("contractorName") + "</td><td>"
					+ troubleNum.get("0") + "</td><td>" + troubleNum.get("1") + "</td><td>" + troubleNum.get("2")
					+ "</td><td>" + troubleNum.get("3") + "</td></tr>");
		}
		Map troubleSum = num.get(num.size() - 1);
		troubleHtml.append("<tr class=\"trcolor\"><td>合计</td><td>" + troubleSum.get("currentAllTouble") + "</td><td>"
				+ troubleSum.get("currentTrouble") + "</td><td>" + troubleSum.get("monthTroubleNum") + "</td><td>"
				+ troubleSum.get("finishTrouble") + "</td></tr>");
		troubleHtml.append("</table>");
	}

	private void setEOMSTroubleHtml(StringBuffer html, List<Map> eomsTroubles) {
		if (eomsTroubles.size() > 0) {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>当前EOMS下发的故障单数：" + eomsTroubles.size()
					+ "</div><br />");
			html
					.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\"><tr class=\"trcolor\" align=\"center\"><td width=\"50%\">故障名称</td><td width=\"10%\">是否重大故障</td><td width=\"10%\">故障状态</td><td width=\"15%\">故障下发时间</td><td width=\"15%\">故障开始时间</td></tr>");
			for (Map eomsTrouble : eomsTroubles) {
				html.append("<tr class=\"trcolor\" align=\"center\"><td width=\"50%\">" + eomsTrouble.get("troubleName")
						+ "</td><td width=\"10%\">" + eomsTrouble.get("isGreatTrouble") + "</td><td width=\"10%\">"
						+ eomsTrouble.get("troubleState") + "</td><td width=\"15%\">" + eomsTrouble.get("troubleSendTime")
						+ "</td><td width=\"15%\">" + eomsTrouble.get("troubleStartTime") + "</td></tr>");
			}
			html.append("</table>");
		} else {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>当前EOMS下发的故障单数：0</div><br />");
		}
	}

	private void setTroubleHtml(StringBuffer html, Map contractor, List<Map> troubles) {
		if (troubles.size() > 0) {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>（" + troubles.size()
					+ "条）</div><br />");
			html
					.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\"><tr class=\"trcolor\"><td width=\"50%\">故障名称</td><td width=\"15%\">是否重大故障</td><td width=\"15%\">故障情况</td><td width=\"20%\">代维单位</td></tr>");
			for (Map trouble : troubles) {
				html.append("<tr class=\"trcolor\"><td width=\"50%\">" + trouble.get("troubleName")
						+ "</td><td width=\"15%\">" + trouble.get("isGreatTrouble") + "</td><td width=\"15%\">"
						+ trouble.get("unitState") + "</td><td width=\"20%\">"
						+ contractor.get(trouble.get("contractorId")) + "</td></tr>");
			}
			html.append("</table> <br />");
		} else {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>待处理故障（0条）</h4></div><br />");
		}
	}

	private void setTroubleHtml(List<Map> approveTroubles, StringBuffer html, Map contractor, String title) {
		if (approveTroubles.size() > 0) {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>" + title + "（" + approveTroubles.size()
					+ "条）</div><br />");
			html
					.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\"><tr class=\"trcolor\" align=\"center\"><td width=\"60%\">故障名称</td><td width=\"15%\">是否重大故障</td><td width=\"25%\">代维单位</td></tr>");
			for (Map approveTrouble : approveTroubles) {
				html.append("<tr class=\"trcolor\" align=\"center\"><td width=\"60%\">"
						+ approveTrouble.get("troubleName") + "</td><td width=\"15%\">"
						+ approveTrouble.get("isGreatTrouble") + "</td><td width=\"25%\">"
						+ contractor.get(approveTrouble.get("contractorId")) + "</td></tr>");
			}
			html.append("</table>");
		} else {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>" + title + "（0条）</div><br />");
		}
	}
}
