package com.cabletech.baseinfo.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.beans.UseTerminalBean;

public class UseTerminalNoteBO extends UseTerminalBO{
	private Logger logger = Logger.getLogger("UseTerminalNoteBO");
	public UseTerminalNoteBO(UseTerminalBean querycon){
		super.queryCon = querycon;
	}
	public String getField(){
		return "totalnum";
	}

	public List getGrade() {
		return getConfig().getSplitNoteGrade();
	}


}
