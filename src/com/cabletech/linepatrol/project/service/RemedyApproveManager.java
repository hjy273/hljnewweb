package com.cabletech.linepatrol.project.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.ReturnMaterialDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.dao.UseMaterialDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.ReturnMaterial;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.commons.module.UseMaterial;
import com.cabletech.linepatrol.material.dao.MaterialInfoDao;
import com.cabletech.linepatrol.material.dao.MaterialStockDao;
import com.cabletech.linepatrol.project.constant.ExecuteCode;
import com.cabletech.linepatrol.project.constant.RemedyConstant;
import com.cabletech.linepatrol.project.dao.RemedyApplyDao;
import com.cabletech.linepatrol.project.domain.ProjectRemedyApply;
import com.cabletech.linepatrol.project.workflow.ProjectRemedyWorkflowBO;

@Service
@Transactional
public class RemedyApproveManager extends EntityManager<ApproveInfo, String> {
	@Resource(name = "remedyApplyDao")
	private RemedyApplyDao applyDao;
	@Resource(name = "approveDAO")
	private ApproveDAO approveDao;
	@Resource(name = "materialInfoDao")
	private MaterialInfoDao materialDao;
	@Resource(name = "materialStockDao")
	private MaterialStockDao materialStorageDao;
	@Resource(name = "useMaterialDAO")
	private UseMaterialDAO useMaterialDao;
	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;
	@Resource(name = "uploadFileService")
	private UploadFileService uploadFile;
	@Resource(name = "projectRemedyWorkflowBO")
	private ProjectRemedyWorkflowBO workflowBo;
	@Resource(name = "returnMaterialDAO")
	private ReturnMaterialDAO returnMaterialDAO;
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDao;
	
	private ConditionGenerate conditionGenerate;

	/**
	 * 执行审批修缮申请信息
	 * 
	 * @param applyId
	 * @param request
	 *            HttpServletRequest 用户输入请求
	 * @param form
	 *            ActionForm 用户输入表单
	 * @return String 审批动作编码
	 * @throws Exception
	 */
	public String approveApply(UserInfo userInfo, ApproveInfo approve, String applyId, String approver) throws Exception {
		approve.setObjectType(RemedyConstant.LP_REMEDY_APPROVE);
		if (!applyDao.judgeExistApply(applyId)) {
			return ExecuteCode.NO_DATA_ERROR;
		}
		// 保存审核信息
		approveDao.saveApproveInfo(approve);
		
		String readers=applyDao.getReaderByCondition(approver, userInfo.getUserID(), applyId, RemedyConstant.LP_REMEDY);
		
		ProjectRemedyApply apply = (ProjectRemedyApply) applyDao
				.viewOneApply(applyId);
		Task task = workflowBo
				.getHandleTaskForId(userInfo.getUserID(), applyId);
		if (task != null
				&& ProjectRemedyWorkflowBO.APPLY_APPROVE_TASK.equals(task
						.getName())) {
			if (RemedyConstant.PASSED.equals(approve.getApproveResult())) {
				logger.info("工程申请审核通过处理开始........................");
				
				// 修改修缮申请使用材料库存信息
//				List materialList = useMaterialDao.getUseMaterials(applyId,
//						RemedyConstant.MATERIAL_USE_TYPE);
//				UseMaterial applyMaterial;
//				for (int i = 0; materialList != null && i < materialList.size(); i++) {
//					applyMaterial = (UseMaterial) materialList.get(i);
//					boolean flag = useMaterialDao.writeMaterialUseNumber(
//							applyMaterial, userInfo.getDeptID());
//					if (!flag) {
//						throw new ServiceException("material not enough storage............");
//					}
//				}
				
				//修改回收材料使用材料库信息
				List<ReturnMaterial> materialRecycleList = returnMaterialDAO.getReturnMaterials(applyId,
						RemedyConstant.MATERIAL_USE_TYPE);
				for(ReturnMaterial returnMaterial : materialRecycleList){
					returnMaterialDAO.writeMaterialNotUseNumber(returnMaterial, userInfo.getDeptID());
				}
				
				//流程历史
				workflowBo.saveProcessHistory(userInfo, apply, task, "", "start", "工程审核通过", "start");
				
				workflowBo.setVariables(task, "assignee", userInfo.getUserID());
				workflowBo.setVariables(task, "transition", "evaluate");
				workflowBo.completeTask(task.getId(), "passed");
				
				//跳过考核评分
				task = workflowBo.getHandleTaskForId(userInfo.getUserID(), applyId);
				if (task != null && ProjectRemedyWorkflowBO.EVALUATE_TASK.equals(task.getName())) {
					workflowBo.completeTask(task.getId(), "end");
				}
				
				//更新状态
				apply.setState(RemedyConstant.EVALUATE);
				applyDao.save(apply);

				logger.info("工程申请审核通过处理结束........................");
			} else if (RemedyConstant.IS_TRANSFER.equals(approve
					.getApproveResult())) {
				approverDao.saveApproverOrReader(approver, apply.getId(),
						CommonConstant.APPROVE_TRANSFER_MAN,
						RemedyConstant.LP_REMEDY);

				logger.info("工程申请转审处理开始........................");
				logger.info("approve transfer.................");
				
				//流程历史
				workflowBo.saveProcessHistory(userInfo, apply, task, approver, "start", "工程转审", "start");
				
				workflowBo.setVariables(task, "assignee", approver+","+readers);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				logger.info("工程申请转审处理结束........................");
			} else {
				logger.info("工程申请审核不通过处理开始........................");
				logger.info("approve not passed.................");
				
				//更新状态
				apply.setState(RemedyConstant.NEEDCOMPLETEAPPLY);
				applyDao.save(apply);
				
				//流程历史
				workflowBo.saveProcessHistory(userInfo, apply, task, apply.getCreator(), "start", "工程审核不通过", "start");
				
				workflowBo.setVariables(task, "assignee", apply.getCreator());
				workflowBo.completeTask(task.getId(), "not_passed");
				logger.info("工程申请审核不通过处理结束........................");
			}
		}
		// 设置短信发送的修缮项目名称和发送目标人编号
		if (RemedyConstant.IS_TRANSFER.equals(approve.getApproveResult())) {
			String[] userId = approver.split(",");
			String msg = "您有一个名称为“" + apply.getProjectName() + "”的工程申请转审给您，等待您的审核！";
			sendMsg(userInfo, apply, userId, msg);
		}
		else if (RemedyConstant.NOT_PASSED.equals(approve.getApproveResult())) {
			String[] userId = apply.getCreator().split(",");
			String msg = "您有一个名称为“" + apply.getProjectName() + "”的工程申请审核不通过，请修改！";
			sendMsg(userInfo, apply, userId, msg);
		}else{
			String[] userId = apply.getCreator().split(",");
			String msg = "您有一个名称为“" + apply.getProjectName() + "”的工程申请审核已通过。";
			sendMsg(userInfo, apply, userId, msg);
		}
		return ExecuteCode.SUCCESS_CODE;
	}

	/**
	 * 执行根据修缮申请编号判断是否可以执行修缮申请的审批操作
	 * 
	 * @param applyId
	 *            String 修缮申请编号
	 * @return boolean 判断结果
	 */
	public boolean judge(String applyId) {
		// TODO Auto-generated method stub
		boolean flag = false;
		return flag;
	}

	/**
	 * 根据当前用户发送修缮管理的短信
	 * 
	 * @param user
	 *            UserInfo 当前用户
	 * @param userId
	 */
	public void sendMsg(UserInfo user, ProjectRemedyApply apply, String[] userId, String msg) {
		String nextProcessManId = "";
		String simId = "";
		logger.info("修缮管理的短信内容：" + msg);
		logger.info("短信发送的目标手机号：" + simId);
		String sim = "";
		String mobiles = "";
		for (int i = 0; i < userId.length; i++) {
			logger.info("短信发送的目标人：" + userId[i]);
			sim = applyDao.getSendPhone(userId[i]);
			mobiles = mobiles + sim + ",";
			super.sendMessage(msg, mobiles);
//			if (sim != null && !sim.equals("")) {
//				smSendProxy.simpleSend(sim, msg, null, null, true);
//			}
		}
		// 保存短信记录
		SMHistory history = new SMHistory();
		history.setSimIds(mobiles);
		history.setSendContent(msg);
		history.setSendTime(new Date());
		// history.setSendState(sendState);
		history.setSmType(RemedyConstant.LP_REMEDY);
		history.setObjectId(apply.getId());
		history.setBusinessModule("sendtask");
		historyDAO.save(history);
	}

	@Override
	protected HibernateDao<ApproveInfo, String> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
