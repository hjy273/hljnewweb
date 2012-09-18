package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class PatrolOpDAOImpl extends HibernateDaoSupport{
    public PatrolOpDAOImpl(){
    }


    /**
     * 增加巡检操作码
     * @param patrolOp PatrolOp
     * @return PatrolOp
     * @throws Exception
     */
    public PatrolOp addPatrolOp( PatrolOp patrolOp ) throws Exception{
        this.getHibernateTemplate().save( patrolOp );
        return patrolOp;
    }


    /**
     * load一个巡检操作码
     * @param id String
     * @return PatrolOp
     * @throws Exception
     */
    public PatrolOp findById( String id ) throws Exception{
        return( PatrolOp )this.getHibernateTemplate().load( PatrolOp.class, id );
    }


    /**
     * 删除一个巡检操作码
     * @param patrolOp PatrolOp
     * @throws Exception
     */
    public void removePatrolOp( PatrolOp patrolOp ) throws Exception{
        this.getHibernateTemplate().delete( patrolOp );
    }


    /**
     * 更新一个巡检操作码
     * @param patrolOp PatrolOp
     * @return PatrolOp
     * @throws Exception
     */
    public PatrolOp updatePatrolOp( PatrolOp patrolOp ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( patrolOp );
        return patrolOp;
    }
}
