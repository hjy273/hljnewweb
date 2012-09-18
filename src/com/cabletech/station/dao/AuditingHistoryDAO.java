package com.cabletech.station.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.station.services.StationConstant;

/**
 * �м�վ�ƻ��������ϢDAO
 * 
 * @author yangjun
 * 
 */
public class AuditingHistoryDAO extends BaseDAO {
    private QueryUtil query;

    /**
     * ���ݲ�ѯ�������������ʷ��Ϣ�Ĳ�ѯ
     * 
     * @param sqlCondition
     *            String ��ѯ������Sql���
     * @return List ���������ʷ��Ϣ���б�
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
