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
	 * 执行根据查询条件获取修缮申请材料信息列表
	 * 
	 * @param condition
	 *            String 查询条件
	 * @return List 修缮申请材料信息列表
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
				+ " decode(t.storage_type,'0','利旧材料','1','新增材料','') as storage_type,t.storage_type as storagetype, ";
		sql = sql
				+ " mt.id as typeid,mm.id as modelid,c.contractorname,ma.address, ";
		sql = sql
				+ " mb.name||'（'||mt.typename||'）（'||mm.modelname||'）' as material_name, ";
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
	 * 执行根据修缮申请编号保存修缮申请材料信息
	 * 
	 * @param applyId
	 *            String 修缮申请编号
	 * @param materialIdList
	 *            List 修缮申请中材料编号列表
	 * @param materialUseNumberList
	 *            List 修缮申请中材料使用数量列表
	 * @param materialStorageAddrList
	 *            List 修缮申请中材料库存地点列表
	 * @param materialStorageTypeList
	 *            List 修缮申请中材料库存类型列表
	 * @param materialStorageNumberList
	 *            List 修缮申请中材料原有库存数量列表
	 * @return String 保存修缮申请材料信息编码
	 * @throws Exception
	 */
	public String saveMaterialList(String applyId, List materialIdList,
			List materialUseNumberList, List materialStorageAddrList,
			List materialStorageTypeList, List materialStorageNumberList,
			List materialUnitPriceList, List materialPriceList)
			throws Exception {
		logger(RemedyApplyMaterialDao.class);
		// 处理材料使用库存（添加材料使用信息）
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
	 * 执行根据修缮申请编号删除修缮申请材料信息
	 * 
	 * @param applyId
	 *            String 修缮申请编号
	 * @return String 删除修缮申请材料信息编码
	 * @throws Exception
	 */
	public String deleteMaterialList(String applyId) throws Exception {
		logger(RemedyApplyMaterialDao.class);
		// 处理材料使用库存（删除材料使用信息）
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
	 * 执行根据修缮申请编号保存修缮申请材料使用库存信息
	 * 
	 * @param applyId
	 *            String 修缮申请编号
	 * @param contractorId
	 *            String 代维单位编号
	 * @return String 保存修缮申请材料信息编码
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
	 * 执行根据修缮申请编号删除修缮申请材料使用库存信息
	 * 
	 * @param applyId
	 *            String 修缮申请编号
	 * @param contractorId
	 *            String 代维单位编号
	 * @return String 删除修缮申请材料信息编码
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
	 * 执行保存修缮材料申请信息
	 * 
	 * @param bean
	 *            RemedyMaterialBean 修缮材料调整信息
	 * @return Strimg 保存修缮材料申请信息 编码
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
