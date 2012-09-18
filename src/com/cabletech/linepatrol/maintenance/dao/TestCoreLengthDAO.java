package com.cabletech.linepatrol.maintenance.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestCoreData;
import com.cabletech.linepatrol.maintenance.module.TestCoreLength;


@Repository
public class TestCoreLengthDAO extends HibernateDao<TestCoreLength, String> {
	
	public void deletCoreLengthByAnaylseId(String anaylseId){
		String sql = " delete LP_TEST_CORELENGTH t where t.ANAYLSE_ID='"+anaylseId+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
	public TestCoreLength getCoreLengthByAnaylseId(String anaylseId){
		String hql=" from TestCoreLength c where c.anaylseId='"+anaylseId+"'";
		TestCoreLength data = (TestCoreLength) this.getSession().createQuery(hql).uniqueResult();
		return data;
	}

}