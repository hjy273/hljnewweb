package com.cabletech.baseinfo.services;

import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;

public class PatrolManSonBO extends BaseBisinessObject{

    public PatrolManSonBO(){
    }


    PatrolManSonDAOImpl dao = new PatrolManSonDAOImpl();

    public void addPatrolManSon( PatrolManSon data ) throws Exception{
        dao.addPatrolManSon( data );
    }


    /**
     * 功能:按人管理巡检人员中,添加巡检人员信息
     * 参数:patrolManson PatrolManSon:巡检人员对象
     * 返回:添加成功返回 true 否则返回 false
     *
     */
    public boolean addPartrolManSonByNoGroup( PatrolManSon pSon ){
        return dao.addPartrolManSonByNoGroup( pSon );
    }


    public boolean removePatrolManSon( PatrolManSon data ) throws Exception{
        return dao.removePatrolManSon( data );
    }


    public PatrolManSon loadPatrolManSon( String id ) throws Exception{
        return dao.findById( id );
    }


    public PatrolManSon updatePatrolManSon( PatrolManSon patrolMan ) throws
        Exception{
        return dao.updatePatrolManSon( patrolMan );
    }


    /**
     * 功能:按人管理巡检人员中,更新巡检人员信息
     * 参数:patrolManson PatrolManSon:巡检人员对象
     * 返回:更新成功返回 true 否则返回 false
     *
     */
    public boolean updatePatrolManByNoGroup( PatrolManSon pSon ){
        return dao.updatePatrolManByNoGroup( pSon );
    }


    /**
     * 功能:按人管理巡检人员中,删除巡检人员信息
     * 参数:patrolManson PatrolManSon:巡检人员对象
     * 返回:删除成功返回 true 否则返回 false
     *
     */
    public boolean removePatrolManSonByNoGroup( PatrolManSon pSon ){
        return dao.removePatrolManSonByNoGroup( pSon );
    }
}
