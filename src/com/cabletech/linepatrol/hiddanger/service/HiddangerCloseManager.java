package com.cabletech.linepatrol.hiddanger.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.hiddanger.beans.CloseBean;
import com.cabletech.linepatrol.hiddanger.dao.HiddangerCloseDao;
import com.cabletech.linepatrol.hiddanger.dao.HiddangerRegistDao;
import com.cabletech.linepatrol.hiddanger.model.HiddangerClose;
import com.cabletech.linepatrol.hiddanger.model.HiddangerRegist;
import com.cabletech.linepatrol.hiddanger.workflow.HiddangerWorkflowBO;

@Service
@Transactional
public class HiddangerCloseManager extends EntityManager<HiddangerClose, String>{
	
	@Resource(name="hiddangerCloseDao")
	private HiddangerCloseDao dao;
	@Resource(name="replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	@Resource(name="hiddangerRegistDao")
	private HiddangerRegistDao registDao;
	@Resource(name="approveDAO")
	private ApproveDAO approveDAO;
	@Autowired
	private HiddangerWorkflowBO workflowBo;
	@Resource(name="smHistoryDAO")
	private SmHistoryDAO historyDAO;
	
	/**
	 * 关闭隐患并需要审核
	 * @param approveInfo
	 * @param userInfo
	 */
	public void reportClose(CloseBean closeBean, UserInfo userInfo, String approver, String reader){
		HiddangerClose close = dao.getCloseFromHiddangerId(closeBean.getHiddangerId());
		
		//审核不通过，删除旧记录并更新审核次数
		if(close != null){
			//删除审核人抄送人
			replyApproverDAO.deleteList(closeBean.getHiddangerId(), "LP_HIDDANGER_CLOSE");
			
			BeanUtil.copyProperties(closeBean, close);
			close.setCloserId(userInfo.getUserID());
		}else{
			close = new HiddangerClose();
			BeanUtil.copyProperties(closeBean, close);
			close.setId(null);
			close.setCloserId(userInfo.getUserID());
			close.setApproveTimes("0");
		}
		
		//保存隐患关闭对象
		dao.save(close);
		
		//保存审核人
		replyApproverDAO.saveApproverOrReader(approver, close.getHiddangerId(), CommonConstant.APPROVE_MAN, "LP_HIDDANGER_CLOSE");
		
		//保存抄送人
		replyApproverDAO.saveApproverOrReader(reader, close.getHiddangerId(), CommonConstant.APPROVE_READ, "LP_HIDDANGER_CLOSE");
		
		//更新隐患主表状态
		saveState(close.getHiddangerId(), HiddangerConstant.CLOSEAPPROVED);
		
		//流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getDeptID(), close.getHiddangerId());
		String assinee = "";
		if(StringUtils.isNotBlank(reader)){
			assinee = approver+","+reader;
		}else{
			assinee = approver;
		}
		workflowBo.setVariables(task, "assignee", assinee);
		workflowBo.completeTask(task.getId(), "close_approve");
		
		//流程历史
		HiddangerRegist regist = registDao.findUniqueByProperty("id", close.getHiddangerId());
		workflowBo.saveProcessHistory(userInfo, regist, task, assinee, "close_approve", "隐患关闭", "");
		
		//短信提醒
		//代维提交隐患关闭申请单，给线维中心审核
		String content = "【隐患盯防】您有一个名称为\""+getRegist(close.getHiddangerId()).getName()+"\"的隐患关闭申请等待您的审核！";
		List<String> mobiles = getMobile(Arrays.asList(assinee.split(",")));
		try{
		super.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("短信发送失败：",ex);
        }
	}
	
	/**
	 * 修改隐患关闭信息
	 * @param closeBean 关闭信息
	 * @param userInfo 用户信息
	 */
	public void editClose(CloseBean closeBean, UserInfo userInfo){
		HiddangerClose close = dao.getCloseFromHiddangerId(closeBean.getHiddangerId());
		BeanUtil.copyProperties(closeBean, close);
		
		//更新隐患关闭对象
		dao.save(close);
	}
	
	/**
	 * 隐患关闭审核
	 * @param approveInfo 审核信息
	 * @param userInfo 用户信息
	 */
	public void approve(ApproveInfo approveInfo, UserInfo userInfo){
		approveInfo.setApproveTime(new Date());
		approveInfo.setObjectType("LP_HIDDANGER_CLOSE");
		
		String hiddangerId = approveInfo.getObjectId();
		HiddangerClose close = dao.getCloseFromHiddangerId(hiddangerId);
		
		String state = "";
		if(approveInfo.getApproveResult().equals("0")){
			state = HiddangerConstant.CLOSE;
		}else if(approveInfo.getApproveResult().equals("1")){
			state = HiddangerConstant.EVALUATE;
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
		}else{
			workflowBo.setVariables(task, "assignee", userInfo.getUserID());
			workflowBo.setVariables(task, "transition", "evaluate");
			workflowBo.completeTask(task.getId(), "passed");
			
			//考核评估流程略过，强制跳过
			//task = workflowBo.getHandleTaskForId(getDept(hiddangerId), hiddangerId);
			//workflowBo.completeTask(task.getId(), "end");
		}
		
		//流程历史
		HiddangerRegist regist = registDao.findUniqueByProperty("id", close.getHiddangerId());
		if(approveInfo.getApproveResult().equals("0")){
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "not_passed", "关闭审核未通过", "");
		}else{
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "passed", "关闭审核通过", "");
		}
			
		//短信提醒
		//不通过发送给代维负责人；
		//线维中心对隐患关闭申请单审核未通过时，通知代维再次进行处理
		if(approveInfo.getApproveResult().equals("0")){
			String content = "【隐患盯防】您有一个名称为\""+getRegist(hiddangerId).getName()+"\"的隐患关闭申请未通过审核,请您及时处理！";
			String phone = registDao.getPhoneFromUserid(close.getCloserId());
			List<String> mobiles = com.cabletech.commons.util.StringUtils.string2List(phone, ",");
			try{
			super.sendMessage(content,mobiles );
			}catch(Exception ex){
	            logger.error("短信发送失败：",ex);
	        }
		}
	}
	
	/**
	 * 转审
	 * @param hiddangerId 隐患id
	 * @param approver 审核人
	 * @param userInfo 用户信息
	 */
	public void closeTransferApprove(String hiddangerId, String approver, UserInfo userInfo){
		//保存审核人
		replyApproverDAO.saveApproverOrReader(approver, hiddangerId, CommonConstant.APPROVE_MAN, "LP_HIDDANGER_CLOSE");
		
		//取得抄送人
		Set<String> all = replyApproverDAO.getApprover(hiddangerId, CommonConstant.APPROVE_READ, "LP_HIDDANGER_CLOSE");
		all.add(approver);
		
		//流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), hiddangerId);
		workflowBo.setVariables(task, "assignee", StringUtils.join(all.iterator(), ","));
		workflowBo.setVariables(task, "transition", "transfer");
		workflowBo.completeTask(task.getId(), "passed");
		
		//流程历史
		HiddangerRegist regist = registDao.findUniqueByProperty("id", hiddangerId);
		workflowBo.saveProcessHistory(userInfo, regist, task, approver, "passed", "关闭转审", "");
		
		//短信提醒
		//发送给转审人;将隐患关闭申请单转给线维中心其他人员进行审核
		String content = "【隐患盯防】用户"+userInfo.getUserName()+"将"+getRegist(hiddangerId).getName()+"\"的隐患关闭申请单转交给您审核，请您及时处理！";
		List<String> mobiles = getMobile(Arrays.asList(approver.split(",")));
		try{
		super.sendMessage(content,mobiles );
		}catch(Exception ex){
		    logger.error("短信发送失败：",ex);
		}
	}
	
	/**
	 * 更新隐患主表状态
	 * @param id 隐患id
	 * @param state 状态
	 */
	public void saveState(String id, String state){
		HiddangerRegist regist = registDao.get(id);
		regist.setHiddangerState(state);
		registDao.save(regist);
	}
	
	/**
	 * 通过隐患id得到隐患关闭信息
	 * @param id 隐患id
	 * @return 关闭信息
	 */
	public CloseBean getCloseInfo(String id){
		HiddangerClose close = dao.getCloseFromHiddangerId(id);
		CloseBean closeBean = null;
		if(close != null){
			closeBean = new CloseBean();
			BeanUtil.copyProperties(close, closeBean);
		}
		return closeBean;
	}
	
	/**
	 * 通过id找到隐患的处理部门id
	 * @param id 隐患id
	 * @return 处理部门id
	 */
	public String getDept(String id){
		HiddangerRegist regist = registDao.findUniqueByProperty("id",id);
		return regist.getTreatDepartment();
	}
	
	public HiddangerRegist getRegist(String id){
		return registDao.findUniqueByProperty("id",id);
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
	
	@Override
	protected HibernateDao<HiddangerClose, String> getEntityDao() {
		return dao;
	}
}
