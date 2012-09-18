package com.cabletech.linepatrol.remedy.service;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyWorkflowConstant;
import com.cabletech.linepatrol.remedy.dao.RemedyBaseDao;
import com.cabletech.linepatrol.remedy.dao.RemedyWorkflowDao;
import com.cabletech.linepatrol.remedy.dao.RemedyWorkflowInfoDao;
import com.cabletech.linepatrol.remedy.domain.RemedyApply;
import com.cabletech.linepatrol.remedy.domain.RemedyWorkflowInfo;

public class RemedyWorkflowBO {
    private static Logger logger = Logger.getLogger(RemedyBaseBO.class.getName());

    private RemedyBaseDao remedyBaseDao;

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
     * @return the remedyBaseDao
     */
    public RemedyBaseDao getRemedyBaseDao() {
        return remedyBaseDao;
    }

    /**
     * @param remedyBaseDao
     *            the remedyBaseDao to set
     */
    public void setRemedyBaseDao(RemedyBaseDao remedyBaseDao) {
        this.remedyBaseDao = remedyBaseDao;
    }

    /**
     * 执行根据修缮申请编号获取当前修缮申请的步骤编号
     * 
     * @param applyId
     *            String 修缮申请编号
     * @return String 当前修缮申请的步骤编号
     * @throws Exception
     */
    public String getCurrentStep(String applyId) throws Exception {
        String condition = " and remedyid='" + applyId + "' order by id desc";
        setRemedyBaseDao(new RemedyWorkflowInfoDao());
        List list = remedyBaseDao.queryList(condition);
        if (list != null && list.size() > 0) {
            DynaBean bean = (DynaBean) list.get(0);
            String stepId = (String) bean.get("step_id");
            return stepId;
        }
        return "";
    }

    /**
     * 执行根据修缮申请编号查询修缮申请的前一个步骤执行前的状态信息
     * 
     * @param applyId
     *            String 修缮申请编号
     * @param stepId
     *            String 修缮申请当前步骤编号
     * @return DynaBean 修缮申请的前一个步骤执行前的状态信息
     * @throws Exception
     */
    public DynaBean getPrevState(String applyId, String stepId) throws Exception {
        // 进行修改Update
        // 取修缮申请审核不通过时的前一步处理状态
        // select last_man where prev_step=:stepId and prev_step<step_id
        String condition = " and t.remedyid='" + applyId + "' ";
        condition = condition + " and t.prev_step_id='" + stepId
                + "' and t.prev_step_id<t.step_id ";
        condition = condition + " order by t.id desc ";
        setRemedyBaseDao(new RemedyWorkflowDao());
        List list = remedyBaseDao.queryList(condition);
        if (list != null && list.size() > 0) {
            DynaBean bean = (DynaBean) list.get(0);
            condition = " and t.remedyid='" + applyId + "' ";
            condition = condition + " and t.id='" + (String) bean.get("step_seq") + "' ";
            condition = condition + " order by t.id desc";
            setRemedyBaseDao(new RemedyWorkflowInfoDao());
            list = remedyBaseDao.queryList(condition);
            if (list != null && list.size() > 0) {
                return (DynaBean) list.get(0);
            }
        }
        return null;
    }

    /**
     * 执行修缮申请的提交动作
     * 
     * @param apply
     *            RemedyApply 修缮申请编号
     * @param currentUserId
     *            String 当前操作用户编号
     * @return String 提交动作编码
     * @throws Exception
     */
    public String submitApply(RemedyApply apply, String currentUserId) throws Exception {
        apply.setState(RemedyWorkflowConstant.SUBMITED_STATE);
        String operationCode = doWorkflowAction(apply, currentUserId);
        return operationCode;
    }

    /**
     * 执行修缮申请的保存但不提交动作
     * 
     * @param apply
     *            RemedyApply 修缮申请编号
     * @param currentUserId
     *            String 当前操作用户编号
     * @return String 提交动作编码
     * @throws Exception
     */
    public String saveApply(RemedyApply apply, String currentUserId) throws Exception {
        apply.setState(RemedyWorkflowConstant.NOT_SUBMITED_STATE);
        String operationCode = doWorkflowAction(apply, currentUserId);
        return operationCode;
    }

    /**
     * 执行根据修缮申请的工作流程信息和申请状态创建一个新的工作流步骤
     * 
     * @param apply
     *            RemedyApply 修缮申请信息
     * @param currentUserId
     *            String 当前处理用户
     * @return String 创建一个新的工作流步骤编码
     * @throws Exception
     */
    public String doWorkflowAction(RemedyApply apply, String currentUserId) throws Exception {
        setRemedyBaseDao(new RemedyWorkflowInfoDao());
        String condition = " and t.remedyid='" + apply.getId() + "' order by t.id desc";
        List list = remedyBaseDao.queryList(condition);

        if (list != null && list.size() > 0) {
            DynaBean bean = (DynaBean) list.get(0);
            String prevStepId = (String) bean.get("step_id");
            String prevStatus = (String) bean.get("state");

            if (prevStatus != null && !prevStatus.equals(apply.getState())) {
                String stepId = createWorkflow(apply, currentUserId, prevStepId);
                if (ExecuteCode.FAIL_CODE.equals(stepId)) {
                    remedyBaseDao.rollbackTransaction();
                    return ExecuteCode.FAIL_CODE;
                }
                remedyBaseDao.commitTransaction();
                return stepId;
            }
            return prevStepId;
        } else {
            String stepId = createWorkflow(apply, currentUserId,
                    RemedyWorkflowConstant.INIT_STEP_ID);
            if (ExecuteCode.FAIL_CODE.equals(stepId)) {
                remedyBaseDao.rollbackTransaction();
                return ExecuteCode.FAIL_CODE;
            }
            remedyBaseDao.commitTransaction();
            return stepId;
        }
        // remedyBaseDao.rollbackTransaction();
        // return ExecuteCode.NO_OP_CODE;
    }

    /**
     * 执行根据修缮申请信息创建一个工作流步骤
     * 
     * @param apply
     *            RemedyApply 修缮申请信息
     * @param currentUserId
     *            String 当前处理用户
     * @param prevStepId
     *            String 前一个修缮申请工作流步骤编号
     * @return String 创建一个工作流步骤编号
     * @throws Exception
     */
    public String createWorkflow(RemedyApply apply, String currentUserId, String prevStepId)
            throws Exception {
        // TODO Auto-generated method stub
        RemedyWorkflowInfoDao workflowInfoDao = new RemedyWorkflowInfoDao();
        RemedyWorkflowInfo workflowInfo = workflowInfoDao.saveWorkflowInfo(apply, currentUserId,
                prevStepId);
        if (workflowInfo == null) {
            return ExecuteCode.FAIL_CODE;
        }
        RemedyWorkflowDao workflowDao = new RemedyWorkflowDao();
        String operationCode = workflowDao.saveWorkflow(apply.getId() + "", prevStepId,
                workflowInfo.getStepId(), workflowInfo.getId());
        if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
            return ExecuteCode.FAIL_CODE;
        }
        return workflowInfo.getStepId();
    }

    /**
     * 执行根据修缮申请的状态和总费用判断是否需要提交上级审批
     * 
     * @param state
     *            String 修缮申请的状态
     * @param totalFee
     *            Float 修缮申请的总费用
     * @return 是否需要提交上级审批
     */
    public String isNeedToUpLevel(String currentState, Float totalFee) {
        // TODO Auto-generated method stub
        if (RemedyWorkflowConstant.SUBMITED_STATE.equals(currentState)) {
            return RemedyWorkflowConstant.YES_CONDITION;
        }
        if (RemedyWorkflowConstant.APPROVED_STATE_LEVEL_ONE.equals(currentState)) {
            return RemedyWorkflowConstant.YES_CONDITION;
        }
        double fee = totalFee.doubleValue() / 10000;
        if (RemedyWorkflowConstant.APPROVED_STATE_LEVEL_TWO.equals(currentState)) {
            if (fee <= 1) {
                return RemedyWorkflowConstant.NO_CONDITION;
            } else {
                return RemedyWorkflowConstant.YES_CONDITION;
            }
        }
        if (RemedyWorkflowConstant.APPROVED_STATE_LEVEL_THREE.equals(currentState)) {
            if (fee > 1 && fee <= 10) {
                return RemedyWorkflowConstant.NO_CONDITION;
            } else {
                return RemedyWorkflowConstant.YES_CONDITION;
            }
        }
        if (RemedyWorkflowConstant.APPROVED_STATE_LEVEL_FOUR.equals(currentState)) {
            return RemedyWorkflowConstant.NO_CONDITION;
        }
        return RemedyWorkflowConstant.NO_CONDITION;
    }

    /**
     * 执行根据修缮申请编号和金额获取修缮申请的审批状态
     * 
     * @param applyId
     *            String 修缮申请编号
     * @param totalFee
     *            Float 修缮申请金额
     * @return String 修缮申请的审批状态
     * @throws Exception
     */
    public String getApplyStateByMoney(String applyId, Float totalFee) throws Exception {
        logger(RemedyWorkflowBO.class);
        setRemedyBaseDao(new RemedyWorkflowInfoDao());
        String condition = " and t.remedyid='" + applyId + "' order by t.id desc";
        List list = remedyBaseDao.queryList(condition);
        if (list != null && list.size() > 0) {
            DynaBean bean = (DynaBean) list.get(0);
            String currentState = (String) bean.get("state");
            if (RemedyWorkflowConstant.SUBMITED_STATE.equals(currentState)) {
                return RemedyWorkflowConstant.APPROVED_STATE_LEVEL_ONE;
            }
            if (RemedyWorkflowConstant.APPROVED_STATE_LEVEL_ONE.equals(currentState)) {
                return RemedyWorkflowConstant.APPROVED_STATE_LEVEL_TWO;
            }
            double fee = totalFee.doubleValue() / 10000;
            if (RemedyWorkflowConstant.APPROVED_STATE_LEVEL_TWO.equals(currentState)) {
                if (fee <= 1) {
                    return RemedyWorkflowConstant.WAIT_CHECKED_STATE;
                } else {
                    return RemedyWorkflowConstant.APPROVED_STATE_LEVEL_THREE;
                }
            }
            if (RemedyWorkflowConstant.APPROVED_STATE_LEVEL_THREE.equals(currentState)) {
                if (fee > 1 && fee <= 10) {
                    return RemedyWorkflowConstant.WAIT_CHECKED_STATE;
                } else {
                    return RemedyWorkflowConstant.APPROVED_STATE_LEVEL_FOUR;
                }
            }
            if (RemedyWorkflowConstant.APPROVED_STATE_LEVEL_FOUR.equals(currentState)) {
                return RemedyWorkflowConstant.WAIT_CHECKED_STATE;
            }
        }
        return ExecuteCode.NO_OP_CODE;
    }

    /**
     * 执行根据修缮申请编号和金额获取修缮申请的审批状态
     * 
     * @param applyId
     *            String 修缮申请编号
     * @param isPassed
     *            boolean 修缮申请的审批结果
     * @return String 修缮申请的审批状态
     * @throws Exception
     */
    public String getApproveState(String applyId, boolean isPassed) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedyWorkflowBO.class);
        setRemedyBaseDao(new RemedyWorkflowInfoDao());
        String condition = " and t.remedyid='" + applyId + "' order by t.id desc";
        List list = remedyBaseDao.queryList(condition);
        if (list != null && list.size() > 0) {
            DynaBean bean = (DynaBean) list.get(0);
            String currentState = (String) bean.get("state");
            if (RemedyWorkflowConstant.SUBMITED_STATE.equals(currentState)) {
                if (isPassed) {
                    return RemedyWorkflowConstant.APPROVED_STATE_LEVEL_ONE;
                } else {
                    return RemedyWorkflowConstant.NOT_APPROVED_STATE_LEVEL_ONE;
                }
            }
            if (RemedyWorkflowConstant.APPROVED_STATE_LEVEL_ONE.equals(currentState)) {
                if (isPassed) {
                    return RemedyWorkflowConstant.APPROVED_STATE_LEVEL_TWO;
                } else {
                    return RemedyWorkflowConstant.NOT_APPROVED_STATE_LEVEL_TWO;
                }
            }
            if (RemedyWorkflowConstant.APPROVED_STATE_LEVEL_TWO.equals(currentState)) {
                if (isPassed) {
                    return RemedyWorkflowConstant.APPROVED_STATE_LEVEL_THREE;
                } else {
                    return RemedyWorkflowConstant.NOT_APPROVED_STATE_LEVEL_THREE;
                }
            }
            if (RemedyWorkflowConstant.APPROVED_STATE_LEVEL_THREE.equals(currentState)) {
                if (isPassed) {
                    return RemedyWorkflowConstant.APPROVED_STATE_LEVEL_FOUR;
                } else {
                    return RemedyWorkflowConstant.NOT_APPROVED_STATE_LEVEL_FOUR;
                }
            }
            if (RemedyWorkflowConstant.APPROVED_STATE_LEVEL_FOUR.equals(currentState)) {
                if (isPassed) {
                    return RemedyWorkflowConstant.APPROVED_STATE_LEVEL_FIVE;
                } else {
                    return RemedyWorkflowConstant.NOT_APPROVED_STATE_LEVEL_FIVE;
                }
            }
        }
        return ExecuteCode.NO_OP_CODE;
    }

}
