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
	 * 制定盯防计划后
	 * 1.触发工作流
	 * 2.更新隐患状态
	 * 3.添加一条HiddangerPlan记录
	 * 4.更新隐患主表盯防计划id
	 * 5.保存审核人和抄送人
	 */
	public void makePlanWorkFlow(UserInfo userInfo, String approver, String reader, String hiddangerId, boolean needApprove, SpecialPlan specialPlan){
		//更新隐患主表特巡计划id
		HiddangerRegist regist = dao.findUniqueByProperty("id",hiddangerId);
		regist.setSplanId(specialPlan.getId());
		
		//更新隐患状态
		if(needApprove){
			regist.setHiddangerState(HiddangerConstant.PLANAPPROVE);
		}else{
			regist.setHiddangerState(HiddangerConstant.WAIT);
		}
		dao.save(regist);
		
		//如果没有记录，则添加一条隐患计划记录
		if(!hpDao.getExistHiddangerPlan(hiddangerId, specialPlan.getId())){
			HiddangerPlan hPlan = new HiddangerPlan();
			hPlan.setHiddangerId(hiddangerId);
			hPlan.setPlanId(specialPlan.getId());
			hpDao.save(hPlan);
		}
		
		//删除审核人抄送人
		replyApproverDAO.deleteList(hiddangerId, "LP_HIDDANGER_MAKEPLAN");
		
		if(needApprove){
			//保存审核人，抄送人
			replyApproverDAO.saveApproverOrReader(approver, hiddangerId, CommonConstant.APPROVE_MAN, "LP_HIDDANGER_MAKEPLAN");
			replyApproverDAO.saveApproverOrReader(reader, hiddangerId, CommonConstant.APPROVE_READ, "LP_HIDDANGER_MAKEPLAN");
		
			//短信
			String content = "【隐患盯防】您有一个名称为\""+specialPlan.getPlanName()+"\"的隐患盯防计划等待您的审批！";
			List<String> mobiles = getMobile(Arrays.asList(approver.split(",")));
			super.sendMessage(content, mobiles);
		}
		
		//流程
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
		
		//流程历史
		if(needApprove){
			workflowBo.saveProcessHistory(userInfo, regist, task, assinee, "toapprove", "计划审核", "");
		}else{
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "toexecute", "计划执行", "");
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
			content = "盯防计划审核未通过，";
		}else if(approveInfo.getApproveResult().equals("1")){
			state = HiddangerConstant.WAIT;
			content = "盯防计划审核已通过，";
		}
		//更新主表的状态
		saveState(hiddangerId, state);
		
		//保存审核信息
		approveDAO.saveApproveInfo(approveInfo);
		
		//流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), hiddangerId);
		if(approveInfo.getApproveResult().equals("0")){
			workflowBo.setVariables(task, "assignee", getDept(hiddangerId));
			workflowBo.completeTask(task.getId(), "not_passed");
		}else if(approveInfo.getApproveResult().equals("1")){
			workflowBo.setVariables(task, "assignee", getDept(hiddangerId));
			workflowBo.setVariables(task, "transition", "toexecute");
			workflowBo.completeTask(task.getId(), "passed");
		}
		
		//流程历史
		HiddangerRegist regist = dao.findUniqueByProperty("id", hiddangerId);
		if(approveInfo.getApproveResult().equals("0")){
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "not_passed", "计划审核未通过", "");
		}else{
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "passed", "计划审核通过", "");
		}
		
		//短信提醒
		//发送给制定计划人
		content += "【隐患盯防】您有一个名称为\""+getRegist(hiddangerId).getName()+"\"的隐患等待您的处理！";
		String creator = specialPlanDao.findUniqueByProperty("id", planId).getCreator();
		String phone = dao.getPhoneFromUserid(creator);
		super.sendMessage(content, phone);
	}
	
	public void transferApprove(String hiddangerId, String approver, UserInfo userInfo){
		//保存审核人
		replyApproverDAO.saveApproverOrReader(approver, hiddangerId, CommonConstant.APPROVE_MAN, "LP_HIDDANGER_MAKEPLAN");
		
		//取得抄送人
		Set<String> all = replyApproverDAO.getApprover(hiddangerId, CommonConstant.APPROVE_READ, "LP_HIDDANGER_MAKEPLAN");
		all.add(approver);

		//流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), hiddangerId);
		workflowBo.setVariables(task, "assignee", StringUtils.join(all.iterator(), ","));
		workflowBo.setVariables(task, "transition", "transfer");
		workflowBo.completeTask(task.getId(), "passed");
		
		//流程历史
		HiddangerRegist regist = dao.findUniqueByProperty("id", hiddangerId);
		workflowBo.saveProcessHistory(userInfo, regist, task, approver, "passed", "计划转审", "");
		
		//短信提醒
		//发送给转审人
		String content = "【隐患盯防】用户"+userInfo.getUserName()+"将\""+getRegist(hiddangerId).getName()+"\"的隐患盯防计划转交给您进行审核！";
		List<String> mobiles = getMobile(Arrays.asList(approver.split(",")));
		super.sendMessage(content, mobiles);
	}
	
	/**
	 * 终止盯防计划
	 * @param userInfo
	 * @param hiddangerId
	 * @param endType
	 * @param reason
	 */
	public void endWatchPlan(UserInfo userInfo, String hiddangerId, HiddangerEndplan endplan, String approver, String reader){
		replyApproverDAO.deleteList(hiddangerId, "LP_HIDDANGER_ENDPLAN");
		
		//保存终止计划对象
		String planId = getRegist(hiddangerId).getSplanId();
		endplan.setPlanId(planId);
		endplan.setCreater(userInfo.getUserID());
		epDao.save(endplan);
		
		//保存审核人
		replyApproverDAO.saveApproverOrReader(approver, hiddangerId, CommonConstant.APPROVE_MAN, "LP_HIDDANGER_ENDPLAN");
		//保存抄送人
		replyApproverDAO.saveApproverOrReader(reader, hiddangerId, CommonConstant.APPROVE_READ, "LP_HIDDANGER_ENDPLAN");
		
		//更新隐患主表状态
		saveState(hiddangerId, HiddangerConstant.ENDPLAN);
		
		//流程
		String assinee = "";
		if(StringUtils.isNotBlank(reader)){
			assinee = approver+","+reader;
		}else{
			assinee = approver;
		}
		Task task = workflowBo.getHandleTaskForId(userInfo.getDeptID(), hiddangerId);
		workflowBo.setVariables(task, "assignee", assinee);
		workflowBo.completeTask(task.getId(), "approve");
		
		//流程历史
		HiddangerRegist regist = dao.findUniqueByProperty("id", hiddangerId);
		workflowBo.saveProcessHistory(userInfo, regist, task, assinee, "approve", "终止计划", "");
		
		//短信提醒
		//发送给审核人
		String content = "【隐患盯防】您有一个名称为\""+getRegist(hiddangerId).getName()+"\"的隐患盯防终止计划等待您的处理！";
		List<String> mobiles = getMobile(Arrays.asList(assinee.split(",")));  
		super.sendMessage(content, mobiles);
	}
	
	public void endPlanApprove(ApproveInfo approveInfo, UserInfo userInfo, String hiddangerId){
		approveInfo.setApproveTime(new Date());
		approveInfo.setObjectType("LP_HIDDANGER_ENDPLAN");
		String planId = dao.findUniqueByProperty("id", hiddangerId).getSplanId();
		approveInfo.setObjectId(planId);
		//保存审核信息
		approveDAO.saveApproveInfo(approveInfo);
		
		String content = "";
		String state = "";
		String endType = getEndPlan(planId).getEndType();
		if(approveInfo.getApproveResult().equals("0")){
			state = HiddangerConstant.WAIT;
			content = "盯防计划终止申请审核未通过，";
		}else if(approveInfo.getApproveResult().equals("1")){
			content = "盯防计划终止申请审核已通过，";
			if(endType.equals("1")){
				state = HiddangerConstant.CLOSE;
			}else{
				setNullPlanId(hiddangerId);
				state = HiddangerConstant.MAKEPLAN;
			}
		}
		//更新主表的状态
		saveState(hiddangerId, state);
		
		//流程
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
		
		//流程历史
		HiddangerRegist regist = dao.findUniqueByProperty("id", hiddangerId);
		if(approveInfo.getApproveResult().equals("0")){
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "not_passed", "计划终止审核未通过", "");
		}else{
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "passed", "计划终止审核通过", "");
		}
		
		//终止审核通过，更新计划结束日期为当前日期
		if(approveInfo.getApproveResult().equals("1")){
			SpecialPlan plan = specialPlanDao.get(planId);
			plan.setEndDate(new Date());
			specialPlanDao.save(plan);
		}
		
		//短信提醒
		//发送给制定计划人
		content = "【隐患盯防】您有一个名称为\""+getRegist(hiddangerId).getName()+"\"的隐患"+content+",请您及时处理！";
		String creator = getEndPlan(planId).getCreater();
		String phone = dao.getPhoneFromUserid(creator);
		super.sendMessage(content, phone);
	}
	
	public void endPlanTransfer(String hiddangerId, String approver, UserInfo userInfo){
		//保存审核人
		replyApproverDAO.saveApproverOrReader(approver, hiddangerId, CommonConstant.APPROVE_MAN, "LP_HIDDANGER_ENDPLAN");
		
		//取得抄送人
		Set<String> all = replyApproverDAO.getApprover(hiddangerId, CommonConstant.APPROVE_READ, "LP_HIDDANGER_ENDPLAN");
		all.add(approver);
		
		//流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), hiddangerId);
		workflowBo.setVariables(task, "assignee", StringUtils.join(all.iterator(), ","));
		workflowBo.setVariables(task, "transition", "transfer");
		workflowBo.completeTask(task.getId(), "passed");
		
		//流程历史
		HiddangerRegist regist = dao.findUniqueByProperty("id", hiddangerId);
		workflowBo.saveProcessHistory(userInfo, regist, task, approver, "passed", "计划终止转审", "");
		
		//短信提醒
		//发送给转审人
		String content = "【隐患盯防】用户"+userInfo.getUserName()+"将\""+getRegist(hiddangerId).getName()+"\"的隐患盯防计划终止申请转交给您进行审核！";
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
