package com.cabletech.troublemanage.beans;

import com.cabletech.commons.base.*;

public class AccidentDetailBean extends BaseBean{
    private String id;
    private String accidentid;
    private String corecode;
    private String tempfixedtime;
    private String fixedtime;
    private String diachronic;
    public AccidentDetailBean(){
    }


    public void setId( String id ){
        this.id = id;
    }


    public void setAccidentid( String accidentid ){
        this.accidentid = accidentid;
    }


    public void setCorecode( String corecode ){
        this.corecode = corecode;
    }


    public void setTempfixedtime( String tempfixedtime ){
        this.tempfixedtime = tempfixedtime;
    }


    public void setFixedtime( String fixedtime ){
        this.fixedtime = fixedtime;
    }


    public void setDiachronic( String diachronic ){
        this.diachronic = diachronic;
    }


    public String getId(){
        return id;
    }


    public String getAccidentid(){
        return accidentid;
    }


    public String getCorecode(){
        return corecode;
    }


    public String getTempfixedtime(){
        return tempfixedtime;
    }


    public String getFixedtime(){
        return fixedtime;
    }


    public String getDiachronic(){
        return diachronic;
    }

}
