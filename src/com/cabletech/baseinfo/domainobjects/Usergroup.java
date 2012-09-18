package com.cabletech.baseinfo.domainobjects;

import com.cabletech.commons.base.*;

public class Usergroup extends BaseBean{
    private String id;
    private String groupname;
    private String regionid;
    private String remark;
    private String type = "";
    public Usergroup(){
    }


    public void setId( String id ){
        this.id = id;
    }


    public void setGroupname( String groupname ){
        this.groupname = groupname;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public void setType( String type ){
        this.type = type;
    }


    public String getId(){
        return id;
    }


    public String getGroupname(){
        return groupname;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getRemark(){
        return remark;
    }


    public String getType(){
        return type;
    }
}
