package com.cabletech.linepatrol.trouble.services;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.trouble.dao.TroubleProcessUnitDAO;
import com.cabletech.linepatrol.trouble.module.TroubleProcessUnit;
@Service
@Transactional
public class TroubleProcessUnitBO extends EntityManager<TroubleProcessUnit,String> {

	@Resource(name="troubleProcessUnitDAO")
	private TroubleProcessUnitDAO dao;

	public void addTroubleProcessUnit(TroubleProcessUnit processUnit) throws ServiceException {
		dao.save(processUnit);
	}

	@Transactional(readOnly=true)
	public TroubleProcessUnit loadTroubleProcessUnit(String ID) throws ServiceException {
		return dao.findByUnique("id",ID);
	}

	/*@Transactional(readOnly=true)
	public List queryTroubleInfo(String operationName) throws ServiceException{
		return dao.queryTaskOperation(operationName);
	}*/

	public void delTroubleProcessUnit(String id) {
		dao.delete(id);
	}


	public void updateTroubleProcessUnit(TroubleProcessUnit processUnit) throws ServiceException {
		dao.save(processUnit);
	}



	@Override
	protected HibernateDao<TroubleProcessUnit, String> getEntityDao() {
		return dao;
	}
}
