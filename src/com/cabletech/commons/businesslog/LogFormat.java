package com.cabletech.commons.businesslog;

import org.apache.log4j.*;

/*
 create table businesslog  ( logdate             VARCHAR2(40)           not null,
   ip             VARCHAR2(40)             not null,
   username          VARCHAR2(40)          not null,
   module          VARCHAR2(40)                  ,
   message          VARCHAR2(200)                   ,
   memo          VARCHAR2(200)
 );
 */

public class LogFormat{
    private Logger logger = Logger.getLogger( LogFormat.class );

    public synchronized String formatMessage( LogRecord record ){
        StringBuffer strBuf = new StringBuffer(
                              "INSERT INTO businesslog (logdate, ip, username,module,message,memo ) VALUES ( " );
        strBuf.append( "to_date('" + record.getDate() + "','YYYY/MM/DD HH24:MI:ss') ," );
        strBuf.append( "'" + record.getIp() + "'," );
        strBuf.append( "'" + record.getUser() + "'," );
        strBuf.append( "'" + record.getModule() + "'," );
        strBuf.append( "'" + record.getMessage() + "'," );
        strBuf.append( "'" + record.getMemo() + "')" );
        logger.info( strBuf.toString() );
        return strBuf.toString();
    }

}
