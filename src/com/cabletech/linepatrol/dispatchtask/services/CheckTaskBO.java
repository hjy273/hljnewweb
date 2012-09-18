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
     * ִ�������ɵ�����֤
     * 
     * @param bean
     * @param userInfo
     * @param files
     * @param dispatchId
     */
    public void checkTask(CheckTaskBean bean, String dispatchId, UserInfo userInfo, List files)
            throws Exception {
        // �����ɵ����������Ϣ
        CheckTask check = new CheckTask();
        BeanUtil.objectCopy(bean, check);
        check.setValidatetime(new Date());
        check.setValidateuserid(userInfo.getUserID());
        dao.saveCheckTask(check);
        String checkId = check.getId();
        String replyId = check.getReplyid();
        uploadFile(userInfo, files, checkId);

        // ����ת������Ϣ
        if (DispatchTaskConstant.TRANFER_APPROVE.equals(bean.getValidateresult())) {
            approverDao.saveApproverOrReader(bean.getApproverid(), replyId,
                    CommonConstant.APPROVE_TRANSFER_MAN, DispatchTaskConstant.LP_SENDTASKREPLY);
        }

        // ִ�й�����
        Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), bean.getSendacceptdeptid(),
                DispatchTaskWorkflowBO.CHECK_TASK);
        if (task != null && DispatchTaskWorkflowBO.CHECK_TASK.equals(task.getName())) {
            ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
            String nextOperateUserId = "";
            // �ɵ�������˲�ͨ��
            if (DispatchTaskConstant.NOT_PASSED.equals(bean.getValidateresult())) {
                nextOperateUserId = bean.getReplyuserid();
                workflowBo.setVariables(task, "assignee", bean.getReplyuserid());
                workflowBo.completeTask(task.getId(), "not_passed");
                logger.info("������֤��ͨ�������½��з�����");
                processHistoryBean.setProcessAction("�ɵ��������");
                processHistoryBean.setTaskOutCome("not_passed");
            }
            // �ɵ��������ͨ��
            if (DispatchTaskConstant.PASSED.equals(bean.getValidateresult())) {
                workflowBo.setVariables(task, "transition", "end");
                workflowBo.completeTask(task.getId(), "passed");
                logger.info("������֤ͨ��������������");
                processHistoryBean.setProcessAction("�ɵ��������");
                processHistoryBean.setTaskOutCome("end");
            }
            // �ɵ�����ת��
            if (DispatchTaskConstant.TRANFER_APPROVE.equals(bean.getValidateresult())) {
                nextOperateUserId = bean.getApproverid();
                workflowBo.setVariables(task, "assignee", bean.getApproverid());
                workflowBo.setVariables(task, "transition", "transfer");
                workflowBo.completeTask(task.getId(), "passed");
                logger.info("�����Ѿ�ת��");
                processHistoryBean.setProcessAction("�ɵ�����ת��");
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
        // ִ���ɵ�������˲�ͨ�����Ͷ���
        if (DispatchTaskConstant.NOT_PASSED.equals(bean.getValidateresult())) {
            if (bean.getReplyuserid() != null) {
                String[] userId = new String[1];
                userId[0] = bean.getReplyuserid();
                sendDispatchMessage(bean, userId, "�����޸ķ�����Ϣ");
            }
        }
        // ִ���ɵ��������ͨ�����Ͷ���
        if (DispatchTaskConstant.PASSED.equals(bean.getValidateresult())) {
            if (bean.getReplyuserid() != null) {
                String[] userId = new String[1];
                userId[0] = bean.getReplyuserid();
                String content = "�������ɵ�������һ������Ϊ��" + bean.getSendtopic() + "���Ĺ����Ѿ���";
                content += userInfo.getUserName();
                content += "���ͨ����";
                sendDispatchMessageSelfContent(bean, userId, content);
            }
        }
        // ִ���ɵ�����ת���Ͷ���
        if (DispatchTaskConstant.TRANFER_APPROVE.equals(bean.getValidateresult())) {
            if (bean.getApproverid() != null) {
                String[] approverId = bean.getApproverid().split(",");
                sendDispatchMessage(bean, approverId, "�������");
            }
        }
        // �����ɵ�������״̬
        if (dispatchTaskDao.allPassed(dispatchId)) {
            DispatchTask dispatchTask = dispatchTaskDao.viewDispatchTask(dispatchId);
            dispatchTask.setWorkState(DispatchTaskConstant.END_STATE);
            dispatchTaskDao.updateDispatchTask(dispatchTask);
        }
    }

    /**
     * ִ�������ɵ�������
     * 
     * @param bean
     * @param dispatchId
     * @param userInfo
     * @param files
     */
    public void checkReadTask(CheckTaskBean bean, String dispatchId, UserInfo userInfo,
            List<FileItem> files) throws Exception {
        // ���³������Ѿ����ĵı����Ϣ
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
            processHistoryBean.setProcessAction("�ɵ���������");
            processHistoryBean.setTaskOutCome("read");
            processHistoryBO.saveProcessHistory(processHistoryBean);
        }
    }

    /**
     * ���������ɵ�������Ų�ѯ�����ɵ�������������Ϣ�б�
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
     * ���������ɵ���֤��Ų鿴�����ɵ�����֤��ϸ��Ϣ
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
     * ���������ɵ�����֪ͨ
     * 
     * @param bean
     * @param userId
     * @param actionString
     */
    private void sendDispatchMessage(CheckTaskBean bean, String[] userId, String actionString) {
        // ���Ͷ���
        String content = "�������ɵ�������һ������Ϊ��" + bean.getSendtopic() + "���Ĺ����ȴ�����";
        content += actionString;
        content += "��";
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
        history.setSmType(DispatchTaskConstant.LP_VALIDATEREPLY);
        history.setObjectId(bean.getId());
        history.setBusinessModule("sendtask");
        historyDAO.save(history);
    }

    /**
     * ���������ɵ�����֪ͨ
     * 
     * @param bean
     * @param userId
     */
    private void sendDispatchMessageSelfContent(CheckTaskBean bean, String[] userId, String content) {
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
                DispatchTaskConstant.LP_VALIDATEREPLY, userInfo.getUserID());
    }

}
