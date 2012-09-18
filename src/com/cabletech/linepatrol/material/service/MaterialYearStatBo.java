package com.cabletech.linepatrol.material.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.material.dao.MaterialYearStatDao;
import com.cabletech.linepatrol.material.domain.MaterialYearStat;
import com.cabletech.linepatrol.material.templates.MaterialYearStatTmplate;

@Service
@Transactional
public class MaterialYearStatBo extends EntityManager<MaterialYearStat,String> {
	private static String CONTENT_TYPE = "application/vnd.ms-excel";
	
	@Resource(name="materialYearStatDao")
	private MaterialYearStatDao materialYearStatDao;
	
	
	/**
	 * 移动查询代维
	 * @param user
	 * @return
	 */
	@Transactional(readOnly=true)
	public List getConsByDeptId(UserInfo user) throws ServiceException {
		return materialYearStatDao.getConsByDeptId(user);
	}
	
	/**
	 * 材料年统计
	 * @return
	 */
	@Transactional(readOnly=true)
	public List statYearMT(String contraid,String year) throws ServiceException {
		return materialYearStatDao.statYearMT(contraid,year);
	}
	
	
	
	
	/**
	 * 根据查询到的材料年统计
	 * 
	 * @param list
	 *            List
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	@Transactional(readOnly=true)
	public void exportStorage(List list, HttpServletResponse response) throws ServiceException {
		try{
			initResponse(response, "材料年统计.xls");
			OutputStream out = response.getOutputStream();
			Properties config = getReportConfig();
			String urlPath = getUrlPath(config, "report.materialyearstat");
			MaterialYearStatTmplate template = new MaterialYearStatTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportModel(list, excelstyle);
			template.write(out);
		}catch(Exception e){
			e.getStackTrace();
		}

	}
	
	/**
	 * 创建输出流的头
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param fileName
	 *            String
	 * @throws UnsupportedEncodingException
	 */
	@Transactional(readOnly=true)
	private void initResponse(HttpServletResponse response, String fileName)
	throws UnsupportedEncodingException {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));
	}


	@Override
	protected HibernateDao<MaterialYearStat, String> getEntityDao() {
		// TODO Auto-generated method stub
		return materialYearStatDao;
	}

	public MaterialYearStatDao getMaterialYearStatDao() {
		return materialYearStatDao;
	}

	public void setMaterialYearStatDao(MaterialYearStatDao materialYearStatDao) {
		this.materialYearStatDao = materialYearStatDao;
	}

	
}
