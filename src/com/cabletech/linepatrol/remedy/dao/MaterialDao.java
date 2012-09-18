package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.List;

import com.cabletech.commons.hb.QueryUtil;

public class MaterialDao extends RemedyBaseDao {
    /**
     * ִ���ж�������������б��в��Ϸ����
     * 
     * @param materialList
     *            List ������������б�
     * @return boolean ������������б��в����Ƿ����
     * @throws Exception
     */
    public boolean judgeExistMaterial(List materialList) throws Exception {
        logger(MaterialDao.class);
        String baseSql = "select id from LINEPATROL_MT_BASE where state='1' ";
        String sql = "";
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list;
            for (int i = 0; materialList != null && i < materialList.size(); i++) {
                sql = baseSql + " and id='" + materialList.get(i) + "' ";
                logger.info("Execute sql:" + sql);
                list = queryUtil.queryBeans(sql);
                if (list == null || list.isEmpty()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * ִ�и��ݲ�ѯ������ȡ�������������Ϣ�б�
     * 
     * @param condition
     *            String ��ѯ����
     * @return List �������������Ϣ�б�
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        logger(MaterialDao.class);
        String sql = "select distinct t.id,t.name,t.modelid,to_char(t.price) as price,t.factory,t.state,tt.id ";
        sql = sql + " from LINEPATROL_MT_BASE t,LINEPATROL_MT_MODEL mt,LINEPATROL_MT_TYPE tt, ";
        sql = sql + " contractorinfo c,region r ";
        sql = sql + " where t.modelid=mt.id and mt.typeid=tt.id and tt.regionid=r.regionid ";
        sql = sql + " and r.regionid=c.regionid and t.state='1' ";
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
