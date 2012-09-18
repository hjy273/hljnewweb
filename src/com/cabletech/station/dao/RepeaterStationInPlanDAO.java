package com.cabletech.station.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;

/**
 * 中继站计划中中继站的DAO操作类
 * 
 * @author yangjun
 * 
 */
public class RepeaterStationInPlanDAO extends BaseDAO {
    private QueryUtil query;

    /**
     * 根据查询条件获取中继站计划中的中继站列表信息
     * 
     * @param conditionString
     *            String 查询条件
     * @return List 返回中继站计划中的中继站列表信息
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
