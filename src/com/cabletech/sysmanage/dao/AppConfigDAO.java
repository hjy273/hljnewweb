package com.cabletech.sysmanage.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.sysmanage.domainobjects.AppConfig;
@Repository
public class AppConfigDAO extends HibernateDao<AppConfig,String>{
	public void saveOrUpdate(AppConfig appconfig){
		super.save(appconfig);
	}
}
