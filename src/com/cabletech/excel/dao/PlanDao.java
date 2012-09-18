package com.cabletech.excel.dao;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;
import com.cabletech.excel.templates.PlanTemplate;
import com.cabletech.planinfo.beans.YearMonthPlanBean;
import com.cabletech.planstat.domainobjects.PlanStat;
import com.cabletech.planstat.services.PlanExeResultBO;
import com.cabletech.planstat.services.PlanExeResultBOImpl;

public class PlanDao {

	private static Logger logger = Logger.getLogger("PlanDao");
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	public PlanDao() {
	}

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

	public void exportYearPlanResult(List list, HttpServletResponse response, UserInfo userinfo, YearMonthPlanBean bean)
			throws Exception {
		OutputStream out;
		initResponse(response, "�ƻ���Ϣ�������.xls");
		out = response.getOutputStream();
		String urlPath = ReportConfig.getInstance().getUrlPath("report.yearplanresult");

		PlanTemplate template = new PlanTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportYearPlanResult(list, excelstyle, userinfo, bean);
		template.write(out);

	}

	/**
	 * �����ƻ�ִ�н���һ����
	 * @param list
	 * @param response
	 * @throws Exception
	 */
	public void exportPlanprogresstextResult(List list, String pmType, HttpServletResponse response) throws Exception {
		OutputStream out;
		initResponse(response, "�鿴�ƻ�ִ�н���һ����.xls");
		out = response.getOutputStream();
		String urlPath = ReportConfig.getInstance().getUrlPath("report.planprogresstext");

		PlanTemplate template = new PlanTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportPlanprogresstextResult(list, pmType, excelstyle);
		template.write(out);

	}

	/**
	 * �����ƻ�ִ�н���һ����
	 * @param list
	 * @param response
	 * @throws Exception
	 */
	public void exportPlanstateResult(PlanStat planStatBean, String pmType, String executorname,
			HttpServletResponse response) throws Exception {
		OutputStream out;
		initResponse(response, "�ƻ�ִ����ϸ��Ϣ.xls");
		out = response.getOutputStream();
		String urlPath = ReportConfig.getInstance().getUrlPath("report.planstateresult");

		PlanTemplate template = new PlanTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportPlanstateResult(planStatBean, pmType, executorname, excelstyle);
		template.write(out);

	}

	public List getPlanList(YearMonthPlanBean bean, HttpServletRequest request) throws Exception {

		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String fID = request.getParameter("fID"); //1, year 2, month
		logger.info("1, year 2, month----" + fID);
		String sql = "select id, planname, year, month, decode(status, '1','ͨ��','-1','δͨ��','������') approvestatus, to_char(CREATEDATE, 'yyyy-mm-dd') CREATEDATE, CREATOR, APPROVER, to_char(APPROVEDATE, 'yyyy-mm-dd') APPROVEDATE  from yearmonthplan   ";
		String planid_sql = "select id from yearmonthplan ";
		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
		QuerySqlBuild sqlBuild_planid = QuerySqlBuild.newInstance(planid_sql);
		String sql2 = "";
		if (fID.equals("2")) {
			sqlBuild.addConditionAnd("plantype = {0}", "2");
			sqlBuild_planid.addConditionAnd("plantype = {0}", "2");
		} else {
			sqlBuild.addConditionAnd("plantype = {0}", "1");
			sqlBuild_planid.addConditionAnd("plantype = {0}", "1");
		}

		sqlBuild.addConditionAnd("year = {0}", bean.getYear());
		sqlBuild.addConditionAnd("month = {0}", bean.getMonth());
		sqlBuild.addConditionAnd("status = {0}", bean.getStatus());

		sqlBuild_planid.addConditionAnd("year = {0}", bean.getYear());
		sqlBuild_planid.addConditionAnd("month = {0}", bean.getMonth());
		sqlBuild_planid.addConditionAnd("status = {0}", bean.getStatus());

		sqlBuild.addConditionAnd("remark != {0}", "2"); //�����޸ĵĸ���
		sqlBuild_planid.addConditionAnd("remark != {0}", "2"); //�����޸ĵĸ���
		//ʡ�ƶ�
		if (userinfo.getType().equals("11")) {
			if (bean.getRegionid() != null && !bean.getRegionid().equals("")) {
				sqlBuild.addConstant(" and  yearmonthplan.regionid IN (SELECT     regionid " + " FROM region "
						+ " CONNECT BY PRIOR regionid = parentregionid " + " START WITH regionid = '"
						+ bean.getRegionid() + "')");
				if (bean.getDeptid() != null && !bean.getDeptid().equals("")) {
					sqlBuild.addConstant(" and yearmonthplan.deptid in( SELECT contractorid "
							+ " FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID "
							+ " START WITH contractorid='" + bean.getDeptid() + "')");
				}

				sqlBuild_planid.addConstant(" and  yearmonthplan.regionid IN (SELECT     regionid " + " FROM region "
						+ " CONNECT BY PRIOR regionid = parentregionid " + " START WITH regionid = '"
						+ bean.getRegionid() + "')");
				if (bean.getDeptid() != null && !bean.getDeptid().equals("")) {
					sqlBuild_planid.addConstant(" and yearmonthplan.deptid in( SELECT contractorid "
							+ " FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID "
							+ " START WITH contractorid='" + bean.getDeptid() + "')");
				}

			}
		}
		//���ƶ�
		if (userinfo.getType().equals("12")) {
			sqlBuild.addConstant(" and  yearmonthplan.regionid IN (SELECT     regionid " + " FROM region "
					+ " CONNECT BY PRIOR regionid = parentregionid " + " START WITH regionid = '"
					+ userinfo.getRegionid() + "')");
			if (bean.getDeptid() != null && !bean.getDeptid().equals("")) {
				sqlBuild.addConstant(" and yearmonthplan.deptid in( SELECT contractorid "
						+ " FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID "
						+ " START WITH contractorid='" + bean.getDeptid() + "')");
			}

			sqlBuild_planid.addConstant(" and  yearmonthplan.regionid IN (SELECT     regionid " + " FROM region "
					+ " CONNECT BY PRIOR regionid = parentregionid " + " START WITH regionid = '"
					+ userinfo.getRegionid() + "')");
			if (bean.getDeptid() != null && !bean.getDeptid().equals("")) {
				sqlBuild_planid.addConstant(" and yearmonthplan.deptid in( SELECT contractorid "
						+ " FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID "
						+ " START WITH contractorid='" + bean.getDeptid() + "')");
			}

		}
		//ʡ��ά
		if (userinfo.getType().equals("21")) {
			if (bean.getRegionid() != null && !bean.getRegionid().equals("")) {
				sqlBuild.addConstant(" and  yearmonthplan.regionid IN (SELECT     regionid " + " FROM region "
						+ " CONNECT BY PRIOR regionid = parentregionid " + " START WITH regionid = '"
						+ bean.getRegionid() + "')");
				if (bean.getDeptid() != null && !bean.getDeptid().equals("")) {
					sqlBuild.addConstant(" and yearmonthplan.deptid in( SELECT contractorid "
							+ " FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID "
							+ " START WITH contractorid='" + bean.getDeptid() + "')");
				} else {
					sqlBuild.addConstant(" and yearmonthplan.deptid in( SELECT contractorid FROM "
							+ "contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID "
							+ "START WITH contractorid='" + userinfo.getDeptID() + "')");
				}
			}

			if (bean.getRegionid() != null && !bean.getRegionid().equals("")) {
				sqlBuild_planid.addConstant(" and  yearmonthplan.regionid IN (SELECT     regionid " + " FROM region "
						+ " CONNECT BY PRIOR regionid = parentregionid " + " START WITH regionid = '"
						+ bean.getRegionid() + "')");
				if (bean.getDeptid() != null && !bean.getDeptid().equals("")) {
					sqlBuild_planid.addConstant(" and yearmonthplan.deptid in( SELECT contractorid "
							+ " FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID "
							+ " START WITH contractorid='" + bean.getDeptid() + "')");
				} else {
					sqlBuild_planid.addConstant(" and yearmonthplan.deptid in( SELECT contractorid FROM "
							+ "contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID "
							+ "START WITH contractorid='" + userinfo.getDeptID() + "')");
				}
			}

		}
		//�д�ά
		if (userinfo.getType().equals("22")) {
			sqlBuild.addConstant("and  yearmonthplan.regionid IN (SELECT     regionid " + " FROM region "
					+ " CONNECT BY PRIOR regionid = parentregionid " + " START WITH regionid = '"
					+ userinfo.getRegionid() + "')");
			sqlBuild.addConstant(" and yearmonthplan.deptid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
					+ userinfo.getDeptID() + "')");

			sqlBuild_planid.addConstant("and  yearmonthplan.regionid IN (SELECT     regionid " + " FROM region "
					+ " CONNECT BY PRIOR regionid = parentregionid " + " START WITH regionid = '"
					+ userinfo.getRegionid() + "')");
			sqlBuild_planid
					.addConstant(" and yearmonthplan.deptid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
							+ userinfo.getDeptID() + "')");

		}
		sqlBuild.addConstant(" order by id "); //id desc,

		sql = sqlBuild.toSql();
		planid_sql = sqlBuild_planid.toSql();

		logger.info("�������¼ƻ�: " + sql);

		sql2 = "SELECT l.NAME linelevel, b.ID taskid, b.describtion taskname," + " b.excutetimes excutetimes,a.PLANID"
				+ "  FROM plantasklist a, taskinfo b, lineclassdic l"
				+ " WHERE l.code = b.linelevel AND a.taskid = b.ID" + " and a.PLANID in (" + planid_sql + ")";
		sql2 += " order by planid";
		logger.info("��ѯ������Ϣ: " + sql2);

		QueryUtil query = new QueryUtil();

		List lplan = query.queryBeans(sql);
		List ltask = query.queryBeans(sql2);

		List alllist = (List) new ArrayList();
		DynaBean recordplan;
		DynaBean record;
		DynaBean record2;
		logger.info("lplan��size��" + Integer.toString(lplan.size()));
		logger.info("ltask��size��" + Integer.toString(ltask.size()));

		for (int i = 0; i < lplan.size(); i++) {
			YearMonthPlanBean ymplanbean = new YearMonthPlanBean();
			List list = (List) new ArrayList();
			recordplan = (DynaBean) lplan.get(i);
			for (int j = 0; j < ltask.size(); j++) {
				record = (DynaBean) ltask.get(j);
				//�Ƚ�������ϢList�е�id��ƻ�List��planid
				if (record.get("planid").toString().equals(recordplan.get("id").toString())) {
					list.add(record);
				}
			}

			logger.info("setYearMonthPlanBean");
			ymplanbean.setId(recordplan.get("id").toString());
			logger.info("getplanname:" + recordplan.get("planname").toString());
			ymplanbean.setPlanname(recordplan.get("planname").toString());
			ymplanbean.setYear(recordplan.get("year").toString());
			if (fID.equals("2")) {
				ymplanbean.setMonth(recordplan.get("month").toString());
			}
			ymplanbean.setStatus(recordplan.get("approvestatus").toString());
			ymplanbean.setCreatedate(recordplan.get("createdate").toString());
			ymplanbean.setCreator(recordplan.get("creator").toString());
			if (recordplan.get("approver") != null) {
				ymplanbean.setApprover(recordplan.get("approver").toString());
			} else {
				ymplanbean.setApprover("");
			}
			if (recordplan.get("approvedate") != null) {
				ymplanbean.setApprovedate(recordplan.get("approvedate").toString());
			} else {
				ymplanbean.setApprovedate("");
			}
			ymplanbean.setTasklist(list);
			alllist.add(i, ymplanbean);
			logger.info("END");

		}
		for (int k = 0; k < alllist.size(); k++) {
			YearMonthPlanBean ymplanbean = (YearMonthPlanBean) alllist.get(k);
			logger.info("getk:" + Integer.toString(k) + ymplanbean.getPlanname());
		}

		logger.info("���ؼ�¼����" + Integer.toString(alllist.size()));
		return alllist;
	}

	public void exportPlanexecuteinfo(List planexecuteList, String pmType, HttpServletResponse response)
			throws Exception {
		OutputStream out;
		initResponse(response, "�ƻ�ִ����ϸ��Ϣ.xls");
		out = response.getOutputStream();
		String urlPath = ReportConfig.getInstance().getUrlPath("report.planexecuteinfo");

		PlanTemplate template = new PlanTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);

		// �����б�
		PlanExeResultBO planExeResultBO = new PlanExeResultBOImpl();

		String strPlanID = null;
		DynaBean rowBean = null;
		PlanStat dataBean = null;
		for (int i = 0; i < planexecuteList.size(); i++) {
			try {
				rowBean = (DynaBean) planexecuteList.get(i);
				strPlanID = String.valueOf(rowBean.get("planid"));
				dataBean = planExeResultBO.getPlanStat(strPlanID);

				template.exportPlanstateSheet(dataBean, pmType, rowBean.get("patrolname").toString(), excelstyle, i);
			} catch (Exception ex) {
				logger.info("ȡ�üƻ�����ϸ��Ϣ:" + ex.toString());
			}
		}

		template.write(out);
	}

}
