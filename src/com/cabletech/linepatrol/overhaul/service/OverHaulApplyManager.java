package com.cabletech.linepatrol.overhaul.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.cut.dao.CutDao;
import com.cabletech.linepatrol.cut.module.Cut;
import com.cabletech.linepatrol.overhaul.beans.OverHaulApplyBean;
import com.cabletech.linepatrol.overhaul.dao.OverHaulApplyDao;
import com.cabletech.linepatrol.overhaul.dao.OverHaulCutDao;
import com.cabletech.linepatrol.overhaul.dao.OverHaulProjectDao;
import com.cabletech.linepatrol.overhaul.model.Constant;
import com.cabletech.linepatrol.overhaul.model.OverHaul;
import com.cabletech.linepatrol.overhaul.model.OverHaulApply;
import com.cabletech.linepatrol.overhaul.model.OverHaulCut;
import com.cabletech.linepatrol.overhaul.model.OverHaulProject;
import com.cabletech.linepatrol.overhaul.workflow.OverhaulWorkflowBO;
import com.cabletech.linepatrol.project.dao.RemedyApplyDao;
import com.cabletech.linepatrol.project.domain.ProjectRemedyApply;

@Service
@Transactional
public class OverHaulApplyManager extends EntityManager<OverHaulApply, String> {
	@Resource(name="overHaulApplyDao")
	private OverHaulApplyDao dao;
	@Resource(name="cutDao")
	private CutDao cutDao;
	@Resource(name="remedyApplyDao")
	private RemedyApplyDao remedyApplyDao;
	@Resource(name="replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	@Resource(name="approveDAO")
	private ApproveDAO approveDAO;
	@Resource(name="overHaulCutDao")
	private OverHaulCutDao overHaulCutDao;
	@Resource(name="overHaulProjectDao")
	private OverHaulProjectDao overHaulProjectDao;
	@Autowired
	private OverhaulWorkflowBO workflowBo;
	@Resource(name="overhaulManager")
	private OverhaulManager overhaulManager;
	@Resource(name="uploadFileService")
	private UploadFileService uploadFile;
	
	public void addApply(UserInfo userInfo, OverHaulApplyBean overHaulApplyBean, List<OverHaulCut> cutList, List<OverHaulProject> projectList, List<FileItem> files){
		//保存申请表（割接信息，工程信息）
		OverHaulApply apply = new OverHaulApply();
		apply.setTaskId(overHaulApplyBean.getTaskId());
		apply.setFee(overHaulApplyBean.getFee());
		apply.setCreator(userInfo.getUserID());
		apply.setContractorId(userInfo.getDeptID());
		apply.setApplicant(userInfo.getDeptName());
		apply.setCreateTime(new Date());
		apply = addCutAndProject(apply, cutList, projectList);
		dao.save(apply);
		
		//保存附件
		saveFiles(files, apply.getId(), userInfo);
		
		//保存审核人,抄送人
		addApprover(apply.getId(), overHaulApplyBean.getApprover(), overHaulApplyBean.getReader());
		
		//启动子流程
		Map<String,Object> variables = new HashMap<String,Object>();
		String assignee = overHaulApplyBean.getApprover();
		if(StringUtils.isNotBlank(overHaulApplyBean.getReader())){
			assignee += "," + overHaulApplyBean.getReader();
		}
		variables.put("assignee", assignee);
		String processId = workflowBo.createProcessInstance("overhaulsubflow", apply.getId(), variables);
		
		//更新子流程编号和状态
		apply.setProcessInstanceId(processId);
		apply.setState(Constant.APPLY);
		dao.save(apply);
		
		//保存流程历史
		OverHaul overHaul = overhaulManager.loadOverHaul(overHaulApplyBean.getTaskId());
		workflowBo.saveProcessHistory(userInfo, overHaul, null, assignee, "", "保存申请", "");
		
		//短信提醒
		setSmsForApply(overHaul, assignee, userInfo);
	}
	
	public void editApply(UserInfo userInfo, OverHaulApplyBean overHaulApplyBean, List<OverHaulCut> cutList, List<OverHaulProject> projectList, List<FileItem> files){
		//更新申请表（割接信息，工程信息）
		String applyId = overHaulApplyBean.getId();
		
		//删除割接信息
		overHaulCutDao.deleteCut(applyId);
		
		//删除工程信息
		overHaulProjectDao.deleteProject(applyId);
		
		OverHaulApply apply = dao.findByUnique("id", applyId);
		apply.setFee(overHaulApplyBean.getFee());
		apply = addCutAndProject(apply, cutList, projectList);
		dao.save(apply);
		
		//保存附件
		saveFiles(files, apply.getId(), userInfo);
		
		//保存审核人,抄送人
		addApprover(apply.getId(), overHaulApplyBean.getApprover(), overHaulApplyBean.getReader());
		
		//流程
		String assignee = overHaulApplyBean.getApprover();
		if(StringUtils.isNotBlank(overHaulApplyBean.getReader())){
			assignee += "," + overHaulApplyBean.getReader();
		}
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), apply.getProcessInstanceId());
		workflowBo.setVariables(task, "assignee", assignee);
		workflowBo.completeTask(task.getId(), "toapprove");
		
		//更新状态
		apply.setState(Constant.APPLY);
		dao.save(apply);
		
		//保存流程历史
		OverHaul overHaul = overhaulManager.loadOverHaul(apply.getTaskId());
		workflowBo.saveProcessHistory(userInfo, overHaul, task, assignee, "", "修改申请", "");
		
		//短信提醒
		setSmsForApply(overHaul, assignee, userInfo);
	}
	
	public void setSmsForApply(OverHaul overHaul, String assignee, UserInfo userInfo){
		//短信提醒
		String content = "【大修项目】您有一个名称为\""+overHaul.getProjectName()+"\"的大修任务申请等待您的及时处理！申请部门"+overhaulManager.getDeptName(userInfo.getDeptID());
		List<String> mobiles = getMobile(Arrays.asList(assignee.split(",")));
		super.sendMessage(content, mobiles);
	}
	
	public OverHaulApply addCutAndProject(OverHaulApply apply, List<OverHaulCut> cutList, List<OverHaulProject> projectList){
		//清理
		apply.clearOverHaulCut();
		apply.clearOverHaulProject();
		
		//添加割接信息
		for(OverHaulCut overHaulCut : cutList){
			//得到割接对象
			Cut cut = cutDao.findUniqueByProperty("id", overHaulCut.getCutId());
			
			overHaulCut.setCutName(cut.getCutName());
			overHaulCut.setOverHaulApply(apply);
			apply.addOverHaulCut(overHaulCut);
		}
		
		//添加工程信息
		for(OverHaulProject overHaulProject : projectList){
			//得到工程对象
			ProjectRemedyApply project = remedyApplyDao.findUniqueByProperty("id", overHaulProject.getProjectId());
			
			overHaulProject.setProjectName(project.getProjectName());
			overHaulProject.setOverHaulApply(apply);
			apply.addOverHaulProject(overHaulProject);
		}
		return apply;
	}
	
	public void addApprover(String id, String approver, String reader){
		//删除以前的审核人，抄送人
		replyApproverDAO.deleteList(id, Constant.MODULE);
		
		//保存审核人，抄送人
		replyApproverDAO.saveApproverOrReader(approver, id, CommonConstant.APPROVE_MAN, Constant.MODULE);
		replyApproverDAO.saveApproverOrReader(reader, id, CommonConstant.APPROVE_READ, Constant.MODULE);
	}
	
	public void approve(ApproveInfo approveInfo, UserInfo userInfo){
		//保存审核信息
		approveInfo.setObjectType(Constant.MODULE);
		approveDAO.saveApproveInfo(approveInfo);
		
		String applyId = approveInfo.getObjectId();
		OverHaulApply apply = dao.findByUnique("id", applyId);
		
		//流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), apply.getProcessInstanceId());
		if(approveInfo.getApproveResult().equals("0")){
			workflowBo.setVariables(task, "assignee", apply.getCreator());
			workflowBo.completeTask(task.getId(), "not_pass");
		}else{
			workflowBo.setVariables(task, "transition", "toend");
			workflowBo.completeTask(task.getId(), "pass");
		}
		
		//更新子流程状态
		if(approveInfo.getApproveResult().equals("0")){
			apply.setState(Constant.EDITAPPLY);
		}else{
			apply.setState(Constant.PASS);
		}
		dao.save(apply);
		
		//保存流程历史
		OverHaul overHaul = overhaulManager.loadOverHaul(apply.getTaskId());
		if(approveInfo.getApproveResult().equals("0")){
			workflowBo.saveProcessHistory(userInfo, overHaul, task, apply.getCreator(), "", "审核未通过", "");
		}else{
			workflowBo.saveProcessHistory(userInfo, overHaul, task, "", "", "审核通过", "");
		}
		
		//发送短信
		if(approveInfo.getApproveResult().equals("0")){
			String content = "【大修项目】您有一个名称为\""+overHaul.getProjectName()+"\"的大修任务申请审核没有通过，请返回修改！审核人"+overhaulManager.getUserName(userInfo.getUserID());
			List<String> mobiles = getMobile(Arrays.asList(apply.getCreator()));
			super.sendMessage(content, mobiles);
		}
	}
	
	public void transfer(UserInfo userInfo, String applyId, String approver){
		OverHaulApply apply = dao.findByUnique("id", applyId);
		
		//取得抄送人
		Set<String> all = replyApproverDAO.getApprover(applyId, CommonConstant.APPROVE_READ, Constant.MODULE);
		all.add(approver);
		
		//流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), apply.getProcessInstanceId());
		workflowBo.setVariables(task, "assignee", StringUtils.join(all.iterator(), ","));
		workflowBo.setVariables(task, "transition", "transfer");
		workflowBo.completeTask(task.getId(), "pass");
		
		//保存审核人
		replyApproverDAO.saveApproverOrReader(approver, applyId, CommonConstant.APPROVE_MAN, Constant.MODULE);
	
		//保存流程历史
		OverHaul overHaul = overhaulManager.loadOverHaul(apply.getTaskId());
		workflowBo.saveProcessHistory(userInfo, overHaul, task, approver, "", "转审", "");
		
		//发送短信
		String content = "【大修项目】用户将\""+overHaul.getProjectName()+"\"的大修任务转交给您进行审核，请及时处理";
		List<String> mobiles = getMobile(Arrays.asList(userInfo.getUserID()));
		super.sendMessage(content, mobiles);
	}
	
	//保存附件
	public void saveFiles(List<FileItem> files, String id, UserInfo userInfo){
		uploadFile.saveFiles(files, ModuleCatalog.OVERHAUL, userInfo.getRegionName(), id, "LP_OVERHAUL", userInfo.getUserID());
	}
	
	public List<OverHaulApply> loadPassedApply(String id){
		List<OverHaulApply> list = dao.getPassedApplyWithOverHaul(id);
		for(OverHaulApply overHaulApply : list){
			dao.initObjects(overHaulApply.getOverHaulCuts());
			dao.initObjects(overHaulApply.getOverHaulProjects());
		}
		return list;
	}
	
	public List<OverHaulApply> loadApplyForDept(UserInfo userInfo, String id){
		List<OverHaulApply> list = dao.getApply(userInfo, id);
		for(OverHaulApply overHaulApply : list){
			dao.initObjects(overHaulApply.getOverHaulCuts());
			dao.initObjects(overHaulApply.getOverHaulProjects());
		}
		return list;
	}
	
	public List<OverHaulApply> loadApply(String id){
		return dao.findByProperty("taskId", id);
	}
	
	public OverHaulApply loadOverHaulApplyFromSubflowId(String subflowId, UserInfo userInfo){
		OverHaulApply overHaulApply = dao.getFromProcessInstanceId(subflowId);
		dao.initObject(overHaulApply.getOverHaulCuts());
		dao.initObject(overHaulApply.getOverHaulProjects());
		return overhaulManager.setReadOnly(overHaulApply, userInfo);
	}
	
	public List<String> getMobile(List<String> approverIds){
		List<String> principals = new ArrayList<String>();
		for(String id : approverIds){
			String phone = overhaulManager.getPhoneFromUserid(id);
			if(StringUtils.isNotBlank(phone)){
				principals.add(phone);
			}
		}
		return principals;
	}
	
	@Override
	protected HibernateDao<OverHaulApply, String> getEntityDao() {
		return dao;
	}
}
