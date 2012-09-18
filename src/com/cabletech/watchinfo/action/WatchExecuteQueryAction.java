package com.cabletech.watchinfo.action;

import java.text.NumberFormat;
import java.util.*;
import javax.servlet.http.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.web.*;
import com.cabletech.utils.*;
import com.cabletech.watchinfo.beans.*;

public class WatchExecuteQueryAction extends WatchinfoBaseDispatchAction{

    private static Logger logger =
        Logger.getLogger( WatchExecuteQueryAction.class.getName() );

    public WatchExecuteQueryAction(){
    }


    /**
     * 查询外力盯防执行情况
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryWactchExecute( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        WatchQueryBean bean = ( WatchQueryBean )form;
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        logger.info("regionid:"+bean.getRegionid());
        logger.info("watchid:"+bean.getWatchid());
        logger.info("executorid:"+bean.getExecutorid());
        StringBuffer sqlB = new StringBuffer();
        sqlB.append( " \n " );
        sqlB.append( "	select 	distinct 	a.WATCHID, 		\n" );
        sqlB.append(
            "	to_char(a.EXECUTETIME,'yy/mm/dd hh24:mi:ss')	worktime,       \n" );
        sqlB.append( "	c.PATROLNAME	executorname,   \n" );

        sqlB.append( "	b.PLACENAME	watchname,      \n" );
        sqlB.append( "	b.WATCHPLACE	sublinename,    \n" );
        sqlB.append( "	e.OPERATIONDES	content         \n" );
        sqlB.append( "	from                          \n" );
        sqlB.append( "	WATCHEXECUTE	a,              \n" );
        sqlB.append( "	WATCHINFO	b,              \n" );
        sqlB.append( "	PATROLMANINFO	c,              \n" );
        sqlB.append( "	SUBLINEINFO	d,               \n" );
        sqlB.append( "	PATROLOPERATION	e               \n" );

        sqlB.append( " \n " );

        String sql = sqlB.toString();

        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

        sqlBuild.addConstant( " a.EXECUTORID = c.PATROLID(+)" );
        sqlBuild.addAnd();
        sqlBuild.addConstant( " a.WATCHID = b.PLACEID(+)" );
        sqlBuild.addAnd();
        sqlBuild.addConstant( " b.lid = d.SUBLINEID(+)" );
        sqlBuild.addAnd();
        sqlBuild.addConstant( "a.CONTENT = e.OPERATIONCODE(+)" );

        //sqlBuild.addConditionAnd("a.EXECUTETIME >= {0}",DateUtil.StringToDate(bean.getBegindate()));
        //sqlBuild.addConditionAnd("a.EXECUTETIME <= {0}",DateUtil.StringToDate(bean.getEnddate()));

        sqlBuild.addDateFormatStrEnd( "a.EXECUTETIME", bean.getBegindate(), ">=" );
        sqlBuild.addDateFormatStrEnd( "a.EXECUTETIME", bean.getEnddate(), "<=" );

        sqlBuild.addConditionAnd( "a.EXECUTORID = {0}", bean.getExecutorid() );
        sqlBuild.addConditionAnd( "a.WATCHID = {0}", bean.getWatchid() );
        sqlBuild.addConditionAnd( "b.lid={0}", bean.getSublineid() );
        sqlBuild.addConditionAnd( "b.regionid={0}", bean.getRegionid() );
        //市代维用户
        if(userinfo.getType().equals("22")){
            sqlBuild.addAnd();
            sqlBuild.addConstant( "b.regionid = '" + userinfo.getRegionID() + "'  and b.CONTRACTORID = ('" + userinfo.getDeptID() + "')");
        }
        //市移动用户
        if(userinfo.getType().equals("12")){
            sqlBuild.addAnd();
            sqlBuild.addConstant( "b.regionid = '" + userinfo.getRegionID() + "'");
        }

        sqlBuild.addConstant( " order by a.WATCHID" );

        sql = sqlBuild.toSql();

        logger.info("查询盯防明细" + sql);
        List list = super.getDbService().queryBeans( sql );

        request.getSession().setAttribute( "queryResult", list );

        return mapping.findForward( "querywatchexeresult" );
    }


    /**
     * 导出外力盯防表
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward exportWatchDetail( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        List lst = ( List )request.getSession().getAttribute( "queryResult" );
        if( lst != null ){
            super.getService().exportWatchDetail( lst, response );
        }
        return null;
    }


    /**
     * 统计外力盯防执行情况
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward getWatchSta( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        WatchStaBean conditionBean = ( WatchStaBean )form;
        if(conditionBean.getRegionid() == null){
            conditionBean.setRegionid("");
        }
        if( conditionBean.getContractorid() == null ){
            conditionBean.setContractorid( "" );
        }
        if( conditionBean.getExecutorid() == null ){
            conditionBean.setExecutorid( "" );
        }
        if( conditionBean.getWatchid() == null ){
            conditionBean.setWatchid( "" );
        }
        ///-------------------------zhj---------------------------
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

        } //市代维
        if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
            conditionBean.setContractorid( userinfo.getDeptID() );
            conditionBean.setRegionid( userinfo.getRegionID() );
        } //省移动
        if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

        }
        //省代维
        if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){

        }

 //       System.out.println( "danwei : " + conditionBean.getContractorid() );

        ///-------------------------------------zhj---------------     
        


        WatchStaResultBean resultBean = super.getService().getStaResultBean( conditionBean, userinfo );
        request.getSession().setAttribute( "QueryResult", resultBean );

        return mapping.findForward( "watchStaResult" );

    }


    /**
     * 导出外力盯防统计报表
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward ExportWatchSta( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        WatchStaResultBean resultBean = ( WatchStaResultBean )request.getSession().
                                        getAttribute(
                                        "QueryResult" );
        if( resultBean != null ){
            super.getService().exportWatchSta( resultBean, response );
        }
        return null;
    }
    public ActionForward exportWatchPointSta( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        WatchStaResultBean resultBean = ( WatchStaResultBean )request.getSession().
                                        getAttribute(
                                        "QueryResult" );
        if( resultBean != null ){
            super.getService().exportWatchPointSta( resultBean, response );
        }
        return null;
    }

    /**
     * 查询外力盯防执行情况
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward getInstantList( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String tableA = "( select distinct WATCHID, EXECUTORID, SIMID,  max(EXECUTETIME) exetime from WATCHEXECUTE where to_char(EXECUTETIME,'yyyy/mm/dd hh24:mi')  > to_char(sysdate-1/12,'yyyy/mm/dd hh24:mi') group by WATCHID, EXECUTORID, SIMID ) a, ";
        String sql = "select b.PLACENAME watch, b.WATCHPLACE place, c.PATROLNAME patrol, a.simid sim, to_char(exetime,'yy/mm/dd hh24:mi:ss') exetime from " +
                     tableA;
        sql += " WATCHINFO b, PATROLMANINFO c ";
        sql += " where a.watchid = b.PLACEID(+) and a.EXECUTORID = c.PATROLID";

        //logger.error("@@@@@@@@@@@@@@@@@@@@@@" + sql);

        List list = super.getDbService().queryBeans( sql );

        request.getSession().setAttribute( "queryResult_THREE", list );

        return mapping.findForward( "querywatchexeresult_login" );
    }


    /**
     * 查询盯防点信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryTempWatchPoint( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        try{
            WatchQueryBean bean = ( WatchQueryBean )form;
            String bdate = bean.getBegindate();
            String edate = bean.getEnddate();
            String rid = bean.getRegionid();
            String sql = "";
            logger.error( bdate + edate + rid );

            sql = "select a.pointid,a.gpscoordinate, b.regionname, a.simid, TO_CHAR(A.RECEIVETIME, 'YY/MM/DD HH24:MI:SS') receivetime, DECODE(a.BEDITED, 0, '未制定' , 1, '已制定', ' ') bedited, a.pointname from tempwatchpointinfo a, region b";

            QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

            sqlBuild.addConstant( "a.regionid=b.regionid" );
            sqlBuild.addAnd();
            sqlBuild.addConstant( "a.BEDITED=0" );
            if( !rid.equals( "" ) ){
                sqlBuild.addConditionAnd( "a.regionid = {0}", rid );
            }
            if( !bdate.equals( "" ) ){
                sqlBuild.addAnd();
                sqlBuild.addConstant( "a.receivetime >= to_date('" + bdate + "','YY/MM/DD HH24:MI:SS')" );
            }
            if( !edate.equals( "" ) ){
                sqlBuild.addAnd();
                sqlBuild.addConstant( "a.receivetime <= to_date('" + edate + "','YY/MM/DD HH24:MI:SS')" );
            }
            sql = sqlBuild.toSql();

//            logger.error( "******************" + sql );

            List list = super.getDbService().queryBeans( sql );
            request.setAttribute( "querytempResult", list );
            request.getSession().setAttribute( "querytempResult", list );
            return mapping.findForward( "querytempwatchresult" );
        }
        catch( Exception e ){

            logger.error( "查询出现异常：" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * 检索所有盯防点信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryAllTempWatchPoint( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        UserInfo userinfo = super.getLoginUserInfo( request );
        String deptid = userinfo.getDeptID();
        String type = userinfo.getDeptype();

        try{
            WatchQueryBean bean = ( WatchQueryBean )form;
            String bdate = bean.getBegindate();
            String edate = bean.getEnddate();
            String rid = bean.getRegionid();
            String bedited = bean.getBedited();
            String sql = "";
            logger.info( bdate + edate + rid );
            sql = "select a.pointid,a.gpscoordinate, a.gpscoordinate x, a.gpscoordinate y, b.regionname, a.simid, TO_CHAR(A.RECEIVETIME, 'YY/MM/DD HH24:MI:SS') receivetime, DECODE(a.BEDITED, 0, '未制定' , 1, '已制定', ' ') bedited, a.pointname, d.patrolname from tempwatchpointinfo a, region b,terminalinfo t, patrolmaninfo d";

            QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
            sqlBuild.addConstant( "a.regionid=b.regionid and a.SIMID = t.SIMNUMBER" );
            if(bedited != null && !bedited.equals("")){
                sqlBuild.addAnd();
                sqlBuild.addConstant( "a.BEDITED= '" + bedited + "' " );
            }
            //市移动
            if( "1".equals( type ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
                sqlBuild.addAnd();
                sqlBuild.addConstant( " a.REGIONID = '" + userinfo.getRegionID() + "' " );
            }
            //市代维
            if( "2".equals( type ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
                sqlBuild.addAnd();
                sqlBuild.addConstant( "  t.CONTRACTORID='" + deptid + "' " );
            }
            //省移动
            if( "1".equals( type ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
                if(rid != null && !rid.equals("")){
                    if( !rid.substring( 2, 6 ).equals( "0000" ) ){
                        sqlBuild.addAnd();
                        sqlBuild.addConstant( " a.REGIONID = '" + rid + "' " );
                    }
                }
            }
            //省代维
            if( "2".equals( type ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
                sqlBuild.addAnd();
                sqlBuild.addConstant( " t.CONTRACTORID in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
                    + userinfo.getDeptID() + "')" );
            }
            if( bdate != null && !bdate.equals( "" ) ){
                sqlBuild.addAnd();
                sqlBuild.addConstant( "a.receivetime >= to_date('" + bdate + "','YY/MM/DD HH24:MI:SS')" );
            }
            if( edate != null && !edate.equals( "" ) ){
                sqlBuild.addAnd();
                sqlBuild.addConstant( "a.receivetime <= to_date('" + edate + "','YY/MM/DD HH24:MI:SS')" );
            }
            sql = sqlBuild.toSql();
            sql += " and d.patrolid = t.ownerid  order by receivetime desc";
            logger.info( "sql" + sql );

            List list = super.getDbService().queryBeans( sql );
            
            // add by guixy 2008-11-12 
            DynaBean row;
            String xyStr;
            String[] xy;
            for (int i = 0; i < list.size(); i++) {
            	row = (DynaBean)list.get(i);
            	xyStr = getGps(String.valueOf(row.get("gpscoordinate")));
            	xy = xyStr.split(",");
            	if (xy.length == 2) {
            		row.set("x", xy[0]);
            		row.set("y", xy[1]);
				} else {
					row.set("x", "");
					row.set("y", "");
				}
			}
            
            request.setAttribute( "querytempResult", list );
            request.getSession().setAttribute( "querytempResult", list );
            super.setPageReset(request);
            return mapping.findForward( "queryalltempwatchresult" );
        }
        catch( Exception e ){
            logger.error( "查询出现异常：" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
	  * 坐标转换
	  * @param strGPSCoordinate
	  * @return String
	  */
	public String getGps(String strGPSCoordinate){
		if (strGPSCoordinate == null || "null".equals(strGPSCoordinate) 
				|| "".equals(strGPSCoordinate.trim())) {
			return "";
		}
		String strLatDu = strGPSCoordinate.substring(0, 2);
		String strLatFen = strGPSCoordinate.substring(2, 8);
		String strLongDu = strGPSCoordinate.substring(8, 11);
		String strLongFen = strGPSCoordinate.substring(11, 17);

		double dbLatDu = java.lang.Double.parseDouble(strLatDu);
		double dbLatFen = java.lang.Double.parseDouble(strLatFen);
		double dbLongDu = java.lang.Double.parseDouble(strLongDu);
		double dbLongFen = java.lang.Double.parseDouble(strLongFen);

		dbLatFen = dbLatFen / 600000;
		dbLongFen = dbLongFen / 600000;

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(8);
		nf.setMaximumIntegerDigits(3);

		dbLatDu = dbLatDu + dbLatFen;
		dbLongDu = dbLongDu + dbLongFen;
		nf.format(dbLatDu);
		nf.format(dbLongDu);
		String dtLd = String.valueOf(dbLongDu);
		if (dtLd.length() > 12)
			dtLd = dtLd.substring(0, 11);
		String dtLf = String.valueOf(dbLatDu);
		if (dtLf.length() > 12)
			dtLf = dtLf.substring(0, 11);
		
		return dtLd + "," + dtLf;
	}
    
    /**
     * 获得盯防统计信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward getWatchPointSta( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        WatchStaBean conditionBean = new WatchStaBean();
        String watchid = request.getParameter( "id" );
        String contractor = request.getParameter( "contractor" );
        conditionBean.setWatchid( watchid );
        conditionBean.setAction( "" );
        conditionBean.setBegindate( "2004-01-01" );
        conditionBean.setEnddate( "2046-01-01" );
        conditionBean.setContractorid( "" );
        conditionBean.setExecutorid( "" );
        conditionBean.setDatetype( "" );
        conditionBean.setMonth( "" );
        conditionBean.setRegionid( "" );
        conditionBean.setStatype( "3" );
        conditionBean.setYear( "" );

        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        WatchStaResultBean resultBean = super.getService().getStaResultBean( conditionBean, userinfo );

        String sql =
            "select b.CONTRACTORNAME, to_char(a.BEGINDATE,'yyyy/mm/dd'), to_char(a.ENDDATE,'yyyy/mm/dd') from watchinfo a, contractorinfo b where a.CONTRACTORID = b.CONTRACTORID and a.PLACEID = '"
            + watchid + "'";
        logger.info( sql );
        java.sql.ResultSet rs = null;
        com.cabletech.commons.hb.QueryUtil util = new com.cabletech.commons.hb.QueryUtil();
        rs = util.executeQuery( sql );
        if( rs.next() ){
            logger.info( rs.getString( 1 ) );
            resultBean.setContractor( rs.getString( 1 ) );
            resultBean.setDaterange( rs.getString( 2 )+"--"+rs.getString( 3 ) );
        }
        rs.close();
        request.getSession().setAttribute( "QueryResult", resultBean );

        return mapping.findForward( "watchStaPointResult" );

    }


    /**
     * 按查询结果导出盯防信息报表
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward exportWatchList( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        String contractorid = request.getParameter( "contractorid" );
        String executorid = request.getParameter( "executorid" );
        String watchid = request.getParameter( "watchid" );
        String begindate = request.getParameter( "begindate" );
        String enddate = request.getParameter( "enddate" );
        String regionid = request.getParameter( "regionid" );
        if(regionid == null){
            regionid = "";
        }
        List lstbean = new ArrayList();
        List lstvct = new ArrayList();
        List listcheck = new ArrayList();
        Vector vct;
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String sql = "select a.PLACEID,a.PLACENAME,a.PRINCIPAL,a.CONTRACTORID, a.TERMINALID ,to_char(a.BEGINDATE,'YYYY-MM-DD') begindate, to_char(a.ENDDATE,'YYYY-MM-DD') enddate, a.ORDERLYBEGINTIME, a.ORDERLYENDTIME, a.ORDERLYCYC, a.ERROR,a.REGIONID,a.LID,a.STARTPOINTID,a.ENDPOINTID,a.GPSCOORDINATE, a.INNERREGION, a.WATCHPLACE, a.PLACETYPE, a.DANGERLEVEL, a.WATCHREASON, a.ENDWATCHINFO, to_char(a.INVOLVEDLINENUMBER) involvedlinenumber,a.IFCHECKINTIME ,a.CHECKRESULT,a.DEALSTATUS from watchinfo a where a.DEALSTATUS != '0' ";
        if( !regionid.trim().equals( "" ) ){
            if( !regionid.substring( 2, 6 ).equals( "0000" ) ){
                sql += "and a.regionid = '" + regionid + "'";
            }
        }
        if( !contractorid.trim().equals( "" ) ){
            sql += "and a.contractorid = '" + contractorid + "'";
        }
        if( !executorid.trim().equals( "" ) ){
            sql += "and a.PRINCIPAL = '" + executorid + "'";
        }
        if( !watchid.trim().equals( "" ) ){
            sql += "and a.PLACEID = '" + watchid + "'";
        }
        if( !begindate.trim().equals( "" ) ){
            sql += "and a.begindate >= to_date('" + begindate + "','YYYY-MM-DD')";
        }
        if( !enddate.trim().equals( "" ) ){
            sql += "and a.enddate <= to_date('" + enddate + "','YYYY-MM-DD')";
        }
        if( begindate.trim().equals( "" ) && enddate.trim().equals( "" ) ){
            sql += "and a.begindate >= to_date('" + DateUtil.getNowYearStr() + "-" + DateUtil.getNowMonthStr()
                + "-"
                + "01" + "','YYYY-MM-DD')";
            sql += "and a.enddate <= LAST_DAY(SYSDATE)";
        }
        //市移动
         if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
             sql += " and a.regionid IN ('" + userinfo.getRegionID() + "') ";
         }
         //市代维
         if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
             sql += "and a.regionid IN ('" + userinfo.getRegionID() + "') and a.CONTRACTORID = ('" + userinfo.getDeptID() + "')";
         }
        sql += "order by a.begindate ";
        QueryUtil query = new QueryUtil();
        logger.info( "sql :" + sql );
        List listWatch = query.queryBeans( sql );
        if( listWatch.isEmpty() ){
            return forwardInfoPage( mapping, request, "019001" );
        }

        if( request.getSession().getAttribute( "ShowFIB" ).toString().equals( "show" ) ){
            for( int i = 0; i < listWatch.size(); i++ ){
                DynaBean DbWatch = ( DynaBean )listWatch.get( i );
                WatchBean wb = new WatchBean();
                // BeanUtil.objectCopy(DbWatch,wb);
                ObjectCopy( DbWatch, wb );

                vct = super.getService().getWatch_cable_fiber_list( wb );
                lstbean.add( i, wb ); //盯防信息
                lstvct.add( i, vct ); //光缆信息
                String sqlc = "select checkinfo, patroltime from watchcheckinfo where watchid = ";
                sqlc += "'" + DbWatch.get( "placeid" ) + "'";
                QueryUtil queryv = new QueryUtil();
                Vector checkvec = queryv.executeQueryGetVector( sqlc );
                listcheck.add( i, checkvec ); //施工核查信息
            }
   //         System.out.println( "listbean : " + lstbean.size() );
            super.getService().esportWatchList( lstbean, lstvct, listcheck, response );
            return null;
        }
        else{
            for( int i = 0; i < listWatch.size(); i++ ){
                DynaBean DbWatch = ( DynaBean )listWatch.get( i );
                WatchBean wb = new WatchBean();
                // BeanUtil.objectCopy(DbWatch,wb);
                ObjectCopy( DbWatch, wb );
                lstbean.add( i, wb );
            }
            super.getService().esportWatchList( lstbean, response );
            return null;
        }

    }


    /**
     * DynaBean copy to WatchBean
     * @param DbWatch DynaBean   from
     * @param wb WatchBean       to
     */
    public void ObjectCopy( DynaBean DbWatch, WatchBean wb ){

        if( DbWatch.get( "begindate" ) != null && !DbWatch.get( "begindate" ).equals( "" ) ){
            wb.setBeginDate( DbWatch.get( "begindate" ).toString() );
        }
        else{
            wb.setBeginDate( "" );
        }
        if( DbWatch.get( "contractorid" ) != null && !DbWatch.get( "contractorid" ).equals( "" ) ){
            wb.setContractorid( DbWatch.get( "contractorid" ).toString() );
        }
        else{
            wb.setContractorid( "" );
        }

        if( DbWatch.get( "dangerlevel" ) != null && !DbWatch.get( "dangerlevel" ).equals( "" ) ){
            wb.setDangerlevel( DbWatch.get( "dangerlevel" ).toString() );
        }
        else{
            wb.setDangerlevel( "" );
        }
        if( DbWatch.get( "dealstatus" ) != null && !DbWatch.get( "dealstatus" ).equals( "" ) ){
            wb.setDealstatus( DbWatch.get( "dealstatus" ).toString() );
        }
        else{
            wb.setDealstatus( "" );
        }
        if( DbWatch.get( "enddate" ) != null && !DbWatch.get( "enddate" ).equals( "" ) ){
            wb.setEndDate( DbWatch.get( "enddate" ).toString() );
        }
        else{
            wb.setEndDate( "" );
        }
        if( DbWatch.get( "endwatchinfo" ) != null && !DbWatch.get( "endwatchinfo" ).equals( "" ) ){
            wb.setEndwatchinfo( DbWatch.get( "endwatchinfo" ).toString() );
        }
        else{
            wb.setEndwatchinfo( "" );
        }
        if( DbWatch.get( "gpscoordinate" ) != null && !DbWatch.get( "gpscoordinate" ).equals( "" ) ){
            wb.setGpscoordinate( DbWatch.get( "gpscoordinate" ).toString() );
        }
        else{
            wb.setGpscoordinate( " " );
        }
        if( DbWatch.get( "ifcheckintime" ) != null && !DbWatch.get( "ifcheckintime" ).equals( "" )
            ){
            wb.setIfcheckintime( DbWatch.get( "ifcheckintime" ).toString().trim() );
        }
        else{
            wb.setIfcheckintime( " " );
        }
        if( DbWatch.get( "innerregion" ) != null && !DbWatch.get( "innerregion" ).equals( "" ) ){
            wb.setInnerregion( DbWatch.get( "innerregion" ).toString() );
        }
        else{
            wb.setInnerregion( " " );
        }
        if( DbWatch.get( "involvedlinenumber" ) != null && !DbWatch.get( "involvedlinenumber" ).equals( "" ) ){
            wb.setInvolvedlinenumber( DbWatch.get( "involvedlinenumber" ).toString() );
        }
        else{
            wb.setInvolvedlinenumber( " " );
        }
        if( DbWatch.get( "orderlybegintime" ) != null && !DbWatch.get( "orderlybegintime" ).equals( "" ) ){
            wb.setOrderlyBeginTime( DbWatch.get( "orderlybegintime" ).toString() );
        }
        else{
            wb.setOrderlyBeginTime( " " );
        }
        if( DbWatch.get( "orderlycyc" ) != null && !DbWatch.get( "orderlycyc" ).equals( "" ) ){
            wb.setOrderlyCyc( DbWatch.get( "orderlycyc" ).toString() );
        }
        else{
            wb.setOrderlyCyc( " " );
        }
        if( DbWatch.get( "orderlyendtime" ) != null && !DbWatch.get( "orderlyendtime" ).equals( "" ) ){
            wb.setOrderlyEndTime( DbWatch.get( "orderlyendtime" ).toString() );
        }
        else{
            wb.setOrderlyEndTime( " " );
        }
        if( DbWatch.get( "placeid" ) != null && !DbWatch.get( "placeid" ).equals( "" ) ){
            wb.setPlaceID( DbWatch.get( "placeid" ).toString() );
        }
        else{
            wb.setPlaceID( " " );
        }
        if( DbWatch.get( "placename" ) != null && !DbWatch.get( "placename" ).equals( "" ) ){
            wb.setPlaceName( DbWatch.get( "placename" ).toString() );
        }
        else{
            wb.setPlaceName( " " );
        }
        if( DbWatch.get( "placetype" ) != null && !DbWatch.get( "placetype" ).equals( "" ) ){
            wb.setPlacetype( DbWatch.get( "placetype" ).toString() );
        }
        else{
            wb.setPlacetype( " " );
        }
        if( DbWatch.get( "principal" ) != null && !DbWatch.get( "principal" ).equals( "" ) ){
            wb.setPrincipal( DbWatch.get( "principal" ).toString() );
        }
        else{
            wb.setPrincipal( " " );
        }
        if( DbWatch.get( "regionid" ) != null && !DbWatch.get( "regionid" ).equals( "" ) ){
            wb.setRegionID( DbWatch.get( "regionid" ).toString() );
        }
        else{
            wb.setRegionID( " " );
        }
        if( DbWatch.get( "terminalid" ) != null && !DbWatch.get( "terminalid" ).equals( "" ) ){
            wb.setTerminalID( DbWatch.get( "terminalid" ).toString() );
        }
        else{
            wb.setTerminalID( " " );
        }
        if( DbWatch.get( "watchplace" ) != null && !DbWatch.get( "watchplace" ).equals( "" ) ){
            wb.setWatchplace( DbWatch.get( "watchplace" ).toString() );
        }
        else{
            wb.setWatchplace( " " );
        }
        if( DbWatch.get( "watchreason" ) != null && !DbWatch.get( "watchreason" ).equals( "" ) ){
            wb.setWatchreason( DbWatch.get( "watchreason" ).toString() );
        }
    }

}
