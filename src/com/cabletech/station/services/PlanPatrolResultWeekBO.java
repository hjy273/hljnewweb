package com.cabletech.station.services;

import java.util.Date;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.station.beans.PlanPatrolResultBean;
import com.cabletech.station.dao.PlanPatrolResultWeekDAO;
import com.cabletech.station.domainobjects.PlanPatrolResultWeek;

/**
 * 波分周计划的业务操作类
 * 
 * @author yangjun
 * 
 */
public class PlanPatrolResultWeekBO extends PlanPatrolResultBO {
    public PlanPatrolResultWeekBO() {
        super.setBaseDao(new PlanPatrolResultWeekDAO());
    }

    /**
     * 保存中继站的周计划填写信息
     * 
     * @param planId
     *            String 中继站周计划编号
     * @param planPatrolResultBean
     *            PlanPatrolResultBean 中继站填写信息
     * @return String 返回执行动作状态编号
     * @throws Exception
    */
    public String insertPlanPatrolResult(String planId, PlanPatrolResultBean planPatrolResultBean)
            throws Exception {
        super.setBaseDao(new PlanPatrolResultWeekDAO());
        PlanPatrolResultWeek planPatrolResult = new PlanPatrolResultWeek();

        if (super.queryExistById(planPatrolResultBean.getTid(), PlanPatrolResultWeek.class)) {
            planPatrolResult = (PlanPatrolResultWeek) super.baseDao.load(planPatrolResultBean
                    .getTid(), PlanPatrolResultWeek.class);
            Date createDate = planPatrolResult.getCreateDate();
            BeanUtil.objectCopy(planPatrolResultBean, planPatrolResult);
            planPatrolResult.setCreateDate(createDate);
            Object object = super.baseDao.update(planPatrolResult);
            if (object != null) {
                return ExecuteCode.SUCCESS_CODE;
            }
        } else {
            BeanUtil.objectCopy(planPatrolResultBean, planPatrolResult);
            planPatrolResult.setTid(ora.getSeq("PLAN_PATROL_RESULT_WEEK", 18));
            planPatrolResult.setCreateDate(new Date());
            Object object = super.baseDao.insert(planPatrolResult);
            if (object != null) {
                return ExecuteCode.SUCCESS_CODE;
            }
        }
        return ExecuteCode.FAIL_CODE;
    }

    /**
     * 根据周计划编号和中继站编号读取中继站填写信息
     * 
     * @param stationId
     *            String 中继站编号
     * @param planId
     *            String 周计划编号
     * @return PlanPatrolResultBean 返回中继站填写信息
     * @throws Exception
     */
    public PlanPatrolResultBean viewPlanStationResult(String stationId, String planId)
            throws Exception {
        super.setBaseDao(new PlanPatrolResultWeekDAO());
        PlanPatrolResultBean planPatrolResultBean = new PlanPatrolResultBean();

        String condition = " and repeater_station_id='" + stationId + "' and plan_id='" + planId + "' ";
        PlanPatrolResultWeek planPatrolResult = (PlanPatrolResultWeek) super.baseDao.find(
                "PlanPatrolResultWeek", condition);
        if (planPatrolResult != null) {
            BeanUtil.objectCopy(planPatrolResult, planPatrolResultBean);
            return planPatrolResultBean;
        } else {
            return null;
        }
    }

}
