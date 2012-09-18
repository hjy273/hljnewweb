package com.cabletech.baseinfo.domainobjects;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;

public class PatrolManSon extends BaseDomainObject{
    public PatrolManSon(){
    }


    private String patrolID;
    private String patrolName;
    private String sex;
    private String birthday;
    private String culture;
    private String isMarriage;
    private String familyAddress;
    private String identityCard;
    private String jobInfo;
    private String jobState;
    private String mobile;
    private String parentID;
    private String phone;
    private String postalcode;
    private String regionID;
    private String remark;
    private String state;
    private String workRecord;
    private String age;
    private String parent_patrol;

    public String getPatrolID(){
        return patrolID;
    }


    public void setPatrolID( String patrolID ){
        this.patrolID = patrolID;
    }


    public String getPatrolName(){
        return patrolName;
    }


    public void setPatrolName( String patrolName ){
        this.patrolName = patrolName;
    }


    public String getSex(){
        return sex;
    }


    public void setSex( String sex ){
        this.sex = sex;
    }


    public String getBirthday(){
        return birthday;
    }


    public void setBirthday( String birthday ){
        this.birthday = birthday;
    }


    public String getCulture(){
        return culture;
    }


    public void setCulture( String culture ){
        this.culture = culture;
    }


    public String getIsMarriage(){
        return isMarriage;
    }


    public void setIsMarriage( String isMarriage ){
        this.isMarriage = isMarriage;
    }


    public String getAge(){
        return age;
    }


    public void setAge( String age ){
        this.age = age;
    }


    public String getPhone(){
        return phone;
    }


    public void setPhone( String phone ){
        this.phone = phone;
    }


    public String getMobile(){
        return mobile;
    }


    public void setMobile( String mobile ){
        this.mobile = mobile;
    }


    public String getPostalcode(){
        return postalcode;
    }


    public void setPostalcode( String postalcode ){
        this.postalcode = postalcode;
    }


    public String getIdentityCard(){
        return identityCard;
    }


    public void setIdentityCard( String identityCard ){
        this.identityCard = identityCard;
    }


    public String getFamilyAddress(){
        return familyAddress;
    }


    public void setFamilyAddress( String familyAddress ){
        this.familyAddress = familyAddress;
    }


    public String getWorkRecord(){
        return workRecord;
    }


    public void setWorkRecord( String workRecord ){
        this.workRecord = workRecord;
    }


    public String getParentID(){
        return parentID;
    }


    public void setParentID( String parentID ){
        this.parentID = parentID;
    }


    public String getJobInfo(){
        return jobInfo;
    }


    public void setJobInfo( String jobInfo ){
        this.jobInfo = jobInfo;
    }


    public String getJobState(){
        return jobState;
    }


    public void setJobState( String jobState ){
        this.jobState = jobState;
    }


    public String getState(){
        return state;
    }


    public void setState( String state ){
        this.state = state;
    }


    public String getRegionID(){
        return regionID;
    }


    public void setRegionID( String regionID ){
        this.regionID = regionID;
    }


    public String getRemark(){
        return remark;
    }


    public String getParent_patrol(){
        return parent_patrol;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public void setParent_patrol( String parent_patrol ){
        this.parent_patrol = parent_patrol;
    }


    public void reset( ActionMapping mapping,
        javax.servlet.http.HttpServletRequest request ){

    }

}
