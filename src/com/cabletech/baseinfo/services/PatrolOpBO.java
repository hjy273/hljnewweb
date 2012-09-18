package com.cabletech.baseinfo.services;

import java.util.*;

import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.*;

/**
 * 
 * ��nvϵ�У�Ѳ���¹������
 *
 */
public class PatrolOpBO extends BaseBisinessObject{

    PatrolOpDAOImpl dao = new PatrolOpDAOImpl();
    public PatrolOpBO(){
    }

    //���
    public void addPatrolOp( PatrolOp data ) throws Exception{
        dao.addPatrolOp( data );
    }

    /**
     * ����Ѳ������롣
     * @param id
     * @return
     * @throws Exception
     */
    public PatrolOp loadPatrolOp( String id ) throws Exception{
        return dao.findById( id );
    }

    /**
     * ɾ��Ѳ���¹��루nvϵ�У�
     * @param patrolOp
     * @throws Exception
     */
    public void removePatrolOp( PatrolOp patrolOp ) throws Exception{
        dao.removePatrolOp( patrolOp );
    }

    /*
     *����Ѳ���¹��� 
     */
    public PatrolOp updatePatrolOp( PatrolOp patrolOp ) throws Exception{
        return dao.updatePatrolOp( patrolOp );
    }


    /**
     * �������
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
