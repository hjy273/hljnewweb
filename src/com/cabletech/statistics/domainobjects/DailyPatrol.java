package com.cabletech.statistics.domainobjects;

public class DailyPatrol{
    private String datestr;
    private String minpoint;
    private String maxpoint;
    private String ptimes;
    private String workcontent;
    private String accnum;
    public DailyPatrol(){
        try{
            jbInit();
        }
        catch( Exception ex ){
            ex.printStackTrace();
        }
    }


    public void setDatestr( String datestr ){
        this.datestr = datestr;
    }


    public void setMinpoint( String minpoint ){
        this.minpoint = minpoint;
    }


    public void setMaxpoint( String maxpoint ){
        this.maxpoint = maxpoint;
    }


    public void setPtimes( String ptimes ){

        this.ptimes = ptimes;
    }


    public void setWorkcontent( String workcontent ){
        this.workcontent = workcontent;
    }


    public void setAccnum( String accnum ){
        this.accnum = accnum;
    }


    public String getDatestr(){
        return datestr;
    }


    public String getMinpoint(){
        return minpoint;
    }


    public String getMaxpoint(){
        return maxpoint;
    }


    public String getPtimes(){

        return ptimes;
    }


    public String getWorkcontent(){
        return workcontent;
    }


    public String getAccnum(){
        return accnum;
    }


    private void jbInit() throws Exception{
    }
}
