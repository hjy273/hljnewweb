package com.cabletech.linepatrol.commons.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.commons.services.UserInfoBO;

public class LoadApproverAction extends BaseDispatchAction {

	/**
	 * 根据输入查询值获取线维中心部门人员信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		String queryValue = request.getParameter("query_value");
		String inputName = request.getParameter("input_name");
		String spanId = request.getParameter("span_id");
		String existValue = request.getParameter("exist_value");
		String inputType = request.getParameter("input_type");
		String notAllowValue = request.getParameter("not_allow_value");
		String displayType = request.getParameter("display_type");
		String departId = request.getParameter("depart_id");
		String exceptSelf = request.getParameter("except_self");
		UserInfoBO userInfoBo = (UserInfoBO) ctx.getBean("userInfoBo");
		List list = userInfoBo.loadApprovers(queryValue, userInfo, existValue,
				notAllowValue, displayType, departId, exceptSelf);
		request.setAttribute("approver_list", list);
		request.setAttribute("input_name", inputName);
		request.setAttribute("input_type", inputType);
		request.setAttribute("not_allow_value", notAllowValue);
		request.setAttribute("span_id", spanId);
		request.setAttribute("display_type", displayType);
		request.setAttribute("depart_id", departId);
		request.setAttribute("except_self", exceptSelf);
		return mapping.findForward("load_approvers");
	}

	/**
	 * 完成线维中心部门操作人员选择信息的添加
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		String inputName = request.getParameter("input_name");
		String spanId = request.getParameter("span_id");
		String[] approver = request.getParameterValues("approver");
		if (approver != null) {
			String[] approverId = new String[approver.length];
			String[] approverName = new String[approver.length];
			String[] approverTel = new String[approver.length];
			String approverIds = "";
			String approverNames = "";
			String approverTels = "";
			for (int i = 0; i < approver.length; i++) {
				if (approver[i] != null && approver[i].indexOf("-") != -1) {
					approverId[i] = approver[i].split("-")[0];
					approverName[i] = approver[i].split("-")[1];
					if (approver[i].split("-").length >= 3) {
						approverTel[i] = approver[i].split("-")[2];
					} else {
						approverTel[i] = "";
					}
					approverIds = approverIds + approverId[i];
					approverNames = approverNames + approverName[i];
					approverTels = approverTels + approverTel[i];
					if (i != approver.length - 1) {
						approverIds = approverIds + ",";
						approverNames = approverNames + ",";
						approverTels = approverTels + ",";
					}
				}
			}
			StringBuffer buf = new StringBuffer("");
			buf.append("<script type=\"text/javascript\">");
			if (inputName != null && inputName.indexOf(",") == -1) {
				buf.append("parent.document.forms[0].");
				buf.append(inputName);
				buf.append(".value='");
				buf.append(approverIds);
				buf.append("';");
			}
			if (inputName != null && inputName.indexOf(",") != -1) {
				String[] inputNames = inputName.split(",");

				buf.append("parent.document.forms[0].");
				buf.append(inputNames[0]);
				buf.append(".value='");
				buf.append(approverIds);
				buf.append("';");

				buf.append("parent.document.forms[0].");
				buf.append(inputNames[1]);
				buf.append(".value='");
				buf.append(approverTels);
				buf.append("';");

			}
			buf.append("parent.document.getElementById('");
			buf.append(spanId);
			buf.append("').innerHTML='");
			buf.append(approverNames);
			buf.append("';");
			buf.append("parent.document.getElementById('" + spanId
					+ "HideDlg').click();");
			buf.append("</script>");
			System.out.println(buf.toString());
			response.setCharacterEncoding("GBK");
			PrintWriter out = response.getWriter();
			out.print(buf.toString());
			out.close();
		} else {
			StringBuffer buf = new StringBuffer("");
			buf.append("<script type=\"text/javascript\">");
			if (inputName != null && inputName.indexOf(",") == -1) {
				buf.append("parent.document.forms[0].");
				buf.append(inputName);
				buf.append(".value='';");
			}
			if (inputName != null && inputName.indexOf(",") != -1) {
				String[] inputNames = inputName.split(",");

				buf.append("parent.document.forms[0].");
				buf.append(inputNames[0]);
				buf.append(".value='';");

				buf.append("parent.document.forms[0].");
				buf.append(inputNames[1]);
				buf.append(".value='';");

			}
			buf.append("parent.document.getElementById('");
			buf.append(spanId);
			buf.append("').innerHTML='';");
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

	/**
	 * 根据输入查询值获取线维中心部门人员信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadWapApprovers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		String actionUrl = request.getParameter("action_url");
		String objectId = request.getParameter("object_id");
		String objectType = request.getParameter("object_type");
		String approverType = request.getParameter("approver_type");
		String approverInputName = request.getParameter("approver_input_name");
		String readerInputName = request.getParameter("reader_input_name");
		String existValue = request.getParameter("exist_value");
		String displayType = request.getParameter("display_type");
		String departId = request.getParameter("depart_id");
		String exceptSelf = request.getParameter("except_self");
		UserInfoBO userInfoBo = (UserInfoBO) ctx.getBean("userInfoBo");
		List list = userInfoBo.loadWapApprovers(userInfo, existValue,
				displayType, departId, exceptSelf, objectId, objectType,
				approverType);
		request.setAttribute("approver_list", list);
		request.setAttribute("action_url", actionUrl);
		request.setAttribute("approver_type", approverType);
		request.setAttribute("approver_input_name", approverInputName);
		request.setAttribute("reader_input_name", readerInputName);
		request.setAttribute("display_type", displayType);
		request.setAttribute("depart_id", departId);
		request.setAttribute("except_self", exceptSelf);
		return mapping.findForward("load_wap_approvers");
	}

	/**
	 * 完成线维中心部门操作人员选择信息的添加
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addWapApprovers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfoBO userInfoBo = (UserInfoBO) ctx.getBean("userInfoBo");
		Map map = new HashMap();
		String actionUrl = request.getParameter("action_url");
		String approverInputName = request.getParameter("approver_input_name");
		String readerInputName = request.getParameter("reader_input_name");
		String[] approver = request.getParameterValues("approver");
		String[] reader = request.getParameterValues("reader");
		userInfoBo.parseUserInfo(map, approverInputName, approver);
		request.getSession().setAttribute("APPROVER_INPUT_NAME_MAP", map);
		map = new HashMap();
		userInfoBo.parseUserInfo(map, readerInputName, reader);
		request.getSession().setAttribute("READER_INPUT_NAME_MAP", map);
		PrintWriter out = response.getWriter();
		out.print("<script type='text/javascript'>");
		out.print("location='" + actionUrl + "';");
		out.print("</script>");
		out.close();
		return null;
	}
}
