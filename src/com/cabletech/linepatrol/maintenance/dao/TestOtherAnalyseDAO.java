package com.cabletech.linepatrol.maintenance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestData;
import com.cabletech.linepatrol.maintenance.module.TestEndWaste;
import com.cabletech.linepatrol.maintenance.module.TestOtherAnalyse;


@Repository
public class TestOtherAnalyseDAO extends HibernateDao<TestOtherAnalyse, String> {

	public void deletOtherByAnaylseId(String anaylseId){
		String sql = " delete LP_TEST_OTHERANALYSE t where t.ANAYLSE_ID='"+anaylseId+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
	public TestOtherAnalyse getOtherAnalyseByAnaylseId(String anaylseId){
		String hql=" from TestOtherAnalyse c where c.anaylseId='"+anaylseId+"'";
		TestOtherAnalyse data = (TestOtherAnalyse) this.getSession().createQuery(hql).uniqueResult();
		return data;
	}
}