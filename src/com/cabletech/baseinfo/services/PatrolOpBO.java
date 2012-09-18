package com.cabletech.baseinfo.services;

import java.util.*;

import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.*;

/**
 * 
 * （nv系列）巡检事故码管理
 *
 */
public class PatrolOpBO extends BaseBisinessObject{

    PatrolOpDAOImpl dao = new PatrolOpDAOImpl();
    public PatrolOpBO(){
    }

    //添加
    public void addPatrolOp( PatrolOp data ) throws Exception{
        dao.addPatrolOp( data );
    }

    /**
     * 载入巡检操作码。
     * @param id
     * @return
     * @throws Exception
     */
    public PatrolOp loadPatrolOp( String id ) throws Exception{
        return dao.findById( id );
    }

    /**
     * 删除巡检事故码（nv系列）
     * @param patrolOp
     * @throws Exception
     */
    public void removePatrolOp( PatrolOp patrolOp ) throws Exception{
        dao.removePatrolOp( patrolOp );
    }

    /*
     *更新巡检事故码 
     */
    public PatrolOp updatePatrolOp( PatrolOp patrolOp ) throws Exception{
        return dao.updatePatrolOp( patrolOp );
    }


    /**
     * 主键检查
     * @return int
     * @throws Exception
     */
    public int checkPk( String id ) throws Exception{
        int flag = -1;

        String sql = "select * from patroloperation where operationcode= '" + id + "'";
        QueryUtil jutil = new QueryUtil();

        Vector vct = jutil.executeQueryGetVector( sql );
        if( vct == null || vct.size() == 0 ){
            flag = 1;
        }

        return flag;
    }
}
