package com.cabletech.troublemanage.beans;

import com.cabletech.commons.base.*;

public class QueryTroubleBean extends BaseBean{
    private String contractorid;
    private String contractorname;
    private String sublineid;
    private String sublinename;
    private String begintime;
    private String endtime;
    private String month;
    private String year;
    private String regionid;
    private String staObj;
    private String cyc;
    private String queryby;
    private String status;
    private String cyctype;
    private String whosend;
    private String type;
    private String sendto;
    private String sendtime;
    public QueryTroubleBean(){
        try{
            jbInit();
        }
        catch( Exception ex ){
            ex.printStackTrace();
        }
    }


    public void setContractorid( String contractorid ){
        this.contractorid = contractorid;
    }


    public void setContractorname( String contractorname ){
        this.contractorname = contractorname;
    }


    public void setSublineid( String sublineid ){
        this.sublineid = sublineid;
    }


    public void setSublinename( String sublinename ){
        this.sublinename = sublinename;
    }


    public void setBegintime( String begintime ){
        this.begintime = begintime;
    }


    public void setEndtime( String endtime ){
        this.endtime = endtime;
    }


    public void setMonth( String month ){
        this.month = month;
    }


    public void setYear( String year ){
        this.year = year;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setStaObj( String staObj ){
        this.staObj = staObj;
    }


    public void setCyc( String cyc ){
        this.cyc = cyc;
    }


    public void setQueryby( String queryby ){
        this.queryby = queryby;
    }


    public void setStatus( String status ){
        this.status = status;
    }


    public void setCyctype( String cyctype ){
        this.cyctype = cyctype;
    }


    public void setWhosend( String whosend ){
        this.whosend = whosend;
    }


    public void setType( String type ){
        this.type = type;
    }


    public void setSendto( String sendto ){
        this.sendto = sendto;
    }


    public void setSendtime( String sendtime ){
        this.sendtime = sendtime;
    }


    public String getContractorid(){
        return contractorid;
    }


    public String getContractorname(){
        return contractorname;
    }


    public String getSublineid(){
        return sublineid;
    }


    public String getSublinename(){
        return sublinename;
    }


    public String getBegintime(){
        return begintime;
    }


    public String getEndtime(){
        return endtime;
    }


    public String getMonth(){
        return month;
    }


    public String getYear(){
        return year;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getStaObj(){
        return staObj;
    }


    public String getCyc(){
        return cyc;
    }


    public String getQueryby(){
        return queryby;
    }


    public String getStatus(){
        return status;
    }


    public String getCyctype(){
        return cyctype;
    }


    public String getWhosend(){
        return whosend;
    }


    public String getType(){
        return type;
    }


    public String getSendto(){
        return sendto;
    }


    public String getSendtime(){
        return sendtime;
    }


    private void jbInit() throws Exception{
    }
}
