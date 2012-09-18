/**
 * 作者：袁素军
 * 日期：2006年3月4日
 * 说明：材料基本信息的form bean
 * */
package com.cabletech.partmanage.beans;

import com.cabletech.commons.base.*;

public class Part_baseInfoBean extends BaseBean{

    protected String id = "";
    protected String name = "";
    protected String unit = "";
    protected String type = "";
    protected String remark = "";
    protected String factory = "";
    private String regionid = "";
    private String state = "";

    public String getFactory(){
        return this.factory;
    }


    public void setFactory( String factory ){
        this.factory = factory;
    }


    public String getId(){
        return this.id;
    }


    public void setId( String id ){
        this.id = id;
    }


    public String getName(){
        return this.name;
    }


    public void setName( String name ){
        this.name = name;
    }


    public String getUnit(){
        return this.unit;
    }


    public void setUnit( String unit ){
        this.unit = unit;
    }


    public String getType(){
        return this.type;
    }


    public void setType( String type ){
        this.type = type;
    }


    public String getRemark(){
        return this.remark;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getState(){
        return state;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setState( String state ){
        this.state = state;
    }


    public Part_baseInfoBean(){
    }
}
