package com.cabletech.linepatrol.maintenance.dao;

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
public class TestPlanLineDAO extends HibernateDao<TestPlanLine, String> {


	public TestPlanLine getTestPlanLineById(String id){
		TestPlanLine planLine = get(id);
		initObject(planLine);
		return planLine;
	}
	
	public void saveOrUpdateLine(TestPlanLine line){
		save(line);
	}
	
	/**
	 * 根据测试计划id查询测试计划中的备纤
	 * @param planid
	 * @return
	 */
	public List<TestPlanLine> getTestPlanLineByPlanId(String planid){
		String hql=" from TestPlanLine t where t.testPlanId='"+planid+"'";
		return (List<TestPlanLine>) this.getSession().createQuery(hql);
	}
	
	public TestPlanLine getTestPlanLineByPlanIdAndCableId(String planid,String cablelineId){
		String hql=" from TestPlanLine t where t.testPlanId='"+planid+"' and t.cablelineId='"+cablelineId+"'";
		return (TestPlanLine) this.getSession().createQuery(hql).uniqueResult();
	}
	
	
	/**
	 * 根据测试计划id删除测试计划中的备纤信息
	 * @param planid
	 */
	public void deleteLine(String planid){
		String sql="delete  from lp_test_plan_line line where line.test_plan_id='"+planid+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
	
	
	public void savePlanLine(Map<String,TestPlanLine> planLines,String planid){
		deleteLine(planid);
		if(planLines!=null && planLines.size()>0){
			Set set = planLines.keySet();
			Iterator ite = set.iterator();
			while(ite.hasNext()){
				TestPlanLine line = planLines.get(ite.next());
				line.setTestPlanId(planid);
				line.setId(null);
				line.setState(MainTenanceConstant.ENTERING_N);
				this.save(line);
			}
		}
	}
	

}
