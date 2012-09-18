package com.cabletech.planstat.services;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.planstat.beans.LogPathBean;
import com.cabletech.planstat.dao.LogPathDAOImpl;

public class LogPathBO extends BaseBisinessObject{
	Logger logger = Logger.getLogger( this.getClass().getName() );
	private String sql = "";
	LogPathDAOImpl logPathDAOImpl = null;
	public LogPathBO(){
	    logPathDAOImpl = new LogPathDAOImpl();
	}	
    
    //得到区域信息
    public List getRegionInfoList(){
        String sql = "select  r.regionid, r.regionname "
                           + " from  region r   where r.STATE is null and substr(r.regionid,3,4) != '1111' ";
        logger.info( "区域SQL:" + sql );
        List list = logPathDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
    //依据选择的区域和类型(只有当类型为"代维公司"时才会如此)装载代维公司列表(AJAX用)
    public List getConByTypeAjax(String RegionID ){
        sql = "select con.contractorid,con.contractorname " +
		      "from contractorinfo con " +
		      "where con.STATE is null " +
		      "and con.REGIONID not like '%0000'";
        //if (!RegionID.equals("")){
        if(!RegionID.substring( 2, 6 ).equals( "0000" )){
        	sql += "and con.regionid = '" + RegionID + "' ";
        }
        sql += "order by con.contractorname";
		logger.info( "市代维单位列表SQL:" + sql );
		ResultSet resultset = logPathDAOImpl.getBackInfoListJDBC( sql );
		if( resultset == null ){
		  logger.info( "resultset is null" );
		}
        try{
            List list = this.resultSetToList( resultset );
            return list;
        }
        catch( SQLException ex ){
            ex.printStackTrace();
            return null;
        }
    }

	public List resultSetToList( ResultSet rs ) throws java.sql.SQLException{
	    if( rs == null )
	        return Collections.EMPTY_LIST;
	    ResultSetMetaData md = rs.getMetaData();
	    int columnCount = md.getColumnCount();
	    List list = new ArrayList();
	    Map rowData;
	    while( rs != null && rs.next() ){
	        rowData = new HashMap( columnCount );
	        for( int i = 1; i <= columnCount; i++ ){
	            rowData.put( md.getColumnName( i ), rs.getString( i ) );
	            //logger.info("the key is: " + md.getColumnName(i) + ",the value is :" + rs.getString(i));
	        }
	        list.add( rowData );
	    }
	    return list;
	}
	
    //得到各个一级模块访问量结果（当类型为“代维”或者“移动”时）,省代维的访问记录屏蔽
    public List getVisitorsTraffic(LogPathBean bean){
    	String sql =  "SELECT m.LABLENAME,s.MAINMENUID,COUNT(s.ID) visittimes ";
    	if (bean.getQueryType().equals("2") && !bean.getConID().equals("0")){
    		sql += "FROM syslogger s,mainmodulemenu m,userinfo u ";
    	}else{
    		sql += "FROM syslogger s,mainmodulemenu m ";
    	}          
	    sql += "WHERE s.MAINMENUID = m.ID " +
				"AND s.visitdate >= TO_DATE ('" + bean.getStartDate() + "', 'yyyy-mm-dd') " +
				"AND s.visitdate < TO_DATE ('" + bean.getEndDate() + "', 'yyyy-mm-dd') + 1 ";
		//if (!bean.getRegionID().equals("")){
	    if(!bean.getRegionID().substring( 2, 6 ).equals( "0000" )){
	        sql += "and s.regionid = '" + bean.getRegionID() + "' ";
	    } 
		if (bean.getQueryType().equals("2") && !bean.getConID().equals("0")){
			sql += "AND s.USERID = u.USERID " +
				   "And s.USERID IN " +
				       "(SELECT u1.userid FROM userinfo u1 WHERE u1.DEPTID='" + bean.getConID() + "' " +
				       "and u1.DEPTYPE ='2' and u1.REGIONID not like '%0000') ";
		}
		if (bean.getQueryType().equals("2")){
			sql += "AND s.usertype = '" + bean.getQueryType() + "' " +
		           "GROUP BY s.mainmenuid,m.lablename " +
		           "ORDER BY visittimes DESC";
		}else{
			sql += "AND s.usertype = '" + bean.getQueryType() + "' " +
			       "GROUP BY s.mainmenuid,m.lablename " +
			       "ORDER BY visittimes DESC";
		}
        logger.info( "得到各个一级模块访问量结果SQL:" + sql );
        List list = logPathDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
    //得到各个一级模块访问量结果(当类型为“不限”时）
    public List getVisitorsTrafficNoType(LogPathBean bean){
    	ResultSet rs = null;
		String sql = "SELECT   m.lablename, s.mainmenuid,mobile.visittimes mvisittimes,con.visittimes cvisittimes " +    
		  	  "FROM syslogger s," +
				       "mainmodulemenu m," +
				       "(SELECT COUNT (s1.ID) visittimes,s1.mainmenuid,m1.lablename  " +
				           "FROM syslogger s1, mainmodulemenu m1 " +
			               "WHERE s1.mainmenuid = m1.ID AND s1.usertype = '1' ";
		//if (!bean.getRegionID().equals("")){
		if(!bean.getRegionID().substring( 2, 6 ).equals( "0000" )){
		  	sql += "and s1.regionid = '" + bean.getRegionID() + "' ";
        }
		sql += "AND s1.visitdate >= TO_DATE ('" + bean.getStartDate() + "', 'yyyy-mm-dd') " +
               "AND s1.visitdate < TO_DATE ('" + bean.getEndDate() + "', 'yyyy-mm-dd') + 1 " +
               "GROUP BY s1.mainmenuid, m1.lablename) mobile," +
               "(SELECT COUNT (s2.ID) visittimes,s2.mainmenuid,m2.lablename " + 
                    "FROM syslogger s2, mainmodulemenu m2 " +
                    "WHERE s2.mainmenuid = m2.ID AND s2.usertype = '2' ";
		//if (!bean.getRegionID().equals("")){
		if(!bean.getRegionID().substring( 2, 6 ).equals( "0000" )){
		  	sql += "and s2.regionid = '" + bean.getRegionID() + "' ";
        }
		sql += "AND s2.visitdate >= TO_DATE ('" + bean.getStartDate() + "', 'yyyy-mm-dd') " +
               "AND s2.visitdate < TO_DATE ('" + bean.getEndDate() + "', 'yyyy-mm-dd') + 1 " +
               "GROUP BY s2.mainmenuid, m2.lablename) con " +		
               "WHERE s.mainmenuid = m.ID " +
                  "AND mobile.mainmenuid(+) = m.ID " +
                  "AND con.mainmenuid(+) = m.ID ";
		//if (!bean.getRegionID().equals("")){
		if(!bean.getRegionID().substring( 2, 6 ).equals( "0000" )){
		  	sql += "and s.regionid = '" + bean.getRegionID() + "' ";
        } 
	    sql += "GROUP BY s.mainmenuid, m.lablename,mobile.visittimes,con.visittimes " +
  	           "ORDER BY mvisittimes  DESC ";
        if (sql != null ||!"".equals(sql)){
        	logger.info( "得到各个一级模块访问量结果SQL:" + sql );
            rs = logPathDAOImpl.getBackInfoListJDBC( sql );
        } 
        if( rs == null ){
            logger.info( "rs is null" );
            return null;
        }
        Map map;
        List list = new ArrayList();
        try {
			while(rs.next()){
				map = new HashMap();
				map.put("lablename", rs.getString(1));
				map.put("mainmenuid", rs.getString(2));
			    map.put("mvisittimes", rs.getString(3));
			    map.put("cvisittimes", rs.getString(4));
			    list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return list;
    }

    
    //得到选定一级模块下的各二级模块访问量结果（当类型为“代维”或者“移动”时）
    public List getSubTraffic(LogPathBean bean,String mainMenuID){   
    	String sql =  "SELECT sub.LABLENAME,s.SUBMENUID,COUNT(s.ID) visittimes  ";
    	if (bean.getQueryType().equals("2") && !bean.getConID().equals("0")){
    		sql += "FROM syslogger s,submenu sub,userinfo u " ;
    	}else{
    		sql += "FROM syslogger s,submenu sub " ;
    	}          
		sql += "WHERE  s.MAINMENUID = sub.PARENTID " +
			        "AND s.SUBMENUID= sub.ID " +
			        "AND s.MAINMENUID='" + mainMenuID + "' " +
					"AND s.visitdate >= TO_DATE ('" + bean.getStartDate() + "', 'yyyy-mm-dd') " +
					"AND s.visitdate < TO_DATE ('" + bean.getEndDate() + "', 'yyyy-mm-dd') + 1 ";
		//if (!bean.getRegionID().equals("")){
		if(!bean.getRegionID().substring( 2, 6 ).equals( "0000" )){
	        	sql += "and s.regionid = '" + bean.getRegionID() + "' ";
	    } 
		if (bean.getQueryType().equals("2") && !bean.getConID().equals("0")){
			sql += "AND s.USERID = u.USERID " +
                   "And s.USERID IN " +
                      "(SELECT u1.userid FROM userinfo u1 WHERE u1.DEPTID='" + bean.getConID() + "' " +
				          "and u1.DEPTYPE ='2' and u1.REGIONID not like '%0000') ";
		}
		if (bean.getQueryType().equals("2")){
			sql += "AND s.usertype = '" + bean.getQueryType() + "' " +
		           "GROUP BY s.SUBMENUID, sub.lablename " +
		           "ORDER BY visittimes DESC";
		}else{
			sql += "AND s.usertype = '" + bean.getQueryType() + "' " +
			       "GROUP BY s.SUBMENUID, sub.lablename " +
			       "ORDER BY visittimes DESC";
		}
        logger.info( "得到选定一级模块下的各二级模块访问量结果SQL:" + sql );
        List list = logPathDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
    //得到选定一级模块下的各二级模块访问量结果(当类型为“不限”时）
    public List getSubTrafficNoType(LogPathBean bean,String mainMenuID){
    	ResultSet rs = null;
		String sql = "SELECT   sub.lablename, s.submenuid, mobile.visittimes mvisittimes,con.visittimes cvisittimes " +    
		  	  "FROM syslogger s,submenu sub," +
		            "(SELECT   COUNT (s1.ID) visittimes, s1.submenuid, sub1.lablename " +
		              "FROM syslogger s1, submenu sub1 " +
		              "WHERE s1.MAINMENUID = sub1.PARENTID " +
		   			     "AND s1.SUBMENUID= sub1.ID AND s1.MAINMENUID='" + mainMenuID + "' " +
		                 "AND s1.usertype = '1' ";
		//if (!bean.getRegionID().equals("")){
		if(!bean.getRegionID().substring( 2, 6 ).equals( "0000" )){
		  	sql += "and s1.regionid = '" + bean.getRegionID() + "' ";
        } 
		sql += "AND s1.visitdate >= TO_DATE ('" + bean.getStartDate() + "', 'yyyy-mm-dd') " +
               "AND s1.visitdate < TO_DATE ('" + bean.getEndDate() + "', 'yyyy-mm-dd') + 1 " +
               "GROUP BY s1.SUBMENUID, sub1.lablename) mobile, " +
               "(SELECT   COUNT (s2.ID) visittimes, s2.submenuid, sub2.lablename " +
                    "FROM syslogger s2, submenu sub2 " +
		            "WHERE s2.MAINMENUID = sub2.PARENTID " +
					   "AND s2.SUBMENUID= sub2.ID AND s2.MAINMENUID='" + mainMenuID + "' " +
		               "AND s2.usertype = '2' " +
		               "AND s2.visitdate >= TO_DATE ('" + bean.getStartDate() + "', 'yyyy-mm-dd') " +
		               "AND s2.visitdate < TO_DATE ('" + bean.getEndDate() + "', 'yyyy-mm-dd') + 1 ";
		//if (!bean.getRegionID().equals("")){
		if(!bean.getRegionID().substring( 2, 6 ).equals( "0000" )){
		  	sql += "and s2.regionid = '" + bean.getRegionID() + "' ";
        }
		sql += "GROUP BY s2.submenuid, sub2.lablename) con " +
		       "WHERE s.mainmenuid = sub.PARENTID " +
				    "AND s.submenuid= sub.ID AND s.MAINMENUID='" + mainMenuID + "' " +
				    "AND mobile.SUBMENUID(+) = sub.ID " +
				    "AND con.SUBMENUID(+) = sub.ID ";
		//if (!bean.getRegionID().equals("")){
		if(!bean.getRegionID().substring( 2, 6 ).equals( "0000" )){
		  	sql += "and s.regionid = '" + bean.getRegionID() + "' ";
        } 
        sql += "GROUP BY s.SUBMENUID, sub.lablename, mobile.visittimes, con.visittimes " +
			   "ORDER BY mvisittimes DESC";			               
        if (sql != null ||!"".equals(sql)){
        	logger.info( "得到选定一级模块下的各二级模块访问量结果SQL:" + sql );
            rs = logPathDAOImpl.getBackInfoListJDBC( sql );
        } 
        if( rs == null ){
            logger.info( "rs is null" );
            return null;
        }
        Map map;
        List list = new ArrayList();
        try {
			while(rs.next()){
				map = new HashMap();
				map.put("lablename", rs.getString(1));
				map.put("submenuid", rs.getString(2));
			    map.put("mvisittimes", rs.getString(3));
			    map.put("cvisittimes", rs.getString(4));
			    list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return list;
    }
    
    //得到选定一级模块(特指动态生成的二级菜单,如"隐患管理"和"实时监控")下的各二级模块访问量结果（当类型为“代维”或者“移动”时）
    public List getSubTrafficSpecial(LogPathBean bean,String mainMenuID){   
    	String sql =  "SELECT   r.REGIONNAME LABLENAME, COUNT (s.ID) visittimes  ";
    	if (bean.getQueryType().equals("2") && !bean.getConID().equals("0")){
    		sql += "FROM syslogger s,region r,userinfo u " ;
    	}else{
    		sql += "FROM syslogger s,region r " ;
    	}          
		sql += "WHERE s.submenuid = r.REGIONID " +
			        "AND s.MAINMENUID='" + mainMenuID + "' " +
					"AND s.visitdate >= TO_DATE ('" + bean.getStartDate() + "', 'yyyy-mm-dd') " +
					"AND s.visitdate < TO_DATE ('" + bean.getEndDate() + "', 'yyyy-mm-dd') + 1 ";
		//if (!bean.getRegionID().equals("")){
		if(!bean.getRegionID().substring( 2, 6 ).equals( "0000" )){
	        	sql += "and s.regionid = '" + bean.getRegionID() + "' ";
	    } 
		if (bean.getQueryType().equals("2") && !bean.getConID().equals("0")){
			sql += "AND s.USERID = u.USERID " +
                   "And s.USERID IN " +
		                "(SELECT u1.userid FROM userinfo u1 WHERE u1.DEPTID='" + bean.getConID() + "' " +
		                      "and u1.DEPTYPE ='2' and u1.REGIONID not like '%0000') ";
		}
		if (bean.getQueryType().equals("2")){
			sql += "AND s.usertype = '" + bean.getQueryType() + "' " +
		           "GROUP BY s.SUBMENUID, r.regionname " +
		           "ORDER BY visittimes DESC";
		}else{
			sql += "AND s.usertype = '" + bean.getQueryType() + "' " +
			       "GROUP BY s.SUBMENUID, r.regionname " +
			       "ORDER BY visittimes DESC";
		}
        logger.info( "得到选定一级模块下的各二级模块访问量结果SQL:" + sql );
        List list = logPathDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
    //得到选定一级模块(特指动态生成的二级菜单,如"隐患管理"和"实时监控")下的各二级模块访问量结果(当类型为“不限”时)
    public List getSubTrafficNoTypeSpecial(LogPathBean bean,String mainMenuID){
    	ResultSet rs = null;
		String sql = "SELECT  r.REGIONNAME LABLENAME,mobile.visittimes mvisittimes,con.visittimes cvisittimes " +    
		  	  "FROM syslogger s,region r," +
		            "(SELECT   COUNT (s1.ID) visittimes,s1.submenuid,r1.regionname " +
		              "FROM syslogger s1, region r1 " +
		              "WHERE s1.SUBMENUID= r1.regionid AND s1.MAINMENUID='" + mainMenuID + "' " +
		                 "AND s1.usertype = '1' ";
		//if (!bean.getRegionID().equals("")){
		if(!bean.getRegionID().substring( 2, 6 ).equals( "0000" )){
		  	sql += "and s1.regionid = '" + bean.getRegionID() + "' ";
        } 
		sql += "AND s1.visitdate >= TO_DATE ('" + bean.getStartDate() + "', 'yyyy-mm-dd') " +
               "AND s1.visitdate < TO_DATE ('" + bean.getEndDate() + "', 'yyyy-mm-dd') + 1 " +
               "GROUP BY s1.SUBMENUID, r1.regionname) mobile, " +
               "(SELECT   COUNT (s2.ID) visittimes,s2.submenuid,r2.regionname " +
                    "FROM syslogger s2, region r2 " +
		            "WHERE s2.SUBMENUID= r2.regionid AND s2.MAINMENUID='" + mainMenuID + "' " +
		               "AND s2.usertype = '2' " +
		               "AND s2.visitdate >= TO_DATE ('" + bean.getStartDate() + "', 'yyyy-mm-dd') " +
		               "AND s2.visitdate < TO_DATE ('" + bean.getEndDate() + "', 'yyyy-mm-dd') + 1 ";
		//if (!bean.getRegionID().equals("")){
		if(!bean.getRegionID().substring( 2, 6 ).equals( "0000" )){
		  	sql += "and s2.regionid = '" + bean.getRegionID() + "' ";
        }
		sql += "GROUP BY s2.submenuid, r2.regionname) con " +
		       "WHERE s.submenuid= r.regionid AND s.MAINMENUID='" + mainMenuID + "' " +
				    "AND mobile.SUBMENUID(+) = r.regionid " +
				    "AND con.SUBMENUID(+) = r.regionid ";
		//if (!bean.getRegionID().equals("")){
		if(!bean.getRegionID().substring( 2, 6 ).equals( "0000" )){
		  	sql += "and s.regionid = '" + bean.getRegionID() + "' ";
        } 
        sql += "GROUP BY s.SUBMENUID, r.regionname, mobile.visittimes, con.visittimes " +
			   "ORDER BY mvisittimes DESC";			               
        if (sql != null ||!"".equals(sql)){
        	logger.info( "得到选定一级模块下的各二级模块访问量结果SQL:" + sql );
            rs = logPathDAOImpl.getBackInfoListJDBC( sql );
        } 
        if( rs == null ){
            logger.info( "rs is null" );
            return null;
        }
        Map map;
        List list = new ArrayList();
        try {
			while(rs.next()){
				map = new HashMap();
				map.put("lablename", rs.getString(1));
				map.put("mvisittimes", rs.getString(2));
			    map.put("cvisittimes", rs.getString(3));
			    list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return list;
    }
  
}
