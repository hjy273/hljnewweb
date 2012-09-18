package com.cabletech.linepatrol.remedy.dao;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.domain.RemedyWorkflow;

public class RemedyWorkflowDao extends RemedyBaseDao {
	/**
	 * 执行根据查询条件获取修缮申请工作流步骤信息列表
	 * 
	 * @param condition
	 *            String 查询条件
	 * @return List 修缮申请工作流步骤信息列表
	 * @throws Exception
	 */
	public List queryList(String condition) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from LINEPATROL_REMEDY_STEPS t where 1=1 "
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
	 * 执行保存修缮申请工作流步骤信息
	 * 
	 * @param applyId
	 *            String 修缮申请编号
	 * @param prevStepId
	 *            String 修缮申请工作流前一个步骤编号
	 * @param currentStepId
	 *            String 修缮申请工作流下一个步骤编号
	 * @param currentStepSeq String 修缮申请工作流下一个步骤序号
	 * @return String 保存修缮申请工作流步骤信息编码
	 * @throws Exception
	 */
	public String saveWorkflow(String applyId, String prevStepId,
			String currentStepId, String currentStepSeq) throws Exception {
		RemedyWorkflow workflow = new RemedyWorkflow();
		String seqId = ora
				.getSeq("LINEPATROL_REMEDY_STEPS", "remedy_steps", 30);
		workflow.setId(seqId);
		workflow.setRemedyId(applyId);
		workflow.setPrevStepId(prevStepId);
		workflow.setCurrentStepSeq(currentStepSeq);
		if (currentStepId != null && !"".equals(currentStepId)) {
			workflow.setCurrentStepId(currentStepId);
		} else {
			String condition = " and remedyid='" + applyId + "' ";
			condition = condition + " and step_id='" + prevStepId + "' ";
			condition = condition + " order by id desc ";
			List list = queryList(condition);
			if (list != null && list.size() > 0) {
				DynaBean bean = (DynaBean) list.get(0);
				currentStepId = (String) bean.get("prev_step_id");
				workflow.setCurrentStepId(currentStepId);
			}
		}
		Object obj = super.insertOneObject(workflow);
		if (obj != null) {
			return ExecuteCode.SUCCESS_CODE;
		}
		return ExecuteCode.FAIL_CODE;
	}
}
