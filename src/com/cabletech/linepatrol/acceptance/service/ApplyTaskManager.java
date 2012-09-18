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
	
	//����õ�δͨ���Ĺ���
	@Transactional(readOnly=true)
	public List<ApplyCable> loadReinspectCables(QueryReinspectBean queryInBean){
		return applyCableDao.getNotPassedApplyCable(queryInBean);
	}
	
	//����õ�δͨ���Ĺܵ�
	@Transactional(readOnly=true)
	public List<ApplyPipe> loadReinspectPipes(QueryReinspectBean queryInBean){
		return applyPipeDao.getNotPassedApplyPipe(queryInBean);
	}
	
	//��������ӹ���
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
	
	//��������ӹܵ�
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
	
	//��������
	//����������䣬���䷽ʽ�����ƶ���ѡ�����֡�
	@Transactional
	public void saveApplyTask(UserInfo userInfo, String type,String remark, String id, Map<String, String> allots, String choose){
		if(type.equals(AcceptanceConstant.SPECIFY)){
			//ָ����ά��Ϊָ����άд������
			Map<String, String> contractorId2ObjectMap = displace(allots);
			saveSpecifyTask(contractorId2ObjectMap, userInfo, id,remark);
		}else{
			//ѡ���ά���ṩ���������ά
			saveChooseTask(choose, userInfo, id,remark);
		}
	}
	//д��������������ά����
	private void saveSpecifyTask(Map<String,String> allotMap, UserInfo userInfo, String id,String remark){
		//��������,�����������
		Apply apply =  am.loadApply(id);
		saveCommonTask(allotMap, userInfo, apply);
		
		//���������ά������
		List<String> contractors = new ArrayList<String>();
		for(Map.Entry<String, String> e : allotMap.entrySet()){
			contractors.add(e.getKey());
			ApplyContractor ac = new ApplyContractor();
			ac.setContractorId(e.getKey());
			ac.setApplyId(id);
			acDao.save(ac);
		}
		
		//������������״̬
		apply.setProcessState(AcceptanceConstant.RECORD);
//		am.saveState(id, AcceptanceConstant.RECORD);
		apply.setRemark(remark);
		am.saveApply(apply);
		//����
	
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), apply.getProcessInstanceId());
		workflowBo.setVariables(task, "transition", "specify");
		workflowBo.completeTask(task.getId(), "todecision");
		
		//������ʷ
		workflowBo.saveProcessHistory(userInfo, apply, task, StringUtils.join(contractors.toArray(), ","), "todecision", "�������(ָ����ά)", "");
		
		//������������������������̺ͷ��Ͷ���
		saveSubflow(contractors,apply);
	}
	

	/**
	 * �����̽������������������̡�
	 * @param allots �������յĴ�ά��˾
	 * @param apply ����������Ϣ��
	 */
	private void saveSubflow(List<String> allots,Apply apply){
		
		//���������������
		for(String contractorId : allots){
			Subflow subflow = new Subflow();
			subflow.setApplyId(apply.getId());
			subflow.setContractorId(contractorId);
			subflowDao.save(subflow);
			
			//����������
			Map<String,Object> variables = new HashMap<String,Object>();
			variables.put("assignee", contractorId);
			String processId = workflowBo.createProcessInstance("acceptancesubflow", subflow.getId(), variables);
			
			subflow.setProcessInstanceId(processId);
			subflow.setProcessState(AcceptanceConstant.RECORD);
			subflowDao.save(subflow);
			
			//��������
			String content = "�����ս�ά����λ��һ������Ϊ\""+apply.getName()+"\"������ȴ�����¼���������ݣ�";
			List<String> mobileList = am.getMobileFromDeptId(contractorId);
			List<String> mobiles = new ArrayList<String>();
			for(String mobile:mobileList){
				mobiles.addAll(com.cabletech.commons.util.StringUtils.string2List(mobile, ","));
			}
			try{
			this.sendMessage(content, mobiles);
			}catch(Exception ex){
	            logger.error("���Ͷ���ʧ��:",ex);
	        }
		}
	}
	
	private void saveChooseTask(String choose, UserInfo userInfo, String id,String remark){
		
		Apply apply = am.loadApply(id);
		//��ʱ��������������������
		//���������ά������
		for(String contractorId : choose.split(",")){
			ApplyContractor ac = new ApplyContractor();
			ac.setContractorId(contractorId);
			ac.setApplyId(id);
			acDao.save(ac);
		}
		
		//������������״̬
		apply.setProcessState(AcceptanceConstant.CLAIM);
		apply.setRemark(remark);
		am.saveApply(apply);
		
		//����
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), apply.getProcessInstanceId());
		workflowBo.setVariables(task, "assignee", choose);
		workflowBo.setVariables(task, "transition", "choose");
		workflowBo.completeTask(task.getId(), "todecision");
		
		//������ʷ
		workflowBo.saveProcessHistory(userInfo, apply, task, choose, "todecision", "�������(ѡ���ά)", "");
		
		//��������
		for(String contractorId : choose.split(",")){
			String content = "�����ս�ά����λ��һ������Ϊ\""+apply.getName()+"\"����������ȴ��������죡";
			List<String> mobileList = am.getMobileFromDeptId(contractorId);
			List<String> mobiles = new ArrayList<String>();
			for(String mobile:mobileList){
				mobiles.addAll(com.cabletech.commons.util.StringUtils.string2List(mobile, ","));
			}
			try{
			this.sendMessage(content, mobiles);
			}catch(Exception ex){
	            logger.error("���Ͷ���ʧ��:",ex);
	        }
		}
	}
	
	//�������մ�ά��Ϣ
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
	
	//��������
	@Transactional
	public void claimTask(String id, Set<String> tasks, UserInfo userInfo){
		Map<String,String> taskMap = new HashMap<String,String>();
		taskMap.put(userInfo.getDeptID(), StringUtils.join(tasks.iterator(), ","));
		Apply apply = am.loadApply(id);
		//if(tasks.size()!=0){//�������������ԴΪ0�����ô�άû���������񣬱����ô�άû�������������Դ��ֱ���������̼��ɡ�
			//������������������
		saveCommonTask(taskMap, userInfo, apply);
		//}
		
		//����
		Task task = workflowBo.getHandleTaskForId(userInfo.getDeptID(), apply.getProcessInstanceId());
		String assinee = isClaimed(id, userInfo);
		if(StringUtils.isNotBlank(assinee)){ //���д�άδ����
			//����
			workflowBo.setVariables(task, "assignee", assinee);
			workflowBo.setVariables(task, "transition", "no");
			workflowBo.completeTask(task.getId(), "todecision");
			
			//������ʷ
			workflowBo.saveProcessHistory(userInfo, apply, task, "", "no", "��������", "");
		}else{  //ȫ����ά������
			//����
			Set<String> other = replyApproverDAO.getApprover(id, CommonConstant.APPROVE_MAN, AcceptanceConstant.MODULE);
			String approver = StringUtils.join(other.iterator(), ",");
			workflowBo.setVariables(task, "assignee", approver);
			workflowBo.setVariables(task, "transition", "toapprove");
			workflowBo.completeTask(task.getId(), "todecision");
			
			//������ʷ
			workflowBo.saveProcessHistory(userInfo, apply, task, approver, "toapprove", "��ȫ������", "");
			
			//��������״̬
			apply.setProcessState(AcceptanceConstant.CHECK);
			am.saveApply(apply);
//			am.saveState(id, AcceptanceConstant.CHECK);
			
			//��������
			String content = "�����ս�ά������һ������Ϊ\""+apply.getName()+"\"��������������ϵȴ����ĺ�׼��";
			List<String> mobiles = new ArrayList<String>();
			for(String s : other){
				String mobile = am.getMobileFromUserId(s);
				mobiles.add(mobile);
			}
			try{
			this.sendMessage(content, mobiles);
			}catch(Exception ex){
	            logger.error("���Ͷ���ʧ��:",ex);
	        }
		}
	}
	//ȡ�ø�����Ĺ��»�ܵ��������ά
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
	//����ÿ����������Ĵ�ά��
	@Transactional(readOnly=true)
	public Map<String, Integer> getNumberToTask(Map<String, List<String>> map){
		Map<String, Integer> numberMap = new HashMap<String, Integer>();
		for(Map.Entry<String, List<String>> e : map.entrySet()){
			numberMap.put(e.getKey(), map.get(e.getKey()).size());
		}
		return numberMap;
	}
	//ÿ���������񣨹��»�ܵ��������մ�ά
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
	//ÿ�����»�ܵ������մ�ά����
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
	
	//�����׼
	@Transactional
	public void check(String id, Map<String, String> objectToContractorIdMap, UserInfo userInfo){
		Apply apply = am.loadApply(id);
		//@todo object2ContractorIdMap to contractorId2ObjectMap 
		Map<String, String> contractorId2ObjectMap = displace(objectToContractorIdMap);
		if(apply.getResourceType().equals(AcceptanceConstant.CABLE)){
			//�ⲿ������������ĵĴ�ά��û��ѡ��Ĵ�ά���ظ���άѡ����Դ�������
			//1.ɾ��������������������¼
			
			cableTaskDao.deleteTaskFromApplyId(id);
			dao.deleteTaskFromApplyId(id);
		}else{
			//�ⲿ������������ĵĴ�ά��û��ѡ��Ĵ�ά���ظ���άѡ����Դ�������
			//1.ɾ��������������������¼
			
			pipeTaskDao.deleteTaskFromApplyId(id);
			dao.deleteTaskFromApplyId(id);
		}
		//2.�������
		saveCommonTask(contractorId2ObjectMap, userInfo, apply);
		
		
		//����
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), apply.getProcessInstanceId());
		workflowBo.completeTask(task.getId(), "torecord");
		
		//������ʷ
		workflowBo.saveProcessHistory(userInfo, apply, task, StringUtils.join(getMapKey(contractorId2ObjectMap).iterator(), ","), "torecord", "�����׼", "");
		
		//��������״̬
//		am.saveState(id, AcceptanceConstant.RECORD);
		apply.setProcessState(AcceptanceConstant.RECORD);
		am.saveApply(apply);
		//���������������,���������̲����Ͷ���
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
			String objectId = displace.get(e.getValue());//��������ܵ�������ID
			if(objectId ==null){
				displace.put(e.getValue(), e.getKey());
			}else{
				displace.put(e.getValue(), objectId+","+e.getKey());
			}
		}
		return displace;
	}
	
	//�õ�δ�����ά
	@Transactional(readOnly=true)
	public String isClaimed(String applyId, UserInfo userInfo){
		List<String> assinee = new ArrayList<String>();
		List<ApplyContractor> applyContractors = acDao.getContractors(applyId);
		for(ApplyContractor a : applyContractors){
			String contractorId = a.getContractorId();
			if(!contractorId.equals(userInfo.getDeptID())){
				//��������޼�¼��˵��û������
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
	
	//�ύ���
	@Transactional
	public void push(String applyId, String approver, UserInfo userInfo){
		//���������
		replyApproverDAO.saveApproverOrReader(approver, applyId, CommonConstant.APPROVE_MAN, AcceptanceConstant.SUBMODULE);
		
		//����
		String contractorId = userInfo.getDeptID();
		Subflow subflow = subflowDao.getSubflow(applyId, contractorId);
		Task task = workflowBo.getHandleTaskForId(contractorId, subflow.getProcessInstanceId());
		workflowBo.setVariables(task, "assignee", approver);
		workflowBo.completeTask(task.getId(), "toapprove");
		
		//������ʷ
		Apply apply = am.loadApply(applyId);
		workflowBo.saveProcessHistory(userInfo, apply, task, approver, "toapprove", "¼������", "");
		
		//����������״̬
		subflow.setProcessState(AcceptanceConstant.APPROVE);
		subflowDao.save(subflow);
		
		//��������
		String content = "�����ս�ά������һ������Ϊ\""+apply.getName()+"\"�����յ��Ѿ����ս����ȴ�������ˣ�";
		String mobile = am.getMobileFromUserId(approver);
		List<String> mobiles = com.cabletech.commons.util.StringUtils.string2List(mobile, ",");
		try{
		this.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("���Ͷ���ʧ��:",ex);
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
	
	//�õ��ô�ά������¼��Ĺܵ������
	@Transactional(readOnly=true)
	public Apply getRecordedTasksList(String applyId, String deptId, String subflowId){
		List<ApplyTask> list = dao.getRecordTasksList(applyId, deptId);
		return setTask(applyId, deptId, subflowId);
	}
	
	//�õ��ô�ά�������еĹܵ������
	@Transactional(readOnly=true)
	public Apply getAllRecordTasksList(String subflowId, String deptId){
		String applyId = subflowDao.get(subflowId).getApplyId();
		//List<ApplyTask> list = dao.getAllRecordTasksList(applyId, deptId);
		return setTask(applyId, deptId, subflowId);
	}
	
	//���ʱ��ͨ�������̱�ŵõ����д���˵�����
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
	
	//���
	//��ѡ�еĹܵ����������Ϊ�ѽ�ά
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
		
		//������˽��
		ApproveInfo approve = new ApproveInfo();
		approve.setObjectId(id);    //subflow��id
		approve.setApproverId(userInfo.getUserID());
		approve.setApproveResult(approveResult);
		approve.setApproveRemark(approveRemark);
		approve.setApproveTime(new Date());
		approve.setObjectType("LP_ACCEPTANCE_APPROVE");
		approveDAO.saveApproveInfo(approve);
		
		//������
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), subflow.getProcessInstanceId());
		if(approveResult.equals(AcceptanceConstant.PASSED)){
			workflowBo.setVariables(task, "assignee", userInfo.getUserID());
			workflowBo.completeTask(task.getId(), "toexam");
		}else{
			workflowBo.setVariables(task, "assignee", subflow.getContractorId());
			workflowBo.completeTask(task.getId(), "torecord");
		}
		
		//������ʷ
		String contractorName = am.getDeptName(contractorId);
		if(approveResult.equals(AcceptanceConstant.PASSED)){
			workflowBo.saveProcessHistory(userInfo, apply, task, userInfo.getUserID(), "toexam", "���ͨ��("+contractorName+")", "");
		}else{
			workflowBo.saveProcessHistory(userInfo, apply, task, contractorId, "torecord", "���δͨ��("+contractorName+")", "");
		}
		
		//����������״̬
		if(approveResult.equals(AcceptanceConstant.PASSED)){
			subflow.setProcessState(AcceptanceConstant.EXAM);
		}else{
			subflow.setProcessState(AcceptanceConstant.RECORD);
		}
		subflowDao.save(subflow);
		
		//��������
		String str = "";
		if(approveResult.equals(AcceptanceConstant.PASSED)){
			str = "��ͨ����";
		}else{
			str = "δͨ����";
		}
		String content = "�����ս�ά����λ��һ������Ϊ\""+apply.getName()+"\"�����յ����"+str;
		List<String> mobileList = am.getMobileFromDeptId(subflow.getContractorId());
		List<String> mobiles = new ArrayList<String>();
		for(String mobile:mobileList){
			mobiles.addAll(com.cabletech.commons.util.StringUtils.string2List(mobile, ","));
		}
		try{
		this.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("���Ͷ���ʧ��:",ex);
        }
	}
	
	//����������ͨ��������id�õ����е���¼��ܵ������
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
	
	//��������Ϊ���״̬
	@Transactional
	public void updateTaskState(String taskId){
		ApplyTask task = dao.get(taskId);
		task.setIsComplete(AcceptanceConstant.COMPLETED);
		dao.save(task);
	}
	
	//��֤������Դ����¼��
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
	
	//�����м̶�Id������չ��µ���Ϣ
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
		//�������Ľ�����ܴ���10����¼
		if(AcceptanceConstant.CABLE.equals(resourceType)){
			return dao.findbyCableConditon( applyName, resourceName,contractorId);
		}else{
			return dao.findbyPipeConditon( applyName, resourceName,contractorId);
		}
		
	}
	
	/**
	 * ���µ���ĳ����Դ�����յ�λ
	 * resources ��ʽΪresourceId����pipeId��cableId��,applyid,oldContractorId,newContractorId
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
			logger.info("����ID��"+applyId +"��ԴID��"+rsId +"ԭ��ά��"+oldContractorId +"�´�ά��"+newContractorId);
			allotLog = new AllotLog(applyId,rsId,oldContractorId,newContractorId,user.getUserID());
			allotLogDao.save(allotLog);//��¼������־
			List<ApplyTask> oldtasks = dao.getAllRecordTasksList(applyId,oldContractorId );
			if(oldtasks.size()>1){
				//task��pTask��Ctask ����ϵ����-ԭ���Ĺ�ϵ����ȷ�����deployResourceOld����ע�͡�
				deployResourceOld(task , apply, rsId, newContractorId);
			}else{
				//������
				List<ApplyTask>  newtasks = dao.getAllRecordTasksList(applyId,newContractorId );//�ҵ��´�ά����ID
				if(newtasks.size()>=1){//�´�ά���������մ�ά֮��
					if(newtasks.size()>1){
						//˵��Ϊ�����ݡ���������ķ�ʽ����
						deployResourceOld(task , apply, rsId, newContractorId);
					}else{
						task = newtasks.get(0);//�´�ά����
						deployResource(oldtasks, task , apply, rsId, newContractorId);//���������Ϣ
						clearSubFlow(apply.getId(),oldContractorId);
					}
					// TODO:�Ƿ���Ҫ���������̡�
				}else{//�µ����մ�ά�����������յĴ�ά֮��
					logger.info("��ά��˾���ڵ�ǰ���������С�");
					
					//Ϊ�´�ά������������
					task = new ApplyTask(apply.getId(),newContractorId,user.getUserID());
					dao.save(task);
					//��Դ���·��䣬�����������
					deployResource(oldtasks, task , apply, rsId, newContractorId);
					
					
					//��Ҫ���������̡�
					List<String> contrators = new ArrayList<String>();
					contrators.add(newContractorId);
					saveSubflow(contrators,apply);
					//TODO:��ֹ��Щû����������ĵ�ǰ��ά�������̡�
					clearSubFlow(apply.getId(),oldContractorId);
				}
			}
		}
		
	
		
	}
	/**
	 * ���������
	 * @param applyId
	 * @param oldContractorId
	 */
	private void clearSubFlow(String applyId, String oldContractorId ) {
		List<ApplyTask> oldtasks = dao.getAllRecordTasksList(applyId,oldContractorId );
		if(oldtasks.size()<1){
			Subflow subflow =subflowDao.getSubflow(applyId,oldContractorId );
			logger.info(subflow.toString());
			workflowBo.endProcessInstance(subflow.getProcessInstanceId());//����������
			subflowDao.delete(subflow);
		}
	}

	/**
	 * ������ݽṹ�Ĺ�ϵ֮��
	 * ��acceptance_task��acceptance_p[c]task��һ�Զ�Ĺ�ϵ��
	 * ��acceptance_p[c]task��acceptance_cable��acceptance_pipe��һ��һ�Ĺ�ϵ��
	 * ��ʱ��Ҫ��acceptance_task��acceptance_cable��acceptance_pipe���е�taskId�Լ�������Դ���е���ԴID���и��¡�
	 * @param newTask ��������
	 * @param apply ��������
	 * @param rsId  ������ԴID
	 * @param newContractorId �µ����մ�ά
	 */
	private void deployResource(List<ApplyTask> oldTasks,ApplyTask newTask ,Apply apply,String rsId,String newContractorId){
		
		if(AcceptanceConstant.CABLE.equals(apply.getResourceType())){
			CableTask ctask= cableTaskDao.getCableTaskFromCableId(rsId);
			ctask.setTaskId(newTask.getId());//�޸���Դ�������������ID
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
	 * ������������
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
	 * ������ݽṹǰ�����ݣ���Ϊ�˼��������ݶ����ӡ�
	 * ��������ķ�ʽ����
	 * ˵��Ϊ�����ݣ�ָ����ҵ���ϵ���¼�acceptance_task��acceptance_p[c]task��һ��һ�Ĺ�ϵ��
	 * ��acceptance_p[c]task��acceptance_cable��acceptance_pipe��һ�Զ�Ĺ�ϵ����
	 * 
	 * @param task �������� --�����Ϊ��ֵ
	 * @param apply ��������
	 * @param rsId ������ԴId
	 * @param newContractorId �µ����մ�ά
	 */
	private void deployResourceOld(ApplyTask task ,Apply apply,String rsId,String newContractorId){
		if(AcceptanceConstant.CABLE.equals(apply.getResourceType())){
			task = dao.getApplyTaskFromCableId(rsId);//rsId 
			task.setContractorId(newContractorId);
			ApplyCable cable=applyCableDao.findUniqueByProperty("id", rsId);
			cable.setContractorId(newContractorId);
			dao.save(task);
			applyCableDao.save(cable);
			//��������û��������Դ������������
		}else{
			task = dao.getApplyTaskFromPipeId(rsId);
			task.setContractorId(newContractorId);
			ApplyPipe pipe = applyPipeDao.findUniqueByProperty("id", rsId);
			pipe.setContractorId(newContractorId);
			dao.save(task);
			applyPipeDao.save(pipe);
			//��������û��������Դ������������
		}
	}
	
	
	@Override
	protected HibernateDao<ApplyTask, String> getEntityDao() {
		return dao;
	}
}
