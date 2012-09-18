package com.cabletech.linepatrol.commons.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.module.ReplyApprover;

@Repository
public class ReplyApproverDAO extends HibernateDao<ReplyApprover, String> {

	/**
	 * 批量保存选择的审核人员信息
	 * 
	 * @param list
	 */
	public void saveList(List<ReplyApprover> list) {
		for (int i = 0; list != null && i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 根据对象编号和对象类型删除审核人和抄送人
	 * 
	 * @param objectId
	 *            对象编号
	 * @param objectType
	 *            对象类型
	 */
	public void deleteList(String objectId, String objectType) {
		String hql = "delete from ReplyApprover r where r.objectId = ? and r.objectType = ?";
		this.batchExecute(hql, objectId, objectType);
	}

	/**
	 * 更新审核人转审
	 * 
	 * @param approvers
	 * @param objectid
	 * @param ObjectType
	 */
	public void updateApprover(String objectid, String ObjectType) {
		String hql = "from ReplyApprover r where r.objectId = ? and r.objectType = ?"
			+ " and r.approverType!=? and r.isTransferApproved=0";
		List<ReplyApprover> list = this.find(hql, objectid, ObjectType,
				CommonConstant.APPROVE_READ);
		if (list != null && list.size() > 0) {
			ReplyApprover approver = list.get(0);
			approver.setIsTransferApproved(CommonConstant.IS_TRANSFER);
			save(approver);
		}
	}

	/**
	 * 更新抄送人的已经批阅标记
	 * 
	 * @param approvers
	 * @param objectid
	 * @param ObjectType
	 */
	public void updateReader(String approverId, String objectid,
			String ObjectType) {
		String hql = "from ReplyApprover r where r.objectId = ? and r.objectType = ?"
			+ " and r.approverType=? and r.approverId=? and r.finishReaded='0'";
		List<ReplyApprover> list = this.find(hql, objectid, ObjectType,
				CommonConstant.APPROVE_READ, approverId);
		System.out.println(" updateReader======== "+approverId+"  objectid==  "+objectid+"   ObjectType=  "+ObjectType);
		System.out.println(list+"hql====== "+hql);
		if (list != null && list.size() > 0) {
			ReplyApprover approver = list.get(0);
			approver.setFinishReaded(CommonConstant.FINISH_READED);
			save(approver);
		}
	}

	/**
	 * 保存审核人或者抄送人
	 * 
	 * @param approvers
	 *            审核人或者抄送人组装字符串，用逗号分隔
	 * @param id
	 *            对象编号
	 * @param type
	 *            审核人或者抄送人身份
	 * @param ObjectType
	 *            对象类型
	 */
	public void saveApproverOrReader(String approvers, String id, String type,
			String ObjectType) {
		if (!type.equals(CommonConstant.APPROVE_READ)) {
			updateApprover(id, ObjectType);
		}
		if (StringUtils.isNotBlank(approvers)) {
			List<String> approverList = Arrays.asList(approvers.split(","));
			ReplyApprover ra = null;
			for (String s : approverList) {
				ra = new ReplyApprover();
				ra.setObjectId(id);
				ra.setObjectType(ObjectType);
				ra.setApproverId(s);
				ra.setApproverType(type);
				ra.setIsTransferApproved("0");
				save(ra);
			}
		}
	}

	/**
	 * 得到唯一的ReplyApprover对象，判断是不是抄送人
	 * 
	 * @param objectId
	 *            对象编号
	 * @param approverId
	 *            用户id
	 * @param objectType
	 *            对象类型
	 * @return 是抄送人返回true
	 */
	public boolean isReadOnly(String objectId, String approverId,
			String objectType) {
		String hql = "from ReplyApprover r where r.objectId = ? and r.approverId = ? and r.objectType = ?"
			+ " and r.isTransferApproved=0";
		logger.info("objectId===========================:" + objectId
				+ " approverId:" + approverId + " objectType:" + objectType);
		List<ReplyApprover> replys = find(hql, objectId, approverId, objectType);
		boolean flag = true;
		if (replys != null && !replys.isEmpty()) {
			for (ReplyApprover r : replys) {
				String approverType = r.getApproverType();
				if (approverType.equals(CommonConstant.APPROVE_MAN)
						|| approverType
						.equals(CommonConstant.APPROVE_TRANSFER_MAN)) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	
	
	/**
	 * 查询指定模块的审核人员信息
	 */
	public Set<String> getApprover(String objectId, String approverType,
			String objectType) {
		String hql = "from ReplyApprover r where r.objectId = ? and r.objectType = ? and r.approverType = ?";
		List<ReplyApprover> list = find(hql, objectId, objectType, approverType);
		Set<String> approvers = new HashSet<String>();
		for (ReplyApprover r : list) {
			approvers.add(r.getApproverId());
		}
		return approvers;
	}

	/**
	 * 查询审核人及抄送人信息
	 * 
	 * @param objectId
	 * @param objectType
	 * @return
	 */
	public List<ReplyApprover> getApprovers(String objectId, String objectType) {
		String hql = "from ReplyApprover r where r.objectId = ? and r.objectType = ? ";
		List<ReplyApprover> list = find(hql, objectId, objectType);
		return list;
	}

	/**
	 * 判断单子是否已经批阅
	 * @param objectId
	 * @param approverId
	 * @param objectType
	 * @return
	 */
	public boolean isReaded(String objectId, String approverId,
			String objectType) {
		String hql = "from ReplyApprover r where r.objectId = ? and r.approverId = ? and r.objectType = ?" +
				" and r.approverType='"+CommonConstant.APPROVE_READ+"'";
		 List<ReplyApprover> list =  find(hql,objectId,approverId,objectType);
		 if(list!=null && list.size()>0){
			 for(ReplyApprover app :list){
				 String readed = app.getFinishReaded();
				 if(!readed.equals(CommonConstant.FINISH_READED)){
					 return false;
				 }
			 }
		 }
		 return true;
	}

	/**
	 * 根据查询条件获取审核人信息列表
	 * 
	 * @param condition
	 * @return
	 */
	public List queryList(String condition) {
		String sql = "select u.username,approver.approver_id,approver_type,u.phone ";
		sql = sql + " from lp_reply_approver approver,userinfo u ";
		sql = sql + " where approver.approver_id=u.userid ";
		sql = sql + condition;
		sql = sql + " order by approver.id ";
		logger.info("Execute sql:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}
}
