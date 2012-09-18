package com.cabletech.linepatrol.watch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.hiddanger.dao.HiddangerEndplanDao;
import com.cabletech.linepatrol.hiddanger.dao.HiddangerPlanDao;
import com.cabletech.linepatrol.hiddanger.dao.HiddangerRegistDao;
import com.cabletech.linepatrol.hiddanger.model.HiddangerEndplan;
import com.cabletech.linepatrol.hiddanger.model.HiddangerPlan;
import com.cabletech.linepatrol.hiddanger.model.HiddangerRegist;
import com.cabletech.linepatrol.hiddanger.service.HiddangerConstant;
import com.cabletech.linepatrol.hiddanger.workflow.HiddangerWorkflowBO;
import com.cabletech.linepatrol.specialplan.dao.SpecialPlanDao;
import com.cabletech.linepatrol.specialplan.module.SpecialPlan;

@Service
@Transactional
public class WatchManager extends EntityManager<HiddangerRegist, String>{
	@Resource(name="hiddangerRegistDao")
	private HiddangerRegistDao dao;
	@Resource(name="hiddangerPlanDao")
	private HiddangerPlanDao hpDao;
	@Resource(name="hiddangerEndplanDao")
	private HiddangerEndplanDao epDao;
	@Autowired
	private HiddangerWorkflowBO workflowBo;
	@Resource(name="approveDAO")
	private ApproveDAO approveDAO;
	@Resource(name="specialPlanDao")
	private SpecialPlanDao specialPlanDao;
	@Resource(name="replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	@Resource(name="smHistoryDAO")
	private SmHistoryDAO historyDAO;
	
	/**
	 * �ƶ������ƻ���
	 * 1.����������
	 * 2.��������״̬
	 * 3.���һ��HiddangerPlan��¼
	 * 4.���������������ƻ�id
	 * 5.��������˺ͳ�����
	 */
	public void makePlanWorkFlow(UserInfo userInfo, String approver, String reader, String hiddangerId, boolean needApprove, SpecialPlan specialPlan){
		//��������������Ѳ�ƻ�id
		HiddangerRegist regist = dao.findUniqueByProperty("id",hiddangerId);
		regist.setSplanId(specialPlan.getId());
		
		//��������״̬
		if(needApprove){
			regist.setHiddangerState(HiddangerConstant.PLANAPPROVE);
		}else{
			regist.setHiddangerState(HiddangerConstant.WAIT);
		}
		dao.save(regist);
		
		//���û�м�¼�������һ�������ƻ���¼
		if(!hpDao.getExistHiddangerPlan(hiddangerId, specialPlan.getId())){
			HiddangerPlan hPlan = new HiddangerPlan();
			hPlan.setHiddangerId(hiddangerId);
			hPlan.setPlanId(specialPlan.getId());
			hpDao.save(hPlan);
		}
		
		//ɾ������˳�����
		replyApproverDAO.deleteList(hiddangerId, "LP_HIDDANGER_MAKEPLAN");
		
		if(needApprove){
			//��������ˣ�������
			replyApproverDAO.saveApproverOrReader(approver, hiddangerId, CommonConstant.APPROVE_MAN, "LP_HIDDANGER_MAKEPLAN");
			replyApproverDAO.saveApproverOrReader(reader, hiddangerId, CommonConstant.APPROVE_READ, "LP_HIDDANGER_MAKEPLAN");
		
			//����
			String content = "����������������һ������Ϊ\""+specialPlan.getPlanName()+"\"�����������ƻ��ȴ�����������";
			List<String> mobiles = getMobile(Arrays.asList(approver.split(",")));
			super.sendMessage(content, mobiles);
		}
		
		//����
		Task task = workflowBo.getHandleTaskForId(userInfo.getDeptID(), hiddangerId);
		String assinee = "";
		if(StringUtils.isNotBlank(reader)){
			assinee = approver+","+reader;
		}else{
			assinee = approver;
		}
		if(needApprove){
			workflowBo.setVariables(task, "assignee", assinee);
			workflowBo.completeTask(task.getId(), "toapprove");
		}else{
			workflowBo.setVariables(task, "assignee", regist.getTreatDepartment());
			workflowBo.completeTask(task.getId(), "toexecute");
		}
		
		//������ʷ
		if(needApprove){
			workflowBo.saveProcessHistory(userInfo, regist, task, assinee, "toapprove", "�ƻ����", "");
		}else{
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "toexecute", "�ƻ�ִ��", "");
		}
	}
	
	public String getPlanId(String hiddangerId){
		HiddangerRegist regist = dao.findUniqueByProperty("id",hiddangerId);
		return regist.getSplanId();
	}
	
	public void approve(ApproveInfo approveInfo, UserInfo userInfo, String hiddangerId){
		approveInfo.setApproveTime(new Date());
		approveInfo.setObjectType("LP_HIDDANGER_PLAN");
		
		String planId = dao.findUniqueByProperty("id", hiddangerId).getSplanId();
		approveInfo.setObjectId(planId);
		
		String content = "";
		String state = "";
		if(approveInfo.getApproveResult().equals("0")){
			state = HiddangerConstant.MAKEPLAN;
			content = "�����ƻ����δͨ����";
		}else if(approveInfo.getApproveResult().equals("1")){
			state = HiddangerConstant.WAIT;
			content = "�����ƻ������ͨ����";
		}
		//���������״̬
		saveState(hiddangerId, state);
		
		//���������Ϣ
		approveDAO.saveApproveInfo(approveInfo);
		
		//����
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), hiddangerId);
		if(approveInfo.getApproveResult().equals("0")){
			workflowBo.setVariables(task, "assignee", getDept(hiddangerId));
			workflowBo.completeTask(task.getId(), "not_passed");
		}else if(approveInfo.getApproveResult().equals("1")){
			workflowBo.setVariables(task, "assignee", getDept(hiddangerId));
			workflowBo.setVariables(task, "transition", "toexecute");
			workflowBo.completeTask(task.getId(), "passed");
		}
		
		//������ʷ
		HiddangerRegist regist = dao.findUniqueByProperty("id", hiddangerId);
		if(approveInfo.getApproveResult().equals("0")){
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "not_passed", "�ƻ����δͨ��", "");
		}else{
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "passed", "�ƻ����ͨ��", "");
		}
		
		//��������
		//���͸��ƶ��ƻ���
		content += "����������������һ������Ϊ\""+getRegist(hiddangerId).getName()+"\"�������ȴ����Ĵ���";
		String creator = specialPlanDao.findUniqueByProperty("id", planId).getCreator();
		String phone = dao.getPhoneFromUserid(creator);
		super.sendMessage(content, phone);
	}
	
	public void transferApprove(String hiddangerId, String approver, UserInfo userInfo){
		//���������
		replyApproverDAO.saveApproverOrReader(approver, hiddangerId, CommonConstant.APPROVE_MAN, "LP_HIDDANGER_MAKEPLAN");
		
		//ȡ�ó�����
		Set<String> all = replyApproverDAO.getApprover(hiddangerId, CommonConstant.APPROVE_READ, "LP_HIDDANGER_MAKEPLAN");
		all.add(approver);

		//����
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), hiddangerId);
		workflowBo.setVariables(task, "assignee", StringUtils.join(all.iterator(), ","));
		workflowBo.setVariables(task, "transition", "transfer");
		workflowBo.completeTask(task.getId(), "passed");
		
		//������ʷ
		HiddangerRegist regist = dao.findUniqueByProperty("id", hiddangerId);
		workflowBo.saveProcessHistory(userInfo, regist, task, approver, "passed", "�ƻ�ת��", "");
		
		//��������
		//���͸�ת����
		String content = "�������������û�"+userInfo.getUserName()+"��\""+getRegist(hiddangerId).getName()+"\"�����������ƻ�ת������������ˣ�";
		List<String> mobiles = getMobile(Arrays.asList(approver.split(",")));
		super.sendMessage(content, mobiles);
	}
	
	/**
	 * ��ֹ�����ƻ�
	 * @param userInfo
	 * @param hiddangerId
	 * @param endType
	 * @param reason
	 */
	public void endWatchPlan(UserInfo userInfo, String hiddangerId, HiddangerEndplan endplan, String approver, String reader){
		replyApproverDAO.deleteList(hiddangerId, "LP_HIDDANGER_ENDPLAN");
		
		//������ֹ�ƻ�����
		String planId = getRegist(hiddangerId).getSplanId();
		endplan.setPlanId(planId);
		endplan.setCreater(userInfo.getUserID());
		epDao.save(endplan);
		
		//���������
		replyApproverDAO.saveApproverOrReader(approver, hiddangerId, CommonConstant.APPROVE_MAN, "LP_HIDDANGER_ENDPLAN");
		//���泭����
		replyApproverDAO.saveApproverOrReader(reader, hiddangerId, CommonConstant.APPROVE_READ, "LP_HIDDANGER_ENDPLAN");
		
		//������������״̬
		saveState(hiddangerId, HiddangerConstant.ENDPLAN);
		
		//����
		String assinee = "";
		if(StringUtils.isNotBlank(reader)){
			assinee = approver+","+reader;
		}else{
			assinee = approver;
		}
		Task task = workflowBo.getHandleTaskForId(userInfo.getDeptID(), hiddangerId);
		workflowBo.setVariables(task, "assignee", assinee);
		workflowBo.completeTask(task.getId(), "approve");
		
		//������ʷ
		HiddangerRegist regist = dao.findUniqueByProperty("id", hiddangerId);
		workflowBo.saveProcessHistory(userInfo, regist, task, assinee, "approve", "��ֹ�ƻ�", "");
		
		//��������
		//���͸������
		String content = "����������������һ������Ϊ\""+getRegist(hiddangerId).getName()+"\"������������ֹ�ƻ��ȴ����Ĵ���";
		List<String> mobiles = getMobile(Arrays.asList(assinee.split(",")));  
		super.sendMessage(content, mobiles);
	}
	
	public void endPlanApprove(ApproveInfo approveInfo, UserInfo userInfo, String hiddangerId){
		approveInfo.setApproveTime(new Date());
		approveInfo.setObjectType("LP_HIDDANGER_ENDPLAN");
		String planId = dao.findUniqueByProperty("id", hiddangerId).getSplanId();
		approveInfo.setObjectId(planId);
		//���������Ϣ
		approveDAO.saveApproveInfo(approveInfo);
		
		String content = "";
		String state = "";
		String endType = getEndPlan(planId).getEndType();
		if(approveInfo.getApproveResult().equals("0")){
			state = HiddangerConstant.WAIT;
			content = "�����ƻ���ֹ�������δͨ����";
		}else if(approveInfo.getApproveResult().equals("1")){
			content = "�����ƻ���ֹ���������ͨ����";
			if(endType.equals("1")){
				state = HiddangerConstant.CLOSE;
			}else{
				setNullPlanId(hiddangerId);
				state = HiddangerConstant.MAKEPLAN;
			}
		}
		//���������״̬
		saveState(hiddangerId, state);
		
		//����
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), hiddangerId);
		if(approveInfo.getApproveResult().equals("0")){
			workflowBo.setVariables(task, "assignee", getDept(hiddangerId));
			workflowBo.completeTask(task.getId(), "not_passed");
		}else if(approveInfo.getApproveResult().equals("1")){
			if(endType.equals("1")){
				workflowBo.setVariables(task, "assignee", getDept(hiddangerId));
				workflowBo.setVariables(task, "transition", "toclose");
				workflowBo.completeTask(task.getId(), "passed");
			}else{
				workflowBo.setVariables(task, "assignee", getDept(hiddangerId));
				workflowBo.setVariables(task, "transition", "makeplan");
				workflowBo.completeTask(task.getId(), "passed");
			}
		}
		
		//������ʷ
		HiddangerRegist regist = dao.findUniqueByProperty("id", hiddangerId);
		if(approveInfo.getApproveResult().equals("0")){
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "not_passed", "�ƻ���ֹ���δͨ��", "");
		}else{
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "passed", "�ƻ���ֹ���ͨ��", "");
		}
		
		//��ֹ���ͨ�������¼ƻ���������Ϊ��ǰ����
		if(approveInfo.getApproveResult().equals("1")){
			SpecialPlan plan = specialPlanDao.get(planId);
			plan.setEndDate(new Date());
			specialPlanDao.save(plan);
		}
		
		//��������
		//���͸��ƶ��ƻ���
		content = "����������������һ������Ϊ\""+getRegist(hiddangerId).getName()+"\"������"+content+",������ʱ����";
		String creator = getEndPlan(planId).getCreater();
		String phone = dao.getPhoneFromUserid(creator);
		super.sendMessage(content, phone);
	}
	
	public void endPlanTransfer(String hiddangerId, String approver, UserInfo userInfo){
		//���������
		replyApproverDAO.saveApproverOrReader(approver, hiddangerId, CommonConstant.APPROVE_MAN, "LP_HIDDANGER_ENDPLAN");
		
		//ȡ�ó�����
		Set<String> all = replyApproverDAO.getApprover(hiddangerId, CommonConstant.APPROVE_READ, "LP_HIDDANGER_ENDPLAN");
		all.add(approver);
		
		//����
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), hiddangerId);
		workflowBo.setVariables(task, "assignee", StringUtils.join(all.iterator(), ","));
		workflowBo.setVariables(task, "transition", "transfer");
		workflowBo.completeTask(task.getId(), "passed");
		
		//������ʷ
		HiddangerRegist regist = dao.findUniqueByProperty("id", hiddangerId);
		workflowBo.saveProcessHistory(userInfo, regist, task, approver, "passed", "�ƻ���ֹת��", "");
		
		//��������
		//���͸�ת����
		String content = "�������������û�"+userInfo.getUserName()+"��\""+getRegist(hiddangerId).getName()+"\"�����������ƻ���ֹ����ת������������ˣ�";
		List<String> mobiles = getMobile(Arrays.asList(approver.split(",")));
		super.sendMessage(content, mobiles);
	}
	
	public HiddangerEndplan loadEndPlan(String id){
		HiddangerRegist regist = dao.findUniqueByProperty("id",id);
		String planId = regist.getSplanId();
		HiddangerEndplan endplan = getEndPlan(planId);
		return endplan == null ? new HiddangerEndplan() : endplan;
	}
	
	public HiddangerEndplan getEndPlan(String id){
		return epDao.getEndplan(id);
	}
	
	public HiddangerEndplan getEndPlanFromHiddangerId(String hiddangerId){
		return getEndPlan(getRegist(hiddangerId).getSplanId());
	}
	
	public void saveState(String id, String state){
		HiddangerRegist regist = dao.get(id);
		regist.setHiddangerState(state);
		dao.save(regist);
	}
	
	public void setNullPlanId(String id){
		HiddangerRegist regist = dao.get(id);
		regist.setSplanId(null);
		dao.save(regist);
	}
	
	public String getDept(String id){
		HiddangerRegist regist = dao.findUniqueByProperty("id",id);
		return regist.getTreatDepartment();
	}
	
	public HiddangerRegist getRegist(String id){
		return dao.findUniqueByProperty("id",id);
	}
	
	public List<String> getMobile(List<String> approverIds){
		List<String> principals = new ArrayList<String>();
		for(String id : approverIds){
			String phone = dao.getPhoneFromUserid(id);
			if(StringUtils.isNotBlank(phone)){
				principals.add(phone);
			}
		}
		return principals;
	}
	
	@Override
	protected HibernateDao<HiddangerRegist, String> getEntityDao() {
		return dao;
	}
}
