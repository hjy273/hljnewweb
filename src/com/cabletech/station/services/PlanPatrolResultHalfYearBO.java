package com.cabletech.station.services;

import java.util.Date;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.station.beans.PlanPatrolResultBean;
import com.cabletech.station.dao.PlanPatrolResultHalfYearDAO;
import com.cabletech.station.domainobjects.PlanPatrolResultHalfYear;

/**
 * ���ְ���ƻ���ҵ�������
 * 
 * @author yangjun
 * 
 */
public class PlanPatrolResultHalfYearBO extends PlanPatrolResultBO {
    public PlanPatrolResultHalfYearBO() {
        super.setBaseDao(new PlanPatrolResultHalfYearDAO());
    }

    /**
     * �����м�վ�İ���ƻ���д��Ϣ
     * 
     * @param planId
     *            String �м�վ����ƻ����
     * @param planPatrolResultBean
     *            PlanPatrolResultBean �м�վ��д��Ϣ
     * @return String ����ִ�ж���״̬���
     * @throws Exception
     */
    public String insertPlanPatrolResult(String planId, PlanPatrolResultBean planPatrolResultBean)
            throws Exception {
        super.setBaseDao(new PlanPatrolResultHalfYearDAO());
        PlanPatrolResultHalfYear planPatrolResult = new PlanPatrolResultHalfYear();

        if (super.queryExistById(planPatrolResultBean.getTid(), PlanPatrolResultHalfYear.class)) {
            planPatrolResult = (PlanPatrolResultHalfYear) super.baseDao.load(planPatrolResultBean
                    .getTid(), PlanPatrolResultHalfYear.class);
            Date createDate = planPatrolResult.getCreateDate();
            BeanUtil.objectCopy(planPatrolResultBean, planPatrolResult);
            planPatrolResult.setCreateDate(createDate);
            Object object = super.baseDao.update(planPatrolResult);
            if (object != null) {
                return ExecuteCode.SUCCESS_CODE;
            }
        } else {
            BeanUtil.objectCopy(planPatrolResultBean, planPatrolResult);
            planPatrolResult.setTid(ora.getSeq("PLAN_PATROL_RESULT_HALF_YEAR", "RESULT_HALFY", 18));
            planPatrolResult.setCreateDate(new Date());
            Object object = super.baseDao.insert(planPatrolResult);
            if (object != null) {
                return ExecuteCode.SUCCESS_CODE;
            }
        }
        return ExecuteCode.FAIL_CODE;
    }

    /**
     * ���ݰ���ƻ���ź��м�վ��Ŷ�ȡ�м�վ��д��Ϣ
     * 
     * @param stationId
     *            String �м�վ���
     * @param planId
     *            String ����ƻ����
     * @return PlanPatrolResultBean �����м�վ��д��Ϣ
     * @throws Exception
     * @throws Exception
     */
    public PlanPatrolResultBean viewPlanStationResult(String stationId, String planId)
            throws Exception {
        super.setBaseDao(new PlanPatrolResultHalfYearDAO());
        PlanPatrolResultBean planPatrolResultBean = new PlanPatrolResultBean();

        String condition = " and repeater_station_id='" + stationId + "' and plan_id='" + planId
                + "' ";
        PlanPatrolResultHalfYear planPatrolResult = (PlanPatrolResultHalfYear) super.baseDao.find(
                "PlanPatrolResultHalfYear", condition);
        if (planPatrolResult != null) {
            BeanUtil.objectCopy(planPatrolResult, planPatrolResultBean);
            return planPatrolResultBean;
        } else {
            return null;
        }
    }

}
