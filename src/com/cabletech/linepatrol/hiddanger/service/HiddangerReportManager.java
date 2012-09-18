package com.cabletech.linepatrol.hiddanger.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.hiddanger.beans.ReportBean;
import com.cabletech.linepatrol.hiddanger.dao.HiddangerRegistDao;
import com.cabletech.linepatrol.hiddanger.dao.HiddangerReportDao;
import com.cabletech.linepatrol.hiddanger.model.HiddangerRegist;
import com.cabletech.linepatrol.hiddanger.model.HiddangerReport;
import com.cabletech.linepatrol.hiddanger.workflow.HiddangerWorkflowBO;

@Service
@Transactional
public class HiddangerReportManager extends EntityManager<HiddangerReport, String>{
	
	@Resource(name="hiddangerReportDao")
	private HiddangerReportDao dao;
	@Resource(name="hiddangerTrunkManager")
	private HiddangerTrunkManager hiddangerTrunkManager;
	@Resource(name="approveDAO")
	private ApproveDAO approveDAO;
	@Resource(name="hiddangerRegistDao")
	private HiddangerRegistDao registDao;
	@Resource(name="uploadFileService")
	private UploadFileService uploadFile;
	@Resource(name="replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	@Autowired
	private HiddangerWorkflowBO workflowBo;
	@Resource(name="smHistoryDAO")
	private SmHistoryDAO historyDAO;
	
	/**
	 * 保存上报且需要审核
	 */
	public void saveReport(ReportBean reportBean, List<String> trunks, UserInfo userInfo, String approver, String reader, List<FileItem> files){
		HiddangerRegist regist = registDao.findUniqueByProperty("id", reportBean.getHiddangerId());
		HiddangerReport report = dao.getReportFromHiddangerId(reportBean.getHiddangerId());
		
		//审核，删除旧记录并更新审核次数
		if(report != null){
			//删除中继段
			hiddangerTrunkManager.deleteTrunk(reportBean.getHiddangerId());
			
			//删除审核人抄送人
			replyApproverDAO.deleteList(reportBean.getHiddangerId(), "LP_HIDDANGER_REPORT");
			
			BeanUtil.copyProperties(reportBean, report);
			report.setAuthorId(userInfo.getUserID());
		}else{
			report = new HiddangerReport();
			BeanUtil.copyProperties(reportBean, report);
			report.setId(null);
			report.setAuthorId(userInfo.getUserID());
			report.setApproveTimes("0");
		}
		
		//保存上报对象
		dao.save(report);
		
		//保存附件
		saveFiles(files, report.getHiddangerId(), userInfo);
		
		//保存中继段
		for(String trunkId : trunks){
			hiddangerTrunkManager.saveTrunk(report.getHiddangerId(), trunkId);
		}
		
		//保存审核人
		replyApproverDAO.saveApproverOrReader(approver, report.getHiddangerId(), CommonConstant.APPROVE_MAN, "LP_HIDDANGER_REPORT");
		
		//保存抄送人
		replyApproverDAO.saveApproverOrReader(reader, report.getHiddangerId(), CommonConstant.APPROVE_READ, "LP_HIDDANGER_REPORT");
		
		//更新隐患主表状态
		saveState(reportBean.getHiddangerId(), HiddangerConstant.NEEDAPPROVE);
		
		//流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getDeptID(), reportBean.getHiddangerId());
		
		String assinee = "";
		if(StringUtils.isNotBlank(reader)){
			assinee = approver+","+reader;
		}else{
			assinee = approver;
		}
		workflowBo.setVariables(task, "assignee", assinee);
		workflowBo.completeTask(task.getId(), "add_approve");
		
		//流程历史
		workflowBo.saveProcessHistory(userInfo, regist, task, assinee, "add_approve", "隐患上报", "");
		
		//短信提醒
		//将一级隐患上报信息发送给审核人
		String content = "【隐患盯防】您有一个名称为\""+getRegist(reportBean.getHiddangerId()).getName()+"\"的隐患上报单等待您的审核！";
		List<String> mobiles = getMobile(Arrays.asList(assinee.split(",")));
		try{
		super.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("短信发送失败：",ex);
        }
	}
	
	/**
	 * 修改上报信息
	 * @param reportBean 上报bean
	 * @param trunks 中继段链表
	 * @param userInfo 用户信息
	 */
	public void editReport(ReportBean reportBean, List<String> trunks, UserInfo userInfo, List<FileItem> files){
		//保存上报对象
		HiddangerReport report = dao.getReportFromHiddangerId(reportBean.getHiddangerId());
		BeanUtil.copyProperties(reportBean, report);
		dao.save(report);
		
		//保存附件
		saveFiles(files, report.getHiddangerId(), userInfo);
		
		//删除中继段
		hiddangerTrunkManager.deleteTrunk(reportBean.getHiddangerId());
		//保存中继段
		for(String trunkId : trunks){
			hiddangerTrunkManager.saveTrunk(report.getHiddangerId(), trunkId);
		}
	}
	
	/**
	 * 保存上报不需要审核
	 */
	public void saveReport(ReportBean reportBean, List<String> trunks, UserInfo userInfo, List<FileItem> files){
		HiddangerRegist regist = registDao.findUniqueByProperty("id", reportBean.getHiddangerId());
		
		HiddangerReport report = new HiddangerReport();
		BeanUtil.copyProperties(reportBean, report);
		report.setId(null);
		report.setApproveTimes("0");
		
		//保存上报对象
		dao.save(report);
		
		//保存附件
		saveFiles(files, report.getHiddangerId(), userInfo);
		
		//保存中继段
		for(String trunkId : trunks){
			hiddangerTrunkManager.saveTrunk(report.getHiddangerId(), trunkId);
		}

		//更新隐患主表状态
		saveState(reportBean.getHiddangerId(), HiddangerConstant.MAKEPLAN);
		
		//流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getDeptID(), reportBean.getHiddangerId());
		workflowBo.setVariables(task, "assignee", getDept(reportBean.getHiddangerId()));
		workflowBo.completeTask(task.getId(), "makeplan");
		
		//流程历史
		workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "makeplan", "隐患上报", "");
	}
	
	/**
	 * 保存附件
	 * @param files 附件链表
	 * @param id 隐患id
	 * @param userInfo 用户信息
	 */
	public void saveFiles(List<FileItem> files, String id, UserInfo userInfo){
		uploadFile.saveFiles(files, ModuleCatalog.HIDDTROUBLEWATCH, userInfo.getRegionName(), id, "LP_HIDDANGER_REPORT", userInfo.getUserID());
	}
		
	public void approve(ApproveInfo approveInfo, UserInfo userInfo){
		approveInfo.setApproveTime(new Date());
		approveInfo.setObjectType("LP_HIDDANGER_REPORT");
		
		String hiddangerId = approveInfo.getObjectId();
		HiddangerReport report = dao.getReportFromHiddangerId(hiddangerId);
		
		String content = "";
		String state = "";
		if(approveInfo.getApproveResult().equals("0")){
			state = HiddangerConstant.REGIST;
			content = "未通过审核";
		}else if(approveInfo.getApproveResult().equals("1")){
			state = HiddangerConstant.MAKEPLAN;
			content = "已通过审核";
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
			workflowBo.setVariables(task, "transition", "makeplan");
			workflowBo.completeTask(task.getId(), "passed");
		}
		
		//流程历史
		HiddangerRegist regist = registDao.findUniqueByProperty("id", hiddangerId);
		if(approveInfo.getApproveResult().equals("0")){
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "not_passed", "隐患审核未通过", "");
		}else{
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "passed", "隐患审核通过", "");
		}
		
		//短信提醒
		//将审核结果发送给隐患上报人
		content = "【隐患盯防】您有一个名称为\""+getRegist(hiddangerId).getName()+"\"的隐患上报单 "+content+",等待您及时处理！";
		String phone = registDao.getPhoneFromUserid(report.getAuthorId());
		List<String> mobiles = com.cabletech.commons.util.StringUtils.string2List(phone, ",");
		try{
		super.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("短信发送失败：",ex);
        }
	}
	
	/**
	 * 转审
	 * @param hiddangerId 隐患id
	 * @param approver 审核人
	 * @param userInfo 用户信息
	 */
	public void transferApprove(String hiddangerId, String approver, UserInfo userInfo){
		//保存审核人
		replyApproverDAO.saveApproverOrReader(approver, hiddangerId, CommonConstant.APPROVE_MAN, "LP_HIDDANGER_REPORT");
		
		//取得抄送人
		Set<String> all = replyApproverDAO.getApprover(hiddangerId, CommonConstant.APPROVE_READ, "LP_HIDDANGER_REPORT");
		all.add(approver);
		
		//流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), hiddangerId);
		workflowBo.setVariables(task, "assignee", StringUtils.join(all.iterator(), ","));
		workflowBo.setVariables(task, "transition", "transfer");
		workflowBo.completeTask(task.getId(), "passed");
		
		//流程历史
		HiddangerRegist regist = registDao.findUniqueByProperty("id", hiddangerId);
		workflowBo.saveProcessHistory(userInfo, regist, task, approver, "passed", "上报转审", "");
		
		//短信提醒
		//发送给转审人
		String content = "【隐患盯防】用户"+userInfo.getUserName()+"将\""+getRegist(hiddangerId).getName()+"\"的隐患上报单转交给您进行审核，请及时处理";
		List<String> mobiles = getMobile(Arrays.asList(approver.split(",")));
		try{
		super.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("短信发送失败：",ex);
        }
	}
	
	public ReportBean getReportInfo(String id){
		HiddangerReport report = dao.getReportFromHiddangerId(id);
		ReportBean reportBean = null;
		if(report != null){
			reportBean = new ReportBean();
			BeanUtil.copyProperties(report, reportBean);
			reportBean.setTrunkIdsString(hiddangerTrunkManager.getTrunkFormHiddangerId(report.getHiddangerId()));
			reportBean.setTempWatchActualizeMan(report.getWatchActualizeManPhone());
			reportBean.setTempWatchPrincipal(report.getWatchPrincipalPhone());
		}
		return reportBean;
	}
	
	public void saveState(String id, String state){
		HiddangerRegist regist = registDao.get(id);
		regist.setHiddangerState(state);
		registDao.save(regist);
	}
	
	public String getDept(String id){
		return getRegist(id).getTreatDepartment();
	}
	
	public HiddangerRegist getRegist(String id){
		return registDao.findUniqueByProperty("id",id);
	}
	
	public List<String> getHiddangerPrincipal(String id){
		HiddangerRegist regist = registDao.findUniqueByProperty("id", id);
		String deptId = regist.getTreatDepartment();
		List<Map> list = registDao.getHiddangerPrincipal(deptId);
		List<String> principals = new ArrayList<String>();
		if(!list.isEmpty()){
			for(Map map : list){
				principals.add((String)map.get("linkmaninfo"));
			}
		}
		return principals;
	}
	
	public List<String> getMobile(List<String> approverIds){
		List<String> principals = new ArrayList<String>();
		for(String id : approverIds){
			String phone = registDao.getPhoneFromUserid(id);
			if(StringUtils.isNotBlank(phone)){
				principals.add(phone);
			}
		}
		return principals;
	}

	@Override
	protected HibernateDao<HiddangerReport, String> getEntityDao() {
		return dao;
	}
}
