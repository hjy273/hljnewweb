package com.cabletech.commons.hb;

import org.hibernate.*;

/**
 * Convenient super class for Hibernate data access objects.
 * Requires a SessionFactory to be set, providing a
 * HibernateTemplate based on it to subclasses.
 *
 * <p>This base class is mainly intended for HibernateTemplate usage
 * but can also be used when working with SessionFactoryUtils directly,
 * e.g. in combination with HibernateInterceptor-managed Sessions.
 *
 * @author Juergen Hoeller
 * @since 28.07.2003
 * @see #setSessionFactory
 * @see org.springframework.orm.hibernate.HibernateTemplate
 * @see org.springframework.orm.hibernate.HibernateInterceptor
 */
public abstract class HibernateDaoSupport{
    private HibernateTemplate hibernateTemplate;
    private HibernateSession sessionFactory;

    /**
     * HibernateDaoSupport
     */
    public HibernateDaoSupport(){
        this.sessionFactory = new HibernateSession();
        this.hibernateTemplate = new HibernateTemplate( sessionFactory );
    }


    /**
     * Return the Hibernate SessionFactory used by this DAO.
     */
    public final Session getSession(){
        try{
            return sessionFactory.currentSession();
        }
        catch( HibernateException ex ){
            return null;
        }
    }


    /**
     * Return the HibernateTemplate for this data access object,
     * pre-initialized with the SessionFactory of this DAO.
     */
    public final HibernateTemplate getHibernateTemplate(){
        return hibernateTemplate;
    }


    /**
     * @see org.appfuse.persistence.DAO#saveObject(java.lang.Object)
     */
    public Object saveObject( Object o ) throws Exception{
        getHibernateTemplate().saveOrUpdate( o );
        return o;
    }
}
