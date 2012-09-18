package com.cabletech.linepatrol.appraise.beans;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.cabletech.commons.base.BaseBean;
import com.cabletech.linepatrol.appraise.module.AppraiseItem;

public class AppraiseTableBean extends BaseBean{
	private String id;
	private String tableName;
	private String type;
	private String year;
	private String startDate;
	private String endDate;
	private FormFile file;
	private String creater;
	private Date createDate;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
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

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}
	public String getTitleTime(){
		String titleTime=null;
		if(startDate!=null){
			titleTime=startDate+"-"+endDate;
		}else{
			titleTime=year;
		}
		return titleTime;
	}
}
