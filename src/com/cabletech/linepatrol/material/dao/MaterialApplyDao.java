package com.cabletech.linepatrol.material.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.domain.MaterialApply;

@Repository
public class MaterialApplyDao extends HibernateDao<MaterialApply, String> {
	private static Logger logger = Logger.getLogger(MaterialApplyDao.class
			.getName());

	/**
	 * <br>
	 * 功能:获得指定用户的电话号码 <br>
	 * 参数:用户编号 <br>
	 * 返回:指定用户的电话号码
	 * */
	public String getSendPhone(String userid) {
		String sql = "select phone from userinfo where userid='" + userid + "'";
		List list = super.getJdbcTemplate().queryForList(sql, String.class);
		if (list != null && !list.isEmpty()) {
			return (String) list.get(0);
		} else {
			return "";
		}
	}

	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select apply.id,nvl(storage.id,'-1') as storage_id, ";
		sql += " apply.creator,apply.contractorid,apply.title,apply.state, ";
		sql += " to_char(apply.createdate,'yyyy-mm-dd hh24:mi:ss') as createtime, ";
		sql += " u.username,ci.contractorname,'' as flow_state,'0' as is_reader ";
		sql += " from lp_mt_new apply,lp_mt_storage storage, ";
		sql += " contractorinfo ci,userinfo u ";
		sql += " where apply.creator=u.userid and apply.contractorid=ci.contractorid ";
		sql += " and apply.id=storage.applyid(+) ";
		sql += condition;
		sql += " order by apply.createdate desc,id desc ";
		logger.info("Execute sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	public List queryFinishHandledList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select apply.id,nvl(storage.id,'-1') as storage_id, ";
		sql += " apply.creator,apply.contractorid,apply.title,apply.state, ";
		sql += " to_char(apply.createdate,'yyyy-mm-dd hh24:mi:ss') as createtime, ";
		sql += " u.username,ci.contractorname,'' as flow_state,'0' as is_reader, ";
		sql += " process.handled_task_name,process.task_out_come,process.id as history_id,process.operate_user_id ";
		sql += " from lp_mt_new apply,lp_mt_storage storage,process_history_info process, ";
		sql += " contractorinfo ci,userinfo u ";
		sql += " where apply.creator=u.userid and apply.contractorid=ci.contractorid ";
		sql += " and process.object_id=apply.id ";
		sql += " and process.object_type='" + ModuleCatalog.MATERIAL + "' ";
		// sql += " and handled_task_id is not null ";
		// sql += " and exists( ";
		// sql += " select h.id from process_history_info h,jbpm4_task task ";
		// sql +=
		// " where h.execution_id=task.execution_id_ and h.id=process.id ";
		// sql += " ) ";
		sql += " and apply.id=storage.applyid(+) ";
		sql += condition;
		sql += " order by history_id desc,apply.createdate desc,id desc ";
		logger.info("Execute sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	/**
	 * //去除已阅和转审给抄送人和自己为抄送人的情况
	 * @param approverId  转审人
	 * @param userId	     当前用户
	 * @param objectId	  	申请id
	 * @param objectType	申请类型
	 * @return
	 */
	public String getReaderByCondition(String approverId,String userId,String objectId, String objectType) {
		String readers="";
		String sql="select APPROVER_ID approverId FROM LP_REPLY_APPROVER WHERE FINISH_READED<>'1' AND APPROVER_ID NOT IN ('"+approverId+"','"+userId+"') AND OBJECT_ID ='"+objectId+"' AND OBJECT_TYPE='"+objectType+"' AND APPROVER_TYPE='03'";
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		list=this.getJdbcTemplate().queryForList(sql);
		for(Map<String,String> reader:list){
			readers+=reader.get("approverId")+",";
		}
		if(!"".equals(readers)){
			readers=readers.substring(0, readers.length()-1);
		}
		return readers;
	}
}
