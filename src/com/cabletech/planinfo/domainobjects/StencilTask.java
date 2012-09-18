package com.cabletech.planinfo.domainobjects;

import java.util.Date;

public class StencilTask {
	private String ID;

	private String stencilname;

	private String patrolid;

	private Date createdate;

	private String regionid;

	private String contractorid;

	public String getContractorid() {
		return contractorid;
	}
	/**
	 * set 代维单位Id
	 * @param contractorid
	 */
	public void setContractorid(String contractorid) {
		this.contractorid = contractorid;
	}
	/**
	 * 获得模板创建时间
	 * @return String
	 */
	public Date getCreatedate() {
		return createdate;
	}

	/**
	 * set 模板创建时间
	 * @param createdate
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}
	/**
	 * 获得巡检员id
	 * @return
	 */
	public String getPatrolid() {
		return patrolid;
	}
	/**
	 * 设置模板对应的巡检员
	 * @param patrolid
	 */
	public void setPatrolid(String patrolid) {
		this.patrolid = patrolid;
	}
	
	/**
	 * 获得模板所属区域
	 * @return
	 */
	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}
	/**
	 * 获得模板名称
	 * @return
	 */
	public String getStencilname() {
		return stencilname;
	}

	/**
	 * 设置模板名称
	 * @param stencilname
	 */
	public void setStencilname(String stencilname) {
		this.stencilname = stencilname;
	}
}
