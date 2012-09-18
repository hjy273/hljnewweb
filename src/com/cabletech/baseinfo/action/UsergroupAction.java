package com.cabletech.baseinfo.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.baseinfo.services.UsergroupBO;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.generatorID.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.web.*;

public class UsergroupAction extends BaseInfoBaseDispatchAction{

	private static Logger logger =
		Logger.getLogger( UsergroupAction.class.getName() );

	public UsergroupAction(){
	}


	/**
	 * ����û���
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward addUsergroup( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{

		String usersStr = request.getParameter( "users" );
		String menusStr = request.getParameter( "menus" );

		/*
                �����û�����Ϣ
		 */
		UsergroupBean bean = ( UsergroupBean )form;
		Usergroup data = new Usergroup();
		BeanUtil.objectCopy( bean, data );
		data.setId( super.getDbService().getSeq( "usergroupmaster", 5 ) );
		UsergroupBO usergroupBO=new UsergroupBO();
		if(usergroupBO.validate(data)){
			String msg = "���û������Ѿ���ռ�ã�����������ԣ�";
			request.setAttribute("innerMsg", msg);
			return mapping.findForward("addUsergroup");
		}
		super.getService().addUsergroup( data );

		//Ϊ�û�������û�
		super.getService().saveUserGroupUser(usersStr, data.getId());
		//�û�����ӹ���ģ��Ȩ��
		super.getService().saveUserGroupModule(menusStr, data.getId());
		//Log
		log( request, " �����û�����Ϣ���û�������Ϊ��"+bean.getGroupname()+"�� ", " ϵͳ���� " );

		return forwardInfoPage( mapping, request, "0217" );
	}

	/**
	 * �����û�����Ϣ
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward editUsergroup( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{

		String usersStr = request.getParameter( "users" );
		logger.info( "users :" + usersStr );
		String menusStr = request.getParameter( "menus" );
		logger.info( "menus :" + menusStr );
		UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		UsergroupBean bean = ( UsergroupBean )form;
		Usergroup data = new Usergroup();
		BeanUtil.objectCopy( bean, data );
		UsergroupBO usergroupBO=new UsergroupBO();
		if(usergroupBO.validate(data)){
			String msg = "���û������Ѿ���ռ�ã�����������ԣ�";
			request.setAttribute("innerMsg", msg);
			return mapping.findForward("editUsergroup");
		}
		super.getService().updateUsergroup( data );
		super.getService().deleteUgRelative( data.getId() );
		super.getService().deleteUserRelative( data.getId(), userInfo );

		//Ϊ�û�������û�
		super.getService().saveUserGroupUser(usersStr, data.getId());

		//�û�����ӹ���ģ��Ȩ��
		super.getService().saveUserGroupModule(menusStr, data.getId());

		log( request, " �����û�����Ϣ���û�������Ϊ��"+bean.getGroupname()+"�� ", " ϵͳ���� " );

		return forwardInfoPage( mapping, request, "0219" );
	}


	/**
	 * Ϊ�û������Ȩ��
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward editPurview( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{

		String usersStr = request.getParameter( "users" );
		String menusStr = request.getParameter( "menus" );

		String id = request.getParameter( "id" );
		UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		super.getService().deleteUgRelative( id );
		super.getService().deleteUserRelative( id, userInfo );

		//Ϊ�û�������û�
		super.getService().saveUserGroupUser(usersStr, id);
		//�û�����ӹ���ģ��Ȩ��
		super.getService().saveUserGroupModule(menusStr, id);

		log( request, " �����û�����Ϣ ", " ϵͳ���� " );
		String strQueryString = getTotalQueryString("method=getUserGroups&groupname=",(UsergroupBean)request.getSession().getAttribute("theQueryBean"));
		Object[] args = getURLArgs("/WebApp/UsergroupAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
		return forwardInfoPage( mapping, request, "0219",null,args);
	}


	/**
	 * ��ȡָ��������û�����Ϣ
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward getUserGroups( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		UsergroupBean bean = (UsergroupBean)form;
		request.getSession().setAttribute("S_BACK_URL",null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		String sql = "select a.id id, a.groupname groupname, a.type type, b.regionid regionid, b.regionname region, " +
		" to_char((select count(*) from usergourpuserlist ul,userinfo u"+
		" where ul.usergroupid=a.id and ul.userid=u.userid and u.state is null )) usernum "+
		" from usergroupmaster a, region b ";
		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

		sqlBuild.addConstant( " a.regionid = b.regionid " );
		sqlBuild.addTableRegion( " a.regionid",
				super.getLoginUserInfo( request ).getRegionid() );

		sqlBuild.addConstant( " order by b.regionid , a.id " );
		logger.info("=======��ѯ�û���================================  "+sqlBuild.toSql());
		List list = super.getDbService().queryBeans( sqlBuild.toSql() );

		request.getSession().setAttribute( "queryresult", list );
		super.setPageReset(request);
		return mapping.findForward( "queryUsergroupResult" );

	}


	/**
	 * �����û�����Ϣ
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward loadUsergroup( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		//��ȡ�û�����Ϣ
		String id = request.getParameter("id");
		Usergroup data = super.getService().loadUsergroup( id );
		UsergroupBean bean = new UsergroupBean();
		BeanUtil.objectCopy( data, bean );
		request.setAttribute( "UsergroupBean", bean );
		//��ȡ�û����µ��û�
		String relativeUsersStr = super.getService().getRelativeUsers( bean.getId() );

		request.setAttribute( "rUsersStr", relativeUsersStr );

		return mapping.findForward( "toEditUsergroup" );
	}

	public ActionForward loadMouduleList( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		String id = request.getParameter( "value" );
		Usergroup data = super.getService().loadUsergroup( id );
		UsergroupBean bean = new UsergroupBean();
		BeanUtil.objectCopy( data, bean );
		request.setAttribute( "UsergroupBean", bean );

		String relativeUsersStr = super.getService().getRelativeUsers( bean.
				getId() );
		String relativeModulesStr = super.getService().getRelativeModules( bean.
				getId() );

		request.setAttribute( "rUsersStr", relativeUsersStr );
		request.setAttribute( "rModuleStr", relativeModulesStr );

		request.setAttribute( "value", id );

		String sql = "select a.id id, a.groupname groupname, a.type type, b.regionid regionid, b.regionname region from usergroupmaster a, region b";
		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

		sqlBuild.addConstant( " a.regionid = b.regionid " );
		sqlBuild.addTableRegion( " a.regionid",
				super.getLoginUserInfo( request ).getRegionid() );

		sqlBuild.addConstant( " order by b.regionid , a.id " );

		List list = super.getDbService().queryBeans( sqlBuild.toSql() );

		request.setAttribute("grouplists",list);


		return mapping.findForward( "toEditPurview" );
	}


	public ActionForward loadEditFrom( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
		request.setAttribute( "value", "" );
		request.removeAttribute("UsergroupBean");

		String sql = "select a.id id, a.groupname groupname, a.type type, b.regionid regionid, b.regionname region from usergroupmaster a, region b";
		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

		sqlBuild.addConstant( " a.regionid = b.regionid " );
		sqlBuild.addTableRegion( " a.regionid",
				super.getLoginUserInfo( request ).getRegionid() );

		sqlBuild.addConstant( " order by b.regionid , a.id " );

		List list = super.getDbService().queryBeans( sqlBuild.toSql() );

		request.setAttribute("grouplists",list);

		return mapping.findForward( "loadEditFrom" );
	}


	public ActionForward deleteUsergroup( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{

		Usergroup data = super.getService().loadUsergroup( request.getParameter(
		"id" ) );
		super.getService().deleteUgRelative( data.getId() );
		super.getService().removeUsergroup( data );
		//Log
		log( request, " ɾ���û�����Ϣ���û�������Ϊ��"+data.getGroupname()+"�� ", " ϵͳ���� " );
		String strQueryString = getTotalQueryString("method=getUserGroups&groupname=",(UsergroupBean)request.getSession().getAttribute("theQueryBean"));
		Object[] args = getURLArgs("/WebApp/UsergroupAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
		return forwardInfoPage( mapping, request, "0218",null,args);

	}


	public String getTotalQueryString(String queryString,UsergroupBean bean){
		if (bean.getGroupname() != null){
			queryString = queryString + bean.getGroupname();
		}
		if (bean.getId()!= null){
			queryString = queryString + "&id=" + bean.getId();
		}
		if (bean.getRegionid() != null){
			queryString = queryString + "&regionid=" + bean.getRegionid();
		}
		return queryString;
	}
	/**
	 * ȡ�ÿ��õ�ģ���б�
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward getModule2Edit( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{

		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String resultVct = super.getService().getMouduleAndMenu(type, id).replace("\"","'");
		request.setAttribute( "queryresult", resultVct );
		request.setAttribute( "functionFlag",request.getParameter( "functionFlag" ) );

		return mapping.findForward( "getMoudle2Edit" );
	}


	/**
	 * ȡ�ÿ��õ��û�
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward getUsers2Edit( ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ) throws
			ClientException, Exception{
//		Vector resultVct = super.getService().getRegionAndUsers( request );
		UsergroupBO userGroupBo = new UsergroupBO();
		String type = request.getParameter("type");
		String groupId = request.getParameter("id");
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		String jsonResult = userGroupBo.getRegionAndUsers2(userinfo,type,groupId);
		
		request.setAttribute( "jsonResult", jsonResult );
		request.setAttribute( "functionFlag",request.getParameter( "functionFlag" ) );

		return mapping.findForward( "getUsers2Edit" );
	}


	public ActionForward exportUsergroupResult( ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response ){
		try{
			logger.info( " ����dao" );
			List list = ( List )request.getSession().getAttribute( "queryresult" );
			logger.info( "�õ�list" );
			super.getService().exportUsergroupResult( list, response );
			logger.info( "���excel�ɹ�" );
			return null;
		}
		catch( Exception e ){
			logger.error( "������Ϣ��������쳣:" + e.getMessage() );
			return forwardErrorPage( mapping, request, "error" );
		}
	}
}
