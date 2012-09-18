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
	 * 保存审核信息
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
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_NO)){//不通过
				reply.setApproveState(TroubleConstant.REPLY_APPROVE_NO);
				int notPassNum = reply.getNotPassedNum()+1;
				reply.setNotPassedNum(notPassNum);
				replyDAO.save(reply);
				approveNotPassed(user,approve,uid,conid,reply);
			}
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_PASS)){//通过
				String cresult = reply.getConfirmResult();
				if(cresult.equals(TroubleConstant.REPLY_ROLE_HOST)){
					trouble.setTroubleReasonId(bean.getTroubleReasonId());
					trouble.setTroubleState(TroubleConstant.TROUBLE_REPLY_WATI_EXAM);
					troubleinfoDAO.save(trouble);
					//更改`使用材料库存
					List<UseMaterial> useMaterials = useMaterialDAO.getUseMaterials(reply.getId(),TroubleConstant.TROUBLE_MODULE);
					updateMaterialStock(useMaterials,conid);
					//更改`回收材料库存
					List<ReturnMaterial> returnMaterials = returnMaterialDAO.getReturnMaterials(reply.getId(),TroubleConstant.TROUBLE_MODULE);
					updateReturnMaterialStock(returnMaterials,conid);
				}
				reply.setApproveState(TroubleConstant.REPLY_APPROVE_PASS);
				replyDAO.save(reply);
				approvePassed(user,approve,uid,cresult);
			}
			uploadFile.saveFiles(files,TroubleConstant.TROUBLE_MODULE, 
					user.getRegionName(), approve.getId(),TroubleConstant.LP_APPROVE_INFO,userid);
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_TRANSFER)){//转审
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
			throw new ServiceException("addReplyApprove出现异常:"+e.getMessage());
		}
	}

	/**
	 * 保存转审人信息
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
	 * 审核后修改材料库存
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
	 * 审核后修改回收材料库存
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
	 * 审核发送短信
	 */
	public void sendMsg(ApproveInfo approve,TroubleInfo trouble,TroubleReply reply,String approveResult){
		//发送短信
		try{	
			//	String content="您的 \""+trouble.getTroubleName()+" \" 反馈单";
			String content="【线路故障】贵单位有一个故障名称为\""+trouble.getTroubleName()+"\"的反馈单";
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_NO)){//不通过
				content+="未通过审核！";
			}
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_PASS)){//通过
				content+="已经通过审核！";
			}
			UserInfo user = replyDAO.getUserInfoByUserId(reply.getReplyManId());
			if(user!=null){
				String mobile = user.getPhone();
				//短信通知  反馈单填写员
				logger.info("短信内容: "+mobile +":"+content);
				super.sendMessage(content, mobile);
				//smSendProxy.simpleSend(mobile,content, null, null, true);
				String id = approve.getId();
				saveSMHistory(mobile,content,id);
			}
		}catch(Exception e){
			logger.error("审核反馈单发送短信失败:"+e.getMessage());
			e.getStackTrace();
		}
	}

	/**
	 *转审 发送短信
	 */
	public void sendMsgByTransfer(ApproveInfo approve,TroubleInfo trouble,TroubleReply reply,String mobiles){
		//发送短信
		//	String content="您有一个名称为"+trouble.getTroubleName()+" 的反馈单等待您的及时审核。";
		String content="【线路故障】您有一个故障名称为\""+trouble.getTroubleName()+"\"的反馈单等待您的审核。";
		System.out.println(content);
		//短信通知   主办反馈但指定的审核人
		if(mobiles!=null && mobiles.length()>0){
			List<String> mobileList = Arrays.asList(mobiles.split(","));
			super.sendMessage(content, mobileList);
			//			for(String mobile:mobileList){
			//				logger.info("短信内容: "+mobile +":"+content);
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
	 * 关闭故障或者关闭反馈单
	 * @param troubleid
	 * @throws Exception 
	 */
	public void closeTrouble(UserInfo user,String troubleid,String act) throws Exception{
		TroubleInfo trouble = troubleinfoDAO.get(troubleid);
		trouble.setTroubleState(TroubleConstant.TROUBLE_END);
		troubleinfoDAO.save(trouble);
		List<TroubleReply> replys = new ArrayList<TroubleReply>();;
		if(act!=null && act.equals("closereply")){
			replys = replyDAO.getReplysByTroubleId(troubleid,"join");//查询所有协办的反馈单
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
	 * 关闭一个反馈单
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
		// 执行故障反馈处理审核业务逻辑
		String userid = user.getUserID();
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		Task task = workflowBo.getHandleTaskForId(user.getUserID(), unitid);
		if (task != null
				&& TroubleWorkflowBO.APPROVE_TASK.equals(task.getName())) {
			if (TroubleConstant.APPROVE_RESULT_PASS.equals(approve
					.getApproveResult())) {
				processHistoryBean.setProcessAction("反馈单审核通过");
				if (TroubleConstant.REPLY_ROLE_HOST.equals(cr)) {
					workflowBo.setVariables(task, "assignee",userid);
					workflowBo.setVariables(task, "transition", "evaluate");
					workflowBo.completeTask(task.getId(), "passed");
					System.out.println("故障已经通过审核，等待评分！");
					processHistoryBean.setTaskOutCome("evaluate");
				}
				if (TroubleConstant.REPLY_ROLE_JOIN.equals(cr)) {
					userid="";
					workflowBo.setVariables(task, "transition", "close");
					workflowBo.completeTask(task.getId(), "passed");
					System.out.println("故障已经关闭！");
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
		// 进行故障反馈审核（审核不通过）
		// 执行故障反馈处理审核业务逻辑
		// user来源于系统登录用户  该unitId为lp_trouble_process_unit的主键
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		Task task = workflowBo.getHandleTaskForId(user.getUserID(), unitid);
		if (task != null
				&& TroubleWorkflowBO.APPROVE_TASK.equals(task.getName())) {
			System.out.println("故障待审核：" + task.getName());
			if (TroubleConstant.APPROVE_RESULT_NO.equals(approve
					.getApproveResult())) {
				workflowBo.setVariables(task, "assignee",conid);
				workflowBo.completeTask(task.getId(), "not_passed");
				System.out.println("故障已经审核不通过，打回重新反馈！");
				processHistoryBean.initial(task, user, "",
						ModuleCatalog.TROUBLE);
				processHistoryBean.setNextOperateUserId(conid);
				processHistoryBean.setObjectId(unitid);
				processHistoryBean.setProcessAction("反馈单审核不通过");
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
	 * @param transfers转审人与抄送人
	 * @param transfer转审人
	 * @throws Exception
	 */
	private void approveTransfer(UserInfo user,ApproveInfo approve,
			String unitid,String transfers,String transfer) throws Exception {
		// 进行故障反馈审核（转审）
		// 执行故障反馈处理审核业务逻辑
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		Task task = workflowBo.getHandleTaskForId(user.getUserID(),unitid);
		if (task != null
				&& TroubleWorkflowBO.APPROVE_TASK.equals(task.getName())) {
			System.out.println("故障待审核：" + task.getName());
			if (TroubleConstant.APPROVE_RESULT_TRANSFER.equals(approve.getApproveResult())) {
				workflowBo.setVariables(task, "assignee",transfers);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				processHistoryBean.setProcessAction("派单反馈转审");
				processHistoryBean.setTaskOutCome("transfer");
				processHistoryBean.initial(task, user, "",
						ModuleCatalog.TROUBLE);
				processHistoryBean.setNextOperateUserId(transfer);
				processHistoryBean.setObjectId(unitid);
				processHistoryBO.saveProcessHistory(processHistoryBean);
			}
			System.out.println("故障已经进行转审！");
		}
	}


	public TroubleReply getReplyById(String replyid){
		return replyDAO.get(replyid);
	}


	/**
	 * 查询一个故障信息
	 * @param id
	 * @return
	 */
	public TroubleInfo  getTroubleInfo(String id){
		return troubleinfoDAO.get(id);
	}


	/**
	 * 根据故障id查询反馈单信息
	 * @param condition
	 * @return
	 */

	public List getReplyInfos(String troubleid,String userid,String act){
		return dao.getReplyInfos(troubleid,userid,act);
	}

	/**
	 * 查看反馈单详细
	 * @param user
	 * @param troubleid
	 */
	public List viewReplys(UserInfo user,String troubleid ){
		return dao.viewReplys(user, troubleid);
	}


	/**
	 * 抄送人查看单子
	 * @param user
	 * @param troubleid
	 */
	public List viewReplysByReads(UserInfo user,String troubleid ){
		return dao.viewReplysByReads(user, troubleid);
	}

	/**
	 * 审核人查看单子
	 * @param user
	 * @param troubleid
	 */
	public List viewReplysByApproves(UserInfo user,String troubleid ){
		return dao.viewReplysByApproves(user, troubleid);
	}

	/**
	 * 判断是不是自己派发的单子
	 * 
	 */
	public boolean getDispatchTrouble(UserInfo user,String troubleid ){
		return dao.getDispatchTrouble(user, troubleid);
	}

	/**
	 * 在审核时查询反馈单信息
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
	 * 保存反馈单审核信息
	 * @param reply
	 * @throws ServiceException
	 */
	public void addReplyApprove(ApproveInfo approve) throws ServiceException {
		dao.save(approve);
	}

	/**
	 * 保存故障审核信息
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
	 * 判断是不是抄送人
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
	 * 查询故障信息
	 * @param replyid
	 * @return
	 */
	public BasicDynaBean getTrouble(String replyid){
		return dao.getTrouble(replyid);
	}

	/**
	 * 查询反馈单的审核历史
	 * @param replyid
	 * @return
	 */
	public List getApproveHistorys(String replyid){
		return dao.getApproveHistorys(replyid);
	}



	/**
	 * 查询待考核的故障的反馈单
	 * @param troubleid 故障单系统id
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
	 * 查询故障单信息
	 * @param troubleid 故障单系统编号
	 * @return
	 */
	public TroubleInfo getTroubleById(String troubleid){
		return troubleinfoDAO.get(troubleid);
	}

	/**
	 * 保存审核人
	 * @param list
	 */
	public void saveApproverList(List list){

	}



	/**
	 * 根据指定的故障派单编号来
	 * 判断是否所有的反馈单都已经审核通过
	 * @param troubleid 故障派单编号
	 * @return
	 */
	public boolean judgeAllApproved(String troubleid){

		return dao.judgeAllApproved(troubleid);
	}

	/**
	 * 根据指定的故障派单编号来判断是否所有的反馈单都已经考核评估。
	 * @param troubleid 故障派单编号
	 * @return
	 */
	public boolean judgeAllEvaluated(String troubleid){
		return dao.judgeAllEvaluated(troubleid);
	}

	/**
	 * 根据指定的故障派单编号来判断是否存在没有进行反馈的代维单位
	 * @param toroubleid
	 * @return
	 */
	public boolean judgeExistNoReply(String toroubleid){
		return dao.judgeExistNoReply(toroubleid);
	}


	/**
	 * 根据故障反馈信息来判断该故障反馈信息是否已经存在。
	 * @param replyInfo
	 * @return
	 */
	public boolean judgeExistReply(TroubleReply replyInfo){
		return dao.judgeExistReply(replyInfo);
	}

	/**
	 * 根据指定的故障反馈信息来判断是否对该反馈信息进行考核评估。
	 * @param replyInfo
	 * @return
	 */
	public boolean judgeNeedEvaluate(TroubleReply replyInfo){
		return dao.judgeNeedEvaluate(replyInfo);

	}

	/**
	 * 根据指定的反馈单编号来写入需要考核评估信息。
	 * @param replyid
	 * @return
	 */
	public boolean writeNeedEvaluate(String replyid){
		return dao.writeNeedEvaluate(replyid);
	}

	/**
	 * 根据指定的反馈单编号来写入不需要考核评估信息。
	 * @param replyid
	 * @return
	 */
	public boolean writeNotNeedEvaluate(String replyid){
		return dao.writeNotNeedEvaluate(replyid);
	}

	/**
	 * 反馈单对应隐患信息列表
	 * @param replyid
	 * @return
	 */
	public List getAccidentList(String replyid){
		return null;
	}

	/**
	 * 反馈单对应的审核信息列表
	 * @param replyid
	 * @return
	 */
	public List getApproveResultList(String replyid){
		return null;
	}

	/**
	 * 反馈单对应的审核人信息列表
	 * @param replyid
	 * @return
	 */
	public List approverList(String replyid){
		return null;
	}

	/**
	 * 反馈单对应的光缆信息列表
	 * @param replyid
	 * @return
	 */
	public List cableList(String replyid){
		return null;
	}



	/**
	 * 反馈单对应的使用材料信息列表
	 * @return
	 */
	public List getMaterialList(String replyid){
		return null;
	}

	/**
	 * 反馈单对应的处理过程信息列表
	 * @param replyid
	 * @return
	 */
	public List getProcessList(String replyid){
		return null;
	}


	/**
	 * 反馈单对应的处理人员信息列表
	 * @param replyid
	 * @return
	 */
	public List processManList(String replyid){
		return null;
	}



	/**
	 * 解析字符串
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
