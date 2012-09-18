package com.cabletech.linepatrol.commons.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.commons.services.TestManBO;

/**
 * 选择计划测试在录入数据时选择测试人员
 * @author Administrator
 *
 */
public class LoadTestManAction extends BaseDispatchAction {

	/**
	 * 查询代维用户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTestMans(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String testMans = request.getParameter("testMans");
		System.out.println("testMans====================== "+testMans);
		String newmans = request.getParameter("newmans");
		System.out.println("newmans====================== "+newmans);
		WebApplicationContext ctx = getWebApplicationContext();
		TestManBO bo = (TestManBO) ctx.getBean("testManBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String userName = request.getParameter("userName");
		List tester = bo.getUsers(userinfo,userName);
		List<String> testManLists = new ArrayList<String>();
		if(StringUtils.isNotBlank(testMans)){
			List<String> testManList = Arrays.asList(testMans.split(","));
			testManLists.addAll(testManList);
		}
		if(StringUtils.isNotBlank(newmans)){
			List<String> newmanList = Arrays.asList(newmans.split(","));
			testManLists.addAll(newmanList);
		}
		request.setAttribute("testMans",testManLists);
		request.setAttribute("contractorUser",tester);
		return mapping.findForward("contractorUsers");
	}
	
	/**
	 * 得到页面选择的用户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void saveUsers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String[] userNames = request.getParameterValues("user");
		String testers = StringUtils.join(userNames,",");
		if(testers==null){
			testers="";
		}
		outPrint(response,testers);
	}
	
}
