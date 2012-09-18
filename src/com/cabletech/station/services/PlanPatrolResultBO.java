package com.cabletech.station.services;

import java.util.List;

import com.cabletech.station.beans.PlanPatrolResultBean;
import com.cabletech.station.beans.RepeaterStationPlanBean;
import com.cabletech.station.dao.PlanPatrolResultDayDAO;
import com.cabletech.station.dao.RepeaterStationPlanDAO;
import com.cabletech.station.domainobjects.RepeaterStationPlan;

/**
 * Ѳ����д��Ϣ�ĳ���ҵ�������
 * 
 * @author yangjun
 * 
 */
public abstract class PlanPatrolResultBO extends BaseBO {
    public PlanPatrolResultBO() {
        super.setBaseDao(new PlanPatrolResultDayDAO());
    }

    /**
     * ִ����дѲ����Ϣ��ҵ�����
     * 
     * @param planPatrolResultBean
     *            PlanPatrolResultBean ��д��Ѳ����Ϣ
     * @return String ��д��ִ�ж���״̬���
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
     * ���ݼƻ���Ŷ�ȡ�м�վ�ƻ���Ϣ
     * 
     * @param planId
     *            String �ƻ����
     * @return PlanPatrolResultBean �����м�վ�ƻ���Ϣ
     */
    public RepeaterStationPlanBean viewRepeaterStationPlan(String planId) {
        super.setBaseDao(new PlanPatrolResultDayDAO());
        RepeaterStationPlanBean stationPlanBean = new RepeaterStationPlanBean();
        return stationPlanBean;
    }

    /**
     * ���ݼƻ���ź��м�վ��Ŷ�ȡ�м�վ��д��Ϣ�ĳ��󷽷�
     * 
     * @param stationId
     *            String �м�վ���
     * @param planId
     *            String �ƻ����
     * @return PlanPatrolResultBean �����м�վ��д��Ϣ
     * @throws Exception
     */
    public abstract PlanPatrolResultBean viewPlanStationResult(String stationId, String planId)
            throws Exception;

    /**
     * �����м�վ�ƻ���д��Ϣ�ĳ��󷽷�
     * 
     * @param planId
     *            String �м�վ�ƻ����
     * @param planPatrolResultBean
     *            PlanPatrolResultBean �м�վ��д��Ϣ
     * @return String ����ִ�ж���״̬���
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
     * ���ݼƻ�����ж��м�վ�ƻ��Ƿ�ͨ��
     * 
     * @param planId
     *            String �ƻ����
     * @return boolean �����м�վ�ƻ��Ƿ�ͨ���ı��
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
     * ���ݼƻ�����ж��м�վ�ƻ��Ƿ�ȫ�����
     * 
     * @param planId
     *            String �ƻ����
     * @return boolean �����м�վ�ƻ��Ƿ�ȫ����ɵı��
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
