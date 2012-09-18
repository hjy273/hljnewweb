package com.cabletech.linepatrol.remedy.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyWorkflowConstant;
import com.cabletech.linepatrol.remedy.domain.RemedyBalance;

public class RemedyBalanceDao extends RemedyBaseDao {
    /**
     * 执行保存修缮结算信息
     * 
     * @param apply
     *            RemedyBalance 修缮结算信息
     * @param actionType
     *            String 保存类型：{insert,update}
     * @return String 保存动作编码
     * @throws Exception
     */
    public String saveOneBalance(RemedyBalance apply, String actionType) throws Exception {
        logger(RemedyBalanceDao.class);
        try {
            if (ExecuteCode.INSERT_ACTION_TYPE.equals(actionType)) {
                Object obj = super.insertOneObject(apply);
                if (obj != null) {
                    return ExecuteCode.SUCCESS_CODE;
                } else {
                    return ExecuteCode.FAIL_CODE;
                }
            }
            if (ExecuteCode.UPDATE_ACTION_TYPE.equals(actionType)) {
                Object obj = super.updateOneObject(apply);
                if (obj != null) {
                    return ExecuteCode.SUCCESS_CODE;
                } else {
                    return ExecuteCode.FAIL_CODE;
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        return ExecuteCode.NO_OP_CODE;
    }

    /**
     * 
     * 执行删除修缮结算信息
     * 
     * @param apply
     *            RemedyBalance 修缮结算信息
     * @return String 删除动作编码
     * @throws Exception
     */
    public String deleteOneBalance(RemedyBalance apply) throws Exception {
        logger(RemedyBalanceDao.class);
        try {
            Object obj = super.deleteOneObject(apply);
            if (obj != null) {
                return ExecuteCode.SUCCESS_CODE;
            } else {
                return ExecuteCode.FAIL_CODE;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * 执行判断是否存在相同的修缮结算信息
     * 
     * @param apply
     *            RemedyBalance 修缮申请信息
     * @return boolean 是否存在相同的修缮申请信息
     * @throws Exception
     */
    public boolean judgeExistBalance(RemedyBalance apply) throws Exception {
        logger(RemedyBaseDao.class);
        String condition = " and balance.remedyid='" + apply.getRemedyId() + "'";
        List list = queryList(condition);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 执行根据查询条件获取修缮结算信息列表
     * 
     * @param condition
     *            String 查询条件
     * @return List 修缮申请信息列表
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedyBalanceDao.class);
        String sql = "select remedy.id,remedy.remedycode,remedy.projectname, ";
        sql = sql + " to_char(remedy.remedydate,'yyyy-mm-dd') as remedydate, ";
        sql = sql + " to_char(balance.totalfee) as totalfee,remedy.state,status.status_name, ";
        sql = sql + " decode(remedy.state,'" + RemedyWorkflowConstant.WAIT_SQUARED_STATE
                + "','1','0') as allow_squared ";
        sql = sql + " from LINEPATROL_REMEDY remedy,LINEPATROL_REMEDY_BALANCE balance, ";
        sql = sql + " LINEPATROL_REMEDY_STATUS status,contractorinfo c,region r ";
        sql = sql + " where remedy.contractorid=c.contractorid and remedy.regionid=r.regionid ";
        sql = sql + " and remedy.id=balance.remedyid and remedy.state=status.status_id ";
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
}
