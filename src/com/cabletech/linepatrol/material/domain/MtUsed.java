package com.cabletech.linepatrol.material.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class MtUsed extends BaseDomainObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1847233603517967710L;

	private int mid;// ���

	private String contractorid;// ��άID

	private String createtime;// ����ʱ��

	private String creator;// ������

	private String remark;// ��ע

	private String state;// ״̬

	private String approverId;// �����

	public final static String STATE_MOBILE_AGREE = "4";

	public final static String STATE_MOBILE_NO_AGREE = "3";

	public final static String STATE_CONTROL_AGREE = "2";

	public final static String STATE_CONTROL_NO_AGREE = "1";

	public final static String STATE_APPLY = "0";

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getContractorid() {
		return contractorid;
	}

	public void setContractorid(String contractorid) {
		this.contractorid = contractorid;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
}
