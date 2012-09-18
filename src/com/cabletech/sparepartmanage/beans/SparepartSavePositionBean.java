package com.cabletech.sparepartmanage.beans;

import com.cabletech.commons.base.BaseBean;

public class SparepartSavePositionBean extends BaseBean {
	private String id;
	private String deptid;
	private String name;//机柜名称
	private String deptType;//部门区分
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeptType() {
		return deptType;
	}
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

}