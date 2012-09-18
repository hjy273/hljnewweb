package com.cabletech.baseinfo.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.baseinfo.services.PatrolManSonBO;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.web.*;
import com.cabletech.commons.hb.QueryUtil;

public class PatrolManSonAction extends BaseInfoBaseDispatchAction{
    public PatrolManSonAction(){
    }


    private static Logger logger =
        Logger.getLogger( PatrolManAction.class.getName() );

    public ActionForward addPatrolManSon( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        PatrolManSonBean bean = ( PatrolManSonBean )form;
        PatrolManSon data = new PatrolManSon();
        BeanUtil.objectCopy( bean, data );
        data.setPatrolID( super.getDbService().getSeq( "patrolman_son_info", 10 ) );
        data.setRegionID( super.getLoginUserInfo( request ).getRegionID() );

        String PMType = ( String )request.getSession().getAttribute( "PMType" ); //获得巡检人员的管理方式
        if( PMType.equals( "group" ) ){ //按组管理
            super.getService().addPatrolManSon( data );
        }
        else{ //按人管理
            data.setParent_patrol( super.getDbService().getSeq( "patrolmaninfo", 10 ) );
            super.getService().addPartrolManSonByNoGroup( data );
        }
        //

        //Log
        log( request, " 增加巡检员信息（巡检员名称为："+bean.getPatrolName()+"） ", " 基础资料管理 " );

        return forwardInfoPage( mapping, request, "0024" );
    }


    public ActionForward loadPatrolSon( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        PatrolManSon data = super.getService().loadPatrolManSon( request.
                            getParameter(
                            "id" ) );
        PatrolManSonBean bean = new PatrolManSonBean();
        BeanUtil.objectCopy( data, bean );
        request.setAttribute( "patrolSonBean", bean );
        request.getSession().setAttribute( "patrolSonBean", bean );
        return mapping.findForward( "updatePatrolSon" );
    }


    public ActionForward queryPatrolSon( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
    	 PatrolManSonBean bean = (PatrolManSonBean)form;
    	request.getSession().setAttribute("S_BACK_URL",null); //
        request.getSession().setAttribute("theQueryBean", bean);//
        String sql = "select a.patrolID, a.patrolName, decode(a.sex,'1','男','2','女', '男') sex, a.phone, a.mobile, a.postalcode, a.identityCard, a.familyAddress, a.workRecord, nvl(b.contractorname,'') parentID, nvl(c.patrolname, '') groupid, a.jobInfo, decode(a.jobState,'1','在岗','2','休假','3','离职','在岗') jobState, a.remark from patrolman_son_info a, contractorinfo b, patrolmaninfo c ";
        //String parolname = ( ( PatrolManSonBean )form ).getPatrolName();
        String parolid = ( ( PatrolManSonBean )form ).getPatrolID();//add
        //logger.info("paroldid in action:" + parolid);
        //logger.info("contractorid in action:" + ( ( PatrolManSonBean )form ).getContractorID());
        //logger.info("regionid in action:" + ( ( PatrolManSonBean )form ).getRegionID());
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        /*
        if( parolname == null ){
            parolname = "";
        }
        else{
            parolname = parolname.replaceAll( "\'", "" );
        }*/
        try{
            QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

            sqlBuild.addConstant( " a.parentid = b.contractorid(+) " );
            sqlBuild.addAnd();
            sqlBuild.addConstant( " a.parent_patrol = c.patrolid(+) " );

            //sqlBuild.addConditionAnd( "a.patrolname like {0}",
            //"%" + parolname + "%" );
            if ("group".equals((String)request.getSession().getAttribute( "PMType"))){
            	sqlBuild.addConditionAnd( "a.parent_patrol = {0}",parolid); //add
            }else{
            	sqlBuild.addConditionAnd( "a.patrolid = {0}",parolid); //add
            }
            sqlBuild.addConditionAnd( "a.sex = {0}",
                ( ( PatrolManSonBean )form ).getSex() );
            sqlBuild.addConditionAnd( "a.parentid = {0}",
                ( ( PatrolManSonBean )form ).getParentID() );
//            sqlBuild.addConditionAnd( "a.parentid = {0}",
//                    ( ( PatrolManSonBean )form ).getContractorID()); //new
            sqlBuild.addConditionAnd( "a.parent_patrol = {0}",
                ( ( PatrolManSonBean )form ).getParent_patrol() );
            sqlBuild.addConditionAnd( "a.jobstate = {0}",
                ( ( PatrolManSonBean )form ).getJobState() );
            sql = sqlBuild.toSql();
            if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
                sql += "  and a.state is null and a.regionid='" + userinfo.getRegionID() + "'";
            }
            //代维单位用户
            if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
                sql += " and a.state is null and a.PARENTID ='" + userinfo.getDeptID() + "'";
            }
            //省移动用户
            if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
                sql += " and a.state is null";
            }
            //省代维用户
            if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
                sql += " and a.state is null and  a.PARENTID in ( SELECT   contractorid FROM contractorinfo CONNECT BY PRIOR contractorid = parentcontractorid START WITH contractorid ='"
                    + userinfo.getDeptID() + "')";
            }
            sql += " order by a.patrolName";
//            sqlBuild.addTableRegion( "a.regionid", super.getLoginUserInfo( request ).getRegionid() );
//            sqlBuild.addConstant( " and a.state is null" );
//            if( !userinfo.getDeptype().equals( "1" ) ){ //如果是代维单位只能看到本单位的信息
//                sqlBuild.addConditionAnd( "a.parentid = {0}", userinfo.getDeptID() );
//            }
//            sqlBuild.addConstant( " order by a.patrolName" );

//            System.out.println( sql );
            List list = super.getDbService().queryBeans( sql );
            logger.info("*******************:" +sql);
            request.getSession().setAttribute( "queryresult", list );
            this.setPageReset(request);
            return mapping.findForward( "querypatrolmanSonresult" );
        }
        catch( Exception ex ){
            logger.error( "查询巡检人员信息时出错： " + ex.getMessage() );
            return forwardInfoPage( mapping, request, "error" );
        }
    }


    public ActionForward updatePatrolSon( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        PatrolManSon data = new PatrolManSon();
        PatrolManSonBean bean=(PatrolManSonBean)form;
        BeanUtil.objectCopy( bean, data );
        //System.out.println( "PARENT: " + data.getParent_patrol() );
        String PMType = ( String )request.getSession().getAttribute( "PMType" ); //获得巡检人员的管理方式
        if( PMType.equals( "group" ) ){ //按组管理
            if( super.getService().updatePatrolMan( data ) == null ){
                return super.forwardErrorPage( mapping, request, "error" );
            }
        }
        else{ //按人管理
            if( !super.getService().updatePatrolManByNoGroup( data ) ){
                return super.forwardErrorPage( mapping, request, "error" );
            }
        }

        //Log
        log( request, " 更新巡检员信息（巡检员名称为："+bean.getPatrolName()+"） ", " 基础资料管理 " );
        String strQueryString = getTotalQueryString("method=queryPatrolSon&patrolName=",(PatrolManSonBean)request.getSession().getAttribute("theQueryBean"));
 		 Object[] args = getURLArgs("/WebApp/patrolSonAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
 		 return forwardInfoPage( mapping, request, "0025",null,args);
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
    public ActionForward deletePatrolSon( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
    	WebApplicationContext ctx=getWebApplicationContext();
    	PatrolManSonBO patrolManSonBO= new PatrolManSonBO();
        PatrolManSon data = super.getService().loadPatrolManSon( request.getParameter( "id" ) );
        String PMType = ( String )request.getSession().getAttribute( "PMType" ); //获得巡检人员的管理方式
        String name=patrolManSonBO.loadPatrolManSon(request.getParameter("id")).getPatrolName();
        if( PMType.equals( "group" ) ){ //按组管理
            if( super.getService().removePatrolManSon( data ) ){
                log( request, " 删除巡检员信息（巡检员名称为："+name+"） ", " 基础资料管理 " );
                String strQueryString = getTotalQueryString("method=queryPatrolSon&patrolName=",(PatrolManSonBean)request.getSession().getAttribute("theQueryBean"));
       		 Object[] args = getURLArgs("/WebApp/patrolSonAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
       		 return forwardInfoPage( mapping, request, "0026",null,args);
            }
            else{
                return forwardErrorPage( mapping, request, "error" );
            }
        }
        else{ //按人管理
            if( super.getService().removePatrolManSonByNoGroup( data ) ){
                log( request, " 删除巡检员信息 ", " 基础资料管理 " );
                String strQueryString = getTotalQueryString("method=queryPatrolSon&patrolName=",(PatrolManSonBean)request.getSession().getAttribute("theQueryBean"));
          		 Object[] args = getURLArgs("/WebApp/patrolSonAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
          		 return forwardInfoPage( mapping, request, "0026",null,args);
            }
            else{
                return forwardErrorPage( mapping, request, "error" );
            }
        }
    }
    
    
    public String getTotalQueryString(String queryString,PatrolManSonBean bean){
    	if (bean.getPatrolName() != null){
    		queryString = queryString + bean.getPatrolName();
    	}
    	if (bean.getId()!= null){
    		queryString = queryString + "&id=" + bean.getId();
    	}
    	if (bean.getRegionID() != null){
    		queryString = queryString + "&regionID=" + bean.getRegionID();
    	}
    	if (bean.getParentID() != null){
    		queryString = queryString + "&parentID=" + bean.getParentID();
    	}
    	if (bean.getParent_patrol() != null){
    		queryString = queryString + "&parent_patrol=" + bean.getParent_patrol();
    	}
    	return queryString;
    }

    public ActionForward exportPatrolSonResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "得到list" );
            super.getService().exportPatrolSonResult( list, response );
            logger.info( "输出excel成功" );
            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    public ActionForward showPatrolManSon( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        QueryUtil query = new QueryUtil();
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        List reginfo = null;
        List coninfo = null;
        List uinfo = null;

////////////////////////查询所有区域----省级用户
        String region = "select  r.REGIONNAME, r.REGIONID "
                      + " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
////////////////////////代维
        String con = "select  c.CONTRACTORID, c.CONTRACTORNAME, c.REGIONID "
                      + " from  contractorinfo c "
                      + " where c.STATE is null ";
////////////////////////巡检人
         String user = "select p.PATROLID, p.PATROLNAME, p.REGIONID, p.PARENTID "
                       + " from patrolmaninfo p "
                       + " where p.STATE is null ";

       //市移动
        if( userinfo.getType().equals("12")){

            con += "  and c.regionid IN ('" + userinfo.getRegionID() + "') ";
            user += "  and p.regionid IN ('" + userinfo.getRegionID() + "')  order by p.PARENTID ";

            coninfo = query.queryBeans( con );
            uinfo = query.queryBeans( user );

        }
        //市代维
        if( userinfo.getType().equals("22")){

            user += "  and p.regionid IN ('" + userinfo.getRegionID() + "')  order by p.PARENTID ";

            uinfo = query.queryBeans( user );

        }
        //省移动
         if( userinfo.getType().equals("11")){
            user += " order by p.PARENTID ";
            reginfo = query.queryBeans( region );
            coninfo = query.queryBeans( con );
            uinfo = query.queryBeans( user );
        }
        //省代维
         if( userinfo.getType().equals("21")){

            user += " order by p.PARENTID ";

            reginfo = query.queryBeans( region );
            coninfo = query.queryBeans( con );
            uinfo = query.queryBeans( user );

        }

        request.setAttribute( "reginfo", reginfo );
        request.setAttribute( "coninfo", coninfo );
        request.setAttribute( "uinfo", uinfo );
        return mapping.findForward( "showpatrolmanson" );
        //return mapping.findForward( "showpatrolmansonajax" );
    }

}
