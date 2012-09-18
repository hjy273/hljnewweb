package com.cabletech.linepatrol.cut.services;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
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
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.util.Hanzi2PinyinUtil;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.cut.beans.CutBean;
import com.cabletech.linepatrol.cut.dao.CutDao;
import com.cabletech.linepatrol.cut.dao.CutHopRelDao;
import com.cabletech.linepatrol.cut.module.Cut;
import com.cabletech.linepatrol.cut.module.CutConstant;
import com.cabletech.linepatrol.cut.workflow.CutWorkflowBO;
import com.cabletech.linepatrol.overhaul.beans.OverHaulQueryBean;
import com.cabletech.utils.StringUtil;

@Service
@Transactional
public class CutManager extends EntityManager<Cut, String> {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;

	@Autowired
	private CutWorkflowBO workflowBo;

	@Resource(name = "cutDao")
	private CutDao cutDao;

	@Resource(name = "cutHopRelDao")
	private CutHopRelDao cutHopRelDao;

	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDAO;

	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;

	@Resource(name = "uploadFileService")
	private UploadFileService uploadFile;

	@Resource(name = "approveDAO")
	private ApproveDAO approveDao;

	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;

	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;

	@Override
	protected HibernateDao<Cut, String> getEntityDao() {
		return cutDao;
	}

	/**
	 * ��Ӹ������ǰ��������
	 * 
	 * @param userInfo
	 * @param cutId
	 * @return
	 */
	public Map addCutApplyForm(UserInfo userInfo, String cutId) {
		String deptId = userInfo.getDeptID();
		String regionId = userInfo.getRegionID();
		Cut cut = null;
		String sublineIds = null;
		// ��ø�ӵĹ������workOrderId
		String deptname = userInfo.getDeptName();
		String dept = Hanzi2PinyinUtil.getPinYinHeadChar(deptname);
		String bdept = dept.toUpperCase();
		String nowDate = DateUtil.DateToString(new Date(), "yyyyMMdd");
		String applyNum = cutDao.getCutApplyNumber(deptId).toString();
		String workOrderId = bdept + nowDate
				+ StringUtil.lpad(applyNum, 4, "0");
		String[] approveInfo = null;
		String[] readerInfo = null;

		List mobiles = cutDao.getMobiles(regionId);
		List cons = cutDao.getCons(deptId);
		if (cutId != null && !"".equals(cutId)) {
			cut = cutDao.findByUnique("id", cutId);
			sublineIds = cutHopRelDao.getSublineIds(cutId);
			approveInfo = cutDao.getApproveIdAndName(cutId, CutConstant.LP_CUT,
					"01");
			readerInfo = cutDao.getApproveIdAndName(cutId, CutConstant.LP_CUT,
					"03");
		}
		Map map = new HashMap();
		map.put("workOrderId", workOrderId);
		map.put("mobiles", mobiles);
		map.put("cons", cons);
		map.put("cut", cut);
		map.put("sublineIds", sublineIds);
		map.put("approveInfo", approveInfo);
		map.put("readerInfo", readerInfo);
		return map;
	}

	/**
	 * ��Ӹ�����뵥
	 * 
	 * @param cut
	 * @throws ServiceException
	 */
	public void addCutApply(CutBean cutBean, String trunks, UserInfo userInfo,
			List<FileItem> files) throws ServiceException {

		String approvers = cutBean.getApproveId();
		String reader = cutBean.getReader();
		String mobiles = cutBean.getMobile();
		String applyState = cutBean.getApplyState();
		String[] readerPhones = cutBean.getReaderPhones();

		if (Cut.TEMPSAVE.equals(applyState)) {
			saveTempApply(cutBean, trunks, userInfo, files);
		} else {
			// ����������
			Cut cutSave = saveCut(cutBean, userInfo.getUserID(), userInfo
					.getRegionid());
			String cutId = cutSave.getId();

			// �����м̶�
			cutHopRelDao.saveTrunks(trunks, cutId);

			// �ϴ��ļ�
			uploadFile.saveFiles(files, ModuleCatalog.LINECUT, userInfo
					.getRegionName(), cutId, CutConstant.LP_CUT, userInfo
					.getUserID());

			replyApproverDAO.deleteList(cutId, CutConstant.LP_CUT);
			// �����������Ϣ
			replyApproverDAO.saveApproverOrReader(approvers, cutId,
					CutConstant.APPROVE_MAN, CutConstant.LP_CUT);

			// ���泭������Ϣ
			replyApproverDAO.saveApproverOrReader(reader, cutId,
					CutConstant.APPROVE_READ, CutConstant.LP_CUT);

			// JBPM
			Map variables = new HashMap();
			variables.put("assignee", cutSave.getProposer());
			workflowBo.createProcessInstance("linechange", cutId, variables);
			Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
					cutId);
			if (task != null && CutWorkflowBO.APPLY_TASK.equals(task.getName())) {
				System.out.println("�����������У�" + task.getName());
				workflowBo.setVariables(task, "assignee", approvers + ","
						+ reader);
				workflowBo.completeTask(task.getId(), "apply_approve");
				System.out.println("��������Ѿ��ύ��");

				// ����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("�������");// ������̴�����˵��
				processHistoryBean.setTaskOutCome("apply_approve");// ��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",
						ModuleCatalog.LINECUT);
				processHistoryBean.setNextOperateUserId(approvers + ","
						+ reader);// �����һ�������˵ı��
				processHistoryBean.setObjectId(cutId);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			// ���Ͷ���
			String content = "����·��ӡ�����һ������Ϊ\"" + cutSave.getCutName()
					+ "\"������뵥�ȴ�����������";
			sendMessage(content, mobiles);

			// ������ż�¼
			saveMessage(content, mobiles, cutId, CutConstant.LP_CUT,
					ModuleCatalog.LINECUT);
			if (readerPhones != null && readerPhones.length > 0) {
				for (int i = 0; i < readerPhones.length; i++) {
					content = "����·��ӡ�����һ������Ϊ\"" + cutSave.getCutName()
							+ "\"������뵥�ȴ����Ĳ��ġ�";
					sendMessage(content, readerPhones[i]);

					// ������ż�¼
					saveMessage(content, readerPhones[i], cutId,
							CutConstant.LP_CUT, ModuleCatalog.LINECUT);
				}
			}
		}
	}

	/**
	 * ���������ʱ����
	 * 
	 * @param cutBean
	 * @param trunks
	 * @param userInfo
	 * @param files
	 */
	public void saveTempApply(CutBean cutBean, String trunks,
			UserInfo userInfo, List<FileItem> files) {
		// ����������
		Cut cutSave = saveTempCut(cutBean, userInfo.getUserID(), userInfo
				.getRegionid());
		String cutId = cutSave.getId();
		String approvers = cutBean.getApproveId();
		String reader = cutBean.getReader();

		// �����м̶�
		cutHopRelDao.saveTrunks(trunks, cutId);

		// �ϴ��ļ�
		uploadFile.saveFiles(files, ModuleCatalog.LINECUT, userInfo
				.getRegionName(), cutId, CutConstant.LP_CUT, userInfo
				.getUserID());

		replyApproverDAO.deleteList(cutId, CutConstant.LP_CUT);
		// �����������Ϣ
		replyApproverDAO.saveApproverOrReader(approvers, cutId,
				CutConstant.APPROVE_MAN, CutConstant.LP_CUT);

		// ���泭������Ϣ
		replyApproverDAO.saveApproverOrReader(reader, cutId,
				CutConstant.APPROVE_READ, CutConstant.LP_CUT);
	}

	/**
	 * �����ʱ��������б�
	 * 
	 * @param userId
	 * @return
	 */
	public List cutTempList(String userId) {
		return cutDao.cutTempList(userId);
	}

	/**
	 * �༭���뵥ǰ�ļ�����Ϣ
	 * 
	 * @param cutId
	 * @return
	 * @throws ServiceException
	 */
	public Map editCutApplyForm(String cutId, String regionId, String deptId)
			throws ServiceException {
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		List mobiles = cutDao.getMobiles(regionId);
		List cons = cutDao.getCons(deptId);
		Map map = new HashMap();
		map.put("mobiles", mobiles);
		map.put("cons", cons);
		map.put("cut", cut);
		map.put("sublineIds", sublineIds);
		return map;
	}

	/**
	 * �޸ĸ�����뵥��Ϣ ͨ�����뵥Id��ѯ�����������ݿ��и�����δͨ������
	 * 
	 * @param cutid
	 * @param cutNew
	 */
	public void editCutApply(CutBean cutBean, UserInfo userInfo, String trunks,
			List<FileItem> files) throws ServiceException {
		String approvers = cutBean.getApproveId();
		String mobiles = cutBean.getMobile();
		String reader = cutBean.getReader();
		String[] readerPhones = cutBean.getReaderPhones();
		// ����������
		Cut cut = saveCut(cutBean, userInfo.getUserID(), userInfo.getRegionID());

		// �ϴ��ļ�
		uploadFile.saveFiles(files, ModuleCatalog.LINECUT, userInfo
				.getRegionName(), cut.getId(), CutConstant.LP_CUT, userInfo
				.getUserID());

		// ɾ����ǰ�м̶���Ϣ
		cutHopRelDao.deleteHopRelByCutId(cut.getId());

		// �����м̶�
		cutHopRelDao.saveTrunks(trunks, cut.getId());

		replyApproverDAO.deleteList(cut.getId(), CutConstant.LP_CUT);
		// �����������Ϣ
		replyApproverDAO.saveApproverOrReader(approvers, cut.getId(),
				CutConstant.APPROVE_MAN, CutConstant.LP_CUT);
		// ���泭������Ϣ
		replyApproverDAO.saveApproverOrReader(reader, cut.getId(),
				CutConstant.APPROVE_READ, CutConstant.LP_CUT);

		// ������
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), cut
				.getId());
		if (task != null && CutWorkflowBO.APPLY_TASK.equals(task.getName())) {
			System.out.println("�����������У�" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers + "," + reader);
			workflowBo.completeTask(task.getId(), "apply_approve");
			System.out.println("��������Ѿ��ύ��");

			// ����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("�������");// ������̴�����˵��
			processHistoryBean.setTaskOutCome("apply_approve");// ��ӹ�����������Ϣ
			processHistoryBean.initial(task, userInfo, "",
					ModuleCatalog.LINECUT);
			processHistoryBean.setNextOperateUserId(approvers + "," + reader);// �����һ�������˵ı��
			processHistoryBean.setObjectId(cut.getId());// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		// ���Ͷ���
		String content = "����·��ӡ�����һ������Ϊ\"" + cut.getCutName()
				+ "\"������뵥�ȴ�����������";
		sendMessage(content, mobiles);

		// ������ż�¼
		saveMessage(content, mobiles, cut.getId(), CutConstant.LP_CUT,
				ModuleCatalog.LINECUT);

		if (readerPhones != null && readerPhones.length > 0) {
			for (int i = 0; i < readerPhones.length; i++) {
				content = "����·��ӡ�����һ������Ϊ\"" + cut.getCutName()
						+ "\"������뵥�ȴ����Ĳ��ġ�";
				sendMessage(content, readerPhones[i]);

				// ������ż�¼
				saveMessage(content, readerPhones[i], cut.getId(),
						CutConstant.LP_CUT, ModuleCatalog.LINECUT);
			}
		}
	}

	/**
	 * �����������ǰ��������
	 * 
	 * @param cutId
	 * @return
	 */
	public Map approveCutApplyForm(String cutId) {
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='"
				+ cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		Map map = new HashMap();
		map.put("cut", cut);
		map.put("sublineIds", sublineIds);
		map.put("approve_info_list", approve_info_list);
		return map;
	}

	/**
	 * �������������Ϣ
	 * 
	 * @param cut
	 * @throws ServiceException
	 */
	public void approveCutApply(UserInfo userInfo, CutBean cutBean,
			List<FileItem> files) throws ServiceException {
		String cutId = cutBean.getId();
		String replyBeginTime = cutBean.getReplyBeginTime();
		String replyEndTime = cutBean.getReplyEndTime();
		String approveResult = cutBean.getApproveResult();
		String approveRemark = cutBean.getApproveRemark();
		String transferId = cutBean.getApprovers();
		String operator = cutBean.getOperator();
		String deadline = cutBean.getDeadline();
		// ȥ�����ĺ�ת��������˺��Լ�Ϊ�����˵����
		String condition = "and t.finish_readed<>'1' and t.approver_id not in ('"
				+ transferId + "','" + userInfo.getUserID() + "')";
		String approverIdsReader = cutDao.getApproverIds(cutId,
				CutConstant.APPROVE_READ, CutConstant.LP_CUT, condition);

		Cut cut = cutDao.findByUnique("id", cutId);
		if ("approve".equals(operator)) {
			try {
				if (replyBeginTime != null && !"".equals(replyBeginTime)) {
					cut.setReplyBeginTime(sdf.parse(replyBeginTime));
				}
				if (replyEndTime != null && !"".equals(replyEndTime)) {
					cut.setReplyEndTime(sdf.parse(replyEndTime));
				}
				if (deadline != null && !"".equals(deadline)) {
					cut.setDeadline(sdf.parse(deadline));
				}
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error("�����������ʱ������ת������" + e.getMessage());
			}
		}

		String proposer = cut.getProposer();
		String phone = getPhoneByUserId(proposer);

		// ���������Ϣ
		ApproveInfo approveInfo = saveApproveInfo2(cutBean.getId(),
				CutConstant.LP_CUT, userInfo.getUserID(), approveResult,
				approveRemark);

		// �ϴ��ļ�
		uploadFile.saveFiles(files, ModuleCatalog.LINECUT, userInfo
				.getRegionName(), approveInfo.getId(),
				CutConstant.LP_APPROVE_INFO, userInfo.getUserID());
		String content = "";
		if (!approveResult.equals("2")) {

			if (CutConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				cutDao.setCutState(cutId, Cut.FEEDBACK);

				// ���Ͷ���
				content = "����·��ӡ�����\"" + cut.getCutName() + "\"������뵥�Ѿ�ͨ����ˡ�";
			}
			if (CutConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				cutDao.setUnapproveNumber(cutId);
				cutDao.setCutState(cutId, Cut.APPLY_NO_PASS);

				// ���Ͷ���
				content = "����·��ӡ�����\"" + cut.getCutName() + "\"������뵥δͨ����ˡ�";
			}

		} else {
			// �����������Ϣ
			replyApproverDAO.saveApproverOrReader(transferId, cut.getId(),
					CutConstant.APPROVE_TRANSFER_MAN, CutConstant.LP_CUT);

			// ���Ͷ���
			content = "����·��ӡ�����һ������Ϊ\"" + cut.getCutName() + "\"������뵥�ȴ�����������";
			phone = cutBean.getMobile();

		}

		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), cut
				.getId());
		if (task != null
				&& CutWorkflowBO.APPLY_APPROVE_TASK.equals(task.getName())) {
			System.out.println("����������ˣ�" + task.getName());
			if (CutConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", cut.getProposer());
				workflowBo.setVariables(task, "transition", "work");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("����������ͨ����");

				// ����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("�����������ͨ��");// ������̴�����˵��
				processHistoryBean.setTaskOutCome("passed");// ��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",
						ModuleCatalog.LINECUT);
				processHistoryBean.setNextOperateUserId(cut.getProposer());// �����һ�������˵ı��
				processHistoryBean.setObjectId(cut.getId());// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (CutConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", cut.getProposer());
				workflowBo.completeTask(task.getId(), "not_passed");
				System.out.println("���������˲�ͨ����");

				// ����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("�������������ͨ��");// ������̴�����˵��
				processHistoryBean.setTaskOutCome("not_passed");// ��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",
						ModuleCatalog.LINECUT);
				processHistoryBean.setNextOperateUserId(cut.getProposer());// �����һ�������˵ı��
				processHistoryBean.setObjectId(cut.getId());// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (CutConstant.APPROVE_RESULT_TRANSFER.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", transferId + ","
						+ approverIdsReader);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("��������Ѿ�ת��");

				// ����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("�������ת��");// ������̴�����˵��
				processHistoryBean.setTaskOutCome("transfer");// ��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",
						ModuleCatalog.LINECUT);
				processHistoryBean.setNextOperateUserId(transferId);// �����һ�������˵ı��
				processHistoryBean.setObjectId(cut.getId());// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
		}
		sendMessage(content, phone);

		// ������ż�¼
		saveMessage(content, phone, cut.getId(), CutConstant.LP_CUT,
				ModuleCatalog.LINECUT);
	}

	/**
	 * ��ô��칤���б� 1��ͨ������isread�ж����������ǲ鿴����isreadΪtrue��Ϊ������falseʱΪ�鿴
	 * 
	 * @param taskName
	 * @param assignee
	 *            ��ĳ���û�ID
	 * @param condition
	 *            ����ѯ����
	 * @return�����û��Ĵ��칤���б�
	 */
	public List getHandWork(String assignee, String type, String taskName) {
		String condition = generatorCondition(type);
//		List<?> list = workflowBo.queryForHandleListBean(assignee, condition,
//				taskName);
		Map<String,Task> tasks =  workflowBo.query4HandleTask(assignee, taskName);
		List cutapplys = cutDao.getList(condition);
		List list2 = new ArrayList();
		int acceptance_count = 0;
		int feedback_count = 0;
		int cut_count = 0;
		int acceptance_count2 = 0;
		int feedback_count2 = 0;
		int cut_count2 = 0;
		if (cutapplys != null && cutapplys.size() > 0) {
			for (int i = 0; i < cutapplys.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) cutapplys.get(i);
				String objectid = (String) bean.get("id");
				Object feedbackId = bean.get("feedback_id");
				Object acceptanceId = bean.get("acceptance_id");
				logger.info("assignee=" + assignee + " condition=" + condition
						+ " objectId=" + objectid + " feedbackId=" + feedbackId
						+ " acceptanceId=" + acceptanceId);
				Task task = tasks.get(CutWorkflowBO.CUT_WORKFLOW+"."+objectid);	
				if(task == null){
					continue;
				}
				bean.set("flow_state", task.getName());
				if (acceptanceId != null) {
					boolean isRead = approverDAO.isReadOnly((String) acceptanceId, assignee,"LP_CUT_ACCEPTANCE");
					bean.set("isread", "" + isRead);
					if (isRead) {
						if (judgeFinishRead((String) acceptanceId,
								CutConstant.LP_CUT_ACCEPTANCE, assignee)) {
							list2.add(bean);
							acceptance_count++;
						}
					} else {
						list2.add(bean);
						acceptance_count2++;
					}
					continue;
				}
				if (feedbackId != null) {
					boolean read = approverDAO.isReadOnly((String) feedbackId,
							assignee, "LP_CUT_FEEDBACK");
					bean.set("isread", "" + read);
					if (read) {
						if (judgeFinishRead((String) feedbackId,
								CutConstant.LP_CUT_FEEDBACK, assignee)) {
							list2.add(bean);
							feedback_count++;
						}
					} else {
						list2.add(bean);
						feedback_count2++;
					}
					continue;
				}
				boolean read = approverDAO.isReadOnly(objectid, assignee,
						"LP_CUT");
				bean.set("isread", "" + read);
				if (read) {
					if (judgeFinishRead((String) objectid, CutConstant.LP_CUT,
							assignee)) {
						list2.add(bean);
						cut_count++;
					}
				} else {
					list2.add(bean);
					cut_count2++;
				}
			}
		}
		return list2;
	}
	public int queryForHandleNumber(String assignee){
		return getHandWork(assignee,"","").size();
	}
	private String generatorCondition(String type){
		String condition = "";
		if (type != null && type != "") {
			String state = "";
			if (type.equals("apply")) {
				state = Cut.APPLY+","+Cut.APPLY_NO_PASS+","+Cut.FEEDBACK;
			} else if (type.equals("feedback")) {
				state = Cut.FEEDBACK_APPROVE+","+Cut.FEEDBACK_NO_PASS+","+Cut.ACCEPTANCE;
			} else {
				state = Cut.ACCEPTANCE_APPROVE+","+Cut.ACCEPTANCE_NO_PASS+","+Cut.EXAM;
			}
			condition = " and cut.state in(" + state + ")";
		}
		return condition;
	}

	/**
	 * �жϸ�������Ƿ��Ѿ�����
	 * 
	 * @param objectId
	 * @param objectType
	 * @param userId
	 * @return
	 */
	private boolean judgeFinishRead(String objectId, String objectType,
			String userId) {
		ReplyApprover approver;
		boolean flag = true;
		List<ReplyApprover> approverList = approverDAO.getApprovers(objectId,
				objectType);
		for (int i = 0; approverList != null && i < approverList.size(); i++) {
			approver = approverList.get(i);
			if (approver != null && userId.equals(approver.getApproverId())) {
				if (CommonConstant.FINISH_READED.equals(approver
						.getFinishReaded())) {
					flag = false;
				} else {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * ����������
	 * 
	 * @param cutBean
	 * @param proposer
	 * @param regionId
	 * @return
	 */
	private Cut saveCut(CutBean cutBean, String proposer, String regionId) {
		Cut cut = new Cut();
		try {
			BeanUtil.objectCopy(cutBean, cut);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CutBeanת��ΪCut����������ϢΪ��" + e.getMessage());
			throw new ServiceException(e);
		}
		cut.setApplyState(Cut.SAVE);
		cut.setState(Cut.APPLY);
		cut.setProposer(proposer);
		cut.setApplyDate(new Date());
		cut.setRegionId(regionId);
		return cutDao.addCutApply(cut);
	}

	/**
	 * ������ʱ�������ʵ��
	 * 
	 * @param cutBean
	 *            ���������Bean
	 * @param proposer
	 *            ��������
	 * @param regionId
	 *            �����û�����ID
	 * @return���������ʵ��
	 */
	private Cut saveTempCut(CutBean cutBean, String proposer, String regionId) {
		Cut cut = new Cut();
		cut.setId(cutBean.getId());
		cut.setWorkOrderId(cutBean.getWorkOrderId());
		cut.setCutName(cutBean.getCutName());
		cut.setCutLevel(cutBean.getCutLevel());
		cut.setBuilder(cutBean.getBuilder());
		cut.setChargeMan(cutBean.getChargeMan());
		try {
			if (cutBean.getBeginTime() != null
					&& !"".equals(cutBean.getBeginTime())) {
				cut.setBeginTime(sdf.parse(cutBean.getBeginTime()));
			}
			if (cutBean.getEndTime() != null
					&& !"".equals(cutBean.getEndTime())) {
				cut.setEndTime(sdf.parse(cutBean.getEndTime()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("�������ʱ����������ʵ������ת������������ϢΪ��" + e.getMessage());
			throw new ServiceException(e);
		}
		cut.setBudget(cutBean.getBudget());
		cut.setIsCompensation(cutBean.getIsCompensation());
		cut.setCompCompany(cutBean.getCompCompany());
		cut.setBeforeLength(cutBean.getBeforeLength());
		cut.setCutCause(cutBean.getCutCause());
		cut.setCutPlace(cutBean.getCutPlace());
		cut.setUnapproveNumber(cutBean.getUnapproveNumber());
		cut.setOtherImpressCable(cutBean.getOtherImpressCable());
		cut.setUseMateral(cutBean.getUseMateral());
		cut.setLiveChargeman(cutBean.getLiveChargeman());
		cut.setApplyState(cutBean.getApplyState());
		cut.setState("");
		cut.setProposer(proposer);
		cut.setApplyDate(new Date());
		cut.setRegionId(regionId);
		return cutDao.addCutApply(cut);
	}

	/**
	 * �鿴�������
	 * 
	 * @param cutId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Map viewCut(String cutId) {
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		String cancelUserName = userInfoDao.getUserName(cut.getCancelUserId());
		cut.setCancelUserName(cancelUserName);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='"
				+ cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		Map map = new HashMap();
		map.put("cut", cut);
		map.put("sublineIds", sublineIds);
		map.put("approve_info_list", approve_info_list);
		return map;
	}

	/**
	 * ���ĳ�û��ĸ�������б�
	 * 
	 * @param userId
	 *            ���û�ID
	 * @return����������б�
	 */
	public List queryApplyList(String userId) {
		return cutDao.queryApplyList(userId);
	}

	/**
	 * ���ַ���ת��ΪList
	 * 
	 * @param str
	 *            ����Ҫת�����ַ���
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
	 * @param entityId
	 *            ��ʵ��ID
	 * @param entityType
	 *            ��ʵ������
	 */
	public void deleteApprove(String entityId, String entityType) {
		approverDAO.deleteList(entityId, entityType);
	}

	/**
	 * ���������Ϣ
	 * 
	 * @param entityId
	 *            ��ʵ��ID
	 * @param entityType
	 *            ��ʵ�����
	 * @param approverId
	 *            �������ID
	 * @param approveResult
	 *            ����˽��
	 * @param approveRemark
	 *            ��������
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

	public ApproveInfo saveApproveInfo2(String entityId, String entityType,
			String approverId, String approveResult, String approveRemark) {
		ApproveInfo approveInfo = new ApproveInfo();
		approveInfo.setObjectId(entityId);
		approveInfo.setObjectType(entityType);
		approveInfo.setApproverId(approverId);
		approveInfo.setApproveTime(new Date());
		approveInfo.setApproveResult(approveResult);
		approveInfo.setApproveRemark(approveRemark);
		approveDao.save(approveInfo);
		return approveInfo;
	}

	/**
	 * ���Ͷ���
	 * 
	 * @param content
	 *            ����������
	 * @param mobiles
	 *            �����ն����ֻ�����
	 */
	public void sendMessage(String content, String mobiles) {
		if (mobiles != null && !"".equals(mobiles)) {
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			super.sendMessage(content, mobileList);
		}
	}

	/**
	 * ������ż�¼
	 * 
	 * @param content
	 *            ����������
	 * @param mobiles
	 *            �����ն����ֻ�����
	 * @param entityId
	 *            ��ʵ��ID
	 * @param entityType
	 *            ��ʵ������
	 * @param entityModule
	 *            ��ʵ��ģ��
	 */
	public void saveMessage(String content, String mobiles, String entityId,
			String entityType, String entityModule) {
		if (mobiles != null && !"".equals(mobiles)) {
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
	 * @param userId
	 *            ���û�ID
	 * @return�������û��ֻ�����
	 */
	public String getPhoneByUserId(String userId) {
		String hql = "from UserInfo userInfo where userInfo.userID=?";
		List list = cutDao.getHibernateTemplate().find(hql, userId);
		if (list != null && !list.equals("")) {
			UserInfo userInfo = (UserInfo) list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}

	/**
	 * ��ô��칤������
	 * 
	 * @param condition
	 * @param userInfo
	 * @return
	 */
	public List queryForHandleCutNum(String condition, UserInfo userInfo) {
		// TODO Auto-generated method stub
		String assignee = userInfo.getUserID();
		List list = new ArrayList();
		List handleTaskList = workflowBo.queryForHandleListBean(assignee,
				condition, "");
//		List<Task> handleTaskList = workflowBo.findTasks(assignee,"");
		DynaBean bean;
		int waitApplyTaskNum = 0;
		int waitApplyApproveTaskNum = 0;
		int waitWorkTaskNum = 0;
		int waitWorkApproveTaskNum = 0;
		int waitCheckTaskNum = 0;
		int waitCheckApproveTaskNum = 0;
		int waitEvaluateTaskNum = 0;
		
		for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
			bean = (DynaBean) handleTaskList.get(i);
//			logger.info("bean.get(flow_state):" + bean.get("flow_state"));
			if (bean != null) {
				if (bean.get("flow_state") != null) {
					if (CutWorkflowBO.APPLY_TASK.equals(bean.get("flow_state"))) {
						waitApplyTaskNum++;
					}
					if (CutWorkflowBO.APPLY_APPROVE_TASK.equals(bean
							.get("flow_state"))) {
						if (judgeFinishRead((String) bean.get("id"),
								CutConstant.LP_CUT, userInfo.getUserID())) {
							waitApplyApproveTaskNum++;
						}
					}
					if (CutWorkflowBO.WORK_TASK.equals(bean.get("flow_state"))) {
						waitWorkTaskNum++;
					}
					if (CutWorkflowBO.WORK_APPROVE_TASK.equals(bean
							.get("flow_state"))) {
						if (judgeFinishRead((String) bean.get("feedback_id"),
								CutConstant.LP_CUT_FEEDBACK, userInfo
										.getUserID())) {
							waitWorkApproveTaskNum++;
						}
					}
					if (CutWorkflowBO.CHECK_TASK.equals(bean.get("flow_state"))) {
						waitCheckTaskNum++;
					}
					if (CutWorkflowBO.CHECK_APPROVE_TASK.equals(bean
							.get("flow_state"))) {
						if (judgeFinishRead((String) bean.get("acceptance_id"),
								CutConstant.LP_CUT_ACCEPTANCE, userInfo
										.getUserID())) {
							waitCheckApproveTaskNum++;
						}
					}
					if (CutWorkflowBO.EVALUATE_TASK.equals(bean
							.get("flow_state"))) {
						waitEvaluateTaskNum++;
					}
				}
			}
		}
		list.add(waitApplyTaskNum);
		list.add(waitApplyApproveTaskNum);
		list.add(waitWorkTaskNum);
		list.add(waitWorkApproveTaskNum);
		list.add(waitCheckTaskNum);
		list.add(waitCheckApproveTaskNum);
		list.add(waitEvaluateTaskNum);
		return list;
	}

	/**
	 * �����������
	 * 
	 * @param userInfo
	 * @param approverId
	 * @param cutId
	 * @throws ServiceException
	 */
	public void readReply(UserInfo userInfo, String approverId, String cutId)
			throws ServiceException {
		approverDAO.updateReader(approverId, cutId, CutConstant.LP_CUT);

		Cut cut = cutDao.findByUnique("id", cutId);
		String creator = cut.getProposer();
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), cutId);
		// ����������ʷ
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.setProcessAction("����������");// ������̴�����˵��
		processHistoryBean.setTaskOutCome("");// ��ӹ�����������Ϣ
		processHistoryBean.initial(task, userInfo, "", ModuleCatalog.LINECUT);
		processHistoryBean.setNextOperateUserId("");// �����һ�������˵ı��
		processHistoryBean.setObjectId(cutId);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	/**
	 * ɾ����ʱ�ĸ������
	 * 
	 * @param cutId
	 * @throws ServiceException
	 */
	public void deleteTempSaveCut(String cutId) throws ServiceException {
		// ɾ������˳�����
		replyApproverDAO.deleteList(cutId, CutConstant.LP_CUT);
		// ɾ��������Ϣ
		List affixIds = cutDao.getAffixIdByCutId(cutId);
		for (Iterator iterator = affixIds.iterator(); iterator.hasNext();) {
			String affixId = (String) iterator.next();
			uploadFile.delById(affixId);
		}
		// ɾ���м̶�
		cutHopRelDao.deleteHopRelByCutId(cutId);
		// ɾ���������
		cutDao.delete(cutId);
	}

	/**
	 * ��ѯ�Ѱ칤���б�
	 * 
	 * @param userInfo
	 * @param taskName
	 * @param taskOutCome
	 * @return
	 */
	public List queryFinishHandledCutApplyList(UserInfo userInfo,
			String taskName, String taskOutCome) {
		String assignee = userInfo.getUserID();
		String condition = "";
		// ConditionGenerate.getUserQueryCondition(userInfo);
		condition += " and process.operate_user_id='" + assignee + "' ";
		List list = new ArrayList();
		List prevList = new ArrayList();
		List handledTaskList = cutDao.queryFinishHandledList(condition);
		DynaBean taskBean;
		DynaBean tmpBean;
		boolean flag = true;
		for (int i = 0; handledTaskList != null && i < handledTaskList.size(); i++) {
			flag = true;
			taskBean = (DynaBean) handledTaskList.get(i);
			if (taskBean != null) {
				for (int j = 0; prevList != null && j < prevList.size(); j++) {
					tmpBean = (DynaBean) prevList.get(j);
					if (tmpBean != null
							&& tmpBean.get("id").equals(taskBean.get("id"))) {
						flag = false;
						break;
					}
				}
				if (flag) {
					prevList.add(taskBean);
				}
			}
		}
		if (taskName != null && !"".equals(taskName)) {
			for (int i = 0; prevList != null && i < prevList.size(); i++) {
				tmpBean = (DynaBean) prevList.get(i);
				if (judgeInStr((String) tmpBean.get("handled_task_name"),
						taskName)) {
					if (taskOutCome != null && !"".equals(taskOutCome)) {
						if (judgeInStr((String) tmpBean.get("task_out_come"),
								taskOutCome)) {
							list.add(tmpBean);
						}
					} else {
						list.add(tmpBean);
					}
				}
			}
		} else {
			list = prevList;
		}
		return list;
	}

	private boolean judgeInStr(String value, String compareStr) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if (compareStr != null) {
			if (compareStr.indexOf(",") != -1) {
				String[] str = compareStr.split(",");
				for (int i = 0; str != null && i < str.length; i++) {
					if (str[i] != null && str[i].equals(value)) {
						flag = true;
						break;
					}
				}
			} else {
				if (compareStr.equals(value)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public List queryForHandledCutTaskNumList(String condition,
			UserInfo userInfo) {
		String assignee = userInfo.getUserID();
		List list = new ArrayList();
		List handleTaskList = queryFinishHandledCutApplyList(userInfo, "", "");
		DynaBean bean;
		int applyedTaskNum = 0;
		int applyApprovedTaskNum = 0;
		int workTaskNum = 0;
		int workApproveTaskNum = 0;
		int checkTaskNum = 0;
		int checkApproveTaskNum = 0;
		int evaluateTaskNum = 0;
		for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
			bean = (DynaBean) handleTaskList.get(i);
			if (bean != null) {
				if (bean.get("handled_task_name") != null) {
					// ���������ɵ�״̬�������������
					if (CutWorkflowBO.APPLY_TASK.equals(bean
							.get("handled_task_name"))) {
						applyedTaskNum++;
					}
					if (CutWorkflowBO.APPLY_APPROVE_TASK.equals(bean
							.get("handled_task_name"))) {
						applyApprovedTaskNum++;
					}
					if (CutWorkflowBO.WORK_TASK.equals(bean
							.get("handled_task_name"))) {
						workTaskNum++;
					}
					if (CutWorkflowBO.WORK_APPROVE_TASK.equals(bean
							.get("handled_task_name"))) {
						workApproveTaskNum++;
					}
					if (CutWorkflowBO.CHECK_TASK.equals(bean
							.get("handled_task_name"))) {
						checkTaskNum++;
					}
					if (CutWorkflowBO.CHECK_APPROVE_TASK.equals(bean
							.get("handled_task_name"))) {
						checkApproveTaskNum++;
					}
					if (CutWorkflowBO.EVALUATE_TASK.equals(bean
							.get("handled_task_name"))) {
						evaluateTaskNum++;
					}
				}
			}
		}
		list.add(applyedTaskNum);
		list.add(applyApprovedTaskNum);
		list.add(workTaskNum);
		list.add(workApproveTaskNum);
		list.add(checkTaskNum);
		list.add(checkApproveTaskNum);
		list.add(evaluateTaskNum);
		return list;
	}

	public void invalidCut(String cutId, UserInfo userInfo)
			throws ServiceException {
		Cut cut = cutDao.findByUnique("id", cutId);
		cut.setState(Cut.FINISH);
		cutDao.save(cut);

		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), cut
				.getId());
		if (task != null && CutWorkflowBO.APPLY_TASK.equals(task.getName())) {
			// ����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("�����������");// ������̴�����˵��
			processHistoryBean.setTaskOutCome("");// ��ӹ�����������Ϣ
			processHistoryBean.initial(task, userInfo, "",
					ModuleCatalog.LINECUT);
			processHistoryBean.setNextOperateUserId("");// �����һ�������˵ı��
			processHistoryBean.setObjectId(cutId);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}

		// ����������
		workflowBo.endProcessInstance("linechange." + cutId);
	}

	/**
	 * ִ��ȡ���������
	 * 
	 * @author yangjun 2010-6-23
	 * @param bean
	 * @param userInfo
	 */
	public void cancelCut(CutBean bean, UserInfo userInfo) {
		// TODO Auto-generated method stub
		Cut cut = cutDao.get(bean.getId());
		cut.setState(Cut.CANCELED_STATE);
		cut.setCancelTime(new Date());
		cut.setCancelUserId(userInfo.getUserID());
		cut.setCancelReason(bean.getCancelReason());
		cutDao.save(cut);
		String processInstanceId = "";
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processInstanceId = CutWorkflowBO.CUT_WORKFLOW + "." + cut.getId();
		processHistoryBean.initial(null, userInfo, processInstanceId,
				ModuleCatalog.LINECUT);
		processHistoryBean.setHandledTaskName("any");
		processHistoryBean.setNextOperateUserId("");
		processHistoryBean.setObjectId(cut.getId());
		processHistoryBean.setProcessAction("�������ȡ��");
		processHistoryBean.setTaskOutCome("cancel");
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
			workflowBo.endProcessInstance(processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	// ������Ŀʹ��--���
	public List<Cut> getCutForOverHaul(String deptId,
			OverHaulQueryBean queryBean) {
		return cutDao.getCutForOverHaul(deptId, queryBean);
	}
}
