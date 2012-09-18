package com.cabletech.linepatrol.remedy.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.domain.RemedyApprove;

public class RemedyApproveDao extends RemedyBaseDao {

    /**
     * 执行根据查询条件获取修缮审批信息列表
     * 
     * @param condition
     *            String 查询条件
     * @return List 修缮审批信息列表
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedyApproveDao.class);
        String sql = "select approve.id,approve.remedyid,approve.state,approve.assessor,approve.remark,approve.step_id, ";
        sql = sql + " to_char(approve.assessdate,'yyyy-mm-dd hh24:mi:ss') as assessdate, ";
        sql = sql
                + " decode(approve.step_id,null,'000',substr(approve.step_id,1,3)) as approve_state, ";
        sql = sql + " decode(approve.state,'0','审批通过','1','审批不通过','') as approve_result, ";
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
     * 执行保存修缮审批信息
     * 
     * @param applyId
     *            String 修缮申请编号
     * @param approve
     *            RemedyApprove 修缮审批信息
     * @return String 保存动作编码
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
