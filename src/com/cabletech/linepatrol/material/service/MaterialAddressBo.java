package com.cabletech.linepatrol.material.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.material.dao.MaterialAddressDao;
import com.cabletech.linepatrol.material.domain.MaterialAddress;

@Service
@Transactional
public class MaterialAddressBo extends EntityManager<MaterialAddress, Integer> {
	private OracleIDImpl ora = new OracleIDImpl();
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	@Resource(name = "materialAddressDao")
	private MaterialAddressDao materialAddressDao;

	@Transactional(readOnly=true)
	public List judgeData(MaterialAddress materialAddress, String flag) throws ServiceException {
		return materialAddressDao.judgeData(materialAddress, flag);
	}

	public void addPartAddress(MaterialAddress materialAddress) throws ServiceException {
		materialAddressDao.addPartAddress(materialAddress);
	}

	@Transactional(readOnly=true)
	public MaterialAddress getPartaddressById(String id,
			MaterialAddress materialAddress) throws ServiceException {
		return materialAddressDao.getPartaddressById(id, materialAddress);
	}

	public void deletePartaddressById(String id) throws ServiceException {
		materialAddressDao.deletePartaddressById(id);
	}

	@Transactional(readOnly=true)
	public List getPartaddressBean(MaterialAddress materialAddress) throws ServiceException {
		return materialAddressDao.getPartaddressBean(materialAddress);
	}

	public void updatePartaddress(MaterialAddress materialAddress) throws ServiceException {
		materialAddressDao.updatePartaddress(materialAddress);
	}

	@Override
	protected HibernateDao<MaterialAddress, Integer> getEntityDao() throws ServiceException {
		// TODO Auto-generated method stub
		return materialAddressDao;
	}
}
