package com.cabletech.linepatrol.commons.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.module.ReturnMaterial;
import com.cabletech.linepatrol.commons.module.UseMaterial;
import com.cabletech.linepatrol.material.domain.MaterialAddressStorage;
import com.cabletech.linepatrol.material.domain.MaterialStorage;

@Repository
public class ReturnMaterialDAO extends HibernateDao<ReturnMaterial, String> {
	/**
	 * ����������ղ���������Ϣ
	 * 
	 * @param list
	 */
	public void saveList(List<ReturnMaterial> list) {
		for (int i = 0; list != null && i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * ���ݶ���ϵͳ���id�Ѿ���������ɾ�����ղ���
	 * 
	 * @param replyid
	 */
	public void deleteList(String replyid, String objectType) {
		String sql = "delete from LP_MATERIAL_RETURN t where t.object_id='"
				+ replyid + "' and t.use_type='" + objectType + "'";
		this.getJdbcTemplate().execute(sql);
	}
	
	public List<ReturnMaterial> getReturnMaterials(String objectid, String useType) {
		String hql = " from ReturnMaterial m where m.objectId='" + objectid
				+ "' and m.useType='" + useType + "'";
		return find(hql);
	}

	/**
	 * ִ�и��ݲ�ѯ������ȡ������Ϣ�б�
	 * 
	 * @param condition
	 *            String ��ѯ����
	 * @return List ������Ϣ�б�
	 * @throws Exception
	 */
	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = "select t.id,to_char(t.material_id) as materialid,to_char(t.storage_address_id) as addressid, ";
		sql = sql + " to_char(t.return_number) as use_number, ";
		sql = sql + " t.storage_type as storagetype, ";
		sql = sql + " mt.id as typeid,mm.id as modelid,ma.address, ";
		sql = sql
				+ " decode(t.storage_type,'0','���ɲ���','1','��������','') as storage_type,t.storage_type as storagetype, ";
		sql = sql
				+ " decode(t.storage_type,'0',ms.oldstock,'1',ms.newstock,'0') as storage_number, ";
		sql = sql
				+ " mb.name||'��'||mt.typename||'����'||mm.modelname||'��' as material_name, ";
		sql = sql
				+ " decode(t.storage_type,'0',ms.oldstock,'1',ms.newstock,'0') as stock_number ";
		sql = sql + " from LP_MATERIAL_RETURN t,LP_MT_BASE mb, ";
		sql = sql + " LP_MT_MODEL mm,LP_MT_TYPE mt,LP_MT_ADDR_STOCK ms, ";
		sql = sql + " LP_MT_ADDR ma ";
		sql = sql
				+ " where t.material_id=mb.id and mb.modelid=mm.id and mm.typeid=mt.id ";
		sql = sql
				+ " and t.storage_address_id=ma.id and t.material_id=ms.materialid ";
		sql = sql + " and t.storage_address_id=ms.addressid ";
		sql = sql + condition;
		logger.info("Execute sql:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}

	/**
	 * ִ�и���ĳһ��������Ϣ��Ӳ��ϻ��տ����Ϣ
	 * 
	 * @param applyMaterial
	 *            ReturnMaterial ĳһ��������Ϣ
	 * @param contractorId
	 *            String ��ά��λ���
	 * @return String ɾ������ʹ�ÿ����Ϣ����
	 * @throws Exception
	 */
	public void writeMaterialNotUseNumber(ReturnMaterial applyMaterial,
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
				double useNumber = applyMaterial.getReturnNumber();
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
				double useNumber = applyMaterial.getReturnNumber();
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
				double useNumber = applyMaterial.getReturnNumber();
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
				double useNumber = applyMaterial.getReturnNumber();
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
	 * ִ�и���ĳһ��������Ϣɾ�����ϻ��տ����Ϣ
	 * 
	 * @param applyMaterial
	 *            RemedyMaterialBaseĳһ��������Ϣ
	 * @param contractorId
	 *            String ��ά��λ���
	 * @return String ��Ӳ���ʹ�ÿ����Ϣ����
	 * @throws Exception
	 */
	public void writeMaterialUseNumber(ReturnMaterial applyMaterial,
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
				double useNumber = applyMaterial.getReturnNumber();
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
				}
			}
			if (CommonConstant.NEW_STORAGE_MATERIAL.equals(applyMaterial
					.getStorageType())) {
				float newStorage = 0;
				if (addrStorage.getNewStock() != null) {
					newStorage = addrStorage.getNewStock().floatValue();
				}
				double useNumber = applyMaterial.getReturnNumber();
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
				double useNumber = applyMaterial.getReturnNumber();
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
				}
			}
			if (CommonConstant.NEW_STORAGE_MATERIAL.equals(applyMaterial
					.getStorageType())) {
				float newStorage = 0;
				if (materialStorage.getNewStock() != null) {
					newStorage = materialStorage.getNewStock().floatValue();
				}
				double useNumber = applyMaterial.getReturnNumber();
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
				}
			}
		}
	}

}
