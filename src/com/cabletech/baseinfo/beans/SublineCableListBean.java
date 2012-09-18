package com.cabletech.baseinfo.beans;

import com.cabletech.commons.base.*;

public class SublineCableListBean extends BaseBean{

    public SublineCableListBean(){
    }


    private String kid;
    private String sublineid;
    private String cablesegmentid;

    public void setKid( String kid ){
        this.kid = kid;
    }


    public void setSublineid( String sublineid ){
        this.sublineid = sublineid;
    }


    public void setCablesegmentid( String cablesegmentid ){
        this.cablesegmentid = cablesegmentid;
    }


    public String getKid(){
        return kid;
    }


    public String getSublineid(){
        return sublineid;
    }


    public String getCablesegmentid(){
        return cablesegmentid;
    }
}
