package com.cabletech.linepatrol.material.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.domain.MaterialApplyItem;

@Repository
public class MaterialApplyItemDao extends
		HibernateDao<MaterialApplyItem, String> {
	private static Logger logger = Logger.getLogger(MaterialApplyItemDao.class
			.getName());

	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select to_char(t.materialid) as materialid, ";
		sql += " to_char(t.addressid) as addressid,to_char(t.count) as count, ";
		sql += " to_char(model.id) as modelid,to_char(type.id) as typeid,model.unit, ";
		sql += " base.name,model.modelname,type.typename,address.address ";
		sql += " from lp_mt_new_item t,lp_mt_type type,lp_mt_model model,lp_mt_base base,lp_mt_addr address ";
		sql += " where t.materialid=base.id and t.addressid=address.id ";
		sql += " and base.modelid=model.id and model.typeid=type.id ";
		sql += condition;
		logger.info("Execute sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
}
