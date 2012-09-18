package com.cabletech.sysmanage.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cabletech.commons.web.*;
import com.cabletech.sysmanage.util.*;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import org.apache.log4j.*;
import com.cabletech.sysmanage.dao.ExportDao;

public class OnLineUserAction extends SystemBaseDispatchAction{

    private static Logger logger = Logger.getLogger( OnLineUserAction.class.
                                   getName() );
    //ȡ�������û�
    public ActionForward getOnlineUsers( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        OnLineUsers online = OnLineUsers.getInstance();
        Vector userVct = online.getOnLineUser();

        String sql_A = " select distinct a.USERNAME username, a.LASTLOGINIP ip, a.USERID userid, b.DEPTNAME dept, c.REGIONNAME region , to_char(a.LASTLOGINTIME,'yy/mm/dd hh24:mi') logintime from USERINFO a, DEPTINFO b, REGION c ";
        sql_A = sql_A +
                " where a.DEPTYPE = '1' and a.DEPTID = b.DEPTID and a.regionid = c.regionid ";

        String extraConditionStr = makeExtraContionStr( userVct );

        sql_A = sql_A + extraConditionStr;

        String sql_B = " select distinct a.USERNAME username, a.LASTLOGINIP ip, a.USERID userid, b.CONTRACTORNAME dept, c.REGIONNAME region , to_char(a.LASTLOGINTIME,'yy/mm/dd hh24:mi') logintime from USERINFO a, CONTRACTORINFO b, REGION c ";
        sql_B = sql_B +
                " where a.DEPTYPE = '2' and a.DEPTID = b.CONTRACTORID and a.regionid = c.regionid ";

        sql_B = sql_B + extraConditionStr;

        String sql = sql_A + " union " + sql_B;

        logger.info( "��ѯ�����û�SQL ��" + sql );

        List list = super.getDbService().queryBeans( sql );

        request.getSession().setAttribute( "queryresult", list );

        return mapping.findForward( "onLineUsersPage" );
    }


    /**
     * makeExtraContionStr
     *
     * @param userVct Vector
     * @return String
     */
    private String makeExtraContionStr( Vector userVct ){
        String extraStr = "";

        for( int i = 0; i < userVct.size(); i++ ){
            if( i == 0 ){
                extraStr = extraStr + "and ( a.USERID = '" +
                           sliceNameAndIp( ( String )userVct.get( i ) ) +
                           "' ";
            }
            else{
                extraStr = extraStr + " or a.USERID = '" +
                           sliceNameAndIp( ( String )userVct.get( i ) ) + "' ";
            }
        }

        if( userVct.size() > 0 ){
            extraStr = extraStr + " )";
        }

        return extraStr;
    }


    private String sliceNameAndIp( String nameip ){
        String name = nameip;
        /*
                 int i = nameip.indexOf(":");
                 name = nameip.substring(0, i);
         */

        return name;
    }


    public ActionForward showQueryUser( ActionMapping mapping,
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

////////////////////////��ѯ��������----ʡ���û�
        String region = "select  r.REGIONNAME, r.REGIONID "
                      + " from  region r   where r.STATE is null ";
////////////////////////����
        String dept = "select  d.DEPTID, d.DEPTNAME, d.REGIONID "
                      + " from  deptinfo d "
                      + " where d.STATE is null ";
////////////////////////��ά
        String con = "select  c.CONTRACTORID, c.CONTRACTORNAME, c.REGIONID "
                      + " from  contractorinfo c "
                      + " where c.STATE is null ";
////////////////////////�û�
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
        
       // ���ƶ�
        if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

            dept += "  and d.regionid IN ('" + userinfo.getRegionID() + "') ";
            con += "  and c.regionid IN ('" + userinfo.getRegionID() + "') ";
            user += "  and u.regionid IN ('" + userinfo.getRegionID() + "') ";
        }
        //�д�ά
        if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

            con += "  and c.regionid IN ('" + userinfo.getRegionID() + "') and c.CONTRACTORID = ('" + userinfo.getDeptID() + "')";
            user += "  and u.regionid IN ('" + userinfo.getRegionID() + "') and u.DEPTID = ('" + userinfo.getDeptID() + "')";

            coninfo = query.queryBeans( con );
            uinfo = query.queryBeans( user );

        }
        //ʡ�ƶ�
        if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            reginfo = query.queryBeans( region );
            deptinfo = query.queryBeans( dept );
            coninfo = query.queryBeans( con );
            uinfo = query.queryBeans( user );
        }
        //ʡ��ά
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
        return mapping.findForward( "showqueryuser" );
    }


    public ActionForward queryUserOnlineTime( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

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
        logger.info( "�û�id:----" + userid );
        logger.info( "����id:----" + regionid );
        logger.info( "����id:----" + deptid );
        logger.info( "��άid:----" + contractorid );
        logger.info( "begintime:----" + begintime );
        logger.info( "endtime:----" + endtime );

        String sql = "select u.USERNAME, ut.LOGINIP, TO_CHAR(ut.LOGINTIME,'YYYY-MM-DD hh24:mi:ss')logintime, TO_CHAR(ut.LOGOUTTIME,'YYYY-MM-DD hh24:mi:ss') logouttime, TO_CHAR(Round(ut.onlinetime)) onlinetime "
                     + " from useronlinetime ut, userinfo u "
                     + " where u.USERID = ut.USERID ";
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        //���ƶ�
        if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql += " and u.regionid = '" + userinfo.getRegionid() + "' ";
        }
        //�д�ά
        if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            sql += " and u.DEPTYPE = '2' and u.regionid = '" + userinfo.getRegionID() + "' ";
        }
        //ʡ�ƶ�
        if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

        }
        //ʡ��ά
        if( userinfo.getType().equals("21")){
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
        if( !userid.equals( "" ) ){
            sql += " and ut.userid = '" + userid + "' ";
        }
        if( !begintime.equals( "" ) ){
            sql += " and ut.logintime >= TO_DATE('" + begintime + "','YYYY-MM-DD')";
        }
        if( !endtime.equals( "" ) ){
            sql += " and ut.logintime < TO_DATE('" + endtime + "','YYYY-MM-DD')";
        }
        if(deptype!=null && !"".equals(deptype)){
        	sql+=" and u.deptype='"+deptype+"'";
        }
        sql += " order by ut.logintime desc";

        List useronlineinfo = null;
        useronlineinfo = super.getDbService().queryBeans( sql );
        request.getSession().setAttribute( "useronlineinfo", useronlineinfo );

        logger.info( "��ѯsql---------------" + sql );

        return mapping.findForward( "queryuserresult" );
    }


    public ActionForward exportUserOnlineTime( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        try{

            List list = ( List )request.getSession().getAttribute( "useronlineinfo" );
            logger.info( "�ɹ��������" );
            ExportDao dao = new ExportDao();
            dao.exportUserOnlineTimeResult( list, response );
            logger.info( "���excel�ɹ�" );
            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ���������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

}
