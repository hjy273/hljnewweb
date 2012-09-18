package com.cabletech.baseinfo.domainobjects;

import com.cabletech.commons.base.*;

public class UsergroupUserList extends BaseDomainObject{
    private String id;
    private String userid;
    private String usergroupid;
    private String remark;
    public UsergroupUserList(){
    }


    public void setId( String id ){
        this.id = id;
    }


    public void setUserid( String userid ){
        this.userid = userid;
    }


    public void setUsergroupid( String usergroupid ){
        this.usergroupid = usergroupid;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public String getId(){
        return id;
    }


    public String getUserid(){
        return userid;
    }


    public String getUsergroupid(){
        return usergroupid;
    }


    public String getRemark(){
        return remark;
    }
}
