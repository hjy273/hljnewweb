package com.cabletech.station.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;

/**
 * �м�վ�ƻ���DAO������
 * 
 * @author yangjun
 * 
 */
public class RepeaterStationPlanDAO extends BaseDAO {
    private QueryUtil query;

    /**
     * ���ݲ�ѯ������ȡ�м�վ�ƻ����б���Ϣ
     * @param conditionString
     *            String ��ѯ����
     * @return List �����м�վ�ƻ����б���Ϣ
     * @throws Exception
     */
    public List queryByCondition(String sqlCondition) throws Exception {
        try {
            String sql = "select tid,region_id,regionname,plan_name,"
                    + "to_char(begin_date,'yyyy/mm/dd') as begin_date_dis,"
                    + "to_char(end_date,'yyyy/mm/dd') as end_date_dis,"
                    + "plan_type,plan_state,validate_result,validate_remark,u.username,'' as station_name_dis "
                    + "from repeater_station_plan p,region r,userinfo u "
                    + "where validate_userid=u.userid(+) and p.region_id=r.regionid "
                    + sqlCondition;
            System.out.println(sql);
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
     * @param auditingResult
     *            String
     * @return boolean
     */
    public boolean updatePlanState(String planId, String auditingResult) {
        boolean flag = false;
        return flag;
    }

    /**
     * 
     * @param planId
     *            String
     * @return String
     */
    public String getPlanState(String planId) {
        String state = "";
        return state;
    }
}
