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
import com.cabletech.commons.hb.QueryUtil;
import java.sql.ResultSet;

public class ToolStorageAction extends BaseDispatchAction{
    private ToolStorageDao dao = new ToolStorageDao();
    private ToolExportDao exportDao = new ToolExportDao();
    private static Logger logger = Logger.getLogger( ToolStorageAction.class.
                                   getName() );

    public ToolStorageAction(){
    }


    //查看库存
    public ActionForward showAllStorage( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90701" ) ){
            return mapping.findForward( "powererror" );
        }
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        List lStorageInfo = null;
        try{
            if( userinfo.getDeptype().equals( "2" ) ){ //代维单位
                lStorageInfo = dao.getAllStorageForCon( request );
            }
            else{ //移动公司
                lStorageInfo = dao.getAllStorageForDept( userinfo.getRegionID() );
            }
            request.getSession().setAttribute( "storageinfo", lStorageInfo );
            request.getSession().setAttribute( "type", "st1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示所有库存信息出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //综合查询显示
    public ActionForward queryShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90703" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        request.setAttribute( "unittype", userinfo.getDeptype() );
        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            //获得b备件名称列表和id列表
            List partName = dao.getToolNameArr();
            request.setAttribute( "toolname", partName );
            //获得备件类型列表
            List partType = dao.getToolTypeArr();
            request.setAttribute( "tooltype", partType );
            //获得代维单位名称和id
            List contractorname = dao.getContractorNameArr( userinfo.getRegionID() );
            request.setAttribute( "contractorname", contractorname );

            request.getSession().setAttribute( "type", "st3" );
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
        if( !CheckPower.checkPower( request.getSession(), "90703" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        try{
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            List lStorageInfo = null;
            if( userinfo.getDeptype().equals( "1" ) ){ //是移动公司
                bean.setRegionid( userinfo.getRegionID() );
                lStorageInfo = dao.getStorageForDept( bean );
            }
            else{ //是代维单位
                String contractorid = ( userinfo.getDeptID() );
                bean.setContractorid( contractorid );
                lStorageInfo = dao.getStorageForContractor( bean );
            }

            request.getSession().setAttribute( "storageinfo", lStorageInfo );
            request.getSession().setAttribute( "type", "st1" );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "综合查询执行异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //初始化库存显示
    public ActionForward initStorageShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90702" ) ){
            return mapping.findForward( "powererror" );
        }

        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        String contractorname = ( String )request.getSession().getAttribute(
                                "LOGIN_USER_DEPT_NAME" );
        request.setAttribute( "deptname", contractorname );
        request.setAttribute( "deptid", userinfo.getDeptID() );
        request.setAttribute( "username", userinfo.getUserName() );
        request.setAttribute( "userid", userinfo.getUserID() );

        //获得服务器的当前时间
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
        String data = df.format( nowDate );
        request.setAttribute( "date", data );

        //获得备件信息,以供用户选择
        List lBaseInfo = dao.getAllInfo();
        request.setAttribute( "baseinfo", lBaseInfo );

        request.getSession().setAttribute( "type", "st2" );
        return mapping.findForward( "success" );
    }


    //执行初始化
    public ActionForward initStorage( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90702" ) ){
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
            String[] essenumber = request.getParameterValues( "essenumber" );
            String[] portmainnumber = request.getParameterValues( "portmainnumber" );
            String[] mainnumber = request.getParameterValues( "mainnumber" );
            bean.setRegionid( userinfo.getRegionID() );

            if( !dao.initStorage( bean, id, essenumber, portmainnumber, mainnumber ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "材料管理", "初始化库存" );
            return forwardInfoPage( mapping, request, "80602" );
        }
        catch( Exception e ){
            logger.error( "在执行出库单中出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportStorageResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        try{
            ToolsInfoBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( ToolsInfoBean )request.getSession().getAttribute( "bean" );
                //获得当前用户的单位名称
                UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
                if( userinfo.getDeptype().equals( "1" ) ){ //是移动公司
                    String sql =
                        "select c.CONTRACTORNAME from contractorinfo c where c.CONTRACTORID = '"
                        + bean.getContractorid() + "'";
                    logger.info( sql );
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setContractorname( rs.getString( 1 ) );
                    }
                    logger.info( "cid:" + bean.getContractorid() );
                    logger.info( "单位名称：" + bean.getContractorname() );
                }
                logger.info( "获得查询条件bean。。。" );

                logger.info( "id:" + bean.getId() );
                String sql =
                    "select b.name from tool_base b where b.id = '"
                    + bean.getId() + "'";
                logger.info( sql );
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setName( rs.getString( 1 ) );
                }

                logger.info( "备件名称:" + bean.getName() );
                logger.info( "备件类型：" + bean.getType() );
                logger.info( "库存量大于：" + bean.getEsselownumber() + " 小于 " + bean.getEssehighnumber() );
                logger.info( "报修量大于：" + bean.getPortlownumber() + " 小于 " + bean.getPorthighnumber() );
            }
            List list = ( List )request.getSession().getAttribute( "storageinfo" );
            logger.info( "得到list" );
            logger.info( "输出excel成功" );

            exportDao.exportStorageResult( list, bean, response );

            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
