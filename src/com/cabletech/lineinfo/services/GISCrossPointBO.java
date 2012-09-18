package com.cabletech.lineinfo.services;

import com.cabletech.commons.base.*;
import com.cabletech.lineinfo.dao.*;
import com.cabletech.lineinfo.domainobjects.*;

public class GISCrossPointBO extends BaseBisinessObject{
    public GISCrossPointBO(){
    }


    GISCrossPointDAOImpl dao = new GISCrossPointDAOImpl();

    public void addGISCrossPoint( GISCrossPoint data ) throws Exception{
        dao.addGISCrossPoint( data );
    }


    public GISCrossPoint loadGISCrossPoint( String id ) throws Exception{
        return dao.findById( id );
    }


    public GISCrossPoint updateGISCrossPoint( GISCrossPoint crosspoint ) throws
        Exception{
        return dao.updatecrosspoint( crosspoint );
    }


    public void removeGISCrossPoint( GISCrossPoint crosspoint ) throws Exception{
        dao.removecrosspoint( crosspoint );
    }

}
