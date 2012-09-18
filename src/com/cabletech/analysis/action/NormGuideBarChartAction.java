package com.cabletech.analysis.action;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;

import com.cabletech.analysis.beans.BarChartBean;
import com.cabletech.analysis.beans.NormGuideBarChartBean;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.trouble.module.TroubleNormGuide;
import com.cabletech.linepatrol.trouble.services.TroubleQuotaBO;

public class NormGuideBarChartAction extends BarChartAction {
	private static final String TITLE = "城域网指标值\n 维护长度（公里）：";
	private static final String NORM_VALUE = "基准值";
	private static final String DARE_VALUE = "挑战值";
	private static final String INTERDICTION_TIME = "维护总时长";
	private static final String INTERDICTION_TIMES = "故障次数";
	private static final String AVG_TIME = "故障平均抢修时长（小时）";
	private static final String CITY_AREA_OUT_STANDARD_NUMBER = "故障抢修超时次数";
	private static final String FINISH_VALUE="指标完成值";
	private int   m_numBefore=DateUtil.getElapseMonthNum(); //前5个月，不包含本月
	@Override
	public BarChartBean dataSet(UserInfo userInfo,HttpSession session) {
		String contractorId=null;
		if(userInfo.getDeptype().equals("2")){
			contractorId=userInfo.getDeptID();
		}
		BarChartBean barChartBean = new BarChartBean();
		setCategoryColor(contractorId,barChartBean);
		setCategoryValues(contractorId,barChartBean);

		return barChartBean;
	}
	
	/**
	 * 考虑专题图分组的类型
	 */
	public BarChartBean dataSetType(UserInfo userInfo,HttpSession session,int CategoryType) {
		String contractorId=null;
		if(userInfo.getDeptype().equals("2")){
			contractorId=userInfo.getDeptID();
		}
		BarChartBean barChartBean =new BarChartBean();
		if (CategoryType<21){ //保留老的展现方式  modify by xueyh 201105 
			setCategoryColor(contractorId,barChartBean);
			setCategoryValues(contractorId,barChartBean);
		}else if (CategoryType==21){
			setCategoryColor_Common(barChartBean);			
			setCategoryValues_Common(contractorId,barChartBean,
					"千公里阻断次数(次)指标","sumResultMap","standard_times","dare_times","trouble_times");
		}else if(CategoryType==22){
			setCategoryColor_Common(barChartBean);
			setCategoryValues_Common(contractorId,barChartBean,
					"千公里阻断时长(小时)指标","sumResultMap","standard_time","dare_time","trouble_time");
		}else if(CategoryType==23){
			setCategoryColor_Common2(barChartBean);
			setCategoryValues_Avg(contractorId,barChartBean,
					"光缆抢修平均历时(小时)指标 ","sumResultMap","avg_time");
		}
		return barChartBean;
	}

	/**
	 * 公共赋值函数
	 * @param contractorId  用户id
	 * @param barChartBean  专题图
	 * @param title         专题图标题名称
	 * @param name_jh       结果集合名称 
	 * @param name_norm     指标名称 
	 * @param name_dare     挑战名称
	 * @param name_finish   完成名称
	 */
	private void setCategoryValues_Common(String contractorId,BarChartBean barChartBean,String title ,String name_jh,String name_norm,String name_dare,String name_finish) {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		String startTime1="";
		String endTime1="";
		double lastLength=0;
		Calendar tmpCalendar =DateUtil.getBeforeMonths(m_numBefore);
		Map normGuideMap =null;
		for(int i=1;i<m_numBefore+1;i++)
		{
			startTime1 = DateUtil.DateToString(tmpCalendar.getTime(), "yyyy/MM");
			tmpCalendar.add(tmpCalendar.MONTH,1);
			endTime1 = DateUtil.DateToString(tmpCalendar.getTime(), "yyyy/MM");
//			normGuideMap = bo.getTimeAreaTroubleQuotaList(contractorId, "0", startTime1, endTime1,"");
			normGuideMap = bo.getTimeAreaTroubleQuotaList(contractorId, "0", startTime1, endTime1);
			setCategoryValue_Common(barChartBean,
					startTime1,
					(Double) ((Map)normGuideMap.get(name_jh)).get(name_norm),
					(Double) ((Map)normGuideMap.get(name_jh)).get(name_dare),
					(Double) ((Map) normGuideMap.get(name_jh)).get(name_finish));
			lastLength=(Double) ((Map) normGuideMap.get(name_jh)).get("maintenance_length") ;
		}
		barChartBean.setMonthTitle(m_numBefore,title+"  "+lastLength+"公里");
	}
	
	/**
	 * 故障平均抢修时间 赋值函数
	 * @param contractorId  用户id
	 * @param barChartBean  专题图
	 * @param title         专题图标题名称
	 * @param name_jh       结果集合名称 
	 * @param name_finish   完成名称
	 */
	private void setCategoryValues_Avg(String contractorId,BarChartBean barChartBean,String title ,String name_jh,String name_finish) {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		String startTime1="";
		String endTime1="";
		TroubleNormGuide normGuide=null;
		double lastLength=0;
		Calendar tmpCalendar = DateUtil.getBeforeMonths(m_numBefore);
		Map normGuideMap =null;
		for(int i=1;i<m_numBefore+1;i++)
		{
			startTime1 = DateUtil.DateToString(tmpCalendar.getTime(), "yyyy/MM");
			tmpCalendar.add(tmpCalendar.MONTH,1);
			endTime1 = DateUtil.DateToString(tmpCalendar.getTime(), "yyyy/MM");
//			normGuideMap = bo.getTimeAreaTroubleQuotaList(contractorId, "0", startTime1, endTime1,"");
			normGuideMap = bo.getTimeAreaTroubleQuotaList(contractorId, "0", startTime1, endTime1);
			normGuide= bo.getQuotaByType("0");
			setCategoryValue_Common2(barChartBean,
					startTime1,
					(Double) normGuide.getRtrTimeNormValue(),
					(Double) ((Map) normGuideMap.get(name_jh)).get(name_finish));
			lastLength=(Double) ((Map) normGuideMap.get(name_jh)).get("maintenance_length") ;
		}
		barChartBean.setMonthTitle(m_numBefore,title+"  "+lastLength+"公里");
	}
		


					
	private void setCategoryColor(String contractorId,BarChartBean barChartBean) {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		String endTime = DateUtil.DateToString(calendar.getTime(), "yyyy/MM");
		Map normGuideMap = bo.getTimeAreaTroubleQuotaList(contractorId, "0", year + "/01", endTime);
		setCategoryColor(barChartBean, DARE_VALUE, Color.red);
		setCategoryColor(barChartBean, NORM_VALUE, Color.blue);
		setCategoryColor(barChartBean, INTERDICTION_TIME, Color.green);
		setCategoryColor(barChartBean, INTERDICTION_TIMES, Color.yellow);
		setCategoryColor(barChartBean, AVG_TIME, Color.orange);
		setCategoryColor(barChartBean, CITY_AREA_OUT_STANDARD_NUMBER, Color.pink);
		barChartBean.setTitle(endTime+TITLE + ((Map) normGuideMap.get("sumResultMap")).get("maintenance_length"));
	}

	private void setCategoryValues(String contractorId,BarChartBean barChartBean) {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		String endTime = DateUtil.DateToString(calendar.getTime(), "yyyy/MM");
		Map normGuideMap = bo.getTimeAreaTroubleQuotaList(contractorId, "0", year + "/01", endTime);
		TroubleNormGuide normGuide= bo.getQuotaByType("0");
		NormGuideBarChartBean normGuideBarChartBean = new NormGuideBarChartBean();
		normGuideBarChartBean.setCategoryTitle("千公里阻断次数(次)指标");
		normGuideBarChartBean.setDareValue((Double) normGuide.getInterdictionTimesDareValue());
		normGuideBarChartBean.setNormValue((Double)normGuide.getInterdictionTimesNormValue());
		setCategoryValue(barChartBean, normGuideBarChartBean);

		normGuideBarChartBean = new NormGuideBarChartBean();
		normGuideBarChartBean.setCategoryTitle("千公里阻断时长(小时)指标");
		normGuideBarChartBean.setDareValue((Double) normGuide.getInterdictionTimeDareValue());
		normGuideBarChartBean.setNormValue((Double) normGuide.getInterdictionTimeNormValue());
		setCategoryValue(barChartBean, normGuideBarChartBean);

		normGuideBarChartBean = new NormGuideBarChartBean();
		normGuideBarChartBean.setCategoryTitle("光缆每次故障平均历时(小时)指标");
		normGuideBarChartBean.setDareValue((Double) normGuide.getRtrTimeDareValue());
		normGuideBarChartBean.setNormValue((Double) normGuide.getRtrTimeNormValue());
		setCategoryValue(barChartBean, normGuideBarChartBean);

		normGuideBarChartBean = new NormGuideBarChartBean();
		normGuideBarChartBean.setCategoryTitle("故障总时长(小时)：");
		normGuideBarChartBean
				.setInterdictionTime((Double) ((Map) normGuideMap.get("sumResultMap")).get("trouble_time") / 100);
		setCategoryValue(barChartBean, normGuideBarChartBean);

		normGuideBarChartBean = new NormGuideBarChartBean();
		normGuideBarChartBean.setCategoryTitle("故障次数：");
		normGuideBarChartBean.setInterdictionTimes((Double) ((Map) normGuideMap.get("sumResultMap"))
				.get("trouble_times"));
		setCategoryValue(barChartBean, normGuideBarChartBean);

		normGuideBarChartBean = new NormGuideBarChartBean();
		normGuideBarChartBean.setCategoryTitle("故障平均抢修时长(小时)：");
		normGuideBarChartBean.setAvgTime((Double) ((Map) normGuideMap.get("sumResultMap")).get("avg_time"));
		setCategoryValue(barChartBean, normGuideBarChartBean);

		normGuideBarChartBean = new NormGuideBarChartBean();
		normGuideBarChartBean.setCategoryTitle("故障抢修超时次数：");
		normGuideBarChartBean.setCityAreaOutStandardNumber((Double) ((Map) normGuideMap.get("sumResultMap"))
				.get("city_area_out_standard_number"));
		setCategoryValue(barChartBean, normGuideBarChartBean);
	}

	private void setCategoryValue(BarChartBean barChartBean, NormGuideBarChartBean parameterObject) {
		List<Object> categoryValue = new ArrayList<Object>();
		Map<String, Double> categoryMap = new HashMap<String, Double>();
		categoryValue.add(parameterObject.getCategoryTitle());
		categoryMap.put(DARE_VALUE, parameterObject.getDareValue());
		categoryMap.put(NORM_VALUE, parameterObject.getNormValue());
		categoryMap.put(INTERDICTION_TIME, parameterObject.getInterdictionTime());
		categoryMap.put(INTERDICTION_TIMES, parameterObject.getInterdictionTimes());
		categoryMap.put(AVG_TIME, parameterObject.getAvgTime());
		categoryMap.put(CITY_AREA_OUT_STANDARD_NUMBER, parameterObject.getCityAreaOutStandardNumber());
		categoryValue.add(categoryMap);
		barChartBean.addCategoryValues(categoryValue);
	}

}
