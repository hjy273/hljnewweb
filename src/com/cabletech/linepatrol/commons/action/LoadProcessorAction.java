package com.cabletech.linepatrol.commons.action;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.cabletech.linepatrol.commons.services.ContractorBO;
import com.cabletech.linepatrol.commons.services.MobileUserBO;
import com.cabletech.linepatrol.commons.services.UserInfoBO;

public class LoadProcessorAction extends BaseDispatchAction {

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
	public ActionForward loadProcessors(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		String inputName = request.getParameter("input_name");
		String spanId = request.getParameter("span_id");
		String existValue = request.getParameter("exist_value");
		String inputType = request.getParameter("input_type");
		String displayType = request.getParameter("display_type");
		String exceptSelf = request.getParameter("except_self");
		String labelName = request.getParameter("label_name");
		request.setAttribute("input_name", inputName);
		request.setAttribute("except_self", exceptSelf);
		request.setAttribute("label_name", labelName);
		request.setAttribute("span_id", spanId);
		request.setAttribute("exist_value", existValue);
		request.setAttribute("input_type", inputType);
		request.setAttribute("display_type", displayType);
		return mapping.findForward("load_processors");
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
	public ActionForward showAllProcessors(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		String queryValue = request.getParameter("query_value");
		String existValue = request.getParameter("exist_value");
		String displayType = request.getParameter("display_type");
		String exceptSelf = request.getParameter("except_self");
		String inputType = request.getParameter("input_type");
		String jsonText = "";
		if ("contractor".equals(displayType)) {
			ContractorBO contractorBo = (ContractorBO) ctx
					.getBean("contractorBo");
			jsonText = contractorBo.loadProcessors(queryValue, userInfo,
					existValue, inputType);
		} else if ("mobile".equals(displayType)) {
			MobileUserBO mobileUserBo = (MobileUserBO) ctx
					.getBean("mobileUserBo");
			jsonText = mobileUserBo.loadProcessors(queryValue, userInfo,
					existValue, inputType);
		} else if ("mobile_contractor".equals(displayType)) {
			UserInfoBO userBo = (UserInfoBO) ctx.getBean("userInfoBo");
			jsonText = userBo.loadMobileAndContractorProcessors(queryValue,
					userInfo, existValue, inputType, exceptSelf);
		} else {
			UserInfoBO userBo = (UserInfoBO) ctx.getBean("userInfoBo");
			jsonText = userBo.loadProcessors(queryValue, userInfo, existValue,
					inputType, exceptSelf);
		}
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
	public ActionForward addProcessors(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		String inputName = request.getParameter("input_name");
		String spanId = request.getParameter("span_id");
		String selectMans = request.getParameter("select_mans");
		if (selectMans != null) {
			String[] userInfos = null;
			if ("1".equals(userInfo.getDeptype())) {
				ContractorBO contractorBo = (ContractorBO) ctx
						.getBean("contractorBo");
				userInfos = contractorBo.parseContractors(selectMans);
			} else {
				MobileUserBO mobileUserBo = (MobileUserBO) ctx
						.getBean("mobileUserBo");
				userInfos = mobileUserBo.parseProcessors(selectMans);
			}
			StringBuffer buf = new StringBuffer("");
			buf.append("<script type=\"text/javascript\">");
			if (inputName != null && inputName.indexOf(",") == -1) {
				buf.append("parent.document.forms[0].");
				buf.append(inputName);
				buf.append(".value='");
				buf.append(userInfos[0]);
				buf.append("';");
			}
			if (inputName != null && inputName.indexOf(",") != -1) {
				String[] inputNames = inputName.split(",");
				for (int i = 0; inputNames != null && i < inputNames.length; i++) {
					buf.append("parent.document.forms[0].");
					buf.append(inputNames[i]);
					buf.append(".value='");
					buf.append(userInfos[i]);
					buf.append("';");
				}
			}
			buf.append("</script>");
			buf.append("<script type=\"text/javascript\">");
			buf.append("parent.document.getElementById('");
			buf.append(spanId);
			buf.append("').innerHTML='");
			buf.append(userInfos[4]);
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

	/**
	 * WAP中载入代维公司单位列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadWapDeparts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		String displayType = request.getParameter("display_type");

		request.setAttribute("action_url", request.getParameter("action_url"));
		request.setAttribute("input_name", request.getParameter("input_name"));
		request.setAttribute("input_type", request.getParameter("input_type"));
		request
				.setAttribute("exist_value", request
						.getParameter("exist_value"));
		request
				.setAttribute("except_self", request
						.getParameter("except_self"));
		request.setAttribute("display_type", displayType);

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		List departList = new ArrayList();
		if ("contractor".equals(displayType)) {
			ContractorBO contractorBo = (ContractorBO) ctx
					.getBean("contractorBo");
			departList = contractorBo.getWapContractorList(userInfo);
		} else if ("mobile".equals(displayType)) {
			MobileUserBO mobileUserBo = (MobileUserBO) ctx
					.getBean("mobileUserBo");
			List userList = mobileUserBo.getWapMobileUserList(userInfo);
			request.setAttribute("user_list", userList);
			return mapping.findForward("load_wap_user");
		} else if ("mobile_contractor".equals(displayType)) {
			UserInfoBO userBo = (UserInfoBO) ctx.getBean("userInfoBo");
			departList = userBo.getWapDepartList();
		} else {
			UserInfoBO userBo = (UserInfoBO) ctx.getBean("userInfoBo");
			departList = userBo.getWapDepartList();
		}
		request.setAttribute("depart_list", departList);
		return mapping.findForward("load_wap_depart");
	}

	/**
	 * WAP中载入代维公司单位列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadWapUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		String displayType = request.getParameter("display_type");
		String exceptSelf = request.getParameter("except_self");
		String[] contractors = request.getParameterValues("contractors");

		request.setAttribute("action_url", request.getParameter("action_url"));
		request.setAttribute("input_name", request.getParameter("input_name"));
		request.setAttribute("input_type", request.getParameter("input_type"));
		request
				.setAttribute("exist_value", request
						.getParameter("exist_value"));
		request.setAttribute("except_self", exceptSelf);
		request.setAttribute("display_type", displayType);

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		List userList = new ArrayList();
		if ("contractor".equals(displayType)) {
			ContractorBO contractorBo = (ContractorBO) ctx
					.getBean("contractorBo");
			userList = contractorBo.getWapUserList(userInfo, contractors);
		} else if ("mobile_contractor".equals(displayType)) {
			UserInfoBO userBo = (UserInfoBO) ctx.getBean("userInfoBo");
			userList = userBo.getWapUserAndContractorPersonList(exceptSelf,
					userInfo, contractors);
		} else {
			UserInfoBO userBo = (UserInfoBO) ctx.getBean("userInfoBo");
			userList = userBo.getWapUserList(exceptSelf, userInfo, contractors);
		}
		request.setAttribute("user_list", userList);
		return mapping.findForward("load_wap_user");
	}

	/**
	 * 完成受理人信息添加
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addWapUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		String inputName = request.getParameter("input_name");
		String actionUrl = request.getParameter("action_url");
		String[] users = request.getParameterValues("user_info");
		Map inputNameMap = new HashMap();
		UserInfoBO userBo = (UserInfoBO) ctx.getBean("userInfoBo");
		inputNameMap = userBo.getInputNameMap(users, inputName);
		request.getSession().setAttribute("INPUT_NAME_MAP", inputNameMap);
		PrintWriter out = response.getWriter();
		out.print("<script type='text/javascript'>");
		out.print("location='" + actionUrl + "';");
		out.print("</script>");
		out.close();
		return null;
	}
}
