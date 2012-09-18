package com.cabletech.linepatrol.acceptance.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.acceptance.beans.RpBean;
import com.cabletech.linepatrol.acceptance.dao.ApplyPipeDao;
import com.cabletech.linepatrol.acceptance.dao.ApplyTaskDao;
import com.cabletech.linepatrol.acceptance.dao.PayPipeDao;
import com.cabletech.linepatrol.acceptance.dao.PipeResultDao;
import com.cabletech.linepatrol.acceptance.dao.PipeTaskDao;
import com.cabletech.linepatrol.acceptance.dao.SubflowDao;
import com.cabletech.linepatrol.acceptance.model.ApplyPipe;
import com.cabletech.linepatrol.acceptance.model.PayPipe;
import com.cabletech.linepatrol.acceptance.model.PipeResult;
import com.cabletech.linepatrol.acceptance.model.PipeTask;
import com.cabletech.linepatrol.acceptance.workflow.AcceptanceFlow;
import com.cabletech.linepatrol.resource.dao.PipeAddoneDao;
import com.cabletech.linepatrol.resource.dao.PipeDao;
import com.cabletech.linepatrol.resource.model.Pipe;
import com.cabletech.linepatrol.resource.model.PipeAddone;

@Service
@Transactional
public class PipeTaskManager extends EntityManager<PipeTask, String>{
	@Resource(name="pipeTaskDao")
	private PipeTaskDao dao;
	@Resource(name="pipeDao")
	private PipeDao pipeDao;
	@Resource(name="pipeAddoneDao")
	private PipeAddoneDao pipeAddoneDao;
	@Resource(name="payPipeDao")
	private PayPipeDao payPipeDao;
	@Resource(name="pipeResultDao")
	private PipeResultDao pipeResultDao;
	@Resource(name="applyTaskDao")
	private ApplyTaskDao applyTaskDao;
	@Autowired
	private AcceptanceFlow workflowBo;
	@Resource(name="applyTaskManager")
	private ApplyTaskManager atm;
	@Resource(name="subflowDao")
	private SubflowDao subflowDao;
	@Resource(name="applyPipeDao")
	private ApplyPipeDao apd;
	@Resource(name="applyManager")
	private ApplyManager am;
	
	public void savePipeRecord(UserInfo userInfo, String subflowId, String objectId, RpBean rpBean){
		String applyId = am.getSubflow(subflowId).getApplyId();
		
		//����ܵ�
		Pipe pipe = new Pipe();
		BeanUtil.copyProperties(rpBean, pipe);
		if(StringUtils.isBlank(rpBean.getPipeId())){
			pipe.setId(null);  //����
		}else{
			pipe.setId(rpBean.getPipeId());  //���飬�޸�
		}
		pipe.setIsCheckOut(AcceptanceConstant.NOPASS);
		pipe.setMaintenanceId(userInfo.getDeptID());
		pipeDao.save(pipe);
		
		//����ܵ�������Ϣ
		PipeAddone pipeAddone = new PipeAddone();
		BeanUtil.copyProperties(rpBean, pipeAddone);
		if(StringUtils.isBlank(rpBean.getPipeAddOneId())){
			pipeAddone.setId(null);  //����
		}else{
			pipeAddone.setId(rpBean.getPipeAddOneId());  //���飬�޸�
		}
		pipeAddone.setPipId(pipe.getId());
		pipeAddoneDao.save(pipeAddone);
		
		//���潻ά��Ϣ
		PayPipe payPipe = new PayPipe();
		BeanUtil.copyProperties(rpBean, payPipe);
		
		PipeResult pipeResult = new PipeResult();
		BeanUtil.copyProperties(rpBean, pipeResult);
		
		if(StringUtils.isBlank(rpBean.getPayPipeId())){
			payPipe.setId(null);  //����
		}else{
			payPipe.setId(rpBean.getPayPipeId());  //���飬�޸�
		}
		if(StringUtils.isBlank(rpBean.getPipeResultId())){
			pipeResult.setId(null);  //����
		}else{
			pipeResult.setId(rpBean.getPipeResultId());  //�޸�
		}
		
		PipeTask pipeTask = dao.loadPipeTaskFromApplyId(applyId, objectId);
		payPipe.setTaskId(pipeTask.getId());//����pipetask�����id****
		payPipe.setPipeId(pipe.getId());
		payPipe.setAcceptanceTimes(pipeResult.getTimes());
		payPipe.setAcceptanceDate(pipeResult.getFactDate());
		payPipe.setPassed(pipeResult.getResult());
		if(pipeResult.getResult().equals(AcceptanceConstant.PASSED)){
			payPipe.setPassedTime(pipeResult.getFactDate());
		}else{
			payPipe.setPassedTime(null);
		}
		payPipeDao.save(payPipe);
		
		pipeResult.setPayPipeId(payPipe.getId());
		pipeResult.setRemark(rpBean.getResultRemark());
		pipeResultDao.save(pipeResult);
		
		//��������״̬�����������������
		atm.updateTaskState(pipeTask.getTaskId());
		
		//���¹ܵ�Ϊ��¼��״̬
		ApplyPipe applyPipe = apd.get(objectId);
		applyPipe.setIsrecord(AcceptanceConstant.ISRECORD);
		//���¹ܵ�����״̬
		if(pipeResult.getResult().equals(AcceptanceConstant.PASSED)){
			applyPipe.setIspassed(AcceptanceConstant.PASSED);
		}else{
			applyPipe.setIspassed(AcceptanceConstant.NOTPASSED);
		}
		apd.save(applyPipe);
	}
	
	public PayPipe loadPayPipeFromPipeId(String applyid, String pipeId){
		PipeTask pipeTask = dao.loadPipeTaskFromApplyId(applyid, pipeId);
		
		return payPipeDao.getPayPipeFrompTaskId(pipeTask.getId());
	}
	
	public PipeAddone loadPipeAddoneFromPipeId(String applyid, String pipeId){
		PayPipe payPipe = loadPayPipeFromPipeId(applyid, pipeId);
		return payPipe == null ? null : loadPipeAddone(payPipe.getPipeId());
	}
	
	public String loadPipeResultFromPipeId(String id, String pipeId){
		int number = 0;
		PayPipe payPipe = loadPayPipeFromPipeId(id, pipeId);
		if(payPipe != null)
			number = loadPipeResult(payPipe.getPipeId()).size();
		number++;
		return String.valueOf(number);
	}
	
	public List<PipeResult> loadPipeResult(String payPipeId){
		return pipeResultDao.getPipeResults(payPipeId);
	}
	
	
	//��ȡ����
	public RpBean loadRpBean(String id, String pipeId){
		RpBean rpBean = new RpBean();
		
		PayPipe payPipe = loadPayPipeFromPipeId(id, pipeId);
		Pipe pipe = loadPipe(payPipe.getPipeId());
		PipeAddone pipeAddone = loadPipeAddone(payPipe.getPipeId());
		
		BeanUtil.copyProperties(payPipe, rpBean);
		BeanUtil.copyProperties(pipe, rpBean);
		BeanUtil.copyProperties(pipeAddone, rpBean);
		
		rpBean.setPayPipeId(payPipe.getId());
		rpBean.setPipeId(pipe.getId());
		rpBean.setPipeAddOneId(pipeAddone.getId());
		rpBean.setTimes(payPipe.getAcceptanceTimes());
		return rpBean;
	}
	
	//��ȡȫ������
	public RpBean editRpBean(String id, String pipeId){
		RpBean rpBean = new RpBean();
		
		PayPipe payPipe = loadPayPipeFromPipeId(id, pipeId);
		Pipe pipe = loadPipe(payPipe.getPipeId());
		PipeAddone pipeAddone = loadPipeAddone(payPipe.getPipeId());
		PipeResult pipeResult = pipeResultDao.loadPipeResult(payPipe.getId());
		
		BeanUtil.copyProperties(payPipe, rpBean);
		BeanUtil.copyProperties(pipe, rpBean);
		BeanUtil.copyProperties(pipeAddone, rpBean);
		BeanUtil.copyProperties(pipeResult, rpBean);
		
		rpBean.setPayPipeId(payPipe.getId());
		rpBean.setPipeId(pipe.getId());
		rpBean.setPipeAddOneId(pipeAddone.getId());
		rpBean.setPipeResultId(pipeResult.getId());
		rpBean.setResultRemark(pipeResult.getRemark());
		
		rpBean.setFactDate(DateUtil.UtilDate2Str(pipeResult.getFactDate(), "yyyy/MM/dd"));
		rpBean.setPlanDate(DateUtil.UtilDate2Str(pipeResult.getPlanDate(), "yyyy/MM/dd"));
		return rpBean;
	}
	
	public Pipe loadPipe(String id){
		return pipeDao.findUniqueByProperty("id", id);
	}
	
	public PipeResult findPipeResult(List<PipeResult> list, String id){
		PipeResult pipeResult = new PipeResult();
		for(PipeResult p : list){
			if(p.getId().equals(id)){
				pipeResult = p;
			}
		}
		return pipeResult;
	}
	
	void approvePipe(String taskId){
		List<PayPipe> payPipes = payPipeDao.getPayPipesByTaskId(taskId);
		for(PayPipe payPipe :payPipes){
			if(payPipe.getPassed().equals(AcceptanceConstant.PASSED)){
				Pipe pipe = pipeDao.findByUnique("id", payPipe.getPipeId());
				pipe.setIsCheckOut(AcceptanceConstant.PASS);
				pipe.setFinishTime(DateUtil.StringToDate(new DateUtil().getNextMonthFirst(), "yyyy-MM-dd"));
				pipeDao.save(pipe);
			}
		}
	}
	
	public List<PipeResult> getPipeResults(String pipeId){
		return pipeResultDao.getPipeResults(pipeId);
	}
	
	public PipeAddone loadPipeAddone(String pipeId){
		return pipeAddoneDao.findUniqueByProperty("pipId", pipeId);
	}
	
	@Override
	protected HibernateDao<PipeTask, String> getEntityDao() {
		return dao;
	}
}