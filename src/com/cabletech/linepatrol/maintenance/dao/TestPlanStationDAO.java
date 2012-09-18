package com.cabletech.linepatrol.maintenance.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestPlanLine;
import com.cabletech.linepatrol.maintenance.module.TestPlanStation;
import com.cabletech.linepatrol.maintenance.service.MainTenanceConstant;


@Repository
public class TestPlanStationDAO extends HibernateDao<TestPlanStation, String> {


	public TestPlanStation getTestPlanStationById(String id){
		TestPlanStation planStation = get(id);
		initObject(planStation);
		return planStation;
	}
	/**
	 * 根据测试计划id查询测试计划中的接地电阻
	 * @param planid
	 * @return
	 */
	public List<TestPlanStation> getTestPlanStationByPlanId(String planid){
		String hql=" from TestPlanStation t where t.testPlanId='"+planid+"'";
		return (List<TestPlanStation>) this.getSession().createQuery(hql);
	}
	
	
	public void deleteStation(String planid){
		String sql="delete  from lp_test_plan_station station where station.test_plan_id='"+planid+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
	public void savePlanStations(Map<String,TestPlanStation> planStations,String planid){
		deleteStation(planid);
		if(planStations!=null && planStations.size()>0){
			Set set = planStations.keySet();
			Iterator ite = set.iterator();
			while(ite.hasNext()){
				TestPlanStation station = planStations.get(ite.next());
				station.setTestPlanId(planid);
				station.setCreateTime(new Date());
				station.setId(null);
				station.setState(MainTenanceConstant.ENTERING_N);
				this.save(station);
			}
		}
	}
	

}
