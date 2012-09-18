package com.cabletech.lineinfo.beans;

import com.cabletech.commons.base.*;

public class GISCrossPointBean extends BaseBean{
    public GISCrossPointBean(){
    }


    private String id;
    private String crosspointname;
    private String gpscoordinate;
    private String status;
    private String remark;
    private String regionid;
    private String type;

    public void setId( String id ){
        this.id = id;
    }


    public void setCrosspointname( String crosspointname ){
        this.crosspointname = crosspointname;
    }


    public void setGpscoordinate( String gpscoordinate ){
        this.gpscoordinate = gpscoordinate;
    }


    public void setStatus( String status ){
        this.status = status;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setType( String type ){
        this.type = type;
    }


    public String getId(){
        return id;
    }


    public String getCrosspointname(){
        return crosspointname;
    }


    public String getGpscoordinate(){
        return gpscoordinate;
    }


    public String getStatus(){
        return status;
    }


    public String getRemark(){
        return remark;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getType(){
        return type;
    }

}
