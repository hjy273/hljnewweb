package com.cabletech.linechange.dao;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linechange.bean.ChangeBuildBean;
import com.cabletech.linechange.bean.ChangeCheckBean;
import com.cabletech.linechange.bean.ChangeInfoBean;
import com.cabletech.linechange.bean.ChangeSurveyBean;
import com.cabletech.linechange.templates.LineChangeTemplate;

public class ExportDao {

	private static Logger logger = Logger.getLogger("ToolExportDao");

	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	public ExportDao() {
	}

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

	public void exportSta(List list, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "修缮工程统计表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath("report.linechangesta");

		LineChangeTemplate template = new LineChangeTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportSta(list, excelstyle);
		template.write(out);

	}

	public void exportApplyResult(List list, ChangeInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "修缮信息统计表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath("report.linechangeapply");

		LineChangeTemplate template = new LineChangeTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportApplyResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportSurveyResult(List list, ChangeSurveyBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "勘查信息统计表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath("report.linechangesurvey");

		LineChangeTemplate template = new LineChangeTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportSurveyResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportBuildSetoutResult(List list, ChangeInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "施工准备信息统计表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath("report.linechangebuildsetout");

		LineChangeTemplate template = new LineChangeTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportBuildSetoutResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportConsignResult(List list, ChangeInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "施工委托信息统计表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath("report.linechangeconsign");

		LineChangeTemplate template = new LineChangeTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportConsignResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportBuildResult(List list, ChangeBuildBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "施工委托信息统计表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath("report.linechangebuild");

		LineChangeTemplate template = new LineChangeTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportBuildResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportCheckResult(List list, ChangeCheckBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "施工验收信息统计表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath("report.linechangecheck");

		LineChangeTemplate template = new LineChangeTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportCheckResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportPageonholeResult(List list, ChangeInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "归档信息统计表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath("report.linechangepageonhole");

		LineChangeTemplate template = new LineChangeTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportPageonholeResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportChangeStat(List list, ChangeInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "修缮统计信息.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath("report.linechangestat");

		LineChangeTemplate template = new LineChangeTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportChangeStat(list, bean, excelstyle);
		template.write(out);
	}

	public List getChangeStat(UserInfo user, ChangeInfoBean bean) {
		List list = null;
		try {
			String sql = " select  c.id,c.CHANGENAME,l.NAME, l.name lineclass, "
					+ "c.CHANGEPRO,TO_CHAR(c.STARTDATE,'yyyy-MM') startdate,c.BUDGET,c.SQUARE, "
					+ " c.squared,TO_CHAR(c.sqdate,'yyyy-MM-dd') sqdate," + " DECODE (c.step, " + " 'A', '待审定', "
					+ "'B1', '通过监理审定','B2','通过移动审定', " + "'C', '施工准备', " + "'D', '开始施工', " + "'E', '施工完毕', "
					+ "'F', '验收完毕', " + " 'G', '已经归档' " + ") state," + "c.SEXPENSE,c.DEXPENSE, "
					+ "decode(c.STEP,'E','是','F','是','G','是','否') buildstate, "
					+ "decode(c.STEP,'F','是','G','是','否') valistate, " + " c.squared "
					+ "from changeinfo c,lineclassdic l  " + "where c.LINECLASS = l.CODE(+)";// "
																								// and
																								// c.STEP='G'";

			// 市移动
			if (user.getDeptype().equals("1") && !user.getRegionID().substring(2, 6).equals("0000")) {
				sql += " and c.regionid in (" + user.getRegionID() + ")";
			}
			// 市代维
			if (user.getDeptype().equals("2") && !user.getRegionID().substring(2, 6).equals("0000")) {
				sql += " and c.applyunit ='" + user.getDeptID() + "'";
			}
			// 省代维
			if (user.getDeptype().equals("2") && user.getRegionID().substring(2, 6).equals("0000")) {
				sql += " and c.applyunit in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
						+ user.getDeptID() + "')";
			}
			// 以上为按不同用户查询条件
			sql += bean.getChangename() == null ? "" : " and c.changename like '%" + bean.getChangename() + "%' ";
			sql += bean.getChangepro() == null ? "" : " and c.changepro like '%" + bean.getChangepro() + "%' ";

			sql += (bean.getLineclass() == null || bean.getLineclass().equals("")) ? "" : "and c.lineclass like '%"
					+ bean.getLineclass() + "%' ";
			sql += (bean.getStep() == null || bean.getStep().equals("")) ? "" : "and c.step like '%" + bean.getStep()
					+ "%' ";
			if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
				sql += " and c.applytime >= TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
			}
			if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
				sql += " and c.applytime <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
			}
			sql += " order by c.startdate desc,c.id desc";
			QueryUtil query = new QueryUtil();
			logger.info("sql:>>>" + sql);
			list = query.queryBeans(sql);
			return list;
		} catch (Exception e) {
			logger.error("按指定条件获得当前登陆代维单位的所有报修信息异常:" + e.getMessage());
			return null;
		}
	}

	public DynaBean getOne(String id) {
		String sql = "select  c.ID,c.CHANGENAME,c.CHANGEPRO,c.CHANGEADDR,"
				+ "        DECODE (c.step,"
				+ "                'A', '待审定',"
				+ "                'B1', '通过监理审定','B2','通过移动审定',"
				+ "                'C', '施工准备',"
				+ "                'D', '开始施工',"
				+ "                'E', '施工完毕',"
				+ "                'F', '验收完毕',"
				+ "                'G', '已经归档'"
				+ " ) state,c.step,"
				+ " c.INVOLVED_SYSTEM,c.CHANGELENGTH,c.LINECLASS,"
				+ " TO_CHAR(c.STARTDATE,'yyyy-MM-dd') starttime,c.PLANTIME,c.REMARK,c.APPLYDATUMID,"
				+ " TO_CHAR(c.APPLYTIME,'yyyy-MM-dd') applytime,c.REGIONID,"
				+ " c.BUDGET,c.APPROVERESULT,"
				+ " c.ENAME,c.EADDR,c.EPERSON,c.EPHONE,"
				+ " c.SNAME,c.SADDR,c.SPERSON,c.SPHONE,c.SGRADE,c.SEXPENSE,"
				+ " c.DNAME,c.DADDR,c.DPERSON,c.DPHONE,c.DGRADE,c.DEXPENSE,c.SETOUTDATUM,c.SETOUTREMARK,TO_CHAR(c.SETOUTTIME,'yyyy-MM-dd') setouttime,c.SETOUTPERSON,"
				+ " c.ENTRUSTUNIT,c.ENTRUSTADDR,c.ENTRUSTPERSON,c.ENTRUSTGRADE,c.ENTRUSTNOTE,c.ENTRUSTDATUM,c.ENTRUSTPHONE,TO_CHAR(c.constartdate,'yyyy-MM-dd') constartdate,c.ENTRUSTREMARK,TO_CHAR(c.ENTRUSTTIME,'yyyy-MM-dd') entrusttime,c.COST,"
				+ " TO_CHAR(c.PAGEONHOLEDATE,'yyyy-mm-dd') pageonholedate,"
				+ " c.PAGEONHOLEPERSON,c.SQUARE,c.ISCHANGEDATUM,c.PAGEONHOLEDATUM,c.PAGEONHOLEREMARK,"
				+ " changesurvey.APPROVEDEPT,TO_CHAR(changesurvey.APPROVEDATE,'yyyy-MM-dd') APPROVEDATE,"
				+ " changesurvey.APPROVER,changesurvey.APPROVEREMARK,changesurvey.APPROVERESULT,changesurvey.ID surveyid,"
				+ " TO_CHAR(changesurvey.FILLINDATE,'yyyy-MM-dd') FILLINDATE,"
				+ " changesurvey.PRINCIPAL,TO_CHAR(CHANGESURVEY.SURVEYDATE,'yyyy-MM-dd') SURVEYDATE,"
				+ " changesurvey.SURVEYDATUM,changesurvey.SURVEYREMARK,"
				+ " changebuild.BUILDADDR,changebuild.BUILDDATUM,changebuild.buildphone,changebuild.BUILDPERSON,changebuild.BUILDREMARK,"
				+ " changebuild.BUILDUNIT,changebuild.BUILDVALUE,TO_CHAR(changebuild.STARTTIME,'yyyy-MM-dd') bstarttime,"
				+ " TO_CHAR(changebuild.ENDTIME,'yyyy-MM-dd') bendtime,changebuild.ID buildid,"
				+ " changecheck.ID checkid ,TO_CHAR(changecheck.CHECKDATE,'yyyy-MM-dd') checkdate,changecheck.CHECKDATUM checkdatum,changecheck.checkresult,"
				+ " changecheck.CHECKPERSON checkperson,changecheck.CHECKREMARK checkremark"
				+ " from changesurvey ,changeinfo c,lineclassdic l,changebuild,changecheck"
				+ " where c.LINECLASS = l.CODE(+)"
				+ " and c.ID = changesurvey.changeid(+)"
				+ " and c.ID = changebuild.CHANGEID(+)"
				+ " AND c.ID = changecheck.changeid(+)"
				+ " AND ((changesurvey.surveytype IS NULL and c.step='A') or (changesurvey.surveytype='B2' and c.step<>'A' and c.step<>'B1') or (changesurvey.surveytype='B1' and c.step='B1'))"
				// + " AND changesurvey.state IS NULL"
				// + " AND changebuild.state IS NULL"
				// + " AND changecheck.state IS NULL"
				// + " AND c.step = 'G' "
				+ " and c.id='" + id + "'";
		logger.info("SQL:" + sql);

		try {
			List list = null;
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
			if (!list.isEmpty()) {
				DynaBean bean = (DynaBean) list.get(0);
				return bean;
			} else {
				return null;
			}
		} catch (Exception ex) {
			logger.error("查看详细信息时异常：" + ex.getMessage());
		}
		return null;
	}
	// ResultSet rst ;
	// QueryUtil qu = new QueryUtil();
	// rst = qu.executeQuery(sql);
	// if(rst !=null && rst.next()){
	// bean.setChangename(rst.getString("changename"));
	// bean.setChangepro(rst.getString("changepro"));
	// bean.setChangeaddr(rst.getString("changeaddr"));
	// bean.setStep(rst.getString("step"));
	// bean.setInvolvedSystem(rst.getString("involved_system"));
	// bean.setChangelength(Float.valueOf(String.valueOf(rst.getFloat("changelength"))));
	//
	// bean.setStartdate(rst.getString("starttime"));
	// bean.setPlantime(Float.valueOf(String.valueOf(rst.getFloat("plantime"))));
	// bean.setRemark(rst.getString("remark"));
	// bean.setApplydatumid(rst.getString("applydatumid"));
	// bean.setApplytime(rst.getString("applytime"));
	// bean.setRegionid(rst.getString("regionid"));
	// bean.setBudget(Float.valueOf(String.valueOf(rst.getFloat("budget"))));
	// bean.setApproveresult(rst.getString("approveresult"));
	// bean.setEname(rst.getString("ename"));
	// bean.setEaddr(rst.getString("eaddr"));
	// bean.setEperson(rst.getString("eperson"));
	// bean.setEphone(rst.getString("ephone"));
	// bean.setSname(rst.getString("sname"));
	// bean.setSaddr(rst.getString("saddr"));
	// bean.setSperson(rst.getString("sperson"));
	// bean.setSphone(rst.getString("sphone"));
	// bean.setSgrade(rst.getString("sgrade"));
	// bean.setSexpense(Float.valueOf(String.valueOf(rst.getFloat("sexpense"))));
	// bean.setDname(rst.getString("dname"));
	// bean.setDaddr(rst.getString("daddr"));
	// bean.setDperson(rst.getString("dperson"));
	// bean.setDgrade(rst.getString("dgrade"));
	// bean.setDexpense(Float.valueOf(String.valueOf(rst.getFloat("dexpense"))));
	// bean.setSetoutdatum(rst.getString("setoutdatum"));
	// bean.setSetoutremark(rst.getString("setoutremark"));
	// bean.setSetouttime(rst.getString("setouttime"));
	// bean.setSetoutperson(rst.getString("setoutperson"));
	// // TO_CHAR(c.ENTRUSTTIME,'yyyy-MM-dd') entrusttime,c.COST,"
	// bean.setEntrustunit(rst.getString("entrustunit"));
	// bean.setEntrustaddr(rst.getString("entrustaddr"));
	// bean.setEntrustperson(rst.getString("entrustperson"));
	// bean.setEntrustgrade(rst.getString("entrustgrade"));
	// bean.setEntrustdatum(rst.getString("entrustdatum"));
	// bean.setEntrustphone(rst.getString("entrustphone"));
	// bean.setEntrustremark(rst.getString("entrustremark"));
	// bean.setEntrusttime(rst.getString("entrusttime"));
	// bean.setCost(Float.valueOf(String.valueOf(rst.getFloat("cost"))));
	// bean.setPageonholedate(rst.getString("pageonholedate"));
	// bean.setPageonholeperson(rst.getString("pageonholeperson"));
	// bean.setSquare(Float.valueOf(String.valueOf(rst.getFloat("square"))));
	// bean.setIschangedatum(rst.getString("ischangedatum"));
	// bean.setPageonholedatum(rst.getString("pageonholedatum"));
	// bean.setPageonholeremark(rst.getString("pageonholeremark"));
	// //changesurvey.APPROVEDEPT,TO_CHAR(changesurvey.APPROVEDATE,'yyyy-MM-dd')
	// APPROVEDATE,"
	// //
	// changesurvey.APPROVER,changesurvey.APPROVEREMARK,changesurvey.APPROVERESULT,changesurvey.ID
	// surveyid,"
	// rst.close();
	// return bean;
	//
	// }
	// }
	// catch( Exception ex ){
	// return null;
	// }
	// return null;
	// }

}
