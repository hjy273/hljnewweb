package com.cabletech.baseinfo.domainobjects;

import com.cabletech.commons.base.*;

public class UsergroupModuleList extends BaseDomainObject{
    private String id;
    private String usergroupid;
    private String sonmenuid;
    private String remark;
    public UsergroupModuleList(){
    }


    public void setId( String id ){
        this.id = id;
    }


    public void setUsergroupid( String usergroupid ){
        this.usergroupid = usergroupid;
    }


    public void setSonmenuid( String sonmenuid ){
        this.sonmenuid = sonmenuid;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public String getId(){
        return id;
    }


    public String getUsergroupid(){
        return usergroupid;
    }


    public String getSonmenuid(){
        return sonmenuid;
    }


    public String getRemark(){
        return remark;
    }
}
