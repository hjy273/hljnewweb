package com.cabletech.bj.wap.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.resource.model.Pipe;
import com.cabletech.linepatrol.resource.model.RepeaterSection;
import com.cabletech.linepatrol.resource.service.PipeManager;
import com.cabletech.linepatrol.resource.service.RepeaterSectionManager;

public class ResourceAction extends BaseInfoBaseDispatchAction {

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		if ("wap".equals(env) && userInfo != null) {
			return mapping.findForward("resindex");
		} else {
			return mapping.findForward("relogin");
		}
	}

	/**
	 * 加载中继段查询页面
	 * @param mapping
	 * @param from
	 * @param requset
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward queryRepeaterSectionForm(ActionMapping mapping, ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		RepeaterSectionManager repeaterSectionManager = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
		ContractorBO contractorBO=new ContractorBO();
		List<Contractor> contractorList = contractorBO.getAllContractor(userInfo);
		StringBuffer contractorHtml = new StringBuffer();
		setContractorHtml(contractorList, contractorHtml);
		request.setAttribute("contractorHtml", contractorHtml);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		String date=sdf.format(new Date());
		request.setAttribute("date", date);
		if (env.equals("wap") && userInfo != null) {
			return mapping.findForward("queryRepeaterSectionForm");
		} else {
			return mapping.findForward("relogin");
		}
	}

	/**
	 * 中继段查询
	 * @param mapping
	 * @param from
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryRepeaterSection(ActionMapping mapping, ActionForm from, HttpServletRequest request,
			HttpServletResponse response) {
		String segmentName = request.getParameter("segmentName");
		String finishTime = request.getParameter("finishTime");
		String contractorId = request.getParameter("contractor");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		RepeaterSectionManager repeaterSectionManager = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
		List<Map> list = repeaterSectionManager.getRepeaterSectionFromPDA(segmentName, finishTime, userInfo,
				contractorId);
		Map<Object, Object> contractor = repeaterSectionManager.loadContractor();
		StringBuffer html = new StringBuffer();
		setRepeaterSectionHtml(list, contractor, html);
		request.setAttribute("REPEATERSECTION", html);
		return mapping.findForward("listRepeaterSection");
	}

	/**
	 * 加载管道查询页面
	 * @param mapping
	 * @param from
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward queryPipeForm(ActionMapping mapping, ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		PipeManager pipeManager = (PipeManager) ctx.getBean("pipeManager");
		ContractorBO conBO = new ContractorBO();
		List<Contractor> contractorList = conBO.getAllContractor(userInfo);
		StringBuffer contractorHtml = new StringBuffer();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		String date=sdf.format(new Date());
		request.setAttribute("date", date);
		setContractorHtml(contractorList, contractorHtml);
		request.setAttribute("contractorHtml", contractorHtml);
		if (env.equals("wap") && userInfo != null) {
			return mapping.findForward("queryPipeForm");
		} else {
			return mapping.findForward("relogin");
		}
	}

	public ActionForward queryPipe(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String pipeAddress = request.getParameter("pipeAddress");
		String finishTime = request.getParameter("finishTime");
		String contractorId = request.getParameter("contractor");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		PipeManager pipeManager = (PipeManager) ctx.getBean("pipeManager");
		RepeaterSectionManager repeaterSectionManager = (RepeaterSectionManager) ctx.getBean("repeaterSectionManager");
		Map<Object, Object> contractor = repeaterSectionManager.loadContractor();
		List<Map> list = pipeManager.getPipeFromPDA(pipeAddress, userInfo, finishTime, contractorId);
		StringBuffer html = new StringBuffer();
		setPipeHtml(contractor, list, html);
		request.setAttribute("PIPE", html);
		return mapping.findForward("listPipe");
	}

	private void setRepeaterSectionHtml(List<Map> list, Map<Object,Object> contractor, StringBuffer html) {
		if (list.size() > 0) {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>中继段数为："+list.size()+"</div><br />");
			html.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\">");
			html
					.append("<tr class=\"trcolor\" align=\"center\"><td width=\"30%\">中继段名称</td><td width=\"10%\">长度（公里）</td><td width=\"20%\">光缆程式</td><td width=\"10%\">代维公司</td><td width=\"10%\">光缆级别</td><td width=\"25%\">交维时间</td></tr>");
			for (Map repeaterSection : list) {
				html
						.append("<tr class=\"trcolor\" align=\"center\"><td>" + repeaterSection.get("segmentName").toString()
								+ "</td><td>" + repeaterSection.get("grossLength").toString() + "</td><td>"
								+ repeaterSection.get("fiberType").toString() + "</td><td>"
								+ contractor.get(repeaterSection.get("maintenanceId").toString()) + "</td><td>"
								+ repeaterSection.get("lable").toString() + "</td><td>" + repeaterSection.get("finishTime").toString()
								+ "</td></tr>");
			}
			html.append("</table>");
		} else {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>没有符合条件的中继段！</div>");
		}
	}

	private void setPipeHtml(Map<Object,Object> contractor, List<Map> list, StringBuffer html) {
		if (list.size() > 0) {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>管道数为："+list.size()+"</div><br />");
			html.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\">");
			html
					.append("<tr class=\"trcolor\" align=\"center\"><td width=\"30%\">管道地点</td><td width=\"10%\">沟长度(公里)</td><td width=\"10%\">孔长度(公里)</td><td width=\"20%\">代维</td><td width=\"20%\">交维时间</td><td width=\"10%\">产权属性</td></tr>");
			for (Map pipe : list) {
				html.append("<tr class=\"trcolor\" align=\"center\"><td>" + pipe.get("pipeAddress") + "</td><td >"
						+ pipe.get("pipeLengthChannel") + "</td><td >"+ pipe.get("pipeLengthHole") + "</td><td>" + contractor.get(pipe.get("maintenanceId"))
						+ "</td><td>" + pipe.get("finishTIme") + "</td><td>" + pipe.get("lable") + "</td></tr>");
			}
			html.append("</table>");
		} else {
			html.append("<div style='font-size:14px;font-weight:600;bottom:1'>没有符合条件的管道！</div>");
		}
	}

	private void setContractorHtml(List<Contractor> contractorList, StringBuffer contractorHtml) {
		contractorHtml.append("<tr><td>代维单位：</td><td>");
		contractorHtml.append("<select name=\"contractor\" style=\"width:140px\">");
		contractorHtml.append("<option value=\"0\">不限</option>");
		for (Contractor contractor : contractorList) {
			contractorHtml.append("<option value=\"" + contractor.getContractorID() + "\">"
					+ contractor.getContractorName() + "</option>");
		}
		contractorHtml.append("</select></td></tr>");
	}
}
