package com.cabletech.planinfo.dao;

import com.cabletech.commons.hb.*;
import com.cabletech.planinfo.domainobjects.*;

public class TaskDAOImpl extends HibernateDaoSupport implements PlanDAO{

    public Task addTask( Task task ) throws Exception{
        this.getHibernateTemplate().save( task );
        return task;
    }


    public SubTask addSubTask( SubTask subtask ) throws Exception{
        this.getHibernateTemplate().save( subtask );
        return subtask;
    }


    /**
     * 添加任务操作对应纪录
     * @param taskoperationlist TaskOperationList
     * @return TaskOperationList
     * @throws Exception
     */
    public TaskOperationList addTaskoperationlist( TaskOperationList
        taskoperationlist ) throws
        Exception{
        this.getHibernateTemplate().save( taskoperationlist );
        return taskoperationlist;
    }


    /**
     * 载入一个任务
     * @param id String
     * @return Task
     * @throws Exception
     */
    public Task findById( String id ) throws Exception{
        return( Task )this.getHibernateTemplate().load( Task.class, id );
    }


    /**
     *更新一个任务
     * @param task Task
     * @return Task
     * @throws Exception
     */
    public Task updateTask( Task task ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( task );
        return task;
    }


	public int queryPlanCount(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

}
