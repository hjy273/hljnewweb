package com.cabletech.linepatrol.material.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.domain.MaterialPutStorageItem;

@Repository
public class MaterialPutStorageItemDao extends
		HibernateDao<MaterialPutStorageItem, String> {
	private static Logger logger = Logger
			.getLogger(MaterialPutStorageItemDao.class.getName());

	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select to_char(t.materialid) as materialid,to_char(t.addressid) addressid, ";
		sql += " to_char(model.id) as modelid,to_char(type.id) as typeid, ";
		sql += " to_char(t.count) as count,to_char(t.applycount) as applycount,model.unit, ";
		sql += " base.name,model.modelname,type.typename,address.address ";
		sql += " from lp_mt_storage_item t,lp_mt_type type,lp_mt_model model,lp_mt_base base,lp_mt_addr address ";
		sql += " where t.materialid=base.id and t.addressid=address.id ";
		sql += " and base.modelid=model.id and model.typeid=type.id ";
		sql = sql + condition;
		logger.info("Execute sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
}
