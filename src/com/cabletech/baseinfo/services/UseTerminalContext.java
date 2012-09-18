package com.cabletech.baseinfo.services;

import com.cabletech.baseinfo.beans.UseTerminalBean;

/**
 * UseTerminalContext
 * 创建3个接口
 * @author Administrator
 *
 */
public class UseTerminalContext {
	
	private static UseTerminalBO createUseTerminalBO(UseTerminalBean querycon){
		if (UseTerminalBean.NOTE_NUM.equals(querycon.getType())) {
			return  new UseTerminalNoteBO(querycon);
		}
		if (UseTerminalBean.PATROL_KM.equals(querycon.getType())) {
			return new UseTerminalKmBO(querycon);
		}
		if (UseTerminalBean.ONLINE_DAY.equals(querycon.getType())) {
			return new UseTerminalOnlineDayBO(querycon);
		}
		return null;
	}
	public static UseTerminalBO getInstance(UseTerminalBean querycon){
		return createUseTerminalBO(querycon);
	}
	
}
