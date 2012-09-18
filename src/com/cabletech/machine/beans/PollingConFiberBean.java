package com.cabletech.machine.beans;

import com.cabletech.uploadfile.formbean.BaseFileFormBean;

public class PollingConFiberBean extends BaseFileFormBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String pid;// 设备巡检任务表ID

	private String isUpdate;// ODF面板图是否更新

	private String isClean;// 箱内清洁程度

	private String isFiberBoxClean;// 光交箱外环境清洁程度

	private String isColligation;// 尾纤是否绑扎整齐

	private String isFiberCheck;// 光缆标识牌核查补贴

	private String isTailFiberCheck;// 尾纤标识牌核查补贴

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getIsClean() {
		return isClean;
	}

	public void setIsClean(String isClean) {
		this.isClean = isClean;
	}

	public String getIsColligation() {
		return isColligation;
	}

	public void setIsColligation(String isColligation) {
		this.isColligation = isColligation;
	}

	public String getIsFiberBoxClean() {
		return isFiberBoxClean;
	}

	public void setIsFiberBoxClean(String isFiberBoxClean) {
		this.isFiberBoxClean = isFiberBoxClean;
	}

	public String getIsFiberCheck() {
		return isFiberCheck;
	}

	public void setIsFiberCheck(String isFiberCheck) {
		this.isFiberCheck = isFiberCheck;
	}

	public String getIsTailFiberCheck() {
		return isTailFiberCheck;
	}

	public void setIsTailFiberCheck(String isTailFiberCheck) {
		this.isTailFiberCheck = isTailFiberCheck;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
