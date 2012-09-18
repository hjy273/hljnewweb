package com.cabletech.lineinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.baseinfo.services.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.services.*;
import com.cabletech.lineinfo.domainobjects.*;
import org.apache.log4j.Logger;

public class GISPointDAOImpl extends HibernateDaoSupport{
    private Logger logger = Logger.getLogger(GISPointDAOImpl.class);
    public GISPointDAOImpl(){
    }


    /**
     * 虚线操作
     * @param templine TempLine
     * @return TempLine
     * @throws Exception
     */
    public TempLine addTempLine( TempLine templine ) throws Exception{
        this.getHibernateTemplate().save( templine );
        return templine;
    }


    public TempLine findById( String id ) throws Exception{
        return( TempLine )this.getHibernateTemplate().load( TempLine.class, id );
    }


    public void removeTempLine( TempLine templine ) throws Exception{
        this.getHibernateTemplate().delete( templine );
    }


    public TempLine updateTempLine( TempLine templine ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( templine );
        return templine;
    }


    /**
     * 将两个点连接起来
     * @param APId String
     * @param BPId String
     * @return int
     * @throws Exception
     */
    public int link2Subline( String APId, String BPId ) throws Exception{
        int flag = 1;

        //第一个点信息
        BaseInfoService baseService = new BaseInfoService();
        Point PA = baseService.loadPoint( APId );

        String sublineid = PA.getSubLineID();
        int AInumber = Integer.parseInt( PA.getInumber() );

        //第二个点信息
        baseService = new BaseInfoService();
        Point PB = baseService.loadPoint( BPId );

        //计算线序
        String sql = "select pointid from pointinfo where SUBLINEID = '" +
                     sublineid +
                     "' order by INUMBER ";
        QueryUtil queryutil = new QueryUtil();
        String[][] resultArr = queryutil.executeQueryGetArray( sql, "" );

        String firstPId = resultArr[0][0];

        int iNumber = 0;

        if( firstPId.equals( APId ) ){
            iNumber = AInumber - 5;
        }
        else{
            iNumber = AInumber + 5;
        }

        DBService dbService = new DBService();

        //数值整理
        Point data = new Point();

        data.setPointID( dbService.getSeq( "pointinfo", 8 ) );
        data.setInumber( String.valueOf( iNumber ) );
        data.setSubLineID( PA.getSubLineID() );
        data.setPatrolid( PA.getPatrolid() );
        data.setPointName( PB.getPointName() );
        data.setRegionID( PB.getRegionID() );
        data.setAddressInfo( PB.getAddressInfo() );
        data.setGpsCoordinate( PB.getGpsCoordinate() );
        data.setIsFocus( PB.getIsFocus() );

        data.setPointtype( PB.getPointtype() );
        data.setState( PB.getState() );

        //插入新点
        baseService.createPoint( data );

        //修改 所属线段 点数量 ++
        if( baseService.updateSublineDym( data.getSubLineID(), "1" ) != 1 ){
            flag = -1;
        }

        return flag;
    }


    /**
     * 将该部分点移动至其他线段
     * @param APId String
     * @param BPId String
     * @return int
     * @throws Exception
     */
    public int moveToAnotherSubline( String APId, String BPId,
        String newSublineId ) throws Exception{
        int flag = 1;

        //第一个点信息
        BaseInfoService baseService = new BaseInfoService();
        Point PA = baseService.loadPoint( APId );

        //第二个点信息
        baseService = new BaseInfoService();
        Point PB = baseService.loadPoint( BPId );

        if( !PA.getSubLineID().equals( PB.getSubLineID() ) ){
            //不是一条线段之内两个点
            return -1;
        }

        //移动
        String iNumA = PA.getInumber();
        String iNumB = PB.getInumber();

        String sql = "update POINTINFO set SUBLINEID = '" + newSublineId +
                     "' where sublineid='" + PA.getSubLineID() +
                     "' and ((INUMBER between " + iNumA + " and " + iNumB +
                     ") or (INUMBER between " + iNumB + " and " + iNumA + "))";

        logger.info( "更新：" + sql );

        UpdateUtil updateUtil = new UpdateUtil();
        updateUtil.executeUpdate( sql );

        return flag;
    }

}
