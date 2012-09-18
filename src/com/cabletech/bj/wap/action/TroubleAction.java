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
	 * ��ѯ����˹���
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
		String approveTroubleTitle = "����˹���";
		String approvalTroubleTitle = "����׼����";
		setTroubleHtml(html, contractor, troubles);
		setTroubleHtml(approvalTroubles, html, contractor, approvalTroubleTitle);
		setTroubleHtml(approveTroubles, html, contractor, approveTroubleTitle);
		setEOMSTroubleHtml(html, eomsTroubles);
		request.setAttribute("APPROVETROUBLE", html);
		return mapping.findForward("listApproveTrouble");
	}

	private void setTroubleNumberHtml(List<Map> num, StringBuffer troubleHtml) {
		troubleHtml
				.append("<table cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\" width=\"100%\"><tr align=\"center\" class=\"trcolor\"><td width=\"40%\">��ά��λ</td><td width=\"15%\">��ǰ������</td><td width=\"15%\">�����еĹ�����</td><td width=\"15%\">���·����Ĺ�����</td><td width=\"15%\">���´�����ɵĹ�����</td></tr>");
		for (int index = 0; index < num.size() - 1; index++) {
			Map troubleNum = num.get(index);
			troubleHtml.append("<tr class=\"trcolor\"><td>" + troubleNum.get("contractorName") + "</td><td>"
					+ troubleNum.get("0") + "</td><td>" + troubleNum.get("1") + "</td><td>" + troubleNum.get("2")
					+ "</td><td>" + troubleNum.get("3") + "</td></tr>");
		}
		Map troubleSum = num.get(num.size() - 1);
		troubleHtml.append("<tr class=\"trcolor\"><td>�ϼ�</td><td>" + troubleSum.get("currentAllTouble") + "</td><td>"
				+ troubleSum.get("currentTrouble") + "</td><td>" + troubleSum.get("monthTroubleNum") + "</td><td>"
				+ troubleSum.get("finishTrouble") + "</td></tr>");
		troubleHtml.append("</table>");
	}

	private void setEOMSTroubleHtml(StringBuffer html, List<Map> eomsTroubles) {
		if (eomsTroubles.size() > 0) {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>��ǰEOMS�·��Ĺ��ϵ�����" + eomsTroubles.size()
					+ "</div><br />");
			html
					.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\"><tr class=\"trcolor\" align=\"center\"><td width=\"50%\">��������</td><td width=\"10%\">�Ƿ��ش����</td><td width=\"10%\">����״̬</td><td width=\"15%\">�����·�ʱ��</td><td width=\"15%\">���Ͽ�ʼʱ��</td></tr>");
			for (Map eomsTrouble : eomsTroubles) {
				html.append("<tr class=\"trcolor\" align=\"center\"><td width=\"50%\">" + eomsTrouble.get("troubleName")
						+ "</td><td width=\"10%\">" + eomsTrouble.get("isGreatTrouble") + "</td><td width=\"10%\">"
						+ eomsTrouble.get("troubleState") + "</td><td width=\"15%\">" + eomsTrouble.get("troubleSendTime")
						+ "</td><td width=\"15%\">" + eomsTrouble.get("troubleStartTime") + "</td></tr>");
			}
			html.append("</table>");
		} else {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>��ǰEOMS�·��Ĺ��ϵ�����0</div><br />");
		}
	}

	private void setTroubleHtml(StringBuffer html, Map contractor, List<Map> troubles) {
		if (troubles.size() > 0) {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>��" + troubles.size()
					+ "����</div><br />");
			html
					.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\"><tr class=\"trcolor\"><td width=\"50%\">��������</td><td width=\"15%\">�Ƿ��ش����</td><td width=\"15%\">�������</td><td width=\"20%\">��ά��λ</td></tr>");
			for (Map trouble : troubles) {
				html.append("<tr class=\"trcolor\"><td width=\"50%\">" + trouble.get("troubleName")
						+ "</td><td width=\"15%\">" + trouble.get("isGreatTrouble") + "</td><td width=\"15%\">"
						+ trouble.get("unitState") + "</td><td width=\"20%\">"
						+ contractor.get(trouble.get("contractorId")) + "</td></tr>");
			}
			html.append("</table> <br />");
		} else {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>��������ϣ�0����</h4></div><br />");
		}
	}

	private void setTroubleHtml(List<Map> approveTroubles, StringBuffer html, Map contractor, String title) {
		if (approveTroubles.size() > 0) {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>" + title + "��" + approveTroubles.size()
					+ "����</div><br />");
			html
					.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\"><tr class=\"trcolor\" align=\"center\"><td width=\"60%\">��������</td><td width=\"15%\">�Ƿ��ش����</td><td width=\"25%\">��ά��λ</td></tr>");
			for (Map approveTrouble : approveTroubles) {
				html.append("<tr class=\"trcolor\" align=\"center\"><td width=\"60%\">"
						+ approveTrouble.get("troubleName") + "</td><td width=\"15%\">"
						+ approveTrouble.get("isGreatTrouble") + "</td><td width=\"25%\">"
						+ contractor.get(approveTrouble.get("contractorId")) + "</td></tr>");
			}
			html.append("</table>");
		} else {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>" + title + "��0����</div><br />");
		}
	}
}
