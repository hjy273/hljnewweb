package com.cabletech.lineinfo.dao;

import org.apache.log4j.*;
import com.cabletech.commons.hb.*;
import com.cabletech.lineinfo.domainobjects.*;

public class MsginfoDAOImpl extends HibernateDaoSupport{
    private Logger logger = Logger.getLogger( MsginfoDAOImpl.class );

    public MsginfoDAOImpl(){
    }


    public Msginfo addMsginfo( Msginfo msginfo ) throws Exception{
        logger.info( "MsginfoDAOImpl::addMsginfo " );
        this.getHibernateTemplate().save( msginfo );
        return msginfo;
    }


    public Msginfo findById( String id ) throws Exception{
        logger.info( "MsginfoDAOImpl::findById " );
        return( Msginfo )this.getHibernateTemplate().load( Msginfo.class, id );
    }


    public void removeMsginfo( Msginfo msginfo ) throws Exception{
        logger.info( "MsginfoDAOImpl::removeMsginfo " );
        this.getHibernateTemplate().delete( msginfo );
    }


    public Msginfo updateMsginfo( Msginfo msginfo ) throws Exception{
        logger.info( "MsginfoDAOImpl::updateMsginfo " );
        this.getHibernateTemplate().saveOrUpdate( msginfo );
        return msginfo;
    }

}
