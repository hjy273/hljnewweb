package com.cabletech.baseinfo.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.beans.SublineBean;
import com.cabletech.baseinfo.domainobjects.Subline;
import com.cabletech.baseinfo.domainobjects.SublineCableList;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.PatrolManBO;
import com.cabletech.baseinfo.services.SublineBO;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.generatorID.CustomID;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;
import com.cabletech.commons.tags.services.DictionaryService;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.commons.web.ClientException;

public class SublineAction extends BaseInfoBaseDispatchAction {

	private static Logger logger = Logger.getLogger(SublineAction.class
			.getName());

	public SublineAction() {
	}

	/**
	 * 添加线段
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward addSubline(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {

		SublineBean bean = (SublineBean) form;
		Subline data = new Subline();
		BeanUtil.objectCopy(bean, data);
		if (super.getService().findBySubLineName(data.getSubLineName(), "add")) {
			data.setSubLineID(super.getDbService().getSeq("sublineinfo", 8));
			data.setRegionID(super.getLoginUserInfo(request).getRegionid());

			super.getService().createSubline(data);
			// 删除对应项
			super.getService().deleteBySublineID(data.getSubLineID());

			// 处理操作关联表
			String[] cableArr = request.getParameterValues("sublinecablelist");
			// String[] cableArr = bean.getSublinecablelist();

			if (cableArr != null && cableArr.length > 0) {
				OracleIDImpl ora = new OracleIDImpl();
				String id = "";
				for (int i = 0; i < cableArr.length; i++) {
					id = ora.getSeq("sublinecablesegment", 10);
					SublineCableList SCList = new SublineCableList();
					SCList.setKid(id);
					SCList.setSublineid(data.getSubLineID());
					SCList.setCablesegmentid(cableArr[i]);
					super.getService().addSublineCableList(SCList);
				}
			}
			log(request, " 增加线段信息（线段名称为："+bean.getSubLineName()+"） ", " 基础资料管理 ");
			return forwardInfoPage(mapping, request, "0041");
		} else {
			return forwardInfoPage(mapping, request, "00410", data
					.getSubLineName());
		}
	}

	/**
	 * 查询线段
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward querySubline(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		DictionaryService dictionaryService = (DictionaryService) ctx
				.getBean("dictionaryService");
		SublineBean bean = (SublineBean) form;
		request.getSession().setAttribute("S_BACK_URL", null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String sql = "select a.SublineID,  e.patrolname patrolname , b.linename LineID,ci.contractorname,  a.SubLineName,  d.deptname RuleDeptID,  decode(a.LineType, "
				+ StringUtils.map2StrSql(dictionaryService
						.loadDictByAssortment("layingmethod",userinfo.getRegionid()))
				+ ") LineType, a.CheckPoints,  c.regionname RegionID from sublineinfo a, lineinfo b,contractorinfo ci, region c, deptinfo d, patrolmaninfo e";
		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
		sqlBuild.addConstant("a.lineid = b.lineid(+)");
		sqlBuild.addAnd();
		sqlBuild.addConstant("a.regionid = c.regionid(+)");
		sqlBuild.addAnd();
		sqlBuild.addConstant("a.ruledeptid = d.deptid(+)");
		sqlBuild.addAnd();
		sqlBuild.addConstant("a.patrolid = e.patrolid(+)");
		sqlBuild.addAnd();
		sqlBuild.addConstant("ci.contractorid=e.parentid");

		sqlBuild.addConditionAnd(" a.sublinename like {0}", "%"
				+ bean.getSubLineName() + "%");
		sqlBuild.addConditionAnd(" a.lineid = {0}", bean.getLineID());
		sqlBuild.addConditionAnd(" a.ruledeptid = {0}", bean.getRuleDeptID());
		sqlBuild.addConditionAnd(" a.linetype = {0}", bean.getLineType());
		sqlBuild.addConditionAnd(" a.patrolid = {0}", bean.getPatrolid());
		sqlBuild.addConditionAnd(" ci.contractorid={0}",bean.getContractorId());
		sqlBuild.addTableRegion("a.regionid", super.getLoginUserInfo(request)
				.getRegionid());
		sql = sqlBuild.toSql();
		if (userinfo.getDeptype() == "2" || userinfo.getDeptype().equals("2"))
			sql += "AND e.PARENTID = '" + userinfo.getDeptID() + "'";
		List list = super.getDbService().queryBeans(sql);
		logger.info("querysubline SQL:" + sql);
		request.getSession().setAttribute("queryresult", list);
		return mapping.findForward("querysublineresult");
	}

	public ActionForward exportSubline(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String reportType;
		List list = (List) request.getSession().getAttribute("queryresult");
		super.getService().ExportSubline(list, response);

		return null;
	}

	public ActionForward queryCableList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String queryValue = request.getParameter("query_value");
		String patrolId = request.getParameter("patrol_id");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		StringBuffer buf = new StringBuffer("");
		buf.append("<select name=\"sublinecablelist\" multiple=\"multiple\" ");
		buf.append(" size=\"15\" style=\"width:160\" class=\"multySelect\">");
		buf.append(super.getService().queryCableList(queryValue,patrolId, userInfo));
		buf.append("</select>");

		response.setCharacterEncoding("GBK");
		PrintWriter out = response.getWriter();
		out.print(buf.toString());
		out.close();
		return null;
	}

	/**
	 * 载入特定线段
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward loadSubline(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		SublineBO bo = new SublineBO();
		PatrolManBO pm = new PatrolManBO();
		String regionid = "";
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String id = request.getParameter("id");
		Subline data = super.getService().loadSubline(id);
		String checkPoints = bo.sumCheckPoints(request.getParameter("id"));
		if (!checkPoints.equals("-1")) {
			data.setCheckPoints(checkPoints);
		} else {
			return forwardErrorPage(mapping, request, "0041e");
		}
		SublineBean bean = new SublineBean();
		BeanUtil.objectCopy(data, bean);
		if (request.getParameter("regionid") != null
				&& !request.getParameter("regionid").equals("")) {
			regionid = request.getParameter("regionid");
			bean.setRegionID(regionid);
		} else {
			regionid = userinfo.getRegionID();
		}

		bean.setCablelist(super.getService().getCableList(data.getSubLineID()));
		List pmlist = (List) pm.getPatrol(userinfo).get(0);
		request.setAttribute("patrolCollection", pmlist);
		request.setAttribute("sublineBean", bean);
		request.getSession().setAttribute("sublineBean", bean);
		List linelist = (List) super.getService().loadLineInfo(regionid).get(0);
		List deptlist = (List) super.getService().loadDept(regionid).get(0);
		request.setAttribute("lineCollection", linelist);
		request.setAttribute("deptCollection", deptlist);
		if (request.getSession().getAttribute("S_BACK_URL") == null) {
			String strQueryString = getTotalQueryString(
					"method=querySubline&subLineName=", (SublineBean) request
							.getSession().getAttribute("theQueryBean"));
			Object[] args = getURLArgs("/WebApp/sublineAction.do",
					strQueryString, (String) request.getSession().getAttribute(
							"S_BACK_URL"));
			logger.info("args in load:" + args[0]);
			request.getSession().setAttribute("InitialURL", args[0]);
		} else {
			request.getSession().setAttribute("InitialURL", null);
		}
		return mapping.findForward("updateSubline");
	}

	/**
	 * 更新线段
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward updateSubline(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {

		// bean.v
		SublineBean bean = (SublineBean) form;
		Subline data = new Subline();
		BeanUtil.objectCopy(bean, data);
		if (super.getService().findBySubLineName(data.getSubLineName(), "edit")) {
			super.getService().updateSubline(data);

			// 删除对应项
			super.getService().deleteBySublineID(data.getSubLineID());

			// 处理操作关联表
			String[] cableArr = request.getParameterValues("sublinecablelist");
			if (cableArr != null && cableArr.length > 0) {
				OracleIDImpl ora = new OracleIDImpl();
				String id = "";
				for (int i = 0; i < cableArr.length; i++) {
					id = ora.getSeq("sublinecablesegment", 10);
					SublineCableList SCList = new SublineCableList();
					SCList.setKid(id);
					SCList.setSublineid(data.getSubLineID());
					SCList.setCablesegmentid(cableArr[i]);

					super.getService().addSublineCableList(SCList);
				}
			}
			// Log
			log(request, " 更新线段信息 （线段名称为："+bean.getSubLineName()+"）", " 基础资料管理 ");
			/*
			 * if (request.getSession().getAttribute("InitialURL") != null){
			 * 
			 * }
			 */
			String strQueryString = getTotalQueryString(
					"method=querySubline&subLineName=", (SublineBean) request
							.getSession().getAttribute("theQueryBean"));
			Object[] args = getURLArgs("/WebApp/sublineAction.do",
					strQueryString, (String) request.getSession().getAttribute(
							"S_BACK_URL"));
			return forwardInfoPage(mapping, request, "0042", null, args);
		} else {
			return forwardInfoPage(mapping, request, "00410", data
					.getSubLineName());
		}

	}

	public ActionForward deleteSubline(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {

		// bean.v
		Subline data = super.getService().loadSubline(
				request.getParameter("id"));
		if (!super.getService().valiSubLineCanDele(request.getParameter("id"))) {
			return forwardErrorPage(mapping, request, "0043aerror");
		}
		super.getService().removeSubline(data);
		// Log
		log(request, " 删除线段信息 ", " 基础资料管理 ");
		logger.info("delete before getUrlArgs :"
				+ request.getSession().getAttribute("S_BACK_URL"));
		String strQueryString = getTotalQueryString(
				"method=querySubline&subLineName=", (SublineBean) request
						.getSession().getAttribute("theQueryBean"));
		Object[] args = getURLArgs("/WebApp/sublineAction.do", strQueryString,
				(String) request.getSession().getAttribute("S_BACK_URL"));
		logger.info("delete after getUrlArgs :" + args[0]);
		return forwardInfoPage(mapping, request, "0043", null, args);
	}

	/**
	 * 载入特定线段 （ GIS 用）
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward loadSubline4Gis(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String regionid = "";
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		Subline data = super.getService().loadSubline(
				request.getParameter("sPID"));
		SublineBean bean = new SublineBean();
		BeanUtil.objectCopy(data, bean);
		if (request.getParameter("regionid") != null
				&& !request.getParameter("regionid").equals("")) {
			regionid = request.getParameter("regionid");
			bean.setRegionID(regionid);
		} else {
			regionid = userinfo.getRegionID();
		}

		bean.setCablelist(super.getService().getCableList(data.getSubLineID()));
		List linelist = (List) super.getService().loadLineInfo(regionid).get(0);
		List deptlist = (List) super.getService().loadDept(regionid).get(0);
		request.setAttribute("lineCollection", linelist);
		request.setAttribute("deptCollection", deptlist);

		request.setAttribute("sublineBean", bean);
		request.getSession().setAttribute("sublineBean", bean);
		// SublineBean bean = (SublineBean) form;
		// request.setAttribute("regionID",request.getParameter("regionID"));
		request.setAttribute("regionID", bean.getRegionID());
		return mapping.findForward("loadSubline4Gis");
	}

	/**
	 * 更新线段 （ GIS 用）
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward updateSubline4Gis(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		// bean.v
		SublineBean bean = (SublineBean) form;
		Subline data = new Subline();
		BeanUtil.objectCopy(bean, data);

		super.getService().updateSubline(data);
		// 删除对应项
		super.getService().deleteBySublineID(data.getSubLineID());

		// 处理操作关联表
		String[] cableArr = request.getParameterValues("sublinecablelist");
		// String[] cableArr = bean.getSublinecablelist();

		if (cableArr != null && cableArr.length > 0) {

			CustomID idFactory = new CustomID();
			String[] idArr = idFactory.getStrSeqs2(cableArr.length,
					"sublinecablesegment", 10);

			for (int i = 0; i < cableArr.length; i++) {
				// System.out.println(" Now Is : " + operAr[i]);
				SublineCableList SCList = new SublineCableList();
				SCList.setKid(idArr[i]);
				SCList.setSublineid(data.getSubLineID());
				SCList.setCablesegmentid(cableArr[i]);

				super.getService().addSublineCableList(SCList);
			}
		}
		// Log
		log(request, " 更新线段信息 （线段名称为："+bean.getSubLineName()+"）", " 实时监控 ");

		return forwardInfoPage(mapping, request, "0046");
	}

	public ActionForward loadSublineForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String regionid = "";
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		if (request.getParameter("regionid") != null
				&& !request.getParameter("regionid").equals("")) {
			regionid = request.getParameter("regionid");
		} else {
			regionid = userinfo.getRegionID();
		}
		SublineBean sublineBean = new SublineBean();
		sublineBean.setRegionID(regionid);
		PatrolManBO pm = new PatrolManBO();
		List linelist = (List) super.getService().loadLineInfo(regionid).get(0);
		List deptlist = (List) super.getService().loadDept(regionid).get(0);
		List pmlist = (List) pm.getPatrol(userinfo).get(0);
		request.setAttribute("patrolCollection", pmlist);
		request.setAttribute("lineCollection", linelist);
		request.setAttribute("deptCollection", deptlist);
		request.setAttribute("sublineBean", sublineBean);
		return mapping.findForward("loadSubLineForm");
	}

	public ActionForward loadQuerySublineForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String regionid = "";
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");

		PatrolManBO pm = new PatrolManBO();

		List pmlist = (List) pm.getPatrol(userinfo).get(0);
		request.setAttribute("patrolCollection", pmlist);
		// return mapping.findForward( "loadQuerySubLineForm" );
		return mapping.findForward("loadQuerySubLineFormAjax");
	}

	public String getTotalQueryString(String queryString, SublineBean bean) {
		if (bean.getSubLineName() != null) {
			queryString = queryString + bean.getSubLineName();
		}
		if (bean.getLineID() != null) {
			queryString = queryString + "&lineID=" + bean.getLineID();
		}
		if (bean.getRuleDeptID() != null) {
			queryString = queryString + "&ruleDeptID=" + bean.getRuleDeptID();
		}
		if (bean.getLineType() != null) {
			queryString = queryString + "&lineType=" + bean.getLineType();
		}
		if (bean.getPatrolid() != null) {
			queryString = queryString + "&patrolid=" + bean.getPatrolid();
		}
		return queryString;
	}

}
