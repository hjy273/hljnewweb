package com.cabletech.sysmanage.beans;

import com.cabletech.commons.base.BaseBean;

public class ConPersonBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6058173903190674365L;
	private String name = "";
	private String phone = "";
	private String contractorid = "";
	private String regionid = "";
	private String state = "";
	private String id;
	private String sex = "";
	private String jobinfo = "";
	private String jobstate = "";
	private String identitycard = "";

	private String remark = "";
	private String culture = "";
	private String ismarriage = "";
	private String workrecord = "";
	private String persontype = "";
	private String postalcode = "";
	private String contractorname = "";
	private String mobile = "";
	private String familyaddress = "";
	
	private String residantarea = "";
	private String issendsms="";
	//add on 20100128
	private String postOffice="";//岗位职责
	private String enterTime ="";//入职时间
	//add on 20100430
	private String leaveTime ="";//离职时间
	private String conditions;//在职情况
	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public ConPersonBean() {
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setContractorid(String contractorid) {
		this.contractorid = contractorid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setJobinfo(String jobinfo) {
		this.jobinfo = jobinfo;
	}

	public void setJobstate(String jobstate) {
		this.jobstate = jobstate;
	}

	public void setCulture(String culture) {
		this.culture = culture;
	}

	public void setIsmarriage(String ismarriage) {
		this.ismarriage = ismarriage;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public void setFamilyaddress(String familyaddress) {
		this.familyaddress = familyaddress;
	}

	public void setWorkrecord(String workrecord) {
		this.workrecord = workrecord;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setPersontype(String persontype) {
		this.persontype = persontype;
	}

	public void setIdentitycard(String identitycard) {
		this.identitycard = identitycard;
	}

	public void setContractorname(String contractorname) {
		this.contractorname = contractorname;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getContractorid() {
		return contractorid;
	}

	public String getRegionid() {
		return regionid;
	}

	public String getState() {
		return state;
	}

	public String getSex() {
		return sex;
	}

	public String getJobinfo() {
		return jobinfo;
	}

	public String getJobstate() {
		return jobstate;
	}

	public String getCulture() {
		return culture;
	}

	public String getIsmarriage() {
		return ismarriage;
	}

	public String getMobile() {
		return mobile;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public String getFamilyaddress() {
		return familyaddress;
	}

	public String getWorkrecord() {
		return workrecord;
	}

	public String getRemark() {
		return remark;
	}

	public String getPersontype() {
		return persontype;
	}

	public String getIdentitycard() {
		return identitycard;
	}

	public String getContractorname() {
		return contractorname;
	}

	public String getResidantarea() {
		return residantarea;
	}

	public void setResidantarea(String residantarea) {
		this.residantarea = residantarea;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getPostOffice() {
		return postOffice;
	}

	public void setPostOffice(String postOffice) {
		this.postOffice = postOffice;
	}

	public String getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}

	public String getIssendsms() {
		return issendsms;
	}

	public void setIssendsms(String issendsms) {
		this.issendsms = issendsms;
	}
}
