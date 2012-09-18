package com.cabletech.linepatrol.material.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
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
import com.cabletech.linepatrol.material.beans.MaterialTypeBean;
import com.cabletech.linepatrol.material.dao.MaterialTypeDao;
import com.cabletech.linepatrol.material.domain.MaterialType;
import com.cabletech.linepatrol.material.templates.MaterialInfoTmplate;

@Service
@Transactional
public class MaterialTypeBo extends EntityManager<MaterialType,Integer> {
	private OracleIDImpl ora = new OracleIDImpl();
	private static String CONTENT_TYPE = "application/vnd.ms-excel";
	
	@Resource(name="materialTypeDao")
	private MaterialTypeDao materialTypeDao;
	
	/**
	 * �����û��õ�����
	 */
	@Transactional(readOnly=true)
	public List getRegions(UserInfo userInfo) throws ServiceException {
		return materialTypeDao.getRegions(userInfo);	
	}
	
	/**
	 * ��������id��ѯ��������
	 */
	@Transactional(readOnly=true)
	public String getRegionNameById(String regionId) throws ServiceException {
		return materialTypeDao.getRegionNameById(regionId);
	}
	
	/**
	 * ����ʱ�ж�ͬһ�����µĲ������������Ƿ��ظ�
	 */
	@Transactional(readOnly=true)
	public boolean isHaveMaterialType(String regionID,String typeName) throws ServiceException {
		boolean flag=false;
		List list = materialTypeDao.getTypesByRIDAndIName(regionID, typeName);
		if(list==null || list.size()==0){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * �޸�ʱ�жϲ������������Ƿ��ظ�
	 */
	@Transactional(readOnly=true)
	public boolean isHaveMaterialType(MaterialTypeBean bean) throws ServiceException {
		boolean flag=false;
		List list = materialTypeDao.getMaterialTypesByBean(bean);
		if(list==null || list.size()==0){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * �����������
	 */
	public MaterialType addMaterialType(MaterialType type) throws ServiceException {
		materialTypeDao.addMeterialType(type);
		return type;
	}
	
	
	/**
	 * ����������ѯ��������
	 * @param user 
	 */
	@Transactional(readOnly=true)
	public List getMaterialTypes(MaterialTypeBean bean, UserInfo user) throws ServiceException {
		return materialTypeDao.getMaterialTypes(bean,user);
	}
	
	
	/**
	 * ����id��ѯ��������
	 */
	@Transactional(readOnly=true)
	public BasicDynaBean getMaterialTypById(String id){
		BasicDynaBean bean = null;
		List list= materialTypeDao.getMaterialTypById(id);
		if(list!=null && list.size()>0){
			bean = (BasicDynaBean)list.get(0);
		}
		return bean;
	}
	
	/**
	 * �޸Ĳ�������
	 */
	public void editMaterialType(MaterialTypeBean bean) throws ServiceException {
		MaterialType type = new MaterialType();
		try {
			BeanUtil.objectCopy(bean, type);
			type.setTid(bean.getTid());
		}catch(Exception e){
			e.printStackTrace();
		}
		materialTypeDao.editMaterialType(type);
	}
	
	/**
	 * ɾ����������
	 */
	public void deleteType(String id) throws ServiceException {
		materialTypeDao.deleteType(id);
	}
	
	
	/**
	 * ���ݲ�ѯ�����޲��������б���е�������
	 */
	@Transactional(readOnly=true)
	public void exportStorage(List list, HttpServletResponse response) throws ServiceException {
		try{
			initResponse(response, "���������б���Ϣ.xls");
			OutputStream out = response.getOutputStream();
			Properties config = getReportConfig();
			String urlPath = getUrlPath(config, "report.materialTypeList");
			MaterialInfoTmplate template = new MaterialInfoTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportType(list, excelstyle);
			template.write(out);
		}catch(Exception e){
			e.getStackTrace();
		}
	}
	
	/**
	 * �����������ͷ
	 */
	private void initResponse(HttpServletResponse response, String fileName)
	throws UnsupportedEncodingException {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));
	}


	@Override
	protected HibernateDao<MaterialType, Integer> getEntityDao() {
		return materialTypeDao;
	}

	public MaterialTypeDao getMaterialTypeDao() {
		return materialTypeDao;
	}

	public void setMaterialTypeDao(MaterialTypeDao materialTypeDao) {
		this.materialTypeDao = materialTypeDao;
	}
}
