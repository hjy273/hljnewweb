package com.cabletech.planstat.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.chart.parameter.ChartParameter;
import com.cabletech.commons.chart.utils.ChartUtils;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.planstat.beans.MonthlyStatCityMobileConBean;
import com.cabletech.planstat.dao.MonthlyStatCityMobileDAOImpl;
import com.cabletech.planstat.dao.PlanStatBaseDAO;

/**
 * 市移动月统计的业务逻辑类
 */
public class WholeNetStatBO extends BaseBisinessObject {
	private Logger logger = Logger.getLogger(this.getClass().getName()); // 建立logger输出对象;

	public static final int YEAR_NUMBER = 5;
	private PlanStatBaseDAO dao = new MonthlyStatCityMobileDAOImpl();

	private String sql = "";

	private List lineTypeList;

	public JFreeChart createLineLevelMonthPatrolRateChart(
			MonthlyStatCityMobileConBean bean) {
		// TODO Auto-generated method stub
		//String sql = " select lc.lable as name,'0' as patrolp from dictionary_formitem lc ";
		//sql += " where lc.assortment_id='cabletype' order by lc.code ";
	    String sql = " select lc.name,'0' as patrolp from lineclassdic lc ";
        sql += " order by lc.code ";
		List allList = dao.queryInfo(sql);
		sql = " select lc.name, ";
		sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') ";
		sql += " from plan_sublinestat pss,sublineinfo si,lineinfo li,lineclassdic lc ";
		sql += " where pss.sublineid=si.sublineid and si.lineid=li.lineid and li.linetype=lc.code ";
		sql += " and pss.factdate=to_date('";
		sql += bean.getEndYear();
		sql += "-";
		sql += bean.getEndMonth();
		sql += "-1','yyyy-mm-dd') ";
		sql += " group by lc.code,lc.name ";
		sql += " order by lc.code ";
		List list = dao.queryInfo(sql);
		list = processList(allList, list);
		ChartParameter parameter = new ChartParameter();
		parameter.setChartType(ChartParameter.BAR_CHART_TYPE);
		parameter.setTitle(bean.getEndYear() + "年" + bean.getEndMonth()
				+ "月各线路级别巡检率对比分析");
		parameter.setXTitle("线路级别");
		parameter.setYTitle("巡检率");
		List chartColors = new ArrayList();
		List chartLabels = new ArrayList();
		chartColors.add(ChartParameter.REF_COLORS[1]);
		chartLabels.add("巡检率");
		parameter.setChartColors(chartColors);
		parameter.setChartLabels(chartLabels);
		parameter.setLabelDisplayFlag(true);
		parameter.setDataSet(ChartUtils.createDataSet(list, parameter));
		parameter.setYMinValue(ChartParameter.Y_MIN_RATE_VALUE);
		parameter.setYMaxValue(ChartParameter.Y_MAX_RATE_VALUE);
		JFreeChart chart = ChartUtils.generateBarChart(parameter);
		return chart;
	}

	public JFreeChart createLineLevelYearPatrolRateChart(
			MonthlyStatCityMobileConBean bean) {
		// TODO Auto-generated method stub
		String sql = " select lc.name,'0' as patrolp from lineclassdic lc ";
		sql += " order by lc.code ";
		List allList = dao.queryInfo(sql);
		sql = " select lc.name, ";
		sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') ";
		sql += " from plan_sublinestat pss,sublineinfo si,lineinfo li,lineclassdic lc ";
		sql += " where pss.sublineid=si.sublineid and si.lineid=li.lineid and li.linetype=lc.code ";
		sql += " and to_char(pss.factdate,'YYYY')='";
		sql += bean.getEndYear();
		sql += "' ";
		sql += " group by lc.code,lc.name ";
		sql += " order by lc.code ";
		List list = dao.queryInfo(sql);
		list = processList(allList, list);
		ChartParameter parameter = new ChartParameter();
		parameter.setChartType(ChartParameter.BAR_CHART_TYPE);
		parameter.setTitle(bean.getEndYear() + "年各线路级别巡检率对比分析");
		parameter.setXTitle("线路级别");
		parameter.setYTitle("巡检率");
		List chartColors = new ArrayList();
		List chartLabels = new ArrayList();
		chartColors.add(ChartParameter.REF_COLORS[1]);
		chartLabels.add("巡检率");
		parameter.setChartColors(chartColors);
		parameter.setChartLabels(chartLabels);
		parameter.setLabelDisplayFlag(true);
		parameter.setDataSet(ChartUtils.createDataSet(list, parameter));
		parameter.setYMinValue(ChartParameter.Y_MIN_RATE_VALUE);
		parameter.setYMaxValue(ChartParameter.Y_MAX_RATE_VALUE);
		JFreeChart chart = ChartUtils.generateBarChart(parameter);
		return chart;
	}

	public JFreeChart createLineLevelDifferentMonthPatrolRateChart(
			MonthlyStatCityMobileConBean bean) {
		// TODO Auto-generated method stub
		String sql = " select lc.code,lc.name,'0' as patrolp from lineclassdic lc ";
		sql += " order by lc.code ";
		lineTypeList = dao.queryInfo(sql);
		sql = " select lpad(level,2,0),'0' as patrolp from dual ";
		sql += " connect by level<13 ";
		List allList = dao.queryInfo(sql);
		String title = bean.getEndYear() + "年各线路级别每月巡检率对比分析";
		String xTitle = "月份";
		DynaBean lineTypeBean;
		List list = new ArrayList();
		List resultList = new ArrayList();
		for (int i = 0; lineTypeList != null && i < lineTypeList.size(); i++) {
			lineTypeBean = (DynaBean) lineTypeList.get(i);
			sql = " select to_char(pss.factdate,'MM'), ";
			sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') ";
			sql += " from plan_sublinestat pss,sublineinfo si,lineinfo li,lineclassdic lc ";
			sql += " where pss.sublineid=si.sublineid and si.lineid=li.lineid and li.linetype=lc.code ";
			sql += " and to_char(pss.factdate,'YYYY')='";
			sql += bean.getEndYear();
			sql += "' ";
			sql += " and lc.code='" + lineTypeBean.get("code") + "' ";
			sql += " group by to_char(pss.factdate,'MM') ";
			sql += " order by to_number(to_char(pss.factdate,'MM')) ";
			list = dao.queryInfo(sql);
			resultList.addAll(processList(allList, list));
		}
		return createLineTypeAnalyseChart(bean, title, xTitle, resultList);
	}

	public JFreeChart createLineLevelFiveYearPatrolRateChart(
			MonthlyStatCityMobileConBean bean) {
		// TODO Auto-generated method stub
		String sql = " select lc.code,lc.name,'0' as patrolp from lineclassdic lc ";
		sql += " order by lc.code ";
		lineTypeList = dao.queryInfo(sql);
		GregorianCalendar calendar = new GregorianCalendar();
		int nowYear = calendar.get(GregorianCalendar.YEAR);
		if (bean.getEndYear() != null) {
			nowYear = Integer.parseInt(bean.getEndYear());
		}
		sql = " select to_char(" + nowYear
				+ "-level+1) as year,'0' as patrolp from dual ";
		sql += " connect by level<=" + YEAR_NUMBER + " ";
		sql += " order by level desc ";
		List allList = dao.queryInfo(sql);
		String title = "最近五年各线路级别" + bean.getEndMonth() + "月巡检率对比分析";
		String xTitle = "年份";
		DynaBean lineTypeBean;
		List list = new ArrayList();
		List resultList = new ArrayList();
		for (int i = 0; lineTypeList != null && i < lineTypeList.size(); i++) {
			lineTypeBean = (DynaBean) lineTypeList.get(i);
			sql = " select to_char(pss.factdate,'YYYY'), ";
			sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') ";
			sql += " from plan_sublinestat pss,sublineinfo si,lineinfo li,lineclassdic lc ";
			sql += " where pss.sublineid=si.sublineid and si.lineid=li.lineid and li.linetype=lc.code ";
			sql += " and to_number(to_char(pss.factdate,'MM'))=";
			sql += bean.getEndMonth() + " ";
			sql += " and to_number(to_char(pss.factdate,'YYYY'))<=";
			sql += bean.getEndYear() + " ";
			sql += " and lc.code='" + lineTypeBean.get("code") + "' ";
			sql += " group by to_char(pss.factdate,'YYYY') ";
			sql += " order by to_number(to_char(pss.factdate,'YYYY')) ";
			list = dao.queryInfo(sql);
			resultList.addAll(processList(allList, list));
		}
		return createLineTypeAnalyseChart(bean, title, xTitle, resultList);
	}

	public JFreeChart createLineLevelInputTimeAreaPatrolRateChart(
			MonthlyStatCityMobileConBean bean) throws Exception {
		// TODO Auto-generated method stub
		String sql = " select lc.code,lc.name,'0' as patrolp from lineclassdic lc ";
		sql += " order by lc.code ";
		lineTypeList = dao.queryInfo(sql);
		List allList = new ArrayList();
		getAllTimeList(bean, allList);
		String title = "从" + bean.getBeginTime() + "到" + bean.getEndTime();
		title += "各线路级别巡检率对比分析";
		String xTitle = "时间";
		DynaBean lineTypeBean;
		List list = new ArrayList();
		List resultList = new ArrayList();
		for (int i = 0; lineTypeList != null && i < lineTypeList.size(); i++) {
			lineTypeBean = (DynaBean) lineTypeList.get(i);
			sql = " select to_char(pss.factdate,'YYYY-MM'), ";
			sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') ";
			sql += " from plan_sublinestat pss,sublineinfo si,lineinfo li,lineclassdic lc ";
			sql += " where pss.sublineid=si.sublineid and si.lineid=li.lineid and li.linetype=lc.code  ";
			sql += " and pss.factdate>=to_date('";
			sql += bean.getBeginTime();
			sql += "','yyyy-mm') ";
			sql += " and pss.factdate<=to_date('";
			sql += bean.getEndTime();
			sql += "','yyyy-mm') ";
			sql += " and lc.code='" + lineTypeBean.get("code") + "' ";
			sql += " group by to_char(pss.factdate,'YYYY-MM') ";
			sql += " order by to_char(pss.factdate,'YYYY-MM') ";
			list = dao.queryInfo(sql);
			resultList.addAll(processList(allList, list));
		}
		return createLineTypeAnalyseChart(bean, title, xTitle, resultList);
	}

	public void getAllTimeList(MonthlyStatCityMobileConBean bean, List allList)
			throws Exception {
		DynaProperty[] props = new DynaProperty[] {
				new DynaProperty("month", String.class),
				new DynaProperty("patrolp", String.class) };
		BasicDynaClass dynaClass = new BasicDynaClass("", null, props);
		DynaBean dynaBean = dynaClass.newInstance();
		GregorianCalendar calendar = new GregorianCalendar();
		String beginTimeStr = bean.getBeginTime();
		String endTimeStr = bean.getEndTime();
		calendar.set(GregorianCalendar.YEAR, Integer.parseInt(beginTimeStr
				.split("/")[0]));
		calendar.set(GregorianCalendar.MONTH, Integer.parseInt(beginTimeStr
				.split("/")[1]) - 1);
		calendar.set(GregorianCalendar.DATE, 1);
		Date beginTime = calendar.getTime();
		calendar.set(GregorianCalendar.YEAR, Integer.parseInt(endTimeStr
				.split("/")[0]));
		calendar.set(GregorianCalendar.MONTH, Integer.parseInt(endTimeStr
				.split("/")[1]) - 1);
		calendar.set(GregorianCalendar.DATE, 1);
		Date endTime = calendar.getTime();
		Date time = beginTime;
		String timeStr = "";
		while (time.compareTo(endTime) <= 0) {
			dynaBean = dynaClass.newInstance();
			calendar.setTime(time);
			timeStr = DateUtil.DateToString(time, "yyyy-MM");
			dynaBean.set("month", timeStr);
			dynaBean.set("patrolp", "0");
			allList.add(dynaBean);
			calendar.add(GregorianCalendar.MONTH, 1);
			time = calendar.getTime();
		}
	}

	public List processList(List allList, List tmpList) {
		List list = new ArrayList();
		DynaBean bean;
		DynaProperty[] properties;
		String key = "";
		String value = "";
		Map map = new LinkedHashMap();
		for (int i = 0; allList != null && i < allList.size(); i++) {
			bean = (DynaBean) allList.get(i);
			properties = bean.getDynaClass().getDynaProperties();
			if (properties != null && properties.length >= 2) {
				key = (String) bean.get(properties[0].getName());
				value = (String) bean.get(properties[1].getName());
				map.put(key, value);
			}
		}
		for (int i = 0; tmpList != null && i < tmpList.size(); i++) {
			bean = (DynaBean) tmpList.get(i);
			properties = bean.getDynaClass().getDynaProperties();
			if (properties != null && properties.length >= 2) {
				key = (String) bean.get(properties[0].getName());
				value = (String) bean.get(properties[1].getName());
				map.put(key, value);
			}
		}
		list.add(map);
		return list;
	}

	public JFreeChart createLineTypeAnalyseChart(
			MonthlyStatCityMobileConBean bean, String title, String xTitle,
			List list) {
		ChartParameter parameter = new ChartParameter();
		parameter.setChartType(ChartParameter.LINE_CHART_TYPE);
		parameter.setTitle(title);
		parameter.setXTitle(xTitle);
		parameter.setYTitle("巡检率");
		List chartColors = new ArrayList();
		List chartLabels = new ArrayList();
		DynaBean lineTypeBean;
		for (int i = 0; lineTypeList != null && i < lineTypeList.size(); i++) {
			lineTypeBean = (DynaBean) lineTypeList.get(i);
			chartColors.add(ChartParameter.REF_COLORS[i]);
			chartLabels.add(lineTypeBean.get("name"));
		}
		parameter.setChartColors(chartColors);
		parameter.setChartLabels(chartLabels);
		parameter.setLabelDisplayFlag(true);
		parameter.setDataSet(ChartUtils.createDataSet(list, parameter));
		parameter.setYMinValue(ChartParameter.Y_MIN_RATE_VALUE);
		parameter.setYMaxValue(ChartParameter.Y_MAX_RATE_VALUE);
		JFreeChart chart = ChartUtils.generateLineChart(parameter);
		return chart;
	}

	public DynaBean showMonthGeneralInfo(MonthlyStatCityMobileConBean bean,
			UserInfo userInfo) {
		// TODO Auto-generated method stub
		String sql = " select decode(sum(pss.planpoint),null,0,sum(pss.planpoint)) as planpoint, ";
		sql += " decode(sum(pss.factpoint),null,0,sum(pss.factpoint)) as factpoint, ";
		sql += " decode(sum(pss.sublinekm),null,0,sum(pss.sublinekm)) as sublinekm, ";
		sql += " decode(sum(pss.factkm),null,0,sum(pss.factkm)) as patrolkm, ";
		sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') as patrolp ";
		sql += " from plan_sublinestat pss,sublineinfo si,lineinfo li,lineclassdic lc ";
		sql += " where pss.sublineid=si.sublineid and si.lineid=li.lineid and li.linetype=lc.code ";
		sql += " and pss.factdate>=to_date('";
		sql += bean.getEndYear();
		sql += "-";
		sql += bean.getEndMonth();
		sql += "','yyyy-mm') ";
		sql += " and pss.factdate<=to_date('";
		sql += bean.getEndYear();
		sql += "-";
		sql += bean.getEndMonth();
		sql += "','yyyy-mm') ";
		sql += " order by to_char(pss.factdate,'YYYY-MM') ";
		List list = dao.queryInfo(sql);
		if (list != null && !list.isEmpty()) {
			return (DynaBean) list.get(0);
		}
		return null;
	}

	public List showMonthExecuteResultInfo(MonthlyStatCityMobileConBean bean,
			UserInfo userInfo) {
		// TODO Auto-generated method stub
		String sql = " select lc.code,lc.name, ";
		sql += " decode(sum(pss.planpoint),null,0,sum(pss.planpoint)) as planpoint, ";
		sql += " decode(sum(pss.factpoint),null,0,sum(pss.factpoint)) as factpoint, ";
		sql += " decode(sum(pss.sublinekm),null,0,sum(pss.sublinekm)) as sublinekm, ";
		sql += " decode(sum(pss.factkm),null,0,sum(pss.factkm)) as patrolkm, ";
		sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') as patrolp ";
		sql += " from plan_sublinestat pss,sublineinfo si,lineinfo li,lineclassdic lc ";
		sql += " where pss.sublineid=si.sublineid and si.lineid=li.lineid and li.linetype=lc.code ";
		sql += " and pss.factdate>=to_date('";
		sql += bean.getEndYear();
		sql += "-";
		sql += bean.getEndMonth();
		sql += "','yyyy-mm') ";
		sql += " and pss.factdate<=to_date('";
		sql += bean.getEndYear();
		sql += "-";
		sql += bean.getEndMonth();
		sql += "','yyyy-mm') ";
		sql += " group by lc.code,lc.name ";
		sql += " order by lc.code ";
		List list = dao.queryInfo(sql);
		return list;
	}

	public DynaBean showWholeYearGeneralInfo(MonthlyStatCityMobileConBean bean,
			UserInfo userInfo) {
		// TODO Auto-generated method stub
		String sql = " select decode(sum(pss.planpoint),null,0,sum(pss.planpoint)) as planpoint, ";
		sql += " decode(sum(pss.factpoint),null,0,sum(pss.factpoint)) as factpoint, ";
		sql += " decode(sum(pss.sublinekm),null,0,sum(pss.sublinekm)) as sublinekm, ";
		sql += " decode(sum(pss.factkm),null,0,sum(pss.factkm)) as patrolkm, ";
		sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') as patrolp ";
		sql += " from plan_sublinestat pss,sublineinfo si,lineinfo li,lineclassdic lc ";
		sql += " where pss.sublineid=si.sublineid and si.lineid=li.lineid and li.linetype=lc.code ";
		sql += " and to_char(pss.factdate,'yyyy')='" + bean.getEndYear()
				+ "' ";
		List list = dao.queryInfo(sql);
		if (list != null && !list.isEmpty()) {
			return (DynaBean) list.get(0);
		}
		return null;
	}

	public List showWholeYearLineTypeExecuteResult(
			MonthlyStatCityMobileConBean bean, UserInfo userInfo) {
		// TODO Auto-generated method stub
		String sql = " select lc.code,lc.name, ";
		sql += " decode(sum(pss.planpoint),null,0,sum(pss.planpoint)) as planpoint, ";
		sql += " decode(sum(pss.factpoint),null,0,sum(pss.factpoint)) as factpoint, ";
		sql += " decode(sum(pss.sublinekm),null,0,sum(pss.sublinekm)) as sublinekm, ";
		sql += " decode(sum(pss.factkm),null,0,sum(pss.factkm)) as patrolkm, ";
		sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') as patrolp ";
		sql += " from plan_sublinestat pss,sublineinfo si,lineinfo li,lineclassdic lc ";
		sql += " where pss.sublineid=si.sublineid and si.lineid=li.lineid and li.linetype=lc.code ";
		sql += " and to_char(pss.factdate,'yyyy')='" + bean.getEndYear()
				+ "' ";
		sql += " group by lc.code,lc.name ";
		sql += " order by lc.code ";
		List list = dao.queryInfo(sql);
		return list;
	}

	public List showMonthLayingMethodExecuteResultInfo(
			MonthlyStatCityMobileConBean bean, UserInfo userInfo) {
		// TODO Auto-generated method stub
		String sql = " select lc.code,lc.lable as name, ";
		sql += " decode(sum(pss.planpoint),null,0,sum(pss.planpoint)) as planpoint, ";
		sql += " decode(sum(pss.factpoint),null,0,sum(pss.factpoint)) as factpoint, ";
		sql += " decode(sum(pss.sublinekm),null,0,sum(pss.sublinekm)) as sublinekm, ";
		sql += " decode(sum(pss.factkm),null,0,sum(pss.factkm)) as patrolkm, ";
		sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') as patrolp ";
		sql += " from plan_sublinestat pss,sublineinfo si,dictionary_formitem lc ";
		sql += " where pss.sublineid=si.sublineid and si.linetype=lc.code and lc.assortment_id='layingmethod'";
		sql += " and pss.factdate>=to_date('";
		sql += bean.getEndYear();
		sql += "-";
		sql += bean.getEndMonth();
		sql += "','yyyy-mm') ";
		sql += " and pss.factdate<=to_date('";
		sql += bean.getEndYear();
		sql += "-";
		sql += bean.getEndMonth();
		sql += "','yyyy-mm') ";
		sql += " group by lc.code,lc.lable ";
		sql += " order by lc.code ";
		List list = dao.queryInfo(sql);
		return list;
	}

	public List showYearLayingMethodExecuteResultInfo(
			MonthlyStatCityMobileConBean bean, UserInfo userInfo) {
		// TODO Auto-generated method stub
		String sql = " select lc.code,lc.lable as name, ";
		sql += " decode(sum(pss.planpoint),null,0,sum(pss.planpoint)) as planpoint, ";
		sql += " decode(sum(pss.factpoint),null,0,sum(pss.factpoint)) as factpoint, ";
		sql += " decode(sum(pss.sublinekm),null,0,sum(pss.sublinekm)) as sublinekm, ";
		sql += " decode(sum(pss.factkm),null,0,sum(pss.factkm)) as patrolkm, ";
		sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') as patrolp ";
		sql += " from plan_sublinestat pss,sublineinfo si,dictionary_formitem lc ";
		sql += " where pss.sublineid=si.sublineid and si.linetype=lc.code and lc.assortment_id='layingmethod'";
		sql += " and to_char(pss.factdate,'yyyy')='" + bean.getEndYear()
				+ "' ";
		sql += " group by lc.code,lc.lable ";
		sql += " order by lc.code ";
		List list = dao.queryInfo(sql);
		return list;
	}

}
