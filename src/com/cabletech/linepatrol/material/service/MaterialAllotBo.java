package com.cabletech.linepatrol.material.service;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.material.beans.MaterialAllotBean;
import com.cabletech.linepatrol.material.dao.MaterialAllotDao;
import com.cabletech.linepatrol.material.domain.MaterialAllot;
import com.cabletech.linepatrol.material.domain.MaterialChangeItem;
import com.cabletech.linepatrol.material.templates.MaterialAllotTmplate;

@Service
@Transactional
public class MaterialAllotBo extends EntityManager<MaterialAllot,Integer> {
	private OracleIDImpl ora = new OracleIDImpl();
	private static String CONTENT_TYPE = "application/vnd.ms-excel";
	
	@Resource(name="materialAllotDao")
	private MaterialAllotDao materialAllotDao;
	
	/**
	 * 移动查询代维
	 * @param user
	 * @return
	 */
	@Transactional(readOnly=true)
	public List getConsByDeptId(UserInfo user) throws ServiceException {
		return materialAllotDao.getConsByDeptId(user);
	}
	
	
	/**
	 *  查询所有材料所在地点
	 * @param user
	 * @return
	 */
	@Transactional(readOnly=true)
	public List getAllMTAddr() throws ServiceException {
		return materialAllotDao.getAllMTAddr();
	}
	
	/**
	 * 根据代维id 查询材料所在地点
	 * @param user
	 * @return
	 */
	@Transactional(readOnly=true)
	public List getMTAddrByConId(String id) throws ServiceException {
		return materialAllotDao.getMTAddrByConId(id);
	}
	
	
	/**
	 * 查询材料所在
	 * @param user
	 * @return
	 */
	@Transactional(readOnly=true)
	public List getMaterialsByAddreId(UserInfo user,String addID) throws ServiceException {
		return materialAllotDao.getMaterialsByAddreId(user,addID);
	}
	
	
	
	/**
	 * 根据代维id 查询address
	 * @param user
	 * @return
	 */
	@Transactional(readOnly=true)
	public BasicDynaBean getAddrById(String id) throws ServiceException {
		BasicDynaBean bean = null;
		List list = materialAllotDao.getAddrById(id);
		if(list!=null && list.size()>0){
			 bean = (BasicDynaBean) list.get(0);
		}
		return bean;
	}
	
	
	public boolean addMaterialAllot(MaterialAllotBean bean,UserInfo user) throws ServiceException {
		int tid = ora.getIntSeq("material_allot");
		bean.setTid(tid);
		return materialAllotDao.addMaterialAllot(bean, user);
	}
	
	/**
	 * 得到查询的材料调拨
	 * @return
	 */
	@Transactional(readOnly=true)
	public List getMaterialAllots(MaterialChangeItem changeItem ) throws ServiceException {
		List list = materialAllotDao.getMaterialAllots(changeItem);
		return list;
	}

	/**
	 * 得到查询的材料调拨明细
	 * @return
	 */
	@Transactional(readOnly=true)
	public List getMaterialAllotItems(String allotid) throws ServiceException {
		return materialAllotDao.getMaterialAllotItems(allotid);
	}
	
	/**
	 * 导出查询的材料调拨明细
	 * 
	 * @param list
	 *            List
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	@Transactional(readOnly=true)
	public void exportStorageItems(List list, HttpServletResponse response) throws ServiceException {
		try{
			initResponse(response, "材料调拨明细.xls");
			OutputStream out = response.getOutputStream();
			Properties config = getReportConfig();
			String urlPath = getUrlPath(config, "report.materialallotdetail");
			MaterialAllotTmplate template = new MaterialAllotTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportAllotItemss(list, excelstyle);
			template.write(out);
		}catch(Exception e){
			e.getStackTrace();
		}
	}
	
	
	/**
	 * 导出查询的材料调拨单
	 * 
	 * @param list
	 *            List
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	@Transactional(readOnly=true)
	public void exportStorageAllot(List list, HttpServletResponse response) throws ServiceException {
		try{
			initResponse(response, "材料调拨单.xls");
			OutputStream out = response.getOutputStream();
			Properties config = getReportConfig();
			String urlPath = getUrlPath(config, "report.materialAllot");
			MaterialAllotTmplate template = new MaterialAllotTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportAllots(list, excelstyle);
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
	protected HibernateDao<MaterialAllot, Integer> getEntityDao() {
		// TODO Auto-generated method stub
		return materialAllotDao;
	}


	public MaterialAllotDao getMaterialAllotDao() {
		return materialAllotDao;
	}


	public void setMaterialAllotDao(MaterialAllotDao materialAllotDao) {
		this.materialAllotDao = materialAllotDao;
	}

	

}
