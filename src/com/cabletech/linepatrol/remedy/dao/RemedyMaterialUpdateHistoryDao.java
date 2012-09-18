package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.beans.RemedyMaterialBean;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.domain.RemedyMaterialUpdateHistory;

public class RemedyMaterialUpdateHistoryDao extends RemedyBaseDao {
    /**
     * 执行根据查询条件获取修缮材料使用数量修改历史信息列表
     * 
     * @param condition
     *            String 查询条件
     * @return List 修缮材料使用数量修改历史信息列表
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedyMaterialUpdateHistoryDao.class);
        String sql = " ";
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

    /**
     * 执行保存修改修缮材料库存历史信息
     * 
     * @param bean
     *            RemedyMaterialBean 修改修缮材料库存信息
     * @param userId
     *            String 当前用户编号
     * @return String 保存修改修缮材料库存历史信息编码
     * @throws Exception
     */
    public String saveOneHistory(RemedyMaterialBean bean, String userId) throws Exception {
        RemedyMaterialUpdateHistory history = new RemedyMaterialUpdateHistory();
        history.setId(ora.getSeq("LINEPATROL_REMEDY_MATERIAL_UPD", "REMEDY_MATERIAL_UPD", 20));
        history.setUpdateMan(userId);
        history.setMaterialId(Integer.parseInt(bean.getMaterialId()));
        history.setAddressId(Integer.parseInt(bean.getMaterialStorageAddressId()));
        history.setOldNumber(new Float(bean.getAdjustOldNum()));
        history.setNewNumber(new Float(bean.getAdjustNewNum()));
        history.setRemedyId(bean.getRemedyId());
        Object obj = super.insertOneObject(history);
        if (obj == null) {
            return ExecuteCode.FAIL_CODE;
        }
        return ExecuteCode.SUCCESS_CODE;
    }
}
