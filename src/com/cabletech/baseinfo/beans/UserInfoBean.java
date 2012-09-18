package com.cabletech.baseinfo.beans;

import com.cabletech.commons.base.*;

/**
 * <p>Title:用户FormBean </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Cable tech</p>
 * @author not attributable
 * @version 1.0
 */

public class UserInfoBean extends BaseBean{
    private String userID;
    private String userName;
    private String deptID;
    private String workID;
    private String password;
    private String phone;
    private String email;
    private String position;
    private String accountTerm;
    private String passwordTerm="2008/12/12";
    private String accountState;
    private String state;
    private String regionid;
    private String deptype;
    private String usergroupid;
    private String newpsdate;
    private String oldps;
    private String isSuperviseUnit;

    private String accidenttime = "24";
    private String onlinemantime = "2";
    private String watchtime = "72";

    private String oldpassword;
    private String newpassword;
    private String affirmpassword;
    public String getUserID(){
        return userID;
    }


    public void setUserID( String userID ){
        this.userID = userID;
    }


    public String getUserName(){
        return userName;
    }


    public void setUserName( String userName ){
        this.userName = userName;
    }


    public String getDeptID(){
        return deptID;
    }


    public void setDeptID( String deptID ){
        this.deptID = deptID;
    }


    public String getWorkID(){
        return workID;
    }


    public void setWorkID( String workID ){
        this.workID = workID;
    }


    public String getPassword(){
        return password;
    }


    public void setPassword( String password ){
        this.password = password;
    }


    public String getPhone(){
        return phone;
    }


    public void setPhone( String phone ){
        this.phone = phone;
    }


    public String getEmail(){
        return email;
    }


    public void setEmail( String email ){
        this.email = email;
    }


    public String getPosition(){
        return position;
    }


    public void setPosition( String position ){
        this.position = position;
    }


    public String getAccountTerm(){
        return accountTerm;
    }


    public void setAccountTerm( String accountTerm ){
        this.accountTerm = accountTerm;
    }


    public String getPasswordTerm(){
        return passwordTerm;
    }


    public void setPasswordTerm( String passwordTerm ){
        if(passwordTerm == null){
            this.passwordTerm="2088/12/31";
        }else
        	this.passwordTerm = passwordTerm;
    }


    public String getAccountState(){
        return accountState;
    }


    public void setAccountState( String accountState ){
        this.accountState = accountState;
    }


    public String getState(){
        return state;
    }


    public void setState( String state ){
        this.state = state;
    }


    public String getRegionid(){
        return regionid;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    /**
     * 新增 by zsh
     * @return String
     */
    public String getRegionID(){
        return regionid;
    }


    public String getDeptype(){
        return deptype;
    }


    public String getUsergroupid(){
        return usergroupid;
    }


    public String getNewpsdate(){
        return newpsdate;
    }


    public String getOldps(){
        return oldps;
    }


    public String getAccidenttime(){
        return accidenttime;
    }


    public String getOnlinemantime(){
        return onlinemantime;
    }


    public String getWatchtime(){
        return watchtime;
    }


    public String getOldpassword(){
        return oldpassword;
    }


    public String getNewpassword(){
        return newpassword;
    }


    public String getAffirmpassword(){
        return affirmpassword;
    }


    public void setDeptype( String deptype ){
        this.deptype = deptype;
    }


    public void setUsergroupid( String usergroupid ){
        this.usergroupid = usergroupid;
    }


    public void setNewpsdate( String newpsdate ){
        this.newpsdate = newpsdate;
    }


    public void setOldps( String oldps ){
        this.oldps = oldps;
    }


    public void setAccidenttime( String accidenttime ){
        this.accidenttime = accidenttime;
    }


    public void setOnlinemantime( String onlinemantime ){
        this.onlinemantime = onlinemantime;
    }


    public void setWatchtime( String watchtime ){
        this.watchtime = watchtime;
    }


    public void setOldpassword( String oldpassword ){
        this.oldpassword = oldpassword;
    }


    public void setNewpassword( String newpassword ){
        this.newpassword = newpassword;
    }


    public void setAffirmpassword( String affirmpassword ){
        this.affirmpassword = affirmpassword;
    }


    public String getIsSuperviseUnit() {
        return isSuperviseUnit;
    }


    public void setIsSuperviseUnit(String isSuperviseUnit) {
        this.isSuperviseUnit = isSuperviseUnit;
    }
}
