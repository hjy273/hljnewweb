package com.cabletech.analysis.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.cabletech.analysis.beans.HistoryTimeInfoBean;
import com.cabletech.analysis.beans.HistoryDateInfoBean;
import com.cabletech.analysis.beans.HistoryWorkInfoCreateBOParam;
import com.cabletech.analysis.dao.WorkInfoHistoryDAOImpl;
import com.cabletech.commons.base.BaseBisinessObject;

public abstract class WorkInfoHistoryBaseBO extends BaseBisinessObject {
	
	protected HistoryWorkInfoCreateBOParam boParam; //传入的参数集（封装）
	
	protected String rangeID; //曲线功能中区域ID

	protected Logger logger = Logger.getLogger(this.getClass().getName()); // 建立logger输出对象;

	protected String sql = "";

	protected WorkInfoHistoryDAOImpl workInfoHistoryDAOImpl = new WorkInfoHistoryDAOImpl();

	/**
	 * 得到用户所输起止日期范围的在线人数曲线图，
	 * @return 返回用户所输起止日期范围的在线人数曲线图列表，Map类型
	 */
	public Map getOnlineNumberForDays() {
		// 构造SQL
		String sql = createOnlineNumberSql();
		logger.info("查询所选起止日期内在线人数SQL:" + sql);
		List onlineNumberList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (onlineNumberList == null) {
			logger.info("所选起止日期内在线人数列表为空");
		}
		// 将List转换为Map型
		return listToMap(onlineNumberList);
	}
    
	/**
	 * 构建得到所输入时间起止范围内每天的在线人数分布的SQL
	 * @return 组织好的SQL
	 */
	public abstract String createOnlineNumberSql();

	/**
	 * list型转换为map
	 * 
	 * @param onlineNumberList
	 *            包含在线人数,时间段（或日期）interval信息的list。
	 * @return 返回时间段（或日期）interval,人数的map对象
	 */
	private Map listToMap(List onlineNumberList) {
		Map map = new HashMap();
		int size = onlineNumberList.size();
		String key = "";
		String value = "";
		for (int i = 0; i < size; i++) {
			// 时间段（或日期）
			key = ((BasicDynaBean) onlineNumberList.get(i)).get("operatedate")
					.toString();
			value = ((BasicDynaBean) onlineNumberList.get(i)).get(
					"onlinenumber")// 在线人数
					.toString();
			map.put(key, new Integer(Integer.parseInt(value)));
		}
		return map;
	}
    
	/**
	 * 得到用户所选具体日期内各个时段的在线人数曲线图，
	 * @return 返回用户所选具体日期内各个时段的在线人数曲线图列表，Map类型
	 */
	public Map getOnlineNumberForHours() {
		sql = createOnlineNumberForHoursSql();
		logger.info("查询所指定具体日期在线人数或在线01图SQL:" + sql);
		List onlineNumberForHoursList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (onlineNumberForHoursList == null) {
			logger.info("指定具体日期在线人数或在线01图列表为空");
		}
		// 将List转换为Map
		return listToMap(onlineNumberForHoursList);

	}
    
	/**
	 * 构建得到具体所选某一天各时段在线人数分布的SQL
	 * @return 组织好的SQL
	 */
	public abstract String createOnlineNumberForHoursSql();
    
	/**
	 * 得到具体某一天的ToolTip信息
	 * @return HistoryDateInfoBean
	 */
	public HistoryDateInfoBean getOnlineInfoGivenDay() {
		sql = createOnlineInfoGivenDaySql(); // 组织SQL
		logger.info("显示所鼠标所指向点的相关信息（日信息）SQL:" + sql);
		List list = workInfoHistoryDAOImpl.queryInfo(sql);
		if (list == null) {
			logger.info("显示所鼠标所指向点的相关信息列表为空");
			return null;
		}
		HistoryDateInfoBean backBean = new HistoryDateInfoBean();
		// 组织返回bean,任一统计对象一天只有唯一一条记录
		backBean.setSmTotal(((BasicDynaBean) list.get(0)).get("totalnum")
				.toString());
		backBean.setOnlineNumber(((BasicDynaBean) list.get(0)).get(
				"onlinenumber").toString());
		backBean.setDistanceTotal(((BasicDynaBean) list.get(0)).get("dailykm")
				.toString());
		return backBean;
	}
    
	/**
	 * 构造"显示所鼠标所指向点的日信息"的SQL
	 * @return 组织好的SQL
	 */
	public abstract String createOnlineInfoGivenDaySql();

	/**
	 * 得到具体某个时段的ToolTip信息
	 * 
	 * @param givenDateAndTime
	 *            具体时段 如：9:00:00,实际上应选择08:30:00到09:00:00的信息（不包括09:00:00)
	 * @param userInfo
	 *            用户
	 * @return HisotryTimeInfoBean
	 */
	public HistoryTimeInfoBean getOnlineInfoGivenHour() {
		List listWillTurnToBean;
		sql = createOnlineInfoGivenHourSql(); // 组织SQL
		logger.info("显示所鼠标所指向点的相关时段信息SQL:" + sql);
		listWillTurnToBean = workInfoHistoryDAOImpl.queryInfo(sql);
		if (listWillTurnToBean == null || listWillTurnToBean.size() == 0) {
			logger.info("显示所鼠标所指向点的相关时段信息列表为空");
			return null;
		}
		HistoryTimeInfoBean backBean = new HistoryTimeInfoBean();
		backBean = organizeBean(listWillTurnToBean); // 组织Bean
		return backBean;
	}   

	/**
	 * 
	 * @param list 传入的即将转化成HistoryTimeInfoBean的list，市代维用户与其它用户的list内部结构不同
	 * @return HistoryTimeInfoBean 
	 */
	public HistoryTimeInfoBean organizeBean(List list) {
		HistoryTimeInfoBean bean = new HistoryTimeInfoBean();
		setBackBeanValue(list, bean); //设置返回bean值（市代维的BO子类重写了这个方法）
		bean.setIntersectPoint(bean.getFinalIntersectPoint(boParam)); // 置时间段范围入bean
		return bean;
	}
	
	/**
	 * 设置返回bean值（适应于非市代维用户）
	 * @param list 传入的即将转化成HistoryTimeInfoBean的list，市代维用户与其它用户的list内部结构不同
	 * @param bean 用户界面上查询时输入的条件bean
	 */
	public void setBackBeanValue(List list, HistoryTimeInfoBean bean) {
		int size = list.size();
		int smnum = 0;
		int onlinenum = 0;
		Map map = new HashMap();
		int smnumeachloop = 0;
		int onlinenumeachloop = 0;
		String rangename = "";
		for (int i = 0; i < size; i++) { // 通过此循环，得到“短信总数”及“在线人员总数”等信息
			smnumeachloop = Integer.parseInt(((BasicDynaBean) list.get(i)).get(
					"smtotalnum").toString());
			onlinenumeachloop = Integer.parseInt(((BasicDynaBean) list.get(i))
					.get("totalnumber").toString());
			rangename = ((BasicDynaBean) list.get(i)).get("rangename")
					.toString();
			smnum += smnumeachloop;
			onlinenum += onlinenumeachloop;
			map.put(rangename, String.valueOf(onlinenumeachloop));
		}
		bean.setOnlineStatList(map);
		bean.setSmTotal(String.valueOf(smnum)); // 置短信总数入bean
		bean.setOnlineNumber(String.valueOf(onlinenum)); // 置在线人数总数入bean

	}

	/**
	 * 构造"显示所鼠标所指向点的相关时段信息"的SQL
	 * @return 组织好的SQL
	 */
	public abstract String createOnlineInfoGivenHourSql();

	/**
	 * 得到短信信息（短信汇总和短信图例都使用之）
	 * 
	 * @param userInfo
	 *            用户信息。
	 * @param bean
	 *            查询界面输入条件bean,保存查询时用户输入的信息。
	 * @param givenDate
	 *            用户所选具体日期。
	 * @param smRangeID
	 *            省移动登录：其值要么为“11",要么为么各地ID，市移动登录：其值要么为“21",要么为该市各代维公司ID，
	 *            市代维登录：其值要么为“22",要么为用户所属代维公司下的巡检组ID，
	 * @return 返回短信信息列表，List类型
	 */
	public List getSMInfoAllType() {
		sql = createSMAllTypeSql();
		if ("0".equals(boParam.getGivenDate())) { // 如果没有选择具体某天，即为多天
			logger.info("起止日期内历史短信信息SQL:" + sql);
		} else {
			logger.info(boParam.getGivenDate() + "该天各时段历史短信信息SQL:" + sql);
		}
		List smAllTypeList = null;
		smAllTypeList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (smAllTypeList == null) {
			if ("0".equals(boParam.getGivenDate())) { // 如果没有选择具体某天，即为多天
				logger.info("起止日期内历史短信信息列表为空");
			} else {
				logger.info(boParam.getGivenDate() + "该天各时段历史短信信息列表为空");
			}
		}
		return smAllTypeList;
	}
  
	/**
	 * 构建得到各种类型短信SQL
	 * @return 组织好的SQL
	 */
	public abstract String createSMAllTypeSql();

	/**
	 * 勾子方法，只有巡检组BO子类用到并重写，得到SIM卡在一天中的某个时段是否在线的01图所需数据,返回Map
	 * @return 巡检组BO子类返回MAP，其它返回null
	 */
	public Map getFinal01GraphicMap() {
		return null;
	}

	/**
	 * 设置“历史曲线”所用到最终的rangeID
	 * @param rangeID 此ID是实体ID，即省移动ID，市移动ID，市代维公司ID或者巡检组ID
	 */
	public void setRangeID(String rangeID) {
		this.rangeID = rangeID;
	}

	/**
	 * 设置从context类中传来的封装后的参数
	 * @param boParam
	 */
	public void setBoParam(HistoryWorkInfoCreateBOParam boParam) {
		this.boParam = boParam;
	}
}
