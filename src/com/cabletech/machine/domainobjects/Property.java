package com.cabletech.machine.domainobjects;

/**
 * ����
 * @author haozi
 *
 */
//�����е�flag�������жϷ��ذ�Ť�ģ�type�������ж����͵ģ�Ӧ�����ĸ�ҳ��
public class Property {
	
	public static final String MECHINE_MODULE = "[����Ѳ��]";
	/**
	 * ���
	 */
	public static final String CLEAN = "1";

	/**
	 * �����
	 */
	public static final String NOT_CLEAN = "0";

	/**
	 * ��
	 */
	public static final String LIGHT = "1";

	/**
	 * ����
	 */
	public static final String NOT_LIGHT = "0";

	/**
	 * ��ǩ��
	 */
	public static final String WAIT_SIGN_FOR = "1";

	/**
	 * ǩ��
	 */
	public static final String SIGN_FOR = "2";
	

	/**
	 * �����
	 */
	public static final String END_TASK = "3";
	
	/**
	 * �˲����
	 */
	public static final String CHECK_OVER = "4";

	/**
	 * ��ǩ��
	 */
	public static final String NOT_SIGN_FOR = "4";

	
	/**
	 * ���Ĳ�
	 */
	public static final String CORE_LAYER = "1";
	
	/**
	 * �����SDH
	 */
	public static final String SDH_LAYER = "2";
	
	/**
	 * ����΢����
	 */
	public static final String MICRO_LAYER = "3";
	
	/**
	 * �����FSO
	 */
	public static final String FSO_LAYER = "4";
	
	/**
	 * ����⽻ά��
	 */
	public static final String FIBER_LAYER = "5";
	
	/**
	 * ����Ǻ��� ��SDH����ʾ�Ľ��� 
	 */
	public static final String FLAG_FOR_CORE_AND_SDH = "1";
	
	/**
	 * �����΢������ʾ�Ľ���
	 */
	public static final String FLAG_FOR_MICRO = "2";
	
	/**
	 * �����FSO����ʾ�Ľ���
	 */
	public static final String FLAG_FOR_FSO = "3";
	
	/**
	 * ����ǹ⽻ά����ʾ�Ľ��� 20090212
	 */
	public static final String FLAG_FOR_FIBER = "4";
	
	/**
	 * û��Ѳ����豸
	 */
	public static final String NOT_POLLING = "0";
	
	/**
	 * �Ѿ�Ѳ������豸
	 */
	public static final String POLLINGED = "1";
	
	/**
	 * �Ѿ��˲�����豸
	 */
	public static final String END_CHECK = "2";
}
