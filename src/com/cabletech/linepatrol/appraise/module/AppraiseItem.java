package com.cabletech.linepatrol.appraise.module;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;

public class AppraiseItem {
	private String id;
	private AppraiseTable appraiseTable;
	private String itemName;
	private int weight;
	private List<AppraiseContent> appraiseContents=new ArrayList<AppraiseContent>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public AppraiseTable getAppraiseTable() {
		return appraiseTable;
	}

	public void setAppraiseTable(AppraiseTable appraiseTable) {
		this.appraiseTable = appraiseTable;
	}

	public List<AppraiseContent> getAppraiseContents() {
		return appraiseContents;
	}

	public void setAppraiseContents(List<AppraiseContent> appraiseContents) {
		this.appraiseContents = appraiseContents;
	}

	public void addAppraiseContent(AppraiseContent appraiseContent) {
		this.appraiseContents.add(appraiseContent);
	}

	public void removeAppraiseContentById(String contentId) {
		if (this.appraiseContents.isEmpty()) {
			for (AppraiseContent appraiseContent : appraiseContents) {
				if (contentId.equals(appraiseContent.getId())) {
					appraiseContents.remove(appraiseContent);
				}
			}
		}
	}
	
	public int getItemSize(){
		int rows=0;
		for(AppraiseContent appraiseContent:appraiseContents){
			rows+=appraiseContent.getAppraiseRules().size();
		}
		return rows;
	}
	
	/**
	 * 设置引用对象的值，其值来源于动态Bean，动态Bean的属性值请严格命名
	 * 
	 * @param bean 动态Bean
	 */
	public void setAppraiseItemFromDynaBean(DynaBean bean) {
		String itemId = (String) bean.get("item_id");
		String itemName = (String) bean.get("item_name");
		String itemWeight = (String)bean.get("item_weight");
		this.setId(itemId);
		this.setItemName(itemName);
		this.setWeight(Integer.parseInt(itemWeight));
	}
}
