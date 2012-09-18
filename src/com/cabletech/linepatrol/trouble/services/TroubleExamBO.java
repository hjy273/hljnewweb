package com.cabletech.linepatrol.trouble.services;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.dao.UserInfoDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.module.AppraiseDaily;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyDailyBO;
import com.cabletech.linepatrol.appraise.service.AppraiseDailySpecialBO;
import com.cabletech.linepatrol.commons.dao.EvaluateDao;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.Evaluate;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.trouble.dao.TroubleInfoDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleProcessUnitDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleReplyDAO;
import com.cabletech.linepatrol.trouble.module.TroubleInfo;
import com.cabletech.linepatrol.trouble.module.TroubleProcessUnit;
import com.cabletech.linepatrol.trouble.module.TroubleReply;
import com.cabletech.linepatrol.trouble.workflow.TroubleWorkflowBO;
@Service
@Transactional
public class TroubleExamBO extends EntityManager<Evaluate,String> {

	@Resource(name="evaluateDao")
	private EvaluateDao dao;

	@Resource(name="troubleReplyDAO")
	private TroubleReplyDAO replyDAO;

	@Resource(name="troubleInfoDAO")
	private TroubleInfoDAO troubleinfoDAO;
	@Resource(name="troubleProcessUnitDAO")
	private TroubleProcessUnitDAO unitDAO;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Resource(name="smHistoryDAO")
	private SmHistoryDAO historyDAO;

	@Autowired
	private TroubleWorkflowBO workflowBo;
	
	@Autowired
	private AppraiseDailyDailyBO appraiseDailyBO;
	@Autowired
	private AppraiseDailySpecialBO appraiseDailySpecialBO;

	/**
	 * ��ѯ�����˵Ĵ����б�
	 * @return
	 */
	@Transactional(readOnly = true)
	public List findWaitExamTroubles(UserInfo user){
		return replyDAO.findWaitExamTroubles(user);
	}


	public void saveEvaluate(UserInfo user, String replyid, String troubleid,
			AppraiseDailyBean appraiseDailyBean,List<AppraiseDailyBean> appraiseDailySpecialBeans) throws Exception {
		TroubleInfo trouble = troubleinfoDAO.get(troubleid);
		TroubleReply reply = replyDAO.get(replyid);
		reply.setEvaluateState(TroubleConstant.REPLY_EXAM_STATE_Y);
		replyDAO.save(reply);
		//�����ճ�������Ϣ
		AppraiseDaily appraiseDailyBack = appraiseDailyBO.saveAppraiseDailyByBean(appraiseDailyBean);
		sendMsg(trouble, reply, appraiseDailyBack);
		appraiseDailySpecialBO.saveAppraiseDailyByBean(appraiseDailySpecialBeans);
		TroubleProcessUnit unit = unitDAO.getProcessUnitByReplyId(replyid);
		String unitid = unit.getId();
		evaluate(user, unitid);
		approvePassed(user, unitid);
	}

	/**
	 * ���Ͷ���(���ŷ��͸���д���Ϸ���������)
	 */
	public void sendMsg(TroubleInfo trouble,TroubleReply reply,AppraiseDaily appraiseDaily){
		try{
			//String score = evaluate.getEntityScore();
			String eid = appraiseDaily.getId();
			UserInfoDAOImpl userdao = new UserInfoDAOImpl();
			String userid = reply.getReplyManId();
			UserInfo user = userdao.findById(userid);
			if(user!=null){
				String phone = user.getPhone();
				if(phone!=null && !"".equals(phone)){
					//���Ͷ���
					String content = "����·���ϡ���λ��һ������Ϊ\""+trouble.getTroubleName()+"\"�ķ������Ѿ����˽�����";
					logger.info("��������: "+phone +":"+content);
					super.sendMessage(content, phone);
//					smSendProxy.simpleSend(phone,content, null, null, true);
					//������ż�¼
					SMHistory history = new SMHistory();
					history.setSimIds(phone);
					history.setSendContent(content);
					history.setSendTime(new Date());
					history.setSmType(TroubleConstant.LP_EVALUATE);
					history.setObjectId(eid);
					history.setBusinessModule(TroubleConstant.TROUBLE_MODULE);
					historyDAO.save(history);
				}
			}
		}catch(Exception e){
			e.getStackTrace();
		}
	}

	private void evaluate(UserInfo user,String unitid) throws Exception {
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		Task task = workflowBo.getHandleTaskForId(user.getUserID(),unitid);
		//System.out.println("���ϴ����ˣ�" + task.getName());
		if (task != null
				&& TroubleWorkflowBO.EVALUATE_TASK.equals(task.getName())) {
			workflowBo.completeTask(task.getId(), "end");
			processHistoryBean.initial(task, user, "",
					ModuleCatalog.TROUBLE);
			processHistoryBean.setNextOperateUserId("");
			processHistoryBean.setObjectId(unitid);
			processHistoryBean.setProcessAction("���Ͽ���");
			processHistoryBean.setTaskOutCome("end");
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
		System.out.println("�����Ѿ����ˣ�");
	}

	
	private void approvePassed(UserInfo user,String unitid) {
		Task task = workflowBo.getHandleTaskForId(user.getUserID(), unitid);
		if (task != null
				&& TroubleWorkflowBO.APPROVE_TASK.equals(task.getName())) {
					workflowBo.setVariables(task, "transition", "close");
					workflowBo.completeTask(task.getId(), "passed");
					System.out.println("�����Ѿ��رգ�");
		}
	}

	public Evaluate getEvaluate(String id, String type){
		return dao.getEvaluate(id, type);
	}




	@Override
	protected HibernateDao<Evaluate, String> getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}


}
