package com.cabletech.troublemanage.beans;

import java.util.*;

import com.cabletech.uploadfile.formbean.*;

public class AccidentBean extends BaseFileFormBean{
    private String keyid;
    private String patrolid;
    private String simid;
    private String sendtime;
    private String reprottime = "";
    private String optype;
    private String gpscoordinate;
    private String pid;
    private String lid;
    private String operationcode;
    private String befortime;
    private String noticetime;
    private String cooperateman;
    private String testtime;
    private String testman;
    private String distance;
    private String realdistance;
    private String fixedman;
    private String monitor;
    private String commander;
    private String resonandfix;
    private String breportman;
    private String bconfirmman;
    private String regionid;
    private String isallblock;
    private String status;
    private String contractorid;
    private String year;
    private String month;
    private Vector detailvct;
    private String type;
    private String whosend;
    private String sendto;
    private String datumid;
    public AccidentBean(){
        try{
            jbInit();
        }
        catch( Exception ex ){
            ex.printStackTrace();
        }
    }


    public void setKeyid( String keyid ){
        this.keyid = keyid;
    }


    public void setPatrolid( String patrolid ){
        this.patrolid = patrolid;
    }


    public void setSimid( String simid ){
        this.simid = simid;
    }


    public void setSendtime( String sendtime ){
        this.sendtime = sendtime;
    }


    public void setOptype( String optype ){
        this.optype = optype;
    }


    public void setGpscoordinate( String gpscoordinate ){
        this.gpscoordinate = gpscoordinate;
    }


    public void setPid( String pid ){
        this.pid = pid;
    }


    public void setLid( String lid ){
        this.lid = lid;
    }


    public void setOperationcode( String operationcode ){
        this.operationcode = operationcode;
    }


    public void setBefortime( String befortime ){
        this.befortime = befortime;
    }


    public void setNoticetime( String noticetime ){
        this.noticetime = noticetime;
    }


    public void setCooperateman( String cooperateman ){
        this.cooperateman = cooperateman;
    }


    public void setTesttime( String testtime ){
        this.testtime = testtime;
    }


    public void setTestman( String testman ){
        this.testman = testman;
    }


    public void setDistance( String distance ){
        this.distance = distance;
    }


    public void setRealdistance( String realdistance ){
        this.realdistance = realdistance;
    }


    public void setFixedman( String fixedman ){
        this.fixedman = fixedman;
    }


    public void setMonitor( String monitor ){
        this.monitor = monitor;
    }


    public void setCommander( String commander ){
        this.commander = commander;
    }


    public void setResonandfix( String resonandfix ){
        this.resonandfix = resonandfix;
    }


    public void setBreportman( String breportman ){
        this.breportman = breportman;
    }


    public void setBconfirmman( String bconfirmman ){
        this.bconfirmman = bconfirmman;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setIsallblock( String isallblock ){
        this.isallblock = isallblock;
    }


    public void setStatus( String status ){
        this.status = status;
    }


    public void setContractorid( String contractorid ){
        this.contractorid = contractorid;
    }


    public void setYear( String year ){
        this.year = year;
    }


    public void setMonth( String month ){
        this.month = month;
    }


    public void setDetailvct( Vector detailvct ){
        this.detailvct = detailvct;
    }


    public void setType( String type ){
        this.type = type;
    }


    public void setWhosend( String whosend ){
        this.whosend = whosend;
    }


    public void setSendto( String sendto ){
        this.sendto = sendto;
    }


    public void setDatumid( String datumid ){
        this.datumid = datumid;
    }


    public String getKeyid(){
        return keyid;
    }


    public String getPatrolid(){
        return patrolid;
    }


    public String getSimid(){
        return simid;
    }


    public String getSendtime(){
        return sendtime;
    }


    public String getOptype(){
        return optype;
    }


    public String getGpscoordinate(){
        return gpscoordinate;
    }


    public String getPid(){
        return pid;
    }


    public String getLid(){
        return lid;
    }


    public String getOperationcode(){
        return operationcode;
    }


    public String getBefortime(){
        return befortime;
    }


    public String getNoticetime(){
        return noticetime;
    }


    public String getCooperateman(){
        return cooperateman;
    }


    public String getTesttime(){
        return testtime;
    }


    public String getTestman(){
        return testman;
    }


    public String getDistance(){
        return distance;
    }


    public String getRealdistance(){
        return realdistance;
    }


    public String getFixedman(){
        return fixedman;
    }


    public String getMonitor(){
        return monitor;
    }


    public String getCommander(){
        return commander;
    }


    public String getResonandfix(){
        return resonandfix;
    }


    public String getBreportman(){
        return breportman;
    }


    public String getBconfirmman(){
        return bconfirmman;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getIsallblock(){
        return isallblock;
    }


    public String getStatus(){
        return status;
    }


    public String getContractorid(){
        return contractorid;
    }


    public String getYear(){
        return year;
    }


    public String getMonth(){
        return month;
    }


    public Vector getDetailvct(){
        return detailvct;
    }


    public String getType(){
        return type;
    }


    public String getWhosend(){
        return whosend;
    }


    public String getSendto(){
        return sendto;
    }


    public String getDatumid(){
        return datumid;
    }

       private String contractorname;
       private String sublineid;
       private String sublinename;
       private String begintime;
       private String endtime;
       private String staObj;
       private String cyc;
       private String queryby;
       private String cyctype;

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



       public void setStaObj( String staObj ){
           this.staObj = staObj;
       }


       public void setCyc( String cyc ){
           this.cyc = cyc;
       }


       public void setQueryby( String queryby ){
           this.queryby = queryby;
       }
       public void setCyctype( String cyctype ){
           this.cyctype = cyctype;
       }


    public void setReprottime( String reprottime ){
        this.reprottime = reprottime;
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
       public String getStaObj(){
           return staObj;
       }


       public String getCyc(){
           return cyc;
       }


       public String getQueryby(){
           return queryby;
       }





       public String getCyctype(){
           return cyctype;
       }


    public String getReprottime(){
        return reprottime;
    }


    private void jbInit() throws Exception{
    }

}
