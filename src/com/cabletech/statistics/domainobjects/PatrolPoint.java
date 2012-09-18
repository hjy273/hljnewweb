package com.cabletech.statistics.domainobjects;

public class PatrolPoint{
    private String pointid;
    private String pointname;
    private String sublineid;
    private String addressInfo;
    private String sublinename;
    private String linetype;
    private int timesneedtopatroled;
    private int timespatroled;
    private String patrolid;
    public PatrolPoint(){
    }


    public String getPointid(){

        return pointid;
    }


    public void setPointid( String pointid ){

        this.pointid = pointid;
    }


    public String getPointname(){

        return pointname;
    }


    public void setPointname( String pointname ){

        this.pointname = pointname;
    }


    public String getSublineid(){

        return sublineid;
    }


    public void setSublineid( String sublineid ){

        this.sublineid = sublineid;
    }


    public String getAddressInfo(){
        return addressInfo;
    }


    public void setAddressInfo( String addressInfo ){
        this.addressInfo = addressInfo;
    }


    public String getSublinename(){

        return sublinename;
    }


    public void setSublinename( String sublinename ){

        this.sublinename = sublinename;
    }


    public String getLinetype(){
        return linetype;
    }


    public int getTimesneedtopatroled(){
        return timesneedtopatroled;
    }


    public int getTimespatroled(){
        return timespatroled;
    }


    public String getPatrolid(){
        return patrolid;
    }


    public void setLinetype( String linetype ){
        this.linetype = linetype;
    }


    public void setTimesneedtopatroled( int timesneedtopatroled ){
        this.timesneedtopatroled = timesneedtopatroled;
    }


    public void setTimespatroled( int timespatroled ){
        this.timespatroled = timespatroled;
    }


    public void setPatrolid( String patrolid ){
        this.patrolid = patrolid;
    }
}
