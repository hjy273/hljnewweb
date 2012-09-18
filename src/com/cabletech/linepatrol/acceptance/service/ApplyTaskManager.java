package com.cabletech.linepatrol.acceptance.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import com.cabletech.linepatrol.acceptance.beans.QueryReinspectBean;
import com.cabletech.linepatrol.acceptance.dao.ApplyCableDao;
import com.cabletech.linepatrol.acceptance.dao.ApplyContractorDao;
import com.cabletech.linepatrol.acceptance.dao.ApplyPipeDao;
import com.cabletech.linepatrol.acceptance.dao.ApplyTaskDao;
import com.cabletech.linepatrol.acceptance.dao.CableTaskDao;
import com.cabletech.linepatrol.acceptance.dao.PipeTaskDao;
import com.cabletech.linepatrol.acceptance.dao.SubflowDao;
import com.cabletech.linepatrol.acceptance.model.AllotLog;
import com.cabletech.linepatrol.acceptance.model.Apply;
import com.cabletech.linepatrol.acceptance.model.ApplyCable;
import com.cabletech.linepatrol.acceptance.model.ApplyContractor;
import com.cabletech.linepatrol.acceptance.model.ApplyPipe;
import com.cabletech.linepatrol.acceptance.model.ApplyTask;
import com.cabletech.linepatrol.acceptance.model.CableTask;
import com.cabletech.linepatrol.acceptance.model.PipeTask;
import com.cabletech.linepatrol.acceptance.model.Subflow;
import com.cabletech.linepatrol.acceptance.workflow.AcceptanceFlow;
import com.cabletech.linepatrol.appraise.dao.AllotLogDao;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;

@Service
public class ApplyTaskManager extends EntityManager<ApplyTask, String>{
	@Resource(name="applyTaskDao")
	private ApplyTaskDao dao;
	@Resource(name="applyCableDao")
	private ApplyCableDao applyCableDao;
	@Resource(name="applyPipeDao")
	private ApplyPipeDao applyPipeDao;
	@Resource(name="cableTaskDao")
	private CableTaskDao cableTaskDao;
	@Resource(name="pipeTaskDao")
	private PipeTaskDao pipeTaskDao;
	@Resource(name="applyContractorDao")
	private ApplyContractorDao acDao;
	@Resource(name="replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	@Autowired
	private AcceptanceFlow workflowBo;
	@Resource(name="applyManager")
	private ApplyManager am;
	@Resource(name="subflowDao")
	private SubflowDao subflowDao;
	@Resource(name="cableTaskManager")
	private CableTaskManager ctm;
	@Resource(name="pipeTaskManager")
	private PipeTaskManager ptm;
	@Resource(name="smHistoryDAO")
	private SmHistoryDAO historyDAO;
	@Resource(name="approveDAO")
	private ApproveDAO approveDAO;
	@Resource(name="allotLogDao")
	private AllotLogDao allotLogDao;
	
	//复验得到未通过的光缆
	@Transactional(readOnly=true)
	public List<ApplyCable> loadReinspectCables(QueryReinspectBean queryInBean){
		return applyCableDao.getNotPassedApplyCable(queryInBean);
	}
	
	//复验得到未通过的管道
	@Transactional(readOnly=true)
	public List<ApplyPipe> loadReinspectPipes(QueryReinspectBean queryInBean){
		return applyPipeDao.getNotPassedApplyPipe(queryInBean);
	}
	
	//复验中添加光缆
	@Transactional(readOnly=true)
	public Apply addReinspectCable(Apply apply, List<ApplyCable> cables, String cableId){
		apply.clearPipe();
		apply.clearCable();
		
		List<String> cableIds = Arrays.asList(cableId.split(","));
		for(String s : cableIds){
			for(ApplyCable c : cables){
				if(s.equals(c.getId())){
					apply.addCable(c);
				}
			}
		}
		return apply;
	}
	
	//复验中添加管道
	@Transactional(readOnly=true)
	public Apply addReinspectPipe(Apply apply, List<ApplyPipe> pipes, String pipeId){
		apply.clearPipe();
		apply.clearCable();
		
		List<String> pipeIds = Arrays.asList(pipeId.split(","));
		for(String s : pipeIds){
			for(ApplyPipe p : pipes){
				if(s.equals(p.getId()))
					apply.addPipe(p);
			}
		}
		return apply;
	}
	
	//保存任务，
	//验收任务分配，分配方式包括制定和选择两种。
	@Transactional
	public void saveApplyTask(UserInfo userInfo, String type,String remark, String id, Map<String, String> allots, String choose){
		if(type.equals(AcceptanceConstant.SPECIFY)){
			//指定代维，为指定代维写入任务
			Map<String, String> contractorId2ObjectMap = displace(allots);
			saveSpecifyTask(contractorId2ObjectMap, userInfo, id,remark);
		}else{
			//选择代维，提供任务认领代维
			saveChooseTask(choose, userInfo, id,remark);
		}
	}
	//写入验收申请分配代维任务
	private void saveSpecifyTask(Map<String,String> allotMap, UserInfo userInfo, String id,String remark){
		//保存任务,和任务关联表
		Apply apply =  am.loadApply(id);
		saveCommonTask(allotMap, userInfo, apply);
		
		//保存申请代维关联表
		List<String> contractors = new ArrayList<String>();
		for(Map.Entry<String, String> e : allotMap.entrySet()){
			contractors.add(e.getKey());
			ApplyContractor ac = new ApplyContractor();
			ac.setContractorId(e.getKey());
			ac.setApplyId(id);
			acDao.save(ac);
		}
		
		//保存主表流程状态
		apply.setProcessState(AcceptanceConstant.RECORD);
//		am.saveState(id, AcceptanceConstant.RECORD);
		apply.setRemark(remark);
		am.saveApply(apply);
		//流程
	
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), apply.getProcessInstanceId());
		workflowBo.setVariables(task, "transition", "specify");
		workflowBo.completeTask(task.getId(), "todecision");
		
		//流程历史
		workflowBo.saveProcessHistory(userInfo, apply, task, StringUtils.join(contractors.toArray(), ","), "todecision", "任务分配(指定代维)", "");
		
		//保存子流程任务表，启动子流程和发送短信
		saveSubflow(contractors,apply);
	}
	

	/**
	 * 主流程结束后启动验收子流程。
	 * @param allots 参与验收的代维公司
	 * @param apply 验收申请信息。
	 */
	private void saveSubflow(List<String> allots,Apply apply){
		
		//保存子流程任务表
		for(String contractorId : allots){
			Subflow subflow = new Subflow();
			subflow.setApplyId(apply.getId());
			subflow.setContractorId(contractorId);
			subflowDao.save(subflow);
			
			//启动子流程
			Map<String,Object> variables = new HashMap<String,Object>();
			variables.put("assignee", contractorId);
			String processId = workflowBo.createProcessInstance("acceptancesubflow", subflow.getId(), variables);
			
			subflow.setProcessInstanceId(processId);
			subflow.setProcessState(AcceptanceConstant.RECORD);
			subflowDao.save(subflow);
			
			//短信提醒
			String content = "【验收交维】贵单位有一个名称为\""+apply.getName()+"\"的申请等待您的录入验收数据！";
			List<String> mobileList = am.getMobileFromDeptId(contractorId);
			List<String> mobiles = new ArrayList<String>();
			for(String mobile:mobileList){
				mobiles.addAll(com.cabletech.commons.util.StringUtils.string2List(mobile, ","));
			}
			try{
			this.sendMessage(content, mobiles);
			}catch(Exception ex){
	            logger.error("发送短信失败:",ex);
	        }
		}
	}
	
	private void saveChooseTask(String choose, UserInfo userInfo, String id,String remark){
		
		Apply apply = am.loadApply(id);
		//此时不保存任务和任务关联表
		//保存申请代维关联表
		for(String contractorId : choose.split(",")){
			ApplyContractor ac = new ApplyContractor();
			ac.setContractorId(contractorId);
			ac.setApplyId(id);
			acDao.save(ac);
		}
		
		//保存主表流程状态
		apply.setProcessState(AcceptanceConstant.CLAIM);
		apply.setRemark(remark);
		am.saveApply(apply);
		
		//流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), apply.getProcessInstanceId());
		workflowBo.setVariables(task, "assignee", choose);
		workflowBo.setVariables(task, "transition", "choose");
		workflowBo.completeTask(task.getId(), "todecision");
		
		//流程历史
		workflowBo.saveProcessHistory(userInfo, apply, task, choose, "todecision", "任务分配(选择代维)", "");
		
		//短信提醒
		for(String contractorId : choose.split(",")){
			String content = "【验收交维】贵单位有一个名称为\""+apply.getName()+"\"的验收任务等待您的认领！";
			List<String> mobileList = am.getMobileFromDeptId(contractorId);
			List<String> mobiles = new ArrayList<String>();
			for(String mobile:mobileList){
				mobiles.addAll(com.cabletech.commons.util.StringUtils.string2List(mobile, ","));
			}
			try{
			this.sendMessage(content, mobiles);
			}catch(Exception ex){
	            logger.error("发送短信失败:",ex);
	        }
		}
	}
	
	//保存验收代维信息
	private void saveCommonTask(Map<String,String> allotMap, UserInfo assigner, Apply apply){
		String contractorId="";
		ApplyTask task = null;
		CableTask cTask = null;
		PipeTask pTask = null;
		
		for(Map.Entry<String, String> e : allotMap.entrySet()){
			String[] objectIds = e.getValue().split(",");
			contractorId = e.getKey();
			task = new ApplyTask();
			task.setApplyId(apply.getId());
			task.setContractorId(contractorId);
			task.setAssigner(assigner.getUserID());
			task.setAssignTime(new Date());
			task.setIsComplete(AcceptanceConstant.UNCOMPLETED);
			dao.save(task);
			for(String objectId :objectIds){
				if(!"".equals(objectId)){
					if(apply.getResourceType().equals(AcceptanceConstant.CABLE)){
						cTask = new CableTask();
						cTask.setCableId(objectId);
						cTask.setTaskId(task.getId());
						cableTaskDao.save(cTask);
						
						ApplyCable cable = applyCableDao.get(objectId);
						cable.setContractorId(contractorId);
						applyCableDao.save(cable);
					}else{					
						pTask = new PipeTask();
						pTask.setPipeId(objectId);
						pTask.setTaskId(task.getId());
						pipeTaskDao.save(pTask);
						
						ApplyPipe pipe = applyPipeDao.get(objectId); 
						pipe.setContractorId(contractorId);
						applyPipeDao.save(pipe);
						
					}
				}
			}
		}
	}
	
	//认领任务
	@Transactional
	public void claimTask(String id, Set<String> tasks, UserInfo userInfo){
		Map<String,String> taskMap = new HashMap<String,String>();
		taskMap.put(userInfo.getDeptID(), StringUtils.join(tasks.iterator(), ","));
		Apply apply = am.loadApply(id);
		//if(tasks.size()!=0){//如果验收任务资源为0表明该代维没有认领任务，表名该代维没有认领的验收资源。直接驱动流程即可。
			//保存任务和任务关联表
		saveCommonTask(taskMap, userInfo, apply);
		//}
		
		//流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getDeptID(), apply.getProcessInstanceId());
		String assinee = isClaimed(id, userInfo);
		if(StringUtils.isNotBlank(assinee)){ //还有代维未认领
			//流程
			workflowBo.setVariables(task, "assignee", assinee);
			workflowBo.setVariables(task, "transition", "no");
			workflowBo.completeTask(task.getId(), "todecision");
			
			//流程历史
			workflowBo.saveProcessHistory(userInfo, apply, task, "", "no", "认领任务", "");
		}else{  //全部代维都认领
			//流程
			Set<String> other = replyApproverDAO.getApprover(id, CommonConstant.APPROVE_MAN, AcceptanceConstant.MODULE);
			String approver = StringUtils.join(other.iterator(), ",");
			workflowBo.setVariables(task, "assignee", approver);
			workflowBo.setVariables(task, "transition", "toapprove");
			workflowBo.completeTask(task.getId(), "todecision");
			
			//流程历史
			workflowBo.saveProcessHistory(userInfo, apply, task, approver, "toapprove", "已全部认领", "");
			
			//保存主表状态
			apply.setProcessState(AcceptanceConstant.CHECK);
			am.saveApply(apply);
//			am.saveState(id, AcceptanceConstant.CHECK);
			
			//短信提醒
			String content = "【验收交维】您有一个名称为\""+apply.getName()+"\"的申请已认领完毕等待您的核准！";
			List<String> mobiles = new ArrayList<String>();
			for(String s : other){
				String mobile = am.getMobileFromUserId(s);
				mobiles.add(mobile);
			}
			try{
			this.sendMessage(content, mobiles);
			}catch(Exception ex){
	            logger.error("发送短信失败:",ex);
	        }
		}
	}
	//取得该申请的光缆或管道的认领代维
	@Transactional(readOnly=true)
	public Map<String, List<String>> getObjectToTask(String applyId){
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Apply apply = am.loadApply(applyId);
		if(apply.getResourceType().equals(AcceptanceConstant.CABLE)){
			Set<ApplyCable> cables = apply.getCables();
			for(ApplyCable cable : cables){
				String cableId = cable.getId();
				List<CableTask> cTasks = cableTaskDao.getCableTasksFromCableId(cableId);
				List<String> list = new ArrayList<String>();
				for(CableTask ct : cTasks){
					String taskId = ct.getTaskId();
					ApplyTask task = dao.findUniqueByProperty("id", taskId);
					String contractorId = task.getContractorId();
					list.add(contractorId);
				}
				map.put(cableId, list);
			}
		}else{
			Set<ApplyPipe> pipes = apply.getPipes();
			for(ApplyPipe pipe : pipes){
				String pipeId = pipe.getId();
				List<PipeTask> pTasks = pipeTaskDao.getPipeTasksFromPipeId(pipeId);
				List<String> list = new ArrayList<String>();
				for(PipeTask pt : pTasks){
					String taskId = pt.getTaskId();
					ApplyTask task = dao.findUniqueByProperty("id", taskId);
					String contractorId = task.getContractorId();
					list.add(contractorId);
				}
				map.put(pipeId, list);
			}
		}
		return map;
	}
	//计算每个光缆认领的代维数
	@Transactional(readOnly=true)
	public Map<String, Integer> getNumberToTask(Map<String, List<String>> map){
		Map<String, Integer> numberMap = new HashMap<String, Integer>();
		for(Map.Entry<String, List<String>> e : map.entrySet()){
			numberMap.put(e.getKey(), map.get(e.getKey()).size());
		}
		return numberMap;
	}
	//每个验收任务（光缆或管道）的验收代维
	@Transactional(readOnly=true)
	public Map<String, String> getChoosedTask(Map<String, List<String>> map){
		Map<String, String> choosed = new HashMap<String, String>();
		for(Map.Entry<String, List<String>> e : map.entrySet()){
			List<String> list = map.get(e.getKey());
			if(list.size() == 1){
				choosed.put(e.getKey(), list.get(0));
			}
		}
		return choosed;
	}
	//每个光缆或管道的验收代维名称
	@Transactional(readOnly=true)
	public Map<String, String> getDeptNameToTask(Map<String, List<String>> map){
		Map<String, String> deptMap = new HashMap<String, String>();
		for(Map.Entry<String, List<String>> e : map.entrySet()){
			List<String> list = map.get(e.getKey());
			List<String> deptNames = new ArrayList<String>();
			for(String s : list){
				String deptName = am.getDeptName(s);
				deptNames.add(deptName);
			}
			deptMap.put(e.getKey(), StringUtils.join(deptNames.toArray(),","));
		}
		return deptMap;
	}
	
	//任务核准
	@Transactional
	public void check(String id, Map<String, String> objectToContractorIdMap, UserInfo userInfo){
		Apply apply = am.loadApply(id);
		//@todo object2ContractorIdMap to contractorId2ObjectMap 
		Map<String, String> contractorId2ObjectMap = displace(objectToContractorIdMap);
		if(apply.getResourceType().equals(AcceptanceConstant.CABLE)){
			//这部分里包含（更改的代维，没有选择的代维和重复代维选择资源的情况）
			//1.删除所有任务和任务关联记录
			
			cableTaskDao.deleteTaskFromApplyId(id);
			dao.deleteTaskFromApplyId(id);
		}else{
			//这部分里包含（更改的代维，没有选择的代维和重复代维选择资源的情况）
			//1.删除所有任务和任务关联记录
			
			pipeTaskDao.deleteTaskFromApplyId(id);
			dao.deleteTaskFromApplyId(id);
		}
		//2.重新添加
		saveCommonTask(contractorId2ObjectMap, userInfo, apply);
		
		
		//流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), apply.getProcessInstanceId());
		workflowBo.completeTask(task.getId(), "torecord");
		
		//流程历史
		workflowBo.saveProcessHistory(userInfo, apply, task, StringUtils.join(getMapKey(contractorId2ObjectMap).iterator(), ","), "torecord", "任务核准", "");
		
		//更新主表状态
//		am.saveState(id, AcceptanceConstant.RECORD);
		apply.setProcessState(AcceptanceConstant.RECORD);
		am.saveApply(apply);
		//保存子流程任务表,启动子流程并发送短信
		saveSubflow(getMapKey(contractorId2ObjectMap),apply);
	}
	private List<String> getMapKey(Map<String, String> map){
		List<String> keys = new ArrayList<String>();
		for(Map.Entry<String, String> e : map.entrySet()){
			keys.add(e.getKey());
		}
		return keys;
	}
	
	private Map<String,String> displace(Map<String, String> map){
		Map<String,String> displace = new HashMap<String,String>();
		for(Map.Entry<String, String> e : map.entrySet()){
			String objectId = displace.get(e.getValue());//验收任务管道，光缆ID
			if(objectId ==null){
				displace.put(e.getValue(), e.getKey());
			}else{
				displace.put(e.getValue(), objectId+","+e.getKey());
			}
		}
		return displace;
	}
	
	//得到未认领代维
	@Transactional(readOnly=true)
	public String isClaimed(String applyId, UserInfo userInfo){
		List<String> assinee = new ArrayList<String>();
		List<ApplyContractor> applyContractors = acDao.getContractors(applyId);
		for(ApplyContractor a : applyContractors){
			String contractorId = a.getContractorId();
			if(!contractorId.equals(userInfo.getDeptID())){
				//任务表里无记录，说明没有认领
				if(!dao.isExistTask(applyId, contractorId)){
					assinee.add(contractorId);
				}
			}
		}
		return StringUtils.join(assinee.toArray(),",");
	}
	@Transactional(readOnly=true)
	public List<String> notClaimed(String applyId){
		Apply apply = am.loadApply(applyId);
		List<String> list = new ArrayList<String>();
		List<Map> objects = new ArrayList<Map>();
		if(apply.getResourceType().equals(AcceptanceConstant.CABLE)){
			objects = applyCableDao.getNotClaimedCable(applyId);
		}else{
			objects = applyPipeDao.getNotClaimedPipe(applyId);
		}
		if(objects != null && !objects.isEmpty()){
			for(Map m : objects){
				list.add((String)m.get("id"));
			}
		}
		return list;
	}
	
	//提交审核
	@Transactional
	public void push(String applyId, String approver, UserInfo userInfo){
		//保存审核人
		replyApproverDAO.saveApproverOrReader(approver, applyId, CommonConstant.APPROVE_MAN, AcceptanceConstant.SUBMODULE);
		
		//流程
		String contractorId = userInfo.getDeptID();
		Subflow subflow = subflowDao.getSubflow(applyId, contractorId);
		Task task = workflowBo.getHandleTaskForId(contractorId, subflow.getProcessInstanceId());
		workflowBo.setVariables(task, "assignee", approver);
		workflowBo.completeTask(task.getId(), "toapprove");
		
		//流程历史
		Apply apply = am.loadApply(applyId);
		workflowBo.saveProcessHistory(userInfo, apply, task, approver, "toapprove", "录入数据", "");
		
		//更新子流程状态
		subflow.setProcessState(AcceptanceConstant.APPROVE);
		subflowDao.save(subflow);
		
		//短信提醒
		String content = "【验收交维】您有一个名称为\""+apply.getName()+"\"的验收单已经验收结束等待您的审核！";
		String mobile = am.getMobileFromUserId(approver);
		List<String> mobiles = com.cabletech.commons.util.StringUtils.string2List(mobile, ",");
		try{
		this.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("发送短信失败:",ex);
        }
	}
	
	private Apply setTask(String applyId,String deptId, String subflowId){
		Apply apply = am.loadApply(applyId);
		apply.clearCable();
		apply.clearPipe();
		if(apply.getResourceType().equals(AcceptanceConstant.CABLE)){
			apply.setCables(new HashSet<ApplyCable>(applyCableDao.getCableForDept( applyId, deptId)));
		}else{
			apply.setPipes(new HashSet<ApplyPipe>(applyPipeDao.getPipeForDept(applyId, deptId)));
		}
		apply.setSubflowId(subflowId);
		return apply;
	}
	
	//得到该代维下面已录入的管道或光缆
	@Transactional(readOnly=true)
	public Apply getRecordedTasksList(String applyId, String deptId, String subflowId){
		List<ApplyTask> list = dao.getRecordTasksList(applyId, deptId);
		return setTask(applyId, deptId, subflowId);
	}
	
	//得到该代维下面所有的管道或光缆
	@Transactional(readOnly=true)
	public Apply getAllRecordTasksList(String subflowId, String deptId){
		String applyId = subflowDao.get(subflowId).getApplyId();
		//List<ApplyTask> list = dao.getAllRecordTasksList(applyId, deptId);
		return setTask(applyId, deptId, subflowId);
	}
	
	//审核时，通过子流程编号得到所有待审核的任务
	@Transactional(readOnly=true)
	public Apply getPassedTaskFromSubflow(String subflowId){
		Subflow subflow = subflowDao.findByUnique("id", subflowId);
		String applyId = subflow.getApplyId();
		String contractorId = subflow.getContractorId();
		Apply apply = am.loadApply(applyId);
		if(apply.getResourceType().equals(AcceptanceConstant.CABLE)){
			List<ApplyCable> list = applyCableDao.getCompletedCable(applyId, contractorId);
			apply.pushCableList(list);
		}else{
			List<ApplyPipe> list = applyPipeDao.getCompletedPipe(applyId, contractorId);
			apply.pushPipeList(list);
		}
		return apply;
	}
	
	//审核
	//将选中的管道或光缆设置为已交维
	@Transactional
	public void approve(String id, String approveResult, String approveRemark, UserInfo userInfo){
		Subflow subflow = subflowDao.findUniqueByProperty("id", id);
		String applyId = subflow.getApplyId();
		Apply apply = am.loadApply(applyId);
		String contractorId = subflow.getContractorId();
		List<ApplyTask> tasks = dao.getAllRecordTasksList(applyId, contractorId);
		
		if(approveResult.equals(AcceptanceConstant.PASSED)){
			for(ApplyTask task : tasks){
				if(apply.getResourceType().equals(AcceptanceConstant.CABLE)){
					ctm.approveCable(task.getId());
				}else{
					ptm.approvePipe(task.getId());
				}
			}
		}
		
		//保存审核结果
		ApproveInfo approve = new ApproveInfo();
		approve.setObjectId(id);    //subflow表id
		approve.setApproverId(userInfo.getUserID());
		approve.setApproveResult(approveResult);
		approve.setApproveRemark(approveRemark);
		approve.setApproveTime(new Date());
		approve.setObjectType("LP_ACCEPTANCE_APPROVE");
		approveDAO.saveApproveInfo(approve);
		
		//子流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), subflow.getProcessInstanceId());
		if(approveResult.equals(AcceptanceConstant.PASSED)){
			workflowBo.setVariables(task, "assignee", userInfo.getUserID());
			workflowBo.completeTask(task.getId(), "toexam");
		}else{
			workflowBo.setVariables(task, "assignee", subflow.getContractorId());
			workflowBo.completeTask(task.getId(), "torecord");
		}
		
		//流程历史
		String contractorName = am.getDeptName(contractorId);
		if(approveResult.equals(AcceptanceConstant.PASSED)){
			workflowBo.saveProcessHistory(userInfo, apply, task, userInfo.getUserID(), "toexam", "审核通过("+contractorName+")", "");
		}else{
			workflowBo.saveProcessHistory(userInfo, apply, task, contractorId, "torecord", "审核未通过("+contractorName+")", "");
		}
		
		//更新子流程状态
		if(approveResult.equals(AcceptanceConstant.PASSED)){
			subflow.setProcessState(AcceptanceConstant.EXAM);
		}else{
			subflow.setProcessState(AcceptanceConstant.RECORD);
		}
		subflowDao.save(subflow);
		
		//短信提醒
		String str = "";
		if(approveResult.equals(AcceptanceConstant.PASSED)){
			str = "已通过！";
		}else{
			str = "未通过！";
		}
		String content = "【验收交维】贵单位有一个名称为\""+apply.getName()+"\"的验收单审核"+str;
		List<String> mobileList = am.getMobileFromDeptId(subflow.getContractorId());
		List<String> mobiles = new ArrayList<String>();
		for(String mobile:mobileList){
			mobiles.addAll(com.cabletech.commons.util.StringUtils.string2List(mobile, ","));
		}
		try{
		this.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("发送短信失败:",ex);
        }
	}
	
	//考核评估，通过子流程id得到所有的已录入管道或光缆
	@Transactional(readOnly=true)
	public Apply getExamTask(String subflowId){
		Subflow subflow = subflowDao.findUniqueByProperty("id", subflowId);
		String deptId = subflow.getContractorId();
		String applyId = subflow.getApplyId();
		Apply apply = getRecordedTasksList(applyId, deptId, subflowId);
		apply.setSubflowId(subflow.getId());
		apply.setSubProcessState(subflow.getProcessState());
		apply.setContractorId(deptId);
		return apply;
	}
	
	//更新任务为完成状态
	@Transactional
	public void updateTaskState(String taskId){
		ApplyTask task = dao.get(taskId);
		task.setIsComplete(AcceptanceConstant.COMPLETED);
		dao.save(task);
	}
	
	//验证所有资源均已录入
	@Transactional
	public boolean validateRecord(Apply apply){
		boolean flag = true;
		if(apply.getResourceType().equals(AcceptanceConstant.CABLE)){
			for(ApplyCable c : apply.getCables()){
				if(c.getIsrecord().equals(AcceptanceConstant.NOTRECORD)){
					flag = false;
				}
			}
		}else{
			for(ApplyPipe p : apply.getPipes()){
				if(p.getIsrecord().equals(AcceptanceConstant.NOTRECORD)){
					flag = false;
				}
			}
		}
		return flag;
	}
	
	//根据中继段Id获得验收光缆的信息
	@Transactional(readOnly=true)
	public ApplyCable getApplyCable4ResId(String rsId){
		return applyCableDao.findbyCable4RSId(rsId);
	}
	@Transactional(readOnly=true)
	public ApplyCable loadCable(String cableId){
		return applyCableDao.findUniqueByProperty("id", cableId);
	}
	@Transactional(readOnly=true)
	public ApplyPipe loadPipe(String pipeId){
		return applyPipeDao.findUniqueByProperty("id", pipeId);
	}
	@Transactional(readOnly=true)
	public List<Map<String, String>> getDeptOptions(String applyId){
		List<ApplyContractor> list = acDao.getContractors(applyId);
		List<Map<String, String>> deptList = new ArrayList<Map<String, String>>();
		for(ApplyContractor a : list){
			String contractorId = a.getContractorId();
			String contractorName = am.getDeptName(contractorId);
			Map<String, String> map = new HashMap<String, String>();
			map.put("contractorId", contractorId);
			map.put("contractorName", contractorName);
			deptList.add(map);
		}
		return deptList;
	}
	@Transactional(readOnly=true)
	public List searchResource(String resourceType,String applyName,String resourceName,UserInfo user){
		String contractorId = "";
		if("2".equals(user.getDeptype())){
			contractorId = user.getDeptID();
		}
		//搜索到的结果不能大于10条记录
		if(AcceptanceConstant.CABLE.equals(resourceType)){
			return dao.findbyCableConditon( applyName, resourceName,contractorId);
		}else{
			return dao.findbyPipeConditon( applyName, resourceName,contractorId);
		}
		
	}
	
	/**
	 * 重新调整某条资源的验收单位
	 * resources 格式为resourceId（即pipeId或cableId）,applyid,oldContractorId,newContractorId
	 */
	@Transactional
	public void reapportion(String [] resources,UserInfo user){
		String applyId="";
		String rsId = "";
		String oldContractorId="";
		String newContractorId="";
		Apply apply= null;
		ApplyTask task = null;
		AllotLog allotLog = null;
		for(String keyValue:resources){
			rsId = keyValue.split(",")[0];
			applyId=keyValue.split(",")[1];
			
			oldContractorId = keyValue.split(",")[2];
			newContractorId = keyValue.split(",")[3];
			apply =  am.loadApply(applyId);
			logger.info("申请ID："+applyId +"资源ID："+rsId +"原代维："+oldContractorId +"新代维："+newContractorId);
			allotLog = new AllotLog(applyId,rsId,oldContractorId,newContractorId,user.getUserID());
			allotLogDao.save(allotLog);//记录分配日志
			List<ApplyTask> oldtasks = dao.getAllRecordTasksList(applyId,oldContractorId );
			if(oldtasks.size()>1){
				//task与pTask，Ctask 表间关系问题-原来的关系不正确。详见deployResourceOld方法注释。
				deployResourceOld(task , apply, rsId, newContractorId);
			}else{
				//改正后
				List<ApplyTask>  newtasks = dao.getAllRecordTasksList(applyId,newContractorId );//找到新代维任务ID
				if(newtasks.size()>=1){//新代维在已有验收代维之列
					if(newtasks.size()>1){
						//说明为老数据。采用上面的方式处理
						deployResourceOld(task , apply, rsId, newContractorId);
					}else{
						task = newtasks.get(0);//新代维任务
						deployResource(oldtasks, task , apply, rsId, newContractorId);//保存调配信息
						clearSubFlow(apply.getId(),oldContractorId);
					}
					// TODO:是否需要结束子流程。
				}else{//新的验收代维根本不再验收的代维之列
					logger.info("代维公司不在当前验收任务中。");
					
					//为新代维创建验收任务
					task = new ApplyTask(apply.getId(),newContractorId,user.getUserID());
					dao.save(task);
					//资源重新分配，清除残余数据
					deployResource(oldtasks, task , apply, rsId, newContractorId);
					
					
					//需要创建子流程。
					List<String> contrators = new ArrayList<String>();
					contrators.add(newContractorId);
					saveSubflow(contrators,apply);
					//TODO:终止那些没有验收任务的当前代维的子流程。
					clearSubFlow(apply.getId(),oldContractorId);
				}
			}
		}
		
	
		
	}
	/**
	 * 清除子流程
	 * @param applyId
	 * @param oldContractorId
	 */
	private void clearSubFlow(String applyId, String oldContractorId ) {
		List<ApplyTask> oldtasks = dao.getAllRecordTasksList(applyId,oldContractorId );
		if(oldtasks.size()<1){
			Subflow subflow =subflowDao.getSubflow(applyId,oldContractorId );
			logger.info(subflow.toString());
			workflowBo.endProcessInstance(subflow.getProcessInstanceId());//结束子流程
			subflowDao.delete(subflow);
		}
	}

	/**
	 * 变更数据结构的关系之后
	 * 即acceptance_task与acceptance_p[c]task是一对多的关系，
	 * 而acceptance_p[c]task与acceptance_cable或acceptance_pipe是一对一的关系）
	 * 这时需要对acceptance_task和acceptance_cable或acceptance_pipe表中的taskId以及验收资源的中的资源ID进行更新。
	 * @param newTask 验收任务
	 * @param apply 验收申请
	 * @param rsId  验收资源ID
	 * @param newContractorId 新的验收代维
	 */
	private void deployResource(List<ApplyTask> oldTasks,ApplyTask newTask ,Apply apply,String rsId,String newContractorId){
		
		if(AcceptanceConstant.CABLE.equals(apply.getResourceType())){
			CableTask ctask= cableTaskDao.getCableTaskFromCableId(rsId);
			ctask.setTaskId(newTask.getId());//修改资源验收任务的任务ID
			ApplyCable cable=applyCableDao.findUniqueByProperty("id", rsId);
			cable.setContractorId(newContractorId);
			cableTaskDao.save(ctask);
			applyCableDao.save(cable);
			clearResidue(oldTasks,apply.getResourceType());
			
		}else{
			PipeTask ptask = pipeTaskDao.getPipeTaskFromPipeId(rsId);
			
			ptask.setTaskId(newTask.getId());
			ApplyPipe pipe = applyPipeDao.findUniqueByProperty("id", rsId);
			pipe.setContractorId(newContractorId);
			pipeTaskDao.save(ptask);
			applyPipeDao.save(pipe);
			clearResidue(oldTasks,apply.getResourceType());
		}
	}
	/**
	 * 清楚残余的数据
	 * @param oldTasks
	 * @param resourceType
	 */
	private void clearResidue(List<ApplyTask> oldTasks, String resourceType) {
		for(ApplyTask task:oldTasks){
			ApplyTask applyTask = dao.query4subtask(task.getId(),resourceType);
			if(applyTask == null){
				dao.delete(task);
			}
		}
		
	}

	/**
	 * 变更数据结构前的数据，是为了兼容老数据而增加。
	 * 采用下面的方式处理
	 * 说明为老数据（指调整业务关系所致即acceptance_task与acceptance_p[c]task是一对一的关系，
	 * 而acceptance_p[c]task与acceptance_cable或acceptance_pipe是一对多的关系）。
	 * 
	 * @param task 验收任务 --传入的为空值
	 * @param apply 验收申请
	 * @param rsId 验收资源Id
	 * @param newContractorId 新的验收代维
	 */
	private void deployResourceOld(ApplyTask task ,Apply apply,String rsId,String newContractorId){
		if(AcceptanceConstant.CABLE.equals(apply.getResourceType())){
			task = dao.getApplyTaskFromCableId(rsId);//rsId 
			task.setContractorId(newContractorId);
			ApplyCable cable=applyCableDao.findUniqueByProperty("id", rsId);
			cable.setContractorId(newContractorId);
			dao.save(task);
			applyCableDao.save(cable);
			//如果变更后没有验收资源，结束该流程
		}else{
			task = dao.getApplyTaskFromPipeId(rsId);
			task.setContractorId(newContractorId);
			ApplyPipe pipe = applyPipeDao.findUniqueByProperty("id", rsId);
			pipe.setContractorId(newContractorId);
			dao.save(task);
			applyPipeDao.save(pipe);
			//如果变更后没有验收资源，结束该流程
		}
	}
	
	
	@Override
	protected HibernateDao<ApplyTask, String> getEntityDao() {
		return dao;
	}
}
