package com.cabletech.linepatrol.maintenance.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestCoreData;


@Repository
public class TestCoredataDAO extends HibernateDao<TestCoreData, String> {
	
	
	public TestCoreData getCoreData(String chipDataId){
		String hql=" from TestCoreData c where c.coreId='"+chipDataId+"'";
		TestCoreData data = (TestCoreData) this.getSession().createQuery(hql).uniqueResult();
		return data;
	}

}