package com.cabletech.sysmanage.services;

import java.util.List;
import org.apache.log4j.Logger;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.config.UserType;
import com.cabletech.sysmanage.beans.PointOutputBean;
import com.cabletech.sysmanage.dao.PointOutputBODAOImpl;

public class PointOutputBO extends BaseBisinessObject{
	private Logger logger = Logger.getLogger( this.getClass().getName() );
    private String sql = null;
    private PointOutputBODAOImpl pointOutputBODAOImpl = null;
    public PointOutputBO(){
    	pointOutputBODAOImpl = new PointOutputBODAOImpl();
    }
    
	public List getRegionList() {
		sql = "select regionid, regionname "
			+ "from region r " + "where r.state is null "
			+ " and substr (r.regionid, 3, 4) != '1111' "
			+ " and substr (r.regionid, 3, 4) != '0000' "
			+ "order by r.regionid";
		logger.info("查询区域SQL:" + sql);
		List regionList = pointOutputBODAOImpl.getResultList(sql);
		return regionList;
	}
	
    public String getPatrolPointSql(UserInfo userInfo,PointOutputBean bean){
    	String regionSql="";
		sql = "SELECT one.pointid, one.pointname, one.linename, one.sublineid, one.sublinename, one.sublinetype,two.pointnum,one.ptype,one.inumber, one.regionname, one.patrolname,one.x,one.y,one.linestype " + 
		      "from (select p.pointid, p.pointname, l.linename, s.sublineid, s.sublinename," +
			        "DECODE (s.linetype,'00', '直埋','01', '架空','02', '管道','04', '挂墙','03', '混合','') sublinetype," +
			        "DECODE (p.pointtype,'1', '石','2', '杆','3', '井','4', '墙','5', '基站','6', '机房','') ptype," +
			        "p.inumber, r.regionname, pm.patrolname," +
			        "p.geoloc.SDO_POINT.X x," + 
			        "p.geoloc.SDO_POINT.Y y,ll.name linestype " +
			        //"TO_CHAR (p.geoloc.SDO_POINT.X,'999D9999999') x," + 
			        //"TO_CHAR (p.geoloc.SDO_POINT.Y,'999D9999999') y " +
			        //"decode(TO_CHAR (p.geoloc.SDO_POINT.X,'999D9999999'),'',' ',TO_CHAR (p.geoloc.SDO_POINT.X,'999D9999999')) x," + 
			        //"decode(TO_CHAR (p.geoloc.SDO_POINT.Y,'999D9999999'),'',' ',TO_CHAR (p.geoloc.SDO_POINT.Y,'999D9999999')) y " +
			  "FROM pointinfo p, region r, sublineinfo s, lineinfo l, patrolmaninfo pm,lineclassdic ll " +
			  "WHERE p.regionid = r.regionid " +
			    "AND p.sublineid = s.sublineid " +
			    "AND s.lineid = l.lineid(+) " +
			    "AND ll.code = l.linetype " + 
			    "AND s.patrolid = pm.patrolid(+) ";
    	String userType = userInfo.getType();
    	if (UserType.PROVINCE.equals(userType)) { // 省移动用户
			regionSql = " p.regionid='" + bean.getRegionID() + "' ";
			sql += "and " + regionSql;
//    		if (!"11".equals(regionID)){
//    			regionSql = " p.regionid='" + regionID + "' ";
//    			sql += "and " + regionSql;
//    		}else{
//    			sql += " and rownum<10000 ";
//    		}
    	}else{
    		regionSql =" p.regionid='" + userInfo.getRegionid() + "' ";
    		sql += "and " + regionSql;
    		
    	}
    	sql +=" and p.oncreate>= to_date ('" + bean.getStartDate() + "', 'yyyy-mm-dd') and p.oncreate< to_date ('" + bean.getEndDate() + "', 'yyyy-mm-dd')+1 ";
    	sql +="ORDER BY l.lineid, s.sublineid, p.pointid) one,";
    	sql += "(select p.sublineid,count(p.sublineid) pointnum from pointinfo p ";
    	if (!"".equals(regionSql)){
    		sql += "where ";
    	}
    	    sql += regionSql + " group by p.sublineid order by p.sublineid ) two where one.sublineid = two.sublineid ";
    	logger.info("查询巡检点及相关信息SQL:" + sql);
		return sql;
    }
    
//    public List getPatrolPointList(UserInfo userInfo,String regionID){
//		sql = "SELECT   p.pointid, p.pointname, l.linename, s.sublineid, s.sublinename," +
//			        "DECODE (s.linetype,'00', '直埋','01', '架空','02', '管道','04', '挂墙','03', '混合','') sublinetype,'' as pointnum," +
//			        "DECODE (p.pointtype,'1', '石','2', '杆','3', '井','4', '墙','5', '基站','6', '机房','') ptype," +
//			        "p.inumber, r.regionname, pm.patrolname, decode(TO_CHAR (p.x,'999D9999999'),'',' ',TO_CHAR (p.x,'999D9999999')) x, decode(TO_CHAR (p.y,'999D9999999'),'',' ',TO_CHAR (p.y,'999D9999999')) y " +
//			  "FROM pointinfo p, region r, sublineinfo s, lineinfo l, patrolmaninfo pm " +
//			  "WHERE p.regionid = r.regionid(+) " +
//			    "AND p.sublineid = s.sublineid(+) " +
//			    "AND s.lineid = l.lineid(+) " +
//			    "AND s.patrolid = pm.patrolid(+) ";
//    	String userType = userInfo.getType();
//    	if (UserType.PROVINCE.equals(userType)) { // 省移动用户
//    		if (!"11".equals(regionID)){
//    			sql +="and p.regionid='" + regionID + "' ";
//    		}
//    	}else{
//    		sql +="and p.regionid='" + userInfo.getRegionid() + "' ";
//    	}
//
//		sql +="and s.sublineid<>'00008503' and s.sublineid<>'00119846' and rownum<100 ORDER BY l.lineid, s.sublineid, p.pointid";
//		logger.info("查询巡检点及相关信息SQL:" + sql);
//		List regionList = pointOutputBODAOImpl.getResultList(sql);
//		for (int i=0;i<regionList.size();i++){
//			BasicDynaBean onerow = (BasicDynaBean)regionList.get(i);
//			logger.info("onerow's field:" + onerow.get("pointid") + "," + onerow.get("sublineid") + "," + onerow.get("pointnum"));
//		}
//		 
//		return regionList;
//    }

}
