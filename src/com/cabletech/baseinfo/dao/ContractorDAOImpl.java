package com.cabletech.baseinfo.dao;

import java.util.List;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class ContractorDAOImpl extends HibernateDaoSupport{

    public Contractor addContractor( Contractor contractor ) throws Exception{
        this.getHibernateTemplate().save( contractor );
        return contractor;
    }


    public Contractor findById( String id ) throws Exception{
        return( Contractor )this.getHibernateTemplate().load( Contractor.class,
            id );
    }


    public void removeContractor( Contractor contractor ) throws Exception{
        this.getHibernateTemplate().delete( contractor );
    }


    public Contractor updateContractor( Contractor contractor ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( contractor );
        return contractor;
    }
    
    public List<Contractor> getAllContractor(UserInfo userInfo){
    	String hql="from Contractor where regionid=?";
    	try {
			return this.getHibernateTemplate().find(hql,userInfo.getRegionID());
		} catch (Exception e) {
			return null;
		}
    }
    
	/**
	 * 通过代维单位编号获得代维单位名称
	 * 
	 * @param id
	 *            代维单位编号
	 * @return 代维单位名称
	 * @throws Exception
	 */
	public String getContractorNameById(String id) throws Exception {
		Contractor contractor = this.findById(id);
		return contractor.getContractorName();
	}
}
