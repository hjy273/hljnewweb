package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class PointDAOImpl extends HibernateDaoSupport{

    public Point addPoint( Point point ) throws Exception{
        //System.out.println( "插入一个点:" + point.getPatrolid() + "  name:" + point.getPointName() ); ;
        this.getHibernateTemplate().save( point );
        return point;
    }


    public Point findById( String id ) throws Exception{
        return( Point )this.getHibernateTemplate().load( Point.class, id );
    }


    public void removePoint( Point point ) throws Exception{
        this.getHibernateTemplate().delete( point );
    }


    public Point updatePoint( Point point ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( point );
        return point;
    }
}
