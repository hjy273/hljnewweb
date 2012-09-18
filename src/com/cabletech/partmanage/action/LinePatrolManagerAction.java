package com.cabletech.partmanage.action;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.fsmanager.bean.CableBean;
import com.cabletech.partmanage.beans.LinePatrolManagerBean;
import com.cabletech.partmanage.dao.LinePatrolManagerDao;
import com.cabletech.power.CheckPower;

public class LinePatrolManagerAction extends BaseDispatchAction {

	private static Logger logger = Logger
	.getLogger(LinePatrolManagerAction.class.getName());

	public ActionForward addLinePatrolRe(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		LinePatrolManagerDao dao = new LinePatrolManagerDao();

		request.setAttribute("username", userinfo.getUserName());
		request.setAttribute("userid", userinfo.getUserID());
		request.setAttribute("deptName", userinfo.getDeptName());

		// 获得服务器的当前时间
		Date nowDate = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.DATE_FIELD);
		String data = df.format(nowDate);
		request.setAttribute("date", data);

//		获得监理单位
		List contractList = dao.getContractList();
		System.out.println("kkk     " +contractList.size());
		request.setAttribute("contractList", contractList);

//		获得材料信息
		List partList = dao.getLinePatrolList();
		request.setAttribute("partList", partList);

//		获得存放地点信息
		List addresList = dao.getAddressList(userinfo.getDeptID());
		request.setAttribute("addresList", addresList);

		return mapping.findForward("addLinePatrolRe");
	}

	public ActionForward addLinePatrolInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean) form;
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		String id = this.getDbService().getSeq("LINEPATROL_MT_NEW", 10);
		System.out.println("getContractorid     "+bean.getContractorid());
		bean.setId(id);
		bean.setCerator(userinfo.getUserID());
		LinePatrolManagerDao dao = new LinePatrolManagerDao();
		if(dao.addLinePatrolInfo(bean,userinfo)){
			return forwardInfoPage( mapping, request, "80101" );
		}else{
			return forwardErrorPage( mapping, request, "error" );
		}
	}

	public ActionForward queryLinePatrolRe(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("queryLinePatrolRe");
	}

	public ActionForward queryLinePatrolInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean) form;
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		request.getSession().setAttribute("S_BACK_URL",null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		LinePatrolManagerDao dao = new LinePatrolManagerDao();
		List linePatrolList = dao.queryLinePatrol(bean,userinfo.getUserID());
		request.getSession().setAttribute("linePatrolList", linePatrolList);
		request.setAttribute("type", userinfo.getDeptype());

		this.setPageReset(request);

		return mapping.findForward("queryLinePatrolInfo");
	}

	public ActionForward viewLinePatrolByID(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean)form;
		String id = request.getParameter("id");
		LinePatrolManagerDao dao = new LinePatrolManagerDao();
		bean = dao.viewLinePatrolById(id,bean);
		request.setAttribute("bean", bean);
		LinePatrolManagerBean lbean = new LinePatrolManagerBean();
		lbean = dao.getLineAssessDept1(lbean, id);
		request.setAttribute("lbean", lbean);
		return mapping.findForward("viewLinePatrolByID");
	}

	public ActionForward deleteLinePatrol(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		LinePatrolManagerDao dao = new LinePatrolManagerDao();
		if(dao.delLinePatrolById(id)){
			String strQueryString = getTotalQueryString("method=queryLinePatrolInfo",(LinePatrolManagerBean)request.getSession().getAttribute("theQueryBean"));
			Object[] args = getURLArgs("/WebApp/LinePatrolManagerAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
			return forwardInfoPage( mapping, request, "801del",null,args);
		}else{
			return forwardErrorPage( mapping, request, "error" );
		}
	}

	public ActionForward editLinePatrol(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean)form;
		String id = request.getParameter("id");
		LinePatrolManagerDao dao = new LinePatrolManagerDao();
		bean = dao.getLinePatrolById(id, bean);
		request.setAttribute("bean", bean);
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");

		if (userinfo.getDeptype().equals("1")) { // 如果是移动公司是不允许添加材料申请的
			return forwardErrorPage(mapping, request, "partstockerror");
		}
		request.setAttribute("username", userinfo.getUserName());
		request.setAttribute("userid", userinfo.getUserID());
		request.setAttribute("deptName", userinfo.getDeptName());

		// 获得服务器的当前时间
		Date nowDate = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.DATE_FIELD);
		String data = df.format(nowDate);
		request.setAttribute("date", data);

		// 获得监理单位
		List contractList = dao.getContractList();
		request.setAttribute("contractList", contractList);

		// 获得材料信息
		List partList = dao.getLinePatrolList();
		request.setAttribute("partList", partList);

		// 获得存放地点信息
		List addresList = dao.getAddressList(userinfo.getDeptID());
		request.setAttribute("addresList", addresList);

		List modellist = dao.getPatrolModellist();
		List baseList = dao.getPatorlBaselist();
		request.setAttribute("modellist", modellist);
		request.setAttribute("baseList", baseList);

		return mapping.findForward("editLinePatrol");
	}

	public String getTotalQueryString(String queryString,LinePatrolManagerBean bean){
		if (bean.getType() != null){
			queryString = queryString + bean.getType();
		}
		if (bean.getBegintime()!= null){
			queryString = queryString + "&getBegintime=" + bean.getBegintime();
		}
		if (bean.getEndtime() != null){
			queryString = queryString + "&id=" + bean.getEndtime();
		}

		return queryString;
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modLinePatrolInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean)form;
		LinePatrolManagerDao dao = new LinePatrolManagerDao();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");

		bean.setCerator(userinfo.getUserID());
		if(dao.modLinePatrolInfo(bean,userinfo)){
			String strQueryString = getTotalQueryString("method=queryLinePatrolInfo",(LinePatrolManagerBean)request.getSession().getAttribute("theQueryBean"));
			Object[] args = getURLArgs("/WebApp/LinePatrolManagerAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
			return forwardInfoPage( mapping, request, "801mod",null,args);
		}else{
			return forwardErrorPage( mapping, request, "error" );
		}
	}

	public ActionForward queryLinePatorAssess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean)form;
		LinePatrolManagerDao dao = new LinePatrolManagerDao();
		request.getSession().setAttribute("S_BACK_URL",null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		request.setAttribute("type", userinfo.getDeptype());
		if(userinfo.getDeptype().equals("1")){
			List assesslist = dao.getAssessByDep2();
			request.getSession().setAttribute("assesslist", assesslist);
			this.setPageReset(request);
			return mapping.findForward("linePatorAssess3List");
		}else if(userinfo.getDeptype().equals("3")){
			List assesslist = dao.getAssessByDepart3(userinfo.getDeptID());
			request.getSession().setAttribute("assesslist", assesslist);
			this.setPageReset(request);
			return mapping.findForward("linePatorAssess3List");
		}else{
			return forwardErrorPage(mapping, request, "partstockerror");
		}

	}

	public ActionForward checkLinePatrolByCon(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean)form;
		String id = request.getParameter("id");
		LinePatrolManagerDao dao = new LinePatrolManagerDao();
		bean= dao.viewLinePatrolById(id, bean);

		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");

		request.setAttribute("username", userinfo.getUserName());
		request.setAttribute("userid", userinfo.getUserID());
		request.setAttribute("deptname", userinfo.getDeptName());
		// 获得服务器的当前时间
		Date nowDate = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.DATE_FIELD);
		String data = df.format(nowDate);
		request.setAttribute("date", data);
//		if(userinfo.getDeptype().equals("1")){
		bean = dao.getLineAssess(bean, id);
//		}
		LinePatrolManagerBean lbean = new LinePatrolManagerBean();
		lbean = dao.getLineAssessDept1(lbean, id);
		request.setAttribute("lbean", lbean);
		request.setAttribute("bean", bean);
		request.setAttribute("type", userinfo.getDeptype());
		
		List users = dao.getUserInfos();
		request.setAttribute("users",users);
		return mapping.findForward("checkLinePatrolByCon");
	}

	public ActionForward addLinePatrolAssessInfo(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userids = request.getParameter("userids");
		System.out.println("userids============================== "+userids);
		
		LinePatrolManagerBean bean = (LinePatrolManagerBean)form;
		LinePatrolManagerDao dao = new LinePatrolManagerDao();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String id = this.getDbService().getSeq("LINEPATROL_MT_ASSESS", 10);
		bean.setId(id);
//		bean.setAssesor(userinfo.getUserID());
		if(dao.addLinePatrolAssessInfo(bean,userinfo,userids)){
//			String strQueryString = getTotalQueryString("method=queryLinePatrolInfo",(LinePatrolManagerBean)request.getSession().getAttribute("theQueryBean"));
			Object[] args = getURLArgs("/WebApp/LinePatrolManagerAction.do","method=queryLinePatorAssess",(String)request.getSession().getAttribute( "S_BACK_URL" ));
			return forwardInfoPage( mapping, request, "815add",null,args);
		}else{
			return forwardErrorPage( mapping, request, "error" );
		}
	}

	public ActionForward queryParolManager(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		request.setAttribute("type", userinfo.getDeptype());
		return mapping.findForward("queryParolManager");
	}

	public ActionForward queryParolManagerList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean)form;
		LinePatrolManagerDao dao = new LinePatrolManagerDao();
		request.getSession().setAttribute("S_BACK_URL",null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		request.setAttribute("type", "query");
		request.setAttribute("stype", userinfo.getDeptype());

		if(userinfo.getDeptype().equals("1")){
			List assesslist = dao.getListByDepartFor1(bean);
			request.getSession().setAttribute("assesslist", assesslist);
			this.setPageReset(request);
			return mapping.findForward("linePatorAssess3List");
		}else if(userinfo.getDeptype().equals("3")){
			List assesslist = dao.getListByDepart3(userinfo.getDeptID(),bean);
			request.getSession().setAttribute("assesslist", assesslist);
			this.setPageReset(request);
			return mapping.findForward("linePatorAssess3List");
		}else{
			return forwardErrorPage(mapping, request, "partstockerror");
		}
	}

	public ActionForward lookLinePatrolByCon(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LinePatrolManagerBean bean = (LinePatrolManagerBean)form;
		String id = request.getParameter("id");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		LinePatrolManagerDao dao = new LinePatrolManagerDao();
		bean = dao.viewLinePatrolById(id, bean);
		bean = dao.getLineAssess(bean, id);
		request.setAttribute("type", userinfo.getDeptype());
//		if(userinfo.getDeptype().equals("1")){
		LinePatrolManagerBean lbean = new LinePatrolManagerBean();
		lbean = dao.getLineAssessDept1(lbean, id);
		request.setAttribute("lbean", lbean);
//		}
		System.out.println("tttttttttttttttttttt   " );

		request.setAttribute("bean", bean);
		return mapping.findForward("lookLinePatrolByCon");
	}

	public ActionForward getModelById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/json; charset=GBK");
		String id = request.getParameter("id");
		LinePatrolManagerDao dao = new LinePatrolManagerDao();
		List modellist = dao.getLinePatrolModelByID(id);
		JSONArray ja = JSONArray.fromObject(modellist);
		PrintWriter out = response.getWriter();
		out.write(ja.toString());
		out.flush();
		return null;
	}


	public ActionForward getPatorlBaseById(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/json; charset=GBK");
		String id = request.getParameter("id");
		LinePatrolManagerDao dao = new LinePatrolManagerDao();
		List baselist = dao.getPatorlBaseById(id);
		JSONArray ja = JSONArray.fromObject(baselist);
		PrintWriter out = response.getWriter();
		out.write(ja.toString());
		out.flush();
		return null;
	}



}
