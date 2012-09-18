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
     * ����:���˹���Ѳ����Ա��,���Ѳ����Ա��Ϣ
     * ����:patrolManson PatrolManSon:Ѳ����Ա����
     * ����:��ӳɹ����� true ���򷵻� false
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
     * ����:���˹���Ѳ����Ա��,����Ѳ����Ա��Ϣ
     * ����:patrolManson PatrolManSon:Ѳ����Ա����
     * ����:���³ɹ����� true ���򷵻� false
     *
     */
    public boolean updatePatrolManByNoGroup( PatrolManSon pSon ){
        return dao.updatePatrolManByNoGroup( pSon );
    }


    /**
     * ����:���˹���Ѳ����Ա��,ɾ��Ѳ����Ա��Ϣ
     * ����:patrolManson PatrolManSon:Ѳ����Ա����
     * ����:ɾ���ɹ����� true ���򷵻� false
     *
     */
    public boolean removePatrolManSonByNoGroup( PatrolManSon pSon ){
        return dao.removePatrolManSonByNoGroup( pSon );
    }
}
