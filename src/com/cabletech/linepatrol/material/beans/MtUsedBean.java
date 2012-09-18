/***
 *
 * MtUsedBean.java
 * @auther <a href="kww@mail.cabletech.com.cn">kww</a>
 * 2009-10-10
 **/

package com.cabletech.linepatrol.material.beans;

import com.cabletech.commons.base.BaseBean;

/**
 * 材料 信息
 * 
 * @author kww
 * 
 */
public class MtUsedBean extends BaseBean {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private int mid;// 编号

	private String contractorid;// 代维ID

	private String createtime;// 创建时间

	private String creator;// 申请人

	private String remark;// 备注

	private String state;// 状态

	private String approverId;// 审核人

	public final static String STATE_MOBILE_AGREE = "2";

	public final static String STATE_MOBILE_NO_AGREE = "1";

	// public final static String STATE_CONTROL_AGREE = "2";

	// public final static String STATE_CONTROL_NO_AGREE = "1";

	public final static String STATE_APPLY = "0";

	public MtUsedBean() {
		super();
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

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
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

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result
				+ ((contractorid == null) ? 0 : contractorid.hashCode());
		result = PRIME * result
				+ ((createtime == null) ? 0 : createtime.hashCode());
		result = PRIME * result + ((creator == null) ? 0 : creator.hashCode());
		result = PRIME * result + mid;
		result = PRIME * result + ((remark == null) ? 0 : remark.hashCode());
		result = PRIME * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MtUsedBean other = (MtUsedBean) obj;
		if (contractorid == null) {
			if (other.contractorid != null)
				return false;
		} else if (!contractorid.equals(other.contractorid))
			return false;
		if (createtime == null) {
			if (other.createtime != null)
				return false;
		} else if (!createtime.equals(other.createtime))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (mid != other.mid)
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
}
