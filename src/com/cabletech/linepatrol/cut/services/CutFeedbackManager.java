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

import com.cabletech.baseinfo.dao.UserInfoDAOImpl;
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
import com.cabletech.linepatrol.commons.dao.ReturnMaterialDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.dao.UseMaterialDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.ReturnMaterial;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.commons.module.UseMaterial;
import com.cabletech.linepatrol.cut.beans.CutFeedbackBean;
import com.cabletech.linepatrol.cut.dao.CutDao;
import com.cabletech.linepatrol.cut.dao.CutFeedbackDao;
import com.cabletech.linepatrol.cut.dao.CutHopRelDao;
import com.cabletech.linepatrol.cut.module.Cut;
import com.cabletech.linepatrol.cut.module.CutConstant;
import com.cabletech.linepatrol.cut.module.CutFeedback;
import com.cabletech.linepatrol.cut.workflow.CutWorkflowBO;

@Service
@Transactional
public class CutFeedbackManager extends EntityManager<CutFeedback, String> {

	@Autowired
	private CutWorkflowBO workflowBo;

	@Resource(name = "cutDao")
	private CutDao cutDao;

	@Resource(name = "cutFeedbackDao")
	private CutFeedbackDao cutFeedbackDao;

	@Resource(name = "useMaterialDAO")
	private UseMaterialDAO useMaterialDAO;

	@Resource(name = "cutHopRelDao")
	private CutHopRelDao cutHopRelDao;
	
	@Resource(name = "returnMaterialDAO")
	private ReturnMaterialDAO returnMaterialDAO;
	
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	
	@Resource(name = "uploadFileService")
	private UploadFileService uploadFile;
	
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDAO;

	@Resource(name = "approveDAO")
	private ApproveDAO approveDao;

	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;
	
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;

	@Override
	protected HibernateDao<CutFeedback, String> getEntityDao() {
		return cutFeedbackDao;
	}

	/**
	 * 添加申请反馈需的加载信息
	 * 
	 * @param cutId
	 * @return
	 * @throws ServiceException
	 */
	public Map addCutFeedbackForm(String cutId, String regionId) throws ServiceException {
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		List<UserInfo> mobiles = cutDao.getMobiles(regionId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		Map map = new HashMap();
		map.put("cut", cut);
		map.put("sublineIds", sublineIds);
		map.put("mobiles", mobiles);
		map.put("cutFeedback", cutFeedback);
		map.put("approve_info_list", approve_info_list);
		return map;
	}

	/**
	 * 保存割接申请反馈信息
	 * 
	 * @param cutFeedback
	 * @throws ServiceException
	 */
	public void addCutFeedback(CutFeedbackBean cutFeedbackBean,
			UserInfo userInfo,List<FileItem> files)
			throws ServiceException {
		String approvers = cutFeedbackBean.getApproveId();
		String reader = cutFeedbackBean.getReader();
		String mobiles = cutFeedbackBean.getMobiles();
		String feedbackType = cutFeedbackBean.getFeedbackType();
		String readerPhones[] = cutFeedbackBean.getReaderPhones();
		cutFeedbackBean.setObjectId(cutFeedbackBean.getCutId());
		
		CutFeedback cutFeedbackSave = new CutFeedback();
		
		if("1".equals(feedbackType)){
			CutFeedback cutFeedback = new CutFeedback();
			cutFeedback.setCancelCause(cutFeedbackBean.getCancelCause());
			cutFeedback.setFeedbackType(cutFeedbackBean.getFeedbackType());
			cutFeedback.setCutId(cutFeedbackBean.getCutId());
			cutFeedback.setCreator(userInfo.getUserID());
			cutFeedback.setCreateTime(new Date());
			cutFeedbackSave = cutFeedbackDao.saveCutFeedback(cutFeedback);
		}else{
			cutFeedbackSave = saveCutFeedback(cutFeedbackBean, userInfo);
		}
		cutDao.updateStateById(cutFeedbackSave.getCutId(), Cut.FEEDBACK_APPROVE);

		// 保存附件
		uploadFile.saveFiles(files, ModuleCatalog.LINECUT, 
				userInfo.getRegionName(), cutFeedbackSave.getId(), CutConstant.LP_CUT_FEEDBACK, userInfo.getUserID());

		// 保存审核人信息
		replyApproverDAO.saveApproverOrReader(approvers, cutFeedbackSave.getId(), CutConstant.APPROVE_MAN, CutConstant.LP_CUT_FEEDBACK);
		
		// 保存抄送人信息
		replyApproverDAO.saveApproverOrReader(reader, cutFeedbackSave.getId(), CutConstant.APPROVE_READ, CutConstant.LP_CUT_FEEDBACK);
		
		// 保存材料
		List<UseMaterial> materials = UseMaterial.packageList("cut",
				cutFeedbackBean);
		useMaterialDAO.saveList(materials);
		List<ReturnMaterial> returnMaterials = ReturnMaterial.packageList(
				cutFeedbackBean, "cut");
		returnMaterialDAO.saveList(returnMaterials);
		
		
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
				cutFeedbackSave.getCutId());
		if (task != null && CutWorkflowBO.WORK_TASK.equals(task.getName())) {
			System.out.println("割接反馈添加中：" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers+","+reader);
			workflowBo.completeTask(task.getId(), "work_approve");
			System.out.println("割接反馈已经提交！");
			
			//保存流程历史
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("割接反馈");//添加流程处理动作说明
			processHistoryBean.setTaskOutCome("work_approve");//添加工作流流向信息
			processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
			processHistoryBean.setNextOperateUserId(approvers+","+reader);//添加下一步处理人的编号
			processHistoryBean.setObjectId(cutFeedbackSave.getCutId());//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		
		//发送短信
		String content = null;
		if("0".equals(feedbackType)){
			content = "【线路割接】您有一个名称为\"" + cutDao.getCutNameById(cutFeedbackSave.getCutId())+"\"割接反馈单等待您的审批。";
		}else{
			content = "【线路割接】您有一个名称为\"" + cutDao.getCutNameById(cutFeedbackSave.getCutId())+"\"取消割接申请单等待您的审批。";
		}
		sendMessage(content, mobiles);
		
		//保存短信记录
		saveMessage(content, mobiles, cutFeedbackSave.getId(), CutConstant.LP_CUT_FEEDBACK, ModuleCatalog.LINECUT);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				if("0".equals(feedbackType)){
					content = "【线路割接】您有一个名称为\"" + cutDao.getCutNameById(cutFeedbackSave.getCutId())+"\"割接反馈单等待您的查阅。";
				}else{
					content = "【线路割接】您有一个名称为\"" + cutDao.getCutNameById(cutFeedbackSave.getCutId())+"\"取消割接申请单等待您的查阅。";
				}
				// 保存短信记录
				saveMessage(content, readerPhones[i], cutFeedbackSave.getId(), CutConstant.LP_CUT_FEEDBACK,
						ModuleCatalog.LINECUT);
				sendMessage(content, readerPhones[i]);
			}
		}
		
	}
	
	/**
	 * 割接反馈临时保存
	 * @param cutFeedbackBean
	 * @param userInfo
	 * @param files
	 * @throws ServiceException
	 */
	public void tempSaveCutFeedback(CutFeedbackBean cutFeedbackBean,UserInfo userInfo,List<FileItem> files)
			throws ServiceException {
		String approvers = cutFeedbackBean.getApproveId();
		String reader = cutFeedbackBean.getReader();
		cutFeedbackBean.setObjectId(cutFeedbackBean.getCutId());
		String cutId = cutFeedbackBean.getCutId();
		
		CutFeedback cutFeedbackSave = new CutFeedback();
		
		cutFeedbackSave = saveCutFeedback(cutFeedbackBean, userInfo);
		//cutDao.updateStateById(cutFeedbackSave.getCutId(), Cut.FEEDBACKAPPROVE);

		// 保存附件
		uploadFile.saveFiles(files, ModuleCatalog.LINECUT, 
				userInfo.getRegionName(), cutFeedbackSave.getId(), CutConstant.LP_CUT_FEEDBACK, userInfo.getUserID());

		// 保存审核人信息
		replyApproverDAO.saveApproverOrReader(approvers, cutFeedbackSave.getId(), CutConstant.APPROVE_MAN, CutConstant.LP_CUT_FEEDBACK);
		
		// 保存抄送人信息
		replyApproverDAO.saveApproverOrReader(reader, cutFeedbackSave.getId(), CutConstant.APPROVE_READ, CutConstant.LP_CUT_FEEDBACK);
		
		//删除材料
		useMaterialDAO.deleteList(cutId,"cut");
		returnMaterialDAO.deleteList(cutId, "cut");
		// 保存材料
		List<UseMaterial> materials = UseMaterial.packageList("cut",
				cutFeedbackBean);
		useMaterialDAO.saveList(materials);
		List<ReturnMaterial> returnMaterials = ReturnMaterial.packageList(
				cutFeedbackBean, "cut");
		returnMaterialDAO.saveList(returnMaterials);
	}
	
	/**
	 * 保存割接反馈实体
	 * @param cutFeedbackBean
	 * @return
	 */
	public CutFeedback saveCutFeedback(CutFeedbackBean cutFeedbackBean, UserInfo userInfo){
		CutFeedback cutFeedback = new CutFeedback();
		try {
			BeanUtil.objectCopy(cutFeedbackBean, cutFeedback);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CutFeedbackBean转换为CutFeedback出错，出错信息：" + e.getMessage());
			throw new ServiceException(e);
		}
		cutFeedback.setFeedbackType("0");
		cutFeedback.setCreator(userInfo.getUserID());
		cutFeedback.setCreateTime(new Date());
		return cutFeedbackDao.saveCutFeedback(cutFeedback);
	}

	/**
	 * 审核后修改材料库存
	 * 
	 * @param materials
	 * @param conid
	 */
	public void updateMaterialStock(List<UseMaterial> materials,
			String contractorId) {
		try {
			if (materials != null && materials.size() > 0) {
				for (int i = 0; i < materials.size(); i++) {
					UseMaterial applyMaterial = materials.get(i);
					useMaterialDAO.writeMaterialUseNumber(applyMaterial,
							contractorId);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.getStackTrace();
			throw new ServiceException(e);
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
	 * 编辑割接反馈前加载信息
	 * 
	 * @param cutId
	 * @return
	 * @throws ServiceException
	 */
	public Map editCutFeedbackForm(String cutId, String deptId, String regionId) throws ServiceException {
		Map map = new HashMap();
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		List mobiles = cutDao.getMobiles(regionId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		map.put("cut", cut);
		map.put("approve_info_list", approve_info_list);
		map.put("cutFeedback", cutFeedback);
		map.put("mobiles", mobiles);
		map.put("sublineIds", sublineIds);
		return map;
	}

	/**
	 * 保存修改割接反馈信息
	 * 
	 * @param cutFeedback
	 * @throws ServiceException
	 */
	public void editCutFeedback(CutFeedbackBean cutFeedbackBean,
			UserInfo userInfo, List<FileItem> files)
			throws ServiceException {
		String cutId = cutFeedbackBean.getCutId();
		String feedbackId = cutFeedbackBean.getId();
		String approvers = cutFeedbackBean.getApproveId();
		String reader = cutFeedbackBean.getReader();
		String mobiles = cutFeedbackBean.getMobiles();
		String[] readerPhones = cutFeedbackBean.getReaderPhones();
		
		saveCutFeedback(cutFeedbackBean, userInfo);
		cutDao.updateStateById(cutId, Cut.FEEDBACK_APPROVE);
		
		// 保存材料
		cutFeedbackBean.setObjectId(cutFeedbackBean.getCutId());
		useMaterialDAO.deleteList(cutId,"cut");
		List<UseMaterial> materials = UseMaterial.packageList("cut",
				cutFeedbackBean);
		useMaterialDAO.saveList(materials);
		returnMaterialDAO.deleteList(cutId, "cut");
		List<ReturnMaterial> returnMaterials = ReturnMaterial.packageList(
				cutFeedbackBean, "cut");
		returnMaterialDAO.saveList(returnMaterials);
		
		// 保存附件
		uploadFile.saveFiles(files, ModuleCatalog.LINECUT, 
				userInfo.getRegionName(), cutFeedbackBean.getId(), 
				CutConstant.LP_CUT_FEEDBACK, userInfo.getUserID());

		replyApproverDAO.deleteList(feedbackId, CutConstant.LP_CUT_FEEDBACK);
		// 保存审核人信息
		replyApproverDAO.saveApproverOrReader(approvers, feedbackId, CutConstant.APPROVE_MAN, CutConstant.LP_CUT_FEEDBACK);
		
		// 保存抄送人信息
		replyApproverDAO.saveApproverOrReader(reader, feedbackId, CutConstant.APPROVE_READ, CutConstant.LP_CUT_FEEDBACK);

		
		
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
				cutId);
		if (task != null && CutWorkflowBO.WORK_TASK.equals(task.getName())) {
			System.out.println("割接反馈修改中：" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers + "," + reader);
			workflowBo.completeTask(task.getId(), "work_approve");
			System.out.println("割接反馈已经提交！");
			
			//保存流程历史
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("割接反馈");//添加流程处理动作说明
			processHistoryBean.setTaskOutCome("work_approve");//添加工作流流向信息
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
		String content = "【线路割接】您有一个名称为\"" + cutDao.getCutNameById(cutId)+"\"割接反馈单等待您的审批。";
		sendMessage(content, mobiles);
		
		//保存短信记录
		saveMessage(content, mobiles, feedbackId, CutConstant.LP_CUT_FEEDBACK, ModuleCatalog.LINECUT);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "【线路割接】您有一个名称为\"" + cutDao.getCutNameById(cutId)+"\"割接反馈单等待您的查阅。";
				sendMessage(content, readerPhones[i]);

				// 保存短信记录
				saveMessage(content, readerPhones[i], feedbackId, CutConstant.LP_CUT_FEEDBACK,
						ModuleCatalog.LINECUT);
			}
		}
	}

	/**
	 * 添加反馈审批前的加载信息
	 * 
	 * @param cutId
	 * @return
	 */
	public Map addCutFeedbackApproveForm(String cutId, String regionId) throws ServiceException {
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		List list = cutDao.getApproves(regionId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		Map map = new HashMap();
		map.put("cut", cut);
		map.put("cutFeedback", cutFeedback);
		map.put("sublineIds", sublineIds);
		map.put("approve_info_list", approve_info_list);
		return map;
	}

	/**
	 * 保存反馈审核信息
	 * 
	 * @param cutId
	 * @param userId
	 * @param approveId
	 * @param approveResult
	 * @throws ServiceException
	 */
	public void addCutFeedbackApprove(CutFeedbackBean cutFeedbackBean,
			UserInfo userInfo) throws ServiceException {
		String proposer = cutFeedbackBean.getProposer();
		String approveResult = cutFeedbackBean.getApproveResult();
		String approveRemark = cutFeedbackBean.getApproveRemark();
		String transfer = cutFeedbackBean.getApprovers();
		String feedbackType = cutFeedbackBean.getFeedbackType();
		String cutFeedbackId = cutFeedbackBean.getId();
		
		String phone = "";
		
		//去除已阅和转审给抄送人和自己为抄送人的情况
		String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + transfer + "','" + userInfo.getUserID() + "')";
		String approverIdsReader = cutDao.getApproverIds(cutFeedbackId, CutConstant.APPROVE_READ, CutConstant.LP_CUT_FEEDBACK, condition);

		// 保存审批信息
		saveApproveInfo(cutFeedbackId, CutConstant.LP_CUT_FEEDBACK, 
				userInfo.getUserID(), approveResult, approveRemark);
		String content = "";
		if (!approveResult.equals("2")) {
			
			if (CutConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				if("0".equals(feedbackType)){
					// 审批通过，申请单状态进入待验收结算状态
					cutDao.updateStateById(cutFeedbackBean.getCutId(), Cut.ACCEPTANCE);
	
					// 获得申请人所在的代维单位Id
					String conId = "";
					try {
						conId = (new UserInfoDAOImpl()).findById(proposer).getDeptID();
					} catch (Exception e) {
						logger.error("CutFeedbackBean转换为CutFeedback出错，出错信息：" + e.getMessage());
					}
	
					// 更改材料库存
					List<UseMaterial> useMaterials = useMaterialDAO
							.getUseMaterials(cutFeedbackBean.getCutId(),
									CutConstant.CUT_MODULE);
					updateMaterialStock(useMaterials, conId);
					//更改回收材料库存
					List<ReturnMaterial> returnMaterials = returnMaterialDAO.getReturnMaterials(cutFeedbackBean.getCutId(),CutConstant.CUT_MODULE);
					updateReturnMaterialStock(returnMaterials,conId);
					
					content = "【线路割接】您的\"" + cutDao.getCutNameById(cutFeedbackBean.getCutId()) + "\"割接反馈单已经通过审核。";
				}
				else{
					cutDao.updateStateById(cutFeedbackBean.getCutId(), Cut.EXAM);
					content = "【线路割接】您的\"" + cutDao.getCutNameById(cutFeedbackBean.getCutId()) + "\"取消割接申请单已经通过审核。";
				}
				
				//发送短信
				
			}
			if (CutConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				if("0".equals(feedbackType)){
					cutFeedbackDao.setUnapproveNumber(cutFeedbackId);
					cutDao.updateStateById(cutFeedbackBean.getCutId(), Cut.FEEDBACK_NO_PASS);
					//发送短信
					content = "【线路割接】您的\"" + cutDao.getCutNameById(cutFeedbackBean.getCutId()) + "\"割接反馈单未通过审核。";
				}else{
					cutDao.updateStateById(cutFeedbackBean.getCutId(), Cut.FEEDBACK);
					CutFeedback cutFeedback = cutFeedbackDao.findByUnique("id", cutFeedbackId);
					cutFeedbackDao.delete(cutFeedback);
					//发送短信
					content = "【线路割接】您的\"" + cutDao.getCutNameById(cutFeedbackBean.getCutId()) + "\"取消割接申请单未通过审核。";
				}
			}
			phone = getPhoneByUserId(proposer);
		}else{
			//转审情况
			
			// 保存审核人信息
			replyApproverDAO.saveApproverOrReader(transfer, cutFeedbackId, CutConstant.APPROVE_TRANSFER_MAN, CutConstant.LP_CUT_FEEDBACK);
			
			//发送短信
			if("0".equals(feedbackType)){
				content = "【线路割接】您有一个名称为\"" + cutDao.getCutNameById(cutFeedbackBean.getCutId())+"\"割接反馈单等待您的审批。";
			}else{
				content = "【线路割接】您有一个名称为\"" + cutDao.getCutNameById(cutFeedbackBean.getCutId())+"\"取消割接申请单等待您的审批。";
			}
			phone  = cutFeedbackBean.getMobiles();
		}

		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
				cutFeedbackBean.getCutId());
		if (task != null
				&& CutWorkflowBO.WORK_APPROVE_TASK.equals(task.getName())) {
			System.out.println("割接反馈待审核：" + task.getName());
			if (CutConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				if("0".equals(feedbackType)){
					workflowBo.setVariables(task, "assignee", proposer);
					workflowBo.setVariables(task, "transition", "check");
					
					//保存流程历史
					ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
					String nextOperateUserId = "";
					processHistoryBean.setProcessAction("割接反馈审批通过");//添加流程处理动作说明
					processHistoryBean.setTaskOutCome("check");//添加工作流流向信息
					processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
					processHistoryBean.setNextOperateUserId(proposer);//添加下一步处理人的编号
					processHistoryBean.setObjectId(cutFeedbackBean.getCutId());//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
					try {
						processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
					} catch (Exception e) {
						e.printStackTrace();
						throw new ServiceException();
					}
				}else{
					workflowBo.setVariables(task, "assignee", userInfo.getUserID());
					workflowBo.setVariables(task, "transition", "end");
					
					//保存流程历史
					ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
					String nextOperateUserId = "";
					processHistoryBean.setProcessAction("取消割接申请通过");//添加流程处理动作说明
					processHistoryBean.setTaskOutCome("end");//添加工作流流向信息
					processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
					processHistoryBean.setNextOperateUserId(proposer);//添加下一步处理人的编号
					processHistoryBean.setObjectId(cutFeedbackBean.getCutId());//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
					try {
						processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
					} catch (Exception e) {
						e.printStackTrace();
						throw new ServiceException();
					}
				}
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("割接反馈审核通过！");
			}
			if (CutConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", proposer);
				workflowBo.completeTask(task.getId(), "not_passed");
				System.out.println("割接反馈审核不通过！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("割接反馈审批不通过");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("not_passed");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
				processHistoryBean.setNextOperateUserId(proposer);//添加下一步处理人的编号
				processHistoryBean.setObjectId(cutFeedbackBean.getCutId());//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
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
				System.out.println("割接反馈已经转审！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("割接反馈转审");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("transfer");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
				processHistoryBean.setNextOperateUserId(transfer);//添加下一步处理人的编号
				processHistoryBean.setObjectId(cutFeedbackBean.getCutId());//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
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
		saveMessage(content, phone, cutFeedbackId, CutConstant.LP_CUT_FEEDBACK, ModuleCatalog.LINECUT);
	}

	/**
	 * 查看反馈
	 * 
	 * @param cutId
	 * @return
	 */
	public Map viewCutFeedback(String cutId) {
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		Map map = new HashMap();
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		map.put("cut", cut);
		map.put("cutFeedback", cutFeedback);
		map.put("sublineIds", sublineIds);
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
		List list = cutFeedbackDao.getHibernateTemplate().find(hql, userId);
		if (list != null && !list.equals("")) {
			UserInfo userInfo = (UserInfo) list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}
	
	public void readReply(UserInfo userInfo, String approverId, String cutFeedbackId) throws ServiceException{
		approverDAO.updateReader(approverId, cutFeedbackId, CutConstant.LP_CUT_FEEDBACK);
		
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("id", cutFeedbackId);
		String creator = cutFeedback.getCreator();
		String cutId = cutFeedback.getCutId();
		
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
				cutId);
		//保存流程历史
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.setProcessAction("割接反馈查阅");//添加流程处理动作说明
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
