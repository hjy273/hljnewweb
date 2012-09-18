package com.cabletech.station.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;

/**
 * 波分周计划的DAO操作类
 * @author yangjun
 *
 */
public class PlanPatrolResultWeekDAO extends BaseDAO {
    private QueryUtil query;

    /**
     * 根据查询条件获取波分周计划的列表信息
     * @param conditionString
     *            String 查询条件
     * @return List 返回波分周计划的列表信息
     * @throws Exception
     */
    public List queryByCondition(String sqlCondition) throws Exception {
        try {
            String sql = "select tid from plan_patrol_result_week where 1=1 " + sqlCondition;
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
