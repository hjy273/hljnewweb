package com.cabletech.baseinfo.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.web.*;
import com.cabletech.power.*;
import java.sql.ResultSet;

public class DepartAction extends BaseInfoBaseDispatchAction{

    private static Logger logger =
        Logger.getLogger( DepartAction.class.getName() );

    /**
     * ����һ������
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward addDepart( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        if( !CheckPower.checkPower( request.getSession(), "70202" ) ){
            return mapping.findForward( "powererror" );
        }
        DepartBean bean = ( DepartBean )form;
        Depart data = new Depart();
        BeanUtil.objectCopy( bean, data );
        data.setDeptID( super.getDbService().getSeq( "departinfo", 8 ) );

        super.getService().createDeaprt( data );

        //Log
        log( request, " ���Ӳ�����Ϣ����������Ϊ��"+bean.getDeptName()+"�� ", " ϵͳ���� " );

        return forwardInfoPage( mapping, request, "0001" );
    }


    /**
     * ���벿����Ϣ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward loadDepart( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        if( !CheckPower.checkPower( request.getSession(), "70204" ) ){
            return mapping.findForward( "powererror" );
        }
        Depart data = super.getService().loadDepart( request.getParameter( "id" ) );
        request.getSession().setAttribute("theRegionFor11",data.getRegionid());
        DepartBean bean = new DepartBean();
        BeanUtil.objectCopy( data, bean );
        request.setAttribute( "departBean", bean );
        return mapping.findForward( "updateDepart" );
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
    public ActionForward deleteDepart( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        if( !CheckPower.checkPower( request.getSession(), "70205" ) ){
            return mapping.findForward( "powererror" );
        }
		ResultSet rst = null;
        String id = ( String )request.getParameter( "id" );
        String sqlc = "select count(*) aa from userinfo u where u.state is null and u.DEPTID='" + id  + "'";
        String sql = "update deptinfo set state = '1' where deptid='" + id + "'";
        try{
            QueryUtil qu = new QueryUtil();
            rst = qu.executeQuery(sqlc);
            if(rst != null && rst.next()){
               if(!rst.getString("aa").equals("0")){
                   rst.close();
                   return forwardInfoPage( mapping, request, "0003haveuser" );
               }
            }
            UpdateUtil exec = new UpdateUtil();
            exec.executeUpdate( sql );
            log( request, " ɾ��������Ϣ ", " ϵͳ���� " );
            String strQueryString = getTotalQueryString("method=queryDepart&departName=",(DepartBean)request.getSession().getAttribute("theQueryBean"));
            Object[] args = getURLArgs("/WebApp/departAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
            return forwardInfoPage( mapping, request, "0003",null,args);
        }
        catch( Exception e ){
            if(rst !=null){
                rst.close();
            }
            logger.debug( "ɾ��������Ϣ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
        //Depart data = super.getService().loadDepart(request.getParameter("id"));
        // super.getService().removeDepart(data);
        //Log

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
    public ActionForward queryDepart( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	DepartBean bean = (DepartBean)form;
    	 request.getSession().setAttribute("S_BACK_URL",null); //
	     request.getSession().setAttribute("theQueryBean", bean);//
        if( !CheckPower.checkPower( request.getSession(), "70203" ) ){
            return mapping.findForward( "powererror" );
        }
        String strRegion =((DepartBean)form).getRegionid();
        if (strRegion == null ){
            strRegion = super.getLoginUserInfo( request ).getRegionid();
        }

        String sql = "select a.DeptID, a.DeptName, a.LinkmanInfo, c.deptname ParentID, a.ParentName, a.Remark, b.regionname regionid from deptinfo a, region b, (select deptid , deptname from deptinfo) c";

        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

        sqlBuild.addConstant( "a.regionid = b.regionid(+)" );
        sqlBuild.addAnd();
        sqlBuild.addConstant( "a.parentid = c.deptid(+)" );

        sqlBuild.addConditionAnd( "a.DeptName like {0}",
            "%" + ( ( DepartBean )form ).getDeptName() + "%" );
        sqlBuild.addConditionAnd( "a.regionid={0}",
            ( ( DepartBean )form ).getRegionid() );
        sqlBuild.addConditionAnd( "a.ParentID={0}",
            ( ( DepartBean )form ).getParentID() );
        sqlBuild.addConditionAnd( "a.LinkmanInfo like {0}",
            "%" + ( ( DepartBean )form ).getLinkmanInfo() + "%" );
        //sqlBuild.addConditionAnd("a.state={0}", ( (DepartBean) form).getState());
        if (!strRegion.equals("")){
            sqlBuild.addTableRegion( "a.regionid", strRegion );
        }
            //super.getLoginUserInfo( request ).getRegionid() );
        sql = sqlBuild.toSql();
        sql = sql + " and a.state is null order by a.regionid , a.deptid";
        try{
            //logger.info("sql"+sql);
            List list = super.getDbService().queryBeans( sql );
            request.getSession().setAttribute( "queryresult", list );
            super.setPageReset(request);
            logger.info( "SQL:" + sql );
            return mapping.findForward( "querydepartresult" );
        }
        catch( Exception e ){
            logger.error( "��ѯ������Ϣ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
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
    public ActionForward updateDepart( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        if( !CheckPower.checkPower( request.getSession(), "70204" ) ){
            return mapping.findForward( "powererror" );
        }

        //bean.v
        Depart data = new Depart();
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        if (userinfo.getType().equals("12")){
            ((DepartBean)form).setRegionid(userinfo.getRegionID());
        }else if (userinfo.getType().equals("11")){
            String theRegionFor11 = (String)request.getSession().getAttribute( "theRegionFor11");
            logger.info("the original region is:" + theRegionFor11);
            ((DepartBean)form).setRegionid(theRegionFor11);
        }
        DepartBean bean=(DepartBean)form;
        BeanUtil.objectCopy( bean, data );
        super.getService().updateDepart( data );
        //Log
        log( request, " ���²�����Ϣ����������Ϊ��"+bean.getDeptName()+"�� ", " ϵͳ���� " );
        String strQueryString = getTotalQueryString("method=queryDepart&departName=",(DepartBean)request.getSession().getAttribute("theQueryBean"));
        Object[] args = getURLArgs("/WebApp/departAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
        return forwardInfoPage( mapping, request, "0002",null,args);
    }


    public ActionForward exportDepartResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "�õ�list" );
            super.getService().exportDepartResult( list, response );
            logger.info( "���excel�ɹ�" );
            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    public ActionForward showAdvancedDepart( ActionMapping mapping,
       ActionForm form,
       HttpServletRequest request,
       HttpServletResponse response ) throws
       ClientException, Exception{
       QueryUtil query = new QueryUtil();
       UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
       List reginfo = null;
       List deptinfo = null;

       if( userinfo.getType().equals("11") ){
           ////////////////////////��ѯ��������----ʡ���û�
           String sqlregion = "select  r.REGIONNAME, r.REGIONID "
                              + " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
           String sqldept = "select d.deptid,d.deptname,d.regionid "
                            + " from deptinfo d where d.state is null order by d.regionid,d.parentid,d.deptid";
           reginfo = query.queryBeans( sqlregion );
           deptinfo = query.queryBeans(sqldept);
           request.setAttribute( "reginfo", reginfo );
           request.setAttribute("deptinfo",deptinfo);
           logger.info( "************:" + sqlregion );
           logger.info( "************:" + sqldept );
       }
       return mapping.findForward( "showadvanceddepart" );
   }
    public String getTotalQueryString(String queryString,DepartBean bean){
    	if (bean.getDeptName() != null){
    		queryString = queryString + bean.getDeptName();
    	}
    	if (bean.getDeptID()!= null){
    		queryString = queryString + "&deptID=" + bean.getDeptID();
    	}
    	if (bean.getRegionid() != null){
    		queryString = queryString + "&regionid=" + bean.getRegionid();
    	}
    	if (bean.getParentID() != null){
    		queryString = queryString + "&parentID=" + bean.getParentID();
    	}
    	if (bean.getRemark() != null){
    		queryString = queryString + "&remark=" + bean.getRemark();
    	}
    	if (bean.getLinkmanInfo() != null){
    		queryString = queryString + "&linkmanInfo=" + bean.getLinkmanInfo();
    	}
    	return queryString;
    }


}
