package com.cabletech.watchinfo.services;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.services.DBService;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;
import com.cabletech.watchinfo.beans.WatchBean;
import com.cabletech.watchinfo.beans.WatchReportBean;
import com.cabletech.watchinfo.dao.WatchDAOImpl;
import com.cabletech.watchinfo.domainobjects.SubWatch;
import com.cabletech.watchinfo.domainobjects.Watch;
import com.cabletech.watchinfo.templates.WatchInfoTemplate;

public class WatchBO extends BaseBisinessObject {
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	WatchDAOImpl dao = new WatchDAOImpl();

	public void addWatch(Watch data) throws Exception {
		dao.addWatch(data);
	}

	public void addSubWatch(SubWatch data) throws Exception {
		dao.addSubWatch(data);
	}

	public SubWatch loadSubWatch(String id) throws Exception {
		return dao.loadSubWatch(id);
	}

	public SubWatch updateSubWatch(SubWatch data) throws Exception {
		return dao.updateSubWatch(data);
	}

	public void removeSubWatch(SubWatch data) throws Exception {
		dao.removeSubWatch(data);
	}

	/**
	 * 根据点id取得所属线
	 * @param pointid String
	 * @return String
	 * @throws Exception
	 */
	public String getLineIdByPoint(String pointid) throws Exception {
		String lineid = "";
		String sql = "select c.lineid from pointinfo a, sublineinfo b, lineinfo c";
		QuerySqlBuild sqlbuild = QuerySqlBuild.newInstance(sql);

		sqlbuild.addConstant("a.sublineid = b.sublineid and b.lineid = c.lineid ");
		sqlbuild.addConditionAnd("a.pointid = {0}", pointid);

		QueryUtil queryUtil = new QueryUtil();
		Vector vct = queryUtil.executeQueryGetStringVector(sqlbuild.toSql(), "");

		if (vct.size() > 0) {
			lineid = (String) ((Vector) vct.get(0)).get(0);
		}

		return lineid;
	}

	public Watch loadWatch(String id) throws Exception {
		return dao.findById(id);
	}

	public Watch updateWatch(Watch data) throws Exception {
		return dao.updateWatch(data);
	}

	public void removeWatch(Watch data) throws Exception {
		dao.removeWatch(data);
	}

	public String queryString(String querySql) throws Exception {
		String resultString = "";
		QueryUtil queryUtil = new QueryUtil();
		String[][] list = queryUtil.executeQueryGetArray(querySql, "");
		if (list == null) {
			resultString = "未知";
		} else {
			resultString = list[0][0];
		}

		if (resultString == null) {
			resultString = "未知";
		}
		return resultString;
	}

	class watchState {
		java.util.Date executeTime;
		String state;

		public void setExecuteTime(java.util.Date executeTime) {
			this.executeTime = executeTime;
		}

		public java.util.Date getExecutetime() {
			return this.executeTime;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getState() {
			return this.state;
		}

	}

	public List queryReport() throws Exception {
		QueryUtil queryUtil = new QueryUtil();
		ArrayList watchReports = new ArrayList();
		WatchReportBean watchReport;
		String[][] resultArray;

		String querySql = "select PlaceID,PlaceName,principal,";
		querySql += "to_char(beginDate,'yyyy-mm-dd hh24-mi-ss'),";
		querySql += "to_char(endDate,'yyyy-mm-dd hh24-mi-ss'),";
		querySql += "lid,placeName,isGeneralPoint,orderlyCyc,error from watchinfo";
		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(querySql);
		resultArray = queryUtil.executeQueryGetArray(sqlBuild.toSql(), "");
		if (resultArray != null) {
			for (int i = 0; i < resultArray.length; i++) {

				watchReport = new WatchReportBean();

				watchReport.setLineID(this.getLineName(resultArray[i][5]));
				watchReport.setSublineID(resultArray[i][0]);
				com.cabletech.utils.StringUtil StringUtil = new com.cabletech.utils.StringUtil();
				String placeName = resultArray[i][6];
				if (placeName.length() > 11) {
					placeName = StringUtil.left(resultArray[i][6], 8) + "...";
				}
				watchReport.setPlaceID(placeName);

				watchReport.setBeginDate(resultArray[i][3]);
				watchReport.setEndDate(resultArray[i][4]);
				watchReport.setPrincipal(resultArray[i][2]);

				watchReport.setLeaderRate(this.getLeaderRate(resultArray[i][0]));
				watchReport.setPrincipalRate(this.getPrincipalRate(resultArray[i][0]));
				watchReport.setWatchRate(this.getWatchRate(resultArray[i][0]));
				watchReport.setState(this.getWatchState(resultArray[i][0]));
				watchReport.setOrderlyCyc(9);
				watchReport.setErrorCyc(9);

				watchReports.add(watchReport);
			}
		}
		return watchReports;
	}

	public String getLineName(String id) throws Exception {
		String sql = "select linename from lineinfo where lineid = '" + id + "'";

		String result = this.queryString(sql);
		if (result == null) {
			result = "未知";
		} else {
			result.toString();
		}
		return result;
	}

	public String getSublineName(String id, String isPoint) throws Exception {
		String result = "";
		if (isPoint == "01") {
			String sql = "select sublinename from sublineinfo where sublineid = '" + id + "'";
			result = this.queryString(sql);
			if (result == null) {
				result = "未知";
			}
		} else {
			result = "未知";
		}
		return result;

	}

	public String getLeaderRate(String placeid) throws Exception {
		//领导巡检率
		String sql = "select count(*) from watchexecute where pid = '" + placeid + "'";
		//     System.out.println( sql );
		String result = ""; //this.queryString(sql);
		if (result.equals(null)) {
			result = "未知";
		}

		return result.toString();
	}

	public String getPrincipalRate(String placeid) throws Exception {
		//负责人巡检率
		int rate = 0;
		return Integer.toString(rate);
	}

	public String getWatchRate(String placeid) throws Exception {
		//外力盯防巡检率
		String sql = "select count(*) from watchexecute where pid = '" + placeid + "'";
		//       System.out.println( sql );
		String result = this.queryString(sql);
		if (result.equals(null)) {
			result = "未知";
		}

		return result.toString();
	}

	public String getWatchState(String placeid) throws Exception {
		//外力盯防巡检状态
		String state = "未知";

		String sql = "select type from watchexecute where id =";
		sql += "(select max(id) from watchexecute where pid = '";
		sql += placeid;
		sql += "')";
		sql = "select lable from sysdictionary where value = (" + sql + ")";

		//      System.out.println( sql );
		String result = this.queryString(sql);
		if (result.equals(null)) {
			state = "未知";
		} else {
			state = result.toString();
		}
		return state;
	}

	public static java.sql.Date StringToDate(String strDate) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				java.util.Date d = formatter.parse(strDate);
				java.sql.Date numTime = new java.sql.Date(d.getTime());
				return numTime;
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 导出完整报表
	 * @param planform PlanStatisticForm
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public void exportWatchInfo(WatchBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "基础盯防信息表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.WatchInfoTemplateFileName");

		WatchInfoTemplate template = new WatchInfoTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(bean, excelstyle);
		template.write(out);

	}

	//*****************************pzj 修改
	public void exportWatchConstructInfo(Vector checkvec, WatchBean bean, Vector vct, HttpServletResponse response)
			throws Exception {

		OutputStream out;
		initResponse(response, "施工核查盯防信息表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.WatchConstructName");

		WatchInfoTemplate template = new WatchInfoTemplate(urlPath);
		template.doWatchConstructExport(checkvec, bean, vct);
		template.write(out);

	}

	public void exportWatchConstructInfo(WatchBean bean, Vector vct, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "施工核查盯防信息表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.WatchConstructName");

		WatchInfoTemplate template = new WatchInfoTemplate(urlPath);
		template.doWatchConstructExport(bean, vct);
		template.write(out);

	}

	//    public void exportWatchConstructInfo(WatchBean bean, Vector vct,
	//                                         HttpServletResponse response) throws
	//        Exception {
	//
	//        OutputStream out;
	//        initResponse(response, "施工核查盯防信息表.xls");
	//        out = response.getOutputStream();
	//
	//        String urlPath = ReportConfig.getInstance().getUrlPath( "report.WatchConstructName");
	//
	//        WatchInfoTemplate template = new WatchInfoTemplate(urlPath);
	//        template.doWatchConstructExport(bean, vct);
	//        template.write(out);
	//
	//    }

	public void exportWatchConstructInfoNoCable(WatchBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "施工核查盯防信息表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.watchinfolist");

		WatchInfoTemplate template = new WatchInfoTemplate(urlPath);
		template.doWatchConstructExportNoCable(bean);
		template.write(out);

	}

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

	/**
	 * 删除该盯防对应的光缆
	 * @param data Watch
	 * @throws Exception
	 */
	public void removeWatchSubList(Watch data) throws Exception {
		String sql = "delete from WATCH_CABLE_FIBER_LIST where WATCHID = '" + data.getPlaceID() + "'";
		UpdateUtil uu = new UpdateUtil();
		uu.executeUpdateWithCloseStmt(sql);
	}

	/**
	 * 插入对应表
	 * @param data Watch
	 * @param cableIdArr String[]
	 * @throws Exception
	 */
	public void addNewWatchSubList(Watch data, String[] cableIdArr) throws Exception {
		for (int i = 0; i < cableIdArr.length; i++) {
			String sql = "select POINTA, POINTZ, SEGMENTNAME, LAYTYPE, CORENUMBER  from repeatersection where KID='"
					+ cableIdArr[i] + "'";

			//System.out.println(":::::::::::::::::::::::::::::::" + sql);

			QueryUtil qu = new QueryUtil();
			String[][] cableArr = qu.executeQueryGetArray(sql, "");

			if (cableArr == null) {
				return;
			}

			String pa = cableArr[0][0];
			String pz = cableArr[0][1];
			String cableName = cableArr[0][2];
			String cableId = cableIdArr[i];
			String laytype = cableArr[0][3];
			String corenum = cableArr[0][4];

			sql = "select kid, SYSTEMNAME, NETLAYER from FIBERROUTE where POINTA='" + pa + "' and POINTZ='" + pz + "'";
			//System.out.println(":::::::::::::::::::::::::::::::" + sql);

			QueryUtil qu2 = new QueryUtil();
			String[][] fiberArr = qu2.executeQueryGetArray(sql, "");

			String fiberId = "";
			String fiberName = "";
			String netlayer = "";

			if (fiberArr != null) {
				fiberId = fiberArr[0][0];
				fiberName = fiberArr[0][1];
				netlayer = fiberArr[0][2];
			}

			DBService dbservice = new DBService();
			String listKid = dbservice.getSeq("watch_cable_fiber_list", 20);

			sql = " insert into WATCH_CABLE_FIBER_LIST (";
			sql += " KID,WATCHID,SEGMENT_KID,SEGMENTNAME,FIBER_KID,FIBERNAME,LAYTYPE, CORENUM, NETLAYER) values ( ";
			sql += " '" + listKid + "' , ";
			sql += " '" + data.getPlaceID() + "' , ";
			sql += " '" + cableId + "' , ";
			sql += " '" + cableName + "' , ";
			sql += " '" + fiberId + "' , ";
			sql += " '" + fiberName + "' , ";

			sql += " '" + laytype + "' , ";
			sql += " '" + corenum + "' , ";
			sql += " '" + netlayer + "' ) ";

			//System.out.println(":::::::::::::::::::::::::::::::" + sql);

			UpdateUtil uu = new UpdateUtil();
			uu.executeUpdateWithCloseStmt(sql);

		}
	}

	/**
	 * 取得列表
	 * @param bean WatchBean
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getWatch_cable_fiber_list(WatchBean bean) throws Exception {

		String sql = " select distinct KID, SEGMENTNAME,FIBERNAME,LAYTYPE,CORENUM,NETLAYER,MONITORTYPE,IFNEEDCUT ";
		sql += " from WATCH_CABLE_FIBER_LIST ";
		sql += " where WATCHID = '" + bean.getPlaceID() + "'";

		QueryUtil qu = new QueryUtil();
		Vector vct = qu.executeQueryGetStringVector(sql, "");

		return vct;
	}
}
