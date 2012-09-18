package com.cabletech.linepatrol.remedy.dao;

import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyWorkflowConstant;
import com.cabletech.linepatrol.remedy.domain.RemedyApply;
import com.cabletech.linepatrol.remedy.domain.RemedyWorkflowInfo;
import com.cabletech.linepatrol.remedy.service.RemedyWorkflowOperate;

public class RemedyWorkflowInfoDao extends RemedyBaseDao {
	/**
	 * ִ�и��ݲ�ѯ������ȡ�������빤������Ϣ�б�
	 * 
	 * @param condition
	 *            String ��ѯ����
	 * @return List �������빤������Ϣ�б�
	 * @throws Exception
	 */
	public List queryList(String condition) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select t.* from LINEPATROL_REMEDY_WORKFLOW t ";
		sql = sql + " where 1=1 " + condition;
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
	 * ִ�б����������빤������Ϣ
	 * 
	 * @param apply
	 *            RemedyApply ��������
	 * @param currentUserId
	 *            String ��ǰ�����û����
	 * @param stepId
	 *            String �������빤����������
	 * @return RemedyWorkflowInfo ����Ĺ�������Ϣ
	 * @throws Exception
	 */
	public RemedyWorkflowInfo saveWorkflowInfo(RemedyApply apply,
			String currentUserId, String stepId) throws Exception {
		RemedyWorkflowInfo workflowInfo = new RemedyWorkflowInfo();
		String seqId = ora.getSeq("LINEPATROL_REMEDY_WORKFLOW",
				"remedy_workflow", 20);
		stepId = RemedyWorkflowOperate.nextAction(stepId, apply);
		workflowInfo.setId(seqId);
		workflowInfo.setRemedyId(apply.getId() + "");
		workflowInfo.setStepId(stepId);
		workflowInfo.setLastProcessMan(currentUserId);
		workflowInfo.setPrevState(apply.getPrevState());
		if (RemedyWorkflowConstant.INIT_STEP_ID.equals(stepId)) {
			workflowInfo.setNextProcessMan(currentUserId);
		} else {
			workflowInfo.setNextProcessMan(apply.getNextProcessMan());
		}
		workflowInfo.setState(apply.getState());
		Object obj = super.insertOneObject(workflowInfo);
		if (obj != null) {
			return workflowInfo;
		}
		return null;
	}
}
