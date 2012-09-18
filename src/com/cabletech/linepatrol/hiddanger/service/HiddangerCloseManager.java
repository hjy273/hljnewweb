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
	 * �ر���������Ҫ���
	 * @param approveInfo
	 * @param userInfo
	 */
	public void reportClose(CloseBean closeBean, UserInfo userInfo, String approver, String reader){
		HiddangerClose close = dao.getCloseFromHiddangerId(closeBean.getHiddangerId());
		
		//��˲�ͨ����ɾ���ɼ�¼��������˴���
		if(close != null){
			//ɾ������˳�����
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
		
		//���������رն���
		dao.save(close);
		
		//���������
		replyApproverDAO.saveApproverOrReader(approver, close.getHiddangerId(), CommonConstant.APPROVE_MAN, "LP_HIDDANGER_CLOSE");
		
		//���泭����
		replyApproverDAO.saveApproverOrReader(reader, close.getHiddangerId(), CommonConstant.APPROVE_READ, "LP_HIDDANGER_CLOSE");
		
		//������������״̬
		saveState(close.getHiddangerId(), HiddangerConstant.CLOSEAPPROVED);
		
		//����
		Task task = workflowBo.getHandleTaskForId(userInfo.getDeptID(), close.getHiddangerId());
		String assinee = "";
		if(StringUtils.isNotBlank(reader)){
			assinee = approver+","+reader;
		}else{
			assinee = approver;
		}
		workflowBo.setVariables(task, "assignee", assinee);
		workflowBo.completeTask(task.getId(), "close_approve");
		
		//������ʷ
		HiddangerRegist regist = registDao.findUniqueByProperty("id", close.getHiddangerId());
		workflowBo.saveProcessHistory(userInfo, regist, task, assinee, "close_approve", "�����ر�", "");
		
		//��������
		//��ά�ύ�����ر����뵥������ά�������
		String content = "����������������һ������Ϊ\""+getRegist(close.getHiddangerId()).getName()+"\"�������ر�����ȴ�������ˣ�";
		List<String> mobiles = getMobile(Arrays.asList(assinee.split(",")));
		try{
		super.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("���ŷ���ʧ�ܣ�",ex);
        }
	}
	
	/**
	 * �޸������ر���Ϣ
	 * @param closeBean �ر���Ϣ
	 * @param userInfo �û���Ϣ
	 */
	public void editClose(CloseBean closeBean, UserInfo userInfo){
		HiddangerClose close = dao.getCloseFromHiddangerId(closeBean.getHiddangerId());
		BeanUtil.copyProperties(closeBean, close);
		
		//���������رն���
		dao.save(close);
	}
	
	/**
	 * �����ر����
	 * @param approveInfo �����Ϣ
	 * @param userInfo �û���Ϣ
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
		
		//���������״̬
		saveState(hiddangerId, state);

		//���������Ϣ
		approveDAO.saveApproveInfo(approveInfo);
		
		//����
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), hiddangerId);
		if(approveInfo.getApproveResult().equals("0")){
			workflowBo.setVariables(task, "assignee", getDept(hiddangerId));
			workflowBo.completeTask(task.getId(), "not_passed");
		}else{
			workflowBo.setVariables(task, "assignee", userInfo.getUserID());
			workflowBo.setVariables(task, "transition", "evaluate");
			workflowBo.completeTask(task.getId(), "passed");
			
			//�������������Թ���ǿ������
			//task = workflowBo.getHandleTaskForId(getDept(hiddangerId), hiddangerId);
			//workflowBo.completeTask(task.getId(), "end");
		}
		
		//������ʷ
		HiddangerRegist regist = registDao.findUniqueByProperty("id", close.getHiddangerId());
		if(approveInfo.getApproveResult().equals("0")){
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "not_passed", "�ر����δͨ��", "");
		}else{
			workflowBo.saveProcessHistory(userInfo, regist, task, regist.getTreatDepartment(), "passed", "�ر����ͨ��", "");
		}
			
		//��������
		//��ͨ�����͸���ά�����ˣ�
		//��ά���Ķ������ر����뵥���δͨ��ʱ��֪ͨ��ά�ٴν��д���
		if(approveInfo.getApproveResult().equals("0")){
			String content = "����������������һ������Ϊ\""+getRegist(hiddangerId).getName()+"\"�������ر�����δͨ�����,������ʱ����";
			String phone = registDao.getPhoneFromUserid(close.getCloserId());
			List<String> mobiles = com.cabletech.commons.util.StringUtils.string2List(phone, ",");
			try{
			super.sendMessage(content,mobiles );
			}catch(Exception ex){
	            logger.error("���ŷ���ʧ�ܣ�",ex);
	        }
		}
	}
	
	/**
	 * ת��
	 * @param hiddangerId ����id
	 * @param approver �����
	 * @param userInfo �û���Ϣ
	 */
	public void closeTransferApprove(String hiddangerId, String approver, UserInfo userInfo){
		//���������
		replyApproverDAO.saveApproverOrReader(approver, hiddangerId, CommonConstant.APPROVE_MAN, "LP_HIDDANGER_CLOSE");
		
		//ȡ�ó�����
		Set<String> all = replyApproverDAO.getApprover(hiddangerId, CommonConstant.APPROVE_READ, "LP_HIDDANGER_CLOSE");
		all.add(approver);
		
		//����
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), hiddangerId);
		workflowBo.setVariables(task, "assignee", StringUtils.join(all.iterator(), ","));
		workflowBo.setVariables(task, "transition", "transfer");
		workflowBo.completeTask(task.getId(), "passed");
		
		//������ʷ
		HiddangerRegist regist = registDao.findUniqueByProperty("id", hiddangerId);
		workflowBo.saveProcessHistory(userInfo, regist, task, approver, "passed", "�ر�ת��", "");
		
		//��������
		//���͸�ת����;�������ر����뵥ת����ά����������Ա�������
		String content = "�������������û�"+userInfo.getUserName()+"��"+getRegist(hiddangerId).getName()+"\"�������ر����뵥ת��������ˣ�������ʱ����";
		List<String> mobiles = getMobile(Arrays.asList(approver.split(",")));
		try{
		super.sendMessage(content,mobiles );
		}catch(Exception ex){
		    logger.error("���ŷ���ʧ�ܣ�",ex);
		}
	}
	
	/**
	 * ������������״̬
	 * @param id ����id
	 * @param state ״̬
	 */
	public void saveState(String id, String state){
		HiddangerRegist regist = registDao.get(id);
		regist.setHiddangerState(state);
		registDao.save(regist);
	}
	
	/**
	 * ͨ������id�õ������ر���Ϣ
	 * @param id ����id
	 * @return �ر���Ϣ
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
	 * ͨ��id�ҵ������Ĵ�����id
	 * @param id ����id
	 * @return ������id
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
