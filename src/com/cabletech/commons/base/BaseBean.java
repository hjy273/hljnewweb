package com.cabletech.commons.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;


/**
 * <p>Base bean encapsulating common bean attributes and functionality.</p>
 *
 * @author Copyright (c) 2003 by BEA Systems. All Rights Reserved.
 */
abstract public class BaseBean extends ActionForm{
    private static Logger logger = Logger.getLogger( BaseBean.class.getName() );

    
    // Instance Variables
    private String action = "";
    private String id = "";
//    private String patrolID;
//    private String regionID;
//    private String contractorID;

    public BaseBean(){}


    // Getters  xvzv
    public String getAction(){
        return this.action;
    }


    public String getId(){
        return this.id;
    }


    // Setters
    public void setAction( String action ){
        this.action = action;
    }


    public void setId( String id ){
        this.id = id;
    }


    public void setId( Integer id ){
        this.id = toStr( id );
    }


    // Utility
    protected String setDateStr( String str ){
        SimpleDateFormat sdf = null;
        String dateStr = "";
        try{
            sdf = new SimpleDateFormat( "MM/dd/yyyy" );
            Date d = sdf.parse( str );
            dateStr = sdf.format( d );
        }
        catch( ParseException e ){}
        return dateStr;
    }


    protected static String toStr( Object obj ){
        if( obj != null ){
            return obj.toString();
        }
        else{
            return null;
        }
    }


    // Validation
    /**
     * <p>String null check.</p>
     *
     * @param str
     */
    protected boolean isNotEmpty( String str ){
        return str != null && str.length() > 0;
    }


    /**
     * <p>String null check.</p>
     *
     * @param str
     */
    protected boolean isEmpty( String str ){
        return! ( isNotEmpty( str ) );
    }


    /**
     * <p>Check for valid email.  Format only.  Format: a@a.aaa</p>
     *
     * @param email
     * @return boolean
     */
    protected boolean isValidEmail( String email ){
        boolean valid = true;
        if( email == null ){
            valid = false;
            logger.warn( "Email: invalid" );
        }
        if( valid ){
            String invalid[] = {
                               " ", "#", "&", ":", ";", "^", "%", "!", "*", "(", ")"};
            for( int i = 0; i < invalid.length; i++ ){
                if( email.indexOf( invalid[i] ) >= 0 ){
                    valid = false;
                    logger.warn( "Email: invalid" );
                }
            }
        }
        if( valid ){
            int atFound = email.indexOf( "@" );
            int dotFound = email.indexOf( ".", atFound );

            if( atFound < 0 || dotFound < 0 || atFound + 1 == dotFound ){
                valid = false;
                logger.warn( "Email: invalid" );
            }
        }
        return valid;
    }


    /**
     * <p>Check for valid date, ignoring delimitator. Format: MM/DD/YYYY</p>
     *
     * @param date
     * @return boolean
     */
    protected boolean isValidDate( String date ){
        boolean valid = true;
        try{
            if( date.length() != 10 ){
                return false;
            }
            String m = date.substring( 0, 2 );
            String d = date.substring( 3, 5 );
            String y = date.substring( 6, 10 );
            Integer.parseInt( m );
            Integer.parseInt( d );
            Integer.parseInt( y );
        }
        catch( Exception e ){
            logger.warn( "Date: invalid" );
            return false;
        }
        return valid;
    }


    /**
     * <p>Check for valid string representation of number.</p>
     *
     * @param str
     * @return boolean
     */
    protected boolean isValidNumber( String str ){
        boolean valid = true;

        try{
            long tmpLong = Long.parseLong( str );
        }
        catch( NumberFormatException nfe ){
            logger.warn( "Number: invalid" );
            return false;
        }

        return valid;
    }


    /**
     * <p>Check for valid string representation of a number ignoring
     * given delimitator.</p>
     *
     * @param pStr
     * @param pDelimitator
     * @return boolean
     */
    protected boolean isValidStrNumber( String pStr, String pDelimitator ){
        String tmpStr = "";
        int location;
        boolean valid = true;

        location = pStr.indexOf( pDelimitator );
        if( location < 0 ){
            tmpStr = pStr;
        }
        else{
            tmpStr = pStr.replaceFirst( "\\" + pDelimitator, "" );

        }
        valid = isValidNumber( tmpStr );

        return valid;
    }


    /**
     * <p>Check for valid social security number. Format: 999999999</p>
     *
     * @param ssn
     * @return boolean
     */
    protected boolean isValidSsn( String ssn ){
        boolean valid = true;

        if( isEmpty( ssn ) ){
            logger.warn( "SSN: invalid" );
            valid = false;
        }
        else{
            if( ssn.length() != 9 ){
                logger.warn( "SSN: invalid" );
                valid = false;
            }
        }

        return valid;
    }

    public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
//	public String getContractorID() {
//		return contractorID;
//	}
//
//
//	public void setContractorID(String contractorID) {
//		this.contractorID = contractorID;
//	}
//
//
//	public String getPatrolID() {
//		return patrolID;
//	}
//
//
//	public void setPatrolID(String patrolID) {
//		this.patrolID = patrolID;
//	}
//
//
//	public String getRegionID() {
//		return regionID;
//	}
//
//
//	public void setRegionID(String regionID) {
//		this.regionID = regionID;
//	}
}
