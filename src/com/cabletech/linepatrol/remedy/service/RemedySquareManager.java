package com.cabletech.linepatrol.remedy.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linepatrol.remedy.beans.RemedySquareBean;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyPowerConstant;
import com.cabletech.linepatrol.remedy.constant.RemedyWorkflowConstant;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyDao;
import com.cabletech.linepatrol.remedy.dao.RemedySquareDao;
import com.cabletech.linepatrol.remedy.domain.RemedyApply;
import com.cabletech.linepatrol.remedy.domain.RemedySquare;

public class RemedySquareManager extends RemedyBaseBO {
    private RemedyApplyDao applyDao = new RemedyApplyDao();

    private RemedySquareDao squareDao = new RemedySquareDao();

    public RemedySquareManager() {
        applyDao = new RemedyApplyDao();
        squareDao = new RemedySquareDao();
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
        logger(RemedySquareManager.class);
        super.setConditionGenerate(new ConditionGenerate());
        String power = request.getParameter("power");
        String userQueryCondition = super.getConditionGenerate().getUserQueryCondition(request,
                form);
        String stateCondition = super.getConditionGenerate().getStateCondition(request, form);
        String userOperationCondition = super.getConditionGenerate().getUserOperationCondition(
                request, form);
        String orderCondition = super.getConditionGenerate().getOrderCondition();
        StringBuffer condition = new StringBuffer();
        condition.append(userQueryCondition);
        condition.append(stateCondition);
        if (!RemedyPowerConstant.SQUARE_LIST_POWER.equals(power)) {
            condition.append(userOperationCondition);
        }
        condition.append(orderCondition);
        return condition.toString();
    }

    /**
     * 执行结算修缮申请信息
     * 
     * @param request
     *            HttpServletRequest 用户输入请求
     * @param form
     *            ActionForm 用户输入表单
     * @return String 结算动作编码
     * @throws Exception
     */
    public String squareApply(HttpServletRequest request, ActionForm form) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedySquareManager.class);

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String currentUserId = userInfo.getUserID();

        String applyId = request.getParameter("apply_id");
        RemedySquareBean squareBean = (RemedySquareBean) form;
        RemedySquare square = new RemedySquare();
        try {
            BeanUtil.objectCopy(squareBean, square);
            if (!applyDao.judgeExistApply(applyId)) {
                return ExecuteCode.NO_DATA_ERROR;
            }
            if (!applyDao.judgeAllowSquared(applyId)) {
                return ExecuteCode.NOT_SQUARED_APPLY_ERROR;
            }

            RemedyApply apply = (RemedyApply) applyDao.viewOneObject(RemedyApply.class, applyId);
            apply.setPrevState(apply.getState());
            String applyState = RemedyWorkflowConstant.ACHIEVED_STATE;
            String nextProcessMan = square.getNextProcessMan();
            apply.setState(applyState);
            apply.setNextProcessMan(nextProcessMan);
            String stepId = workflowBo.doWorkflowAction(apply, currentUserId);
            if (ExecuteCode.FAIL_CODE.equals(stepId)) {
                applyDao.rollbackTransaction();
                return ExecuteCode.FAIL_CODE;
            }
            apply.setCurrentStepId(stepId);

            square.setSquareDate(new Date());
            square.setSquareMan(currentUserId);
            String operationCode = squareDao.saveSquare(applyId, square);
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
            return operationCode;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            applyDao.rollbackTransaction();
            logger.error(e);
            throw e;
        }
    }

    /**
     * 执行根据修缮申请编号判断是否可以执行修缮申请的结算操作
     * 
     * @param applyId
     *            String 修缮申请编号
     * @return boolean 判断结果
     */
    public boolean judge(String applyId) {
        // TODO Auto-generated method stub
        logger(RemedySquareManager.class);
        super.setRemedyBaseDao(new RemedyApplyDao());
        try {
            return super.getRemedyBaseDao().judgeAllowSquared(applyId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            return false;
        }
    }

    /**
     * 执行获取下一步结算人列表
     * 
     * @param applyState
     *            String 修缮申请的状态
     * @return List 下一步结算人列表
     */
    public List getNextProcessManList(String applyState) {
        // TODO Auto-generated method stub
        try {
            super.setRemedyBaseDao(new RemedySquareDao());
            List list = super.getRemedyBaseDao().getMobileUser("");
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
        }
        return null;
    }
}
