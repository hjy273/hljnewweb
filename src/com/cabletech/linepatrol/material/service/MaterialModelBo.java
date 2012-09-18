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
	 * �����û�regionid�õ���������
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getTypesByRegionID(UserInfo user) throws ServiceException {
		return materialModelDao.getTypesByRegionID(user);
	}

	/**
	 * �����û��õ�����
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getRegions(UserInfo user) throws ServiceException {
		return materialModelDao.getRegions(user);
	}

	/**
	 * ��������id��ѯ��������
	 * 
	 * @param regionId
	 * @return
	 */
	@Transactional(readOnly = true)
	public String getRegionNameById(String regionId) throws ServiceException {
		return materialModelDao.getRegionNameById(regionId);
	}

	/**
	 * ����ʱ�ж�ͬһ�����µĲ������������Ƿ��ظ�
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
	 * �޸�ʱ�жϲ��Ϲ�������Ƿ��ظ�
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
	 * ������Ϲ��
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
	 * ����������ѯ���Ϲ��
	 * 
	 * @param user
	 */
	@Transactional(readOnly = true)
	public List getMaterialModels(MaterialModelBean bean, UserInfo user)
			throws ServiceException {
		return materialModelDao.getMaterialModels(bean, user);
	}

	/**
	 * ����id��ѯ���Ϲ��
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
	 * �޸Ĳ��Ϲ��
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
	 * ɾ����������
	 * 
	 * @param bean
	 * @return
	 */
	public void deleteModel(String id) {
		materialModelDao.deleteModel(id);
	}

	/**
	 * ���ݲ�ѯ���Ĳ��Ϲ���б���е�������
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
			initResponse(response, "���Ϲ���б���Ϣ.xls");
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
