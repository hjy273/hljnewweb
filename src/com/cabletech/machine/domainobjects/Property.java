package com.cabletech.machine.domainobjects;

/**
 * 常量
 * @author haozi
 *
 */
//代码中的flag是用来判断返回按扭的，type是用来判断类型的，应该走哪个页面
public class Property {
	
	public static final String MECHINE_MODULE = "[机房巡检]";
	/**
	 * 清洁
	 */
	public static final String CLEAN = "1";

	/**
	 * 不清洁
	 */
	public static final String NOT_CLEAN = "0";

	/**
	 * 亮
	 */
	public static final String LIGHT = "1";

	/**
	 * 不亮
	 */
	public static final String NOT_LIGHT = "0";

	/**
	 * 待签收
	 */
	public static final String WAIT_SIGN_FOR = "1";

	/**
	 * 签收
	 */
	public static final String SIGN_FOR = "2";
	

	/**
	 * 已完成
	 */
	public static final String END_TASK = "3";
	
	/**
	 * 核查完毕
	 */
	public static final String CHECK_OVER = "4";

	/**
	 * 不签收
	 */
	public static final String NOT_SIGN_FOR = "4";

	
	/**
	 * 核心层
	 */
	public static final String CORE_LAYER = "1";
	
	/**
	 * 接入层SDH
	 */
	public static final String SDH_LAYER = "2";
	
	/**
	 * 接入微波层
	 */
	public static final String MICRO_LAYER = "3";
	
	/**
	 * 接入层FSO
	 */
	public static final String FSO_LAYER = "4";
	
	/**
	 * 接入光交维护
	 */
	public static final String FIBER_LAYER = "5";
	
	/**
	 * 如果是核心 、SDH层显示的界面 
	 */
	public static final String FLAG_FOR_CORE_AND_SDH = "1";
	
	/**
	 * 如果是微波层显示的界面
	 */
	public static final String FLAG_FOR_MICRO = "2";
	
	/**
	 * 如果是FSO层显示的界面
	 */
	public static final String FLAG_FOR_FSO = "3";
	
	/**
	 * 如果是光交维护显示的界面 20090212
	 */
	public static final String FLAG_FOR_FIBER = "4";
	
	/**
	 * 没有巡检的设备
	 */
	public static final String NOT_POLLING = "0";
	
	/**
	 * 已经巡检过的设备
	 */
	public static final String POLLINGED = "1";
	
	/**
	 * 已经核查过的设备
	 */
	public static final String END_CHECK = "2";
}
