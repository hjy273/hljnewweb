package com.cabletech.linepatrol.dispatchtask.services;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.dispatchtask.beans.DispatchTaskBean;
import com.cabletech.linepatrol.dispatchtask.beans.ReplyTaskBean;
import com.cabletech.linepatrol.dispatchtask.dao.QueryDispatchTaskDao;
import com.cabletech.linepatrol.dispatchtask.dao.ReplyTaskDao;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;
import com.cabletech.linepatrol.dispatchtask.module.ReplyTask;
import com.cabletech.linepatrol.dispatchtask.workflow.DispatchTaskWorkflowBO;

@Service
@Transactional
public class ReplyTaskBO extends EntityManager<ReplyTask, String> {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource(name = "replyTaskDao")
    private ReplyTaskDao dao;

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

    @Override
    protected HibernateDao<ReplyTask, String> getEntityDao() {
        return dao;
    }

    /**
     * 执行任务派单的反馈
     * 
     * @param bean
     * @param userInfo
     * @param files
     */
    public void saveReplyTask(ReplyTaskBean bean, UserInfo userInfo, List files) throws Exception {
        // 保存派单反馈信息
        ReplyTask reply = new ReplyTask();
        BeanUtil.objectCopy(bean, reply);
        reply.setReplytime(new Date());
        reply.setReplyuserid(userInfo.getUserID());
        dao.saveReplyTask(reply);
        String replyId = reply.getId();
        uploadFile(userInfo, files, replyId);

        // 保存派单审核人和抄送人信息
        approverDao.saveApproverOrReader(bean.getApproverid(), replyId, CommonConstant.APPROVE_MAN,
                DispatchTaskConstant.LP_SENDTASKREPLY);
        approverDao.saveApproverOrReader(bean.getReaderid(), replyId, CommonConstant.APPROVE_READ,
                DispatchTaskConstant.LP_SENDTASKREPLY);

        // 执行工作流
        String approverId = bean.getApproverid() + "," + bean.getReaderid();
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), bean.getSendacceptdeptid(),
                DispatchTaskWorkflowBO.REPLY_TASK);
        if (task != null && DispatchTaskWorkflowBO.REPLY_TASK.equals(task.getName())) {
            // 执行派单反馈提交审核
            workflowBo.setVariables(task, "assignee", approverId);
            workflowBo.completeTask(task.getId(), "check");
            logger.info("工单已经反馈。");
            // processHistoryBO.saveProcessHistory(task.getExecutionId(), task,
            // approverId, userInfo.getUserID(), bean
            // .getSendacceptdeptid(), ModuleCatalog.SENDTASK);
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SENDTASK);
            processHistoryBean.setNextOperateUserId(approverId);
            processHistoryBean.setObjectId(bean.getSendacceptdeptid());
            processHistoryBean.setProcessAction("派单反馈");
            processHistoryBean.setTaskOutCome("check");
            processHistoryBO.saveProcessHistory(processHistoryBean);
        }
        bean.setId(replyId);
        // 执行派单反馈提交审核发送短信
        sendDispatchMessage(bean, bean.getApproverid(), "反馈审核");
        sendDispatchMessage(bean, bean.getReaderid(), "批阅");
    }

    /**
     * 执行任务派单反馈的修改
     * 
     * @param bean
     * @param userInfo
     * @param files
     */
    public void updateReplyTask(ReplyTaskBean bean, UserInfo userInfo, List files) throws Exception {
        // 保存派单反馈修改信息
        ReplyTask reply = dao.get(bean.getId());
        BeanUtil.objectCopy(bean, reply);
        reply.setReplyuserid(userInfo.getUserID());
        dao.updateReplyTask(reply);
        String replyId = reply.getId();
        uploadFile(userInfo, files, replyId);
        // uploadFile.delById(replyId);

        // 保存派单反馈提交审核人和抄送人修改信息
        approverDao.deleteList(replyId, DispatchTaskConstant.LP_SENDTASKREPLY);
        approverDao.saveApproverOrReader(bean.getApproverid(), replyId, CommonConstant.APPROVE_MAN,
                DispatchTaskConstant.LP_SENDTASKREPLY);
        approverDao.saveApproverOrReader(bean.getReaderid(), replyId, CommonConstant.APPROVE_READ,
                DispatchTaskConstant.LP_SENDTASKREPLY);

        // 执行工作流
        String approverId = bean.getApproverid() + "," + bean.getReaderid();
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), bean.getSendacceptdeptid(),
                DispatchTaskWorkflowBO.REPLY_TASK);
        if (task != null && DispatchTaskWorkflowBO.REPLY_TASK.equals(task.getName())) {
            // 执行派单反馈提交审核
            workflowBo.setVariables(task, "assignee", approverId);
            workflowBo.completeTask(task.getId(), "check");
            logger.info("工单已经进行了修改，并反馈。");
            // processHistoryBO.saveProcessHistory(task.getExecutionId(), task,
            // approverId, userInfo.getUserID(), bean
            // .getSendacceptdeptid(), ModuleCatalog.SENDTASK);
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SENDTASK);
            processHistoryBean.setNextOperateUserId(approverId);
            processHistoryBean.setObjectId(bean.getSendacceptdeptid());
            processHistoryBean.setProcessAction("派单反馈");
            processHistoryBean.setTaskOutCome("check");
            processHistoryBO.saveProcessHistory(processHistoryBean);
        }

        // 执行派单反馈提交审核发送短信
        sendDispatchMessage(bean, bean.getApproverid(), "反馈审核");
        sendDispatchMessage(bean, bean.getReaderid(), "批阅");
    }

    /**
     * 执行任务派单反馈的删除
     * 
     * @param bean
     */
    public void deleteReplyTask(ReplyTaskBean bean) {
        dao.delete(bean.getId());
        // uploadFile.delById(bean.getId());
    }

    /**
     * 根据任务派单编号查询任务派单的反馈信息列表
     * 
     * @param dispatchId
     * @param userId
     * @return
     */
    public List queryForWaitCheckReplyTask(String dispatchId, String userId) {
        // TODO Auto-generated method stub
        String condition = " and d.id='" + dispatchId + "' ";
        List list = new ArrayList();
        List<ReplyApprover> approverList;
        List replyList = dao.queryForList(condition);
        List waitCheckTaskList = workflowBo.queryForNoDistinctHandleListBean(userId, condition,
                DispatchTaskWorkflowBO.CHECK_TASK);
        DynaBean bean;
        DynaBean tmpBean;
        ReplyApprover approver;
        boolean flag = false;
        for (int i = 0; replyList != null && i < replyList.size(); i++) {
            flag = false;
            bean = (DynaBean) replyList.get(i);
            if (bean != null) {
                // 根据反馈单编号获取反馈单的审核人和抄送人列表
                approverList = approverDao.getApprovers((String) bean.get("replyid"),
                        DispatchTaskConstant.LP_SENDTASKREPLY);
                for (int j = 0; waitCheckTaskList != null && j < waitCheckTaskList.size(); j++) {
                    tmpBean = (DynaBean) waitCheckTaskList.get(j);
                    if (tmpBean != null) {
                        // 获取可以进行反馈审核的反馈单信息
                        if (bean.get("subid").equals(tmpBean.get("subid"))) {
                            flag = true;
                            break;
                        }
                    }
                }
                if (flag) {
                    // 判断当前用户是否为该反馈单的抄送人
                    for (int k = 0; approverList != null && k < approverList.size(); k++) {
                        approver = approverList.get(k);
                        if (approver != null) {
                            if (userId.equals(approver.getApproverId())) {
                                if (CommonConstant.APPROVE_MAN.equals(approver.getApproverType())) {
                                    flag = true;
                                    bean.set("is_reader", "0");
                                    break;
                                }
                                if (CommonConstant.APPROVE_TRANSFER_MAN.equals(approver
                                        .getApproverType())) {
                                    flag = true;
                                    bean.set("is_reader", "0");
                                    break;
                                }
                                if (CommonConstant.APPROVE_READ.equals(approver.getApproverType())) {
                                    bean.set("is_reader", "1");
                                }
                                // 判断该反馈单是否被当前用户（抄送人）批阅过
                                if (CommonConstant.FINISH_READED.equals(approver.getFinishReaded())) {
                                    flag = false;
                                }
                            }
                        }
                    }
                    if (flag) {
                        list.add(bean);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 根据任务派单编号查询任务派单的反馈信息列表
     * 
     * @param dispatchId
     * @param userInfo
     * @return
     */
    public List queryForReplyTask(String dispatchId, UserInfo userInfo) {
        // TODO Auto-generated method stub
        String condition = " and d.id='" + dispatchId + "' ";
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
     * 根据任务派单反馈编号查看任务派单的反馈详细信息
     * 
     * @param replyId
     * @return
     */
    public ReplyTaskBean viewReplyTask(String replyId) throws Exception {
        // TODO Auto-generated method stub
        ReplyTask replyTask = dao.viewReplyTask(replyId);
        ReplyTaskBean bean = new ReplyTaskBean();
        BeanUtil.objectCopy(replyTask, bean);
        String userName = queryDao.getUserName(bean.getReplyuserid());
        bean.setReplyusername(userName);
        String condition = " and approver.object_id='" + replyId + "' ";
        condition = " and approver.object_type='" + DispatchTaskConstant.LP_SENDTASKREPLY + "' ";
        List approverUserList = queryDao.queryApproverList(condition);
        bean.setApproverUserList(approverUserList);
        return bean;
    }

    /**
     * 执行计算反馈超时时间
     * 
     * @param bean
     * @param replyBean
     * @throws Exception
     */
    public void calculateReplyTime(DispatchTaskBean bean, ReplyTaskBean replyBean) throws Exception {
        String dateFormat = "yyyy/MM/dd HH:mm:ss";
        String numberFormat = "#0";
        DecimalFormat format = new DecimalFormat(numberFormat);
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Date deadLineTime = formatter.parse(bean.getProcessterm());
        Date replyTime = formatter.parse(replyBean.getReplytime());
        long timeMSLength = deadLineTime.getTime() - replyTime.getTime();
        long timeHourLength = timeMSLength / (60 * 1000);
        if (timeHourLength < 0) {
            replyBean.setReplyTimeSign("-");
        } else {
            replyBean.setReplyTimeSign("+");
        }
        timeHourLength = Math.abs(timeHourLength);
        String replyTimeLengthStr = timeHourLength / 60 + "小时";
        replyTimeLengthStr += timeHourLength % 60 + "分钟";
        replyBean.setReplyTimeLength(replyTimeLengthStr);
    }

    /**
     * 发送任务派单短信通知
     * 
     * @param bean
     * @param userId
     */
    private void sendDispatchMessage(ReplyTaskBean bean, String userIds, String actionString) {
        String[] approverIds;
        if (userIds != null) {
            if (userIds.indexOf(",") != -1) {
                approverIds = userIds.split(",");
            } else {
                approverIds = new String[1];
                approverIds[0] = userIds;
            }
            // 发送短信
            String content = "【任务派单】您有一个名称为“" + bean.getSendtopic() + "”的工单等待您的" + actionString
                    + "！";
            String sim = "";
            String mobiles = "";
            for (int i = 0; i < approverIds.length; i++) {
                sim = queryDao.getSendPhone(approverIds[i]);
                mobiles = mobiles + sim + ",";
                logger.info("派单的短信内容:" + content);
                // if (sim != null && !sim.equals("")) {
                // smSendProxy.simpleSend(sim, content, null, null, true);
                // }
                List<String> mobileList = com.cabletech.commons.util.StringUtils.string2List(sim,
                        ",");
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
            history.setSmType(DispatchTaskConstant.LP_SENDTASKREPLY);
            history.setObjectId(bean.getId());
            history.setBusinessModule("sendtask");
            historyDAO.save(history);
        }
    }

    /**
     * 处理任务派单附件上传功能
     * 
     * @param bean
     * @param userInfo
     * @param files
     * @param dispatchId
     */
    private void uploadFile(UserInfo userInfo, List files, String dispatchId) {
        uploadFile.saveFiles(files, ModuleCatalog.SENDTASK, userInfo.getRegionName(), dispatchId,
                DispatchTaskConstant.LP_SENDTASKREPLY, userInfo.getUserID());
    }

}
