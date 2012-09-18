package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class UserInfoDAOImpl extends HibernateDaoSupport{
    public UserInfo addUserInfo( UserInfo userInfo ) throws Exception{
        //����
    //    System.out.print( "׼������UserInfo��Ϣ" );
        this.getHibernateTemplate().save( userInfo );
   //     System.out.print( "�ѱ���UserInfo��Ϣ������UserInfo����" );
        return userInfo;
    }


    /**
     * ͨ��id�����û���Ϣ
     * @param id String
     * @return UserInfo
     * @throws Exception
     */
    public UserInfo findById( String id ) throws Exception{
        return( UserInfo )this.getHibernateTemplate().load( UserInfo.class, id );
    }


    public void removeUserInfo( UserInfo userInfo ) throws Exception{
        this.getHibernateTemplate().delete( userInfo );
    }


    public UserInfo updateUserInfo( UserInfo userInfo ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( userInfo );
        return userInfo;
    }

}
