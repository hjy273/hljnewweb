package com.cabletech.linepatrol.appraise.module;

import org.apache.commons.beanutils.DynaBean;

public class AppraiseRule {
	private String id;
	private AppraiseContent appraiseContent;
	private String ruleDescription;
	private int weight;
	private String gradeDescription;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public AppraiseContent getAppraiseContent() {
		return appraiseContent;
	}
	public void setAppraiseContent(AppraiseContent appraiseContent) {
		this.appraiseContent = appraiseContent;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getGradeDescription() {
		return gradeDescription;
	}
	public void setGradeDescription(String gradeDescription) {
		if(gradeDescription==null){
			gradeDescription="";
		}
		this.gradeDescription = gradeDescription;
		
	}
	
	/**
	 * 设置引用对象的值，其值来源于动态Bean，动态Bean的属性值请严格命名
	 * 
	 * @param bean 动态Bean
	 */
	public void setAppraiseRuleFromDynaBean(DynaBean bean) {
		String ruleId = (String) bean.get("rule_id");
		String ruleName = (String) bean.get("rule_name");
		String ruleWeight = (String)bean.get("ru_weight");
		String remark = (String) bean.get("remark");
		this.setId(ruleId);
		this.setRuleDescription(ruleName);
		this.setGradeDescription(remark);
		this.setWeight(Integer.parseInt(ruleWeight));
	}
}
