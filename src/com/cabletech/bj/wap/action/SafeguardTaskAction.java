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
import com.cabletech.linepatrol.resource.service.RepeaterSectionManager;
import com.cabletech.linepatrol.safeguard.services.SafeguardQueryStatBo;

public class SafeguardTaskAction extends BaseInfoBaseDispatchAction {
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		request.setAttribute("userInfo", userInfo);
		if ("wap".equals(env) && userInfo != null) {
			return mapping.findForward("safeguardTaskIndex");
		} else {
			return mapping.findForward("relogin");
		}
	}

	public ActionForward querySafeguardForm(ActionMapping mapping, ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		SafeguardQueryStatBo bo = (SafeguardQueryStatBo) ctx.getBean("safeguardQueryStatBo");
		List<Map<String,String>> contractorList=bo.getContractorForList(userInfo);
		StringBuffer contractorHtml=new StringBuffer();
		setContractorHtml(contractorList, contractorHtml);
		request.setAttribute("contractorHtml", contractorHtml);
		if (env.equals("wap") && userInfo != null) {
			return mapping.findForward("querySafeguardForm");
		} else {
			return mapping.findForward("relogin");
		}
	}
	
	private void setContractorHtml(List<Map<String, String>> contractorList, StringBuffer contractorHtml) {
		contractorHtml.append("<tr><td>代维单位：</td><td>");
		contractorHtml.append("<select name=\"contractor\" style=\"width:140px\">");
		contractorHtml.append("<option value=\"0\">不限</option>");
		for (Map<String, String> contractor : contractorList) {
			contractorHtml.append("<option value=\"" + contractor.get("contractorId") + "\">"
					+ contractor.get("contractorName") + "</option>");
		}
		contractorHtml.append("</select></td></tr>");
	}
	
	public ActionForward querySafeguardTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		String contractorId=request.getParameter("contractor");
		String safeguardName=request.getParameter("safeguardName");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();

		SafeguardQueryStatBo bo = (SafeguardQueryStatBo) ctx.getBean("safeguardQueryStatBo");
		List<Map> safeguards = bo.QuerySafeguardFromPDA(userInfo,contractorId,safeguardName);

		StringBuffer html = new StringBuffer();
		if (safeguards.size() > 0) {
			html.append("<div style='font-size:12px;font-weight:600;bottom:1'>当前正在执行的保障数为："+safeguards.size()+"</div><br />");
			html
					.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\"><tr class=\"trcolor\" align=\"center\"><td width=\"30%\">保障名称</td><td width=\"10%\">保障范围</td><td width=\"5%\">保障级别</td><td width=\"5%\">人员数</td><td width=\"5%\">车辆数</td><td width=\"5%\">设备数</td><td width=\"30%\">保障时间</td><td width=\"10%\">代维单位</td></tr>");
			for (Map safeguard : safeguards) {
				html.append("<tr class=\"trcolor\" align=\"center\"><td width=\"30%\">" + safeguard.get("safeguardName")
						+ "</td><td width=\"10%\">" + safeguard.get("isAll") + "</td><td width=\"5%\">"
						+ safeguard.get("safeguardLevel") + "</td><td width=\"5%\">" + safeguard.get("planResponder")
						+ "</td><td width=\"5%\">" + safeguard.get("planRespondingUnit") + "</td><td width=\"5%\">"
						+ safeguard.get("equipmentNum") + "</td><td width=\"30%\">" + safeguard.get("startDate")
						+ "至" + safeguard.get("endDate") + "</td><td width=\"10%\">"
						+ safeguard.get("contractorName") + "</td></tr>");
			}
			html.append("</table>");
		} else {
			html.append("<div>当前正在执行的保障数为：0</div>");
		}
		request.setAttribute("SAFEGUARDTASK", html);
		return mapping.findForward("listSafeguardTask");
	}
}
