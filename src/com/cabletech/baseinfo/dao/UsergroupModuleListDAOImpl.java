package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class UsergroupModuleListDAOImpl extends HibernateDaoSupport{
    public UsergroupModuleListDAOImpl(){
    }


    public UsergroupModuleList addUgMList( UsergroupModuleList data ) throws
        Exception{
        this.getHibernateTemplate().save( data );
        return data;
    }


    public UsergroupModuleList findById( String id ) throws Exception{
        return( UsergroupModuleList )this.getHibernateTemplate().load(
            UsergroupModuleList.class, id );
    }


    public void deleteUgMList( UsergroupModuleList data ) throws Exception{
        this.getHibernateTemplate().delete( data );
    }


    public UsergroupModuleList updateUgMList( UsergroupModuleList data ) throws
        Exception{
        this.getHibernateTemplate().saveOrUpdate( data );
        return data;
    }

}
