package com.cabletech.linepatrol.commons.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.commons.services.ContractorBO;

public class LoadContractorAction extends BaseDispatchAction {

	/**
	 * 根据输入查询值获取代维公司信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadContractors(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String inputName = request.getParameter("input_name");
		String spanId = request.getParameter("span_id");
		String existValue = request.getParameter("exist_value");
		request.setAttribute("input_name", inputName);
		request.setAttribute("span_id", spanId);
		request.setAttribute("exist_value", existValue);
		return mapping.findForward("load_contractors");
	}

	/**
	 * 完成所有代维公司的树状列表输出
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showAllContractors(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		ContractorBO contractorBo = (ContractorBO) ctx.getBean("contractorBo");
		String queryValue = request.getParameter("query_value");
		String existValue = request.getParameter("exist_value");
		String jsonText = contractorBo.loadContractors(queryValue, userInfo,
				existValue);
		response.setHeader("X-JSON", jsonText);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.write(jsonText);
		out.close();
		return null;
	}

	/**
	 * 完成代维公司信息添加
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addContractors(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		WebApplicationContext ctx = getWebApplicationContext();
		ContractorBO contractorBo = (ContractorBO) ctx.getBean("contractorBo");
		String inputName = request.getParameter("input_name");
		String spanId = request.getParameter("span_id");
		String selectMans = request.getParameter("select_mans");
		if (selectMans != null) {
			String[] contractorInfo = contractorBo.parseContractors(selectMans);
			StringBuffer buf = new StringBuffer("");
			buf.append("<script type=\"text/javascript\">");
			if (inputName != null && inputName.indexOf(",") == -1) {
				buf.append("parent.document.forms[0].");
				buf.append(inputName);
				buf.append(".value='");
				buf.append(contractorInfo[0]);
				buf.append("';");
			}
			if (inputName != null && inputName.indexOf(",") != -1) {
				String[] inputNames = inputName.split(",");
				for (int i = 0; inputNames != null && i < inputNames.length; i++) {
					buf.append("parent.document.forms[0].");
					buf.append(inputNames[i]);
					buf.append(".value='");
					buf.append(contractorInfo[i]);
					buf.append("';");
				}
			}
			buf.append("</script>");
			buf.append("<script type=\"text/javascript\">");
			buf.append("parent.document.getElementById('");
			buf.append(spanId);
			buf.append("').innerHTML='");
			buf.append(contractorInfo[4]);
			buf.append("';");
			buf.append("parent.document.getElementById('" + spanId
					+ "HideDlg').click();");
			buf.append("</script>");
			System.out.println(buf.toString());
			response.setCharacterEncoding("GBK");
			PrintWriter out = response.getWriter();
			out.print(buf.toString());
			out.close();
		}
		return null;
	}
}
