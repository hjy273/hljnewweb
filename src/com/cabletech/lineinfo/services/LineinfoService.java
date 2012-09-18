package com.cabletech.lineinfo.services;

import com.cabletech.commons.base.*;
import com.cabletech.lineinfo.domainobjects.*;

public class LineinfoService extends BaseService{
    MsginfoBO bom;
    SmsSendLogBO smsSendBo;
    GISCrossPointBO crosspointBo;
    GISPointBO pointBo;

    public LineinfoService(){
        bom = new MsginfoBO();
        smsSendBo = new SmsSendLogBO();
        crosspointBo = new GISCrossPointBO();
        pointBo = new GISPointBO();
    }


    public void createMsginfo( Msginfo data ) throws Exception{
        bom.addMsginfo( data );
    }


    public Msginfo loadMsginfo( String id ) throws Exception{
        return bom.loadMsginfo( id );
    }


    public Msginfo updateMsginfo( Msginfo msginfo ) throws Exception{
        return bom.updateMsginfo( msginfo );
    }


    /**
     * 添加短信发送日至
     * @param data SmsSendLog
     * @throws Exception
     */
    public void createSmsSendLog( SmsSendLog data ) throws Exception{
        smsSendBo.addSmsSendLog( data );
    }


    public void createGISCrossPoint( GISCrossPoint data ) throws Exception{
        crosspointBo.addGISCrossPoint( data );
    }


    public GISCrossPoint loadGISCrossPoint( String id ) throws Exception{
        return crosspointBo.loadGISCrossPoint( id );
    }


    public GISCrossPoint updateGISCrossPoint( GISCrossPoint crosspoint ) throws
        Exception{
        return crosspointBo.updateGISCrossPoint( crosspoint );
    }


    public void removeGISCrossPoint( GISCrossPoint crosspoint ) throws Exception{
        crosspointBo.removeGISCrossPoint( crosspoint );
    }


    /**
     * 连接两个相邻线段
     */
    public void addTempLine( TempLine data ) throws Exception{
        pointBo.addTempLine( data );
    }


    public void removeTempLine( TempLine data ) throws Exception{
        pointBo.removeTempLine( data );
    }


    public TempLine loadTempLine( String id ) throws Exception{
        return pointBo.loadTempLine( id );
    }


    public TempLine updateTempLine( TempLine line ) throws Exception{
        return pointBo.updateTempLine( line );
    }


    public int link2Subline( String APId, String BPId ) throws Exception{
        return pointBo.link2Subline( APId, BPId );
    }


    public int moveToAnotherSubline( String APId, String BPId,
        String newSublineId ) throws Exception{
        return pointBo.moveToAnotherSubline( APId, BPId, newSublineId );
    }

}
