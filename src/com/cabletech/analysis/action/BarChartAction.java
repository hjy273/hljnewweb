package com.cabletech.analysis.action;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.cabletech.analysis.beans.BarChartBean;
import com.cabletech.analysis.util.LayeredBarChart;
import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;

public abstract class BarChartAction extends BaseInfoBaseDispatchAction{
	private static final String NORM_VALUE = "��׼ֵ";
	private static final String DARE_VALUE = "��սֵ";
	private static final String FINISH_VALUE="ָ�����ֵ";
	public void generateChart( ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response ) throws
    ServletException, IOException{
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		request.setCharacterEncoding("GBK");
		response.setCharacterEncoding("GBK");
		int width= Integer.valueOf(request.getParameter("width"));
		int heigth=Integer.valueOf(request.getParameter("heigth"));
		int CategoryType=Integer.valueOf(request.getParameter("CategoryType"));//add by xueyh for ����һ��ѡ��ר��ͼ��ѡ������
		JFreeChart chart ;
		if (CategoryType<11){ chart = getChart(userInfo,request.getSession());}
		else{ chart = getChart(userInfo,request.getSession(),CategoryType);}
		ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, width,heigth);
	}

	private JFreeChart getChart(UserInfo userInfo,HttpSession session) {
		BarChartBean barChartBean = dataSet(userInfo,session);
		LayeredBarChart layeredBarChart = new LayeredBarChart();
		layeredBarChart.setBarChartBean(barChartBean);
		//add by xueyh for bar VERTICAL 1;  HORIZONTAL 0 (default) ; ???����ͻ�����Ҫ������ʽ��Ӧ����������ʽ�����ж�ȡ���
		layeredBarChart.setArrangeType(1); 
		JFreeChart chart = layeredBarChart.createChart();
		chart.setBackgroundPaint(java.awt.Color.white);
		return chart;
	}
	
	private JFreeChart getChart(UserInfo userInfo,HttpSession session,int type) {
		BarChartBean barChartBean = dataSetType(userInfo,session,type);
		LayeredBarChart layeredBarChart = new LayeredBarChart();
		layeredBarChart.setBarChartBean(barChartBean);
		//add by xueyh for bar VERTICAL 1;  HORIZONTAL 0 (default) ; ???����ͻ�����Ҫ������ʽ��Ӧ����������ʽ�����ж�ȡ���
		layeredBarChart.setArrangeType(1); 
		JFreeChart chart = layeredBarChart.createChart();
		chart.setBackgroundPaint(java.awt.Color.white);
		return chart;
	}
	
	public void setCategoryColor(BarChartBean barChartBean, String categoryName, Color colorName) {
		Map<String, Object> categoryColor = new HashMap<String, Object>();
		categoryColor.put("category", categoryName);
		categoryColor.put("color", colorName);
		barChartBean.addCategoryColors(categoryColor);
	}

	//�ع�  by xueyh 20110505 δ��ɣ����ü��� 
	
	/**
	 * ͨ�����û�׼����ս�����ֵ��ɫ
	 * @param barChartBean
	 */
	public void setCategoryColor_Common(BarChartBean barChartBean) {
		setCategoryColor(barChartBean, NORM_VALUE, new Color(249,243,142));// Color.yellow);
		setCategoryColor(barChartBean, DARE_VALUE, Color.red);		
		setCategoryColor(barChartBean,FINISH_VALUE,Color.green);
	}
	/**
	 * ͨ�����û�׼�����ֵ2ɫ
	 * @param barChartBean
	 */	
	public void setCategoryColor_Common2(BarChartBean barChartBean) {
		barChartBean.clearCategoryColors();
		setCategoryColor(barChartBean, NORM_VALUE, new Color(249,243,142));//Color.yellow);
//		setCategoryColor(barChartBean, DARE_VALUE, Color.red);		
		setCategoryColor(barChartBean,FINISH_VALUE,Color.green);
	}
	/**
	 * ͨ�����û�׼����ս�����ֵ��Ӧ����ֵ
	 * @param barChartBean
	 * @param strTitle
	 * @param dNormValue
	 * @param dDareValue
	 * @param dFinishValue
	 */
	public void setCategoryValue_Common(BarChartBean barChartBean,String strTitle, double dNormValue,double dDareValue,double dFinishValue) {
		List<Object> categoryValue = new ArrayList<Object>();
		Map<String, Double> categoryMap = new HashMap<String, Double>();
		categoryValue.add(strTitle);
		categoryMap.put(NORM_VALUE, dNormValue);
		categoryMap.put(DARE_VALUE, dDareValue);
		categoryMap.put(FINISH_VALUE, dFinishValue);
		categoryValue.add(categoryMap);
		barChartBean.addCategoryValues(categoryValue);
	}
	
	/**
	 * ͨ�����û�׼�����ֵ��Ӧ����ֵ
	 * @param barChartBean
	 * @param strTitle
	 * @param dNormValue
	 * @param dFinishValue
	 */
	public void setCategoryValue_Common2(BarChartBean barChartBean,String strTitle, double dNormValue,double dFinishValue) {
		List<Object> categoryValue = new ArrayList<Object>();
		Map<String, Double> categoryMap = new HashMap<String, Double>();
		categoryValue.add(strTitle);
		categoryMap.put(NORM_VALUE, dNormValue);
		categoryMap.put(FINISH_VALUE, dFinishValue);
		categoryValue.add(categoryMap);
		barChartBean.addCategoryValues(categoryValue);
	}
	
	public abstract BarChartBean dataSet(UserInfo userInfo,HttpSession session);
	public abstract BarChartBean dataSetType(UserInfo userInfo,HttpSession session,int CategoryType);
}
