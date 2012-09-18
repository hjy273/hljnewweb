package com.cabletech.planinfo.beans;

import com.cabletech.commons.base.BaseBean;

public class StencilTaskBean extends BaseBean{
	private String ID;

	private String stencilname;

	private String patrolid;

	private String createdate;

	private String regionid;

	private String contractorid;
	

	public String getContractorid() {
		return contractorid;
	}
	/**
	 * set ��ά��λId
	 * @param contractorid
	 */
	public void setContractorid(String contractorid) {
		this.contractorid = contractorid;
	}
	/**
	 * ���ģ�崴��ʱ��
	 * @return String
	 */
	public String getCreatedate() {
		return createdate;
	}

	/**
	 * set ģ�崴��ʱ��
	 * @param createdate
	 */
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}
	/**
	 * ���Ѳ��Աid
	 * @return
	 */
	public String getPatrolid() {
		return patrolid;
	}
	/**
	 * ����ģ���Ӧ��Ѳ��Ա
	 * @param patrolid
	 */
	public void setPatrolid(String patrolid) {
		this.patrolid = patrolid;
	}
	
	/**
	 * ���ģ����������
	 * @return
	 */
	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}
	/**
	 * ���ģ������
	 * @return
	 */
	public String getStencilname() {
		return stencilname;
	}

	/**
	 * ����ģ������
	 * @param stencilname
	 */
	public void setStencilname(String stencilname) {
		this.stencilname = stencilname;
	}
}
