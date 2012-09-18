package com.cabletech.linepatrol.remedy.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linepatrol.remedy.beans.RemedyCheckBean;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyWorkflowConstant;
import com.cabletech.linepatrol.remedy.dao.MaterialDao;
import com.cabletech.linepatrol.remedy.dao.MaterialStorageDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyAttachDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyItemDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyMaterialDao;
import com.cabletech.linepatrol.remedy.dao.RemedyBalanceDao;
import com.cabletech.linepatrol.remedy.dao.RemedyBalanceItemDao;
import com.cabletech.linepatrol.remedy.dao.RemedyBalanceMaterialDao;
import com.cabletech.linepatrol.remedy.dao.RemedyCheckDao;
import com.cabletech.linepatrol.remedy.domain.RemedyApply;
import com.cabletech.linepatrol.remedy.domain.RemedyBalance;
import com.cabletech.linepatrol.remedy.domain.RemedyBalanceMaterial;
import com.cabletech.linepatrol.remedy.domain.RemedyCheck;
import com.cabletech.utils.StringUtil;

public class RemedyCheckManager extends RemedyBaseBO {
    private RemedyApplyDao applyDao;

    private RemedyBalanceDao balanceDao;

    private RemedyCheckDao checkDao;

    private RemedyBalanceItemDao applyItemDao;

    private RemedyBalanceMaterialDao applyMaterialDao;

    private MaterialDao materialDao;

    private MaterialStorageDao materialStorageDao;

    private List materialIdList;

    private List materialStorageTypeList;

    private List materialUseNumberList;

    private List materialStorageAddrList;

    private List materialStorageNumberList;

    public RemedyCheckManager() {
        applyDao = new RemedyApplyDao();
        checkDao = new RemedyCheckDao();
        balanceDao = new RemedyBalanceDao();
        applyItemDao = new RemedyBalanceItemDao();
        applyMaterialDao = new RemedyBalanceMaterialDao();
        materialDao = new MaterialDao();
        materialStorageDao = new MaterialStorageDao();
    }

    /**
     * ִ�и����û�������֯�����ѯ����
     * 
     * @param request
     *            HttpServletRequest �û���������
     * @param form
     *            ActionForm �û������
     * @return String ��ѯ����
     */
    public String compositeCondition(HttpServletRequest request, ActionForm form) {
        // TODO Auto-generated method stub
        logger(RemedyCheckManager.class);
        super.setConditionGenerate(new ConditionGenerate());
        String userQueryCondition = super.getConditionGenerate().getUserQueryCondition(request,
                form);
        String stateCondition = super.getConditionGenerate().getStateCondition(request, form);
        String userOperationCondition = super.getConditionGenerate().getUserOperationCondition(
                request, form);
        String orderCondition = super.getConditionGenerate().getOrderCondition();
        StringBuffer condition = new StringBuffer();
        condition.append(userQueryCondition);
        condition.append(stateCondition);
        condition.append(userOperationCondition);
        condition.append(orderCondition);
        return condition.toString();
    }

    /**
     * ִ����������������Ϣ
     * 
     * @param request
     *            HttpServletRequest �û���������
     * @param form
     *            ActionForm �û������
     * @return String ���ն�������
     * @throws Exception
     */
    public String checkApply(HttpServletRequest request, ActionForm form) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedyCheckManager.class);

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String currentUserId = userInfo.getUserID();

        String seqId = ora.getSeq("LINEPATROL_REMEDY_BALANCE", "REMEDY_BALANCE", 20);
        String applyId = request.getParameter("apply_id");
        RemedyCheckBean checkBean = (RemedyCheckBean) form;
        RemedyCheck check = new RemedyCheck();

        List itemIdList = StringUtil.convertStringArrayToList(checkBean.getItemId());
        List itemTypeIdList = StringUtil.convertStringArrayToList(checkBean.getItemTypeId());

        materialIdList = StringUtil.convertStringArrayToList(checkBean.getMaterial());
        materialStorageTypeList = StringUtil.convertStringArrayToList(checkBean
                .getMaterialStorageType());
        materialUseNumberList = StringUtil.convertStringArrayToList(checkBean
                .getMaterialUseNumber());
        materialStorageAddrList = StringUtil.convertStringArrayToList(checkBean
                .getMaterialStorageAddr());
        materialStorageNumberList = StringUtil.convertStringArrayToList(checkBean
                .getMaterialStorageNumber());

        try {
            BeanUtil.objectCopy(checkBean, check);
            if (!applyDao.judgeExistApply(applyId)) {
                return ExecuteCode.NO_DATA_ERROR;
            }
            if (!applyDao.judgeAllowChecked(applyId)) {
                return ExecuteCode.NOT_CHECKED_APPLY_ERROR;
            }

            if (!applyItemDao.judgeExistItem(itemIdList)) {
                return ExecuteCode.NOT_EXIST_ITEM_ERROR;
            }
            if (!applyItemDao.judgeExistItemType(itemTypeIdList)) {
                return ExecuteCode.NOT_EXIST_ITEM_ERROR;
            }
            if (!materialDao.judgeExistMaterial(materialIdList)) {
                return ExecuteCode.NOT_EXIST_MATERIAL_ERROR;
            }

            RemedyApply apply = (RemedyApply) applyDao.viewOneObject(RemedyApply.class, applyId);
            String operationCode;
            // �������ɶ�������Ϣ
            operationCode = applyItemDao.saveItemList(applyId, seqId, checkBean.packageInfo());
            if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
                applyDao.rollbackTransaction();
                return ExecuteCode.FAIL_CODE;
            }
            // ��������ʹ�ò�����Ϣ
            operationCode = applyMaterialDao.saveMaterialList(applyId, seqId, materialIdList,
                    materialUseNumberList, materialStorageAddrList, materialStorageTypeList,
                    materialStorageNumberList);
            if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
                applyDao.rollbackTransaction();
                return ExecuteCode.FAIL_CODE;
            }
            String contractorId = apply.getContractorId();
            // ����������ʱʹ�õĲ��Ͽ����Ϣд��ز��Ͽ��
            logger.info("����������ʱʹ�õĲ��Ͽ����Ϣд��ز��Ͽ��");
            operationCode = applyMaterialDao.deleteMaterialUseStorage(applyId, contractorId);
            if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
                applyDao.rollbackTransaction();
                return ExecuteCode.FAIL_CODE;
            }
            // �Ӳ��Ͽ���е������ɽ���ʱʹ�õĲ��Ͽ����Ϣ
            logger.info("�Ӳ��Ͽ���е��������ɽ���ʱʹ�õĲ��Ͽ����Ϣ");
            List balanceMaterialList = getBalanceMaterialList();
            operationCode = applyMaterialDao.saveMaterialUseStorage(balanceMaterialList,
                    contractorId);
            if (ExecuteCode.NOT_ENOUGH_STORAGE_ERROR.equals(operationCode)) {
                applyDao.rollbackTransaction();
                return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
            }
            if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
                applyDao.rollbackTransaction();
                return ExecuteCode.FAIL_CODE;
            }

            apply.setPrevState(apply.getState());
            String applyState = RemedyWorkflowConstant.WAIT_SQUARED_STATE;
            String nextProcessMan = check.getNextProcessMan();
            apply.setState(applyState);
            apply.setNextProcessMan(nextProcessMan);
            String stepId = workflowBo.doWorkflowAction(apply, currentUserId);
            if (ExecuteCode.FAIL_CODE.equals(stepId)) {
                applyDao.rollbackTransaction();
                return ExecuteCode.FAIL_CODE;
            }
            apply.setCurrentStepId(stepId);

            check.setCheckDate(new Date());
            check.setCheckMan(currentUserId);
            operationCode = checkDao.saveCheck(applyId, check);
            if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
                applyDao.rollbackTransaction();
                return ExecuteCode.FAIL_CODE;
            }

            RemedyBalance balance = new RemedyBalance();
            balance.setCreator(userInfo.getUserID());
            balance.setTotalFee(new Float(checkBean.getTotalFee()));
            balance.setRemedyId(applyId);
            balance.setId(seqId);
            operationCode = balanceDao.saveOneBalance(balance, ExecuteCode.INSERT_ACTION_TYPE);
            if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
                applyDao.rollbackTransaction();
                return ExecuteCode.FAIL_CODE;
            }

            operationCode = applyDao.saveOneApply(apply, ExecuteCode.UPDATE_ACTION_TYPE);
            if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
                applyDao.commitTransaction();
            } else {
                applyDao.rollbackTransaction();
            }

            // ���ö��ŷ��͵�������Ŀ���ƺͷ���Ŀ���˱��
            super.setApplyProjectName(apply.getProjectName());
            super.setNextProcessManId(apply.getNextProcessMan());
            
            return operationCode;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            applyDao.rollbackTransaction();
            logger.error(e);
            throw e;
        }
    }

    /**
     * ִ�и��������������ж��Ƿ����ִ��������������ղ���
     * 
     * @param applyId
     *            String ����������
     * @return boolean �жϽ��
     */
    public boolean judge(String applyId) {
        // TODO Auto-generated method stub
        logger(RemedyCheckManager.class);
        super.setRemedyBaseDao(new RemedyApplyDao());
        try {
            return super.getRemedyBaseDao().judgeAllowChecked(applyId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            return false;
        }
    }

    /**
     * ִ�л�ȡ��һ���������б�
     * 
     * @param applyState
     *            String ���������״̬
     * @return List ��һ���������б�
     */
    public List getNextProcessManList(String applyState) {
        // TODO Auto-generated method stub
        try {
            super.setRemedyBaseDao(new RemedyCheckDao());
            List list = super.getRemedyBaseDao().getMobileUser("");
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return null;
    }

    /**
     * ��ȡ���ɽ���ʱ�Ĳ���ʹ���б���Ϣ
     * 
     * @return List ���ɽ���ʱ�Ĳ���ʹ���б���Ϣ
     */
    public List getBalanceMaterialList() {
        RemedyBalanceMaterial applyMaterial;
        int addressId;
        int materialId;
        Float materialUseNumber;
        Float materialStorageNumber;
        List list = new ArrayList();
        for (int i = 0; materialIdList != null && i < materialIdList.size(); i++) {
            applyMaterial = new RemedyBalanceMaterial();
            addressId = Integer.valueOf((String) materialStorageAddrList.get(i)).intValue();
            materialId = Integer.valueOf((String) materialIdList.get(i)).intValue();
            materialUseNumber = Float.valueOf((String) materialUseNumberList.get(i));
            materialStorageNumber = Float.valueOf((String) materialStorageNumberList.get(i));
            applyMaterial.setId("");
            applyMaterial.setAddressId(addressId);
            applyMaterial.setMaterialStorageType((String) materialStorageTypeList.get(i));
            applyMaterial.setMaterialId(materialId);
            applyMaterial.setMaterialUseNumber(materialUseNumber);
            applyMaterial.setMaterialStorageNumber(materialStorageNumber);
            list.add(applyMaterial);
        }
        return list;
    }
}
