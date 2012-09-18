package com.cabletech.commons.services;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.MenuBean;
import com.cabletech.commons.hb.UpdateUtil;

public class SysLoggerService {
	private Logger logger = Logger.getLogger("SysLoggerService");
	UpdateUtil update =null;
	public void log(UserInfo user ,String mainid,String regionid){
		DBService db = new DBService();
		String sql = "insert into syslogger(id,userid,usertype,mainmenuid,submenuid,sonmenuid,visitdate,regionid,state)"
			+" values ('"+db.getSeq("syslogger", 20)+"','"+user.getUserID()+"','"+user.getDeptype()+"','"+mainid+"','"+regionid+"','',sysdate,'"+user.getRegionid()+"','1')";
		logger.info("sql :"+sql);
		writeLog(sql);
	}
	public void log(UserInfo user,MenuBean menu){
		DBService db = new DBService();
		String sql = "insert into syslogger(id,userid,usertype,mainmenuid,submenuid,sonmenuid,visitdate,regionid,state)"
			+" values ('"+db.getSeq("syslogger", 20)+"','"+user.getUserID()+"','"+user.getDeptype()+"','"+menu.getMainid()+"','"+menu.getSubid()+"','"+menu.getSonid()+"',sysdate,'"+user.getRegionid()+"','0')";
		logger.info("sql :"+sql);
		writeLog(sql);
	}
	private void writeLog(String sql ){
		try {
			update = new UpdateUtil();
			update.executeUpdate(sql);
		} catch (Exception e) {
			update = null;
			e.printStackTrace();
		}
		
		update=null;
	}
}
