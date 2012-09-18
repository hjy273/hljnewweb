package com.cabletech.linepatrol.maintenance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestCableData;
import com.cabletech.linepatrol.maintenance.module.TestStationData;


@Repository
public class TestLineDataDAO extends HibernateDao<TestCableData, String> {
	
	public TestCableData getLineDataById(String id){
		TestCableData data = get(id);
		initObject(data);
		return data;
	}

	public List<TestCableData> getCableDataByPlanId(String planid){
		String hql=" from TestCableData t where t.testPlanId='"+planid+"'";
		return find(hql);
	}
	
	public TestCableData getLineDataByPlanIdAndLineId(String planid,String lineId){
		String hql = " from TestCableData t where t.testPlanId='"+planid+"' and t.testCablelineId='"+lineId+"'";
		TestCableData cabledata = (TestCableData) this.getSession().createQuery(hql).uniqueResult();
		return cabledata;
		/*List<TestCableData> datas = this.getHibernateTemplate().find(hql);
		if(datas!=null && datas.size()>0){
			TestCableData data = datas.get(0);
			return data;
		}
		return null;*/
	}
	
	/**
	 * 根据测试计划id删除备纤录入数据
	 * @param planid
	 */
	public void deleteLineDataByPlanId(String planid){
		String sql = "delete LP_TEST_CABLE_DATA l where l.test_plan_id='"+planid+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
}
