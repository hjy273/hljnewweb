package com.cabletech.linepatrol.commons.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.commons.services.HideDangerBO;

public class LoadHideDangerAction extends BaseDispatchAction {

	/**
	 * 根据输入查询值获取最近的隐患信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadHideDangers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String queryValue = request.getParameter("query_value");
		String beginTime = request.getParameter("begin_time");
		String endTime = request.getParameter("end_time");
		String inputName = request.getParameter("input_name");
		String spanId = request.getParameter("span_id");
		String accidents = request.getParameter("accidents");
		HideDangerBO hideDangerBO = (HideDangerBO) ctx.getBean("hideDangerBO");
		List list = hideDangerBO.loadHideDangers(userInfo, queryValue,
				beginTime, endTime, accidents);
		request.setAttribute("input_name", inputName);
		request.setAttribute("span_id", spanId);
		request.setAttribute("begin_time", beginTime);
		request.setAttribute("end_time", endTime);
		request.setAttribute("accidents", accidents);
		request.setAttribute("hide_danger_list", list);
		return mapping.findForward("load_hide_dangers");
	}

	/**
	 * 完成最近隐患的关联信息添加
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addHideDangers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		String inputName = request.getParameter("input_name");
		String spanId = request.getParameter("span_id");
		String[] hideDanger = request.getParameterValues("hide_danger");
		if (hideDanger != null) {
			String[] hideDangerId = new String[hideDanger.length];
			String[] hideDangerName = new String[hideDanger.length];
			String hideDangerIds = "";
			String hideDangerNames = "";
			for (int i = 0; i < hideDanger.length; i++) {
				if (hideDanger[i] != null && hideDanger[i].indexOf("-") != -1) {
					hideDangerId[i] = hideDanger[i].split("-")[0];
					hideDangerName[i] = hideDanger[i].split("-")[1];
					hideDangerIds = hideDangerIds + hideDangerId[i];
					hideDangerNames = hideDangerNames + hideDangerName[i];
					if (i != hideDanger.length - 1) {
						hideDangerIds = hideDangerIds + ",";
						hideDangerNames = hideDangerNames + ",";
					}
				}
			}
			StringBuffer buf = new StringBuffer("");
			buf.append("<script type=\"text/javascript\">");
			buf.append("parent.document.forms[0].");
			buf.append(inputName);
			buf.append(".value='");
			buf.append(hideDangerIds);
			buf.append("';");
			buf.append("parent.document.getElementById('");
			buf.append(spanId);
			buf.append("').innerHTML='");
			buf.append(hideDangerNames);
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
