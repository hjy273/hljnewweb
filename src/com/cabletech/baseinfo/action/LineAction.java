package com.cabletech.baseinfo.action;

//nn
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.beans.LineBean;
import com.cabletech.baseinfo.domainobjects.Line;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;
import com.cabletech.commons.web.ClientException;
import com.cabletech.power.CheckPower;

public class LineAction extends BaseInfoBaseDispatchAction {

	private static Logger logger = Logger.getLogger(LineAction.class.getName());

	public LineAction() {
	}

	/* 添加线路信息 */
	public ActionForward addLine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		if (!CheckPower.checkPower(request.getSession(), "70902")) {
			return mapping.findForward("powererror");
		}

		LineBean bean = (LineBean) form;
		Line data = new Line();
		BeanUtil.objectCopy(bean, data);
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");

		if (super.getService().findByLineName(bean.getLineName(), "add",
				userinfo.getRegionID())) {
			data.setLineID(super.getDbService().getSeq("lineinfo", 8));
			data.setRegionid(super.getLoginUserInfo(request).getRegionid());

			super.getService().createLine(data);
			log(request, " 增加巡检线路信息（线路名称为："+bean.getLineName()+"） ", " 基础资料管理 ");
			return forwardInfoPage(mapping, request, "0031");
		} else {
			return forwardInfoPage(mapping, request, "00310", data
					.getLineName());
		}
		// Log

	}

	/* 加载线路信息 */
	public ActionForward loadLine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {

		Line data = super.getService().loadLine(request.getParameter("id"));
		LineBean bean = new LineBean();
		BeanUtil.objectCopy(data, bean);
		request.setAttribute("lineBean", bean);
		request.getSession().setAttribute("lineBean", bean);
		request.getSession().setAttribute("theOldLineName", bean.getLineName());
		// 以下代码是与showAddLine中似
		QueryUtil query = new QueryUtil();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List deptinfo_line = null;
		String sqldept = "select d.deptid,d.deptname from deptinfo d where d.deptid = '"
				+ userinfo.getDeptID() + "'";
		deptinfo_line = query.queryBeans(sqldept);
		request.setAttribute("deptinfo_line", deptinfo_line);
		logger.info("************:" + sqldept);
		if (request.getSession().getAttribute("S_BACK_URL") == null) {
			logger.info("load when S_BACK_URL is null:");
			String strQueryString = getTotalQueryString(
					"method=queryLine&lineName=", (LineBean) request
							.getSession().getAttribute("theQueryBean"));
			Object[] args = getURLArgs("/WebApp/lineAction.do", strQueryString,
					(String) request.getSession().getAttribute("S_BACK_URL"));
			request.setAttribute("InitialURL", args[0]);
			logger.info("load after getUrlArgs:" + args[0]);
			return mapping.findForward("updateLine");
		} else {
			request.getSession().setAttribute("InitialURL", null);
		}
		logger.info("load when S_BACK_URL is not null:"
				+ request.getSession().getAttribute("S_BACK_URL"));
		return mapping.findForward("updateLine");
	}

	/**
	 * 检索
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
	public ActionForward queryLine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		request.getSession().setAttribute("S_BACK_URL", null); //
		request.getSession().setAttribute("theQueryBean", (LineBean) form);//
		String linename = ((LineBean) form).getLineName();
		String strRegion = ((LineBean) form).getRegionid();
		if (strRegion == null) {
			strRegion = super.getLoginUserInfo(request).getRegionid();
		}
		String sql = "";
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		if (userinfo.getType().equals("22")) {
			sql = "SELECT distinct a.lineid, a.linename, b.deptname ruledeptid,l.NAME linetype, c.regionname regionid FROM lineinfo a, deptinfo b, region c,lineclassdic l,sublineinfo s,patrolmaninfo p ";
		} else if (userinfo.getType().equals("11") || userinfo.getType().equals("12")) {
			sql = "SELECT a.lineid, a.linename, b.deptname ruledeptid,l.NAME linetype, c.regionname regionid FROM lineinfo a, deptinfo b, region c,lineclassdic l";
		}
		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);

		sqlBuild.addConstant("a.ruledeptid = b.deptid(+)");
		sqlBuild.addAnd();
		sqlBuild.addConstant("a.regionid = c.regionid(+)");
		sqlBuild.addAnd();
		sqlBuild.addConstant("l.CODE = a.LINETYPE");
	/*	if (userinfo.getType().equals("22")) {
			sqlBuild.addAnd();
			sqlBuild.addConstant("a.LINEID = s.LINEID");
			 sqlBuild.addAnd();
			 sqlBuild.addConstant( "s.PATROLID = p.PATROLID" );
			 sqlBuild.addConditionAnd( "p.PARENTID = {0}",
			 userinfo.getDeptID() );
		}*/
		sqlBuild.addConditionAnd("a.lineid = {0}", ((LineBean) form).getLineID());
		sqlBuild.addConditionAnd("a.linename like {0}", "%" + linename + "%");
		sqlBuild.addConditionAnd("a.regionid={0}", ((LineBean) form).getRegionid());
		sqlBuild.addConditionAnd("a.ruledeptid = {0}", ((LineBean) form).getRuleDeptID());
		sqlBuild.addConditionAnd("a.linetype = {0}", ((LineBean) form).getLineType());
		sqlBuild.addConditionAnd("a.state = {0}", ((LineBean) form).getState());
		if (!strRegion.equals("")) {
			sqlBuild.addTableRegion("a.regionid", strRegion);
		}
		// super.getLoginUserInfo( request ).getRegionid() );
		sql = sqlBuild.toSql();
		if (userinfo.getType().equals("22")) {
			sql = sql + "  order by a.linename";
		} else {
			sql = sql + "  order by a.linename ,a.linetype";
		}
		/*
		 * if(super.getLoginUserInfo( request ).getType().equals("22")){ sql =
		 * "SELECT l.lineid, l.linename, d.deptname ruledeptid,c.NAME linetype,
		 * r.regionname regionid from lineinfo l,lineclassdic c,deptinfo
		 * d,region r where l.lineid in (select s.lineid from sublineinfo s
		 * where s.patrolid in (select p.patrolid from patrolmaninfo p where
		 * p.parentid = '" + super.getLoginUserInfo( request ).getDeptID() +
		 * "')) and c.code=l.linetype and l.ruledeptid = d.deptid(+) and
		 * l.regionid = r.regionid(+)"; if (((LineBean)form).getLineType() !=
		 * null ){ if (!((LineBean)form).getLineType().equals("")){ sql = sql + "
		 * and l.linetype='" + ( ( LineBean )form ).getLineType() + "'"; } } }
		 */
		if (super.getLoginUserInfo(request).getType().equals("21")) {
			sql = "SELECT l.lineid, l.linename, d.deptname ruledeptid,c.NAME linetype, r.regionname regionid from lineinfo l,lineclassdic c,deptinfo d,region r  where c.code = l.linetype and l.ruledeptid = d.deptid(+) and l.regionid = r.regionid(+) and (l.regionid IN (SELECT RegionID FROM region CONNECT BY PRIOR RegionID = parentregionid START WITH RegionID = '"
					+ super.getLoginUserInfo(request).getRegionid()
					+ "')) and l.lineid in (select s.lineid  from sublineinfo s where s.patrolid in (select p.patrolid from patrolmaninfo p where p.parentid in(select con.contractorid  from contractorinfo con CONNECT BY PRIOR con.contractorid = con.parentcontractorid START WITH con.contractorid = '"
					+ super.getLoginUserInfo(request).getDeptID() + "'))) ";
			if (((LineBean) form).getRegionid() != null) {
				if (!((LineBean) form).getRegionid().equals("")) {
					if (!((LineBean) form).getRegionid().substring(2, 6).equals("0000")) {
						sql = sql + " and l.regionid='" + ((LineBean) form).getRegionid() + "'";
					}
				}
			}
			if (((LineBean) form).getRuleDeptID() != null) {
				if (!((LineBean) form).getRuleDeptID().equals("")) {
					sql = sql + " and l.ruledeptid='" + ((LineBean) form).getRuleDeptID() + "'";
				}
			}
			if (((LineBean) form).getLineType() != null) {
				if (!((LineBean) form).getLineType().equals("")) {
					sql = sql + " and l.linetype='" + ((LineBean) form).getLineType() + "'";
				}
			}
			sql = sql + " order by r.regionid ";
		}
		logger.info("SQL:" + sql);
		List list = super.getDbService().queryBeans(sql);
		request.getSession().setAttribute("queryresult", list);
		super.setPageReset(request);
		return mapping.findForward("querylineresult");
	}

	/*
	 * 更新线路信息
	 */
	public ActionForward updateLine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		Line data = new Line();
		LineBean bean=(LineBean)form;
		BeanUtil.objectCopy(bean, data);
		String theOldLineName = (String) request.getSession().getAttribute(
				"theOldLineName");
		logger.info("is equal" + data.getLineName().equals(theOldLineName));
		if (data.getLineName().equals(theOldLineName)
				|| super.getService().findByLineName(data.getLineName(),
						"edit", userinfo.getRegionid())) {
			super.getService().updateLine(data);
			log(request, " 更新巡检线路信息（线路名称为："+bean.getLineName()+"） ", " 基础资料管理 ");

			logger.info("update before getUrlArgs :"
					+ request.getSession().getAttribute("S_BACK_URL"));

			/*
			 * Object[] args = new Object[1]; if
			 * (request.getSession().getAttribute( "S_BACK_URL" ) == null){
			 * args[0]="/WebApp/baseinfo/querylineresult.jsp?method=queryLine&lineName=";
			 * }else{ args[0] =
			 * request.getSession().getAttribute("S_BACK_URL"); }
			 */
			String strQueryString = getTotalQueryString(
					"method=queryLine&lineName=", (LineBean) request
							.getSession().getAttribute("theQueryBean"));
			Object[] args = getURLArgs("/WebApp/lineAction.do", strQueryString,
					(String) request.getSession().getAttribute("S_BACK_URL"));
			logger.info("update after getUrlArgs:" + args[0]);
			return forwardInfoPage(mapping, request, "0032",
					data.getLineName(), args);
		} else {
			return forwardInfoPage(mapping, request, "00310", data
					.getLineName());
		}
	}

	/*
	 * 删除线路信息
	 */
	public ActionForward delLine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		if (!CheckPower.checkPower(request.getSession(), "70905")) {
			return mapping.findForward("powererror");
		}
		Line data = super.getService().loadLine(request.getParameter("id"));
		if (!super.getService().valiLineCanDel(request.getParameter("id"))) {
			return forwardErrorPage(mapping, request, "0032aerror");
		} else {
			super.getService().removeLine(data);
			log(request, " 删除巡检线路信息（线路名称为："+data.getLineName()+"）  ", " 基础资料管理 ");
		}
		logger.info("delete before getUrlArgs :"
				+ request.getSession().getAttribute("S_BACK_URL"));
		String strQueryString = getTotalQueryString(
				"method=queryLine&lineName=", (LineBean) request.getSession()
						.getAttribute("theQueryBean"));
		Object[] args = getURLArgs("/WebApp/lineAction.do", strQueryString,
				(String) request.getSession().getAttribute("S_BACK_URL"));
		logger.info("delete after getUrlArgs :" + args[0]);
		return forwardInfoPage(mapping, request, "0033", null, args);
	}

	// 已不用
	public ActionForward deleteLine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {

		Line data = super.getService().loadLine(request.getParameter("id"));
		if (!super.getService().valiLineCanDele(request.getParameter("id"))) {
			return forwardErrorPage(mapping, request, "0032aerror");
		}
		super.getService().removeLine(data);

		// Log
		log(request, " 删除线信息 ", " 基础资料管理 ");
		return forwardInfoPage(mapping, request, "0033");
	}

	public ActionForward exportLineResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			logger.info(" 创建dao");
			List list = (List) request.getSession().getAttribute("queryresult");
			logger.info("得到list");
			super.getService().exportLineResult(list, response);
			logger.info("输出excel成功");
			return null;
		} catch (Exception e) {
			logger.error("导出信息报表出现异常:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	public ActionForward showAddLine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		QueryUtil query = new QueryUtil();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List deptinfo_line = null;
		String sqldept = "select d.deptid,d.deptname from deptinfo d where d.deptid = '"
				+ userinfo.getDeptID() + "'";
		deptinfo_line = query.queryBeans(sqldept);
		request.setAttribute("deptinfo_line", deptinfo_line);
		logger.info("************:" + sqldept);
		return mapping.findForward("showaddline");
	}

	public ActionForward showAdvancedLine(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		QueryUtil query = new QueryUtil();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List reginfo = null;
		List deptinfo = null;
		if (userinfo.getType().equals("11") || userinfo.getType().equals("21")) {
			// //////////////////////查询所有区域----省级用户
			String sqlregion = "select  r.REGIONNAME, r.REGIONID "
					+ " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
			String sqldept = "select d.deptid,d.deptname,d.regionid "
					+ " from deptinfo d where d.state is null order by d.regionid,d.parentid,d.deptid";
			reginfo = query.queryBeans(sqlregion);
			deptinfo = query.queryBeans(sqldept);
			request.setAttribute("reginfo", reginfo);
			request.setAttribute("deptinfo", deptinfo);
			logger.info("************:" + sqlregion);
			logger.info("************:" + sqldept);
		}
		return mapping.findForward("showadvancedline");
	}

	public String getTotalQueryString(String queryString, LineBean bean) {
		if (bean.getLineName() != null) {
			queryString = queryString + bean.getLineName();
		}
		if (bean.getLineID() != null) {
			queryString = queryString + "&lineID=" + bean.getLineID();
		}
		if (bean.getRegionid() != null) {
			queryString = queryString + "&regionid=" + bean.getRegionid();
		}
		if (bean.getRuleDeptID() != null) {
			queryString = queryString + "&ruleDeptID=" + bean.getRuleDeptID();
		}
		if (bean.getLineType() != null) {
			queryString = queryString + "&lineType=" + bean.getLineType();
		}
		return queryString;
	}

}
