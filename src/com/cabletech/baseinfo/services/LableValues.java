package com.cabletech.baseinfo.services;

import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.struts.util.*;
import com.cabletech.commons.hb.*;

public final class LableValues{
    /**
     * ���ڲ�ѯһ��������м�¼��ֻ����Name�ֶκ�ID�ֶΣ�������һ��ArrayList�з���
     * @param tableName String���� ����
     * @param lblColName String����Name�ֶ���
     * @param valColName String����ID�ֶ���
     * @throws Exception
     * @return ArrayList          ����ֵ
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
