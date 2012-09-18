package com.cabletech.watchinfo.dao;

import com.cabletech.commons.hb.*;
import com.cabletech.watchinfo.domainobjects.*;

public class WatchDAOImpl extends HibernateDaoSupport{

    public WatchDAOImpl(){
    }


    public Watch addWatch( Watch watch ) throws Exception{
        this.getHibernateTemplate().save( watch );
        return watch;
    }


    public SubWatch addSubWatch( SubWatch subwatch ) throws Exception{
        this.getHibernateTemplate().save( subwatch );
        return subwatch;
    }


    public Watch findById( String id ) throws Exception{
        return( Watch )this.getHibernateTemplate().load( Watch.class, id );
    }


    public SubWatch loadSubWatch( String id ) throws Exception{
        return( SubWatch )this.getHibernateTemplate().load( SubWatch.class, id );
    }


    public void removeWatch( Watch watch ) throws Exception{
        this.getHibernateTemplate().delete( watch );
    }


    public void removeSubWatch( SubWatch subwatch ) throws Exception{
        this.getHibernateTemplate().delete( subwatch );
    }


    public Watch updateWatch( Watch watch ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( watch );
        return watch;
    }


    public SubWatch updateSubWatch( SubWatch subwatch ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( subwatch );
        return subwatch;
    }

}
