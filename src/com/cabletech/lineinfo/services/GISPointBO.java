package com.cabletech.lineinfo.services;

import com.cabletech.commons.base.*;
import com.cabletech.lineinfo.dao.*;
import com.cabletech.lineinfo.domainobjects.*;

public class GISPointBO extends BaseBisinessObject{
    public GISPointBO(){
    }


    GISPointDAOImpl dao = new GISPointDAOImpl();

    public void addTempLine( TempLine data ) throws Exception{
        dao.addTempLine( data );
    }


    public void removeTempLine( TempLine data ) throws Exception{
        dao.removeTempLine( data );
    }


    public TempLine loadTempLine( String id ) throws Exception{
        return dao.findById( id );
    }


    public TempLine updateTempLine( TempLine data ) throws Exception{
        return dao.updateTempLine( data );
    }


    public int link2Subline( String APId, String BPId ) throws Exception{
        return dao.link2Subline( APId, BPId );
    }


    public int moveToAnotherSubline( String APId, String BPId,
        String newSublineId ) throws Exception{
        return dao.moveToAnotherSubline( APId, BPId, newSublineId );
    }
}
