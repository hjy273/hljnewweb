package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.beans.RemedyMaterialBean;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyConstant;
import com.cabletech.linepatrol.remedy.domain.MaterialAddressStorage;
import com.cabletech.linepatrol.remedy.domain.MaterialStorage;
import com.cabletech.linepatrol.remedy.domain.RemedyMaterialBase;

public class MaterialStorageDao extends RemedyBaseDao {
    /**
     * 执行判断修缮申请材料列表中材料是否存在足够库存
     * 
     * @param materialIdList
     *            List 修缮申请材料编号列表
     * @param materialUseNumberList
     *            List 修缮申请材料数量列表
     * @param materialAddrList
     *            List 修缮申请材料存放地点列表
     * @param storageTypeList
     *            List 修缮申请使用的材料类型列表
     * @return boolean 修缮申请材料列表中材料是否存在足够库存
     * @throws Exception
     */
    public boolean judgeEnoughStorage(List materialIdList, List materialUseNumberList,
            List materialAddrList, List storageTypeList) throws Exception {
        logger(MaterialStorageDao.class);
        String baseSql = "select id from LINEPATROL_MT_ADDR_STOCK where 1=1 ";
        String sql = "";
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list;
            for (int i = 0; materialIdList != null && i < materialIdList.size(); i++) {
                sql = baseSql + " and materialid='" + materialIdList.get(i) + "' ";
                sql = sql + " and addressid='" + materialAddrList.get(i) + "' ";
                if (RemedyConstant.OLD_STORAGE_MATERIAL.equals(storageTypeList.get(i))) {
                    sql = sql + " and oldstock>=" + materialUseNumberList.get(i) + " ";
                }
                if (RemedyConstant.NEW_STORAGE_MATERIAL.equals(storageTypeList.get(i))) {
                    sql = sql + " and newstock>=" + materialUseNumberList.get(i) + " ";
                }
                logger.info("Execute sql:" + sql);
                list = queryUtil.queryBeans(sql);
                if (list == null || list.isEmpty()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * 执行根据修缮申请中某一个材料信息删除材料使用库存信息
     * 
     * @param applyMaterial
     *            RemedyMaterialBase 修缮申请中某一个材料信息
     * @param contractorId
     *            String 代维单位编号
     * @return String 删除材料使用库存信息编码
     * @throws Exception
     */
    public String writeMaterialNotUseNumber(RemedyMaterialBase applyMaterial, String contractorId)
            throws Exception {
        logger(MaterialStorageDao.class);
        String addrCondition = " and materialId=" + applyMaterial.getMaterialId()
                + " and addressId=" + applyMaterial.getAddressId() + " ";
        String contractorCondition = " and materialId=" + applyMaterial.getMaterialId()
                + " and contractorid='" + contractorId + "' ";
        try {
            List list = super.findAll("MaterialAddressStorage", addrCondition);
            logger.info("material address storage size=" + list.size());
            if (list != null && list.size() > 0) {
                MaterialAddressStorage addrStorage = (MaterialAddressStorage) list.get(0);
                logger.info("material address old storage before change ="
                        + addrStorage.getOldStock());
                logger.info("material address new storage before change ="
                        + addrStorage.getNewStock());
                if (RemedyConstant.OLD_STORAGE_MATERIAL.equals(applyMaterial
                        .getMaterialStorageType())) {
                    float oldStorage = 0;
                    if (addrStorage.getOldStock() != null) {
                        oldStorage = addrStorage.getOldStock().floatValue();
                    }
                    float useNumber = applyMaterial.getMaterialUseNumber().floatValue();
                    logger.info("material address old storage after change ="
                            + (oldStorage + useNumber));
                    float changedOldStorage = oldStorage + useNumber;
                    addrStorage.setOldStock(Float.valueOf(Float.toString(changedOldStorage)));
                    logger.info("material address old storage after change ="
                            + addrStorage.getOldStock());
                    Object obj = super.updateOneObject(addrStorage);
                    if (obj == null) {
                        return ExecuteCode.FAIL_CODE;
                    }
                }
                if (RemedyConstant.NEW_STORAGE_MATERIAL.equals(applyMaterial
                        .getMaterialStorageType())) {
                    float newStorage = 0;
                    if (addrStorage.getNewStock() != null) {
                        newStorage = addrStorage.getNewStock().floatValue();
                    }
                    float useNumber = applyMaterial.getMaterialUseNumber().floatValue();
                    logger.info("material address new storage after change ="
                            + (newStorage + useNumber));
                    float changedNewStorage = newStorage + useNumber;
                    addrStorage.setNewStock(Float.valueOf(Float.toString(changedNewStorage)));
                    logger.info("material address new storage after change ="
                            + addrStorage.getNewStock());
                    Object obj = super.updateOneObject(addrStorage);
                    if (obj == null) {
                        return ExecuteCode.FAIL_CODE;
                    }
                }
            }
            list = super.findAll("MaterialStorage", contractorCondition);
            logger.info("material storage size=" + list.size());
            if (list != null && list.size() > 0) {
                MaterialStorage materialStorage = (MaterialStorage) list.get(0);
                logger.info("material old storage before change =" + materialStorage.getOldStock());
                logger.info("material new storage before change =" + materialStorage.getNewStock());
                if (RemedyConstant.OLD_STORAGE_MATERIAL.equals(applyMaterial
                        .getMaterialStorageType())) {
                    float oldStorage = 0;
                    if (materialStorage.getOldStock() != null) {
                        oldStorage = materialStorage.getOldStock().floatValue();
                    }
                    float useNumber = applyMaterial.getMaterialUseNumber().floatValue();
                    logger.info("material old storage after change =" + (oldStorage + useNumber));
                    float changedOldStorage = oldStorage + useNumber;
                    materialStorage.setOldStock(Float.valueOf(Float.toString(changedOldStorage)));
                    logger.info("material old storage after change ="
                            + materialStorage.getOldStock());
                    Object obj = super.updateOneObject(materialStorage);
                    if (obj != null) {
                        return ExecuteCode.SUCCESS_CODE;
                    }
                }
                if (RemedyConstant.NEW_STORAGE_MATERIAL.equals(applyMaterial
                        .getMaterialStorageType())) {
                    float newStorage = 0;
                    if (materialStorage.getNewStock() != null) {
                        newStorage = materialStorage.getNewStock().floatValue();
                    }
                    float useNumber = applyMaterial.getMaterialUseNumber().floatValue();
                    logger.info("material new storage after change =" + (newStorage + useNumber));
                    float changedNewStorage = newStorage + useNumber;
                    materialStorage.setNewStock(Float.valueOf(Float.toString(changedNewStorage)));
                    logger.info("material new storage after change ="
                            + materialStorage.getNewStock());
                    Object obj = super.updateOneObject(materialStorage);
                    if (obj != null) {
                        return ExecuteCode.SUCCESS_CODE;
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        return ExecuteCode.FAIL_CODE;
    }

    /**
     * 执行根据修缮申请中某一个材料信息添加材料使用库存信息
     * 
     * @param applyMaterial
     *            RemedyMaterialBase 修缮申请中某一个材料信息
     * @param contractorId
     *            String 代维单位编号
     * @return String 添加材料使用库存信息编码
     * @throws Exception
     */
    public String writeMaterialUseNumber(RemedyMaterialBase applyMaterial, String contractorId)
            throws Exception {
        logger(MaterialStorageDao.class);
        String addrCondition = " and materialId=" + applyMaterial.getMaterialId()
                + " and addressId=" + applyMaterial.getAddressId() + " ";
        String contractorCondition = " and materialId=" + applyMaterial.getMaterialId()
                + " and contractorid='" + contractorId + "' ";
        try {
            List list = super.findAll("MaterialAddressStorage", addrCondition);
            // logger.info("material address storage size=" + list.size());
            if (list != null && list.size() > 0) {
                MaterialAddressStorage addrStorage = (MaterialAddressStorage) list.get(0);
                logger.info("material address old storage before change ="
                        + addrStorage.getOldStock());
                logger.info("material address new storage before change ="
                        + addrStorage.getNewStock());
                if (RemedyConstant.OLD_STORAGE_MATERIAL.equals(applyMaterial
                        .getMaterialStorageType())) {
                    float oldStorage = 0;
                    if (addrStorage.getOldStock() != null) {
                        oldStorage = addrStorage.getOldStock().floatValue();
                    }
                    float useNumber = applyMaterial.getMaterialUseNumber().floatValue();
                    logger.info("material address old storage after change ="
                            + (oldStorage - useNumber));
                    if (oldStorage - useNumber > 0) {
                        float changedOldStorage = oldStorage - useNumber;
                        addrStorage.setOldStock(Float.valueOf(Float.toString(changedOldStorage)));
                        logger.info("material address old storage after change ="
                                + addrStorage.getOldStock());
                        Object obj = super.updateOneObject(addrStorage);
                        if (obj == null) {
                            return ExecuteCode.FAIL_CODE;
                        }
                    } else {
                        return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
                    }
                }
                if (RemedyConstant.NEW_STORAGE_MATERIAL.equals(applyMaterial
                        .getMaterialStorageType())) {
                    float newStorage = 0;
                    if (addrStorage.getNewStock() != null) {
                        newStorage = addrStorage.getNewStock().floatValue();
                    }
                    float useNumber = applyMaterial.getMaterialUseNumber().floatValue();
                    logger.info("material address new storage after change ="
                            + (newStorage - useNumber));
                    if (newStorage - useNumber > 0) {
                        float changedNewStorage = newStorage - useNumber;
                        addrStorage.setNewStock(Float.valueOf(Float.toString(changedNewStorage)));
                        logger.info("material address new storage after change ="
                                + addrStorage.getNewStock());
                        Object obj = super.updateOneObject(addrStorage);
                        if (obj == null) {
                            return ExecuteCode.FAIL_CODE;
                        }
                    } else {
                        return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
                    }
                }
            }
            list = super.findAll("MaterialStorage", contractorCondition);
            // logger.info("material storage size=" + list.size());
            if (list != null && list.size() > 0) {
                MaterialStorage materialStorage = (MaterialStorage) list.get(0);
                logger.info("material old storage before change =" + materialStorage.getOldStock());
                logger.info("material new storage before change =" + materialStorage.getNewStock());
                if (RemedyConstant.OLD_STORAGE_MATERIAL.equals(applyMaterial
                        .getMaterialStorageType())) {
                    float oldStorage = 0;
                    if (materialStorage.getOldStock() != null) {
                        oldStorage = materialStorage.getOldStock().floatValue();
                    }
                    float useNumber = applyMaterial.getMaterialUseNumber().floatValue();
                    logger.info("material old storage after change =" + (oldStorage - useNumber));
                    if (oldStorage - useNumber > 0) {
                        float changedOldStorage = oldStorage - useNumber;
                        materialStorage.setOldStock(Float
                                .valueOf(Float.toString(changedOldStorage)));
                        logger.info("material old storage after change ="
                                + materialStorage.getOldStock());
                        Object obj = super.updateOneObject(materialStorage);
                        if (obj != null) {
                            return ExecuteCode.SUCCESS_CODE;
                        }
                    } else {
                        return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
                    }
                }
                if (RemedyConstant.NEW_STORAGE_MATERIAL.equals(applyMaterial
                        .getMaterialStorageType())) {
                    float newStorage = 0;
                    if (materialStorage.getNewStock() != null) {
                        newStorage = materialStorage.getNewStock().floatValue();
                    }
                    float useNumber = applyMaterial.getMaterialUseNumber().floatValue();
                    logger.info("material new storage after change =" + (newStorage - useNumber));
                    if (newStorage - useNumber > 0) {
                        float changedNewStorage = newStorage - useNumber;
                        materialStorage.setNewStock(Float
                                .valueOf(Float.toString(changedNewStorage)));
                        logger.info("material new storage after change ="
                                + materialStorage.getNewStock());
                        Object obj = super.updateOneObject(materialStorage);
                        if (obj != null) {
                            return ExecuteCode.SUCCESS_CODE;
                        }
                    } else {
                        return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        return ExecuteCode.FAIL_CODE;
    }

    /**
     * 执行根据查询条件获取修缮申请材料库存信息列表
     * 
     * @param condition
     *            String 查询条件
     * @return List 修缮申请材料库存信息列表
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        logger(MaterialStorageDao.class);
        String sql = "select distinct t.id,t.materialid,t.addressid,a.address, ";
        sql = sql + " decode(t.oldstock,null,0,t.oldstock) as oldstock, ";
        sql = sql + " decode(t.newstock,null,0,t.newstock) as newstock ";
        sql = sql + " from LINEPATROL_MT_ADDR_STOCK t,LINEPATROL_MT_ADDR a, ";
        sql = sql + " contractorinfo c,region r ";
        sql = sql + " where t.addressid=a.id and a.contractorid=c.contractorid ";
        sql = sql + " and c.regionid=r.regionid ";
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
     * 执行修缮材料库存调整操作
     * 
     * @param bean
     *            RemedyMaterialBean 修缮材料调整信息
     * @return String 修缮材料库存调整操作编码
     * @throws Exception
     */
    public String writeMaterialStroage(RemedyMaterialBean bean) throws Exception {
        float oldNumber = Float.parseFloat(bean.getAdjustOldNum());
        float newNumber = Float.parseFloat(bean.getAdjustNewNum());
        String condition = " and materialid=" + bean.getMaterialId() + " ";
        condition = condition + " and addressid=" + bean.getMaterialStorageAddressId() + " ";
        List storageList = super.findAll("MaterialAddressStorage", condition);
        if (storageList != null && storageList.size() > 0) {
            MaterialAddressStorage addrStorage = (MaterialAddressStorage) storageList.get(0);
            logger.info("material address old storage before change =" + addrStorage.getOldStock());
            logger.info("material address new storage before change =" + addrStorage.getNewStock());
            if (RemedyConstant.OLD_STORAGE_MATERIAL.equals(bean.getMaterialStorageType())) {
                float oldStorage = 0;
                if (addrStorage.getOldStock() != null) {
                    oldStorage = addrStorage.getOldStock().floatValue();
                }
                oldStorage = oldStorage + oldNumber - newNumber;
                if (oldStorage >= 0) {
                    addrStorage.setOldStock(Float.valueOf(Float.toString(oldStorage)));
                    logger.info("material address old storage after change ="
                            + addrStorage.getOldStock());
                    Object obj = super.updateOneObject(addrStorage);
                    if (obj != null) {
                        return ExecuteCode.SUCCESS_CODE;
                    } else {
                        return ExecuteCode.FAIL_CODE;
                    }
                } else {
                    return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
                }
            }
            if (RemedyConstant.NEW_STORAGE_MATERIAL.equals(bean.getMaterialStorageType())) {
                float newStorage = 0;
                if (addrStorage.getNewStock() != null) {
                    newStorage = addrStorage.getNewStock().floatValue();
                }
                newStorage = newStorage + oldNumber - newNumber;
                if (newStorage >= 0) {
                    addrStorage.setNewStock(Float.valueOf(Float.toString(newStorage)));
                    logger.info("material address old storage after change ="
                            + addrStorage.getNewStock());
                    Object obj = super.updateOneObject(addrStorage);
                    if (obj != null) {
                        return ExecuteCode.SUCCESS_CODE;
                    } else {
                        return ExecuteCode.FAIL_CODE;
                    }
                } else {
                    return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
                }
            }
        }
        return ExecuteCode.FAIL_CODE;
    }
}
