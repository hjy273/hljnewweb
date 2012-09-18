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
     * ���߲���
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
     * ����������������
     * @param APId String
     * @param BPId String
     * @return int
     * @throws Exception
     */
    public int link2Subline( String APId, String BPId ) throws Exception{
        int flag = 1;

        //��һ������Ϣ
        BaseInfoService baseService = new BaseInfoService();
        Point PA = baseService.loadPoint( APId );

        String sublineid = PA.getSubLineID();
        int AInumber = Integer.parseInt( PA.getInumber() );

        //�ڶ�������Ϣ
        baseService = new BaseInfoService();
        Point PB = baseService.loadPoint( BPId );

        //��������
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

        //��ֵ����
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

        //�����µ�
        baseService.createPoint( data );

        //�޸� �����߶� ������ ++
        if( baseService.updateSublineDym( data.getSubLineID(), "1" ) != 1 ){
            flag = -1;
        }

        return flag;
    }


    /**
     * ���ò��ֵ��ƶ��������߶�
     * @param APId String
     * @param BPId String
     * @return int
     * @throws Exception
     */
    public int moveToAnotherSubline( String APId, String BPId,
        String newSublineId ) throws Exception{
        int flag = 1;

        //��һ������Ϣ
        BaseInfoService baseService = new BaseInfoService();
        Point PA = baseService.loadPoint( APId );

        //�ڶ�������Ϣ
        baseService = new BaseInfoService();
        Point PB = baseService.loadPoint( BPId );

        if( !PA.getSubLineID().equals( PB.getSubLineID() ) ){
            //����һ���߶�֮��������
            return -1;
        }

        //�ƶ�
        String iNumA = PA.getInumber();
        String iNumB = PB.getInumber();

        String sql = "update POINTINFO set SUBLINEID = '" + newSublineId +
                     "' where sublineid='" + PA.getSubLineID() +
                     "' and ((INUMBER between " + iNumA + " and " + iNumB +
                     ") or (INUMBER between " + iNumB + " and " + iNumA + "))";

        logger.info( "���£�" + sql );

        UpdateUtil updateUtil = new UpdateUtil();
        updateUtil.executeUpdate( sql );

        return flag;
    }

}
