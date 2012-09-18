package com.cabletech.linechange.domainobjects;

import java.io.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: Cable tech</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ChangeBuild implements Serializable{
    public ChangeBuild(){
        initialize();
    }


    /**
     * Constructor for required fields
     */
    public ChangeBuild(
        java.lang.String id ){

        this.setId( id );
        initialize();
    }


    protected void initialize(){}


// fields
    private java.lang.String buildaddr;
    private java.lang.String builddatum;
    private java.lang.String buildperson;
    private java.lang.String buildphone;
    private java.lang.String buildremark;
    private java.lang.String buildunit;
    private java.lang.String buildvalue;
    private java.lang.String changeid;
    private java.util.Date endtime;
    private java.util.Date fillindate;
    private java.lang.String fillinperson;
    private java.lang.String fillinunit;
    private java.lang.String id;
    private java.util.Date starttime;
    private String state;

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
    public java.util.Date getEndtime(){
        return endtime;
    }


    /**
     * Set the value related to the column: ENDTIME
     * @param endtime the ENDTIME value
     */
    public void setEndtime( java.util.Date endtime ){
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
    public java.util.Date getStarttime(){
        return starttime;
    }


    public String getState(){
        return state;
    }


    /**
     * Set the value related to the column: STARTTIME
     * @param starttime the STARTTIME value
     */
    public void setStarttime( java.util.Date starttime ){
        this.starttime = starttime;
    }


    public void setState( String state ){
        this.state = state;
    }


    public String toString(){
        return super.toString();
    }

}
