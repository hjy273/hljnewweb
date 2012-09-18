package com.cabletech.baseinfo.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.baseinfo.services.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.web.*;
import com.cabletech.utils.SMSkit.*;

public class PatrolManAction extends BaseInfoBaseDispatchAction{

	private static Logger logger =
		Logger.getLogger( PatrolManAction.class.getName() );

	public ActionForward addPatrolMan( ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{

		PatrolManBean bean = ( PatrolManBean )form;
		PatrolMan data = new PatrolMan();
		BeanUtil.objectCopy( bean, data );
		data.setPatrolID( super.getDbService().getSeq( "patrolmaninfo", 10 ) );
		data.setRegionID( super.getLoginUserInfo( request ).getRegionID() );

		super.getService().createPatrolMan( data );

		//Log
		log( request, " ����Ѳ��ά��С����Ϣ��Ѳ��������Ϊ��"+bean.getPatrolName()+"�� ", " �������Ϲ��� " );
	//	refreshSMSCache( request );

		return forwardInfoPage( mapping, request, "0021" );
	}


	public ActionForward loadPatrol( ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{

		PatrolMan data = super.getService().loadPatrolMan( request.getParameter(
		"id" ) );
		PatrolManBean bean = new PatrolManBean();
		BeanUtil.objectCopy( data, bean );
		request.setAttribute( "patrolBean", bean );
		request.getSession().setAttribute( "patrolBean", bean );
		return mapping.findForward( "updatePatrol" );
	}


	public ActionForward queryPatrol( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		PatrolManBean bean = (PatrolManBean)form;
		request.getSession().setAttribute("S_BACK_URL",null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		PatrolManBO patrolmanBo = new PatrolManBO();
		String sql = patrolmanBo.createQuerySqu( userinfo );
		if( ( ( PatrolManBean )form ).getPatrolName() != null && ! ( ( PatrolManBean )form ).getPatrolName().equals( "" ) ){
			sql += " and a.patrolname like '%" + ( ( PatrolManBean )form ).getPatrolName() + "%'";
		}
		if( ( ( PatrolManBean )form ).getRegionID() != null && ! ( ( PatrolManBean )form ).getRegionID().equals( "" ) ){
			sql += " and a.regionid='" + ( ( PatrolManBean )form ).getRegionID() + "'";
		}

		if( ( ( PatrolManBean )form ).getParentID() != null && ! ( ( PatrolManBean )form ).getParentID().equals( "" ) ){
			sql += " and a.parentid ='" + ( ( PatrolManBean )form ).getParentID() + "'";
		}
		//String sql = "select a.patrolID, a.patrolName, decode(a.sex,'1','��','2','Ů', '��') sex, a.phone, a.mobile, a.postalcode, a.identityCard, a.familyAddress, a.workRecord, nvl(b.contractorname,'') parentID, a.jobInfo, decode(a.jobState,'1','�ڸ�','2','�ݼ�','3','��ְ','�ڸ�') jobState, a.remark from patrolmaninfo a, contractorinfo b";
		//QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
		//sqlBuild.addConstant("a.parentid = b.contractorid(+)");
		//sqlBuild.addConditionAnd("a. = {0}",( (PatrolManBean) form).getPatrolName());
		//sqlBuild.addConditionAnd("a.sex = {0}", ( (PatrolManBean) form).getSex());
		//sqlBuild.addConditionAnd("a.parentid = {0}",( (PatrolManBean) form).getParentID());
		//sqlBuild.addConditionAnd("a.jobstate = {0}", ( (PatrolManBean) form).getJobState());
		//sqlBuild.addTableRegion("a.regionid",super.getLoginUserInfo(request).getRegionid());
		//sqlBuild.addConstant("  and a.state is null  ");
		//sql = sqlBuild.toSql();
		sql = sql + " order by a.patrolname";
		logger.info("the sql in querypatrol" + sql);
		//        System.out.println( "sql :" + sql );
		List list = super.getDbService().queryBeans( sql );
		request.getSession().setAttribute( "queryresult", list );
		super.setPageReset(request);
		return mapping.findForward( "querypatrolmanresult" );
	}


	public ActionForward updatePatrol( ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{

		PatrolMan data = new PatrolMan();
		PatrolManBean bean=(PatrolManBean)form;
		BeanUtil.objectCopy( bean, data );
		super.getService().updatePatrolMan( data );

		//Log
		log( request, " ����Ѳ��ά��С����Ϣ ��Ѳ��������Ϊ��"+bean.getPatrolName()+"��", " �������Ϲ��� " );
		//2005 / 08 / 30
		// refreshSMSCache( request );
		//    String strQueryString = getTotalQueryString("method=queryPatrol&patrolName=",(PatrolManBean)request.getSession().getAttribute("theQueryBean"));
		// Object[] args = getURLArgs("/WebApp/patrolAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
		Object[] args = new Object[1];
		args[0] = request.getSession().getAttribute("S_BACK_URL"); 
		return super.forwardInfoPage(mapping, request, "0022",null,args);
		//  return forwardInfoPage( mapping, request, "0022",data.getPatrolName(),args);
	}


	/**
	 * ɾ��
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward deletePatrol( ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		//WebApplicationContext ctx=getWebApplicationContext();
		//PatrolManBO patrolManBO=(PatrolManBO)ctx.getBean("patrolManBO");
		PatrolManBO patrolManBO = new PatrolManBO();
		String id = ( String )request.getParameter( "id" );
		String sql = "update patrolmaninfo set state = '1' where patrolid='" + id + "'";
		PatrolMan patrolMan=patrolManBO.loadPatrolMan(id);
		String name=patrolMan.getPatrolName();
		try{
			UpdateUtil exec = new UpdateUtil();
			exec.executeUpdate( sql );
			log( request, " ɾ��Ѳ��С����Ϣ  ��Ѳ��������Ϊ��"+name+"��", " �������Ϲ��� " );
			/*  String strQueryString = getTotalQueryString("method=queryPatrol&patrolName=",(PatrolManBean)request.getSession().getAttribute("theQueryBean"));
   		 Object[] args = getURLArgs("/WebApp/patrolAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
   		 return forwardInfoPage( mapping, request, "0023",null,args);*/
			Object[] args = new Object[1];
			args[0] = request.getSession().getAttribute("S_BACK_URL"); 
			return super.forwardInfoPage(mapping, request, "0023",null,args);
		}
		catch( Exception e ){
			logger.debug( "ɾ��Ѳ��С����Ϣ�쳣:" + e.getMessage() );
			return forwardErrorPage( mapping, request, "error" );
		}
		//        PatrolMan data = super.getService().loadPatrolMan(request.getParameter(
		//            "id"));
		//        super.getService().removePatrolMan(data);
		//        //Log
		//        log(request, " ɾ��Ѳ��ά��С����Ϣ ", " �������Ϲ��� ");
		//        return forwardInfoPage(mapping, request, "0023");
	}

	public String getTotalQueryString(String queryString,PatrolManBean bean){
		if (bean.getPatrolName() != null){
			queryString = queryString + bean.getPatrolName();
		}
		if (bean.getPatrolID()!= null){
			queryString = queryString + "&patrolID=" + bean.getPatrolID();
		}
		if (bean.getParentID() != null){
			queryString = queryString + "&parentID=" + bean.getParentID();
		}
		if (bean.getRegionID() != null){
			queryString = queryString + "&regionID=" + bean.getRegionID();
		}
		if (bean.getId() != null){
			queryString = queryString + "&id=" + bean.getId();
		}
		return queryString;
	}

	/**
	 * ȡ������Ѳ����б�
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward getInstantList( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{

		String tableA = "(select distinct EXECUTORID, simid,  max(EXECUTETIME) from PLANEXECUTE where to_char(EXECUTETIME,'yyyy/mm/dd hh24:mi')  > to_char(sysdate-1/12,'yyyy/mm/dd hh24:mi') group by EXECUTORID, simid ) a,";
		String sql =
			"select b.PATROLNAME patrolName, a.simid sim, nvl(c.contractorname,'') parentID , nvl(b.jobInfo,'') jobinfo from " +
			tableA;
		sql += " patrolmaninfo b, contractorinfo c ";
		sql +=
			" where a. EXECUTORID = b.PATROLID(+) and b.PARENTID = c.CONTRACTORID(+) ";

		//System.out.println("*********************************" + sql);

		List list = super.getDbService().queryBeans( sql );

		request.getSession().setAttribute( "queryresult_TWO", list );
		return mapping.findForward( "querypatrolmanresult_login" );
	}


	/**
	 * ���û���ӿ�
	 * @param request HttpServletRequest
	 * @throws Exception
	 */
	public void refreshSMSCache( HttpServletRequest request ) throws Exception{

		try{
			String[] infoArr = new String[1];
			infoArr[0] = super.getLoginUserInfo( request ).getUserID();
			SmsUtil smsutil = SmsUtil.getInstance();
			smsutil.refreshCache( infoArr );
		}
		catch( Exception e ){
			System.out.println( "ˢ�»����쳣 ��" + e.toString() );
		}
	}
	public ActionForward exportPatrolMan( ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ){
		try{
			logger.info( " ����dao" );
			List list = ( List )request.getSession().getAttribute( "queryresult" );
			logger.info( "�õ�list" );
			super.getService().exportPatrolMan( list, response );
			logger.info( "���excel�ɹ�" );
			return null;
		}
		catch( Exception e ){
			logger.error( "������Ϣ��������쳣:" + e.getMessage() );
			return forwardErrorPage( mapping, request, "error" );
		}
	}
	public ActionForward showAdvancedPatrolMan( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		QueryUtil query = new QueryUtil();
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		List reginfo = null;
		List coninfo = null;


		if( userinfo.getType().equals("11") || userinfo.getType().equals("21") ){
			////////////////////////��ѯ��������----ʡ���û�
			String sqlregion = "select  r.REGIONNAME, r.REGIONID "
				+ " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
			String sqlcon = null;
			if (userinfo.getType().equals("11")){
				sqlcon = "select  c.CONTRACTORID, c.CONTRACTORNAME, c.REGIONID "
					+ " from  contractorinfo c "
					+ " where c.STATE is null  order by c.regionid";
			}
			if (userinfo.getType().equals("21")){
				sqlcon = "select  c.CONTRACTORID, c.CONTRACTORNAME, c.REGIONID "
					+ " from  contractorinfo c "
					+ " where c.STATE is null "
					+ " and contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"')"
					+ " order by c.regionid";
			}

			reginfo = query.queryBeans( sqlregion );
			coninfo = query.queryBeans(sqlcon);
			request.setAttribute( "reginfo", reginfo );
			request.setAttribute("coninfo",coninfo);
			logger.info( "************:" + sqlregion );
			logger.info( "************:" + sqlcon );
		}
		return mapping.findForward( "showadvancedpatrolman" );
	}

}
