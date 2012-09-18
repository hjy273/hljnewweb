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
public class ChangeCheck  implements Serializable{
    public ChangeCheck(){
        initialize();
    }


    public ChangeCheck( java.lang.String id ){

        this.setId( id );
        initialize();

    }


    protected void initialize(){}


    // fields
    private java.lang.String changeid;
    private java.util.Date checkdate;
    private String checkdatum;
    private java.lang.String checkperson;
    private String checkremark;
    private String checkresult;
    private String fillinperson;
    private java.util.Date fillintime;
    private java.lang.String id;
    private String state;

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
     * Return the value associated with the column: checkdate
     */
    public java.util.Date getCheckdate(){
        return checkdate;
    }


    /**
     * Set the value related to the column: checkdate
     * @param checkdate the checkdate value
     */
    public void setCheckdate( java.util.Date checkdate ){
        this.checkdate = checkdate;
    }


    /**
     * Return the value associated with the column: checkdatum
     */
    public String getCheckdatum(){
        return checkdatum;
    }


    /**
     * Set the value related to the column: checkdatum
     * @param checkdatum the checkdatum value
     */
    public void setCheckdatum( String checkdatum ){
        this.checkdatum = checkdatum;
    }


    /**
     * Return the value associated with the column: checkperson
     */
    public java.lang.String getCheckperson(){
        return checkperson;
    }


    /**
     * Set the value related to the column: checkperson
     * @param checkperson the checkperson value
     */
    public void setCheckperson( java.lang.String checkperson ){
        this.checkperson = checkperson;
    }


    /**
     * Return the value associated with the column: checkremark
     */
    public String getCheckremark(){
        return checkremark;
    }


    /**
     * Set the value related to the column: checkremark
     * @param checkremark the checkremark value
     */
    public void setCheckremark( String checkremark ){
        this.checkremark = checkremark;
    }


    /**
     * Return the value associated with the column: checkresult
     */
    public String getCheckresult(){
        return checkresult;
    }


    /**
     * Set the value related to the column: checkresult
     * @param checkresult the checkresult value
     */
    public void setCheckresult( String checkresult ){
        this.checkresult = checkresult;
    }


    /**
     * Return the value associated with the column: fillinperson
     */
    public String getFillinperson(){
        return fillinperson;
    }


    /**
     * Set the value related to the column: fillinperson
     * @param fillinperson the fillinperson value
     */
    public void setFillinperson( String fillinperson ){
        this.fillinperson = fillinperson;
    }


    /**
     * Return the value associated with the column: fillintime
     */
    public java.util.Date getFillintime(){
        return fillintime;
    }


    /**
     * Set the value related to the column: fillintime
     * @param fillintime the fillintime value
     */
    public void setFillintime( java.util.Date fillintime ){
        this.fillintime = fillintime;
    }


    /**
     * Return the value associated with the column: ID
     */
    public java.lang.String getId(){
        return id;
    }


    public String getState(){
        return state;
    }


    /**
     * Set the value related to the column: ID
     * @param id the ID value
     */
    public void setId( java.lang.String id ){
        this.id = id;
    }


    public void setState( String state ){
        this.state = state;
    }


    public String toString(){
        return super.toString();
    }

}
