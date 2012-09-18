package com.cabletech.baseinfo.dao;

import java.util.ArrayList;
import java.util.List;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class UsergroupDAOImpl extends HibernateDaoSupport{
    public UsergroupDAOImpl(){
    }


    public Usergroup addUsergroup( Usergroup data ){
    	try{
    		this.getHibernateTemplate().save( data );
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return data;
    }


    public Usergroup findById( String id ) throws Exception{
        return( Usergroup )this.getHibernateTemplate().load( Usergroup.class, id );
    }


    public void removeUsergroup( Usergroup data ) throws Exception{
        this.getHibernateTemplate().delete( data );
    }


    public Usergroup updateUsergroup( Usergroup data ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( data );
        return data;
    }


	public boolean validate(Usergroup usergroup) {
		String hql = "from Usergroup where groupname='" + usergroup.getGroupname() + "'";
		List<Usergroup> list = new ArrayList<Usergroup>();
		try {
			list = super.getHibernateTemplate().find(hql);
			this.getSession().clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list.size() > 0) {
			if(usergroup.getId()!=null&&usergroup.getId().equals(list.get(0).getId())){
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

}
