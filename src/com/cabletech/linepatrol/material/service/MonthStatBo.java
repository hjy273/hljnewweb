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
import com.cabletech.linepatrol.material.dao.MonthStateDao;
import com.cabletech.linepatrol.material.domain.MonthStat;

@Service
@Transactional
public class MonthStatBo extends EntityManager<MonthStat, String> {
	private OracleIDImpl ora = new OracleIDImpl();
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	@Resource(name = "monthStateDao")
	private MonthStateDao monthStateDao;

	@Transactional(readOnly=true)
	public List getConsByDeptId(UserInfo userInfo) throws ServiceException {
		return monthStateDao.getConsByDeptId(userInfo);
	}
	
	@Transactional(readOnly=true)
	public List getBaseInfo(String month, String contractorid) throws ServiceException {
		return monthStateDao.getBaseInfo(month, contractorid);
	}
	
	@Transactional(readOnly=true)
	public List getIntendance(String month, String contractorid) throws ServiceException {
		return monthStateDao.getIntendance(month, contractorid);
	}
	
	@Transactional(readOnly=true)
	public List getRegionPrincipal(String month, String contractorid) throws ServiceException {
		return monthStateDao.getRegionPrincipal(month, contractorid);
	}
	
	@Transactional(readOnly=true)
	public List getMaterialName(String month, String contractorid) throws ServiceException {
		return monthStateDao.getMaterialName(month, contractorid);
	}
	
	@Transactional(readOnly=true)
	public List getMaterialInfo(String month, String contractorid) throws ServiceException {
		return monthStateDao.getMaterialInfo(month, contractorid);
	}

	@Override
	protected HibernateDao<MonthStat, String> getEntityDao() {
		// TODO Auto-generated method stub
		return monthStateDao;
	}
}
