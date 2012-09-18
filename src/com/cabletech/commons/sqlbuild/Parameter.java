package com.cabletech.commons.sqlbuild;

import com.cabletech.commons.util.DateUtil;
import com.cabletech.utils.*;

public class Parameter{
    String strParameter = null;
    int intParameter;
    double douParameter;
    float floatParameter;
    long longParameter;
    java.util.Date dateParameter = null;
    java.sql.Date sqldateParameter = null;
    boolean bParameter;

    int PARA_TYE;

    boolean bStringIsNullFlag = false;

    public Parameter(){
    }


    //这样如果,String类型参数是""字做为无效的参数,即自动删除该条件
    public void setStringFlag( boolean bflag ){
        bStringIsNullFlag = bflag;
    }


    public Parameter( int value ){
        intParameter = value;
        PARA_TYE = Type.INT_TYPE;
    }


    public Parameter( String value ){
        strParameter = value;
        PARA_TYE = Type.STRING_TYPE;
    }


    public Parameter( double value ){
        douParameter = value;
        PARA_TYE = Type.DOUBLE_TYPE;
    }


    public Parameter( float value ){
        floatParameter = value;
        PARA_TYE = Type.FLOAT_TYPE;
    }


    public Parameter( long value ){
        longParameter = value;
        PARA_TYE = Type.LONG_TYPE;
    }


    public Parameter( java.util.Date value ){
        dateParameter = value;
        PARA_TYE = Type.DATE_TYPE;
    }


    public Parameter( java.sql.Date value ){
        sqldateParameter = value;
        PARA_TYE = Type.SQLDATE_TYPE;
    }


    public Parameter( boolean value ){
        bParameter = value;
        PARA_TYE = Type.BOOLEAN_TYPE;
    }


    public String getValue(){
        String returnValue = "";
        switch( PARA_TYE ){
            case Type.STRING_TYPE:{
                returnValue = "'" + strParameter + "'";
                break;
            }
            case Type.DATE_TYPE:{

                returnValue = "to_date('" + DateUtil.DateToString( dateParameter,"yyyy-MM-dd HH:mm:ss" ) + "','YYYY/MM/DD  hh24:mi:ss')";
                break;
            }
            case Type.SQLDATE_TYPE:{
                returnValue = "to_date('" + DateUtil.DateToString( sqldateParameter,"yyyy-MM-dd HH:mm:ss" ) + "','YYYY/MM/DD hh24:mi:ss')";
                break;
            }
            case Type.INT_TYPE:{
                returnValue = "" + intParameter;
                break;
            }
            case Type.FLOAT_TYPE:{
                returnValue = "" + floatParameter;
                break;
            }

            case Type.DOUBLE_TYPE:{
                returnValue = "" + douParameter;
                break;
            }
            case Type.LONG_TYPE:{
                returnValue = "" + longParameter;
                break;
            }
            default:
                returnValue = "";
                break;
        }

        return returnValue;
    }


    public boolean isValidatorParameter(){
        boolean returnValue = true;
        switch( PARA_TYE ){
            case Type.STRING_TYPE:{
                returnValue = ( strParameter == null || strParameter.equals( "" ) ) ? false : true;
                break;
            }
            case Type.DATE_TYPE:{
                returnValue = ( dateParameter == null ) ? false : true;
                break;
            }
            case Type.SQLDATE_TYPE:{
                returnValue = ( sqldateParameter == null ) ? false : true;
                break;
            }
            default:
                break;
        }
        return returnValue;
    }


    class Type{
        public static final int INT_TYPE = 1;
        public static final int LONG_TYPE = 2;
        public static final int DOUBLE_TYPE = 3;
        public static final int FLOAT_TYPE = 4;
        public static final int DATE_TYPE = 5;
        public static final int SQLDATE_TYPE = 6;
        public static final int STRING_TYPE = 7;
        public static final int BOOLEAN_TYPE = 8;
    }
}
