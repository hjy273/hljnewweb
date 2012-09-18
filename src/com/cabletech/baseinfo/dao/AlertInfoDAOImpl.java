package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class AlertInfoDAOImpl extends HibernateDaoSupport{

    public AlertInfo findById( String id ) throws Exception{
        return( AlertInfo )this.getHibernateTemplate().load( AlertInfo.class, id );
    }

}
