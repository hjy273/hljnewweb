package com.cabletech.analysis.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.cabletech.commons.util.DateUtil;

public class BarChartBean {
	private List<Map<String,Object>> categoryColors=new ArrayList<Map<String,Object>>(); //属性分类-颜色对应表 [{category=First, color=java.awt.Color[r=0,g=0,b=255]}, {category=Second, color=java.awt.Color[r=0,g=255,b=0]}, {category=Third, color=java.awt.Color[r=255,g=0,b=0]}]
	private List<List<Object>> categoryValues=new ArrayList<List<Object>>(); //属性-值 对应表   例如：[[Category 1, {Third=4.0, Second=5.0, First=1.0}], [Category 2, {Third=3.0, Second=7.0, First=4.0}], [Category 3, {Third=2.0, Second=6.0, First=3.0}], [Category 4, {Third=3.0, Second=8.0, First=5.0}], [Category 5, {Third=6.0, Second=4.0, First=5.0}]]
	private String title;  //图表标题
	public final List<Map<String, Object>> getCategoryColors() {
		return categoryColors;
	}
	public final void setCategoryColors(List<Map<String, Object>> categoryColors) {
		this.categoryColors = categoryColors;
	}
	public final List<List<Object>> getCategoryValues() {
		return categoryValues;
	}
	public final void setCategoryValues(List<List<Object>> categoryValues) {
		this.categoryValues = categoryValues;
	}
	public final String getTitle() {
		return title;
	}
	public final void setTitle(String title) {
		this.title = title;
	}
	public void addCategoryColors(Map<String,Object> categoryColor){
		this.categoryColors.add(categoryColor);
	}
	public void addCategoryValues(List<Object> categoryValue){
		this.categoryValues.add(categoryValue);
	}
	
	public void clearCategoryColors(){
		this.categoryColors.clear();
	}
	
	/**
	 * 设置年 专题图title  包含当年
	 * @param iNumYear 指定几年内
	 * @param strTitle
	 */
	public  void setYearTitle(int iNumYears,String strTitle){
		Calendar calendar = Calendar.getInstance();
		String endTime = DateUtil.DateToString(calendar.getTime(), "yyyy");
		Calendar tmpCalendar=Calendar.getInstance();
		tmpCalendar.add(tmpCalendar.YEAR,-(iNumYears-1));
		setTitle(DateUtil.DateToString(tmpCalendar.getTime(), "yyyy")+"-"+endTime+" "+strTitle );
		//tmpCalendar.add(tmpCalendar.MONTH,-1);
	}
	
	/**
	 * 设置月 专题图title   不含当月
	 * @param iNumMonths  
	 * @param strTitle
	 */
	public  void setMonthTitle(int iNumMonths,String strTitle){
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.MONTH, -1);
		String endTime = DateUtil.DateToString(calendar.getTime(), "yyyy/MM");
		Calendar tmpCalendar=Calendar.getInstance();
		tmpCalendar.add(tmpCalendar.MONTH,-iNumMonths);
		setTitle(DateUtil.DateToString(tmpCalendar.getTime(), "yyyy/MM")+"-"+endTime+" "+strTitle );
	}
}
