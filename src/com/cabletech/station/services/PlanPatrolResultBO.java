package com.cabletech.station.services;

import java.util.List;

import com.cabletech.station.beans.PlanPatrolResultBean;
import com.cabletech.station.beans.RepeaterStationPlanBean;
import com.cabletech.station.dao.PlanPatrolResultDayDAO;
import com.cabletech.station.dao.RepeaterStationPlanDAO;
import com.cabletech.station.domainobjects.RepeaterStationPlan;

/**
 * 巡检填写信息的抽象业务操作类
 * 
 * @author yangjun
 * 
 */
public abstract class PlanPatrolResultBO extends BaseBO {
    public PlanPatrolResultBO() {
        super.setBaseDao(new PlanPatrolResultDayDAO());
    }

    /**
     * 执行填写巡检信息的业务操作
     * 
     * @param planPatrolResultBean
     *            PlanPatrolResultBean 填写的巡检信息
     * @return String 填写的执行动作状态编号
     * @throws Exception
     */
    public String writePlanStationResult(PlanPatrolResultBean planPatrolResultBean)
            throws Exception {
        super.setBaseDao(new PlanPatrolResultDayDAO());
        String planId = planPatrolResultBean.getPlanId();

        if (!super.queryExistById(planId, RepeaterStationPlan.class)) {
            return ExecuteCode.NOT_EXIST_PLAN_ERR_CODE;
        }
        if (!judgePassedPlan(planId)) {
            return ExecuteCode.NOT_PASSED_PLAN_ERR_CODE;
        }
        if (judgeFinishPlan(planId)) {
            return ExecuteCode.FINISHED_PLAN_ERR_CODE;
        }

        return insertPlanPatrolResult(planId, planPatrolResultBean);
    }

    /**
     * 根据计划编号读取中继站计划信息
     * 
     * @param planId
     *            String 计划编号
     * @return PlanPatrolResultBean 返回中继站计划信息
     */
    public RepeaterStationPlanBean viewRepeaterStationPlan(String planId) {
        super.setBaseDao(new PlanPatrolResultDayDAO());
        RepeaterStationPlanBean stationPlanBean = new RepeaterStationPlanBean();
        return stationPlanBean;
    }

    /**
     * 根据计划编号和中继站编号读取中继站填写信息的抽象方法
     * 
     * @param stationId
     *            String 中继站编号
     * @param planId
     *            String 计划编号
     * @return PlanPatrolResultBean 返回中继站填写信息
     * @throws Exception
     */
    public abstract PlanPatrolResultBean viewPlanStationResult(String stationId, String planId)
            throws Exception;

    /**
     * 保存中继站计划填写信息的抽象方法
     * 
     * @param planId
     *            String 中继站计划编号
     * @param planPatrolResultBean
     *            PlanPatrolResultBean 中继站填写信息
     * @return String 返回执行动作状态编号
     * @throws Exception
     */
    public abstract String insertPlanPatrolResult(String planId,
            PlanPatrolResultBean planPatrolResultBean) throws Exception;

    /**
     * 
     * @param stationId
     *            String
     * @param planPatrolResultBean
     *            PlanPatrolResultBean
     */
    public void setPlanStationResultExist(String stationId,
            PlanPatrolResultBean planPatrolResultBean) {
        super.setBaseDao(new PlanPatrolResultDayDAO());

    }

    /**
     * 根据计划编号判断中继站计划是否通过
     * 
     * @param planId
     *            String 计划编号
     * @return boolean 返回中继站计划是否通过的标记
     * @throws Exception
     */
    public boolean judgePassedPlan(String planId) throws Exception {
        super.setBaseDao(new RepeaterStationPlanDAO());
        boolean flag = false;

        StringBuffer condition = new StringBuffer("");
        condition.append(" and ");
        condition.append(" plan_state='");
        condition.append(StationConstant.PASSED_STATE);
        condition.append("' ");
        condition.append(" and tid='");
        condition.append(planId);
        condition.append("'");
        List list = super.baseDao.queryByCondition(condition.toString());
        if (list != null && list.size() > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 根据计划编号判断中继站计划是否全部完成
     * 
     * @param planId
     *            String 计划编号
     * @return boolean 返回中继站计划是否全部完成的标记
     * @throws Exception
     */
    public boolean judgeFinishPlan(String planId) throws Exception {
        super.setBaseDao(new RepeaterStationPlanDAO());
        boolean flag = false;

        StringBuffer condition = new StringBuffer("");
        condition.append(" and ");
        condition.append(" plan_state='");
        condition.append(StationConstant.FINISHED_STATE);
        condition.append("' ");
        condition.append(" and tid='");
        condition.append(planId);
        condition.append("'");
        List list = super.baseDao.queryByCondition(condition.toString());
        if (list != null && list.size() > 0) {
            flag = true;
        }
        return flag;
    }
}
