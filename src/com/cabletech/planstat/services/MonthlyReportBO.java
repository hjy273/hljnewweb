package com.cabletech.planstat.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.planstat.beans.MonthlyReportBean;

import com.cabletech.planstat.dao.MonthlyReportDAOImpl;

public class MonthlyReportBO extends BaseBisinessObject{
	Logger logger = Logger.getLogger( this.getClass().getName() );
    private String sql = "";
    private MonthlyReportDAOImpl monthlyReportDAOImpl = null;
    public MonthlyReportBO(){
    	monthlyReportDAOImpl = new MonthlyReportDAOImpl();
    }
    
    //�õ�Ѳ��ά����/��Ա�±���
    public List getPatrolmanMonthlyReport(MonthlyReportBean bean,UserInfo userinfo){
    	String theStartDate =getFirst2Month(bean);
    	//String theEndDate = getLast2Month(bean);
    	if( userinfo.getType().equals( "22" ) ){
	        sql = "SELECT p.patrolname, rm.pdfurl, rm.excelurl " +
				  "FROM REPORT_MONTHLY rm, PATROLMANINFO p " +
				  "WHERE rm.objectid = p.patrolid " +
				    "AND p.parentid ='" + userinfo.getDeptID() + "' " +
				    "AND rm.TYPE = '1' " +
				    "AND (stateddate >= TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd') " +    
				    //"AND stateddate < TO_DATE ('" + theEndDate + "', 'yyyy-mm-dd')) " +
				    "AND stateddate <= last_day(TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd'))) " +   
				  "ORDER BY p.patrolname";
    	}
        logger.info( "�õ�Ѳ��ά����/��Ա�±���SQL:" + sql );
        List list = monthlyReportDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }

    //�õ���ά��˾�±���
    public List getConMonthlyReport(MonthlyReportBean bean,UserInfo userinfo){
    	String theStartDate =getFirst2Month(bean);
    	//String theEndDate = getLast2Month(bean);
    	if( userinfo.getType().equals( "22" ) ){
	        sql = "SELECT distinct c.contractorid,c.contractorname, rm.pdfurl, rm.excelurl " +
				  "FROM REPORT_MONTHLY rm, CONTRACTORINFO c " +
				  "WHERE rm.objectid = c.CONTRACTORID " +
				    "AND c.REGIONID ='" + userinfo.getRegionid() + "' " +
				    "AND c.contractorid = '" + userinfo.getDeptID() + "' " +
				    "AND rm.TYPE = '2' " +
				    "AND (stateddate >= TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd') " +
				    //"AND stateddate < TO_DATE ('" + theEndDate + "', 'yyyy-mm-dd')) " +
				    "AND stateddate <= last_day(TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd'))) " +   
				  "ORDER BY c.contractorname";
    	}else if( userinfo.getType().equals( "12" ) ){
	        sql = "SELECT distinct c.contractorid,c.contractorname, rm.pdfurl, rm.excelurl " +
				  "FROM REPORT_MONTHLY rm, CONTRACTORINFO c " +
				  "WHERE rm.objectid = c.CONTRACTORID " +
				    "AND c.REGIONID ='" + userinfo.getRegionid() + "' " +
				    "AND rm.TYPE = '2' " +
				    "AND (stateddate >= TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd') " +
				    //"AND stateddate < TO_DATE ('" + theEndDate + "', 'yyyy-mm-dd')) " +
				    "AND stateddate <= last_day(TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd'))) " +   
				  "ORDER BY c.contractorname";
    	}
        logger.info( "�õ���ά��˾�±���SQL:" + sql );
        List list = monthlyReportDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
    //�õ����ƶ���˾�±���    
    public List getMobileMonthlyReport(MonthlyReportBean bean,UserInfo userinfo){
    	String theStartDate =getFirst2Month(bean);
    	//String theEndDate = getLast2Month(bean);
    	if( userinfo.getType().equals( "11" ) ){
	        sql = "SELECT r.regionname, rm.pdfurl, rm.excelurl " +
				  "FROM report_monthly rm, region r " +
				  "WHERE rm.objectid = r.REGIONID " +
				    "AND rm.TYPE = '3' " +
				    "AND (stateddate >= TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd') " +
				    //"AND stateddate < TO_DATE ('" + theEndDate + "', 'yyyy-mm-dd')) " +
				    "AND stateddate <= last_day(TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd'))) " +   
				  "ORDER BY r.regionname";
    	}else if( userinfo.getType().equals( "12" ) ){
    		sql = "SELECT r.regionname, rm.pdfurl, rm.excelurl " +
				  "FROM report_monthly rm, region r " +
				  "WHERE rm.objectid = r.REGIONID " +
				    "AND rm.objectid ='" + userinfo.getRegionid() + "' " +
				    "AND rm.TYPE = '3' " +
				    "AND (stateddate >= TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd') " +
				    //"AND stateddate < TO_DATE ('" + theEndDate + "', 'yyyy-mm-dd')) " +
				    "AND stateddate <= last_day(TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd'))) " +   
				  "ORDER BY r.regionname";
    	}
        logger.info( "�õ����ƶ���˾(������)�±���SQL:" + sql );
        List list = monthlyReportDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
    //�õ�ʡ�ƶ���˾�±���(ȫʡ�ۺϣ�    
    public List getPMobileMonthlyReport(MonthlyReportBean bean,UserInfo userinfo){
    	String theStartDate =getFirst2Month(bean);
    	//String theEndDate = getLast2Month(bean);
    	if( userinfo.getType().equals( "11" ) ){
	        sql = "SELECT distinct 'ȫʡ����' as regionname, rm.pdfurl, rm.excelurl " +
				  "FROM report_monthly rm " +
				  "WHERE rm.TYPE = '4' " +
				    "AND (stateddate >= TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd') " +
				    "AND stateddate <= last_day(TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd'))) ";
    	}
        logger.info( "�õ�ʡ�ƶ���˾�±���SQL:" + sql );
        List list = monthlyReportDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
	private String getFirst2Month(MonthlyReportBean bean){
		String theStartDate = bean.getEndYear() + "-" + bean.getEndMonth() + "-1";
		return theStartDate;
    	
	}

	/*
	private String getLast2Month(MonthlyReportBean bean){
    	//����û��ڽ�������ѡ���·�Ϊ12������Ҫ�ı����.
    	if ("12".equals(bean.getEndMonth())){
    		return String.valueOf(Integer.parseInt(bean.getEndYear()) + 1) + "-1-1";
    	}else{
    		return  bean.getEndYear() + "-" + String.valueOf(Integer.parseInt(bean.getEndMonth()) + 1) + "-1"; 
    	}	
	}
	*/
}
