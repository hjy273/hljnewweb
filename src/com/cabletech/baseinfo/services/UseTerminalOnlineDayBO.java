package com.cabletech.baseinfo.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.beans.UseTerminalBean;

public class UseTerminalOnlineDayBO extends UseTerminalBO {
	private Logger logger = Logger.getLogger("UseTerminalOnlineDayBO");
	public UseTerminalOnlineDayBO(UseTerminalBean querycon){
		this.queryCon = querycon;
	}
	public String getField(){
		return "onlinedays";
	}

	public List getGrade() {
		return getConfig().getSplitOnlineDayGrade();
	}
	
}
