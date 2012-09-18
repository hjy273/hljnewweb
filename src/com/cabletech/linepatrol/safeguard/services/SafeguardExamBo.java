package com.cabletech.linepatrol.safeguard.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.safeguard.beans.SafeguardExamBean;
import com.cabletech.linepatrol.safeguard.dao.SafeguardConDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardExamDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardPlanDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSegmentDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSpplanDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSummaryDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardTaskDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardCon;
import com.cabletech.linepatrol.safeguard.module.SafeguardConstant;
import com.cabletech.linepatrol.safeguard.module.SafeguardPlan;
import com.cabletech.linepatrol.safeguard.module.SafeguardSpplan;
import com.cabletech.linepatrol.safeguard.module.SafeguardSummary;
import com.cabletech.linepatrol.safeguard.module.SafeguardTask;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardWorkflowBO;

@Service
@Transactional
public class SafeguardExamBo extends EntityManager<SafeguardTask, String> {

    @Override
    protected HibernateDao<SafeguardTask, String> getEntityDao() {
        return safeguardExamDao;
    }

    @Autowired
    private SafeguardWorkflowBO workflowBo;

    @Resource(name = "safeguardExamDao")
    private SafeguardExamDao safeguardExamDao;

    @Resource(name = "safeguardConDao")
    private SafeguardConDao safeguardConDao;

    @Resource(name = "safeguardSpplanDao")
    private SafeguardSpplanDao safeguardSpplanDao;

    @Resource(name = "safeguardPlanDao")
    private SafeguardPlanDao safeguardPlanDao;

    @Resource(name = "safeguardTaskDao")
    private SafeguardTaskDao safeguardTaskDao;

    @Resource(name = "safeguardSegmentDao")
    private SafeguardSegmentDao safeguardSegmentDao;

    @Resource(name = "safeguardSummaryDao")
    private SafeguardSummaryDao safeguardSummaryDao;

    @Resource(name = "smHistoryDAO")
    private SmHistoryDAO historyDAO;

    @Resource(name = "processHistoryBO")
    private ProcessHistoryBO processHistoryBO;

    @Autowired
    private AppraiseDailyDailyBO appraiseDailyBO;

    @Autowired
    private AppraiseDailySpecialBO appraiseDailySpecialBO;

    /**
     * 由保障总结ID查询保障任务、保障计划、保障计划与中继段关系、保障总结
     * 
     * @param summaryId：保障总结ID
     * @return
     */
    public Map getSafeguardSummaryData(String summaryId) {
        String planId = safeguardSummaryDao.getPlanIdBySummaryId(summaryId);
        String taskId = safeguardPlanDao.getTaskIdByPlanId(planId);
        SafeguardTask safeguardTask = safeguardTaskDao.findByUnique("id", taskId);
        SafeguardPlan safeguardPlan = safeguardPlanDao.findByUnique("id", planId);
        SafeguardSummary safeguardSummary = safeguardSummaryDao.findByUnique("id", summaryId);
        List list = safeguardSpplanDao.getSafeguardSpplanByPlanId(planId);
        String sublineIds = safeguardSegmentDao.getSublineIds(planId);
        List safeguardSps = null;
        List specialPlans = new ArrayList();
        safeguardSps = safeguardSpplanDao.findByProperty("planId", planId);
        if (safeguardSps != null) {
            for (Iterator iterator = safeguardSps.iterator(); iterator.hasNext();) {
                SafeguardSpplan safeguardSp = (SafeguardSpplan) iterator.next();
                String spId = safeguardSp.getSpplanId();
                /*
                 * SpecialPlan specialPlan = specialPlanDao.findByUnique("id",
                 * spId); String flag = safeguardPlanDao.whetherStat(spId);
                 * specialPlan.setPlanType(flag);
                 */
                List<Map> specialPlan = safeguardPlanDao.getSafeguardPlan(spId);
                specialPlans.add(specialPlan);
            }
        }
        Map map = new HashMap();
        map.put("safeguardTask", safeguardTask);
        map.put("safeguardPlan", safeguardPlan);
        map.put("safeguardSummary", safeguardSummary);
        map.put("safeguardSps", safeguardSps);
        map.put("specialPlans", specialPlans);
        map.put("sublineIds", sublineIds);
        map.put("list", list);
        return map;
    }

    /**
     * 保障评分
     * 
     * @param score：分数
     * @param remark：评论
     * @param userInfo
     * @param safeguardSummaryBean：保障总结Bean
     * @throws ServiceException
     */
    public void examSafeguard(SafeguardExamBean examBean, UserInfo userInfo,
            AppraiseDailyBean appraiseDailyBean, List<AppraiseDailyBean> speicalBeans)
            throws ServiceException {
        String planId = examBean.getPlanId();
        String taskId = examBean.getTaskId();
        String contractorId = examBean.getConId();

        /*
         * Evaluate evaluate = new Evaluate(); evaluate.setEntityScore(score);
         * evaluate.setEntityRemark(remark);
         * evaluate.setEvaluator(userInfo.getUserID());
         * evaluate.setEntityType(SafeguardConstant.LP_SAFEGUARD_EVALUATE);
         * evaluate.setEntityId(planId); evaluate.setEvaluaterDate(new Date());
         * evaluateDao.savaEvaluate(evaluate);
         */

        // 保存日常考核信息
        appraiseDailyBO.saveAppraiseDailyByBean(appraiseDailyBean);
        // 保存专项日常考核信息
        appraiseDailySpecialBO.saveAppraiseDailyByBean(speicalBeans);

        safeguardConDao.setStateByTaskIdAndConId(taskId, contractorId,
                SafeguardCon.SAFEGUARDRFINISH);

        SafeguardPlan safeguardPlan = safeguardPlanDao.findByUnique("id", planId);
        String name = safeguardTaskDao.getTaskNameByTaskId(taskId);
        String creator = safeguardPlan.getMaker();
        String phone = getPhoneByUserId(creator);
        String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, contractorId);
        // JBPM
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
        if (task != null && SafeguardWorkflowBO.EVALUATE_TASK.equals(task.getName())) {
            System.out.println("保障待考核：" + task.getName());
            workflowBo.completeTask(task.getId(), "end");
            System.out.println("保障已经考核！");

            // 保存流程历史
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            processHistoryBean.setProcessAction("保障评分");// 添加流程处理动作说明
            processHistoryBean.setTaskOutCome("end");// 添加工作流流向信息
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
            processHistoryBean.setNextOperateUserId("");// 添加下一步处理人的编号
            processHistoryBean.setObjectId(eid);// 添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
            try {
                processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException();
            }
        }
        String content = "【保障】贵单位的名称为\"" + name + "\"的保障任务已经考核！";
        sendMessage(content, phone);
        saveMessage(content, phone, planId, SafeguardConstant.LP_SAFEGUARD_PLAN,
                ModuleCatalog.SAFEGUARD);

    }

    /**
     * 通过用户ID查询用户的手机号码
     * 
     * @param userId：用户ID
     * @return：返回用户手机号码
     */
    public String getPhoneByUserId(String userId) {
        String hql = "from UserInfo userInfo where userInfo.userID=?";
        List list = safeguardSummaryDao.getHibernateTemplate().find(hql, userId);
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
        if (mobiles != null && !"".equals(mobiles)) {
            List<String> mobileList = StringUtils.string2List(mobiles, ",");
            try {
                super.sendMessage(content, mobileList);
            } catch (Exception e) {
                logger.error("发送短信失败", e);
            }
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
    public void saveMessage(String content, String mobiles, String entityId, String entityType,
            String entityModule) {
        if (mobiles != null && !"".equals(mobiles)) {
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