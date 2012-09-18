package com.cabletech.linepatrol.appraise.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseTableBean;
import com.cabletech.linepatrol.appraise.dao.AppraiseTableDao;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
import com.cabletech.linepatrol.appraise.template.AppraiseTemplate;
import com.cabletech.linepatrol.appraise.util.AppraiseUtil;

@Service
public class AppraiseTableBO extends EntityManager<AppraiseTable, String>{
	@Resource(name = "appraiseTableDao")
	private AppraiseTableDao appraiseTableDao;
	
	private static String CONTENT_TYPE = "application/vnd.ms-excel";
	/**
	 * 保存考核表
	 * @param appraiseTable
	 */
	@Transactional
	public void saveAppraiseTable(AppraiseTable appraiseTable) {
		appraiseTableDao.save(appraiseTable);
	}
	/**
	 * 通过年份和表的类型查询表
	 * @param year
	 * @param type
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<AppraiseTable> getTableByYear(String year,String type){
		return appraiseTableDao.queryAppraiseTableByYear(year,type);
	}
	/**
	 * 删除表
	 * @param id
	 */
	@Transactional
	public void deleteTableById(String id){
		appraiseTableDao.deleteTable(id);
	}
	/**
	 * 通过类型获得所有该类型的表
	 * @param type
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<AppraiseTable> getAllTable(String type){
		return appraiseTableDao.getAllTable(type);
	}
	/**
	 * 通过类型获得所有该类型的表名
	 * @param type
	 * @return
	 */
	public Map<String,String> getAllTableName(String type){
		Map<String,String> tableName=new HashMap<String, String>();
		List<AppraiseTable> appraiseTables=appraiseTableDao.getAllTable(type);
		for(AppraiseTable appraiseTable:appraiseTables){
			tableName.put(appraiseTable.getId(), appraiseTable.getTableName());
		}
		return tableName;
	}
	/**
	 * 通过表id获得表对象
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public AppraiseTable getTableById(String id) {
		return appraiseTableDao.getTableById(id);
	}

	/**
	 * 导出月考核表
	 * @param id
	 * @param response
	 */
	@Transactional
	public void exprotTable(String id, HttpServletResponse response) {
		AppraiseTable table = appraiseTableDao.getTableById(id);
		try {
			AppraiseUtil.InitResponse(response, table.getTableName() + ".xls",CONTENT_TYPE);
			OutputStream out = response.getOutputStream();
			Properties config = getReportConfig();
			String urlPath = getUrlPath(config, "report.appraiseTableMonth");
			AppraiseTemplate template = new AppraiseTemplate(urlPath);
			ExcelStyle excelStyle = new ExcelStyle(urlPath);
			template.doExportAppriaseTable(table, excelStyle);
			template.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

	@Override
	protected HibernateDao<AppraiseTable, String> getEntityDao() {
		// TODO Auto-generated method stub
		return appraiseTableDao;
	}
	/**
	 * 导出考核表模板
	 * @param templateName
	 * @param response
	 */
	public void exprotTableTemplate(String templateName, HttpServletResponse response) {
		try {
			AppraiseUtil.InitResponse(response, "考核表模板.xls",CONTENT_TYPE);
			OutputStream out = response.getOutputStream();
			Properties config = getReportConfig();
			String name="report."+templateName;
			String urlPath = getUrlPath(config, name);
			AppraiseTemplate template = new AppraiseTemplate(urlPath);
			template.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 将excel存储到table中
	 * @param bean
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public AppraiseTable setTableFromExcel(AppraiseTableBean bean) throws Exception {
		FormFile file = bean.getFile();
		InputStream in = file.getInputStream();
		ExcelManager excelManager = new ExcelManager(in);
		if(!excelManager.checkFormat()){
			throw new IOException();
		}
		AppraiseTable appraiseTable = excelManager.inputAppraiseTableFormExcel(bean);
		return appraiseTable;
	}
	@Transactional(readOnly=true)
	public List<AppraiseTable> getTableByDate(Date startDate,Date endDate,String type){
		return appraiseTableDao.queryTableByDate(startDate,endDate,type);
	}
	@Transactional
	public List<AppraiseTable> getTableInDate(Date startDate,Date endDate, String type) {
		return appraiseTableDao.queryTableInDate(startDate, endDate, type);
	}
	
}
