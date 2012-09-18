package com.cabletech.linepatrol.maintenance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestStationData;


@Repository
public class TestStationDataDAO extends HibernateDao<TestStationData, String> {
	
	public TestStationData getStationDataById(String id){
		TestStationData data = get(id);
		initObject(data);
		return data;
	}

	public void deleteStationDateByPlanId(String planid){
		String sql = "delete LP_TEST_STATION_DATA from l where l.test_plan_id='"+planid+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
	public TestStationData getStationDataByPlanIdAndStationId(String planid,String stationId){
		String hql = " from TestStationData t where t.testPlanId='"+planid+"' and t.testStationId='"+stationId+"'";
		List<TestStationData> datas = this.getHibernateTemplate().find(hql);
		if(datas!=null && datas.size()>0){
			TestStationData data = datas.get(0);
			return data;
		}
		return null;
	}
	
}
