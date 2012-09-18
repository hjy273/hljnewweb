package com.cabletech.linepatrol.commons;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	//验收交维
	public static final String BUSINESSMODULE_ACCEPTANCE="acceptance";
	//其他日常考核
	public static final String BUSINESSMODULE_OTHER="other";
	//割接
	public static final String BUSINESSMODULE_LINECUT="lineCut";
	//任务派单
	public static final String BUSINESSMODULE_SENDTASK="sendtask";
	//演练
	public static final String BUSINESSMODULE_DRILL="drill";
	//隐患盯防
	public static final String BUSINESSMODULE_HIDDANGER="hiddanger";
	//技术维护
	public static final String BUSINESSMODULE_MAINTENCE="maintence";
	//大修
	public static final String BUSINESSMODULE_OVERHAUL="overHaul";
	//工程
	public static final String BUSINESSMODULE_PROJECT="project";
	//保障
	public static final String BUSINESSMODULE_SAFEGUARD="safeguard";
	//故障
	public static final String BUSINESSMODULE_TROUBLE="trouble";
	public static final Map<String,String> BUSINESSMODULE= new HashMap<String,String>();
	static{
		BUSINESSMODULE.put("acceptance", "验收交维");
		BUSINESSMODULE.put("other", "其他");
		BUSINESSMODULE.put("lineCut", "线路割接");
		BUSINESSMODULE.put("sendtask", "任务派单");
		BUSINESSMODULE.put("drill", "应急演练");
		BUSINESSMODULE.put("hiddanger", "隐患盯防");
		BUSINESSMODULE.put("maintence", "技术维护");
		BUSINESSMODULE.put("overHaul", "大修项目");
		BUSINESSMODULE.put("project", "工程");
		BUSINESSMODULE.put("safeguard", "通讯保障");
		BUSINESSMODULE.put("trouble", "线路故障");
	}
}
