package com.cabletech.baseinfo.services;

import java.util.*;

import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.*;

public class PointExtraBO extends BaseBisinessObject{
    public PointExtraBO(){
    }


    /**
     * �ж�ͬһ�߶���,ͬһ����ĵ��Ƿ��Ѿ�����
     * @param sublineid String
     * @param pointOrder String
     * @throws Exception
     * @return int
     */
    public int checkPointOrder( String sublineid, String pointOrder ) throws
        Exception{
        int flag = 1;
        // 1 : �ɲ���õ� , -1 : �õ������Ѵ���,���ɲ���
        //db
        String sqlString = new String();
        sqlString = "select distinct pointid from pointinfo where sublineid='" +
                    sublineid + "' and inumber ='" + pointOrder + "'";

        //System.out.println( sqlString );

        QueryUtil jutil = new QueryUtil();
        Vector vct = jutil.executeQueryGetVector( sqlString );

        //System.out.println( "VCT length : " + vct.size() );
        //logic operation
        if( vct.size() > 0 ){
            flag = -1;
        }

        return flag;
    }


    /**
     * �¼ӵ㣬 �����߶� ����� �Զ��ۼ�
     * @param sublineid String
     * @param opFlag String : 1, ++  0, --
     * @return int
     * @throws Exception
     */
    public int updateSublineDym( String sublineid, String opFlag ) throws
        Exception{
        int flag = 1;
        // 1 : �ɹ� , -1 : ʧ��
        //db
        String sqlString = new String();

        if( opFlag.equals( "1" ) ){
            sqlString =
                "update sublineinfo set checkpoints = (checkpoints + 1) where sublineid = '" +
                sublineid + "'";
        }
        else{
            sqlString =
                "update sublineinfo set checkpoints = (checkpoints - 1) where sublineid = '" +
                sublineid + "'";

        }

        //System.out.println( sqlString );

        UpdateUtil util = new UpdateUtil();
        try{
            util.executeUpdate( sqlString );
        }
        catch( Exception e ){
            flag = -1;
        }

        return flag;
    }

}
