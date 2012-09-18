package com.cabletech.linepatrol.maintenance.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.tags.module.Dictionary;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.maintenance.beans.TestYearPlanBean;
import com.cabletech.linepatrol.maintenance.module.TestYearPlan;
import com.cabletech.linepatrol.maintenance.module.TestYearPlanTask;
import com.cabletech.linepatrol.maintenance.service.MainTenanceConstant;
import com.cabletech.linepatrol.resource.model.RepeaterSection;


@Repository
public class TestYearPlanDAO extends HibernateDao<TestYearPlan, String> {

	/**
	 * 查询年计划是否存在
	 * @param year
	 * @param conid
	 * @param planid 年计划id
	 * @return
	 */
	public boolean judgeYearPlanIsHave(String year,String conid,String planid){
		String hql = "from TestYearPlan y where y.year='"+year+"' and contractorId='"+conid+"' ";
		if(planid!=null && !"".equals(planid)){
			hql+=" and y.id!='"+planid+"'";
		}
		List list = find(hql);
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}

	/**
	 * 查询待办年计划
	 * @param user
	 * @return
	 */
	public List getWaitHandelYearPlans(UserInfo user){
		String type = user.getDeptype();
		StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append(" select distinct p.id,c.contractorname,p.plan_name,p.year,");
		sb.append(" to_char(p.create_time,'yyyy/MM/dd') create_time,'' isread ");
		sb.append(" from lp_test_year_plan p ,contractorinfo c");
		if(type.equals("1")){
			/*	String regionid = user.getRegionid();
			List lists =getConstractorByDeptId(regionid);
			String cons =listTOString(lists);
			sb.append(" and p.contractor_id in("+cons+")");*/
			sb.append(",lp_reply_approver a ");
			sb.append(" where p.contractor_id=c.contractorid and a.object_type=?");
			values.add(MainTenanceConstant.LP_TEST_YEAR_PLAN);
			sb.append(" and a.object_id=p.id and  a.approver_id=?");
			values.add(user.getUserID());
			sb.append(" and a.is_transfer_approved=0 and finish_readed=0");
			//values.add(CommonConstant.IS_TRANSFER);
			sb.append(" and p.state=? ");
			values.add(MainTenanceConstant.YEAR_PLAN_WAIT_APPROVE);
		}
		if(type.equals("2")){
			sb.append(" where p.contractor_id=c.contractorid ");
			sb.append(" and p.contractor_id =?");
			values.add(user.getDeptID());
			sb.append(" and p.state=? ");
			values.add(MainTenanceConstant.YEAR_PLAN_APPROVE_NO);
		}
		sb.append(" order by create_time desc");
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 查询数据字典名称
	 * @param code
	 * @return
	 */
	public String getCableLevelNameByCode(String code){
		String hql = " from Dictionary d where d.code='" +code+"'"+
		" and d.assortmentId='cabletype' ";
		List list = find(hql);
		if(list!=null && list.size()>0){
			Dictionary d = (Dictionary) list.get(0);
			String lable = d.getLable();
			return lable;
		}
		return "";
	}

	/**
	 * 查询中继段名称
	 * @param code
	 * @return
	 */
	public String getTrunkNameByTrunkId(String id){
		String hql = " from RepeaterSection r where kid=?";
		List list = find(hql,id);
		if(list!=null && list.size()>0){
			RepeaterSection r = (RepeaterSection) list.get(0);
			String lable = r.getSegmentname();
			return lable;
		}
		return "";
	}

	/**
	 * 查询用户名称
	 * @param code
	 * @return
	 */
	public String getUserNameByUserId(String userid){
		String hql = " from UserInfo u where userID=?";
		List list = find(hql,userid);
		if(list!=null && list.size()>0){
			UserInfo r = (UserInfo) list.get(0);
			String userName = r.getUserName();
			return userName;
		}
		return "";
	}


	/**
	 * 查询代维单位名称
	 * @param code
	 * @return
	 */
	public String getConNameByContractId(String contracId){
		String hql = " from Contractor u where contractorID=?";
		List list = find(hql,contracId);
		if(list!=null && list.size()>0){
			Contractor c = (Contractor) list.get(0);
			String conName = c.getContractorName();
			return conName;
		}
		return "";
	}


	/**
	 * 删除年计划
	 * @param planId
	 */
	public void deleteYearPlan(String planId){
		String queryTaskHql = "from TestYearPlanTask t where t.yearPlanId=?";
		List list = find(queryTaskHql,planId);
		if(list!=null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				TestYearPlanTask task = (TestYearPlanTask)list.get(i);
				String taskid = task.getId();
				String delTrunkHql = "delete from TestYearPlanTrunk t where t.yearTaskId='"+taskid+"'";
				this.getSession().createQuery(delTrunkHql);
			}
		}
		//String delPlanHql = "delete from TestYearPlan y where y.id='"+planId+"'";
		String delTaskHql = "delete from TestYearPlanTask t where t.yearPlanId='"+planId+"'";
		this.getSession().createQuery(delTaskHql);
		this.delete(planId);
	}



	/**
	 * 查询年计划
	 * @param user
	 * @return
	 */
	public List getYearPlans(UserInfo user,TestYearPlanBean bean ){
		String type = user.getDeptype();
		StringBuffer seltotalnum = new StringBuffer();
		seltotalnum.append("((select count(*) from repeatersection rep ");
		seltotalnum.append(" where rep.ischeckout='y' and scrapstate='false' ");
		seltotalnum.append(" and rep.maintenance_id=p.contractor_id");
		seltotalnum.append(" and to_char(rep.finishtime,'yyyy')<p.year)*");
		seltotalnum.append(" p.test_times+(select count(*) from lp_test_year_plan_trunk_detail td,lp_test_year_plan_task task ");
		seltotalnum.append(" where td.yeartask_id=task.id and task.year_plan_id=p.id)*((");
		seltotalnum.append(" select t.apply_num from lp_test_year_plan_task t where t.year_plan_id=p.id)-p.test_times)) ");
		StringBuffer selworknum = new StringBuffer();
		selworknum.append(" (select count(*) from lp_test_plan plan,lp_test_plan_line pl");
		selworknum.append(" where to_char(plan.test_begin_date,'yyyy')=p.year and  pl.test_plan_id=plan.id ");
		selworknum.append(" and pl.state='1') ");
		StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append(" select distinct p.id,c.contractorname,p.plan_name,p.year,");
		sb.append(" to_char(p.create_time,'yyyy/MM/dd') create_time, ");
		sb.append(seltotalnum.toString()+" totalnum,");
		sb.append(selworknum.toString()+" worknum,");
		sb.append(seltotalnum.toString()+"-"+selworknum.toString()+" notestnum");
		sb.append(" from lp_test_year_plan p ,contractorinfo c");
		sb.append(" where p.contractor_id=c.contractorid ");
		if(type.equals("1")){
			String conid  = bean.getContractorId();
			if(conid!=null && !"".equals(conid)){
				sb.append(" and p.contractor_id =?");
				values.add(conid);
			}else{
				sb.append(" and p.contractor_id in(");
				sb.append(" select contractorid from contractorinfo where regionid=?)");
				values.add(user.getRegionid());
			}
		}
		if(type.equals("2")){
			sb.append(" and p.contractor_id =?");
			values.add(user.getDeptID());
		}
		String year  = bean.getYear();
		String planName = bean.getPlanName();
		if(year!=null && !"".equals(year)){
			sb.append(" and p.year =?");
			values.add(year);
		}
		if(planName!=null && !"".equals(planName)){
			sb.append(" and p.plan_name like '%"+planName+"%'");
		}
		sb.append(" order by create_time desc");
		System.out.println("查询年计划sql："+sb.toString());
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}


}
