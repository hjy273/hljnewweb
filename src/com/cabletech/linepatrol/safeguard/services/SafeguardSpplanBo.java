package com.cabletech.linepatrol.safeguard.services;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSpplanDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardSpplan;

@Service
@Transactional
public class SafeguardSpplanBo extends EntityManager<SafeguardSpplan, String> {

	private static Logger logger = Logger.getLogger(SafeguardSpplanBo.class
			.getName());
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@Override
	protected HibernateDao<SafeguardSpplan, String> getEntityDao() {
		return safeguardSpplanDao;
	}

	@Resource(name = "safeguardSpplanDao")
	private SafeguardSpplanDao safeguardSpplanDao;
	
	/**
	 * 由特巡计划ID获得保障方案ID
	 * @param spId：特巡计划ID
	 * @return
	 */
	public String getPlanIdBySpId(String spId){
		return safeguardSpplanDao.getPlanIdBySpId(spId);
	}
}
