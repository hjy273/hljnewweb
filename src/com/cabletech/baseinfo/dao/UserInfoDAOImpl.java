package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class UserInfoDAOImpl extends HibernateDaoSupport{
    public UserInfo addUserInfo( UserInfo userInfo ) throws Exception{
        //测试
    //    System.out.print( "准备保存UserInfo信息" );
        this.getHibernateTemplate().save( userInfo );
   //     System.out.print( "已保存UserInfo信息，返回UserInfo对象" );
        return userInfo;
    }


    /**
     * 通过id查找用户信息
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
