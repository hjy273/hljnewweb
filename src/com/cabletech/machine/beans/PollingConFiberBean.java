package com.cabletech.machine.beans;

import com.cabletech.uploadfile.formbean.BaseFileFormBean;

public class PollingConFiberBean extends BaseFileFormBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String pid;// �豸Ѳ�������ID

	private String isUpdate;// ODF���ͼ�Ƿ����

	private String isClean;// �������̶�

	private String isFiberBoxClean;// �⽻���⻷�����̶�

	private String isColligation;// β���Ƿ��������

	private String isFiberCheck;// ���±�ʶ�ƺ˲鲹��

	private String isTailFiberCheck;// β�˱�ʶ�ƺ˲鲹��

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
