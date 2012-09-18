package com.cabletech.troublemanage.dao;

import com.cabletech.commons.hb.*;
import com.cabletech.troublemanage.domainobjects.*;

public class AccidentDAOImpl extends HibernateDaoSupport{

    public Accident addAccident( Accident data ) throws Exception{
        this.getHibernateTemplate().save( data );
        return data;
    }


    public Accident findById( String id ) throws Exception{
        return( Accident )this.getHibernateTemplate().load( Accident.class, id );
    }


    public void removeAccident( Accident data ) throws Exception{
        this.getHibernateTemplate().delete( data );
    }


    public Accident updateAccident( Accident data ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( data );
        return data;
    }
}
