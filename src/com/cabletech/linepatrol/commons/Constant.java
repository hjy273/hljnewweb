package com.cabletech.linepatrol.commons;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	//���ս�ά
	public static final String BUSINESSMODULE_ACCEPTANCE="acceptance";
	//�����ճ�����
	public static final String BUSINESSMODULE_OTHER="other";
	//���
	public static final String BUSINESSMODULE_LINECUT="lineCut";
	//�����ɵ�
	public static final String BUSINESSMODULE_SENDTASK="sendtask";
	//����
	public static final String BUSINESSMODULE_DRILL="drill";
	//��������
	public static final String BUSINESSMODULE_HIDDANGER="hiddanger";
	//����ά��
	public static final String BUSINESSMODULE_MAINTENCE="maintence";
	//����
	public static final String BUSINESSMODULE_OVERHAUL="overHaul";
	//����
	public static final String BUSINESSMODULE_PROJECT="project";
	//����
	public static final String BUSINESSMODULE_SAFEGUARD="safeguard";
	//����
	public static final String BUSINESSMODULE_TROUBLE="trouble";
	public static final Map<String,String> BUSINESSMODULE= new HashMap<String,String>();
	static{
		BUSINESSMODULE.put("acceptance", "���ս�ά");
		BUSINESSMODULE.put("other", "����");
		BUSINESSMODULE.put("lineCut", "��·���");
		BUSINESSMODULE.put("sendtask", "�����ɵ�");
		BUSINESSMODULE.put("drill", "Ӧ������");
		BUSINESSMODULE.put("hiddanger", "��������");
		BUSINESSMODULE.put("maintence", "����ά��");
		BUSINESSMODULE.put("overHaul", "������Ŀ");
		BUSINESSMODULE.put("project", "����");
		BUSINESSMODULE.put("safeguard", "ͨѶ����");
		BUSINESSMODULE.put("trouble", "��·����");
	}
}
