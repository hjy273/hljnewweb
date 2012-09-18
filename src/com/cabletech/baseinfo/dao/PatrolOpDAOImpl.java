package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class PatrolOpDAOImpl extends HibernateDaoSupport{
    public PatrolOpDAOImpl(){
    }


    /**
     * ����Ѳ�������
     * @param patrolOp PatrolOp
     * @return PatrolOp
     * @throws Exception
     */
    public PatrolOp addPatrolOp( PatrolOp patrolOp ) throws Exception{
        this.getHibernateTemplate().save( patrolOp );
        return patrolOp;
    }


    /**
     * loadһ��Ѳ�������
     * @param id String
     * @return PatrolOp
     * @throws Exception
     */
    public PatrolOp findById( String id ) throws Exception{
        return( PatrolOp )this.getHibernateTemplate().load( PatrolOp.class, id );
    }


    /**
     * ɾ��һ��Ѳ�������
     * @param patrolOp PatrolOp
     * @throws Exception
     */
    public void removePatrolOp( PatrolOp patrolOp ) throws Exception{
        this.getHibernateTemplate().delete( patrolOp );
    }


    /**
     * ����һ��Ѳ�������
     * @param patrolOp PatrolOp
     * @return PatrolOp
     * @throws Exception
     */
    public PatrolOp updatePatrolOp( PatrolOp patrolOp ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( patrolOp );
        return patrolOp;
    }
}
