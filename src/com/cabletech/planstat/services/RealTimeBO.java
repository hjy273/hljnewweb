package com.cabletech.planstat.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;
import java.sql.*;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.planstat.dao.RealTimeDAOImpl;
import com.cabletech.planstat.beans.RealTimeConditionBean;

public class RealTimeBO extends BaseBisinessObject{
	Logger logger = Logger.getLogger( this.getClass().getName() );
    private String sql = null;
    private RealTimeDAOImpl realTimeDAOImpl = null;
    public RealTimeBO(){
    	realTimeDAOImpl = new RealTimeDAOImpl();
    }
    
    public List getRegionInfoList(){
        String sql = "select  r.REGIONNAME, r.REGIONID "
                           + " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
        logger.info( "区域SQL:" + sql );
        List list = realTimeDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
    //得到符合条件的市代维单位列表
    public List getContractorInfoList( UserInfo userinfo ){
        if( userinfo.getType().equals( "12" ) ){
            sql = "select con.contractorid,con.contractorname,con.parentcontractorid " +
                  "from contractorinfo con " +
                  "where con.STATE is null " +
                  "and con.regionid='" + userinfo.getRegionID() + "' " +
                  "order by con.contractorname";
        }
        else if ( userinfo.getType().equals( "11" ) ){
            sql = "select con.contractorid,con.contractorname,con.parentcontractorid,con.regionid " +
                  "from contractorinfo con " +
                  "where con.STATE is null " +
                  "order by con.contractorname";
        }else if( userinfo.getType().equals( "22" ) ){
            sql = "select con.contractorid,con.contractorname,con.parentcontractorid " +
                  "from contractorinfo con " +
                  "where con.STATE is null " +
                  "and con.regionid='" + userinfo.getRegionID() + "' " +
                  "and con.contractorid='" + userinfo.getDeptID() + "' " +
                  "order by con.contractorname";
        }
        logger.info( "市代维单位列表SQL:" + sql );
        List list = realTimeDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
//  得到符合条件的实时信息
    public List getRealTimeInfoList( UserInfo userinfo,RealTimeConditionBean bean ){
    	String strPatrolID="";
    	String strOldPatrolID="";
    	String strSimNumber="";
    	String strOldSimNumber="";
    	String strPatrolName="";
    	String strOldPatrolName="";
    	String strMaxArriveTime="";
    	String strMinArriveTime="";
    	String strMostMaxArriveTime="";
    	String strMostMinArriveTime="";
    	String LastPatrolName="";
    	String LastState="";
    	String LastSimNumber="";
    	String LastMaxArriveTime="";
    	int  LastItem = 0;  	
    	int intItems = 0;
    	int intPatrol = 0;
    	int intCollect = 0;
    	int intWatch = 0;
    	int intTotal = 0;
    	int intTrouble = 0;
    	int intOther = 0;
    	boolean equalFlag = true;   	
    	List list = new ArrayList();    	
    	sql="select t.patrolid,t.patrolname,r.simid,to_char(max(r.arrivetime),'yyyy-mm-dd hh24:mi:ss') arrivetime," +
    	           "r.handlestate, count(r.id) as item,to_char(min(r.arrivetime), 'yyyy-mm-dd hh24:mi:ss') minarrivetime " +
    	    "from recievemsglog r,terminalinfo tl,patrolmaninfo t,contractorinfo c " +
    	    "where c.contractorid = t.parentid " +
    	      "and t.patrolid = tl.ownerid " +
    	      "and tl.simnumber = r.simid " +
    	      "and c.state is null " +
    	      "and t.state is null " +
    	      "and (tl.state <> '1' or tl.state is null) " +
    	     "and r.arrivetime between trunc(sysdate) and sysdate " + //for running
    	      "and c.contractorid='" + bean.getConID() + "' " +
    	    "group by t.patrolid, t.patrolname,r.simid, r.handlestate " +
    	    "order by t.patrolid,r.simid,r.handlestate"; 
    	logger.info( "实时信息列表SQL:" + sql );
        ResultSet rs = realTimeDAOImpl.getBackInfoListJDBC( sql );
        if( rs == null ){
            logger.info( "rs is null" );
            return null;
        }

        try {
			if( !rs.next()){
			  logger.info( "rs's size is :0" );
			  return null;
			}else{
			  rs.previous();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;

		}       
        Map map;
        try {
			while(rs.next()){
				strPatrolID = rs.getString(1);
				strPatrolName = rs.getString(2);
				strSimNumber = rs.getString(3);
				if (equalFlag == true || strSimNumber.equals(strOldSimNumber)){
					equalFlag = false;
					intItems = rs.getInt(6);
					if (rs.getString(5).equals("3") ){  //采集
						intCollect += intItems;
					}else if (rs.getString(5).equals("20") ){  //隐患
						intTrouble += intItems;
					}else if (rs.getString(5).equals("6") || rs.getString(5).equals("9") || rs.getString(5).equals("11") ){  //盯防
						intWatch += intItems;	
					}else if (rs.getString(5).equals("7") || rs.getString(5).equals("8") || rs.getString(5).equals("4") || rs.getString(5).equals("12") || rs.getString(5).equals("10")){ //匹配
						intPatrol += intItems;
					}else{
						intOther += intItems;//其它
					}
					intTotal += intItems;
					LastPatrolName = rs.getString(2);
					LastSimNumber = rs.getString(3);
					if (strSimNumber.equals(strOldSimNumber)){
						if (rs.getString(4).compareTo(strMaxArriveTime)>0){
							strMaxArriveTime = rs.getString(4);
						}
						if (rs.getString(7).compareTo(strMinArriveTime)<0){
							strMinArriveTime = rs.getString(7);
						}
					}else{
						strMaxArriveTime = rs.getString(4);
						strMinArriveTime = rs.getString(7);
					}
					LastState = rs.getString(5);
					LastItem = rs.getInt(6);
				}else{
					map = new HashMap();
				    map.put("name", strOldPatrolName);
				    map.put("simnumber", strOldSimNumber);
				    map.put("arrivetime", strMostMaxArriveTime.substring(11));
				    map.put("total", new Integer(intTotal));
				    map.put("patrol", new Integer(intPatrol));
				    map.put("collect", new Integer(intCollect));
				    map.put("trouble", new Integer(intTrouble));
				    map.put("watch", new Integer(intWatch));
				    map.put("other", new Integer(intOther));
				    map.put("minarrivetime", strMostMinArriveTime.substring(11));
				    list.add(map);
				    intTotal = 0;
				    intPatrol = 0;
				    intCollect = 0;
				    intWatch = 0;
				    intOther = 0;
				    intTrouble = 0;
				    intItems = rs.getInt(6);
					if (rs.getString(5).equals("3") ){  //采集
						intCollect += intItems;
					}else if (rs.getString(5).equals("20") ){  //隐患
						intTrouble += intItems;
					}else if (rs.getString(5).equals("6") || rs.getString(5).equals("9") || rs.getString(5).equals("11") ){  //盯防
						intWatch += intItems;
					}else if (rs.getString(5).equals("7") || rs.getString(5).equals("8") || rs.getString(5).equals("4") || rs.getString(5).equals("12") || rs.getString(5).equals("10")){ //匹配
						intPatrol += intItems;
					}else{
						intOther += intItems; //其它
					}
					intTotal += intItems;
					LastPatrolName = rs.getString(2);
					LastSimNumber = rs.getString(3);
					strMaxArriveTime = rs.getString(4);
					LastState = rs.getString(5);
					LastItem = rs.getInt(6);
					strMinArriveTime = rs.getString(7);
				}
				strOldPatrolID = strPatrolID;
				strOldPatrolName = strPatrolName;
				strOldSimNumber = strSimNumber;
				strMostMaxArriveTime = strMaxArriveTime;
				strMostMinArriveTime = strMinArriveTime;
			}
			map = new HashMap();
			map.put("name", LastPatrolName);
			map.put("simnumber", strOldSimNumber);
			//map.put("arrivetime", strMostMaxArriveTime);
			map.put("arrivetime", strMostMaxArriveTime.substring(11));
		    map.put("total", new Integer(intTotal));
		    map.put("patrol", new Integer(intPatrol));
		    map.put("collect", new Integer(intCollect));
		    map.put("trouble", new Integer(intTrouble));
		    map.put("watch", new Integer(intWatch));
		    map.put("other", new Integer(intOther));
		    map.put("minarrivetime", strMostMinArriveTime.substring(11));
		    list.add(map);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return list;
    }
    
//  得到每个SIM卡的详细实时信息
    public List getRealTimePerCardInfoList( String simNumber,String handleState ){
    	sql = "select to_char(r.arrivetime,'YYYY-MM-DD hh24:mi:ss') arrivetime,r.simid " +
	    	  "from recievemsglog r " +
	    	  "where r.simid = '" + simNumber + "' " +
	    	    "and r.handlestate in (" + handleState + ") " +
	    	    "and r.arrivetime between trunc(sysdate) and sysdate " + // for running
	    	    "order by r.arrivetime";
    	logger.info( "每个SIM卡的详细实时信息SQL:" + sql );
    	List list = realTimeDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
     //  得到符合条件的历史信息
    public List getHistoryInfoList(String conid,String strDate){
    	sql="select p.patrolname,t.simid,t.totalnum,t.patrolnum,t.troublenum,t.collectnum,t.watchnum,othernum,TO_CHAR(t.firstdate,'hh24:mi:ss') firstdate,TO_CHAR(t.lastdate,'hh24:mi:ss') lastdate,t.dailykm,t.avgsendtime,t.avgsenddistance " +
        "from stat_messagedaily t, patrolmaninfo p " +
        "where p.patrolid = t.patrolmanid " +
          "and p.parentid = '" + conid + "' " +
          "and t.operatedate = to_date('" + strDate + "','yyyy-mm-dd') " +
          "and p.state is null " +
        "order by t.patrolmanid, t.simid";
    	logger.info( "详细历史信息SQL:" + sql );
        List list = realTimeDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
//  得到每个SIM卡的详细历史信息
    public List getHistoryPerCardInfoList( String simNumber,String handleState,String strDate ){	
    	sql = "select to_char(r.arrivetime,'YYYY-MM-DD hh24:mi:ss') arrivetime,r.simid " +
	    	  "from recievemsglog r " +
	    	  "where r.simid = '" + simNumber + "' " +
	    	    "and r.handlestate in (" + handleState + ") " +
	    	    "and (r.arrivetime between to_date('" + strDate + "', 'yyyy-mm-dd hh24:mi:ss') and to_date('" + strDate + "', 'yyyy-mm-dd hh24:mi:ss') + 1) " +
	    	    "order by r.arrivetime";
    	logger.info( "每个SIM卡的详细历史信息SQL:" + sql );
    	List list = realTimeDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    } 
    
    //  得到符合条件的代维公司历史信息 
    public List getConHistoryInfoList(UserInfo userinfo,RealTimeConditionBean bean){
    	logger.info("bean's statedate is:" + bean.getStatDate());
    	if( userinfo.getType().equals("12") ){
    		sql ="SELECT sc.contractorid, con.contractorname, sc.totalnum, sc.patrolnum," +
			            "sc.watchnum, sc.troublenum, sc.collectnum, sc.othernum, sc.dailykm,TO_CHAR(sc.operatedate,'yyyy-mm-dd') operatedate " +
			     "FROM STAT_CONMESSAGEDAILY sc, CONTRACTORINFO con " +
			     "WHERE sc.contractorid = con.contractorid " +
			            "AND con.regionid = '" + userinfo.getRegionid() + "' " +
			            "AND sc.operatedate = TO_DATE ('" + bean.getStatDate() + "', 'yyyy-mm-dd') " +
			     "ORDER BY con.contractorname";
    	}else if ( userinfo.getType().equals("22") ){
    		sql ="SELECT sc.contractorid, con.contractorname, sc.totalnum, sc.patrolnum," +
			            "sc.watchnum, sc.troublenum, sc.collectnum, sc.othernum, sc.dailykm,TO_CHAR(sc.operatedate,'yyyy-mm-dd') operatedate " +
			     "FROM STAT_CONMESSAGEDAILY sc, CONTRACTORINFO con " +
			     "WHERE sc.contractorid = con.contractorid " +
			            "AND sc.contractorid = '" + userinfo.getDeptID() + "' " +
			            "AND sc.operatedate = TO_DATE ('" + bean.getStatDate() + "', 'yyyy-mm-dd') " +
			     "ORDER BY con.contractorname";
    	}
    	logger.info( "代维公司历史短信信息SQL:" + sql );
        List list = realTimeDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
    //  得到符合条件的各地市历史信息
    public List getRegionHistoryInfoList(UserInfo userinfo,RealTimeConditionBean bean){
    	logger.info("bean's statedate is:" + bean.getStatDate());
    	if( userinfo.getType().equals("11") ){
    		sql = "SELECT sr.regionid, r.regionname, sr.totalnum, sr.patrolnum,sr.watchnum, sr.troublenum," +
    		              "sr.collectnum, sr.othernum, sr.dailykm,TO_CHAR(sr.operatedate,'yyyy-mm-dd') operatedate " +
			      "FROM STAT_REGIONMESSAGEDAILY sr, REGION r " +
			      "WHERE sr.regionid = r.regionid  " +
			            "AND sr.operatedate = TO_DATE ('" + bean.getStatDate() + "', 'yyyy-mm-dd') " +
			      "ORDER BY r.regionname";
    	}
    	logger.info( "各地市历史短信信息SQL:" + sql );
        List list = realTimeDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }    
    
    
    //得到每个代维公司历史短信信息
    public List getConHistoryPerConInfoList( String contractorid,String handleState,String strDate ){
    	
    	sql = "select TO_CHAR(r.arrivetime,'YYYY-MM-DD hh24:mi:ss') arrivetime, r.simid,p.patrolname " +
	    	  "FROM recievemsglog r,terminalinfo t,patrolmaninfo p " +
	    	  "where r.SIMID = t.SIMNUMBER AND t.OWNERID = p.PATROLID AND p.PARENTID='" + contractorid + "' " +
	    	    "and r.handlestate in (" + handleState + ") " +
	    	    "and (r.arrivetime between to_date('" + strDate + "', 'yyyy-mm-dd hh24:mi:ss') and to_date('" + strDate + "', 'yyyy-mm-dd hh24:mi:ss') + 1) " +
	    	    "order by r.arrivetime";
    	logger.info( "每个代维公司详细历史信息SQL:" + sql );
    	List list = realTimeDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    } 
    
    //得到每个地市级移动公司历史短信信息
    public List getRegionHistoryPerRegInfoList( String regionid,String handleState,String strDate ){
    	
    	sql = "SELECT TO_CHAR(r.arrivetime,'YYYY-MM-DD hh24:mi:ss') arrivetime, r.simid,p.patrolname,c.contractorname " +
	    	  "FROM recievemsglog r,terminalinfo t,contractorinfo c,patrolmaninfo p " +
	    	  "where r.SIMID = t.SIMNUMBER AND t.OWNERID = p.PATROLID " +
	    	    "and c.CONTRACTORID=p.PARENTID AND c.REGIONID = p.REGIONID " +
	    	    "AND p.REGIONID='" + regionid + "' " +
	    	    "and r.handlestate in (" + handleState + ") " +
	    	    "and (r.arrivetime between to_date('" + strDate + "', 'yyyy-mm-dd hh24:mi:ss') and to_date('" + strDate + "', 'yyyy-mm-dd hh24:mi:ss') + 1) " +
	    	  "order by r.arrivetime";
    	logger.info( "每个地市级移动公司详细历史信息SQL:" + sql );
    	List list = realTimeDAOImpl.getBackInfoList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }     

}
