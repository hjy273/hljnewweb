package com.cabletech.station.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;

/**
 * �����ռƻ���DAO������
 * @author yangjun
 *
 */
public class PlanPatrolResultDayDAO extends BaseDAO {
    private QueryUtil query;

    /**
     * ���ݲ�ѯ������ȡ�����ռƻ����б���Ϣ
     * @param conditionString
     *            String ��ѯ����
     * @return List ���ز����ռƻ����б���Ϣ
     * @throws Exception
     */
    public List queryByCondition(String sqlCondition) throws Exception {
        try {
            String sql = "select tid from plan_patrol_result_day where 1=1 " + sqlCondition;
            query = new QueryUtil();
            return query.queryBeans(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            query.close();
        }
    }

    public List findPlanStationResultItem(String planType) {
        return null;
    }

    public List findPlanStationResult(String planId, String stationId) {
        return null;
    }
}