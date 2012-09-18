package com.cabletech.partmanage.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.partmanage.beans.*;
import com.cabletech.partmanage.dao.*;
import com.cabletech.power.*;
import com.cabletech.commons.web.ClientException;


/**
 * 此类是用来处理材料基本信息的ＣＲＵＤ操作以及表单载入、综合查询、导出报表的Action
 *
 * @version 1.0
 */

public class PartBaseInfoAction extends BaseDispatchAction{

    private static Logger logger = Logger.getLogger( PartBaseInfoAction.class.
                                   getName() );

    /**
     * 显示材料的所有信息
     */
    public ActionForward showPartBaseInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80701" )
            ||CheckPower.checkPower( request.getSession(), "80709" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        try{
            PartBaseInfoDao dao = new PartBaseInfoDao();
            if(request.getParameter("querytype")!=null
               &&request.getParameter("querytype").equals("1")){
                List lInfo = dao.getAllInfo( request );
                request.getSession().setAttribute( "partInfo", lInfo );
                request.getSession().setAttribute( "type", "showpart1" );
            }else{
                List lInfo = dao.getAllInfo( userinfo.getRegionID() );
                request.getSession().setAttribute( "partInfo", lInfo );
                request.getSession().setAttribute( "type", "1" );
            }
            request.getSession().setAttribute("querytype","1");
            super.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "显示材料基本信息中显示所有信息出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * 显示单个材料的所有信息
     */
    public ActionForward showOnePartBaseInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80701" )
            ||CheckPower.checkPower( request.getSession(), "80709" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        Part_baseInfoBean bean = ( Part_baseInfoBean )form;
        PartBaseInfoDao dao = new PartBaseInfoDao();
        String id = bean.getId();
        bean = dao.getOneInfo( id );
        if( null != bean ){
            request.setAttribute( "partInfo", bean );
            request.getSession().setAttribute( "type", "10" );
            return mapping.findForward( "success" );
        }
        else{
            logger.error( "显示材料基本信息中显示所有信息出:" );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * 添加显示
     */
    public ActionForward addPartBaseInfoshow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80702" ) ){
            return mapping.findForward( "powererror" );
        }
        request.getSession().setAttribute( "type", "2" );
        return mapping.findForward( "success" );
    }


    /**
     * 执行添加
     */
    public ActionForward addPartBaseInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_baseInfoBean bean = ( Part_baseInfoBean )form;
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
            bean.setRegionid( userinfo.getRegionID() );
            PartBaseInfoDao dao = new PartBaseInfoDao( bean );
            if( true == dao.addPartBaseInfo() ){
                log( request, "材料管理", "添加材料名称" );
                return forwardInfoPage( mapping, request, "80702" );
            }
            else{
                return forwardErrorPage( mapping, request, "error" );
            }
        }
        catch( Exception e ){
            logger.error( "添加维护材料信息异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * 修改显示
     */
    public ActionForward upPartBaseInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80704" ) ){
            return mapping.findForward( "powererror" );
        }

        Part_baseInfoBean bean = ( Part_baseInfoBean )form;
        PartBaseInfoDao dao = new PartBaseInfoDao();
        String id = bean.getId();
        bean = dao.getOneInfo( id );
        if( null != bean ){
            request.setAttribute( "partInfo", bean );
            request.getSession().setAttribute( "type", "4" );
            return mapping.findForward( "success" );
        }
        else{
            logger.error( "执行修改显示材料基本信息中出现错误:" );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * 执行修改
     */
    public ActionForward updatePartBaseInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80704" ) ){
            return mapping.findForward( "powererror" );
        }
        try{
            Part_baseInfoBean bean = ( Part_baseInfoBean )form;
            PartBaseInfoDao dao = new PartBaseInfoDao( bean );
            if( true == dao.updatePartBaseInfo() ){
                log( request, "材料管理", "修改材料信息" );
                return forwardInfoPage( mapping, request, "80704" );
            }
            else{
                return forwardErrorPage( mapping, request, "error" );
            }
        }
        catch( Exception e ){
            logger.error( "添加维护材料信息异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * 执行删除
     */
    public ActionForward deletePartBaseInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80705" ) ){
            return mapping.findForward( "powererror" );
        }
        try{
            Part_baseInfoBean bean = ( Part_baseInfoBean )form;
            String id = bean.getId();
            PartBaseInfoDao dao = new PartBaseInfoDao();
            if( dao.deletePartBaseInfo( id ) ){
                log( request, "材料管理", "删除材料信息" );
                return forwardInfoPage( mapping, request, "80705" );
            }
            else{
                return forwardErrorPage( mapping, request, "80705e" );
            }
        }
        catch( Exception e ){
            logger.error( "删除维护材料信息异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * 综合查询显示
     */
    public ActionForward queryShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80703" ) 
        		&& !CheckPower.checkPower( request.getSession(), "80701" ) ){
            return mapping.findForward( "powererror" );
        }
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        try{
            PartBaseInfoDao dao = new PartBaseInfoDao();
            List lName = dao.getAllName( userinfo.getRegionID() );
            List lType = dao.getAllType( userinfo.getRegionID() );
            List lFactory = dao.getAllFactory( userinfo.getRegionID() );
            request.setAttribute( "nameList", lName );
            request.setAttribute( "typeList", lType );
            request.setAttribute( "factoryList", lFactory );

            request.getSession().setAttribute( "type", "3" );
            super.setPageReset(request);
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "查询显示维护材料异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * 执行综合查询
     */
    public ActionForward doQuery( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){

        if( !CheckPower.checkPower( request.getSession(), "80703" ) 
        		&& !CheckPower.checkPower( request.getSession(), "80701" )){
            return mapping.findForward( "powererror" );
        }
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );

        try{
            PartBaseInfoDao dao = new PartBaseInfoDao();
            Part_baseInfoBean bean = ( Part_baseInfoBean )form; //
            bean.setRegionid( userinfo.getRegionID() );
            List lPartInfo;
            String strQueryType = request.getParameter( "querytype" );
            if( strQueryType.equals( "m" ) ){
                lPartInfo = dao.getSegInfo_Blur( bean );
            }
            else{
                lPartInfo = dao.getSegInfo_Rigor( bean );
            }

            request.getSession().setAttribute( "partInfo", lPartInfo );
            request.getSession().setAttribute( "type", "1" );
            super.setPageReset(request);
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "查询显示维护材料异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * 导出报表
     */
    public ActionForward exportBaseInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{

            PartExportDao dao = new PartExportDao();
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "partInfo" );
            logger.info( "得到list" );
            dao.exportBaseInfo( list, response );
            logger.info( "输出excel成功" );

            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    public ActionForward queryAllPartInfo(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,HttpServletResponse response)
        throws ClientException, Exception{

        if( !CheckPower.checkPower( request.getSession(), "80709" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        //if( userinfo.getType().equals( "12" ) ){ //如果是市移动公司是不允许的
        if( !userinfo.getDeptype().equals( "1" )
            &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //如果是市移动公司是不允许的
            return forwardErrorPage( mapping, request, "partauditerror" );
        }

        List regionList=null;
        List deptList=null;
        List conList=null;
        String region="select r.regionid,r.regionname "
                     +"from region r "
                     +"where (r.state is null or r.state<>'1') and substr(regionid,3,4)<>'1111'";
        String dept="select d.deptid,d.deptname,d.regionid "
                    +"from deptinfo d "
                    +"where (d.state is null or d.state<>'1')";
        String con="select c.contractorid, c.contractorname ,c.regionid "
                   +"from contractorinfo c "
                   +"where (c.state is null or c.state<>'1')";
        if(userinfo.getType().equals("21")){
            con+=" and contractorid in ("
                +"   select contractorid from contractorinfo "
                +"   where parentcontractorid='"+userinfo.getDeptID()+"' or contractorid='"+userinfo.getDeptID()+"'"
                +" )";
        }
        if(userinfo.getType().equals("12")){
            con+=" and regionid='"+userinfo.getRegionID()+"'";
            dept+=" and regionid='"+userinfo.getRegionID()+"'";
        }
        if(userinfo.getType().equals("22")){
            con+=" and contractorid='"+userinfo.getDeptID()+"'";
        }
        dept+=" order by regionid";
        con+=" order by regionid";
        regionList = super.getDbService().queryBeans( region );
        deptList = super.getDbService().queryBeans( dept );
        conList = super.getDbService().queryBeans( con );
        request.setAttribute("regionlist",regionList);
        request.setAttribute("deptlist",deptList);
        request.setAttribute("conlist",conList);

        PartBaseInfoDao dao = new PartBaseInfoDao();
        List lName = dao.getAllName();
        List lType = dao.getAllType();
        request.setAttribute( "nameList", lName );
        request.setAttribute( "typeList", lType );

        return mapping.findForward("queryallpartinfo");
    }
}
