package com.cabletech.station.services;

import java.util.Date;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.station.beans.PlanPatrolResultBean;
import com.cabletech.station.dao.PlanPatrolResultMonthDAO;
import com.cabletech.station.domainobjects.PlanPatrolResultMonth;

/**
 * �����¼ƻ���ҵ�������
 * 
 * @author yangjun
 * 
 */
public class PlanPatrolResultMonthBO extends PlanPatrolResultBO {
    public PlanPatrolResultMonthBO() {
        super.setBaseDao(new PlanPatrolResultMonthDAO());
    }

    /**
     * �����м�վ���¼ƻ���д��Ϣ
     * 
     * @param planId
     *            String �м�վ�¼ƻ����
     * @param planPatrolResultBean
     *            PlanPatrolResultBean �м�վ��д��Ϣ
     * @return String ����ִ�ж���״̬���
     * @throws Exception
     */
    public String insertPlanPatrolResult(String planId, PlanPatrolResultBean planPatrolResultBean)
            throws Exception {
        super.setBaseDao(new PlanPatrolResultMonthDAO());
        PlanPatrolResultMonth planPatrolResult = new PlanPatrolResultMonth();

        if (super.queryExistById(planPatrolResultBean.getTid(), PlanPatrolResultMonth.class)) {
            planPatrolResult = (PlanPatrolResultMonth) super.baseDao.load(planPatrolResultBean
                    .getTid(), PlanPatrolResultMonth.class);
            Date createDate = planPatrolResult.getCreateDate();
            BeanUtil.objectCopy(planPatrolResultBean, planPatrolResult);
            planPatrolResult.setCreateDate(createDate);
            Object object = super.baseDao.update(planPatrolResult);
            if (object != null) {
                return ExecuteCode.SUCCESS_CODE;
            }
        } else {
            BeanUtil.objectCopy(planPatrolResultBean, planPatrolResult);
            planPatrolResult.setTid(ora.getSeq("PLAN_PATROL_RESULT_MONTH", "RESULT_MONTH", 18));
            planPatrolResult.setCreateDate(new Date());
            Object object = super.baseDao.insert(planPatrolResult);
            if (object != null) {
                return ExecuteCode.SUCCESS_CODE;
            }
        }
        return ExecuteCode.FAIL_CODE;
    }

    /**
     * �����¼ƻ���ź��м�վ��Ŷ�ȡ�м�վ��д��Ϣ
     * 
     * @param stationId
     *            String �м�վ���
     * @param planId
     *            String �¼ƻ����
     * @return PlanPatrolResultBean �����м�վ��д��Ϣ
     * @throws Exception
     */
    public PlanPatrolResultBean viewPlanStationResult(String stationId, String planId)
            throws Exception {
        super.setBaseDao(new PlanPatrolResultMonthDAO());
        PlanPatrolResultBean planPatrolResultBean = new PlanPatrolResultBean();

        String condition = " and repeater_station_id='" + stationId + "' and plan_id='" + planId
                + "' ";
        PlanPatrolResultMonth planPatrolResult = (PlanPatrolResultMonth) super.baseDao.find(
                "PlanPatrolResultMonth", condition);
        if (planPatrolResult != null) {
            BeanUtil.objectCopy(planPatrolResult, planPatrolResultBean);
            return planPatrolResultBean;
        } else {
            return null;
        }
    }

}
