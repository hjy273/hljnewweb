package com.cabletech.linepatrol.material.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.domain.MaterialAddressStorage;
import com.cabletech.linepatrol.material.domain.MaterialPutStorageItem;

@Repository
public class MaterialAddressStorageDao extends
		HibernateDao<MaterialAddressStorage, Integer> {
	private static Logger logger = Logger
			.getLogger(MaterialAddressStorageDao.class.getName());

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

	public void saveList(List itemList) {
		MaterialPutStorageItem item;
		MaterialAddressStorage stock;
		List list;
		String hql = " from MaterialAddressStorage t where addressId=? and materialId=? ";
		for (int i = 0; itemList != null && i < itemList.size(); i++) {
			item = (MaterialPutStorageItem) itemList.get(i);
			list = super.find(hql, item.getAddressId() + "", item
					.getMaterialId()
					+ "");
			if (list != null && !list.isEmpty()) {
				stock = (MaterialAddressStorage) list.get(0);
				super.initObject(stock);
				logger.info("材料入库以前.....................");
				logger.info("材料" + stock.getMaterialId());
				logger.info("地点" + stock.getAddressId());
				logger.info("利旧数量" + stock.getOldStock());
				logger.info("新增数量" + stock.getNewStock());
				if ("0".equals(item.getState())) {
					stock.setOldStock(new Float(stock.getOldStock()
							.floatValue()
							+ item.getCount()));
				} else {
					stock.setNewStock(new Float(stock.getNewStock()
							.floatValue()
							+ item.getCount()));
				}
				super.initObject(stock);
				super.save(stock);
				logger.info("材料入库以后.....................");
				logger.info("材料" + stock.getMaterialId());
				logger.info("地点" + stock.getAddressId());
				logger.info("利旧数量" + stock.getOldStock());
				logger.info("新增数量" + stock.getNewStock());
			} else {
				stock = new MaterialAddressStorage();
				stock.setAddressId(item.getAddressId() + "");
				stock.setMaterialId(item.getMaterialId() + "");
				stock.setNewStock(new Float(0));
				stock.setOldStock(new Float(0));
				if ("0".equals(item.getState())) {
					stock.setOldStock(new Float(stock.getOldStock()
							.floatValue()
							+ item.getCount()));
				} else {
					stock.setNewStock(new Float(stock.getNewStock()
							.floatValue()
							+ item.getCount()));
				}
				super.initObject(stock);
				super.save(stock);
				logger.info("材料入库以后.....................");
				logger.info("材料" + stock.getMaterialId());
				logger.info("地点" + stock.getAddressId());
				logger.info("利旧数量" + stock.getOldStock());
				logger.info("新增数量" + stock.getNewStock());
			}
		}
	}

}
