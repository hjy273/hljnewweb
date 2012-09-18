package com.cabletech.linepatrol.quest.module;

import com.cabletech.commons.base.BaseDomainObject;
 
//指标类别
public class QuestGuidelineClass extends BaseDomainObject {
	private String id;
	private String typeId;//类型ID
	private String className;//类别名称
	private String remark;//备注

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
