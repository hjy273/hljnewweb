package com.cabletech.linepatrol.maintenance.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestData;


@Repository
public class TestDataDAO extends HibernateDao<TestData, String> {
	
	public TestData getDataById(String id){
		return (TestData) this.getHibernateTemplate().load(TestData.class,id);
		
	}
	
	/**
	 * ������ƻ���ѯ¼��������Ϣ��
	 * @param planid
	 * @return
	 */
	public TestData getDataByPlanId(String planid){
		String hql=" from TestData t where t.testPlanId='"+planid+"'";
		TestData data = (TestData) getSession().createQuery(hql).uniqueResult();
		return data;
	/*	List<TestData> data = this.getHibernateTemplate().find(hql);
		if(data!=null && data.size()>0){
			return data.get(0);
		}
		return null;*/
	}
	
	/**
	 * ������ƻ�idɾ��¼��������Ϣ��
	 * @param planid
	 */
	public void deleteDataByPlanId(String planid){
		String sql = "delete LP_TEST_DATA l where l.test_plan_id='"+planid+"'";
		getJdbcTemplate().execute(sql);
	}

}