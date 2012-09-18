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
	 * ִ����������������Ϣ
	 * 
	 * @param applyId
	 * @param request
	 *            HttpServletRequest �û���������
	 * @param form
	 *            ActionForm �û������
	 * @return String ������������
	 * @throws Exception
	 */
	public String approveApply(UserInfo userInfo, ApproveInfo approve, String applyId, String approver) throws Exception {
		approve.setObjectType(RemedyConstant.LP_REMEDY_APPROVE);
		if (!applyDao.judgeExistApply(applyId)) {
			return ExecuteCode.NO_DATA_ERROR;
		}
		// ���������Ϣ
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
				logger.info("�����������ͨ������ʼ........................");
				
				// �޸���������ʹ�ò��Ͽ����Ϣ
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
				
				//�޸Ļ��ղ���ʹ�ò��Ͽ���Ϣ
				List<ReturnMaterial> materialRecycleList = returnMaterialDAO.getReturnMaterials(applyId,
						RemedyConstant.MATERIAL_USE_TYPE);
				for(ReturnMaterial returnMaterial : materialRecycleList){
					returnMaterialDAO.writeMaterialNotUseNumber(returnMaterial, userInfo.getDeptID());
				}
				
				//������ʷ
				workflowBo.saveProcessHistory(userInfo, apply, task, "", "start", "�������ͨ��", "start");
				
				workflowBo.setVariables(task, "assignee", userInfo.getUserID());
				workflowBo.setVariables(task, "transition", "evaluate");
				workflowBo.completeTask(task.getId(), "passed");
				
				//������������
				task = workflowBo.getHandleTaskForId(userInfo.getUserID(), applyId);
				if (task != null && ProjectRemedyWorkflowBO.EVALUATE_TASK.equals(task.getName())) {
					workflowBo.completeTask(task.getId(), "end");
				}
				
				//����״̬
				apply.setState(RemedyConstant.EVALUATE);
				applyDao.save(apply);

				logger.info("�����������ͨ���������........................");
			} else if (RemedyConstant.IS_TRANSFER.equals(approve
					.getApproveResult())) {
				approverDao.saveApproverOrReader(approver, apply.getId(),
						CommonConstant.APPROVE_TRANSFER_MAN,
						RemedyConstant.LP_REMEDY);

				logger.info("��������ת����ʼ........................");
				logger.info("approve transfer.................");
				
				//������ʷ
				workflowBo.saveProcessHistory(userInfo, apply, task, approver, "start", "����ת��", "start");
				
				workflowBo.setVariables(task, "assignee", approver+","+readers);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				logger.info("��������ת�������........................");
			} else {
				logger.info("����������˲�ͨ������ʼ........................");
				logger.info("approve not passed.................");
				
				//����״̬
				apply.setState(RemedyConstant.NEEDCOMPLETEAPPLY);
				applyDao.save(apply);
				
				//������ʷ
				workflowBo.saveProcessHistory(userInfo, apply, task, apply.getCreator(), "start", "������˲�ͨ��", "start");
				
				workflowBo.setVariables(task, "assignee", apply.getCreator());
				workflowBo.completeTask(task.getId(), "not_passed");
				logger.info("����������˲�ͨ���������........................");
			}
		}
		// ���ö��ŷ��͵�������Ŀ���ƺͷ���Ŀ���˱��
		if (RemedyConstant.IS_TRANSFER.equals(approve.getApproveResult())) {
			String[] userId = approver.split(",");
			String msg = "����һ������Ϊ��" + apply.getProjectName() + "���Ĺ�������ת��������ȴ�������ˣ�";
			sendMsg(userInfo, apply, userId, msg);
		}
		else if (RemedyConstant.NOT_PASSED.equals(approve.getApproveResult())) {
			String[] userId = apply.getCreator().split(",");
			String msg = "����һ������Ϊ��" + apply.getProjectName() + "���Ĺ���������˲�ͨ�������޸ģ�";
			sendMsg(userInfo, apply, userId, msg);
		}else{
			String[] userId = apply.getCreator().split(",");
			String msg = "����һ������Ϊ��" + apply.getProjectName() + "���Ĺ������������ͨ����";
			sendMsg(userInfo, apply, userId, msg);
		}
		return ExecuteCode.SUCCESS_CODE;
	}

	/**
	 * ִ�и��������������ж��Ƿ����ִ�������������������
	 * 
	 * @param applyId
	 *            String ����������
	 * @return boolean �жϽ��
	 */
	public boolean judge(String applyId) {
		// TODO Auto-generated method stub
		boolean flag = false;
		return flag;
	}

	/**
	 * ���ݵ�ǰ�û��������ɹ���Ķ���
	 * 
	 * @param user
	 *            UserInfo ��ǰ�û�
	 * @param userId
	 */
	public void sendMsg(UserInfo user, ProjectRemedyApply apply, String[] userId, String msg) {
		String nextProcessManId = "";
		String simId = "";
		logger.info("���ɹ���Ķ������ݣ�" + msg);
		logger.info("���ŷ��͵�Ŀ���ֻ��ţ�" + simId);
		String sim = "";
		String mobiles = "";
		for (int i = 0; i < userId.length; i++) {
			logger.info("���ŷ��͵�Ŀ���ˣ�" + userId[i]);
			sim = applyDao.getSendPhone(userId[i]);
			mobiles = mobiles + sim + ",";
			super.sendMessage(msg, mobiles);
//			if (sim != null && !sim.equals("")) {
//				smSendProxy.simpleSend(sim, msg, null, null, true);
//			}
		}
		// ������ż�¼
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
