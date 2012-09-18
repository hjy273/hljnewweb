package com.cabletech.linepatrol.remedy.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linepatrol.remedy.beans.RemedyApproveBean;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyWorkflowConstant;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyMaterialDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApproveDao;
import com.cabletech.linepatrol.remedy.domain.RemedyApply;
import com.cabletech.linepatrol.remedy.domain.RemedyApprove;

public class RemedyApproveManager extends RemedyBaseBO {
    private RemedyApplyDao applyDao;

    private RemedyApplyMaterialDao applyMaterialDao;

    private RemedyApproveDao approveDao;

    public RemedyApproveManager() {
        applyDao = new RemedyApplyDao();
        applyMaterialDao = new RemedyApplyMaterialDao();
        approveDao = new RemedyApproveDao();
    }

    /**
     * 执行根据用户输入组织具体查询条件
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return String 查询条件
     */
    public String compositeCondition(HttpServletRequest request, ActionForm form) {
        // TODO Auto-generated method stub
        logger(RemedyApproveManager.class);
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
     * 执行审批修缮申请信息
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return String 审批动作编码
     * @throws Exception
     */
    public String approveApply(HttpServletRequest request, ActionForm form) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedyApproveManager.class);

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String currentUserId = userInfo.getUserID();

        String applyId = request.getParameter("apply_id");
        RemedyApproveBean approveBean = (RemedyApproveBean) form;
        RemedyApprove approve = new RemedyApprove();
        try {
            BeanUtil.objectCopy(approveBean, approve);
            if (!applyDao.judgeExistApply(applyId)) {
                return ExecuteCode.NO_DATA_ERROR;
            }
            if (!applyDao.judgeAllowApproved(applyId)) {
                return ExecuteCode.NOT_APPROVED_APPLY_ERROR;
            }

            RemedyApply apply = (RemedyApply) applyDao.viewOneObject(RemedyApply.class, applyId);
            apply.setPrevState(apply.getState());
            boolean isPassed = judgeApprovePassed(approve);
            String operationCode = ExecuteCode.NO_OP_CODE;
            if (isPassed) {
                logger.info("approve passed.................");
                apply.setPassed(true);
                String applyState = workflowBo.getApplyStateByMoney(applyId, apply.getTotalFee());
                String approveState = workflowBo.getApproveState(applyId, isPassed);
                String nextProcessMan = approve.getNextProcessMan();
                apply.setState(applyState);
                apply.setNextProcessMan(nextProcessMan);
                logger.info("next process man :" + nextProcessMan);
                logger.info("apply state :" + applyState);
                String stepId = workflowBo.doWorkflowAction(apply, currentUserId);
                logger.info("step :" + stepId);
                if (ExecuteCode.FAIL_CODE.equals(stepId)) {
                    applyDao.rollbackTransaction();
                    return ExecuteCode.FAIL_CODE;
                }
                if (!ExecuteCode.NO_OP_CODE.equals(stepId)) {
                    apply.setCurrentStepId(stepId);
                }
                approve.setStepId(approveState);
                // 修改修缮申请使用材料库存信息
                if (RemedyWorkflowConstant.WAIT_CHECKED_STATE.equals(applyState)) {
                    String contractorId = apply.getContractorId();
                    operationCode = applyMaterialDao.saveMaterialUseStorage(applyId, contractorId);
                    if (ExecuteCode.NOT_ENOUGH_STORAGE_ERROR.equals(operationCode)) {
                        applyDao.rollbackTransaction();
                        return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
                    }
                    if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
                        applyDao.rollbackTransaction();
                        return ExecuteCode.FAIL_CODE;
                    }
                }
            } else {
                // 修改修缮申请使用材料库存信息
                // String operationCode =
                // applyMaterialDao.deleteMaterialUseStorage(applyId);
                // if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
                // return ExecuteCode.FAIL_CODE;
                // }
                logger.info("approve not passed.................");
                apply.setPassed(false);
                String stepId = workflowBo.getCurrentStep(applyId);
                stepId = RemedyWorkflowOperate.nextAction(stepId, apply);
                DynaBean prevStateBean = workflowBo.getPrevState(applyId, stepId);
                String applyState = (String) prevStateBean.get("prev_state");
                String approveState = workflowBo.getApproveState(applyId, isPassed);
                ;
                String nextProcessMan = (String) prevStateBean.get("last_man");
                logger.info("next process man :" + nextProcessMan);
                logger.info("apply state :" + applyState);
                if (RemedyWorkflowConstant.NOT_SUBMITED_STATE.equals(applyState)) {
                    applyState = RemedyWorkflowConstant.NOT_APPROVED_STATE_LEVEL_ONE;
                }
                apply.setState(applyState);
                apply.setNextProcessMan(nextProcessMan);
                if (RemedyWorkflowConstant.NOT_APPROVED_STATE_LEVEL_ONE.equals(applyState)) {
                    apply.setNextProcessMan(apply.getFirstStepApproveMan());
                }
                stepId = workflowBo.doWorkflowAction(apply, currentUserId);
                logger.info("step :" + stepId);
                if (ExecuteCode.FAIL_CODE.equals(stepId)) {
                    applyDao.rollbackTransaction();
                    return ExecuteCode.FAIL_CODE;
                }
                if (!ExecuteCode.NO_OP_CODE.equals(stepId)) {
                    apply.setCurrentStepId(stepId);
                }
                approve.setStepId(approveState);
            }
            logger.info("save approve.................");
            approve.setApproveDate(new Date());
            approve.setApproveMan(currentUserId);
            operationCode = approveDao.saveApprove(applyId, approve);
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

            // 设置短信发送的修缮项目名称和发送目标人编号
            super.setApplyProjectName(apply.getProjectName());
            if (!RemedyWorkflowConstant.NOT_APPROVED_STATE_LEVEL_ONE.equals(apply.getState())) {
                super.setNextProcessManId(apply.getNextProcessMan());
            } else {
                super.setNextProcessManId(apply.getCreator());
            }

            return operationCode;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            applyDao.rollbackTransaction();
            logger.error(e);
            throw e;
        }
    }

    /**
     * 执行根据修缮审批信息获取审批是否通过状态
     * 
     * @param approve
     *            RemedyApprove
     * @return boolean 审批是否通过
     */
    public boolean judgeApprovePassed(RemedyApprove approve) {
        logger(RemedyApproveManager.class);
        if (RemedyWorkflowConstant.NOT_PASSED.equals(approve.getState())) {
            return false;
        }
        if (RemedyWorkflowConstant.PASSED.equals(approve.getState())) {
            return true;
        }
        return false;
    }

    /**
     * 执行根据修缮申请编号判断是否可以执行修缮申请的审批操作
     * 
     * @param applyId
     *            String 修缮申请编号
     * @return boolean 判断结果
     */
    public boolean judge(String applyId) {
        // TODO Auto-generated method stub
        logger(RemedyApproveManager.class);
        super.setRemedyBaseDao(new RemedyApplyDao());
        boolean flag = false;
        try {
            flag = super.getRemedyBaseDao().judgeAllowApproved(applyId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return flag;
    }

    /**
     * 执行获取下一步审批人列表
     * 
     * @param applyState
     *            String 修缮申请的状态
     * @return List 下一步审批人列表
     */
    public List getNextProcessManList(String applyState) {
        // TODO Auto-generated method stub
        try {
            super.setRemedyBaseDao(new RemedyApproveDao());
            List list = super.getRemedyBaseDao().getMobileUser("");
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return null;
    }

}
