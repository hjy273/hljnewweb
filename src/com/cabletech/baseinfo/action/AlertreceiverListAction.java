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

public class AlertreceiverListAction extends BaseInfoBaseDispatchAction {

	private static Logger logger = Logger.getLogger("AlertreceiverListAction");

	/**
	 * 新增一个部门
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward addAlertreceiverList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		AlertreceiverListBean bean = (AlertreceiverListBean) form;
		AlertreceiverList data = new AlertreceiverList();
		String id = super.getDbService().getSeq("AlertreceiverList", 8);
		bean.setId(id);
		BeanUtil.objectCopy(bean, data);
		if (this.checkAlertCode(mapping, form, request, response)) {
			return forwardInfoPage(mapping, request, "1304");
		}
		data.setId(super.getDbService().getSeq("AlertreceiverList", 8));
		super.getService().createAlertreceiverList(data);

		//Log
		log(request, " 增加报警接收信息 ", " 基础资料管理 ");

		return forwardInfoPage(mapping, request, "1301");
	}

	/**
	 * 转向新增一个部门的表单
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward addAlertreceiverListForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		//String region="select regionid,regionname from region where state is null or state<>'1'";
		String con = "select contractorid,contractorname,regionid from contractorinfo where (state is null or state<>'1')";
		String rec = "select userid,username,phone,regionid,deptid from userinfo where (state is null or state<>'1')";

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String type = userInfo.getType();
		if (type.equals("12")) {
			con += " and regionid IN ('" + userInfo.getRegionID() + "')";
		}
		if (type.equals("21")) {
			con += " and contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
					+ userInfo.getDeptID() + "')";
		}
		if (type.equals("22")) {
			con += " and contractorid='" + userInfo.getDeptID() + "' ";
		}

		//List regList=super.getDbService().queryBeans(region);
		List conList = super.getDbService().queryBeans(con);
		List recList = super.getDbService().queryBeans(rec);
		//request.setAttribute("reglist",regList);
		request.setAttribute("conlist", conList);
		request.setAttribute("reclist", recList);

		return mapping.findForward("addAlertreceiverListForm");
	}

	/**
	 * 转向高级查询的表单
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward queryAlertreceiverListForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		String region = "select regionid,regionname " + "from region "
				+ "where (state is null or state<>'1') and substr(REGIONID,3,4) != '1111'";
		String con = "select contractorid,contractorname,regionid " + "from contractorinfo "
				+ "where (state is null or state<>'1')";
		String rec = "select u.userid,u.username,a.simcode phone,u.regionid,u.deptid "
				+ "from userinfo u,alertreceiverlist a " + "where u.userid=a.userid and (state is null or state<>'1')";

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String type = userInfo.getType();
		if (type.equals("12")) {
			con += " and regionid IN ('" + userInfo.getRegionID() + "')";
		}
		if (type.equals("21")) {
			con += " and contractorid in( " + "      SELECT contractorid " + "      FROM contractorinfo "
					+ "      CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
					+ userInfo.getDeptID() + "')";
			/*
			region+=" and regionid like '%1111' or regionid in("
			    +"        select regionid "
			    +"        from contractorinfo"
			    +"        where (state is null or state<>'1')"
			    +"        and contractorid in("
			    +"            select contractorid"
			    +"            from contractorinfo"
			    +"            where (state is null or state<>'1')"
			    +"            and parentcontractorid='"+userInfo.getDeptID()+"' or regionid like '%0000'"
			    +"        )"
			    +"     )";
			*/
		}
		if (type.equals("22")) {
			con += " and contractorid='" + userInfo.getDeptID() + "' ";
		}
		//region+=" order by regionid";
		con += " order by regionid";

		List regList = super.getDbService().queryBeans(region);
		List conList = super.getDbService().queryBeans(con);
		List recList = super.getDbService().queryBeans(rec);
		request.setAttribute("reglist", regList);
		request.setAttribute("conlist", conList);
		request.setAttribute("reclist", recList);

		return mapping.findForward("queryAlertreceiverListForm");
	}

	/**
	 * 转向修改页面的表单
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward loadAlertreceiverList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		AlertreceiverList data = super.getService().loadAlertreceiverList(request.getParameter("id"));
		AlertreceiverListBean bean = new AlertreceiverListBean();
		BeanUtil.objectCopy(data, bean);
		request.setAttribute("AlertreceiverListBean", bean);
		//String region="select regionid,regionname from region where state is null or state<>'1'";
		String con = "select contractorid,contractorname,regionid from contractorinfo where (state is null or state<>'1')";
		String rec = "select userid,username,phone,regionid,deptid from userinfo where (state is null or state<>'1')";

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String type = userInfo.getType();
		if (type.equals("12")) {
			con += " and regionid IN ('" + userInfo.getRegionID() + "')";
		}
		if (type.equals("21")) {
			con += " and contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
					+ userInfo.getDeptID() + "')";
		}
		if (type.equals("22")) {
			con += " and contractorid='" + userInfo.getDeptID() + "' ";
		}

		//List regList=super.getDbService().queryBeans(region);
		List conList = super.getDbService().queryBeans(con);
		List recList = super.getDbService().queryBeans(rec);
		//request.setAttribute("reglist",regList);
		request.setAttribute("conlist", conList);
		request.setAttribute("reclist", recList);

		return mapping.findForward("updateAlertreceiverList");
	}

	/**
	 * 删除
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward deleteAlertreceiverList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		AlertreceiverList data = super.getService().loadAlertreceiverList(request.getParameter("id"));
		super.getService().removeAlertreceiverList(data);
		//Log
		log(request, " 删除报警接收信息 ", " 基础资料管理 ");
		String strQueryString = getTotalQueryString("method=queryAlertreceiverList&simcode=",
				(AlertreceiverListBean) request.getSession().getAttribute("theQueryBean"));
		Object[] args = getURLArgs("/WebApp/AlertreceiverListAction.do", strQueryString, (String) request.getSession()
				.getAttribute("S_BACK_URL"));
		return forwardInfoPage(mapping, request, "1303", null, args);
	}

	/**
	 *查询故障信息
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward queryAlertreceiverList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		AlertreceiverListBean bean = (AlertreceiverListBean) form;
		request.getSession().setAttribute("S_BACK_URL", null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String type = userInfo.getType();

		String sql = "select a.id,a.simcode,decode(a.emergencylevel,'1','轻微','2','普通','3','严重','轻微') emergencylevel,b.username userid,c.ContractorName contractorid from alertreceiverlist a,userinfo b,contractorinfo c";

		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
		sqlBuild.addConstant("a.userid=b.userid(+)");
		sqlBuild.addAnd();
		sqlBuild.addConstant("a.contractorid=c.contractorid(+)");

		sqlBuild.addConditionAnd("a.id like {0}", ((AlertreceiverListBean) form).getId());
		sqlBuild.addConditionAnd("a.simcode like {0}", "%" + ((AlertreceiverListBean) form).getSimcode() + "%");
		sqlBuild.addConditionAnd("a.emergencylevel like {0}", ((AlertreceiverListBean) form).getEmergencylevel());
		sqlBuild.addConditionAnd("a.userid like {0}", ((AlertreceiverListBean) form).getUserid());
		sqlBuild.addConditionAnd("c.regionid={0}", ((AlertreceiverListBean) form).getRegionid());
		sqlBuild.addTableRegion("c.regionid", super.getLoginUserInfo(request).getRegionid());

		String contractorID = ((AlertreceiverListBean) form).getContractorid();
		if (contractorID == null || contractorID.equals("")) {
			if (super.getLoginUserInfo(request).getType().equals("21")) {
				sqlBuild.addConstant(" and a.contractorid in (" + "                      select contractorid "
						+ "                      from contractorinfo"
						+ "                      where (state is null or state<>'1') and contractorid in("
						+ "                            select contractorid "
						+ "                            from contractorinfo "
						+ "                            where parentcontractorid='"
						+ super.getLoginUserInfo(request).getDeptID() + "' and (state is null or state<>'1')"
						+ "                            )" + "                      )");
			}
			if (super.getLoginUserInfo(request).getType().equals("22")) {
				sqlBuild.addConditionAnd("a.contractorid like {0}", super.getLoginUserInfo(request).getDeptID());
			}
		} else {
			sqlBuild.addConditionAnd("a.contractorid like {0}", ((AlertreceiverListBean) form).getContractorid());
		}

		List list = super.getDbService().queryBeans(sqlBuild.toSql());

		logger.info("查询 报警中心" + sqlBuild.toSql());

		request.getSession().setAttribute("queryresult", list);
		super.setPageReset(request);
		return mapping.findForward("queryAlertreceiverList");
	}

	/**
	 *
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward updateAlertreceiverList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		//bean.v
		AlertreceiverList data = new AlertreceiverList();
		BeanUtil.objectCopy((AlertreceiverListBean) form, data);

		super.getService().updateAlertreceiverList(data);
		//return mapping.findForward("sucessMsg");
		//Log
		log(request, " 更新报警接收信息 ", " 基础资料管理 ");
		String strQueryString = getTotalQueryString("method=queryAlertreceiverList&simcode=",
				(AlertreceiverListBean) request.getSession().getAttribute("theQueryBean"));
		Object[] args = getURLArgs("/WebApp/AlertreceiverListAction.do", strQueryString, (String) request.getSession()
				.getAttribute("S_BACK_URL"));
		return forwardInfoPage(mapping, request, "1302", null, args);
	}

	public String getTotalQueryString(String queryString, AlertreceiverListBean bean) {
		if (bean.getSimcode() != null) {
			queryString = queryString + bean.getSimcode();
		}
		if (bean.getId() != null) {
			queryString = queryString + "&id=" + bean.getId();
		}
		if (bean.getRegionid() != null) {
			queryString = queryString + "&regionid=" + bean.getRegionid();
		}
		if (bean.getEmergencylevel() != null) {
			queryString = queryString + "&emergencylevel=" + bean.getEmergencylevel();
		}
		return queryString;
	}

	private boolean checkAlertCode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		AlertreceiverList data = new AlertreceiverList();
		BeanUtil.objectCopy((AlertreceiverListBean) form, data);
		String sql = "select id,simcode,emergencylevel,userid,contractorid from alertreceiverlist";

		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
		sqlBuild.addConditionAnd("simcode = {0}", data.getSimcode());
		sqlBuild.addConditionAnd("emergencylevel={0}", data.getEmergencylevel());

		sql = sqlBuild.toSql();
		//logger.debug(sql);

		Vector vct = new Vector();

		try {
			vct = super.getDbService().queryVector(sql, "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (vct.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public ActionForward exportAlertreceiverListResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info(" 创建dao");
			List list = (List) request.getSession().getAttribute("queryresult");
			logger.info("得到list");
			super.getService().exportAlertreceiverListResult(list, response);
			logger.info("输出excel成功");
			return null;
		} catch (Exception e) {
			logger.error("导出信息报表出现异常:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}
}
