package com.cabletech.toolsmanage.action;

import java.text.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.power.*;
import com.cabletech.toolsmanage.beans.*;
import com.cabletech.toolsmanage.dao.*;
import java.sql.ResultSet;
import com.cabletech.commons.hb.QueryUtil;

public class ToolUseAction extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( ToolUseAction.class.
                                   getName() );
    private ToolUseDao useDao = new ToolUseDao();
    private ToolExportDao exportDao = new ToolExportDao();
    //显示领用单
    public ActionForward addUseShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90102" ) ){
            return mapping.findForward( "powererror" );
        }
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        String deptname = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" ); ;
        request.setAttribute( "deptname", deptname );
        request.setAttribute( "deptid", userinfo.getDeptID() );
        request.setAttribute( "username", userinfo.getUserName() );
        request.setAttribute( "userid", userinfo.getUserID() );

        //获得服务器的当前时间
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
        String data = df.format( nowDate );
        request.setAttribute( "date", data );

        //获得该单位库中已经存放的备件信息,以供用户选择
        List lBaseInfo = useDao.getToolInfo( userinfo.getDeptID() );
        request.setAttribute( "baseinfo", lBaseInfo );

        request.getSession().setAttribute( "type", "use2" );
        return mapping.findForward( "success" );
    }


    //执行填写领用单
    public ActionForward addUse( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90102" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            String[] id = request.getParameterValues( "id" );
            String[] enumber = request.getParameterValues( "enumber" );
            if( !useDao.addUseInfo( bean, id, enumber ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "备件管理", "添加领用单" );
            return forwardInfoPage( mapping, request, "90102" );
        }
        catch( Exception e ){
            logger.error( "在执行填写领用单中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示所有领用单
    public ActionForward showAllUse( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90101" ) ){
            return mapping.findForward( "powererror" );
        }

        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            List lReqInfo = useDao.getAllUse( request );

            request.getSession().setAttribute( "useinfo", lReqInfo );
            request.getSession().setAttribute( "type", "use1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示所有出库单信息出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示一个入库单的详细信息
    public ActionForward showOneUse( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90101" ) ){
            return mapping.findForward( "powererror" );
        }
        ToolsInfoBean useinfo = new ToolsInfoBean(); //传回页面的领用单基本信息
        List useToolInfo = null;
        String useid = request.getParameter( "useid" );

        try{
            //获得当前用户的单位名称
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允的
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            //获得领用单基本信息
            useinfo = useDao.getOneUse( useid ); ;
            request.setAttribute( "useinfo", useinfo );

            //获得领用单所领用的备件信息
            useToolInfo = useDao.getToolsOfOneUse( useid );
            request.setAttribute( "usepartinfo", useToolInfo );
            request.getSession().setAttribute( "type", "use10" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "在显示详细中出现错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //综合查询显示
    public ActionForward queryShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90103" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            //获得操作人名单
            List lUser = useDao.getUserArr( contractorid );
            request.setAttribute( "useuser", lUser );
            //获得领用人名单
            List lUsename = useDao.getUseNameArr( contractorid );
            request.setAttribute( "lusename", lUsename );
            //获得领用单位列表
            List lUseunit = useDao.getUseUnitArr( contractorid );
            request.setAttribute( "luseunit", lUseunit );
            //获得领用原因列表
            List lRemark = useDao.getUseRemarkArr( contractorid );
            request.setAttribute( "useremark", lRemark );
            request.getSession().setAttribute( "type", "use3" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "综合查询显示异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //综合查询执行
    public ActionForward queryExec( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90103" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setContractorid( contractorid );

            List lUseInfo = useDao.getAllUseForSearch( bean );
            request.getSession().setAttribute( "useinfo", lUseInfo );
            request.getSession().setAttribute( "type", "use1" );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "综合查询执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


//////////////////////////////////////////////////////////////////////////
    //返还时查询领用单显示
    public ActionForward backShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90106" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            //获得操作人名单
            List lUser = useDao.getUserArr( contractorid );
            request.setAttribute( "useuser", lUser );
            //获得领用人名单
            List lUsename = useDao.getUseNameArr( contractorid );
            request.setAttribute( "lusename", lUsename );
            //获得领用单位列表
            List lUseunit = useDao.getUseUnitArr( contractorid );
            request.setAttribute( "luseunit", lUseunit );
            //获得领用原因列表
            List lRemark = useDao.getUseRemarkArr( contractorid );
            request.setAttribute( "useremark", lRemark );
            request.getSession().setAttribute( "type", "back3" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "返还时查询领用单显示异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //返还时查询领用单执行
    public ActionForward backQueryExec( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90106" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setContractorid( contractorid );

            List lUseInfo = useDao.getAllUseForBack( bean );
            request.getSession().setAttribute( "useinfo", lUseInfo );
            request.getSession().setAttribute( "type", "back1" );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "返还时查询领用单执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //返还页面显示
    public ActionForward showOneUseForBack( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90106" ) ){
            return mapping.findForward( "powererror" );
        }
        ToolsInfoBean useinfo = new ToolsInfoBean(); //传回页面的领用单基本信息
        List useToolInfo = null;
        String useid = request.getParameter( "useid" );

        try{
            //获得当前用户的单位名称
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允的
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            //获得服务器的当前时间
            Date nowDate = new Date();
            DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
            String data = df.format( nowDate );
            request.setAttribute( "date", data );
            //获得领用单基本信息
            useinfo = useDao.getOneUse( useid ); ;
            request.setAttribute( "useinfo", useinfo );

            //获得领用单所领用的备件信息
            useToolInfo = useDao.getToolsOfOneUse( useid );
            request.setAttribute( "usepartinfo", useToolInfo );
            request.getSession().setAttribute( "type", "back10" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "在返还页面显示中出现错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //执行填写返还单
    public ActionForward addBack( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90106" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setUserid( userinfo.getUserID() );
            bean.setContractorid( userinfo.getDeptID() );
            String[] id = request.getParameterValues( "id" );
            String[] bnumber = request.getParameterValues( "bnumber" );
            if( !useDao.addBackInfo( bean, id, bnumber ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "备件管理", "返还备件" );
            return forwardInfoPage( mapping, request, "90106" );
        }
        catch( Exception e ){
            logger.error( "在执行填写返还单中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //待返还备件页面显示
    public ActionForward shouldBackShow( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90107" ) ){
            return mapping.findForward( "powererror" );
        }

        try{
            //获得当前用户的单位名称
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允的
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            //获得领用单所领用的备件信息
            List shouldBackTool = useDao.getToolForShouldBack( userinfo.getDeptID() );
            request.getSession().setAttribute( "shouldBackTool", shouldBackTool );
            request.getSession().setAttribute( "type", "back07" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "在返还页面显示中出现错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //查询应返还备件领用单执行
    public ActionForward showShouldBackUse( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90106" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            String id = request.getParameter( "id" );
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setContractorid( contractorid );
            bean.setId( id );

            List lUseInfo = useDao.getAllShouldBackUse( bean );
            request.getSession().setAttribute( "useinfo", lUseInfo );
            request.getSession().setAttribute( "type", "back1" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "查询应返还备件领用单执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportUseResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        try{
            ToolsInfoBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( ToolsInfoBean )request.getSession().getAttribute( "bean" );
                logger.info( "获得查询条件bean。。。" );

                logger.info( "id：" + bean.getUserid() );
                logger.info( "领用人姓名：" + bean.getUsename());
                logger.info( "领用单位：" + bean.getUseunit() );
                logger.info( "领用原因：" + bean.getUseremark() );
                logger.info( "归还情况：" + bean.getBack() );
                logger.info( "开始时间：" + bean.getBegintime() );
                logger.info( "结束时间：" + bean.getEndtime() );

                if( !bean.getUserid().equals( "" ) ){
                    String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getUserid() + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setUsername( rs.getString( 1 ) );
                    }
                    logger.info( "入库人：" + bean.getUsername() );
                    request.getSession().removeAttribute( "bean" );
                }
            }
            List list = ( List )request.getSession().getAttribute( "useinfo" );
            logger.info( "得到list" );
            logger.info( "输出excel成功" );

            exportDao.exportUseResult( list, bean, response );

            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportBackResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        try{
            ToolsInfoBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( ToolsInfoBean )request.getSession().getAttribute( "bean" );
                logger.info( "获得查询条件bean。。。" );

                logger.info( "id：" + bean.getUserid() );
                logger.info( "领用人姓名：" + bean.getUsename());
                logger.info( "领用单位：" + bean.getUseunit() );
                logger.info( "领用原因：" + bean.getUseremark() );
                logger.info( "开始时间：" + bean.getBegintime() );
                logger.info( "结束时间：" + bean.getEndtime() );

                if( !bean.getUserid().equals( "" ) ){
                    String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getUserid() + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setUsername( rs.getString( 1 ) );
                    }
                    logger.info( "入库人：" + bean.getUsername() );
                    request.getSession().removeAttribute( "bean" );
                }
            }
            List list = ( List )request.getSession().getAttribute( "useinfo" );
            logger.info( "得到list" );
            logger.info( "输出excel成功" );

            exportDao.exportBackResult( list, bean, response );

            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    public ActionForward exportUseList( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
//        if( !CheckPower.checkPower( request.getSession(), "80403" ) ){
//            return mapping.findForward( "powererror" );
//        }
//        //获得当前用户的单位名称
//        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
//                            "LOGIN_USER" );
//        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
//            return forwardErrorPage( mapping, request, "partstockerror" );
//        }

        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );

        try{

            String userid = request.getParameter( "userid" );
            String usename = request.getParameter( "usename" );
            String useunit = request.getParameter( "useunit" );
            String useremark = request.getParameter( "useremark" );
            String back = request.getParameter( "back" );
            String begintime = request.getParameter( "begintime" );
            String endtime = request.getParameter( "endtime" );

            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setContractorid( contractorid );
            bean.setUsername( userid );
            bean.setUsename( usename );
            bean.setUseunit( useunit );
            bean.setUseremark( useremark );
            bean.setBack( back );
            bean.setBegintime( begintime );
            bean.setEndtime( endtime );
            if( userid != null){
                String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + userid + "'";
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setUsername( rs.getString( 1 ) );
                }
                logger.info( "操作人：" + bean.getUsername() );
                }
            List list = useDao.getUseList( bean );

            exportDao.exportUseList(list, bean, response);

            return null;

        }
        catch( Exception e ){
            logger.error( "导出信息报表异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
