package com.cabletech.linechange.bean;

import com.cabletech.commons.base.BaseBean;

public class ChangeBuildBean extends BaseBean{
    private java.lang.String buildaddr;
    private java.lang.String builddatum;
    private java.lang.String buildperson;
    private java.lang.String buildphone;
    private java.lang.String buildremark;
    private java.lang.String buildunit;
    private java.lang.String buildvalue;
    private java.lang.String changeid;
    private String endtime;
    private java.util.Date fillindate;
    private java.lang.String fillinperson;
    private java.lang.String fillinunit;
    private java.lang.String id;
    private String step;
    private String starttime;
    private String changename;
    private String changepro;
    private String lineclass;
    private String changeaddr;

    public ChangeBuildBean(){
        super();
       // starttime = DateUtil.getNowDateString( "yyyy/MM/dd" );
       // endtime = DateUtil.getNowDateString( "yyyy/MM/dd" );
    }

     /**
     * Return the value associated with the column: BUILDADDR
     */
    public java.lang.String getBuildaddr(){
        return buildaddr;
    }


    /**
     * Set the value related to the column: BUILDADDR
     * @param buildaddr the BUILDADDR value
     */
    public void setBuildaddr( java.lang.String buildaddr ){
        this.buildaddr = buildaddr;
    }


    /**
     * Return the value associated with the column: BUILDDATUM
     */
    public java.lang.String getBuilddatum(){
        return builddatum;
    }


    /**
     * Set the value related to the column: BUILDDATUM
     * @param builddatum the BUILDDATUM value
     */
    public void setBuilddatum( java.lang.String builddatum ){
        this.builddatum = builddatum;
    }


    /**
     * Return the value associated with the column: BUILDPERSON
     */
    public java.lang.String getBuildperson(){
        return buildperson;
    }


    /**
     * Set the value related to the column: BUILDPERSON
     * @param buildperson the BUILDPERSON value
     */
    public void setBuildperson( java.lang.String buildperson ){
        this.buildperson = buildperson;
    }


    /**
     * Return the value associated with the column: BUILDPHONE
     */
    public java.lang.String getBuildphone(){
        return buildphone;
    }


    /**
     * Set the value related to the column: BUILDPHONE
     * @param buildphone the BUILDPHONE value
     */
    public void setBuildphone( java.lang.String buildphone ){
        this.buildphone = buildphone;
    }


    /**
     * Return the value associated with the column: BUILDREMARK
     */
    public java.lang.String getBuildremark(){
        return buildremark;
    }


    /**
     * Set the value related to the column: BUILDREMARK
     * @param buildremark the BUILDREMARK value
     */
    public void setBuildremark( java.lang.String buildremark ){
        this.buildremark = buildremark;
    }


    /**
     * Return the value associated with the column: BUILDUNIT
     */
    public java.lang.String getBuildunit(){
        return buildunit;
    }


    /**
     * Set the value related to the column: BUILDUNIT
     * @param buildunit the BUILDUNIT value
     */
    public void setBuildunit( java.lang.String buildunit ){
        this.buildunit = buildunit;
    }


    /**
     * Return the value associated with the column: BUILDVALUE
     */
    public java.lang.String getBuildvalue(){
        return buildvalue;
    }


    /**
     * Set the value related to the column: BUILDVALUE
     * @param buildvalue the BUILDVALUE value
     */
    public void setBuildvalue( java.lang.String buildvalue ){
        this.buildvalue = buildvalue;
    }


    /**
     * Return the value associated with the column: CHANGEID
     */
    public java.lang.String getChangeid(){
        return changeid;
    }


    /**
     * Set the value related to the column: CHANGEID
     * @param changeid the CHANGEID value
     */
    public void setChangeid( java.lang.String changeid ){
        this.changeid = changeid;
    }


    /**
     * Return the value associated with the column: ENDTIME
     */
    public String getEndtime(){
        return endtime;
    }


    /**
     * Set the value related to the column: ENDTIME
     * @param endtime the ENDTIME value
     */
    public void setEndtime( String endtime ){
        this.endtime = endtime;
    }


    /**
     * Return the value associated with the column: FILLINDATE
     */
    public java.util.Date getFillindate(){
        return fillindate;
    }


    /**
     * Set the value related to the column: FILLINDATE
     * @param fillindate the FILLINDATE value
     */
    public void setFillindate( java.util.Date fillindate ){
        this.fillindate = fillindate;
    }


    /**
     * Return the value associated with the column: FILLINPERSON
     */
    public java.lang.String getFillinperson(){
        return fillinperson;
    }


    /**
     * Set the value related to the column: FILLINPERSON
     * @param fillinperson the FILLINPERSON value
     */
    public void setFillinperson( java.lang.String fillinperson ){
        this.fillinperson = fillinperson;
    }


    /**
     * Return the value associated with the column: FILLINUNIT
     */
    public java.lang.String getFillinunit(){
        return fillinunit;
    }


    /**
     * Set the value related to the column: FILLINUNIT
     * @param fillinunit the FILLINUNIT value
     */
    public void setFillinunit( java.lang.String fillinunit ){
        this.fillinunit = fillinunit;
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
     * Return the value associated with the column: STARTTIME
     */
    public String getStarttime(){
        return starttime;
    }


    public String getStep(){
        return step;
    }


    public String getChangename(){
        return changename;
    }


    public String getChangepro(){
        return changepro;
    }


    public String getLineclass(){
        return lineclass;
    }


    public String getChangeaddr(){
        return changeaddr;
    }


    /**
     * Set the value related to the column: STARTTIME
     * @param starttime the STARTTIME value
     */
    public void setStarttime( String starttime ){
        this.starttime = starttime;
    }


    public void setStep( String step ){
        this.step = step;
    }


    public void setChangename( String changename ){
        this.changename = changename;
    }


    public void setLineclass( String lineclass ){
        this.lineclass = lineclass;
    }


    public void setChangeaddr( String changeaddr ){
        this.changeaddr = changeaddr;
    }

}
