package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.beans.RemedyMaterialBean;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.domain.RemedyApplyMaterial;
import com.cabletech.linepatrol.remedy.domain.RemedyBalanceMaterial;

public class RemedyBalanceMaterialDao extends RemedyBaseDao {
    private MaterialStorageDao storageDao = new MaterialStorageDao();

    /**
     * ִ�и��ݲ�ѯ������ȡ���ɽ��������Ϣ�б�
     * 
     * @param condition
     *            String ��ѯ����
     * @return List ���ɽ��������Ϣ�б�
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedyBalanceMaterialDao.class);
        String sql = "select t.id,to_char(t.materialid) as materialid,to_char(t.addressid) as addressid, ";
        sql = sql + " to_char(t.materialcount) as use_number,";
        sql = sql + " to_char(t.storage_number) as storage_number,remedy.projectname, ";
        sql = sql
                + " decode(t.storage_type,'0','���ɲ���','1','��������','') as storage_type,t.storage_type as storagetype, ";
        sql = sql + " mt.id as typeid,mm.id as modelid,c.contractorname,ma.address, ";
        sql = sql + " mb.name||'��'||mt.typename||'����'||mm.modelname||'��' as material_name, ";
        sql = sql + " decode(t.storage_type,'0',ms.oldstock,'1',ms.newstock,'0') as stock_number, ";
        sql = sql
                + " decode(t.storage_type,'0',decode(sign(ms.oldstock-t.materialcount),-1,'-','+'),'1',decode(sign(ms.newstock-t.materialcount),-1,'-','+'),'') as is_enough_material ";
        sql = sql + " from LINEPATROL_REMEDY_BAL_MATERIAL t,LINEPATROL_MT_BASE mb, ";
        sql = sql + " LINEPATROL_MT_MODEL mm,LINEPATROL_MT_TYPE mt,LINEPATROL_MT_ADDR_STOCK ms, ";
        sql = sql + " LINEPATROL_MT_ADDR ma,LINEPATROL_REMEDY remedy,contractorinfo c ";
        sql = sql + " where t.materialid=mb.id and mb.modelid=mm.id and mm.typeid=mt.id ";
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
     * ִ�и������������ű������ɽ��������Ϣ
     * 
     * @param applyId
     *            String ���ɽ�����
     * @param materialIdList
     *            List ���ɽ����в��ϱ���б�
     * @param materialUseNumberList
     *            List ���ɽ����в���ʹ�������б�
     * @param materialStorageAddrList
     *            List ���ɽ����в��Ͽ��ص��б�
     * @param balanceId
     *            String ���ɽ��㵥���
     * @param materialStorageTypeList
     *            List ���ɽ����в��Ͽ�������б�
     * @param materialStorageNumberList
     *            List ���ɽ����в���ԭ�п�������б�
     * @return String �������ɽ��������Ϣ����
     * @throws Exception
     */
    public String saveMaterialList(String applyId, String balanceId, List materialIdList,
            List materialUseNumberList, List materialStorageAddrList, List materialStorageTypeList,
            List materialStorageNumberList) throws Exception {
        logger(RemedyBalanceMaterialDao.class);
        // �������ʹ�ÿ�棨��Ӳ���ʹ����Ϣ��
        String[] seqIds = new String[0];
        int addressId;
        int materialId;
        Float materialUseNumber;
        Float materialStorageNumber;
        RemedyBalanceMaterial applyMaterial;
        try {
            if (materialIdList != null && materialIdList.size() > 0) {
                seqIds = ora.getSeqs("LINEPATROL_REMEDY_BAL_MATERIAL", "REMEDY_BAL_MATERIAL", 20,
                        materialIdList.size());
            }
            for (int i = 0; materialIdList != null && i < materialIdList.size(); i++) {
                applyMaterial = new RemedyBalanceMaterial();
                addressId = Integer.valueOf((String) materialStorageAddrList.get(i)).intValue();
                materialId = Integer.valueOf((String) materialIdList.get(i)).intValue();
                materialUseNumber = Float.valueOf((String) materialUseNumberList.get(i));
                materialStorageNumber = Float.valueOf((String) materialStorageNumberList.get(i));
                applyMaterial.setId(seqIds[i]);
                applyMaterial.setRemedyId(applyId);
                applyMaterial.setAddressId(addressId);
                applyMaterial.setMaterialStorageType((String) materialStorageTypeList.get(i));
                applyMaterial.setMaterialId(materialId);
                applyMaterial.setMaterialUseNumber(materialUseNumber);
                applyMaterial.setMaterialStorageNumber(materialStorageNumber);
                applyMaterial.setBalanceId(balanceId);

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
     * ִ�и������ɽ���ʱ�Ĳ���ʹ���б���Ϣ�������ɽ������ʹ�ÿ����Ϣ
     * 
     * @param balanceMaterialList
     *            List ���ɽ���ʱ�Ĳ���ʹ���б���Ϣ
     * @param contractorId
     *            String ��ά��λ���
     * @return String �������ɽ��������Ϣ����
     * @throws Exception
     */
    public String saveMaterialUseStorage(List balanceMaterialList, String contractorId)
            throws Exception {
        logger(RemedyBalanceMaterialDao.class);
        try {
            String operationCode;
            RemedyBalanceMaterial applyMaterial;
            for (int i = 0; balanceMaterialList != null && i < balanceMaterialList.size(); i++) {
                applyMaterial = (RemedyBalanceMaterial) balanceMaterialList.get(i);
                operationCode = storageDao.writeMaterialUseNumber(applyMaterial, contractorId);
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
    public String deleteMaterialUseStorage(String applyId, String contractorId) throws Exception {
        logger(RemedyBalanceMaterialDao.class);
        String condition = " and remedyId='" + applyId + "' ";
        try {
            String operationCode;
            List list = findAll("RemedyApplyMaterial", condition);
            RemedyApplyMaterial applyMaterial;
            for (int i = 0; list != null && i < list.size(); i++) {
                applyMaterial = (RemedyApplyMaterial) list.get(i);
                operationCode = storageDao.writeMaterialNotUseNumber(applyMaterial, contractorId);
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
     * ִ�б������ɲ��Ͻ�����Ϣ
     * 
     * @param bean
     *            RemedyMaterialBean ���ɲ��ϵ�����Ϣ
     * @return String �������ɲ��Ͻ�����Ϣ ����
     * @throws Exception
     */
    public String saveOneApplyMaterial(RemedyMaterialBean bean) throws Exception {
        float storageNumber = 0;
        float oldNumber = Float.parseFloat(bean.getAdjustOldNum());
        float newNumber = Float.parseFloat(bean.getAdjustNewNum());
        RemedyBalanceMaterial oneMaterial = (RemedyBalanceMaterial) super.viewOneObject(
                RemedyBalanceMaterial.class, bean.getId());
        storageNumber = oneMaterial.getMaterialStorageNumber().floatValue();
        storageNumber = storageNumber + oldNumber - newNumber;
        oneMaterial.setMaterialUseNumber(new Float(bean.getAdjustNewNum()));
        oneMaterial.setMaterialStorageNumber(new Float(Float.toString(storageNumber)));
        Object object = super.saveObject(oneMaterial);
        if (object == null) {
            return ExecuteCode.FAIL_CODE;
        }
        return ExecuteCode.SUCCESS_CODE;
    }
}
