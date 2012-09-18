package com.cabletech.commons.beans;

import java.text.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.Logger;

public class Date2String implements Converter{
	//private Logger logger = Logger.getLogger(Date2String.class);
	private static final SimpleDateFormat SHORT_FORMAT = new SimpleDateFormat( "yyyy/MM/dd" );
	private static final SimpleDateFormat LONG_FORMAT = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
    // ----------------------------------------------------------- Constructors


    /**
     * Create a {@link Converter} that will throw a {@link ConversionException}
     * if a conversion error occurs.
     */
    public Date2String(){
        this.defaultValue = null;
        this.useDefault = false;
    }


    /**
     * Create a {@link Converter} that will return the specified default value
     * if a conversion error occurs.
     *
     * @param defaultValue The default value to be returned
     */
    public Date2String( Object defaultValue ){
        this.defaultValue = defaultValue;
        this.useDefault = true;
    }


    // ----------------------------------------------------- Instance Variables
    /**
     * The default value specified to our Constructor, if any.
     */
    private Object defaultValue = "2004/12/23";

    /**
     * Should we return the default value on conversion errors?
     */
    private boolean useDefault = true;

    // --------------------------------------------------------- Public Methods


    /**
     * Convert the specified input object into an output object of the
     * specified type.
     *
     * @param type Data type to which this value should be converted
     * @param value The input value to be converted
     *
     * @exception ConversionException if conversion cannot be performed
     *  successfully
     */
    public Object convert( Class type, Object value ){
        //logger.info( value );
        if( value == null ){
            if( useDefault ){
                return( defaultValue );
            }
            else{
                return null;
            }
        }

        if( value instanceof String ){
            return( value );
        }

        if( value instanceof Number ){
            return( value.toString() );
        }

        try{
            String strDate = LONG_FORMAT.format( value );
            return strDate;
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
