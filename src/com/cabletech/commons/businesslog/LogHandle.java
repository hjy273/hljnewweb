package com.cabletech.commons.businesslog;

import com.cabletech.commons.hb.*;

public class LogHandle{
    private LogFormat sqlFormat;

    public LogHandle(){
        sqlFormat = new LogFormat();
    }


    public void publish( LogRecord record ){
        try{
            String msg = sqlFormat.formatMessage( record );
            //System.out.println(" Log ::::::::::::::::::::::::::::   " + msg);
            UpdateUtil dbUtil = new UpdateUtil();
            dbUtil.executeUpdate( msg );
        }
        catch( Exception ex ){
            ex.printStackTrace();
        }
    }
}
