package com.cabletech.linepatrol.commons.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.module.UseMaterial;
import com.cabletech.linepatrol.material.domain.MaterialAddressStorage;
import com.cabletech.linepatrol.material.domain.MaterialStorage;

@Repository
public class UseMaterialDAO extends HibernateDao<UseMaterial, String> {
	/**
	 * 批量保存使用材料数量信息
	 * 
	 * @param list
	 */
	public void saveList(List<UseMaterial> list) {
		for (int i = 0; list != null && i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 根据对象系统编号id已经对象类型删除材料
	 * 
	 * @param replyid
	 */
	public void deleteList(String replyid, String objectType) {
		String sql = "delete from LP_MATERIAL_USED m where m.object_id='"
				+ replyid + "' and m.use_type='" + objectType + "'";
		this.getJdbcTemplate().execute(sql);
	}

	public List<UseMaterial> getUseMaterials(String objectid, String useType) {
		String hql = " from UseMaterial m where m.objectId='" + objectid
				+ "' and m.useType='" + useType + "'";
		return find(hql);
	}

	/**
	 * 执行根据查询条件获取材料信息列表
	 * 
	 * @param condition
	 *            String 查询条件
	 * @return List 材料信息列表
	 * @throws Exception
	 */
	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = "select t.id,to_char(t.material_id) as materialid, ";
		sql += " to_char(t.storage_address_id) as addressid, ";
		sql += " to_char(t.used_number) as use_number, ";
		sql += " t.storage_type as storagetype, ";
		sql += " mt.id as typeid,mm.id as modelid,ma.address, ";
		sql += " decode(t.storage_type,'0','利旧材料','1','新增材料','') as storage_type, ";
		sql += " t.storage_type as storagetype, ";
		sql += " decode(t.storage_type,'0',ms.oldstock,'1',ms.newstock,'0') as storage_number, ";
		sql += " mb.name||'（'||mt.typename||'）（'||mm.modelname||'）' as material_name, ";
		sql += " decode(t.storage_type,'0',ms.oldstock,'1',ms.newstock,'0') as stock_number ";
		sql += " from LP_MATERIAL_USED t,LP_MT_BASE mb, ";
		sql += " LP_MT_MODEL mm,LP_MT_TYPE mt,LP_MT_ADDR_STOCK ms, LP_MT_ADDR ma ";
		sql += " where t.material_id=mb.id and mb.modelid=mm.id and mm.typeid=mt.id ";
		sql += " and t.storage_address_id=ma.id and t.material_id=ms.materialid ";
		sql += " and t.storage_address_id=ms.addressid ";
		sql += condition;
		logger.info("Execute sql:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}

	/**
	 * 执行根据某一个材料信息删除材料使用库存信息
	 * 
	 * @param applyMaterial
	 *            ReturnMaterial 某一个材料信息
	 * @param contractorId
	 *            String 代维单位编号
	 * @return String 删除材料使用库存信息编码
	 * @throws Exception
	 */
	public void writeMaterialNotUseNumber(UseMaterial applyMaterial,
			String contractorId) {
		String addrHql = " from MaterialAddressStorage where 1=1 and materialId=?"
				+ " and addressId=?";
		String contractorHql = " from MaterialStorage where 1=1 and materialId=?"
				+ " and contractorid=?";
		List list = super.find(addrHql, applyMaterial.getMaterialId(),
				applyMaterial.getStorageAddressId());
		logger.info("material address storage size=" + list.size());
		if (list != null && list.size() > 0) {
			MaterialAddressStorage addrStorage = (MaterialAddressStorage) list
					.get(0);
			logger.info("material address old storage before change ="
					+ addrStorage.getOldStock());
			logger.info("material address new storage before change ="
					+ addrStorage.getNewStock());
			if (CommonConstant.OLD_STORAGE_MATERIAL.equals(applyMaterial
					.getStorageType())) {
				float oldStorage = 0;
				if (addrStorage.getOldStock() != null) {
					oldStorage = addrStorage.getOldStock().floatValue();
				}
				double useNumber = applyMaterial.getUsedNumber();
				logger.info("material address old storage after change ="
						+ (oldStorage + useNumber));
				double changedOldStorage = oldStorage + useNumber;
				addrStorage.setOldStock(Float.valueOf(Double
						.toString(changedOldStorage)));
				logger.info("material address old storage after change ="
						+ addrStorage.getOldStock());
				super.getHibernateTemplate().saveOrUpdate(addrStorage);
			}
			if (CommonConstant.NEW_STORAGE_MATERIAL.equals(applyMaterial
					.getStorageType())) {
				float newStorage = 0;
				if (addrStorage.getNewStock() != null) {
					newStorage = addrStorage.getNewStock().floatValue();
				}
				double useNumber = applyMaterial.getUsedNumber();
				logger.info("material address new storage after change ="
						+ (newStorage + useNumber));
				double changedNewStorage = newStorage + useNumber;
				addrStorage.setNewStock(Float.valueOf(Double
						.toString(changedNewStorage)));
				logger.info("material address new storage after change ="
						+ addrStorage.getNewStock());
				super.getHibernateTemplate().saveOrUpdate(addrStorage);
			}
		}
		list = super.find(contractorHql, applyMaterial.getMaterialId(),
				contractorId);
		logger.info("material storage size=" + list.size());
		if (list != null && list.size() > 0) {
			MaterialStorage materialStorage = (MaterialStorage) list.get(0);
			logger.info("material old storage before change ="
					+ materialStorage.getOldStock());
			logger.info("material new storage before change ="
					+ materialStorage.getNewStock());
			if (CommonConstant.OLD_STORAGE_MATERIAL.equals(applyMaterial
					.getStorageType())) {
				float oldStorage = 0;
				if (materialStorage.getOldStock() != null) {
					oldStorage = materialStorage.getOldStock().floatValue();
				}
				double useNumber = applyMaterial.getUsedNumber();
				logger.info("material old storage after change ="
						+ (oldStorage + useNumber));
				double changedOldStorage = oldStorage + useNumber;
				materialStorage.setOldStock(Float.valueOf(Double
						.toString(changedOldStorage)));
				logger.info("material old storage after change ="
						+ materialStorage.getOldStock());
				super.getHibernateTemplate().saveOrUpdate(materialStorage);
			}
			if (CommonConstant.NEW_STORAGE_MATERIAL.equals(applyMaterial
					.getStorageType())) {
				float newStorage = 0;
				if (materialStorage.getNewStock() != null) {
					newStorage = materialStorage.getNewStock().floatValue();
				}
				double useNumber = applyMaterial.getUsedNumber();
				logger.info("material new storage after change ="
						+ (newStorage + useNumber));
				double changedNewStorage = newStorage + useNumber;
				materialStorage.setNewStock(Float.valueOf(Double
						.toString(changedNewStorage)));
				logger.info("material new storage after change ="
						+ materialStorage.getNewStock());
				super.getHibernateTemplate().saveOrUpdate(materialStorage);
			}
		}
	}

	/**
	 * 执行根据某一个材料信息添加材料使用库存信息
	 * 
	 * @param applyMaterial
	 *            RemedyMaterialBase某一个材料信息
	 * @param contractorId
	 *            String 代维单位编号
	 * @return String 添加材料使用库存信息编码
	 * @throws Exception
	 */
	public boolean writeMaterialUseNumber(UseMaterial applyMaterial,
			String contractorId) {
		String addrHql = " from MaterialAddressStorage where 1=1 and materialId=?"
				+ " and addressId=?";
		String contractorHql = " from MaterialStorage where 1=1 and materialId=?"
				+ " and contractorid=?";
		List list = super.find(addrHql, applyMaterial.getMaterialId(),
				applyMaterial.getStorageAddressId());
		// logger.info("material address storage size=" + list.size());
		if (list != null && list.size() > 0) {
			MaterialAddressStorage addrStorage = (MaterialAddressStorage) list
					.get(0);
			logger.info("material address old storage before change ="
					+ addrStorage.getOldStock());
			logger.info("material address new storage before change ="
					+ addrStorage.getNewStock());
			if (CommonConstant.OLD_STORAGE_MATERIAL.equals(applyMaterial
					.getStorageType())) {
				float oldStorage = 0;
				if (addrStorage.getOldStock() != null) {
					oldStorage = addrStorage.getOldStock().floatValue();
				}
				double useNumber = applyMaterial.getUsedNumber();
				logger.info("material address old storage after change ="
						+ (oldStorage - useNumber));
				if (oldStorage - useNumber >= 0) {
					double changedOldStorage = oldStorage - useNumber;
					addrStorage.setOldStock(Float.valueOf(Double
							.toString(changedOldStorage)));
					logger.info("material address old storage after change ="
							+ addrStorage.getOldStock());
					super.getHibernateTemplate().saveOrUpdate(addrStorage);
				} else {
					return false;
				}
			}
			if (CommonConstant.NEW_STORAGE_MATERIAL.equals(applyMaterial
					.getStorageType())) {
				float newStorage = 0;
				if (addrStorage.getNewStock() != null) {
					newStorage = addrStorage.getNewStock().floatValue();
				}
				double useNumber = applyMaterial.getUsedNumber();
				logger.info("material address new storage after change ="
						+ (newStorage - useNumber));
				if (newStorage - useNumber >= 0) {
					double changedNewStorage = newStorage - useNumber;
					addrStorage.setNewStock(Float.valueOf(Double
							.toString(changedNewStorage)));
					logger.info("material address new storage after change ="
							+ addrStorage.getNewStock());
					super.getHibernateTemplate().saveOrUpdate(addrStorage);
				} else {
					return false;
				}
			}
		}
		list = super.find(contractorHql, applyMaterial.getMaterialId(),
				contractorId);
		// logger.info("material storage size=" + list.size());
		if (list != null && list.size() > 0) {
			MaterialStorage materialStorage = (MaterialStorage) list.get(0);
			logger.info("material old storage before change ="
					+ materialStorage.getOldStock());
			logger.info("material new storage before change ="
					+ materialStorage.getNewStock());
			if (CommonConstant.OLD_STORAGE_MATERIAL.equals(applyMaterial
					.getStorageType())) {
				float oldStorage = 0;
				if (materialStorage.getOldStock() != null) {
					oldStorage = materialStorage.getOldStock().floatValue();
				}
				double useNumber = applyMaterial.getUsedNumber();
				logger.info("material old storage after change ="
						+ (oldStorage - useNumber));
				if (oldStorage - useNumber >= 0) {
					double changedOldStorage = oldStorage - useNumber;
					materialStorage.setOldStock(Float.valueOf(Double
							.toString(changedOldStorage)));
					logger.info("material old storage after change ="
							+ materialStorage.getOldStock());
					super.getHibernateTemplate().saveOrUpdate(materialStorage);
				} else {
					return false;
				}
			}
			if (CommonConstant.NEW_STORAGE_MATERIAL.equals(applyMaterial
					.getStorageType())) {
				float newStorage = 0;
				if (materialStorage.getNewStock() != null) {
					newStorage = materialStorage.getNewStock().floatValue();
				}
				double useNumber = applyMaterial.getUsedNumber();
				logger.info("material new storage after change ="
						+ (newStorage - useNumber));
				if (newStorage - useNumber >= 0) {
					double changedNewStorage = newStorage - useNumber;
					materialStorage.setNewStock(Float.valueOf(Double
							.toString(changedNewStorage)));
					logger.info("material new storage after change ="
							+ materialStorage.getNewStock());
					super.getHibernateTemplate().saveOrUpdate(materialStorage);
				} else {
					return false;
				}
			}
		}
		return true;
	}

}
