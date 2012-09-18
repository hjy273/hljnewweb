package com.cabletech.planstat.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.planstat.beans.CompAnalysisBean;
import com.cabletech.planstat.dao.CompAnalysisDAOImpl;


public class CompAnalysisBO extends BaseBisinessObject {
	Logger logger = Logger.getLogger( this.getClass().getName() );
    private String sql = null;
    private CompAnalysisDAOImpl compAnalysisDAOImpl = null;
    public CompAnalysisBO(){
    	compAnalysisDAOImpl = new CompAnalysisDAOImpl();
    }
    
    //得到区域列表 
    public List getRegionInfoList(){
        String sql = "select  r.REGIONNAME, r.REGIONID "
                           + " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
        logger.info( "区域SQL:" + sql );
        List list = compAnalysisDAOImpl.getResultList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
    //得到符合条件的市代维单位列表
    public List getContractorInfoList( UserInfo userinfo ){
        if( userinfo.getType().equals( "12" ) ){
            sql = "select con.contractorid,con.contractorname,con.parentcontractorid,con.regionid " +
                  "from contractorinfo con " +
                  "where con.STATE is null " +
                  "and con.regionid='" + userinfo.getRegionID() + "' " +
                  "order by con.contractorname";
        }
        else if( userinfo.getType().equals( "21" ) ){
                sql = "SELECT c.contractorid, c.contractorname,c.parentcontractorid,c.regionid  " +
                      "FROM contractorinfo c " +
                      "where c.regionid not like '%0000' " +
                      "and c.state is null " +
                      "CONNECT BY PRIOR contractorid = parentcontractorid " +
                      "START WITH contractorid in " +
                      " (select u.deptid from userinfo u where u.userid = '" + userinfo.getUserID() + "') " +
                      "order by c.contractorname";
            }
        else if ( userinfo.getType().equals( "11" ) ){
            sql = "select con.contractorid,con.contractorname,con.parentcontractorid,con.regionid " +
                  "from contractorinfo con " +
                  "where con.STATE is null " +
                  "order by con.contractorname";
        }else if( userinfo.getType().equals( "22" ) ){
            sql = "select con.contractorid,con.contractorname,con.parentcontractorid,con.regionid " +
                  "from contractorinfo con " +
                  "where con.STATE is null " +
                  "and con.regionid='" + userinfo.getRegionID() + "' " +
                  "and con.contractorid='" + userinfo.getDeptID() + "' " +
                  "order by con.contractorname";
        }
        logger.info( "市代维单位列表SQL:" + sql );
        List list = compAnalysisDAOImpl.getResultList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
    //  得到符合条件的巡检员列表
    public List getPatrolmanInfoList( UserInfo userinfo){
        if (userinfo.getType().equals("12")){
            sql = "select p.PATROLID, p.PATROLNAME, p.PARENTID,p.Regionid " +
                  " from patrolmaninfo p " +
                  " where p.STATE is null " +
                    " and p.regionid='" + userinfo.getRegionID() + "'" +
                  " order by p.parentid,p.patrolname";
        }else if (userinfo.getType().equals("22")){
            sql = "select p.PATROLID, p.PATROLNAME, p.PARENTID,p.Regionid " +
                  " from patrolmaninfo p " +
                  " where p.STATE is null " +
                    " and p.regionid='" + userinfo.getRegionID() + "'" +
                    " and p.parentid='" + userinfo.getDeptID() + "'" +
                  " order by p.parentid,p.patrolname";
        }else if (userinfo.getType().equals("11")){
        	sql = "select p.PATROLID, p.PATROLNAME, p.PARENTID,p.Regionid " +
	              " from patrolmaninfo p " +
	              " where p.STATE is null " +
	              " order by p.parentid,p.patrolname";
        }else if (userinfo.getType().equals("21")){
        	sql = "select p.PATROLID, p.PATROLNAME, p.PARENTID,p.Regionid " +
                  "from patrolmaninfo p " +
                  "where p.STATE is null " +
        	        "and p.parentid in " +
                         "(SELECT c.contractorid " +
                          "FROM contractorinfo c " +
                          "CONNECT BY PRIOR contractorid = PARENTCONTRACTORID " +
                          "START WITH contractorid = '" + userinfo.getDeptID() + "') " + 
                  "order by p.parentid,p.patrolname";
        }
        logger.info("巡检员列表SQL:" + sql);
        List list = compAnalysisDAOImpl.getResultList(sql);
        return list;
    }
    
    //得到所选巡检员纵向对比分析数据
    public List getPmVResult(CompAnalysisBean bean ){
        String startDate = bean.getEndYear() + "-" + bean.getStartMonth() + "-01";
        String endYearMonth = bean.getEndYear() + "-" + bean.getEndMonth();
    	sql = "select p.executorid,pm.patrolname,to_char(p.factdate, 'mm') startmonth,p.planpoint,p.factpoint " +
    	      "from PLAN_PATROLSTAT p,patrolmaninfo pm " +
    	      "where p.executorid = pm.patrolid and " +
    	            "p.executorid = '" + bean.getPatrolID() + "' and " +
    	            "p.factdate between to_date('" + startDate + "', 'yyyy-mm-dd') and " +
    	            "last_day(to_date('" + endYearMonth + "', 'yyyy-mm')) " +
    	      "order by to_char(p.factdate,'yyyy-mm') ";
    	
        logger.info( "统计巡检员/组选定起止月份内的巡检情况纵向对比分析SQL:" + sql );
        List list = compAnalysisDAOImpl.getResultList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
    //得到所选巡检员横向对比分析数据
    public List getPmHResult(CompAnalysisBean bean ){
    	String theYearMonth = bean.getEndYear() + "-" + bean.getTheMonth();
    	String theDate = theYearMonth + "-01";
        sql = "select p.executorid, pm.patrolname, p.planpoint,p.factpoint " +
              "from PLAN_PATROLSTAT p, patrolmaninfo pm " +
              "where p.executorid = pm.patrolid and " +
                    "p.contractorid = '" + bean.getContractorID() + "' and " +
                    "p.factdate between to_date('" + theDate + "', 'yyyy-mm-dd') and " +
                    "last_day(to_date('" + theYearMonth + "', 'yyyy-mm')) and " +
                    "pm.state is null " +
              "order by factpoint desc";
        logger.info( "统计某代维公司下巡检员/组某一个月巡检情况横向对比分析SQL:" + sql );
        List list = compAnalysisDAOImpl.getResultList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }

    //得到所选代维公司纵向对比分析数据
    public List getComVResult(CompAnalysisBean bean ){
        String startDate = bean.getEndYear() + "-" + bean.getStartMonth() + "-01";
        String endYearMonth = bean.getEndYear() + "-" + bean.getEndMonth();
    	/*sql = "select p.contractorid,con.contractorname,to_char(p.startdate, 'mm') startmonth,sum(p.planpoint) planpoint,sum(p.factpoint) factpoint " +
    	      "from plan_stat p,contractorinfo con " +
    	      "where p.contractorid = con.contractorid and " +
    	            "p.contractorid ='" + bean.getContractorID() + "' and " +
    	            "p.startdate between to_date('" + startDate + "', 'yyyy-mm-dd') and " +
    	            "last_day(to_date('" + endYearMonth + "', 'yyyy-mm')) " +
    	      "group by to_char(p.startdate,'yyyy-mm'),p.contractorid, con.contractorname,to_char(p.startdate, 'mm') " +
    	      "order by to_char(p.startdate,'yyyy-mm') ";*/
        sql = "select p.CDEPTID contractorid,con.contractorname,to_char(p.factdate, 'mm') startmonth,p.planpoint,p.factpoint " +
	      "from plan_cstat p,contractorinfo con " +
	      "where p.CDEPTID = con.contractorid and " +
	            "p.CDEPTID ='" + bean.getContractorID() + "' and " +
	            "p.factdate between to_date('" + startDate + "', 'yyyy-mm-dd') and " +
	            "last_day(to_date('" + endYearMonth + "', 'yyyy-mm')) " +
	      "order by to_char(p.factdate,'yyyy-mm') ";
        logger.info( "统计市代维公司选定起止月份内的巡检情况纵向对比分析SQL:" + sql );
        List list = compAnalysisDAOImpl.getResultList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
    //得到所选代维公司横向对比分析数据
    public List getComHResult(CompAnalysisBean bean,UserInfo userinfo ){
    	String theYearMonth = bean.getEndYear() + "-" + bean.getTheMonth();
    	String theDate = theYearMonth + "-01";
        sql = "select p.CDEPTID AS contractorid,con.contractorname," +
                     "p.planpoint,p.factpoint " +
              "from plan_cstat p, contractorinfo con " +
              "where p.CDEPTID = con.contractorid and ";
        logger.info("in bo,the bean's regionid is:" + bean.getRegionId());
        if (!bean.getRegionId().substring(2,6).equals("0000")){
        	sql += "con.regionid = '" + bean.getRegionId() + "' and ";
        }
        sql += "p.factdate between to_date('" + theDate + "', 'yyyy-mm-dd') and " +
                    "last_day(to_date('" + theYearMonth + "', 'yyyy-mm')) and " +
                    "con.state is null " +
              "order by factpoint desc";
        logger.info( "统计某地市所有市代维公司某一个月巡检情况横向对比分析SQL:" + sql );
        List list = compAnalysisDAOImpl.getResultList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
}
