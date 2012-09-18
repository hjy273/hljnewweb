package com.cabletech.bj.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.DepartBO;
import com.cabletech.bj.services.InitDisplayBO;
import com.cabletech.commons.web.ClientException;

/**
 * 通讯录
 * 
 * @author Administrator
 * 
 */
public class AddressListAction extends BaseInfoBaseDispatchAction {

	/**
	 * 查询通讯录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward showAddrList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		request.setCharacterEncoding("UTF-8");
		return showAddrDetailList(mapping, form, request, response);
	}

	/**
	 * 查询通讯录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward showAddrDetailList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		List<String> deptlist=new ArrayList<String>();
		DepartBO departBo=new DepartBO();
		deptlist=departBo.getDeptByUser(user);		
		System.out.println(deptlist.get(0).getClass());
		request.setAttribute("deptList", deptlist);
		
		InitDisplayBO listnoticebo = new InitDisplayBO();
		
		String userName = request.getParameter("user_name");
		String mobile = request.getParameter("user_mobile");
		String departName = request.getParameter("depart_name");
		if (userName != null) {
			// userName = new String(userName.getBytes("GBK"), "UTF-8");
			userName = userName.trim();
		}
		if (departName != null) {
			// departName = new String(userName.getBytes("GBK"), "UTF-8");
			departName = departName.trim();
		}
		List list = listnoticebo.getAddressList(user, departName, userName, mobile);
		request.setAttribute("depart_name", departName);
		request.setAttribute("user_name", userName);
		request.setAttribute("user_mobile", mobile);
		request.setAttribute("addrList", list);
		return mapping.findForward("showAddressList");
	}

}
