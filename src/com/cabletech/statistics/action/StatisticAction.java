package com.cabletech.statistics.action;

import java.util.*;

import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.baseinfo.services.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.web.*;
import com.cabletech.planinfo.beans.*;
import com.cabletech.planinfo.domainobjects.*;
import com.cabletech.planinfo.services.*;
import com.cabletech.statistics.beans.*;
import com.cabletech.statistics.dao.*;
import com.cabletech.statistics.domainobjects.*;
import com.cabletech.statistics.utils.*;

public class StatisticAction extends StatisticsBaseDispatchAction{
    private StatDao statdao = new StatDao();
    private Logger logger = Logger.getLogger("StatisticAction");
    public StatisticAction(){
    }


    /**
     * <br>功能:统计巡检明细显示
     * <br>参数:
     * <br>返回:
     */
    public ActionForward showSearchPatrolDetail( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
    	List lRegion = null;
//    	 查询所有区域----省级用户
		String sql1 = "SELECT r.REGIONNAME, r.REGIONID FROM region r " +
				"where r.STATE is null and substr(r.REGIONID,3,4) != '1111' " +
				"CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"+userinfo.getRegionid()+"'";
		try{
		QueryUtil _qu = new QueryUtil();
		//if (!userinfo.getType().equals("22")) {
			lRegion = _qu.queryBeans(sql1);
		//}
		}catch(Exception e){
			logger.error("查询区域错误");
		}

		request.setAttribute("lRegion",lRegion);
        List lcon = statdao.getContracorInfo( request );
        List lsubline = statdao.getSublineInfo( request ,"Detail");
        List lpatrol = statdao.getPatrolManInfo( request );
        request.setAttribute( "lCon", lcon );
        request.setAttribute( "lsubline", lsubline );
        request.setAttribute( "lpatrol", lpatrol );
        return mapping.findForward( "showSearchPatrolDetail" );
    }


    /**
     * <br>功能:统计漏检明细显示
     * <br>参数:
     * <br>返回:
     */
    public ActionForward showSearchPatrolloss( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
    	List lRegion = null;
//    	 查询所有区域----省级用户
		String sql1 = "SELECT r.REGIONNAME, r.REGIONID FROM region r " +
				"where r.STATE is null and substr(r.REGIONID,3,4) != '1111' " +
				"CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"+userinfo.getRegionid()+"'";
		try{
		QueryUtil _qu = new QueryUtil();
		//if (!userinfo.getType().equals("22")) {
			lRegion = _qu.queryBeans(sql1);
		//}
		}catch(Exception e){
			logger.error("查询区域错误");
		}

		request.setAttribute("lRegion",lRegion);
        List lcon = statdao.getContracorInfo( request );
        List lsubline = statdao.getSublineInfo( request ,"loss");
        List lLine = statdao.getLineInfo( request );
        List lpatrol =statdao.getPatrolManInfo(request);
        request.setAttribute( "lCon", lcon );
        request.setAttribute( "lsubline", lsubline );
        request.setAttribute( "lLine", lLine );
        request.setAttribute("lpatrol",lpatrol);
        return mapping.findForward( "showSearchPatrolloss" );
    }
    /**
     * 显示审批通过率查询表单
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward showSearchApproveRate(ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		List lRegion = null;
		List lContractor = null;
		//List lgroup = null;

		// 查询所有区域----省级用户
		String sql1 = "select  r.REGIONNAME, r.REGIONID "
				+ " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
		// 代维
		String sql2 = "select  c.CONTRACTORID, c.CONTRACTORNAME, c.REGIONID "
				+ " from  contractorinfo c " + " where c.STATE is null ";
		// 巡检人
		//String sql3 = "select p.PATROLID, p.PATROLNAME, p.REGIONID, p.PARENTID "
		//		+ " from patrolmaninfo p " + " where p.STATE is null ";
		try {
			QueryUtil _qu = new QueryUtil();
			// 市移动
			if (userinfo.getType().equals("12")) {
				sql1 += " and r.regionid in ('" + userinfo.getRegionid() + "')";
				sql2 += " and c.regionid IN ('" + userinfo.getRegionID()
						+ "') ";
				//sql3 += " and p.regionid IN ('" + userinfo.getRegionID()
				//		+ "') order by p.PARENTID,p.PATROLNAME ";
				lRegion = _qu.queryBeans(sql1);
				lContractor = _qu.queryBeans(sql2);
				//lgroup = _qu.queryBeans(sql3);
			}
			// 市代维
			if (userinfo.getType().equals("22")) {
				// sql1 += " and r.regionid='"+ userinfo.getRegionid() +"'";
				sql2 += " and c.CONTRACTORID = '" + userinfo.getDeptID() + "'";
				//sql3 += " and p.regionid IN ('" + userinfo.getRegionID()
				//		+ "') and p.PARENTID = '" + userinfo.getDeptID()
				//		+ "' order by p.PARENTID,p.PATROLNAME ";
				//lgroup = _qu.queryBeans(sql3);
			}
			// 省移动
			if (userinfo.getType().equals("11")) {
				//sql3 += " order by p.PARENTID,p.PATROLNAME ";
				lRegion = _qu.queryBeans(sql1);
				lContractor = _qu.queryBeans(sql2);
				//lgroup = _qu.queryBeans(sql3);
			}
			// 省代维
			if (userinfo.getType().equals("21")) {
				sql2 += " and c.parentcontractorid='" + userinfo.getDeptID()
						+ "'";
				//sql3 += " order by p.PARENTID,p.PATROLNAME ";
				lRegion = _qu.queryBeans(sql1);
				lContractor = _qu.queryBeans(sql2);
				//lgroup = _qu.queryBeans(sql3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("sql" + sql1);
		request.setAttribute("lRegion", lRegion);
		request.setAttribute("lContractor", lContractor);
		//request.setAttribute("patrolgroup", lgroup);
		return mapping.findForward("showSearchApproveRate");
    }

    /**
     * <br>功能:统计巡检率显示
     * <br>参数:
     * <br>返回:
     */
    public ActionForward showSearchPatrolRate( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
    	List lRegion = null;
//    	 查询所有区域----省级用户
		String sql1 = "SELECT r.REGIONNAME, r.REGIONID FROM region r " +
				"where r.STATE is null and substr(r.REGIONID,3,4) != '1111' " +
				"CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"+userinfo.getRegionid()+"'";
		try{
		QueryUtil _qu = new QueryUtil();
		//if (!userinfo.getType().equals("22")) {
			lRegion = _qu.queryBeans(sql1);
		//}
		}catch(Exception e){
			logger.error("查询区域错误");
		}

        List lcon = statdao.getContracorInfo( request );
        List lLine = statdao.getLineInfo( request );
        List lpatrol = statdao.getPatrolManInfo( request );
        request.setAttribute("lRegion",lRegion);
        request.setAttribute( "lCon", lcon );
        request.setAttribute( "lLine", lLine );
        request.setAttribute( "lpatrol", lpatrol );

        return mapping.findForward( "showSearchPatrolRate" );
    }


    /**
     * <br>功能:统计巡检率显示
     * <br>参数:
     * <br>返回:
     */
    public ActionForward showNewQueryPlanForm( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
    	List lRegion = null;
//    	 查询所有区域----省级用户
		String sql1 = "SELECT r.REGIONNAME, r.REGIONID FROM region r " +
				"where r.STATE is null and substr(r.REGIONID,3,4) != '1111' " +
				"CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"+userinfo.getRegionid()+"'";
		try{
		QueryUtil _qu = new QueryUtil();
		//if (!userinfo.getType().equals("22")) {
			lRegion = _qu.queryBeans(sql1);
		//}
		}catch(Exception e){
			logger.error("查询区域错误");
		}

		request.setAttribute("lRegion",lRegion);
        List lcon = statdao.getContracorInfo( request );
        List lLine = statdao.getLineInfo( request );
        List lpatrol = statdao.getPatrolManInfo( request );
    //    System.out.println( "lSize:" + lpatrol.size() );
        request.setAttribute( "lCon", lcon );
        request.setAttribute( "lLine", lLine );
        request.setAttribute( "lpatrol", lpatrol );

        return mapping.findForward( "showNewQueryPlanForm" );
    }


    /**
     * 取得漏检明细
     */
    public ActionForward getLossDetail( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        QueryCondition condition = new QueryCondition();
        QueryConditionBean conditionBean = ( QueryConditionBean )form;
        BeanUtil.objectCopy( conditionBean, condition );
        condition.setRegionid( super.getLoginUserInfo( request ).getRegionid() ); //区域ID

        List lLoss = null;
        if( conditionBean.getCyctype().equals( "month" ) ){
            DateTools datetools = new DateTools();
            datetools.getMonthBeginAndEnd( condition, conditionBean.getYear(),
                conditionBean.getMonth() );
            String begindate = conditionBean.getYear() + "/" + conditionBean.getMonth() + "/1";
            String enddate = DateTools.getLastOfMonth( conditionBean.getYear(), conditionBean.getMonth() );
            lLoss = statdao.getLossDetail( conditionBean.getPatrolid(), conditionBean.getDeptid(), begindate, enddate,
                    conditionBean.getSublineid(), conditionBean.getLineid() );
        }
        else{
            lLoss = statdao.getLossDetail( conditionBean.getPatrolid(), conditionBean.getDeptid(),
                    conditionBean.getBegindate(), conditionBean.getEnddate(), conditionBean.getSublineid(),
                    conditionBean.getLineid() );
        }

        request.getSession().removeAttribute( "QueryResult" );
        request.getSession().setAttribute( "QueryResult", lLoss );
        conditionBean.setQueryby( "ByDepart" );
        return mapping.findForward( "DisplayLossDetail" );

    }


    /**
     * 查询巡检率
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */

    public ActionForward getPatrolRate( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        //取得查询类型的名称.如:按代维单位查询,则是代维单位名称
        String staObjectName = request.getParameter( "staObj" );

        QueryCondition condition = new QueryCondition();
        QueryConditionBean conditionBean = ( QueryConditionBean )form;
        //根据不同的查询类型设置查询条件
        if( conditionBean.getQueryby().equals( "patrol" ) ){ //按巡检维护组查询
            conditionBean.setDeptid( null );
            conditionBean.setLineid( null );
        }
        else{
            if( conditionBean.getQueryby().equals( "contractor" ) ){ //按代维单位查询
                conditionBean.setPatrolid( null );
                conditionBean.setLineid( null );
            }
            else{ //按巡检线路查询
                conditionBean.setPatrolid( null );
                conditionBean.setDeptid( null );
            }
        }

        BeanUtil.objectCopy( conditionBean, condition );
        condition.setRegionid( super.getLoginUserInfo( request ).getRegionid() ); //区域ID

        if( conditionBean.getCyctype().equals( "month" ) ){
            DateTools datetools = new DateTools();
            datetools.getMonthBeginAndEnd( condition, conditionBean.getYear(), conditionBean.getMonth() );
        }
        //按查询条件获得巡检率
        NewPlanRateQueryDao newDao = new NewPlanRateQueryDao();
        PatrolRate patrolrate = newDao.getPatrolRete( condition );
        //设置传回页面的基本信息
        patrolrate.setStaobject( staObjectName );
        if( conditionBean.getCyctype().equals( "month" ) ){
            patrolrate.setFormtime( conditionBean.getYear() + " 年 " + conditionBean.getMonth() + " 月 " );
            patrolrate.setFormname( "巡检率月统计报表" );
        }
        else{
            patrolrate.setFormtime( conditionBean.getBegindate() + " -- " + conditionBean.getEnddate() );
            patrolrate.setFormname( "巡检率统计报表" );
        }

        if( conditionBean.getQueryby().equals( "patrol" ) ){
            if( ( ( String )request.getSession().getAttribute( "PMType" ) ).equals( "group" ) ){
                patrolrate.setStatype( "巡检维护组" );
            }
            else{
                patrolrate.setStatype( "巡检维护人" );
            }
        }
        else{
            if( conditionBean.getQueryby().equals( "contractor" ) ){
                patrolrate.setStatype( "代维单位" );
            }
            else{
                patrolrate.setStatype( "巡检线路" );
            }
        }

        request.getSession().setAttribute( "QueryResult", patrolrate );
        return mapping.findForward( "DisplayPatrolRate" );

    }


    /**
     * 取得巡检率统计报表
     */
    public ActionForward getStatisticForm( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        QueryConditionBean conBean = ( QueryConditionBean )form;
        conBean.setRegionid( super.getLoginUserInfo( request ).getRegionid() );

        String staObjectName = request.getParameter( "staObj" ); //名称
        if( conBean.getQueryby().equals( "patrol" ) ){ //按维护组查询
            conBean.setDeptid( null );
            //PlanStatisticForm planform = super.getService().getPlanStatistic( conBean);
            NewPlanStatDao sdao = new NewPlanStatDao();
            PlanStatisticForm planform = sdao.getPlanStatistic( conBean );
            request.getSession().setAttribute( "QueryResult", planform );
            return mapping.findForward( "getPlanForm" );

        }
        else{
            conBean.setPatrolid( null );
            QueryCondition condition = new QueryCondition();
            BeanUtil.objectCopy( conBean, condition );
            BaseInfoService bService = new BaseInfoService();
            Contractor contractor = bService.loadContractor( conBean.getDeptid() );
            String regionid = contractor.getRegionid();
            //String regionid = super.getLoginUserInfo(request).getRegionid();
            condition.setRegionid( regionid ); //区域ID

            String monthPlanId = regionid + conBean.getDeptid() + conBean.getYear() + conBean.getMonth();
            //monthPlanId = regionid + conBean.getYear() + conBean.getMonth();
            if( !this.planIsExist( monthPlanId ) ){
                monthPlanId = regionid + conBean.getYear() + conBean.getMonth();
            }
            DateTools datetools = new DateTools();
            datetools.getMonthBeginAndEnd( condition, conBean.getYear(),
                conBean.getMonth() );

            //PatrolRate patrolrate = super.getService().getPatrolReteAsObj( condition);//获得巡检率
            NewPlanRateQueryDao newDao = new NewPlanRateQueryDao();
            PatrolRate patrolrate = newDao.getPatrolRete( condition );

            patrolrate.setStaobject( staObjectName );

            patrolrate.setFormtime( conBean.getYear() + " 年 " +
                conBean.getMonth() + " 月 " );
            patrolrate.setFormname( "代维单位巡检率月统计报表" );

            patrolrate.setStatype( "代维单位" );
            loadMonthPlan( request, monthPlanId );
            request.getSession().setAttribute( "QueryResult", patrolrate );
            return mapping.findForward( "DisplayContractorPlanForm" );
        }
    }


    /**
     * 统计巡检率
     */
    public ActionForward ReportPatrolRateWithSession( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        PatrolRate patrolrate = ( PatrolRate )request.getSession().getAttribute(
                                "QueryResult" );
        super.getService().ExportPatrolRate( patrolrate, response );
        return null;

    }


    /**
     * 巡检明细查询
     */
    public ActionForward getPatrolDetail( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        QueryConditionBean bean = ( QueryConditionBean )form;
        //System.out.println( "Query By : " + bean.getQueryby() );

        QueryCondition data = new QueryCondition();

        BeanUtil.objectCopy( bean, data );
        data.setRegionid( super.getLoginUserInfo( request ).getRegionid() ); //区域ID

        request.getSession().removeAttribute( "QueryResult" );
        //List list = super.getService().GetPatrolDetailReport(data);
        List list = statdao.queryPatrolDetail( data );
        request.getSession().setAttribute( "QueryResult", list );
        bean.setQueryby( "ByDepart" );
        this.setPageReset(request);
        return mapping.findForward( "DisplayPatrolDetail" );

    }


    /**
     * 统计代维单位月计划与巡检率完整报表
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward ExportContractorPlanForm( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        PatrolRate patrolrate = ( PatrolRate )request.getSession().getAttribute(
                                "QueryResult" );
        YearMonthPlanBean planbean = ( YearMonthPlanBean )request.getSession().
                                     getAttribute( "YearMonthPlanBean" );
        Vector taskVct = ( Vector )request.getSession().getAttribute( "tasklist" );

        super.getService().ExportContractorPlanForm( patrolrate, planbean,
            taskVct, response );

        return null;

    }


    /**
     * 导出计划通过率报表
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward ExportPlanAppRate( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        ApproveRateBean appRate = ( ApproveRateBean )request.getSession().
                                  getAttribute( "queryresult" );

        super.getService().ExportPlanAppRate( appRate, response );
        return null;

    }


    public void loadMonthPlan( HttpServletRequest request, String monthPlanId ) throws
        ClientException, Exception{

        PlanInfoService basicPlanService = new PlanInfoService();
        if( planIsExist( monthPlanId ) ){ //如果计划存在,取得计划信息

            YearMonthPlan data = basicPlanService.loadymPlan( monthPlanId );

            if( data != null && data.getId().length() > 0 ){

                YearMonthPlanBean bean = new YearMonthPlanBean();
                BeanUtil.objectCopy( data, bean );

                if( bean.getIfinnercheck().equals( "2" ) ){
                    bean.setIfinnercheck( "待审批" );
                }
                else{
                    if( bean.getIfinnercheck().equals( "3" ) ){
                        bean.setIfinnercheck( "未通过审批" );
                    }
                    else{
                        bean.setIfinnercheck( "通过审批" );
                    }
                }

                if( bean.getMonth() != null && bean.getMonth().length() > 0 ){
                    bean.setMonth( bean.getMonth() + " 月" );
                }
                else{
                    bean.setMonth( " " );
                }

                if( bean.getStatus() != null && bean.getStatus().equals( "1" ) ){
                    bean.setStatus( "通过审批" );
                }
                else{
                    if( bean.getStatus() != null && bean.getStatus().equals( "-1" ) ){
                        bean.setStatus( "未通过审批" );
                    }
                    else{
                        bean.setStatus( "待审批" );
                        bean.setApprovedate( " - " );
                        bean.setApprover( " - " );
                    }
                }
                Vector taskListVct = basicPlanService.getTasklistByPlanID( bean.getId(),null); //加入参数null,其实该方法早已作废
                request.getSession().setAttribute( "tasklist", taskListVct );
                request.getSession().setAttribute( "YearMonthPlanBean", bean );
            }
            else{
                //System.out.println( "YEARMONTH PLAN IS NOT FOUND!!!!!!" );
            }
        }
        else{ //不存在
            //System.out.println( "YEARMONTH PLAN IS NOT FOUND!!" );
            request.getSession().removeAttribute( "tasklist" );
            request.getSession().removeAttribute( "YearMonthPlanBean" );
        }
    }


    /**
     * 功能:检验指定的年月计划是否存在
     * 参数:年月计划的id值
     * 返回:存在返回 true 不存在返回 false
     */
    public boolean planIsExist( String yearmonthid ){
        String sql = " select count(*) from yearmonthplan where id='" + yearmonthid + "'";
        String[][] str;
        try{

            QueryUtil query = new QueryUtil();
            str = query.executeQueryGetArray( sql, "" );
            if( str[0][0].equals( "0" ) ){
                return false;
            }
            else{
                return true;
            }
        }
        catch( Exception e ){
            //System.out.println( "检验指定的年月计划是否存在异常:" + e.getMessage() );
            return false;
        }

    }


    /**
     * 导出完整报表
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward ExportPlanFormWithCon( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        QueryConditionBean conBean = ( QueryConditionBean )form;
        conBean.setRegionid( super.getLoginUserInfo( request ).getRegionid() );

        PlanStatisticForm planform = super.getService().getPlanStatistic(
                                     conBean );

        super.getService().ExportPlanForm( planform, response,request );

        return null;

    }


    /**
     * 导出完整报表
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward ExportPlanFormWithSession( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        PlanStatisticForm planform = ( PlanStatisticForm )request.getSession().
                                     getAttribute( "QueryResult" );

        super.getService().ExportPlanForm( planform, response,request );

        return null;

    }


    /**
     * 月计划任务对应甘特图
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward getMonthPlanTaskForm( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        QueryConditionBean conBean = ( QueryConditionBean )form;
        conBean.setRegionid( super.getLoginUserInfo( request ).getRegionid() );

        Hashtable monthPlanTaskHt = super.getService().getMonthTaskStaVct(
                                    conBean );
        request.setAttribute( "QueryResult", monthPlanTaskHt );

        return mapping.findForward( "getMonthPlanTaskForm" );

    }
}
