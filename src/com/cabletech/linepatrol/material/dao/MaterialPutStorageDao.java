package com.cabletech.linepatrol.material.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.domain.MaterialPutStorage;

@Repository
public class MaterialPutStorageDao extends HibernateDao<MaterialPutStorage, String> {
	private static Logger logger = Logger.getLogger(MaterialPutStorageDao.class
			.getName());

	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select t.id from lp_mt_storage t where 1=1 ";
		sql = sql + condition;
		logger.info("Execute sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
}
