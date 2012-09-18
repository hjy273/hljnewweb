package com.cabletech.baseinfo.services;

import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;

public class PointBO extends BaseBisinessObject{
    PointDAOImpl dao = new PointDAOImpl();
    //PointDAO dao=DaoFactory.createDao();

    public void addPoint( Point data ) throws Exception{
        dao.addPoint( data );
    }


    public void removePoint( Point data ) throws Exception{
        dao.removePoint( data );
    }


    public Point loadPoint( String id ) throws Exception{
        return dao.findById( id );
    }


    public Point updatePoint( Point point ) throws Exception{
        return dao.updatePoint( point );
    }
}
