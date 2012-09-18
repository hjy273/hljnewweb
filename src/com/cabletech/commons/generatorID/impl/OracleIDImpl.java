package com.cabletech.commons.generatorID.impl;

import java.sql.*;
import java.util.*;

import com.cabletech.commons.generatorID.*;
import com.cabletech.commons.hb.*;
import com.cabletech.utils.*;

public class OracleIDImpl extends GeneratorID{
	/**
	 *  按照类型选择一个序列的值
	 * @param strType
	 * @return
	 */
	public java.lang.String getSeq( java.lang.String strTable, int iLength ){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String strFindSeqSQL = "";

		String strSQL = "";
		String strSeq = "";
		//设置seq MAXVALUE
//		StringBuffer maxvalue = new StringBuffer();
//		for(int i=0;i<iLength;i++){
//		maxvalue.append(9);
//		}
//		if(maxvalue.length()==0){
//		maxvalue.append("9999999999");
//		}
//		MAXVALUE "+maxvalue.toString()+" cycle
		try{

			connection = HibernateSession.currentSession().connection();

			strFindSeqSQL = "select SEQUENCE_NAME  from USER_SEQUENCES  WHERE SEQUENCE_NAME='SEQ_"
				+ strTable.toUpperCase() + "_ID'";
			statement = connection.prepareStatement( strFindSeqSQL );
			resultSet = statement.executeQuery();
			//首先判断序列是否存在，不存在就创建
			if( !resultSet.next() ){
				strSQL = "CREATE SEQUENCE  SEQ_" + strTable.toUpperCase() + "_ID  START WITH 2 INCREMENT BY 1 ";
				statement = connection.prepareStatement( strSQL );
				statement.execute();
				String strSeqTemp = StringUtil.lpad( "1", iLength, "0" );
				/*while( isExistID( strSeqTemp, strTable ) ){
                    strSQL = "select LPAD(SEQ_" + strTable.toUpperCase() + "_ID.nextval," + String.valueOf( iLength )
                         + ",'0')  from dual";
                    statement = connection.prepareStatement( strSQL );
                    resultSet = statement.executeQuery();
                    resultSet.next();
                    strSeqTemp = resultSet.getString( 1 ).trim();
                    System.out.println("strSeqTemp "+strSeqTemp);
                                 }*/
				strSeq = strSeqTemp;

				//序列存在就读取下一个值 00001212
			}
			else{
				strSQL = "select LPAD(SEQ_" + strTable.toUpperCase() + "_ID.nextval," + String.valueOf( iLength )
				+ ",'0')  from dual";
				statement = connection.prepareStatement( strSQL );
				resultSet = statement.executeQuery();
				resultSet.next();
				String strSeqTemp = resultSet.getString( 1 ).trim();
				/*while( isExistID( strSeqTemp, strTable ) ){
                    statement = connection.prepareStatement( strSQL );
                    resultSet = statement.executeQuery();
                    resultSet.next();
                      strSeqTemp = resultSet.getString( 1 ).trim();
                    System.out.println("strSeqTemp "+strSeqTemp);
                                 }*/
				strSeq = strSeqTemp;
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		finally{
			//try {
			//connection.close();
			//}
			//catch (SQLException ex) {
			//    ex.printStackTrace();
			//}
		}
		return strSeq;
	}

	public java.lang.String[] getSeqs( java.lang.String strTable, int iLength, int iCount ){
		// Connection connection = null;
		// PreparedStatement statement = null;
		ResultSet resultSet = null;
		String strFindSeqSQL = "";

		String strSQL = "";
		String[] strSeq = new String[iCount];

		try{
			QueryUtil qu = new QueryUtil();
			//connection = HibernateSession.currentSession().connection();

			strFindSeqSQL = "select SEQUENCE_NAME  from USER_SEQUENCES  WHERE SEQUENCE_NAME='SEQ_"
				+ strTable.toUpperCase() + "_ID'";
			// statement = connection.prepareStatement( strFindSeqSQL );
			resultSet = qu.executeQuery(strFindSeqSQL);
			int k = 0;
			//首先判断序列是否存在，不存在就创建
			if( !resultSet.next()){            	
				strSQL = "CREATE SEQUENCE  SEQ_" + strTable.toUpperCase() + "_ID  START WITH 2 INCREMENT BY 1 ";
				//statement = connection.prepareStatement( strSQL );
				UpdateUtil  up = new UpdateUtil();
				up.executeUpdate(strSQL);
				String strSeqTemp = StringUtil.lpad( "1", iLength, "0" );
				strSeq[k] = strSeqTemp;
				k++;
			}
			for (int i = k; i < iCount; i++) {
				// 序列存在就读取下一个值            
				strSQL = "select LPAD(SEQ_" + strTable.toUpperCase() + "_ID.nextval," + String.valueOf( iLength )
				+ ",'0')  from dual";
				//statement = connection.prepareStatement( strSQL );
				resultSet = qu.executeQuery(strSQL);
				resultSet.next();
				String strSeqTemp = resultSet.getString( 1 ).trim();     
				strSeq[i] = strSeqTemp;
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		finally{
			//try {
			//connection.close();
			//}
			//catch (SQLException ex) {
			//    ex.printStackTrace();
			//}
		}
		return strSeq;
	}

    public java.lang.String[] getSeqs( java.lang.String strTable,String strTableSimple, int iLength, int iCount ){
        // Connection connection = null;
        // PreparedStatement statement = null;
        ResultSet resultSet = null;
        String strFindSeqSQL = "";

        String strSQL = "";
        String[] strSeq = new String[iCount];

        try{
            QueryUtil qu = new QueryUtil();
            //connection = HibernateSession.currentSession().connection();

            strFindSeqSQL = "select SEQUENCE_NAME  from USER_SEQUENCES  WHERE SEQUENCE_NAME='SEQ_"
                + strTableSimple.toUpperCase() + "_ID'";
            // statement = connection.prepareStatement( strFindSeqSQL );
            resultSet = qu.executeQuery(strFindSeqSQL);
            int k = 0;
            //首先判断序列是否存在，不存在就创建
            if( !resultSet.next()){             
                strSQL = "CREATE SEQUENCE  SEQ_" + strTableSimple.toUpperCase() + "_ID  START WITH 2 INCREMENT BY 1 ";
                //statement = connection.prepareStatement( strSQL );
                UpdateUtil  up = new UpdateUtil();
                up.executeUpdate(strSQL);
                String strSeqTemp = StringUtil.lpad( "1", iLength, "0" );
                strSeq[k] = strSeqTemp;
                k++;
            }
            for (int i = k; i < iCount; i++) {
                // 序列存在就读取下一个值            
                strSQL = "select LPAD(SEQ_" + strTableSimple.toUpperCase() + "_ID.nextval," + String.valueOf( iLength )
                + ",'0')  from dual";
                //statement = connection.prepareStatement( strSQL );
                resultSet = qu.executeQuery(strSQL);
                resultSet.next();
                String strSeqTemp = resultSet.getString( 1 ).trim();     
                strSeq[i] = strSeqTemp;
            }
        }
        catch( Exception e ){
            e.printStackTrace();
        }
        finally{
            //try {
            //connection.close();
            //}
            //catch (SQLException ex) {
            //    ex.printStackTrace();
            //}
        }
        return strSeq;
    }

	/**
	 *  按照类型选择一个序列的值
	 * @param strType
	 * @return
	 */
	public java.lang.String getSeq( java.lang.String strTable,String strTableSimple, int iLength ){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String strFindSeqSQL = "";

		String strSQL = "";
		String strSeq = "";
		//设置seq MAXVALUE
//		StringBuffer maxvalue = new StringBuffer();
//		for(int i=0;i<iLength;i++){
//		maxvalue.append(9);
//		}
//		if(maxvalue.length()==0){
//		maxvalue.append("9999999999");
//		}
//		MAXVALUE "+maxvalue.toString()+" cycle
		try{

			connection = HibernateSession.currentSession().connection();

			strFindSeqSQL = "select SEQUENCE_NAME  from USER_SEQUENCES  WHERE SEQUENCE_NAME='SEQ_"
				+ strTableSimple.toUpperCase() + "_ID'";
			statement = connection.prepareStatement( strFindSeqSQL );
			resultSet = statement.executeQuery();
			//首先判断序列是否存在，不存在就创建
			if( !resultSet.next() ){
				strSQL = "CREATE SEQUENCE  SEQ_" + strTableSimple.toUpperCase() + "_ID  START WITH 2 INCREMENT BY 1 ";
				statement = connection.prepareStatement( strSQL );
				statement.execute();
				String strSeqTemp = StringUtil.lpad( "1", iLength, "0" );
				strSeq = strSeqTemp;

				//序列存在就读取下一个值
			}
			else{
				strSQL = "select LPAD(SEQ_" + strTableSimple.toUpperCase() + "_ID.nextval," + String.valueOf( iLength )
				+ ",'0')  from dual";
				statement = connection.prepareStatement( strSQL );
				resultSet = statement.executeQuery();
				resultSet.next();
				String strSeqTemp = resultSet.getString( 1 ).trim();
				strSeq = strSeqTemp;
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		finally{
			//try {
			//connection.close();
			//}
			//catch (SQLException ex) {
			//    ex.printStackTrace();
			//}
		}
		return strSeq;
	}


	public boolean isExistID( String strSeq, String tablename ) throws SQLException, Exception{

		QueryUtil queryutil2 = new QueryUtil();
		String sql2 = "select count(*) c from " + tablename.toUpperCase() + " where id='" + strSeq + "'";
		//System.out.println( "sql2 : " + sql2 );
		Vector resultVec = queryutil2.executeQueryGetVector( sql2 );
		String count = ( ( Vector )resultVec.get( 0 ) ).get( 0 ).toString();
		if( count.equals( "0" ) || count == "0" ){
			return false; //ID不存在
		}
		else{
			return true;
		}
	}

	
	public int getIntSeq(String tableName) throws NumberFormatException{
		return Integer.parseInt(getSeq(tableName, 8));
	}
}
