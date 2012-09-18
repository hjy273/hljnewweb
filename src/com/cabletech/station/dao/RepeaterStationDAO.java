package com.cabletech.station.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;

/**
 * �м�վ��DAO������
 * 
 * @author yangjun
 * 
 */
public class RepeaterStationDAO extends BaseDAO {
    private QueryUtil query;

    /**
     * ���ݲ�ѯ������ȡ�м�վ���б���Ϣ
     * 
     * @param conditionString
     *            String ��ѯ����
     * @return List �����м�վ���б���Ϣ
     * @throws Exception
     */
    public List queryByCondition(String sqlCondition) throws Exception {
        try {
            String sql = "select tid,region_id,regionname,station_name,is_ground_station,has_electricity,";
            sql = sql
                    + "connect_electricity,has_air_condition,connect_air_condition,connect_wind_power_generate,";
            sql = sql
                    + "station_remark,to_char(create_date,'yyyy-mm-dd hh24:mi:ss') as create_date_dis,'' as is_allow ";
            sql = sql + "from repeater_station_info s,region r where s.region_id=r.regionid "
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
}
