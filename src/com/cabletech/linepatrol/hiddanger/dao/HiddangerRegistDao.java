package com.cabletech.linepatrol.hiddanger.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.hiddanger.workflow.HiddangerWorkflowBO;
import com.cabletech.linepatrol.hiddanger.beans.QueryBean;
import com.cabletech.linepatrol.hiddanger.model.HiddangerRegist;

@Repository
public class HiddangerRegistDao extends HibernateDao<HiddangerRegist, String> {
	private static final String PLAN_CANCEL = "999";

//	public long getNumberCount(String number) {
//		String hql = "from HiddangerRegist r where r.hiddangerNumber = ?";
//		return countHqlResult(hql, number);
//	}
	
	public List<HiddangerRegist> getHiddangers4dept(String dept) {
		String hql = "from HiddangerRegist r where to_char(createTime,'yyyy') = to_char(sysdate,'yyyy') and treatDepartment= ?";
		return find(hql,dept);
	}

	public long getIdCount(String id) {
		String hql = "from HiddangerRegist r where r.id = ?";
		return countHqlResult(hql, id);
	}

	public List<Map> getTroubleCodeList(String typeId) {
		String sql = "select c.id,c.troublename from troubletype t,troublecode c where c.troubletype = t.id and t.id = '"
				+ typeId + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List<Map> getDeptOptions(String deptType, String deptId) {
		String sql = "select contractorid,contractorname from contractorinfo where state is null and linkmaninfo is not null";
		if (!deptType.equals("1")) {
			sql += " and contractorid='" + deptId + "'";
		}
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List<Map> getPrincipalOptions(String deptId) {
		String sql = "select name,mobile from contractorperson where contractorid = '"
				+ deptId + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List<Map> getCodes() {
		String hql = "select id, troublename from troublecode";
		return this.getJdbcTemplate().queryForList(hql);
	}

	public List<Map> getTypes() {
		String hql = "select id, typename from troubletype";
		return this.getJdbcTemplate().queryForList(hql);
	}

	public List<Map> getDepts() {
		String hql = "select contractorid, contractorname from contractorinfo";
		return this.getJdbcTemplate().queryForList(hql);
	}

	public List<Map> getUsers() {
		String hql = "select userid, username from userinfo";
		return this.getJdbcTemplate().queryForList(hql);
	}

	public List<Map> getHiddangerPrincipal(String deptId) {
		String sql = "select linkmaninfo from contractorinfo where contractorid = '"
				+ deptId + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}

	public String getPhoneFromUserid(String userId) {
		String sql = "select phone from userinfo where userid='" + userId + "'";
		return (String) this.getJdbcTemplate()
				.queryForObject(sql, String.class);
	}

	public List<HiddangerRegist> getHiddangerList() {
		String hql = "from HiddangerRegist r where r.hiddangerState = '00'";
		return find(hql);
	}

	public String getUserName(String userId) {
		String sql = "select username from userinfo where userid='" + userId
				+ "'";
		return (String) this.getJdbcTemplate()
				.queryForObject(sql, String.class);
	}

	public List<HiddangerRegist> getTerminalResult(String deptId) {
		String hql = "from HiddangerRegist r where r.treatDepartment = ? and terminnalState = 0";
		return find(hql, deptId);
	}

	public List<HiddangerRegist> getQueryResult(QueryBean queryBean,
			UserInfo userInfo) {
		StringBuffer hql = new StringBuffer();
		hql.append("from HiddangerRegist hm where hm.terminnalState = 1 ");
		List<String> param = new ArrayList<String>();

		if (StringUtils.isNotBlank(queryBean.getName())) {
			hql.append("and hm.name like '%");
			hql.append(queryBean.getName());
			hql.append("%' ");
		}
		if (StringUtils.isNotBlank(queryBean.getType())) {
			hql.append("and hm.type = ? ");
			param.add(queryBean.getType());
		}
		if (StringUtils.isNotBlank(queryBean.getCode())) {
			hql.append("and hm.code = ? ");
			param.add(queryBean.getCode());
		}
		String level = queryBean.getHiddangerLevel();
		if (level != null) {
			hql.append("and hm.hiddangerLevel in (" + level + ")");
		}
		// if(StringUtils.isNotBlank(queryBean.getHiddangerLevel())){
		// hql.append("and hm.hiddangerLevel = ? ");
		// for(String s:queryBean.getHiddangerLevel()){
		// param.add(s);
		// }
		// }
		String state = queryBean.getHiddangerState();
		if (state != null) {
			hql.append("and hm.hiddangerState in (" + state + ")");
		}
		// if(StringUtils.isNotBlank(queryBean.getHiddangerState())){
		// String[] state = queryBean.getHiddangerState();
		// if(state.indexOf(",") != -1){
		// state = "'" + state.replace(",", "','") + "'";
		// hql.append("and hm.hiddangerState in (");
		// hql.append(state);
		// hql.append(") ");
		// }else{
		// hql.append("and hm.hiddangerState = ? ");
		// param.add(queryBean.getHiddangerState());
		// }
		// }
		// 开始结束均不为空
		// 开始日期空，结束日期不为空
		// 开始不为空，结束日期为空
		// 开始结束均为空
		if (StringUtils.isNotBlank(queryBean.getBegintime())) {
			hql
					.append("and hm.createTime >= to_Date(?,'yyyy/MM/dd HH24:mi:ss') ");
			param.add(queryBean.getBegintime());

			if (StringUtils.isBlank(queryBean.getEndtime())) {
				hql.append("and hm.createTime <= sysdate ");
			} else {
				hql
						.append("and hm.createTime <= to_Date(?,'yyyy/MM/dd HH24:mi:ss') ");
				param.add(queryBean.getEndtime());
			}
		} else {
			if (StringUtils.isNotBlank(queryBean.getEndtime())) {
				hql
						.append("and hm.createTime <= to_Date(?,'yyyy/MM/dd HH24:mi:ss') ");
				param.add(queryBean.getEndtime());
			}
		}
		if (userInfo.getDeptype().equals("2")) {
			hql.append("and hm.treatDepartment = ? ");
			param.add(userInfo.getDeptID());
		}
		if (queryBean.getTaskStates() != null) {
			String[] taskStates = queryBean.getTaskStates();
			hql.append(" and exists( ");
			hql
					.append(" select dbid from org.jbpm.pvm.internal.task.TaskImpl jbpm_task ");
			hql.append(" where jbpm_task.executionId='");
			hql.append(HiddangerWorkflowBO.HIDDANGER_WORKFLOW);
			hql.append(".'||hm.id ");
			hql.append(" and ( ");
			for (int i = 0; i < taskStates.length; i++) {
				hql.append(" jbpm_task.name='");
				hql.append(taskStates[i] + "' ");
				if (i < taskStates.length - 1) {
					hql.append(" or ");
				}
			}
			hql.append(" ) ");
			hql.append(" ) ");
		}
		//隐患处理单位
		if(StringUtils.isNotBlank(queryBean.getTreatDepartment())){
			hql.append(" and hm.treatDepartment='");
			hql.append(queryBean.getTreatDepartment());
			hql.append("' ");
		}
		//是否取消
		String hideDangerState = queryBean.getHideDangerState();
		if(StringUtils.isNotBlank(hideDangerState)){
			if(StringUtils.equals(hideDangerState, PLAN_CANCEL)){
				hql.append(" and hm.hideDangerState='");
				hql.append(hideDangerState);
				hql.append("'");
			} else {
				hql.append(" and (hm.hideDangerState<>'");
				hql.append(PLAN_CANCEL);
				hql.append("' or hm.hideDangerState is null)");
			}
		}
		hql.append("order by hm.createTime desc");
		return this.find(hql.toString(), param.toArray());
	}

	public List<HiddangerRegist> getQueryStat(QueryBean queryBean) {
		List<String> param = new ArrayList<String>();
		StringBuffer hql = new StringBuffer();
		hql.append("from HiddangerRegist hm where 1=1 ");

		if (StringUtils.isNotBlank(queryBean.getName())) {
			hql.append("and hm.name like '%");
			hql.append(queryBean.getName());
			hql.append("%' ");
		}
		if (StringUtils.isNotBlank(queryBean.getType())) {
			hql.append("and hm.type = ? ");
			param.add(queryBean.getType());
		}
		if (StringUtils.isNotBlank(queryBean.getCode())) {
			hql.append("and hm.code = ? ");
			param.add(queryBean.getCode());
		}
		hql
				.append("and hm.splanId is not null and hm.splanId in (select wps.specPlanId from WatchPlanStat wps where 1=1 ");
		if (StringUtils.isNotBlank(queryBean.getBegintime())) {
			hql.append("and wps.factDate >= to_Date(?,'yyyy/MM/dd') ");
			param.add(queryBean.getBegintime());

			if (StringUtils.isBlank(queryBean.getEndtime())) {
				hql.append("and wps.factDate <= sysdate ");
			} else {
				hql.append("and wps.factDate <= to_Date(?,'yyyy/MM/dd') ");
				param.add(queryBean.getEndtime());
			}
		} else {
			if (StringUtils.isNotBlank(queryBean.getEndtime())) {
				hql.append("and wps.factDate <= to_Date(?,'yyyy/MM/dd') ");
				param.add(queryBean.getEndtime());
			}
		}
		hql.append(") ");
		hql.append("order by hm.createTime desc");
		return this.find(hql.toString(), param.toArray());
	}

	public List<HiddangerRegist> getHandeledWorks(UserInfo userInfo) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("select distinct hm from HiddangerRegist hm,ProcessHistory ph where hm.id = ph.objectId and ph.objectType = '");
		sb.append(ModuleCatalog.HIDDTROUBLEWATCH);
		sb.append("' and ph.operateUserId = '");
		sb.append(userInfo.getUserID());
		sb.append("' ");
		return find(sb.toString());
	}
	
	public List<HiddangerRegist> getHiddangerRegistByIds(String ids){
		String sql="from HiddangerRegist where id in("+ids+") order by id";
		return this.find(sql);
	}
	/**
	 * 通过PDA查询本月隐患的处理情况
	 * @param userInfo
	 * @return
	 */
	public List<Map> getHiddangerNumFromPDA(UserInfo userInfo,String hiddangerLevel) {
		String sql = "select count(*) num,'0' state,(select contractorname from contractorinfo where contractorid=R.TREAT_DEPARTMENT) contractorName from lp_hiddanger_regist r where R.HIDDANGER_STATE in ('10','20','30','40','50','51','52','53','54','60') and r.hiddanger_level='"+hiddangerLevel+"' and r.regionid='"
				+ userInfo.getRegionID()
				+ "' and R.HIDDANGER_LEVEL is not null group by grouping sets((r.hiddanger_level,r.TREAT_DEPARTMENT)) "
				+ "union select count(*) num,'1' state,(select contractorname from contractorinfo where contractorid=R.TREAT_DEPARTMENT) contractorname from lp_hiddanger_regist r where R.HIDDANGER_STATE='53' and r.hiddanger_level='"+hiddangerLevel+"' and r.regionid='"
				+ userInfo.getRegionID()
				+ "' and R.HIDDANGER_LEVEL is not null group by grouping sets((r.hiddanger_level,R.TREAT_DEPARTMENT)) "
				+ "union select count(*) num,'2' state,(select contractorname from contractorinfo where contractorid=R.TREAT_DEPARTMENT) contractorname from lp_hiddanger_regist r where to_char(R.CREATE_TIME,'yyyy-MM')=to_char(sysdate,'yyyy-MM') and r.hiddanger_level='"+hiddangerLevel+"' and r.regionid='"
				+ userInfo.getRegionID()
				+ "' group by grouping sets((r.hiddanger_level,R.TREAT_DEPARTMENT)) "
				+ "union select count(*) num,'3' state,(select contractorname from contractorinfo where contractorid=R.TREAT_DEPARTMENT) contractorname from lp_hiddanger_regist r where to_char(R.CREATE_TIME,'yyyy-MM')=to_char(sysdate,'yyyy-MM') and r.hiddanger_level='"+hiddangerLevel+"' and r.regionid='"
				+ userInfo.getRegionID()
				+ "' and R.HIDDANGER_STATE in ('70','00','0') group by grouping sets((r.hiddanger_level,R.TREAT_DEPARTMENT)) order by state,contractorName";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	
	/**
	 * 通过PDA查询现在正在执行的盯防
	 * @param userInfo
	 * @return
	 */
	public List<Map> getSpecialFromPDA(UserInfo userInfo, String contractorId, String hiddangerLevel) {
		String sql = "SELECT distinct regist.id id,R.DISTANCE_TO_CABLE tanceToCable,patrol.PATROLNAME patrolName,regist.name hiddangerName,DECODE (regist.hiddanger_level,'0','忽略','1','1级','2','2级','3','3级','4','4级') hiddangerLevel,r.watch_principal watchPrincipal,r.watch_principal_phone watchPrincipalPhone,con.contractorname contractorname "
				+ "FROM lp_special_plan special,lp_hiddanger_plan p,lp_hiddanger_report r,userinfo u,lp_hiddanger_regist regist,contractorinfo con,lp_special_watch w,patrolmaninfo patrol "
				+ "WHERE  p.plan_id=w.plan_id and w.patrol_group_id=patrol.PATROLID and regist.TREAT_DEPARTMENT like '%"
				+ contractorId
				+ "%' and regist.hiddanger_level like '%"
				+ hiddangerLevel
				+ "%' and SPECIAL .CREATOR = U.USERID AND U.DEPTID = CON.CONTRACTORID AND SPECIAL.REGION_ID = '"
				+ userInfo.getRegionID()
				+ "' AND P.PLAN_ID = SPECIAL.ID AND P.HIDDANGER_ID = R.HIDDANGER_ID AND REGIST.ID = P.HIDDANGER_ID ";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
}