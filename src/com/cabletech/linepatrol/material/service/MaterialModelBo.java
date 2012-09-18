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
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.material.beans.MaterialModelBean;
import com.cabletech.linepatrol.material.dao.MaterialModelDao;
import com.cabletech.linepatrol.material.domain.MaterialModel;
import com.cabletech.linepatrol.material.templates.MaterialInfoTmplate;

@Service
@Transactional
public class MaterialModelBo extends EntityManager<MaterialModel, Integer> {
	private OracleIDImpl ora = new OracleIDImpl();
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	@Resource(name = "materialModelDao")
	private MaterialModelDao materialModelDao;

	/**
	 * 根据用户regionid得到材料类型
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getTypesByRegionID(UserInfo user) throws ServiceException {
		return materialModelDao.getTypesByRegionID(user);
	}

	/**
	 * 根据用户得到区域
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getRegions(UserInfo user) throws ServiceException {
		return materialModelDao.getRegions(user);
	}

	/**
	 * 根据区域id查询区域名称
	 * 
	 * @param regionId
	 * @return
	 */
	@Transactional(readOnly = true)
	public String getRegionNameById(String regionId) throws ServiceException {
		return materialModelDao.getRegionNameById(regionId);
	}

	/**
	 * 增加时判断同一区域下的材料类型名称是否重复
	 * 
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	@Transactional(readOnly = true)
	public boolean isHaveMaterialMode(int typeid, String modelname)
			throws ServiceException {
		boolean flag = false;
		List list = materialModelDao.getModelssByTIDAndMName(typeid, modelname);
		if (list == null || list.size() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 修改时判断材料规格名称是否重复
	 * 
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	@Transactional(readOnly = true)
	public boolean getMaterialModelsByBean(MaterialModelBean bean)
			throws ServiceException {
		boolean flag = false;
		List list = materialModelDao.getMaterialModelsByBean(bean);
		if (list == null || list.size() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 保存材料规格
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional(readOnly = true)
	public void addMaterialModel(MaterialModelBean bean)
			throws ServiceException {
		MaterialModel model = new MaterialModel();
		try {
			int tid = ora.getIntSeq("material_model");
			BeanUtil.objectCopy(bean, model);
			model.setTid(tid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		materialModelDao.addMaterialModel(model);
	}

	/**
	 * 根据条件查询材料规格
	 * 
	 * @param user
	 */
	@Transactional(readOnly = true)
	public List getMaterialModels(MaterialModelBean bean, UserInfo user)
			throws ServiceException {
		return materialModelDao.getMaterialModels(bean, user);
	}

	/**
	 * 根据id查询材料规格
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public BasicDynaBean getMaterialModelById(String id)
			throws ServiceException {
		BasicDynaBean bean = null;
		List list = materialModelDao.getMaterialModelById(id);
		if (list != null && list.size() > 0) {
			bean = (BasicDynaBean) list.get(0);
		}
		return bean;
	}

	/**
	 * 修改材料规格
	 * 
	 * @param bean
	 * @return
	 */
	public void editMaterialModel(MaterialModelBean bean)
			throws ServiceException {
		MaterialModel model = new MaterialModel();
		try {
			BeanUtil.objectCopy(bean, model);
			model.setTid(bean.getTid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		materialModelDao.editMaterialModel(model);
	}

	/**
	 * 删除材料类型
	 * 
	 * @param bean
	 * @return
	 */
	public void deleteModel(String id) {
		materialModelDao.deleteModel(id);
	}

	/**
	 * 根据查询到的材料规格列表进行导出动作
	 * 
	 * @param list
	 *            List
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	@Transactional(readOnly = true)
	public void exportStorage(List list, HttpServletResponse response)
			throws ServiceException {
		try {
			initResponse(response, "材料规格列表信息.xls");
			OutputStream out = response.getOutputStream();
			Properties config = getReportConfig();
			String urlPath = getUrlPath(config, "report.materialModelList");
			MaterialInfoTmplate template = new MaterialInfoTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportModel(list, excelstyle);
			template.write(out);
		} catch (Exception e) {
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
	@Transactional(readOnly = true)
	private void initResponse(HttpServletResponse response, String fileName)
			throws UnsupportedEncodingException {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));
	}

	@Override
	protected HibernateDao<MaterialModel, Integer> getEntityDao() {
		// TODO Auto-generated method stub
		return materialModelDao;
	}

	public MaterialModelDao getMaterialModelDao() {
		return materialModelDao;
	}

	public void setMaterialModelDao(MaterialModelDao materialModelDao) {
		this.materialModelDao = materialModelDao;
	}
}
