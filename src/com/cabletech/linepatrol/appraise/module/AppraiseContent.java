package com.cabletech.linepatrol.appraise.module;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;

public class AppraiseContent {
	private String id;
	private AppraiseItem appraiseItem;
	private String contentDescription;
	private int weight;
	private List<AppraiseRule> appraiseRules=new ArrayList<AppraiseRule>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContentDescription() {
		return contentDescription;
	}

	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public AppraiseItem getAppraiseItem() {
		return appraiseItem;
	}

	public void setAppraiseItem(AppraiseItem appraiseItem) {
		this.appraiseItem = appraiseItem;
	}


	public List<AppraiseRule> getAppraiseRules() {
		return appraiseRules;
	}

	public void setAppraiseRules(List<AppraiseRule> appraiseRules) {
		this.appraiseRules = appraiseRules;
	}

	public void addAppraiseRule(AppraiseRule appraiseRule) {
		this.appraiseRules.add(appraiseRule);
	}

	public void removeAppraiseRuleById(String ruleId) {
		if (!this.appraiseRules.isEmpty()) {
			for (AppraiseRule appraiseRule : appraiseRules) {
				if (ruleId.equals(appraiseRule.getId())) {
					this.appraiseRules.remove(appraiseRule);
				}
			}
		}
	}
	
	/**
	 * 设置引用对象的值，其值来源于动态Bean，动态Bean的属性值请严格命名
	 * 
	 * @param bean 动态Bean
	 */
	public void setAppraiseContentFromDynaBean(DynaBean bean) {
		String conId = (String) bean.get("con_id");
		String conName = (String) bean.get("con_name");
		String conWeight = (String)bean.get("con_weight");
		this.setId(conId);
		this.setContentDescription(conName);
		this.setWeight(Integer.parseInt(conWeight));
	}
}
