package com.cabletech.baseinfo.beans;

import java.util.List;

import com.cabletech.commons.base.BaseBean;

public class TroubleTypeBean extends BaseBean{
	private String code;
	private String typename;
	private String regionid;
	private List troublecode;
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRegionid() {
		return regionid;
	}
	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}
	public List getTroublecode() {
		return troublecode;
	}
	public void setTroublecode(List troublecode) {
		this.troublecode = troublecode;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
}
