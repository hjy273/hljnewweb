package com.cabletech.baseinfo.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.beans.UseTerminalBean;

public class UseTerminalKmBO extends UseTerminalBO {
	private Logger logger = Logger.getLogger("UseTerminalKmBO");
	public UseTerminalKmBO(UseTerminalBean querycon){
		this.queryCon = querycon;
	}
	public String getField(){
		return "MONTHLYKM";
	}

	public List getGrade() {
		return getConfig().getSplitKmGrade();
	}
}
