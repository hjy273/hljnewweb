package com.cabletech.linepatrol.trouble.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.trouble.workflow.TroubleWorkflowBO;
import com.cabletech.linepatrol.trouble.beans.TroubleQueryStatBean;
import com.cabletech.linepatrol.trouble.module.TroubleInfo;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;
import com.hp.hpl.sparta.xpath.ThisNodeTest;

@Repository
public class TroubleQueryStatDAO extends HibernateDao<TroubleInfo, String> {

	private static final String PLAN_CANCEL = "999";
	
	/**
	 * 查询故障负责代维
	 * @param user
	 * @return
	 */
	public List getContractors(UserInfo user) {
		String deptype = user.getDeptype();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select distinct contractorid,c.contractorname ");
		sb.append(" from contractorinfo c,lp_trouble_process_unit unit ");
		sb.append(" where c.contractorid=unit.process_unit_id and c.state is null ");
		if (deptype.equals("1")) {
			sb.append(" and c.regionid=?");
			values.add(user.getRegionID());
		}
		if (deptype.equals("2")) {
			sb.append(" and unit.process_unit_id=?");
			values.add(user.getDeptID());
		}
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 查询已办
	 * @param user
	 * @return
	 */
	public List getHandeledWorks(UserInfo user, String condition, String selcondition) {
		String deptype = user.getDeptype();
		String userid = user.getUserID();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select distinct t.id,t.trouble_id,t.trouble_name,u.username trouble_send_man,t.trouble_state, ");
		sb.append(" decode(is_great_trouble,'1','是','0','否','否') AS is_great_trouble,t.send_man_id,");
		sb.append(" to_char(t.trouble_start_time,'yyyy-mm-dd hh24:mi:ss') trouble_start_time,");
		sb.append(" to_char(t.trouble_send_time,'yyyy-mm-dd hh24:mi:ss') trouble_send_time");
		sb.append(selcondition);
		sb.append(" from lp_trouble_info t,process_history_info process");
		sb.append(" ,lp_trouble_process_unit unit,userinfo u ");
		sb.append(" where process.object_id=unit.id and unit.trouble_id=t.id");
		sb.append(" and u.userid = t.send_man_id");
		sb.append(" and process.object_type=? and operate_user_id=?");
		values.add(ModuleCatalog.TROUBLE);
		values.add(userid);
		sb.append(condition);
		if (selcondition != null && !"".equals(selcondition)) {
			sb.append(" order by handled_time desc");
		}
		System.out.println("查询已办:" + sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 根据条件查询故障信息
	 * @param bean
	 * @return
	 */
	public List getTroubles(TroubleQueryStatBean bean, UserInfo user, String act) {
		String deptype = user.getDeptype();
		String startTime = bean.getStartTimeBegin();
		String endtime = bean.getStartTimeEnd();
		String conid = bean.getContractorid();
		String[] isGreatTrouble = bean.getIsGreatTrouble();
		String[] termiAddr = bean.getTermiAddr();
		String[] troubleType = bean.getTroubleType();
		String[] troubleReason = bean.getTroublReason();
		String trunks = bean.getTrunks();
		String eomscode = bean.getEomscode();
		String troubleState = bean.getTroubleState();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		if (act == null || !act.equals("stat")) {
			boolean addr = (termiAddr == null || termiAddr.length == 0);
			boolean type = (troubleType == null || troubleType.length == 0);
			boolean reason = (troubleReason == null || troubleReason.length == 0);
			boolean trunk = (trunks == null || trunks.equals(""));
			if (addr && type && reason && trunk) {//查询未反馈或者是协办的单子 
				sb.append(getTroublesNoReply(eomscode, user));
				if (act == null || !(act.equals("general") || act.equals("great"))) {
					if (isGreatTrouble != null && isGreatTrouble.length > 0) {
						String isgreat = arrayParseToStr(isGreatTrouble);
						sb.append(" and t.is_great_trouble in (" + isgreat + ")");
					}
				}
				//是否取消
				if(StringUtils.isNotBlank(troubleState)){
					if(StringUtils.equals(troubleState, PLAN_CANCEL)){
						sb.append(" and t.trouble_state='");
						sb.append(troubleState);
						sb.append("'");
					} else {
						sb.append(" and (t.trouble_state<>'");
						sb.append(PLAN_CANCEL);
						sb.append("' or t.trouble_state is null)");
					}
				}
				if (conid != null && !"".equals(conid)) {
					sb.append(" and unit.process_unit_id=?");
					values.add(conid);
				}
				if (startTime != null && !"".equals(startTime)) {
					sb.append(" and t.trouble_start_time>=to_date(?,'yyyy/mm/dd')");
					values.add(startTime);
				}

				if (endtime != null && !"".equals(endtime)) {
					sb.append(" and t.trouble_start_time<=to_date(?,'yyyy/mm/dd')");
					values.add(endtime);
				}

				if (deptype.equals("1")) {
					sb.append(" and c.regionid=?");
					values.add(user.getRegionID());
				}

				if (deptype.equals("2")) {
					sb.append(" and unit.process_unit_id=?");
					values.add(user.getDeptID());
				}

				if (act != null) {
					if (act.equals("general")) {
						sb.append(" and t.is_great_trouble=?");
						values.add(TroubleConstant.IS_GREAT_TROUBLE_N);
					}
					if (act.equals("great")) {
						sb.append(" and t.is_great_trouble=?");
						values.add(TroubleConstant.IS_GREAT_TROUBLE_Y);
					}
				}
				sb.append(" union ");
			}
		}
		sb
				.append("select distinct t.id,t.trouble_state trouble_state, t.trouble_id,t.trouble_name,reply.id replyid,c.contractorname,");
		sb.append(" to_char(t.trouble_send_time,'yyyy-mm-dd hh24:mi:ss') trouble_send_time,");
		sb.append(" to_char(reply.trouble_end_time,'yyyy-mm-dd hh24:mi:ss') trouble_end_time, ");
		//sb.append(" trunc((reply.return_time - t.trouble_send_time)*24 ,2) as etime, ");
		sb.append(" trunc((reply.renew_time - reply.trouble_accept_time)*24 ,2) as etime, ");//抢修历时=故障恢复时间-故障受理时间
		sb.append(" trunc((reply.reply_submit_time - t.trouble_send_time)*24 ,2) as replytime, ");
		sb.append(" dfaddr.lable teraddr,dftype.lable trouble_type,");
		sb.append(" t.trouble_remark ,reply.impress_type ");
		//sb.append(" ,'' trunk ");
		sb.append(queryTrunkCondition());
		sb.append(" from lp_trouble_info t,lp_trouble_process_unit unit, ");
		sb.append(" lp_trouble_reply reply,contractorinfo c,");
		sb.append(" dictionary_formitem dfaddr,dictionary_formitem dftype ");
		if (trunks != null && !"".equals(trunks.trim())) {
			sb.append(",lp_trouble_cable_info cable ");
		}
		if (eomscode != null && !"".equals(eomscode.trim())) {
			sb.append(",lp_trouble_eoms_code eoms ");
		}
		sb.append(" where t.id=reply.trouble_id and unit.trouble_id=t.id  ");
		sb.append(" and c.contractorid=reply.contractor_id ");
		sb.append(" and reply.contractor_id=unit.process_unit_id");
		sb.append(" and reply.confirm_result='" + TroubleConstant.REPLY_ROLE_HOST + "'");
		sb.append(" and dfaddr.code=reply.terminal_address and dfaddr.assortment_id=? ");
		values.add(TroubleConstant.ASSORTMENT_TERMINAL_ADDR);
		sb.append(" and dftype.code=reply.trouble_type and dftype.assortment_id=? ");
		values.add(TroubleConstant.ASSORTMENT_TROUBLE_TYPE);

		if (termiAddr != null && termiAddr.length > 0) {
			String addrs = arrayParseToStr(termiAddr);
			sb.append(" and reply.terminal_address in(" + addrs + ")");
		}
		if (troubleType != null && troubleType.length > 0) {
			String types = arrayParseToStr(troubleType);
			sb.append(" and reply.trouble_type in (" + types + ")");
		}
		if (troubleReason != null && troubleReason.length >= 0) {
			String reasons = arrayParseToStr(troubleReason);
			sb.append(" and t.trouble_reason_id in (" + reasons + ")");
		}
		if (act == null || !(act.equals("general") || act.equals("great"))) {
			if (isGreatTrouble != null && isGreatTrouble.length > 0) {
				String isgreat = arrayParseToStr(isGreatTrouble);
				sb.append(" and t.is_great_trouble in (" + isgreat + ") ");
			}
		}

		if (trunks != null && !"".equals(trunks.trim())) {
			sb.append(" and cable.trouble_reply_id=reply.id and cable.trouble_cable_line_id in(" + trunks + ")");
		}
		if (eomscode != null && !"".equals(eomscode.trim())) {
			sb.append(" and eoms.trouble_id = t.id and eoms.eoms_code like '%" + eomscode + "%' ");
		}
		//是否取消
		if(StringUtils.isNotBlank(troubleState)){
			if(StringUtils.equals(troubleState, PLAN_CANCEL)){
				sb.append(" and t.trouble_state='");
				sb.append(troubleState);
				sb.append("'");
			} else {
				sb.append(" and (t.trouble_state<>'");
				sb.append(PLAN_CANCEL);
				sb.append("' or t.trouble_state is null)");
			}
		}
		if (conid != null && !"".equals(conid)) {
			sb.append(" and unit.process_unit_id=?");
			values.add(conid);
		}
		if (startTime != null && !"".equals(startTime)) {
			sb.append("and t.trouble_start_time>=to_date('" + startTime.trim() + " 00:00:00','yyyy/MM/dd hh24:mi:ss')");
		}
		if (endtime != null && !"".equals(endtime)) {
			sb.append("and t.trouble_start_time<=to_date('" + endtime.trim() + " 23:59:59','yyyy/MM/dd hh24:mi:ss')");
		}
		if (deptype.equals("1")) {
			sb.append(" and c.regionid=?");
			values.add(user.getRegionID());
		}
		if (deptype.equals("2")) {
			sb.append(" and unit.process_unit_id=?");
			values.add(user.getDeptID());
		}
		if (act != null && act.equals("stat")) {
			sb.append(" and reply.approve_state=?");
			values.add(TroubleConstant.REPLY_APPROVE_PASS);
		}
		if (act != null) {
			if (act.equals("general")) {
				sb.append(" and t.is_great_trouble=?");
				values.add(TroubleConstant.IS_GREAT_TROUBLE_N);
			}
			if (act.equals("great")) {
				sb.append(" and t.is_great_trouble=?");
				values.add(TroubleConstant.IS_GREAT_TROUBLE_Y);
			}
		}
		if (bean.getTaskStates() != null) {
			String[] taskStates = bean.getTaskStates();
			sb.append(" and exists( ");
			sb.append(" select dbid_ from jbpm4_task jbpm_task ");
			sb.append(" where jbpm_task.execution_id_='");
			sb.append(TroubleWorkflowBO.TROUBLE_WORKFLOW);
			sb.append(".'||unit.id ");
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
		sb.append(" order by trouble_send_time desc");
		System.out.println("=======query============   " + sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 查询与返回单关联的中继段
	 * @return
	 */
	public StringBuffer queryTrunkCondition() {
		StringBuffer sb = new StringBuffer();
		sb.append(", SUBSTR (");
		sb.append("(SELECT   xx.segmentname ");
		sb.append(" FROM   (select trouble_reply_id, wmsys.wm_concat(cseg.segmentname) segmentname ");
		sb.append(" FROM   lp_trouble_cable_info it, repeatersection cseg");
		sb.append(" WHERE   it.trouble_cable_line_id = cseg.kid  group by it.trouble_reply_id) xx");
		sb.append(" WHERE   xx.trouble_reply_id = reply.id),");
		sb.append(" 0, 100) trunk ");
//		解决 ORA-30004: 使用 SYS_CONNECT_BY_PATH 函数时, 不能将分隔符作为列值的一部分
		//使用wmsys.wm_concat替换sys_connect_by_path
		
//		sb.append(", substr((select xx.segmentname ");
//		sb.append(" from ( SELECT DISTINCT yy.trouble_reply_id, ltrim(first_value(path)");
//		sb.append(" over(PARTITION BY yy.trouble_reply_id ORDER BY yy.lev DESC), ';') segmentname ");
//		sb.append("  FROM (SELECT tt.*,LEVEL lev, sys_connect_by_path(segmentname, ';') path ");
//		sb.append("  FROM (SELECT it.trouble_reply_id ||");
//		sb.append("  (row_number() over(PARTITION BY it.trouble_reply_id ORDER BY");
//		sb.append("  it.trouble_reply_id)) a,");
//		sb.append(" it.trouble_reply_id ||");
//		sb.append("  (row_number() over(PARTITION BY it.trouble_reply_id ORDER BY");
//		sb.append("   it.trouble_reply_id) - 1) b,");
//		sb.append("  it.*,cseg.segmentname");
//		sb.append("  FROM lp_trouble_cable_info it,repeatersection cseg");
//		sb.append(" where it.trouble_cable_line_id=cseg.kid  ) tt");
//		sb.append(" CONNECT BY PRIOR a = b) yy) xx");
//		sb.append("   where xx.trouble_reply_id =reply.id),0,100) trunk ");
		return sb;
	}

	//查询为反馈或者协办的单子
	public StringBuffer getTroublesNoReply(String eomscode, UserInfo user) {
		String deptype = user.getDeptype();
		String deptid = user.getDeptID();
		StringBuffer sb = new StringBuffer();
		sb
				.append("select distinct t.id,t.trouble_state trouble_state, t.trouble_id,t.trouble_name,'' replyid,'' contractorname,");
		sb.append(" to_char(t.trouble_send_time,'yyyy-mm-dd hh24:mi:ss') trouble_send_time,");
		sb.append(" '' trouble_end_time,null as etime, null replytime, ");
		sb.append(" '' teraddr,'' trouble_type ,t.trouble_remark,'' impress_type,'' trunk ");
		sb.append(" from lp_trouble_info t,lp_trouble_process_unit unit,contractorinfo c");
		if (eomscode != null && !"".equals(eomscode.trim())) {
			sb.append(",lp_trouble_eoms_code eoms ");
		}
		sb.append(" where unit.trouble_id=t.id  and c.contractorid=unit.process_unit_id ");
		sb.append(" and t.id not in(select reply.trouble_id from lp_trouble_reply reply ");
		sb.append(" where reply.trouble_id=t.id ");
		sb.append(" and reply.confirm_result='" + TroubleConstant.REPLY_ROLE_HOST + "'");
		if (deptype.equals("2")) {
			sb.append("  and reply.contractor_id='" + deptid + "')");
		} else {
			sb.append(" )");
		}
		if (eomscode != null && !"".equals(eomscode.trim())) {
			sb.append(" and eoms.trouble_id = t.id and eoms.eoms_code like '%" + eomscode + "%' ");
		}
		return sb;
	}

	/**
	 * 根据条件查询故障信息(三级快捷菜单的条件)
	 * @param condition
	 * @return
	 */
	public List getTroubleByCondition(UserInfo user, String condition) {
		String deptype = user.getDeptype();
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct t.id, t.trouble_id,t.trouble_name,reply.id replyid,c.contractorname,");
		sb.append(" to_char(t.trouble_send_time,'yyyy-mm-dd hh24:mi:ss') trouble_send_time,");
		sb.append(" to_char(reply.trouble_end_time,'yyyy-mm-dd hh24:mi:ss') trouble_end_time, ");
		sb.append(" trunc((reply.renew_time - reply.trouble_accept_time)*24 ,2) as etime, ");//抢修历时=故障恢复时间-故障受理时间
		sb.append(" trunc((reply.reply_submit_time - t.trouble_send_time)*24 ,2) as replytime, ");
		sb.append(" dfaddr.lable teraddr,dftype.lable trouble_type,");
		sb.append(" t.trouble_remark ,reply.impress_type ");
		//sb.append(" ,'' trunk ");
		sb.append(queryTrunkCondition());
		sb.append(" from lp_trouble_info t,lp_trouble_process_unit unit,");
		sb.append(" lp_trouble_reply reply,contractorinfo c, ");
		sb.append(" dictionary_formitem dfaddr,dictionary_formitem dftype ");
		sb.append(" where t.id=reply.trouble_id and unit.trouble_id=t.id  ");
		sb.append(" and c.contractorid=reply.contractor_id and reply.contractor_id=unit.process_unit_id ");
		sb.append(" and reply.confirm_result='" + TroubleConstant.REPLY_ROLE_HOST + "'");
		sb.append(" and dfaddr.code=reply.terminal_address and dfaddr.assortment_id=? ");
		values.add(TroubleConstant.ASSORTMENT_TERMINAL_ADDR);
		sb.append(" and dftype.code=reply.trouble_type and dftype.assortment_id=? ");
		values.add(TroubleConstant.ASSORTMENT_TROUBLE_TYPE);
		if (condition.equals("general")) {
			sb.append(" and t.is_great_trouble=?");
			values.add(TroubleConstant.IS_GREAT_TROUBLE_N);
		}
		if (condition.equals("great")) {
			sb.append(" and t.is_great_trouble=?");
			values.add(TroubleConstant.IS_GREAT_TROUBLE_Y);
		}
		if (condition.equals("city")) {
			sb.append(" and reply.terminal_address=?");
			values.add(TroubleConstant.TERMINAL_ADDRESS_CITY);
		}
		if (condition.equals("outskirt")) {
			sb.append(" and reply.terminal_address=?");
			values.add(TroubleConstant.TERMINAL_ADDRESS_OUTSKIRT);
		}
		if (deptype.equals("1")) {
			sb.append(" and c.regionid=?");
			values.add(user.getRegionID());
		}
		if (deptype.equals("2")) {
			sb.append(" and unit.process_unit_id=?");
			values.add(user.getDeptID());
		}
		sb.append(" order by trouble_send_time desc");
		System.out.println("====ssss=============== " + sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 解析字符串为'',''分割，可以直接用于sql中
	 * @param str
	 * @return
	 */
	public String arrayParseToStr(String[] str) {
		String sqlstr = "";
		if (str != null && str.length > 0) {
			for (int i = 0; i < str.length; i++) {
				if (i == 0) {
					sqlstr += "'" + str[i] + "'";
				} else {
					sqlstr += ",'" + str[i] + "'";
				}
			}
		}
		return sqlstr;
	}
	
	/**
	 * 通过PDA查询各代维流程节点数
	 * @param userInfo
	 * @return
	 */
	public List<Map<String, Object>> queryTroubleNumFromPDA(UserInfo userInfo) {
		String sql = "select count(*) num,'0' state,(select con.contractorname from contractorinfo con where con.contractorid=U.PROCESS_UNIT_ID) contractorName from lp_trouble_info t,lp_trouble_process_unit u where T.TROUBLE_STATE not in ('40','50') and T.REGION_ID='"
				+ userInfo.getRegionID()
				+ "' and T.TROUBLE_STATE is not null and T.ID=U.TROUBLE_ID group by U.PROCESS_UNIT_ID "
				+ "union select count(*) num,'1' state,(select con.contractorname from contractorinfo con where con.contractorid=U.PROCESS_UNIT_ID) contractorName from lp_trouble_info t,lp_trouble_process_unit u where T.TROUBLE_STATE ='0' and T.REGION_ID='"
				+ userInfo.getRegionID()
				+ "' and T.ID=U.TROUBLE_ID group by U.PROCESS_UNIT_ID "
				+ "union select count(*) num,'2' state,(select con.contractorname from contractorinfo con where con.contractorid=U.PROCESS_UNIT_ID) contractorName from lp_trouble_info t,lp_trouble_process_unit u where to_char(T.TROUBLE_SEND_TIME,'yyyy-MM')=to_char(sysdate,'yyyy-MM') and T.REGION_ID='"
				+ userInfo.getRegionID()
				+ "' and T.ID=U.TROUBLE_ID group by U.PROCESS_UNIT_ID "
				+ "union select count(*) num,'3' state,(select con.contractorname from contractorinfo con where con.contractorid=U.PROCESS_UNIT_ID) contractorName from lp_trouble_info t,lp_trouble_process_unit u where to_char(T.TROUBLE_SEND_TIME,'yyyy-MM')=to_char(sysdate,'yyyy-MM') and T.REGION_ID='"
				+ userInfo.getRegionID()
				+ "' and T.TROUBLE_STATE='50' and T.ID=U.TROUBLE_ID group by U.PROCESS_UNIT_ID order by state,contractorName";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 通过PDA查询待处理故障
	 * @param userInfo
	 * @return
	 */
	public List<Map> queryTroubleFromPDA(UserInfo userInfo) {
		String sql = "SELECT L.TROUBLE_NAME troubleName,decode(L.IS_GREAT_TROUBLE,'0','否','1','是') isGreatTrouble,c.contractorname contractorName,r.contractor_id contractorId,decode(p.state,'0','处理中','1','处理结束') unitState "
				+ "FROM LP_TROUBLE_INFO L,lp_trouble_reply r,lp_trouble_process_unit p,contractorinfo c "
				+ "where c.contractorid=r.contractor_id and L.REGION_ID='"
				+ userInfo.getRegionID()
				+ "' and L.TROUBLE_STATE in('0','1','20') and sysdate-L.TROUBLE_SEND_TIME<=7 and L.id=r.trouble_id and p.trouble_id=l.id order by r.contractor_id";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 通过PDA查询所有待审核的故障
	 * @param userInfo
	 * @return
	 */
	public List<Map> queryApproveTroubleFromPDA(UserInfo userInfo) {
		String sql = "SELECT L.TROUBLE_NAME troubleName,decode(L.IS_GREAT_TROUBLE,'0','否','1','是') isGreatTrouble,c.contractorname contractorName,r.contractor_id contractorId "
				+ "FROM LP_TROUBLE_INFO L,lp_trouble_reply r,lp_reply_approver approver,contractorinfo c "
				+ "where c.contractorid=r.contractor_id and  L.REGION_ID='"
				+ userInfo.getRegionID()
				+ "' and L.TROUBLE_STATE='30' and L.id=r.trouble_id AND L.id = r.trouble_id and approver.object_type='LP_TROUBLE_REPLY' and approver.approver_id='"
				+ userInfo.getUserID() + "' and approver.object_id=r.id order by r.contractor_id";
		return this.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 通过PDA查询所有待审核的故障
	 * @param userInfo
	 * @return
	 */
	public List<Map> queryApprovalTroubleFromPDA(UserInfo userInfo) {
		String sql = "SELECT L.TROUBLE_NAME troubleName,decode(L.IS_GREAT_TROUBLE,'0','否','1','是') isGreatTrouble,c.contractorname contractorName,r.contractor_id contractorId "
				+ "FROM LP_TROUBLE_INFO L,lp_trouble_reply r,lp_reply_approver approver,contractorinfo c "
				+ "where c.contractorid=r.contractor_id and  L.REGION_ID='"
				+ userInfo.getRegionID()
				+ "' and L.TROUBLE_STATE='10' and L.id=r.trouble_id AND L.id = r.trouble_id and approver.object_type='LP_TROUBLE_REPLY' and approver.approver_id='"
				+ userInfo.getUserID() + "' and approver.object_id=r.id order by r.contractor_id";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	
	public List<Map> queryEOMSTroubleFromPDA(UserInfo userInfo) {
		String sql = "select i.trouble_name troubleName,i.trouble_start_time troubleStartTime,I.TROUBLE_SEND_TIME troubleSendTime,decode(i.is_great_trouble,'0','是','1','否') isGreatTrouble,decode(i.trouble_state,'0','待故障反馈','10','待平台核准','30','待审核反馈','40','待考核评分') troubleState  "
				+ "from lp_trouble_info i "
				+ "where I.SEND_MAN_ID like '%eoms%' and I.TROUBLE_STATE <>'50' and I.TROUBLE_STATE is not null and i.region_id='"
				+ userInfo.getRegionID() + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 获得代维单位
	 * @param userInfo
	 * @return
	 */
	public List<Map<String,String>> getContractorForList(UserInfo userInfo){
		String sql="select contractorId,contractorname from contractorinfo con where CON.REGIONID='"+userInfo.getRegionID()+"'";
		return this.getJdbcTemplate().queryForList(sql);
	}
}
