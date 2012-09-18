package com.cabletech.sysmanage.services;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.sysmanage.dao.ConPersonDAOImpl;
import com.cabletech.sysmanage.domainobjects.ConPerson;

@Service
@Transactional
public class ConPersonBO extends EntityManager<ConPerson,String> {
	private Logger logger = Logger.getLogger(ConPersonBO.class);
	
	@Resource(name="conPersonDAOImpl")
	private ConPersonDAOImpl conPersonDAOImpl;
	
	public void addConPerson(ConPerson data) throws ServiceException {
		conPersonDAOImpl.addConPerson(data);
	}

	public boolean removeConPerson(ConPerson data) {
		try {
			conPersonDAOImpl.removeConPerson(data);
			return true;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return false;
		}
	}

	@Transactional(readOnly=true)
	public ConPerson loadConPerson(String id) {
		try {
			return conPersonDAOImpl.findConPersonById(id);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return null;
		}
	}

	public boolean updateConPerson(ConPerson data) {
		try {
			conPersonDAOImpl.updateConPerson(data);
			return true;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return false;
		}
	}

	@Transactional(readOnly=true)
	public List getAllConPerson(UserInfo userinfo) {
		try {
			List linfo = conPersonDAOImpl.getAllPersonForSearch(null,userinfo);
			return linfo;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <p>功能:按指定条件获得当前登陆代维单位的所有人员信息
	 * <p>参数:代维单位id
	 * <p>返回值:获得成功返回List,否则返回 NULL;
	 */
	@Transactional(readOnly=true)
	public List getAllPersonForSearch(ConPerson bean, UserInfo userinfo) {
		return conPersonDAOImpl.getAllPersonForSearch(bean, userinfo);
	}

	

	@Override
	protected HibernateDao<ConPerson, String> getEntityDao() {
		// TODO Auto-generated method stub
		return conPersonDAOImpl;
	}

}
