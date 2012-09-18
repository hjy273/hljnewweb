package com.cabletech.linepatrol.safeguard.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
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
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.safeguard.beans.SafeguardTaskBean;
import com.cabletech.linepatrol.safeguard.dao.SafeguardConDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardPlanDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSpplanDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardTaskDao;
import com.cabletech.linepatrol.safeguard.dao.SpecialEndPlanDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardCon;
import com.cabletech.linepatrol.safeguard.module.SafeguardConstant;
import com.cabletech.linepatrol.safeguard.module.SafeguardPlan;
import com.cabletech.linepatrol.safeguard.module.SafeguardSpplan;
import com.cabletech.linepatrol.safeguard.module.SafeguardTask;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardReplanSubWorkflowBO;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardSubWorkflowBO;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardWorkflowBO;
import com.cabletech.linepatrol.specialplan.dao.SpecialPlanDao;

@Service
@Transactional
public class SafeguardTaskBo extends EntityManager<SafeguardTask, String> {

    private static Logger logger = Logger.getLogger(SafeguardTaskBo.class.getName());

    @Resource(name = "userInfoDao")
    private UserInfoDAOImpl userInfoDao;

    @Autowired
    private SafeguardWorkflowBO workflowBo;

    @Resource(name = "replyApproverDAO")
    private ReplyApproverDAO approverDAO;

    @Resource(name = "processHistoryBO")
    private ProcessHistoryBO processHistoryBO;

    @Override
    protected HibernateDao<SafeguardTask, String> getEntityDao() {
        return safeguardTaskDao;
    }

    @Resource(name = "safeguardTaskDao")
    private SafeguardTaskDao safeguardTaskDao;

    @Resource(name = "safeguardPlanDao")
    private SafeguardPlanDao safeguardPlanDao;

    @Resource(name = "specialEndPlanDao")
    private SpecialEndPlanDao specialEndPlanDao;

    @Resource(name = "safeguardSpplanDao")
    private SafeguardSpplanDao safeguardSpplanDao;

    @Resource(name = "specialPlanDao")
    private SpecialPlanDao specialPlanDao;

    @Resource(name = "safeguardConDao")
    private SafeguardConDao safeguardConDao;

    @Resource(name = "uploadFileService")
    private UploadFileService uploadFile;

    @Resource(name = "approveDAO")
    private ApproveDAO approveDao;

    @Resource(name = "smHistoryDAO")
    private SmHistoryDAO historyDAO;

    /**
     * ��ñ���������
     * 
     * @param deptId������ID
     * @return���ò��Ÿ���ı���������
     */
    public Integer getSafeguardTaskNumber(String deptId) {
        return safeguardTaskDao.getSafeguardTaskNumber(deptId);
    }

    /**
     * ���汣������
     * 
     * @param safeguardTaskBean����������ʵ��Bean
     * @param userInfo
     * @throws ServiceException
     */
    public void addSafeguardTask(SafeguardTaskBean safeguardTaskBean, UserInfo userInfo,
            List<FileItem> files) throws ServiceException {
        String contractorId = safeguardTaskBean.getContractorId();
        String userId = safeguardTaskBean.getUserId();
        String mobileId = safeguardTaskBean.getMobileId();
        String conUser = safeguardTaskBean.getConUser();

        // ���汣�Ϸ���
        SafeguardTask safeguardTaskSave = saveSafeguardTask("save", safeguardTaskBean, userInfo);
        String taskId = safeguardTaskSave.getId();

        // ���汣�Ϸ������ά��ϵ
        saveSafeguardTaskCon(contractorId, taskId, SafeguardCon.ADDPLAN);

        // �ϴ��ļ�
        saveFiles(files, ModuleCatalog.SAFEGUARD, userInfo.getRegionName(), safeguardTaskSave
                .getId(), SafeguardConstant.LP_SAFEGUARD_TASK, userInfo.getUserID());
        // ��ô�ά��λ�û�ID
        String[] cons = contractorId.split(",");
        for (int i = 0; i < cons.length; i++) {
            String conId = cons[i].split(";")[0];
            String user = null;
            if (conUser == null || "".equals(conUser)) {
                user = safeguardConDao.getUserIdByConId(conId);
            } else {
                user = safeguardConDao.getUserStrByConId(conId, conUser);
            }
            String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, conId);
            // JBPM
            Map variables = new HashMap();
            variables.put("assignee", user);
            variables.put("transition", "start");
            workflowBo.createProcessInstance("safeguard", eid, variables);

            // ����������ʷ
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            processHistoryBean.setProcessAction("���������ɷ�");// ������̴�����˵��
            processHistoryBean.setTaskOutCome("start");// ��ӹ�����������Ϣ
            processHistoryBean.initial(null, userInfo, "safeguard." + eid, ModuleCatalog.SAFEGUARD);
            processHistoryBean.setNextOperateUserId(user);// �����һ�������˵ı��
            processHistoryBean.setObjectId(eid);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
            try {
                processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException();
            }
        }
        // ��ô�ά��λID
        String[] mobileIds = mobileId.split(",");
        for (int i = 0; i < mobileIds.length; i++) {
            String content = "�����ϡ�����һ������Ϊ\"" + safeguardTaskSave.getSafeguardName()
                    + "\"�������񵥣�������ʱ����";
            sendMessage(content, mobileIds[i]);
            saveMessage(content, mobileIds[i], taskId, SafeguardConstant.LP_SAFEGUARD_TASK,
                    ModuleCatalog.SAFEGUARD);
        }
    }

    /**
     * ����������ʱ����
     * 
     * @param safeguardTaskBean
     * @param userInfo
     * @param files��������Ϣ
     * @throws ServiceException
     */
    public void tempSaveSafeguardTask(SafeguardTaskBean safeguardTaskBean, UserInfo userInfo,
            List<FileItem> files) throws ServiceException {
        String contractorId = safeguardTaskBean.getContractorId();

        // ���汣�Ϸ���
        SafeguardTask safeguardTaskSave = saveSafeguardTask("tempSave", safeguardTaskBean, userInfo);
        String taskId = safeguardTaskSave.getId();

        // ���汣�Ϸ������ά��ϵ
        saveSafeguardTaskCon(contractorId, taskId, SafeguardCon.ADDPLAN);

        // �ϴ��ļ�
        saveFiles(files, ModuleCatalog.SAFEGUARD, userInfo.getRegionName(), safeguardTaskSave
                .getId(), SafeguardConstant.LP_SAFEGUARD_TASK, userInfo.getUserID());
    }

    /**
     * ��ñ������������б�
     * 
     * @param creator���������񴴽���
     * @return
     */
    public List getPerfectList(String creator) {
        return safeguardTaskDao.getPerfectList(creator);
    }

    /**
     * ���Ʊ�������ǰ����������Ϣ
     * 
     * @param taskId����������ID
     * @return
     */
    public Map perfectSafeguardTaskForm(String taskId) {
        SafeguardTask safeguardTask = safeguardTaskDao.findByUnique("id", taskId);
        String[] userIds = safeguardConDao.getConUserIdsByTaskId(taskId);
        Map map = new HashMap();
        map.put("safeguardTask", safeguardTask);
        map.put("userIds", userIds);
        return map;
    }

    /**
     * ɾ����ʱ��������
     * 
     * @param taskId����������ID
     * @throws ServiceException
     */
    public void deleteTempTask(String taskId) throws ServiceException {
        // ɾ��������Ϣ
        List affixIds = safeguardTaskDao.getAffixIdByTaskId(taskId);
        for (Iterator iterator = affixIds.iterator(); iterator.hasNext();) {
            String affixId = (String) iterator.next();
            uploadFile.delById(affixId);
        }
        safeguardConDao.delteTaskConByTaskId(taskId);
        safeguardTaskDao.delete(taskId);
    }

    /**
     * �鿴��������
     * 
     * @param taskId����������ID
     * @return����������ʵ��
     */
    public Map viewSafeguardTask(String taskId, String contractorId) {
        SafeguardTask safeguardTask = safeguardTaskDao.findByUnique("id", taskId);
        String cancelUserName = userInfoDao.getUserName(safeguardTask.getCancelUserId());
        safeguardTask.setCancelUserName(cancelUserName);
        String conId = safeguardConDao.getConIdByTaskIdAndConId(taskId, contractorId);
        Map map = new HashMap();
        map.put("safeguardTask", safeguardTask);
        map.put("conId", conId);
        return map;
    }

    /**
     * ���汣������ʵ��
     * 
     * @param safeguardTaskBean����������ʵ��Bean
     * @param userInfo
     * @return
     */
    private SafeguardTask saveSafeguardTask(String flag, SafeguardTaskBean safeguardTaskBean,
            UserInfo userInfo) {
        SafeguardTask safeguardTask = new SafeguardTask();
        try {
            BeanUtil.objectCopy(safeguardTaskBean, safeguardTask);
        } catch (Exception e) {
            logger.error("SafeguardTaskBeanת��ΪSafeguardTask����������Ϣ��" + e.getMessage());
        }
        if ("tempSave".equals(flag)) {
            safeguardTask.setSaveFlag("1");
        } else {
            safeguardTask.setSaveFlag("0");
        }
        safeguardTask.setSendDate(new Date());
        safeguardTask.setSender(userInfo.getUserID());
        safeguardTask.setRegionId(userInfo.getRegionID());
        return safeguardTaskDao.addSafeguardTask(safeguardTask);
    }

    /**
     * ���汣���������ά��ϵ
     * 
     * @param contractorId����άID
     * @param taskId����������ID
     * @param state��״̬
     */
    private void saveSafeguardTaskCon(String contractorId, String taskId, String state) {

        if (contractorId != null && !"".equals(contractorId)) {
            safeguardConDao.delteTaskConByTaskId(taskId);
            String[] conId = contractorId.split(",");
            for (int i = 0; i < conId.length; i++) {
                SafeguardCon safeguardCon = new SafeguardCon();
                safeguardCon.setSafeguardId(taskId);
                safeguardCon.setContractorId(conId[i]);
                safeguardCon.setTransactState(state);
                safeguardConDao.save(safeguardCon);
            }
        }
    }

    /**
     * ��ô�������
     * 
     * @param taskName
     * @param userInfo���û�ID
     * @param condition���ж�����
     * @return�����������б�
     */
    public List getAgentList(UserInfo userInfo, String condition, String taskName) {
        String deptType = userInfo.getDeptype();
        String assignee = userInfo.getUserID();
        List list = workflowBo.queryForHandleListBean(assignee, condition, taskName);
        List list2 = new ArrayList();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                boolean canSummaryFlag = false;
                BasicDynaBean bean = (BasicDynaBean) list.get(i);
                // String state = (String) bean.get("transact_state");
                String workFlow = (String) bean.get("flow_state");
                // ����������guard_plan_execute_state״̬ʱ�����ܻ���������̺������̵������̣�Ҫ���ֶԴ�
                if ("guard_plan_execute_state".equals(workFlow)) {
                    String planId = (String) bean.get("plan_id");
                    for (int j = 0; j < list.size(); j++) {
                        BasicDynaBean bean2 = (BasicDynaBean) list.get(j);
                        String workFlow2 = (String) bean2.get("flow_state");
                        if (planId.equals((String) bean2.get("plan_id"))) {
                            // �������̵�������Ϊremake_guard_plan_taskʱ������ѭ�����������
                            if ("remake_guard_plan_task".equals(workFlow2)) {
                                canSummaryFlag = true;
                                break;
                            }
                        }
                    }
                    if (canSummaryFlag) {
                        canSummaryFlag = false;
                        continue;
                    }
                    // �ж��������Ƿ�������
                    String flag = specialEndPlanDao.whetherCanSummary(planId);// �ж��Ƿ�����ƶ������ܽ�
                    Date spEndDate = getMaxDateByPlanId(planId);// ��ñ�����Ѳ�ƻ����������ֹʱ��
                    if ("yes".equals(flag)) {
                        // ��ʱ�����ƶ������ܽ�
                        continue;
                    } else {
                        // ��ʱ����ָ�������ܽ�
                        if (spEndDate.after(new Date())) {
                            // ʱ��Ϊ��
                            bean.set("page_flag", "view");
                            bean.set("transact_state", "����ִ����");
                        } else {
                            bean.set("page_flag", "operator");
                        }
                    }
                } else if ("change_guard_plan_approve_task".equals(workFlow)) {
                    bean.set("transact_state", "��ֹ�������");
                } else if ("wait_replan_task".equals(workFlow)) {
                    bean.set("transact_state", "�����ƶ��ƻ�");
                } else if ("remake_guard_plan_approve_task".equals(workFlow)) {
                    bean.set("transact_state", "��Ѳ�ƻ����");
                } else if ("remake_guard_plan_task".equals(workFlow)) {
                    bean.set("transact_state", "�޸���Ѳ�ƻ�");
                }

                // ����ά�ύ������ֹ�󣬸ñ��Ϸ��������ڴ�ά��������ʾ
                if ("��δ�����ֹ����".equals((String) bean.get("transact_state"))) {
                    continue;
                }
                if ("��ֹ�������".equals((String) bean.get("transact_state"))) {
                    boolean read = approverDAO.isReadOnly((String) bean.get("sp_end_id"), assignee,
                            SafeguardConstant.LP_SPECIAL_ENDPLAN);
                    bean.set("isread", "" + read);
                    if (read) {
                        if (judgeFinishRead((String) bean.get("sp_end_id"),
                                SafeguardConstant.LP_SPECIAL_ENDPLAN, assignee)) {
                            list2.add(bean);
                        }
                    } else {
                        list2.add(bean);
                    }
                    continue;
                }

                Object planId = bean.get("plan_id");
                Object summaryId = bean.get("summary_id");
                if (summaryId != null) {
                    boolean read = approverDAO.isReadOnly((String) summaryId, assignee,
                            SafeguardConstant.LP_SAFEGUARD_SUMMARY);
                    bean.set("isread", "" + read);
                    if (read) {
                        if (judgeFinishRead((String) summaryId,
                                SafeguardConstant.LP_SAFEGUARD_SUMMARY, assignee)) {
                            list2.add(bean);
                        }
                    } else {
                        list2.add(bean);
                    }
                    continue;
                }
                if (planId != null) {
                    if ("remake_guard_plan_approve_task".equals(workFlow)) {
                        boolean read = approverDAO.isReadOnly((String) bean.get("sp_id"), assignee,
                                SafeguardConstant.LP_SPECIAL_PLAN);
                        bean.set("isread", "" + read);
                        if (read) {
                            if (judgeFinishRead((String) bean.get("sp_id"),
                                    SafeguardConstant.LP_SPECIAL_PLAN, assignee)) {
                                list2.add(bean);
                            }
                        } else {
                            list2.add(bean);
                        }
                        continue;
                    } else {
                        boolean read = approverDAO.isReadOnly((String) planId, assignee,
                                SafeguardConstant.LP_SAFEGUARD_PLAN);
                        bean.set("isread", "" + read);
                        if (read) {
                            if (judgeFinishRead((String) planId,
                                    SafeguardConstant.LP_SAFEGUARD_PLAN, assignee)) {
                                list2.add(bean);
                            }
                        } else {
                            list2.add(bean);
                        }
                        continue;
                    }
                }
                list2.add(bean);
            }
        }
        return list2;
    }

    /**
     * �ж��Ƿ��Ѳ���
     * 
     * @param objectId
     * @param objectType
     * @param userId
     * @return
     */
    private boolean judgeFinishRead(String objectId, String objectType, String userId) {
        ReplyApprover approver;
        boolean flag = true;
        List<ReplyApprover> approverList = approverDAO.getApprovers(objectId, objectType);
        for (int i = 0; approverList != null && i < approverList.size(); i++) {
            approver = approverList.get(i);
            if (approver != null && userId.equals(approver.getApproverId())) {
                if (CommonConstant.FINISH_READED.equals(approver.getFinishReaded())) {
                    flag = false;
                } else {
                    flag = true;
                }
            }
        }
        // logger.info("*******flag:::" + flag + " objectId::" + objectId + "
        // objectType::" + objectType);
        return flag;
    }

    public Date getMaxDateByPlanId(String planId) {
        SafeguardPlan safeguardPlan = safeguardPlanDao.findByUnique("id", planId);
        String taskId = safeguardPlan.getSafeguardId();
        SafeguardTask safeguardTask = safeguardTaskDao.findByUnique("id", taskId);
        List list = safeguardSpplanDao.findByProperty("planId", planId);
        Date lastDate = new Date(0);
        if (list != null && list.size() > 0) {
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                SafeguardSpplan safeguardSpplan = (SafeguardSpplan) iterator.next();
                String spId = safeguardSpplan.getSpplanId();
                Date endDate = specialPlanDao.getEndDateBySpId(spId);
                logger.info("lastDate:" + lastDate.toString() + " endDate:" + endDate.toString());
                if (lastDate.before(endDate)) {
                    lastDate = endDate;
                }
            }
        } else {
            lastDate = safeguardTask.getEndDate();
        }
        return lastDate;
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
        List list = safeguardTaskDao.getHibernateTemplate().find(hql, userId);
        if (list != null && !list.equals("")) {
            UserInfo userInfo = (UserInfo) list.get(0);
            return userInfo.getPhone();
        }
        return "";
    }

    public List queryForHandleSafeGuardNum(String condition, UserInfo userInfo) {
        String assignee = userInfo.getUserID();
        List list = new ArrayList();
        List handleTaskList = workflowBo.queryForHandleListBean(assignee, condition, "");
        DynaBean bean;
        int waitCreateGuardProjTaskNum = 0;
        int waitApproveGuardProjTaskNum = 0;
        int waitGuardPlanExecuteTaskNum = 0;
        int waitChangeSafeguardPlan = 0;
        int waitChangeSafeguardPlanApprove = 0;
        int waitCreateGuardSummaryTaskNum = 0;
        int waitApproveGuardSummaryTaskNum = 0;
        int waitEvaluateTaskNum = 0;
        for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
            bean = (DynaBean) handleTaskList.get(i);
            if (bean != null) {
                if (bean.get("flow_state") != null) {
                    if (SafeguardWorkflowBO.CREATE_GUARD_PROJ_TASK.equals(bean.get("flow_state"))) {
                        waitCreateGuardProjTaskNum++;
                    }
                    if (SafeguardWorkflowBO.APPROVE_GUARD_PROJ_TASK.equals(bean.get("flow_state"))) {
                        boolean read = approverDAO.isReadOnly((String) bean.get("plan_id"),
                                assignee, SafeguardConstant.LP_SAFEGUARD_PLAN);
                        if (read) {
                            if (judgeFinishRead((String) bean.get("plan_id"),
                                    SafeguardConstant.LP_SAFEGUARD_PLAN, assignee)) {
                                waitApproveGuardProjTaskNum++;
                            }
                        } else {
                            waitApproveGuardProjTaskNum++;
                        }
                    }
                    if (SafeguardSubWorkflowBO.WAIT_REPLAN_TASK.equals(bean.get("flow_state"))
                            || SafeguardReplanSubWorkflowBO.REMAKE_GUARD_PLAN_TASK.equals(bean
                                    .get("flow_state"))) {
                        waitGuardPlanExecuteTaskNum++;
                    }
                    if (SafeguardWorkflowBO.GUARD_PLAN_EXECUTE_STATE.equals(bean.get("flow_state"))) {
                        String planId = (String) bean.get("plan_id");
                        String flag = specialEndPlanDao.whetherCanSummary(planId);
                        Date spEndDate = getMaxDateByPlanId(planId);
                        if (userInfo.getDeptype().equals("2")) {
                            if ("yes".equals(flag)) {
                                continue;
                            }
                        }
                        if (spEndDate.after(new Date())) {
                            waitGuardPlanExecuteTaskNum++;
                        } else {
                            waitCreateGuardSummaryTaskNum++;
                        }
                    }
                    if (SafeguardWorkflowBO.CREATE_GUARD_SUMMARY_TASK
                            .equals(bean.get("flow_state"))) {
                        waitCreateGuardSummaryTaskNum++;
                    }
                    if (SafeguardSubWorkflowBO.CHANGE_GUARD_PLAN_APPROVE_TASK.equals(bean
                            .get("flow_state"))
                            || SafeguardReplanSubWorkflowBO.REMAKE_GUARD_PLAN_APPROVE_TASK
                                    .equals(bean.get("flow_state"))) {
                        waitChangeSafeguardPlanApprove++;
                    }
                    if (SafeguardWorkflowBO.APPROVE_GUARD_SUMMARY_TASK.equals(bean
                            .get("flow_state"))) {
                        boolean read = approverDAO.isReadOnly((String) bean.get("summary_id"),
                                assignee, SafeguardConstant.LP_SAFEGUARD_SUMMARY);
                        if (read) {
                            if (judgeFinishRead((String) bean.get("summary_id"),
                                    SafeguardConstant.LP_SAFEGUARD_SUMMARY, assignee)) {
                                waitApproveGuardSummaryTaskNum++;
                            }
                        } else {
                            waitApproveGuardSummaryTaskNum++;
                        }
                    }
                    if (SafeguardWorkflowBO.EVALUATE_TASK.equals(bean.get("flow_state"))) {
                        waitEvaluateTaskNum++;
                    }
                }
            }
        }
        list.add(waitCreateGuardProjTaskNum);
        list.add(waitApproveGuardProjTaskNum);
        list.add(waitGuardPlanExecuteTaskNum);
        list.add(waitCreateGuardSummaryTaskNum);
        list.add(waitApproveGuardSummaryTaskNum);
        list.add(waitEvaluateTaskNum);
        list.add(waitChangeSafeguardPlanApprove);
        return list;
    }

    // public List queryForHandleDrillTaskNum(String condition, UserInfo
    // userInfo) {
    // // TODO Auto-generated method stub
    // String assignee = "";
    // String userId = userInfo.getUserID();
    // assignee = userId;
    // List list=new ArrayList();
    // List handleTaskList = workflowBo.queryForHandleListBean(assignee,
    // condition, "");
    // DynaBean bean;
    // int waitCreateDrillTaskNum=0;
    // int waitApproveDrillTaskNum=0;
    // int waitChangeDrillTaskNum=0;
    // int waitApproveChangeDrillTaskNum=0;
    // int waitCreateDrillSummaryTaskNum=0;
    // int waitApproveDrillSummaryTaskNum=0;
    // int waitEvaluateTaskNum=0;
    // for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++)
    // {
    // bean = (DynaBean) handleTaskList.get(i);
    // if (bean != null) {
    // if (bean.get("flow_state") != null) {
    // if(DrillWorkflowBO.CREATE_DRILL_PROJ_TASK.equals(bean.get("flow_state"))){
    // waitCreateDrillTaskNum++;
    // }
    // if(DrillWorkflowBO.APPROVE_DRILL_PROJ_TASK.equals(bean.get("flow_state"))){
    // if(judgeFinishRead((String)bean.get("plan_id"),
    // DrillConstant.LP_DRILLPLAN, userId)){
    // waitApproveDrillTaskNum++;
    // }
    // }
    // if(DrillWorkflowBO.CHANGE_DRILL_PROJ_TASK.equals(bean.get("flow_state"))){
    // waitChangeDrillTaskNum++;
    // }
    // if(DrillWorkflowBO.APPROVE_CHANGE_DRILL_PROJ_TASK.equals(bean.get("flow_state"))){
    // //if(judgeFinishRead((String)bean.get("plan_id"),
    // DrillConstant.LP_DRILLPLAN, userId)){
    // waitApproveChangeDrillTaskNum++;
    // //}
    // }
    // if(DrillWorkflowBO.CREATE_DRILL_SUMMARY_TASK.equals(bean.get("flow_state"))){
    // waitCreateDrillSummaryTaskNum++;
    // }
    // if(DrillWorkflowBO.APPROVE_DRILL_SUMMARY_TASK.equals(bean.get("flow_state"))){
    // if(judgeFinishRead((String)bean.get("summary_id"),
    // DrillConstant.LP_DRILLSUMMARY, userId)){
    // waitApproveDrillSummaryTaskNum++;
    // }
    // }
    // if(DrillWorkflowBO.EVALUATE_TASK.equals(bean.get("flow_state"))){
    // waitEvaluateTaskNum++;
    // }
    // }
    // }
    // }
    // list.add(waitCreateDrillTaskNum);
    // list.add(waitApproveDrillTaskNum);
    // list.add(waitChangeDrillTaskNum);
    // list.add(waitApproveChangeDrillTaskNum);
    // list.add(waitCreateDrillSummaryTaskNum);
    // list.add(waitApproveDrillSummaryTaskNum);
    // list.add(waitEvaluateTaskNum);
    // return list;
    // }
    /**
     * ��ѯ��ǰ���Ѱ칤��
     */
    public List queryFinishHandledSafeguardList(UserInfo userInfo, String taskName,
            String taskOutCome) {
        String condition = "";
        // ConditionGenerate.getUserQueryCondition(userInfo);
        if ("2".equals(userInfo.getDeptype())) {
            condition = " and process.operate_user_id in (select userid from userinfo where deptid='"
                    + userInfo.getDeptID() + "')";
        }
        condition = " and process.operate_user_id='" + userInfo.getUserID() + "' ";
        return safeguardTaskDao.queryFinishHandledSafeguardList(condition);

    }

    private boolean judgeInStr(String value, String compareStr) {
        boolean flag = false;
        if (compareStr != null) {
            if (compareStr.indexOf(",") != -1) {
                String[] str = compareStr.split(",");
                for (int i = 0; str != null && i < str.length; i++) {
                    if (str[i] != null && str[i].equals(value)) {
                        flag = true;
                        break;
                    }
                }
            } else {
                if (compareStr.equals(value)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public List queryForHandledSafeguardNumList(String condition, UserInfo userInfo) {
        String assignee = userInfo.getUserID();
        List list = new ArrayList();
        List handleTaskList = queryFinishHandledSafeguardList(userInfo, "", "");
        DynaBean bean;
        int createGuardProjNum = 0;
        int approveGuardProjNum = 0;
        int guardPlanExecuteNum = 0;
        int createGuardSummaryNum = 0;
        int approveGuardSummaryNum = 0;
        int evaluteTaskNum = 0;
        for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
            bean = (DynaBean) handleTaskList.get(i);
            if (bean != null) {
                if (bean.get("handled_task_name") != null) {
                    // ���������ɵ�״̬�������������
                    if (SafeguardWorkflowBO.CREATE_GUARD_PROJ_TASK.equals(bean
                            .get("handled_task_name"))) {
                        createGuardProjNum++;
                    }
                    if (SafeguardWorkflowBO.APPROVE_GUARD_PROJ_TASK.equals(bean
                            .get("handled_task_name"))) {
                        approveGuardProjNum++;
                    }
                    if (SafeguardWorkflowBO.GUARD_PLAN_EXECUTE_STATE.equals(bean
                            .get("handled_task_name"))) {
                        guardPlanExecuteNum++;
                    }
                    if (SafeguardWorkflowBO.CREATE_GUARD_SUMMARY_TASK.equals(bean
                            .get("handled_task_name"))) {
                        createGuardSummaryNum++;
                    }
                    if (SafeguardWorkflowBO.APPROVE_GUARD_SUMMARY_TASK.equals(bean
                            .get("handled_task_name"))) {
                        approveGuardSummaryNum++;
                    }
                    if (SafeguardWorkflowBO.EVALUATE_TASK.equals(bean.get("handled_task_name"))) {
                        evaluteTaskNum++;
                    }
                }
            }
        }
        list.add(createGuardProjNum);
        list.add(approveGuardProjNum);
        list.add(guardPlanExecuteNum);
        list.add(createGuardSummaryNum);
        list.add(approveGuardSummaryNum);
        list.add(evaluteTaskNum);
        return list;
    }

    public void cancelSafeguardTask(SafeguardTaskBean bean, UserInfo userInfo) {
        // TODO Auto-generated method stub
        SafeguardTask safeguardTask = safeguardTaskDao.findByUnique("id", bean.getId());
        safeguardTask.setCancelReason(bean.getCancelReason());
        safeguardTask.setCancelTime(new Date());
        safeguardTask.setCancelUserId(userInfo.getUserID());
        safeguardTaskDao.save(safeguardTask);
        List<SafeguardCon> safeguardCons = safeguardConDao.getObjectByTaskId(safeguardTask.getId());

        String processInstanceId = "";
        ProcessHistoryBean processHistoryBean;
        for (SafeguardCon sc : safeguardCons) {
            sc.setTransactState(SafeguardTask.CANCELED_STATE);
            safeguardConDao.save(sc);
            processInstanceId = SafeguardWorkflowBO.SAFEGUARD_WORKFLOW + "." + sc.getId();
            processHistoryBean = new ProcessHistoryBean();
            processHistoryBean.initial(null, userInfo, processInstanceId, ModuleCatalog.SAFEGUARD);
            processHistoryBean.setHandledTaskName("any");
            processHistoryBean.setNextOperateUserId("");
            processHistoryBean.setObjectId(sc.getId());
            processHistoryBean.setProcessAction("����ȡ��");
            processHistoryBean.setTaskOutCome("cancel");
            processHistoryBO.saveProcessHistory(processHistoryBean);
            workflowBo.endProcessInstance(processInstanceId);

        }
    }
}
