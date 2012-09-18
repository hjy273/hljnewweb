package com.cabletech.linepatrol.maintenance.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestProblem;
import com.cabletech.linepatrol.maintenance.service.MainTenanceConstant;


@Repository
public class TestProblemDAO extends HibernateDao<TestProblem, String> {


	public List<TestProblem> getProblemsByPlanIdAndLineId(String planId,String lineid){
		String hql = " from TestProblem t where t.testPlanId='"+planId+"' and t.testCablelineId='"+lineid+"'";
		return  this.getHibernateTemplate().find(hql);
	}

	public void deleteProblemsByPlanIdAndLineId(String planId,String lineid){
		String sql = " delete lp_test_problem t where t.test_plan_id='"+planId+"' and t.test_cableline_id='"+lineid+"'";
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 查询未解决的光缆信息
	 * @param u
	 * @return
	 */
	public List getProblems(UserInfo u,String state){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		String regionid = u.getRegionid();
		List lists = this.getConstractorByDeptId(regionid);
		String cons = this.listTOString(lists);
		sb.append(" select pro.id,plan.test_plan_name,r.segmentname,pro.problem_description,");
		sb.append(" pro.tester,decode(pro.problem_state,'0','未解决','1','已解决') state");
		sb.append(",plan.create_time");
		sb.append(" from lp_test_problem pro,repeatersection r,lp_test_plan plan");
		sb.append(" where pro.test_cableline_id=r.kid and plan.id=pro.test_plan_id");
		if(u.getDeptype().equals("1")){
			sb.append(" and plan.contractor_id in("+cons+")");
			//values.add(cons);
		}else{
			sb.append(" and plan.contractor_id=?");
			values.add(u.getDeptID());
		}
		
		sb.append(" and pro.problem_state=?");
		//values.add(MainTenanceConstant.PROBLEM_STATE_N);
		values.add(state);
		sb.append(" order by create_time desc,test_plan_name,segmentname");
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}



	/**
	 *查询光缆问题的数量
	 * @param planid 测试计划id
	 * @return
	 */
	public Integer getProblemNumByPlanId(String planid){
		StringBuffer sb = new StringBuffer(); 
		sb.append(" select count(*) from lp_test_problem pro,lp_test_plan p  ");
		sb.append(" where pro.test_plan_id=p.id");
		sb.append(" and p.id='"+planid+"'");
		int num = this.getJdbcTemplate().queryForInt(sb.toString());
		return num;
	}



	/**
	 *查询已经解决的光缆问题的数量
	 * @param planid
	 * @return
	 */
	public Integer getSolveProblemNum(String planid){
		StringBuffer sb = new StringBuffer(); 
		sb.append(" select count(*) from lp_test_problem pro,lp_test_plan p  ");
		sb.append(" where pro.test_plan_id=p.id ");
		sb.append(" and pro.problem_state="+MainTenanceConstant.PROBLEM_STATE_Y);
		sb.append(" and p.id='"+planid+"'");
		int num = this.getJdbcTemplate().queryForInt(sb.toString());
		return num;
	}

	/**
	 * 移动查询代维
	 * @param deptid
	 * @return
	 */
	public List getConstractorByDeptId(String regionid){
		String sql = "select contractorid from contractorinfo where regionid='"+regionid+"'";
		return this.getJdbcTemplate().queryForBeans(sql,null);
	}

	public String listTOString(List list){
		StringBuilder strs = new StringBuilder();
		if(list!=null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String conid = (String) bean.get("contractorid");
				strs.append("'"+conid+"'");
				if(i<list.size()-1){
					strs.append(",");
				}
				
			}
		}
		return strs.toString();
	}



}
