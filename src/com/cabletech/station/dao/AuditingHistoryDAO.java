package com.cabletech.station.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.station.services.StationConstant;

/**
 * 中继站计划的审核信息DAO
 * 
 * @author yangjun
 * 
 */
public class AuditingHistoryDAO extends BaseDAO {
    private QueryUtil query;

    /**
     * 根据查询条件进行审核历史信息的查询
     * 
     * @param sqlCondition
     *            String 查询条件的Sql语句
     * @return List 返回审核历史信息的列表
     * @throws Exception
     */
    public List queryByCondition(String sqlCondition) throws Exception {
        try {
            String sql = "select tid,username,validate_result,validate_remark,to_char(validate_time,'yyyy-mm-dd') as validate_time_dis from flow_work_info f,userinfo u where f.validate_userid=u.userid and sonmenu_id='"
                    + StationConstant.SONMENU + "' " + sqlCondition;
            query = new QueryUtil();
            return query.queryBeans(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            query.close();
        }
    }
}
