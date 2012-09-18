/***
 *
 * MtUsedAssessBean.java
 * @auther <a href="kww@mail.cabletech.com.cn">kww</a>
 * 2009-10-10
 **/

package com.cabletech.linepatrol.material.beans;


import com.cabletech.commons.base.BaseBean;

/**
 * ²ÄÁÏÉóºËĞÅÏ¢
 * @author kww
 *
 */
public class MtUsedAssessBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int aid;// ±àºÅ

	private int mtusedid;// ÉêÇëµ¥±àºÅ

	private String assessor;// ÉóºËÈË

	private String assessdate;// ÉóºËÈÕÆÚ

	private String state;// ×´Ì¬

	private String remark;// ±¸×¢
	
	private String type;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MtUsedAssessBean() {
		super();
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getAssessdate() {
		return assessdate;
	}

	public void setAssessdate(String assessdate) {
		this.assessdate = assessdate;
	}

	public String getAssessor() {
		return assessor;
	}

	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}

	public int getMtusedid() {
		return mtusedid;
	}

	public void setMtusedid(int mtusedid) {
		this.mtusedid = mtusedid;
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
		result = PRIME * result + aid;
		result = PRIME * result + ((assessdate == null) ? 0 : assessdate.hashCode());
		result = PRIME * result + ((assessor == null) ? 0 : assessor.hashCode());
		result = PRIME * result + mtusedid;
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
		final MtUsedAssessBean other = (MtUsedAssessBean) obj;
		if (aid != other.aid)
			return false;
		if (assessdate == null) {
			if (other.assessdate != null)
				return false;
		} else if (!assessdate.equals(other.assessdate))
			return false;
		if (assessor == null) {
			if (other.assessor != null)
				return false;
		} else if (!assessor.equals(other.assessor))
			return false;
		if (mtusedid != other.mtusedid)
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

	
}
