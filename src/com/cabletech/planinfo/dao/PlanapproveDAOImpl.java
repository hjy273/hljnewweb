package com.cabletech.planinfo.dao;

import com.cabletech.commons.hb.*;
import com.cabletech.planinfo.domainobjects.*;

public class PlanapproveDAOImpl extends HibernateDaoSupport {
    public PlanapproveDAOImpl(){
    }


    public Planapprove addPlanapprove( Planapprove planapprove ) throws Exception{
        this.getHibernateTemplate().save( planapprove );
        return planapprove;
    }

}
