package com.cabletech.linepatrol.appraise.module;

import org.apache.commons.beanutils.DynaBean;

public class AppraiseTransTable {
	private AppraiseTable appraiseTable = new AppraiseTable();
	private AppraiseItem appraiseItem = null;
	private AppraiseContent appraiseContent = null;
	private AppraiseRule appraiseRule = null;

	public AppraiseRule getAppraiseRule() {
		return appraiseRule;
	}

	public void setAppraiseRule(AppraiseRule appraiseRule) {
		this.appraiseRule = appraiseRule;
	}

	private DynaBean remarkInfo;

	public AppraiseTable getAppraiseTable() {
		return appraiseTable;
	}

	public void setAppraiseTable(AppraiseTable appraiseTable) {
		this.appraiseTable = appraiseTable;
	}

	public AppraiseItem getAppraiseItem() {
		return appraiseItem;
	}

	public void setAppraiseItem(AppraiseItem appraiseItem) {
		this.appraiseItem = appraiseItem;
	}

	public AppraiseContent getAppraiseContent() {
		return appraiseContent;
	}

	public void setAppraiseContent(AppraiseContent appraiseContent) {
		this.appraiseContent = appraiseContent;
	}

	public DynaBean getRemarkInfo() {
		return remarkInfo;
	}

	public void setRemarkInfo(DynaBean remarkInfo) {
		this.remarkInfo = remarkInfo;
	}

	public void newTable() {
		appraiseTable = new AppraiseTable();
		appraiseTable.setAppraiseTableFromDynaBean(remarkInfo, "yyyy-MM-dd");
	}

	public void newItem() {
		this.appraiseItem = new AppraiseItem();
		appraiseItem.setAppraiseItemFromDynaBean(remarkInfo);
	}

	public void newContent() {
		this.appraiseContent = new AppraiseContent();
		appraiseContent.setAppraiseContentFromDynaBean(remarkInfo);
	}

	public void newRule() {
		this.appraiseRule = new AppraiseRule();
		appraiseRule.setAppraiseRuleFromDynaBean(remarkInfo);
	}

	public void addRule() {
		this.appraiseContent.addAppraiseRule(this.appraiseRule);
	}

	public void addContent() {
		this.appraiseItem.addAppraiseContent(this.appraiseContent);
	}

	public void addItem() {
		this.appraiseTable.addAppraiseItem(this.appraiseItem);
	}

	public void inputTable() {
		addContent();
		addItem();
	}
}
