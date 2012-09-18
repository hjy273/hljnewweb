package com.cabletech.linepatrol.maintenance.dao;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Point;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.maintenance.beans.TestPlanQueryStatBean;
import com.cabletech.linepatrol.maintenance.module.TestPlan;
import com.cabletech.linepatrol.maintenance.module.TestPlanLine;
import com.cabletech.linepatrol.maintenance.module.TestPlanStation;
import com.cabletech.linepatrol.maintenance.service.MainTenanceConstant;
import com.cabletech.linepatrol.maintenance.workflow.MaintenanceWorkflowBO;


@Repository
public class TestPlanDAO extends HibernateDao<TestPlan, String> {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	
	private static final String PLAN_CANCEL = "999";


	public TestPlan getTestPlanById(String id){
		TestPlan plan = get(id);
		initObject(plan);
		return plan;
	}

	/**
	 * 根据年计划id查询制定的中继段
	 * @param planId
	 * @return
	 */
	public List getPlanLinesByPlanId(String planId){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select line.id lineid,line.test_plan_id, r.segmentname,");
		sb.append("line.cableline_test_port,");
		sb.append("to_char(r.finishtime,'yyyy/MM') finishtime,");
		sb.append(" to_char(line.test_plan_date,'yyyy-mm-dd') plan_date,");
		sb.append(" line.test_remark,line.state,line.test_man username,");
		sb.append(" decode(line.state,'0','未录入','1','已录入','暂存') as iswritestate, ");
		sb.append("  decode((select count(*) from lp_test_chip_data chip,lp_test_cable_data cable where ");
		sb.append(" chip.test_cable_data_id=cable.id and cable.test_cableline_id=line.cableline_id");
		sb.append(" and cable.test_plan_id=? and chip.is_eligible='0'),'0','合格','不合格' ) as isok ");
		values.add(planId);
		sb.append(" from  lp_test_plan_line line,repeatersection r ");
		sb.append(" where line.cableline_id=r.kid ");
		sb.append(" and line.test_plan_id=?");
		values.add(planId);
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 *查询备纤数据是否录入完
	 * @param planId
	 * @return
	 */
	public boolean judgeAllEnteringLine(String planId){
		StringBuffer sb = new StringBuffer(); 
		sb.append(" select count(*) num ");
		sb.append(" from lp_test_plan_line l ");
		sb.append(" where l.state=0 and l.test_plan_id='"+planId+"'");
		int datanum = this.getJdbcTemplate().queryForInt(sb.toString());
		if(datanum==0){
			return true;
		}
		return false;
	}

	/**
	 * 根据年计划id查询制定的基站
	 * @param planId
	 * @return
	 */
	public List getPlanStationsByPlanId(String planId){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select station.id stationid,station.test_plan_id,");
		sb.append("point.pointname,station.test_remark,station.state,station.test_plan_man username,");
		sb.append(" to_char(station.test_plan_time,'yyyy-mm-dd') plan_date, ");
		sb.append(" decode(station.state,'0','未录入','1','已录入','暂存') as iswritestate ");
		sb.append(" from lp_test_plan_station station,pointinfo point");
		sb.append(" where station.test_station_id=point.pointid  ");
		sb.append(" and station.test_plan_id=?");
		values.add(planId);
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 *查询基站数据是否录入完
	 * @param planId
	 * @return
	 */
	public boolean judgeAllEnteringStation(String planId){
		StringBuffer sb = new StringBuffer(); 
		sb.append(" select count(*) num ");
		sb.append(" from lp_test_plan_station s ");
		sb.append(" where s.state=0 and s.test_plan_id='"+planId+"'");
		int datanum = this.getJdbcTemplate().queryForInt(sb.toString());
		if(datanum==0){
			return true;
		}
		return false;
	}

	/**
	 * 根据年计划id查询制定的中继段
	 * @param planId
	 * @return
	 */
	public List<TestPlanLine>  getPlanLineByPlanId(String planId){
		String hql = " from TestPlanLine t where t.testPlanId='"+planId+"'";
		List<TestPlanLine> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	/**
	 * 根据年计划id查询制定的基站
	 * @param planId
	 * @return
	 */
	public 	List<TestPlanStation> getPlanStationByPlanId(String planId){
		String hql = " from TestPlanStation t where t.testPlanId='"+planId+"'";
		List<TestPlanStation> list = this.getHibernateTemplate().find(hql);
		return list;

	}

	/**
	 * 查询待办工作
	 * @return
	 */
	public List getWaitWork(String condition){
		List<Object> values = new ArrayList<Object>();
		StringBuffer selsql = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		selsql.append("select p.creator_id,p.id,p.test_plan_name,(to_char(p.test_begin_date,'yyyy-mm-dd')");
		selsql.append(" || '至' || to_char(p.test_end_date,'yyyy-mm-dd')) as plantime,'' as flow_state,");
		selsql.append(" decode(p.test_plan_type,'1','备纤测试','2','接地电阻测试') as plantype,p.test_state,");
		selsql.append(" decode(p.test_state,'00','未提交','0','计划待审核','1','修改测试计划','20','录入数据',");
		selsql.append(" '21','未录完数据','30','录入待审核','31','录入审核不通过','40','待考核','999','已取消') as state,");
		selsql.append(" p.test_plan_type type,p.test_state planstate,'false' as isread");
		selsql.append(",c.contractorname,create_time");
		sb.append(selsql.toString());
		sb.append(", (select count(*) from lp_test_plan_line line where line.test_plan_id=p.id) plannum,");
		sb.append("(select count(*) from lp_test_plan_line line where line.test_plan_id=p.id and line.state=?) testnum");
		values.add(MainTenanceConstant.ENTERING_Y);
		sb.append(" from lp_test_plan p,contractorinfo c ");
		sb.append(" where c.contractorid=p.contractor_id ");
		sb.append(" and p.test_plan_type=?");
		values.add(MainTenanceConstant.LINE_TEST);
		sb.append(" union ");
		sb.append(selsql.toString());
		sb.append(", (select count(*) from lp_test_plan_station s where s.test_plan_id=p.id) plannum,");
		sb.append(" (select count(*) from lp_test_plan_station s where s.test_plan_id=p.id and s.state=?) testnum ");
		values.add(MainTenanceConstant.ENTERING_Y);
		sb.append(" from lp_test_plan p,contractorinfo c ");
		sb.append(" where c.contractorid=p.contractor_id ");
		sb.append(" and p.test_plan_type=?");
		values.add(MainTenanceConstant.STATION_TEST);
		sb.append(" order by create_time desc");
		System.out.println("查询待办工作sql:"+sb.toString());
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}


	/**
	 *查询上次测试中继段的个数
	 * @param troubleid
	 * @return
	 */
	public Integer getTestCableNum(String conid){
		StringBuffer sb = new StringBuffer(); 
		sb.append(" select count(*) num ");
		sb.append(" from lp_test_plan p,lp_test_plan_line line");	
		sb.append(" where line.test_plan_id=p.id and p.contractor_id='"+conid+"'");
		sb.append(" and to_char(p.create_time,'yyyy') like to_char(sysdate,'yyyy')");
		int num = this.getJdbcTemplate().queryForInt(sb.toString());
		return num;
	}

	/**
	 * 该代维单位负责的中继段的总数量
	 * @return
	 */
	public Integer getCableNumber(UserInfo userinfo,String begindate) {
		String time = "2009";
		if(begindate!=null){
			time = time.substring(0,4);
		}
		StringBuffer buf = new StringBuffer();
		buf.append(" select count(*) num ");
		buf.append(" from repeatersection r ");
		buf.append(" where r.maintenance_id='"+userinfo.getDeptID()+"'");
		buf.append(" and r.ischeckout='y' and scrapstate='false'");
		buf.append(" and r.finishtime<to_date('"+time+"', 'yyyy')");
		logger.info("查询代维单位负责的中继段的总数量:"+buf.toString());
		int num = this.getJdbcTemplate().queryForInt(buf.toString());
		return num;
	}

	/**
	 * 查询中继段信息
	 * @return
	 */
	public List getCable(UserInfo userinfo,String level,String cableName,String begindate){
		String time = "2009";
		if(begindate!=null){
			time = begindate.substring(0,4);
		}
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select r.kid,r.segmentname,r.pointa, r.pointz ");
		sb.append(" from repeatersection r ");
		sb.append("  where r.maintenance_id=?");
		values.add(userinfo.getDeptID());
		if(level!=null && !"".equals(level)){
			sb.append("  and r.cable_level=?");
			values.add(level);
		}
		if(cableName!=null && !"".equals(cableName)){
			sb.append("  and r.segmentname like '%"+cableName+"%'");
		}
		sb.append(" and r.kid not in(select pb.test_cableline_id ");
		sb.append("from lp_test_problem pb where pb.problem_state=?)");
		values.add(MainTenanceConstant.PROBLEM_STATE_N);
		sb.append(" and r.finishtime<to_date('"+time+"', 'yyyy')");
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 查询光缆年计划测试次数
	 * @param trunkid
	 * @return
	 */
	public int getTestNumYearPlan(UserInfo user,String trunkid,String testBeginDate){
		Date time = DateUtil.parseDate(testBeginDate, "yyyy");
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select  pt.apply_num  ");
		sb.append(" from  lp_test_year_plan yp, lp_test_year_plan_task pt, ");
		sb.append(" lp_test_year_plan_trunk_detail td");
		sb.append(" where yp.id=pt.year_plan_id and pt.id = td.yeartask_id");
		sb.append(" and td.trunkid=? and yp.year='"+sdf.format(time)+"'");
		values.add(trunkid);
		sb.append(" and yp.contractor_id=?");
		values.add(user.getDeptID());
		List list =   this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list!=null && list.size()>0){
			BasicDynaBean bean = (BasicDynaBean) list.get(0);
			Object num = bean.get("apply_num");
			if(num!=null){
				return Integer.parseInt(num.toString());			}
		}
		return 0;
	}

	/**
	 * 今年已经做多少次
	 * @param trunkid
	 * @param time
	 * @return
	 */
	public int getFinishedNum(UserInfo user,String trunkid,String testBeginDate){
		Date time = DateUtil.parseDate(testBeginDate, "yyyy");
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) num  ");
		sb.append(" from lp_test_plan lp ,lp_test_plan_line pl ");
		sb.append(" where lp.id=pl.test_plan_id and pl.cableline_id=? ");
		values.add(trunkid);
		sb.append(" and lp.contractor_id=? and to_char(pl.test_plan_date,'yyyy')='"+sdf.format(time)+"'");
		values.add(user.getDeptID());
		List list =   this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list!=null && list.size()>0){
			BasicDynaBean bean = (BasicDynaBean) list.get(0);
			Object num = bean.get("num");
			if(num!=null){//BigDecimal b;b.intValue();
				return Integer.parseInt(num.toString());			}
		}
		return 0;
	}




	/**
	 *查询上次测试基站的个数
	 * @param troubleid
	 * @return
	 */
	public Integer getTestStationNum(String conid){
		StringBuffer sb = new StringBuffer(); 
		sb.append(" select count(*) num ");
		sb.append(" from lp_test_plan p,lp_test_plan_station station");	
		sb.append(" where station.test_plan_id=p.id and p.contractor_id='"+conid+"'");
		sb.append(" and to_char(p.create_time,'yyyy') like to_char(sysdate,'yyyy')");
		int num = this.getJdbcTemplate().queryForInt(sb.toString());
		return num;
	}


	/**
	 * 查询基站信息
	 * @return
	 */
	public List getStation(UserInfo user){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select  distinct p.pointid,p.pointname  ");
		sb.append(" from userinfo u,patrolmaninfo patrol,sublineinfo s,pointinfo p");
		sb.append(" where u.deptid=patrol.parentid and s.patrolid=patrol.patrolid");
		sb.append(" and p.sublineid=s.sublineid and p.pointtype='5' and u.deptid=?");
		values.add(user.getDeptID());
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 得到计划测试人员
	 * @param user
	 * @return
	 */
	public List getUsers(UserInfo user,String userName){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select c.id,c.name from contractorperson c");
		sb.append(" where c.contractorid=?");
		values.add(user.getDeptID());
		if(userName!=null && !"".equals(userName.trim())){
			sb.append(" and c.name like '%"+userName+"%'");
		}
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	public Point getPoint(String id){
		return (Point) this.getHibernateTemplate().get(Point.class, id);
	}

	/**
	 * 查询待考核的计划列表
	 * @return
	 */
	public List getWaitExams(String regionid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select p.id,p.test_plan_name,(to_char(p.test_begin_date,'yyyy-mm-dd')");
		sb.append(" || '至' || to_char(p.test_end_date,'yyyy-mm-dd')) as plantime,");
		sb.append(" decode(p.test_plan_type,'1','备纤测试','2','接地电阻测试','3','金属护套绝缘电阻测试') as plantype,");
		sb.append(" '待考核' as state,p.test_plan_type type");
		sb.append(" ,p.test_state planstate,c.contractorname");
		sb.append(" from lp_test_plan p ,contractorinfo c");
		sb.append(" where p.test_state=? and  c.contractorid=p.contractor_id ");
		values.add(MainTenanceConstant.WAIT_EXAM);
		sb.append(" and p.regionid=?");
		values.add(regionid);
		sb.append(" order by create_time desc");
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}



	/**
	 * 查询一个计划详细信息
	 * @return
	 */
	public BasicDynaBean getPlanInfo(String planid){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select p.id,p.contractor_id,p.test_plan_name,(to_char(p.test_begin_date,'yyyy-mm-dd')");
		sb.append(" || '至' || to_char(p.test_end_date,'yyyy-mm-dd')) as plantime,");
		sb.append(" decode(p.test_plan_type,'1','备纤测试','2','接地电阻测试','3','金属护套绝缘电阻测试') as plantype,");
		sb.append(" p.test_plan_remark, u.username,c.contractorname ");
		sb.append(" from lp_test_plan p ,contractorinfo c ,userinfo u  ");
		sb.append(" where p.contractor_id=c.contractorid and p.creator_id=u.userid");
		sb.append(" and p.id=?");
		values.add(planid);
		List list = this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list!=null && list.size()>0){
			return (BasicDynaBean) list.get(0);
		}
		return null;
	}

	/**
	 * 根据用户查询代维
	 * @param user
	 * @return
	 */
	public List<Contractor> getContractors(UserInfo user){
		String hql = " from Contractor c  where c.regionid='"+user.getRegionid()+"'";
		return this.getHibernateTemplate().find(hql);
	}

	/**
	 * 查询审核历史
	 * @param id 计划id或者是录入数据id
	 * @param type 判断是测试计划的还是录入数据的
	 * @return
	 */
	public List getApproveHistorys(String id,String type){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select appinfo.id,u.username,");
		sb.append(" to_char(approve_time,'yyyy-mm-dd hh24:mi:ss') approve_time,approve_remark,");
		sb.append(" decode(approve_result,'0','不通过','1','通过','2','转审') AS approve_result");
		sb.append(" from lp_approve_info appinfo,userinfo u");
		sb.append(" where appinfo.approver_id=u.userid and appinfo.object_id=?");
		values.add(id);
		sb.append(" and object_type=?");
		if(type.equals("plan")){
			values.add(MainTenanceConstant.LP_TEST_PLAN);
		}
		if(type.equals("data")){
			values.add(MainTenanceConstant.LP_TEST_DATA);
		}
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 抄送人查看单子
	 * @param user
	 * @param planid
	 */
	public List getPlanByReads(UserInfo user,String planid ){
		String userid = user.getUserID();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select distinct p.id ");
		sb.append(" from  lp_test_plan p,lp_reply_approver a ");
		sb.append(" where   p.id=?");
		values.add(planid);
		sb.append(" and a.object_id=p.id and a.approver_id=?");
		values.add(userid);
		sb.append(" and a.object_type=? and finish_readed !=?");
		values.add(MainTenanceConstant.LP_TEST_PLAN);
		values.add(CommonConstant.FINISH_READED);
		sb.append(" and a.approver_type=?");
		values.add(CommonConstant.APPROVE_READ);
		//	logger.info("抄送人查看单子s=============== "+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 抄送人查看单子
	 * @param user
	 * @param dataid
	 */
	public List getDataByReads(UserInfo user,String dataid ){
		String userid = user.getUserID();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select distinct p.id ");
		sb.append(" from  lp_test_data p,lp_reply_approver a ");
		sb.append(" where p.id=?");
		values.add(dataid);
		sb.append(" and a.object_id=p.id and a.approver_id=?");
		values.add(userid);
		sb.append(" and a.object_type=? and finish_readed !=?");
		values.add(MainTenanceConstant.LP_TEST_DATA);
		values.add(CommonConstant.FINISH_READED);
		sb.append(" and a.approver_type=?");
		values.add(CommonConstant.APPROVE_READ);
		//	logger.info("抄送人查看单子s=============== "+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}


	/**
	 * 查询已办
	 * @param user
	 * @return
	 */
	public List getHandeledWorks(UserInfo user,String condition,String selcondition){
		String userid =user.getUserID();
		List<Object> values = new ArrayList<Object>();
		StringBuffer selsql = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		selsql.append("select distinct p.creator_id,p.id,p.test_plan_name,(to_char(p.test_begin_date,'yyyy-mm-dd')");
		selsql.append(" || '至' || to_char(p.test_end_date,'yyyy-mm-dd')) as plantime,");
		selsql.append(" decode(p.test_plan_type,'1','备纤测试','2','接地电阻测试') as plantype,p.test_state,");
		selsql.append(" decode(p.test_state,'0','计划待审核','1','修改测试计划','20','录入数据',");
		selsql.append(" '21','未录完数据','30','录入待审核','31','录入审核不通过','40','待考核','999','已取消') as state,");
		selsql.append(" p.test_plan_type type,c.contractorname,create_time ");
		selsql.append(selcondition);
		
		sb.append(selsql.toString());
		sb.append(", (select count(*) from lp_test_plan_line line where line.test_plan_id=p.id) plannum,");
		sb.append("(select count(*) from lp_test_plan_line line where line.test_plan_id=p.id and line.state=?) testnum");
		values.add(MainTenanceConstant.ENTERING_Y);
		sb.append(" from lp_test_plan p,process_history_info process,contractorinfo c ");
		sb.append(" where process.object_id=p.id and c.contractorid=p.contractor_id ");
		sb.append(" and process.object_type=? and operate_user_id=?");
		values.add(ModuleCatalog.MAINTENANCE);
		values.add(userid);
		sb.append(condition);
		sb.append(" and p.test_plan_type=?");
		values.add(MainTenanceConstant.LINE_TEST);
		sb.append(" union ");
		sb.append(selsql.toString());
		sb.append(", (select count(*) from lp_test_plan_station s where s.test_plan_id=p.id) plannum,");
		sb.append(" (select count(*) from lp_test_plan_station s where s.test_plan_id=p.id and s.state=?) testnum ");
		values.add(MainTenanceConstant.ENTERING_Y);
		sb.append(" from lp_test_plan p,process_history_info process,contractorinfo c");
		sb.append(" where process.object_id=p.id  and c.contractorid=p.contractor_id");
		sb.append(" and process.object_type=? and operate_user_id=?");
		values.add(ModuleCatalog.MAINTENANCE);
		values.add(userid);
		sb.append(condition);
		sb.append(" and p.test_plan_type=?");
		values.add(MainTenanceConstant.STATION_TEST);
		/*if(selcondition!=null && !"".equals(selcondition)){
			sb.append(" order by handled_time desc");
		}*/
		sb.append(" order by create_time desc");
		System.out.println("查询已办sql:"+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}


	/**
	 * 根据条件查询或者统计年计划
	 * @return
	 */
	public List getTestPlans(TestPlanQueryStatBean bean,UserInfo user,String act){
		List<Object> values = new ArrayList<Object>();
		StringBuffer selsql = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		selsql.append("select p.creator_id,p.id,p.test_plan_name,(to_char(p.test_begin_date,'yyyy-mm-dd')");
		selsql.append(" || '至' || to_char(p.test_end_date,'yyyy-mm-dd')) as plantime,");
		selsql.append(" decode(p.test_plan_type,'1','备纤测试','2','接地电阻测试','3','金属护套绝缘电阻测试') as plantype,");
		selsql.append(" decode(p.test_state,'0','计划待审核','1','修改测试计划','20','录入数据',");
		selsql.append(" '21','未录完数据','30','录入待审核','31','录入审核不通过','40','待考核','50','完成','999','已取消') as state,");
		selsql.append(" p.test_plan_type type,p.test_state planstate,u.username,c.contractorname,");
		selsql.append(" (select count(*) from lp_test_problem pro where pro.test_plan_id=p.id) num,");
		selsql.append(" (select count(*) from lp_test_problem pro where pro.test_plan_id=p.id ");
		selsql.append(" and pro.problem_state='"+MainTenanceConstant.PROBLEM_STATE_Y+"') solvenum,create_time ");
		
		sb.append(selsql.toString());
		sb.append(", (select count(*) from lp_test_plan_line line where line.test_plan_id=p.id) plannum,");
		sb.append("(select count(*) from lp_test_plan_line line where line.test_plan_id=p.id and line.state=?) testnum");
		values.add(MainTenanceConstant.ENTERING_Y);
		sb.append(" from lp_test_plan p ,userinfo u,contractorinfo c ");
		sb.append(" where p.creator_id=u.userid and c.contractorid=p.contractor_id ");
		sb.append(" and p.test_plan_type=?");
		values.add(MainTenanceConstant.LINE_TEST);
		sb.append(getQueryCondition(bean,user,act));
		sb.append(" union ");
		sb.append(selsql.toString());
		sb.append(", (select count(*) from lp_test_plan_station s where s.test_plan_id=p.id) plannum,");
		sb.append(" (select count(*) from lp_test_plan_station s where s.test_plan_id=p.id and s.state=?) testnum");
		values.add(MainTenanceConstant.ENTERING_Y);
		sb.append(" from lp_test_plan p ,userinfo u,contractorinfo c ");
		sb.append(" where p.creator_id=u.userid and c.contractorid=p.contractor_id ");
		sb.append(" and p.test_plan_type=?");
		values.add(MainTenanceConstant.STATION_TEST);
		sb.append(getQueryCondition(bean,user,act));
		sb.append(" order by create_time desc");
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	
	public String getQueryCondition(TestPlanQueryStatBean bean,UserInfo user,String act){
		StringBuffer sb = new StringBuffer();
		String beginTimeStart = bean.getPlanBeginTimeStart();
		String beginTimeEnd = bean.getPlanBeginTimeEnd();
		String endTimeStart = bean.getPlanEndTimeStart();
		String endTimeEnd = bean.getPlanEndinTimeEnd();
		String planName = bean.getPlanName();
		String man = bean.getCreateMan();
		String[] planType = bean.getPlanType();
		String contractorId = bean.getContractorId();
		String deptype = user.getDeptype();
		if(deptype.equals("1")){
			sb.append(" and p.regionid='"+user.getRegionid()+"'");
		}
		if(deptype.equals("2")){
			sb.append(" and p.contractor_id='"+user.getDeptID()+"'");
		}
		if(beginTimeStart!=null && !"".equals(beginTimeStart)){
			sb.append(" and p.test_begin_date>=to_date('"+beginTimeStart+"','yyyy/mm/dd')");
		}
		if(beginTimeEnd!=null && !"".equals(beginTimeEnd)){
			sb.append(" and p.test_begin_date<=to_date('"+beginTimeEnd+"','yyyy/mm/dd')");
		}
		if(endTimeStart!=null && !"".equals(endTimeStart)){
			sb.append(" and p.test_end_date>=to_date('"+endTimeStart+"','yyyy/mm/dd')");
		}
		if(endTimeEnd!=null && !"".equals(endTimeEnd)){
			sb.append(" and p.test_end_date<=to_date('"+endTimeEnd+"','yyyy/mm/dd')");
		}
		if(planName!=null && !"".equals(planName)){
			sb.append(" and p.test_plan_name like '%"+planName+"%'");
		}
		//是否取消
		String testState = bean.getTestState();
		if(StringUtils.isNotBlank(testState)){
			if(StringUtils.equals(testState, PLAN_CANCEL)){
				sb.append(" and p.test_state='");
				sb.append(testState);
				sb.append("'");
			} else {
				sb.append(" and (p.test_state<>'");
				sb.append(PLAN_CANCEL);
				sb.append("' or p.test_state is null)");
			}
		}
		if(man!=null && !"".equals(man)){
			sb.append(" and u.username like '%"+man+"%'");
		}
		if(planType!=null&&planType.length>0){
			String sql=" and p.test_plan_type in("+planType[0];
			for(int i=1;i<planType.length;i++){
				sql+=","+planType[i];
			}
			sql+=")";
			sb.append(sql);
		}
		if(contractorId!=null && !"".equals(contractorId)){
			sb.append(" and p.contractor_id='"+contractorId+"'");
		}
		if(act!=null && act.equals("stat")){
			sb.append(" and p.test_state in ('"+MainTenanceConstant.WAIT_EXAM+"','"+MainTenanceConstant.TEST_PLAN_END+"')");
		}
		if (bean.getTaskStates() != null) {
			String[] taskStates = bean.getTaskStates();
			sb.append(" and exists( ");
			sb.append(" select dbid_ from jbpm4_task jbpm_task ");
			sb.append(" where jbpm_task.execution_id_='");
			sb.append(MaintenanceWorkflowBO.MAINTENANCE_WORKFLOW);
			sb.append(".'||p.id ");
			sb.append(" and ( ");
			for (int i = 0; i < taskStates.length; i++) {
				sb.append(" jbpm_task.name_='");
				sb.append(taskStates[i] + "' ");
				if (i < taskStates.length - 1) {
					sb.append(" or ");
				}
			}
			sb.append(" ) ");
			sb.append(" ) ");
		}
		return sb.toString();
	}

	/**
	 * 查询已经录入的光缆信息
	 * @param user
	 * @return
	 */
	public List getCables(UserInfo user,TestPlanQueryStatBean bean){
		String deptype = user.getDeptype();
		String contractorid = bean.getContractorId();
		String cableName = bean.getCableName();
		String cableLevel = bean.getCableLevel();
		String planEndTimeStart = bean.getPlanBeginTimeStart();
		String planEndTimeEnd = bean.getPlanEndTimeEnd();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct cabledata.id id, df.lable, rep.segmentname,");
		sb.append("c.contractorname,cabledata.test_cableline_id, ");
		sb.append("to_char(cabledata.fact_test_time,'yyyy/MM/dd') fact_test_time");
		sb.append(" from lp_test_plan_line line ,  lp_test_plan plan, ");
		sb.append(" lp_test_cable_data cabledata,repeatersection rep,contractorinfo c");
		sb.append(" ,dictionary_formitem df");
		sb.append(" where line.state='1' and plan.id=cabledata.test_plan_id ");
		sb.append(" and line.test_plan_id=plan.id");
		sb.append(" and rep.kid=cabledata.test_cableline_id and c.contractorid=rep.maintenance_id");
		sb.append(" and df.code=rep.cable_level and df.assortment_id='cabletype'");
		if(deptype.equals("2")){
			sb.append(" and c.contractorid=?");
			values.add(user.getDeptID());
		}
		if(contractorid!=null && !"".equals(contractorid)){
			sb.append(" and c.contractorid=?");
			values.add(contractorid);
		}
		if(cableLevel!=null && !"".equals(cableLevel)){
			sb.append(" and rep.cable_level=?");
			values.add(cableLevel);
		}
		if(cableName!=null && !"".equals(cableName)){
			sb.append(" and rep.segmentname like '%"+cableName+"%'");
		}
		if(planEndTimeStart!=null && !"".equals(planEndTimeStart)){
			sb.append(" and fact_test_time>=to_date(?,'yyyy/mm/dd')");
			values.add(planEndTimeStart);
		}
		if(planEndTimeEnd!=null && !"".equals(planEndTimeEnd)){
			sb.append(" and fact_test_time<=to_date(?,'yyyy/mm/dd')");
			values.add(planEndTimeEnd);
		}
		sb.append(" order by fact_test_time desc");
		System.out.println("查询已经录入的光缆信息sql:"+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

}
