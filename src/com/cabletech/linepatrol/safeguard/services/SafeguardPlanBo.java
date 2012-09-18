package com.cabletech.linepatrol.safeguard.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.cabletech.linepatrol.safeguard.beans.SafeguardPlanBean;
import com.cabletech.linepatrol.safeguard.dao.SafeguardConDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardPlanDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSegmentDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSpplanDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardTaskDao;
import com.cabletech.linepatrol.safeguard.dao.SpecialEndPlanDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardCon;
import com.cabletech.linepatrol.safeguard.module.SafeguardConstant;
import com.cabletech.linepatrol.safeguard.module.SafeguardPlan;
import com.cabletech.linepatrol.safeguard.module.SafeguardSegment;
import com.cabletech.linepatrol.safeguard.module.SafeguardSpplan;
import com.cabletech.linepatrol.safeguard.module.SafeguardTask;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardReplanSubWorkflowBO;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardSubWorkflowBO;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardWorkflowBO;
import com.cabletech.linepatrol.specialplan.beans.SpecialPlanBean;
import com.cabletech.linepatrol.specialplan.dao.SpecialPlanDao;
import com.cabletech.linepatrol.specialplan.module.SpecialPlan;

@Service
@Transactional
public class SafeguardPlanBo extends EntityManager<SafeguardPlan, String> {

    private static Logger logger = Logger.getLogger(SafeguardPlanBo.class.getName());

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    protected HibernateDao<SafeguardPlan, String> getEntityDao() {
        return safeguardPlanDao;
    }

    @Autowired
    private SafeguardWorkflowBO workflowBo;

    @Resource(name = "safeguardPlanDao")
    private SafeguardPlanDao safeguardPlanDao;

    @Resource(name = "specialPlanDao")
    private SpecialPlanDao specialPlanDao;

    @Resource(name = "specialEndPlanDao")
    private SpecialEndPlanDao specialEndPlanDao;

    @Resource(name = "safeguardSpplanDao")
    private SafeguardSpplanDao safeguardSpplanDao;

    @Resource(name = "safeguardTaskDao")
    private SafeguardTaskDao safeguardTaskDao;

    @Resource(name = "safeguardConDao")
    private SafeguardConDao safeguardConDao;

    @Resource(name = "safeguardSegmentDao")
    private SafeguardSegmentDao safeguardSegmentDao;

    @Resource(name = "replyApproverDAO")
    private ReplyApproverDAO replyApproverDAO;

    @Resource(name = "uploadFileService")
    private UploadFileService uploadFile;

    @Resource(name = "approveDAO")
    private ApproveDAO approveDao;

    @Resource(name = "smHistoryDAO")
    private SmHistoryDAO historyDAO;

    @Resource(name = "processHistoryBO")
    private ProcessHistoryBO processHistoryBO;

    @Resource(name = "replyApproverDAO")
    private ReplyApproverDAO approverDAO;

    /**
     * 添加保障计划前加载数据
     * 
     * @param taskId：保障任务ID
     * @param userInfo
     * @return
     */
    public Map addSafeguardPlanForm(String taskId, UserInfo userInfo) {
        String existFlag = null;
        String conId = userInfo.getDeptID();
        Map map = new HashMap();
        String[] approveInfo = null;
        // 加载保障方案信息
        SafeguardTask safeguardTask = safeguardTaskDao.findByUnique("id", taskId);
        // 若保障方案已存在，加载保障方案信息
        SafeguardPlan safeguardPlan = safeguardPlanDao.getPlanByTaskIdAndConId(taskId, conId);
        List safeguardSps = null;
        String sublineIds = null;
        List specialPlans = new ArrayList();
        String approveMan = "";
        approveInfo = safeguardPlanDao.getUserIdAndUserNameByUserId(safeguardTask.getSender());
        if (safeguardPlan == null) {
            SafeguardPlan safeguardPlanOrg = new SafeguardPlan();
            safeguardPlanOrg.setSafeguardId(taskId);
            safeguardPlanOrg.setContractorId(conId);
            safeguardPlan = safeguardPlanDao.saveSafeguardPlan(safeguardPlanOrg);
            existFlag = "new";
        } else {
            String planId = safeguardPlan.getId();
            safeguardSps = safeguardSpplanDao.findByProperty("planId", planId);
            if (safeguardSps != null) {
                for (Iterator iterator = safeguardSps.iterator(); iterator.hasNext();) {
                    SafeguardSpplan safeguardSp = (SafeguardSpplan) iterator.next();
                    String spId = safeguardSp.getSpplanId();
                    List<Map> specialPlan = safeguardPlanDao.getSafeguardPlan(spId);
                    // specialPlanDao.findByUnique("id", spId);
                    /*
                     * String flag = safeguardPlanDao.whetherStat(spId);
                     * specialPlan.setPlanType(flag);
                     */
                    specialPlans.add(specialPlan);
                }
            }
            sublineIds = safeguardSegmentDao.getSublineIds(planId);
            existFlag = "old";
            approveMan = safeguardPlanDao.whetherPlaned(planId);
        }
        map.put("safeguardTask", safeguardTask);
        map.put("safeguardPlan", safeguardPlan);
        map.put("safeguardSps", safeguardSps);
        map.put("specialPlans", specialPlans);
        map.put("approveInfo", approveInfo);
        map.put("existFlag", existFlag);
        map.put("sublineIds", sublineIds);
        map.put("approveMan", approveMan);
        return map;
    }

    /**
     * 由特巡计划ID查找特巡计划列表
     * 
     * @param spid：特巡计划ID
     * @return
     */
    public List<SpecialPlan> findSpPlanBySafeguardId(String spid) {
        List list = safeguardSpplanDao.findByProperty("planId", spid);
        List list2 = new ArrayList();
        if (list != null) {
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                SafeguardSpplan name = (SafeguardSpplan) iterator.next();
                String sppid = name.getSpplanId();
                SpecialPlan sp = specialPlanDao.findByUnique("id", sppid);
                String patrolGroupId = safeguardSpplanDao.getPatrolGroupIdBySpId(sppid);
                sp.setPatrolGroupId(patrolGroupId);
                list2.add(sp);
            }
        }
        return list2;
    }

    /**
     * 通过任务ID查询计划列表
     * 
     * @param taskId：保障任务ID
     * @return：保障计划列表
     */
    public List getPlanByTaskIdConId(String taskId, String conId) {
        return safeguardPlanDao.getPlanByTaskIdConId(taskId, conId);
    }

    /**
     * 通过任务ID和代维单位ID查询保障计划ID和保障名称
     * 
     * @param taskId：保障任务ID
     * @param conId：代维单位ID
     * @return
     */
    @Transactional(readOnly = true)
    public Map getPlanByTaskIdAndConId(String taskId, String conId) {
        SafeguardPlan safeguardPlan = safeguardPlanDao.getPlanByTaskIdAndConId(taskId, conId);
        String planId = null;
        if (safeguardPlan != null) {
            planId = safeguardPlan.getId();
        }
        String safeguardName = safeguardTaskDao.getTaskNameByTaskId(taskId);
        Map map = new HashMap();
        map.put("planId", planId);
        map.put("safeguardName", safeguardName);
        return map;
    }

    /**
     * 添加保障方案信息
     * 
     * @param safeguardPlanBean：保障方案ID
     * @param userInfo
     * @param trunkList：中继段列表
     * @param files：附件信息
     * @param spplanIds：特巡计划IDS
     * @throws ServiceException
     */
    public void addSafeguardPlan(SafeguardPlanBean safeguardPlanBean, UserInfo userInfo,
            List<String> trunkList, List<FileItem> files, String[] spplanIds)
            throws ServiceException {
        String deptId = userInfo.getDeptID();
        String userId = userInfo.getUserID();
        String approver = safeguardPlanBean.getApproveId();
        String reader = safeguardPlanBean.getReader();
        String[] readerPhones = safeguardPlanBean.getReaderPhones();
        // 保存保障计划
        SafeguardPlan safeguardPlanSave = saveSafeguardPlan(safeguardPlanBean, deptId, userId);
        // 保存保障计划与特巡计划关系
        String planId = safeguardPlanSave.getId();
        if (spplanIds != null && spplanIds.length > 0) {
            for (int i = 0; i < spplanIds.length; i++) {
                SafeguardSpplan safeguardSpplan = new SafeguardSpplan();
                safeguardSpplan.setPlanId(planId);
                safeguardSpplan.setSpplanId(spplanIds[i]);
                safeguardSpplanDao.save(safeguardSpplan);
            }
        }

        String taskId = safeguardPlanSave.getSafeguardId();
        String conId = userInfo.getDeptID();

        // 上传文件
        saveFiles(files, ModuleCatalog.SAFEGUARD, userInfo.getRegionName(), safeguardPlanSave
                .getId(), SafeguardConstant.LP_SAFEGUARD_PLAN, userInfo.getUserID());

        // 保存保障与中继段关系
        saveSafeguardPlanCon(trunkList, planId);

        // 更新保障与代维关系表
        safeguardConDao.setStateByTaskIdAndConId(taskId, conId, SafeguardCon.APPROVEPLAN);
        // 保存审核人
        replyApproverDAO.saveApproverOrReader(approver, planId, SafeguardConstant.APPROVE_MAN,
                SafeguardConstant.LP_SAFEGUARD_PLAN);
        // 保存抄送人
        replyApproverDAO.saveApproverOrReader(reader, planId, SafeguardConstant.APPROVE_READ,
                SafeguardConstant.LP_SAFEGUARD_PLAN);

        String name = safeguardTaskDao.getTaskNameByTaskId(taskId);
        String mobiles = safeguardTaskDao.getMobiles(approver);
        // 代维认领线维分配任务并制定计划
        String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, conId);
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
        if (task != null && SafeguardWorkflowBO.CREATE_GUARD_PROJ_TASK.equals(task.getName())) {
            System.out.println("保障计划添加中：" + task.getName());
            workflowBo.setVariables(task, "assignee", approver + "," + reader);
            workflowBo.completeTask(task.getId(), "approve");
            System.out.println("保障计划已经提交！");

            // 保存流程历史
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            processHistoryBean.setProcessAction("制定保障方案");// 添加流程处理动作说明
            processHistoryBean.setTaskOutCome("approve");// 添加工作流流向信息
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
            processHistoryBean.setNextOperateUserId(approver + "," + reader);// 添加下一步处理人的编号
            processHistoryBean.setObjectId(eid);// 添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
            try {
                processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException();
            }
        }

        // 发送短信
        String content = "【保障】您有一个名称为\"" + name + "\"保障方案单等待您的审核。";
        sendMessage(content, mobiles);
        // 保存短信记录
        saveMessage(content, mobiles, planId, SafeguardConstant.LP_SAFEGUARD_PLAN,
                ModuleCatalog.SAFEGUARD);

        if (readerPhones != null && readerPhones.length > 0) {
            for (int i = 0; i < readerPhones.length; i++) {
                content = "【保障】您有一个名称为\"" + name + "\"保障方案单等待您的查阅。";
                if (!"".equals(readerPhones[i])) {
                    sendMessage(content, readerPhones[i]);

                    // 保存短信记录
                    saveMessage(content, readerPhones[i], planId,
                            SafeguardConstant.LP_SAFEGUARD_PLAN, ModuleCatalog.SAFEGUARD);
                }
            }
        }

    }

    /**
     * 保障方案暂存
     * 
     * @param safeguardPlanBean：保障方案Bean
     * @param userInfo
     * @param trunkList：中继段列表
     * @param files：附件信息
     * @param spplanIds：特巡计划IDS
     * @throws ServiceException
     */
    public void tempSaveSafeguardPlan(SafeguardPlanBean safeguardPlanBean, UserInfo userInfo,
            List<String> trunkList, List<FileItem> files, String[] spplanIds)
            throws ServiceException {
        String deptId = userInfo.getDeptID();
        String userId = userInfo.getUserID();
        String approver = safeguardPlanBean.getApproveId();
        String reader = safeguardPlanBean.getReader();
        // 保存保障计划
        SafeguardPlan safeguardPlanSave = saveSafeguardPlan(safeguardPlanBean, deptId, userId);
        // 保存保障计划与特巡计划关系
        String planId = safeguardPlanSave.getId();
        if (spplanIds != null && spplanIds.length > 0) {
            for (int i = 0; i < spplanIds.length; i++) {
                SafeguardSpplan safeguardSpplan = new SafeguardSpplan();
                safeguardSpplan.setPlanId(planId);
                safeguardSpplan.setSpplanId(spplanIds[i]);
                safeguardSpplanDao.save(safeguardSpplan);
            }
        }

        // 上传文件
        saveFiles(files, ModuleCatalog.SAFEGUARD, userInfo.getRegionName(), safeguardPlanSave
                .getId(), SafeguardConstant.LP_SAFEGUARD_PLAN, userInfo.getUserID());

        // 保存保障与中继段关系
        saveSafeguardPlanCon(trunkList, planId);

        replyApproverDAO.deleteList(planId, SafeguardConstant.LP_SAFEGUARD_PLAN);
        // 保存审核人
        replyApproverDAO.saveApproverOrReader(approver, planId, SafeguardConstant.APPROVE_MAN,
                SafeguardConstant.LP_SAFEGUARD_PLAN);
        // 保存抄送人
        replyApproverDAO.saveApproverOrReader(reader, planId, SafeguardConstant.APPROVE_READ,
                SafeguardConstant.LP_SAFEGUARD_PLAN);
    }

    /**
     * 保障计划审核
     * 
     * @param safeguardPlanBean：保障计划实体Bean
     * @param userInfo
     */
    public void approveSafeguardPlan(SafeguardPlanBean safeguardPlanBean, UserInfo userInfo) {
        String taskId = safeguardPlanBean.getSafeguardId();
        String conId = safeguardPlanBean.getContractorId();
        String planId = safeguardPlanBean.getId();
        String approveResult = safeguardPlanBean.getApproveResult();
        String approveRemark = safeguardPlanBean.getApproveRemark();
        String operator = safeguardPlanBean.getOperator();
        String approvers = safeguardPlanBean.getApprovers();
        String creator = safeguardPlanBean.getMaker();
        String phone = "";
        String deadline = safeguardPlanBean.getDeadline();
        String content = "";
        // 去除已阅和转审给抄送人和自己为抄送人的情况
        String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + approvers
                + "','" + userInfo.getUserID() + "')";
        String approverIdsReader = safeguardPlanDao.getApproverIds(planId,
                SafeguardConstant.APPROVE_READ, SafeguardConstant.LP_SAFEGUARD_PLAN, condition);
        // 保存审核信息
        saveApproveInfo(planId, SafeguardConstant.LP_SAFEGUARD_PLAN, userInfo.getUserID(),
                approveResult, approveRemark);

        if ("approve".equals(operator)) {
            phone = getPhoneByUserId(creator);
            if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {
                safeguardConDao.setStateByTaskIdAndConId(taskId, conId, SafeguardCon.PLANUNAPPROVE);
                try {
                    safeguardPlanDao.setUnpassTimessAndDeadline(planId, deadline);
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new ServiceException();
                }

                // 发送短信内容
                content = "【保障】您的\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                        + "\"保障方案单未通过审核。";
            } else {
                safeguardConDao.setStateByTaskIdAndConId(taskId, conId, SafeguardCon.ADDSUMMARY);

                // 设置保障总结提交时限
                safeguardPlanDao.setDeadline(planId, deadline);

                // 发送短信内容
                content = "【保障】您的\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                        + "\"保障方案单已经通过审核。";
            }

        } else {
            // 保存审核人信息
            replyApproverDAO.saveApproverOrReader(approvers, planId,
                    SafeguardConstant.APPROVE_TRANSFER_MAN, SafeguardConstant.LP_SAFEGUARD_PLAN);
            phone = safeguardPlanBean.getMobiles();
            // 发送短信
            content = "【保障】您有一个名称为\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                    + "\"保障方案单等待您的审核。";
        }
        String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, conId);
        logger.info("*************eid:" + eid);
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
        if (task != null && SafeguardWorkflowBO.APPROVE_GUARD_PROJ_TASK.equals(task.getName())) {
            System.out.println("保障计划待审核：" + task.getName());
            if (SafeguardConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
                workflowBo.setVariables(task, "assignee", creator);
                workflowBo.setVariables(task, "transition", "execute");
                workflowBo.completeTask(task.getId(), "passed");
                System.out.println("保障计划审核通过！");

                // 保存流程历史
                ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
                String nextOperateUserId = "";
                processHistoryBean.setProcessAction("保障方案审批通过");// 添加流程处理动作说明
                processHistoryBean.setTaskOutCome("execute");// 添加工作流流向信息
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
            if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {
                workflowBo.setVariables(task, "assignee", creator);
                workflowBo.completeTask(task.getId(), "not_passed");
                System.out.println("保障计划审核不通过！");

                // 保存流程历史
                ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
                String nextOperateUserId = "";
                processHistoryBean.setProcessAction("保障方案审批不通过");// 添加流程处理动作说明
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
                workflowBo.setVariables(task, "assignee", approvers + "," + approverIdsReader);
                workflowBo.setVariables(task, "transition", "transfer");
                workflowBo.completeTask(task.getId(), "passed");
                System.out.println("保障计划已经转审！");

                // 保存流程历史
                ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
                String nextOperateUserId = "";
                processHistoryBean.setProcessAction("保障方案转审");// 添加流程处理动作说明
                processHistoryBean.setTaskOutCome("transfer");// 添加工作流流向信息
                processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
                processHistoryBean.setNextOperateUserId(approvers);// 添加下一步处理人的编号
                processHistoryBean.setObjectId(eid);// 添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
                try {
                    processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException();
                }
            }
        }

        // 发送短信
        sendMessage(content, phone);
        // 保存短信记录
        saveMessage(content, phone, planId, SafeguardConstant.LP_SAFEGUARD_PLAN,
                ModuleCatalog.SAFEGUARD);
    }

    /**
     * 重新制定特巡计划
     * 
     * @param spId：特巡计划ID
     * @param bussinessId：保障方案ID
     * @param userId：用户ID
     * @param spBean：特巡计划Bean
     * @param endId：保障方案终止ID
     * @param spPlanName：特巡计划名称
     * @throws ServiceException
     */
    public void redoSpecialPlan(String spId, String bussinessId, String userId,
            SpecialPlanBean spBean, String endId, String spPlanName) throws ServiceException {
        String approver = spBean.getApprover();
        String reader = spBean.getReader();

        // 保障特巡计划终止流程结束
        Task task = workflowBo.getHandleTaskForId(userId, "safeguard_sub." + endId);
        if (task != null && SafeguardSubWorkflowBO.WAIT_REPLAN_TASK.equals(task.getName())) {
            workflowBo.completeTask(task.getId(), "end");
            System.out.println("保障方案终止审核通过！");
        }

        // 启动特巡计划流程
        Map variables = new HashMap();
        variables.put("assignee", userId);
        // variables.put("transition", "start");
        workflowBo.createProcessInstance("safeguard_replan_sub", spId, variables);
        task = workflowBo.getHandleTaskForId(userId, "safeguard_replan_sub." + spId);
        if (task != null
                && SafeguardReplanSubWorkflowBO.REMAKE_GUARD_PLAN_TASK.equals(task.getName())) {
            workflowBo.setVariables(task, "assignee", approver + "," + reader);
            workflowBo.completeTask(task.getId(), "approve");

            UserInfo userInfo = new UserInfo();
            userInfo.setUserID(userId);

            String conId = safeguardConDao.getConIdByPlanId(bussinessId);
            // 保存流程历史
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            processHistoryBean.setProcessAction("特巡计划重新制定");// 添加流程处理动作说明
            processHistoryBean.setTaskOutCome("approve");// 添加工作流流向信息
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
            processHistoryBean.setNextOperateUserId(approver + "," + reader);// 添加下一步处理人的编号
            processHistoryBean.setObjectId(conId);// 添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
            try {
                processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException();
            }
        }
        String content = "【保障】您有一个名称为\"" + spPlanName + "\"特巡计划单等待您的审核。";
        String phone = getPhoneByUserId(userId);
        // 发送短信
        sendMessage(content, phone);
        // 保存短信记录
        saveMessage(content, phone, spId, SafeguardConstant.LP_SPECIAL_PLAN,
                ModuleCatalog.SAFEGUARD);
    }

    /**
     * 修改特巡计划信息
     * 
     * @param spId：特巡计划ID
     * @param bussinessId：保障方案ID
     * @param userId：用户ID
     * @param spBean：特巡计划Bean
     * @param spPlanName：特巡计划名称
     * @throws ServiceException
     */
    public void editSpecialPlan(String spId, String bussinessId, String userId,
            SpecialPlanBean spBean, String spPlanName) throws ServiceException {
        String approver = spBean.getApprover();
        String reader = spBean.getReader();

        Task task = workflowBo.getHandleTaskForId(userId, "safeguard_replan_sub." + spId);
        if (task != null
                && SafeguardReplanSubWorkflowBO.REMAKE_GUARD_PLAN_TASK.equals(task.getName())) {
            workflowBo.setVariables(task, "assignee", approver + "," + reader);
            workflowBo.completeTask(task.getId(), "approve");

            String conId = safeguardConDao.getConIdByPlanId(bussinessId);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserID(userId);
            // 保存流程历史
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            processHistoryBean.setProcessAction("修改特巡计划");// 添加流程处理动作说明
            processHistoryBean.setTaskOutCome("approve");// 添加工作流流向信息
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
            processHistoryBean.setNextOperateUserId(approver + "," + reader);// 添加下一步处理人的编号
            processHistoryBean.setObjectId(conId);// 添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
            try {
                processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException();
            }
        }

        String content = "【保障】您有一个名称为\"" + spPlanName + "\"特巡计划单等待您的审核。";
        String phone = getPhoneByUserId(userId);
        // 发送短信
        sendMessage(content, phone);
        // 保存短信记录
        saveMessage(content, phone, spId, SafeguardConstant.LP_SPECIAL_PLAN,
                ModuleCatalog.SAFEGUARD);
    }

    /**
     * 特巡计划审批
     * 
     * @param userInfo
     * @param spId：特巡计划ID
     */
    public void approveRedoSpecialPlan(UserInfo userInfo, String spId) {
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard_replan_sub."
                + spId);
        if (task != null
                && SafeguardReplanSubWorkflowBO.REMAKE_GUARD_PLAN_APPROVE_TASK.equals(task
                        .getName())) {
            workflowBo.setVariables(task, "assignee", "");
            workflowBo.completeTask(task.getId(), "approve");

            // 保存流程历史
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            processHistoryBean.setProcessAction("特巡重新制定");// 添加流程处理动作说明
            processHistoryBean.setTaskOutCome("approve");// 添加工作流流向信息
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
            processHistoryBean.setNextOperateUserId("");// 添加下一步处理人的编号
            processHistoryBean.setObjectId(spId);// 添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
            try {
                processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException();
            }
        }
    }

    /**
     * 通过保障计划ID查询保障计划、保障任务和保障计划与中继段关系
     * 
     * @param planId：保障计划ID
     * @return
     */
    public Map getSafeguardPlanData(String planId) {
        String taskId = safeguardPlanDao.getTaskIdByPlanId(planId);
        SafeguardPlan safeguardPlan = safeguardPlanDao.findByUnique("id", planId);
        SafeguardTask safeguardTask = safeguardTaskDao.findByUnique("id", taskId);
        String sublineIds = safeguardSegmentDao.getSublineIds(planId);
        List list = safeguardSpplanDao.getSafeguardSpplanByPlanId(planId);
        List safeguardSps = null;
        List specialPlans = new ArrayList();
        safeguardSps = safeguardSpplanDao.findByProperty("planId", planId);
        String[] approveInfo = safeguardPlanDao.getUserIdAndUserNameByUserId(safeguardTask
                .getSender());
        // String flag =
        if (safeguardSps != null) {
            for (Iterator iterator = safeguardSps.iterator(); iterator.hasNext();) {
                SafeguardSpplan safeguardSp = (SafeguardSpplan) iterator.next();
                String spId = safeguardSp.getSpplanId();
                /*
                 * SpecialPlan specialPlan = specialPlanDao.findByUnique("id",
                 * spId); String patrolName =
                 * safeguardSpplanDao.getPatrolGroupIdBySpId(spId);
                 * specialPlan.setPatrolGroupId(patrolName);
                 */
                List<Map> specialPlan = safeguardPlanDao.getSafeguardPlan(spId);
                specialPlans.add(specialPlan);
            }
        }
        Map map = new HashMap();
        map.put("safeguardTask", safeguardTask);
        map.put("safeguardPlan", safeguardPlan);
        map.put("safeguardSps", safeguardSps);
        map.put("specialPlans", specialPlans);
        map.put("approveInfo", approveInfo);
        map.put("sublineIds", sublineIds);
        map.put("list", list);
        return map;
    }

    /**
     * 查看保障方案信息
     * 
     * @param planId：保障方案ID
     * @return
     */
    public Map viewSafeguardPlanData(String planId) {
        String taskId = safeguardPlanDao.getTaskIdByPlanId(planId);
        SafeguardPlan safeguardPlan = safeguardPlanDao.findByUnique("id", planId);
        SafeguardTask safeguardTask = safeguardTaskDao.findByUnique("id", taskId);
        String sublineIds = safeguardSegmentDao.getSublineIds(planId);
        List list = safeguardSpplanDao.getSafeguardSpplanByPlanId(planId);
        List safeguardSps2 = null;
        List specialPlans = new ArrayList();
        List safeguardSps = new ArrayList();
        safeguardSps2 = safeguardSpplanDao.findByProperty("planId", planId);
        String conId = safeguardConDao.getConIdByPlanId(planId);
        // String flag =
        if (safeguardSps2 != null) {
            for (Iterator iterator = safeguardSps2.iterator(); iterator.hasNext();) {
                SafeguardSpplan safeguardSp = (SafeguardSpplan) iterator.next();
                String spId = safeguardSp.getSpplanId();
                String flag = specialEndPlanDao.judgeProcessing(spId);
                logger.info("****************" + flag);
                // SpecialPlan specialPlan = specialPlanDao.findByUnique("id",
                // spId);
                List<Map> specialPlan1 = safeguardPlanDao.getSafeguardPlan(spId);
                List<Map> specialPlan = new ArrayList();
                /*
                 * String spName = specialPlan.getPlanName(); String patrolName =
                 * safeguardSpplanDao.getPatrolGroupIdBySpId(spId);
                 * specialPlan.setPatrolGroupId(patrolName); Map map1 = new
                 * HashMap(); map1.put(flag, new
                 * String[]{spId,spName,patrolName});
                 */
                if (specialPlan1 != null && specialPlan1.size() > 0) {
                    for (int i = 0; i < specialPlan1.size(); i++) {
                        Map map = (Map) specialPlan1.get(i);
                        map.put("flag", flag);
                        specialPlan.add(map);
                    }
                }
                safeguardSps.add(specialPlan);
                // List<Map> specialPlan =
                // safeguardPlanDao.getSafeguardPlan(spId);
                specialPlans.add(specialPlan);
            }
        }
        Map map = new HashMap();
        map.put("safeguardTask", safeguardTask);
        map.put("safeguardPlan", safeguardPlan);
        map.put("safeguardSps", safeguardSps);
        map.put("specialPlans", specialPlans);
        map.put("sublineIds", sublineIds);
        map.put("list", list);
        map.put("conId", conId);
        return map;
    }

    /**
     * 编辑保障计划
     * 
     * @param safeguardPlanBean：保障计划实体Bean
     * @param userInfo
     * @param ids：删除附件IDS
     * @param trunkList：中继段列表
     * @return：特巡计划ID
     */
    public void editSafeguardPlan(SafeguardPlanBean safeguardPlanBean, UserInfo userInfo,
            List trunkList, List<FileItem> files) {
        String planId = safeguardPlanBean.getId();
        String taskId = safeguardPlanBean.getSafeguardId();
        String conId = userInfo.getDeptID();
        String approver = safeguardPlanBean.getApproveId();
        String reader = safeguardPlanBean.getReader();
        String[] readerPhones = safeguardPlanBean.getReaderPhones();

        saveSafeguardPlan(safeguardPlanBean, userInfo.getDeptID(), userInfo.getUserID());

        // 保存保障与中继段关系
        saveSafeguardPlanCon(trunkList, planId);

        // 保存附件
        saveFiles(files, ModuleCatalog.SAFEGUARD, userInfo.getRegionName(), safeguardPlanBean
                .getId(), SafeguardConstant.LP_SAFEGUARD_PLAN, userInfo.getUserID());

        // 保存保障与中继段关系
        saveSafeguardPlanCon(trunkList, planId);

        // 更新保障与代维关系表
        safeguardConDao.setStateByTaskIdAndConId(taskId, conId, SafeguardCon.APPROVEPLAN);
        replyApproverDAO.deleteList(planId, SafeguardConstant.LP_SAFEGUARD_PLAN);
        // 保存审核人
        replyApproverDAO.saveApproverOrReader(approver, planId, SafeguardConstant.APPROVE_MAN,
                SafeguardConstant.LP_SAFEGUARD_PLAN);
        // 保存抄送人
        replyApproverDAO.saveApproverOrReader(reader, planId, SafeguardConstant.APPROVE_READ,
                SafeguardConstant.LP_SAFEGUARD_PLAN);

        String name = safeguardTaskDao.getTaskNameByTaskId(taskId);
        String mobiles = safeguardTaskDao.getMobiles(approver);
        // 代维认领线维分配任务并制定计划
        String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, conId);
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
        if (task != null && SafeguardWorkflowBO.CREATE_GUARD_PROJ_TASK.equals(task.getName())) {
            System.out.println("保障计划添加中：" + task.getName());
            workflowBo.setVariables(task, "assignee", approver + "," + reader);
            workflowBo.completeTask(task.getId(), "approve");
            System.out.println("保障计划已经提交！");

            // 保存流程历史
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            processHistoryBean.setProcessAction("制定保障方案");// 添加流程处理动作说明
            processHistoryBean.setTaskOutCome("approve");// 添加工作流流向信息
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
            processHistoryBean.setNextOperateUserId(approver + "," + reader);// 添加下一步处理人的编号
            processHistoryBean.setObjectId(eid);// 添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
            try {
                processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException();
            }
        }

        // 发送短信
        String content = "【保障】您有一个名称为\"" + name + "\"保障方案单等待您的审核。";
        sendMessage(content, mobiles);
        // 保存短信记录
        saveMessage(content, mobiles, planId, SafeguardConstant.LP_SAFEGUARD_PLAN,
                ModuleCatalog.SAFEGUARD);

        if (readerPhones != null && readerPhones.length > 0) {
            for (int i = 0; i < readerPhones.length; i++) {
                content = "【保障】您有一个名称为\"" + name + "\"保障方案单等待您的查阅。";
                if (!"".equals(readerPhones[i])) {
                    sendMessage(content, readerPhones[i]);

                    // 保存短信记录
                    saveMessage(content, readerPhones[i], planId,
                            SafeguardConstant.LP_SAFEGUARD_PLAN, ModuleCatalog.SAFEGUARD);
                }
            }
        }

    }

    /**
     * 保存保障计划实体
     * 
     * @param safeguardPlanBean：保障计划实体Bean
     * @param conId：代维单位
     * @param creator：保障计划创建人
     * @return：保障计划实体
     */
    private SafeguardPlan saveSafeguardPlan(SafeguardPlanBean safeguardPlanBean, String conId,
            String creator) {
        SafeguardPlan safeguardPlan = new SafeguardPlan();
        try {
            BeanUtil.objectCopy(safeguardPlanBean, safeguardPlan);
        } catch (Exception e) {
            logger.error("SafeguardPlanBean转换为SafeguardPlan出错，出错信息：" + e.getMessage());
        }

        safeguardPlan.setContractorId(conId);
        safeguardPlan.setMaker(creator);
        safeguardPlan.setMakeDate(new Date());
        return safeguardPlanDao.saveSafeguardPlan(safeguardPlan);
    }

    /**
     * 保存保障计划与中继段关系
     * 
     * @param trunkList：中继段列表
     * @param planId：保障计划ID
     */
    private void saveSafeguardPlanCon(List<String> trunkList, String planId) {
        if (trunkList != null) {
            for (Iterator iterator = trunkList.iterator(); iterator.hasNext();) {
                String sublineId = (String) iterator.next();
                SafeguardSegment safeguardSegment = new SafeguardSegment();
                safeguardSegment.setPlanId(planId);
                safeguardSegment.setSegmentId(sublineId);
                safeguardSegmentDao.save(safeguardSegment);
            }
        }
    }

    /**
     * 将字符串转换为List
     * 
     * @param str：需要转换的字符串
     * @return：list
     */
    public List<String> stringToList(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        String[] strArray = str.split(",");
        List<String> list = new ArrayList<String>();
        for (int i = 0; strArray != null && i < strArray.length; i++) {
            list.add(strArray[i]);
        }
        return list;
    }

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
            // List<String> mobileList = StringUtils.string2List(mobiles, ",");
            // super.sendMessage(content, mobiles);
            try {
                super.sendMessage(content, mobiles);
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
        List list = safeguardPlanDao.getHibernateTemplate().find(hql, userId);
        if (list != null && !list.equals("")) {
            UserInfo userInfo = (UserInfo) list.get(0);
            return userInfo.getPhone();
        }
        return "";
    }

    /**
     * 查阅保障方案
     * 
     * @param userInfo
     * @param approverId：审批人ID
     * @param planId：保障方案ID
     * @throws ServiceException
     */
    public void readReply(UserInfo userInfo, String approverId, String planId)
            throws ServiceException {
        approverDAO.updateReader(approverId, planId, SafeguardConstant.LP_SAFEGUARD_PLAN);

        SafeguardPlan safeguardPlan = safeguardPlanDao.findByUnique("id", planId);
        String creator = safeguardPlan.getMaker();
        String taskId = safeguardPlan.getSafeguardId();
        String conId = safeguardPlan.getContractorId();
        String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, conId);
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
        // 保存流程历史
        ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
        processHistoryBean.setProcessAction("保障方案查阅");// 添加流程处理动作说明
        processHistoryBean.setTaskOutCome("approve");// 添加工作流流向信息
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
