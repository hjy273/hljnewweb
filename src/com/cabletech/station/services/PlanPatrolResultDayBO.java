package com.cabletech.station.services;

import java.util.Date;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.station.beans.PlanPatrolResultBean;
import com.cabletech.station.dao.PlanPatrolResultDayDAO;
import com.cabletech.station.domainobjects.PlanPatrolResultDay;

/**
 * 波分日计划的业务操作类
 * 
 * @author yangjun
 * 
 */
public class PlanPatrolResultDayBO extends PlanPatrolResultBO {
    public PlanPatrolResultDayBO() {
        super.setBaseDao(new PlanPatrolResultDayDAO());
    }

    /**
     * 保存中继站的日计划填写信息
     * 
     * @param planId
     *            String 中继站日计划编号
     * @param planPatrolResultBean
     *            PlanPatrolResultBean 中继站填写信息
     * @return String 返回执行动作状态编号
     * @throws Exception
     */
    public String insertPlanPatrolResult(String planId, PlanPatrolResultBean planPatrolResultBean)
            throws Exception {
        super.setBaseDao(new PlanPatrolResultDayDAO());
        PlanPatrolResultDay planPatrolResult = new PlanPatrolResultDay();

        if (super.queryExistById(planPatrolResultBean.getTid(), PlanPatrolResultDay.class)) {
            planPatrolResult = (PlanPatrolResultDay) super.baseDao.load(planPatrolResultBean
                    .getTid(), PlanPatrolResultDay.class);
            Date createDate = planPatrolResult.getCreateDate();
            BeanUtil.objectCopy(planPatrolResultBean, planPatrolResult);
            planPatrolResult.setCreateDate(createDate);
            Object object = super.baseDao.update(planPatrolResult);
            if (object != null) {
                return ExecuteCode.SUCCESS_CODE;
            }
        } else {
            BeanUtil.objectCopy(planPatrolResultBean, planPatrolResult);
            planPatrolResult.setTid(ora.getSeq("PLAN_PATROL_RESULT_DAY", 18));
            planPatrolResult.setCreateDate(new Date());
            Object object = super.baseDao.insert(planPatrolResult);
            if (object != null) {
                return ExecuteCode.SUCCESS_CODE;
            }
        }
        return ExecuteCode.FAIL_CODE;
    }

    /**
     * 根据日计划编号和中继站编号读取中继站填写信息
     * 
     * @param stationId
     *            String 中继站编号
     * @param planId
     *            String 日计划编号
     * @return PlanPatrolResultBean 返回中继站填写信息
     * @throws Exception
     */
    public PlanPatrolResultBean viewPlanStationResult(String stationId, String planId)
            throws Exception {
        super.setBaseDao(new PlanPatrolResultDayDAO());
        PlanPatrolResultBean planPatrolResultBean = new PlanPatrolResultBean();

        String condition = " and repeater_station_id='" + stationId + "' and plan_id='" + planId
                + "' ";
        PlanPatrolResultDay planPatrolResult = (PlanPatrolResultDay) super.baseDao.find(
                "PlanPatrolResultDay", condition);
        if (planPatrolResult != null) {
            BeanUtil.objectCopy(planPatrolResult, planPatrolResultBean);
            return planPatrolResultBean;
        } else {
            return null;
        }
    }

}
