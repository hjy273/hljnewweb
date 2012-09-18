package com.cabletech.analysis.action;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.analysis.beans.BarChartBean;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.trouble.services.TroubleQuotaBO;

@Service
public class MainGuideBarChartAction extends BarChartAction {

	private static final String TITLE = "һ��ָ��ֵ\n ά�����ȣ������";
	private static final String NORM_VALUE = "��׼ֵ";
	private static final String DARE_VALUE = "��սֵ";
	private static final String INTERDICTION_TIME = "����������ʱ��";//"ά����ʱ��";
	private static final String FINISH_VALUE="ָ�����ֵ";
	private int   m_numBefore=DateUtil.getElapseMonthNum(); //ǰ5���£�����������
	@Override
	public BarChartBean dataSet(UserInfo userInfo,HttpSession session) {
		String contractorId=null;
		if(userInfo.getDeptype().equals("2")){
			contractorId=userInfo.getDeptID();
		}		
		BarChartBean barChartBean = new BarChartBean();
//		setBarChartBeanValue(userInfo,barChartBean);		
		setCategoryColor_Common(barChartBean);
		setCategoryValues_Common(contractorId,barChartBean,
				"һ��ǧ�������ʱ��(Сʱ)ָ��","sumResultMap","standard_time","dare_time","trouble_time");
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
//			normGuideMap = bo.getTimeAreaTroubleQuotaList(contractorId, "1", startTime1, endTime1,"");//
			normGuideMap = bo.getTimeAreaTroubleQuotaList(contractorId, "1", startTime1, endTime1);//
			setCategoryValue_Common(barChartBean,
					startTime1,
					(Double) ((Map)normGuideMap.get(name_jh)).get(name_norm),
					(Double) ((Map)normGuideMap.get(name_jh)).get(name_dare),
					(Double) ((Map) normGuideMap.get(name_jh)).get(name_finish));
			lastLength=(Double) ((Map) normGuideMap.get(name_jh)).get("maintenance_length") ;
		}
		barChartBean.setMonthTitle(m_numBefore,title+"  "+lastLength+"����");
	}
	

	@Override
	public BarChartBean dataSetType(UserInfo userInfo, HttpSession session,
			int CategoryType) {
		
		return null;
	}
}
