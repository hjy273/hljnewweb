package com.cabletech.commons.upload; 

import java.util.HashMap;
import java.util.Map;
/**
 * ����ÿ��ģ���ϴ�������Ŀ¼�ṹ
 * @author Administrator
 *
 */
public class ModuleCatalog {
	private static Map<String,String> MODULECATALOG = new HashMap<String,String>();
	public static final String TROUBLE = "trouble";
	public static final String MAINTENANCE = "maintenance";//����ά��ģ��
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
		MODULECATALOG.put("trouble", "��·����");
		MODULECATALOG.put("safeguard", "��·����");
		MODULECATALOG.put("drill", "����");
		MODULECATALOG.put("opticalcable", "ά����Դ/�м̶�");
		MODULECATALOG.put("pipeline", "ά����Դ/�ܵ�");
		MODULECATALOG.put("datum", "ά����Դ/����");
		MODULECATALOG.put("hiddtroubleWatch", "��������");
		MODULECATALOG.put("inspection", "���ս�ά");
		MODULECATALOG.put("lineCut", "��·���");
		MODULECATALOG.put("fibreCoreTest", "����ά��/���˲���");
		MODULECATALOG.put("earthingTest", "����ά��/�ӵص������");
		MODULECATALOG.put("sendtask", "�����ɵ�");
		MODULECATALOG.put("other", "����ά������");
		MODULECATALOG.put("project", "���̹���");
		MODULECATALOG.put("quest", "�ʾ����");
		MODULECATALOG.put("overhaul", "������Ŀ");
	}
	public static String get(String key){
		return (String) MODULECATALOG.get(key);
	}
	
}
