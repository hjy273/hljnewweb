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

public class ToolMainAction extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( ToolMainAction.class.
                                   getName() );
    private ToolMainDao mainDao = new ToolMainDao();
    private ToolExportDao exportDao = new ToolExportDao();


    /**
     * 显示报修单
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward addMainShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90302" ) ){
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
        ToolUseDao useDao = new ToolUseDao();
        List lBaseInfo = useDao.getToolInfo( userinfo.getDeptID() );
        request.setAttribute( "baseinfo", lBaseInfo );

        request.getSession().setAttribute( "type", "main2" );
        return mapping.findForward( "success" );
    }

    /**
     * 执行填写报修单
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward addMain( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90302" ) ){
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
            bean.setContractorid( userinfo.getDeptID() );
            if( !mainDao.addMianInfo( bean, id, enumber ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "备件管理", "添加报修单" );
            return forwardInfoPage( mapping, request, "90302" );
        }
        catch( Exception e ){
            logger.error( "在执行填写报修单中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * 显示所有报修单
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward showAllMain( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90301" ) ){
            return mapping.findForward( "powererror" );
        }

        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            List lMainInfo = mainDao.getAllMain( request );
//			if(lMainInfo == null ){
//                return forwardErrorPage( mapping, request, "90301e" );
//            }
  //          //System.out.println( "lMaintainSize:" + lMainInfo.size() );
            request.getSession().setAttribute( "maininfo", lMainInfo );
            request.getSession().setAttribute( "type", "mian1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示所有显示所有报修单出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * 显示一个报修单的详细信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward showOneMain( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90301" ) ){
            return mapping.findForward( "powererror" );
        }
        ToolsInfoBean maininfo = new ToolsInfoBean(); //传回页面的报修单基本信息
        List mainToolInfo = null;
        String mainid = request.getParameter( "mainid" );

        try{
            //获得当前用户的单位名称
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允的
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            //获得报修单基本信息
            maininfo = mainDao.getOneUse( mainid ); ;
            request.setAttribute( "maininfo", maininfo );

            //获得领用单所领用的备件信息
            mainToolInfo = mainDao.getToolsOfOneUse( mainid );
            request.setAttribute( "maintoolinfo", mainToolInfo );
            request.getSession().setAttribute( "type", "main10" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "在显示详细中出现错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * 修改显示
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward upShow( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90304" ) ){
            return mapping.findForward( "powererror" );
        }
        ToolsInfoBean maininfo = new ToolsInfoBean(); //传回页面的报修单基本信息
        List mainToolInfo = null;
        String mainid = request.getParameter( "mainid" );

        try{
            //获得当前用户的单位名称
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

            //获得报修单基本信息
            maininfo = mainDao.getOneUse( mainid ); ;
            request.setAttribute( "maininfo", maininfo );

            //获得领用单所领用的备件信息
            mainToolInfo = mainDao.getToolsOfOneUse( mainid );
            request.setAttribute( "maintoolinfo", mainToolInfo );
            request.getSession().setAttribute( "type", "main4" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "修改显示错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * 执行修改报修单
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward UpMain( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90304" ) ){
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
            String[] oldenumber = request.getParameterValues( "bnumber" );
            bean.setContractorid( userinfo.getDeptID() );
            if( !mainDao.upMianInfo( bean, id, enumber, oldenumber ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "备件管理", "修改报修" );
            return forwardInfoPage( mapping, request, "90304" );
        }
        catch( Exception e ){
            logger.error( "在执行填写报修单中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //执行删除
    public ActionForward deleMain( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        //权限检查
        if( !CheckPower.checkPower( request.getSession(), "90305" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String mainid = request.getParameter( "mainid" );

            if( mainDao.deleMain( userinfo.getDeptID(), mainid ) ){
                log( request, "备件管理", "删除备件报修单" );
                return forwardInfoPage( mapping, request, "90305" );
            }
            else{
                return forwardErrorPage( mapping, request, "error" );
            }

        }
        catch( Exception e ){
            logger.error( "在删除申请单中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //    //综合查询显示
    public ActionForward queryShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90303" ) ){
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
            List lUser = mainDao.getUserArr( contractorid );
            request.setAttribute( "mainuser", lUser );
            //获得报修原因列表
            List lRemark = mainDao.getRemarkArr( contractorid );
            request.setAttribute( "mianremark", lRemark );
            request.getSession().setAttribute( "type", "main3" );
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
        if( !CheckPower.checkPower( request.getSession(), "90303" ) ){
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

            List lMainInfo = mainDao.getAllMainForSearch( bean );
            request.getSession().setAttribute( "maininfo", lMainInfo );
            request.getSession().setAttribute( "type", "mian1" );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "综合查询执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //已报修备件页面显示
    public ActionForward showMainToolAll( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90306" ) ){
            return mapping.findForward( "powererror" );
        }

        try{
            //获得当前用户的单位名称
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允的
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            //获得所有报修备件备件信息
            List lMainTool = mainDao.getMainToolAll( userinfo.getDeptID() );
            request.getSession().setAttribute( "mainTool", lMainTool );
            request.getSession().setAttribute( "type", "main6" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "已报修备件页面显示错误:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //显示包含一备件的所有报修单
    public ActionForward showTool_Main( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90306" ) ){
            return mapping.findForward( "powererror" );
        }

        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            List lMainInfo = mainDao.getTool_Main( request );
            if( lMainInfo == null ){
                return forwardErrorPage( mapping, request, "90301e" );
            }
            request.getSession().setAttribute( "maininfo", lMainInfo );
            request.getSession().setAttribute( "type", "mian1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示所有显示所有报修单出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportMainResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        try{
            ToolsInfoBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( ToolsInfoBean )request.getSession().getAttribute( "bean" );
                logger.info( "获得查询条件bean。。。" );

                logger.info( "id：" + bean.getUserid() );
                logger.info( "报修原因：" + bean.getMainremark() );
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
                    logger.info( "操作人：" + bean.getUsername() );
                    request.getSession().removeAttribute( "bean" );
                }
            }
            List list = ( List )request.getSession().getAttribute( "maininfo" );
            logger.info( "得到list" );
            logger.info( "输出excel成功" );

            exportDao.exportMainResult( list, bean, response );

            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    public ActionForward exportMainList( ActionMapping mapping, ActionForm form,
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
            String mainremark = request.getParameter( "mainremark" );
            String begintime = request.getParameter( "begintime" );
            String endtime = request.getParameter( "endtime" );

            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setContractorid( contractorid );
            bean.setUserid(userid);
            bean.setMainremark(mainremark);
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
                logger.info( "操作人：" + bean.getUsername() );
                request.getSession().removeAttribute( "bean" );
                }
            List list = mainDao.getMainList( bean );

            exportDao.exportMainList(list, bean, response);

            return null;

        }
        catch( Exception e ){
            logger.error( "导出信息报表异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
