package com.cabletech.lineinfo.services;

import com.cabletech.commons.base.*;
import com.cabletech.lineinfo.dao.*;
import com.cabletech.lineinfo.domainobjects.*;

public class MsginfoBO extends BaseBisinessObject{

    public MsginfoBO(){
    }


    MsginfoDAOImpl dao = new MsginfoDAOImpl();

    public void addMsginfo( Msginfo data ) throws Exception{
        dao.addMsginfo( data );
    }


    public Msginfo loadMsginfo( String id ) throws Exception{
        return dao.findById( id );
    }


    public Msginfo updateMsginfo( Msginfo msginfo ) throws Exception{
        return dao.updateMsginfo( msginfo );
    }

}
