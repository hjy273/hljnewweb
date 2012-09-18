package com.cabletech.linepatrol.remedy.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.domain.RemedyApprove;

public class RemedyApproveDao extends RemedyBaseDao {

    /**
     * ִ�и��ݲ�ѯ������ȡ����������Ϣ�б�
     * 
     * @param condition
     *            String ��ѯ����
     * @return List ����������Ϣ�б�
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedyApproveDao.class);
        String sql = "select approve.id,approve.remedyid,approve.state,approve.assessor,approve.remark,approve.step_id, ";
        sql = sql + " to_char(approve.assessdate,'yyyy-mm-dd hh24:mi:ss') as assessdate, ";
        sql = sql
                + " decode(approve.step_id,null,'000',substr(approve.step_id,1,3)) as approve_state, ";
        sql = sql + " decode(approve.state,'0','����ͨ��','1','������ͨ��','') as approve_result, ";
        sql = sql + " u.username ";
        sql = sql
                + " from LINEPATROL_REMEDY_TRY_ITEM approve,userinfo u where approve.assessor=u.userid ";
        sql = sql + condition;
        sql = sql + " order by approve.id ";
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
     * ִ�б�������������Ϣ
     * 
     * @param applyId
     *            String ����������
     * @param approve
     *            RemedyApprove ����������Ϣ
     * @return String ���涯������
     * @throws Exception
     */
    public String saveApprove(String applyId, RemedyApprove approve) throws Exception {
        logger(RemedyApproveDao.class);
        String seqId = ora.getSeq("LINEPATROL_REMEDY_TRY_ITEM", "REMEDY_APPROVE", 20);
        approve.setId(seqId);
        approve.setApplyId(applyId);
        try {
            Object obj = super.insertOneObject(approve);
            if (obj != null) {
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
