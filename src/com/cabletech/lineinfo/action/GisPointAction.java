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
     * ȡ����ʱ����Ϣ
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

        //��ʱ��
        String sPID = request.getParameter( "sPID" );

        logger.info( "ȡ����ʱ��" + sPID );

        String sql =
            "SELECT A.GPSCOORDINATE GPS, A.POINTNAME POINTNAME, A.SIMID SIM, C.PATROLNAME PATROLNAME, TO_CHAR(A.RECEIVETIME, 'YY/MM/DD HH24:MI:SS') CREATETIME  FROM TEMPPOINTINFO A, TERMINALINFO B, PATROLMANINFO C WHERE A.SIMID = B.SIMNUMBER(+) AND B.OWNERID = C.PATROLID(+) AND A.pointid = '" +
            sPID +
            "'";
        logger.info( "��ѯ sql : " + sql );
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
     * ȡ���¹���Ϣ
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

        //ȡ���¹���Ϣ
        String sPID = request.getParameter( "sPID" );

        logger.info( "ȡ���¹���Ϣ" + sPID );

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

        logger.info( "��ѯ sql : " + sql );
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
     * ȡ��Ѳ�����Ϣ
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

        //Ѳ���
        String sPID = request.getParameter( "sPID" );
        logger.info( "GIS ��ID :" + sPID );

        Point data = super.getService().loadPoint( sPID );
        PointBean bean = new PointBean();
        logger.info( "GIS ��ȡѲ�����Ϣ ���" );

        BeanUtil.objectCopy( data, bean );

        request.setAttribute( "pointBean", bean );
        request.getSession().setAttribute( "pointBean", bean );

        return mapping.findForward( "toEditePoint" );
    }


    /**
     * ���Ӷ�����
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

            //����
            super.getDbService().dbUpdate( sqlStr );

            //return mapping.findForward("addWatchSuc");
            return forwardInfoPage( mapping, request, "0056" );
        }
        else{
            String errmsg = "�õ��Ѿ�������������";
            request.setAttribute( "errmsg", errmsg );

            logger.info( errmsg );
            return mapping.findForward( "haveSameWatch" );

        }
    }


    /**
     * �Ƿ�����ͬid
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
     * ͨ������λ����Ϣ�����µ���Ϣ���������������߶� (����)
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

        //��һ��Ѳ���
        String APId = request.getParameter( "sPID" );
        //�ڶ���Ѳ���
        String BPId = request.getParameter( "tPID" );

        LineinfoService lineService = new LineinfoService();
        lineService.link2Subline( APId, BPId );

        return mapping.findForward( "commonSuccess" );
    }


    /**
     * �������ߣ������������߶�
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

        //��һ��Ѳ���
        String APointGps = request.getParameter( "sPID" );
        //�ڶ���Ѳ���
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
     * ���߶��е�һ���ֵ�ת������Ѳ���
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

        //��һ��Ѳ���
        String APId = request.getParameter( "sPID" );
        //�ڶ���Ѳ���
        String BPId = request.getParameter( "tPID" );
        //��Ѳ���
        String newSubline = request.getParameter( "newSubline" );

        LineinfoService lineService = new LineinfoService();
        int flag = lineService.moveToAnotherSubline( APId, BPId, newSubline );

        if( flag == -1 ){
            String errmsg = "ѡ����������ֱ������������ͬ���߶��У�������ѡ������ͬһ�߶ε�����������ԣ�";
            request.setAttribute( "errmsg", errmsg );

            return mapping.findForward( "commonerror" );

        }

        return mapping.findForward( "commonSuccess" );
    }


    /**
     * ��ȡѲ��켣��ʷ��Ϣ
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

        //��ʱ��
        String sPID = request.getParameter( "sPID" );

        //logger.info("ȡ�õ�" + sPID);

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

        //logger.info("��ѯ sql : " + sql);

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
