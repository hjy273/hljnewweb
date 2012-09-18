package com.cabletech.linechange.dao;

import java.util.*;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.linechange.domainobjects.*;
import org.apache.log4j.Logger;
import com.cabletech.commons.hb.QueryUtil;
import org.hibernate.Session;
import org.hibernate.*;

public class ChangeSurveyDAOImpl extends HibernateDaoSupport implements ChangeSurveyDao{
    private Logger logger = Logger.getLogger( "ChangeSurveyDAOImpl" );
    public ChangeSurveyDAOImpl(){
    }


    /**
     * delChangeSurvey
     *
     * @todo Implement this com.cabletech.linechange.dao.ChangeSurveyDao
     *   method
     */
    public void delChangeSurvey(){
    }


    /**
     * getChangeSurvey
     *
     * @return ChangeSurvey
     * @todo Implement this com.cabletech.linechange.dao.ChangeSurveyDao
     *   method
     */
    public ChangeSurvey getChangeSurvey( String id ){
        try{
            return( ChangeSurvey )getHibernateTemplate().load( ChangeSurvey.class, id );
        }
        catch( Exception ex ){
            ex.printStackTrace();
            logger.warn( "获取ChangeSurvey信息" + ex.getMessage() );
            return null;
        }
    }


    /**
     * getChangeSurvey
     *
     * @return ChangeSurvey
     * @todo Implement this com.cabletech.linechange.dao.ChangeSurveyDao
     *   method
     */
    public ChangeSurvey getChangeSurveyForChangeID( String hql, String changeid ){
        try{
            List list = getHibernateTemplate().find( hql, changeid );
            Iterator it = list.iterator();
            ChangeSurvey survey = new ChangeSurvey();
            while( it.hasNext() ){
                survey = ( ChangeSurvey )it.next();
            }
//            System.out.println( "changesurvey: " + survey.getBudget() );
            return( ChangeSurvey )survey;
        }
        catch( Exception ex ){
            ex.printStackTrace();
            logger.warn( "获取ChangeSurvey信息" + ex.getMessage() );
            return null;
        }
    }


    /**
     * getChangeSurveyList
     *
     * @return List
     * @todo Implement this com.cabletech.linechange.dao.ChangeSurveyDao
     *   method
     */
    public List getChangeSurveyList( String hql ){
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


    /**
     * saveChangeSurvey
     *
     * @todo Implement this com.cabletech.linechange.dao.ChangeSurveyDao
     *   method
     */
    public void saveChangeSurvey( ChangeSurvey surveyinfo, ChangeInfo changeinfo ){
        Session session = this.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save( surveyinfo );
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
            logger.error( "save survey Exception:" + ex.getMessage() );
        }

    }


    /**
     * updateChangeSurvey
     *
     * @todo Implement this com.cabletech.linechange.dao.ChangeSurveyDao
     *   method
     */
    public void updateChangeSurvey( ChangeSurvey surveyinfo, ChangeInfo changeinfo ){
        Session session = this.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save( surveyinfo );
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
            logger.error( "update survey Exception:" + ex.getMessage() );
        }

    }
}
