package com.cabletech.linepatrol.drill.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyDailyBO;
import com.cabletech.linepatrol.appraise.service.AppraiseDailySpecialBO;
import com.cabletech.linepatrol.commons.dao.EvaluateDao;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.drill.dao.DrillExamDao;
import com.cabletech.linepatrol.drill.dao.DrillPlanDao;
import com.cabletech.linepatrol.drill.dao.DrillPlanModifyDao;
import com.cabletech.linepatrol.drill.dao.DrillSummaryDao;
import com.cabletech.linepatrol.drill.dao.DrillTaskConDao;
import com.cabletech.linepatrol.drill.dao.DrillTaskDao;
import com.cabletech.linepatrol.drill.module.DrillConstant;
import com.cabletech.linepatrol.drill.module.DrillPlan;
import com.cabletech.linepatrol.drill.module.DrillSummary;
import com.cabletech.linepatrol.drill.module.DrillTask;
import com.cabletech.linepatrol.drill.module.DrillTaskCon;
import com.cabletech.linepatrol.drill.workflow.DrillWorkflowBO;

@Service
@Transactional
public class DrillExamBo extends EntityManager<DrillTask, String> {


	@Override
	protected HibernateDao<DrillTask, String> getEntityDao() {
		return drillExamDao;
	}
	
	@Autowired
	private DrillWorkflowBO workflowBo;

	@Resource(name = "drillExamDao")
	private DrillExamDao drillExamDao;

	@Resource(name = "drillTaskDao")
	private DrillTaskDao drillTaskDao;

	@Resource(name = "drillTaskConDao")
	private DrillTaskConDao drillTaskConDao;

	@Resource(name = "drillPlanDao")
	private DrillPlanDao drillPlanDao;
	
	@Resource(name = "drillPlanModifyDao")
	private DrillPlanModifyDao drillPlanModifyDao;

	@Resource(name = "drillSummaryDao")
	private DrillSummaryDao drillSummaryDao;
	
	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;
	
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;

	public List getDrillExamList() {
		return drillExamDao.getDrillExamList();
	}
	
	@Autowired
	private AppraiseDailyDailyBO appraiseDailyBO;
	@Autowired
	private AppraiseDailySpecialBO appraiseDailySpecialBO;

	/**
	 * 获得演练评分列表
	 * 
	 * @param summaryId
	 * @return
	 */
	public Map getExamDrill(String summaryId) {
		String planId = drillSummaryDao.getPlanIdBySummaryId(summaryId);
		String taskId = drillPlanDao.getTaskIdByPlanId(planId);
		Map map = new HashMap();
		DrillTask drillTask = drillTaskDao.findByUnique("id", taskId);
		DrillPlan drillPlan = drillPlanDao.findByUnique("id", planId);
		DrillSummary drillSummary = drillSummaryDao.findByUnique("id",
				summaryId);
		List list = drillPlanModifyDao.findByProperty("planId", planId);
		map.put("drillTask", drillTask);
		map.put("drillPlan", drillPlan);
		map.put("drillSummary", drillSummary);
		map.put("list", list);
		return map;
	}

	/**
	 * 演练评分
	 * 
	 * @param score：分数
	 * @param remark：评分评论
	 * @param userInfo
	 * @param taskId：演练任务ID
	 * @param planId：演练方案ID
	 * @param contractorId：代维单位ID
	 * @throws ServiceException
	 */
	public void examDrill(UserInfo userInfo,String taskId, String planId, String contractorId,AppraiseDailyBean appraiseDailyBean,List<AppraiseDailyBean> speicalBeans)
			throws ServiceException {
		/*Evaluate evaluate = new Evaluate();
		evaluate.setEntityScore(score);
		evaluate.setEntityRemark(remark);
		evaluate.setEvaluator(userInfo.getUserID());
		evaluate.setEntityType(DrillConstant.LP_DRILL_EVALUATE);
		evaluate.setEntityId(planId);
		evaluate.setEvaluaterDate(new Date());
		evaluateDao.savaEvaluate(evaluate);*/
		
		//保存日常考核信息
		appraiseDailyBO.saveAppraiseDailyByBean(appraiseDailyBean);
		//保存专项日常考核信息
		appraiseDailySpecialBO.saveAppraiseDailyByBean(speicalBeans);

		drillTaskConDao.setStateByContractorIdAndTaskId(contractorId, taskId,
				DrillTaskCon.DRILLRFINISH);
		
		DrillTask drillTask = drillTaskDao.findByUnique("id", taskId);
		DrillPlan drillPlan = drillPlanDao.findByUnique("id", planId);
		String name = drillTask.getName();
		String creator = drillPlan.getCreator();
		String phone = getPhoneByUserId(creator);
		
		String content = "【演练】贵单位的名称为\"" + name + "\"的演练任务已经考核！";
		
		
		String eid = drillTaskConDao.getIdByConIdAndTaskId(taskId, contractorId);
		//JBPM
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), eid);
		if (task != null
				&& DrillWorkflowBO.EVALUATE_TASK.equals(task.getName())) {
			System.out.println("演练待考核：" + task.getName());
			workflowBo.completeTask(task.getId(), "end");
			System.out.println("演练已经考核！");
			
			//保存流程历史
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.setProcessAction("演练评分");//添加流程处理动作说明
			processHistoryBean.setTaskOutCome("end");//添加工作流流向信息
			processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
			processHistoryBean.setNextOperateUserId("");//添加下一步处理人的编号
			processHistoryBean.setObjectId(eid);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		sendMessage(content, phone);
		
		saveMessage(content, phone, planId, DrillConstant.LP_DRILLPLAN, ModuleCatalog.DRILL);
	}
	
	/**
	 * 通过用户ID查询用户的手机号码
	 * 
	 * @param userId：用户ID
	 * @return：返回用户手机号码
	 */
	public String getPhoneByUserId(String userId) {
		String hql = "from UserInfo userInfo where userInfo.userID=?";
		List list = drillSummaryDao.getHibernateTemplate().find(hql, userId);
		if (list != null && !list.equals("")) {
			UserInfo userInfo = (UserInfo) list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}
	
	/**
	 * 发送短信
	 * 
	 * @param content：短信内容
	 * @param mobiles：接收短信手机号码
	 */
	public void sendMessage(String content, String mobiles) {
		if(mobiles != null && !"".equals(mobiles)){
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			super.sendMessage(content, mobileList);
		}
	}
	
	/**
	 * 保存短信记录
	 * 
	 * @param content：短信内容
	 * @param mobiles：接收短信手机号码
	 * @param entityId：实体ID
	 * @param entityType：实体类型
	 * @param entityModule：实体模型
	 */
	public void saveMessage(String content, String mobiles, String entityId,
			String entityType, String entityModule) {
		if(mobiles != null && !"".equals(mobiles)){
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			for (String mobile : mobileList) {
				SMHistory history = new SMHistory();
				history.setSimIds(mobile);
				history.setSendContent(content);
				history.setSendTime(new Date());
				history.setSmType(entityType);
				history.setObjectId(entityId);
				history.setBusinessModule(entityModule);
				historyDAO.save(history);
			}
		}
	}
}