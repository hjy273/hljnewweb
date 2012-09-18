package com.cabletech.linepatrol.cut.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
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
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.cut.beans.CutAcceptanceBean;
import com.cabletech.linepatrol.cut.dao.CutAcceptanceDao;
import com.cabletech.linepatrol.cut.dao.CutDao;
import com.cabletech.linepatrol.cut.dao.CutFeedbackDao;
import com.cabletech.linepatrol.cut.dao.CutHopRelDao;
import com.cabletech.linepatrol.cut.module.Cut;
import com.cabletech.linepatrol.cut.module.CutAcceptance;
import com.cabletech.linepatrol.cut.module.CutConstant;
import com.cabletech.linepatrol.cut.module.CutFeedback;
import com.cabletech.linepatrol.cut.workflow.CutWorkflowBO;

@Service
@Transactional
public class CutAcceptanceManager extends EntityManager<CutAcceptance, String> {

	@Autowired
	private CutWorkflowBO workflowBo;

	@Resource(name = "cutDao")
	private CutDao cutDao;

	@Resource(name = "cutFeedbackDao")
	private CutFeedbackDao cutFeedbackDao;

	@Resource(name = "cutAcceptanceDao")
	private CutAcceptanceDao cutAcceptanceDao;
	
	@Resource(name = "uploadFileService")
	private UploadFileService uploadFile;

	@Override
	protected HibernateDao<CutAcceptance, String> getEntityDao() {
		return cutAcceptanceDao;
	}

	@Resource(name = "cutHopRelDao")
	private CutHopRelDao cutHopRelDao;

	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDAO;

	@Resource(name = "approveDAO")
	private ApproveDAO approveDao;

	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;

	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	
	/**
	 * 在添加割接验收前加载申请信息和反馈信息
	 * 
	 * @param cutId
	 * @return
	 */
	public Map addCutAccptanceForm(String cutId, String regionId) throws ServiceException {
		Map map = new HashMap();
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = (CutFeedback) cutFeedbackDao.findByUnique("cutId", cutId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		List subline = cutHopRelDao.getRepeaterSection(sublineIds);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		map.put("cut", cut);
		map.put("cutFeedback", cutFeedback);
		map.put("sublineIds", sublineIds);
		map.put("subline", subline);
		map.put("approve_info_list", approve_info_list);
		return map;
	}

	/**
	 * 保存割接申请验收信息
	 * 
	 * @param cutAcceptance
	 * @throws ServiceException
	 */
	public void addCutAcceptance(CutAcceptanceBean cutAcceptanceBean,
			UserInfo userInfo, List<FileItem> files)
			throws ServiceException {
		String approvers = cutAcceptanceBean.getApproveId();
		String reader = cutAcceptanceBean.getReader();
		String mobiles = cutAcceptanceBean.getMobiles();
		String cutId = cutAcceptanceBean.getCutId();
		CutAcceptance cutAcceptanceSave = saveCutAcceptance(cutAcceptanceBean, userInfo);
		cutDao.updateStateById(cutId, Cut.ACCEPTANCE_APPROVE);
		String[] readerPhones = cutAcceptanceBean.getReaderPhones();

		// 保存附件
		uploadFile.saveFiles(files, ModuleCatalog.LINECUT, 
				userInfo.getRegionName(), cutAcceptanceSave.getId(), 
				CutConstant.LP_CUT_ACCEPTANCE, userInfo.getUserID());

		// 保存审核人信息
		replyApproverDAO.saveApproverOrReader(approvers, cutAcceptanceSave.getId(), CutConstant.APPROVE_MAN, CutConstant.LP_CUT_ACCEPTANCE);
		
		// 保存抄送人信息
		replyApproverDAO.saveApproverOrReader(reader, cutAcceptanceSave.getId(), CutConstant.APPROVE_READ, CutConstant.LP_CUT_ACCEPTANCE);
		
	
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
				cutId);
		if (task != null && CutWorkflowBO.CHECK_TASK.equals(task.getName())) {
			System.out.println("割接验收添加中：" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers+","+reader);
			workflowBo.completeTask(task.getId(), "check_approve");
			System.out.println("割接验收已经提交！");
			
			//保存流程历史
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("割接验收结算");//添加流程处理动作说明
			processHistoryBean.setTaskOutCome("check_approve");//添加工作流流向信息
			processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
			processHistoryBean.setNextOperateUserId(approvers+","+reader);//添加下一步处理人的编号
			processHistoryBean.setObjectId(cutId);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		//发送短信
		String content = "【线路割接】您有一个名称为\"" + cutDao.getCutNameById(cutId)+"\"割接验收结算单等待您的审批。";
		sendMessage(content, mobiles);
		//给抄送人发送短信
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "【线路割接】您有一个名称为\"" + cutDao.getCutNameById(cutId)+"\"割接验收结算单等待您的查阅。";
				sendMessage(content, readerPhones[i]);

				// 保存短信记录
				saveMessage(content, readerPhones[i], cutAcceptanceSave.getId(), CutConstant.LP_CUT_ACCEPTANCE,
						ModuleCatalog.LINECUT);
			}
		}
		//保存短信记录
		saveMessage(content, mobiles, cutAcceptanceSave.getId(), CutConstant.LP_CUT_ACCEPTANCE, ModuleCatalog.LINECUT);
	}
	
	/**
	 * 保存割接验收实体
	 * @param cutAcceptanceBean
	 * @return
	 */
	public CutAcceptance saveCutAcceptance(CutAcceptanceBean cutAcceptanceBean, UserInfo userInfo){
		CutAcceptance cutAcceptance = new CutAcceptance();
		try {
			BeanUtil.objectCopy(cutAcceptanceBean, cutAcceptance);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CutAcceptanceBean转换为CutAcceptance出错，出错信息为：" + e.getMessage());
			throw new ServiceException(e);
		}
		cutAcceptance.setUnapproveNumber(0);
		cutAcceptance.setCreator(userInfo.getUserID());
		cutAcceptance.setCreateTime(new Date());
		return cutAcceptanceDao.saveCutAcceptance(cutAcceptance);
	}

	/**
	 * 编辑验收结算前加载信息
	 * 
	 * @param cutId
	 * @return
	 * @throws ServiceException
	 */
	public Map editCutAcceptanceForm(String cutId, String regionId) throws ServiceException {
		Map map = new HashMap();
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		CutAcceptance cutAcceptance = cutAcceptanceDao.findByUnique("cutId",
				cutId);
		List list = cutDao.getApproves(regionId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		List subline = cutHopRelDao.getRepeaterSection(sublineIds);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		map.put("cut", cut);
		map.put("cutFeedback", cutFeedback);
		map.put("cutAcceptance", cutAcceptance);
		map.put("list", list);
		map.put("sublineIds", sublineIds);
		map.put("subline", subline);
		map.put("approve_info_list", approve_info_list);
		return map;
	}

	/**
	 * 保存修改割接验收信息
	 * 
	 * @param cutAcceptance
	 * @param userId
	 * @param approvers
	 * @throws ServiceException
	 */
	public void editCutAcceptance(CutAcceptanceBean cutAcceptanceBean,
			UserInfo userInfo, List<FileItem> files)
			throws ServiceException {
		String cutId = cutAcceptanceBean.getCutId();
		String approvers = cutAcceptanceBean.getApproveId();
		String reader = cutAcceptanceBean.getReader();
		String mobiles = cutAcceptanceBean.getMobiles();
		saveCutAcceptance(cutAcceptanceBean, userInfo);
		cutDao.updateStateById(cutId, Cut.ACCEPTANCE_APPROVE);
		String[] readerPhones = cutAcceptanceBean.getReaderPhones();

		// 保存附件
		uploadFile.saveFiles(files, ModuleCatalog.LINECUT, 
				userInfo.getRegionName(), cutAcceptanceBean.getId(), CutConstant.LP_CUT_ACCEPTANCE, userInfo.getUserID());

		replyApproverDAO.deleteList(cutAcceptanceBean.getId(), CutConstant.LP_CUT_ACCEPTANCE);
		// 保存审核人信息
		replyApproverDAO.saveApproverOrReader(approvers, cutAcceptanceBean.getId(), CutConstant.APPROVE_MAN, CutConstant.LP_CUT_ACCEPTANCE);
		
		// 保存抄送人信息
		replyApproverDAO.saveApproverOrReader(reader, cutAcceptanceBean.getId(), CutConstant.APPROVE_READ, CutConstant.LP_CUT_ACCEPTANCE);
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),	cutId);
		if (task != null && CutWorkflowBO.CHECK_TASK.equals(task.getName())) {
			System.out.println("割接申请添加中：" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers+","+reader);
			workflowBo.completeTask(task.getId(), "check_approve");
			System.out.println("割接申请已经提交！");
			
			//保存流程历史
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("割接验收结算");//添加流程处理动作说明
			processHistoryBean.setTaskOutCome("check_approve");//添加工作流流向信息
			processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
			processHistoryBean.setNextOperateUserId(approvers+","+reader);//添加下一步处理人的编号
			processHistoryBean.setObjectId(cutId);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		
		//发送短信
		String content = "【线路割接】您有一个名称为\"" + cutDao.getCutNameById(cutId)+"\"割接验收结算单等待您的审批。";
		sendMessage(content, mobiles);
		
		//保存短信记录
		saveMessage(content, mobiles, cutAcceptanceBean.getId(), CutConstant.LP_CUT_ACCEPTANCE, ModuleCatalog.LINECUT);

		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "【线路割接】您有一个名称为\"" + cutDao.getCutNameById(cutId)+"\"割接验收结算单等待您的查阅。";
				sendMessage(content, readerPhones[i]);

				// 保存短信记录
				saveMessage(content, readerPhones[i], cutAcceptanceBean.getId(), CutConstant.LP_CUT_ACCEPTANCE,
						ModuleCatalog.LINECUT);
			}
		}
	}

	/**
	 * 验收结算审核前加载信息
	 * 
	 * @param cutId
	 * @return
	 * @throws ServiceException
	 */
	public Map cutAcceptanceApproveForm(String cutId) throws ServiceException {
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		CutAcceptance cutAcceptance = cutAcceptanceDao.findByUnique("cutId",
				cutId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		List subline = cutHopRelDao.getRepeaterSection(sublineIds);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		Map map = new HashMap();
		map.put("cut", cut);
		map.put("cutFeedback", cutFeedback);
		map.put("cutAcceptance", cutAcceptance);
		map.put("sublineIds", sublineIds);
		map.put("subline", subline);
		map.put("approve_info_list", approve_info_list);
		return map;
	}

	/**
	 * 验收结算审核
	 * 
	 * @param id
	 * @param cutId
	 * @param userId:登陆的用户
	 * @param proposer:申请人
	 * @param approveResult
	 * @throws ServiceException
	 */
	public void cutAcceptanceApprove(CutAcceptanceBean cutAcceptanceBean, String cutId,
			UserInfo userInfo, String proposer) throws ServiceException {
		String phone = "";
		String cutAcceptanceId = cutAcceptanceBean.getId();
		String approveResult = cutAcceptanceBean.getApproveResult();
		String approveRemark = cutAcceptanceBean.getApproveRemark();
		String transfer = cutAcceptanceBean.getApprovers();
		
		//去除已阅和转审给抄送人和自己为抄送人的情况
		String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + transfer + "','" + userInfo.getUserID() + "')";
		String approverIdsReader = cutDao.getApproverIds(cutAcceptanceId, CutConstant.APPROVE_READ, CutConstant.LP_CUT_ACCEPTANCE, condition);

		// 保存审批信息
		saveApproveInfo(cutAcceptanceId, CutConstant.LP_CUT_ACCEPTANCE, userInfo.getUserID(), approveResult, approveRemark);
		String content = "";
		if (!approveResult.equals("2")) {
			CutAcceptance cutAcceptance = cutAcceptanceDao.get(cutAcceptanceId);
			cutAcceptanceDao.initObject(cutAcceptance);
			phone = getPhoneByUserId(proposer);
			if (CutConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				cutDao.updateStateById(cutId, Cut.EXAM);
				//发送短信
				content = "【线路割接】您的\"" + cutDao.getCutNameById(cutAcceptance.getCutId()) + "\"割接验收结算单已经通过审核，等待线维相关人员评分。";
			}
			if (CutConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				cutAcceptanceDao.setUnapproveNumberByAcceptanceId(cutAcceptanceId);
				cutDao.updateStateById(cutId, Cut.ACCEPTANCE_NO_PASS);
				//发送短信
				content = "【线路割接】您的\"" + cutDao.getCutNameById(cutAcceptance.getCutId()) + "\"割接验收结算单未通过审核。";
			}
			cutAcceptanceId = cutAcceptance.getId();
		}else{
			phone = cutAcceptanceBean.getMobiles();
			// 保存审核人信息
			replyApproverDAO.saveApproverOrReader(transfer, cutAcceptanceId, CutConstant.APPROVE_TRANSFER_MAN, CutConstant.LP_CUT_ACCEPTANCE);
			
			//发送短信
			content = "【线路割接】您有一个"+userInfo.getUserName()+"转审给您的名称为\"" + cutDao.getCutNameById(cutId)+"\"割接验收结算单等待您的审批。";
			
		}

		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), cutId);
		if (task != null
				&& CutWorkflowBO.CHECK_APPROVE_TASK.equals(task.getName())) {
			System.out.println("割接验收待审核：" + task.getName());
			if (CutConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", userInfo.getUserID());
				workflowBo.setVariables(task, "transition", "evaluate");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("割接验收审核通过！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("验收结算审批通过");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("evaluate");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
				processHistoryBean.setNextOperateUserId(userInfo.getUserID());//添加下一步处理人的编号
				processHistoryBean.setObjectId(cutId);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (CutConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", proposer);
				workflowBo.completeTask(task.getId(), "not_passed");
				System.out.println("割接验收审核不通过！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("验收结算审批不通过");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("not_passed");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
				processHistoryBean.setNextOperateUserId(proposer);//添加下一步处理人的编号
				processHistoryBean.setObjectId(cutId);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (CutConstant.APPROVE_RESULT_TRANSFER.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", transfer+","+approverIdsReader);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("割接验收已经转审！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("验收结算转审");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("transfer");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
				processHistoryBean.setNextOperateUserId(transfer);//添加下一步处理人的编号
				processHistoryBean.setObjectId(cutId);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
		}
		
		sendMessage(content, phone);
		//保存短信记录
		saveMessage(content, phone, cutAcceptanceId, CutConstant.LP_CUT_ACCEPTANCE, ModuleCatalog.LINECUT);
	}

	/**
	 * 查看验收结算
	 * 
	 * @param cutId
	 * @return
	 */
	public Map viewCutAcceptance(String cutId) {
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		CutAcceptance cutAcceptance = cutAcceptanceDao.findByUnique("cutId",
				cutId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		List subline = cutHopRelDao.getRepeaterSection(sublineIds);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		Map map = new HashMap();
		map.put("cut", cut);
		map.put("cutFeedback", cutFeedback);
		map.put("cutAcceptance", cutAcceptance);
		map.put("sublineIds", sublineIds);
		map.put("subline", subline);
		map.put("approve_info_list", approve_info_list);
		return map;
	}
	
	/**
	 * 将字符串转换为List
	 * 
	 * @param str：需要转换的字符串
	 * @return：list
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
	 * 删除审核信息
	 * 
	 * @param entityId：实体ID
	 * @param entityType：实体类型
	 */
	public void deleteApprove(String entityId, String entityType) {
		approverDAO.deleteList(entityId, entityType);
	}

	/**
	 * 保存审核信息
	 * 
	 * @param entityId：实体ID
	 * @param entityType：实体表名
	 * @param approverId：审核人ID
	 * @param approveResult：审核结果
	 * @param approveRemark：审核意见
	 */
	public void saveApproveInfo(String entityId, String entityType,
			String approverId, String approveResult, String approveRemark) {
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
	 * 发送短信
	 * 
	 * @param content：短信内容
	 * @param mobiles：接收短信手机号码
	 */
	public void sendMessage(String content, String mobiles) {
		if(mobiles != null && !"".equals(mobiles)){
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			super.sendMessage(content, mobileList);
		}
	}

	/**
	 * 保存短信记录
	 * 
	 * @param content：短信内容
	 * @param mobiles：接收短信手机号码
	 * @param entityId：实体ID
	 * @param entityType：实体类型
	 * @param entityModule：实体模型
	 */
	public void saveMessage(String content, String mobiles, String entityId,
			String entityType, String entityModule) {
		if(mobiles != null && !"".equals(mobiles)){
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
	 * 通过用户ID查询用户的手机号码
	 * 
	 * @param userId：用户ID
	 * @return：返回用户手机号码
	 */
	public String getPhoneByUserId(String userId) {
		String hql = "from UserInfo userInfo where userInfo.userID=?";
		List list = cutAcceptanceDao.getHibernateTemplate().find(hql, userId);
		if (list != null && !list.equals("")) {
			UserInfo userInfo = (UserInfo) list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}
	
	public void readReply(UserInfo userInfo, String approverId, String cutAcceptanceId) throws ServiceException{
		approverDAO.updateReader(approverId, cutAcceptanceId, CutConstant.LP_CUT_ACCEPTANCE);
		
		CutAcceptance cutAcceptance = cutAcceptanceDao.findByUnique("id", cutAcceptanceId);
		String creator = cutAcceptance.getCreator();
		String cutId = cutAcceptance.getCutId();
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
				cutId);
		//保存流程历史
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.setProcessAction("验收结算查阅");//添加流程处理动作说明
		processHistoryBean.setTaskOutCome("");//添加工作流流向信息
		processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
		processHistoryBean.setNextOperateUserId("");//添加下一步处理人的编号
		processHistoryBean.setObjectId(cutId);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
}
