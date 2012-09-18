package com.cabletech.linepatrol.dispatchtask.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.dispatchtask.beans.SignInTaskBean;
import com.cabletech.linepatrol.dispatchtask.dao.DispatchAcceptDeptDao;
import com.cabletech.linepatrol.dispatchtask.dao.DispatchTaskDao;
import com.cabletech.linepatrol.dispatchtask.dao.QueryDispatchTaskDao;
import com.cabletech.linepatrol.dispatchtask.dao.RefuseConfirmTaskDao;
import com.cabletech.linepatrol.dispatchtask.dao.SignInTaskDao;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTask;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;
import com.cabletech.linepatrol.dispatchtask.module.RefuseConfirmTask;
import com.cabletech.linepatrol.dispatchtask.module.SignInTask;
import com.cabletech.linepatrol.dispatchtask.workflow.DispatchTaskWorkflowBO;

@Service
@Transactional
public class SignInTaskBO extends EntityManager<SignInTask, String> {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource(name = "signInTaskDao")
    private SignInTaskDao dao;

    @Resource(name = "refuseConfirmTaskDao")
    private RefuseConfirmTaskDao refuseDao;

    @Resource(name = "dispatchTaskDao")
    private DispatchTaskDao dispatchTaskDao;

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
    protected HibernateDao<SignInTask, String> getEntityDao() {
        return dao;
    }

    /**
     * ִ�������ɵ���ǩ�ա���ǩ�Լ�ת��
     * 
     * @param bean
     * @param userInfo
     * @param files
     */
    public void signInTask(SignInTaskBean bean, UserInfo userInfo, List files) throws Exception {
        // ���������ɵ���ǩ����Ϣ
        String dispatchId = bean.getSendtaskid();
        SignInTask signIn = new SignInTask();
        BeanUtil.objectCopy(bean, signIn);
        signIn.setDeptid(userInfo.getDeptID());
        signIn.setUserid(userInfo.getUserID());
        signIn.setTime(new Date());
        if (DispatchTaskConstant.IS_TRANSFER.equals(bean.getIsTransfer())) {
            signIn.setTransferuserid(userInfo.getUserID());
        }
        dao.saveSignInTask(signIn);
        String signInId = signIn.getId();
        bean.setId(signInId);
        // uploadFile(userInfo, files, signInId);

        // ���������ɵ���ת������Ϣ
        String[] seqIds = null;
        if (DispatchTaskConstant.IS_TRANSFER.equals(bean.getIsTransfer())) {
            String[] acceptDept = bean.getAcceptdeptid().split(",");
            String[] acceptUser = bean.getAcceptuserid().split(",");
            seqIds = acceptDeptDao.saveDispatchAcceptDept(dispatchId, acceptDept, acceptUser,
                    signInId);
        }

        // ִ�й�����
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), bean.getSendacceptdeptid(),
                DispatchTaskWorkflowBO.SIGN_IN_TASK);
        if (task == null) {
            task = workflowBo.getHandleTaskForId(userInfo.getUserID(), bean.getSendacceptdeptid(),
                    DispatchTaskWorkflowBO.TRANSFER_SIGN_IN_TASK);
        }
        if (task != null) {
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            if (DispatchTaskConstant.REFUSE_ACTION.equals(bean.getResult())) {
                // ִ�������ɵ���ת�ɾ�ǩ
                if (DispatchTaskWorkflowBO.TRANSFER_SIGN_IN_TASK.equals(task.getName())) {
                    String transferUserId = dao.getTransferUserId(dispatchId, userInfo.getDeptID());
                    nextOperateUserId = transferUserId;
                    workflowBo.setVariables(task, "assignee", transferUserId);
                    workflowBo.completeTask(task.getId(), "reject");
                    logger.info("ת�ɹ����Ѿ���ǩ��");
                    bean.setSignInState(task.getName());
                    processHistoryBean.setProcessAction("�ɵ�ת�ɾ�ǩ");
                }
                // ִ�������ɵ���ֱ�Ӿ�ǩ
                if (DispatchTaskWorkflowBO.SIGN_IN_TASK.equals(task.getName())) {
                    DispatchTask dispatchTask = dispatchTaskDao.viewDispatchTask(dispatchId);
                    nextOperateUserId = dispatchTask.getSenduserid();
                    workflowBo.setVariables(task, "assignee", dispatchTask.getSenduserid());
                    workflowBo.completeTask(task.getId(), "refuse");
                    logger.info("�����Ѿ���ǩ���ȴ��ƶ���˾ȷ�ϡ�");
                    bean.setSignInState(task.getName());
                    processHistoryBean.setProcessAction("�ɵ���ǩ");
                }
                processHistoryBean.setTaskOutCome("refuse");
            }
            if (DispatchTaskConstant.SIGNIN_ACTION.equals(bean.getResult())) {
                // ִ�������ɵ���ת��ǩ��
                if (DispatchTaskWorkflowBO.TRANSFER_SIGN_IN_TASK.equals(task.getName())) {
                    nextOperateUserId = userInfo.getUserID();
                    workflowBo.setVariables(task, "assignee", userInfo.getUserID());
                    workflowBo.completeTask(task.getId(), "reply");
                    logger.info("ת�ɹ����Ѿ�ǩ�ա�");
                    bean.setSignInState(task.getName());
                    processHistoryBean.setProcessAction("�ɵ�ת��ǩ��");
                }
                // ִ�������ɵ���ֱ��ǩ��
                if (DispatchTaskWorkflowBO.SIGN_IN_TASK.equals(task.getName())) {
                    nextOperateUserId = userInfo.getUserID();
                    workflowBo.setVariables(task, "assignee", userInfo.getUserID());
                    workflowBo.setVariables(task, "transition", "reply");
                    workflowBo.completeTask(task.getId(), "sign_in");
                    logger.info("�����Ѿ�ǩ�ա�");
                    bean.setSignInState(task.getName());
                    processHistoryBean.setProcessAction("�ɵ�ǩ��");
                }
                processHistoryBean.setTaskOutCome("reply");
            }
            if (DispatchTaskConstant.IS_TRANSFER.equals(bean.getIsTransfer())) {
                // ִ�������ɵ�ת��
                if (DispatchTaskWorkflowBO.SIGN_IN_TASK.equals(task.getName())) {
                    String[] acceptDept = bean.getAcceptdeptid().split(",");
                    String[] acceptUser = bean.getAcceptuserid().split(",");
                    if (acceptDept != null) {
                        nextOperateUserId = acceptUser[0].split(";")[1];
                        workflowBo.setVariables(task, "assignee", acceptUser[0].split(";")[1]);
                        workflowBo.setVariables(task, "transition", "tranfer_dispatch");
                        workflowBo.completeTask(task.getId(), "sign_in");
                        logger.info("�����Ѿ�ת�ɡ�");
                        bean.setSignInState(task.getName());
                        processHistoryBean.setProcessAction("�ɵ�ת��");
                        processHistoryBean.setTaskOutCome("transfer_dispatch");
                    }
                }
            }
            // processHistoryBO.saveProcessHistory(task.getExecutionId(), task,
            // nextOperateUserId, userInfo.getUserID(), bean
            // .getSendacceptdeptid(), ModuleCatalog.SENDTASK);
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SENDTASK);
            processHistoryBean.setNextOperateUserId(nextOperateUserId);
            processHistoryBean.setObjectId(bean.getSendacceptdeptid());
            processHistoryBO.saveProcessHistory(processHistoryBean);
        }

        // ִ�������ɵ���ת�ɷ��Ͷ���
        if (DispatchTaskConstant.IS_TRANSFER.equals(bean.getIsTransfer())) {
            String[] acceptUser = bean.getAcceptuserid().split(",");
            String[] userIds = null;
            if (acceptUser != null) {
                userIds = new String[1];
                userIds[0] = acceptUser[0].split(";")[1];
            }
            sendDispatchMessage(bean, userIds, "ǩ��");
        }
        // ִ�������ɵ���ǩ�շ��Ͷ���
        if (task != null) {
            if (DispatchTaskConstant.REFUSE_ACTION.equals(bean.getResult())) {
                // ִ�������ɵ���ת�ɾ�ǩ
                if (DispatchTaskWorkflowBO.TRANSFER_SIGN_IN_TASK.equals(task.getName())) {
                    String transferUserId = dao.getTransferUserId(dispatchId, userInfo.getDeptID());
                    String[] userIds = new String[] { transferUserId };
                    sendDispatchMessage(bean, userIds, "����ǩ��");
                }
                // ִ�������ɵ���ֱ�Ӿ�ǩ
                if (DispatchTaskWorkflowBO.SIGN_IN_TASK.equals(task.getName())) {
                    DispatchTask dispatchTask = dispatchTaskDao.viewDispatchTask(dispatchId);
                    String[] userIds = new String[] { dispatchTask.getSenduserid() };
                    sendDispatchMessage(bean, userIds, "��ǩȷ��");
                }
            }
            if (DispatchTaskConstant.SIGNIN_ACTION.equals(bean.getResult())) {
                // ִ�������ɵ���ת��ǩ��
                if (DispatchTaskWorkflowBO.TRANSFER_SIGN_IN_TASK.equals(task.getName())) {
                    String transferUserId = dao.getTransferUserId(dispatchId, userInfo.getDeptID());
                    String[] userIds = new String[] { transferUserId };
                    String content = "�������ɵ�������һ������Ϊ��" + bean.getSendtopic() + "���Ĺ����Ѿ���";
                    content += userInfo.getUserName();
                    content += "ǩ�գ�";
                    sendDispatchMessageSelfContent(bean, userIds, content);
                }
                // ִ�������ɵ���ֱ��ǩ��
                if (DispatchTaskWorkflowBO.SIGN_IN_TASK.equals(task.getName())) {
                    DispatchTask dispatchTask = dispatchTaskDao.viewDispatchTask(dispatchId);
                    String[] userIds = new String[] { dispatchTask.getSenduserid() };
                    String content = "�������ɵ�������һ������Ϊ��" + bean.getSendtopic() + "���Ĺ����Ѿ���";
                    content += userInfo.getUserName();
                    content += "ǩ�գ�";
                    sendDispatchMessageSelfContent(bean, userIds, content);
                }
            }
        }
    }

    /**
     * ִ�������ɵ��ľ�ǩȷ��
     * 
     * @param bean
     * @param userInfo
     */
    public void refuseConfirmTask(SignInTaskBean bean, UserInfo userInfo) throws Exception {
        // ���������ɵ��ľ�ǩȷ����Ϣ
        String dispatchId = bean.getSendtaskid();
        RefuseConfirmTask refuseConfirm = new RefuseConfirmTask();
        BeanUtil.objectCopy(bean, refuseConfirm);
        refuseConfirm.setConfirmUserId(userInfo.getUserID());
        refuseConfirm.setConfirmTime(new Date());
        refuseDao.saveRefuseConfirmTask(refuseConfirm);

        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), bean.getSendacceptdeptid(),
                DispatchTaskWorkflowBO.REFUSE_CONFIRM_TASK);
        if (task != null) {
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            if (DispatchTaskWorkflowBO.REFUSE_CONFIRM_TASK.equals(task.getName())) {
                // ִ�������ɵ��ľ�ǩȷ�ϲ�ͨ��
                if (DispatchTaskConstant.NOT_PASSED.equals(bean.getConfirmResult())) {
                    nextOperateUserId = bean.getRefuseUserId();
                    workflowBo.setVariables(task, "assignee", bean.getRefuseUserId());
                    workflowBo.completeTask(task.getId(), "not_passed");
                    processHistoryBean.setTaskOutCome("not_passed");
                    logger.info("������ǩû��ͨ��ȷ�ϣ��ȴ�����������ǩ�գ�");
                }
                // ִ�������ɵ��ľ�ǩȷ��ͨ��
                if (DispatchTaskConstant.PASSED.equals(bean.getConfirmResult())) {
                    workflowBo.completeTask(task.getId(), "end");
                    processHistoryBean.setTaskOutCome("end");
                    logger.info("������ǩ�Ѿ�ͨ��ȷ�ϣ�");
                }
                processHistoryBean.setProcessAction("�ɵ���ǩȷ��");
            }
            // processHistoryBO.saveProcessHistory(task.getExecutionId(), task,
            // nextOperateUserId, userInfo.getUserID(), bean
            // .getSendacceptdeptid(), ModuleCatalog.SENDTASK);
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SENDTASK);
            processHistoryBean.setNextOperateUserId(nextOperateUserId);
            processHistoryBean.setObjectId(bean.getSendacceptdeptid());
            processHistoryBO.saveProcessHistory(processHistoryBean);
        }

        // �����ɵ�������״̬
        if (dispatchTaskDao.allPassed(dispatchId)) {
            DispatchTask dispatchTask = dispatchTaskDao.viewDispatchTask(dispatchId);
            dispatchTask.setWorkState(DispatchTaskConstant.END_STATE);
            dispatchTaskDao.updateDispatchTask(dispatchTask);
        }

        // ִ�������ɵ��ľ�ǩȷ�ϲ�ͨ�����Ͷ���
        if (DispatchTaskConstant.NOT_PASSED.equals(bean.getConfirmResult())) {
            String[] userIds = null;
            if (bean.getRefuseUserId() != null) {
                userIds = new String[1];
                userIds[0] = bean.getRefuseUserId();
            }
            sendDispatchMessage(bean, userIds, "����ǩ��");
        }
        // ִ�������ɵ��ľ�ǩȷ��ͨ�����Ͷ���
        if (DispatchTaskConstant.PASSED.equals(bean.getConfirmResult())) {
            String[] userIds = null;
            if (bean.getRefuseUserId() != null) {
                userIds = new String[1];
                userIds[0] = bean.getRefuseUserId();
            }
            String content = "�������ɵ�������һ������Ϊ��" + bean.getSendtopic() + "���Ĺ����Ѿ���";
            content += userInfo.getUserName();
            content += "��ǩȷ��ͨ����";
            sendDispatchMessageSelfContent(bean, userIds, content);
        }
    }

    /**
     * ���������ɵ���Ų�ѯ�����ɵ��ķ�����Ϣ�б�
     * 
     * @param dispatchId
     * @param userId
     * @return
     */
    public List queryForWaitRefuseConfirmTask(String dispatchId, String userId) {
        // TODO Auto-generated method stub
        String condition = " and d.id='" + dispatchId + "' ";
        // condition += " and s.result='01' ";
        List list = new ArrayList();
        List signInList = dao.queryForList(condition, " order by s.id desc ");
        List waitRefuseConfirmTaskList = workflowBo.queryForNoDistinctHandleListBean(userId,
                condition, DispatchTaskWorkflowBO.REFUSE_CONFIRM_TASK);
        DynaBean bean;
        DynaBean tmpBean;
        boolean flag = false;
        for (int i = 0; signInList != null && i < signInList.size(); i++) {
            flag = false;
            bean = (DynaBean) signInList.get(i);
            if (bean != null) {
                for (int j = 0; waitRefuseConfirmTaskList != null
                        && j < waitRefuseConfirmTaskList.size(); j++) {
                    tmpBean = (DynaBean) waitRefuseConfirmTaskList.get(j);
                    if (tmpBean != null) {
                        if (bean.get("subid").equals(tmpBean.get("subid"))) {
                            flag = true;
                            break;
                        }
                    }
                }
                if (flag) {
                    if (list != null && list.isEmpty()) {
                        list.add(bean);
                    } else {
                        for (int j = 0; list != null && j < list.size(); j++) {
                            tmpBean = (DynaBean) list.get(j);
                            if (bean.get("subid").equals(tmpBean.get("subid"))) {
                                continue;
                            } else {
                                list.add(bean);
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * ���������ɵ���Ų�ѯ�����ɵ���ǩ����Ϣ�б�
     * 
     * @param dispatchId
     * @param subTaskId
     * @param userInfo
     * @return
     */
    public List queryForSignTask(String dispatchId, String subTaskId, UserInfo userInfo) {
        // TODO Auto-generated method stub
        String condition = " and d.id='" + dispatchId + "' ";
        if (subTaskId != null && !subTaskId.equals("")) {
            condition += " and s.sendaccid='" + subTaskId + "' ";
        }
        if (ConditionGenerate.CITY_CONTRACTOR_USER.equals(userInfo.getType())) {
            condition += " and ( ";
            condition += " (u.deptid='" + userInfo.getDeptID() + "' ";
            condition += " and u.deptype='2') ";
            condition += " or ";
            condition += " (u.deptype='1') ";
            condition += " ) ";
        }
        return dao.queryForList(condition);
    }

    /**
     * ���������ɵ���Ų�ѯ�����ɵ��ľ�ǩȷ����Ϣ�б�
     * 
     * @param dispatchId
     * @return
     */
    public List queryForRefuseConfirmTask(String signInId) {
        // TODO Auto-generated method stub
        String condition = " and s.id='" + signInId + "' ";
        return refuseDao.queryForList(condition);
    }

    /**
     * ����ǩ���ɵ���Ų鿴�����ɵ���ǩ����ϸ��Ϣ
     * 
     * @param signInId
     * @return
     */
    public SignInTaskBean viewSignInTask(String signInId) throws Exception {
        // TODO Auto-generated method stub
        SignInTask signInTask = dao.viewSignInTask(signInId);
        SignInTaskBean bean = new SignInTaskBean();
        BeanUtil.objectCopy(signInTask, bean);
        String userName = queryDao.getUserName(bean.getUserid());
        bean.setEndorseusername(userName);
        if (signInTask != null && signInTask.getTransferuserid() != null
                && !"".equals(signInTask.getTransferuserid())) {
            String condition = " and acceptdept.sign_in_id='" + signInId + "' ";
            List acceptDeptList = acceptDeptDao.queryForDepartList(condition);
            List acceptUserList = acceptDeptDao.queryForUserList(condition);
            bean.setAcceptDeptList(acceptDeptList);
            bean.setAcceptUserList(acceptUserList);
        }
        return bean;
    }

    /**
     * ����ǩ���ɵ���ǩȷ�ϱ�Ų鿴�����ɵ��ľ�ǩȷ����ϸ��Ϣ
     * 
     * @param signInId
     * @return
     */
    public SignInTaskBean viewRefuseConfirmTask(String refuseId) throws Exception {
        // TODO Auto-generated method stub
        RefuseConfirmTask refuseConfirmTask = refuseDao.viewRefuseConfirmTask(refuseId);
        SignInTaskBean bean = new SignInTaskBean();
        BeanUtil.objectCopy(refuseConfirmTask, bean);
        String userName = queryDao.getUserName(bean.getUserid());
        bean.setConfirmUserName(userName);
        return bean;
    }

    /**
     * ����ǩ���ɵ��ľ�ǩȷ�ϱ�Ų鿴�����ɵ��ľ�ǩȷ����ϸ��Ϣ
     * 
     * @param signInId
     * @return
     */
    public SignInTaskBean viewRefuseConfirmTaskBySignInId(String signInId) throws Exception {
        // TODO Auto-generated method stub
        RefuseConfirmTask refuseConfirmTask = refuseDao.viewRefuseConfirmTaskBySignInId(signInId);
        SignInTaskBean bean = new SignInTaskBean();
        if (refuseConfirmTask != null) {
            BeanUtil.objectCopy(refuseConfirmTask, bean);
            String userName = queryDao.getUserName(bean.getConfirmUserId());
            bean.setConfirmUserName(userName);
        }
        return bean;
    }

    /**
     * ���������ɵ�����֪ͨ
     * 
     * @param bean
     * @param userId
     */
    private void sendDispatchMessage(SignInTaskBean bean, String[] userId, String actionString) {
        // ���Ͷ���
        String content = "�������ɵ�������һ������Ϊ��" + bean.getSendtopic() + "���Ĺ����ȴ�����";
        content += actionString;
        content += "��";
        sendDispatchMessageSelfContent(bean, userId, content);
    }

    /**
     * ���������ɵ�����֪ͨ
     * 
     * @param bean
     * @param userId
     */
    private void sendDispatchMessageSelfContent(SignInTaskBean bean, String[] userId, String content) {
        // ���Ͷ���
        String sim = "";
        String mobiles = "";
        for (int i = 0; i < userId.length; i++) {
            sim = queryDao.getSendPhone(userId[i]);
            mobiles = mobiles + sim + ",";
            logger.info("�ɵ��Ķ�������:" + content);
            // if (sim != null && !sim.equals("")) {
            // smSendProxy.simpleSend(sim, content, null, null, true);
            // }
            // List<String> mobileList = com.cabletech.commons.util.StringUtils
            // .string2List(sim, ",");
        }
        try {
            super.sendMessage(content, mobiles);
        } catch (Exception ex) {
            logger.error("���Ͷ��ų���", ex);
        }
        // ������ż�¼
        SMHistory history = new SMHistory();
        history.setSimIds(mobiles);
        history.setSendContent(content);
        history.setSendTime(new Date());
        // history.setSendState(sendState);
        history.setSmType(DispatchTaskConstant.LP_SENDTASKENDORSE);
        history.setObjectId(bean.getId());
        history.setBusinessModule("sendtask");
        historyDAO.save(history);
    }

    /**
     * ���������ɵ������ϴ�����
     * 
     * @param userInfo
     * @param files
     * @param dispatchId
     */
    private void uploadFile(UserInfo userInfo, List files, String dispatchId) {
        uploadFile.saveFiles(files, ModuleCatalog.SENDTASK, userInfo.getRegionName(), dispatchId,
                DispatchTaskConstant.LP_SENDTASKENDORSE, userInfo.getUserID());
    }

}
