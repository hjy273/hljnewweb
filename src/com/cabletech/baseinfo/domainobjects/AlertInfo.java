package com.cabletech.baseinfo.domainobjects;

import com.cabletech.commons.base.*;

public class AlertInfo extends BaseDomainObject{
    // Public Methods

    public void setId( String Id ){
        this.Id = Id;
    }


    public String getId(){
        return Id;
    }


    public void setExecutorid( String Executorid ){
        this.Executorid = Executorid;
    }


    public String getExecutorid(){
        return Executorid;
    }


    public void setSimid( String Simid ){
        this.Simid = Simid;
    }


    public String getSimid(){
        return Simid;
    }


    public void setExecutetime( String executetime ){
        this.executetime = executetime;
    }


    public void setType( String type ){
        this.type = type;
    }


    public void setOperationcode( String operationcode ){
        this.operationcode = operationcode;
    }


    public void setGPSCoordinate( String GPSCoordinate ){
        this.GPSCoordinate = GPSCoordinate;
    }


    public void setLid( String Lid ){
        this.Lid = Lid;
    }


    public void setPid( String Pid ){
        this.Pid = Pid;
    }


    public String getExecutetime(){
        return executetime;
    }


    public String getType(){
        return type;
    }


    public String getOperationcode(){
        return operationcode;
    }


    public String getGPSCoordinate(){
        return GPSCoordinate;
    }


    public String getLid(){
        return Lid;
    }


    public String getPid(){
        return Pid;
    }


    private String Id;
    private String Executorid;
    private String Simid;
    private String executetime;
    private String type;
    private String Pid;
    private String Lid;
    private String GPSCoordinate;
    private String operationcode;

}
