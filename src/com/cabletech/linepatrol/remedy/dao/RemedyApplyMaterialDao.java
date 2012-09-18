package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.beans.RemedyMaterialBean;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.domain.RemedyApplyMaterial;

public class RemedyApplyMaterialDao extends RemedyBaseDao {
	private MaterialStorageDao storageDao = new MaterialStorageDao();

	/**
	 * ִ�и��ݲ�ѯ������ȡ�������������Ϣ�б�
	 * 
	 * @param condition
	 *            String ��ѯ����
	 * @return List �������������Ϣ�б�
	 * @throws Exception
	 */
	public List queryList(String condition) throws Exception {
		// TODO Auto-generated method stub
		logger(RemedyApplyMaterialDao.class);
		String sql = "select t.id,to_char(t.materialid) as materialid,to_char(t.addressid) as addressid, ";
		sql = sql + " to_char(t.materialcount) as use_number,to_char(t.price) as price,to_char(t.totalprice) as totalprice, ";
		sql = sql
				+ " to_char(t.storage_number) as storage_number,remedy.projectname, ";
		sql = sql
				+ " decode(t.storage_type,'0','���ɲ���','1','��������','') as storage_type,t.storage_type as storagetype, ";
		sql = sql
				+ " mt.id as typeid,mm.id as modelid,c.contractorname,ma.address, ";
		sql = sql
				+ " mb.name||'��'||mt.typename||'����'||mm.modelname||'��' as material_name, ";
		sql = sql
				+ " decode(t.storage_type,'0',ms.oldstock,'1',ms.newstock,'0') as stock_number, ";
		sql = sql
				+ " decode(t.storage_type,'0',decode(sign(ms.oldstock-t.materialcount),-1,'-','+'),'1',decode(sign(ms.newstock-t.materialcount),-1,'-','+'),'') as is_enough_material ";
		sql = sql
				+ " from LINEPATROL_REMEDY_MATERIAL t,LINEPATROL_MT_BASE mb, ";
		sql = sql
				+ " LINEPATROL_MT_MODEL mm,LINEPATROL_MT_TYPE mt,LINEPATROL_MT_ADDR_STOCK ms, ";
		sql = sql
				+ " LINEPATROL_MT_ADDR ma,LINEPATROL_REMEDY remedy,contractorinfo c ";
		sql = sql
				+ " where t.materialid=mb.id and mb.modelid=mm.id and mm.typeid=mt.id ";
		sql = sql + " and t.addressid=ma.id and t.materialid=ms.materialid ";
		sql = sql + " and t.addressid=ms.addressid and t.remedyid=remedy.id ";
		sql = sql + " and remedy.contractorid=c.contractorid ";
		sql = sql + condition;
		try {
			logger.info("Execute sql:" + sql);
			QueryUtil queryUtil = new QueryUtil();
			List list = queryUtil.queryBeans(sql);
			if (list != null) {
				return list;
			} else {
				return new ArrayList();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw e;
		}
	}

	/**
	 * ִ�и������������ű����������������Ϣ
	 * 
	 * @param applyId
	 *            String ����������
	 * @param materialIdList
	 *            List ���������в��ϱ���б�
	 * @param materialUseNumberList
	 *            List ���������в���ʹ�������б�
	 * @param materialStorageAddrList
	 *            List ���������в��Ͽ��ص��б�
	 * @param materialStorageTypeList
	 *            List ���������в��Ͽ�������б�
	 * @param materialStorageNumberList
	 *            List ���������в���ԭ�п�������б�
	 * @return String �����������������Ϣ����
	 * @throws Exception
	 */
	public String saveMaterialList(String applyId, List materialIdList,
			List materialUseNumberList, List materialStorageAddrList,
			List materialStorageTypeList, List materialStorageNumberList,
			List materialUnitPriceList, List materialPriceList)
			throws Exception {
		logger(RemedyApplyMaterialDao.class);
		// �������ʹ�ÿ�棨��Ӳ���ʹ����Ϣ��
		String[] seqIds = new String[0];
		int addressId;
		int materialId;
		Float materialUseNumber;
		Float materialStorageNumber;
		Float unitprice;
		Float totalprice;
		RemedyApplyMaterial applyMaterial;
		try {
			if (materialIdList != null && materialIdList.size() > 0) {
				seqIds = ora.getSeqs("LINEPATROL_REMEDY_MATERIAL",
						"REMEDY_MATERIAL", 20, materialIdList.size());
			}
			for (int i = 0; materialIdList != null && i < materialIdList.size(); i++) {
				applyMaterial = new RemedyApplyMaterial();
				addressId = Integer.valueOf(
						(String) materialStorageAddrList.get(i)).intValue();
				materialId = Integer.valueOf((String) materialIdList.get(i))
						.intValue();
				materialUseNumber = Float
						.valueOf((String) materialUseNumberList.get(i));
				materialStorageNumber = Float
						.valueOf((String) materialStorageNumberList.get(i));

				unitprice = Float
						.valueOf((String) materialUnitPriceList.get(i));
				totalprice = Float.valueOf((String) materialPriceList.get(i));
				applyMaterial.setId(seqIds[i]);
				applyMaterial.setRemedyId(applyId);
				applyMaterial.setAddressId(addressId);
				applyMaterial
						.setMaterialStorageType((String) materialStorageTypeList
								.get(i));
				applyMaterial.setMaterialId(materialId);
				applyMaterial.setMaterialUseNumber(materialUseNumber);
				applyMaterial.setMaterialStorageNumber(materialStorageNumber);

				// add by cqp 2009-11-27
				applyMaterial.setMaterialUnitPrice(unitprice);
				applyMaterial.setMaterialPrice(totalprice);

				Object obj = super.insertOneObject(applyMaterial);
				if (obj == null) {
					return ExecuteCode.FAIL_CODE;
				}
			}
			return ExecuteCode.SUCCESS_CODE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw e;
		}
	}

	/**
	 * ִ�и�������������ɾ���������������Ϣ
	 * 
	 * @param applyId
	 *            String ����������
	 * @return String ɾ���������������Ϣ����
	 * @throws Exception
	 */
	public String deleteMaterialList(String applyId) throws Exception {
		logger(RemedyApplyMaterialDao.class);
		// �������ʹ�ÿ�棨ɾ������ʹ����Ϣ��
		String condition = " and remedyId='" + applyId + "' ";
		try {
			String operationCode;
			List list = findAll("RemedyApplyMaterial", condition);
			// RemedyApplyMaterial applyMaterial;
			// for (int i = 0; list != null && i < list.size(); i++) {
			// applyMaterial = (RemedyApplyMaterial) list.get(i);
			// operationCode =
			// storageDao.writeMaterialNotUseNumber(applyMaterial);
			// if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
			// return ExecuteCode.FAIL_CODE;
			// }
			// }
			boolean flag = super.deleteAll("RemedyApplyMaterial", condition);
			if (flag) {
				return ExecuteCode.SUCCESS_CODE;
			}
			return ExecuteCode.FAIL_CODE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw e;
		}
	}

	/**
	 * ִ�и������������ű��������������ʹ�ÿ����Ϣ
	 * 
	 * @param applyId
	 *            String ����������
	 * @param contractorId
	 *            String ��ά��λ���
	 * @return String �����������������Ϣ����
	 * @throws Exception
	 */
	public String saveMaterialUseStorage(String applyId, String contractorId)
			throws Exception {
		logger(RemedyApplyMaterialDao.class);
		String condition = " and remedyId='" + applyId + "' ";
		try {
			String operationCode;
			List list = findAll("RemedyApplyMaterial", condition);
			RemedyApplyMaterial applyMaterial;
			for (int i = 0; list != null && i < list.size(); i++) {
				applyMaterial = (RemedyApplyMaterial) list.get(i);
				operationCode = storageDao.writeMaterialUseNumber(
						applyMaterial, contractorId);
				if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
					return ExecuteCode.FAIL_CODE;
				}
				if (ExecuteCode.NOT_ENOUGH_STORAGE_ERROR.equals(operationCode)) {
					return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
				}
			}
			return ExecuteCode.SUCCESS_CODE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw e;
		}
	}

	/**
	 * ִ�и�������������ɾ�������������ʹ�ÿ����Ϣ
	 * 
	 * @param applyId
	 *            String ����������
	 * @param contractorId
	 *            String ��ά��λ���
	 * @return String ɾ���������������Ϣ����
	 * @throws Exception
	 */
	public String deleteMaterialUseStorage(String applyId, String contractorId)
			throws Exception {
		logger(RemedyApplyMaterialDao.class);
		String condition = " and remedyId='" + applyId + "' ";
		try {
			String operationCode;
			List list = findAll("RemedyApplyMaterial", condition);
			RemedyApplyMaterial applyMaterial;
			for (int i = 0; list != null && i < list.size(); i++) {
				applyMaterial = (RemedyApplyMaterial) list.get(i);
				operationCode = storageDao.writeMaterialNotUseNumber(
						applyMaterial, contractorId);
				if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
					return ExecuteCode.FAIL_CODE;
				}
			}
			return ExecuteCode.SUCCESS_CODE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw e;
		}
	}

	/**
	 * ִ�б������ɲ���������Ϣ
	 * 
	 * @param bean
	 *            RemedyMaterialBean ���ɲ��ϵ�����Ϣ
	 * @return Strimg �������ɲ���������Ϣ ����
	 * @throws Exception
	 */
	public String saveOneApplyMaterial(RemedyMaterialBean bean)
			throws Exception {
		float storageNumber = 0;
		float oldNumber = Float.parseFloat(bean.getAdjustOldNum());
		float newNumber = Float.parseFloat(bean.getAdjustNewNum());
		RemedyApplyMaterial oneMaterial = (RemedyApplyMaterial) super
				.viewOneObject(RemedyApplyMaterial.class, bean.getId());
		storageNumber = oneMaterial.getMaterialStorageNumber().floatValue();
		storageNumber = storageNumber + oldNumber - newNumber;
		oneMaterial.setMaterialUseNumber(new Float(bean.getAdjustNewNum()));
		oneMaterial.setMaterialStorageNumber(new Float(Float
				.toString(storageNumber)));
		Object object = super.saveObject(oneMaterial);
		if (object == null) {
			return ExecuteCode.FAIL_CODE;
		}
		return ExecuteCode.SUCCESS_CODE;
	}
}
