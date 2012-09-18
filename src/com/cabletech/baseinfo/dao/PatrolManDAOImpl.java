package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class PatrolManDAOImpl extends HibernateDaoSupport{

    public PatrolMan addPatrolMan( PatrolMan patrolMan ) throws Exception{
        this.getHibernateTemplate().save( patrolMan );
        return patrolMan;
    }


    public PatrolMan findById( String id ) throws Exception{
        return( PatrolMan )this.getHibernateTemplate().load( PatrolMan.class, id );
    }


    public void removePatrolMan( PatrolMan patrolMan ) throws Exception{
        this.getHibernateTemplate().delete( patrolMan );
    }


    public PatrolMan updatePatrolMan( PatrolMan patrolMan ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( patrolMan );
        return patrolMan;
    }

}
