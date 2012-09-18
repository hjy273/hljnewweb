package com.cabletech.linepatrol.safeguard.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.safeguard.beans.SpecialEndPlanBean;
import com.cabletech.linepatrol.safeguard.dao.SafeguardConDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardPlanDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSpplanDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardTaskDao;
import com.cabletech.linepatrol.safeguard.dao.SpecialEndPlanDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardConstant;
import com.cabletech.linepatrol.safeguard.module.SafeguardPlan;
import com.cabletech.linepatrol.safeguard.module.SafeguardSpplan;
import com.cabletech.linepatrol.safeguard.module.SpecialEndPlan;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardReplanSubWorkflowBO;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardSubWorkflowBO;
import com.cabletech.linepatrol.specialplan.dao.SpecialPlanDao;

@Service
@Transactional
public class SpecialEndPlanBo extends EntityManager<SpecialEndPlan, String> {

	private static Logger logger = Logger.getLogger(SpecialEndPlanBo.class
			.getName());
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@Override
	protected HibernateDao<SpecialEndPlan, String> getEntityDao() {
		return specialEndPlanDao;
	}

	@Autowired
	private SafeguardSubWorkflowBO workflowBo;

	@Resource(name = "specialEndPlanDao")
	private SpecialEndPlanDao specialEndPlanDao;
	
	@Resource(name = "specialPlanDao")
	private SpecialPlanDao specialPlanDao;
	
	@Resource(name = "safeguardSpplanDao")
	private SafeguardSpplanDao safeguardSpplanDao;
	
	@Resource(name = "safeguardPlanDao")
	private SafeguardPlanDao safeguardPlanDao;
	
	@Resource(name = "approveDAO")
	private ApproveDAO approveDao;
	
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDAO;
	
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	
	@Resource(name = "safeguardTaskDao")
	private SafeguardTaskDao safeguardTaskDao;
	
	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;
	
	@Resource(name = "safeguardConDao")
	private SafeguardConDao safeguardConDao;
	
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	
	public SpecialEndPlan getSpecialEndPlan(String spEndId){
		return specialEndPlanDao.findByUnique("id", spEndId);
	}
	
	/**
	 * ����Ѳ�ƻ�ID�����ֹ�ƻ�ID
	 * @param spid
	 * @return
	 */
	public String getEndPlanIdBySpId(String spid){
		String endPlanId = null;
		List list = specialEndPlanDao.findByProperty("planId", spid);
		if(list != null && list.size() > 0){
			endPlanId = ((SpecialEndPlan)list.get(0)).getId();
			Date createDate = ((SpecialEndPlan)list.get(0)).getCreateTime();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				SpecialEndPlan specialEndPlan = (SpecialEndPlan) iterator.next();
				if(createDate.before(specialEndPlan.getCreateTime())){
					createDate = specialEndPlan.getCreateTime();
					endPlanId = specialEndPlan.getId();
				}
			}
		}
		return endPlanId;
	}
	
	/**
	 * ��ô��칤��
	 * @param userInfo
	 * @param condition
	 * @param taskName
	 * @return
	 */
	public List getAgentList(UserInfo userInfo, String condition,
			String taskName) {
		String assignee = userInfo.getUserID();
		List list = workflowBo.queryForHandleListBean(assignee, condition,
				taskName);
		List list2 = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String spplanId = (String) bean.get("sp_end_id");
				boolean read = approverDAO.isReadOnly(spplanId,
						assignee, SafeguardConstant.LP_SPECIAL_ENDPLAN);
				bean.set("isread", "" + read);
				if(judgeFinishRead(bean, spplanId, SafeguardConstant.LP_SPECIAL_ENDPLAN, assignee)){
					list2.add(bean);
				}
			}
		}
		return list2;
	}
	
	/**
	 * �ж��Ƿ��ѱ�����
	 * @param bean
	 * @param objectId
	 * @param objectType
	 * @param userId
	 * @return
	 */
	private boolean judgeFinishRead(BasicDynaBean bean, String objectId,
			String objectType, String userId) {
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
		//logger.info("*******flag:::" + flag + " objectId::" + objectId + " objectType::" + objectType);
		return flag;
	}

	/**
	 * �������ƻ���ֹ
	 * @param specialEndPlanBean
	 * @param userInfo
	 * @throws ServiceException
	 */
	public void addSpecialEndPlan(SpecialEndPlanBean specialEndPlanBean, UserInfo userInfo) throws ServiceException {
		String approver = specialEndPlanBean.getApproveId();
		String reader = specialEndPlanBean.getReader();
		//String spEndId = specialEndPlanBean.getId();
		String spId = specialEndPlanBean.getPlanId();
		String conId = userInfo.getDeptID();
		
		SafeguardSpplan safeguardSpplan = safeguardSpplanDao.findByUnique("spplanId", spId);
		String planId = safeguardSpplan.getPlanId();
		SafeguardPlan safeguardPlan = safeguardPlanDao.findByUnique("id", planId);
		String taskId = safeguardPlan.getSafeguardId();
		String[] readerPhones = specialEndPlanBean.getReaderPhones();
		String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, conId);
		
		SpecialEndPlan specialEndPlan = new SpecialEndPlan();
		try {
			BeanUtil.objectCopy(specialEndPlanBean, specialEndPlan);
		} catch (Exception e) {
			logger.error("SpecialEndPlanBeanת��ΪSpecialEndPlan����������Ϣ��" + e.getMessage());
			e.printStackTrace();
			throw new ServiceException();
		}
		//specialEndPlan.setEndType("1");
		specialEndPlan.setCreater(userInfo.getUserID());
		specialEndPlan.setCreateTime(new Date());
		
		SpecialEndPlan specialEndPlanSave = specialEndPlanDao.saveSpecialEndPlan(specialEndPlan);
		String spEndId = specialEndPlanSave.getId();
		// ���������
		replyApproverDAO.saveApproverOrReader(approver, spEndId,
				SafeguardConstant.APPROVE_MAN,
				SafeguardConstant.LP_SPECIAL_ENDPLAN);
		// ���泭����
		replyApproverDAO.saveApproverOrReader(reader, spEndId,
				SafeguardConstant.APPROVE_READ,
				SafeguardConstant.LP_SPECIAL_ENDPLAN);
		// ��ά������ά���������ƶ��ƻ�
		
		// JBPM
		Map variables = new HashMap();
		variables.put("assignee", userInfo.getUserID());
		//variables.put("transition", "start");
		workflowBo.createProcessInstance("safeguard_sub", spEndId, variables);
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard_sub."+spEndId);
		if (task != null
				&& SafeguardSubWorkflowBO.CHANGE_GUARD_PLAN_TASK.equals(task
						.getName())) {
			workflowBo.setVariables(task, "assignee", approver + "," + reader);
			workflowBo.completeTask(task.getId(), "approve");
			
			//����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("��Ѳ�ƻ���ֹ");//������̴�����˵��
			processHistoryBean.setTaskOutCome("approve");//��ӹ�����������Ϣ
			processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
			processHistoryBean.setNextOperateUserId(approver + "," + reader);//�����һ�������˵ı��
			processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		
		String name = safeguardTaskDao.getTaskNameByTaskId(taskId);
		String mobiles = safeguardTaskDao.getMobiles(approver);
		// ���Ͷ���
		String content = "�����ϡ�����һ������Ϊ\"" + name + "\"���Ϸ�����ֹ���ȴ�������ˡ�";
		sendMessage(content, mobiles);
		// ������ż�¼
		saveMessage(content, mobiles, spEndId,
				SafeguardConstant.LP_SPECIAL_ENDPLAN,
				ModuleCatalog.SAFEGUARD);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "�����ϡ�����һ������Ϊ\"" + name + "\"���Ϸ�����ֹ���ȴ����Ĳ��ġ�";
				sendMessage(content, readerPhones[i]);

				// ������ż�¼
				saveMessage(content, readerPhones[i], spEndId, SafeguardConstant.LP_SPECIAL_ENDPLAN,
						ModuleCatalog.SAFEGUARD);
			}
		}
	}
	
	/**
	 * ������Ѳ�ƻ���ֹ
	 * @param specialEndPlanBean
	 * @param userInfo
	 * @throws ServiceException
	 */
	public void approveSpecialEndPlan(SpecialEndPlanBean specialEndPlanBean, UserInfo userInfo) throws ServiceException {
		String id = specialEndPlanBean.getId();
		String approveResult = specialEndPlanBean.getApproveResult();
		String approveRemark = specialEndPlanBean.getApproveRemark();
		String operator = specialEndPlanBean.getOperator();
		String spId = specialEndPlanBean.getPlanId();
		String approvers = specialEndPlanBean.getApprovers();
		SpecialEndPlan specialEndPlan = specialEndPlanDao.findByUnique("id", id);
		Date endDate = specialEndPlan.getEndDate();
		String endType = specialEndPlan.getEndType();
		
		String creator = specialEndPlanBean.getCreater();
		String phone = "";
		
		SafeguardSpplan safeguardSpplan = safeguardSpplanDao.findByUnique("spplanId", spId);
		String planId = safeguardSpplan.getPlanId();
		SafeguardPlan safeguardPlan = safeguardPlanDao.findByUnique("id", planId);
		String conId = safeguardPlan.getContractorId();
		String taskId = safeguardPlan.getSafeguardId();
		String content = "";
		
		//�������Ѳ��ֹID������ID
		String otherEndPlanId = specialEndPlanDao.getOtherEndId(spId, id);
		//ȥ�����ĺ�ת��������˺��Լ�Ϊ�����˵����
		String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + approvers + "','" + userInfo.getUserID() + "')";
		String approverIdsReader1 = safeguardPlanDao.getApproverIds(id, SafeguardConstant.APPROVE_READ, SafeguardConstant.LP_SPECIAL_ENDPLAN, condition);
		String approverIdsReader2 = safeguardPlanDao.getApproverIds(otherEndPlanId, SafeguardConstant.APPROVE_READ, SafeguardConstant.LP_SPECIAL_ENDPLAN, condition);
		String approverIdsReader = approverIdsReader1.equals("")?approverIdsReader2:approverIdsReader1;
		// ���������Ϣ
		saveApproveInfo(id, SafeguardConstant.LP_SPECIAL_ENDPLAN, userInfo
				.getUserID(), approveResult, approveRemark);
		String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, conId);

		if ("approve".equals(operator)) {
			phone = getPhoneByUserId(creator);
			if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {

				// ���Ͷ�������
				content = "�����ϡ�����\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
						+ "\"���Ϸ�����ֹ��δͨ����ˡ�";
			} else {
				// ���Ͷ�������
				content = "�����ϡ�����\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
						+ "\"���Ϸ�����ֹ���Ѿ�ͨ����ˡ�";
				specialPlanDao.updateEndDateBySpId(spId, endDate);
			}
			
		} else {
			// �����������Ϣ
			replyApproverDAO.saveApproverOrReader(approvers, id,
					SafeguardConstant.APPROVE_TRANSFER_MAN,
					SafeguardConstant.LP_SPECIAL_ENDPLAN);

			// ���Ͷ���
			content = "�����ϡ�����һ������Ϊ\""
					+ safeguardTaskDao.getTaskNameByTaskId(taskId)
					+ "\"���Ϸ�����ֹ���ȴ�������ˡ�";
			phone = specialEndPlanBean.getMobiles();
			
		}
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard_sub."+id);
		if (task != null
				&& SafeguardSubWorkflowBO.CHANGE_GUARD_PLAN_APPROVE_TASK.equals(task
						.getName())) {
			System.out.println("���Ϸ�����ֹ����ˣ�" + task.getName());
			if (SafeguardConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				if(SpecialEndPlan.ENDPLAN.equals(endType)){
					//workflowBo.completeTask(id, "approve");
					workflowBo.setVariables(task, "transition", "end");
					workflowBo.completeTask(task.getId(), "passed");
					System.out.println("���Ϸ�����ֹ���ͨ����");
				}else{
					workflowBo.setVariables(task, "assignee", creator);
					workflowBo.setVariables(task, "transition", "re_planed");
					workflowBo.completeTask(task.getId(), "passed");
					System.out.println("�ȴ������ƶ���Ѳ�ƻ���");
				}
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("��Ѳ�ƻ���ֹ����ͨ��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("passed");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
				processHistoryBean.setNextOperateUserId(creator);//�����һ�������˵ı��
				processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				workflowBo.setVariables(task, "transition", "end");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("���Ϸ�����ֹ��˲�ͨ����");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("��Ѳ�ƻ���ֹ������ͨ��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("passed");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
				processHistoryBean.setNextOperateUserId(creator);//�����һ�������˵ı��
				processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (SafeguardConstant.APPROVE_RESULT_TRANSFER.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", approvers+","+approverIdsReader);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("���Ϸ�����ֹ�Ѿ�ת��");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("��Ѳ�ƻ���ֹת��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("transfer");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
				processHistoryBean.setNextOperateUserId(approvers);//�����һ�������˵ı��
				processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
		}
		
		// ���Ͷ���
		sendMessage(content, phone);
		// ������ż�¼
		saveMessage(content, phone, id,
				SafeguardConstant.LP_SPECIAL_ENDPLAN,
				ModuleCatalog.SAFEGUARD);
		
	}
	
	//������Ѳ�ƻ�
	public void approveSpecialPlan(SpecialEndPlanBean specialEndPlanBean, UserInfo userInfo, String spplan, String creator) throws ServiceException {
		String approveResult = specialEndPlanBean.getApproveResult();
		String approveRemark = specialEndPlanBean.getApproveRemark();
		String approvers = specialEndPlanBean.getApprovers();
		String operator = specialEndPlanBean.getOperator();
		
		//ȥ�����ĺ�ת��������˺��Լ�Ϊ�����˵����
		String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + approvers + "','" + userInfo.getUserID() + "')";
		String approverIdsReader = safeguardPlanDao.getApproverIds(spplan, SafeguardConstant.APPROVE_READ, SafeguardConstant.LP_SPECIAL_PLAN, condition);
		
		String phone = "";
		String content = "";
		// ���������Ϣ
		saveApproveInfo(spplan, SafeguardConstant.LP_SPECIAL_PLAN, userInfo
				.getUserID(), approveResult, approveRemark);
		String eid = safeguardConDao.getConIdBySpecialPlanId(spplan);
		String name = specialEndPlanDao.getSpecialNameBySpecialId(spplan);
		
		if ("approve".equals(operator)) {
			phone = getPhoneByUserId(creator);
			if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {

				// ���Ͷ�������
				content = "�����ϡ�����\"" + name	+ "\"��Ѳ�ƻ���δͨ����ˡ�";
			} else {
				// ���Ͷ�������
				content = "�����ϡ�����\"" + name + "\"��Ѳ�ƻ����Ѿ�ͨ����ˡ�";
			}
			
		} else {
			// �����������Ϣ
			replyApproverDAO.saveApproverOrReader(approvers, spplan,
					SafeguardConstant.APPROVE_TRANSFER_MAN,
					SafeguardConstant.LP_SPECIAL_PLAN);

			// ���Ͷ���
			content = "�����ϡ�����һ������Ϊ\"" + name + "\"��Ѳ�ƻ����ȴ�������ˡ�";
			phone = specialEndPlanBean.getMobiles();
			
		}
		
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard_replan_sub."+spplan);
		if (task != null
				&& SafeguardReplanSubWorkflowBO.REMAKE_GUARD_PLAN_APPROVE_TASK.equals(task
						.getName())) {
			System.out.println("��Ѳ�ƻ�����ˣ�" + task.getName());
			if (SafeguardConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				workflowBo.setVariables(task, "transition", "end");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("��Ѳ�ƻ����ͨ����");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("��Ѳ�ƻ�����ͨ��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("passed");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
				processHistoryBean.setNextOperateUserId(creator);//�����һ�������˵ı��
				processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", creator);
				workflowBo.completeTask(task.getId(), "not_passed");
				System.out.println("��Ѳ�ƻ���˲�ͨ����");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("��Ѳ�ƻ�������ͨ��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("not_passed");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
				processHistoryBean.setNextOperateUserId(creator);//�����һ�������˵ı��
				processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (SafeguardConstant.APPROVE_RESULT_TRANSFER.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", approvers+","+approverIdsReader);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("��Ѳ�ƻ��Ѿ�ת��");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("��Ѳ�ƻ�ת��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("transfer");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
				processHistoryBean.setNextOperateUserId(approvers);//�����һ�������˵ı��
				processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
		}
		
		// ���Ͷ���
		sendMessage(content, phone);
		// ������ż�¼
		saveMessage(content, phone, spplan,
				SafeguardConstant.LP_SPECIAL_PLAN,
				ModuleCatalog.SAFEGUARD);
		
	}
	
	/**
	 * �����Ѳ�ƻ���ֹ�б�
	 * @param userInfo
	 * @return
	 * @throws ServiceException
	 */
	public List getSpecialEndPlanList(UserInfo userInfo) throws ServiceException {
		String condition = "";
		logger.info("����û����ͣ�" + userInfo.getDeptype());
		if("2".equals(userInfo.getDeptype())){
			condition += " and userInfo.userid='" + userInfo.getUserID() + "'";
		}
		List list = specialEndPlanDao.getSpecialEndPlanList(condition);
		return list;
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
	 * ͨ���û�ID��ѯ�û����ֻ�����
	 * 
	 * @param userId���û�ID
	 * @return�������û��ֻ�����
	 */
	public String getPhoneByUserId(String userId) {
		String hql = "from UserInfo userInfo where userInfo.userID=?";
		List list = safeguardPlanDao.getHibernateTemplate().find(hql, userId);
		if (list != null && !list.equals("")) {
			UserInfo userInfo = (UserInfo) list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}
	
	/**
	 * ������Ѳ�ƻ���ֹ
	 * @param userInfo
	 * @param approverId
	 * @param endId
	 * @throws ServiceException
	 */
	public void readReply(UserInfo userInfo, String approverId, String endId) throws ServiceException{
		approverDAO.updateReader(approverId, endId, SafeguardConstant.LP_SPECIAL_ENDPLAN);
		
		SpecialEndPlan specialEndPlan = specialEndPlanDao.findByUnique("id", endId);
		String creator = specialEndPlan.getCreater();
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard_sub."+endId);
		String eid = specialEndPlanDao.getTaskConIdByEndId(endId);
		//����������ʷ
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.setProcessAction("��Ѳ�ƻ���ֹ����");//������̴�����˵��
		processHistoryBean.setTaskOutCome("");//��ӹ�����������Ϣ
		processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
		processHistoryBean.setNextOperateUserId("");//�����һ�������˵ı��
		processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
	/**
	 * ������Ѳ�ƻ�
	 * @param userInfo
	 * @param approverId
	 * @param spId
	 * @throws ServiceException
	 */
	public void readSpecialReply(UserInfo userInfo, String approverId, String spId) throws ServiceException{
		approverDAO.updateReader(approverId, spId, SafeguardConstant.LP_SPECIAL_PLAN);
		
		/*SpecialEndPlan specialEndPlan = specialEndPlanDao.findByUnique("id", spId);
		String creator = specialEndPlan.getCreater();*/
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard_replan_sub."+spId);
		String eid = safeguardConDao.getConIdBySpecialPlanId(spId);
		//����������ʷ
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.setProcessAction("��Ѳ�ƻ�����");//������̴�����˵��
		processHistoryBean.setTaskOutCome("");//��ӹ�����������Ϣ
		processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
		processHistoryBean.setNextOperateUserId("");//�����һ�������˵ı��
		processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
}
