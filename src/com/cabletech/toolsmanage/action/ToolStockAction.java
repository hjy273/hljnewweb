package com.cabletech.toolsmanage.action;

import java.text.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.partmanage.dao.*;
import com.cabletech.power.*;
import com.cabletech.toolsmanage.beans.*;
import com.cabletech.toolsmanage.dao.*;
import com.cabletech.commons.hb.QueryUtil;
import java.sql.ResultSet;

public class ToolStockAction extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( ToolStockAction.class.
                                   getName() );
    private ToolStockDao stockDao = new ToolStockDao();
    private ToolExportDao exportDao = new ToolExportDao();
    //入库显示
    public ActionForward stockShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90502" ) ){
            return mapping.findForward( "powererror" );
        }
        PartRequisitionDao dao = new PartRequisitionDao();
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        String deptname = dao.getUserDeptName( userinfo );
        request.setAttribute( "deptname", deptname );
        request.setAttribute( "deptid", userinfo.getDeptID() );
        request.setAttribute( "username", userinfo.getUserName() );
        request.setAttribute( "userid", userinfo.getUserID() );

        //获得服务器的当前时间
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
        String data = df.format( nowDate );
        request.setAttribute( "date", data );

        //获得备件信息,以供用户选择
        List lBaseInfo = stockDao.getAllInfo( userinfo.getRegionID() );
        //获得保管人信息,供用户选择
        List lPatrolid = stockDao.getAllpatrolid( userinfo.getDeptID() );
        request.setAttribute( "patrol", lPatrolid );
        request.setAttribute( "baseinfo", lBaseInfo );
        request.getSession().setAttribute( "type", "2" );
        return mapping.findForward( "success" );
    }


    //执行添加入库单
    public ActionForward addStock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90502" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        try{
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            String[] id = request.getParameterValues( "id" );
            String[] enumber = request.getParameterValues( "enumber" );
            //判断是否没有入库备件的数量，如果一个备件的数量都没有，则出错
            for( int i = 0; i < enumber.length; i++ ){
                if( enumber[i].equals( "0" ) || id[i].equals( "" ) ){
                    return forwardErrorPage( mapping, request, "90502e" );
                }
            }

            if( !stockDao.doAddStock( bean, id, enumber, userinfo.getRegionID() ) ){
                return forwardErrorPage( mapping, request, "error" );
            }
            log( request, "备件管理", "添加备件入库单" );
            return forwardInfoPage( mapping, request, "90502" );
        }
        catch( Exception e ){
            logger.error( "在添加入库单中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示所有入库单
    public ActionForward showAllStock( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90501" ) ){
            return mapping.findForward( "powererror" );
        }

        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        try{
            List lReqInfo = stockDao.getAllStock( request );
            request.getSession().setAttribute( "stockinfo", lReqInfo );
            request.getSession().setAttribute( "type", "1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示所有入库单出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示一个入库单的详细信息
    public ActionForward showOneInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90501" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        String stockid = request.getParameter( "stockid" );
        ToolsInfoBean stockinfo = new ToolsInfoBean(); //传回页面的入库单基本信息
        List toolsinfo = null; //入库详细信息

        try{
            //获得入库单基本信息
            stockinfo = stockDao.getOneStock( stockid );
            request.setAttribute( "stockinfo", stockinfo );

            //获得入库单入库备件信息
            toolsinfo = stockDao.getStockTools( stockid );
            request.setAttribute( "toolsinfo", toolsinfo );
            request.getSession().setAttribute( "type", "10" );

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
        if( !CheckPower.checkPower( request.getSession(), "90503" ) ){
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
            //获得入库人名单
            List lUser = stockDao.getAllUsers( contractorid );
            request.setAttribute( "users", lUser );
            //获得存放地点列表
            List lAdress = stockDao.getAllAdress( contractorid );
            request.setAttribute( "adress", lAdress );
            //获得保管人列表
            List lPatrolid = stockDao.getAllPatrid( contractorid );
            request.setAttribute( "patrolid", lPatrolid );
            request.getSession().setAttribute( "type", "3" );
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
        if( !CheckPower.checkPower( request.getSession(), "90503" ) ){
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
            String contractorid = userinfo.getDeptID();
            bean.setContractorid( contractorid );
            List lStockInfo = this.stockDao.doSearchStock( bean );
            request.getSession().setAttribute( "stockinfo", lStockInfo );
            request.getSession().setAttribute( "type", "1" );
            request.getSession().setAttribute("bean",bean);
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "综合查询执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


//////////////////////报废操作////////////////////////////////
    //报废显示
    public ActionForward revokeShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90602" ) ){
            return mapping.findForward( "powererror" );
        }
        PartRequisitionDao dao = new PartRequisitionDao();
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        String deptname = dao.getUserDeptName( userinfo );
        request.setAttribute( "deptname", deptname );
        request.setAttribute( "deptid", userinfo.getDeptID() );
        request.setAttribute( "username", userinfo.getUserName() );
        request.setAttribute( "userid", userinfo.getUserID() );

        //获得服务器的当前时间
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
        String data = df.format( nowDate );
        request.setAttribute( "date", data );

        //获得备件信息,以供用户选择
        List lBaseInfo = stockDao.getAllInfoByCon( userinfo.getDeptID() );
        request.setAttribute( "baseinfo", lBaseInfo );
        request.getSession().setAttribute( "type", "revoke2" );
        return mapping.findForward( "success" );
    }


    //执行添加报废单
    public ActionForward addRevoke( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90602" ) ){
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
            //判断是否没有报废备件的数量，如果一个备件的数量都没有，则出错
            for( int i = 0; i < enumber.length; i++ ){
                if( enumber[i].equals( "0" ) || id[i].equals( "" ) ){
                    return forwardErrorPage( mapping, request, "90502e" );
                }
            }

            if( !stockDao.doAddRevoke( bean, id, enumber ) ){
                return forwardErrorPage( mapping, request, "error" );
            }
            log( request, "备件管理", "添加备件报废单" );
            return forwardInfoPage( mapping, request, "90602" );
        }
        catch( Exception e ){
            logger.error( "在添加报废单中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示所有报废单
    public ActionForward showAllRevoke( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90601" ) ){
            return mapping.findForward( "powererror" );
        }

        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        try{
            List lReqInfo = stockDao.getAllRevoke( request );
            request.getSession().setAttribute( "stockinfo", lReqInfo );
            request.getSession().setAttribute( "type", "revoke1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示所有报废单出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示一个报废单的详细信息
    public ActionForward showOneRevoke( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90601" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        String stockid = request.getParameter( "stockid" );
        ToolsInfoBean revokeinfo = new ToolsInfoBean(); //传回页面的 报废单基本信息
        List toolsinfo = null; //报废详细信息

        try{
            //获得入库单基本信息
            revokeinfo = stockDao.getOneRevoke( stockid );
            request.setAttribute( "stockinfo", revokeinfo );

            //获得报废单报废备件信息
            toolsinfo = stockDao.getStockTools( stockid );
            request.setAttribute( "toolsinfo", toolsinfo );
            request.getSession().setAttribute( "type", "revoke10" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "在显示报废详细中出现错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //综合查询显示
    public ActionForward queryShowForRevoke( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90603" ) ){
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
            //获得报废人名单
            List lUser = stockDao.getAllUsersForRevoke( contractorid );
            request.setAttribute( "users", lUser );
            //获得报废原因列表
            List lRemark = stockDao.getAllRemarkForRevoke( contractorid );
            request.setAttribute( "remark", lRemark );
            request.getSession().setAttribute( "type", "revoke3" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "综合查询显示异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //综合查询执行
    public ActionForward queryExecForRevoke( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90603" ) ){
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
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            bean.setContractorid( contractorid );
            List lStockInfo = this.stockDao.doSearchStockForRevoke( bean );
            request.getSession().setAttribute( "stockinfo", lStockInfo );
            request.getSession().setAttribute( "type", "revoke1" );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "综合查询执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportStockResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        try{

            ToolsInfoBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( ToolsInfoBean )request.getSession().getAttribute( "bean" );
                logger.info( "获得查询条件bean。。。" );

                logger.info( "id：" + bean.getUserid() );
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

            List list = ( List )request.getSession().getAttribute( "stockinfo" );
            logger.info( "得到list" );
            logger.info( "输出excel成功" );

            exportDao.exportStockResult( list, bean, response );

            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportRevokeResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        try{
            ToolsInfoBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( ToolsInfoBean )request.getSession().getAttribute( "bean" );
                logger.info( "获得查询条件bean。。。" );

                logger.info( "id：" + bean.getUserid() );
                logger.info( "报废原因：" + bean.getRemark() );
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
            List list = ( List )request.getSession().getAttribute( "stockinfo" );
            logger.info( "得到list" );
            logger.info( "输出excel成功" );

            exportDao.exportRevokeResult( list, bean, response );

            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    //导出利旧材料入库单详细信息一览表
    public ActionForward exportStockList( ActionMapping mapping, ActionForm form,
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
            String begintime = request.getParameter( "begintime" );
            String endtime = request.getParameter( "endtime" );

            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setContractorid( contractorid );
            bean.setUserid(userid);
            bean.setBegintime(begintime);
            bean.setEndtime(endtime);
            if( userid != null ){
                String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + userid + "'";
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setUsername( rs.getString( 1 ) );
                }
                logger.info( "入库人：" + userid );
            }
            List list = stockDao.getStockList( bean );

            exportDao.exportStockList(list, bean, response);

            return null;

        }
        catch( Exception e ){
            logger.error( "导出信息报表异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    public ActionForward exportRevokeList( ActionMapping mapping, ActionForm form,
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
            String remark = request.getParameter( "remark" );
            String begintime = request.getParameter( "begintime" );
            String endtime = request.getParameter( "endtime" );

            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setContractorid( contractorid );
            bean.setRemark( remark );
            bean.setUserid( userid );
            bean.setBegintime( begintime );
            bean.setEndtime( endtime );
            if( userid != null ){
                String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + userid + "'";
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setUsername( rs.getString( 1 ) );
                }
                logger.info( "操作人：" + userid );
            }
            List list = stockDao.getRevokeList( bean );

            exportDao.exportRevokeList(list, bean, response);

            return null;

        }
        catch( Exception e ){
            logger.error( "导出信息报表异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
