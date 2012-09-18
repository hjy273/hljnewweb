package com.cabletech.commons.services;

import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.struts.util.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.generatorID.*;
import com.cabletech.commons.hb.*;

/**
 * �����ݿ�Ĵ���ķ�װ
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Cable tech</p>
 * @author not attributable
 * @version 1.0
 */
public class DBService extends BaseService{

    /**
     * ִ�����ݿ�Ĳ�ѯ������bean����
     * @param sql String
     * @throws Exception
     * @return List
     */
    public List queryBeans( String sql ) throws Exception{
        QueryUtil util = new QueryUtil();
        return util.queryBeans( sql );
    }


    /**
     * ִ�����ݿ�Ĳ�ѯ������LableValue����
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
     * ִ�����ݿ�Ĳ�ѯ�������������ͣ�������ݿ��ֶ�Ϊnull,��ʹ��strTemp
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
     * ִ�����ݿ�Ĳ�ѯ�������������ͣ�������ݿ��ֶ�Ϊnull,��ʹ��strTemp
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
     * ִ�����ݿ��update �� inserte ,delete
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
     * ȡ��ȱʡ�����У�����Ϊdefault,����Ϊ8
     * @return String
     */
    public java.lang.String getSeq(){
        return GeneratorFactory.createGenerator().getSeq();
    }


    /**
     * һ����ȡ�������
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
