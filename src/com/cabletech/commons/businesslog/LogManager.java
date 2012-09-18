package com.cabletech.commons.businesslog;

public class LogManager{
    private static LogManager manager;
    private LogHandle handle;
    public LogManager(){
        handle = new LogHandle();
    }


    public static LogManager getLogManager(){
        if( manager == null ){
            manager = new LogManager();
        }
        return manager;
    }


    public void log( LogRecord logRecord ){
        handle.publish( logRecord );
    }


    public void log( String username, String module, String Msg, String ip, String memo ){
        LogRecord logRecord = new LogRecord();
        logRecord.setUser( username );
        logRecord.setIp( ip );
        logRecord.setUser( username );
        logRecord.setModule( module );
        logRecord.setMessage( Msg );
        logRecord.setMemo( memo );

        log( logRecord );
    }

}
