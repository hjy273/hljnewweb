package com.cabletech.linepatrol.cut.dao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.cut.module.Cut;
import com.cabletech.linepatrol.cut.module.QueryCondition;
import com.cabletech.linepatrol.cut.workflow.CutWorkflowBO;;

/**
 * ������������
 * 
 * @author Administrator
 * 
 */
@Repository
public class CutQueryStatDao extends HibernateDao<Cut, String> {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	private static final String PLAN_CANCEL = "999";

	/**
	 * ����������ѯ��������б�
	 * 
	 * @param condition
	 * @return
	 */
	public List queryByCondition(QueryCondition condition, UserInfo userInfo) {
		String contractorId = condition.getContractorId();
		String[] cutLevels = condition.getCutLevels();
		String[] cableLevels = condition.getCableLevels();
		String isInterrupt = condition.getIsInterrupt();
		String[] states = condition.getStates();
		String isTimeOut = condition.getIsTimeOut();
		String timeType = condition.getTimeType();
		String beginTime = condition.getBeginTime();
		String endTime = condition.getEndTime();

		String sql = "select distinct cut.id,cut.proposer,con.contractorname,u.username,cut.cut_name,cut.proposer,cut.workorder_id,decode(cut.cut_level,'1','һ����','2','�������','3','Ԥ���') cut_level,"
				+ "to_char(cut.apply_date,'yyyy-mm-dd hh24:mi:ss') apply_date,trunc((feedback.create_time-cut.reply_endtime)*24,2) feedback_hours,"
				+ "to_char(cut.begintime,'yyyy-mm-dd hh24:mi:ss') begintime,to_char(cut.endtime,'yyyy-mm-dd hh24:mi:ss') endtime,cut.builder,"
				+ "decode(cut.state,'1','���������','2','������ͨ��','3','���������','4','���뷴��������','5','����������ͨ��','6','��������ս���','7','���ս��������','8','���ս���������ͨ��','9','��������','0','���','999','ȡ��') as cut_state "
				+ "from lp_cut cut,userinfo u,contractorinfo con, lp_cut_feedback feedback where 1=1 and u.userid=cut.proposer and u.deptid=contractorid and cut.id=feedback.cut_id(+) ";
		if (contractorId != null && !"".equals(contractorId)) {
			sql += "and cut.proposer in (select userinfo.userid from userinfo userinfo where userinfo.deptid='"
					+ contractorId + "') ";
		}
		if (cutLevels != null && cutLevels.length > 0) {
			sql += "and cut.cut_level in (" + stringArray2String(cutLevels)
					+ ") ";
		}
		if (cableLevels != null && cableLevels.length > 0) {
			sql += "and cut.id in (select subline.cut_id from lp_cut_subline subline where subline.subline_id in (select cablesegment.kid from repeatersection cablesegment where cablesegment.cable_level in ("
					+ stringArray2String(cableLevels) + "))) ";
		}
		if (isInterrupt != null && !"".equals(isInterrupt)) {
			sql += "and cut.id in (select feedback.cut_id from lp_cut_feedback feedback where feedback.isinterrupt='"
					+ isInterrupt + "') ";
		}
		if (states != null && states.length > 0) {
			sql += "and cut.state in (" + stringArray2String(states) + ") ";
		}
		//�Ƿ�ȡ��
		String cancelState = condition.getCancelState();
		if(StringUtils.isNotBlank(cancelState)){
			if(StringUtils.equals(cancelState, PLAN_CANCEL)){
				sql += " and cut.state='" + cancelState + "'";
			} else {
				sql += " and (cut.state<>'" + PLAN_CANCEL + "' or cut.state is null)";
			}
		}
		if (isTimeOut != null && !"".equals(isTimeOut)) {
			sql += "and cut.id in (select feedback.cut_id from lp_cut_feedback feedback where feedback.istimeout='"
					+ isTimeOut + "') ";
		}
		// ����ʱ��
		if (timeType.equals("1")) {
			if (beginTime != null && !"".equals(beginTime)) {
				sql += " and cut.begintime>to_date('" + beginTime
						+ "','yyyy/mm/dd hh24:mi:ss')";
			}
			if (endTime != null && !"".equals(endTime)) {
				sql += " and cut.endtime<to_date('" + endTime
						+ "','yyyy/mm/dd hh24:mi:ss') ";
			}
		}
		// ʵ�ʿ�ʼʱ��
		if (timeType.equals("2")) {
			if ((beginTime != null && !"".equals(beginTime))
					&& (endTime != null && !"".equals(endTime))) {
				sql += " and cut.id in (select feedback.cut_id from lp_cut_feedback feedback where feedback.begintime>to_date('"
						+ beginTime
						+ "','yyyy/mm/dd hh24:mi:ss') and feedback.endtime<to_date('"
						+ endTime + "','yyyy/mm/dd hh24:mi:ss'))";
			}
			if ((beginTime == null || "".equals(beginTime))
					&& (endTime != null && !"".equals(endTime))) {
				sql += " and cut.id in (select feedback.cut_id from lp_cut_feedback feedback where feedback.endtime<to_date('"
						+ endTime + "','yyyy/mm/dd hh24:mi:ss')) ";
			}
			if ((beginTime != null && !"".equals(beginTime))
					&& (endTime == null || "".equals(endTime))) {
				sql += " and cut.id in (select feedback.cut_id from lp_cut_feedback feedback where feedback.begintime>to_date('"
						+ beginTime + "','yyyy/mm/dd hh24:mi:ss')) ";
			}
		}
		// ����ʱ��
		if (timeType.equals("3")) {
			if (beginTime != null && !"".equals(beginTime)) {
				sql += " and cut.reply_begintime>to_date('" + beginTime
						+ "','yyyy/mm/dd hh24:mi:ss')";
			}
			if (endTime != null && !"".equals(endTime)) {
				sql += "  and cut.reply_endtime<to_date('" + endTime
						+ "','yyyy/mm/dd hh24:mi:ss') ";
			}
		}
		StringBuffer buf = new StringBuffer("");
		if (condition.getTaskStates() != null) {
			String[] taskStates = condition.getTaskStates();
			buf.append(" and exists( ");
			buf.append(" select dbid_ from jbpm4_task jbpm_task ");
			buf.append(" where jbpm_task.execution_id_='");
			buf.append(CutWorkflowBO.CUT_WORKFLOW);
			buf.append(".'||cut.id ");
			buf.append(" and ( ");
			for (int i = 0; i < taskStates.length; i++) {
				buf.append(" jbpm_task.name_='");
				buf.append(taskStates[i] + "' ");
				if (i < taskStates.length - 1) {
					buf.append(" or ");
				}
			}
			buf.append(" ) ");
			buf.append(" ) ");
		}
		sql += buf.toString();
		sql += " order by apply_date desc";
		logger.info("��Ӳ�ѯ��䣺" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ���ַ�������ת��Ϊ�ַ���
	 * 
	 * @param field
	 *            �����ݿ��Ӧ�ֶ���
	 * @param array
	 * @return
	 */
	public String stringArray2String(String[] arrays) {
		String value = "";
		if (arrays.length > 0) {
			for (int i = 0; i < arrays.length; i++) {
				value += "'" + arrays[i] + "',";
			}
			value = value.substring(0, value.length() - 1);
		}
		return value;
	}

	/*
	 * ���ͳ��
	 */
	public List cutStatForm(QueryCondition condition) {
		String contractorId = condition.getContractorId();
		String[] cutLevels = condition.getCutLevels();
		String[] cableLevels = condition.getCableLevels();
		String isInterrupt = condition.getIsInterrupt();
		String isTimeOut = condition.getIsTimeOut();
		String timeType = condition.getTimeType();
		String beginTime = condition.getBeginTime();
		String endTime = condition.getEndTime();
		String sql = "select c.contractorname, count(cut.id) cut_number, sum(cut.budget) total_budget, "
				+ "sum(trunc((feedback.endtime-feedback.begintime)*24,2)) total_time, sum(feedback.bs) total_bs from lp_cut cut,"
				+ " lp_cut_feedback feedback,userinfo u, contractorinfo c where cut.id=feedback.cut_id and cut.proposer=u.userid "
				+ "and u.deptid=c.contractorid and cut.state='0' ";
		if (contractorId != null && !"".equals(contractorId)) {
			sql += "and cut.proposer in (select userinfo.userid from userinfo userinfo where userinfo.deptid='"
					+ contractorId + "') ";
		}
		if (cutLevels != null && cutLevels.length > 0) {
			sql += "and cut.cut_level in (" + stringArray2String(cutLevels)
					+ ") ";
		}
		if (cableLevels != null && cableLevels.length > 0) {
			sql += "and cut.id in (select subline.cut_id from lp_cut_subline subline where subline.subline_id in (select cablesegment.kid from repeatersection cablesegment where cablesegment.cable_level in ("
					+ stringArray2String(cableLevels) + "))) ";
		}
		if (isInterrupt != null && !"".equals(isInterrupt)) {
			sql += "and cut.id in (select feedback.cut_id from lp_cut_feedback feedback where feedback.isinterrupt='"
					+ isInterrupt + "') ";
		}
		if (isTimeOut != null && !"".equals(isTimeOut)) {
			sql += "and cut.id in (select feedback.cut_id from lp_cut_feedback feedback where feedback.istimeout='"
					+ isTimeOut + "') ";
		}
		if (timeType.equals("1")) {
			if (beginTime != null && !"".equals(beginTime)) {
				sql += " and cut.begintime>to_date('" + beginTime
						+ "','yyyy/mm/dd hh24:mi:ss')";
			}
			if (endTime != null && !"".equals(endTime)) {
				sql += " and cut.endtime<to_date('" + endTime
						+ "','yyyy/mm/dd hh24:mi:ss') ";
			}
		}
		if (timeType.equals("2")) {
			if ((beginTime != null && !"".equals(beginTime))
					&& (endTime != null && !"".equals(endTime))) {
				sql += " and cut.id in (select feedback.cut_id from lp_cut_feedback feedback where feedback.begintime>to_date('"
						+ beginTime
						+ "','yyyy/mm/dd hh24:mi:ss') and feedback.endtime<to_date('"
						+ endTime + "','yyyy/mm/dd hh24:mi:ss'))";
			}
			if ((beginTime == null || "".equals(beginTime))
					&& (endTime != null && !"".equals(endTime))) {
				sql += " and cut.id in (select feedback.cut_id from lp_cut_feedback feedback where feedback.endtime<to_date('"
						+ endTime + "','yyyy/mm/dd hh24:mi:ss')) ";
			}
			if ((beginTime != null && !"".equals(beginTime))
					&& (endTime == null || "".equals(endTime))) {
				sql += " and cut.id in (select feedback.cut_id from lp_cut_feedback feedback where feedback.begintime>to_date('"
						+ beginTime + "','yyyy/mm/dd hh24:mi:ss')) ";
			}
		}
		if (timeType.equals("3")) {
			if (beginTime != null && !"".equals(beginTime)) {
				sql += " and cut.reply_begintime>to_date('" + beginTime
						+ "','yyyy/mm/dd hh24:mi:ss')";
			}
			if (endTime != null && !"".equals(endTime)) {
				sql += "  and cut.reply_endtime<to_date('" + endTime
						+ "','yyyy/mm/dd hh24:mi:ss') ";
			}
		}
		StringBuffer buf = new StringBuffer("");
		if (condition.getTaskStates() != null) {
			String[] taskStates = condition.getTaskStates();
			buf.append(" and exists( ");
			buf.append(" select dbid_ from jbpm4_task jbpm_task ");
			buf.append(" where jbpm_task.execution_id_='");
			buf.append(CutWorkflowBO.CUT_WORKFLOW);
			buf.append(".'||cut.id ");
			buf.append(" and ( ");
			for (int i = 0; i < taskStates.length; i++) {
				buf.append(" jbpm_task.name_='");
				buf.append(taskStates[i] + "' ");
				if (i < taskStates.length - 1) {
					buf.append(" or ");
				}
			}
			buf.append(" ) ");
			buf.append(" ) ");
		}
		sql += buf.toString();
		sql += " group by c.contractorname";
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * �ɿ�ݲ˵���ѯ����б�
	 * 
	 * @param state
	 * @param regionId
	 * @return
	 */
	public List queryCutInfoByMenu(String state, String regionId) {
		String sql = "select distinct cut.id,cut.workorder_id,decode(cut_level,'1','һ����','2','�������','3','Ԥ���') cut_level,"
				+ "to_char(cut.begintime,'yyyy-mm-dd hh24:mi:ss') begintime,to_char(cut.endtime,'yyyy-mm-dd hh24:mi:ss') endtime,cut.builder,"
				+ "decode(state,'1','���������','2','������ͨ��','3','���������','4','���뷴��������','5','����������ͨ��','6','��������ս���','7','���ս��������','8','���ս���������ͨ��','9','��������','0','���','999','ȡ��') as state "
				+ "from lp_cut cut where cut.state in ("
				+ state
				+ ") and proposer in (select userid from userinfo where regionid='"
				+ regionId + "')";
		logger.info("*******************queryCutInfoByMenu:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * ͨ��PDA��ѯ����������
	 * @param userInfo
	 * @return
	 */
	public List<Map> queryCutApproveFromPDA(UserInfo userInfo) {
		String sql = "select CUT.CUT_NAME cutName,CUT.LIVE_CHARGEMAN liveChargeMan, con.contractorname contractorName,to_char(CUT.APPLY_DATE,'yy-MM-dd') applyDate,to_char(CUT.BEGINTIME,'yy-MM-dd hh:mm')||' - '||to_char(cut.endtime,'yy-MM-dd hh:mm') cutTime "
				+ "from lp_cut cut,userinfo u,contractorinfo con,lp_reply_approver r "
				+ "where cut.state='1' and CUT.PROPOSER=U.USERID and U.DEPTID=con.contractorid and CUT.REGION_ID='"
				+ userInfo.getRegionID()
				+ "' and R.OBJECT_TYPE='LP_CUT' and R.APPROVER_ID='"
				+ userInfo.getUserID()
				+ "' and r.approver_type='01' and R.IS_TRANSFER_APPROVED='0' and R.OBJECT_ID=CUT.ID order by CUT.PROPOSER,CUT.BEGINTIME";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * ͨ��PDA��ѯ����������
	 * @param userInfo
	 * @return
	 */
	public List<Map> queryFeedbackFromPDA(UserInfo userInfo) {
		String sql = "select CUT.CUT_NAME cutName,CUT.LIVE_CHARGEMAN liveChargeMan, con.contractorname contractorName,TO_CHAR (cut.begintime, 'yy-MM-dd hh:mm')||' - '||TO_CHAR (cut.endtime, 'yy-MM-dd hh:mm') cutTime "
				+ "from lp_cut cut,userinfo u,contractorinfo con,lp_cut_feedback f,lp_reply_approver r "
				+ "where cut.state='4' and CUT.PROPOSER=U.USERID and U.DEPTID=con.contractorid and CUT.REGION_ID='"
				+ userInfo.getRegionID()
				+ "' and CUT.ID=f.cut_id and R.OBJECT_TYPE='LP_CUT_FEEDBACK' and R.APPROVER_ID='"
				+ userInfo.getUserID() + "' and r.approver_type='01' and R.IS_TRANSFER_APPROVED='0'  and R.OBJECT_ID=F.ID order by CUT.PROPOSER,cut.begintime";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	
	/**
	 * ͨ��PDA��ѯ��������
	 * @param userInfo
	 * @return
	 */
	public List<Map> queryAccecptanceFromPDA(UserInfo userInfo) {
		String sql = "select CUT.CUT_NAME cutName,CUT.LIVE_CHARGEMAN liveChargeMan, con.contractorname contractorName,to_char(a.create_time,'yy-MM-dd hh:mm') createTime "
				+ "from lp_cut cut,userinfo u,contractorinfo con,lp_cut_acceptance a,lp_reply_approver r "
				+ "where cut.state='7' and a.CUT_ID=cut.id and CUT.PROPOSER=U.USERID and U.DEPTID=con.contractorid and CUT.REGION_ID='"
				+ userInfo.getRegionID()
				+ "' and R.OBJECT_TYPE='LP_CUT_ACCEPTANCE' and R.APPROVER_ID='"
				+ userInfo.getUserID() + "' and r.approver_type='01' and R.IS_TRANSFER_APPROVED='0' and R.OBJECT_ID=a.ID order by CUT.PROPOSER,a.create_time";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	public List<Map<String, Object>> queryCutNumFromPDA(UserInfo userInfo) {
		String sql = "select count(*) num,'0' state,(select con.contractorname from contractorinfo con,userinfo u where U.USERID=C.PROPOSER and CON.CONTRACTORID=U.DEPTID)  contractorName from lp_cut c where c.state not in ('0','9','999') and c.region_id='"
				+ userInfo.getRegionID()
				+ "' and c.state is not null group by C.PROPOSER "
				+ "union select count(*) num,'1' state,(select con.contractorname from contractorinfo con,userinfo u where U.USERID=C.PROPOSER and CON.CONTRACTORID=U.DEPTID)  contractorName from lp_cut c where c.state='3' and c.region_id='"
				+ userInfo.getRegionID()
				+ "' group by C.PROPOSER "
				+ "union select count(*) num,'2' state,(select con.contractorname from contractorinfo con,userinfo u where U.USERID=C.PROPOSER and CON.CONTRACTORID=U.DEPTID)  contractorName from lp_cut c where to_char(C.APPLY_DATE,'yyyy-MM')=to_char(sysdate,'yyyy-MM') and c.region_id='"
				+ userInfo.getRegionID()
				+ "' group by C.PROPOSER "
				+ "union select count(*) num,'3' state,(select con.contractorname from contractorinfo con,userinfo u where U.USERID=C.PROPOSER and CON.CONTRACTORID=U.DEPTID)  contractorName from lp_cut c where to_char(C.APPLY_DATE,'yyyy-MM')=to_char(sysdate,'yyyy-MM') and c.region_id='"
				+ userInfo.getRegionID() + "' and C.STATE='0' group by C.PROPOSER order by state,contractorName";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
}
