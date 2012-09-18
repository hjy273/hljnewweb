package com.cabletech.baseinfo.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;

import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.web.*;

public class PointAction extends BaseInfoBaseDispatchAction{

    private static Logger logger =
        Logger.getLogger( PointAction.class.getName() );

    public ActionForward addPoint( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        //System.out.println("addPoint");
        PointBean bean = ( PointBean )form;

        Point data = new Point();
        BeanUtil.objectCopy( bean, data );
        data.setPointID( super.getDbService().getSeq( "pointinfo", 8 ) );
        data.setRegionID( super.getLoginUserInfo( request ).getRegionid() );

        //判断 点 顺序是否重复
        if( super.getService().checkPointOrder( data.getSubLineID(),
            data.getInumber() ) == 1 ){

            super.getService().createPoint( data );

            //Log
            log( request, " 增加巡检点信息（巡检点名称为："+bean.getPointName()+"） ", " 基础资料管理 " );

            //修改 所属线段 点数量 ++
            if( super.getService().updateSublineDym( data.getSubLineID(), "1" ) ==
                1 ){
                //更新线段成功
                return forwardInfoPage( mapping, request, "0051" );
            }
            else{
                //更新线段失败
                return forwardInfoPage( mapping, request, "0051" );
            }
        }
        else{
            //因为线序存在，需要重新输入
            String errmsg = "该线段中该顺序点已经存在，请调整后重试！";
            request.setAttribute( "errmsg", errmsg );

            //System.out.println(errmsg);
            return mapping.findForward( "commonerror" );
        }

    }


    /**
     * GIS 将临时点 -〉巡检点
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward addPointForGis( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        //临时点id
        String tempID = request.getParameter( "tempID" );

        PointBean bean = ( PointBean )form;

        Point data = new Point();
        BeanUtil.objectCopy( bean, data );
        data.setPointID( super.getDbService().getSeq( "pointinfo", 8 ) );
        data.setRegionID( super.getLoginUserInfo( request ).getRegionid() );

        //判断 点 顺序是否重复
        if( super.getService().checkPointOrder( data.getSubLineID(),
            data.getInumber() ) == 1 ){

            super.getService().createPoint( data );

            //修改 所属线段 点数量 ++
            super.getService().updateSublineDym( data.getSubLineID(), "1" );

            //将临时点置位

            //Log
            log( request, " 由临时点增加巡检点 （巡检点名称为："+bean.getPointName()+"）", " 实时监控 " );

            super.getService().setTempEdit( tempID );
            //记住上次的选择
            request.getSession().setAttribute( "sublineidLastTime",
                bean.getSubLineID() );

            return mapping.findForward( "addPointForGis" );
        }
        else{
            //因为线序存在，需要重新输入
            String errmsg = "该线段中该顺序点已经存在";
            request.setAttribute( "errmsg", errmsg );

            //System.out.println(errmsg);
            return mapping.findForward( "commonerror" );
        }

    }


    public ActionForward loadPoint( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        Point data = super.getService().loadPoint( request.getParameter( "id" ) );
        PointBean bean = new PointBean();
        BeanUtil.objectCopy( data, bean );
        request.setAttribute( "pointBean", bean );
        request.getSession().setAttribute( "pointBean", bean );
        return mapping.findForward( "updatePoint" );
    }


    /**
     * GIS 用
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward loadPointForGis( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        Point data = super.getService().loadPoint( request.getParameter( "id" ) );
        PointBean bean = new PointBean();
        BeanUtil.objectCopy( data, bean );
        request.setAttribute( "pointBean", bean );
        request.getSession().setAttribute( "pointBean", bean );
        return mapping.findForward( "loadPointForGIS" );
    }


    /**
     * 盯防点 用(根据线载入)
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward loadPoint4Watch( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        /*
         Point data = super.getService().loadPoint(request.getParameter("id"));
                PointBean bean = new PointBean();
                BeanUtil.objectCopy(data, bean);
                request.setAttribute("pointBean", bean);
                request.getSession().setAttribute("pointBean", bean);
         */

        String lineid = request.getParameter( "id" );
        String sqlStr = "pointid in (select p.pointid pointid from sublineinfo s,pointinfo p where p.sublineid = s.sublineid and	p.sublineid = '" +
                        lineid + "')";
        //System.out.println("盯防点载入...");
        request.setAttribute( "conStr", sqlStr );

        return mapping.findForward( "loadPoint4Watch" );
    }


    /**
     * 盯防点 用 (根据线段载入)
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward loadPoint4WatchBySubLine( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        /*
         Point data = super.getService().loadPoint(request.getParameter("id"));
                PointBean bean = new PointBean();
                BeanUtil.objectCopy(data, bean);
                request.setAttribute("pointBean", bean);
                request.getSession().setAttribute("pointBean", bean);
         */

        String sublineid = request.getParameter( "id" );

        String sqlStr = "pointid in (select p.pointid pointid from sublineinfo s,pointinfo p where p.sublineid = s.sublineid and	s.sublineid =  = '" +
                        sublineid + "')";

        //System.out.println("盯防点载入...");
        request.setAttribute( "conStr", sqlStr );

        return mapping.findForward( "loadPoint4Watch" );
    }


    public ActionForward queryPoint( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
    	//if(request.getSession().getAttribute("pointForm")!=null){
        if (!"1".equals(request.getParameter("isQuery"))) {
            form = (ActionForm) request.getSession().getAttribute("pointForm");
        }
        String sql = "select a.PointID, a.PointName,decode(a.state,'1','是','0','否','否') state, a.AddressInfo, a.gpsCoordinate, c.sublinename SubLineID, a.LineType, decode(a.IsFocus,'1','是','0','否','否') IsFocus, b.regionname RegionID from pointinfo a, region b, sublineinfo c ";

        String GpsCoordinate = ( ( PointBean )form ).getGpsCoordinate();
        if( GpsCoordinate == null ){
            GpsCoordinate = "";
        }
        else{
            GpsCoordinate = GpsCoordinate.replaceAll( "\'", "" );
        }

        String Inumber = ( ( PointBean )form ).getInumber();
        if( Inumber == null ){
            Inumber = "";
        }
        else{
            Inumber = Inumber.replaceAll( "\'", "" );
        }
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

        sqlBuild.addConstant( "a.regionid = b.regionid(+)" );
        sqlBuild.addAnd();
        sqlBuild.addConstant( "a.sublineid = c.sublineid(+)" );

        sqlBuild.addConditionAnd( "a.pointname like {0}",
            "%" + ( ( PointBean )form ).getPointName() + "%" );
        sqlBuild.addConditionAnd( "a.addressinfo like {0}",
            "%" + ( ( PointBean )form ).getAddressInfo() +
            "%" );
        sqlBuild.addConditionAnd( "a.gpscoordinate = {0}",
            GpsCoordinate );
        sqlBuild.addConditionAnd( "a.sublineid = {0}",
            ( ( PointBean )form ).getSubLineID() );
        sqlBuild.addConditionAnd( "a.isfocus = {0}",
            ( ( PointBean )form ).getIsFocus() );
        sqlBuild.addConditionAnd( "a.inumber = {0}",
            Inumber );
        sqlBuild.addTableRegion( "a.regionid",
            super.getLoginUserInfo( request ).getRegionid() );
        sqlBuild.addConditionAnd( "a.state = {0}",
                ( ( PointBean )form ).getState() );
        sql = sqlBuild.toSql();
        System.out.println( "************************" + sql );

        List list = super.getDbService().queryBeans( sql );
        request.getSession().setAttribute( "queryresult", list );
        request.getSession().setAttribute("pointForm", form);
        super.setPageReset(request);
        return mapping.findForward( "querypointresult" );
    }


    public ActionForward PointState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if(id !=null && !id.equals("")){
			String[] ids = id.split(",");
			String state;
			for (int i =0;i<ids.length;i++){
				if(!"".equals(ids[i])){
					Point point = this.getService().loadPoint(ids[i]);
					state = point.getState();
					if("1".equals(state)){
						state = "0";
					}else{
						state = "1";
					}
					point.setState(state);
					this.getService().updatePoint(point);
				}
			}
		}
		
		response.sendRedirect((String)request.getSession().getAttribute("S_BACK_URL"));
		return null;
	}


	public ActionForward updatePoint( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //bean.v
        Point data = new Point();
        PointBean bean=(PointBean)form;
        BeanUtil.objectCopy( bean, data );

        super.getService().updatePoint( data );
        //return mapping.findForward("sucessMsg");
        //Log
        log( request, " 更新巡检点信息（巡检点名称为："+bean.getPointName()+"） ", " 基础资料管理 " );
        return forwardInfoPage( mapping, request, "0052", data.getPointName() );
    }


    public ActionForward deletePoint( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //bean.v
        Point data = super.getService().loadPoint( request.getParameter( "id" ) );
        String sql1 = "delete from pointinfo where pointid ='" + data.getPointID() + "'";
        String sql2 = "delete from planexecute where pid='" + data.getPointID() + "'";
        String sql3 = "delete from subtaskinfo where objectid='" + data.getPointID() + "'";
        String sql4 = "delete from subline2point where pointid = '" + data.getPointID() + "'";
        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            try{
                exec.executeUpdate( sql1 );
                exec.executeUpdate( sql2 );
                exec.executeUpdate( sql3 );
                exec.executeUpdate(sql4);
                exec.commit();
                exec.setAutoCommitTrue();
                super.getService().updateSublineDym( data.getSubLineID(), "0" );
            }
            catch( Exception es ){
                logger.error( "删除点信息时出现异常：" + es.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return forwardInfoPage( mapping, request, "0058e" );
            }
        }
        catch( Exception ex ){
            logger.error( "删除巡检点信息出错：" + ex.getMessage() );
            return forwardInfoPage( mapping, request, "0058e" );
        }
//        super.getService().removePoint(data);
//        super.getService().deleteSubTaskInfo(data.getLineType());
//        super.getService().deletePlanExecute(String pointid);
        //巡检点数量 --

        //Log
        log( request, " 删除巡检点信息 ", " 基础资料管理 " );
        return forwardInfoPage( mapping, request, "0058" );
    }


    /**
     * GIS 用
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward deletePoint4GIS( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //bean.v
        Point data = super.getService().loadPoint( request.getParameter( "id" ) );
        super.getService().removePoint( data );

        //巡检点数量 --
        super.getService().updateSublineDym( data.getSubLineID(), "0" );
        //Log
        log( request, " 删除巡检点信息 ", " 实时监控 " );

        return mapping.findForward( "deletePoint4GIS" );
    }


    /**
     * 删除错误临时点，GIS用
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward deleteTempPoint4GIS( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //bean.v
        TempPoint data = super.getService().loadTP( request.getParameter( "id" ) );
        super.getService().deleteTempPoint( data );
        //Log
        log( request, " 删除临时点信息 ", " 实时监控 " );

        return mapping.findForward( "deletePoint4GIS" );
    }


    /**
     * 删除(置位)临时点信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward setEditTempPoint4GIS( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //bean.v
        super.getService().setTempEdit( request.getParameter( "id" ) );
        //Log
        log( request, " 删除(置位)临时点信息 ", " 实时监控 " );

        return mapping.findForward( "deletePoint4GIS" );
    }


    /**
     * GIS 用更新
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward updatePointForGis( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //bean.v
        Point data = new Point();
        PointBean bean=(PointBean)form;
        BeanUtil.objectCopy( bean, data );

        super.getService().updatePoint( data );
        //Log
        log( request, " 增加巡检点信息（巡检点名称为："+bean.getPointName()+"） ", " 实时监控 " );
        return mapping.findForward( "updatePointForGIS" );
        //return forwardInfoPage(mapping, request, "0052", data.getPointName());
    }


    /**
     * 巡检点属性更新（带有盯防点设置叶面）
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward updatePointForGisWatch( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //bean.v
        Point data = new Point();
        PointBean bean=(PointBean)form;
        BeanUtil.objectCopy( bean, data );

        super.getService().updatePoint( data );
        //return mapping.findForward("updatePointForGIS");
        log( request, " 更新巡检点信息（巡检点名称为："+bean.getPointName()+"） ", " 实时监控 " );
        return forwardInfoPage( mapping, request, "0053" );
    }


    public ActionForward exportPointResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "得到list" );
            super.getService().exportPointResult( list, response );
            logger.info( "输出excel成功" );
            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
  
}
