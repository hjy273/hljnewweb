package com.cabletech.partmanage.action;

import java.text.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.partmanage.beans.*;
import com.cabletech.partmanage.dao.*;
import com.cabletech.power.*;
import com.cabletech.commons.hb.QueryUtil;
import java.sql.ResultSet;
import com.cabletech.commons.web.ClientException;

public class PartStorageAction extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( PartStorageAction.class.
                                   getName() );
    public PartStorageAction(){
    }


    //查看库存
    public ActionForward showAllStorage( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80601" )
        	|| CheckPower.checkPower( request.getSession(), "80603")
            ||CheckPower.checkPower( request.getSession(), "80609" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        List lStorageInfo = null;
        try{
            PartStorageDao dao = new PartStorageDao();
            if(request.getParameter("querytype")!=null
                &&request.getParameter("querytype").equals("1")){
                if( request.getParameter("deptype").equals( "2" ) ){ //代维单位
                    lStorageInfo = dao.getAllStorageForContractor( request );
                }
                else{ //移动公司
                    lStorageInfo = dao.getAllStorageForDepart( request );
                }
                request.getSession().setAttribute( "type", "showst1" );
            }else{
                if( userinfo.getDeptype().equals( "2" ) ){ //代维单位
                    lStorageInfo = dao.getAllStorageForCon( request );
                }
                else{ //移动公司
                    lStorageInfo = dao.getAllStorageForDept( userinfo.getRegionID() );
                }
                request.getSession().setAttribute( "type", "st1" );
            }
            request.getSession().setAttribute("querytype","1");
            request.getSession().setAttribute( "storageinfo", lStorageInfo );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            e.printStackTrace();
            logger.error( "显示所有出库单信息出错:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //综合查询显示
    public ActionForward queryShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	
        if( !CheckPower.checkPower( request.getSession(), "80603" ) 
        		&& !CheckPower.checkPower( request.getSession(), "80601")){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        request.setAttribute( "unittype", userinfo.getDeptype() );

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            PartStorageDao stDao = new PartStorageDao();
            //获得材料名称列表和id列表
            List partName = stDao.getPartNameArr( userinfo.getRegionID() );
            request.setAttribute( "partname", partName );
            //获得材料类型列表
            List partType = stDao.getPartTypeArr( userinfo.getRegionID() );
            request.setAttribute( "parttype", partType );
            //获得代维单位名称和id
            List contractorname = stDao.getContractorNameArr( request );
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
        if( !CheckPower.checkPower( request.getSession(), "80603" )
        		&& !CheckPower.checkPower( request.getSession(), "80601")){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        String newlownumber = request.getParameter( "newlownumber" );
        String newhignumber = request.getParameter( "newhignumber" );
        String oldlownumber = request.getParameter( "oldlownumber" );
        String oldhignumber = request.getParameter( "oldhignumber" );

        try{
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setRegionid( userinfo.getRegionID() );
            PartStorageDao dao = new PartStorageDao();
            List lStorageInfo = null;
            if( userinfo.getDeptype().equals( "1" ) ){ //是移动公司
                lStorageInfo = dao.getStorageForDept( bean, newlownumber, newhignumber, oldlownumber, oldhignumber );
            }
            else{ //是代维单位
                String contractorid = ( String )request.getSession().getAttribute(
                                      "LOGIN_USER_DEPT_ID" );
                bean.setContractorid( contractorid );
                lStorageInfo = dao.getStorageForContractor( bean, newlownumber, newhignumber, oldlownumber,
                               oldhignumber, contractorid );
            }

            request.getSession().setAttribute( "storageinfo", lStorageInfo );
            request.getSession().setAttribute( "type", "st1" );
            request.getSession().setAttribute( "newlownumber", newlownumber );
            request.getSession().setAttribute( "newhignumber", newhignumber );
            request.getSession().setAttribute( "oldlownumber", oldlownumber );
            request.getSession().setAttribute( "oldhignumber", oldhignumber );
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
        if( !CheckPower.checkPower( request.getSession(), "80602" ) ){
            return mapping.findForward( "powererror" );
        }

        PartRequisitionDao dao = new PartRequisitionDao();
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
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

        //获得材料信息,以供用户选择
        List lBaseInfo = dao.getAllInfo( userinfo.getRegionID() );
        request.setAttribute( "baseinfo", lBaseInfo );

        request.getSession().setAttribute( "type", "st2" );
        return mapping.findForward( "success" );
    }


    //执行初始化
    public ActionForward initStorage( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80602" ) ){
            return mapping.findForward( "powererror" );
        }
        //获得当前用户的单位名称
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //如果是移动公司是不允许的
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStorageDao dao = new PartStorageDao();
            Part_requisitionBean bean = ( Part_requisitionBean )form;

            String[] id = request.getParameterValues( "id" );
            String[] newesse = request.getParameterValues( "newesse" );
            String[] oldnumber = request.getParameterValues( "oldnumber" );

            if( !dao.initStorage( bean, id, oldnumber, newesse ) ){
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
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            String newlownumber = "";
            String newhignumber = "";
            String oldlownumber = "";
            String oldhignumber = "";

            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "获得查询条件bean。。。" );

                 newlownumber = ( String )request.getSession().getAttribute( "newlownumber" );
                 newhignumber = ( String )request.getSession().getAttribute( "newhignumber" );
                 oldlownumber = ( String )request.getSession().getAttribute( "oldlownumber" );
                 oldhignumber = ( String )request.getSession().getAttribute( "oldhignumber" );

                logger.info( "newlownumber" + newlownumber );
                logger.info( "newhignumber" + newhignumber );
                logger.info( "oldlownumber" + oldlownumber );
                logger.info( "oldhignumber" + oldhignumber );
                //获得当前用户的单位名称
                UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
                if( userinfo.getDeptype().equals( "1" ) ){ //是移动公司
                    String sql =
                        "select c.CONTRACTORNAME from contractorinfo c where c.CONTRACTORID = '"
                        + bean.getId() + "'";
                    logger.info( sql );
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setContractorname( rs.getString( 1 ) );
                    }
                 logger.info( "contractorid：" + bean.getId() );
                 logger.info( "单位名称：" + bean.getContractorname() );
                }
                logger.info( "材料名称：" + bean.getName() );
                logger.info( "材料类型：" + bean.getType() );
                request.getSession().removeAttribute("bean");
            }

            PartExportDao dao = new PartExportDao();
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "storageinfo" );
            logger.info( "得到list" );
            dao.exportStorageResult( list, bean, newlownumber, newhignumber, oldlownumber, oldhignumber, response);
            logger.info( "输出excel成功" );

            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * 查询所有材料库存信息的表单
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryAllStorage(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,HttpServletResponse response)
        throws ClientException, Exception{
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        List regionList=null;
        List deptList=null;
        List conList=null;
        String region="select r.regionid,r.regionname "
                     +"from region r "
                     +"where (r.state is null or r.state<>'1') and substr(regionid,3,4)<>'1111'";
        String dept="select d.deptid,d.deptname,d.regionid "
                    +"from deptinfo d "
                    +"where (d.state is null or d.state<>'1')";
        String con="select c.contractorid ,c.contractorname ,c.regionid "
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

        return mapping.findForward("queryallstorage");
    }
}
