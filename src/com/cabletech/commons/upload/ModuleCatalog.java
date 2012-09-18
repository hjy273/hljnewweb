package com.cabletech.commons.upload; 

import java.util.HashMap;
import java.util.Map;
/**
 * 定义每个模块上传附件的目录结构
 * @author Administrator
 *
 */
public class ModuleCatalog {
	private static Map<String,String> MODULECATALOG = new HashMap<String,String>();
	public static final String TROUBLE = "trouble";
	public static final String MAINTENANCE = "maintenance";//技术维护模块
	public static final String SAFEGUARD="safeguard";
	public static final String DRILL = "drill";
	public static final String OPTICALCABLE="opticalcable";
	public static final String PIPELINE = "pipeline";
	public static final String DATUM = "datum";
	public static final String HIDDTROUBLEWATCH="hiddtroubleWatch";
	public static final String INSPECTION="inspection";
	public static final String LINECUT="lineCut";
	public static final String FIBRECORETEST="fibreCoreTest";
	public static final String EARTHINGTEST ="earthingTest";
	public static final String SENDTASK ="sendtask";
	public static final String OTHER = "other";
	public static final String MATERIAL = "material";
	public static final String PROJECT = "project";
	public static final String QUEST = "quest";
	public static final String OVERHAUL = "overhaul";
	
	static {
		MODULECATALOG.put("trouble", "线路故障");
		MODULECATALOG.put("safeguard", "线路保障");
		MODULECATALOG.put("drill", "演练");
		MODULECATALOG.put("opticalcable", "维护资源/中继段");
		MODULECATALOG.put("pipeline", "维护资源/管道");
		MODULECATALOG.put("datum", "维护资源/其他");
		MODULECATALOG.put("hiddtroubleWatch", "隐患盯防");
		MODULECATALOG.put("inspection", "验收交维");
		MODULECATALOG.put("lineCut", "线路割接");
		MODULECATALOG.put("fibreCoreTest", "技术维护/备纤测试");
		MODULECATALOG.put("earthingTest", "技术维护/接地电阻测试");
		MODULECATALOG.put("sendtask", "任务派单");
		MODULECATALOG.put("other", "其他维护工作");
		MODULECATALOG.put("project", "工程管理");
		MODULECATALOG.put("quest", "问卷调查");
		MODULECATALOG.put("overhaul", "大修项目");
	}
	public static String get(String key){
		return (String) MODULECATALOG.get(key);
	}
	
}
