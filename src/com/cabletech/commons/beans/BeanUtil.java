package com.cabletech.commons.beans;

import java.lang.reflect.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.Logger;

public class BeanUtil{
	private static Logger log = Logger.getLogger( BeanUtil.class );

	//注册属性转换器，只需要执行一次
//	public static void registerConvert(){
//        ConvertUtils.register( new String2Date(), java.util.Date.class );
//        log.info("Register com.cabletech.commons.beans.String2Date Converter");        
//        ConvertUtils.register( new Date2String(), String.class );
//        log.info("Register com.cabletech.commons.beans.Date2String Converter");
//	}
	static{
		 ConvertUtils.register( new String2Date(), java.util.Date.class );
	     log.info("====Register com.cabletech.commons.beans.String2Date Converter");        
	     ConvertUtils.register( new Date2String(), String.class );
	     log.info("====Register com.cabletech.commons.beans.Date2String Converter");
	}
    /**
     * 对象的copy
     * @param from Object
     * @param to Object
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void objectCopy( Object from, Object to ) throws
        InvocationTargetException, IllegalAccessException{    	

        BeanUtils.copyProperties( to, from );
    }
    
    public static void copyProperties( Object from, Object to ){
    	try{
        	BeanUtils.copyProperties( to, from );
    	} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		}
    }
}
