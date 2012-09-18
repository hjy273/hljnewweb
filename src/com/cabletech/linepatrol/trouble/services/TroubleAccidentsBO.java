package com.cabletech.linepatrol.trouble.services;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.trouble.dao.TroubleAccidentsDAO;
import com.cabletech.linepatrol.trouble.module.TroubleAccidents;
import com.cabletech.linepatrol.trouble.module.TroubleInfo;
@Service
@Transactional
public class TroubleAccidentsBO extends EntityManager<TroubleAccidents,String> {

	@Resource(name="troubleAccidentsDAO")
	private TroubleAccidentsDAO dao;

	public void addTroubleAccidents(TroubleAccidents troubleAccidents) throws ServiceException {
		dao.save(troubleAccidents);
	}

	@Transactional(readOnly=true)
	public TroubleAccidents loadTroubleAccidents(String ID) throws ServiceException {
		return dao.findByUnique("id",ID);
	}

	/*@Transactional(readOnly=true)
	public List queryTroubleInfo(String operationName) throws ServiceException{
		return dao.queryTaskOperation(operationName);
	}*/

	public void delTroubleAccidents(String id) {
		dao.delete(id);
	}


	public void updateTroubleAccidents(TroubleAccidents troubleAccidents) throws ServiceException {
		dao.save(troubleAccidents);
	}



	@Override
	protected HibernateDao<TroubleAccidents, String> getEntityDao() {
		return dao;
	}
}
