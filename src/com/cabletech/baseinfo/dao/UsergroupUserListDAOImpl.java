package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;
import org.apache.log4j.Logger;

public class UsergroupUserListDAOImpl extends HibernateDaoSupport{
    private static Logger logger = Logger.getLogger("UsergroupUserListDAOImpl");
    public UsergroupUserListDAOImpl(){
    }


    /**
     *
     * @param data UsergroupUserList
     * @return UsergroupUserList
     * @throws Exception
     */
    public UsergroupUserList addUgUList( UsergroupUserList data ) throws
        Exception{
        this.getHibernateTemplate().save( data );
        return data;
    }


    public UsergroupUserList findById( String id ) throws Exception{
        return( UsergroupUserList )this.getHibernateTemplate().load(
            UsergroupUserList.class, id );
    }


    public void deleteUgUList( UsergroupUserList data ) throws Exception{
        this.getHibernateTemplate().delete( data );
    }


    public UsergroupUserList updateUgUList( UsergroupUserList data ) throws
        Exception{
        this.getHibernateTemplate().saveOrUpdate( data );
        return data;
    }
    /**
     *  清除此用户任何组内的信息，确保一个用户只属于一个用户组
     * @param userid String
     */
    public void clearUgUList (String userid)throws
        Exception{
        String sql = "delete USERGOURPUSERLIST where userid='"+userid+"'";
        logger.info("clearsql ："+sql);
        UpdateUtil del = new UpdateUtil();
        del.executeUpdate(sql);
    }

}
