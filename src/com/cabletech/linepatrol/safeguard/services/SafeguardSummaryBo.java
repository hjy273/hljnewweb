package com.cabletech.linepatrol.safeguard.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.safeguard.beans.SafeguardSummaryBean;
import com.cabletech.linepatrol.safeguard.dao.SafeguardConDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardPlanDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSegmentDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSpplanDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSummaryDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardTaskDao;
import com.cabletech.linepatrol.safeguard.dao.SpecialEndPlanDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardCon;
import com.cabletech.linepatrol.safeguard.module.SafeguardConstant;
import com.cabletech.linepatrol.safeguard.module.SafeguardPlan;
import com.cabletech.linepatrol.safeguard.module.SafeguardSpplan;
import com.cabletech.linepatrol.safeguard.module.SafeguardSummary;
import com.cabletech.linepatrol.safeguard.module.SafeguardTask;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardWorkflowBO;
import com.cabletech.linepatrol.specialplan.dao.SpecialPlanDao;

@Service
@Transactional
public class SafeguardSummaryBo extends EntityManager<SafeguardSummary, String> {

    private static Logger logger = Logger.getLogger(SafeguardSummaryBo.class.getName());

    @Override
    protected HibernateDao<SafeguardSummary, String> getEntityDao() {
        return safeguardSummaryDao;
    }

    @Autowired
    private SafeguardWorkflowBO workflowBo;

    @Resource(name = "safeguardPlanDao")
    private SafeguardPlanDao safeguardPlanDao;

    @Resource(name = "specialPlanDao")
    private SpecialPlanDao specialPlanDao;

    @Resource(name = "safeguardTaskDao")
    private SafeguardTaskDao safeguardTaskDao;

    @Resource(name = "safeguardConDao")
    private SafeguardConDao safeguardConDao;

    @Resource(name = "safeguardSegmentDao")
    private SafeguardSegmentDao safeguardSegmentDao;

    @Resource(name = "safeguardSummaryDao")
    private SafeguardSummaryDao safeguardSummaryDao;

    @Resource(name = "replyApproverDAO")
    private ReplyApproverDAO replyApproverDAO;

    @Resource(name = "uploadFileService")
    private UploadFileService uploadFile;

    @Resource(name = "replyApproverDAO")
    private ReplyApproverDAO approverDAO;

    @Resource(name = "approveDAO")
    private ApproveDAO approveDao;

    @Resource(name = "smHistoryDAO")
    private SmHistoryDAO historyDAO;

    @Resource(name = "processHistoryBO")
    private ProcessHistoryBO processHistoryBO;

    @Resource(name = "safeguardSpplanDao")
    private SafeguardSpplanDao safeguardSpplanDao;

    /**
     * 通过计划ID查询保障任务和保障计划
     * 
     * @param planId：保障计划ID
     * @return
     */
    public Map addSafeguardSummaryForm(String planId) {
        String taskId = safeguardPlanDao.getTaskIdByPlanId(planId);
        SafeguardTask safeguardTask = safeguardTaskDao.findByUnique("id", taskId);
        SafeguardPlan safeguardPlan = safeguardPlanDao.findByUnique("id", planId);
        String sublineIds = safeguardSegmentDao.getSublineIds(planId);
        List list = safeguardSpplanDao.getSafeguardSpplanByPlanId(planId);
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
        SafeguardSummary safeguardSummary = safeguardSummaryDao.findByUnique("planId", planId);
        Map map = new HashMap();
        map.put("safeguardTask", safeguardTask);
        map.put("safeguardPlan", safeguardPlan);
        map.put("safeguardSps", safeguardSps);
        map.put("specialPlans", specialPlans);
        map.put("sublineIds", sublineIds);
        map.put("safeguardSummary", safeguardSummary);
        map.put("list", list);
        return map;
    }

    /**
     * 添加保障总结
     * 
     * @param safeguardSummaryBean：保障总结实体Bean
     * @param userInfo
     */
    public void addSafeguardSummary(SafeguardSummaryBean safeguardSummaryBean, UserInfo userInfo,
            List<FileItem> files) throws ServiceException {
        String taskId = safeguardSummaryBean.getTaskId();
        String deptId = userInfo.getDeptID();
        String approveId = safeguardSummaryBean.getApproveId();
        String reader = safeguardSummaryBean.getReader();
        String mobiles = safeguardSummaryBean.getMobiles();
        String readerPhones[] = safeguardSummaryBean.getReaderPhones();
        List<SafeguardSummary> ssSize = safeguardSummaryDao.findByProperty("planId",
                safeguardSummaryBean.getPlanId());
        if (ssSize.size() <= 0) {// 增加总结重复添加验证，防止多次写入数据。
            SafeguardSummary safeguardSummarySave = saveSafeguardSummary(safeguardSummaryBean,
                    userInfo.getUserID());
            String id = safeguardSummarySave.getId();
            String planId = safeguardSummarySave.getPlanId();
            safeguardConDao.setStateByTaskIdAndConId(taskId, deptId, SafeguardCon.APPROVESUMMARY);

            // 上传文件
            saveFiles(files, ModuleCatalog.SAFEGUARD, userInfo.getRegionName(), id,
                    SafeguardConstant.LP_SAFEGUARD_SUMMARY, userInfo.getUserID());

            // 保存审核人信息
            replyApproverDAO.saveApproverOrReader(approveId, id, SafeguardConstant.APPROVE_MAN,
                    SafeguardConstant.LP_SAFEGUARD_SUMMARY);

            replyApproverDAO.saveApproverOrReader(reader, id, SafeguardConstant.APPROVE_READ,
                    SafeguardConstant.LP_SAFEGUARD_SUMMARY);
            String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, deptId);

            Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
            if (task != null && SafeguardWorkflowBO.GUARD_PLAN_EXECUTE_STATE.equals(task.getName())) {
                workflowBo.setVariables(task, "assignee", userInfo.getUserID());
                workflowBo.completeTask(task.getId(), "summarize");
            }
            task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
            if (task != null
                    && SafeguardWorkflowBO.CREATE_GUARD_SUMMARY_TASK.equals(task.getName())) {
                System.out.println("保障方案添加中：" + task.getName());
                workflowBo.setVariables(task, "assignee", approveId + "," + reader);
                workflowBo.completeTask(task.getId(), "approve");
                logger.info("保障方案已经提交！");

                // 保存流程历史
                ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
                String nextOperateUserId = "";
                processHistoryBean.setProcessAction("制定保障总结");// 添加流程处理动作说明
                processHistoryBean.setTaskOutCome("approve");// 添加工作流流向信息
                processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
                processHistoryBean.setNextOperateUserId(approveId + "," + reader);// 添加下一步处理人的编号
                processHistoryBean.setObjectId(eid);// 添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
                try {
                    processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException();
                }
            }
            // 发送短信
            String content = "【保障】您有一个名称为\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                    + "\"保障总结单等待您的审核。";
            sendMessage(content, mobiles);

            // 保存短信记录
            saveMessage(content, mobiles, id, SafeguardConstant.LP_SAFEGUARD_SUMMARY,
                    ModuleCatalog.SAFEGUARD);

            if (readerPhones != null && readerPhones.length > 0) {
                for (int i = 0; i < readerPhones.length; i++) {
                    content = "【保障】您有一个名称为\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                            + "\"保障总结单等待您的查阅。";
                    sendMessage(content, readerPhones[i]);

                    // 保存短信记录
                    saveMessage(content, readerPhones[i], id,
                            SafeguardConstant.LP_SAFEGUARD_SUMMARY, ModuleCatalog.SAFEGUARD);
                }
            }
        }
    }

    /**
     * 保障总结临时保存
     * 
     * @param safeguardSummaryBean：保障总结Bean
     * @param userInfo
     * @param files：附件信息
     * @throws ServiceException
     */
    public void tempSaveSafeguardSummary(SafeguardSummaryBean safeguardSummaryBean,
            UserInfo userInfo, List<FileItem> files) throws ServiceException {
        String approveId = safeguardSummaryBean.getApproveId();
        String reader = safeguardSummaryBean.getReader();

        SafeguardSummary safeguardSummarySave = saveSafeguardSummary(safeguardSummaryBean, userInfo
                .getUserID());
        String id = safeguardSummarySave.getId();

        // 上传文件
        saveFiles(files, ModuleCatalog.SAFEGUARD, userInfo.getRegionName(), id,
                SafeguardConstant.LP_SAFEGUARD_SUMMARY, userInfo.getUserID());

        replyApproverDAO.deleteList(id, SafeguardConstant.LP_SAFEGUARD_SUMMARY);
        // 保存审核人信息
        replyApproverDAO.saveApproverOrReader(approveId, id, SafeguardConstant.APPROVE_MAN,
                SafeguardConstant.LP_SAFEGUARD_SUMMARY);

        replyApproverDAO.saveApproverOrReader(reader, id, SafeguardConstant.APPROVE_READ,
                SafeguardConstant.LP_SAFEGUARD_SUMMARY);
    }

    /**
     * 保存保障总结实体
     * 
     * @param safeguardSummaryBean：保障总结实体Bean
     * @param creator：保障总结创建人
     * @return：保障总结实体
     */
    private SafeguardSummary saveSafeguardSummary(SafeguardSummaryBean safeguardSummaryBean,
            String creator) {
        SafeguardSummary safeguardSummary = new SafeguardSummary();
        try {
            BeanUtil.objectCopy(safeguardSummaryBean, safeguardSummary);
        } catch (Exception e) {
            logger.error("SafeguardSummaryBean转换为SafeguardSummary出错，出错信息：" + e.getMessage());
        }
        safeguardSummary.setSumManId(creator);
        safeguardSummary.setSumDate(new Date());
        return safeguardSummaryDao.saveSafeguardSummary(safeguardSummary);
    }

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
        String conId = safeguardConDao.getConIdBySummaryId(summaryId);
        safeguardSps = safeguardSpplanDao.findByProperty("planId", planId);
        if (safeguardSps != null) {
            for (Iterator iterator = safeguardSps.iterator(); iterator.hasNext();) {
                SafeguardSpplan safeguardSp = (SafeguardSpplan) iterator.next();
                String spId = safeguardSp.getSpplanId();
                // SpecialPlan specialPlan = specialPlanDao.findByUnique("id",
                // spId);
                List<Map> specialPlan = safeguardPlanDao.getSafeguardPlan(spId);
                specialPlans.add(specialPlan);
            }
        }
        Map map = new HashMap();
        map.put("safeguardTask", safeguardTask);
        map.put("safeguardPlan", safeguardPlan);
        map.put("safeguardSps", safeguardSps);
        map.put("specialPlans", specialPlans);
        map.put("safeguardSummary", safeguardSummary);
        map.put("sublineIds", sublineIds);
        map.put("list", list);
        map.put("conId", conId);
        return map;
    }

    /**
     * 审核保障总结
     * 
     * @param safeguardSummaryBean：保障总结实体Bean
     * @param userInfo
     */
    public void approveSafeguardSummary(SafeguardSummaryBean safeguardSummaryBean, UserInfo userInfo)
            throws ServiceException {
        String summaryId = safeguardSummaryBean.getId();
        String approveResult = safeguardSummaryBean.getApproveResult();
        String approveRemark = safeguardSummaryBean.getApproveRemark();
        String operator = safeguardSummaryBean.getOperator();
        String taskId = safeguardSummaryBean.getTaskId();
        String conId = safeguardSummaryBean.getConId();
        String approves = safeguardSummaryBean.getApprovers();
        String creator = safeguardSummaryBean.getSumManId();
        String phone = "";
        String content = "";
        // 去除已阅和转审给抄送人和自己为抄送人的情况
        String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + approves
                + "','" + userInfo.getUserID() + "')";
        String approverIdsReader = safeguardPlanDao.getApproverIds(summaryId,
                SafeguardConstant.APPROVE_READ, SafeguardConstant.LP_SAFEGUARD_SUMMARY, condition);
        // 保存审核信息
        saveApproveInfo(summaryId, SafeguardConstant.LP_SAFEGUARD_SUMMARY, userInfo.getUserID(),
                approveResult, approveRemark);

        if ("approve".equals(operator)) {
            phone = getPhoneByUserId(creator);
            if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {
                safeguardConDao.setStateByTaskIdAndConId(taskId, conId,
                        SafeguardCon.SUMMARYUNAPPROVE);

                // 创建短信内容
                content = "【保障】您的\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                        + "\"保障总结未通过审核。";
            } else {
                safeguardConDao.setStateByTaskIdAndConId(taskId, conId, SafeguardCon.EVALUATE);

                // 创建短信内容
                content = "【保障】您的\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                        + "\"保障总结已经通过审核。";
            }

        } else {
            // 保存审核人信息
            replyApproverDAO.saveApproverOrReader(approves, summaryId,
                    SafeguardConstant.APPROVE_TRANSFER_MAN, SafeguardConstant.LP_SAFEGUARD_SUMMARY);
            phone = safeguardSummaryBean.getMobiles();
            // 发送短信
            content = "【保障】您有一个名称为\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                    + "\"保障总结单等待您的审核。";

        }
        String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, conId);
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
        if (task != null && SafeguardWorkflowBO.APPROVE_GUARD_SUMMARY_TASK.equals(task.getName())) {
            System.out.println("保障方案待审核：" + task.getName());
            if (SafeguardConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
                workflowBo.setVariables(task, "assignee", userInfo.getUserID());
                workflowBo.setVariables(task, "transition", "evaluate");
                workflowBo.completeTask(task.getId(), "passed");
                System.out.println("保障方案审核通过！");

                // 保存流程历史
                ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
                String nextOperateUserId = "";
                processHistoryBean.setProcessAction("保障总结审批通过");// 添加流程处理动作说明
                processHistoryBean.setTaskOutCome("evaluate");// 添加工作流流向信息
                processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
                processHistoryBean.setNextOperateUserId(userInfo.getUserID());// 添加下一步处理人的编号
                processHistoryBean.setObjectId(eid);// 添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
                try {
                    processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException();
                }
            }
            if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {
                workflowBo.setVariables(task, "assignee", creator);
                workflowBo.completeTask(task.getId(), "not_passed");
                System.out.println("保障方案审核不通过！");

                // 保存流程历史
                ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
                String nextOperateUserId = "";
                processHistoryBean.setProcessAction("保障总结审批不通过");// 添加流程处理动作说明
                processHistoryBean.setTaskOutCome("not_passed");// 添加工作流流向信息
                processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
                processHistoryBean.setNextOperateUserId(creator);// 添加下一步处理人的编号
                processHistoryBean.setObjectId(eid);// 添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
                try {
                    processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException();
                }
            }
            if (SafeguardConstant.APPROVE_RESULT_TRANSFER.equals(approveResult)) {
                workflowBo.setVariables(task, "assignee", approves + "," + approverIdsReader);
                workflowBo.setVariables(task, "transition", "transfer");
                workflowBo.completeTask(task.getId(), "passed");
                System.out.println("保障方案已经转审！");

                // 保存流程历史
                ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
                String nextOperateUserId = "";
                processHistoryBean.setProcessAction("保障总结转审");// 添加流程处理动作说明
                processHistoryBean.setTaskOutCome("transfer");// 添加工作流流向信息
                processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
                processHistoryBean.setNextOperateUserId(approves);// 添加下一步处理人的编号
                processHistoryBean.setObjectId(eid);// 添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
                try {
                    processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException();
                }
            }
        }
        sendMessage(content, phone);
        // 保存短信记录
        saveMessage(content, phone, summaryId, SafeguardConstant.LP_SAFEGUARD_SUMMARY,
                ModuleCatalog.SAFEGUARD);
    }

    /**
     * 编辑保障总结
     * 
     * @param safeguardSummaryBean：保障总结实体Bean
     * @param userInfo
     */
    public void editSafeguardSummary(SafeguardSummaryBean safeguardSummaryBean, UserInfo userInfo,
            List<FileItem> files, String flag) throws ServiceException {
        String taskId = safeguardSummaryBean.getTaskId();
        String conId = safeguardSummaryBean.getConId();
        String summaryId = safeguardSummaryBean.getId();
        String approveId = safeguardSummaryBean.getApproveId();
        String reader = safeguardSummaryBean.getReader();
        String mobiles = safeguardSummaryBean.getMobiles();
        String[] readerPhones = safeguardSummaryBean.getReaderPhones();

        // 保存保障总结实体
        saveSafeguardSummary(safeguardSummaryBean, userInfo.getUserID());

        // 修改保障与代维关系状态
        safeguardConDao.setStateByTaskIdAndConId(taskId, conId, SafeguardCon.APPROVESUMMARY);

        // 保存附件
        saveFiles(files, ModuleCatalog.SAFEGUARD, userInfo.getRegionName(), summaryId,
                SafeguardConstant.LP_SAFEGUARD_SUMMARY, userInfo.getUserID());
        replyApproverDAO.deleteList(summaryId, SafeguardConstant.LP_SAFEGUARD_SUMMARY);
        // 保存审核人信息
        replyApproverDAO.saveApproverOrReader(approveId, summaryId, SafeguardConstant.APPROVE_MAN,
                SafeguardConstant.LP_SAFEGUARD_SUMMARY);

        replyApproverDAO.saveApproverOrReader(reader, summaryId, SafeguardConstant.APPROVE_READ,
                SafeguardConstant.LP_SAFEGUARD_SUMMARY);

        String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, userInfo.getDeptID());
        Task task = null;

        if (flag != null && !"".equals(flag)) {
            task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
            if (task != null && SafeguardWorkflowBO.GUARD_PLAN_EXECUTE_STATE.equals(task.getName())) {
                workflowBo.setVariables(task, "assignee", userInfo.getUserID());
                workflowBo.completeTask(task.getId(), "summarize");
            }
            task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
        }

        task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
        if (task != null && SafeguardWorkflowBO.CREATE_GUARD_SUMMARY_TASK.equals(task.getName())) {
            System.out.println("保障方案添加中：" + task.getName());
            workflowBo.setVariables(task, "assignee", approveId + "," + reader);
            workflowBo.completeTask(task.getId(), "approve");
            System.out.println("保障方案已经提交！");

            // 保存流程历史
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            processHistoryBean.setProcessAction("保障总结转审");// 添加流程处理动作说明
            processHistoryBean.setTaskOutCome("approve");// 添加工作流流向信息
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
            processHistoryBean.setNextOperateUserId(approveId + "," + reader);// 添加下一步处理人的编号
            processHistoryBean.setObjectId(eid);// 添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
            try {
                processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException();
            }
        }

        // 发送短信
        String content = "【保障】您有一个名称为\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                + "\"保障总结单等待您的审核。";
        sendMessage(content, mobiles);

        // 保存短信记录
        saveMessage(content, mobiles, summaryId, SafeguardConstant.LP_SAFEGUARD_SUMMARY,
                ModuleCatalog.SAFEGUARD);

        if (readerPhones != null && readerPhones.length > 0) {
            for (int i = 0; i < readerPhones.length; i++) {
                content = "【保障】您有一个名称为\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                        + "\"保障总结单等待您的查阅。";
                sendMessage(content, readerPhones[i]);

                // 保存短信记录
                saveMessage(content, readerPhones[i], summaryId,
                        SafeguardConstant.LP_SAFEGUARD_SUMMARY, ModuleCatalog.SAFEGUARD);
            }
        }

    }

    /**
     * 将字符串转换为List
     * 
     * @param str：需要转换的字符串
     * @return：list
     */
    // public List<String> stringToList(String str) {
    // if (str == null || str.equals("")) {
    // return null;
    // }
    // String[] strArray = str.split(",");
    // List<String> list = new ArrayList<String>();
    // for (int i = 0; strArray != null && i < strArray.length; i++) {
    // list.add(strArray[i]);
    // }
    // return list;
    // }
    /**
     * 保存文件
     * 
     * @param files：上传的附件
     * @param module：模块常量
     * @param regionName：区域名称
     * @param entityId：附件关联的实体ID
     * @param entityType：附件关联的表名
     * @param uploader：附件上传人
     */
    public void saveFiles(List<FileItem> files, String module, String regionName, String entityId,
            String entityType, String uploader) {
        uploadFile.saveFiles(files, module, regionName, entityId, entityType, uploader);
    }

    /**
     * 保存审核信息
     * 
     * @param entityId：实体ID
     * @param entityType：实体表名
     * @param approverId：审核人ID
     * @param approveResult：审核结果
     * @param approveRemark：审核意见
     */
    public void saveApproveInfo(String entityId, String entityType, String approverId,
            String approveResult, String approveRemark) {
        ApproveInfo approveInfo = new ApproveInfo();
        approveInfo.setObjectId(entityId);
        approveInfo.setObjectType(entityType);
        approveInfo.setApproverId(approverId);
        approveInfo.setApproveTime(new Date());
        approveInfo.setApproveResult(approveResult);
        approveInfo.setApproveRemark(approveRemark);
        approveDao.save(approveInfo);
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
            // super.sendMessage(content, mobileList);
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

    public void readReply(UserInfo userInfo, String approverId, String summaryId)
            throws ServiceException {
        approverDAO.updateReader(approverId, summaryId, SafeguardConstant.LP_SAFEGUARD_SUMMARY);

        SafeguardSummary safeguardSummary = safeguardSummaryDao.findByUnique("id", summaryId);
        String creator = safeguardSummary.getSumManId();

        String eid = safeguardSummaryDao.getTaskConIdBySummaryId(summaryId);
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
        // 保存流程历史
        ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
        processHistoryBean.setProcessAction("保障总结查阅");// 添加流程处理动作说明
        processHistoryBean.setTaskOutCome("");// 添加工作流流向信息
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
}
