package com.cabletech.baseinfo.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.web.*;

/**
 * 事故处理控制器
 *
 */
public class AlertInfoAction extends BaseInfoBaseDispatchAction {

	private static Logger logger = Logger.getLogger(AlertInfoAction.class.getName());

	public AlertInfoAction() {
	}

	/**
	 * 载入事故信息
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */

	public ActionForward loadAlertInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		//logger.debug(".............................loadAlertInfo");
		AlertInfo data = super.getService().loadAlertInfo((request.getParameter("id")));
		AlertInfoBean bean = new AlertInfoBean();
		BeanUtil.objectCopy(data, bean);
		request.setAttribute("AlertInfoBean", bean);
		return mapping.findForward("updateAlertInfo");
	}

	/**
	 *	查询事故信息
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward queryAlertInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		UserInfo ui = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String sql = "select a.id,b.patrolname executorid,a.simid,a.executetime,a.type,c.pointname pid,a.lid,a.gpscoordinate,d.operationdes operationcode from (select id,executorid,simid,executetime,type,pid,lid,gpscoordinate,operationcode from planexecute"
				+ " where substr(operationcode,0,1)='A' and executetime > (select lastlogintime from userinfo where userid='"
				+ ui.getUserID()
				+ "')) a,patrolmaninfo b,(select * from pointinfo where regionid='"
				+ ui.getRegionID()
				+ "' ) c,patroloperation d";

		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
		sqlBuild.addConstant("a.executorid=b.patrolid(+)");
		sqlBuild.addAnd();
		sqlBuild.addConstant("a.pid=c.pointid(+)");
		sqlBuild.addAnd();
		sqlBuild.addConstant("a.operationcode=d.operationcode(+)");
		List list = super.getDbService().queryBeans(sqlBuild.toSql());

		request.getSession().setAttribute("queryresult", list);

		return mapping.findForward("queryAlertInfoResult");
	}

	/**
	 * 待处理事故列表
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward loadUndoenAccident(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		//
		//UserInfo user = super.getLoginUserInfo(request);
		//String regionid = user.getRegionID();

		String sql = " select a.KEYID id, decode(a.type,'1','障碍','隐患') type, to_char(a.SENDTIME,'yy/mm/dd hh24:mi:ss') sendtime, nvl(b.pointname,'') point, nvl(c.sublinename,'') subline, nvl(f.operationdes,'') reason, nvl(decode(f.emergencylevel,'1','轻微','2','中度','3','严重','轻微'),'') emlevel, nvl(e.ContractorName,'') contractor from ACCIDENT a, pointinfo b, sublineinfo c, contractorinfo e, patroloperation f, patrolmaninfo g ";
		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
		sqlBuild
				.addConstant(" a.pid = b.pointid(+) and a.lid = c.sublineid(+) and a.patrolid = g.patrolid(+) and g.parentid = e.contractorid(+) and a.operationcode = f.operationcode(+) ");
		sqlBuild.addConstant(" and a.status = '0' order by  a.SENDTIME desc ");
		//sqlBuild.addAnd();
		//sqlBuild.addConstant("a.type='1'");
		//zsh 20050803 added
		//sqlBuild.addTableRegion("a.regionid", regionid);
		//end zsh 20050803 added
		List list = super.getDbService().queryBeans(sqlBuild.toSql());

		request.getSession().setAttribute("queryresult_ONE", list);
		return mapping.findForward("unDoneAccidentListpage");
	}

	private void jbInit() throws Exception {
	}
}
