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
     * ִ�������ɵ��ķ���
     * 
     * @param bean
     * @param userInfo
     * @param files
     */
    public void saveReplyTask(ReplyTaskBean bean, UserInfo userInfo, List files) throws Exception {
        // �����ɵ�������Ϣ
        ReplyTask reply = new ReplyTask();
        BeanUtil.objectCopy(bean, reply);
        reply.setReplytime(new Date());
        reply.setReplyuserid(userInfo.getUserID());
        dao.saveReplyTask(reply);
        String replyId = reply.getId();
        uploadFile(userInfo, files, replyId);

        // �����ɵ�����˺ͳ�������Ϣ
        approverDao.saveApproverOrReader(bean.getApproverid(), replyId, CommonConstant.APPROVE_MAN,
                DispatchTaskConstant.LP_SENDTASKREPLY);
        approverDao.saveApproverOrReader(bean.getReaderid(), replyId, CommonConstant.APPROVE_READ,
                DispatchTaskConstant.LP_SENDTASKREPLY);

        // ִ�й�����
        String approverId = bean.getApproverid() + "," + bean.getReaderid();
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), bean.getSendacceptdeptid(),
                DispatchTaskWorkflowBO.REPLY_TASK);
        if (task != null && DispatchTaskWorkflowBO.REPLY_TASK.equals(task.getName())) {
            // ִ���ɵ������ύ���
            workflowBo.setVariables(task, "assignee", approverId);
            workflowBo.completeTask(task.getId(), "check");
            logger.info("�����Ѿ�������");
            // processHistoryBO.saveProcessHistory(task.getExecutionId(), task,
            // approverId, userInfo.getUserID(), bean
            // .getSendacceptdeptid(), ModuleCatalog.SENDTASK);
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SENDTASK);
            processHistoryBean.setNextOperateUserId(approverId);
            processHistoryBean.setObjectId(bean.getSendacceptdeptid());
            processHistoryBean.setProcessAction("�ɵ�����");
            processHistoryBean.setTaskOutCome("check");
            processHistoryBO.saveProcessHistory(processHistoryBean);
        }
        bean.setId(replyId);
        // ִ���ɵ������ύ��˷��Ͷ���
        sendDispatchMessage(bean, bean.getApproverid(), "�������");
        sendDispatchMessage(bean, bean.getReaderid(), "����");
    }

    /**
     * ִ�������ɵ��������޸�
     * 
     * @param bean
     * @param userInfo
     * @param files
     */
    public void updateReplyTask(ReplyTaskBean bean, UserInfo userInfo, List files) throws Exception {
        // �����ɵ������޸���Ϣ
        ReplyTask reply = dao.get(bean.getId());
        BeanUtil.objectCopy(bean, reply);
        reply.setReplyuserid(userInfo.getUserID());
        dao.updateReplyTask(reply);
        String replyId = reply.getId();
        uploadFile(userInfo, files, replyId);
        // uploadFile.delById(replyId);

        // �����ɵ������ύ����˺ͳ������޸���Ϣ
        approverDao.deleteList(replyId, DispatchTaskConstant.LP_SENDTASKREPLY);
        approverDao.saveApproverOrReader(bean.getApproverid(), replyId, CommonConstant.APPROVE_MAN,
                DispatchTaskConstant.LP_SENDTASKREPLY);
        approverDao.saveApproverOrReader(bean.getReaderid(), replyId, CommonConstant.APPROVE_READ,
                DispatchTaskConstant.LP_SENDTASKREPLY);

        // ִ�й�����
        String approverId = bean.getApproverid() + "," + bean.getReaderid();
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), bean.getSendacceptdeptid(),
                DispatchTaskWorkflowBO.REPLY_TASK);
        if (task != null && DispatchTaskWorkflowBO.REPLY_TASK.equals(task.getName())) {
            // ִ���ɵ������ύ���
            workflowBo.setVariables(task, "assignee", approverId);
            workflowBo.completeTask(task.getId(), "check");
            logger.info("�����Ѿ��������޸ģ���������");
            // processHistoryBO.saveProcessHistory(task.getExecutionId(), task,
            // approverId, userInfo.getUserID(), bean
            // .getSendacceptdeptid(), ModuleCatalog.SENDTASK);
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            processHistoryBean.initial(task, userInfo, "", ModuleCatalog.SENDTASK);
            processHistoryBean.setNextOperateUserId(approverId);
            processHistoryBean.setObjectId(bean.getSendacceptdeptid());
            processHistoryBean.setProcessAction("�ɵ�����");
            processHistoryBean.setTaskOutCome("check");
            processHistoryBO.saveProcessHistory(processHistoryBean);
        }

        // ִ���ɵ������ύ��˷��Ͷ���
        sendDispatchMessage(bean, bean.getApproverid(), "�������");
        sendDispatchMessage(bean, bean.getReaderid(), "����");
    }

    /**
     * ִ�������ɵ�������ɾ��
     * 
     * @param bean
     */
    public void deleteReplyTask(ReplyTaskBean bean) {
        dao.delete(bean.getId());
        // uploadFile.delById(bean.getId());
    }

    /**
     * ���������ɵ���Ų�ѯ�����ɵ��ķ�����Ϣ�б�
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
                // ���ݷ�������Ż�ȡ������������˺ͳ������б�
                approverList = approverDao.getApprovers((String) bean.get("replyid"),
                        DispatchTaskConstant.LP_SENDTASKREPLY);
                for (int j = 0; waitCheckTaskList != null && j < waitCheckTaskList.size(); j++) {
                    tmpBean = (DynaBean) waitCheckTaskList.get(j);
                    if (tmpBean != null) {
                        // ��ȡ���Խ��з�����˵ķ�������Ϣ
                        if (bean.get("subid").equals(tmpBean.get("subid"))) {
                            flag = true;
                            break;
                        }
                    }
                }
                if (flag) {
                    // �жϵ�ǰ�û��Ƿ�Ϊ�÷������ĳ�����
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
                                // �жϸ÷������Ƿ񱻵�ǰ�û��������ˣ����Ĺ�
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
     * ���������ɵ���Ų�ѯ�����ɵ��ķ�����Ϣ�б�
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
     * ���������ɵ�������Ų鿴�����ɵ��ķ�����ϸ��Ϣ
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
     * ִ�м��㷴����ʱʱ��
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
        String replyTimeLengthStr = timeHourLength / 60 + "Сʱ";
        replyTimeLengthStr += timeHourLength % 60 + "����";
        replyBean.setReplyTimeLength(replyTimeLengthStr);
    }

    /**
     * ���������ɵ�����֪ͨ
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
            // ���Ͷ���
            String content = "�������ɵ�������һ������Ϊ��" + bean.getSendtopic() + "���Ĺ����ȴ�����" + actionString
                    + "��";
            String sim = "";
            String mobiles = "";
            for (int i = 0; i < approverIds.length; i++) {
                sim = queryDao.getSendPhone(approverIds[i]);
                mobiles = mobiles + sim + ",";
                logger.info("�ɵ��Ķ�������:" + content);
                // if (sim != null && !sim.equals("")) {
                // smSendProxy.simpleSend(sim, content, null, null, true);
                // }
                List<String> mobileList = com.cabletech.commons.util.StringUtils.string2List(sim,
                        ",");
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
            history.setSmType(DispatchTaskConstant.LP_SENDTASKREPLY);
            history.setObjectId(bean.getId());
            history.setBusinessModule("sendtask");
            historyDAO.save(history);
        }
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
                DispatchTaskConstant.LP_SENDTASKREPLY, userInfo.getUserID());
    }

}
