package com.cabletech.station.services;

import java.util.Date;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.station.beans.PlanPatrolResultBean;
import com.cabletech.station.dao.PlanPatrolResultDayDAO;
import com.cabletech.station.domainobjects.PlanPatrolResultDay;

/**
 * �����ռƻ���ҵ�������
 * 
 * @author yangjun
 * 
 */
public class PlanPatrolResultDayBO extends PlanPatrolResultBO {
    public PlanPatrolResultDayBO() {
        super.setBaseDao(new PlanPatrolResultDayDAO());
    }

    /**
     * �����м�վ���ռƻ���д��Ϣ
     * 
     * @param planId
     *            String �м�վ�ռƻ����
     * @param planPatrolResultBean
     *            PlanPatrolResultBean �м�վ��д��Ϣ
     * @return String ����ִ�ж���״̬���
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
     * �����ռƻ���ź��м�վ��Ŷ�ȡ�м�վ��д��Ϣ
     * 
     * @param stationId
     *            String �м�վ���
     * @param planId
     *            String �ռƻ����
     * @return PlanPatrolResultBean �����м�վ��д��Ϣ
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
