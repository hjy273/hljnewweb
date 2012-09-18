package com.cabletech.lineinfo.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.web.*;
import com.cabletech.lineinfo.beans.*;
import com.cabletech.lineinfo.domainobjects.*;
import com.cabletech.lineinfo.services.*;
import org.apache.log4j.Logger;


public class GisPointAction extends com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction{
   private static Logger logger = Logger.getLogger("GisPointAction");

    public GisPointAction(){
    }


    /**
     * 取得临时点信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward getTempPointInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //临时点
        String sPID = request.getParameter( "sPID" );

        logger.info( "取得临时点" + sPID );

        String sql =
            "SELECT A.GPSCOORDINATE GPS, A.POINTNAME POINTNAME, A.SIMID SIM, C.PATROLNAME PATROLNAME, TO_CHAR(A.RECEIVETIME, 'YY/MM/DD HH24:MI:SS') CREATETIME  FROM TEMPPOINTINFO A, TERMINALINFO B, PATROLMANINFO C WHERE A.SIMID = B.SIMNUMBER(+) AND B.OWNERID = C.PATROLID(+) AND A.pointid = '" +
            sPID +
            "'";
        logger.info( "查询 sql : " + sql );
        Vector vct = super.getDbService().queryVector( sql, "" );

        String GPS = "";
        String pointname = "";
        String sim = "";
        String patrolname = "";
        String ctime = "";

        GISTemppointbean temppbean = new GISTemppointbean();

        if( vct.size() > 0 ){
            GPS = ( String ) ( ( Vector )vct.get( 0 ) ).get( 0 );
            pointname = ( String ) ( ( Vector )vct.get( 0 ) ).get( 1 );
            sim = ( String ) ( ( Vector )vct.get( 0 ) ).get( 2 );
            patrolname = ( String ) ( ( Vector )vct.get( 0 ) ).get( 3 );
            ctime = ( String ) ( ( Vector )vct.get( 0 ) ).get( 4 );
        }

        if( pointname == null || pointname.length() == 0 ){
            pointname = "";
        }

        temppbean.setPointid( sPID );
        temppbean.setPointname( pointname );
        temppbean.setGps( GPS );
        temppbean.setSim( sim );
        temppbean.setPatrolname( patrolname );
        temppbean.setCreatetime( ctime );

        request.setAttribute( "temppbean", temppbean );

        return mapping.findForward( "tempPointPage" );
    }


    /**
     * 取得事故信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward getAccidentInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //取得事故信息
        String sPID = request.getParameter( "sPID" );

        logger.info( "取得事故信息" + sPID );

        String sql = "select nvl(b.patrolname,'') patrol, a.simid simid, ";
        sql +=
            " to_char(a.sendtime, 'yyyy/mm/dd') sendtime, nvl(c.operationdes,'') optype, ";

        sql += " nvl(d.pointname,'') point, nvl(e.sublinename,'') subline, ";
        sql += " a.pid pointid ";
        sql += " from ACCIDENT a, patrolmaninfo b, PATROLOPERATION c, pointinfo d, sublineinfo e  ";
        sql += " where a.patrolid = b.patrolid(+) ";
        sql += " and a.OPERATIONCODE = c.OPERATIONCODE(+) ";
        sql += " and a.pid = d.pointid(+) ";
        sql += " and a.lid = e.sublineid(+) ";
        sql += " and a.keyid = '" + sPID + "'";

        logger.info( "查询 sql : " + sql );
        Vector vct = super.getDbService().queryVector( sql, "" );

        String patrol = "";
        String simid = "";
        String sendtime = "";
        String optype = "";
        String point = "";
        String subline = "";
        String pointid = "";
        String alerttime = "";

        GisAccidentBean bean = new GisAccidentBean();

        if( vct.size() > 0 ){
            patrol = ( String ) ( ( Vector )vct.get( 0 ) ).get( 0 );
            simid = ( String ) ( ( Vector )vct.get( 0 ) ).get( 1 );
            sendtime = ( String ) ( ( Vector )vct.get( 0 ) ).get( 2 );
            optype = ( String ) ( ( Vector )vct.get( 0 ) ).get( 3 );
            point = ( String ) ( ( Vector )vct.get( 0 ) ).get( 4 );
            subline = ( String ) ( ( Vector )vct.get( 0 ) ).get( 5 );
            pointid = ( String ) ( ( Vector )vct.get( 0 ) ).get( 6 );
        }

        bean.setKeyid( sPID );
        bean.setPatrol( patrol );
        bean.setSimid( simid );
        bean.setSendtime( sendtime );
        bean.setOptype( optype );
        bean.setPoint( point );
        bean.setSubline( subline );

        sql = "select count(*) alerttimes from accident where pid = '" +
              pointid + "'";
        vct = super.getDbService().queryVector( sql, "" );

        if( vct.size() > 0 ){
            alerttime = ( String ) ( ( Vector )vct.get( 0 ) ).get( 0 );
        }

        bean.setAlerttime( alerttime );

        request.setAttribute( "GisAccidentBean", bean );

        return mapping.findForward( "getGisAccidentInfo" );
    }


    /**
     * 取得巡检点信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward getPointInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //巡检点
        String sPID = request.getParameter( "sPID" );
        logger.info( "GIS 点ID :" + sPID );

        Point data = super.getService().loadPoint( sPID );
        PointBean bean = new PointBean();
        logger.info( "GIS 获取巡检点信息 完成" );

        BeanUtil.objectCopy( data, bean );

        request.setAttribute( "pointBean", bean );
        request.getSession().setAttribute( "pointBean", bean );

        return mapping.findForward( "toEditePoint" );
    }


    /**
     * 增加盯防点
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward toAddWatchInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        PointBean ptBean = ( PointBean )form;

        int flag = ifHaveSameWatch( ptBean.getPointID() );

        if( flag == 1 ){

            StringBuffer sql = new StringBuffer(
                               "insert into watchinfo (placeid, placename, regionid, lid, gpscoordinate, isgeneralpoint) values " );

            sql.append( "(" );

            sql.append( "'" + ptBean.getPointID() +
                "', " );
            sql.append( "'" + ptBean.getPointName() + "', " );
            sql.append( "'" + ptBean.getRegionID() + "', " );
            sql.append( "'" + ptBean.getSubLineID() + "', " );
            sql.append( "'" + ptBean.getGpsCoordinate() + "', " );
            sql.append( "'1'" );

            sql.append( ")" );

            String sqlStr = sql.toString();
            logger.info( sqlStr );

            //保存
            super.getDbService().dbUpdate( sqlStr );

            //return mapping.findForward("addWatchSuc");
            return forwardInfoPage( mapping, request, "0056" );
        }
        else{
            String errmsg = "该点已经是外力盯防点";
            request.setAttribute( "errmsg", errmsg );

            logger.info( errmsg );
            return mapping.findForward( "haveSameWatch" );

        }
    }


    /**
     * 是否有相同id
     * @param id String
     * @throws Exception
     * @return int
     */
    public int ifHaveSameWatch( String id ) throws Exception{
        int flag = 1;

        String sql = "select * from watchinfo where placeid = '" + id + "'";
        Vector vct = new Vector();
        vct = super.getDbService().queryVector( sql, "" );

        if( vct.size() > 0 ){
            flag = -1;
        }

        return flag;
    }


    /**
     * 通过复制位置信息制造新点信息，连接两条相邻线段 (弃用)
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */

    public ActionForward MakePointViaGPS( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //第一个巡检点
        String APId = request.getParameter( "sPID" );
        //第二个巡检点
        String BPId = request.getParameter( "tPID" );

        LineinfoService lineService = new LineinfoService();
        lineService.link2Subline( APId, BPId );

        return mapping.findForward( "commonSuccess" );
    }


    /**
     * 增加虚线，以连接两条线段
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward makeLinkLine( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //第一个巡检点
        String APointGps = request.getParameter( "sPID" );
        //第二个巡检点
        String BPointGps = request.getParameter( "tPID" );
        TempLine data = new TempLine();

        data.setPointid( super.getDbService().getSeq( "templineinfo", 24 ) );
        data.setStartpointgps( APointGps );
        data.setEndpointgps( BPointGps );
        data.setRegionid( super.getLoginUserInfo( request ).getRegionid() );

        LineinfoService lineService = new LineinfoService();
        //lineService.link2Subline(APId, BPId);

        lineService.addTempLine( data );

        return mapping.findForward( "commonSuccess" );
    }


    /**
     * 将线段中的一部分点转移至新巡检段
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward moveToAnotherSubline( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //第一个巡检点
        String APId = request.getParameter( "sPID" );
        //第二个巡检点
        String BPId = request.getParameter( "tPID" );
        //新巡检段
        String newSubline = request.getParameter( "newSubline" );

        LineinfoService lineService = new LineinfoService();
        int flag = lineService.moveToAnotherSubline( APId, BPId, newSubline );

        if( flag == -1 ){
            String errmsg = "选区的两个点分别存在于两条不同的线段中，请重新选择属于同一线段的两个点后重试！";
            request.setAttribute( "errmsg", errmsg );

            return mapping.findForward( "commonerror" );

        }

        return mapping.findForward( "commonSuccess" );
    }


    /**
     * 获取巡检轨迹历史信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */

    public ActionForward getHistoryRouteInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //临时点
        String sPID = request.getParameter( "sPID" );

        //logger.info("取得点" + sPID);

        /*String sql =
            "select a.simid,a.executetime,p.pointname,s.sublinename from ((select id,executorid,simid,executetime,type,pid,lid,gpscoordinate from LocalizerRspLog) union (select id,executorid,simid,executetime,type,pid,lid,gpscoordinate from planexecute)) a,pointinfo p,sublineinfo s where a.pid=p.pointid(+) and a.lid=s.sublineid(+) AND a.id = '" +
            sPID +
            "'";
         */
        String sql = "";
        sql +=
            " select simid, executetime, pointname, sublinename, patrolname from ( \n";
        sql += " select a.simid simid, a.executetime executetime, b.pointname pointname, c.sublinename sublinename, d.patrolname patrolname \n";
        sql +=
            " from planexecute a, pointinfo b, sublineinfo c, patrolmaninfo d \n";
        sql += " where a.id = '" + sPID
            + "' and a.pid = b.pointid(+) and a.lid = c.sublineid(+) and a.executorid = d.patrolid(+)  \n";
        sql += " union \n";
        sql += " select a.simid simid, a.executetime executetime, b.pointname pointname, c.sublinename sublinename, d.patrolname patrolname \n";
        sql +=
            " from localizerrsplog a, pointinfo b, sublineinfo c, patrolmaninfo d \n";
        sql += " where a.id = '" + sPID
            + "' and a.pid = b.pointid(+) and a.lid = c.sublineid(+) and a.executorid = d.patrolid(+)  \n";
        sql += " )";

        //logger.info("查询 sql : " + sql);

        Vector vct = super.getDbService().queryVector( sql, "" );

        String GPS = "";
        String pointname = "";
        String simid = "";
        String patrolname = "";
        String sublinename = "";
        String executetime = "";

        Planexecutebean Planexecutebean = new Planexecutebean();

        if( vct.size() > 0 ){
            simid = ( String ) ( ( Vector )vct.get( 0 ) ).get( 0 );
            executetime = ( String ) ( ( Vector )vct.get( 0 ) ).get( 1 );
            pointname = ( String ) ( ( Vector )vct.get( 0 ) ).get( 2 );
            sublinename = ( String ) ( ( Vector )vct.get( 0 ) ).get( 3 );
            patrolname = ( String ) ( ( Vector )vct.get( 0 ) ).get( 4 );
        }

        if( pointname == null || pointname.length() == 0 ){
            pointname = "";
        }
        Planexecutebean.setSimid( simid );
        Planexecutebean.setPointname( pointname );
        Planexecutebean.setGps( GPS );
        Planexecutebean.setExecutetime( executetime );
        Planexecutebean.setPatrolname( patrolname );
        Planexecutebean.setSublinename( sublinename );
        Planexecutebean.setGps( GPS );

        request.setAttribute( "Planexecutebean", Planexecutebean );

        return mapping.findForward( "planexeuteinfopage" );
    }

}
