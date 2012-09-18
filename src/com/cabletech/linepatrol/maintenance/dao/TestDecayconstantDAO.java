package com.cabletech.linepatrol.maintenance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestCoreLength;
import com.cabletech.linepatrol.maintenance.module.TestData;
import com.cabletech.linepatrol.maintenance.module.TestDecayConstant;


@Repository
public class TestDecayconstantDAO extends HibernateDao<TestDecayConstant, String> {
	
	public void deletDecayconstantByAnaylseId(String anaylseId){
		String sql = " delete LP_TEST_DECAYCONSTANT t where t.ANAYLSE_ID='"+anaylseId+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
	public TestDecayConstant getDecayConstantByAnaylseId(String anaylseId){
		String hql=" from TestDecayConstant c where c.anaylseId='"+anaylseId+"'";
		TestDecayConstant data = (TestDecayConstant) this.getSession().createQuery(hql).uniqueResult();
		return data;
	}

}