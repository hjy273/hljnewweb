package com.cabletech.linepatrol.cut.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.cut.module.Cut;
import com.cabletech.linepatrol.overhaul.beans.OverHaulQueryBean;
import com.cabletech.sysmanage.domainobjects.ConPerson;

/**
 * 割接申请操作类
 * 
 * @author Administrator
 * 
 */
@Repository
public class CutDao extends HibernateDao<Cut, String> {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 获得移动人员
	 * @param regionId：区域ID
	 * @return：移动人员列表
	 */
	public List<UserInfo> getMobiles(String regionId){
		String hql = "from UserInfo userinfo where userinfo.deptype='1' and userinfo.id<>'sys' and userinfo.state is null and userinfo.regionid=?";
		return this.getHibernateTemplate().find(hql, regionId);
	}
	
	/**
	 * 获得代维人员列表
	 * @param regionId：区域ID
	 * @return：代维人员列表
	 */
	public List<ConPerson> getCons(String deptId){
		String hql = "from ConPerson conPerson where conPerson.contractorid=?";
		return this.getHibernateTemplate().find(hql, deptId);
	}
	
	/**
	 * 查询该用户当年割接数
	 * @param deptId：用户部门ID
	 * @return：数量
	 */
	public Integer getCutApplyNumber(String deptId) {
		StringBuffer buf = new StringBuffer();
		buf.append(" select count(*) num ");
		buf.append(" from lp_cut where 1=1 ");
		buf.append(" and to_char(apply_date,'yyyy')=to_char(sysdate,'yyyy')");
		buf.append(" and proposer in (select userID from userinfo where deptid='" + deptId + "')");
		logger.info("*************buf.toString():" + buf.toString());
		int num = this.getJdbcTemplate().queryForInt(buf.toString());
		return num + 1;
	}
	
	/**
	 * 修改割接未通过次数
	 * @param cutId：割接申请ID
	 */
	public void setUnapproveNumber(String cutId){
		Cut cut = this.findByUnique("id", cutId);
		int unapproveNumber = cut.getUnapproveNumber();
		cut.setUnapproveNumber(++unapproveNumber);
		this.save(cut);
	}
	
	/**
	 * 根据割接申请ID修改割接申请状态
	 * @param cutId：割接申请ID
	 * @param state：割接申请状态
	 */
	public void setCutState(String cutId, String state){
		Cut cut = this.findByUnique("id", cutId);
		cut.setState(state);
		this.save(cut);
	}
	
	/**
	 * 获得某人临时割接申请列表
	 * @param userId：用户ID
	 * @return：临时割接申请列表
	 */
	public List cutTempList(String userId){
		String sql = "select cut.id,cut.workorder_id,cut.cut_name,cut.charge_man,decode(cut_level,'1','一般割接','2','紧急割接','3','预割接') cut_level,to_char(cut.apply_date,'yyyy-mm-dd hh24:mi:ss') apply_date,cut.proposer from lp_cut cut,userinfo userinfo where cut.proposer=userinfo.userid and cut.apply_state='1' and userinfo.userid='" + userId +"'";
		sql += " order by cut.apply_date desc";
		logger.info("***********getList():" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * 添加割接申请信息
	 * 
	 * @param cut
	 * @return
	 */
	public Cut addCutApply(Cut cut) {
		this.save(cut);
		return cut;
	}
	
	/**
	 * 查看该用户的割接申请列表
	 * @param userId：用户ID
	 * @return：割接申请列表
	 */
	public List queryApplyList(String userId){
		String sql = "select cut.id ,cut.workorder_id,con.contractorname,decode(cut_level,'"+Cut.LEVEL_NORMAL+"','一般割接','"+Cut.LEVEL_EXIGENCE+"','紧急割接','"+Cut.LEVEL_PREPARE+"','预割接') cut_level,to_char(cut.begintime,'yyyy-mm-dd hh24:mi:ss') cut_begintime," +
			"to_char(cut.endtime,'yyyy-mm-dd hh24:mi:ss') cut_endtime,cut.builder cut_builder, cut.state state," +
			"decode(cut.state,'"+Cut.APPLY+"','割接申请','"+Cut.APPLY_NO_PASS+"','审批未通过','"+Cut.FEEDBACK+"','待反馈','"+Cut.FEEDBACK_APPROVE+"','反馈待审核','"+Cut.FEEDBACK_NO_PASS+"','反馈审核未通过','"+Cut.ACCEPTANCE+"','验收结算','"+Cut.ACCEPTANCE_APPROVE+"','验收结算审核','"+Cut.ACCEPTANCE_NO_PASS+"','验收结算审核未通过','"+Cut.EXAM+"','考核评估','"+Cut.FINISH+"','完成','"+Cut.CANCELED_STATE+"','取消') as cut_state" + 
			" from lp_cut cut,userinfo userinfo,contractorinfo con where cut.proposer=userinfo.userid and userinfo.deptid=con.contractorid and cut.proposer=? and cut.state='1'";
		logger.info("queryApplyList:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, userId);
	}
	
	/**
	 * 获得移动人员列表
	 * @param regionId
	 * @return
	 */
	public List<UserInfo> getApproves(String regionId) {
		String hql = "from UserInfo userinfo where userinfo.deptype='1' and userinfo.id<>'sys' and userinfo.state is null and userinfo.regionid=?";
		return this.getHibernateTemplate().find(hql, regionId);
	}
	
	/**
	 * 获得代维人员列表
	 * @param deptId
	 * @return
	 */
	public List<Contractor> getContractors(String deptId){
		String hql = "from UserInfo user where user.deptype='2' and user.deptID=? ";
		logger.info("********deptId:" + deptId);
		return this.getHibernateTemplate().find(hql, deptId);
	}
	
	/**
	 * 通过id查询割接申请名称
	 * @param id
	 * @return
	 */
	public String getCutNameById(String id){
		Cut cut = this.findByUnique("id", id);
		return cut.getCutName();
	}

	/**
	 * 查找所有的割接申请
	 * 
	 * @return
	 */
	public List getAllCut() {
		String hql = "from Cut cut";
		return this.find(hql);
	}
	
	/**
	 * 获得代维人员信息
	 * @return
	 */
	public List getAllContractor(){
		String sql = "select * from contractorinfo";
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * 由部门获得代维人员信息
	 * @param deptId：部门ID
	 * @return
	 */
	public List getContractorById(String deptId){
		String sql = "select * from contractorinfo a where a.contractorid=?";
		List list = this.getJdbcTemplate().queryForBeans(sql, deptId);
		return list;
	}

	/**
	 * 更新割接申请状态为value状态
	 * 
	 * @param cutId：割接申请ID
	 * @param value：割接申请状态
	 */
	public void updateStateById(String cutId, String value) {
		Cut cut = this.get(cutId);
		cut.setState(value);
		this.save(cut);
	}

	/**
	 * 根据查询条件获得割接列表
	 * @param condition：查询条件
	 * @return
	 */
	public List getList(String condition) {
		String sql = "select distinct cut.id, feedback.id feedback_id,acceptance.id acceptance_id,'' as isread,cut.proposer,con.contractorname,userinfo.username,cut.cut_name,cut.workorder_id,decode(cut_level,'1','一般割接','2','紧急割接','3','预割接') cut_level,to_char(cut.begintime,'yyyy-mm-dd hh24:mi:ss') cut_begintime," +
				"to_char(cut.apply_date,'yyyy-mm-dd hh24:mi:ss') apply_date,to_char(cut.endtime,'yyyy-mm-dd hh24:mi:ss') cut_endtime,cut.builder cut_builder,'' as flow_state, cut.state state," +
				"decode(cut.state,'"+Cut.APPLY+"','割接申请','"+Cut.APPLY_NO_PASS+"','审批未通过','"+Cut.FEEDBACK+"','待反馈','"+Cut.FEEDBACK_APPROVE+"','反馈待审核','"+Cut.FEEDBACK_NO_PASS+"','反馈审核未通过','"+Cut.ACCEPTANCE+"','验收结算','"+Cut.ACCEPTANCE_APPROVE+"','验收结算审核','"+Cut.ACCEPTANCE_NO_PASS+"','验收结算审核未通过','"+Cut.EXAM+"','考核评估','"+Cut.FINISH+"','完成','"+Cut.CANCELED_STATE+"','取消') as cut_state" + 
				" from lp_cut cut,lp_cut_feedback feedback,lp_cut_acceptance acceptance,userinfo userinfo,contractorinfo con where cut.proposer=userinfo.userid and userinfo.deptid=con.contractorid and cut.id=feedback.cut_id(+) and cut.id=acceptance.cut_id(+) " + condition;
		sql += " order by apply_date desc";
		logger.info("***********getList():" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	//已办工作
	public List queryFinishHandledList(String condition) {
		String sql = "select distinct cut.id,cut.state, feedback.id feedback_id,acceptance.id acceptance_id,'' as isread,cut.proposer,con.contractorname,userinfo.username,cut.cut_name,cut.workorder_id,decode(cut_level,'1','一般割接','2','紧急割接','3','预割接') cut_level,to_char(cut.begintime,'yyyy-mm-dd hh24:mi:ss') cut_begintime," +
			" to_char(cut.apply_date,'yyyy-mm-dd hh24:mi:ss') apply_date,to_char(cut.endtime,'yyyy-mm-dd hh24:mi:ss') cut_endtime,cut.builder cut_builder,'' as flow_state, cut.state state," +
			" decode(cut.state,'"+Cut.APPLY+"','割接申请','"+Cut.APPLY_NO_PASS+"','审批未通过','"+Cut.FEEDBACK+"','待反馈','"+Cut.FEEDBACK_APPROVE+"','反馈待审核','"+Cut.FEEDBACK_NO_PASS+"','反馈审核未通过','"+Cut.ACCEPTANCE+"','验收结算','"+Cut.ACCEPTANCE_APPROVE+"','验收结算审核','"+Cut.ACCEPTANCE_NO_PASS+"','验收结算审核未通过','"+Cut.EXAM+"','考核评估','"+Cut.FINISH+"','完成','"+Cut.CANCELED_STATE+"','取消') as cut_state," + 
			" process.handled_task_name,process.task_out_come,process.id as history_id" + 
			" from lp_cut cut,lp_cut_feedback feedback,lp_cut_acceptance acceptance,userinfo userinfo,contractorinfo con,process_history_info process " +
			" where cut.proposer=userinfo.userid and userinfo.deptid=con.contractorid and cut.id=feedback.cut_id(+) and cut.id=acceptance.cut_id(+) and process.object_id=cut.id and process.object_type='lineCut' " + condition;
		sql += " order by apply_date desc";
		logger.info("***********getList():" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * 获得评分列表
	 * @return
	 */
	public List getCheckAndMarkList(){
		String sql = "select cut.id ,'' as isread,cut.proposer,con.contractorname,userinfo.username,cut.cut_name,cut.workorder_id,decode(cut_level,'"+Cut.LEVEL_NORMAL+"','一般割接','"+Cut.LEVEL_EXIGENCE+"','紧急割接','"+Cut.LEVEL_PREPARE+"','预割接') cut_level,to_char(cut.begintime,'yyyy-mm-dd hh24:mi:ss') cut_begintime," +
		"to_char(cut.apply_date,'yyyy-mm-dd hh24:mi:ss') apply_date,to_char(cut.endtime,'yyyy-mm-dd hh24:mi:ss') cut_endtime,cut.builder cut_builder,'' as flow_state, cut.state state," +
		"decode(cut.state,'"+Cut.APPLY+"','割接申请','"+Cut.APPLY_NO_PASS+"','审批未通过','"+Cut.FEEDBACK+"','待反馈','"+Cut.FEEDBACK_APPROVE+"','反馈待审核','"+Cut.FEEDBACK_NO_PASS+"','反馈审核未通过','"+Cut.ACCEPTANCE+"','验收结算','"+Cut.ACCEPTANCE_APPROVE+"','验收结算审核','"+Cut.ACCEPTANCE_NO_PASS+"','验收结算审核未通过','"+Cut.EXAM+"','考核评估','"+Cut.FINISH+"','完成','"+Cut.CANCELED_STATE+"','取消') as cut_state" + 
		" from lp_cut cut,userinfo userinfo,contractorinfo con where cut.proposer=userinfo.userid and userinfo.deptid=con.contractorid and cut.state='9' ";
		sql += " order by apply_date desc";
		//String hql = "from Cut cut where cut.state='9'";
		//return this.find(hql);
		logger.info("***********getCheckAndMarkList():" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * 查找未审批的抄送人ID
	 * @param objectId：业务ID
	 * @param approverType：审批类型 审批、抄送、转审
	 * @param objectType：业务类型
	 * @param condition：添加查询条件
	 * @return
	 */
	public String getApproverIds(String objectId, String approverType, String objectType, String condition){
		String approverIds = "";
		String sql = "select t.approver_id from lp_reply_approver t where t.object_type=? and t.object_id=? and t.approver_type=? ";
		sql += condition;
		List list = getJdbcTemplate().queryForBeans(sql, objectType, objectId, approverType);
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				BasicDynaBean bean = (BasicDynaBean)list.get(i);
				approverIds += bean.get("approver_id") + ",";
			}
		}
		if(!"".equals(approverIds)){
			approverIds = approverIds.substring(0, approverIds.length() - 1);
		}
		return approverIds;
	}
	
	/**
	 * 根据查询条件获取审核信息列表
	 * 
	 * @param condition
	 * @return
	 */
	public List queryApproveList(String condition) {
		String sql = "select approve.id,u.username,approve.approve_remark, ";
		sql = sql + " to_char(approve.approve_time,'yyyy-mm-dd hh24:mi:ss') ";
		sql = sql + " as approve_time,approve.approve_result, ";
		sql = sql + " decode(approve.approve_result,'0','审核不通过','1', ";
		sql = sql + "'审核通过','转审') as approve_result_dis ";
		sql = sql + " from lp_approve_info approve,userinfo u ";
		sql = sql + " where approve.approver_id=u.userid ";
		sql = sql + condition;
		sql = sql + " order by approve.id";
		logger.info("Execute sql:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}
	
	/**
	 * 由用户ID获得代维单位名称
	 * @param userId：用户ID
	 * @return
	 */
	public String getConNameByUserId(String userId){
		String hql = "from UserInfo user where user.id=?";
		UserInfo user = (UserInfo)this.getHibernateTemplate().find(hql,userId).get(0);
		String deptId = user.getDeptID();
		hql = "from Contractor con where con.id=?";
		Contractor con = (Contractor)this.getHibernateTemplate().find(hql,deptId).get(0);
		return con.getContractorName();
	}
	
	/**
	 * 获得审批人信息
	 * @param objectId
	 * @param objectType
	 * @param approveType
	 * @return
	 */
	public String[] getApproveIdAndName(String objectId, String objectType, String approveType){
		String sql = "select userinfo.phone,approve.approver_id,userinfo.username from lp_reply_approver approve,userinfo where approve.approver_id=userinfo.userid and approve.object_id='" + objectId
					+ "' and approve.object_type='" + objectType + "' and approve.approver_type='" + approveType + "'";
		List list = this.getJdbcTemplate().queryForBeans(sql);
		if(list.size() == 0){
			return null;
		}
		String appId = "";
		String appName = "";
		String appPhone = "";
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			BasicDynaBean bean = (BasicDynaBean) iterator.next();
			appId += (String)bean.get("approver_id") + ",";
			appName += (String)bean.get("username") + ",";
			appPhone += (String)bean.get("phone") + ",";
		}
		return new String[]{appId.substring(0,appId.length()-1),appName.substring(0,appName.length()-1),appPhone.substring(0,appPhone.length()-1)};
	}
	
	/**
	 * 获得该割接的附件ID
	 * @param cutId：割接ID
	 * @return
	 */
	public List getAffixIdByCutId(String cutId){
		List list = new ArrayList();
		String sql = "select one.fileid from annex_add_one one where one.entity_id='" + cutId + "' and one.entity_type='LP_CUT' and one.module='lineCut'";
		List list2 = this.getJdbcTemplate().queryForBeans(sql);
		if(list2 != null && list2.size() > 0){
			for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
				BasicDynaBean bean = (BasicDynaBean) iterator.next();
				String id = (String)bean.get("fileid");
				list.add(id);
			}
		}
		return list;
	}
	
	//大修项目使用--朱枫
	public List<Cut> getCutForOverHaul(String deptId, OverHaulQueryBean queryBean){
		StringBuffer sb = new StringBuffer();
		List<String> param = new ArrayList<String>();
		sb.append("select c from Cut c where (c.state = 9 or c.state = 0) and c.id not in (select ohc.cutId from OverHaulCut ohc ");
		if(StringUtils.isNotBlank(queryBean.getApplyId())){
			sb.append("where ohc.overHaulApply <> (select o from OverHaulApply o where o.id = ?) ");
			param.add(queryBean.getApplyId());
		}
		sb.append(") and c.proposer in (select u.userID from UserInfo u where u.deptID = ?)");
		param.add(deptId);
		if(StringUtils.isNotBlank(queryBean.getProjectName())){
			sb.append("and c.cutName like '%");
			sb.append(queryBean.getProjectName());
			sb.append("%' ");
		}
		if(StringUtils.isNotBlank(queryBean.getStartTime())){
			sb.append("and c.beginTime >= to_Date(?,'yyyy/MM/dd')");
			param.add(queryBean.getStartTime());
		}
		if(StringUtils.isNotBlank(queryBean.getEndTime())){
			sb.append("and c.endTime <= to_Date(?,'yyyy/MM/dd')");
			param.add(queryBean.getEndTime());
		}
		return find(sb.toString(), param.toArray());
	}
}