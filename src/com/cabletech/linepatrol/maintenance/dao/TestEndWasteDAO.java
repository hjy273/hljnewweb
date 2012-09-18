package com.cabletech.linepatrol.maintenance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestData;
import com.cabletech.linepatrol.maintenance.module.TestDecayConstant;
import com.cabletech.linepatrol.maintenance.module.TestEndWaste;


@Repository
public class TestEndWasteDAO extends HibernateDao<TestEndWaste, String> {
	
	public void deletEndWasteByAnaylseId(String anaylseId){
		String sql = " delete LP_TEST_ENDWASTE t where t.ANAYLSE_ID='"+anaylseId+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
	public TestEndWaste getEndWasteByAnaylseId(String anaylseId){
		String hql=" from TestEndWaste c where c.anaylseId='"+anaylseId+"'";
		TestEndWaste data = (TestEndWaste) this.getSession().createQuery(hql).uniqueResult();
		return data;
	}

}