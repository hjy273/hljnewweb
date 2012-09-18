package com.cabletech.sysmanage.dao;

import com.cabletech.commons.hb.*;
import com.cabletech.sysmanage.domainobjects.*;

public class ModuleDAOImpl extends HibernateDaoSupport{
    public ModuleDAOImpl(){
    }


    public Module addModule( Module module ) throws Exception{
        this.getHibernateTemplate().save( module );
        return module;
    }


    public Module findById( String id ) throws Exception{
        return( Module )this.getHibernateTemplate().load( Module.class, id );
    }


    public void removeModule( Module module ) throws Exception{
        this.getHibernateTemplate().delete( module );
    }


    public Module updateModule( Module module ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( module );
        return module;
    }

}
