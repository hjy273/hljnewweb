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
     * ͨ���ƻ�ID��ѯ��������ͱ��ϼƻ�
     * 
     * @param planId�����ϼƻ�ID
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
     * ��ӱ����ܽ�
     * 
     * @param safeguardSummaryBean�������ܽ�ʵ��Bean
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
        if (ssSize.size() <= 0) {// �����ܽ��ظ������֤����ֹ���д�����ݡ�
            SafeguardSummary safeguardSummarySave = saveSafeguardSummary(safeguardSummaryBean,
                    userInfo.getUserID());
            String id = safeguardSummarySave.getId();
            String planId = safeguardSummarySave.getPlanId();
            safeguardConDao.setStateByTaskIdAndConId(taskId, deptId, SafeguardCon.APPROVESUMMARY);

            // �ϴ��ļ�
            saveFiles(files, ModuleCatalog.SAFEGUARD, userInfo.getRegionName(), id,
                    SafeguardConstant.LP_SAFEGUARD_SUMMARY, userInfo.getUserID());

            // �����������Ϣ
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
                System.out.println("���Ϸ�������У�" + task.getName());
                workflowBo.setVariables(task, "assignee", approveId + "," + reader);
                workflowBo.completeTask(task.getId(), "approve");
                logger.info("���Ϸ����Ѿ��ύ��");

                // ����������ʷ
                ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
                String nextOperateUserId = "";
                processHistoryBean.setProcessAction("�ƶ������ܽ�");// ������̴�����˵��
                processHistoryBean.setTaskOutCome("approve");// ��ӹ�����������Ϣ
                processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
                processHistoryBean.setNextOperateUserId(approveId + "," + reader);// �����һ�������˵ı��
                processHistoryBean.setObjectId(eid);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
                try {
                    processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException();
                }
            }
            // ���Ͷ���
            String content = "�����ϡ�����һ������Ϊ\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                    + "\"�����ܽᵥ�ȴ�������ˡ�";
            sendMessage(content, mobiles);

            // ������ż�¼
            saveMessage(content, mobiles, id, SafeguardConstant.LP_SAFEGUARD_SUMMARY,
                    ModuleCatalog.SAFEGUARD);

            if (readerPhones != null && readerPhones.length > 0) {
                for (int i = 0; i < readerPhones.length; i++) {
                    content = "�����ϡ�����һ������Ϊ\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                            + "\"�����ܽᵥ�ȴ����Ĳ��ġ�";
                    sendMessage(content, readerPhones[i]);

                    // ������ż�¼
                    saveMessage(content, readerPhones[i], id,
                            SafeguardConstant.LP_SAFEGUARD_SUMMARY, ModuleCatalog.SAFEGUARD);
                }
            }
        }
    }

    /**
     * �����ܽ���ʱ����
     * 
     * @param safeguardSummaryBean�������ܽ�Bean
     * @param userInfo
     * @param files��������Ϣ
     * @throws ServiceException
     */
    public void tempSaveSafeguardSummary(SafeguardSummaryBean safeguardSummaryBean,
            UserInfo userInfo, List<FileItem> files) throws ServiceException {
        String approveId = safeguardSummaryBean.getApproveId();
        String reader = safeguardSummaryBean.getReader();

        SafeguardSummary safeguardSummarySave = saveSafeguardSummary(safeguardSummaryBean, userInfo
                .getUserID());
        String id = safeguardSummarySave.getId();

        // �ϴ��ļ�
        saveFiles(files, ModuleCatalog.SAFEGUARD, userInfo.getRegionName(), id,
                SafeguardConstant.LP_SAFEGUARD_SUMMARY, userInfo.getUserID());

        replyApproverDAO.deleteList(id, SafeguardConstant.LP_SAFEGUARD_SUMMARY);
        // �����������Ϣ
        replyApproverDAO.saveApproverOrReader(approveId, id, SafeguardConstant.APPROVE_MAN,
                SafeguardConstant.LP_SAFEGUARD_SUMMARY);

        replyApproverDAO.saveApproverOrReader(reader, id, SafeguardConstant.APPROVE_READ,
                SafeguardConstant.LP_SAFEGUARD_SUMMARY);
    }

    /**
     * ���汣���ܽ�ʵ��
     * 
     * @param safeguardSummaryBean�������ܽ�ʵ��Bean
     * @param creator�������ܽᴴ����
     * @return�������ܽ�ʵ��
     */
    private SafeguardSummary saveSafeguardSummary(SafeguardSummaryBean safeguardSummaryBean,
            String creator) {
        SafeguardSummary safeguardSummary = new SafeguardSummary();
        try {
            BeanUtil.objectCopy(safeguardSummaryBean, safeguardSummary);
        } catch (Exception e) {
            logger.error("SafeguardSummaryBeanת��ΪSafeguardSummary����������Ϣ��" + e.getMessage());
        }
        safeguardSummary.setSumManId(creator);
        safeguardSummary.setSumDate(new Date());
        return safeguardSummaryDao.saveSafeguardSummary(safeguardSummary);
    }

    /**
     * �ɱ����ܽ�ID��ѯ�������񡢱��ϼƻ������ϼƻ����м̶ι�ϵ�������ܽ�
     * 
     * @param summaryId�������ܽ�ID
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
     * ��˱����ܽ�
     * 
     * @param safeguardSummaryBean�������ܽ�ʵ��Bean
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
        // ȥ�����ĺ�ת��������˺��Լ�Ϊ�����˵����
        String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + approves
                + "','" + userInfo.getUserID() + "')";
        String approverIdsReader = safeguardPlanDao.getApproverIds(summaryId,
                SafeguardConstant.APPROVE_READ, SafeguardConstant.LP_SAFEGUARD_SUMMARY, condition);
        // ���������Ϣ
        saveApproveInfo(summaryId, SafeguardConstant.LP_SAFEGUARD_SUMMARY, userInfo.getUserID(),
                approveResult, approveRemark);

        if ("approve".equals(operator)) {
            phone = getPhoneByUserId(creator);
            if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {
                safeguardConDao.setStateByTaskIdAndConId(taskId, conId,
                        SafeguardCon.SUMMARYUNAPPROVE);

                // ������������
                content = "�����ϡ�����\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                        + "\"�����ܽ�δͨ����ˡ�";
            } else {
                safeguardConDao.setStateByTaskIdAndConId(taskId, conId, SafeguardCon.EVALUATE);

                // ������������
                content = "�����ϡ�����\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                        + "\"�����ܽ��Ѿ�ͨ����ˡ�";
            }

        } else {
            // �����������Ϣ
            replyApproverDAO.saveApproverOrReader(approves, summaryId,
                    SafeguardConstant.APPROVE_TRANSFER_MAN, SafeguardConstant.LP_SAFEGUARD_SUMMARY);
            phone = safeguardSummaryBean.getMobiles();
            // ���Ͷ���
            content = "�����ϡ�����һ������Ϊ\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                    + "\"�����ܽᵥ�ȴ�������ˡ�";

        }
        String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, conId);
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
        if (task != null && SafeguardWorkflowBO.APPROVE_GUARD_SUMMARY_TASK.equals(task.getName())) {
            System.out.println("���Ϸ�������ˣ�" + task.getName());
            if (SafeguardConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
                workflowBo.setVariables(task, "assignee", userInfo.getUserID());
                workflowBo.setVariables(task, "transition", "evaluate");
                workflowBo.completeTask(task.getId(), "passed");
                System.out.println("���Ϸ������ͨ����");

                // ����������ʷ
                ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
                String nextOperateUserId = "";
                processHistoryBean.setProcessAction("�����ܽ�����ͨ��");// ������̴�����˵��
                processHistoryBean.setTaskOutCome("evaluate");// ��ӹ�����������Ϣ
                processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
                processHistoryBean.setNextOperateUserId(userInfo.getUserID());// �����һ�������˵ı��
                processHistoryBean.setObjectId(eid);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
                try {
                    processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException();
                }
            }
            if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {
                workflowBo.setVariables(task, "assignee", creator);
                workflowBo.completeTask(task.getId(), "not_passed");
                System.out.println("���Ϸ�����˲�ͨ����");

                // ����������ʷ
                ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
                String nextOperateUserId = "";
                processHistoryBean.setProcessAction("�����ܽ�������ͨ��");// ������̴�����˵��
                processHistoryBean.setTaskOutCome("not_passed");// ��ӹ�����������Ϣ
                processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
                processHistoryBean.setNextOperateUserId(creator);// �����һ�������˵ı��
                processHistoryBean.setObjectId(eid);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
                try {
                    processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException();
                }
            }
            if (SafeguardConstant.APPROVE_RESULT_TRANSFER.equals(approveResult)) {
                workflowBo.setVariables(task, "assignee", approves + "," + approverIdsReader);
                workflowBo.setVariables(task, "transition", "transfer");
                workflowBo.completeTask(task.getId(), "passed");
                System.out.println("���Ϸ����Ѿ�ת��");

                // ����������ʷ
                ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
                String nextOperateUserId = "";
                processHistoryBean.setProcessAction("�����ܽ�ת��");// ������̴�����˵��
                processHistoryBean.setTaskOutCome("transfer");// ��ӹ�����������Ϣ
                processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
                processHistoryBean.setNextOperateUserId(approves);// �����һ�������˵ı��
                processHistoryBean.setObjectId(eid);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
                try {
                    processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException();
                }
            }
        }
        sendMessage(content, phone);
        // ������ż�¼
        saveMessage(content, phone, summaryId, SafeguardConstant.LP_SAFEGUARD_SUMMARY,
                ModuleCatalog.SAFEGUARD);
    }

    /**
     * �༭�����ܽ�
     * 
     * @param safeguardSummaryBean�������ܽ�ʵ��Bean
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

        // ���汣���ܽ�ʵ��
        saveSafeguardSummary(safeguardSummaryBean, userInfo.getUserID());

        // �޸ı������ά��ϵ״̬
        safeguardConDao.setStateByTaskIdAndConId(taskId, conId, SafeguardCon.APPROVESUMMARY);

        // ���渽��
        saveFiles(files, ModuleCatalog.SAFEGUARD, userInfo.getRegionName(), summaryId,
                SafeguardConstant.LP_SAFEGUARD_SUMMARY, userInfo.getUserID());
        replyApproverDAO.deleteList(summaryId, SafeguardConstant.LP_SAFEGUARD_SUMMARY);
        // �����������Ϣ
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
            System.out.println("���Ϸ�������У�" + task.getName());
            workflowBo.setVariables(task, "assignee", approveId + "," + reader);
            workflowBo.completeTask(task.getId(), "approve");
            System.out.println("���Ϸ����Ѿ��ύ��");

            // ����������ʷ
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            processHistoryBean.setProcessAction("�����ܽ�ת��");// ������̴�����˵��
            processHistoryBean.setTaskOutCome("approve");// ��ӹ�����������Ϣ
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
            processHistoryBean.setNextOperateUserId(approveId + "," + reader);// �����һ�������˵ı��
            processHistoryBean.setObjectId(eid);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
            try {
                processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException();
            }
        }

        // ���Ͷ���
        String content = "�����ϡ�����һ������Ϊ\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                + "\"�����ܽᵥ�ȴ�������ˡ�";
        sendMessage(content, mobiles);

        // ������ż�¼
        saveMessage(content, mobiles, summaryId, SafeguardConstant.LP_SAFEGUARD_SUMMARY,
                ModuleCatalog.SAFEGUARD);

        if (readerPhones != null && readerPhones.length > 0) {
            for (int i = 0; i < readerPhones.length; i++) {
                content = "�����ϡ�����һ������Ϊ\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                        + "\"�����ܽᵥ�ȴ����Ĳ��ġ�";
                sendMessage(content, readerPhones[i]);

                // ������ż�¼
                saveMessage(content, readerPhones[i], summaryId,
                        SafeguardConstant.LP_SAFEGUARD_SUMMARY, ModuleCatalog.SAFEGUARD);
            }
        }

    }

    /**
     * ���ַ���ת��ΪList
     * 
     * @param str����Ҫת�����ַ���
     * @return��list
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
     * �����ļ�
     * 
     * @param files���ϴ��ĸ���
     * @param module��ģ�鳣��
     * @param regionName����������
     * @param entityId������������ʵ��ID
     * @param entityType�����������ı���
     * @param uploader�������ϴ���
     */
    public void saveFiles(List<FileItem> files, String module, String regionName, String entityId,
            String entityType, String uploader) {
        uploadFile.saveFiles(files, module, regionName, entityId, entityType, uploader);
    }

    /**
     * ���������Ϣ
     * 
     * @param entityId��ʵ��ID
     * @param entityType��ʵ�����
     * @param approverId�������ID
     * @param approveResult����˽��
     * @param approveRemark��������
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
     * ���Ͷ���
     * 
     * @param content����������
     * @param mobiles�����ն����ֻ�����
     */
    public void sendMessage(String content, String mobiles) {
        if (mobiles != null && !"".equals(mobiles)) {
            List<String> mobileList = StringUtils.string2List(mobiles, ",");
            // super.sendMessage(content, mobileList);
            try {
                super.sendMessage(content, mobileList);
            } catch (Exception e) {
                logger.error("���Ͷ���ʧ��", e);
            }
        }
    }

    /**
     * ������ż�¼
     * 
     * @param content����������
     * @param mobiles�����ն����ֻ�����
     * @param entityId��ʵ��ID
     * @param entityType��ʵ������
     * @param entityModule��ʵ��ģ��
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
     * ͨ���û�ID��ѯ�û����ֻ�����
     * 
     * @param userId���û�ID
     * @return�������û��ֻ�����
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
        // ����������ʷ
        ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
        processHistoryBean.setProcessAction("�����ܽ����");// ������̴�����˵��
        processHistoryBean.setTaskOutCome("");// ��ӹ�����������Ϣ
        processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
        processHistoryBean.setNextOperateUserId("");// �����һ�������˵ı��
        processHistoryBean.setObjectId(eid);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
        try {
            processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }
}
