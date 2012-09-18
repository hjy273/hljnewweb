package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.List;

import com.cabletech.commons.hb.QueryUtil;

public class MaterialModelDao extends RemedyBaseDao {
    /**
     * 执行根据查询条件获取修缮申请材料类型信息列表
     * 
     * @param condition
     *            String 查询条件
     * @return List 修缮申请材料类型信息列表
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        logger(MaterialModelDao.class);
        String sql = "select distinct t.id,t.typeid,t.modelname,t.unit,t.state ";
        sql = sql + " from LINEPATROL_MT_MODEL t,LINEPATROL_MT_TYPE mt,contractorinfo c,region r ";
        sql = sql
                + " where t.typeid=mt.id and mt.regionid=r.regionid and r.regionid=c.regionid and t.state='1' ";
        sql = sql + condition;
        try {
            logger.info("Execute sql:" + sql);
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            if (list != null) {
                return list;
            } else {
                return new ArrayList();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }
}
