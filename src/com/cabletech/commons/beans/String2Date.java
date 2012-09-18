package com.cabletech.commons.beans;

import java.text.*;
import java.util.*;

import org.apache.commons.beanutils.*;

public class String2Date implements Converter{
	
    private static final SimpleDateFormat DT_LONG_FORMAT = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
    private static final SimpleDateFormat DT_SHORT_FORMAT = new SimpleDateFormat( "yyyy/MM/dd" );
    private static final SimpleDateFormat DT_YEAR_MONTH_FORMAT = new SimpleDateFormat( "yyyy/MM" );

    // ----------------------------------------------------------- Constructors


    /**
	 * Create a {@link Converter} that will throw a {@link ConversionException}
	 * if a conversion error occurs.
	 */
    public String2Date(){

        this.defaultValue = null;
        this.useDefault = false;
    }


    /**
	 * Create a {@link Converter} that will return the specified default value
	 * if a conversion error occurs.
	 * 
	 * @param defaultValue
	 *            The default value to be returned
	 */
    public String2Date( Object defaultValue ){

        this.defaultValue = defaultValue;
        this.useDefault = true;

    }


    // ----------------------------------------------------- Instance Variables


    /**
	 * The default value specified to our Constructor, if any.
	 */
    private Object defaultValue = new java.util.Date();

    /**
	 * Should we return the default value on conversion errors?
	 */
    private boolean useDefault = true;

    // --------------------------------------------------------- Public Methods


    /**
	 * Convert the specified input object into an output object of the specified
	 * type.
	 * 
	 * @param type
	 *            Data type to which this value should be converted
	 * @param value
	 *            The input value to be converted
	 * 
	 * @exception ConversionException
	 *                if conversion cannot be performed successfully
	 */
    public Object convert( Class type, Object value ){
        if( value == null || "".equals(value)){
            if( useDefault ){
                return( defaultValue );
            }else{
                return null;
            }
        }
        if( value instanceof Date ){
            return( value );
        }
        
        java.util.Date result=null;
        String time = (String)value;
        try{
        	 if(time.length()==7){
             	return DT_YEAR_MONTH_FORMAT.parse(time);
             }
            try{
            	result = DT_LONG_FORMAT.parse(time);
            	return result;
            }catch(Exception e2){
            	// donothing
            }
        	result = DT_SHORT_FORMAT.parse(time);
        	return result;
        }
        catch( Exception e ){
            if( useDefault ){
                return( defaultValue );
            }
            else{
                throw new ConversionException( e );
            }
        }
    }
}
