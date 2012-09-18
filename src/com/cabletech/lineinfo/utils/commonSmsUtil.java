package com.cabletech.lineinfo.utils;

public class commonSmsUtil{
    public commonSmsUtil(){
    }


    public static String removeNullValue( String str ){
        if( str == null ){
            str = "";
        }
        return str;
    }


    public static String replaceNullValue( String str, String str2 ){
        if( str == null ){
            str = str2;
        }
        return str;
    }

}
