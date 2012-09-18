package com.cabletech.planstat.services;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.planstat.beans.PlanExeResultBean;
import com.cabletech.planstat.dao.PlanExeDAOImpl;
import com.cabletech.planstat.dao.PlanExeResultDAO;
import com.cabletech.planstat.domainobjects.PlanStat;

public class PlanExeResultBOImpl implements PlanExeResultBO {
	private Logger logger = Logger.getLogger(PlanExeResultBOImpl.class
			.getName());

	private String sql = null;

	private PlanExeResultDAO planExeResultDAO = null;

	public PlanExeResultBOImpl() {
		planExeResultDAO = new PlanExeDAOImpl();
	}

	public PlanStat getPlanStat(String planid) throws Exception {
		PlanStat planstat = planExeResultDAO.getPlanStat(planid);
		String sql = "select planname,patrolmode from plan where id='" + planid + "'";
		List list = planExeResultDAO.getBackInfoList(sql);
		DynaBean plan = (DynaBean) list.get(0);
		planstat.setPlanname(plan.get("planname").toString());
		planstat.setPatrolmode(plan.get("patrolmode").toString());
		return planstat;
	}

	// �õ����������Ĵ�ά��λ�б�
	public List getContractorInfoList(UserInfo userinfo) {
		if (userinfo.getType().equals("11")) {
			sql = "select con.contractorid,con.contractorname"
					+ " from contractorinfo con" + " where con.STATE is null"
					+ " order by con.contractorname";
		} else if (userinfo.getType().equals("12")) {
			sql = "select con.contractorid,con.contractorname"
					+ " from contractorinfo con" + " where con.STATE is null"
					+ " and con.regionid='" + userinfo.getRegionID() + "'"
					+ " order by con.contractorname";
		} else if (userinfo.getType().equals("22")) {
			sql = "select con.contractorid,con.contractorname"
					+ " from contractorinfo con" + " where con.STATE is null"
					+ " and con.regionid='" + userinfo.getRegionID() + "'"
					+ " and con.contractorid='" + userinfo.getDeptID() + "'";
		}
		logger.info("��ά��λ�б�SQL:" + sql);
		List list = planExeResultDAO.getBackInfoList(sql);
		return list;
	}

	// �õ�����������Ѳ��Ա�б�
	public List getPatrolmanInfoList(UserInfo userinfo) {
		if (userinfo.getType().equals("12")) {
			sql = "select p.PATROLID, p.PATROLNAME, p.PARENTID "
					+ " from patrolmaninfo p " + " where p.STATE is null "
					+ " and p.regionid='" + userinfo.getRegionID() + "'"
					+ " order by p.parentid,p.patrolname";
		} else if (userinfo.getType().equals("22")) {
			sql = "select p.PATROLID, p.PATROLNAME, p.PARENTID "
					+ " from patrolmaninfo p " + " where p.STATE is null "
					+ " and p.regionid='" + userinfo.getRegionID() + "'"
					+ " and p.parentid='" + userinfo.getDeptID() + "'"
					+ " order by p.parentid,p.patrolname";
		}
		logger.info("Ѳ��Ա�б�SQL:" + sql);
		List list = planExeResultDAO.getBackInfoList(sql);
		return list;
	}

	// �õ�ִ�мƻ����
	public List getPlanExeResult(UserInfo userinfo, PlanExeResultBean bean) {
		String theStartDate = bean.getEndYear() + "-" + bean.getEndMonth()
				+ "-1";
		if (StringUtils.isNotBlank(bean.getPatrolID())) {
			sql = "select p.planid, p.planname, c.contractorname, p2.patrolname,"
					+ "to_char(p.startdate,'YYYY-MM-DD') startdate,"
					+ "to_char(p.enddate,'YYYY-MM-DD') enddate,examineresult "
					+ " from plan_stat p,"
					+ " ( select ps.patrolid, ps.parentid, ps.patrolname"
					+ " from patrolmaninfo ps"
					+ " where ps.patrolid = '"
					+ bean.getPatrolID()
					+ "'"
					+ " and ps.state is null )p2,"
					+ " contractorinfo c"
					+ " where c.state is null"
					+ " and p.executorid = '"
					+ bean.getPatrolID()
					+ "'"
					+ " and (p.enddate >= TO_DATE ('"
					+ theStartDate
					+ "', 'yyyy-mm-dd') AND p.enddate <= last_day(TO_DATE ('"
					+ theStartDate
					+ "', 'yyyy-mm-dd')))"
					+ " and c.contractorid = p2.parentid"
					+ "  order by p.startdate";
		} else {
			sql = "select p.planid, p.planname, c.contractorname, p2.patrolname,"
					+ "to_char(p.startdate,'YYYY-MM-DD') startdate,"
					+ "to_char(p.enddate,'YYYY-MM-DD') enddate,examineresult "
					+ " from plan_stat p,"
					+ " ( select ps.patrolid, ps.parentid, ps.patrolname"
					+ " from patrolmaninfo ps"
					+ " where  ps.state is null )p2,"
					+ " contractorinfo c "
					+ "where c.state is null "
					+ "and p.executorid = p2.patrolid "					
					+ " and (p.enddate >= TO_DATE ('"
					+ theStartDate
					+ "', 'yyyy-mm-dd') AND p.enddate <= last_day(TO_DATE ('"
					+ theStartDate
					+ "', 'yyyy-mm-dd')))"
					+ "and c.contractorid = p2.parentid "
					+ "AND p2.PARENTID = c.CONTRACTORID ";
					if(bean.getContractorID() != null && !"".equals(bean.getContractorID())) {
						sql += " AND c.contractorid = '" + bean.getContractorID() + "' ";
					}
					sql += " order by p.startdate";
		}
		logger.info("ִ�мƻ����SQL:" + sql);
		List list = planExeResultDAO.getBackInfoList(sql);
		return list;
	}

	// �õ��ƻ��������߶�
	public List getSubLineInfo(String strPlanID) {
		sql = "select li.linename,lc.name linetype,pm.patrolname,s.sublinename,ps.planpointn,ps.planpoint,ps.factpointn,ps.factpoint,ps.leakpoint,ps.examineresult,ps.PATROLP || '%' as patrolp "
				+ " from plan_statsubline ps, sublineinfo s,lineinfo li,lineclassdic lc,patrolmaninfo pm "
				+ " where s.lineid=li.lineid and s.patrolid=pm.patrolid and li.linetype=lc.code "
				+"  and ps.sublineid = s.sublineid  and s.state is null and ps.planid='"
				+ strPlanID + "'" + " order by ps.patrolp,ps.examineresult desc";
		List list = planExeResultDAO.getBackInfoList(sql);
		logger.info("�õ��ƻ��������߶�SQL:" + sql);
		return list;

	}

	// �õ�ѡ���ƻ���Ѳ����ϸ
	public List getPatrolList(String strPlanID) {
		sql = "select l.linename,s.sublinename,p.pointname,p.addressinfo,"
				+ "to_char(ps.patroldate,'YYYY-MM-DD hh24:mi:ss') patroldate,"
				+ "decode(p.isfocus, '0', '��', '1', '��') as isfocus"
				+ " from plan_statpatroldad ps, sublineinfo s, pointinfo p, lineinfo l"
				+ " where ps.pointid = p.pointid"
				+ " and s.sublineid = p.sublineid" + " and l.lineid = s.lineid"
				+ " and l.state is null" + " and s.state is null"
				+ " and ps.planid = '" + strPlanID + "'"
				+ " order by ps.patroldate";
		List list = planExeResultDAO.getBackInfoList(sql);
		logger.info("�õ�ѡ���ƻ���Ѳ����ϸSQL:" + sql);
		return list;
	}

	// �õ�ѡ���ƻ���©����ϸ
	public List getLeakList(String strPlanID) {
		sql = "select l.linename,s.sublinename,p.pointname,p.addressinfo,"
				+ "ps.executetimes,ps.leaktime,"
				+ "decode(p.isfocus, '0', '��', '1', '��') as isfocus"
				+ " from plan_statleakdad ps, sublineinfo s, pointinfo p, lineinfo l"
				+ " where ps.pointid = p.pointid"
				+ " and s.sublineid = p.sublineid" + " and l.lineid = s.lineid"
				+ " and l.state is null" + " and s.state is null"
				+ " and ps.planid = '" + strPlanID + "'"
				+ "  order by l.linename";
		List list = planExeResultDAO.getBackInfoList(sql);
		logger.info("�õ�ѡ���ƻ���©����ϸSQL:" + sql);
		return list;
	}

	// �õ���ѡ�ƻ��ľ��幤����
	public List getWorkdayList(String strPlanID) {
		sql = "select to_char(ps.patroldate,'yyyy-mm-dd') patroldate ,count(ps.pointid) pointnum "
				+ "from plan_statpatroldad ps "
				+ "where ps.planid = '"
				+ strPlanID
				+ "' "
				+ "group by to_char(ps.patroldate,'yyyy-mm-dd') "
				+ "order by patroldate";
		List list = planExeResultDAO.getBackInfoList(sql);
		logger.info("�õ�ѡ���ƻ��ľ��幤����SQL:" + sql);
		return list;
	}

	// �õ���ѡ�ƻ��ľ��幤���յ���ϸ��Ϣ
	public List getWorkdayText(String strPlanID, String strPatrolDate) {
		sql = "select to_char(ps.patroldate,'YYYY-MM-DD hh24:mi:ss') patroldate,p.pointname,p.addressinfo,s.sublinename,"
				+ "decode(p.isfocus, '1', '��', '') as isfocus,"
				+ "decode(ps.ismodified, 'Y', '��', '') as ismodified "
				+ "from plan_statpatroldad ps, pointinfo p, sublineinfo s "
				+ "where ps.pointid = p.pointid "
				+ "and p.sublineid = s.sublineid "
				+ "and p.state is not null "
				+ "and s.state is null "
				+ "and ps.planid='"
				+ strPlanID
				+ "' "
				+ "and to_char(ps.patroldate,'YYYY-MM-DD') = '"
				+ strPatrolDate
				+ "' " + "order by ps.patroldate ";
		List list = planExeResultDAO.getBackInfoList(sql);
		logger.info("�õ�ѡ���ƻ��ľ��幤���յ���ϸ��ϢSQL:" + sql);
		return list;
	}
	
	/**
	 * ����ƻ�δ��ɵ�ԭ��
	 * @param planid �ƻ�id
	 * @param reason ԭ��
	 * @return ���³ɹ����ı�־λ
	 */
	public boolean saveReason(String planid, String reason) {
		
		String sqlStr = "update plan_stat set nofinishreason = '" + reason + "' where planid = '" + planid + "'";
		boolean returnFlg = false;
		try {
			UpdateUtil up = new UpdateUtil();
			up.executeUpdate(sqlStr);			
			returnFlg = true;
		} catch (Exception e) {
			logger.error("����δ��ɼƻ���ԭ���Sql:" + sqlStr);
		}
		
		return returnFlg;
	}

}
