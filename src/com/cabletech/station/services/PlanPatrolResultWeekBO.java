package com.cabletech.station.services;

import java.util.Date;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.station.beans.PlanPatrolResultBean;
import com.cabletech.station.dao.PlanPatrolResultWeekDAO;
import com.cabletech.station.domainobjects.PlanPatrolResultWeek;

/**
 * �����ܼƻ���ҵ�������
 * 
 * @author yangjun
 * 
 */
public class PlanPatrolResultWeekBO extends PlanPatrolResultBO {
    public PlanPatrolResultWeekBO() {
        super.setBaseDao(new PlanPatrolResultWeekDAO());
    }

    /**
     * �����м�վ���ܼƻ���д��Ϣ
     * 
     * @param planId
     *            String �м�վ�ܼƻ����
     * @param planPatrolResultBean
     *            PlanPatrolResultBean �м�վ��д��Ϣ
     * @return String ����ִ�ж���״̬���
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
     * �����ܼƻ���ź��м�վ��Ŷ�ȡ�м�վ��д��Ϣ
     * 
     * @param stationId
     *            String �м�վ���
     * @param planId
     *            String �ܼƻ����
     * @return PlanPatrolResultBean �����м�վ��д��Ϣ
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
