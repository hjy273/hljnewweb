package com.cabletech.watchinfo.action;

import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.PatrolMan;
import com.cabletech.baseinfo.domainobjects.Point;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.BaseInfoService;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.web.ClientException;
import com.cabletech.lineinfo.beans.GISTemppointbean;
import com.cabletech.power.CheckPower;
import com.cabletech.watchinfo.beans.SubWatchBean;
import com.cabletech.watchinfo.beans.TempWatchBean;
import com.cabletech.watchinfo.beans.WatchBean;
import com.cabletech.watchinfo.domainobjects.SubWatch;
import com.cabletech.watchinfo.domainobjects.Watch;

public class WatchAction extends WatchinfoBaseDispatchAction {
    private static Logger logger = Logger.getLogger(WatchAction.class.getName());

    public WatchAction() {
    }

    /**
     * 删除临时外力盯防点
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward setEditTempWatch4GIS(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        // 临时盯防点
        String id = request.getParameter("id");
        String sql = "update TEMPWATCHPOINTINFO set BEDITED = '1' where POINTID='" + id + "'";
        UpdateUtil uu = new UpdateUtil();
        uu.executeUpdateWithCloseStmt(sql);

        return mapping.findForward("deleteTempPoint4GIS");
    }

    /**
     * 载入临时盯防点
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */

    public ActionForward getTempWatchInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        // 临时盯防点
        String sPID = request.getParameter("sPID");

        logger.info("临时盯防点" + sPID);

        String sql = "SELECT A.GPSCOORDINATE GPS, A.POINTNAME POINTNAME, A.SIMID SIM, C.PATROLNAME PATROLNAME, TO_CHAR(A.RECEIVETIME, 'YY/MM/DD HH24:MI:SS') CREATETIME  FROM TEMPWATCHPOINTINFO A, TERMINALINFO B, PATROLMANINFO C WHERE A.SIMID = B.SIMNUMBER(+) AND B.OWNERID = C.PATROLID(+) AND A.pointid = '"
                + sPID + "'";
        logger.info("查询 sql : " + sql);
        Vector vct = super.getDbService().queryVector(sql, "");

        String GPS = "";
        String pointname = "";
        String sim = "";
        String patrolname = "";
        String ctime = "";

        GISTemppointbean temppbean = new GISTemppointbean();

        if (vct.size() > 0) {
            GPS = (String) ((Vector) vct.get(0)).get(0);
            pointname = (String) ((Vector) vct.get(0)).get(1);
            sim = (String) ((Vector) vct.get(0)).get(2);
            patrolname = (String) ((Vector) vct.get(0)).get(3);
            ctime = (String) ((Vector) vct.get(0)).get(4);
        }

        if (pointname == null || pointname.length() == 0) {
            pointname = "";
        }

        temppbean.setPointid(sPID);
        temppbean.setPointname(pointname);
        temppbean.setGps(GPS);
        temppbean.setSim(sim);
        temppbean.setPatrolname(patrolname);
        temppbean.setCreatetime(ctime);

        request.setAttribute("temppbean", temppbean);

        return mapping.findForward("tempWatchPage");
    }

    /**
     * 执行添加盯防信息
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward addWatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        WatchBean bean = (WatchBean) form;

        String terminalid = "";
        terminalid = getTerminalidByPatrol(bean.getPrincipal());
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        // System.out.println( "cid<<<<<<<<<<<" + bean.getContractorid() + "
        // pid<<<<<<<<<<<" + bean.getPrincipal() );
        if (terminalid.equals("")) {

            String errmsg = "并未为该巡检员分配移动终端，请为指定巡检员分配终端或指定其他巡检员后重试！";
            request.setAttribute("errmsg", errmsg);

            logger.info(errmsg);
            // return mapping.findForward( "commonerror" );
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "commonerror", backUrl);

        } else {
            // System.out.println( "111" );
            bean.setPlaceID(getDbService().getSeq("watchinfo", 24));
            // System.out.println( "aaa" );
            String regionID = request.getParameter("regionID");
            // System.out.println( "sss" );
            if (userinfo.getDeptype().equals("2")) {
                logger.info("my depttye is 2");// //////////
                bean.setContractorid(userinfo.getDeptID());
                bean.setRegionID(userinfo.getRegionID());
                bean.setTerminalID(terminalid);
                bean.setWatchwidth(bean.getWatchwidth());
            } else {
                if (!regionID.equals("null")) {
                    bean.setRegionID(request.getParameter("regionID"));
                    bean.setTerminalID(terminalid);
                } else {
                    // System.out.println( "ddd" );
                    String cid = bean.getContractorid();
                    String sqlr = "select c.REGIONID from contractorinfo c where c.CONTRACTORID = '"
                            + cid + "'";
                    QueryUtil util = new QueryUtil();
                    ResultSet rs = util.executeQuery(sqlr);
                    String regionid = "";
                    while (rs.next()) {
                        regionid = rs.getString(1);
                    }
                    // System.out.println( "fff" );
                    bean.setRegionID(regionid);
                    // System.out.println( "ggg" );
                    bean.setTerminalID(terminalid);
                    bean.setWatchwidth(bean.getWatchwidth());
                    // System.out.println( "hhh" );
                }
            }
            // 时间间隔
            bean.setOrderlyCyc(String.valueOf(getCyc(bean.getOrderlyBeginTime(), bean
                    .getOrderlyEndTime())));
            // System.out.println( "jjjj" );
            logger.info("时间间隔 : " + bean.getError());
            // System.out.println( "222" );
            userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
            if (userinfo.getDeptype().equals("2")) { // 如果是代维单位，只显示本公司
                String contractorid = userinfo.getDeptID();
                bean.setContractorid(contractorid);
            }

            String endDate = bean.getEndDate();
            if (endDate.equals("待定") || bean.getEndDate().length() == 0) {
                bean.setEndDate("2004/10/10");
            }

            Watch data = new Watch();
            BeanUtil.objectCopy(bean, data);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            if (endDate.equals("待定") || endDate.length() == 0) {
                data.setEndDate(null);
            } else {
                // guixy add by 2009-4-8
                Date endTime = sdf.parse(bean.getEndDate().replaceAll("/", "-") + " "
                        + bean.getOrderlyEndTime());
                data.setEndDate(endTime);
                // guixy add by 2009-4-8
            }

            // guixy add by 2009-4-8
            Date beginTime = sdf.parse(bean.getBeginDate().replaceAll("/", "-") + " "
                    + bean.getOrderlyBeginTime());
            data.setBeginDate(beginTime);
            // guixy add by 2009-4-8

            data.setDealstatus("0");
            super.getService().createWatch(data);

            String sql = "";
            if (userinfo.getDeptype().equals("2")) {
                sql = "update TEMPWATCHPOINTINFO set BEDITED = '1' where POINTID = '"
                        + bean.getStartpointid() + "'";
                String gps1 = bean.getGpscoordinate();
                if (!gps1.equals("null")) {
                    logger.info("gps from bean is :" + gps1);
                    sql = "update TEMPWATCHPOINTINFO set BEDITED = '1' where gpscoordinate = '"
                            + gps1 + "'";
                }
            } else {
                if (!regionID.equals("null")) {
                    sql = "update TEMPWATCHPOINTINFO set BEDITED = '1' where POINTID = '"
                            + bean.getStartpointid() + "'";
                } else {
                    if (regionID.equals("null")) {
                        String gps = request.getSession().getAttribute("gpscoordinate").toString();
                        logger.info(gps);
                        sql = "update tempwatchpointinfo set bedited = '1' where gpscoordinate = '"
                                + gps + "'";
                        logger.info("my region is null and " + "sql is :" + sql);// /////
                    }
                }
                /*
                 * String tempID = request.getParameter( "tempID" );
                 * logger.info("=============************the tempID is:" +
                 * tempID ); if( !tempID.equals( "null" ) ){ sql = "update
                 * TEMPWATCHPOINTINFO set BEDITED = '1' where POINTID='" +
                 * tempID + "'"; logger.info("my tempid is not null and " + "sql
                 * is :" + sql);/////// }
                 */
                String gps2 = bean.getGpscoordinate();
                logger.info("=============************the gps is:" + gps2);
                if (!gps2.equals("null")) {
                    sql = "update TEMPWATCHPOINTINFO set BEDITED = '1' where gpscoordinate = '"
                            + gps2 + "'";
                }

                /*
                 * String gpscoordinate = request.getParameter( "gpscoordinate" );
                 * logger.info("=============the gpscoordinate is:" +
                 * gpscoordinate ); //add
                 * //System.out.println("========"+tempID); if(
                 * !gpscoordinate.equals( "null" ) ){ sql = "update
                 * TEMPWATCHPOINTINFO set BEDITED = '1' where GPSCOORDINATE='" +
                 * gpscoordinate + "'"; }
                 */
            }
            logger.info("sql:" + sql);
            super.getDbService().dbUpdate(sql);
            // String tempPoint = bean.getStartpointid();
            // logger.info("=============the tempID from request is:" +
            // request.getParameter( "tempID" ) + " from bean is " + tempPoint
            // );
            /*
             * if( !tempPoint.equals( "null" ) ){ sql = "update
             * TEMPWATCHPOINTINFO set BEDITED = '1' where POINTID='" + tempPoint +
             * "'"; super.getDbService().dbUpdate( sql ); }else{
             */

            if (request.getSession().getAttribute("ShowFIB").toString().equals("show")) {

                if (request.getParameter("involvedsegmentlist") != null) {
                    String[] cableIdArr = request.getParameterValues("involvedsegmentlist");

                    super.getService().removeWatchSubList(data);
                    super.getService().addNewWatchSubList(data, cableIdArr);
                }
            }
            if (userinfo.getDeptype().equals("2")) {
                // return forwardInfoPage( mapping, request, "0191" );
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "0191", backUrl);
            } else {
                if (!regionID.equals("null")) {
                    return mapping.findForward("addWatchForGis");
                } else {
                    // return forwardInfoPage( mapping, request, "0191" );
                    String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                    return super.forwardErrorPageWithUrl(mapping, request, "0191", backUrl);

                }
            }

        }

        /*
         * } else { String errmsg = "该线路内巡检点在交叉时段内已经存在盯防设定，请调整后重试！";
         * request.setAttribute("errmsg", errmsg);
         * 
         * logger.info(errmsg); return mapping.findForward("commonerror"); }
         */
    }

    /**
     * checkWatch
     * 
     * @param bean
     *            WatchBean
     * @return int
     */
    private int checkWatch(WatchBean bean) throws Exception {
        int flag = -1;

        String begindate = bean.getBeginDate();
        String enddate = bean.getEndDate();

        if (enddate.equals("待定") || enddate.length() == 0) {
            enddate = "3000/12/30";
        }

        String lineid = bean.getLid();

        BaseInfoService service = new BaseInfoService();
        Point beginpoint = service.loadPoint(bean.getStartpointid());
        // Point endpoint = service.loadPoint(bean.getEndpointid());
        Point endpoint = beginpoint;

        StringBuffer sqlb = new StringBuffer();
        sqlb.append("\n");
        sqlb
                .append("				select count(*) rnum from watchinfo a,                                                                       		                                                        		\n");
        sqlb
                .append("				(select distinct po.inumber stINum , wa.startpointid startpointid from watchinfo wa, pointinfo po where wa.startpointid = po.pointid) b, 		                            		\n");
        sqlb
                .append("				(select distinct po.inumber enINum , wa.endpointid endpointid from watchinfo wa, pointinfo po where wa.endpointid = po.pointid) c    		                                		\n");
        sqlb
                .append("				where  a.startpointid = b.startpointid and a.endpointid = c.endpointid and                                                                                          				\n");
        sqlb
                .append("				(                                                                                                                                                                       		\n");
        sqlb
                .append("					(                                                                                                		                                                                  		\n");
        sqlb
                .append("						(                                                                                              		                                                                  		\n");
        sqlb
                .append("							(b.stINum <= '"
                        + beginpoint.getInumber()
                        + "' and c.enINum >=  '"
                        + beginpoint.getInumber()
                        + "')                                                       		                                                                  		\n");
        sqlb
                .append("							or                                                                                           		                                                                  		\n");
        sqlb
                .append("							(b.stINum <= '"
                        + endpoint.getInumber()
                        + "' and c.enINum >=  '"
                        + endpoint.getInumber()
                        + "')                                                                                                                          		\n");
        sqlb
                .append("							or                                                                                                                                                                		\n");
        sqlb
                .append("							(b.stINum >= '"
                        + beginpoint.getInumber()
                        + "' and c.enINum <=  '"
                        + endpoint.getInumber()
                        + "')                                                                                                                           		\n");
        sqlb
                .append("																			                                                                                                                                          		\n");
        sqlb
                .append("						)                                                                                              		                                                                  		\n");
        sqlb
                .append("						and                                                                                            		                                                                  		\n");
        sqlb
                .append("						lid = '"
                        + lineid
                        + "'                                                                               		                                                                  		\n");
        sqlb
                .append("					)                                                                                                		                                                                  		\n");
        sqlb
                .append("					and                                                                                              		                                                                  		\n");
        sqlb
                .append("					(	                                                                                                                                                                    		\n");
        sqlb
                .append("						(                                                   		                                                                                                            		\n");
        sqlb
                .append("														                                                                                                                                                    		\n");
        sqlb
                .append("							(                                                                                                                                                                 		\n");
        sqlb
                .append("								(to_char(begindate,'yyyy/mm/dd') <= '"
                        + begindate
                        + "' )	                                                                                                            		\n");
        sqlb
                .append("								or                                                		                                                                                                          		\n");
        sqlb
                .append("								(to_char(begindate,'yyyy/mm/dd') <= '"
                        + enddate
                        + "' )                                                                                                              		\n");
        sqlb
                .append("								or                                                                                                                                                              		\n");
        sqlb.append("								(to_char(begindate,'yyyy/mm/dd') >= '" + begindate
                + "' and to_char(begindate,'yyyy/mm/dd') <= '" + enddate
                + "')                                                           		\n");
        sqlb
                .append("							)	                                                                                                                                                                		\n");
        sqlb
                .append("							and enddate is null                                		                                                                                                            		\n");
        sqlb
                .append("						)                                                   		                                                                                                            		\n");
        sqlb
                .append("						or	                                                    		                                                                                                        		\n");
        sqlb
                .append("						(                                                                                                  			                                                            		\n");
        sqlb
                .append("																					                                                                                                                                      		\n");
        sqlb.append("							(to_char(begindate,'yyyy/mm/dd') <= '" + begindate
                + "' and to_char(enddate,'yyyy/mm/dd') >= '" + begindate
                + "')			                                                          		\n");
        sqlb
                .append("							or                                                                                               			                                                            		\n");
        sqlb.append("							(to_char(begindate,'yyyy/mm/dd') <= '" + enddate
                + "' and to_char(enddate,'yyyy/mm/dd') >= '" + enddate
                + "')                                                               		\n");
        sqlb
                .append("							or                                                                                                                                                                		\n");
        sqlb.append("							(to_char(begindate,'yyyy/mm/dd') >= '" + begindate
                + "' and to_char(enddate,'yyyy/mm/dd') <= '" + enddate
                + "')                                                               		\n");
        sqlb
                .append("																					                                                                                                                                      		\n");
        sqlb
                .append("						)                                                                                                                                                                   		\n");
        sqlb
                .append("					                                                                                                                                                                      		\n");
        sqlb
                .append("					)                                                                                                                                                                     		\n");
        sqlb
                .append("				)                                                                                                                                                                       		\n");
        sqlb.append("				\n");

        String sql = sqlb.toString();

        logger.info("唯一性检查： \n" + sql);
        Vector vct = super.getDbService().queryVector(sql, "");
        Vector liVct = (Vector) vct.get(0);
        int tempi = Integer.parseInt((String) liVct.get(0));

        if (tempi == 0) {
            flag = 1;
        }

        return flag;
    }

    /**
     * getTerminalidByPatrol
     * 
     * @param string
     *            String
     * @return String
     */
    private String getTerminalidByPatrol(String patrolid) {
        String sql = "select terminalid from terminalinfo where ownerid ='" + patrolid + "'";

        Vector vct = new Vector();
        try {
            vct = super.getDbService().queryVector(sql, "");
        } catch (Exception e) {
            logger.info(e.toString());
        }

        String terminalid = "";
        if (vct != null && vct.size() > 0) {
            terminalid = (String) (((Vector) vct.get(0)).get(0));
        }

        return terminalid;
    }

    /**
     * 载入盯防信息
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadWatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        String watchid = request.getParameter("id");

        String sql = "select segment_kid segmentid,segmentname from watch_cable_fiber_list where watchid='"
                + watchid + "'";
        logger.info("查询光缆SQL:" + sql);
        QueryUtil qu = new QueryUtil();
        String[][] tempArr = qu.executeQueryGetArray(sql, "");

        Watch data = super.getService().loadWatch(watchid);
        WatchBean bean = new WatchBean();
        BeanUtil.objectCopy(data, bean);
        String xyStr = getGps(String.valueOf(bean.getGpscoordinate()));
        String[] xy = xyStr.split(",");
        if (xy.length == 2) {
            bean.setX(xy[0]);
            bean.setY(xy[1]);
        }

        request.setAttribute("watchBean", bean);
        request.setAttribute("gisweb", "web");
        request.setAttribute("tempArr", tempArr);

        return mapping.findForward("updateWatch");
    }

    /**
     * 载入盯防 GIS
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadWatch4GIS(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        String watchid = request.getParameter("sPID");

        String sql = "select segment_kid segmentid,segmentname from watch_cable_fiber_list where watchid='"
                + watchid + "'";
        logger.info("查询光缆SQL:" + sql);
        QueryUtil qu = new QueryUtil();
        String[][] tempArr = qu.executeQueryGetArray(sql, "");

        // Watch data = super.getService().loadWatch( request.getParameter(
        // "sPID" ) );
        Watch data = super.getService().loadWatch(watchid);
        WatchBean bean = new WatchBean();
        BeanUtil.objectCopy(data, bean);
        request.setAttribute("watchBean", bean);
        request.setAttribute("tempArr", tempArr);
        return mapping.findForward("updateWatch");

    }

    /**
     * 报表用
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadWatchAsObj(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        Watch data = super.getService().loadWatch(request.getParameter("id"));

        BaseInfoService baseinfoservice = new BaseInfoService();
        // Line line = baseinfoservice.loadLine(data.getLid());
        /*
         * String sql = "select nvl(a.sublinename,'') sublinename from
         * sublineinfo a, pointinfo b "; sql += " where b.sublineid =
         * a.sublineid(+) and b.pointid = '" + data.getStartpointid() + "' ";
         * QueryUtil qu = new QueryUtil(); String[][] resultArr =
         * qu.executeQueryGetArray(sql, "");
         */

        // Point p1 = baseinfoservice.loadPoint(data.getStartpointid());
        // Point p2 = baseinfoservice.loadPoint(data.getEndpointid());
        PatrolMan patrol = baseinfoservice.loadPatrolMan(data.getPrincipal());
        WatchBean bean = new WatchBean();
        BeanUtil.objectCopy(data, bean);

        bean.setLid("");
        // bean.setStartpointid(p1.getPointName());
        // bean.setEndpointid(p2.getPointName());
        bean.setPrincipal(patrol.getPatrolName());
        bean.setWatchwidth(bean.getWatchwidth() + "   米");

        if (bean.getEndDate() == null || bean.getEndDate().length() == 0) {
            bean.setEndDate("待定");
        }
        if (bean.getPlaceName() == null) {
            bean.setPlaceName("");
        }
        if (bean.getInnerregion() == null) {
            bean.setInnerregion("");
        }
        if (bean.getPlacetype() == null) {
            bean.setPlacetype("");
        }
        if (bean.getWatchreason() == null) {
            bean.setWatchreason("");
        }
        if (bean.getDangerlevel() == null) {
            bean.setDangerlevel("");
        }
        request.getSession().setAttribute("watchBean", bean);
        // add by guixy 2008-11-13
        String xyStr = getGps(String.valueOf(bean.getGpscoordinate()));
        String[] xy = xyStr.split(",");
        if (xy.length == 2) {
            bean.setX(xy[0]);
            bean.setY(xy[1]);
        }
        return mapping.findForward("watchform");
    }

    /**
     * GIS 新增盯防
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward toAddWatch4Gis(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        try {
            String sql = "select p.PATROLID,p.PATROLNAME,c.CONTRACTORID,c.CONTRACTORNAME "
                    + " from patrolmaninfo p,contractorinfo c "
                    + " where p.PARENTID = c.CONTRACTORID";
            QueryUtil query = new QueryUtil();
            List conInfo = query.queryBeans(sql);
            request.setAttribute("coninfo", conInfo);
            WatchBean bean = new WatchBean();
            String GPS = request.getParameter("sPID");
            bean.setGpscoordinate(GPS);
            request.setAttribute("watchBean", bean);
            return mapping.findForward("toAddWatch4GisDirectly");
        } catch (Exception e) {
            logger.info("添加盯防显示异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 删除盯防
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward deleteWatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        Watch data = super.getService().loadWatch(request.getParameter("id"));
        super.getService().removeWatch(data);

        // return forwardInfoPage( mapping, request, "0197" );
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, "0197", backUrl);
    }

    /**
     * 检索盯防
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryWatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        // if( !CheckPower.checkPower( request.getSession(), "40201" ) ){
        // return mapping.findForward( "powererror" );
        // }

        WatchBean bean = (WatchBean) form;
        // logger.info( "contractorid:" + bean.getContractorid() );
        // logger.info( "principal:" + bean.getPrincipal() );
        // logger.info("regionid:"+bean.getRegionID());
        String sql = "select a.placeid placeid, e.contractorname   contractorname,  innerregion  innerregion, watchplace      watchplace,           a.placename placename, a.dealstatus dealstatus, b.patrolname patrolname, c.sublinename line, to_char(a.begindate,'yy/mm/dd') begindate, nvl(to_char(a.enddate,'yy/mm/dd'),'待定' )enddate , watchplace, placetype from watchinfo a, patrolmaninfo b, sublineinfo c, pointinfo d, contractorinfo e";
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
        sqlBuild
                .addConstant("a.principal = b.patrolid(+) and d.sublineid = c.sublineid(+) and a.startpointid = d.pointid(+)");
        sqlBuild.addConditionAnd("a.PlaceName like {0}", "%" + bean.getPlaceName() + "%");
        sqlBuild.addConditionAnd("a.regionid = {0}", bean.getRegionID());
        sqlBuild.addConditionAnd("b.patrolid = {0}", bean.getPrincipal());
        sqlBuild.addConditionAnd("a.beginDate >= {0}", DateUtil.StringToDate(bean.getBeginDate()));
        sqlBuild.addConditionAnd("a.endDate <= {0}", DateUtil.StringToDate(bean.getEndDate()));
        sqlBuild.addAnd();
        sqlBuild.addConstant("a.contractorid = e.contractorid");
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String contractorid = userinfo.getDeptID();
        // 市移动
        if (userinfo.getDeptype().equals("1")
                && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
            sqlBuild.addConditionAnd("a.contractorid = {0}", bean.getContractorid());
        } // 市代维
        if (userinfo.getDeptype().equals("2")
                && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
            sqlBuild.addConditionAnd("a.contractorid = {0}", contractorid);
        } // 省移动
        if (userinfo.getDeptype().equals("1")
                && userinfo.getRegionID().substring(2, 6).equals("0000")) {
            sqlBuild.addConditionAnd("a.contractorid = {0}", bean.getContractorid());
        }
        // 省代维
        if (userinfo.getDeptype().equals("2")
                && userinfo.getRegionID().substring(2, 6).equals("0000")) {
            sqlBuild.addConditionAnd("a.contractorid = {0}", bean.getContractorid());
        }
        // sqlBuild.addConditionAnd("c.sublineid = {0}", bean.getLid());
        // sqlBuild.addConditionAnd("a.regionID = {0}", bean.getRegionID());
        sqlBuild.addTableRegion("a.regionid", super.getLoginUserInfo(request).getRegionid());
        sqlBuild.addConstant("order by a.beginDate desc");
        logger.info("&&&&&&&&&&&" + sqlBuild.toSql());

        List list = super.getDbService().queryBeans(sqlBuild.toSql());
        request.setAttribute("queryResult", list);
        request.getSession().setAttribute("queryResult", list);
        super.setPageReset(request);
        return mapping.findForward("querywatchresult");
    }

    /**
     * 更新盯防
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward updateWatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        Watch data = new Watch();
        WatchBean bean = (WatchBean) form;

        String terminalid = "";
        terminalid = getTerminalidByPatrol(bean.getPrincipal());
        // logger.info( "ttttttttttttttttttt" + terminalid );
        if (terminalid.equals("")) {

            String errmsg = "并未为该巡检员分配移动终端，请为指定巡检员分配终端或指定其他巡检员后重试！";
            request.setAttribute("errmsg", errmsg);

            logger.info(errmsg);
            // return mapping.findForward( "commonerror" );
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "commonerror", backUrl);

        } else {
            BaseInfoService bService = new BaseInfoService();
            // Point pt = bService.loadPoint(bean.getStartpointid());

            // bean.setGpscoordinate(pt.getGpsCoordinate());
            // bean.setEndpointid(bean.getStartpointid());

            bean.setTerminalID(terminalid);
            // 时间间隔
            bean.setOrderlyCyc(String.valueOf(getCyc(bean.getOrderlyBeginTime(), bean
                    .getOrderlyEndTime())));

            String endDate = bean.getEndDate();
            if (endDate.equals("待定") || bean.getEndDate().length() == 0) {
                bean.setEndDate("2004/10/10");
            }

            UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
            if (userinfo.getDeptype().equals("2")) { // 如果是代维单位，只显示本公司
                String contractorid = userinfo.getDeptID();
                bean.setContractorid(contractorid);
            }

            BeanUtil.objectCopy(bean, data);

            if (endDate.equals("待定") || endDate.length() == 0) {
                data.setEndDate(null);
            }

            super.getService().updateWatch(data);

            if (request.getSession().getAttribute("ShowFIB").toString().equals("show")) {

                if (request.getParameter("involvedsegmentlist") != null) {
                    String[] cableIdArr = request.getParameterValues("involvedsegmentlist");
                    super.getService().removeWatchSubList(data);
                    super.getService().addNewWatchSubList(data, cableIdArr);
                }
            }

            String gisweb = request.getParameter("gisweb");
            if (!gisweb.equals("null")) {
                // System.out.println("跳转到Web的页面！！！");
                request.setAttribute("gisweb", gisweb);
                // return forwardInfoPage( mapping, request, "0192" );
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardInfoPageWithUrl(mapping, request, "0192", backUrl);
            } else {
                // System.out.println("跳转到GIS的页面！！！");
                return mapping.findForward("updateWatch4GISSuccess");
            }
        }
    }

    public boolean checkPlaceID(String placeid) throws Exception {
        boolean flag = true; // 1,Ok 2,err

        String sql = "select * from watchinfo ";

        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
        sqlBuild.addConditionAnd("placeid = {0}", placeid);

        sql = sqlBuild.toSql();
        logger.info(sql);

        Vector vct = new Vector();

        try {
            vct = super.getDbService().queryVector(sql, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (vct.size() > 0) {
            flag = false;
        }

        return flag;
    }

    public ActionForward queryReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        List list = super.getService().watchReport();

        request.setAttribute("queryResult", list);
        request.getSession().setAttribute("queryResult", list);
        return mapping.findForward("querywatchreport");
    }

    /**
     * 导出盯防信息
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward exportWatchInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        WatchBean bean = (WatchBean) request.getSession().getAttribute("watchBean");
        if (bean != null) {
            super.getService().exportWatchInfo(bean, response);
        }
        return null;
    }

    /**
     * 取得值班总时长
     * 
     * @param startTimeStr
     *            String
     * @param endTimeStr
     *            String
     * @return int
     */
    public int getCyc(String startTimeStr, String endTimeStr) {
        int hours = 0;
        int minutes = 0;
        String[] startArr = DateUtil.parseStringForDate(3, startTimeStr);
        String[] endArr = DateUtil.parseStringForDate(3, endTimeStr);

        hours = Integer.parseInt(endArr[0]) - Integer.parseInt(startArr[0]);
        hours = Math.abs(hours);

        // 设置间隔时间为分钟
        // minutes = (Integer.parseInt(endArr[0])*60 +
        // Integer.parseInt(endArr[1]))
        // - (Integer.parseInt(startArr[0])*60 + Integer.parseInt(startArr[1]));

        if (Integer.parseInt(startArr[0]) > Integer.parseInt(endArr[0])) {
            hours = 24 - hours;
        }
        return hours;

    }

    /**
     * 载入完成的所有盯防
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadAllDoneWatches(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        String sql = "select distinct a.placeid placeid, a.placename placename, b.patrolname patrolname, to_char(a.begindate,'yy/mm/dd') begindate, nvl(to_char(a.enddate,'yy/mm/dd'),'待定' )enddate, nvl(dealstatus,'0') dealstatus, watchreason  from watchinfo a, patrolmaninfo b ";
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);

        sqlBuild.addConstant("a.principal = b.patrolid(+) ");

        sqlBuild.addConditionAnd("a.beginDate <= {0}", DateUtil.getNowDate());

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String contractorid = userinfo.getDeptID();
        // 市移动
        if (userinfo.getDeptype().equals("1")
                && !userinfo.getRegionID().substring(2, 6).equals("0000")) {

        } // 市代维
        if (userinfo.getDeptype().equals("2")
                && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
            sqlBuild.addConditionAnd("a.contractorid = {0}", contractorid);
        } // 省移动
        if (userinfo.getDeptype().equals("1")
                && userinfo.getRegionID().substring(2, 6).equals("0000")) {

        }
        // 省代维
        if (userinfo.getDeptype().equals("2")
                && userinfo.getRegionID().substring(2, 6).equals("0000")) {

        }
        sqlBuild.addTableRegion("a.regionid", super.getLoginUserInfo(request).getRegionid());

        sqlBuild.addConstant("order by beginDate desc");

        logger.info("sql" + sqlBuild.toSql());

        List list = super.getDbService().queryBeans(sqlBuild.toSql());

        request.setAttribute("queryResult", list);
        request.getSession().setAttribute("queryResult", list);
        super.setPageReset(request);
        return mapping.findForward("allDoneWatchesRestult");
    }

    /**
     * 填写完成施工信息
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadForStepOne(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        Watch data = super.getService().loadWatch(request.getParameter("id"));
        WatchBean bean = new WatchBean();
        BeanUtil.objectCopy(data, bean);

        // guixy add by 2008-11-27
        String gpsStr = getGps(bean.getGpscoordinate());
        String[] xy = gpsStr.split(",");
        if (xy.length == 2) {
            bean.setX(xy[0]);
            bean.setY(xy[1]);
        } else {
            bean.setX("");
            bean.setY("");
        }

        request.setAttribute("watchBean", bean);

        request.getSession().setAttribute("watchBean", bean);

        return mapping.findForward("toStepOne");

    }

    /**
     * 填写核查信息
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadForStepTwo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // 如果是代维单位是不允许的
            return forwardErrorPage(mapping, request, "partstockerror");
        }

        Watch data = super.getService().loadWatch(request.getParameter("id"));
        WatchBean bean = new WatchBean();
        BeanUtil.objectCopy(data, bean);

        // guixy add by 2008-11-27
        String gpsStr = getGps(bean.getGpscoordinate());
        String[] xy = gpsStr.split(",");
        if (xy.length == 2) {
            bean.setX(xy[0]);
            bean.setY(xy[1]);
        } else {
            bean.setX("");
            bean.setY("");
        }

        request.setAttribute("watchBean", bean);

        request.getSession().setAttribute("watchBean", bean);
        request.getSession().setAttribute("watchSubList",
                super.getService().getWatch_cable_fiber_list(bean));

        return mapping.findForward("toStepTwo");

    }

    /**
     * 执行填加施工信息
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward FinishStepOne(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        WatchBean bean = (WatchBean) form;
        String wid = "";
        wid = bean.getPlaceID();
        String sql = "";
        if (!bean.getEndwatchinfo().trim().equals("")) {
            sql = "update watchinfo set dealstatus = '1',endwatchinfo = ";
            sql += "'" + bean.getEndwatchinfo() + "' where placeid = ";
            sql += "'" + wid + "'";
            System.out.println("sql:" + sql);
            super.getDbService().dbUpdate(sql);
        }

        QueryUtil queryutil = new QueryUtil();
        ResultSet rs = null;
        sql = "select enddate from watchinfo where placeid = '" + wid + "'";
        rs = queryutil.executeQuery(sql);
        while (rs.next()) {
            if (rs.getString(1) == null) {
                String sql2 = "update watchinfo set enddate = TO_Date( '" + DateUtil.getNowDate()
                        + "', 'YYYY-MM-DD') where placeid = '" + wid + "'";
                super.getDbService().dbUpdate(sql2);
            }
        }
        return forwardInfoPage(mapping, request, "0194");
    }

    /**
     * 取得列表中某条的信息
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadSubWatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        SubWatch data = super.getService().loadSubWatch(request.getParameter("id"));
        SubWatchBean bean = new SubWatchBean();
        BeanUtil.objectCopy(data, bean);
        request.setAttribute("SubWatchBean", bean);

        request.getSession().setAttribute("SubWatchBean", bean);

        return mapping.findForward("toEditSubWatch");

    }

    /**
     * 更新盯防
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward updateSubWatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        SubWatch data = new SubWatch();
        SubWatchBean bean = (SubWatchBean) form;

        BeanUtil.objectCopy(bean, data);

        super.getService().updateSubWatch(data);

        // return forwardInfoPage( mapping, request, "0195" );
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, "0195", backUrl);
    }

    // public ActionForward FinishStepTwo( ActionMapping mapping,
    // ActionForm form,
    // HttpServletRequest request,
    // HttpServletResponse response ) throws
    // ClientException, Exception{
    //
    // Watch data = new Watch();
    // WatchBean bean = ( WatchBean )form;
    //
    // String terminalid = "";
    // terminalid = getTerminalidByPatrol( bean.getPrincipal() );
    //
    // bean.setTerminalID( terminalid );
    // data.setTerminalID( terminalid );
    // bean.setOrderlyCyc( String.valueOf( getCyc( bean.
    // getOrderlyBeginTime(),
    // bean.getOrderlyEndTime() ) ) );
    // BeanUtil.objectCopy( bean, data );
    //
    // data.setDealstatus( "2" );
    //
    // super.getService().updateWatch( data );
    // return forwardInfoPage( mapping, request, "0194" );
    //
    // }
    // ********************************pzj 修改
    /**
     * 执行添加核查信息
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward FinishStepTwo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        WatchBean bean = (WatchBean) form;

        String wid = "";
        wid = bean.getPlaceID();
        String patrolid = "";
        patrolid = bean.getPrincipal();
        // System.out.println( "盯防id：" + wid + "人id：" + patrolid );

        String sql = "";

        if (!bean.getCheckresult().trim().equals("")) {
            sql = "INSERT INTO watchcheckinfo ( WATCHID, PATROLID, PATROLTIME, CHECKINFO) VALUES ( ";
            sql += "'" + wid + "',";
            sql += "'" + patrolid + "',";
            sql += "TO_Date( '" + DateUtil.getNowDate() + "', 'YYYY/MM/DD'),";

            sql += "'" + bean.getCheckresult() + "')";
            // System.out.println( "sql: " + sql );
            super.getDbService().dbUpdate(sql);
        }
        String ifcheckdone = "";
        ifcheckdone = bean.getIfcheckdone();
        // System.out.println( "if。。。。" + ifcheckdone );

        if (ifcheckdone.trim().equals("yes")) {
            String sql1 = "update watchinfo set dealstatus = '2' where placeid = ";
            sql1 += "'" + wid + "'";
            super.getDbService().dbUpdate(sql1);
        }
        String sql2 = "";
        if (!bean.getIfcheckintime().trim().equals("")) {
            sql2 = "update watchinfo set ifcheckintime = ";
            sql2 += "'" + bean.getIfcheckintime() + "' where placeid = ";
            sql2 += "'" + wid + "'";
            super.getDbService().dbUpdate(sql2);
        }
        return forwardInfoPage(mapping, request, "0194");
    }

    /**
     * 导出新报表
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    // public ActionForward exportWatchConstructInfo( ActionMapping mapping,
    // ActionForm form,
    // HttpServletRequest request,
    // HttpServletResponse response ) throws
    // ClientException, Exception{
    //
    // Watch data = super.getService().loadWatch( request.getParameter( "id" )
    // );
    // WatchBean bean = new WatchBean();
    // BeanUtil.objectCopy( data, bean );
    // request.setAttribute( "watchBean", bean );
    //
    // Vector vct = super.getService().getWatch_cable_fiber_list( bean );
    //
    // if( bean != null ){
    // if(request.getSession().getAttribute("ShowFIB").toString().equals("show")){
    // super.getService().exportWatchConstructInfo( bean, vct, response );
    // }else{
    // super.getService().exportWatchConstructInfoNoCable( bean, response );
    // }
    // }
    // return null;
    // }
    // *************************************pzj 修改
    /**
     * 导出盯防信息
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward exportWatchConstructInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        Watch data = super.getService().loadWatch(request.getParameter("id"));
        WatchBean bean = new WatchBean();
        BeanUtil.objectCopy(data, bean);
        request.setAttribute("watchBean", bean);

        Vector vct = super.getService().getWatch_cable_fiber_list(bean);

        String sql = "select checkinfo from watchcheckinfo where watchid = ";
        sql += "'" + request.getParameter("id") + "'";
        // System.out.println( "sql----" + sql );
        QueryUtil query = new QueryUtil();
        Vector checkvec = query.executeQueryGetVector(sql);
        if (checkvec.isEmpty()) {
            if (bean != null) {
                if (request.getSession().getAttribute("ShowFIB").toString().equals("show")) {
                    super.getService().exportWatchConstructInfo(bean, vct, response);
                } else {
                    super.getService().exportWatchConstructInfoNoCable(bean, response);
                }
            }

            return null;
        } else {
            if (bean != null) {
                if (request.getSession().getAttribute("ShowFIB") != null
                        && request.getSession().getAttribute("ShowFIB").toString().equals("show")) {
                    super.getService().exportWatchConstructInfo(checkvec, bean, vct, response);
                } else {
                    super.getService().exportWatchConstructInfoNoCable(bean, response);
                }
            }
            return null;
        }
    }

    /**
     * 查询所有临时盯防点信息
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryTempWatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        if (!CheckPower.checkPower(request.getSession(), "40102")) {
            return mapping.findForward("powererror");
        }

        try {
            UserInfo userinfo = super.getLoginUserInfo(request);
            String cid = userinfo.getDeptID();
            String type = userinfo.getDeptype();
            StringBuffer sql = new StringBuffer(
                    "select a.pointid,a.gpscoordinate, a.gpscoordinate x, a.gpscoordinate y, b.regionname, a.simid, TO_CHAR(A.RECEIVETIME, 'YY/MM/DD HH24:MI:SS') receivetime, DECODE(a.BEDITED, 0, '未制定' , 1, '已制定', ' ') bedited, a.pointname, d.patrolname from tempwatchpointinfo a, region b , terminalinfo c, patrolmaninfo d where a.regionid=b.regionid and a.SIMID=c.SIMNUMBER   and a.bedited=0 ");
            // 市移动
            if ("1".equals(type) && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
                sql.append(" and  a.regionid='" + userinfo.getRegionID() + "' ");
            }
            // 市代维
            if ("2".equals(type) && !userinfo.getRegionID().substring(2, 6).equals("0000")) {
                sql.append(" and  c.CONTRACTORID='" + cid + "' ");
            }
            // 省移动
            if ("1".equals(type) && userinfo.getRegionID().substring(2, 6).equals("0000")) {

            }
            // 省代维
            if ("2".equals(type) && userinfo.getRegionID().substring(2, 6).equals("0000")) {
                sql
                        .append(" and c.CONTRACTORID in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
                                + userinfo.getDeptID() + "')");
            }
            sql.append(" and d.patrolid = c.ownerid ");
            sql.append("order by receivetime desc");
            // System.out.println( super.getLoginUserInfo( request ).getDeptID()
            // + sql );
            QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql.toString());
            logger.info("查询临时盯防点SQL:" + sqlBuild.toSql());
            List list = super.getDbService().queryBeans(sqlBuild.toSql());

            // add by guixy 2008-11-12
            DynaBean row;
            String xyStr;
            String[] xy;
            for (int i = 0; i < list.size(); i++) {
                row = (DynaBean) list.get(i);
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

            request.setAttribute("querytempResult", list);
            request.getSession().setAttribute("querytempResult", list);
            return mapping.findForward("querytempwatchresult");
        } catch (Exception e) {
            logger.info("查询出现异常：" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 坐标转换
     * 
     * @param strGPSCoordinate
     * @return String
     */
    public String getGps(String strGPSCoordinate) {
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
     * 读取临时盯防点信息
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward getTempWatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        try {
            String GPS = request.getParameter("id");
            String sql = "SELECT A.GPSCOORDINATE , A.POINTNAME ,b.regionname,  A.SIMID ,  TO_CHAR(A.RECEIVETIME, 'YY/MM/DD HH24:MI:SS') CREATETIME  FROM TEMPWATCHPOINTINFO A, region b WHERE a.regionid=b.regionid and A.GPSCOORDINATE = '"
                    + GPS + "'";
            logger.info("查询 sql : " + sql);

            Vector vct = super.getDbService().queryVector(sql, "");

            String pointname = "";
            String sim = "";
            String regionname = "";
            String ctime = "";

            TempWatchBean tempbean = new TempWatchBean();

            if (vct.size() > 0) {
                GPS = (String) ((Vector) vct.get(0)).get(0);
                pointname = (String) ((Vector) vct.get(0)).get(1);
                sim = (String) ((Vector) vct.get(0)).get(3);
                regionname = (String) ((Vector) vct.get(0)).get(2);
                // ctime = ( String ) ( ( Vector )vct.get( 0 ) ).get( 3 );
            }

            if (pointname == null || pointname.length() == 0) {
                pointname = "";
            }

            logger.info(GPS);
            tempbean.setPointname(pointname);
            tempbean.setGpscoordinate(GPS);
            tempbean.setSimid(sim);
            tempbean.setReceivetime(ctime);

            request.setAttribute("watchtempbean", tempbean);

            request.getSession().setAttribute("pointname", pointname);
            request.getSession().setAttribute("regionname", regionname);
            request.getSession().setAttribute("gpscoordinate", GPS);
            // add by guixy 2008-11-12
            String xyStr = getGps(String.valueOf(GPS));
            String[] xy = xyStr.split(",");
            if (xy.length == 2) {
                request.getSession().setAttribute("x", xy[0]);
                request.getSession().setAttribute("y", xy[1]);
            } else {
                request.getSession().setAttribute("x", "");
                request.getSession().setAttribute("y", "");
            }

            String sql1 = "select p.PATROLID,p.PATROLNAME,c.CONTRACTORID,c.CONTRACTORNAME "
                    + " from patrolmaninfo p,contractorinfo c "
                    + " where p.PARENTID = c.CONTRACTORID";
            QueryUtil query = new QueryUtil();
            List conInfo = query.queryBeans(sql1);
            UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
            if (userinfo.getDeptype().equals("2")) {
                String cid = userinfo.getDeptID();
                request.getSession().setAttribute("cid", cid);
                // System.out.println( "cid:" + cid );
            }
            request.setAttribute("coninfo", conInfo);

            return mapping.findForward("gettempwatch");
        } catch (Exception e) {
            logger.info("读取盯防点出现异常：" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }

    }

    /**
     * 删除临时盯防点
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward deleteTempWatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        String gps = request.getParameter("id");
        logger.info(gps);
        String sql = "delete from tempwatchpointinfo where gpscoordinate = '" + gps + "'";
        super.getDbService().dbUpdate(sql);
        // return forwardInfoPage( mapping, request, "0198" );
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, "0198", backUrl);

    }

    /**
     * getInfoByConid
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward getInfoByConid(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        // logger.info( "dddddddddddddddddddddddddd" );
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String contractorid = userinfo.getDeptID();
        // logger.info( contractorid );
        String sql = "select patrolname from patrolmaninfo where parentid = '" + contractorid + "'";
        logger.info(sql);
        QueryUtil util = new QueryUtil();
        ResultSet rs = util.executeQuery(sql);
        while (rs.next()) {
            String s = rs.getString(1);
            logger.info("ssssssssssssss" + s);
        }
        return null;

    }

    /**
     * 导出临时点报表
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward exportTempWatchResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {

            List list = (List) request.getSession().getAttribute("querytempResult");
            logger.info("得到list");
            logger.info("输出excel成功");
            super.getService().exportTempWatchResult(list, response);
            return null;
        } catch (Exception e) {
            logger.info("导出信息报表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 导出信息报表
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward exportWatchResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {

            List list = (List) request.getSession().getAttribute("queryResult");
            logger.info("得到list");
            logger.info("输出excel成功");
            super.getService().exportWatchResult(list, response);
            return null;
        } catch (Exception e) {
            logger.info("导出信息报表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward showQueryWatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        QueryUtil query = new QueryUtil();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List reginfo = null;
        List coninfo = null;
        List uinfo = null;

        // //////////////////////查询所有区域----省级用户
        String region = "select  r.REGIONNAME, r.REGIONID "
                + " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
        // //////////////////////代维
        String con = "select  c.CONTRACTORID, c.CONTRACTORNAME, c.REGIONID "
                + " from  contractorinfo c " + " where c.STATE is null ";
        // //////////////////////巡检人
        String user = "select p.PATROLID, p.PATROLNAME, p.REGIONID, p.PARENTID "
                + " from patrolmaninfo p " + " where p.STATE is null ";

        // 市移动
        if (userinfo.getDeptype().equals("1")
                && !userinfo.getRegionID().substring(2, 6).equals("0000")) {

            con += "  and c.regionid IN ('" + userinfo.getRegionID() + "') ";
            user += "  and p.regionid IN ('" + userinfo.getRegionID() + "')  order by p.PARENTID ";

            coninfo = query.queryBeans(con);
            uinfo = query.queryBeans(user);

        }
        // 市代维
        if (userinfo.getDeptype().equals("2")
                && !userinfo.getRegionID().substring(2, 6).equals("0000")) {

            user += "  and p.PARENTID in  ('" + userinfo.getDeptID() + "') order by p.PARENTID ";

            uinfo = query.queryBeans(user);

        }
        // 省移动
        if (userinfo.getDeptype().equals("1")
                && userinfo.getRegionID().substring(2, 6).equals("0000")) {
            user += " order by p.PARENTID ";
            reginfo = query.queryBeans(region);
            coninfo = query.queryBeans(con);
            uinfo = query.queryBeans(user);
        }
        // 省代维
        if (userinfo.getDeptype().equals("2")
                && userinfo.getRegionID().substring(2, 6).equals("0000")) {

            user += " order by p.PARENTID ";

            reginfo = query.queryBeans(region);
            coninfo = query.queryBeans(con);
            uinfo = query.queryBeans(user);

        }
        request.setAttribute("reginfo", reginfo);
        request.setAttribute("coninfo", coninfo);
        request.setAttribute("uinfo", uinfo);
        super.setPageReset(request);
        return mapping.findForward("showquerywatch");
    }

    public ActionForward showQueryWatchExe(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        QueryUtil query = new QueryUtil();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List reginfo = null;
        List watchinfo = null;
        List uinfo = null;
        List coninfo = null;

        // //////////////////////查询所有区域----省级用户
        String region = "select  r.REGIONNAME, r.REGIONID "
                + " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
        // //////////////////////代维
        String con = "select  c.CONTRACTORID, c.CONTRACTORNAME, c.REGIONID "
                + " from  contractorinfo c " + " where c.STATE is null ";
        // //////////////////////外力盯防
        String watch = "select  w.PLACEID, w.PLACENAME, w.REGIONID, w.CONTRACTORID "
                + " from  watchinfo w ";
        // //////////////////////巡检人
        String user = "select p.PATROLID, p.PATROLNAME, p.REGIONID, p.PARENTID "
                + " from patrolmaninfo p " + " where p.STATE is null ";

        // 市移动
        if (userinfo.getDeptype().equals("1")
                && !userinfo.getRegionID().substring(2, 6).equals("0000")) {

            watch += " where w.regionid IN ('" + userinfo.getRegionID() + "') ";
            user += "  and p.regionid IN ('" + userinfo.getRegionID() + "') order by p.PARENTID";
            con += "  and c.regionid IN ('" + userinfo.getRegionID() + "') ";

            watchinfo = query.queryBeans(watch);
            uinfo = query.queryBeans(user);
            coninfo = query.queryBeans(con);

        }
        // 市代维
        if (userinfo.getDeptype().equals("2")
                && !userinfo.getRegionID().substring(2, 6).equals("0000")) {

            watch += " where w.CONTRACTORID IN ('" + userinfo.getDeptID() + "') ";
            user += "  and p.PARENTID in  ('" + userinfo.getDeptID() + "') order by p.PARENTID";

            watchinfo = query.queryBeans(watch);
            uinfo = query.queryBeans(user);

        }
        // 省移动
        if (userinfo.getDeptype().equals("1")
                && userinfo.getRegionID().substring(2, 6).equals("0000")) {
            user += " order by p.PARENTID ";
            reginfo = query.queryBeans(region);
            watchinfo = query.queryBeans(watch);
            uinfo = query.queryBeans(user);
            coninfo = query.queryBeans(con);
        }
        // 省代维
        if (userinfo.getDeptype().equals("2")
                && userinfo.getRegionID().substring(2, 6).equals("0000")) {

            user += " order by p.PARENTID ";
            reginfo = query.queryBeans(region);
            watchinfo = query.queryBeans(watch);
            uinfo = query.queryBeans(user);
            coninfo = query.queryBeans(con);

        }
        request.setAttribute("reginfo", reginfo);
        request.setAttribute("watchinfo", watchinfo);
        request.setAttribute("coninfo", coninfo);
        request.setAttribute("uinfo", uinfo);
        return mapping.findForward("showquerywatchexe");
    }

    public ActionForward showQueryWatchExeSta(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        QueryUtil query = new QueryUtil();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

        List reginfo = null;
        List watchinfo = null;
        List coninfo = null;
        List uinfo = null;

        // //////////////////////查询所有区域----省级用户
        String region = "select  r.REGIONNAME, r.REGIONID "
                + " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
        // //////////////////////代维
        String con = "select  c.CONTRACTORID, c.CONTRACTORNAME, c.REGIONID "
                + " from  contractorinfo c " + " where c.STATE is null ";

        // //////////////////////外力盯防
        String watch = "select  w.PLACEID, w.PLACENAME, w.REGIONID, w.CONTRACTORID "
                + " from  watchinfo w ";
        // //////////////////////巡检人
        String user = "select p.PATROLID, p.PATROLNAME, p.REGIONID, p.PARENTID "
                + " from patrolmaninfo p " + " where p.STATE is null ";

        // 市移动
        if (userinfo.getDeptype().equals("1")
                && !userinfo.getRegionID().substring(2, 6).equals("0000")) {

            watch += " where w.regionid IN ('" + userinfo.getRegionID() + "') ";
            user += "  and p.regionid IN ('" + userinfo.getRegionID() + "') order by p.PARENTID ";
            con += "  and c.regionid IN ('" + userinfo.getRegionID() + "') ";
            watchinfo = query.queryBeans(watch);
            uinfo = query.queryBeans(user);
            coninfo = query.queryBeans(con);

        }
        // 市代维
        if (userinfo.getDeptype().equals("2")
                && !userinfo.getRegionID().substring(2, 6).equals("0000")) {

            watch += " where w.CONTRACTORID IN ('" + userinfo.getDeptID() + "') ";
            user += "  and p.PARENTID in  ('" + userinfo.getDeptID() + "') order by p.PARENTID ";
            con += "  and c.regionid IN ('" + userinfo.getRegionID() + "') and c.CONTRACTORID = ('"
                    + userinfo.getDeptID() + "')";
            watchinfo = query.queryBeans(watch);
            uinfo = query.queryBeans(user);
        }
        // 省移动
        if (userinfo.getDeptype().equals("1")
                && userinfo.getRegionID().substring(2, 6).equals("0000")) {
            user += " order by p.PARENTID ";
            coninfo = query.queryBeans(con);
            reginfo = query.queryBeans(region);
            watchinfo = query.queryBeans(watch);
            uinfo = query.queryBeans(user);
            // logger.info("region:" + region);
            // logger.info("con:" + con);
            // logger.info("watch:" + watch);
            // logger.info("user:" + user);
        }
        // 省代维
        if (userinfo.getDeptype().equals("2")
                && userinfo.getRegionID().substring(2, 6).equals("0000")) {
            user += " order by p.PARENTID ";
            coninfo = query.queryBeans(con);
            reginfo = query.queryBeans(region);
            watchinfo = query.queryBeans(watch);
            uinfo = query.queryBeans(user);

        }

        request.setAttribute("reginfo", reginfo);
        request.setAttribute("watchinfo", watchinfo);
        request.setAttribute("coninfo", coninfo);
        request.setAttribute("uinfo", uinfo);
        return mapping.findForward("showquerywatchexesta");
    }
}
