package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class AlertreceiverListDAOImpl extends HibernateDaoSupport{

    public AlertreceiverList addAlertreceiverList( AlertreceiverList data ) throws Exception{
        this.getHibernateTemplate().save( data );
        return data;
    }


    public AlertreceiverList findById( String id ) throws Exception{
        return( AlertreceiverList )this.getHibernateTemplate().load( AlertreceiverList.class, id );
    }


    public void removeAlertreceiverList( AlertreceiverList data ) throws Exception{
        this.getHibernateTemplate().delete( data );
    }


    public AlertreceiverList updateAlertreceiverList( AlertreceiverList data ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( data );
        return data;
    }
}
