package com.cabletech.linepatrol.trouble.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
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
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.ReturnMaterialDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.dao.UseMaterialDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.commons.module.ReturnMaterial;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.commons.module.UseMaterial;
import com.cabletech.linepatrol.trouble.beans.TroubleApproveBean;
import com.cabletech.linepatrol.trouble.beans.TroubleApproverInfoBean;
import com.cabletech.linepatrol.trouble.dao.TroubleInfoDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleProcessUnitDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleReplyApproveDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleReplyDAO;
import com.cabletech.linepatrol.trouble.module.TroubleInfo;
import com.cabletech.linepatrol.trouble.module.TroubleProcessUnit;
import com.cabletech.linepatrol.trouble.module.TroubleReply;
import com.cabletech.linepatrol.trouble.workflow.TroubleWorkflowBO;
@Service
@Transactional
public class TroubleReplyApproveBO extends EntityManager<ApproveInfo,String> {

	@Resource(name="troubleReplyApproveDAO")
	private TroubleReplyApproveDAO dao;

	@Resource(name="troubleInfoDAO")
	private TroubleInfoDAO troubleinfoDAO;

	@Resource(name="troubleReplyDAO")
	private TroubleReplyDAO replyDAO;

	@Resource(name="troubleProcessUnitDAO")
	private TroubleProcessUnitDAO unitDAO;

	@Resource(name="approveDAO")
	private ApproveDAO approveDAO;

	@Resource(name="replyApproverDAO")
	private ReplyApproverDAO approverDAO;

	@Resource(name="uploadFileService")
	private UploadFileService uploadFile;

	@Resource(name="useMaterialDAO")
	private UseMaterialDAO useMaterialDAO;

	@Resource(name="returnMaterialDAO")
	private ReturnMaterialDAO returnMaterialDAO;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Resource(name="smHistoryDAO")
	private SmHistoryDAO historyDAO;

	@Autowired
	private TroubleWorkflowBO workflowBo;


	/**
	 * ���������Ϣ
	 * @param user
	 * @param bean
	 * @param troubleId
	 * @param attachids
	 * @throws Exception
	 */
	public void addApprove(UserInfo user,TroubleApproveBean bean,
			String troubleId,List<FileItem> files) throws ServiceException{
		String userid = user.getUserID();
		ApproveInfo approve = new ApproveInfo();
		try {
			BeanUtil.objectCopy(bean, approve);
			TroubleReply reply = replyDAO.getTroubleReply(approve.getObjectId());
			TroubleInfo trouble = troubleinfoDAO.get(troubleId);
			TroubleProcessUnit unit = unitDAO.getProcessUnitByReplyId(approve.getObjectId());
			String conid = unit.getProcessUnitId();
			String uid= unit.getId();
			String approveResult = approve.getApproveResult();
			approveDAO.saveApproveInfo(approve);
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_NO)){//��ͨ��
				reply.setApproveState(TroubleConstant.REPLY_APPROVE_NO);
				int notPassNum = reply.getNotPassedNum()+1;
				reply.setNotPassedNum(notPassNum);
				replyDAO.save(reply);
				approveNotPassed(user,approve,uid,conid,reply);
			}
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_PASS)){//ͨ��
				String cresult = reply.getConfirmResult();
				if(cresult.equals(TroubleConstant.REPLY_ROLE_HOST)){
					trouble.setTroubleReasonId(bean.getTroubleReasonId());
					trouble.setTroubleState(TroubleConstant.TROUBLE_REPLY_WATI_EXAM);
					troubleinfoDAO.save(trouble);
					//����`ʹ�ò��Ͽ��
					List<UseMaterial> useMaterials = useMaterialDAO.getUseMaterials(reply.getId(),TroubleConstant.TROUBLE_MODULE);
					updateMaterialStock(useMaterials,conid);
					//����`���ղ��Ͽ��
					List<ReturnMaterial> returnMaterials = returnMaterialDAO.getReturnMaterials(reply.getId(),TroubleConstant.TROUBLE_MODULE);
					updateReturnMaterialStock(returnMaterials,conid);
				}
				reply.setApproveState(TroubleConstant.REPLY_APPROVE_PASS);
				replyDAO.save(reply);
				approvePassed(user,approve,uid,cresult);
			}
			uploadFile.saveFiles(files,TroubleConstant.TROUBLE_MODULE, 
					user.getRegionName(), approve.getId(),TroubleConstant.LP_APPROVE_INFO,userid);
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_TRANSFER)){//ת��
				String replyid = reply.getId();
				String transfers = bean.getTransfer();
				approverDAO.updateReader(userid,replyid,TroubleConstant.LP_TROUBLE_REPLY);
				approverDAO.updateApprover(replyid, TroubleConstant.LP_TROUBLE_REPLY);
				saveApprover(replyid,transfers);
				Set<String> all= approverDAO.getApprover(replyid,TroubleConstant.APPROVE_READ,TroubleConstant.LP_TROUBLE_REPLY);
				all.add(transfers);
				approveTransfer(user,approve,uid,StringUtils.join(all.iterator(), ","),transfers);
				String mobiles = bean.getMobiles();
				sendMsgByTransfer(approve,trouble,reply,mobiles);
			}else{
				sendMsg(approve,trouble,reply,approveResult);
			}
		}catch(Exception e) {
			throw new ServiceException("addReplyApprove�����쳣:"+e.getMessage());
		}
	}

	/**
	 * ����ת������Ϣ
	 * @param str
	 */
	public void saveApprover(String replyid,String transfer){
		List<String> transfers = this.parseString(transfer);
		if(transfers!=null && transfers.size()>0){
			for(int i = 0;i<transfers.size();i++){
				ReplyApprover approver = new ReplyApprover();
				approver.setObjectId(replyid);
				approver.setObjectType(TroubleConstant.LP_TROUBLE_REPLY);
				approver.setApproverId(transfers.get(i));
				approver.setApproverType(TroubleConstant.APPROVE_TRANSFER_MAN);
				approver.setIsTransferApproved("0");
				approverDAO.save(approver);
			}
		}
	}


	/**
	 * ��˺��޸Ĳ��Ͽ��
	 * @param materials
	 * @param conid
	 */
	public void updateMaterialStock(List<UseMaterial>  materials,String contractorId){
		try {
			if(materials!=null && materials.size()>0){
				for(int i =0;i<materials.size();i++){
					UseMaterial applyMaterial = materials.get(i);
					useMaterialDAO.writeMaterialUseNumber(applyMaterial, contractorId);
				} 
			}
		}
		catch (Exception e) {
			logger.error(e);
			e.getStackTrace();
		}
	}

	/**
	 * ��˺��޸Ļ��ղ��Ͽ��
	 * @param materials
	 * @param conid
	 */
	public void updateReturnMaterialStock(List<ReturnMaterial>  materials,String contractorId){
		try {
			if(materials!=null && materials.size()>0){
				for(int i =0;i<materials.size();i++){
					ReturnMaterial applyMaterial = materials.get(i);
					returnMaterialDAO.writeMaterialNotUseNumber(applyMaterial, contractorId);
				} 
			}
		}
		catch (Exception e) {
			logger.error(e);
			e.getStackTrace();
		}
	}

	/**
	 * ��˷��Ͷ���
	 */
	public void sendMsg(ApproveInfo approve,TroubleInfo trouble,TroubleReply reply,String approveResult){
		//���Ͷ���
		try{	
			//	String content="���� \""+trouble.getTroubleName()+" \" ������";
			String content="����·���ϡ���λ��һ����������Ϊ\""+trouble.getTroubleName()+"\"�ķ�����";
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_NO)){//��ͨ��
				content+="δͨ����ˣ�";
			}
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_PASS)){//ͨ��
				content+="�Ѿ�ͨ����ˣ�";
			}
			UserInfo user = replyDAO.getUserInfoByUserId(reply.getReplyManId());
			if(user!=null){
				String mobile = user.getPhone();
				//����֪ͨ  ��������дԱ
				logger.info("��������: "+mobile +":"+content);
				super.sendMessage(content, mobile);
				//smSendProxy.simpleSend(mobile,content, null, null, true);
				String id = approve.getId();
				saveSMHistory(mobile,content,id);
			}
		}catch(Exception e){
			logger.error("��˷��������Ͷ���ʧ��:"+e.getMessage());
			e.getStackTrace();
		}
	}

	/**
	 *ת�� ���Ͷ���
	 */
	public void sendMsgByTransfer(ApproveInfo approve,TroubleInfo trouble,TroubleReply reply,String mobiles){
		//���Ͷ���
		//	String content="����һ������Ϊ"+trouble.getTroubleName()+" �ķ������ȴ����ļ�ʱ��ˡ�";
		String content="����·���ϡ�����һ����������Ϊ\""+trouble.getTroubleName()+"\"�ķ������ȴ�������ˡ�";
		System.out.println(content);
		//����֪ͨ   ���췴����ָ���������
		if(mobiles!=null && mobiles.length()>0){
			List<String> mobileList = Arrays.asList(mobiles.split(","));
			super.sendMessage(content, mobileList);
			//			for(String mobile:mobileList){
			//				logger.info("��������: "+mobile +":"+content);
			//				//if(mobile.length() == 13){
			//				smSendProxy.simpleSend(mobile,content, null, null, true);
			//				//}
			//			}
			String id = approve.getId();
			saveSMHistory(mobiles,content,id);
		}
	}

	public void saveSMHistory(String mobiles,String content,String id){
		SMHistory history = new SMHistory();
		history.setSimIds(mobiles);
		history.setSendContent(content);
		history.setSendTime(new Date());
		history.setSmType(TroubleConstant.LP_APPROVE_INFO);
		history.setObjectId(id);
		history.setBusinessModule(TroubleConstant.TROUBLE_MODULE);
		historyDAO.save(history);
	}


	/**
	 * �رչ��ϻ��߹رշ�����
	 * @param troubleid
	 * @throws Exception 
	 */
	public void closeTrouble(UserInfo user,String troubleid,String act) throws Exception{
		TroubleInfo trouble = troubleinfoDAO.get(troubleid);
		trouble.setTroubleState(TroubleConstant.TROUBLE_END);
		troubleinfoDAO.save(trouble);
		List<TroubleReply> replys = new ArrayList<TroubleReply>();;
		if(act!=null && act.equals("closereply")){
			replys = replyDAO.getReplysByTroubleId(troubleid,"join");//��ѯ����Э��ķ�����
		}else{
			replys = replyDAO.getReplysByTroubleId(troubleid,"");
		}
		if(replys!=null && replys.size()>0){
			for(int i = 0;i<replys.size();i++){
				ApproveInfo approve = new ApproveInfo();
				approve.setApproveResult(TroubleConstant.APPROVE_RESULT_PASS);
				TroubleReply reply = replys.get(i);
				//TODO
				//	reply.setApproveState(TroubleConstant.REPLY_APPROVE_CLOSE);
				replyDAO.save(reply);
				TroubleProcessUnit unit = unitDAO.getProcessUnitByReplyId(reply.getId());
				String cr = reply.getConfirmResult();
				approvePassed(user,approve,unit.getId(),cr);
			}
		}
	}


	/**
	 * �ر�һ��������
	 * @param troubleid
	 * @throws Exception 
	 */
	public void closeOneReply(UserInfo user,String replyid) throws Exception{
		TroubleReply reply = replyDAO.get(replyid);
		ApproveInfo approve = new ApproveInfo();
		approve.setApproveResult(TroubleConstant.APPROVE_RESULT_PASS);
		//TODO
		//	reply.setApproveState(TroubleConstant.REPLY_APPROVE_CLOSE);
		replyDAO.save(reply);
		TroubleProcessUnit unit = unitDAO.getProcessUnitByReplyId(reply.getId());
		String cr = reply.getConfirmResult();
		approvePassed(user,approve,unit.getId(),cr);
	}

	private void approvePassed(UserInfo user,ApproveInfo approve,
			String unitid,String cr) throws Exception {
		// ִ�й��Ϸ����������ҵ���߼�
		String userid = user.getUserID();
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		Task task = workflowBo.getHandleTaskForId(user.getUserID(), unitid);
		if (task != null
				&& TroubleWorkflowBO.APPROVE_TASK.equals(task.getName())) {
			if (TroubleConstant.APPROVE_RESULT_PASS.equals(approve
					.getApproveResult())) {
				processHistoryBean.setProcessAction("���������ͨ��");
				if (TroubleConstant.REPLY_ROLE_HOST.equals(cr)) {
					workflowBo.setVariables(task, "assignee",userid);
					workflowBo.setVariables(task, "transition", "evaluate");
					workflowBo.completeTask(task.getId(), "passed");
					System.out.println("�����Ѿ�ͨ����ˣ��ȴ����֣�");
					processHistoryBean.setTaskOutCome("evaluate");
				}
				if (TroubleConstant.REPLY_ROLE_JOIN.equals(cr)) {
					userid="";
					workflowBo.setVariables(task, "transition", "close");
					workflowBo.completeTask(task.getId(), "passed");
					System.out.println("�����Ѿ��رգ�");
					processHistoryBean.setTaskOutCome("end");
				}
				processHistoryBean.initial(task, user, "",
						ModuleCatalog.TROUBLE);
				processHistoryBean.setNextOperateUserId(userid);
				processHistoryBean.setObjectId(unitid);
				processHistoryBO.saveProcessHistory(processHistoryBean);
			}
		}
	}


	private void approveNotPassed(UserInfo user,ApproveInfo approve,
			String unitid,String conid,TroubleReply reply) throws Exception {
		// ���й��Ϸ�����ˣ���˲�ͨ����
		// ִ�й��Ϸ����������ҵ���߼�
		// user��Դ��ϵͳ��¼�û�  ��unitIdΪlp_trouble_process_unit������
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		Task task = workflowBo.getHandleTaskForId(user.getUserID(), unitid);
		if (task != null
				&& TroubleWorkflowBO.APPROVE_TASK.equals(task.getName())) {
			System.out.println("���ϴ���ˣ�" + task.getName());
			if (TroubleConstant.APPROVE_RESULT_NO.equals(approve
					.getApproveResult())) {
				workflowBo.setVariables(task, "assignee",conid);
				workflowBo.completeTask(task.getId(), "not_passed");
				System.out.println("�����Ѿ���˲�ͨ����������·�����");
				processHistoryBean.initial(task, user, "",
						ModuleCatalog.TROUBLE);
				processHistoryBean.setNextOperateUserId(conid);
				processHistoryBean.setObjectId(unitid);
				processHistoryBean.setProcessAction("��������˲�ͨ��");
				processHistoryBean.setTaskOutCome("not_passed");
				processHistoryBO.saveProcessHistory(processHistoryBean);
			}
		}
	}

	/**
	 * 
	 * @param user
	 * @param approve
	 * @param unitid
	 * @param transfersת�����볭����
	 * @param transferת����
	 * @throws Exception
	 */
	private void approveTransfer(UserInfo user,ApproveInfo approve,
			String unitid,String transfers,String transfer) throws Exception {
		// ���й��Ϸ�����ˣ�ת��
		// ִ�й��Ϸ����������ҵ���߼�
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		Task task = workflowBo.getHandleTaskForId(user.getUserID(),unitid);
		if (task != null
				&& TroubleWorkflowBO.APPROVE_TASK.equals(task.getName())) {
			System.out.println("���ϴ���ˣ�" + task.getName());
			if (TroubleConstant.APPROVE_RESULT_TRANSFER.equals(approve.getApproveResult())) {
				workflowBo.setVariables(task, "assignee",transfers);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				processHistoryBean.setProcessAction("�ɵ�����ת��");
				processHistoryBean.setTaskOutCome("transfer");
				processHistoryBean.initial(task, user, "",
						ModuleCatalog.TROUBLE);
				processHistoryBean.setNextOperateUserId(transfer);
				processHistoryBean.setObjectId(unitid);
				processHistoryBO.saveProcessHistory(processHistoryBean);
			}
			System.out.println("�����Ѿ�����ת��");
		}
	}


	public TroubleReply getReplyById(String replyid){
		return replyDAO.get(replyid);
	}


	/**
	 * ��ѯһ��������Ϣ
	 * @param id
	 * @return
	 */
	public TroubleInfo  getTroubleInfo(String id){
		return troubleinfoDAO.get(id);
	}


	/**
	 * ���ݹ���id��ѯ��������Ϣ
	 * @param condition
	 * @return
	 */

	public List getReplyInfos(String troubleid,String userid,String act){
		return dao.getReplyInfos(troubleid,userid,act);
	}

	/**
	 * �鿴��������ϸ
	 * @param user
	 * @param troubleid
	 */
	public List viewReplys(UserInfo user,String troubleid ){
		return dao.viewReplys(user, troubleid);
	}


	/**
	 * �����˲鿴����
	 * @param user
	 * @param troubleid
	 */
	public List viewReplysByReads(UserInfo user,String troubleid ){
		return dao.viewReplysByReads(user, troubleid);
	}

	/**
	 * ����˲鿴����
	 * @param user
	 * @param troubleid
	 */
	public List viewReplysByApproves(UserInfo user,String troubleid ){
		return dao.viewReplysByApproves(user, troubleid);
	}

	/**
	 * �ж��ǲ����Լ��ɷ��ĵ���
	 * 
	 */
	public boolean getDispatchTrouble(UserInfo user,String troubleid ){
		return dao.getDispatchTrouble(user, troubleid);
	}

	/**
	 * �����ʱ��ѯ��������Ϣ
	 * @param troubleid
	 * @param userid
	 * @param act
	 * @return
	 */
	public List getReplys(String troubleid,String userid,String act){
		List list = dao.getReplyInfos(troubleid,userid,act);;
		if(list!=null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String replyid = (String) bean.get("id");
				boolean flag=true;
				boolean read = approverDAO.isReadOnly(replyid, userid, TroubleConstant.LP_TROUBLE_REPLY);
				if(read==false){
					flag = false;
				}
				bean.set("isread", flag+"");
			}
		}
		return list;
	}

	/**
	 * ���淴���������Ϣ
	 * @param reply
	 * @throws ServiceException
	 */
	public void addReplyApprove(ApproveInfo approve) throws ServiceException {
		dao.save(approve);
	}

	/**
	 * ������������Ϣ
	 * @param bean
	 * @param userinfo
	 * @throws Exception
	 */
	public void addTroubleApprove(TroubleApproverInfoBean bean,UserInfo userinfo) throws Exception {
		ApproveInfo approve = new ApproveInfo();
		BeanUtil.objectCopy(bean, approve);
		dao.save(approve);
	}

	/**
	 * �ж��ǲ��ǳ�����
	 * @param objectid
	 * @param userid
	 * @param objectType
	 * @return
	 */
	public boolean isReadOnly(String objectid,String userid,String objectType){
		return approverDAO.isReadOnly(objectid, userid, objectType);
	}

	public boolean isReaded(String objectid,String userid,String objectType){
		return approverDAO.isReaded(objectid, userid, objectType);
	}

	/**
	 * ��ѯ������Ϣ
	 * @param replyid
	 * @return
	 */
	public BasicDynaBean getTrouble(String replyid){
		return dao.getTrouble(replyid);
	}

	/**
	 * ��ѯ�������������ʷ
	 * @param replyid
	 * @return
	 */
	public List getApproveHistorys(String replyid){
		return dao.getApproveHistorys(replyid);
	}



	/**
	 * ��ѯ�����˵Ĺ��ϵķ�����
	 * @param troubleid ���ϵ�ϵͳid
	 * @return
	 */
	public List getWaitExamTroubleReplys(String troubleid,String userid){
		return dao.getWaitExamTroubleReplys(troubleid,userid);
	}


	/*@Transactional(readOnly=true)
	public List queryTroubleInfo(String operationName) throws ServiceException{
		return dao.queryTaskOperation(operationName);
	}*/

	public void delTroubleReply(String id) {
		dao.delete(id);
	}




	public List<ApproveInfo> getAll(){
		return dao.getAll();
	}


	/**
	 * ��ѯ���ϵ���Ϣ
	 * @param troubleid ���ϵ�ϵͳ���
	 * @return
	 */
	public TroubleInfo getTroubleById(String troubleid){
		return troubleinfoDAO.get(troubleid);
	}

	/**
	 * ���������
	 * @param list
	 */
	public void saveApproverList(List list){

	}



	/**
	 * ����ָ���Ĺ����ɵ������
	 * �ж��Ƿ����еķ��������Ѿ����ͨ��
	 * @param troubleid �����ɵ����
	 * @return
	 */
	public boolean judgeAllApproved(String troubleid){

		return dao.judgeAllApproved(troubleid);
	}

	/**
	 * ����ָ���Ĺ����ɵ�������ж��Ƿ����еķ��������Ѿ�����������
	 * @param troubleid �����ɵ����
	 * @return
	 */
	public boolean judgeAllEvaluated(String troubleid){
		return dao.judgeAllEvaluated(troubleid);
	}

	/**
	 * ����ָ���Ĺ����ɵ�������ж��Ƿ����û�н��з����Ĵ�ά��λ
	 * @param toroubleid
	 * @return
	 */
	public boolean judgeExistNoReply(String toroubleid){
		return dao.judgeExistNoReply(toroubleid);
	}


	/**
	 * ���ݹ��Ϸ�����Ϣ���жϸù��Ϸ�����Ϣ�Ƿ��Ѿ����ڡ�
	 * @param replyInfo
	 * @return
	 */
	public boolean judgeExistReply(TroubleReply replyInfo){
		return dao.judgeExistReply(replyInfo);
	}

	/**
	 * ����ָ���Ĺ��Ϸ�����Ϣ���ж��Ƿ�Ը÷�����Ϣ���п���������
	 * @param replyInfo
	 * @return
	 */
	public boolean judgeNeedEvaluate(TroubleReply replyInfo){
		return dao.judgeNeedEvaluate(replyInfo);

	}

	/**
	 * ����ָ���ķ����������д����Ҫ����������Ϣ��
	 * @param replyid
	 * @return
	 */
	public boolean writeNeedEvaluate(String replyid){
		return dao.writeNeedEvaluate(replyid);
	}

	/**
	 * ����ָ���ķ����������д�벻��Ҫ����������Ϣ��
	 * @param replyid
	 * @return
	 */
	public boolean writeNotNeedEvaluate(String replyid){
		return dao.writeNotNeedEvaluate(replyid);
	}

	/**
	 * ��������Ӧ������Ϣ�б�
	 * @param replyid
	 * @return
	 */
	public List getAccidentList(String replyid){
		return null;
	}

	/**
	 * ��������Ӧ�������Ϣ�б�
	 * @param replyid
	 * @return
	 */
	public List getApproveResultList(String replyid){
		return null;
	}

	/**
	 * ��������Ӧ���������Ϣ�б�
	 * @param replyid
	 * @return
	 */
	public List approverList(String replyid){
		return null;
	}

	/**
	 * ��������Ӧ�Ĺ�����Ϣ�б�
	 * @param replyid
	 * @return
	 */
	public List cableList(String replyid){
		return null;
	}



	/**
	 * ��������Ӧ��ʹ�ò�����Ϣ�б�
	 * @return
	 */
	public List getMaterialList(String replyid){
		return null;
	}

	/**
	 * ��������Ӧ�Ĵ��������Ϣ�б�
	 * @param replyid
	 * @return
	 */
	public List getProcessList(String replyid){
		return null;
	}


	/**
	 * ��������Ӧ�Ĵ�����Ա��Ϣ�б�
	 * @param replyid
	 * @return
	 */
	public List processManList(String replyid){
		return null;
	}



	/**
	 * �����ַ���
	 * @param str
	 * @return
	 */
	public List<String> parseString(String str){
		List<String> list = new ArrayList<String>();
		if(str!=null && !"".equals(str.trim())){
			String[] con = str.split(",");
			for(int i = 0;i<con.length;i++){
				list.add(con[i]);
			}
		}
		return list;
	}

	@Override
	protected HibernateDao<ApproveInfo, String> getEntityDao() {
		return dao;
	}
}
