package com.cabletech.commons.config;

import java.io.*;
import java.util.*;

public class SmsConInfo{
    private static SmsConInfo config;

    private String SmsServerip;
    private String SmsServerport;
    private String SmsServerapp;

    private String initSmsPath;
    private String localizeSmsPath;
    private String commonSmsPath;
    private String smsCachePath;

    public void setInitSmsPath( String initSmsPath ){
        this.initSmsPath = initSmsPath;
    }


    public void setLocalizeSmsPath( String localizeSmsPath ){
        this.localizeSmsPath = localizeSmsPath;
    }


    public void setCommonSmsPath( String commonSmsPath ){
        this.commonSmsPath = commonSmsPath;
    }


    public void setSmsCachePath( String smsCachePath ){
        this.smsCachePath = smsCachePath;
    }


    public String getInitSmsPath(){
        return initSmsPath;
    }


    public String getLocalizeSmsPath(){
        return localizeSmsPath;
    }


    public String getCommonSmsPath(){
        return commonSmsPath;
    }


    public String getSmsCachePath(){
        return smsCachePath;
    }


    public String getSmsServerip(){
        return SmsServerip;
    }


    public void setSmsServerip( String SmsServerip ){
        this.SmsServerip = SmsServerip;
    }


    public String getSmsServerport(){
        return SmsServerport;
    }


    public void setSmsServerport( String SmsServerport ){
        this.SmsServerport = SmsServerport;
    }


    public String getSmsServerapp(){
        return SmsServerapp;
    }


    public void setSmsServerapp( String SmsServerapp ){
        this.SmsServerapp = SmsServerapp;
    }


    /**
     *初始化GIS 信息
     */
    public static SmsConInfo newInstance(){
        try{
            if( config == null ){
                config = new SmsConInfo();
            }
        }
        catch( Exception ex ){
            ex.printStackTrace();
        }
        return config;
    }


    /**
     * 读具体属性
     */
    public SmsConInfo(){
        String filePath = ConfigPathUtil.getClassPathConfigFile(
                          "gisServer.properties" );
        Properties prop = new Properties();
        try{
            FileInputStream fis = new FileInputStream( filePath );
            prop.load( fis );
            this.setSmsServerip( prop.getProperty( "smsServerip" ) );
            this.setSmsServerport( prop.getProperty( "smsServerport" ) );
            this.setSmsServerapp( prop.getProperty( "smsServerapp" ) );

            this.setCommonSmsPath( prop.getProperty( "textMsgPage" ) );
            this.setInitSmsPath( prop.getProperty( "initTerminalMsgPage" ) );
            this.setLocalizeSmsPath( prop.getProperty( "localizeMsgPage" ) );

            this.setSmsCachePath( prop.getProperty( "cacheFreshPath" ) );

        }
        catch( IOException e ){
            e.printStackTrace();
        }

    }


    /**
     * 完整路径
     * @return String
     */
    public String getWholePath(){
        String wholePath = "http://" + this.getSmsServerip() + ":" +
                           this.getSmsServerport() +
                           "/" + this.getSmsServerapp() + "/";

        //System.out.println("完整GIS路径 ：" + wholePath);

        return wholePath;
    }
}
