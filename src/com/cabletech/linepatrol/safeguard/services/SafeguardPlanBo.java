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
     * ��ӱ��ϼƻ�ǰ��������
     * 
     * @param taskId����������ID
     * @param userInfo
     * @return
     */
    public Map addSafeguardPlanForm(String taskId, UserInfo userInfo) {
        String existFlag = null;
        String conId = userInfo.getDeptID();
        Map map = new HashMap();
        String[] approveInfo = null;
        // ���ر��Ϸ�����Ϣ
        SafeguardTask safeguardTask = safeguardTaskDao.findByUnique("id", taskId);
        // �����Ϸ����Ѵ��ڣ����ر��Ϸ�����Ϣ
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
     * ����Ѳ�ƻ�ID������Ѳ�ƻ��б�
     * 
     * @param spid����Ѳ�ƻ�ID
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
     * ͨ������ID��ѯ�ƻ��б�
     * 
     * @param taskId����������ID
     * @return�����ϼƻ��б�
     */
    public List getPlanByTaskIdConId(String taskId, String conId) {
        return safeguardPlanDao.getPlanByTaskIdConId(taskId, conId);
    }

    /**
     * ͨ������ID�ʹ�ά��λID��ѯ���ϼƻ�ID�ͱ�������
     * 
     * @param taskId����������ID
     * @param conId����ά��λID
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
     * ��ӱ��Ϸ�����Ϣ
     * 
     * @param safeguardPlanBean�����Ϸ���ID
     * @param userInfo
     * @param trunkList���м̶��б�
     * @param files��������Ϣ
     * @param spplanIds����Ѳ�ƻ�IDS
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
        // ���汣�ϼƻ�
        SafeguardPlan safeguardPlanSave = saveSafeguardPlan(safeguardPlanBean, deptId, userId);
        // ���汣�ϼƻ�����Ѳ�ƻ���ϵ
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

        // �ϴ��ļ�
        saveFiles(files, ModuleCatalog.SAFEGUARD, userInfo.getRegionName(), safeguardPlanSave
                .getId(), SafeguardConstant.LP_SAFEGUARD_PLAN, userInfo.getUserID());

        // ���汣�����м̶ι�ϵ
        saveSafeguardPlanCon(trunkList, planId);

        // ���±������ά��ϵ��
        safeguardConDao.setStateByTaskIdAndConId(taskId, conId, SafeguardCon.APPROVEPLAN);
        // ���������
        replyApproverDAO.saveApproverOrReader(approver, planId, SafeguardConstant.APPROVE_MAN,
                SafeguardConstant.LP_SAFEGUARD_PLAN);
        // ���泭����
        replyApproverDAO.saveApproverOrReader(reader, planId, SafeguardConstant.APPROVE_READ,
                SafeguardConstant.LP_SAFEGUARD_PLAN);

        String name = safeguardTaskDao.getTaskNameByTaskId(taskId);
        String mobiles = safeguardTaskDao.getMobiles(approver);
        // ��ά������ά���������ƶ��ƻ�
        String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, conId);
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
        if (task != null && SafeguardWorkflowBO.CREATE_GUARD_PROJ_TASK.equals(task.getName())) {
            System.out.println("���ϼƻ�����У�" + task.getName());
            workflowBo.setVariables(task, "assignee", approver + "," + reader);
            workflowBo.completeTask(task.getId(), "approve");
            System.out.println("���ϼƻ��Ѿ��ύ��");

            // ����������ʷ
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            processHistoryBean.setProcessAction("�ƶ����Ϸ���");// ������̴�����˵��
            processHistoryBean.setTaskOutCome("approve");// ��ӹ�����������Ϣ
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
            processHistoryBean.setNextOperateUserId(approver + "," + reader);// �����һ�������˵ı��
            processHistoryBean.setObjectId(eid);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
            try {
                processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException();
            }
        }

        // ���Ͷ���
        String content = "�����ϡ�����һ������Ϊ\"" + name + "\"���Ϸ������ȴ�������ˡ�";
        sendMessage(content, mobiles);
        // ������ż�¼
        saveMessage(content, mobiles, planId, SafeguardConstant.LP_SAFEGUARD_PLAN,
                ModuleCatalog.SAFEGUARD);

        if (readerPhones != null && readerPhones.length > 0) {
            for (int i = 0; i < readerPhones.length; i++) {
                content = "�����ϡ�����һ������Ϊ\"" + name + "\"���Ϸ������ȴ����Ĳ��ġ�";
                if (!"".equals(readerPhones[i])) {
                    sendMessage(content, readerPhones[i]);

                    // ������ż�¼
                    saveMessage(content, readerPhones[i], planId,
                            SafeguardConstant.LP_SAFEGUARD_PLAN, ModuleCatalog.SAFEGUARD);
                }
            }
        }

    }

    /**
     * ���Ϸ����ݴ�
     * 
     * @param safeguardPlanBean�����Ϸ���Bean
     * @param userInfo
     * @param trunkList���м̶��б�
     * @param files��������Ϣ
     * @param spplanIds����Ѳ�ƻ�IDS
     * @throws ServiceException
     */
    public void tempSaveSafeguardPlan(SafeguardPlanBean safeguardPlanBean, UserInfo userInfo,
            List<String> trunkList, List<FileItem> files, String[] spplanIds)
            throws ServiceException {
        String deptId = userInfo.getDeptID();
        String userId = userInfo.getUserID();
        String approver = safeguardPlanBean.getApproveId();
        String reader = safeguardPlanBean.getReader();
        // ���汣�ϼƻ�
        SafeguardPlan safeguardPlanSave = saveSafeguardPlan(safeguardPlanBean, deptId, userId);
        // ���汣�ϼƻ�����Ѳ�ƻ���ϵ
        String planId = safeguardPlanSave.getId();
        if (spplanIds != null && spplanIds.length > 0) {
            for (int i = 0; i < spplanIds.length; i++) {
                SafeguardSpplan safeguardSpplan = new SafeguardSpplan();
                safeguardSpplan.setPlanId(planId);
                safeguardSpplan.setSpplanId(spplanIds[i]);
                safeguardSpplanDao.save(safeguardSpplan);
            }
        }

        // �ϴ��ļ�
        saveFiles(files, ModuleCatalog.SAFEGUARD, userInfo.getRegionName(), safeguardPlanSave
                .getId(), SafeguardConstant.LP_SAFEGUARD_PLAN, userInfo.getUserID());

        // ���汣�����м̶ι�ϵ
        saveSafeguardPlanCon(trunkList, planId);

        replyApproverDAO.deleteList(planId, SafeguardConstant.LP_SAFEGUARD_PLAN);
        // ���������
        replyApproverDAO.saveApproverOrReader(approver, planId, SafeguardConstant.APPROVE_MAN,
                SafeguardConstant.LP_SAFEGUARD_PLAN);
        // ���泭����
        replyApproverDAO.saveApproverOrReader(reader, planId, SafeguardConstant.APPROVE_READ,
                SafeguardConstant.LP_SAFEGUARD_PLAN);
    }

    /**
     * ���ϼƻ����
     * 
     * @param safeguardPlanBean�����ϼƻ�ʵ��Bean
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
        // ȥ�����ĺ�ת��������˺��Լ�Ϊ�����˵����
        String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + approvers
                + "','" + userInfo.getUserID() + "')";
        String approverIdsReader = safeguardPlanDao.getApproverIds(planId,
                SafeguardConstant.APPROVE_READ, SafeguardConstant.LP_SAFEGUARD_PLAN, condition);
        // ���������Ϣ
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

                // ���Ͷ�������
                content = "�����ϡ�����\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                        + "\"���Ϸ�����δͨ����ˡ�";
            } else {
                safeguardConDao.setStateByTaskIdAndConId(taskId, conId, SafeguardCon.ADDSUMMARY);

                // ���ñ����ܽ��ύʱ��
                safeguardPlanDao.setDeadline(planId, deadline);

                // ���Ͷ�������
                content = "�����ϡ�����\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                        + "\"���Ϸ������Ѿ�ͨ����ˡ�";
            }

        } else {
            // �����������Ϣ
            replyApproverDAO.saveApproverOrReader(approvers, planId,
                    SafeguardConstant.APPROVE_TRANSFER_MAN, SafeguardConstant.LP_SAFEGUARD_PLAN);
            phone = safeguardPlanBean.getMobiles();
            // ���Ͷ���
            content = "�����ϡ�����һ������Ϊ\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
                    + "\"���Ϸ������ȴ�������ˡ�";
        }
        String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, conId);
        logger.info("*************eid:" + eid);
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
        if (task != null && SafeguardWorkflowBO.APPROVE_GUARD_PROJ_TASK.equals(task.getName())) {
            System.out.println("���ϼƻ�����ˣ�" + task.getName());
            if (SafeguardConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
                workflowBo.setVariables(task, "assignee", creator);
                workflowBo.setVariables(task, "transition", "execute");
                workflowBo.completeTask(task.getId(), "passed");
                System.out.println("���ϼƻ����ͨ����");

                // ����������ʷ
                ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
                String nextOperateUserId = "";
                processHistoryBean.setProcessAction("���Ϸ�������ͨ��");// ������̴�����˵��
                processHistoryBean.setTaskOutCome("execute");// ��ӹ�����������Ϣ
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
            if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {
                workflowBo.setVariables(task, "assignee", creator);
                workflowBo.completeTask(task.getId(), "not_passed");
                System.out.println("���ϼƻ���˲�ͨ����");

                // ����������ʷ
                ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
                String nextOperateUserId = "";
                processHistoryBean.setProcessAction("���Ϸ���������ͨ��");// ������̴�����˵��
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
                workflowBo.setVariables(task, "assignee", approvers + "," + approverIdsReader);
                workflowBo.setVariables(task, "transition", "transfer");
                workflowBo.completeTask(task.getId(), "passed");
                System.out.println("���ϼƻ��Ѿ�ת��");

                // ����������ʷ
                ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
                String nextOperateUserId = "";
                processHistoryBean.setProcessAction("���Ϸ���ת��");// ������̴�����˵��
                processHistoryBean.setTaskOutCome("transfer");// ��ӹ�����������Ϣ
                processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
                processHistoryBean.setNextOperateUserId(approvers);// �����һ�������˵ı��
                processHistoryBean.setObjectId(eid);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
                try {
                    processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException();
                }
            }
        }

        // ���Ͷ���
        sendMessage(content, phone);
        // ������ż�¼
        saveMessage(content, phone, planId, SafeguardConstant.LP_SAFEGUARD_PLAN,
                ModuleCatalog.SAFEGUARD);
    }

    /**
     * �����ƶ���Ѳ�ƻ�
     * 
     * @param spId����Ѳ�ƻ�ID
     * @param bussinessId�����Ϸ���ID
     * @param userId���û�ID
     * @param spBean����Ѳ�ƻ�Bean
     * @param endId�����Ϸ�����ֹID
     * @param spPlanName����Ѳ�ƻ�����
     * @throws ServiceException
     */
    public void redoSpecialPlan(String spId, String bussinessId, String userId,
            SpecialPlanBean spBean, String endId, String spPlanName) throws ServiceException {
        String approver = spBean.getApprover();
        String reader = spBean.getReader();

        // ������Ѳ�ƻ���ֹ���̽���
        Task task = workflowBo.getHandleTaskForId(userId, "safeguard_sub." + endId);
        if (task != null && SafeguardSubWorkflowBO.WAIT_REPLAN_TASK.equals(task.getName())) {
            workflowBo.completeTask(task.getId(), "end");
            System.out.println("���Ϸ�����ֹ���ͨ����");
        }

        // ������Ѳ�ƻ�����
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
            // ����������ʷ
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            processHistoryBean.setProcessAction("��Ѳ�ƻ������ƶ�");// ������̴�����˵��
            processHistoryBean.setTaskOutCome("approve");// ��ӹ�����������Ϣ
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
            processHistoryBean.setNextOperateUserId(approver + "," + reader);// �����һ�������˵ı��
            processHistoryBean.setObjectId(conId);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
            try {
                processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException();
            }
        }
        String content = "�����ϡ�����һ������Ϊ\"" + spPlanName + "\"��Ѳ�ƻ����ȴ�������ˡ�";
        String phone = getPhoneByUserId(userId);
        // ���Ͷ���
        sendMessage(content, phone);
        // ������ż�¼
        saveMessage(content, phone, spId, SafeguardConstant.LP_SPECIAL_PLAN,
                ModuleCatalog.SAFEGUARD);
    }

    /**
     * �޸���Ѳ�ƻ���Ϣ
     * 
     * @param spId����Ѳ�ƻ�ID
     * @param bussinessId�����Ϸ���ID
     * @param userId���û�ID
     * @param spBean����Ѳ�ƻ�Bean
     * @param spPlanName����Ѳ�ƻ�����
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
            // ����������ʷ
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            processHistoryBean.setProcessAction("�޸���Ѳ�ƻ�");// ������̴�����˵��
            processHistoryBean.setTaskOutCome("approve");// ��ӹ�����������Ϣ
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
            processHistoryBean.setNextOperateUserId(approver + "," + reader);// �����һ�������˵ı��
            processHistoryBean.setObjectId(conId);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
            try {
                processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException();
            }
        }

        String content = "�����ϡ�����һ������Ϊ\"" + spPlanName + "\"��Ѳ�ƻ����ȴ�������ˡ�";
        String phone = getPhoneByUserId(userId);
        // ���Ͷ���
        sendMessage(content, phone);
        // ������ż�¼
        saveMessage(content, phone, spId, SafeguardConstant.LP_SPECIAL_PLAN,
                ModuleCatalog.SAFEGUARD);
    }

    /**
     * ��Ѳ�ƻ�����
     * 
     * @param userInfo
     * @param spId����Ѳ�ƻ�ID
     */
    public void approveRedoSpecialPlan(UserInfo userInfo, String spId) {
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard_replan_sub."
                + spId);
        if (task != null
                && SafeguardReplanSubWorkflowBO.REMAKE_GUARD_PLAN_APPROVE_TASK.equals(task
                        .getName())) {
            workflowBo.setVariables(task, "assignee", "");
            workflowBo.completeTask(task.getId(), "approve");

            // ����������ʷ
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            processHistoryBean.setProcessAction("��Ѳ�����ƶ�");// ������̴�����˵��
            processHistoryBean.setTaskOutCome("approve");// ��ӹ�����������Ϣ
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
            processHistoryBean.setNextOperateUserId("");// �����һ�������˵ı��
            processHistoryBean.setObjectId(spId);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
            try {
                processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException();
            }
        }
    }

    /**
     * ͨ�����ϼƻ�ID��ѯ���ϼƻ�����������ͱ��ϼƻ����м̶ι�ϵ
     * 
     * @param planId�����ϼƻ�ID
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
     * �鿴���Ϸ�����Ϣ
     * 
     * @param planId�����Ϸ���ID
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
     * �༭���ϼƻ�
     * 
     * @param safeguardPlanBean�����ϼƻ�ʵ��Bean
     * @param userInfo
     * @param ids��ɾ������IDS
     * @param trunkList���м̶��б�
     * @return����Ѳ�ƻ�ID
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

        // ���汣�����м̶ι�ϵ
        saveSafeguardPlanCon(trunkList, planId);

        // ���渽��
        saveFiles(files, ModuleCatalog.SAFEGUARD, userInfo.getRegionName(), safeguardPlanBean
                .getId(), SafeguardConstant.LP_SAFEGUARD_PLAN, userInfo.getUserID());

        // ���汣�����м̶ι�ϵ
        saveSafeguardPlanCon(trunkList, planId);

        // ���±������ά��ϵ��
        safeguardConDao.setStateByTaskIdAndConId(taskId, conId, SafeguardCon.APPROVEPLAN);
        replyApproverDAO.deleteList(planId, SafeguardConstant.LP_SAFEGUARD_PLAN);
        // ���������
        replyApproverDAO.saveApproverOrReader(approver, planId, SafeguardConstant.APPROVE_MAN,
                SafeguardConstant.LP_SAFEGUARD_PLAN);
        // ���泭����
        replyApproverDAO.saveApproverOrReader(reader, planId, SafeguardConstant.APPROVE_READ,
                SafeguardConstant.LP_SAFEGUARD_PLAN);

        String name = safeguardTaskDao.getTaskNameByTaskId(taskId);
        String mobiles = safeguardTaskDao.getMobiles(approver);
        // ��ά������ά���������ƶ��ƻ�
        String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, conId);
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard." + eid);
        if (task != null && SafeguardWorkflowBO.CREATE_GUARD_PROJ_TASK.equals(task.getName())) {
            System.out.println("���ϼƻ�����У�" + task.getName());
            workflowBo.setVariables(task, "assignee", approver + "," + reader);
            workflowBo.completeTask(task.getId(), "approve");
            System.out.println("���ϼƻ��Ѿ��ύ��");

            // ����������ʷ
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            processHistoryBean.setProcessAction("�ƶ����Ϸ���");// ������̴�����˵��
            processHistoryBean.setTaskOutCome("approve");// ��ӹ�����������Ϣ
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SAFEGUARD);
            processHistoryBean.setNextOperateUserId(approver + "," + reader);// �����һ�������˵ı��
            processHistoryBean.setObjectId(eid);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
            try {
                processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException();
            }
        }

        // ���Ͷ���
        String content = "�����ϡ�����һ������Ϊ\"" + name + "\"���Ϸ������ȴ�������ˡ�";
        sendMessage(content, mobiles);
        // ������ż�¼
        saveMessage(content, mobiles, planId, SafeguardConstant.LP_SAFEGUARD_PLAN,
                ModuleCatalog.SAFEGUARD);

        if (readerPhones != null && readerPhones.length > 0) {
            for (int i = 0; i < readerPhones.length; i++) {
                content = "�����ϡ�����һ������Ϊ\"" + name + "\"���Ϸ������ȴ����Ĳ��ġ�";
                if (!"".equals(readerPhones[i])) {
                    sendMessage(content, readerPhones[i]);

                    // ������ż�¼
                    saveMessage(content, readerPhones[i], planId,
                            SafeguardConstant.LP_SAFEGUARD_PLAN, ModuleCatalog.SAFEGUARD);
                }
            }
        }

    }

    /**
     * ���汣�ϼƻ�ʵ��
     * 
     * @param safeguardPlanBean�����ϼƻ�ʵ��Bean
     * @param conId����ά��λ
     * @param creator�����ϼƻ�������
     * @return�����ϼƻ�ʵ��
     */
    private SafeguardPlan saveSafeguardPlan(SafeguardPlanBean safeguardPlanBean, String conId,
            String creator) {
        SafeguardPlan safeguardPlan = new SafeguardPlan();
        try {
            BeanUtil.objectCopy(safeguardPlanBean, safeguardPlan);
        } catch (Exception e) {
            logger.error("SafeguardPlanBeanת��ΪSafeguardPlan����������Ϣ��" + e.getMessage());
        }

        safeguardPlan.setContractorId(conId);
        safeguardPlan.setMaker(creator);
        safeguardPlan.setMakeDate(new Date());
        return safeguardPlanDao.saveSafeguardPlan(safeguardPlan);
    }

    /**
     * ���汣�ϼƻ����м̶ι�ϵ
     * 
     * @param trunkList���м̶��б�
     * @param planId�����ϼƻ�ID
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
     * ���ַ���ת��ΪList
     * 
     * @param str����Ҫת�����ַ���
     * @return��list
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
            // List<String> mobileList = StringUtils.string2List(mobiles, ",");
            // super.sendMessage(content, mobiles);
            try {
                super.sendMessage(content, mobiles);
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
        List list = safeguardPlanDao.getHibernateTemplate().find(hql, userId);
        if (list != null && !list.equals("")) {
            UserInfo userInfo = (UserInfo) list.get(0);
            return userInfo.getPhone();
        }
        return "";
    }

    /**
     * ���ı��Ϸ���
     * 
     * @param userInfo
     * @param approverId��������ID
     * @param planId�����Ϸ���ID
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
        // ����������ʷ
        ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
        processHistoryBean.setProcessAction("���Ϸ�������");// ������̴�����˵��
        processHistoryBean.setTaskOutCome("approve");// ��ӹ�����������Ϣ
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
