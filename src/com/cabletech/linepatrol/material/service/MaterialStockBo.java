package com.cabletech.linepatrol.material.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.material.beans.MaterialStockRecordBean;
import com.cabletech.linepatrol.material.dao.MaterialStockDao;
import com.cabletech.linepatrol.material.domain.MaterialStock;
import com.cabletech.linepatrol.material.templates.MaterialStockTmplate;

@Service
@Transactional
public class MaterialStockBo extends EntityManager<MaterialStock, Integer> {
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	@Resource(name = "materialStockDao")
	private MaterialStockDao materialStockDao;

	/**
	 * �ƶ���ѯ��ά
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getConsByDeptId(UserInfo user) throws ServiceException {
		return materialStockDao.getConsByDeptId(user);
	}

	/**
	 * ��ѯ���в������ڵص�
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getAllMTAddr() throws ServiceException {
		return materialStockDao.getAllMTAddr();
	}

	/**
	 * ���ݴ�άid ��ѯ�������ڵص�
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getMTAddrByConId(String id) throws ServiceException {
		return materialStockDao.getMTAddrByConId(id);
	}

	/**
	 * ���ݴ�άid ��ѯaddress
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public BasicDynaBean getAddrById(String id) throws ServiceException {
		BasicDynaBean bean = null;
		List list = materialStockDao.getAddrById(id);
		if (list != null && list.size() > 0) {
			bean = (BasicDynaBean) list.get(0);
		}
		return bean;
	}

	/**
	 * ���ݴ�ά��ѯ������ѯ�����Ϣ
	 * 
	 * @param deptype
	 * @param contraid
	 *            ��ά��id
	 * @param addrID
	 *            ���ϴ�ŵص�
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getMarterialStocksByCon(String deptype, String contraid,
			String addrID) throws ServiceException {
		return materialStockDao.getMarterialStocksByCon(deptype, contraid,
				addrID);
	}

	/**
	 * ��ѯ��������
	 * 
	 * @param regionid
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getMTTypes(String regionid) throws ServiceException {
		return materialStockDao.getMTTypes(regionid);
	}

	/**
	 * ���ݲ������Ͳ�ѯ���Ϲ��
	 * 
	 * @param regionid
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getModelByType(String typeid) throws ServiceException {
		return materialStockDao.getModelByType(typeid);
	}

	/**
	 * ���ݲ��Ϲ���ѯ����
	 * 
	 * @param regionid
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getMTByModel(String modelid) throws ServiceException {
		return materialStockDao.getMTByModel(modelid);
	}

	/**
	 * �����ϲ�ѯ���Ͽ��
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getMarterialStocksByMT(UserInfo user, String typeid,
			String modelid, String mtid) throws ServiceException {
		return materialStockDao.getMarterialStocksByMT(user, typeid, modelid,
				mtid);
	}

	/**
	 * ���ݲ�ѯ���Ĳ�����ͳ��
	 * 
	 * @param list
	 *            List
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	@Transactional(readOnly = true)
	public void exportStorage(List list, String deptype, String news,
			String olds, String sum, HttpServletResponse response)
			throws ServiceException {
		try {
			initResponse(response, "���Ͽ��һ����.xls");
			OutputStream out = response.getOutputStream();
			Properties config = getReportConfig();
			String urlPath = getUrlPath(config, "report.materialStock");
			MaterialStockTmplate template = new MaterialStockTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			if (deptype.equals("1")) {
				template.doExportStockByMob(list, excelstyle, news, olds, sum);
			}
			if (deptype.equals("2")) {
				template.doExportStockByCon(list, excelstyle, news, olds, sum);
			}
			// template.doExportStock(list,excelstyle,deptype);
			template.write(out);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/**
	 * �����������ͷ
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param fileName
	 *            String
	 * @throws UnsupportedEncodingException
	 */
	@Transactional(readOnly = true)
	private void initResponse(HttpServletResponse response, String fileName)
			throws UnsupportedEncodingException {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));
	}

	@Override
	protected HibernateDao<MaterialStock, Integer> getEntityDao() {
		// TODO Auto-generated method stub
		return materialStockDao;
	}

	public MaterialStockDao getMaterialStockDao() {
		return materialStockDao;
	}

	public void setMaterialStockDao(MaterialStockDao materialStockDao) {
		this.materialStockDao = materialStockDao;
	}

	/**
	 * ���ݲ�ѯ������ȡ���ϵĳ������Ϣ
	 * 
	 * @param bean
	 * @param user
	 * @return
	 */
	public Map getMaterialStockRecordMap(MaterialStockRecordBean bean,
			UserInfo user) {
		// TODO Auto-generated method stub
		String condition = ConditionGenerate.getUserQueryCondition(user);
		condition += ConditionGenerate.getCondition("ci.contractorid", bean
				.getContractorid(), "ci.contractorid", "=");
		condition += ConditionGenerate.getCondition(
				"material_stock_record.addressid", bean.getAddrID(),
				"material_stock_record.addressid", "=");
		condition += ConditionGenerate.getCondition(
				"material_stock_record.typeid", bean.getTypeid(),
				"material_stock_record.typeid", "=");
		condition += ConditionGenerate.getCondition(
				"material_stock_record.modelid", bean.getModelid(),
				"material_stock_record.modelid", "=");
		condition += ConditionGenerate.getCondition(
				"material_stock_record.materialid", bean.getMtid(),
				"material_stock_record.materialid", "=");
		condition += ConditionGenerate.getCondition("storage_type", bean
				.getStorageType(), "storage_type", "=");
		condition += ConditionGenerate.getDateCondition(
				"material_stock_record.record_date", bean.getBeginDate(),
				"material_stock_record.record_date", ">=", "00:00:00");
		condition += ConditionGenerate.getDateCondition(
				"material_stock_record.record_date", bean.getEndDate(),
				"material_stock_record.record_date", "<=", "23:59:59");
		List recordInList = materialStockDao.getMaterialInList(condition);

		List recordOutList = materialStockDao.getMaterialOutList(condition);
		Map map = new HashMap();
		if (!"1".equals(bean.getUseType())) {
			map.put("record_in_list", recordInList);
		}
		if (!"0".equals(bean.getUseType())) {
			map.put("record_out_list", recordOutList);
		}
		return map;
	}

	public MaterialStockTmplate exportMaterialStockRecord(Map map,
			String useType) throws Exception {
		// TODO Auto-generated method stub
		logger.info(map);
		Properties config = getReportConfig();
		String urlPath = getUrlPath(config, "report.materialInOutList");
		MaterialStockTmplate template = new MaterialStockTmplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExportMaterialStockRecord(map, excelstyle, useType);
		return template;
	}

}
