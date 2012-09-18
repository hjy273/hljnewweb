package com.cabletech.lineinfo.common;

import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.struts.util.*;
import com.cabletech.commons.hb.*;

public class TableList extends ArrayList{
    /**
     * 用于查询一个表的所有记录，只包括Name字段和ID字段，并放于一个ArrayList中返回
     * @param tableName String　　 表名
     * @param lblColName String　　Name字段名
     * @param valColName String　　ID字段名
     * @throws Exception
     * @return ArrayList          返回值
     */

    public List getList( String sqlString ) throws Exception{
        QueryUtil jutil = new QueryUtil();
        List queryList = jutil.queryBeans( sqlString );
        return queryList;
    }


    public static final ArrayList getLableValueCollection(
        String tableName, String lblColName, String valColName ) throws Exception{
        String sqlString = new String();
        sqlString = "select " + lblColName + "," + valColName + " from " + tableName;
 //       System.out.println( sqlString );
        Iterator it;
        BasicDynaBean bdb;
        ArrayList lableValueList = new ArrayList();
   //     System.out.println( "1" );
        QueryUtil jutil = new QueryUtil();
  //      System.out.println( 2 );
        it = jutil.queryBeans( sqlString ).iterator();
        while( it.hasNext() ){
            bdb = ( BasicDynaBean )it.next();
            lableValueList.add(
                new LabelValueBean( ( String ) ( bdb.get( lblColName ) ), ( String ) ( bdb.get( valColName ) ) ) );
        }
        return lableValueList;
    }


    public static final ArrayList getList( List tableList, String lblColName, String valColName ) throws Exception{
        Iterator it;
        BasicDynaBean bdb;
        ArrayList lableValueList = new ArrayList();

        it = ( ( List )tableList ).iterator();
        while( it.hasNext() ){
            bdb = ( BasicDynaBean )it.next();
            lableValueList.add(
                new LabelValueBean( ( String ) ( bdb.get( lblColName ) ), ( String ) ( bdb.get( valColName ) ) ) );
        }
        return lableValueList;
    }

}
