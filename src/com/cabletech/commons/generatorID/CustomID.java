package com.cabletech.commons.generatorID;

import java.util.*;

import com.cabletech.commons.hb.*;
import org.apache.log4j.Logger;

public class CustomID{
	private Logger logger = Logger.getLogger(CustomID.class);
    public CustomID(){
    }


    public String[] getStrSeqs( int iTotal, String strTable,
        int iLength ) throws Exception{
        String[] strSeqs = new String[iTotal];
        String strSeq = "";
        //如果没有，创建一个

        doMakeSeq( strTable, iLength );

        for( int i = 0; i < iTotal; ){
            String sql = "select LPAD(SEQ_" + strTable.toUpperCase() +
                         "_ID.nextval," + String.valueOf( iLength ) +
                         ",'0')  from dual";
            //logger.info(" sql "+sql);
            QueryUtil queryutil = new QueryUtil();
            String[][] resultArr = queryutil.executeQueryGetArray( sql, "" );

            strSeq = resultArr[0][0].trim();

            QueryUtil queryutil2 = new QueryUtil();
            String sql2 = "select count(id) c from " + strTable.toUpperCase() + " where id='" + strSeq + "'";
            //logger.info( "sql2 " + sql2 );
            Vector resultVec = queryutil2.executeQueryGetVector( sql2 );
            String count = ( ( Vector )resultVec.get( 0 ) ).get( 0 ).toString();
            //logger.info( count );
            if( count.equals( "0" ) || count == "0" ){
                strSeqs[i] = strSeq;
                i++;
            }
        }
        return strSeqs;
    }
    public String[] getStrSeqs( int iTotal, String strTable,
            int iLength,String strIDName ) throws Exception{
    
            String[] strSeqs = new String[iTotal];
            String strSeq = "";
            //如果没有，创建一个

            doMakeSeq( strTable, iLength );

            for( int i = 0; i < iTotal; ){
                String sql = "select LPAD(SEQ_" + strTable.toUpperCase() +
                             "_ID.nextval," + String.valueOf( iLength ) +
                             ",'0')  from dual";
                //logger.info(" sql "+sql);
                QueryUtil queryutil = new QueryUtil();
                String[][] resultArr = queryutil.executeQueryGetArray( sql, "" );

                strSeq = resultArr[0][0].trim();

                QueryUtil queryutil2 = new QueryUtil();
                String sql2 = "select count("+strIDName+") c from " + strTable.toUpperCase() + " where "+strIDName+"='" + strSeq + "'";
                //logger.info( "sql2 " + sql2 );
                Vector resultVec = queryutil2.executeQueryGetVector( sql2 );
                String count = ( ( Vector )resultVec.get( 0 ) ).get( 0 ).toString();
                //logger.info( count );
                if( count.equals( "0" ) || count == "0" ){
                    strSeqs[i] = strSeq;
                    i++;
                }
            }
            return strSeqs;
        }
    public String[] getStrSeqs2( int iTotal, String strTable,
        int iLength ) throws Exception{
        String[] strSeqs = new String[iTotal];
        String strSeq = "";
        //如果没有，创建一个

        doMakeSeq( strTable, iLength );

        for( int i = 0; i < iTotal; ){
            String sql = "select LPAD(SEQ_" + strTable.toUpperCase() +
                         "_ID.nextval," + String.valueOf( iLength ) +
                         ",'0')  from dual";
            logger.info(" sql "+sql);
            QueryUtil queryutil = new QueryUtil();
            String[][] resultArr = queryutil.executeQueryGetArray( sql, "" );

            strSeq = resultArr[0][0].trim();

            QueryUtil queryutil2 = new QueryUtil();
            String sql2 = "select count(kid) c from " + strTable.toUpperCase() + " where kid='" + strSeq + "'";
            logger.info( "sql2 " + sql2 );
            Vector resultVec = queryutil2.executeQueryGetVector( sql2 );
            String count = ( ( Vector )resultVec.get( 0 ) ).get( 0 ).toString();
            //logger.info( count );
            if( count.equals( "0" ) || count == "0" ){
                strSeqs[i] = strSeq;
                i++;
            }
        }
        return strSeqs;
    }

    public void doMakeSeq( String strTable, int iLength ) throws Exception{
        QueryUtil queryutil = new QueryUtil();
        String sql =
            "select SEQUENCE_NAME  from USER_SEQUENCES  WHERE SEQUENCE_NAME='SEQ_" +
            strTable.toUpperCase() + "_ID'";
        Vector resultVct = queryutil.executeQueryGetVector( sql );
        StringBuffer maxvalue = new StringBuffer();
        for( int i = 0; i < iLength; i++ ){
            maxvalue.append( 9 );
        }
        if( maxvalue.length() == 0 ){
            maxvalue.append( "99999999999999999999" );
        }
        if( resultVct == null || resultVct.size() == 0 ){
            sql = "CREATE SEQUENCE  SEQ_" + strTable.toUpperCase() +
                  "_ID  START WITH 2 INCREMENT BY 1 MAXVALUE " + maxvalue.toString() + " cycle";

            UpdateUtil updateutil = new UpdateUtil();
            updateutil.executeUpdate( sql );
        }
    }
}
