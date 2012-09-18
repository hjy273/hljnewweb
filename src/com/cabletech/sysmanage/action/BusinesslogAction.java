package com.cabletech.sysmanage.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cabletech.baseinfo.action.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.web.*;
import com.cabletech.sysmanage.beans.*;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import org.apache.log4j.Logger;

public class BusinesslogAction extends BaseInfoBaseDispatchAction{


    private static Logger logger = Logger.getLogger( BusinesslogAction.class.
                                   getName() );

    public BusinesslogAction(){
    }


    public ActionForward queryLogList( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String username = "";
        if( request.getParameter( "username" ) != null ){
            username = request.getParameter( "username" );
        }
        String userid = "";
        if( request.getParameter( "userid" ) != null ){
            userid = request.getParameter( "userid" );
        }
        String regionid = "";
        if( request.getParameter( "regionid" ) != null ){
            regionid = request.getParameter( "regionid" );
        }
        String deptid = "";
        if( request.getParameter( "deptid" ) != null ){
            deptid = request.getParameter( "deptid" );
        }

        String contractorid = "";
        if( request.getParameter( "contractorid" ) != null ){
            contractorid = request.getParameter( "contractorid" );
        }
        String begintime = "";
        if( request.getParameter( "beginDate" ) != null ){
            begintime = request.getParameter( "beginDate" );
        }
        String endtime = "";
        if( request.getParameter( "endDate" ) != null ){
            endtime = request.getParameter( "endDate" );
        }
        String deptype = request.getParameter("deptype");
        logger.info("用户:----"+username);
        logger.info("区域id:----"+regionid);
        logger.info("部门id:----"+deptid);
        logger.info("代维id:----"+contractorid);
        logger.info("begintime:----"+begintime);
        logger.info("endtime:----"+endtime);
        String sql =
        "SELECT TO_CHAR (b.logdate, 'YY/MM/DD HH24:MI:SS') logdate, b.ip, b.username, b.module, b.message msg,u.userid"
        + " FROM businesslog b, userinfo u "
        + " where b.USERNAME = u.USERNAME ";

            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
            //市移动
            if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
                sql += " and u.regionid = '" + userinfo.getRegionid() + "' ";
            }
            //市代维
            if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
                sql += " and u.DEPTYPE = '2' and u.regionid = '" + userinfo.getRegionID() + "' ";
            }
            //省移动
            if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

            }
            //省代维
            if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
                sql += " and u.DEPTYPE = '2' ";
            }
            if( !regionid.equals( "" ) ){
                sql += " and u.regionid = '" + regionid + "' ";
            }
            if( !deptid.equals( "" ) ){
                sql += " and u.deptid = '" + deptid + "' ";
            }
            if( !contractorid.equals( "" ) ){
                sql += " and u.deptid = '" + contractorid + "' ";
            }
            if( !username.equals( "" ) ){
                sql += " and u.username = '" + username + "' ";
            }
            if( !userid.equals( "" ) ){
                sql += " and u.userid = '" + userid + "' ";
            }
            if( !begintime.equals( "" ) ){
                sql += " and b.logdate >= TO_DATE('" + begintime + "','YYYY-MM-DD')";
            }
            if( !endtime.equals( "" ) ){
                sql += " and b.logdate < TO_DATE('" + endtime + "','YYYY-MM-DD')";
            }
            if(deptype!=null && !"".equals(deptype)){
            	sql+=" and u.deptype='"+deptype+"'";
            }
        sql += " order by b.logdate desc";
        logger.info("查询sql---------------"+sql);

//
//
//
//        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
//
//        sqlBuild.addConstant( "1 = 1 " );
//        sqlBuild.addDateFormatStrEnd( "LOGDATE",
//            ( ( BusinesslogBean )form ).getFromdate(),
//            ">=" );
//        sqlBuild.addDateFormatStrEnd( "LOGDATE",
//            ( ( BusinesslogBean )form ).getEnddate(),
//            "<=" );
//
//        sqlBuild.addConstant( " order by LOGDATE desc " );

        List list = super.getDbService().queryBeans( sql );
        request.getSession().setAttribute( "queryresult", list );

        return mapping.findForward( "querybusinesslogsult" );

    }

    public ActionForward showQueryUserLog( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        QueryUtil query = new QueryUtil();
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        List reginfo = null;
        List deptinfo = null;
        List coninfo = null;
        List uinfo = null;

////////////////////////查询所有区域----省级用户
        String region = "select  r.REGIONNAME, r.REGIONID "
                      + " from  region r   where r.STATE is null ";
////////////////////////部门
        String dept = "select  d.DEPTID, d.DEPTNAME, d.REGIONID "
                      + " from  deptinfo d "
                      + " where d.STATE is null ";
////////////////////////代维
        String con = "select  c.CONTRACTORID, c.CONTRACTORNAME, c.REGIONID "
                      + " from  contractorinfo c "
                      + " where c.STATE is null ";
////////////////////////用户
        String user = "select  u.USERID, u.USERNAME, u.DEPTID, u.REGIONID, u.DEPTYPE   "
                      + " from  userinfo u "
                      + " where u.STATE is null ";
        if (userinfo.getDeptype().equals("1")
                && userinfo.getRegionID().substring(2, 6).equals("0000")) {
            region += "  and exists(select regionid from region re ";
            region += " where r.regionid=re.regionid ";
            region += " start with re.regionid='" + userinfo.getRegionid() + "' ";
            region += " connect by prior re.regionid=re.parentregionid) ";

            dept += "  and exists(select regionid from region re ";
            dept += " where d.regionid=re.regionid ";
            dept += " start with re.regionid='" + userinfo.getRegionid() + "' ";
            dept += " connect by prior re.regionid=re.parentregionid) ";

            con += "  and exists(select regionid from region re ";
            con += " where c.regionid=re.regionid ";
            con += " start with re.regionid='" + userinfo.getRegionid() + "' ";
            con += " connect by prior re.regionid=re.parentregionid) ";

            user += " and exists(select regionid from region re ";
            user += " where u.regionid=re.regionid ";
            user += " start with re.regionid='" + userinfo.getRegionid() + "' ";
            user += " connect by prior re.regionid=re.parentregionid) ";
        }
       // 市移动
        if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

            dept += "  and d.regionid IN ('" + userinfo.getRegionID() + "') ";
            con += "  and c.regionid IN ('" + userinfo.getRegionID() + "') ";
            user += "  and u.regionid IN ('" + userinfo.getRegionID() + "') ";

            reginfo = query.queryBeans( region );
            deptinfo = query.queryBeans( dept );
            coninfo = query.queryBeans( con );
            uinfo = query.queryBeans( user );

        }
        //市代维
        if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

            con += "  and c.regionid IN ('" + userinfo.getRegionID() + "') and c.CONTRACTORID = ('" + userinfo.getDeptID() + "')";
            user += "  and u.regionid IN ('" + userinfo.getRegionID() + "') and u.DEPTID = ('" + userinfo.getDeptID() + "')";

            coninfo = query.queryBeans( con );
            uinfo = query.queryBeans( user );

        }
        //省移动
        if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

            reginfo = query.queryBeans( region );
            deptinfo = query.queryBeans( dept );
            coninfo = query.queryBeans( con );
            uinfo = query.queryBeans( user );
        }
        //省代维
        if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

            user += "  and u.DEPTYPE = ('" + userinfo.getDeptype() + "')";

            reginfo = query.queryBeans( region );
            coninfo = query.queryBeans( con );
            uinfo = query.queryBeans( user );

        }
        request.setAttribute( "reginfo", reginfo );
        request.setAttribute( "deptinfo", deptinfo );
        request.setAttribute( "coninfo", coninfo );
        request.setAttribute( "uinfo", uinfo );
        return mapping.findForward( "showqueryuserlog" );
    }



}
