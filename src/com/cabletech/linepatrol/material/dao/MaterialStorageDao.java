package com.cabletech.linepatrol.material.dao;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.domain.MaterialPutStorageItem;
import com.cabletech.linepatrol.material.domain.MaterialStorage;

@Repository
public class MaterialStorageDao extends HibernateDao<MaterialStorage, Integer> {
	private static Logger logger = Logger.getLogger(MaterialStorageDao.class
			.getName());

	/**
	 * 执行根据查询条件获取材料信息列表
	 * 
	 * @param materialId
	 *            String 查询条件
	 * @return List 材料信息列表
	 * @throws Exception
	 */
	public String getMaterialName(String materialId) {
		// TODO Auto-generated method stub
		String sql = "select distinct t.id,t.name ";
		sql = sql + " from LP_MT_BASE t,LP_MT_MODEL mt,LP_MT_TYPE tt ";
		sql = sql + " where t.modelid=mt.id and mt.typeid=tt.id ";
		sql = sql + " and t.id='" + materialId + "' ";
		logger.info("Execute sql:" + sql);
		List list = super.getJdbcTemplate().queryForBeans(sql);
		String materialName = "";
		if (list != null && !list.isEmpty()) {
			materialName = (String) ((DynaBean) list.get(0)).get("name");
		}
		return materialName;
	}

	/**
	 * 执行根据查询条件获取材料库存信息列表
	 * 
	 * @param condition
	 *            String 查询条件
	 * @return List 材料库存信息列表
	 * @throws Exception
	 */
	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = " ";
		sql = sql + condition;
		logger.info("Execute sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	public void saveList(List itemList, String contractorId) {
		MaterialPutStorageItem item;
		MaterialStorage storage;
		List list;
		String hql = " from MaterialStorage t where materialId=? and contractorId=? ";
		for (int i = 0; itemList != null && i < itemList.size(); i++) {
			item = (MaterialPutStorageItem) itemList.get(i);
			list = super.find(hql, item.getMaterialId() + "", contractorId);
			if (list != null && !list.isEmpty()) {
				storage = (MaterialStorage) list.get(0);
				super.initObject(storage);
				logger.info("材料入库以前.....................");
				logger.info("材料" + storage.getMaterialId());
				logger.info("利旧数量" + storage.getOldStock());
				logger.info("新增数量" + storage.getNewStock());
				if ("0".equals(item.getState())) {
					storage.setOldStock(new Float(storage.getOldStock()
							.floatValue()
							+ item.getCount()));
				} else {
					storage.setNewStock(new Float(storage.getNewStock()
							.floatValue()
							+ item.getCount()));
				}
				super.initObject(storage);
				super.save(storage);
				logger.info("材料入库以后.....................");
				logger.info("材料" + storage.getMaterialId());
				logger.info("利旧数量" + storage.getOldStock());
				logger.info("新增数量" + storage.getNewStock());
			} else {
				storage = new MaterialStorage();
				storage.setContractorId(contractorId);
				storage.setMaterialId(item.getMaterialId() + "");
				storage.setMaterialName(getMaterialName(item.getMaterialId()
						+ ""));
				storage.setNewStock(new Float(0.0));
				storage.setOldStock(new Float(0.0));
				if ("0".equals(item.getState())) {
					storage.setOldStock(new Float(storage.getOldStock()
							.floatValue()
							+ item.getCount()));
				} else {
					storage.setNewStock(new Float(storage.getNewStock()
							.floatValue()
							+ item.getCount()));
				}
				super.initObject(storage);
				super.save(storage);
				logger.info("材料入库以后.....................");
				logger.info("材料" + storage.getMaterialId());
				logger.info("利旧数量" + storage.getOldStock());
				logger.info("新增数量" + storage.getNewStock());
			}
		}
	}

}
