package com.cabletech.sysmanage.domainobjects;

import com.cabletech.commons.base.*;

public class Module extends BaseDomainObject{
    public Module(){
    }


    private String id;
    private String modulename;
    private String hrefurl;
    private String imageurl;
    private String remark;
    private String iftoplevel;

    public void setId( String id ){
        this.id = id;
    }


    public void setModulename( String modulename ){
        this.modulename = modulename;
    }


    public void setHrefurl( String hrefurl ){
        this.hrefurl = hrefurl;
    }


    public void setImageurl( String imageurl ){
        this.imageurl = imageurl;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public void setIftoplevel( String iftoplevel ){
        this.iftoplevel = iftoplevel;
    }


    public String getId(){
        return id;
    }


    public String getModulename(){
        return modulename;
    }


    public String getHrefurl(){
        return hrefurl;
    }


    public String getImageurl(){
        return imageurl;
    }


    public String getRemark(){
        return remark;
    }


    public String getIftoplevel(){
        return iftoplevel;
    }


    private void jbInit() throws Exception{
    }

}
