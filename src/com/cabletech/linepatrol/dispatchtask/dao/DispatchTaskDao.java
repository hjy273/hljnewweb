package com.cabletech.linepatrol.dispatchtask.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTask;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;

@Repository
public class DispatchTaskDao extends HibernateDao<DispatchTask, String> {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public String getDepartTableSql() {
		String sql = " ( ";
		sql += " select deptid as departid,deptname as departname,regionid ";
		sql += " from deptinfo ";
		sql += " union ";
		sql += " select contractorid as departid,contractorname as departname,regionid ";
		sql += " from contractorinfo ";
		sql += " ) ";
		return sql;
	}

	public void saveDispatchTask(DispatchTask dispatchTask) {
		super.initObject(dispatchTask);
		super.getSession().save(dispatchTask);
	}

	public void updateDispatchTask(DispatchTask dispatchTask) {
		super.initObject(dispatchTask);
		super.getSession().saveOrUpdate(dispatchTask);
	}

	public List queryForList(String condition) {
		// TODO Auto-generated method stub
		// 查询派给代维单位的任务派单
		String sql = " select * from ( ";
		sql += " select d.id,d.serialnumber,d.sendtopic,d.sendtime,d.processterm, ";
		sql += " decode(d.workstate,null,'000','','000',d.workstate) as workstate,";
		sql += " d.sendtype,dic.lable as sendtypelabel,d.senduserid,d.senddeptid, ";
		sql += " acceptdept.id as subid,'-1' as flow_state,'0' as exist_reply,'-1' as reply_id,'0' as wait_refuse_confirm, ";
		sql += " abs(round(to_number(d.processterm - sysdate)*24,1)) as processterm_cal, ";
		sql += " decode(sign(round(to_number(d.processterm - sysdate)*24,1)),'-1','1','0') as is_out_time, ";
		sql += " senddept.departname as senddeptname,u.username as sendusername ";
		sql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept,dictionary_formitem dic, ";
		sql += " " + getDepartTableSql() + " senddept,userinfo u,"
				+ getDepartTableSql() + " di,region r ";
		sql += " where d.id=acceptdept.sendtaskid ";
		sql += " and acceptdept.deptid=di.departid and di.regionid=r.regionid ";
		sql += " and d.sendtype=dic.code ";
		sql += " and (dic.assortment_id='dispatch_task' or dic.assortment_id='dispatch_task_con') ";
		sql += " and d.senddeptid=senddept.departid and d.senduserid=u.userid ";
		sql += condition;
		sql += " ) order by sendtime desc,id desc ";
		logger.info("查询派单信息sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	public List queryForFinishHandledList(String condition) {
		// TODO Auto-generated method stub
		// 查询派给代维单位的任务派单
		String sql = " select * from ( ";
		sql += " select d.id,d.serialnumber,d.sendtopic,d.sendtime,d.processterm, ";
		sql += " decode(d.workstate,null,'000','','000',d.workstate) as workstate,";
		sql += " d.sendtype,dic.lable as sendtypelabel,d.senduserid, ";
		sql += " acceptdept.id as subid,'-1' as flow_state,'0' as exist_reply,'-1' as reply_id,'0' as wait_refuse_confirm, ";
		sql += " abs(round(to_number(d.processterm - sysdate)*24,1)) as processterm_cal, ";
		sql += " decode(sign(round(to_number(d.processterm - sysdate)*24,1)),'-1','1','0') as is_out_time, ";
		sql += " senddept.departname as senddeptname,u.username as sendusername, ";
		sql += " process.handled_task_name,process.task_out_come,process.id as history_id,process.operate_user_id ";
		sql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept,dictionary_formitem dic, ";
		sql += " " + getDepartTableSql() + " senddept,userinfo u,"
				+ getDepartTableSql()
				+ " di,region r,process_history_info process ";
		sql += " where d.id=acceptdept.sendtaskid ";
		sql += " and acceptdept.deptid=di.departid and di.regionid=r.regionid ";
		sql += " and d.sendtype=dic.code ";
		sql += " and (dic.assortment_id='dispatch_task' or dic.assortment_id='dispatch_task_con') ";
		sql += " and process.object_id=acceptdept.id ";
		// sql += " and handled_task_id is not null ";
		// sql += " and exists( ";
		// sql += " select h.id from process_history_info h,jbpm4_task task ";
		// sql +=
		// " where h.execution_id=task.execution_id_ and h.id=process.id ";
		// sql += " ) ";
		sql += " and process.object_type='" + ModuleCatalog.SENDTASK + "'  ";
		sql += " and d.senddeptid=senddept.departid and d.senduserid=u.userid ";
		sql += condition;
		sql += " ) order by history_id desc,sendtime desc,id desc ";
		logger.info("查询派单信息sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	public DispatchTask viewDispatchTask(String dispatchId) {
		// TODO Auto-generated method stub
		DispatchTask dispatchTask = super.get(dispatchId);
		super.initObject(dispatchTask);
		return dispatchTask;
	}

	/**
	 * 查询当前年的派单数量信息
	 * 
	 * @return
	 */
//	public Integer getDispatchTaskNumber() {
//		StringBuffer buf = new StringBuffer();
//		// 执行根据当前用户查询当前月份的故障派单数量信息
//		buf.append(" select count(*) num ");
//		buf.append(" from lp_sendtask t,userinfo u ");
//		buf.append(" where u.userid=t.senduserid ");
//		buf
//				.append(" and to_char(t.sendtime,'yyyy') like to_char(sysdate,'yyyy')");
//		buf.append(" and u.deptid='" + userinfo.getDeptID() + "'");
//		logger.info("查询当前年的派单数量信息:" + buf.toString());
//		int num = super.getJdbcTemplate().queryForInt(buf.toString());
//		return num + 1;
//	}
	public List<DispatchTask> getDispatchTasks4Dept(String deptid){
		String hql = "from DispatchTask where to_char(sendtime,'yyyy') = to_char(sysdate,'yyyy') and senddeptid=?";
		return super.find(hql, deptid);
	}

	/**
	 * 根据派单编号查询是否是所有的部门都已经完成反馈验证
	 * 
	 * @param dispatchId
	 * @return
	 */
	public boolean allPassed(String dispatchId) {
		// TODO Auto-generated method stub
		String allAcceptDeptNumSql = " select count(acceptdept.id) ";
		allAcceptDeptNumSql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept ";
		allAcceptDeptNumSql += " where d.id=acceptdept.sendtaskid and acceptdept.sign_in_id is null ";
		allAcceptDeptNumSql += " and d.id='" + dispatchId + "' ";
		int allAcceptDeptNum = super.getJdbcTemplate().queryForInt(
				allAcceptDeptNumSql);

		String refuseDeptNumSql = " select count(acceptdept.id) ";
		refuseDeptNumSql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept, ";
		refuseDeptNumSql += " lp_sendtaskendorse s,lp_sendtask_refuse r ";
		refuseDeptNumSql += " where d.id=acceptdept.sendtaskid and acceptdept.id=s.sendaccid ";
		refuseDeptNumSql += " and s.id=r.signinid ";
		refuseDeptNumSql += " and s.result='"
				+ DispatchTaskConstant.REFUSE_ACTION + "' ";
		refuseDeptNumSql += " and r.confirmresult='"
				+ DispatchTaskConstant.PASSED + "' ";
		refuseDeptNumSql += " and d.id='" + dispatchId + "' ";
		int refuseDeptNum = super.getJdbcTemplate().queryForInt(
				refuseDeptNumSql);

		String checkPassedDeptNumSql = " select count(acceptdept.id) ";
		checkPassedDeptNumSql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept, ";
		checkPassedDeptNumSql += " lp_sendtaskreply r,lp_sendtask_check c ";
		checkPassedDeptNumSql += " where d.id=acceptdept.sendtaskid and acceptdept.id=r.sendaccid ";
		checkPassedDeptNumSql += " and r.id=c.replyid ";
		checkPassedDeptNumSql += " and c.validateresult='"
				+ DispatchTaskConstant.PASSED + "' ";
		checkPassedDeptNumSql += " and d.id='" + dispatchId + "' ";
		int checkPassedDeptNum = super.getJdbcTemplate().queryForInt(
				checkPassedDeptNumSql);

		if (allAcceptDeptNum == checkPassedDeptNum + refuseDeptNum) {
			return true;
		}
		return false;
	}
}
