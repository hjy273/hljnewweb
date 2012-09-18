package com.cabletech.linepatrol.remedy.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.linepatrol.remedy.beans.RemedyMaterialBean;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyConstant;
import com.cabletech.linepatrol.remedy.dao.MaterialDao;
import com.cabletech.linepatrol.remedy.dao.MaterialStorageDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyMaterialDao;
import com.cabletech.linepatrol.remedy.dao.RemedyBaseDao;
import com.cabletech.linepatrol.remedy.dao.RemedyMaterialUpdateHistoryDao;
import com.cabletech.linepatrol.remedy.domain.RemedyApply;
import com.cabletech.linepatrol.remedy.domain.RemedyApplyMaterial;

public class RemedyMaterialManager extends BaseBisinessObject {
    protected static Logger logger = Logger.getLogger(RemedyMaterialManager.class.getName());

    protected OracleIDImpl ora = new OracleIDImpl();

    private RemedyBaseDao remedyBaseDao;

    public RemedyBaseDao getRemedyBaseDao() {
        return remedyBaseDao;
    }

    public void setRemedyBaseDao(RemedyBaseDao remedyBaseDao) {
        this.remedyBaseDao = remedyBaseDao;
    }

    /**
     * 写入日志信息
     * 
     * @param clazz
     *            Class
     */
    public void logger(Class clazz) {
        logger.info("Enter bo class " + clazz.getName() + "...............");
    }

    /**
     * 执行根据用户输入组织查询条件
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return String 查询条件
     */
    public String compositeCondition(HttpServletRequest request, ActionForm form) {
        StringBuffer buf = new StringBuffer();
        MaterialConditionGenerate conditionGenerate = new MaterialConditionGenerate();
        buf.append(conditionGenerate.getInputCondition(request, form));
        buf.append(conditionGenerate.getUserQueryCondition(request, form));
        buf.append(conditionGenerate.getAchieveCondition());
        buf.append(conditionGenerate.getOrderCondition());
        return buf.toString();
    }

    /**
     * 执行根据条件查询修缮材料列表
     * 
     * @param condition
     *            String 查询条件
     * @return List 修缮材料结果列表
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        logger(RemedyMaterialManager.class);
        setRemedyBaseDao(new RemedyApplyMaterialDao());
        List list = remedyBaseDao.queryList(condition);
        // list = remedyBaseDao.processList(list);
        return list;
    }

    /**
     * 执行根据修缮材料编号查询修缮申请信息
     * 
     * @param applyMaterialId
     *            String 修缮材料编号
     * @return RemedyMaterialBean 修缮材料信息
     * @throws Exception
     */
    public RemedyMaterialBean view(String applyMaterialId) throws Exception {
        try {
            setRemedyBaseDao(new RemedyApplyMaterialDao());
            RemedyApplyMaterial oneMaterial = (RemedyApplyMaterial) remedyBaseDao.viewOneObject(
                    RemedyApplyMaterial.class, applyMaterialId);
            RemedyMaterialBean oneMaterialBean = new RemedyMaterialBean();
            BeanUtil.objectCopy(oneMaterial, oneMaterialBean);

            setRemedyBaseDao(new RemedyApplyDao());
            RemedyApply oneApply = (RemedyApply) remedyBaseDao.viewOneObject(RemedyApply.class,
                    oneMaterial.getRemedyId());
            oneMaterialBean.setRemedyCode(oneApply.getRemedyCode());
            oneMaterialBean.setRemedyProjectName(oneApply.getProjectName());

            String materialName = remedyBaseDao.getMaterialName(oneMaterial.getMaterialId() + "");
            oneMaterialBean.setMaterialName(materialName);

            String addressName = remedyBaseDao.getAddressName(oneMaterial.getAddressId() + "");
            oneMaterialBean.setMaterialStorageAddress(addressName);
            oneMaterialBean.setMaterialStorageAddressId(oneMaterial.getAddressId() + "");
            oneMaterialBean.setAdjustOldNum(oneMaterial.getMaterialUseNumber().toString());

            setRemedyBaseDao(new MaterialStorageDao());
            String condition = " and MATERIALID=" + oneMaterial.getMaterialId() + " ";
            condition = condition + " and ADDRESSID=" + oneMaterial.getAddressId() + " ";
            List list = remedyBaseDao.queryList(condition);
            if (list != null && list.size() > 0) {
                DynaBean bean = (DynaBean) list.get(0);
                String materialStorageNumber = "0.0";
                if (RemedyConstant.OLD_STORAGE_MATERIAL
                        .equals(oneMaterial.getMaterialStorageType())) {
                    materialStorageNumber = ((BigDecimal) bean.get("oldstock")).toString();
                }
                if (RemedyConstant.NEW_STORAGE_MATERIAL
                        .equals(oneMaterial.getMaterialStorageType())) {
                    materialStorageNumber = ((BigDecimal) bean.get("newstock")).toString();
                }
                oneMaterialBean.setMaterialStorageNumber(materialStorageNumber);
            }
            if (oneMaterialBean == null) {
                return null;
            }
            return oneMaterialBean;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * 执行调整修缮材料信息
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return String 执行操作信息编码
     * @throws Exception
     */
    public String adjustMaterial(HttpServletRequest request, ActionForm form) throws Exception {
        // TODO Auto-generated method stub
        RemedyMaterialBean bean = (RemedyMaterialBean) form;
        List materialList = new ArrayList();
        List materialAddrList = new ArrayList();
        List storageTypeList = new ArrayList();
        List materialUseNumberList = new ArrayList();
        materialList.add(bean.getMaterialId());
        materialAddrList.add(bean.getMaterialStorageAddressId());
        storageTypeList.add(bean.getMaterialStorageType());
        float oldNumber = Float.parseFloat(bean.getAdjustOldNum());
        float newNumber = Float.parseFloat(bean.getAdjustNewNum());
        materialUseNumberList.add(Float.toString(newNumber - oldNumber));
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

        RemedyApplyMaterialDao applyMaterialDao = new RemedyApplyMaterialDao();
        try {
            MaterialDao materialDao = new MaterialDao();
            if (!materialDao.judgeExistMaterial(materialList)) {
                return ExecuteCode.NOT_EXIST_MATERIAL_ERROR;
            }

            MaterialStorageDao storageDao = new MaterialStorageDao();
            if (oldNumber < newNumber) {
                if (!storageDao.judgeEnoughStorage(materialList, materialUseNumberList,
                        materialAddrList, storageTypeList)) {
                    return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
                }
            }

            String operationCode = storageDao.writeMaterialStroage(bean);
            if (ExecuteCode.NOT_ENOUGH_STORAGE_ERROR.equals(operationCode)) {
                storageDao.rollbackTransaction();
                return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
            }
            if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
                storageDao.rollbackTransaction();
                return ExecuteCode.FAIL_CODE;
            }

            operationCode = applyMaterialDao.saveOneApplyMaterial(bean);
            if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
                applyMaterialDao.rollbackTransaction();
                return ExecuteCode.FAIL_CODE;
            }

            RemedyMaterialUpdateHistoryDao updateHistoryDao = new RemedyMaterialUpdateHistoryDao();
            operationCode = updateHistoryDao.saveOneHistory(bean, userInfo.getUserID());
            if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
                updateHistoryDao.rollbackTransaction();
                return ExecuteCode.FAIL_CODE;
            }

            applyMaterialDao.commitTransaction();
            return ExecuteCode.SUCCESS_CODE;
        } catch (Exception e) {
            applyMaterialDao.rollbackTransaction();
            logger.error(e);
            throw e;
        }
    }

}
