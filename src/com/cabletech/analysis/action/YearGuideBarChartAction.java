package com.cabletech.analysis.action;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;

import com.cabletech.analysis.beans.BarChartBean;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.trouble.services.TroubleQuotaBO;

public class YearGuideBarChartAction extends BarChartAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int  m_numBefore=5; //���5��
	private String  m_guideType="0"; //0 ������ ;1 һ��
	@Override
	public BarChartBean dataSet(UserInfo userInfo, HttpSession session) {
		String contractorId=null;
		if(userInfo.getDeptype().equals("2")){
			contractorId=userInfo.getDeptID();
		}
		BarChartBean barChartBean = new BarChartBean();
/*		setCategoryColor(contractorId,barChartBean);
		setCategoryValues(contractorId,barChartBean);*/

		return barChartBean;
	}

	@Override
	public BarChartBean dataSetType(UserInfo userInfo, HttpSession session,
			int CategoryType) {
		String contractorId=null;
		if(userInfo.getDeptype().equals("2")){
			contractorId=userInfo.getDeptID();
		}
		BarChartBean barChartBean = new BarChartBean();
		if (CategoryType<21){
/*			setCategoryColor(contractorId,barChartBean);
			setCategoryValues(contractorId,barChartBean);*/
		}else if (CategoryType==21){
			m_guideType="0";
			setCategoryColor_Common(barChartBean);
			setCategoryValues_Common(contractorId,barChartBean,
					"ǧ������ϴ���(��)��ָ��","sumResultMap","standard_times","dare_times","trouble_times");
		}else if(CategoryType==22){
			m_guideType="0";
			setCategoryColor_Common(barChartBean);
			setCategoryValues_Common(contractorId,barChartBean,
					"ǧ�������ʱ��(Сʱ)��ָ��","sumResultMap","standard_time","dare_time","trouble_time");
		}else if(CategoryType==23){
			m_guideType="0";
			setCategoryColor_Common(barChartBean);
			setCategoryValues_Common(contractorId,barChartBean,
					"��������ƽ����ʱ(Сʱ)��ָ��","sumResultMap","standard_time","dare_time","trouble_time");
		}else if(CategoryType==100){
			m_guideType="1";
			setCategoryColor_Common(barChartBean);
			setCategoryValues_Common(contractorId,barChartBean,
					"һ��ǧ�������ʱ��(Сʱ)��ָ��","sumResultMap","standard_time","dare_time","trouble_time");
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
		double lastLength=0;
		Calendar tmpCalendar = DateUtil.getBeforeYears(this.m_numBefore);
		Map normGuideMap =null;
		for(int i=1;i<m_numBefore+1;i++)
		{
			int iBeginYear=Integer.parseInt(DateUtil.DateToString(tmpCalendar.getTime(), "yyyy"));
			tmpCalendar.add(tmpCalendar.YEAR,1);
			normGuideMap=bo.getYearsTroubleQuotaList(contractorId, m_guideType, iBeginYear, 0, "");				
			setCategoryValue_Common(barChartBean,
					String.valueOf(iBeginYear),
					(Double) ((Map)normGuideMap.get(name_jh)).get(name_norm),
					(Double) ((Map)normGuideMap.get(name_jh)).get(name_dare),
					(Double) ((Map) normGuideMap.get(name_jh)).get(name_finish));
			lastLength=(Double) ((Map) normGuideMap.get(name_jh)).get("maintenance_length") ;
		}
		barChartBean.setYearTitle(m_numBefore, "ǧ�������ʱ��(Сʱ)��ָ��  "+lastLength+"����");
	}
	


}
