package com.cabletech.linepatrol.dispatchtask.services;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
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
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.dispatchtask.beans.CheckTaskBean;
import com.cabletech.linepatrol.dispatchtask.dao.CheckTaskDao;
import com.cabletech.linepatrol.dispatchtask.dao.DispatchTaskDao;
import com.cabletech.linepatrol.dispatchtask.dao.QueryDispatchTaskDao;
import com.cabletech.linepatrol.dispatchtask.module.CheckTask;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTask;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;
import com.cabletech.linepatrol.dispatchtask.workflow.DispatchTaskWorkflowBO;

@Service
@Transactional
public class CheckTaskBO extends EntityManager<CheckTask, String> {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource(name = "checkTaskDao")
    private CheckTaskDao dao;

    @Resource(name = "dispatchTaskDao")
    private DispatchTaskDao dispatchTaskDao;

    @Resource(name = "queryDispatchTaskDao")
    private QueryDispatchTaskDao queryDao;

    @Resource(name = "replyApproverDAO")
    private ReplyApproverDAO approverDao;

    @Resource(name = "dispatchTaskWorkflowBO")
    private DispatchTaskWorkflowBO workflowBo;

    @Resource(name = "smHistoryDAO")
    private SmHistoryDAO historyDAO;

    @Resource(name = "processHistoryBO")
    private ProcessHistoryBO processHistoryBO;

    @Resource(name = "uploadFileService")
    private UploadFileService uploadFile;

    public CheckTaskBO() {
    }

    @Override
    protected HibernateDao<CheckTask, String> getEntityDao() {
        return dao;
    }

    /**
     * 执行任务派单的验证
     * 
     * @param bean
     * @param userInfo
     * @param files
     * @param dispatchId
     */
    public void checkTask(CheckTaskBean bean, String dispatchId, UserInfo userInfo, List files)
            throws Exception {
        // 保存派单反馈审核信息
        CheckTask check = new CheckTask();
        BeanUtil.objectCopy(bean, check);
        check.setValidatetime(new Date());
        check.setValidateuserid(userInfo.getUserID());
        dao.saveCheckTask(check);
        String checkId = check.getId();
        String replyId = check.getReplyid();
        uploadFile(userInfo, files, checkId);

        // 保存转审人信息
        if (DispatchTaskConstant.TRANFER_APPROVE.equals(bean.getValidateresult())) {
            approverDao.saveApproverOrReader(bean.getApproverid(), replyId,
                    CommonConstant.APPROVE_TRANSFER_MAN, DispatchTaskConstant.LP_SENDTASKREPLY);
        }

        // 执行工作流
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), bean.getSendacceptdeptid(),
                DispatchTaskWorkflowBO.CHECK_TASK);
        if (task != null && DispatchTaskWorkflowBO.CHECK_TASK.equals(task.getName())) {
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            // 派单反馈审核不通过
            if (DispatchTaskConstant.NOT_PASSED.equals(bean.getValidateresult())) {
                nextOperateUserId = bean.getReplyuserid();
                workflowBo.setVariables(task, "assignee", bean.getReplyuserid());
                workflowBo.completeTask(task.getId(), "not_passed");
                logger.info("工单验证不通过，重新进行反馈。");
                processHistoryBean.setProcessAction("派单反馈审核");
                processHistoryBean.setTaskOutCome("not_passed");
            }
            // 派单反馈审核通过
            if (DispatchTaskConstant.PASSED.equals(bean.getValidateresult())) {
                workflowBo.setVariables(task, "transition", "end");
                workflowBo.completeTask(task.getId(), "passed");
                logger.info("工单验证通过，结束工单。");
                processHistoryBean.setProcessAction("派单反馈审核");
                processHistoryBean.setTaskOutCome("end");
            }
            // 派单反馈转审
            if (DispatchTaskConstant.TRANFER_APPROVE.equals(bean.getValidateresult())) {
                nextOperateUserId = bean.getApproverid();
                workflowBo.setVariables(task, "assignee", bean.getApproverid());
                workflowBo.setVariables(task, "transition", "transfer");
                workflowBo.completeTask(task.getId(), "passed");
                logger.info("工单已经转审。");
                processHistoryBean.setProcessAction("派单反馈转审");
                processHistoryBean.setTaskOutCome("transfer");
            }
            // processHistoryBO.saveProcessHistory(task.getExecutionId(), task,
            // nextOperateUserId, userInfo.getUserID(), bean
            // .getSendacceptdeptid(), ModuleCatalog.SENDTASK);
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SENDTASK);
            processHistoryBean.setNextOperateUserId(nextOperateUserId);
            processHistoryBean.setObjectId(bean.getSendacceptdeptid());
            processHistoryBO.saveProcessHistory(processHistoryBean);
        }
        bean.setId(checkId);
        // 执行派单反馈审核不通过发送短信
        if (DispatchTaskConstant.NOT_PASSED.equals(bean.getValidateresult())) {
            if (bean.getReplyuserid() != null) {
                String[] userId = new String[1];
                userId[0] = bean.getReplyuserid();
                sendDispatchMessage(bean, userId, "重新修改反馈信息");
            }
        }
        // 执行派单反馈审核通过发送短信
        if (DispatchTaskConstant.PASSED.equals(bean.getValidateresult())) {
            if (bean.getReplyuserid() != null) {
                String[] userId = new String[1];
                userId[0] = bean.getReplyuserid();
                String content = "【任务派单】您有一个名称为“" + bean.getSendtopic() + "”的工单已经被";
                content += userInfo.getUserName();
                content += "审核通过！";
                sendDispatchMessageSelfContent(bean, userId, content);
            }
        }
        // 执行派单反馈转审发送短信
        if (DispatchTaskConstant.TRANFER_APPROVE.equals(bean.getValidateresult())) {
            if (bean.getApproverid() != null) {
                String[] approverId = bean.getApproverid().split(",");
                sendDispatchMessage(bean, approverId, "反馈审核");
            }
        }
        // 更新派单的整体状态
        if (dispatchTaskDao.allPassed(dispatchId)) {
            DispatchTask dispatchTask = dispatchTaskDao.viewDispatchTask(dispatchId);
            dispatchTask.setWorkState(DispatchTaskConstant.END_STATE);
            dispatchTaskDao.updateDispatchTask(dispatchTask);
        }
    }

    /**
     * 执行任务派单的批阅
     * 
     * @param bean
     * @param dispatchId
     * @param userInfo
     * @param files
     */
    public void checkReadTask(CheckTaskBean bean, String dispatchId, UserInfo userInfo,
            List<FileItem> files) throws Exception {
        // 更新抄送人已经批阅的标记信息
        String replyId = bean.getReplyid();
        approverDao.updateReader(userInfo.getUserID(), replyId,
                DispatchTaskConstant.LP_SENDTASKREPLY);

        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), bean.getSendacceptdeptid(),
                DispatchTaskWorkflowBO.CHECK_TASK);
        if (task != null && DispatchTaskWorkflowBO.CHECK_TASK.equals(task.getName())) {
            String nextOperateUserId = "";
            // processHistoryBO.saveProcessHistory(task.getExecutionId(), task,
            // nextOperateUserId, userInfo.getUserID(), bean
            // .getSendacceptdeptid(), ModuleCatalog.SENDTASK);
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SENDTASK);
            processHistoryBean.setNextOperateUserId(nextOperateUserId);
            processHistoryBean.setObjectId(bean.getSendacceptdeptid());
            processHistoryBean.setProcessAction("派单反馈批阅");
            processHistoryBean.setTaskOutCome("read");
            processHistoryBO.saveProcessHistory(processHistoryBean);
        }
    }

    /**
     * 根据任务派单反馈编号查询任务派单反馈的验收信息列表
     * 
     * @param dispatchId
     * @return
     */
    public List queryForCheckTask(String replyId) {
        // TODO Auto-generated method stub
        String condition = " and r.id='" + replyId + "' ";
        return dao.queryForList(condition);
    }

    /**
     * 根据任务派单验证编号查看任务派单的验证详细信息
     * 
     * @param checkId
     * @return
     */
    public CheckTaskBean viewCheckTask(String checkId) throws Exception {
        // TODO Auto-generated method stub
        CheckTask checkTask = dao.viewCheckTask(checkId);
        CheckTaskBean bean = new CheckTaskBean();
        BeanUtil.objectCopy(checkTask, bean);
        String userName = queryDao.getUserName(bean.getValidateuserid());
        String condition = " and approver.object_id='" + checkTask.getReplyid() + "' ";
        condition += " and approver.object_type='" + DispatchTaskConstant.LP_SENDTASKREPLY + "' ";
        condition += " and approver.approver_type='" + CommonConstant.APPROVE_TRANSFER_MAN + "' ";
        List approverUserList = queryDao.queryApproverList(condition);
        bean.setValidateusername(userName);
        bean.setApproverUserList(approverUserList);
        return bean;
    }

    /**
     * 发送任务派单短信通知
     * 
     * @param bean
     * @param userId
     * @param actionString
     */
    private void sendDispatchMessage(CheckTaskBean bean, String[] userId, String actionString) {
        // 发送短信
        String content = "【任务派单】您有一个名称为“" + bean.getSendtopic() + "”的工单等待您的";
        content += actionString;
        content += "！";
        String sim = "";
        String mobiles = "";
        for (int i = 0; i < userId.length; i++) {
            sim = queryDao.getSendPhone(userId[i]);
            mobiles = mobiles + sim + ",";
            logger.info("派单的短信内容:" + content);
            // if (sim != null && !sim.equals("")) {
            // smSendProxy.simpleSend(sim, content, null, null, true);
            // }
            List<String> mobileList = com.cabletech.commons.util.StringUtils.string2List(sim, ",");
            try {
                super.sendMessage(content, mobileList);
            } catch (Exception ex) {
                logger.error("发送短信出错：", ex);
            }
        }
        // 保存短信记录
        SMHistory history = new SMHistory();
        history.setSimIds(mobiles);
        history.setSendContent(content);
        history.setSendTime(new Date());
        // history.setSendState(sendState);
        history.setSmType(DispatchTaskConstant.LP_VALIDATEREPLY);
        history.setObjectId(bean.getId());
        history.setBusinessModule("sendtask");
        historyDAO.save(history);
    }

    /**
     * 发送任务派单短信通知
     * 
     * @param bean
     * @param userId
     */
    private void sendDispatchMessageSelfContent(CheckTaskBean bean, String[] userId, String content) {
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
            List<String> mobileList = com.cabletech.commons.util.StringUtils.string2List(sim, ",");
            try {
                super.sendMessage(content, mobileList);
            } catch (Exception ex) {
                logger.error("发送短信出错：", ex);
            }
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
                DispatchTaskConstant.LP_VALIDATEREPLY, userInfo.getUserID());
    }

}
