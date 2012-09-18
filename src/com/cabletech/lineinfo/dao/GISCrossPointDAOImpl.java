package com.cabletech.lineinfo.dao;

import com.cabletech.commons.hb.*;
import com.cabletech.lineinfo.domainobjects.*;

public class GISCrossPointDAOImpl extends HibernateDaoSupport{
    public GISCrossPointDAOImpl(){
    }


    public GISCrossPoint addGISCrossPoint( GISCrossPoint crosspoint ) throws
        Exception{
        this.getHibernateTemplate().save( crosspoint );
        return crosspoint;
    }


    public GISCrossPoint findById( String id ) throws Exception{
        return( GISCrossPoint )this.getHibernateTemplate().load( GISCrossPoint.class,
            id );
    }


    public void removecrosspoint( GISCrossPoint crosspoint ) throws Exception{
        this.getHibernateTemplate().delete( crosspoint );
    }


    public GISCrossPoint updatecrosspoint( GISCrossPoint crosspoint ) throws
        Exception{
        this.getHibernateTemplate().saveOrUpdate( crosspoint );
        return crosspoint;
    }

}
