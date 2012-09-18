package com.cabletech.manager.beans;

import com.cabletech.uploadfile.formbean.*;

public class SortBean extends BaseFileFormBean{
    private String userid = "";
    private String positionno = "";
    private String password = "";
    public SortBean(){
    }


    public void setUserid( String userid ){
        this.userid = userid;
    }


    public void setPositionno( String positionno ){
        this.positionno = positionno;
    }


    public void setPassword( String password ){
        this.password = password;
    }


    public String getUserid(){
        return userid;
    }


    public String getPositionno(){
        return positionno;
    }


    public String getPassword(){
        return password;
    }

}
