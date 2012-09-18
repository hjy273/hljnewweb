package com.cabletech.commons.services;

import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.struts.util.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.generatorID.*;
import com.cabletech.commons.hb.*;

/**
 * 对数据库的处理的风装
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Cable tech</p>
 * @author not attributable
 * @version 1.0
 */
public class DBService extends BaseService{

    /**
     * 执行数据库的查询，返回bean类型
     * @param sql String
     * @throws Exception
     * @return List
     */
    public List queryBeans( String sql ) throws Exception{
        QueryUtil util = new QueryUtil();
        return util.queryBeans( sql );
    }


    /**
     * 执行数据库的查询，返回LableValue类型
     * @param sql String
     * @throws Exception
     * @return List
     */
    public ArrayList getLableValueCollection(
        String tableName, String lblColName, String valColName ) throws
        Exception{

        String sqlString = "select " + lblColName + "," + valColName + " from " +
                           tableName;
        ArrayList lableValueList = new ArrayList();

        Iterator it = queryBeans( sqlString ).iterator();
        while( it.hasNext() ){
            BasicDynaBean bdb = ( BasicDynaBean )it.next();
            lableValueList.add( new LabelValueBean( ( String ) ( bdb.get( lblColName ) ),
                ( String ) ( bdb.get( valColName ) ) ) );
        }
        return lableValueList;
    }


    /**
     * 执行数据库的查询，返回数组类型，如果数据库字段为null,则使用strTemp
     * @param sql String
     * @param strTemp String
     * @throws Exception
     * @return String[][]
     */
    public String[][] queryArray( String sql, String strTemp ) throws Exception{
        QueryUtil util = new QueryUtil();
        return util.executeQueryGetArray( sql, strTemp );
    }


    /**
     * 执行数据库的查询，返回数组类型，如果数据库字段为null,则使用strTemp
     * @param sql String
     * @param strTemp String
     * @throws Exception
     * @return String[][]
     */
    public Vector queryVector( String sql, String strTemp ) throws Exception{
        QueryUtil util = new QueryUtil();
        return util.executeQueryGetStringVector( sql, strTemp );
    }


    /**
     * 执行数据库的update 和 inserte ,delete
     * @param sql String
     * @param strTemp String
     * @throws Exception
     * @return String[][]
     */
    public void dbUpdate( String sql ) throws Exception{
        UpdateUtil util = new UpdateUtil();
        util.executeUpdate( sql );
    }


    public java.lang.String getSeq( java.lang.String keyName, int iLength ){
        return GeneratorFactory.createGenerator().getSeq( keyName, iLength );
    }


    /**
     * 取得缺省的序列，名称为default,长度为8
     * @return String
     */
    public java.lang.String getSeq(){
        return GeneratorFactory.createGenerator().getSeq();
    }


    /**
     * 一次性取多个序列
     * @param iTotal int
     * @param strTable String
     * @param iLength int
     * @return String[]
     */
    public java.lang.String[] getSeqs( int iTotal, java.lang.String strTable,
        int iLength ){
        return GeneratorFactory.createGenerator().getSeqs( iTotal, strTable,
            iLength );
    }

}
