package com.cabletech.linepatrol.remedy.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.domain.RemedySquare;

public class RemedySquareDao extends RemedyBaseDao {
    /**
     * 执行根据查询条件获取修缮结算信息列表
     * 
     * @param condition
     *            String 查询条件
     * @return List 修缮结算信息列表
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedySquareDao.class);
        String sql = "select square.id,square.remedyid,square.state,square.acceptor,square.remark,to_char(square.acceptdate,'yyyy-mm-dd hh24:mi:ss') as acceptdate, ";
        sql = sql + " u.username ";
        sql = sql
                + " from LINEPATROL_REMEDY_STRIKE square,userinfo u where square.acceptor=u.userid ";
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
     * 执行保存修缮结算信息
     * 
     * @param applyId
     *            String 修缮结算编号
     * @param approve
     *            RemedyApprove 修缮审批信息
     * @return String 保存动作编码
     * @throws Exception
     */
    public String saveSquare(String applyId, RemedySquare square) throws Exception {
        logger(RemedySquareDao.class);
        String seqId = ora.getSeq("LINEPATROL_REMEDY_STRIKE", "REMEDY_SQUARE", 20);
        square.setId(seqId);
        square.setApplyId(applyId);
        try {
            Object obj = super.insertOneObject(square);
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
