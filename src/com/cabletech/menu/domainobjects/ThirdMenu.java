package com.cabletech.menu.domainobjects;

import com.cabletech.commons.base.BaseDomainObject;

public class ThirdMenu  extends BaseDomainObject{
    public ThirdMenu(){
    }


    private String id;
    private String lablename;
    private String parentid;
    private String hrefurl;
    private String remark;
    public String getHrefurl(){
        return hrefurl;
    }


    public String getId(){
        return id;
    }


    public String getLablename(){
        return lablename;
    }


    public String getParentid(){
        return parentid;
    }


    public String getRemark(){
        return remark;
    }


    public void setHrefurl( String hrefurl ){
        this.hrefurl = hrefurl;
    }


    public void setId( String id ){
        this.id = id;
    }


    public void setLablename( String lablename ){
        this.lablename = lablename;
    }


    public void setParentid( String parentid ){
        this.parentid = parentid;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }
}
