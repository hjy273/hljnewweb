package com.cabletech.linepatrol.dispatchtask.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.commons.util.Hanzi2PinyinUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.workflow.AbstractProcessService;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.dispatchtask.beans.DispatchTaskBean;
import com.cabletech.linepatrol.dispatchtask.dao.DispatchAcceptDeptDao;
import com.cabletech.linepatrol.dispatchtask.dao.DispatchTaskDao;
import com.cabletech.linepatrol.dispatchtask.dao.QueryDispatchTaskDao;
import com.cabletech.linepatrol.dispatchtask.dao.ReplyTaskDao;
import com.cabletech.linepatrol.dispatchtask.module.DispatchAcceptDept;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTask;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;
import com.cabletech.linepatrol.dispatchtask.template.SendTaskTemplate;
import com.cabletech.linepatrol.dispatchtask.workflow.DispatchTaskWorkflowBO;

@Service
@Transactional
public class DispatchTaskBO extends AbstractProcessService<DispatchTask, String> {
    private static Properties config;

    @Resource(name = "dispatchTaskDao")
    private DispatchTaskDao dao;

    @Resource(name = "replyTaskDao")
    private ReplyTaskDao replyDao;

    @Resource(name = "replyApproverDAO")
    private ReplyApproverDAO approverDao;

    @Resource(name = "queryDispatchTaskDao")
    private QueryDispatchTaskDao queryDao;

    @Resource(name = "dispatchAcceptDeptDao")
    private DispatchAcceptDeptDao acceptDeptDao;

    @Resource(name = "dispatchTaskWorkflowBO")
    private DispatchTaskWorkflowBO workflowBo;

    @Resource(name = "smHistoryDAO")
    private SmHistoryDAO historyDAO;

    @Resource(name = "processHistoryBO")
    private ProcessHistoryBO processHistoryBO;

    @Resource(name = "uploadFileService")
    private UploadFileService uploadFile;

    @Override
    protected HibernateDao<DispatchTask, String> getEntityDao() {
        return dao;
    }

    /**
     * �������������������б�ţ�������Ƿ����ظ�
     * 
     * @param deptName
     * @param deptid
     * @return
     */
    public String generateCode(String deptName, String deptid) {
        List<DispatchTask> dispatchTasks = dao.getDispatchTasks4Dept(deptid);
        String code = Hanzi2PinyinUtil.generateCode4Prefix(deptName);
        int lenght = dispatchTasks.size() + 1;
        String number = String.format("%04d", lenght);
        for (DispatchTask task : dispatchTasks) {
            if (task.getSerialnumber().indexOf(number) != -1) {
                number = String.format("%04d", ++lenght);
            }
        }
        logger.info("ҵ��ID��" + code + number);

        return code + number;
    }

    /**
     * �ɷ������ɵ�
     * 
     * @param bean
     * @param userInfo
     * @param files
     */
    public void saveDispatchTask(DispatchTaskBean bean, UserInfo userInfo, List files)
            throws Exception {
        // ���������ɵ���Ϣ
        DispatchTask dispatchTask = new DispatchTask();
        BeanUtil.objectCopy(bean, dispatchTask);
        dispatchTask.setSendtime(new Date());
        dispatchTask.setSenddeptid(userInfo.getDeptID());
        dispatchTask.setSenduserid(userInfo.getUserID());
        dispatchTask.setWorkState(DispatchTaskConstant.NOT_END_STATE);
        String generateId = generateCode(userInfo.getDeptName(), userInfo.getDeptID());
        dispatchTask.setSerialnumber(generateId);
        dao.saveDispatchTask(dispatchTask);
        // ���������ɵ�����������Ϣ
        String dispatchId = dispatchTask.getId();
        String[] acceptDept = bean.getAcceptdeptid().split(",");
        String[] acceptUser = bean.getAcceptuserid().split(",");
        String[] userIds = null;
        if (acceptUser != null) {
            userIds = new String[acceptUser.length];
        }
        String[] seqIds = acceptDeptDao.saveDispatchAcceptDept(dispatchId, acceptDept, acceptUser,
                "");
        uploadFile(userInfo, files, dispatchId);

        // ִ�й�����
        Map variables = new HashMap();
        String processInstanceId;
        ProcessHistoryBean processHistoryBean;
        for (int i = 0; acceptUser != null && i < acceptUser.length; i++) {
            // ��ÿһ�������˷���һ�������ɵ�����
            variables = new HashMap();
            variables.put("assignee", acceptUser[i].split(";")[1]);
            userIds[i] = acceptUser[i].split(";")[1];
            processInstanceId = workflowBo.createProcessInstance(
                    DispatchTaskWorkflowBO.DISPATCH_TASK_WORKFLOW, seqIds[i], variables);
            // processHistoryBO.saveProcessHistory(processInstanceId, null,
            // userIds[i], userInfo.getUserID(), seqIds[i],
            // ModuleCatalog.SENDTASK);
            processHistoryBean = new ProcessHistoryBean();
            processHistoryBean.initial(null, userInfo, processInstanceId, ModuleCatalog.SENDTASK);
            processHistoryBean.setHandledTaskName("start");
            processHistoryBean.setNextOperateUserId(userIds[i]);
            processHistoryBean.setObjectId(seqIds[i]);
            processHistoryBean.setProcessAction("�ɵ�");
            processHistoryBean.setTaskOutCome("dispatch");
            processHistoryBO.saveProcessHistory(processHistoryBean);
        }
        logger.info("�����Ѿ��ɷ���");
        // ִ�������ɵ����ŷ���
        bean.setAcceptDeptSeqs(seqIds);
        bean.setId(dispatchId);

        sendDispatchMessage(bean, userIds);
    }

    /**
     * �޸������ɵ�����
     * 
     * @param bean
     * @param userInfo
     * @param files
     */
    public void updateDispatchTask(DispatchTaskBean bean, UserInfo userInfo, List files)
            throws Exception {
        // ���������ɵ��޸���Ϣ
        String dispatchId = bean.getId();
        DispatchTask dispatchTask = new DispatchTask();
        Date dispatchDate = dispatchTask.getSendtime();
        BeanUtil.objectCopy(bean, dispatchTask);
        dispatchTask.setSendtime(dispatchDate);
        dispatchTask.setSenddeptid(userInfo.getDeptID());
        dispatchTask.setSenduserid(userInfo.getUserID());
        dispatchTask.setWorkState(DispatchTaskConstant.NOT_END_STATE);
        dao.updateDispatchTask(dispatchTask);
        // ���������ɵ��������޸���Ϣ
        acceptDeptDao.deleteDispatchAcceptDept(dispatchId);
        // uploadFile.delById(dispatchId);
        String[] acceptDept = bean.getAcceptdeptid().split(",");
        String[] acceptUser = bean.getAcceptuserid().split(",");
        acceptDeptDao.saveDispatchAcceptDept(dispatchId, acceptDept, acceptUser, "");
        uploadFile(userInfo, files, dispatchId);
    }

    /**
     * ɾ�������ɵ���Ϣ
     * 
     * @param bean
     */
    public void deleteDispatchTask(DispatchTaskBean bean) {
        String dispatchId = bean.getId();
        dao.delete(bean.getId());
        acceptDeptDao.deleteDispatchAcceptDept(dispatchId);
        // uploadFile.delById(dispatchId);
    }

    /**
     * ִ�������ɵ���ȡ��
     * 
     * @param bean
     * @param cancelUserId
     */
    public void cancelDispatchTask(DispatchTaskBean bean, UserInfo userInfo) throws Exception {
        // ���������ɵ���ȡ����Ϣ
        DispatchTask dispatchTask = dao.viewDispatchTask(bean.getId());
        dispatchTask.setWorkState(DispatchTaskConstant.CANCEL_STATE);
        dispatchTask.setCancelTime(new Date());
        dispatchTask.setCancelUserId(userInfo.getUserID());
        dispatchTask.setCancelReason(bean.getCancelReason());
        dao.updateDispatchTask(dispatchTask);
        // ��ȡ�����ɵ�����������Ϣ
        String hql = " from DispatchAcceptDept dept where sendtaskid=? ";
        List acceptDeptList = acceptDeptDao.find(hql, bean.getId());
        DispatchAcceptDept acceptDept = null;
        String processInstanceId = "";
        ProcessHistoryBean processHistoryBean;
        for (int i = 0; acceptDeptList != null && i < acceptDeptList.size(); i++) {
            // ִ�������ɵ������������ɵ����̵�ȡ��
            acceptDept = (DispatchAcceptDept) acceptDeptList.get(i);
            processInstanceId = DispatchTaskWorkflowBO.DISPATCH_TASK_WORKFLOW + "."
                    + acceptDept.getTid();
            workflowBo.endProcessInstance(processInstanceId);
            processHistoryBean = new ProcessHistoryBean();
            processHistoryBean.initial(null, userInfo, processInstanceId, ModuleCatalog.SENDTASK);
            processHistoryBean.setHandledTaskName("any");
            processHistoryBean.setNextOperateUserId("");
            processHistoryBean.setObjectId(acceptDept.getTid());
            processHistoryBean.setProcessAction("�ɵ�ȡ��");
            processHistoryBean.setTaskOutCome("cancel");
            processHistoryBO.saveProcessHistory(processHistoryBean);
        }
        logger.info("�����Ѿ�ȡ����");
    }

    /**
     * ���ݵ�ǰ�û���ѯ���������ɵ���Ϣ
     * 
     * @param taskName
     * @param userInfo
     * @param condition
     * @return
     */
    public List queryForHandleDispatchTask(String taskName, String condition, UserInfo userInfo) {
        String assignee = userInfo.getUserID();
        List list = new ArrayList();
        List handleTaskList = workflowBo.queryForHandleListBean(assignee, condition, taskName);
        String replyCondition = " and r.replyuserid='" + userInfo.getUserID() + "' ";
        List replyList = replyDao.queryForList(replyCondition);
        DynaBean bean;
        DynaBean tmpBean;
        String dispatchId = "";
        for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
            bean = (DynaBean) handleTaskList.get(i);
            if (bean != null) {
                dispatchId = (String) bean.get("id");
                // �жϸ������ɵ��Ƿ���ڷ�����Ϣ
                for (int j = 0; replyList != null && j < replyList.size(); j++) {
                    tmpBean = (DynaBean) replyList.get(j);
                    if (tmpBean != null) {
                        if (bean.get("id").equals(tmpBean.get("id"))) {
                            bean.set("exist_reply", "1");
                            bean.set("reply_id", tmpBean.get("replyid"));
                            break;
                        }
                    }
                }
                if (DispatchTaskWorkflowBO.CHECK_TASK.equals(bean.get("flow_state"))) {
                    if (judgeNotReplyFinishRead(dispatchId, userInfo.getUserID())) {
                        list.add(bean);
                    }
                } else {
                    list.add(bean);
                }
            }
        }
        return list;
    }

    /**
     * ���ݵ�ǰ�û���ѯ�Ѱ������ɵ���Ϣ
     * 
     * @param userInfo
     * @param condition
     * @param taskOutCome
     * @param taskName
     * @return
     */
    public List queryForFinishHandledDispatchTask(String condition, UserInfo userInfo,
            String taskName, String taskOutCome) {
        String assignee = userInfo.getUserID();
        condition += " and process.operate_user_id='" + assignee + "' ";
        List handledDispatchTaskList = dao.queryForFinishHandledList(condition);
        List prevList = new ArrayList();
        List list = new ArrayList();
        DynaBean bean;
        DynaBean tmpBean;
        boolean flag = true;
        for (int i = 0; handledDispatchTaskList != null && i < handledDispatchTaskList.size(); i++) {
            flag = true;
            bean = (DynaBean) handledDispatchTaskList.get(i);
            if (bean != null) {
                for (int j = 0; prevList != null && j < prevList.size(); j++) {
                    tmpBean = (DynaBean) prevList.get(j);
                    if (tmpBean != null && tmpBean.get("id").equals(bean.get("id"))) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    prevList.add(bean);
                }
            }
        }
        if (taskName != null && !"".equals(taskName)) {
            for (int i = 0; prevList != null && i < prevList.size(); i++) {
                tmpBean = (DynaBean) prevList.get(i);
                if (judgeInStr((String) tmpBean.get("handled_task_name"), taskName)) {
                    if (taskOutCome != null && !"".equals(taskOutCome)) {
                        if (judgeInStr((String) tmpBean.get("task_out_come"), taskOutCome)) {
                            list.add(tmpBean);
                        }
                    } else {
                        list.add(tmpBean);
                    }
                }
            }
        } else {
            list = prevList;
        }
        return list;
    }

    /**
     * ���ݵ�ǰ�û���ѯ���������ɵ���Ϣ
     * 
     * @param taskName
     * @param userInfo
     * @param condition
     * @return
     */
    public List queryForHandleDispatchTaskNum(String condition, UserInfo userInfo) {
        String assignee = userInfo.getUserID();
        List list = new ArrayList();
        List handleTaskList = queryForHandleDispatchTask("", condition, userInfo);
        DynaBean bean;
        String dispatchId = "";
        int waitSignInTaskNum = 0;
        int waitTransferSignInTaskNum = 0;
        int waitRefuseConfirmTaskNum = 0;
        int waitReplyTaskNum = 0;
        int waitCheckTaskNum = 0;
        for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
            bean = (DynaBean) handleTaskList.get(i);
            if (bean != null) {
                dispatchId = (String) bean.get("id");
                if (bean.get("flow_state") != null) {
                    // ���������ɵ�״̬�������������
                    if (DispatchTaskWorkflowBO.SIGN_IN_TASK.equals(bean.get("flow_state"))) {
                        waitSignInTaskNum++;
                    }
                    if (DispatchTaskWorkflowBO.TRANSFER_SIGN_IN_TASK.equals(bean.get("flow_state"))) {
                        waitTransferSignInTaskNum++;
                    }
                    if (DispatchTaskWorkflowBO.REFUSE_CONFIRM_TASK.equals(bean.get("flow_state"))) {
                        waitRefuseConfirmTaskNum++;
                    }
                    if (DispatchTaskWorkflowBO.REPLY_TASK.equals(bean.get("flow_state"))) {
                        waitReplyTaskNum++;
                    }
                    if (DispatchTaskWorkflowBO.CHECK_TASK.equals(bean.get("flow_state"))) {
                        if (judgeNotReplyFinishRead(dispatchId, userInfo.getUserID())) {
                            waitCheckTaskNum++;
                        }
                    }
                }
            }
        }
        list.add(waitSignInTaskNum);
        list.add(waitTransferSignInTaskNum);
        list.add(waitRefuseConfirmTaskNum);
        list.add(waitReplyTaskNum);
        list.add(waitCheckTaskNum);
        return list;
    }

    /**
     * ���ݵ�ǰ�û���ѯ�Ѱ������ɵ���Ϣ
     * 
     * @param taskName
     * @param userInfo
     * @param condition
     * @return
     */
    public List queryForHandledDispatchTaskNum(String condition, UserInfo userInfo) {
        String assignee = userInfo.getUserID();
        List list = new ArrayList();
        List handleTaskList = queryForFinishHandledDispatchTask(condition, userInfo, "", "");
        DynaBean bean;
        String dispatchId = "";
        int dispatchedTaskNum = 0;
        int signedInTaskNum = 0;
        int transferedTaskNum = 0;
        int replyedTaskNum = 0;
        int checkedTaskNum = 0;
        int canceledTaskNum = 0;
        int refusedTaskNum = 0;
        int confirmedTaskNum = 0;
        for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
            bean = (DynaBean) handleTaskList.get(i);
            if (bean != null) {
                dispatchId = (String) bean.get("id");
                if (bean.get("handled_task_name") != null) {
                    if (DispatchTaskWorkflowBO.CANCEL_TASK.equals(bean.get("handled_task_name"))) {
                        canceledTaskNum++;
                    }
                    if (DispatchTaskWorkflowBO.START_TASK.equals(bean.get("handled_task_name"))) {
                        dispatchedTaskNum++;
                    }
                    if (DispatchTaskWorkflowBO.SIGN_IN_TASK.equals(bean.get("handled_task_name"))) {
                        if (DispatchTaskWorkflowBO.REFUSE_OUT_COME
                                .equals(bean.get("task_out_come"))) {
                            refusedTaskNum++;
                        }
                        if (DispatchTaskWorkflowBO.SIGN_IN_OUT_COME.equals(bean
                                .get("task_out_come"))) {
                            signedInTaskNum++;
                        }
                        if (DispatchTaskWorkflowBO.TRANSFER_OUT_COME.equals(bean
                                .get("task_out_come"))) {
                            transferedTaskNum++;
                        }
                    }
                    if (DispatchTaskWorkflowBO.TRANSFER_SIGN_IN_TASK.equals(bean
                            .get("handled_task_name"))) {
                        if (DispatchTaskWorkflowBO.REFUSE_OUT_COME
                                .equals(bean.get("task_out_come"))) {
                            refusedTaskNum++;
                        }
                        if (DispatchTaskWorkflowBO.SIGN_IN_OUT_COME.equals(bean
                                .get("task_out_come"))) {
                            signedInTaskNum++;
                        }
                    }
                    if (DispatchTaskWorkflowBO.REFUSE_CONFIRM_TASK.equals(bean
                            .get("handled_task_name"))) {
                        confirmedTaskNum++;
                    }
                    if (DispatchTaskWorkflowBO.REPLY_TASK.equals(bean.get("handled_task_name"))) {
                        replyedTaskNum++;
                    }
                    if (DispatchTaskWorkflowBO.CHECK_TASK.equals(bean.get("handled_task_name"))) {
                        checkedTaskNum++;
                    }
                }
            }
        }
        list.add(dispatchedTaskNum);
        list.add(signedInTaskNum);
        list.add(transferedTaskNum);
        list.add(replyedTaskNum);
        list.add(checkedTaskNum);
        list.add(canceledTaskNum);
        list.add(refusedTaskNum);
        list.add(confirmedTaskNum);
        return list;
    }

    /**
     * ����������ѯ�����ɵ���Ϣ
     * 
     * @param condition
     * @return
     */
    public List queryForDispatchTask(String condition) {
        List tmpList = dao.queryForList(condition);
        List list = new ArrayList();
        DynaBean bean;
        DynaBean tmpBean;
        boolean flag = false;
        for (int i = 0; tmpList != null && i < tmpList.size(); i++) {
            bean = (DynaBean) tmpList.get(i);
            flag = true;
            for (int j = 0; list != null && j < list.size(); j++) {
                tmpBean = (DynaBean) list.get(j);
                if (bean != null && bean.get("id").equals(tmpBean.get("id"))) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                list.add(bean);
            }
        }
        return list;
    }

    /**
     * ִ�л�ȡWAP�����еĴ��칤���б���Ϣ
     * 
     * @param handleTaskList
     * @return
     */
    public List processHandleTaskList(List handleTaskList) {
        // TODO Auto-generated method stub
        List list = handleTaskList;
        handleTaskList = new ArrayList();
        DynaBean bean;
        for (int i = 0; list != null && i < list.size(); i++) {
            bean = (DynaBean) list.get(i);
            if (!"refuse_confirm_task".equals(bean.get("flow_state"))) {
                if (!"check_task".equals(bean.get("flow_state"))) {
                    continue;
                }
            }
            handleTaskList.add(bean);
        }
        return handleTaskList;
    }

    /**
     * ���������Ż�ȡ�����ɵ���ϸ��Ϣ
     * 
     * @param dispatchId
     * @return
     * @throws Exception
     */
    public DispatchTaskBean viewDispatchTask(String dispatchId) throws Exception {
        DispatchTask dispatchTask = dao.viewDispatchTask(dispatchId);
        String condition = " and acceptdept.sendtaskid='" + dispatchId + "' ";
        List acceptDepartList = acceptDeptDao.queryForDepartList(condition);
        List acceptUserList = acceptDeptDao.queryForUserList(condition);
        List list = new ArrayList();
        DynaBean userBean;
        DynaBean tmpBean;
        boolean flag = false;
        for (int i = 0; acceptUserList != null && i < acceptUserList.size(); i++) {
            flag = false;
            userBean = (DynaBean) acceptUserList.get(i);
            if (userBean != null) {
                for (int j = 0; list != null && j < list.size(); j++) {
                    tmpBean = (DynaBean) list.get(j);
                    if (tmpBean != null) {
                        if (tmpBean.get("deptid").equals(userBean.get("deptid"))) {
                            if (tmpBean.get("userid").equals(userBean.get("userid"))) {
                                flag = true;
                                break;
                            }
                        }
                    }
                }
                if (!flag) {
                    list.add(userBean);
                }
            }
        }
        DispatchTaskBean bean = new DispatchTaskBean();
        BeanUtil.objectCopy(dispatchTask, bean);
        String departName = queryDao.getDepartName(bean.getSenddeptid());
        String userName = queryDao.getUserName(bean.getSenduserid());
        String cancelUserName = queryDao.getUserName(bean.getCancelUserId());
        bean.setSenddeptname(departName);
        bean.setSendusername(userName);
        bean.setCancelUserName(cancelUserName);
        bean.setAcceptDeptList(acceptDepartList);
        bean.setAcceptUserList(list);
        return bean;
    }

    /**
     * ���������Ż�ȡ�����ɵ���ϸ��Ϣ
     * 
     * @param dispatchId
     * @return
     * @throws Exception
     */
    public List getDispatchTaskAcceptUsers(String dispatchId, UserInfo userInfo) throws Exception {
        String condition = " and acceptdept.sendtaskid='" + dispatchId + "' ";
        condition += " and acceptdept.sign_in_id is null ";
        // if ("2".equals(userInfo.getDeptype())) {
        // condition += " and acceptdept.deptid='" + userInfo.getDeptID()
        // + "' ";
        // }
        List acceptUserList = acceptDeptDao.queryForUserList(condition);
        return acceptUserList;
    }

    /**
     * 
     * @param list
     * @return
     * @throws Exception
     */
    public SendTaskTemplate exportDispatchTaskResult(List list) throws Exception {
        config = getReportConfig();
        String urlPath = getUrlPath(config, "report.sendtaskresult");
        SendTaskTemplate template = new SendTaskTemplate(urlPath);
        ExcelStyle excelstyle = new ExcelStyle(urlPath);
        template.exportDispatchTaskResult(list, excelstyle);
        if (template != null) {
            logger.info("���excel�ɹ�");
            return template;
        } else {
            return null;
        }
    }

    /**
     * ִ�и���ҵ�����ݱ��ɾ��������ָ���ɵ������ص������ɵ���Ϣ
     * 
     * @param objectId
     */
    @Override
    public void deleteAllData(String objectId) {
        // TODO Auto-generated method stub

    }

    /**
     * ִ�л�ȡָ���ɵ���Ӧ���е�����ʵ������б���Ϣ
     * 
     * @param objectId
     * @return
     */
    @Override
    public List getProcessInstanceIdList(String objectId) {
        // TODO Auto-generated method stub
        return null;
    }

    private boolean judgeInStr(String value, String compareStr) {
        // TODO Auto-generated method stub
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

    /**
     * �������ɵ���ÿһ�����������е�ǰ�û��Ƿ�Ϊ���ĵĳ����˵��ж�
     * 
     * @param dispatchId
     * @param userId
     * @return
     */
    private boolean judgeNotReplyFinishRead(String dispatchId, String userId) {
        ReplyApprover approver;
        boolean flag = false;
        DynaBean tmpBean;
        String replyId;
        String condition = " and d.id='" + dispatchId + "' ";
        List replyList = replyDao.queryForList(condition);
        for (int j = 0; replyList != null && j < replyList.size(); j++) {
            tmpBean = (DynaBean) replyList.get(j);
            replyId = (String) tmpBean.get("replyid");
            if (replyId != null && !replyId.equals("")) {
                List<ReplyApprover> approverList = approverDao.getApprovers(replyId,
                        DispatchTaskConstant.LP_SENDTASKREPLY);
                for (int i = 0; approverList != null && i < approverList.size(); i++) {
                    approver = approverList.get(i);
                    if (approver != null && userId.equals(approver.getApproverId())) {
                        if (CommonConstant.APPROVE_MAN.equals(approver.getApproverType())) {
                            flag = true;
                        } else if (CommonConstant.APPROVE_TRANSFER_MAN.equals(approver
                                .getApproverType())) {
                            flag = true;
                        } else if (CommonConstant.APPROVE_READ.equals(approver.getApproverType())) {
                            if (CommonConstant.FINISH_READED.equals(approver.getFinishReaded())) {
                                flag = false;
                            } else {
                                flag = true;
                            }
                        } else {
                            flag = true;
                        }
                    }
                }
            }
        }
        return flag;
    }

    /**
     * ���������ɵ�����֪ͨ
     * 
     * @param bean
     * @param userId
     */
    private void sendDispatchMessage(DispatchTaskBean bean, String[] userId) {
        // ���Ͷ���
        String content = "�������ɵ�������һ������Ϊ��" + bean.getSendtopic() + "���Ĺ����ȴ�����ǩ�գ�";
        String sim = "";
        String mobiles = "";
        for (int i = 0; i < userId.length; i++) {
            sim = queryDao.getSendPhone(userId[i]);
            mobiles = mobiles + sim + ",";
            logger.info("�ɵ��Ķ�������:" + content);
            // if (sim != null && !sim.equals("")) {
            // smSendProxy.simpleSend(sim, content, null, null, true);
            // }
            List<String> mobileList = com.cabletech.commons.util.StringUtils.string2List(sim, ",");
            try {
                super.sendMessage(content, mobileList);
            } catch (Exception ex) {
                logger.error("���Ͷ��ų���", ex);
            }
        }
        // ������ż�¼
        SMHistory history = new SMHistory();
        history.setSimIds(mobiles);
        history.setSendContent(content);
        history.setSendTime(new Date());
        // history.setSendState(sendState);
        history.setSmType(DispatchTaskConstant.LP_SENDTASK);
        history.setObjectId(bean.getId());
        history.setBusinessModule("sendtask");
        historyDAO.save(history);
    }

    /**
     * ���������ɵ������ϴ�����
     * 
     * @param bean
     * @param userInfo
     * @param files
     * @param dispatchId
     */
    private void uploadFile(UserInfo userInfo, List files, String dispatchId) {
        uploadFile.saveFiles(files, ModuleCatalog.SENDTASK, userInfo.getRegionName(), dispatchId,
                DispatchTaskConstant.LP_SENDTASK, userInfo.getUserID());
    }

}