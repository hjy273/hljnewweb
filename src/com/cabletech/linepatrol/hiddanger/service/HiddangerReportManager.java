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
	 * �����ϱ�����Ҫ���
	 */
	public void saveReport(ReportBean reportBean, List<String> trunks, UserInfo userInfo, String approver, String reader, List<FileItem> files){
		HiddangerRegist regist = registDao.findUniqueByProperty("id", reportBean.getHiddangerId());
		HiddangerReport report = dao.getReportFromHiddangerId(reportBean.getHiddangerId());
		
		//��ˣ�ɾ���ɼ�¼��������˴���
		if(report != null){
			//ɾ���м̶�
			hiddangerTrunkManager.deleteTrunk(reportBean.getHiddangerId());
			
			//ɾ������˳�����
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
		
		//�����ϱ�����
		dao.save(report);
		
		//���渽��
		saveFiles(files, report.getHiddangerId(), userInfo);
		
		//�����м̶�
		for(String trunkId : trunks){
			hiddangerTrunkManager.saveTrunk(report.getHiddangerId(), trunkId);
		}
		
		//���������
		replyApproverDAO.saveApproverOrReader(approver, report.getHiddangerId(), CommonConstant.APPROVE_MAN, "LP_HIDDANGER_REPORT");
		
		//���泭����
		replyApproverDAO.saveApproverOrReader(reader, report.getHiddangerId(), CommonConstant.APPROVE_READ, "LP_HIDDANGER_REPORT");
		
		//������������״̬
		saveState(reportBean.getHiddangerId(), HiddangerConstant.NEEDAPPROVE);
		
		//����
		Task task = workflowBo.getHandleTaskForId(userInfo.getDeptID(), reportBean.getHiddangerId());
		
		String assinee = "";
		if(StringUtils.isNotBlank(reader)){
			assinee = approver+","+reader;
		}else{
			assinee = approver;
		}
		workflowBo.setVariables(task, "assignee", assinee);
		workflowBo.completeTask(task.getId(), "add_approve");
		
		//������ʷ
		workflowBo.saveProcessHistory(userInfo, regist, task, assinee, "add_approve", "�����ϱ�", "");
		
		//��������
		//��һ�������ϱ���Ϣ���͸������
		String content = "����������������һ������Ϊ\""+getRegist(reportBean.getHiddangerId()).getName()+"\"�������ϱ����ȴ�������ˣ�";
		List<String> mobiles = getMobile(Arrays.asList(assinee.split(",")));
		try{
		super.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("���ŷ���ʧ�ܣ�",ex);
        }
	}
	
	/**
	 * �޸��ϱ���Ϣ
	 * @param reportBean �ϱ�bean
	 * @param trunks �м̶�����
	 * @param userInfo �û���Ϣ
	 */
	public void editReport(ReportBean reportBean, List<String> trunks, UserInfo userInfo, List<FileItem> files){
		//�����ϱ�����
		HiddangerReport report = dao.getReportFromHiddangerId(reportBean.getHiddangerId());
		BeanUtil.copyProperties(reportBean, report);
		dao.save(report);
		
		//���渽��
		saveFiles(files, report.getHiddangerId(), userInfo);
		
		//ɾ���м̶�
		hiddangerTrunkManager.deleteTrunk(reportBean.getHiddangerId());
		//�����м̶�
		for(String trunkId : trunks){
			hiddangerTrunkManager.saveTrunk(report.getHiddangerId(), trunkId);
		}
	}
	
	/**
	 * �����ϱ�����Ҫ���
	 */
	public void saveReport(ReportBean reportBean, List<String> trunks, UserInfo userInfo, List<FileItem> files){
		HiddangerRegist regist = registDao.findUniqueByProperty("id", reportBean.getHiddangerId());
		
		HiddangerReport report = new HiddangerReport();
		BeanUtil.copyProperties(reportBean, report);
		report.setId(null);
		report.setApproveTimes("0");
		
		//�����ϱ�����
		dao.save(report);
		
		//���渽��
		saveFiles(files, report.getHiddangerId(), userInfo);
		
		//�����м̶�
		for(String trunkId : trunks){
			hiddangerTrunkManager.saveTrunk(report.getHiddangerId(), trunkId);
		}

		//������������״̬
		saveState(reportBean.getHiddangerId(), HiddangerConstant.MAKEPLAN);
		
		//����
		Task task = workflowBo.getHandleTaskForId(userInfo.getDeptID(), reportBean.getHiddangerId());
		workflowBo.setVariables(task, "assignee", getDept(reportBean.getHiddangerId()));
		workflowBo.completeTask(task.getId(), "makeplan");
		
		//������ʷ
		workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "makeplan", "�����ϱ�", "");
	}
	
	/**
	 * ���渽��
	 * @param files ��������
	 * @param id ����id
	 * @param userInfo �û���Ϣ
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
			content = "δͨ�����";
		}else if(approveInfo.getApproveResult().equals("1")){
			state = HiddangerConstant.MAKEPLAN;
			content = "��ͨ�����";
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
			workflowBo.setVariables(task, "transition", "makeplan");
			workflowBo.completeTask(task.getId(), "passed");
		}
		
		//������ʷ
		HiddangerRegist regist = registDao.findUniqueByProperty("id", hiddangerId);
		if(approveInfo.getApproveResult().equals("0")){
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "not_passed", "�������δͨ��", "");
		}else{
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "passed", "�������ͨ��", "");
		}
		
		//��������
		//����˽�����͸������ϱ���
		content = "����������������һ������Ϊ\""+getRegist(hiddangerId).getName()+"\"�������ϱ��� "+content+",�ȴ�����ʱ����";
		String phone = registDao.getPhoneFromUserid(report.getAuthorId());
		List<String> mobiles = com.cabletech.commons.util.StringUtils.string2List(phone, ",");
		try{
		super.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("���ŷ���ʧ�ܣ�",ex);
        }
	}
	
	/**
	 * ת��
	 * @param hiddangerId ����id
	 * @param approver �����
	 * @param userInfo �û���Ϣ
	 */
	public void transferApprove(String hiddangerId, String approver, UserInfo userInfo){
		//���������
		replyApproverDAO.saveApproverOrReader(approver, hiddangerId, CommonConstant.APPROVE_MAN, "LP_HIDDANGER_REPORT");
		
		//ȡ�ó�����
		Set<String> all = replyApproverDAO.getApprover(hiddangerId, CommonConstant.APPROVE_READ, "LP_HIDDANGER_REPORT");
		all.add(approver);
		
		//����
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), hiddangerId);
		workflowBo.setVariables(task, "assignee", StringUtils.join(all.iterator(), ","));
		workflowBo.setVariables(task, "transition", "transfer");
		workflowBo.completeTask(task.getId(), "passed");
		
		//������ʷ
		HiddangerRegist regist = registDao.findUniqueByProperty("id", hiddangerId);
		workflowBo.saveProcessHistory(userInfo, regist, task, approver, "passed", "�ϱ�ת��", "");
		
		//��������
		//���͸�ת����
		String content = "�������������û�"+userInfo.getUserName()+"��\""+getRegist(hiddangerId).getName()+"\"�������ϱ���ת������������ˣ��뼰ʱ����";
		List<String> mobiles = getMobile(Arrays.asList(approver.split(",")));
		try{
		super.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("���ŷ���ʧ�ܣ�",ex);
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
