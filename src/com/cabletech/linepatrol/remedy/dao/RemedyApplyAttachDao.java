package com.cabletech.linepatrol.remedy.dao;

import java.util.List;
import java.util.Map;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.domain.RemedyApplyAttach;

public class RemedyApplyAttachDao extends RemedyBaseDao {
    /**
     * ִ�и��ݲ�ѯ������ȡ�������븽����Ϣ�б�
     * 
     * @param condition
     *            String ��ѯ����
     * @return List �������븽����Ϣ�б�
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedyApplyAttachDao.class);
        String sql = "select id,REMEDYID,FLAG,ATTACHNAME,ATTACHID from LINEPATROL_REMEDY_ATTACH where 1=1 "
                + condition;
        try {
            logger.info("Execute sql:" + sql);
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * ִ�и������������ű����������븽����Ϣ
     * 
     * @param applyId
     *            String ����������
     * @param attachList
     *            List �������븽����Ϣ�б�
     * @param attachType
     *            �������븽������
     * @return String �����������븽����Ϣ����
     * @throws Exception
     */
    public String saveAttachList(String applyId, List attachList, String attachType)
            throws Exception {
        logger(RemedyApplyAttachDao.class);
        RemedyApplyAttach attach;
        String[] seqIds = new String[0];
        Map oneAttach;
        try {
            if (attachList != null && attachList.size() > 0) {
                seqIds = ora.getSeqs("LINEPATROL_REMEDY_ATTACH", "REMEDY_ATTACH", 20, attachList
                        .size());
            }
            for (int i = 0; attachList != null && i < attachList.size(); i++) {
                oneAttach = (Map) attachList.get(i);
                attach = new RemedyApplyAttach();
                attach.setId(seqIds[i]);
                attach.setFlag(attachType);
                attach.setAttachName((String) oneAttach.get("file_name"));
                attach.setAttachId((String) oneAttach.get("file_id"));
                attach.setRemedyId(applyId);
                Object obj = super.insertOneObject(attach);
                if (obj == null) {
                    return ExecuteCode.FAIL_CODE;
                }
            }
            return ExecuteCode.SUCCESS_CODE;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * ִ�и�������������ɾ���������븽����Ϣ
     * 
     * @param applyId
     *            String ����������
     * @param attachType
     *            String �������븽������
     * @return String ɾ���������븽����Ϣ����
     * @throws Exception
     */
    public String deleteAttachList(String applyId, String attachType) throws Exception {
        logger(RemedyApplyAttachDao.class);
        String condition = " and remedyId='" + applyId + "' and flag='" + attachType + "' ";
        try {
            boolean flag = super.deleteAll("RemedyApplyAttach", condition);
            if (flag) {
                return ExecuteCode.SUCCESS_CODE;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        return ExecuteCode.FAIL_CODE;
    }

}
