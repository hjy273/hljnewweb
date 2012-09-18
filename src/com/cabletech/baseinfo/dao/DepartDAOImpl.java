package com.cabletech.baseinfo.dao;

import java.util.ArrayList;
import java.util.List;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class DepartDAOImpl extends HibernateDaoSupport{

    public Depart addDepart( Depart depart ) throws Exception{
        this.getHibernateTemplate().save( depart );
        return depart;
    }


    public Depart findById( String id ) throws Exception{
        return( Depart )this.getHibernateTemplate().load( Depart.class, id );
    }


    public void removeDepart( Depart depart ) throws Exception{
        this.getHibernateTemplate().delete( depart );
    }


    public Depart updateDepart( Depart depart ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( depart );
        return depart;
    }
    /**
     * 通过用户对象得到可以查询的单位名称
     * @param userinfo
     * @return
     */
    public List<String> findByUser(UserInfo userinfo){
    	List<String> list=new ArrayList<String>();
    	String sb;
    	sb="select deptName from Depart where state is null";
    	try{
			list = this.getHibernateTemplate().find(sb);
			String depttype=userinfo.getDeptype();
			String sql;
			if("2".equals(depttype)){
				sql="select contractorName from Contractor where contractorID="+userinfo.getDeptID();
				List<String> list1=new ArrayList<String>();
				list1=this.getHibernateTemplate().find(sql);
				list.addAll(list1);				
			}else{
				sql="select c.contractorName from Contractor c where state is null";
				List<String> list1=new ArrayList<String>();
				list1=this.getHibernateTemplate().find(sql);
				list.addAll(list1);
			}
		}
		catch( Exception ex ){	
		}
    	return list;
    }
    
    public List<Depart> getAllDepart(UserInfo userInfo){
    	String hql="from Depart where regionid=?";
    	try {
			return this.getHibernateTemplate().find(hql, userInfo.getRegionID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
    }
}
