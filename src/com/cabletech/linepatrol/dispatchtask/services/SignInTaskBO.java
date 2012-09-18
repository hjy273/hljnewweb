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
     * 执行任务派单的签收、拒签以及转派
     * 
     * @param bean
     * @param userInfo
     * @param files
     */
    public void signInTask(SignInTaskBean bean, UserInfo userInfo, List files) throws Exception {
        // 保存任务派单的签收信息
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

        // 保存任务派单的转派人信息
        String[] seqIds = null;
        if (DispatchTaskConstant.IS_TRANSFER.equals(bean.getIsTransfer())) {
            String[] acceptDept = bean.getAcceptdeptid().split(",");
            String[] acceptUser = bean.getAcceptuserid().split(",");
            seqIds = acceptDeptDao.saveDispatchAcceptDept(dispatchId, acceptDept, acceptUser,
                    signInId);
        }

        // 执行工作流
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
                // 执行任务派单的转派拒签
                if (DispatchTaskWorkflowBO.TRANSFER_SIGN_IN_TASK.equals(task.getName())) {
                    String transferUserId = dao.getTransferUserId(dispatchId, userInfo.getDeptID());
                    nextOperateUserId = transferUserId;
                    workflowBo.setVariables(task, "assignee", transferUserId);
                    workflowBo.completeTask(task.getId(), "reject");
                    logger.info("转派工单已经拒签。");
                    bean.setSignInState(task.getName());
                    processHistoryBean.setProcessAction("派单转派拒签");
                }
                // 执行任务派单的直接拒签
                if (DispatchTaskWorkflowBO.SIGN_IN_TASK.equals(task.getName())) {
                    DispatchTask dispatchTask = dispatchTaskDao.viewDispatchTask(dispatchId);
                    nextOperateUserId = dispatchTask.getSenduserid();
                    workflowBo.setVariables(task, "assignee", dispatchTask.getSenduserid());
                    workflowBo.completeTask(task.getId(), "refuse");
                    logger.info("工单已经拒签，等待移动公司确认。");
                    bean.setSignInState(task.getName());
                    processHistoryBean.setProcessAction("派单拒签");
                }
                processHistoryBean.setTaskOutCome("refuse");
            }
            if (DispatchTaskConstant.SIGNIN_ACTION.equals(bean.getResult())) {
                // 执行任务派单的转派签收
                if (DispatchTaskWorkflowBO.TRANSFER_SIGN_IN_TASK.equals(task.getName())) {
                    nextOperateUserId = userInfo.getUserID();
                    workflowBo.setVariables(task, "assignee", userInfo.getUserID());
                    workflowBo.completeTask(task.getId(), "reply");
                    logger.info("转派工单已经签收。");
                    bean.setSignInState(task.getName());
                    processHistoryBean.setProcessAction("派单转派签收");
                }
                // 执行任务派单的直接签收
                if (DispatchTaskWorkflowBO.SIGN_IN_TASK.equals(task.getName())) {
                    nextOperateUserId = userInfo.getUserID();
                    workflowBo.setVariables(task, "assignee", userInfo.getUserID());
                    workflowBo.setVariables(task, "transition", "reply");
                    workflowBo.completeTask(task.getId(), "sign_in");
                    logger.info("工单已经签收。");
                    bean.setSignInState(task.getName());
                    processHistoryBean.setProcessAction("派单签收");
                }
                processHistoryBean.setTaskOutCome("reply");
            }
            if (DispatchTaskConstant.IS_TRANSFER.equals(bean.getIsTransfer())) {
                // 执行任务派单转派
                if (DispatchTaskWorkflowBO.SIGN_IN_TASK.equals(task.getName())) {
                    String[] acceptDept = bean.getAcceptdeptid().split(",");
                    String[] acceptUser = bean.getAcceptuserid().split(",");
                    if (acceptDept != null) {
                        nextOperateUserId = acceptUser[0].split(";")[1];
                        workflowBo.setVariables(task, "assignee", acceptUser[0].split(";")[1]);
                        workflowBo.setVariables(task, "transition", "tranfer_dispatch");
                        workflowBo.completeTask(task.getId(), "sign_in");
                        logger.info("工单已经转派。");
                        bean.setSignInState(task.getName());
                        processHistoryBean.setProcessAction("派单转派");
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

        // 执行任务派单的转派发送短信
        if (DispatchTaskConstant.IS_TRANSFER.equals(bean.getIsTransfer())) {
            String[] acceptUser = bean.getAcceptuserid().split(",");
            String[] userIds = null;
            if (acceptUser != null) {
                userIds = new String[1];
                userIds[0] = acceptUser[0].split(";")[1];
            }
            sendDispatchMessage(bean, userIds, "签收");
        }
        // 执行任务派单的签收发送短信
        if (task != null) {
            if (DispatchTaskConstant.REFUSE_ACTION.equals(bean.getResult())) {
                // 执行任务派单的转派拒签
                if (DispatchTaskWorkflowBO.TRANSFER_SIGN_IN_TASK.equals(task.getName())) {
                    String transferUserId = dao.getTransferUserId(dispatchId, userInfo.getDeptID());
                    String[] userIds = new String[] { transferUserId };
                    sendDispatchMessage(bean, userIds, "重新签收");
                }
                // 执行任务派单的直接拒签
                if (DispatchTaskWorkflowBO.SIGN_IN_TASK.equals(task.getName())) {
                    DispatchTask dispatchTask = dispatchTaskDao.viewDispatchTask(dispatchId);
                    String[] userIds = new String[] { dispatchTask.getSenduserid() };
                    sendDispatchMessage(bean, userIds, "拒签确认");
                }
            }
            if (DispatchTaskConstant.SIGNIN_ACTION.equals(bean.getResult())) {
                // 执行任务派单的转派签收
                if (DispatchTaskWorkflowBO.TRANSFER_SIGN_IN_TASK.equals(task.getName())) {
                    String transferUserId = dao.getTransferUserId(dispatchId, userInfo.getDeptID());
                    String[] userIds = new String[] { transferUserId };
                    String content = "【任务派单】您有一个名称为“" + bean.getSendtopic() + "”的工单已经被";
                    content += userInfo.getUserName();
                    content += "签收！";
                    sendDispatchMessageSelfContent(bean, userIds, content);
                }
                // 执行任务派单的直接签收
                if (DispatchTaskWorkflowBO.SIGN_IN_TASK.equals(task.getName())) {
                    DispatchTask dispatchTask = dispatchTaskDao.viewDispatchTask(dispatchId);
                    String[] userIds = new String[] { dispatchTask.getSenduserid() };
                    String content = "【任务派单】您有一个名称为“" + bean.getSendtopic() + "”的工单已经被";
                    content += userInfo.getUserName();
                    content += "签收！";
                    sendDispatchMessageSelfContent(bean, userIds, content);
                }
            }
        }
    }

    /**
     * 执行任务派单的拒签确认
     * 
     * @param bean
     * @param userInfo
     */
    public void refuseConfirmTask(SignInTaskBean bean, UserInfo userInfo) throws Exception {
        // 保存任务派单的拒签确认信息
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
                // 执行任务派单的拒签确认不通过
                if (DispatchTaskConstant.NOT_PASSED.equals(bean.getConfirmResult())) {
                    nextOperateUserId = bean.getRefuseUserId();
                    workflowBo.setVariables(task, "assignee", bean.getRefuseUserId());
                    workflowBo.completeTask(task.getId(), "not_passed");
                    processHistoryBean.setTaskOutCome("not_passed");
                    logger.info("工单拒签没有通过确认，等待受理人重新签收！");
                }
                // 执行任务派单的拒签确认通过
                if (DispatchTaskConstant.PASSED.equals(bean.getConfirmResult())) {
                    workflowBo.completeTask(task.getId(), "end");
                    processHistoryBean.setTaskOutCome("end");
                    logger.info("工单拒签已经通过确认！");
                }
                processHistoryBean.setProcessAction("派单拒签确认");
            }
            // processHistoryBO.saveProcessHistory(task.getExecutionId(), task,
            // nextOperateUserId, userInfo.getUserID(), bean
            // .getSendacceptdeptid(), ModuleCatalog.SENDTASK);
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SENDTASK);
            processHistoryBean.setNextOperateUserId(nextOperateUserId);
            processHistoryBean.setObjectId(bean.getSendacceptdeptid());
            processHistoryBO.saveProcessHistory(processHistoryBean);
        }

        // 更新派单的整体状态
        if (dispatchTaskDao.allPassed(dispatchId)) {
            DispatchTask dispatchTask = dispatchTaskDao.viewDispatchTask(dispatchId);
            dispatchTask.setWorkState(DispatchTaskConstant.END_STATE);
            dispatchTaskDao.updateDispatchTask(dispatchTask);
        }

        // 执行任务派单的拒签确认不通过发送短信
        if (DispatchTaskConstant.NOT_PASSED.equals(bean.getConfirmResult())) {
            String[] userIds = null;
            if (bean.getRefuseUserId() != null) {
                userIds = new String[1];
                userIds[0] = bean.getRefuseUserId();
            }
            sendDispatchMessage(bean, userIds, "重新签收");
        }
        // 执行任务派单的拒签确认通过发送短信
        if (DispatchTaskConstant.PASSED.equals(bean.getConfirmResult())) {
            String[] userIds = null;
            if (bean.getRefuseUserId() != null) {
                userIds = new String[1];
                userIds[0] = bean.getRefuseUserId();
            }
            String content = "【任务派单】您有一个名称为“" + bean.getSendtopic() + "”的工单已经被";
            content += userInfo.getUserName();
            content += "拒签确认通过！";
            sendDispatchMessageSelfContent(bean, userIds, content);
        }
    }

    /**
     * 根据任务派单编号查询任务派单的反馈信息列表
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
     * 根据任务派单编号查询任务派单的签收信息列表
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
     * 根据任务派单编号查询任务派单的拒签确认信息列表
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
     * 根据签收派单编号查看任务派单的签收详细信息
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
     * 根据签收派单拒签确认编号查看任务派单的拒签确认详细信息
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
     * 根据签收派单的拒签确认编号查看任务派单的拒签确认详细信息
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
     * 发送任务派单短信通知
     * 
     * @param bean
     * @param userId
     */
    private void sendDispatchMessage(SignInTaskBean bean, String[] userId, String actionString) {
        // 发送短信
        String content = "【任务派单】您有一个名称为“" + bean.getSendtopic() + "”的工单等待您的";
        content += actionString;
        content += "！";
        sendDispatchMessageSelfContent(bean, userId, content);
    }

    /**
     * 发送任务派单短信通知
     * 
     * @param bean
     * @param userId
     */
    private void sendDispatchMessageSelfContent(SignInTaskBean bean, String[] userId, String content) {
        // 发送短信
        String sim = "";
        String mobiles = "";
        for (int i = 0; i < userId.length; i++) {
            sim = queryDao.getSendPhone(userId[i]);
            mobiles = mobiles + sim + ",";
            logger.info("派单的短信内容:" + content);
            // if (sim != null && !sim.equals("")) {
            // smSendProxy.simpleSend(sim, content, null, null, true);
            // }
            // List<String> mobileList = com.cabletech.commons.util.StringUtils
            // .string2List(sim, ",");
        }
        try {
            super.sendMessage(content, mobiles);
        } catch (Exception ex) {
            logger.error("发送短信出错：", ex);
        }
        // 保存短信记录
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
     * 处理任务派单附件上传功能
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
