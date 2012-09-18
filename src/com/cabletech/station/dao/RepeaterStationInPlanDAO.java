package com.cabletech.station.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;

/**
 * �м�վ�ƻ����м�վ��DAO������
 * 
 * @author yangjun
 * 
 */
public class RepeaterStationInPlanDAO extends BaseDAO {
    private QueryUtil query;

    /**
     * ���ݲ�ѯ������ȡ�м�վ�ƻ��е��м�վ�б���Ϣ
     * 
     * @param conditionString
     *            String ��ѯ����
     * @return List �����м�վ�ƻ��е��м�վ�б���Ϣ
     * @throws Exception
     */
    public List queryByCondition(String sqlCondition) throws Exception {
        try {
            String sql = "select repeater_station_id,plan_id,station_name ";
            sql = sql
                    + " from repeater_station_plan_item i,repeater_station_info s,repeater_station_plan p ";
            sql = sql + " where i.repeater_station_id=s.tid and p.tid=i.plan_id " + sqlCondition;
            query = new QueryUtil();
            return query.queryBeans(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            query.close();
        }
    }

    /**
     * 
     * @param planId
     *            String
     */
    public void deleteAllStationInPlan(String planId) {

    }
}
