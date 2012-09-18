package com.cabletech.baseinfo.services;

import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.struts.util.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;

public class RoleManage{
    public RoleManage(){
    }


    /**
     * 根据区域id分析权限级别
     * @param regionid String
     * @return int
     */
    public int getRoleByRegion( String regionid ){
        int role = 0;
        int L = 0;

        if( regionid.length() != 6 ){
            return -1;
            //非法区域id
        }
        else{
            L = regionid.length();
        }

        if( regionid.substring( L - 4, L ).equals( "0000" ) ){
            role = 1;
        }
        else{
            if( regionid.substring( L - 2, L ).equals( "00" ) ){
                role = 2;
            }
            else{
                role = 3;
            }
        }

        //System.out.println( "当前权限 ：" + role );

        return role;
    }


    /**
     * 取得当前区域级别
     * @param currRegion String
     * @return Vector
     * @throws Exception
     */
    public Vector getRegionPras( String currRegion ) throws
        Exception{

        Vector vct = new Vector();

        int role = getRoleByRegion( currRegion );

        if( role == -1 ){
            //System.out.println( "该区域id不合法，无法解析权限" );
            return vct;
        }
        //1 权限
        vct.add( String.valueOf( role ) );
        //顶级区域
        String supReg = getSuperRegionid( role, currRegion );

        BaseInfoService service = new BaseInfoService();
        Region region = new Region();

        region = service.loadRegion( supReg );
        //2
        vct.add( region.getRegionID() );
        //3
        vct.add( region.getRegionName() );

        Vector subVct = new Vector();

        if( role == 1 ){
            subVct = getSubRegion_1( currRegion );
        }
        if( role == 2 ){
            subVct = getSubRegion_2( currRegion );
        }
        if( role == 3 ){
            subVct = getSubRegion_3( currRegion );
        }
        //4
        vct.add( subVct );

        return vct;

    }


    /**
     * 顶级区域
     * @param currRegion String
     * @return Vector
     * @throws Exception
     */
    public Vector getSubRegion_1( String currRegion ) throws
        Exception{
        Vector vct = new Vector();

        String sql =
            "select regionid, regionname from region where parentregionid = '" +
            currRegion + "'";

        //System.out.println( "sql 1 :" + sql );

        QueryUtil jutil = new QueryUtil();

        Vector subvct = jutil.executeQueryGetVector( sql );

        for( int i = 0; i < subvct.size(); i++ ){

            String[] subStr = new String[2];
            subStr[0] = ( String ) ( ( Vector )subvct.get( i ) ).get( 0 );
            subStr[1] = ( String ) ( ( Vector )subvct.get( i ) ).get( 1 );

            Vector oneUnitV = new Vector();
            //oneUnitV第一项
            oneUnitV.add( subStr );

            sql =
                "select regionid, regionname from region where parentregionid = '" +
                subStr[0] + "'";

            //System.out.println( "sql 2 :" + sql );

            jutil = new QueryUtil();
            Vector litvct = jutil.executeQueryGetVector( sql );

            for( int k = 0; k < litvct.size(); k++ ){
                String[] litStr = new String[2];
                litStr[0] = ( String ) ( ( Vector )litvct.get( k ) ).get( 0 );
                litStr[1] = ( String ) ( ( Vector )litvct.get( k ) ).get( 1 );

                oneUnitV.add( litStr );
            }

            vct.add( oneUnitV );
        }

        return vct;
    }


    /**
     * 二级区域
     * @param currRegion String
     * @return Vector
     * @throws Exception
     */
    public Vector getSubRegion_2( String currRegion ) throws
        Exception{
        Vector vct = new Vector();

        BaseInfoService service = new BaseInfoService();
        Region region = new Region();
        region = service.loadRegion( currRegion );

        String[] curStr = new String[2];
        curStr[0] = region.getRegionID();
        curStr[1] = region.getRegionName();

        //第一项
        vct.add( curStr );

        String sql =
            "select regionid, regionname from region where parentregionid = '" +
            currRegion + "'";

        QueryUtil jutil = new QueryUtil();
        Vector subvct = jutil.executeQueryGetVector( sql );

        for( int i = 0; i < subvct.size(); i++ ){

            String[] subStr = new String[2];
            subStr[0] = ( String ) ( ( Vector )subvct.get( i ) ).get( 0 );
            subStr[1] = ( String ) ( ( Vector )subvct.get( i ) ).get( 1 );

            //oneUnitV第一项
            vct.add( subStr );
        }

        return vct;
    }


    public Vector getSubRegion_3( String currRegion ) throws
        Exception{
        Vector vct = new Vector();

        BaseInfoService service = new BaseInfoService();
        Region region = new Region();
        region = service.loadRegion( currRegion );

        String[] curStr = new String[3];
        curStr[0] = region.getRegionID();
        curStr[1] = region.getRegionName();

        String paRegid = region.getParentregionid();
        Region paRegion = new Region();
        paRegion = service.loadRegion( paRegid );

        curStr[2] = paRegion.getRegionName();

        //第一项
        vct.add( curStr );
        return vct;
    }


    public String getSuperRegionid( int role, String cRegionid ) throws
        Exception{

        if( role == 3 ){

            String sql = "select a.parentregionid ,b.regionname from region a, (select * from region )b where a.parentregionid = b.regionid and a.regionid = '" +
                         cRegionid + "'";

            //System.out.println("取得顶级 ：" + sql);

            QueryUtil jutil = new QueryUtil();
            Vector vct = jutil.executeQueryGetVector( sql );

            cRegionid = ( String ) ( ( Vector )vct.get( 0 ) ).get( 0 );

            sql = "select a.parentregionid ,b.regionname from region a, (select * from region )b where a.parentregionid = b.regionid and a.regionid = '" +
                  cRegionid + "'";

            //System.out.println("取得顶级 ：" + sql);

            jutil = new QueryUtil();
            vct = jutil.executeQueryGetVector( sql );

            cRegionid = ( String ) ( ( Vector )vct.get( 0 ) ).get( 0 );

            return cRegionid;

        }
        else{
            if( role == 2 ){

                String sql = "select a.parentregionid ,b.regionname from region a, (select * from region )b where a.parentregionid = b.regionid and a.regionid = '" +
                             cRegionid + "'";

                //System.out.println( "取得顶级 ：" + sql );

                QueryUtil jutil = new QueryUtil();
                Vector vct = jutil.executeQueryGetVector( sql );

                cRegionid = ( String ) ( ( Vector )vct.get( 0 ) ).get( 0 );

                return cRegionid;
            }
            else{
                return cRegionid;
            }
        }

    }


    /**
     * 根据区域id取得该区域下所有区域下拉列表
     * @param regionid String
     * @throws Exception
     * @return ArrayList
     */
    public static ArrayList getRegionCollection( String regionid ) throws
        Exception{
        String sqlString = makeGetRegionSql( regionid );

        //System.out.println( sqlString );

        Iterator it;
        BasicDynaBean bdb;
        ArrayList lableValueList = new ArrayList();

        QueryUtil jutil = new QueryUtil();
        it = jutil.queryBeans( sqlString ).iterator();
        while( it.hasNext() ){
            bdb = ( BasicDynaBean )it.next();
            lableValueList.add(
                new LabelValueBean( ( String ) ( bdb.get( "objectname" ) ),
                ( String ) ( bdb.get( "objectid" ) ) ) );
        }
        return lableValueList;
    }


    /**
     * 查询区域列表的SQL
     * @param regionid String
     * @return String
     */
    public static String makeGetRegionSql( String regionid ){

        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(
                                 "select regionname objectname, regionid objectid from region" );
        sqlBuild.addRegion( regionid );

        String sql = sqlBuild.toSql();
        return sql;

    }


    /**
     * 根据区域id取得该区域下所有 部门 下拉列表
     * @param regionid String
     * @throws Exception
     * @return ArrayList
     */
    public static final ArrayList getDeptCollection( String regionid,
        int depOrCont ) throws
        Exception{

        //depOrCont 1-内部部门 2-代维单位

        String sqlString = "";
        if( depOrCont == 2 ){
            sqlString = makeGetContractorSql( regionid );
        }
        else{
            sqlString = makeGetDepSql( regionid );
        }

        //System.out.println( sqlString );

        Iterator it;
        BasicDynaBean bdb;
        ArrayList lableValueList = new ArrayList();

        QueryUtil jutil = new QueryUtil();
        it = jutil.queryBeans( sqlString ).iterator();
        while( it.hasNext() ){

            bdb = ( BasicDynaBean )it.next();
            lableValueList.add(
                new LabelValueBean( ( String ) ( bdb.get( "objectname" ) ),
                ( String ) ( bdb.get( "objectid" ) ) ) );
        }
        return lableValueList;
    }


    /**
     * 根据区域取得可用部门
     * @param regionid String
     * @return String
     */
    public static String makeGetDepSql( String regionid ){
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(
                                 "select deptname objectname, deptid objectid from deptinfo " );
        sqlBuild.addRegion( regionid );

        String sql = sqlBuild.toSql();
        sql +=" and state is null ";
        //System.out.println( "SQL:" + sql );
        return sql;

    }


    /**
     * 根据区域取得可用 代维单位
     * @param regionid String
     * @return String
     */
    public static String makeGetContractorSql( String regionid ){
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(
                                 "select contractorname objectname, contractorid objectid from contractorinfo " );
        sqlBuild.addRegion( regionid );

        String sql = sqlBuild.toSql();
        sql +=" and state is null ";
        return sql;

    }


    /**
     * 根据区域id取得该区域下所有 线 下拉列表
     * @param regionid String
     * @throws Exception
     * @return ArrayList
     */
    public static ArrayList getLineCollection( String regionid ) throws
        Exception{
        String sqlString = "";
        sqlString = makeGetLineSql( regionid );

        //System.out.println( sqlString );

        Iterator it;
        BasicDynaBean bdb;
        ArrayList lableValueList = new ArrayList();

        QueryUtil jutil = new QueryUtil();
        it = jutil.queryBeans( sqlString ).iterator();
        while( it.hasNext() ){
            bdb = ( BasicDynaBean )it.next();
            lableValueList.add(
                new LabelValueBean( ( String ) ( bdb.get( "objectname" ) ),
                ( String ) ( bdb.get( "objectid" ) ) ) );
        }
        return lableValueList;
    }


    /**
     * 取得 线 列表 SQL
     * @param regionid String
     * @return String
     */
    public static String makeGetLineSql( String regionid ){
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(
                                 "select linename objectname, lineid objectid from lineinfo" );
        sqlBuild.addRegion( regionid );

        String sql = sqlBuild.toSql();
        return sql;
    }


    /**
     * 取得 线段 列表
     * @param regionid String
     * @throws Exception
     * @return ArrayList
     */
    public static ArrayList getSubLineCollection( String regionid ) throws
        Exception{
        String sqlString = "";
        sqlString = makeGetSubLineSql( regionid );

        //System.out.println( sqlString );

        Iterator it;
        BasicDynaBean bdb;
        ArrayList lableValueList = new ArrayList();

        QueryUtil jutil = new QueryUtil();
        it = jutil.queryBeans( sqlString ).iterator();
        while( it.hasNext() ){
            bdb = ( BasicDynaBean )it.next();
            lableValueList.add(
                new LabelValueBean( ( String ) ( bdb.get( "objectname" ) ),
                ( String ) ( bdb.get( "objectid" ) ) ) );
        }
        return lableValueList;
    }


    /**
     * 取得 线段 列表 SQL
     * @param regionid String
     * @return String
     */
    public static String makeGetSubLineSql( String regionid ){
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(
                                 "select sublinename objectname, sublineid objectid from sublineinfo" );
        sqlBuild.addRegion( regionid );

        String sql = sqlBuild.toSql();
        return sql;
    }


    /**
     * 取得 巡检员 列表
     * @param regionid String
     * @throws Exception
     * @return ArrayList
     */
    public static ArrayList getPatrolmanCollection( String regionid ) throws
        Exception{
        String sqlString = "";
        sqlString = makeGetPatrolmanSql( regionid );

        //System.out.println( sqlString );

        Iterator it;
        BasicDynaBean bdb;
        ArrayList lableValueList = new ArrayList();

        QueryUtil jutil = new QueryUtil();
        it = jutil.queryBeans( sqlString ).iterator();
        while( it.hasNext() ){
            bdb = ( BasicDynaBean )it.next();
            lableValueList.add(
                new LabelValueBean( ( String ) ( bdb.get( "objectname" ) ),
                ( String ) ( bdb.get( "objectid" ) ) ) );
        }
        return lableValueList;
    }


    /**
     * 依据部门获得巡检人员列表
     * @param contractorid String
     * @return ArrayList
     * @throws Exception
     */
    public static ArrayList getDeptPatrolmanCollection( String contractorid ) throws
        Exception{
        String sqlString = "";
        sqlString = "select username objectname,userid objectid from userinfo  where deptid = '" +
                    contractorid + "'";

        //System.out.println( sqlString );

        Iterator it;
        BasicDynaBean bdb;
        ArrayList lableValueList = new ArrayList();

        QueryUtil jutil = new QueryUtil();
        it = jutil.queryBeans( sqlString ).iterator();
        while( it.hasNext() ){
            bdb = ( BasicDynaBean )it.next();
            lableValueList.add(
                new LabelValueBean( ( String ) ( bdb.get( "objectname" ) ),
                ( String ) ( bdb.get( "objectid" ) ) ) );
        }
        return lableValueList;
    }

    public static ArrayList getDeptPatrolmanPhone( String contractorid ) throws
        Exception{
        String sqlString = "";
        sqlString = "select phone objectname,userid objectid from userinfo  where deptid = '" +
                    contractorid + "'";

        //System.out.println( sqlString );

        Iterator it;
        BasicDynaBean bdb;
        ArrayList lableValueList = new ArrayList();

        QueryUtil jutil = new QueryUtil();
        it = jutil.queryBeans( sqlString ).iterator();
        while( it.hasNext() ){
            bdb = ( BasicDynaBean )it.next();
            lableValueList.add(
                new LabelValueBean( ( String ) ( bdb.get( "objectname" ) ),
                ( String ) ( bdb.get( "objectid" ) ) ) );
        }
        return lableValueList;
    }

    /**
     * 巡检员 SQL
     * @param regionid String
     * @return String
     */
    public static String makeGetPatrolmanSql( String regionid ){
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(
                                 "select patrolname objectname, patrolid objectid from patrolmaninfo" );
        sqlBuild.addRegion( regionid );

        String sql = sqlBuild.toSql();
        return sql;
    }


    /**
     * 取得区域下点
     * @param regionid String
     * @throws Exception
     * @return ArrayList
     */
    public static ArrayList getPoints( String regionid ) throws
        Exception{
        String sqlString = "";
        sqlString = "select pointname objectname, pointid objectid from pointinfo where regionid = '" +
                    regionid + "'";

        //sqlString = makeGetPatrolmanSql(regionid);

        //System.out.println( sqlString );

        Iterator it;
        BasicDynaBean bdb;
        ArrayList lableValueList = new ArrayList();

        QueryUtil jutil = new QueryUtil();
        it = jutil.queryBeans( sqlString ).iterator();
        while( it.hasNext() ){
            bdb = ( BasicDynaBean )it.next();
            lableValueList.add(
                new LabelValueBean( ( String ) ( bdb.get( "objectname" ) ),
                ( String ) ( bdb.get( "objectid" ) ) ) );
        }
        return lableValueList;
    }


    /**
     * 取得区域下持有巡检设备的巡检员
     * @param regionid String
     * @throws Exception
     * @return ArrayList
     */
    public static ArrayList getPatrlAvailableList( String regionid ) throws
        Exception{
        String sqlString = "";
        sqlString = "select a.PATROLNAME objectname, b.SIMNUMBER objectid from PATROLMANINFO a, TERMINALINFO b where a.PATROLID = b.OWNERID  and a.regionid = '" +
                    regionid + "'";

        //sqlString = makeGetPatrolmanSql(regionid);

        //System.out.println( sqlString );

        Iterator it;
        BasicDynaBean bdb;
        ArrayList lableValueList = new ArrayList();

        QueryUtil jutil = new QueryUtil();
        it = jutil.queryBeans( sqlString ).iterator();
        while( it.hasNext() ){
            bdb = ( BasicDynaBean )it.next();
            lableValueList.add(
                new LabelValueBean( ( String ) ( bdb.get( "objectname" ) ),
                ( String ) ( bdb.get( "objectid" ) ) ) );
        }
        return lableValueList;
    }

}
