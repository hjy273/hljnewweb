package com.cabletech.linepatrol.material.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.material.dao.MaterialInfoDao;
import com.cabletech.linepatrol.material.domain.MaterialInfo;

@Service
@Transactional
public class MaterialInfoBo extends EntityManager<MaterialInfo, Integer> {
	private OracleIDImpl ora = new OracleIDImpl();
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	@Resource(name = "materialInfoDao")
	private MaterialInfoDao materialInfoDao;

	@Transactional(readOnly = true)
	public List getTypeList(UserInfo user) throws ServiceException {
		return materialInfoDao.getTypeList(user);
	}

	@Transactional(readOnly = true)
	public List getModelByTypeId(String id, UserInfo user)
			throws ServiceException {
		return materialInfoDao.getModelByTypeId(id, user);
	}

	@Transactional(readOnly = true)
	public boolean isHaveMaterialName(String name, String modelid)
			throws ServiceException {
		return materialInfoDao.isHaveMaterialName(name, modelid);
	}

	public void addPartBase(MaterialInfo materialInfo) throws ServiceException {
		materialInfoDao.addPartBase(materialInfo);
	}

	@Transactional(readOnly = true)
	public List getModelList(UserInfo user) throws ServiceException {
		return materialInfoDao.getModelList(user);
	}

	@Transactional(readOnly = true)
	public MaterialInfo getPartBaseById(String id, MaterialInfo materialInfo)
			throws ServiceException {
		return materialInfoDao.getPartBaseById(id, materialInfo);
	}

	public void deletePartbaseById(String id) throws ServiceException {
		materialInfoDao.deletePartbaseById(id);
	}

	@Transactional(readOnly = true)
	public List getPartBaseBean(MaterialInfo materialInfo, UserInfo user)
			throws ServiceException {
		return materialInfoDao.getPartBaseBean(materialInfo, user);
	}

	@Transactional(readOnly = true)
	public boolean isHaveMaterialType(MaterialInfo materialInfo)
			throws ServiceException {
		return materialInfoDao.isHaveMaterialType(materialInfo);
	}

	public void updatePartBase(MaterialInfo materialInfo)
			throws ServiceException {
		materialInfoDao.updatePartBase(materialInfo);
	}

	@Override
	protected HibernateDao<MaterialInfo, Integer> getEntityDao() {
		// TODO Auto-generated method stub
		return materialInfoDao;
	}

	public MaterialInfoDao getMaterialInfoDao() {
		return materialInfoDao;
	}

	public void setMaterialInfoDao(MaterialInfoDao materialInfoDao) {
		this.materialInfoDao = materialInfoDao;
	}
}
