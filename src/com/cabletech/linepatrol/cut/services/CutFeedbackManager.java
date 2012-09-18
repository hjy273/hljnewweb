package com.cabletech.linepatrol.cut.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.dao.UserInfoDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.ReturnMaterialDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.dao.UseMaterialDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.ReturnMaterial;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.commons.module.UseMaterial;
import com.cabletech.linepatrol.cut.beans.CutFeedbackBean;
import com.cabletech.linepatrol.cut.dao.CutDao;
import com.cabletech.linepatrol.cut.dao.CutFeedbackDao;
import com.cabletech.linepatrol.cut.dao.CutHopRelDao;
import com.cabletech.linepatrol.cut.module.Cut;
import com.cabletech.linepatrol.cut.module.CutConstant;
import com.cabletech.linepatrol.cut.module.CutFeedback;
import com.cabletech.linepatrol.cut.workflow.CutWorkflowBO;

@Service
@Transactional
public class CutFeedbackManager extends EntityManager<CutFeedback, String> {

	@Autowired
	private CutWorkflowBO workflowBo;

	@Resource(name = "cutDao")
	private CutDao cutDao;

	@Resource(name = "cutFeedbackDao")
	private CutFeedbackDao cutFeedbackDao;

	@Resource(name = "useMaterialDAO")
	private UseMaterialDAO useMaterialDAO;

	@Resource(name = "cutHopRelDao")
	private CutHopRelDao cutHopRelDao;
	
	@Resource(name = "returnMaterialDAO")
	private ReturnMaterialDAO returnMaterialDAO;
	
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	
	@Resource(name = "uploadFileService")
	private UploadFileService uploadFile;
	
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDAO;

	@Resource(name = "approveDAO")
	private ApproveDAO approveDao;

	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;
	
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;

	@Override
	protected HibernateDao<CutFeedback, String> getEntityDao() {
		return cutFeedbackDao;
	}

	/**
	 * ������뷴����ļ�����Ϣ
	 * 
	 * @param cutId
	 * @return
	 * @throws ServiceException
	 */
	public Map addCutFeedbackForm(String cutId, String regionId) throws ServiceException {
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		List<UserInfo> mobiles = cutDao.getMobiles(regionId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		Map map = new HashMap();
		map.put("cut", cut);
		map.put("sublineIds", sublineIds);
		map.put("mobiles", mobiles);
		map.put("cutFeedback", cutFeedback);
		map.put("approve_info_list", approve_info_list);
		return map;
	}

	/**
	 * ���������뷴����Ϣ
	 * 
	 * @param cutFeedback
	 * @throws ServiceException
	 */
	public void addCutFeedback(CutFeedbackBean cutFeedbackBean,
			UserInfo userInfo,List<FileItem> files)
			throws ServiceException {
		String approvers = cutFeedbackBean.getApproveId();
		String reader = cutFeedbackBean.getReader();
		String mobiles = cutFeedbackBean.getMobiles();
		String feedbackType = cutFeedbackBean.getFeedbackType();
		String readerPhones[] = cutFeedbackBean.getReaderPhones();
		cutFeedbackBean.setObjectId(cutFeedbackBean.getCutId());
		
		CutFeedback cutFeedbackSave = new CutFeedback();
		
		if("1".equals(feedbackType)){
			CutFeedback cutFeedback = new CutFeedback();
			cutFeedback.setCancelCause(cutFeedbackBean.getCancelCause());
			cutFeedback.setFeedbackType(cutFeedbackBean.getFeedbackType());
			cutFeedback.setCutId(cutFeedbackBean.getCutId());
			cutFeedback.setCreator(userInfo.getUserID());
			cutFeedback.setCreateTime(new Date());
			cutFeedbackSave = cutFeedbackDao.saveCutFeedback(cutFeedback);
		}else{
			cutFeedbackSave = saveCutFeedback(cutFeedbackBean, userInfo);
		}
		cutDao.updateStateById(cutFeedbackSave.getCutId(), Cut.FEEDBACK_APPROVE);

		// ���渽��
		uploadFile.saveFiles(files, ModuleCatalog.LINECUT, 
				userInfo.getRegionName(), cutFeedbackSave.getId(), CutConstant.LP_CUT_FEEDBACK, userInfo.getUserID());

		// �����������Ϣ
		replyApproverDAO.saveApproverOrReader(approvers, cutFeedbackSave.getId(), CutConstant.APPROVE_MAN, CutConstant.LP_CUT_FEEDBACK);
		
		// ���泭������Ϣ
		replyApproverDAO.saveApproverOrReader(reader, cutFeedbackSave.getId(), CutConstant.APPROVE_READ, CutConstant.LP_CUT_FEEDBACK);
		
		// �������
		List<UseMaterial> materials = UseMaterial.packageList("cut",
				cutFeedbackBean);
		useMaterialDAO.saveList(materials);
		List<ReturnMaterial> returnMaterials = ReturnMaterial.packageList(
				cutFeedbackBean, "cut");
		returnMaterialDAO.saveList(returnMaterials);
		
		
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
				cutFeedbackSave.getCutId());
		if (task != null && CutWorkflowBO.WORK_TASK.equals(task.getName())) {
			System.out.println("��ӷ�������У�" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers+","+reader);
			workflowBo.completeTask(task.getId(), "work_approve");
			System.out.println("��ӷ����Ѿ��ύ��");
			
			//����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("��ӷ���");//������̴�����˵��
			processHistoryBean.setTaskOutCome("work_approve");//��ӹ�����������Ϣ
			processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
			processHistoryBean.setNextOperateUserId(approvers+","+reader);//�����һ�������˵ı��
			processHistoryBean.setObjectId(cutFeedbackSave.getCutId());//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		
		//���Ͷ���
		String content = null;
		if("0".equals(feedbackType)){
			content = "����·��ӡ�����һ������Ϊ\"" + cutDao.getCutNameById(cutFeedbackSave.getCutId())+"\"��ӷ������ȴ�����������";
		}else{
			content = "����·��ӡ�����һ������Ϊ\"" + cutDao.getCutNameById(cutFeedbackSave.getCutId())+"\"ȡ��������뵥�ȴ�����������";
		}
		sendMessage(content, mobiles);
		
		//������ż�¼
		saveMessage(content, mobiles, cutFeedbackSave.getId(), CutConstant.LP_CUT_FEEDBACK, ModuleCatalog.LINECUT);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				if("0".equals(feedbackType)){
					content = "����·��ӡ�����һ������Ϊ\"" + cutDao.getCutNameById(cutFeedbackSave.getCutId())+"\"��ӷ������ȴ����Ĳ��ġ�";
				}else{
					content = "����·��ӡ�����һ������Ϊ\"" + cutDao.getCutNameById(cutFeedbackSave.getCutId())+"\"ȡ��������뵥�ȴ����Ĳ��ġ�";
				}
				// ������ż�¼
				saveMessage(content, readerPhones[i], cutFeedbackSave.getId(), CutConstant.LP_CUT_FEEDBACK,
						ModuleCatalog.LINECUT);
				sendMessage(content, readerPhones[i]);
			}
		}
		
	}
	
	/**
	 * ��ӷ�����ʱ����
	 * @param cutFeedbackBean
	 * @param userInfo
	 * @param files
	 * @throws ServiceException
	 */
	public void tempSaveCutFeedback(CutFeedbackBean cutFeedbackBean,UserInfo userInfo,List<FileItem> files)
			throws ServiceException {
		String approvers = cutFeedbackBean.getApproveId();
		String reader = cutFeedbackBean.getReader();
		cutFeedbackBean.setObjectId(cutFeedbackBean.getCutId());
		String cutId = cutFeedbackBean.getCutId();
		
		CutFeedback cutFeedbackSave = new CutFeedback();
		
		cutFeedbackSave = saveCutFeedback(cutFeedbackBean, userInfo);
		//cutDao.updateStateById(cutFeedbackSave.getCutId(), Cut.FEEDBACKAPPROVE);

		// ���渽��
		uploadFile.saveFiles(files, ModuleCatalog.LINECUT, 
				userInfo.getRegionName(), cutFeedbackSave.getId(), CutConstant.LP_CUT_FEEDBACK, userInfo.getUserID());

		// �����������Ϣ
		replyApproverDAO.saveApproverOrReader(approvers, cutFeedbackSave.getId(), CutConstant.APPROVE_MAN, CutConstant.LP_CUT_FEEDBACK);
		
		// ���泭������Ϣ
		replyApproverDAO.saveApproverOrReader(reader, cutFeedbackSave.getId(), CutConstant.APPROVE_READ, CutConstant.LP_CUT_FEEDBACK);
		
		//ɾ������
		useMaterialDAO.deleteList(cutId,"cut");
		returnMaterialDAO.deleteList(cutId, "cut");
		// �������
		List<UseMaterial> materials = UseMaterial.packageList("cut",
				cutFeedbackBean);
		useMaterialDAO.saveList(materials);
		List<ReturnMaterial> returnMaterials = ReturnMaterial.packageList(
				cutFeedbackBean, "cut");
		returnMaterialDAO.saveList(returnMaterials);
	}
	
	/**
	 * �����ӷ���ʵ��
	 * @param cutFeedbackBean
	 * @return
	 */
	public CutFeedback saveCutFeedback(CutFeedbackBean cutFeedbackBean, UserInfo userInfo){
		CutFeedback cutFeedback = new CutFeedback();
		try {
			BeanUtil.objectCopy(cutFeedbackBean, cutFeedback);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CutFeedbackBeanת��ΪCutFeedback����������Ϣ��" + e.getMessage());
			throw new ServiceException(e);
		}
		cutFeedback.setFeedbackType("0");
		cutFeedback.setCreator(userInfo.getUserID());
		cutFeedback.setCreateTime(new Date());
		return cutFeedbackDao.saveCutFeedback(cutFeedback);
	}

	/**
	 * ��˺��޸Ĳ��Ͽ��
	 * 
	 * @param materials
	 * @param conid
	 */
	public void updateMaterialStock(List<UseMaterial> materials,
			String contractorId) {
		try {
			if (materials != null && materials.size() > 0) {
				for (int i = 0; i < materials.size(); i++) {
					UseMaterial applyMaterial = materials.get(i);
					useMaterialDAO.writeMaterialUseNumber(applyMaterial,
							contractorId);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.getStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * ��˺��޸Ļ��ղ��Ͽ��
	 * @param materials
	 * @param conid
	 */
	public void updateReturnMaterialStock(List<ReturnMaterial>  materials,String contractorId){
		try {
			if(materials!=null && materials.size()>0){
				for(int i =0;i<materials.size();i++){
					ReturnMaterial applyMaterial = materials.get(i);
					returnMaterialDAO.writeMaterialNotUseNumber(applyMaterial, contractorId);
				} 
			}
		}
		catch (Exception e) {
			logger.error(e);
			e.getStackTrace();
		}
	}

	/**
	 * �༭��ӷ���ǰ������Ϣ
	 * 
	 * @param cutId
	 * @return
	 * @throws ServiceException
	 */
	public Map editCutFeedbackForm(String cutId, String deptId, String regionId) throws ServiceException {
		Map map = new HashMap();
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		List mobiles = cutDao.getMobiles(regionId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		map.put("cut", cut);
		map.put("approve_info_list", approve_info_list);
		map.put("cutFeedback", cutFeedback);
		map.put("mobiles", mobiles);
		map.put("sublineIds", sublineIds);
		return map;
	}

	/**
	 * �����޸ĸ�ӷ�����Ϣ
	 * 
	 * @param cutFeedback
	 * @throws ServiceException
	 */
	public void editCutFeedback(CutFeedbackBean cutFeedbackBean,
			UserInfo userInfo, List<FileItem> files)
			throws ServiceException {
		String cutId = cutFeedbackBean.getCutId();
		String feedbackId = cutFeedbackBean.getId();
		String approvers = cutFeedbackBean.getApproveId();
		String reader = cutFeedbackBean.getReader();
		String mobiles = cutFeedbackBean.getMobiles();
		String[] readerPhones = cutFeedbackBean.getReaderPhones();
		
		saveCutFeedback(cutFeedbackBean, userInfo);
		cutDao.updateStateById(cutId, Cut.FEEDBACK_APPROVE);
		
		// �������
		cutFeedbackBean.setObjectId(cutFeedbackBean.getCutId());
		useMaterialDAO.deleteList(cutId,"cut");
		List<UseMaterial> materials = UseMaterial.packageList("cut",
				cutFeedbackBean);
		useMaterialDAO.saveList(materials);
		returnMaterialDAO.deleteList(cutId, "cut");
		List<ReturnMaterial> returnMaterials = ReturnMaterial.packageList(
				cutFeedbackBean, "cut");
		returnMaterialDAO.saveList(returnMaterials);
		
		// ���渽��
		uploadFile.saveFiles(files, ModuleCatalog.LINECUT, 
				userInfo.getRegionName(), cutFeedbackBean.getId(), 
				CutConstant.LP_CUT_FEEDBACK, userInfo.getUserID());

		replyApproverDAO.deleteList(feedbackId, CutConstant.LP_CUT_FEEDBACK);
		// �����������Ϣ
		replyApproverDAO.saveApproverOrReader(approvers, feedbackId, CutConstant.APPROVE_MAN, CutConstant.LP_CUT_FEEDBACK);
		
		// ���泭������Ϣ
		replyApproverDAO.saveApproverOrReader(reader, feedbackId, CutConstant.APPROVE_READ, CutConstant.LP_CUT_FEEDBACK);

		
		
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
				cutId);
		if (task != null && CutWorkflowBO.WORK_TASK.equals(task.getName())) {
			System.out.println("��ӷ����޸��У�" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers + "," + reader);
			workflowBo.completeTask(task.getId(), "work_approve");
			System.out.println("��ӷ����Ѿ��ύ��");
			
			//����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("��ӷ���");//������̴�����˵��
			processHistoryBean.setTaskOutCome("work_approve");//��ӹ�����������Ϣ
			processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
			processHistoryBean.setNextOperateUserId(approvers+","+reader);//�����һ�������˵ı��
			processHistoryBean.setObjectId(cutId);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		//���Ͷ���
		String content = "����·��ӡ�����һ������Ϊ\"" + cutDao.getCutNameById(cutId)+"\"��ӷ������ȴ�����������";
		sendMessage(content, mobiles);
		
		//������ż�¼
		saveMessage(content, mobiles, feedbackId, CutConstant.LP_CUT_FEEDBACK, ModuleCatalog.LINECUT);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "����·��ӡ�����һ������Ϊ\"" + cutDao.getCutNameById(cutId)+"\"��ӷ������ȴ����Ĳ��ġ�";
				sendMessage(content, readerPhones[i]);

				// ������ż�¼
				saveMessage(content, readerPhones[i], feedbackId, CutConstant.LP_CUT_FEEDBACK,
						ModuleCatalog.LINECUT);
			}
		}
	}

	/**
	 * ��ӷ�������ǰ�ļ�����Ϣ
	 * 
	 * @param cutId
	 * @return
	 */
	public Map addCutFeedbackApproveForm(String cutId, String regionId) throws ServiceException {
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		List list = cutDao.getApproves(regionId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		Map map = new HashMap();
		map.put("cut", cut);
		map.put("cutFeedback", cutFeedback);
		map.put("sublineIds", sublineIds);
		map.put("approve_info_list", approve_info_list);
		return map;
	}

	/**
	 * ���淴�������Ϣ
	 * 
	 * @param cutId
	 * @param userId
	 * @param approveId
	 * @param approveResult
	 * @throws ServiceException
	 */
	public void addCutFeedbackApprove(CutFeedbackBean cutFeedbackBean,
			UserInfo userInfo) throws ServiceException {
		String proposer = cutFeedbackBean.getProposer();
		String approveResult = cutFeedbackBean.getApproveResult();
		String approveRemark = cutFeedbackBean.getApproveRemark();
		String transfer = cutFeedbackBean.getApprovers();
		String feedbackType = cutFeedbackBean.getFeedbackType();
		String cutFeedbackId = cutFeedbackBean.getId();
		
		String phone = "";
		
		//ȥ�����ĺ�ת��������˺��Լ�Ϊ�����˵����
		String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + transfer + "','" + userInfo.getUserID() + "')";
		String approverIdsReader = cutDao.getApproverIds(cutFeedbackId, CutConstant.APPROVE_READ, CutConstant.LP_CUT_FEEDBACK, condition);

		// ����������Ϣ
		saveApproveInfo(cutFeedbackId, CutConstant.LP_CUT_FEEDBACK, 
				userInfo.getUserID(), approveResult, approveRemark);
		String content = "";
		if (!approveResult.equals("2")) {
			
			if (CutConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				if("0".equals(feedbackType)){
					// ����ͨ�������뵥״̬��������ս���״̬
					cutDao.updateStateById(cutFeedbackBean.getCutId(), Cut.ACCEPTANCE);
	
					// ������������ڵĴ�ά��λId
					String conId = "";
					try {
						conId = (new UserInfoDAOImpl()).findById(proposer).getDeptID();
					} catch (Exception e) {
						logger.error("CutFeedbackBeanת��ΪCutFeedback����������Ϣ��" + e.getMessage());
					}
	
					// ���Ĳ��Ͽ��
					List<UseMaterial> useMaterials = useMaterialDAO
							.getUseMaterials(cutFeedbackBean.getCutId(),
									CutConstant.CUT_MODULE);
					updateMaterialStock(useMaterials, conId);
					//���Ļ��ղ��Ͽ��
					List<ReturnMaterial> returnMaterials = returnMaterialDAO.getReturnMaterials(cutFeedbackBean.getCutId(),CutConstant.CUT_MODULE);
					updateReturnMaterialStock(returnMaterials,conId);
					
					content = "����·��ӡ�����\"" + cutDao.getCutNameById(cutFeedbackBean.getCutId()) + "\"��ӷ������Ѿ�ͨ����ˡ�";
				}
				else{
					cutDao.updateStateById(cutFeedbackBean.getCutId(), Cut.EXAM);
					content = "����·��ӡ�����\"" + cutDao.getCutNameById(cutFeedbackBean.getCutId()) + "\"ȡ��������뵥�Ѿ�ͨ����ˡ�";
				}
				
				//���Ͷ���
				
			}
			if (CutConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				if("0".equals(feedbackType)){
					cutFeedbackDao.setUnapproveNumber(cutFeedbackId);
					cutDao.updateStateById(cutFeedbackBean.getCutId(), Cut.FEEDBACK_NO_PASS);
					//���Ͷ���
					content = "����·��ӡ�����\"" + cutDao.getCutNameById(cutFeedbackBean.getCutId()) + "\"��ӷ�����δͨ����ˡ�";
				}else{
					cutDao.updateStateById(cutFeedbackBean.getCutId(), Cut.FEEDBACK);
					CutFeedback cutFeedback = cutFeedbackDao.findByUnique("id", cutFeedbackId);
					cutFeedbackDao.delete(cutFeedback);
					//���Ͷ���
					content = "����·��ӡ�����\"" + cutDao.getCutNameById(cutFeedbackBean.getCutId()) + "\"ȡ��������뵥δͨ����ˡ�";
				}
			}
			phone = getPhoneByUserId(proposer);
		}else{
			//ת�����
			
			// �����������Ϣ
			replyApproverDAO.saveApproverOrReader(transfer, cutFeedbackId, CutConstant.APPROVE_TRANSFER_MAN, CutConstant.LP_CUT_FEEDBACK);
			
			//���Ͷ���
			if("0".equals(feedbackType)){
				content = "����·��ӡ�����һ������Ϊ\"" + cutDao.getCutNameById(cutFeedbackBean.getCutId())+"\"��ӷ������ȴ�����������";
			}else{
				content = "����·��ӡ�����һ������Ϊ\"" + cutDao.getCutNameById(cutFeedbackBean.getCutId())+"\"ȡ��������뵥�ȴ�����������";
			}
			phone  = cutFeedbackBean.getMobiles();
		}

		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
				cutFeedbackBean.getCutId());
		if (task != null
				&& CutWorkflowBO.WORK_APPROVE_TASK.equals(task.getName())) {
			System.out.println("��ӷ�������ˣ�" + task.getName());
			if (CutConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				if("0".equals(feedbackType)){
					workflowBo.setVariables(task, "assignee", proposer);
					workflowBo.setVariables(task, "transition", "check");
					
					//����������ʷ
					ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
					String nextOperateUserId = "";
					processHistoryBean.setProcessAction("��ӷ�������ͨ��");//������̴�����˵��
					processHistoryBean.setTaskOutCome("check");//��ӹ�����������Ϣ
					processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
					processHistoryBean.setNextOperateUserId(proposer);//�����һ�������˵ı��
					processHistoryBean.setObjectId(cutFeedbackBean.getCutId());//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
					try {
						processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
					} catch (Exception e) {
						e.printStackTrace();
						throw new ServiceException();
					}
				}else{
					workflowBo.setVariables(task, "assignee", userInfo.getUserID());
					workflowBo.setVariables(task, "transition", "end");
					
					//����������ʷ
					ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
					String nextOperateUserId = "";
					processHistoryBean.setProcessAction("ȡ���������ͨ��");//������̴�����˵��
					processHistoryBean.setTaskOutCome("end");//��ӹ�����������Ϣ
					processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
					processHistoryBean.setNextOperateUserId(proposer);//�����һ�������˵ı��
					processHistoryBean.setObjectId(cutFeedbackBean.getCutId());//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
					try {
						processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
					} catch (Exception e) {
						e.printStackTrace();
						throw new ServiceException();
					}
				}
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("��ӷ������ͨ����");
			}
			if (CutConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", proposer);
				workflowBo.completeTask(task.getId(), "not_passed");
				System.out.println("��ӷ�����˲�ͨ����");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("��ӷ���������ͨ��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("not_passed");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
				processHistoryBean.setNextOperateUserId(proposer);//�����һ�������˵ı��
				processHistoryBean.setObjectId(cutFeedbackBean.getCutId());//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (CutConstant.APPROVE_RESULT_TRANSFER.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", transfer+","+approverIdsReader);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("��ӷ����Ѿ�ת��");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("��ӷ���ת��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("transfer");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
				processHistoryBean.setNextOperateUserId(transfer);//�����һ�������˵ı��
				processHistoryBean.setObjectId(cutFeedbackBean.getCutId());//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
		}
		sendMessage(content, phone);
		
		//������ż�¼
		saveMessage(content, phone, cutFeedbackId, CutConstant.LP_CUT_FEEDBACK, ModuleCatalog.LINECUT);
	}

	/**
	 * �鿴����
	 * 
	 * @param cutId
	 * @return
	 */
	public Map viewCutFeedback(String cutId) {
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		Map map = new HashMap();
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		map.put("cut", cut);
		map.put("cutFeedback", cutFeedback);
		map.put("sublineIds", sublineIds);
		map.put("approve_info_list", approve_info_list);
		return map;
	}
	
	/**
	 * ���ַ���ת��ΪList
	 * 
	 * @param str����Ҫת�����ַ���
	 * @return��list
	 */
	public List<String> stringToList(String str) {
		if (str == null || str.equals("")) {
			return null;
		}
		String[] strArray = str.split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; strArray != null && i < strArray.length; i++) {
			list.add(strArray[i]);
		}
		return list;
	}

	/**
	 * ɾ�������Ϣ
	 * 
	 * @param entityId��ʵ��ID
	 * @param entityType��ʵ������
	 */
	public void deleteApprove(String entityId, String entityType) {
		approverDAO.deleteList(entityId, entityType);
	}

	/**
	 * ���������Ϣ
	 * 
	 * @param entityId��ʵ��ID
	 * @param entityType��ʵ�����
	 * @param approverId�������ID
	 * @param approveResult����˽��
	 * @param approveRemark��������
	 */
	public void saveApproveInfo(String entityId, String entityType,
			String approverId, String approveResult, String approveRemark) {
		ApproveInfo approveInfo = new ApproveInfo();
		approveInfo.setObjectId(entityId);
		approveInfo.setObjectType(entityType);
		approveInfo.setApproverId(approverId);
		approveInfo.setApproveTime(new Date());
		approveInfo.setApproveResult(approveResult);
		approveInfo.setApproveRemark(approveRemark);
		approveDao.save(approveInfo);
	}

	/**
	 * ���Ͷ���
	 * 
	 * @param content����������
	 * @param mobiles�����ն����ֻ�����
	 */
	public void sendMessage(String content, String mobiles) {
		if(mobiles != null && !"".equals(mobiles)){
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			super.sendMessage(content, mobileList);
		}
	}

	/**
	 * ������ż�¼
	 * 
	 * @param content����������
	 * @param mobiles�����ն����ֻ�����
	 * @param entityId��ʵ��ID
	 * @param entityType��ʵ������
	 * @param entityModule��ʵ��ģ��
	 */
	public void saveMessage(String content, String mobiles, String entityId,
			String entityType, String entityModule) {
		if(mobiles != null && !"".equals(mobiles)){
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			for (String mobile : mobileList) {
				SMHistory history = new SMHistory();
				history.setSimIds(mobile);
				history.setSendContent(content);
				history.setSendTime(new Date());
				history.setSmType(entityType);
				history.setObjectId(entityId);
				history.setBusinessModule(entityModule);
				historyDAO.save(history);
			}
		}
	}

	/**
	 * ͨ���û�ID��ѯ�û����ֻ�����
	 * 
	 * @param userId���û�ID
	 * @return�������û��ֻ�����
	 */
	public String getPhoneByUserId(String userId) {
		String hql = "from UserInfo userInfo where userInfo.userID=?";
		List list = cutFeedbackDao.getHibernateTemplate().find(hql, userId);
		if (list != null && !list.equals("")) {
			UserInfo userInfo = (UserInfo) list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}
	
	public void readReply(UserInfo userInfo, String approverId, String cutFeedbackId) throws ServiceException{
		approverDAO.updateReader(approverId, cutFeedbackId, CutConstant.LP_CUT_FEEDBACK);
		
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("id", cutFeedbackId);
		String creator = cutFeedback.getCreator();
		String cutId = cutFeedback.getCutId();
		
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
				cutId);
		//����������ʷ
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.setProcessAction("��ӷ�������");//������̴�����˵��
		processHistoryBean.setTaskOutCome("");//��ӹ�����������Ϣ
		processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
		processHistoryBean.setNextOperateUserId("");//�����һ�������˵ı��
		processHistoryBean.setObjectId(cutId);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
}
