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
 * ������������
 * 
 * @author Administrator
 * 
 */
@Repository
public class CutDao extends HibernateDao<Cut, String> {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * ����ƶ���Ա
	 * @param regionId������ID
	 * @return���ƶ���Ա�б�
	 */
	public List<UserInfo> getMobiles(String regionId){
		String hql = "from UserInfo userinfo where userinfo.deptype='1' and userinfo.id<>'sys' and userinfo.state is null and userinfo.regionid=?";
		return this.getHibernateTemplate().find(hql, regionId);
	}
	
	/**
	 * ��ô�ά��Ա�б�
	 * @param regionId������ID
	 * @return����ά��Ա�б�
	 */
	public List<ConPerson> getCons(String deptId){
		String hql = "from ConPerson conPerson where conPerson.contractorid=?";
		return this.getHibernateTemplate().find(hql, deptId);
	}
	
	/**
	 * ��ѯ���û���������
	 * @param deptId���û�����ID
	 * @return������
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
	 * �޸ĸ��δͨ������
	 * @param cutId���������ID
	 */
	public void setUnapproveNumber(String cutId){
		Cut cut = this.findByUnique("id", cutId);
		int unapproveNumber = cut.getUnapproveNumber();
		cut.setUnapproveNumber(++unapproveNumber);
		this.save(cut);
	}
	
	/**
	 * ���ݸ������ID�޸ĸ������״̬
	 * @param cutId���������ID
	 * @param state���������״̬
	 */
	public void setCutState(String cutId, String state){
		Cut cut = this.findByUnique("id", cutId);
		cut.setState(state);
		this.save(cut);
	}
	
	/**
	 * ���ĳ����ʱ��������б�
	 * @param userId���û�ID
	 * @return����ʱ��������б�
	 */
	public List cutTempList(String userId){
		String sql = "select cut.id,cut.workorder_id,cut.cut_name,cut.charge_man,decode(cut_level,'1','һ����','2','�������','3','Ԥ���') cut_level,to_char(cut.apply_date,'yyyy-mm-dd hh24:mi:ss') apply_date,cut.proposer from lp_cut cut,userinfo userinfo where cut.proposer=userinfo.userid and cut.apply_state='1' and userinfo.userid='" + userId +"'";
		sql += " order by cut.apply_date desc";
		logger.info("***********getList():" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * ��Ӹ��������Ϣ
	 * 
	 * @param cut
	 * @return
	 */
	public Cut addCutApply(Cut cut) {
		this.save(cut);
		return cut;
	}
	
	/**
	 * �鿴���û��ĸ�������б�
	 * @param userId���û�ID
	 * @return����������б�
	 */
	public List queryApplyList(String userId){
		String sql = "select cut.id ,cut.workorder_id,con.contractorname,decode(cut_level,'"+Cut.LEVEL_NORMAL+"','һ����','"+Cut.LEVEL_EXIGENCE+"','�������','"+Cut.LEVEL_PREPARE+"','Ԥ���') cut_level,to_char(cut.begintime,'yyyy-mm-dd hh24:mi:ss') cut_begintime," +
			"to_char(cut.endtime,'yyyy-mm-dd hh24:mi:ss') cut_endtime,cut.builder cut_builder, cut.state state," +
			"decode(cut.state,'"+Cut.APPLY+"','�������','"+Cut.APPLY_NO_PASS+"','����δͨ��','"+Cut.FEEDBACK+"','������','"+Cut.FEEDBACK_APPROVE+"','���������','"+Cut.FEEDBACK_NO_PASS+"','�������δͨ��','"+Cut.ACCEPTANCE+"','���ս���','"+Cut.ACCEPTANCE_APPROVE+"','���ս������','"+Cut.ACCEPTANCE_NO_PASS+"','���ս������δͨ��','"+Cut.EXAM+"','��������','"+Cut.FINISH+"','���','"+Cut.CANCELED_STATE+"','ȡ��') as cut_state" + 
			" from lp_cut cut,userinfo userinfo,contractorinfo con where cut.proposer=userinfo.userid and userinfo.deptid=con.contractorid and cut.proposer=? and cut.state='1'";
		logger.info("queryApplyList:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, userId);
	}
	
	/**
	 * ����ƶ���Ա�б�
	 * @param regionId
	 * @return
	 */
	public List<UserInfo> getApproves(String regionId) {
		String hql = "from UserInfo userinfo where userinfo.deptype='1' and userinfo.id<>'sys' and userinfo.state is null and userinfo.regionid=?";
		return this.getHibernateTemplate().find(hql, regionId);
	}
	
	/**
	 * ��ô�ά��Ա�б�
	 * @param deptId
	 * @return
	 */
	public List<Contractor> getContractors(String deptId){
		String hql = "from UserInfo user where user.deptype='2' and user.deptID=? ";
		logger.info("********deptId:" + deptId);
		return this.getHibernateTemplate().find(hql, deptId);
	}
	
	/**
	 * ͨ��id��ѯ�����������
	 * @param id
	 * @return
	 */
	public String getCutNameById(String id){
		Cut cut = this.findByUnique("id", id);
		return cut.getCutName();
	}

	/**
	 * �������еĸ������
	 * 
	 * @return
	 */
	public List getAllCut() {
		String hql = "from Cut cut";
		return this.find(hql);
	}
	
	/**
	 * ��ô�ά��Ա��Ϣ
	 * @return
	 */
	public List getAllContractor(){
		String sql = "select * from contractorinfo";
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * �ɲ��Ż�ô�ά��Ա��Ϣ
	 * @param deptId������ID
	 * @return
	 */
	public List getContractorById(String deptId){
		String sql = "select * from contractorinfo a where a.contractorid=?";
		List list = this.getJdbcTemplate().queryForBeans(sql, deptId);
		return list;
	}

	/**
	 * ���¸������״̬Ϊvalue״̬
	 * 
	 * @param cutId���������ID
	 * @param value���������״̬
	 */
	public void updateStateById(String cutId, String value) {
		Cut cut = this.get(cutId);
		cut.setState(value);
		this.save(cut);
	}

	/**
	 * ���ݲ�ѯ������ø���б�
	 * @param condition����ѯ����
	 * @return
	 */
	public List getList(String condition) {
		String sql = "select distinct cut.id, feedback.id feedback_id,acceptance.id acceptance_id,'' as isread,cut.proposer,con.contractorname,userinfo.username,cut.cut_name,cut.workorder_id,decode(cut_level,'1','һ����','2','�������','3','Ԥ���') cut_level,to_char(cut.begintime,'yyyy-mm-dd hh24:mi:ss') cut_begintime," +
				"to_char(cut.apply_date,'yyyy-mm-dd hh24:mi:ss') apply_date,to_char(cut.endtime,'yyyy-mm-dd hh24:mi:ss') cut_endtime,cut.builder cut_builder,'' as flow_state, cut.state state," +
				"decode(cut.state,'"+Cut.APPLY+"','�������','"+Cut.APPLY_NO_PASS+"','����δͨ��','"+Cut.FEEDBACK+"','������','"+Cut.FEEDBACK_APPROVE+"','���������','"+Cut.FEEDBACK_NO_PASS+"','�������δͨ��','"+Cut.ACCEPTANCE+"','���ս���','"+Cut.ACCEPTANCE_APPROVE+"','���ս������','"+Cut.ACCEPTANCE_NO_PASS+"','���ս������δͨ��','"+Cut.EXAM+"','��������','"+Cut.FINISH+"','���','"+Cut.CANCELED_STATE+"','ȡ��') as cut_state" + 
				" from lp_cut cut,lp_cut_feedback feedback,lp_cut_acceptance acceptance,userinfo userinfo,contractorinfo con where cut.proposer=userinfo.userid and userinfo.deptid=con.contractorid and cut.id=feedback.cut_id(+) and cut.id=acceptance.cut_id(+) " + condition;
		sql += " order by apply_date desc";
		logger.info("***********getList():" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	//�Ѱ칤��
	public List queryFinishHandledList(String condition) {
		String sql = "select distinct cut.id,cut.state, feedback.id feedback_id,acceptance.id acceptance_id,'' as isread,cut.proposer,con.contractorname,userinfo.username,cut.cut_name,cut.workorder_id,decode(cut_level,'1','һ����','2','�������','3','Ԥ���') cut_level,to_char(cut.begintime,'yyyy-mm-dd hh24:mi:ss') cut_begintime," +
			" to_char(cut.apply_date,'yyyy-mm-dd hh24:mi:ss') apply_date,to_char(cut.endtime,'yyyy-mm-dd hh24:mi:ss') cut_endtime,cut.builder cut_builder,'' as flow_state, cut.state state," +
			" decode(cut.state,'"+Cut.APPLY+"','�������','"+Cut.APPLY_NO_PASS+"','����δͨ��','"+Cut.FEEDBACK+"','������','"+Cut.FEEDBACK_APPROVE+"','���������','"+Cut.FEEDBACK_NO_PASS+"','�������δͨ��','"+Cut.ACCEPTANCE+"','���ս���','"+Cut.ACCEPTANCE_APPROVE+"','���ս������','"+Cut.ACCEPTANCE_NO_PASS+"','���ս������δͨ��','"+Cut.EXAM+"','��������','"+Cut.FINISH+"','���','"+Cut.CANCELED_STATE+"','ȡ��') as cut_state," + 
			" process.handled_task_name,process.task_out_come,process.id as history_id" + 
			" from lp_cut cut,lp_cut_feedback feedback,lp_cut_acceptance acceptance,userinfo userinfo,contractorinfo con,process_history_info process " +
			" where cut.proposer=userinfo.userid and userinfo.deptid=con.contractorid and cut.id=feedback.cut_id(+) and cut.id=acceptance.cut_id(+) and process.object_id=cut.id and process.object_type='lineCut' " + condition;
		sql += " order by apply_date desc";
		logger.info("***********getList():" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * ��������б�
	 * @return
	 */
	public List getCheckAndMarkList(){
		String sql = "select cut.id ,'' as isread,cut.proposer,con.contractorname,userinfo.username,cut.cut_name,cut.workorder_id,decode(cut_level,'"+Cut.LEVEL_NORMAL+"','һ����','"+Cut.LEVEL_EXIGENCE+"','�������','"+Cut.LEVEL_PREPARE+"','Ԥ���') cut_level,to_char(cut.begintime,'yyyy-mm-dd hh24:mi:ss') cut_begintime," +
		"to_char(cut.apply_date,'yyyy-mm-dd hh24:mi:ss') apply_date,to_char(cut.endtime,'yyyy-mm-dd hh24:mi:ss') cut_endtime,cut.builder cut_builder,'' as flow_state, cut.state state," +
		"decode(cut.state,'"+Cut.APPLY+"','�������','"+Cut.APPLY_NO_PASS+"','����δͨ��','"+Cut.FEEDBACK+"','������','"+Cut.FEEDBACK_APPROVE+"','���������','"+Cut.FEEDBACK_NO_PASS+"','�������δͨ��','"+Cut.ACCEPTANCE+"','���ս���','"+Cut.ACCEPTANCE_APPROVE+"','���ս������','"+Cut.ACCEPTANCE_NO_PASS+"','���ս������δͨ��','"+Cut.EXAM+"','��������','"+Cut.FINISH+"','���','"+Cut.CANCELED_STATE+"','ȡ��') as cut_state" + 
		" from lp_cut cut,userinfo userinfo,contractorinfo con where cut.proposer=userinfo.userid and userinfo.deptid=con.contractorid and cut.state='9' ";
		sql += " order by apply_date desc";
		//String hql = "from Cut cut where cut.state='9'";
		//return this.find(hql);
		logger.info("***********getCheckAndMarkList():" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * ����δ�����ĳ�����ID
	 * @param objectId��ҵ��ID
	 * @param approverType���������� ���������͡�ת��
	 * @param objectType��ҵ������
	 * @param condition����Ӳ�ѯ����
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
	 * ���ݲ�ѯ������ȡ�����Ϣ�б�
	 * 
	 * @param condition
	 * @return
	 */
	public List queryApproveList(String condition) {
		String sql = "select approve.id,u.username,approve.approve_remark, ";
		sql = sql + " to_char(approve.approve_time,'yyyy-mm-dd hh24:mi:ss') ";
		sql = sql + " as approve_time,approve.approve_result, ";
		sql = sql + " decode(approve.approve_result,'0','��˲�ͨ��','1', ";
		sql = sql + "'���ͨ��','ת��') as approve_result_dis ";
		sql = sql + " from lp_approve_info approve,userinfo u ";
		sql = sql + " where approve.approver_id=u.userid ";
		sql = sql + condition;
		sql = sql + " order by approve.id";
		logger.info("Execute sql:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}
	
	/**
	 * ���û�ID��ô�ά��λ����
	 * @param userId���û�ID
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
	 * �����������Ϣ
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
	 * ��øø�ӵĸ���ID
	 * @param cutId�����ID
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
	
	//������Ŀʹ��--���
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