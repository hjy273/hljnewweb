package com.cabletech.linepatrol.appraise.module;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;

import com.cabletech.commons.util.DateUtil;

public class AppraiseTable {
	private String id;
	private String tableName;
	private String type;
	private String year;
	private Date startDate;
	private Date endDate;
	private String creater;
	private Date createDate;
	private List<AppraiseItem> appraiseItems = new ArrayList<AppraiseItem>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<AppraiseItem> getAppraiseItems() {
		return appraiseItems;
	}

	public void setAppraiseItems(List<AppraiseItem> appraiseItems) {
		this.appraiseItems = appraiseItems;
	}

	public void addAppraiseItem(AppraiseItem appraiseItem) {
		this.appraiseItems.add(appraiseItem);
	}

	public void removeAppraiseItemById(String itemId) {
		if (!appraiseItems.isEmpty()) {
			for (AppraiseItem appraiseItem : appraiseItems) {
				if (itemId.equals(appraiseItem.getId())) {
					appraiseItems.remove(appraiseItem);
				}
			}
		}
	}

	public void removeAppraiseItem(AppraiseItem appraiseItem) {
		if (!appraiseItems.isEmpty()) {
			appraiseItems.remove(appraiseItem);
		}
	}
	public int getTableSize(){
		int rows=0;
		for(AppraiseItem appraiseItem:appraiseItems){
			rows+=appraiseItem.getItemSize();
		}
		return rows;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Map getAllWeight(){
		Map<String,Integer> allWeight=new HashMap<String, Integer>();
		int tableWeight=0;
		int itemWeight=0;
		int contentWeight=0;
		for(AppraiseItem appraiseItem:appraiseItems){
			tableWeight+=appraiseItem.getWeight();
			for(AppraiseContent appraiseContent:appraiseItem.getAppraiseContents()){
				itemWeight+=appraiseContent.getWeight();
				for(AppraiseRule appraiseRule:appraiseContent.getAppraiseRules()){
					contentWeight+=appraiseRule.getWeight();
				}
			}
		}
		allWeight.put("tableWeight", tableWeight);
		allWeight.put("itemWeight", itemWeight);
		allWeight.put("contentWeight", contentWeight);
		return allWeight;
	}
	
	/**
	 * 设置引用对象的值，其值来源于动态Bean，动态Bean的属性值请严格命名
	 * 
	 * @param bean 动态Bean
	 * @param dateFormatPattern 日期格式化样式
	 */
	public void setAppraiseTableFromDynaBean(DynaBean bean, String dateFormatPattern) {
		String tableId = (String) bean.get("tab_id");
		String tableName = (String) bean.get("table_name");
		String appraiser = (String) bean.get("username");
		String appraiseDate = (String) bean.get("appraise_date");
		this.setId(tableId);
		this.setTableName(tableName);
		this.setCreater(appraiser);
		try {
			this.setCreateDate(DateUtil.StringToDate(appraiseDate, dateFormatPattern));
		} catch (Exception e) {
			this.setCreateDate(null);
		}
	}
	/**
	 * 将AppraiseTable的列表转化为格式为  Id：Name; Id：Name;的字符串
	 * @param list
	 * @return
	 */
	public static String getTableOption(List<AppraiseTable> list){
		String str="";
		for(AppraiseTable appraiseTable:list){
			str+=appraiseTable.getId()+":"+appraiseTable.getTableName()+";";
		}
		return str;
	}
}
