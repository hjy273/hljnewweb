package com.cabletech.linechange.dao;

import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import com.cabletech.commons.hb.*;
import com.cabletech.linechange.domainobjects.*;
import org.hibernate.*;

public class ChangeBuildDAOImpl extends HibernateDaoSupport implements ChangeBuildDao{

    private Logger logger = Logger.getLogger( "ChangeBuildDAOImpl" );
    public List getBuildInfoList( String hql ){
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


    public ChangeBuild getBuildInfo( String id ){
        try{
            return( ChangeBuild )getHibernateTemplate().load( ChangeBuild.class, id );
        }
        catch( Exception ex ){
            ex.printStackTrace();
            logger.warn( "获取ChangeBuild信息" + ex.getMessage() );
            return null;
        }
    }


    public void insertBuild( ChangeBuild buildinfo, ChangeInfo changeinfo ){
        Session session = this.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save( buildinfo );
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


    public void updateBuild( ChangeBuild buildinfo, ChangeInfo changeinfo ){
        Session session = this.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.saveOrUpdate( buildinfo );
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


    public void removeBuild( ChangeBuild buildinfo ){
    }


    /**
     * blankOut
     *
     * @param string String
     */
    public void blankOut( String changeid ){
        QueryUtil query = null;
        String id = "";
        String querysql = "select id from changebuild where id not like '%M' and changeid='"+changeid+"'";
        String updatesql = "";
        try{
            query = new QueryUtil();
            UpdateUtil update = new UpdateUtil();
            //List list = query.queryBeans(querysql);
            Iterator it = query.queryBeans( querysql ).iterator();
//            int size = list.size();
//            logger.info("更新 "+size+" 条数据");
//            for(int i=0;i<size;i++){
//                logger.info(list.get(i).toString());
//                //logger.info("bean "+bean.get(id));
//               // List l = (List)list.get(i);
//                //logger.info("-- "+l.get(0));
//                //id = (String) l.get(0);
//                id = list.get(i).toString();
//                logger.info("id :"+id) ;
//                logger.info("updatesql "+updatesql);
//                //update.executeUpdate(updatesql);
//            }
            while( it.hasNext() ){
                DynaBean dynaBean = ( BasicDynaBean )it.next();
                 id = ( String ) dynaBean.get( "id" );//update changebuild set id=('"+id+"M'),state='false' where  id='"+id+"'"
                 updatesql = "update changebuild set state='false' where  id='"+id+"'";
                 logger.info("id :"+id) ;
                 logger.info("updatesql "+updatesql);
                 update.executeUpdate(updatesql);
            }

        }
        catch( Exception ex1 ){
            logger.info("更新记录id异常："+ex1.getMessage());
        }

    }


    /**
     * getBuildInfoByChangeId
     *
     * @param hql String
     * @param changeid String
     * @return ChangeBuild
     */
    public ChangeBuild getBuildInfoByChangeId( String hql, String changeid ){
        try{
            List list = getHibernateTemplate().find( hql, changeid );
            Iterator it = list.iterator();
            ChangeBuild build = new ChangeBuild();
            while( it.hasNext() ){
                build = ( ChangeBuild )it.next();
            }
            return( ChangeBuild)build;
        }
        catch( Exception ex ){
            ex.printStackTrace();
            logger.warn( "获取Changebuild信息" + ex.getMessage() );
            return null;
        }

    }

}
