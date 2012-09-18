package com.cabletech.linepatrol.remedy.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.domain.RemedyCheck;

public class RemedyCheckDao extends RemedyBaseDao {
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
        logger(RemedyCheckDao.class);
        String sql = "select lcheck.id,lcheck.remedyid,lcheck.state,lcheck.acceptor,lcheck.remark,to_char(lcheck.acceptdate,'yyyy-mm-dd hh24:mi:ss') as acceptdate, ";
        sql = sql + " u.username ";
        sql = sql
                + " from LINEPATROL_REMEDY_ACCEPT lcheck,userinfo u where lcheck.acceptor=u.userid ";
        sql = sql + condition;
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
    public String saveCheck(String applyId, RemedyCheck check) throws Exception {
        logger(RemedyCheckDao.class);
        String seqId = ora.getSeq("LINEPATROL_REMEDY_ACCEPT", "REMEDY_CHECK", 20);
        check.setId(seqId);
        check.setApplyId(applyId);
        try {
            Object obj = super.insertOneObject(check);
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
