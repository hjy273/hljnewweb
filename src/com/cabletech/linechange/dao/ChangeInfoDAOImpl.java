package com.cabletech.linechange.dao;

import java.util.*;

import org.apache.log4j.*;
import com.cabletech.commons.hb.*;
import com.cabletech.linechange.domainobjects.*;
import org.hibernate.Session;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: Cable tech</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ChangeInfoDAOImpl extends HibernateDaoSupport implements ChangeInfoDao{
    private Logger logger = Logger.getLogger("ChangeInfoDAOImpl");
    public ChangeInfoDAOImpl(){
    }
    public List getChangeInfo(String hql) {
        QueryUtil query = null;
        try{
            query = new QueryUtil();
            List list = query.queryBeans(hql);
            logger.info("size :"+list.size());
            return list;
        }
        catch( Exception ex1 ){
            ex1.printStackTrace();
            logger.error("get changeinfo list :"+ex1.getMessage());
            return null;
        }
//        try{
//            List list = getHibernateTemplate().find( hql );
//            Session session = this.getSession();
//            list = session.createQuery(hql).list();
//            logger.info(" --"+list.size());
//            return list;
//        }
//        catch( Exception ex ){
//            return null;
//        }

    }


    public ChangeInfo getChange( String changeid ){
        //StringBuffer hql = null;
        //hql.append("select id,changename,changepro,changeaddr,lineclass,INVOLVED_SYSTEM,changelength,startdate,plantime,remark,applydatumid,applyunit,applytime,step,approveresult from changeinfo changeinfo where id="+changeid);
        try{
            return( ChangeInfo )getHibernateTemplate().load( ChangeInfo.class, changeid );
        }
        catch( Exception ex ){
            ex.printStackTrace();
            logger.warn("获取ChangeInfo信息"+ex.getMessage()) ;
            return null;
        }
    }


    public void insertChange( ChangeInfo changeinfo ){
        //String hql= "insert into changeinfo (id,changename,changepro,changeaddr,lineclass,INVOLVED_SYSTEM,changelength,startdate,plantime,remark,applydatumid,applyunit,applytime,step,approveresult ) values ()";
        try{
            getHibernateTemplate().save( changeinfo );
        }
        catch( Exception ex ){

            logger.error("保存changinfo信息 "+ex.getMessage());
        }
    }


    public void removeChange( ChangeInfo changeinfo){
        try{
            getHibernateTemplate().delete( changeinfo );
        }
        catch( Exception ex ){
            logger.error("删除changinfo信息 "+ex.getMessage());
        }
    }


    public void updateChange( ChangeInfo change ){
        try{
            this.getHibernateTemplate().saveOrUpdate(change);
        }
        catch( Exception ex ){

            logger.error("保存changinfo信息 "+ex.getMessage());
        }

    }

}
