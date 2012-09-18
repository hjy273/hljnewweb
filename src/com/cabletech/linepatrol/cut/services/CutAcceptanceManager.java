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
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.cut.beans.CutAcceptanceBean;
import com.cabletech.linepatrol.cut.dao.CutAcceptanceDao;
import com.cabletech.linepatrol.cut.dao.CutDao;
import com.cabletech.linepatrol.cut.dao.CutFeedbackDao;
import com.cabletech.linepatrol.cut.dao.CutHopRelDao;
import com.cabletech.linepatrol.cut.module.Cut;
import com.cabletech.linepatrol.cut.module.CutAcceptance;
import com.cabletech.linepatrol.cut.module.CutConstant;
import com.cabletech.linepatrol.cut.module.CutFeedback;
import com.cabletech.linepatrol.cut.workflow.CutWorkflowBO;

@Service
@Transactional
public class CutAcceptanceManager extends EntityManager<CutAcceptance, String> {

	@Autowired
	private CutWorkflowBO workflowBo;

	@Resource(name = "cutDao")
	private CutDao cutDao;

	@Resource(name = "cutFeedbackDao")
	private CutFeedbackDao cutFeedbackDao;

	@Resource(name = "cutAcceptanceDao")
	private CutAcceptanceDao cutAcceptanceDao;
	
	@Resource(name = "uploadFileService")
	private UploadFileService uploadFile;

	@Override
	protected HibernateDao<CutAcceptance, String> getEntityDao() {
		return cutAcceptanceDao;
	}

	@Resource(name = "cutHopRelDao")
	private CutHopRelDao cutHopRelDao;

	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDAO;

	@Resource(name = "approveDAO")
	private ApproveDAO approveDao;

	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;

	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	
	/**
	 * ����Ӹ������ǰ����������Ϣ�ͷ�����Ϣ
	 * 
	 * @param cutId
	 * @return
	 */
	public Map addCutAccptanceForm(String cutId, String regionId) throws ServiceException {
		Map map = new HashMap();
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = (CutFeedback) cutFeedbackDao.findByUnique("cutId", cutId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		List subline = cutHopRelDao.getRepeaterSection(sublineIds);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		map.put("cut", cut);
		map.put("cutFeedback", cutFeedback);
		map.put("sublineIds", sublineIds);
		map.put("subline", subline);
		map.put("approve_info_list", approve_info_list);
		return map;
	}

	/**
	 * ����������������Ϣ
	 * 
	 * @param cutAcceptance
	 * @throws ServiceException
	 */
	public void addCutAcceptance(CutAcceptanceBean cutAcceptanceBean,
			UserInfo userInfo, List<FileItem> files)
			throws ServiceException {
		String approvers = cutAcceptanceBean.getApproveId();
		String reader = cutAcceptanceBean.getReader();
		String mobiles = cutAcceptanceBean.getMobiles();
		String cutId = cutAcceptanceBean.getCutId();
		CutAcceptance cutAcceptanceSave = saveCutAcceptance(cutAcceptanceBean, userInfo);
		cutDao.updateStateById(cutId, Cut.ACCEPTANCE_APPROVE);
		String[] readerPhones = cutAcceptanceBean.getReaderPhones();

		// ���渽��
		uploadFile.saveFiles(files, ModuleCatalog.LINECUT, 
				userInfo.getRegionName(), cutAcceptanceSave.getId(), 
				CutConstant.LP_CUT_ACCEPTANCE, userInfo.getUserID());

		// �����������Ϣ
		replyApproverDAO.saveApproverOrReader(approvers, cutAcceptanceSave.getId(), CutConstant.APPROVE_MAN, CutConstant.LP_CUT_ACCEPTANCE);
		
		// ���泭������Ϣ
		replyApproverDAO.saveApproverOrReader(reader, cutAcceptanceSave.getId(), CutConstant.APPROVE_READ, CutConstant.LP_CUT_ACCEPTANCE);
		
	
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
				cutId);
		if (task != null && CutWorkflowBO.CHECK_TASK.equals(task.getName())) {
			System.out.println("�����������У�" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers+","+reader);
			workflowBo.completeTask(task.getId(), "check_approve");
			System.out.println("��������Ѿ��ύ��");
			
			//����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("������ս���");//������̴�����˵��
			processHistoryBean.setTaskOutCome("check_approve");//��ӹ�����������Ϣ
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
		String content = "����·��ӡ�����һ������Ϊ\"" + cutDao.getCutNameById(cutId)+"\"������ս��㵥�ȴ�����������";
		sendMessage(content, mobiles);
		//�������˷��Ͷ���
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "����·��ӡ�����һ������Ϊ\"" + cutDao.getCutNameById(cutId)+"\"������ս��㵥�ȴ����Ĳ��ġ�";
				sendMessage(content, readerPhones[i]);

				// ������ż�¼
				saveMessage(content, readerPhones[i], cutAcceptanceSave.getId(), CutConstant.LP_CUT_ACCEPTANCE,
						ModuleCatalog.LINECUT);
			}
		}
		//������ż�¼
		saveMessage(content, mobiles, cutAcceptanceSave.getId(), CutConstant.LP_CUT_ACCEPTANCE, ModuleCatalog.LINECUT);
	}
	
	/**
	 * ����������ʵ��
	 * @param cutAcceptanceBean
	 * @return
	 */
	public CutAcceptance saveCutAcceptance(CutAcceptanceBean cutAcceptanceBean, UserInfo userInfo){
		CutAcceptance cutAcceptance = new CutAcceptance();
		try {
			BeanUtil.objectCopy(cutAcceptanceBean, cutAcceptance);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CutAcceptanceBeanת��ΪCutAcceptance����������ϢΪ��" + e.getMessage());
			throw new ServiceException(e);
		}
		cutAcceptance.setUnapproveNumber(0);
		cutAcceptance.setCreator(userInfo.getUserID());
		cutAcceptance.setCreateTime(new Date());
		return cutAcceptanceDao.saveCutAcceptance(cutAcceptance);
	}

	/**
	 * �༭���ս���ǰ������Ϣ
	 * 
	 * @param cutId
	 * @return
	 * @throws ServiceException
	 */
	public Map editCutAcceptanceForm(String cutId, String regionId) throws ServiceException {
		Map map = new HashMap();
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		CutAcceptance cutAcceptance = cutAcceptanceDao.findByUnique("cutId",
				cutId);
		List list = cutDao.getApproves(regionId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		List subline = cutHopRelDao.getRepeaterSection(sublineIds);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		map.put("cut", cut);
		map.put("cutFeedback", cutFeedback);
		map.put("cutAcceptance", cutAcceptance);
		map.put("list", list);
		map.put("sublineIds", sublineIds);
		map.put("subline", subline);
		map.put("approve_info_list", approve_info_list);
		return map;
	}

	/**
	 * �����޸ĸ��������Ϣ
	 * 
	 * @param cutAcceptance
	 * @param userId
	 * @param approvers
	 * @throws ServiceException
	 */
	public void editCutAcceptance(CutAcceptanceBean cutAcceptanceBean,
			UserInfo userInfo, List<FileItem> files)
			throws ServiceException {
		String cutId = cutAcceptanceBean.getCutId();
		String approvers = cutAcceptanceBean.getApproveId();
		String reader = cutAcceptanceBean.getReader();
		String mobiles = cutAcceptanceBean.getMobiles();
		saveCutAcceptance(cutAcceptanceBean, userInfo);
		cutDao.updateStateById(cutId, Cut.ACCEPTANCE_APPROVE);
		String[] readerPhones = cutAcceptanceBean.getReaderPhones();

		// ���渽��
		uploadFile.saveFiles(files, ModuleCatalog.LINECUT, 
				userInfo.getRegionName(), cutAcceptanceBean.getId(), CutConstant.LP_CUT_ACCEPTANCE, userInfo.getUserID());

		replyApproverDAO.deleteList(cutAcceptanceBean.getId(), CutConstant.LP_CUT_ACCEPTANCE);
		// �����������Ϣ
		replyApproverDAO.saveApproverOrReader(approvers, cutAcceptanceBean.getId(), CutConstant.APPROVE_MAN, CutConstant.LP_CUT_ACCEPTANCE);
		
		// ���泭������Ϣ
		replyApproverDAO.saveApproverOrReader(reader, cutAcceptanceBean.getId(), CutConstant.APPROVE_READ, CutConstant.LP_CUT_ACCEPTANCE);
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),	cutId);
		if (task != null && CutWorkflowBO.CHECK_TASK.equals(task.getName())) {
			System.out.println("�����������У�" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers+","+reader);
			workflowBo.completeTask(task.getId(), "check_approve");
			System.out.println("��������Ѿ��ύ��");
			
			//����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("������ս���");//������̴�����˵��
			processHistoryBean.setTaskOutCome("check_approve");//��ӹ�����������Ϣ
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
		String content = "����·��ӡ�����һ������Ϊ\"" + cutDao.getCutNameById(cutId)+"\"������ս��㵥�ȴ�����������";
		sendMessage(content, mobiles);
		
		//������ż�¼
		saveMessage(content, mobiles, cutAcceptanceBean.getId(), CutConstant.LP_CUT_ACCEPTANCE, ModuleCatalog.LINECUT);

		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "����·��ӡ�����һ������Ϊ\"" + cutDao.getCutNameById(cutId)+"\"������ս��㵥�ȴ����Ĳ��ġ�";
				sendMessage(content, readerPhones[i]);

				// ������ż�¼
				saveMessage(content, readerPhones[i], cutAcceptanceBean.getId(), CutConstant.LP_CUT_ACCEPTANCE,
						ModuleCatalog.LINECUT);
			}
		}
	}

	/**
	 * ���ս������ǰ������Ϣ
	 * 
	 * @param cutId
	 * @return
	 * @throws ServiceException
	 */
	public Map cutAcceptanceApproveForm(String cutId) throws ServiceException {
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		CutAcceptance cutAcceptance = cutAcceptanceDao.findByUnique("cutId",
				cutId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		List subline = cutHopRelDao.getRepeaterSection(sublineIds);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		Map map = new HashMap();
		map.put("cut", cut);
		map.put("cutFeedback", cutFeedback);
		map.put("cutAcceptance", cutAcceptance);
		map.put("sublineIds", sublineIds);
		map.put("subline", subline);
		map.put("approve_info_list", approve_info_list);
		return map;
	}

	/**
	 * ���ս������
	 * 
	 * @param id
	 * @param cutId
	 * @param userId:��½���û�
	 * @param proposer:������
	 * @param approveResult
	 * @throws ServiceException
	 */
	public void cutAcceptanceApprove(CutAcceptanceBean cutAcceptanceBean, String cutId,
			UserInfo userInfo, String proposer) throws ServiceException {
		String phone = "";
		String cutAcceptanceId = cutAcceptanceBean.getId();
		String approveResult = cutAcceptanceBean.getApproveResult();
		String approveRemark = cutAcceptanceBean.getApproveRemark();
		String transfer = cutAcceptanceBean.getApprovers();
		
		//ȥ�����ĺ�ת��������˺��Լ�Ϊ�����˵����
		String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + transfer + "','" + userInfo.getUserID() + "')";
		String approverIdsReader = cutDao.getApproverIds(cutAcceptanceId, CutConstant.APPROVE_READ, CutConstant.LP_CUT_ACCEPTANCE, condition);

		// ����������Ϣ
		saveApproveInfo(cutAcceptanceId, CutConstant.LP_CUT_ACCEPTANCE, userInfo.getUserID(), approveResult, approveRemark);
		String content = "";
		if (!approveResult.equals("2")) {
			CutAcceptance cutAcceptance = cutAcceptanceDao.get(cutAcceptanceId);
			cutAcceptanceDao.initObject(cutAcceptance);
			phone = getPhoneByUserId(proposer);
			if (CutConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				cutDao.updateStateById(cutId, Cut.EXAM);
				//���Ͷ���
				content = "����·��ӡ�����\"" + cutDao.getCutNameById(cutAcceptance.getCutId()) + "\"������ս��㵥�Ѿ�ͨ����ˣ��ȴ���ά�����Ա���֡�";
			}
			if (CutConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				cutAcceptanceDao.setUnapproveNumberByAcceptanceId(cutAcceptanceId);
				cutDao.updateStateById(cutId, Cut.ACCEPTANCE_NO_PASS);
				//���Ͷ���
				content = "����·��ӡ�����\"" + cutDao.getCutNameById(cutAcceptance.getCutId()) + "\"������ս��㵥δͨ����ˡ�";
			}
			cutAcceptanceId = cutAcceptance.getId();
		}else{
			phone = cutAcceptanceBean.getMobiles();
			// �����������Ϣ
			replyApproverDAO.saveApproverOrReader(transfer, cutAcceptanceId, CutConstant.APPROVE_TRANSFER_MAN, CutConstant.LP_CUT_ACCEPTANCE);
			
			//���Ͷ���
			content = "����·��ӡ�����һ��"+userInfo.getUserName()+"ת�����������Ϊ\"" + cutDao.getCutNameById(cutId)+"\"������ս��㵥�ȴ�����������";
			
		}

		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), cutId);
		if (task != null
				&& CutWorkflowBO.CHECK_APPROVE_TASK.equals(task.getName())) {
			System.out.println("������մ���ˣ�" + task.getName());
			if (CutConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", userInfo.getUserID());
				workflowBo.setVariables(task, "transition", "evaluate");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("����������ͨ����");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("���ս�������ͨ��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("evaluate");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
				processHistoryBean.setNextOperateUserId(userInfo.getUserID());//�����һ�������˵ı��
				processHistoryBean.setObjectId(cutId);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (CutConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", proposer);
				workflowBo.completeTask(task.getId(), "not_passed");
				System.out.println("���������˲�ͨ����");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("���ս���������ͨ��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("not_passed");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
				processHistoryBean.setNextOperateUserId(proposer);//�����һ�������˵ı��
				processHistoryBean.setObjectId(cutId);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
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
				System.out.println("��������Ѿ�ת��");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("���ս���ת��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("transfer");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
				processHistoryBean.setNextOperateUserId(transfer);//�����һ�������˵ı��
				processHistoryBean.setObjectId(cutId);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
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
		saveMessage(content, phone, cutAcceptanceId, CutConstant.LP_CUT_ACCEPTANCE, ModuleCatalog.LINECUT);
	}

	/**
	 * �鿴���ս���
	 * 
	 * @param cutId
	 * @return
	 */
	public Map viewCutAcceptance(String cutId) {
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		CutAcceptance cutAcceptance = cutAcceptanceDao.findByUnique("cutId",
				cutId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		List subline = cutHopRelDao.getRepeaterSection(sublineIds);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		Map map = new HashMap();
		map.put("cut", cut);
		map.put("cutFeedback", cutFeedback);
		map.put("cutAcceptance", cutAcceptance);
		map.put("sublineIds", sublineIds);
		map.put("subline", subline);
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
		List list = cutAcceptanceDao.getHibernateTemplate().find(hql, userId);
		if (list != null && !list.equals("")) {
			UserInfo userInfo = (UserInfo) list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}
	
	public void readReply(UserInfo userInfo, String approverId, String cutAcceptanceId) throws ServiceException{
		approverDAO.updateReader(approverId, cutAcceptanceId, CutConstant.LP_CUT_ACCEPTANCE);
		
		CutAcceptance cutAcceptance = cutAcceptanceDao.findByUnique("id", cutAcceptanceId);
		String creator = cutAcceptance.getCreator();
		String cutId = cutAcceptance.getCutId();
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
				cutId);
		//����������ʷ
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.setProcessAction("���ս������");//������̴�����˵��
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
