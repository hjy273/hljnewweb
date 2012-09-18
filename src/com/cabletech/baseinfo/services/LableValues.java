package com.cabletech.baseinfo.services;

import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.struts.util.*;
import com.cabletech.commons.hb.*;

public final class LableValues{
    /**
     * 用于查询一个表的所有记录，只包括Name字段和ID字段，并放于一个ArrayList中返回
     * @param tableName String　　 表名
     * @param lblColName String　　Name字段名
     * @param valColName String　　ID字段名
     * @throws Exception
     * @return ArrayList          返回值
     */
    public static final ArrayList getLableValueCollection(
        String tableName, String lblColName, String valColName ) throws
        Exception{
        String sqlString = new String();
        sqlString = "select " + lblColName + "," + valColName + " from " +
                    tableName;
        Iterator it;
        BasicDynaBean bdb;
        ArrayList lableValueList = new ArrayList();

        QueryUtil jutil = new QueryUtil();
        it = jutil.queryBeans( sqlString ).iterator();
        while( it.hasNext() ){
            bdb = ( BasicDynaBean )it.next();
            lableValueList.add(
                new LabelValueBean( ( String ) ( bdb.get( lblColName ) ),
                ( String ) ( bdb.get( valColName ) ) ) );
        }
        return lableValueList;
    }
}
