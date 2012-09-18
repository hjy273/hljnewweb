package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class TaskOperationDAOImpl extends HibernateDaoSupport{
    public TaskOperationDAOImpl(){
    }


    /**
     * findByID
     *
     * @param taskoper TaskOperation
     * @return TaskOperation
     */
    public TaskOperation findByID( String ID ) throws Exception{
        return( TaskOperation )this.getHibernateTemplate().load( TaskOperation.class, ID );
    }


    /**
     * addTaskOperation
     */
    public TaskOperation addTaskOperation( TaskOperation taskoper ) throws Exception{
        this.getHibernateTemplate().save( taskoper );
        return taskoper;
    }


    /**
     * removeTaskOperation
     *
     * @return TaskOperation
     */
    public void removeTaskOperation( TaskOperation taskoper ) throws Exception{
        this.getHibernateTemplate().delete( taskoper );
    }


    /**
     * updateTaskOperation
     *
     * @return TaskOperation
     */
    public TaskOperation updateTaskOperation( TaskOperation taskoper ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( taskoper );
        return taskoper;
    }
}
