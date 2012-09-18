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
import com.cabletech.planstat.dao.SMNoticePlanStartDAOImpl;

public class SMNoticePlanStartBO  extends BaseBisinessObject {
	Logger logger = Logger.getLogger( this.getClass().getName() );
    private String sql = null;
    private SMNoticePlanStartDAOImpl smDAOImpl = null;
    public SMNoticePlanStartBO(){
    	smDAOImpl = new SMNoticePlanStartDAOImpl();
    }
    //得到短信通知计划执行列表
    public List getReceiverInfoList( UserInfo userinfo ){
    	GisConInfo config = GisConInfo.newInstance();
    	int days = 1;
    	try{
    	    days =Integer.parseInt(config.getDaysfornoticeplanstart());
    	}catch(Exception ex){
    		logger.info("未能在gisServer.properties文件中找到daysfornoticeplanstart属性值,请检查");
    	    days = 1;
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
				    "and t.contractorid = '" + userinfo.getDeptID() + "' " +  //for running
				    "and p.begindate = trunc(sysdate) + " + days + " " +    // for running
				    "and (t.state <> '1' or t.state is null) " +
				    "order by p.begindate";
        } 
        logger.info( "短信通知计划执行SQL:" + sql );
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
