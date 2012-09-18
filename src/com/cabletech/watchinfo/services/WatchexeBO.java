package com.cabletech.watchinfo.services;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.watchinfo.beans.WatchStaResultBean;
import com.cabletech.watchinfo.dao.WatchexeDAOImpl;
import com.cabletech.watchinfo.domainobjects.Watchexe;
import com.cabletech.watchinfo.templates.WatchDetailTemplate;

public class WatchexeBO extends BaseBisinessObject {
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	WatchexeDAOImpl dao = new WatchexeDAOImpl();

	public void addWatchexe(Watchexe data) throws Exception {
		dao.addWatchexe(data);
	}

	public Watchexe loadWatchexe(String id) throws Exception {
		return dao.findById(id);
	}

	public Watchexe updateWatchexe(Watchexe data) throws Exception {
		return dao.updateWatchexe(data);
	}

	public void removeWatchexe(Watchexe data) throws Exception {
		dao.removeWatchexe(data);
	}

	/**
	 * 导出完整报表
	 * @param planform PlanStatisticForm
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public void exportWatchDetail(List lst, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "盯防巡检明细表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.WatchDetailTemplateFileName");

		WatchDetailTemplate template = new WatchDetailTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(lst, excelstyle);
		template.write(out);

	}

	/**
	 * 盯防统计表
	 * @param resultBean WatchStaResultBean
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public void exportWatchSta(WatchStaResultBean resultBean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "盯防执行统计信息表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.WatchStaTemplateFileName");

		WatchDetailTemplate template = new WatchDetailTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExportSta(resultBean, excelstyle);
		template.write(out);

	}

	public void exportWatchPointSta(WatchStaResultBean resultBean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "盯防执行统计信息表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.watchpointsta");

		WatchDetailTemplate template = new WatchDetailTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportPointSta(resultBean, excelstyle);
		template.write(out);

	}

	/**
	 * 按条件导出报表
	 * @param planform PlanStatisticForm
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	//******************************pzj 修改
	public void exportWatchList(List lstbean, List lstvct, List listcheck, HttpServletResponse response)
			throws Exception {

		OutputStream out;
		initResponse(response, "盯防信息总报表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.watchinfolist");

		WatchDetailTemplate template = new WatchDetailTemplate(urlPath);
		template.doExportList(lstbean, lstvct, listcheck);
		template.write(out);

	}

	//    public void exportWatchList( List lstbean, List lstvct,
	//        HttpServletResponse response ) throws
	//        Exception{
	//
	//        OutputStream out;
	//        initResponse( response, "盯防信息总报表.xls" );
	//        out = response.getOutputStream();
	//
	//        String urlPath = getUrlPath( config,
	//                         "report.watchinfolist" );
	//
	//        WatchDetailTemplate template = new WatchDetailTemplate( urlPath );
	//        template.doExportList( lstbean, lstvct );
	//        template.write( out );
	//
	//    }
	public void exportWatchList(List lstbean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "盯防信息总报表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.watchinfolist");

		WatchDetailTemplate template = new WatchDetailTemplate(urlPath);
		template.doExportList(lstbean);
		template.write(out);

	}

	public void exportTempWatchResult(List list, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "盯防点信息报表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.tempwatchresult");

		WatchDetailTemplate template = new WatchDetailTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.ExportTempWatchResult(list, excelstyle);
		template.write(out);

	}

	public void exportWatchResult(List list, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "盯防信息报表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.watchresult");

		WatchDetailTemplate template = new WatchDetailTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.ExportWatchResult(list, excelstyle);
		template.write(out);

	}

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

}
