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
     * д����־��Ϣ
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
     * ִ�и������������Ż�ȡ��ǰ��������Ĳ�����
     * 
     * @param applyId
     *            String ����������
     * @return String ��ǰ��������Ĳ�����
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
     * ִ�и������������Ų�ѯ���������ǰһ������ִ��ǰ��״̬��Ϣ
     * 
     * @param applyId
     *            String ����������
     * @param stepId
     *            String �������뵱ǰ������
     * @return DynaBean ���������ǰһ������ִ��ǰ��״̬��Ϣ
     * @throws Exception
     */
    public DynaBean getPrevState(String applyId, String stepId) throws Exception {
        // �����޸�Update
        // ȡ����������˲�ͨ��ʱ��ǰһ������״̬
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
     * ִ������������ύ����
     * 
     * @param apply
     *            RemedyApply ����������
     * @param currentUserId
     *            String ��ǰ�����û����
     * @return String �ύ��������
     * @throws Exception
     */
    public String submitApply(RemedyApply apply, String currentUserId) throws Exception {
        apply.setState(RemedyWorkflowConstant.SUBMITED_STATE);
        String operationCode = doWorkflowAction(apply, currentUserId);
        return operationCode;
    }

    /**
     * ִ����������ı��浫���ύ����
     * 
     * @param apply
     *            RemedyApply ����������
     * @param currentUserId
     *            String ��ǰ�����û����
     * @return String �ύ��������
     * @throws Exception
     */
    public String saveApply(RemedyApply apply, String currentUserId) throws Exception {
        apply.setState(RemedyWorkflowConstant.NOT_SUBMITED_STATE);
        String operationCode = doWorkflowAction(apply, currentUserId);
        return operationCode;
    }

    /**
     * ִ�и�����������Ĺ���������Ϣ������״̬����һ���µĹ���������
     * 
     * @param apply
     *            RemedyApply ����������Ϣ
     * @param currentUserId
     *            String ��ǰ�����û�
     * @return String ����һ���µĹ������������
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
     * ִ�и�������������Ϣ����һ������������
     * 
     * @param apply
     *            RemedyApply ����������Ϣ
     * @param currentUserId
     *            String ��ǰ�����û�
     * @param prevStepId
     *            String ǰһ���������빤����������
     * @return String ����һ��������������
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
     * ִ�и������������״̬���ܷ����ж��Ƿ���Ҫ�ύ�ϼ�����
     * 
     * @param state
     *            String ���������״̬
     * @param totalFee
     *            Float ����������ܷ���
     * @return �Ƿ���Ҫ�ύ�ϼ�����
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
     * ִ�и������������źͽ���ȡ�������������״̬
     * 
     * @param applyId
     *            String ����������
     * @param totalFee
     *            Float ����������
     * @return String �������������״̬
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
     * ִ�и������������źͽ���ȡ�������������״̬
     * 
     * @param applyId
     *            String ����������
     * @param isPassed
     *            boolean ����������������
     * @return String �������������״̬
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
