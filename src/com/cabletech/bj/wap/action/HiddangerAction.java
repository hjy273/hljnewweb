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
import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.hiddanger.service.HiddangerConstant;
import com.cabletech.linepatrol.hiddanger.service.HiddangerRegistManager;
import com.cabletech.linepatrol.resource.service.RepeaterSectionManager;

public class HiddangerAction extends BaseInfoBaseDispatchAction {
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		request.setAttribute("userInfo", userInfo);
		if ("wap".equals(env) && userInfo != null) {
			return mapping.findForward("hiddangerIndex");
		} else {
			return mapping.findForward("relogin");
		}
	}
	
	public ActionForward queryHiddangerNum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager manager = (HiddangerRegistManager) ctx.getBean("hiddangerRegistManager");
		StringBuffer html=new StringBuffer();
		html.append("<div style='font-size:14px;font-weight:600;bottom:1'>隐患等级：一级</div><br />");
		setHiddangerHtml(manager.getHiddangerNumFromPDA(userInfo,"1"), html);
		html.append("<div style='font-size:14px;font-weight:600;bottom:1'>隐患等级：二级</div><br />");
		setHiddangerHtml(manager.getHiddangerNumFromPDA(userInfo,"2"), html);
		html.append("<div style='font-size:14px;font-weight:600;bottom:1'>隐患等级：三级</div><br />");
		setHiddangerHtml(manager.getHiddangerNumFromPDA(userInfo,"3"), html);
		html.append("<div style='font-size:14px;font-weight:600;bottom:1'>隐患等级：四级</div><br />");
		setHiddangerHtml(manager.getHiddangerNumFromPDA(userInfo,"4"), html);
		html.append("<div style='font-size:14px;font-weight:600;bottom:1'>隐患等级：忽略</div><br />");
		setHiddangerHtml(manager.getHiddangerNumFromPDA(userInfo,"0"), html);
		request.setAttribute("HIDDANGERNUM", html);
		return mapping.findForward("listHiddangerNum");
	}

	public ActionForward queryHiddangerSpecialForm(ActionMapping mapping, ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager manager = (HiddangerRegistManager) ctx.getBean("hiddangerRegistManager");
		ContractorBO conBO=new ContractorBO();
		List<Contractor> contractorList=conBO.getAllContractor(userInfo);
		StringBuffer contractorHtml=new StringBuffer();
		setHtml(contractorList, contractorHtml);
		
		request.setAttribute("contractorHtml", contractorHtml);
		if (env.equals("wap") && userInfo != null) {
			return mapping.findForward("querySpecialForm");
		} else {
			return mapping.findForward("relogin");
		}
	}

	private void setHtml(List<Contractor> contractorList, StringBuffer contractorHtml) {
		contractorHtml.append("<table width=\"100%\" border=\"0\" style=\"text-align:center;\"><tr><td>代维单位：</td><td><select name=\"contractor\" style=\"width:140px\">");
		contractorHtml.append("<option value=\"0\">不限</option>");
		for(Contractor contractor:contractorList){
			contractorHtml.append("<option value=\""+contractor.getContractorID()+"\">"+contractor.getContractorName()+"</option>");
		}
		contractorHtml.append("</select></td></tr>");
		contractorHtml.append("<tr><td>隐患级别：</td><td><select name=\"hiddangerLevel\" style=\"width:140px\">");
		contractorHtml.append("<option value=\"\">不限</option>");
		contractorHtml.append("<option value=\"0\">忽略</option>");
		contractorHtml.append("<option value=\"1\">一级</option>");
		contractorHtml.append("<option value=\"2\">二级</option>");
		contractorHtml.append("<option value=\"3\">三级</option>");
		contractorHtml.append("<option value=\"4\">四级</option>");
		contractorHtml.append("</select></td></tr></table>");
	}
	
	public ActionForward queryHiddanger(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String contractorId=request.getParameter("contractor");
		String hiddangerLevel=request.getParameter("hiddangerLevel");
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager manager = (HiddangerRegistManager) ctx.getBean("hiddangerRegistManager");
		List<Map> specials = manager.getSpecialFromPDA(userInfo,contractorId,hiddangerLevel);

		StringBuffer html = new StringBuffer();
		if (specials.size() > 0) {
			html.append("<div style='font-size:12px;font-weight:600;bottom:1'>当前盯防（"+specials.size()+"条）</div><br />");
			html.append("<table cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\" width=\"100%\"><tr class=\"trcolor\"><td width=\"30%\">隐患名称</td><td width=\"10%\">隐患级别</td><td width=\"10%\">影响范围（米）</td><td width=\"10%\">盯防组</td><td width=\"10%\">盯防负责人</td><td width=\"15%\">负责人联系方式</td><td width=\"15%\">维护单位</td></tr>");
			for (Map special : specials) {
				html.append("<tr class=\"trcolor\"><td>" + special.get("hiddangerName") + "</td><td>" + special.get("hiddangerLevel")
						+ "</td><td>" + special.get("tanceToCable") + "</td><td>" + special.get("patrolName") + "</td><td>" + special.get("watchPrincipal") + "</td><td>"
						+ special.get("watchPrincipalPhone") + "</td><td>" + special.get("contractorName")
						+ "</td></tr>");
			}
			html.append("</table>");
		} else {
			html.append("<div style='font-size:12px;font-weight:600;bottom:1'>当前盯防（0条）</div>");
		}
		request.setAttribute("SPECIAL", html);
		return mapping.findForward("listSpecial");
	}
	
	private void setHiddangerHtml(List<Map> num, StringBuffer hiddangerHtml) {
		hiddangerHtml.append("<table cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\" width=\"100%\"><tr align=\"center\" class=\"trcolor\"><td width=\"40%\">代维单位</td><td width=\"15%\">当前隐患数</td><td width=\"15%\">正在盯防的隐患数</td><td width=\"15%\">当月上报的隐患数</td><td width=\"15%\">当月处理完成的隐患数</td></tr>");
		for (int index=0;index<num.size()-1;index++) {
			Map hiddangerNum=num.get(index);
			hiddangerHtml.append("<tr class=\"trcolor\"><td>" + hiddangerNum.get("contractorName") + "</td><td>"
					+ hiddangerNum.get("0") + "</td><td>" + hiddangerNum.get("1") + "</td><td>"
					+ hiddangerNum.get("2") + "</td><td>" + hiddangerNum.get("3") + "</td></tr>");
		}
		Map sum=num.get(num.size()-1);
		hiddangerHtml.append("<tr class=\"trcolor\"><td>合计</td><td>"
				+ sum.get("nowHiddanger") + "</td><td>" + sum.get("markHiddanger") + "</td><td>"
				+ sum.get("monthApprove") + "</td><td>" + sum.get("colseApproved") + "</td></tr>");
		hiddangerHtml.append("</table>");
	}
}
