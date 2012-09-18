package com.cabletech.linepatrol.dispatchtask.dao;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.dispatchtask.beans.DispatchExamBean;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTask;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;

/**
 * 任务派单考核操作类
 * 
 * @author liusq
 * 
 */
@Repository
public class DispatchExamDao extends HibernateDao<DispatchTask, String> {

	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 通过查询条件查询考核的列表，包括未考核与已考核的
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<DynaBean> getExamListByCondition(DispatchExamBean bean,
			UserInfo userInfo, String examFlag) {
		StringBuffer sb = new StringBuffer("");
		sb.append("select d.id,c.id check_id,r.id reply_id,d.sendtopic,to_char(d.sendtime,'yyyy-mm-dd hh24:mi:ss') sendtime,");
		sb.append("d.sendtype,dic.lable as sendtypelabel,d.senduserid,");
		sb.append("d.senddeptid,con.contractorname,u.username,acceptdept.deptid ");
		sb.append("from lp_sendtask d,lp_sendtask_acceptdept acceptdept,");
		sb.append("lp_sendtaskreply r,lp_sendtask_check c,deptinfo dept,");
		sb.append("dictionary_formitem dic,contractorinfo con,userinfo u ");
		sb.append("where d.id=acceptdept.sendtaskid and acceptdept.id=r.sendaccid ");
		sb.append("and r.id=c.replyid and d.senddeptid=dept.deptid ");
		sb.append("and d.sendtype=dic.code and acceptdept.deptid=con.contractorid ");
		sb.append("and (dic.assortment_id='dispatch_task' or dic.assortment_id='dispatch_task_con') ");
		sb.append("and u.userid=d.senduserid and c.validateresult='");
		sb.append(DispatchTaskConstant.PASSED);
		sb.append("' ");
		//未考核
		if(StringUtils.equals(examFlag, DispatchTaskConstant.UNEXAM)){
			sb.append("and (c.exam_flag is null or c.exam_flag<>'");
			sb.append(DispatchTaskConstant.EXAMED);
			sb.append("') ");
		}else{
			//已考核
			sb.append("and c.exam_flag='");
			sb.append(DispatchTaskConstant.EXAMED);
			sb.append("' ");
		}
		sb.append("and d.senduserid='");
		sb.append(userInfo.getUserID());
		sb.append("' ");
		// 名称
		if (StringUtils.isNotBlank(bean.getSendtopic())) {
			sb.append("and d.sendtopic like '%");
			sb.append(bean.getSendtopic());
			sb.append("%' ");
		}
		sb.append("order by c.id desc");
		logger.info("查询语句：" + sb.toString());
		return super.getJdbcTemplate().queryForBeans(sb.toString());
	}
}
