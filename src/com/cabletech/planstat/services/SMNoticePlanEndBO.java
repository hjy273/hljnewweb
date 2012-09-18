package com.cabletech.planstat.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.config.GisConInfo;
import com.cabletech.planstat.dao.SMNoticePlanEndDAOImpl;

public class SMNoticePlanEndBO extends BaseBisinessObject {
	Logger logger = Logger.getLogger( this.getClass().getName() );
    private String sql = null;
    private SMNoticePlanEndDAOImpl smDAOImpl = null;
    public SMNoticePlanEndBO(){
    	smDAOImpl = new SMNoticePlanEndDAOImpl();
    }
    //得到短信通知计划完成列表
    public List getReceiverInfoList( UserInfo userinfo ){
      	GisConInfo config = GisConInfo.newInstance();
    	int days = 2;
    	try{
    	    days =Integer.parseInt(config.getDaysfornoticeplanend());
    	}catch(Exception ex){
    		logger.info("未能在gisServer.properties文件中找到daysfornoticeplanend属性值,请检查");
    	    days = 2;
    	}
    	ResultSet rs = null;
        if( userinfo.getType().equals( "22" ) ){
            sql = "select p.id," +
		                 "p.planname," +
				         "to_char(p.begindate,'YYYY-MM-DD') begindate," + 
				         "to_char(p.enddate,'YYYY-MM-DD') enddate," +
				         "t.simnumber " +
                  "from plan p,terminalinfo t " +
                  "where p.executorid = t.ownerid " +
				    "and t.contractorid = '" + userinfo.getDeptID() + "' " +
				    "and p.enddate = trunc(sysdate,'dd') - " + days + " " + // for running
				    "and (t.state <> '1' or t.state is null) " +
				    "order by p.begindate";
        } 
        logger.info( "短信通知计划完成SQL:" + sql );
        if (sql != null ||!"".equals(sql)){
            rs = smDAOImpl.getReceiverListJDBC( sql );
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
				map.put("planid", rs.getString(1));
				map.put("planname", rs.getString(2));
			    map.put("begindate", rs.getString(3));
			    map.put("enddate", rs.getString(4));
			    map.put("simnumber", rs.getString(5));
			    list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return list;
    }
}
