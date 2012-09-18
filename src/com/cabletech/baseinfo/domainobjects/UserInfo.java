package com.cabletech.baseinfo.domainobjects;

import java.util.*;

import com.cabletech.commons.base.*;

/**
 * <p>
 * Title: 用户信息
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Cable tech
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class UserInfo extends BaseDomainObject {
    private String userID;

    private String userName;

    private String deptID;

    private String deptName;

    private String workID;

    private String password;

    private String accountState;

    private String phone;

    private String email;

    private String position;

    private String state;

    private String regionid;
    
    private String regionName;

	private String deptype;

    private Date accountTerm;

    private Date passwordTerm;

    private Date newpsdate;

    private String oldps;

    private String accidenttime = "24";

    private String onlinemantime = "2";

    private String watchtime = "72";

    private String type = "";
    
    private String isSuperviseUnit;

    public static final String PROVINCE_MUSER_TYPE = "11";

    public static final String CITY_MUSER_TYPE = "12";

    public static final String PROVINCE_CUSER_TYPE = "21";

    public static final String CITY_CUSER_TYPE = "22";

    public UserInfo() {

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getWorkID() {
        return workID;
    }

    public void setWorkID(String workID) {
        this.workID = workID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountState() {
        return accountState;
    }

    public void setAccountState(String accountState) {
        this.accountState = accountState;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getPasswordTerm() {
        return passwordTerm;
    }

    public void setPasswordTerm(Date passwordTerm) {
        this.passwordTerm = passwordTerm;
    }

    public Date getAccountTerm() {
        return accountTerm;
    }

    public void setAccountTerm(Date accountTerm) {
        this.accountTerm = accountTerm;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegionid() {
        return regionid;
    }

    public void setRegionid(String regionid) {
        this.regionid = regionid;
    }

    public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
    
    public void setRegionID(String regionid) {
        this.regionid = regionid;
    }

    /**
     * 新增 by zsh
     * 
     * @return String
     */
    public String getRegionID() {
        return regionid;
    }

    public String getDeptype() {
        return deptype;
    }

    public Date getNewpsdate() {
        return newpsdate;
    }

    public String getOldps() {
        return oldps;
    }

    public String getAccidenttime() {
        return accidenttime;
    }

    public String getOnlinemantime() {
        return onlinemantime;
    }

    public String getWatchtime() {
        return watchtime;
    }

    public String getType() {
        return type;
    }

    public void setDeptype(String deptype) {
        this.deptype = deptype;
    }

    public void setNewpsdate(Date newpsdate) {
        this.newpsdate = newpsdate;
    }

    public void setOldps(String oldps) {
        this.oldps = oldps;
    }

    public void setAccidenttime(String accidenttime) {
        this.accidenttime = accidenttime;
    }

    public void setOnlinemantime(String onlinemantime) {
        this.onlinemantime = onlinemantime;
    }

    public void setWatchtime(String watchtime) {
        this.watchtime = watchtime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getIsSuperviseUnit() {
        return isSuperviseUnit;
    }

    public void setIsSuperviseUnit(String isSuperviseUnit) {
        this.isSuperviseUnit = isSuperviseUnit;
    }

}
