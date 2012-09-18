package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.beans.RemedyMaterialBean;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.domain.RemedyMaterialUpdateHistory;

public class RemedyMaterialUpdateHistoryDao extends RemedyBaseDao {
    /**
     * ִ�и��ݲ�ѯ������ȡ���ɲ���ʹ�������޸���ʷ��Ϣ�б�
     * 
     * @param condition
     *            String ��ѯ����
     * @return List ���ɲ���ʹ�������޸���ʷ��Ϣ�б�
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
     * ִ�б����޸����ɲ��Ͽ����ʷ��Ϣ
     * 
     * @param bean
     *            RemedyMaterialBean �޸����ɲ��Ͽ����Ϣ
     * @param userId
     *            String ��ǰ�û����
     * @return String �����޸����ɲ��Ͽ����ʷ��Ϣ����
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
