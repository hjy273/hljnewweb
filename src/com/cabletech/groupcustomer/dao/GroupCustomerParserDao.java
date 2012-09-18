package com.cabletech.groupcustomer.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.groupcustomer.bean.GroupCustomerBean;
import com.cabletech.partmanage.action.PartRequisitionAction;

public class GroupCustomerParserDao {
	
	private static Logger logger = Logger.getLogger( PartRequisitionAction.class.
            getName() );

	
    /**
     * 取得分析的集团客户信息
     * @param hform 页面提交内容
     * @param userinfo 用户登录信息
     * @return List
     */
    public List getCustomerParserData(GroupCustomerBean hform, UserInfo userinfo) {    	
    	// sql语句
    	StringBuffer  sql = new StringBuffer();
    	sql.append("select g.grouptype, g.city, g.groupname ,"); 
    	sql.append(" round(MIN(SDO_GEOM.sdo_distance(S.GEOLOC, ");
    	sql.append(" mdsys.sdo_geometry(2001,8307,sdo_point_type(");
    	sql.append("g.x, g.y");
    	sql.append(",null),null,null),1)),1) as len ");
    	sql.append(" from sublineinfo s , groupcustomer g ");
    	sql.append(" where SDO_WITHIN_DISTANCE(S.GEOLOC,MDSYS.SDO_GEOMETRY(2001,8307, ");
    	sql.append(" MDSYS.SDO_POINT_TYPE(");
    	sql.append("g.x, g.y");;
    	sql.append(",NULL),NULL,NULL),'distance=" + hform.getBestrowrange() +"') = 'TRUE' ");
    	sql.append(" and SDO_GEOM.sdo_distance(S.GEOLOC,  mdsys.sdo_geometry(2001,8307,sdo_point_type(g.x, g.y,null),null,null),1) > 0 ");
    	String conSql = "";
    	// 客户类型
    	if(hform.getGrouptype() != null && !"".equals(hform.getGrouptype())) {
    		conSql = "and (g.grouptype='" + hform.getGrouptype() + "' or g.grouptype='" + hform.getGrouptype().toLowerCase() +  "') ";
    	}
    	// 客户所属区
    	if(hform.getCity() != null && !"".equals(hform.getCity())) {
    		conSql += "and g.city='" + hform.getCity() + "'";
    	}
    	
    	// 区域	
    	if(UserInfo.PROVINCE_MUSER_TYPE.equals(userinfo.getType())) {
    		// 省移动
    		String regionid = hform.getRegionid();
        	if (regionid != null && !"".equals(regionid)) {        		
        		conSql += " and g.regionid = '" + regionid + "' " ;        					
        	}
    	} else if(UserInfo.CITY_MUSER_TYPE.equals(userinfo.getType())){
    		// 市移动
    		conSql += " and g.regionid = '" + userinfo.getRegionID() + "' " ;
    	}
    	
    	sql.append(conSql);
    	
    	sql.append(" and g.x is not null and g.y is not null group by g.grouptype, g.city, g.groupname , g.x , g.y ");

        try{
            QueryUtil qu = new QueryUtil();
            List lregion = qu.queryBeans( sql.toString() );
            return lregion;
        }
        catch( Exception ex ){
            logger.warn( "取得分析的集团客户信息出错:" + ex.getMessage() );
            return null;
        }
    }
    
    /**
     * 取得查询范围内的客户个数
     * @param hform
     * @return
     */
    public int getCustomerParserCount(GroupCustomerBean hform , UserInfo userinfo) {
    	// sql语句
    	StringBuffer sql = new StringBuffer();
    	sql.append("select count(groupid) num  from groupcustomer g ");
    	String conSql = "";
    	// 客户类型
    	if(hform.getGrouptype() != null && !"".equals(hform.getGrouptype())) {
    		conSql = "where (g.grouptype='" + hform.getGrouptype() + "' or g.grouptype='" + hform.getGrouptype().toLowerCase() +  "') ";
    	}
    	// 客户所属区
    	if(hform.getCity() != null && !"".equals(hform.getCity())) {
    		if("".equals(conSql)) {
    			conSql = "where g.city='" + hform.getCity() + "'";
    		} else {
    			conSql += "and g.city='" + hform.getCity() + "'";
    		}
    		
    	}
    	
    	// 区域	
    	if(UserInfo.PROVINCE_MUSER_TYPE.equals(userinfo.getType())) {
    		// 省移动
    		String regionid = hform.getRegionid();
        	if (regionid != null && !"".equals(regionid)) {  
        		if("".equals(conSql)) {
        			conSql += " where g.regionid = '" + regionid + "' " ; 
        		} else {
        			conSql += " and g.regionid = '" + regionid + "' " ; 
        		}
        		       					
        	}
    	} else if(UserInfo.CITY_MUSER_TYPE.equals(userinfo.getType())){
    		// 市移动
    		if("".equals(conSql)) {
    			conSql += " where g.regionid = '" + userinfo.getRegionID() + "' " ;
    		} else {
    			conSql += " and g.regionid = '" + userinfo.getRegionID() + "' " ;
    		}
    		
    	}    	
    	sql.append(conSql);
    	
    	
    	ResultSet rst = null;
        QueryUtil qu = null;
        int countNum = 0;
 		try {
 			qu = new QueryUtil();
 			rst = qu.executeQuery( sql.toString() );
 			
 			if(rst != null && rst.next()) {
 				countNum = rst.getInt("num");
 			}
 			rst.close();
 					
 		} catch (Exception e) {
 			logger.warn( "获得范围内的客户个数出错:" + e.getMessage() );
 			if(rst != null) {
 				try {
 					rst.close();
 				} catch (SQLException e1) { 				
 				}
 			}
 		} finally {
 			if(rst != null) {
 				try {
 					rst.close();
 				} catch (SQLException e1) { 					
 				}
 			}
 		} 
 		
 		return countNum; 	
    }   
}
