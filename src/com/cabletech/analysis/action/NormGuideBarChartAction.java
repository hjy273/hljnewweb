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
	private static final String TITLE = "������ָ��ֵ\n ά�����ȣ������";
	private static final String NORM_VALUE = "��׼ֵ";
	private static final String DARE_VALUE = "��սֵ";
	private static final String INTERDICTION_TIME = "ά����ʱ��";
	private static final String INTERDICTION_TIMES = "���ϴ���";
	private static final String AVG_TIME = "����ƽ������ʱ����Сʱ��";
	private static final String CITY_AREA_OUT_STANDARD_NUMBER = "�������޳�ʱ����";
	private static final String FINISH_VALUE="ָ�����ֵ";
	private int   m_numBefore=DateUtil.getElapseMonthNum(); //ǰ5���£�����������
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
	 * ����ר��ͼ���������
	 */
	public BarChartBean dataSetType(UserInfo userInfo,HttpSession session,int CategoryType) {
		String contractorId=null;
		if(userInfo.getDeptype().equals("2")){
			contractorId=userInfo.getDeptID();
		}
		BarChartBean barChartBean =new BarChartBean();
		if (CategoryType<21){ //�����ϵ�չ�ַ�ʽ  modify by xueyh 201105 
			setCategoryColor(contractorId,barChartBean);
			setCategoryValues(contractorId,barChartBean);
		}else if (CategoryType==21){
			setCategoryColor_Common(barChartBean);			
			setCategoryValues_Common(contractorId,barChartBean,
					"ǧ������ϴ���(��)ָ��","sumResultMap","standard_times","dare_times","trouble_times");
		}else if(CategoryType==22){
			setCategoryColor_Common(barChartBean);
			setCategoryValues_Common(contractorId,barChartBean,
					"ǧ�������ʱ��(Сʱ)ָ��","sumResultMap","standard_time","dare_time","trouble_time");
		}else if(CategoryType==23){
			setCategoryColor_Common2(barChartBean);
			setCategoryValues_Avg(contractorId,barChartBean,
					"��������ƽ����ʱ(Сʱ)ָ�� ","sumResultMap","avg_time");
		}
		return barChartBean;
	}

	/**
	 * ������ֵ����
	 * @param contractorId  �û�id
	 * @param barChartBean  ר��ͼ
	 * @param title         ר��ͼ��������
	 * @param name_jh       ����������� 
	 * @param name_norm     ָ������ 
	 * @param name_dare     ��ս����
	 * @param name_finish   �������
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
		barChartBean.setMonthTitle(m_numBefore,title+"  "+lastLength+"����");
	}
	
	/**
	 * ����ƽ������ʱ�� ��ֵ����
	 * @param contractorId  �û�id
	 * @param barChartBean  ר��ͼ
	 * @param title         ר��ͼ��������
	 * @param name_jh       ����������� 
	 * @param name_finish   �������
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
		barChartBean.setMonthTitle(m_numBefore,title+"  "+lastLength+"����");
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
		normGuideBarChartBean.setCategoryTitle("ǧ������ϴ���(��)ָ��");
		normGuideBarChartBean.setDareValue((Double) normGuide.getInterdictionTimesDareValue());
		normGuideBarChartBean.setNormValue((Double)normGuide.getInterdictionTimesNormValue());
		setCategoryValue(barChartBean, normGuideBarChartBean);

		normGuideBarChartBean = new NormGuideBarChartBean();
		normGuideBarChartBean.setCategoryTitle("ǧ�������ʱ��(Сʱ)ָ��");
		normGuideBarChartBean.setDareValue((Double) normGuide.getInterdictionTimeDareValue());
		normGuideBarChartBean.setNormValue((Double) normGuide.getInterdictionTimeNormValue());
		setCategoryValue(barChartBean, normGuideBarChartBean);

		normGuideBarChartBean = new NormGuideBarChartBean();
		normGuideBarChartBean.setCategoryTitle("����ÿ�ι���ƽ����ʱ(Сʱ)ָ��");
		normGuideBarChartBean.setDareValue((Double) normGuide.getRtrTimeDareValue());
		normGuideBarChartBean.setNormValue((Double) normGuide.getRtrTimeNormValue());
		setCategoryValue(barChartBean, normGuideBarChartBean);

		normGuideBarChartBean = new NormGuideBarChartBean();
		normGuideBarChartBean.setCategoryTitle("������ʱ��(Сʱ)��");
		normGuideBarChartBean
				.setInterdictionTime((Double) ((Map) normGuideMap.get("sumResultMap")).get("trouble_time") / 100);
		setCategoryValue(barChartBean, normGuideBarChartBean);

		normGuideBarChartBean = new NormGuideBarChartBean();
		normGuideBarChartBean.setCategoryTitle("���ϴ�����");
		normGuideBarChartBean.setInterdictionTimes((Double) ((Map) normGuideMap.get("sumResultMap"))
				.get("trouble_times"));
		setCategoryValue(barChartBean, normGuideBarChartBean);

		normGuideBarChartBean = new NormGuideBarChartBean();
		normGuideBarChartBean.setCategoryTitle("����ƽ������ʱ��(Сʱ)��");
		normGuideBarChartBean.setAvgTime((Double) ((Map) normGuideMap.get("sumResultMap")).get("avg_time"));
		setCategoryValue(barChartBean, normGuideBarChartBean);

		normGuideBarChartBean = new NormGuideBarChartBean();
		normGuideBarChartBean.setCategoryTitle("�������޳�ʱ������");
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
