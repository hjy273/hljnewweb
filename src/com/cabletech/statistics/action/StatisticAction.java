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
     * <br>����:ͳ��Ѳ����ϸ��ʾ
     * <br>����:
     * <br>����:
     */
    public ActionForward showSearchPatrolDetail( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
    	List lRegion = null;
//    	 ��ѯ��������----ʡ���û�
		String sql1 = "SELECT r.REGIONNAME, r.REGIONID FROM region r " +
				"where r.STATE is null and substr(r.REGIONID,3,4) != '1111' " +
				"CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"+userinfo.getRegionid()+"'";
		try{
		QueryUtil _qu = new QueryUtil();
		//if (!userinfo.getType().equals("22")) {
			lRegion = _qu.queryBeans(sql1);
		//}
		}catch(Exception e){
			logger.error("��ѯ�������");
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
     * <br>����:ͳ��©����ϸ��ʾ
     * <br>����:
     * <br>����:
     */
    public ActionForward showSearchPatrolloss( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
    	List lRegion = null;
//    	 ��ѯ��������----ʡ���û�
		String sql1 = "SELECT r.REGIONNAME, r.REGIONID FROM region r " +
				"where r.STATE is null and substr(r.REGIONID,3,4) != '1111' " +
				"CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"+userinfo.getRegionid()+"'";
		try{
		QueryUtil _qu = new QueryUtil();
		//if (!userinfo.getType().equals("22")) {
			lRegion = _qu.queryBeans(sql1);
		//}
		}catch(Exception e){
			logger.error("��ѯ�������");
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
     * ��ʾ����ͨ���ʲ�ѯ��
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

		// ��ѯ��������----ʡ���û�
		String sql1 = "select  r.REGIONNAME, r.REGIONID "
				+ " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
		// ��ά
		String sql2 = "select  c.CONTRACTORID, c.CONTRACTORNAME, c.REGIONID "
				+ " from  contractorinfo c " + " where c.STATE is null ";
		// Ѳ����
		//String sql3 = "select p.PATROLID, p.PATROLNAME, p.REGIONID, p.PARENTID "
		//		+ " from patrolmaninfo p " + " where p.STATE is null ";
		try {
			QueryUtil _qu = new QueryUtil();
			// ���ƶ�
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
			// �д�ά
			if (userinfo.getType().equals("22")) {
				// sql1 += " and r.regionid='"+ userinfo.getRegionid() +"'";
				sql2 += " and c.CONTRACTORID = '" + userinfo.getDeptID() + "'";
				//sql3 += " and p.regionid IN ('" + userinfo.getRegionID()
				//		+ "') and p.PARENTID = '" + userinfo.getDeptID()
				//		+ "' order by p.PARENTID,p.PATROLNAME ";
				//lgroup = _qu.queryBeans(sql3);
			}
			// ʡ�ƶ�
			if (userinfo.getType().equals("11")) {
				//sql3 += " order by p.PARENTID,p.PATROLNAME ";
				lRegion = _qu.queryBeans(sql1);
				lContractor = _qu.queryBeans(sql2);
				//lgroup = _qu.queryBeans(sql3);
			}
			// ʡ��ά
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
     * <br>����:ͳ��Ѳ������ʾ
     * <br>����:
     * <br>����:
     */
    public ActionForward showSearchPatrolRate( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
    	List lRegion = null;
//    	 ��ѯ��������----ʡ���û�
		String sql1 = "SELECT r.REGIONNAME, r.REGIONID FROM region r " +
				"where r.STATE is null and substr(r.REGIONID,3,4) != '1111' " +
				"CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"+userinfo.getRegionid()+"'";
		try{
		QueryUtil _qu = new QueryUtil();
		//if (!userinfo.getType().equals("22")) {
			lRegion = _qu.queryBeans(sql1);
		//}
		}catch(Exception e){
			logger.error("��ѯ�������");
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
     * <br>����:ͳ��Ѳ������ʾ
     * <br>����:
     * <br>����:
     */
    public ActionForward showNewQueryPlanForm( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
    	List lRegion = null;
//    	 ��ѯ��������----ʡ���û�
		String sql1 = "SELECT r.REGIONNAME, r.REGIONID FROM region r " +
				"where r.STATE is null and substr(r.REGIONID,3,4) != '1111' " +
				"CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"+userinfo.getRegionid()+"'";
		try{
		QueryUtil _qu = new QueryUtil();
		//if (!userinfo.getType().equals("22")) {
			lRegion = _qu.queryBeans(sql1);
		//}
		}catch(Exception e){
			logger.error("��ѯ�������");
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
     * ȡ��©����ϸ
     */
    public ActionForward getLossDetail( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        QueryCondition condition = new QueryCondition();
        QueryConditionBean conditionBean = ( QueryConditionBean )form;
        BeanUtil.objectCopy( conditionBean, condition );
        condition.setRegionid( super.getLoginUserInfo( request ).getRegionid() ); //����ID

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
     * ��ѯѲ����
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
        //ȡ�ò�ѯ���͵�����.��:����ά��λ��ѯ,���Ǵ�ά��λ����
        String staObjectName = request.getParameter( "staObj" );

        QueryCondition condition = new QueryCondition();
        QueryConditionBean conditionBean = ( QueryConditionBean )form;
        //���ݲ�ͬ�Ĳ�ѯ�������ò�ѯ����
        if( conditionBean.getQueryby().equals( "patrol" ) ){ //��Ѳ��ά�����ѯ
            conditionBean.setDeptid( null );
            conditionBean.setLineid( null );
        }
        else{
            if( conditionBean.getQueryby().equals( "contractor" ) ){ //����ά��λ��ѯ
                conditionBean.setPatrolid( null );
                conditionBean.setLineid( null );
            }
            else{ //��Ѳ����·��ѯ
                conditionBean.setPatrolid( null );
                conditionBean.setDeptid( null );
            }
        }

        BeanUtil.objectCopy( conditionBean, condition );
        condition.setRegionid( super.getLoginUserInfo( request ).getRegionid() ); //����ID

        if( conditionBean.getCyctype().equals( "month" ) ){
            DateTools datetools = new DateTools();
            datetools.getMonthBeginAndEnd( condition, conditionBean.getYear(), conditionBean.getMonth() );
        }
        //����ѯ�������Ѳ����
        NewPlanRateQueryDao newDao = new NewPlanRateQueryDao();
        PatrolRate patrolrate = newDao.getPatrolRete( condition );
        //���ô���ҳ��Ļ�����Ϣ
        patrolrate.setStaobject( staObjectName );
        if( conditionBean.getCyctype().equals( "month" ) ){
            patrolrate.setFormtime( conditionBean.getYear() + " �� " + conditionBean.getMonth() + " �� " );
            patrolrate.setFormname( "Ѳ������ͳ�Ʊ���" );
        }
        else{
            patrolrate.setFormtime( conditionBean.getBegindate() + " -- " + conditionBean.getEnddate() );
            patrolrate.setFormname( "Ѳ����ͳ�Ʊ���" );
        }

        if( conditionBean.getQueryby().equals( "patrol" ) ){
            if( ( ( String )request.getSession().getAttribute( "PMType" ) ).equals( "group" ) ){
                patrolrate.setStatype( "Ѳ��ά����" );
            }
            else{
                patrolrate.setStatype( "Ѳ��ά����" );
            }
        }
        else{
            if( conditionBean.getQueryby().equals( "contractor" ) ){
                patrolrate.setStatype( "��ά��λ" );
            }
            else{
                patrolrate.setStatype( "Ѳ����·" );
            }
        }

        request.getSession().setAttribute( "QueryResult", patrolrate );
        return mapping.findForward( "DisplayPatrolRate" );

    }


    /**
     * ȡ��Ѳ����ͳ�Ʊ���
     */
    public ActionForward getStatisticForm( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        QueryConditionBean conBean = ( QueryConditionBean )form;
        conBean.setRegionid( super.getLoginUserInfo( request ).getRegionid() );

        String staObjectName = request.getParameter( "staObj" ); //����
        if( conBean.getQueryby().equals( "patrol" ) ){ //��ά�����ѯ
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
            condition.setRegionid( regionid ); //����ID

            String monthPlanId = regionid + conBean.getDeptid() + conBean.getYear() + conBean.getMonth();
            //monthPlanId = regionid + conBean.getYear() + conBean.getMonth();
            if( !this.planIsExist( monthPlanId ) ){
                monthPlanId = regionid + conBean.getYear() + conBean.getMonth();
            }
            DateTools datetools = new DateTools();
            datetools.getMonthBeginAndEnd( condition, conBean.getYear(),
                conBean.getMonth() );

            //PatrolRate patrolrate = super.getService().getPatrolReteAsObj( condition);//���Ѳ����
            NewPlanRateQueryDao newDao = new NewPlanRateQueryDao();
            PatrolRate patrolrate = newDao.getPatrolRete( condition );

            patrolrate.setStaobject( staObjectName );

            patrolrate.setFormtime( conBean.getYear() + " �� " +
                conBean.getMonth() + " �� " );
            patrolrate.setFormname( "��ά��λѲ������ͳ�Ʊ���" );

            patrolrate.setStatype( "��ά��λ" );
            loadMonthPlan( request, monthPlanId );
            request.getSession().setAttribute( "QueryResult", patrolrate );
            return mapping.findForward( "DisplayContractorPlanForm" );
        }
    }


    /**
     * ͳ��Ѳ����
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
     * Ѳ����ϸ��ѯ
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
        data.setRegionid( super.getLoginUserInfo( request ).getRegionid() ); //����ID

        request.getSession().removeAttribute( "QueryResult" );
        //List list = super.getService().GetPatrolDetailReport(data);
        List list = statdao.queryPatrolDetail( data );
        request.getSession().setAttribute( "QueryResult", list );
        bean.setQueryby( "ByDepart" );
        this.setPageReset(request);
        return mapping.findForward( "DisplayPatrolDetail" );

    }


    /**
     * ͳ�ƴ�ά��λ�¼ƻ���Ѳ������������
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
     * �����ƻ�ͨ���ʱ���
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
        if( planIsExist( monthPlanId ) ){ //����ƻ�����,ȡ�üƻ���Ϣ

            YearMonthPlan data = basicPlanService.loadymPlan( monthPlanId );

            if( data != null && data.getId().length() > 0 ){

                YearMonthPlanBean bean = new YearMonthPlanBean();
                BeanUtil.objectCopy( data, bean );

                if( bean.getIfinnercheck().equals( "2" ) ){
                    bean.setIfinnercheck( "������" );
                }
                else{
                    if( bean.getIfinnercheck().equals( "3" ) ){
                        bean.setIfinnercheck( "δͨ������" );
                    }
                    else{
                        bean.setIfinnercheck( "ͨ������" );
                    }
                }

                if( bean.getMonth() != null && bean.getMonth().length() > 0 ){
                    bean.setMonth( bean.getMonth() + " ��" );
                }
                else{
                    bean.setMonth( " " );
                }

                if( bean.getStatus() != null && bean.getStatus().equals( "1" ) ){
                    bean.setStatus( "ͨ������" );
                }
                else{
                    if( bean.getStatus() != null && bean.getStatus().equals( "-1" ) ){
                        bean.setStatus( "δͨ������" );
                    }
                    else{
                        bean.setStatus( "������" );
                        bean.setApprovedate( " - " );
                        bean.setApprover( " - " );
                    }
                }
                Vector taskListVct = basicPlanService.getTasklistByPlanID( bean.getId(),null); //�������null,��ʵ�÷�����������
                request.getSession().setAttribute( "tasklist", taskListVct );
                request.getSession().setAttribute( "YearMonthPlanBean", bean );
            }
            else{
                //System.out.println( "YEARMONTH PLAN IS NOT FOUND!!!!!!" );
            }
        }
        else{ //������
            //System.out.println( "YEARMONTH PLAN IS NOT FOUND!!" );
            request.getSession().removeAttribute( "tasklist" );
            request.getSession().removeAttribute( "YearMonthPlanBean" );
        }
    }


    /**
     * ����:����ָ�������¼ƻ��Ƿ����
     * ����:���¼ƻ���idֵ
     * ����:���ڷ��� true �����ڷ��� false
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
            //System.out.println( "����ָ�������¼ƻ��Ƿ�����쳣:" + e.getMessage() );
            return false;
        }

    }


    /**
     * ������������
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
     * ������������
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
     * �¼ƻ������Ӧ����ͼ
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
