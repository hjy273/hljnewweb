package com.cabletech.commons.hb;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;

import org.apache.log4j.*;
import org.springframework.context.ApplicationContext;

import com.cabletech.commons.config.SpringContext;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ConnectionManager{
    private static String dataSourceName = "dataSource";
    private static Logger logger = Logger.getLogger( "ConnectionManager" );
    private static DataSource ds = null;

    public synchronized Connection getCon(){
        Connection dbCon = null;
        //String dataSourceName=SMSystem.getConfig().getString("sm.datasource");
        if( ds == null ){
//            try{
//                Context ctx = new InitialContext();
//                ds = ( DataSource )ctx.lookup( dataSourceName );
            	 ApplicationContext context = SpringContext.getApplicationContext();
            	 ds = ( DataSource )context.getBean(dataSourceName );
//            }
//            catch( NamingException ex ){
//                logger.error( "获取数据源出错！！！", ex );
//            }
        }

        try{
            dbCon = ds.getConnection();
        }
        catch( SQLException ex ){
            logger.error( "获取数据连接时出错！！！", ex );
        }

        return dbCon;

    }


    public static void main( String arg[] ){
        new ConnectionManager().getCon();
    }


    public void closeCon( Connection dbCon ){
        if( dbCon != null ){
            try{
                dbCon.close();
                dbCon = null;
            }
            catch( SQLException ex ){
                logger.error( "关闭数据库连接时出错！！！", ex );
            }
        }
    }
}
