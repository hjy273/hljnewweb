package com.cabletech.linepatrol.remedy.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyWorkflowConstant;
import com.cabletech.linepatrol.remedy.domain.RemedyApply;

public class RemedyApplyDao extends RemedyBaseDao {
    /**
     * 执行保存修缮申请信息
     * 
     * @param apply
     *            RemedyApply 修缮申请信息
     * @param actionType
     *            String 保存类型：{insert,update}
     * @return String 保存动作编码
     * @throws Exception
     */
    public String saveOneApply(RemedyApply apply, String actionType) throws Exception {
        logger(RemedyApplyDao.class);
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
     * 执行删除修缮申请信息
     * 
     * @param apply
     *            RemedyApply 修缮申请信息
     * @return String 删除动作编码
     * @throws Exception
     */
    public String deleteOneApply(RemedyApply apply) throws Exception {
        logger(RemedyApplyDao.class);
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
     * 执行更新修缮申请状态信息
     * 
     * @param applyId
     *            String 修缮申请编号
     * @param prevStep
     *            String 修缮申请前一步工作流步骤编号
     * @param currentStep
     *            String 修缮申请当前工作流步骤编号
     * @param nextStep
     *            String 修缮申请下一步工作流步骤编号
     * @return String 更新修缮申请状态信息编码
     * @throws Exception
     */
    public String updateApplyState(String applyId, String prevStep, String currentStep,
            String nextStep) {
        logger(RemedyApplyDao.class);
        return "";
    }

    /**
     * 执行判断是否存在相同的修缮申请信息
     * 
     * @param apply
     *            RemedyApply 修缮申请信息
     * @return boolean 是否存在相同的修缮申请信息
     * @throws Exception
     */
    public boolean judgeExistSameApply(RemedyApply apply) throws Exception {
        logger(RemedyBaseDao.class);
        String condition = " and remedy.projectname='" + apply.getProjectName()
                + "' and remedy.id<>" + apply.getId();
        List list = queryList(condition);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据修缮申请的日期和代维单位编号判断是否存在上月的材料盘点信息
     * 
     * @param apply
     *            RemedyApply 修缮申请信息
     * @return boolean 是否存在上月的材料盘点信息
     * @throws Exception
     */
    public boolean judgeExistMtUsed(RemedyApply apply) throws Exception {
        logger(RemedyBaseDao.class);
        String date = DateUtil.DateToString(apply.getRemedyDate());
        String sql = "select t.contractorid from LINEPATROL_MT_USED t,userinfo u ";
        sql = sql + " where t.creator=u.userid and u.deptid='" + apply.getContractorId() + "' ";
        sql = sql + " and t.createtime=add_months(last_day(to_date('" + date
                + "','yyyy-mm-dd'))+1,-2) ";
        try {
            logger.info("Execute sql:" + sql);
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            if (list != null && list.size() > 0) {
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        return false;
    }

    /**
     * 执行根据查询条件获取修缮申请信息列表
     * 
     * @param condition
     *            String 查询条件
     * @return List 修缮申请信息列表
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedyApplyDao.class);
        String sql = "select remedy.id,remedy.remedycode,remedy.projectname,to_char(remedy.remedydate,'yyyy-mm-dd') as remedydate,to_char(remedy.totalfee) as totalfee,remedy.state,status.status_name, ";
        sql = sql + " decode(balance.totalfee,null,'0',balance.totalfee) as balance_fee, ";
        sql = sql + " decode(remedy.state,'" + RemedyWorkflowConstant.NOT_SUBMITED_STATE
                + "','1','" + RemedyWorkflowConstant.NOT_APPROVED_STATE_LEVEL_ONE
                + "','1','0') as allow_edited, ";
        sql = sql + " decode(remedy.state,'" + RemedyWorkflowConstant.NOT_SUBMITED_STATE
                + "','1','0') as allow_deleted, ";
        sql = sql + " decode(remedy.state,'" + RemedyWorkflowConstant.SUBMITED_STATE + "','1','"
                + RemedyWorkflowConstant.NOT_APPROVED_STATE_LEVEL_ONE
                + "','0',decode(substr(remedy.state,1,2),'" + RemedyWorkflowConstant.APPROVED_STATE
                + "','1','0')) as allow_approved, ";
        sql = sql + " decode(remedy.state,'" + RemedyWorkflowConstant.WAIT_CHECKED_STATE
                + "','1','0') as allow_checked, ";
        sql = sql + " decode(remedy.state,'" + RemedyWorkflowConstant.WAIT_SQUARED_STATE
                + "','1','0') as allow_squared ";
        sql = sql
                + " from LINEPATROL_REMEDY remedy,LINEPATROL_REMEDY_BALANCE balance,LINEPATROL_REMEDY_STATUS status,contractorinfo c,region r ";
        sql = sql
                + " where remedy.contractorid=c.contractorid and remedy.id=balance.remedyid(+) and remedy.regionid=r.regionid and remedy.state=status.status_id ";
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
