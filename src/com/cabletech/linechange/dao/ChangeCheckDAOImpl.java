package com.cabletech.linechange.dao;

import java.util.*;

import org.apache.log4j.*;
import com.cabletech.commons.hb.*;
import com.cabletech.linechange.domainobjects.*;
import org.hibernate.*;

public class ChangeCheckDAOImpl extends HibernateDaoSupport implements ChangeCheckDao{

    private Logger logger = Logger.getLogger( "ChangeCheckDAOImpl" );
    public List getCheckInfoList( String hql ){
        QueryUtil query = null;
        try{
            query = new QueryUtil();
            List list = query.queryBeans( hql );
            return list;
        }
        catch( Exception ex1 ){
            return null;
        }

    }


    public ChangeCheck getCheckInfo( String id ){
        try{
            return( ChangeCheck )getHibernateTemplate().load( ChangeCheck.class, id );
        }
        catch( Exception ex ){
            ex.printStackTrace();
            logger.warn( "获取ChangeCheck信息" + ex.getMessage() );
            return null;
        }

    }


    public void insertCheck( ChangeCheck checkinfo, ChangeInfo changeinfo ){
        Session session = this.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save( checkinfo );
            session.saveOrUpdate( changeinfo );
            tx.commit();
            session.flush();
        }
        catch( HibernateException ex ){
            try{
                tx.rollback();
            }
            catch( HibernateException ex1 ){
            }
            logger.error( "save ChangeCheck Exception:" + ex.getMessage() );
        }

    }


    public void updateCheck( ChangeCheck checkinfo, ChangeInfo changeinfo ){
        Session session = this.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save( checkinfo );
            session.saveOrUpdate( changeinfo );
            tx.commit();
            session.flush();
        }
        catch( HibernateException ex ){
            try{
                tx.rollback();
            }
            catch( HibernateException ex1 ){
            }
            logger.error( "update ChangeCheck Exception:" + ex.getMessage() );
        }

    }


    public void removeCheck( ChangeCheck checkinfo ){
    }


    /**
     * getChCheckInfoByChangeId
     *
     * @param hql String
     * @param changeid String
     * @return ChangeInfo
     */
    public ChangeCheck getChCheckInfoByChangeId( String hql, String changeid ){
        try{
            List list = getHibernateTemplate().find( hql, changeid );
            Iterator it = list.iterator();
            ChangeCheck changecheck = new ChangeCheck();
            while( it.hasNext() ){
                changecheck = ( ChangeCheck )it.next();
            }
            return( ChangeCheck )changecheck;
        }
        catch( Exception ex ){
            ex.printStackTrace();
            logger.warn( "获取ChangeCheck信息" + ex.getMessage() );
            return null;
        }

    }

}
