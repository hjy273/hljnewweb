package com.cabletech.linechange.domainobjects;

import java.io.*;

/**
 * This is an object that contains data related to the CHANGEINFO table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="CHANGEINFO"
 */
public class ChangeInfo
    implements Serializable{

    public ChangeInfo(){
        initialize();
    }


    /**
     * Constructor for required fields
     */
    public ChangeInfo(
        java.lang.String id ){

        this.setId( id );
        initialize();
    }


    protected void initialize(){}


    // fields
    private java.lang.String applydatumid;
    private java.lang.String applyperson;
    private String applyunit;
    private java.util.Date applytime;
    private java.lang.String approveresult;
    private java.lang.Float budget;
    private java.lang.String changeaddr;
    private java.lang.Float changelength;
    private java.lang.String changename;
    private java.lang.String changepro;
    private java.util.Date constartdate;
    private java.lang.Float cost;
    private java.lang.String daddr;
    private java.lang.Float dexpense;
    private java.lang.String dgrade;
    private java.lang.String dname;
    private java.lang.String dperson;
    private java.lang.String dphone;
    private java.lang.String eaddr;
    private java.lang.String ename;
    private java.lang.String entrustaddr;
    private java.lang.String entrustdatum;
    private java.lang.String entrustgrade;
    private java.lang.String entrustnote;
    private java.lang.String entrustperson;
    private java.lang.String entrustphone;
    private java.lang.String entrustremark;
    private java.util.Date entrusttime;
    private java.lang.String entrustunit;
    private java.lang.String eperson;
    private java.lang.String ephone;
    private java.lang.String id;
    private java.lang.String involvedSystem;
    private java.lang.String ischangedatum;
    private java.lang.String lineclass;
    private java.util.Date pageonholedate;
    private java.lang.String pageonholedatum;
    private java.lang.String pageonholeperson;
    private java.lang.Float plantime;
    private java.lang.String regionid;
    private java.lang.String remark;
    private java.lang.String saddr;
    private java.lang.String setoutdatum;
    private java.lang.String setoutperson;
    private java.lang.String setoutremark;
    private java.util.Date setouttime;
    private java.lang.Float sexpense;
    private java.lang.String sgrade;
    private java.lang.String sname;
    private java.lang.String sperson;
    private java.lang.String sphone;
    private java.lang.Float square;
    private java.util.Date startdate;
    private java.lang.String step;
    private java.lang.String buildunit;
    private java.util.Date starttime;
    private String superviseUnitId;
    private String pageonholeremark;

    /**
     * Return the value associated with the column: APPLYDATUMID
     */
    public java.lang.String getApplydatumid(){
        return applydatumid;
    }


    /**
     * Set the value related to the column: APPLYDATUMID
     * @param applydatumid the APPLYDATUMID value
     */
    public void setApplydatumid( java.lang.String applydatumid ){
        this.applydatumid = applydatumid;
    }


    /**
     * Return the value associated with the column: APPLYPERSON
     */
    public java.lang.String getApplyperson(){
        return applyperson;
    }


    /**
     * Set the value related to the column: APPLYPERSON
     * @param applyperson the APPLYPERSON value
     */
    public void setApplyperson( java.lang.String applyperson ){
        this.applyperson = applyperson;
    }


    /**
     * Return the value associated with the column: APPLYTIME
     */
    public java.util.Date getApplytime(){
        return applytime;
    }


    /**
     * Set the value related to the column: APPLYTIME
     * @param applytime the APPLYTIME value
     */
    public void setApplytime( java.util.Date applytime ){
        this.applytime = applytime;
    }


    /**
     * Return the value associated with the column: APPROVERESULT
     */
    public java.lang.String getApproveresult(){
        return approveresult;
    }


    /**
     * Set the value related to the column: APPROVERESULT
     * @param approveresult the APPROVERESULT value
     */
    public void setApproveresult( java.lang.String approveresult ){
        this.approveresult = approveresult;
    }


    /**
     * Return the value associated with the column: BUDGET
     */
    public java.lang.Float getBudget(){
        return budget;
    }


    /**
     * Set the value related to the column: BUDGET
     * @param budget the BUDGET value
     */
    public void setBudget( java.lang.Float budget ){
        this.budget = budget;
    }


    /**
     * Return the value associated with the column: CHANGEADDR
     */
    public java.lang.String getChangeaddr(){
        return changeaddr;
    }


    /**
     * Set the value related to the column: CHANGEADDR
     * @param changeaddr the CHANGEADDR value
     */
    public void setChangeaddr( java.lang.String changeaddr ){
        this.changeaddr = changeaddr;
    }


    /**
     * Return the value associated with the column: CHANGELENGTH
     */
    public java.lang.Float getChangelength(){
        return changelength;
    }


    /**
     * Set the value related to the column: CHANGELENGTH
     * @param changelength the CHANGELENGTH value
     */
    public void setChangelength( java.lang.Float changelength ){
        this.changelength = changelength;
    }


    /**
     * Return the value associated with the column: CHANGENAME
     */
    public java.lang.String getChangename(){
        return changename;
    }


    /**
     * Set the value related to the column: CHANGENAME
     * @param changename the CHANGENAME value
     */
    public void setChangename( java.lang.String changename ){
        this.changename = changename;
    }


    /**
     * Return the value associated with the column: CHANGEPRO
     */
    public java.lang.String getChangepro(){
        return changepro;
    }


    /**
     * Set the value related to the column: CHANGEPRO
     * @param changepro the CHANGEPRO value
     */
    public void setChangepro( java.lang.String changepro ){
        this.changepro = changepro;
    }


    /**
     * Return the value associated with the column: CONSTARTDATE
     */
    public java.util.Date getConstartdate(){
        return constartdate;
    }


    /**
     * Set the value related to the column: CONSTARTDATE
     * @param constartdate the CONSTARTDATE value
     */
    public void setConstartdate( java.util.Date constartdate ){
        this.constartdate = constartdate;
    }


    /**
     * Return the value associated with the column: COST
     */
    public java.lang.Float getCost(){
        return cost;
    }


    /**
     * Set the value related to the column: COST
     * @param cost the COST value
     */
    public void setCost( java.lang.Float cost ){
        this.cost = cost;
    }


    /**
     * Return the value associated with the column: DADDR
     */
    public java.lang.String getDaddr(){
        return daddr;
    }


    /**
     * Set the value related to the column: DADDR
     * @param daddr the DADDR value
     */
    public void setDaddr( java.lang.String daddr ){
        this.daddr = daddr;
    }


    /**
     * Return the value associated with the column: DEXPENSE
     */
    public java.lang.Float getDexpense(){
        return dexpense;
    }


    /**
     * Set the value related to the column: DEXPENSE
     * @param dexpense the DEXPENSE value
     */
    public void setDexpense( java.lang.Float dexpense ){
        this.dexpense = dexpense;
    }


    /**
     * Return the value associated with the column: DGRADE
     */
    public java.lang.String getDgrade(){
        return dgrade;
    }


    /**
     * Set the value related to the column: DGRADE
     * @param dgrade the DGRADE value
     */
    public void setDgrade( java.lang.String dgrade ){
        this.dgrade = dgrade;
    }


    /**
     * Return the value associated with the column: DNAME
     */
    public java.lang.String getDname(){
        return dname;
    }


    /**
     * Set the value related to the column: DNAME
     * @param dname the DNAME value
     */
    public void setDname( java.lang.String dname ){
        this.dname = dname;
    }


    /**
     * Return the value associated with the column: DPERSON
     */
    public java.lang.String getDperson(){
        return dperson;
    }


    /**
     * Set the value related to the column: DPERSON
     * @param dperson the DPERSON value
     */
    public void setDperson( java.lang.String dperson ){
        this.dperson = dperson;
    }


    /**
     * Return the value associated with the column: DPHONE
     */
    public java.lang.String getDphone(){
        return dphone;
    }


    /**
     * Set the value related to the column: DPHONE
     * @param dphone the DPHONE value
     */
    public void setDphone( java.lang.String dphone ){
        this.dphone = dphone;
    }


    /**
     * Return the value associated with the column: EADDR
     */
    public java.lang.String getEaddr(){
        return eaddr;
    }


    /**
     * Set the value related to the column: EADDR
     * @param eaddr the EADDR value
     */
    public void setEaddr( java.lang.String eaddr ){
        this.eaddr = eaddr;
    }


    /**
     * Return the value associated with the column: ENAME
     */
    public java.lang.String getEname(){
        return ename;
    }


    /**
     * Set the value related to the column: ENAME
     * @param ename the ENAME value
     */
    public void setEname( java.lang.String ename ){
        this.ename = ename;
    }


    /**
     * Return the value associated with the column: ENTRUSTADDR
     */
    public java.lang.String getEntrustaddr(){
        return entrustaddr;
    }


    /**
     * Set the value related to the column: ENTRUSTADDR
     * @param entrustaddr the ENTRUSTADDR value
     */
    public void setEntrustaddr( java.lang.String entrustaddr ){
        this.entrustaddr = entrustaddr;
    }


    /**
     * Return the value associated with the column: ENTRUSTDATUM
     */
    public java.lang.String getEntrustdatum(){
        return entrustdatum;
    }


    /**
     * Set the value related to the column: ENTRUSTDATUM
     * @param entrustdatum the ENTRUSTDATUM value
     */
    public void setEntrustdatum( java.lang.String entrustdatum ){
        this.entrustdatum = entrustdatum;
    }


    /**
     * Return the value associated with the column: ENTRUSTGRADE
     */
    public java.lang.String getEntrustgrade(){
        return entrustgrade;
    }


    /**
     * Set the value related to the column: ENTRUSTGRADE
     * @param entrustgrade the ENTRUSTGRADE value
     */
    public void setEntrustgrade( java.lang.String entrustgrade ){
        this.entrustgrade = entrustgrade;
    }


    /**
     * Return the value associated with the column: ENTRUSTNOTE
     */
    public java.lang.String getEntrustnote(){
        return entrustnote;
    }


    /**
     * Set the value related to the column: ENTRUSTNOTE
     * @param entrustnote the ENTRUSTNOTE value
     */
    public void setEntrustnote( java.lang.String entrustnote ){
        this.entrustnote = entrustnote;
    }


    /**
     * Return the value associated with the column: ENTRUSTPERSON
     */
    public java.lang.String getEntrustperson(){
        return entrustperson;
    }


    /**
     * Set the value related to the column: ENTRUSTPERSON
     * @param entrustperson the ENTRUSTPERSON value
     */
    public void setEntrustperson( java.lang.String entrustperson ){
        this.entrustperson = entrustperson;
    }


    /**
     * Return the value associated with the column: ENTRUSTPHONE
     */
    public java.lang.String getEntrustphone(){
        return entrustphone;
    }


    /**
     * Set the value related to the column: ENTRUSTPHONE
     * @param entrustphone the ENTRUSTPHONE value
     */
    public void setEntrustphone( java.lang.String entrustphone ){
        this.entrustphone = entrustphone;
    }


    /**
     * Return the value associated with the column: ENTRUSTREMARK
     */
    public java.lang.String getEntrustremark(){
        return entrustremark;
    }


    /**
     * Set the value related to the column: ENTRUSTREMARK
     * @param entrustremark the ENTRUSTREMARK value
     */
    public void setEntrustremark( java.lang.String entrustremark ){
        this.entrustremark = entrustremark;
    }


    /**
     * Return the value associated with the column: ENTRUSTTIME
     */
    public java.util.Date getEntrusttime(){
        return entrusttime;
    }


    /**
     * Set the value related to the column: ENTRUSTTIME
     * @param entrusttime the ENTRUSTTIME value
     */
    public void setEntrusttime( java.util.Date entrusttime ){
        this.entrusttime = entrusttime;
    }


    /**
     * Return the value associated with the column: ENTRUSTUNIT
     */
    public java.lang.String getEntrustunit(){
        return entrustunit;
    }


    /**
     * Set the value related to the column: ENTRUSTUNIT
     * @param entrustunit the ENTRUSTUNIT value
     */
    public void setEntrustunit( java.lang.String entrustunit ){
        this.entrustunit = entrustunit;
    }


    /**
     * Return the value associated with the column: EPERSON
     */
    public java.lang.String getEperson(){
        return eperson;
    }


    /**
     * Set the value related to the column: EPERSON
     * @param eperson the EPERSON value
     */
    public void setEperson( java.lang.String eperson ){
        this.eperson = eperson;
    }


    /**
     * Return the value associated with the column: EPHONE
     */
    public java.lang.String getEphone(){
        return ephone;
    }


    /**
     * Set the value related to the column: EPHONE
     * @param ephone the EPHONE value
     */
    public void setEphone( java.lang.String ephone ){
        this.ephone = ephone;
    }


    /**
     * Return the value associated with the column: ID
     */
    public java.lang.String getId(){
        return id;
    }


    /**
     * Set the value related to the column: ID
     * @param id the ID value
     */
    public void setId( java.lang.String id ){
        this.id = id;
    }


    /**
     * Return the value associated with the column: INVOLVED_SYSTEM
     */
    public java.lang.String getInvolvedSystem(){
        return involvedSystem;
    }


    /**
     * Set the value related to the column: INVOLVED_SYSTEM
     * @param involvedSystem the INVOLVED_SYSTEM value
     */
    public void setInvolvedSystem( java.lang.String involvedSystem ){
        this.involvedSystem = involvedSystem;
    }


    /**
     * Return the value associated with the column: ISCHANGEDATUM
     */
    public java.lang.String getIschangedatum(){
        return ischangedatum;
    }


    /**
     * Set the value related to the column: ISCHANGEDATUM
     * @param ischangedatum the ISCHANGEDATUM value
     */
    public void setIschangedatum( java.lang.String ischangedatum ){
        this.ischangedatum = ischangedatum;
    }


    /**
     * Return the value associated with the column: LINECLASS
     */
    public java.lang.String getLineclass(){
        return lineclass;
    }


    /**
     * Set the value related to the column: LINECLASS
     * @param lineclass the LINECLASS value
     */
    public void setLineclass( java.lang.String lineclass ){
        this.lineclass = lineclass;
    }


    /**
     * Return the value associated with the column: PageonholeDATE
     */
    public java.util.Date getPageonholedate(){
        return pageonholedate;
    }


    /**
     * Set the value related to the column: PageonholeDATE
     * @param pageonholedate the PageonholeDATE value
     */
    public void setPageonholedate( java.util.Date pageonholedate ){
        this.pageonholedate = pageonholedate;
    }


    /**
     * Return the value associated with the column: PageonholeDATUM
     */
    public java.lang.String getPageonholedatum(){
        return pageonholedatum;
    }


    /**
     * Set the value related to the column: PageonholeDATUM
     * @param pageonholedatum the PageonholeDATUM value
     */
    public void setPageonholedatum( java.lang.String pageonholedatum ){
        this.pageonholedatum = pageonholedatum;
    }


    /**
     * Return the value associated with the column: PageonholePERSON
     */
    public java.lang.String getPageonholeperson(){
        return pageonholeperson;
    }


    /**
     * Set the value related to the column: PageonholePERSON
     * @param pageonholeperson the PageonholePERSON value
     */
    public void setPageonholeperson( java.lang.String pageonholeperson ){
        this.pageonholeperson = pageonholeperson;
    }


    /**
     * Return the value associated with the column: PLANTIME
     */
    public java.lang.Float getPlantime(){
        return plantime;
    }


    /**
     * Set the value related to the column: PLANTIME
     * @param plantime the PLANTIME value
     */
    public void setPlantime( java.lang.Float plantime ){
        this.plantime = plantime;
    }


    /**
     * Return the value associated with the column: REGIONID
     */
    public java.lang.String getRegionid(){
        return regionid;
    }


    /**
     * Set the value related to the column: REGIONID
     * @param regionid the REGIONID value
     */
    public void setRegionid( java.lang.String regionid ){
        this.regionid = regionid;
    }


    /**
     * Return the value associated with the column: REMARK
     */
    public java.lang.String getRemark(){
        return remark;
    }


    /**
     * Set the value related to the column: REMARK
     * @param remark the REMARK value
     */
    public void setRemark( java.lang.String remark ){
        this.remark = remark;
    }


    /**
     * Return the value associated with the column: SADDR
     */
    public java.lang.String getSaddr(){
        return saddr;
    }


    /**
     * Set the value related to the column: SADDR
     * @param saddr the SADDR value
     */
    public void setSaddr( java.lang.String saddr ){
        this.saddr = saddr;
    }


    /**
     * Return the value associated with the column: SETOUTDATUM
     */
    public java.lang.String getSetoutdatum(){
        return setoutdatum;
    }


    /**
     * Set the value related to the column: SETOUTDATUM
     * @param setoutdatum the SETOUTDATUM value
     */
    public void setSetoutdatum( java.lang.String setoutdatum ){
        this.setoutdatum = setoutdatum;
    }


    /**
     * Return the value associated with the column: SETOUTPERSON
     */
    public java.lang.String getSetoutperson(){
        return setoutperson;
    }


    /**
     * Set the value related to the column: SETOUTPERSON
     * @param setoutperson the SETOUTPERSON value
     */
    public void setSetoutperson( java.lang.String setoutperson ){
        this.setoutperson = setoutperson;
    }


    /**
     * Return the value associated with the column: SETOUTREMARK
     */
    public java.lang.String getSetoutremark(){
        return setoutremark;
    }


    /**
     * Set the value related to the column: SETOUTREMARK
     * @param setoutremark the SETOUTREMARK value
     */
    public void setSetoutremark( java.lang.String setoutremark ){
        this.setoutremark = setoutremark;
    }


    /**
     * Return the value associated with the column: SETOUTTIME
     */
    public java.util.Date getSetouttime(){
        return setouttime;
    }


    /**
     * Set the value related to the column: SETOUTTIME
     * @param setouttime the SETOUTTIME value
     */
    public void setSetouttime( java.util.Date setouttime ){
        this.setouttime = setouttime;
    }


    /**
     * Return the value associated with the column: SEXPENSE
     */
    public java.lang.Float getSexpense(){
        return sexpense;
    }


    /**
     * Set the value related to the column: SEXPENSE
     * @param sexpense the SEXPENSE value
     */
    public void setSexpense( java.lang.Float sexpense ){
        this.sexpense = sexpense;
    }


    /**
     * Return the value associated with the column: SGRADE
     */
    public java.lang.String getSgrade(){
        return sgrade;
    }


    /**
     * Set the value related to the column: SGRADE
     * @param sgrade the SGRADE value
     */
    public void setSgrade( java.lang.String sgrade ){
        this.sgrade = sgrade;
    }


    /**
     * Return the value associated with the column: SNAME
     */
    public java.lang.String getSname(){
        return sname;
    }


    /**
     * Set the value related to the column: SNAME
     * @param sname the SNAME value
     */
    public void setSname( java.lang.String sname ){
        this.sname = sname;
    }


    /**
     * Return the value associated with the column: SPERSON
     */
    public java.lang.String getSperson(){
        return sperson;
    }


    /**
     * Set the value related to the column: SPERSON
     * @param sperson the SPERSON value
     */
    public void setSperson( java.lang.String sperson ){
        this.sperson = sperson;
    }


    /**
     * Return the value associated with the column: SPHONE
     */
    public java.lang.String getSphone(){
        return sphone;
    }


    /**
     * Set the value related to the column: SPHONE
     * @param sphone the SPHONE value
     */
    public void setSphone( java.lang.String sphone ){
        this.sphone = sphone;
    }


    /**
     * Return the value associated with the column: SQUARE
     */
    public java.lang.Float getSquare(){
        return square;
    }


    /**
     * Set the value related to the column: SQUARE
     * @param square the SQUARE value
     */
    public void setSquare( java.lang.Float square ){
        this.square = square;
    }


    /**
     * Return the value associated with the column: STARTDATE
     */
    public java.util.Date getStartdate(){
        return startdate;
    }


    /**
     * Set the value related to the column: STARTDATE
     * @param startdate the STARTDATE value
     */
    public void setStartdate( java.util.Date startdate ){
        this.startdate = startdate;
    }


    /**
     * Return the value associated with the column: STEP
     */
    public java.lang.String getStep(){
        return step;
    }


    public String getApplyunit(){
        return applyunit;
    }


    public String getPageonholeremark(){
        return pageonholeremark;
    }


    /**
     * Set the value related to the column: STEP
     * @param step the STEP value
     */
    public void setStep( java.lang.String step ){
        this.step = step;
    }


    public void setApplyunit( String applyunit ){
        this.applyunit = applyunit;
    }


    public void setPageonholeremark( String pageonholeremark ){
        this.pageonholeremark = pageonholeremark;
    }


    public String toString(){
        return super.toString();
    }


    public java.lang.String getBuildunit() {
        return buildunit;
    }


    public void setBuildunit(java.lang.String buildunit) {
        this.buildunit = buildunit;
    }


    public java.util.Date getStarttime() {
        return starttime;
    }


    public void setStarttime(java.util.Date starttime) {
        this.starttime = starttime;
    }


    public String getSuperviseUnitId() {
        return superviseUnitId;
    }


    public void setSuperviseUnitId(String superviseUnitId) {
        this.superviseUnitId = superviseUnitId;
    }

}
