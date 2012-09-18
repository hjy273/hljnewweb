package com.cabletech.lineinfo.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.web.*;
import com.cabletech.lineinfo.common.*;
import org.apache.log4j.Logger;

public class GISAction extends com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction{
    private Logger logger = Logger.getLogger(GISAction.class);
    /*JSP调用
         public String getURL(String type)
         type为GIS的输出参数sType
         以下为JSP调用方法，需要GIS在返回串后面添加GIS的三个参数
     <%@ page import="com.cabletech.lineinfo.action.*" %>
     <%
         GISAction gis = new GISAction();
         out.println(gis.getURL("PointID"));
         out.println("<br>");
         out.println(gis.getURL("SubLineID"));
     %>
     */
    public String getURL( String pid, String type, String funid ) throws Exception{
//        String server = new String("http://wx:7001/WebApp/");
        String action = new String( "" );
        String method = new String( "method=gis2Manage" );
        String url = new String( "" );

        if( convertStr( type ).equals( String.valueOf( "point" ) ) ){
            action = "/tpAction.do?"; //巡检点相关
        }
        if( convertStr( type ).equals( String.valueOf( "subline" ) ) ){
            action = "/tsAction.do?"; //巡检段相关
        }
        if( convertStr( type ).equals( String.valueOf( "patrol" ) ) ){
            action = "/pmAction.do?"; //巡检人员相关
        }
        if( convertStr( type ).equals( String.valueOf( "error" ) ) ){
            action = "/info.do?"; //巡检人员相关
        }

        url = action + method + "&amp;sPID=" + pid + "&amp;sType=" + type + "&amp;sFunID=" + funid;
//        System.out.print( url );
        return url;
    }


    /*得到请求转发的URL，同时生成FORMBEAN，提供到请求转发的页面进行FORMAT FORM*/

    public ActionForward gis2Manage( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        String sType = this.convertStr( request.getParameter( "sType" ) );
        GISAction gisAction = new GISAction();
        ActionForward af = new ActionForward();
        if( sType.equals( String.valueOf( "point" ) ) ){
            logger.info( "point" );

            String sql = new String( "select sublineid, sublinename from sublineinfo" );
            List list = super.getDbService().queryBeans( sql );

            ArrayList lableValueList = new ArrayList();
            TableList tl = new TableList();
            lableValueList = tl.getList( list, "sublinename", "sublineid" );
            /*
                         Iterator it;
                         BasicDynaBean bdb;

                         it=((List)list).iterator();
                         while(it.hasNext()){
                bdb=(BasicDynaBean)it.next();
                lableValueList.add(
                    new LabelValueBean((String)(bdb.get("sublinename")),(String)(bdb.get("sublineid"))));
                }
             */
            request.setAttribute( "sublineList", lableValueList );
            af = gisAction.getPoint( mapping, form, request, response );
        }
        if( sType.equals( String.valueOf( "subline" ) ) ){
            logger.info( "subline" );
            af = gisAction.getSubline( mapping, form, request, response );
        }
        return af;
    }


    public ActionForward welcome( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        //String mapurl = new String("welcome");
        return mapping.findForward( "success" );
    }


    public ActionForward getPatrolman( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        String sPID = this.convertStr( request.getParameter( "sPID" ) );
        String sType = this.convertStr( request.getParameter( "sType" ) );
        String sFunID = this.convertStr( request.getParameter( "sFunID" ) );
        logger.info( "getTP Info: " + "  sPID:" + sPID + "   sType:" + sType + "   sFunID:" + sFunID );
        String mapurl = new String( "error" );
        PatrolMan data = super.getService().loadPatrolMan( sPID );

        PatrolManBean bean = new PatrolManBean();
        BeanUtil.objectCopy( data, bean );
        request.setAttribute( "pmBean", bean );

        mapurl = "updatepm";
        //String mapurl = new String(getMapping(sPID, sType, sFunID));
        //logger.info("Mapping URL = " + mapurl);
        return mapping.findForward( mapurl );
    }


    public ActionForward getSubline( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        String sPID = this.convertStr( request.getParameter( "sPID" ) );
        String sType = this.convertStr( request.getParameter( "sType" ) );
        String sFunID = this.convertStr( request.getParameter( "sFunID" ) );
        logger.info( "getTP Info: " + "  sPID:" + sPID + "   sType:" + sType + "   sFunID:" + sFunID );
        String mapurl = new String( "error" );
        Subline data = super.getService().loadSubline( sPID );
        SublineBean bean = new SublineBean();
        BeanUtil.objectCopy( data, bean );
        request.setAttribute( "tsBean", bean );
        mapurl = "updatesl";
        //String mapurl = new String(getMapping(sPID, sType, sFunID));
        //logger.info("Mapping URL = " + mapurl);
        return mapping.findForward( mapurl );
    }


    public ActionForward getPoint( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        String sPID = this.convertStr( request.getParameter( "sPID" ) );
        String sType = this.convertStr( request.getParameter( "sType" ) );
        String sFunID = this.convertStr( request.getParameter( "sFunID" ) );
        logger.info( "getTP Info: " + "  sPID:" + sPID + "   sType:" + sType + "   sFunID:" + sFunID );
        String mapurl = new String( "error" );

        PointBean bean = new PointBean();

        if( sFunID.equals( "insert" ) ){
            //插入临时点数据
            List datasl;
            TempPoint data = super.getService().loadTP( sPID );
            //datasl = super.getService().querySubline("subline");

            bean.setPointID( data.getPointID() );
            bean.setGpsCoordinate( data.getGpsCoordinate() );
            bean.setRegionID( data.getRegionID() );
            //BeanUtil.objectCopy(data, bean);
            request.setAttribute( "tpBean", bean );
            //request.setAttribute("sublineList",datasl);
            mapurl = "inserttp";
        }
        if( sFunID.equals( "update" ) ){
            Point data = super.getService().loadPoint( sPID );
            logger.info( "Load ok" );

            BeanUtil.objectCopy( data, bean );
            request.setAttribute( "tpBean", bean );
            mapurl = "updatetp";
        }

        logger.info( "setAttribute ok" );

        //String mapurl = new String(getMapping(sPID, sType, sFunID));
        //logger.info("Mapping URL = " + mapurl);
        return mapping.findForward( mapurl );
    }


    public String convertStr( String gisParameter ){
        /*GIS传递的参数转化成JSP需要的参数*/
        //String returnStr = new String("");
        /*得到的点为巡检段*/

        if( gisParameter.equals( null ) ){
            gisParameter = "error";
            logger.info( "error" );
        }
        else{
            if( gisParameter.equals( String.valueOf( "SubLineID" ) ) ){
                gisParameter = "subline";
            }
            /*得到的点为巡检点*/
            if( gisParameter.equals( String.valueOf( "PointID" ) ) ){
                gisParameter = "point";
            }

            /*得到手持设备发来的点的数据*/
            if( gisParameter.equals( String.valueOf( "GetTempPointID" ) ) ){
                gisParameter = "insert";
            }
            /*得到巡检点数据，用于更新*/
            if( gisParameter.equals( String.valueOf( "GetPointID" ) ) ){
                gisParameter = "update";
            }
            /*得到巡检点数据，用于察看*/
            if( gisParameter.equals( String.valueOf( "GetPointIDByCoordinate" ) ) ){
                gisParameter = "update";
            }
            logger.info( gisParameter );
        }
        return gisParameter;
    }


    /*根据传入参数sType判断mapping的页面*/
    public String getMapping( String pid, String type, String funid ){
        logger.info( "getMapping Info: " + "  PID:" + pid + "  Type:" + type + "  FunID:" + funid );
        String realpath = new String( "/realtime/" ); //可以通过参数配置
        String mapurl = new String( "error" );

        if( ( type.equals( String.valueOf( "point" ) ) ) & ( funid.equals( String.valueOf( "insert" ) ) ) ){
            mapurl = "inserttp";
            logger.info( "getMapping = " + mapurl );
        }

        if( ( type.equals( String.valueOf( "point" ) ) ) & ( funid.equals( String.valueOf( "update" ) ) ) ){
            mapurl = "updatetp";
            logger.info( "getMapping = " + mapurl );
        }

        if( type.equals( String.valueOf( "subline" ) ) ){
            mapurl = "updatets";
            logger.info( "getMapping = " + mapurl );
        }

        logger.info( "getMapping end = " + mapurl );
        return mapurl;
    }


    public ActionForward addPoint( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        logger.info( "addTP" );
        PointBean bean = ( PointBean )form;
        Point data = new Point();
        BeanUtil.objectCopy( bean, data );

        super.getService().createPoint( data );
        return forwardInfoPage( mapping, request, "1001" );
    }


    public ActionForward updatePoint( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        Point data = new Point();
        BeanUtil.objectCopy( ( PointBean )form, data );

        super.getService().updatePoint( data );
        return forwardInfoPage( mapping, request, "1002", data.getPointName() );
    }


    public ActionForward updateSubline( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        Subline data = new Subline();
        BeanUtil.objectCopy( ( SublineBean )form, data );

        super.getService().updateSubline( data );
        return forwardInfoPage( mapping, request, "1002", data.getSubLineName() );
    }


    public ActionForward updatePatrolman( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        PatrolMan data = new PatrolMan();
        BeanUtil.objectCopy( ( PatrolManBean )form, data );

        super.getService().updatePatrolMan( data );
        return forwardInfoPage( mapping, request, "1002", data.getPatrolName() );
    }

}
