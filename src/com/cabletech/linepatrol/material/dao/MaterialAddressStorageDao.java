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
	 * ִ�и��ݲ�ѯ������ȡ���Ͽ����Ϣ�б�
	 * 
	 * @param condition
	 *            String ��ѯ����
	 * @return List ���Ͽ����Ϣ�б�
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
				logger.info("���������ǰ.....................");
				logger.info("����" + stock.getMaterialId());
				logger.info("�ص�" + stock.getAddressId());
				logger.info("��������" + stock.getOldStock());
				logger.info("��������" + stock.getNewStock());
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
				logger.info("��������Ժ�.....................");
				logger.info("����" + stock.getMaterialId());
				logger.info("�ص�" + stock.getAddressId());
				logger.info("��������" + stock.getOldStock());
				logger.info("��������" + stock.getNewStock());
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
				logger.info("��������Ժ�.....................");
				logger.info("����" + stock.getMaterialId());
				logger.info("�ص�" + stock.getAddressId());
				logger.info("��������" + stock.getOldStock());
				logger.info("��������" + stock.getNewStock());
			}
		}
	}

}
