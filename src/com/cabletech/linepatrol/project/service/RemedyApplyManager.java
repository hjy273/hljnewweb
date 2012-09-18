package com.cabletech.linepatrol.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.util.Hanzi2PinyinUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ContractorDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.ReturnMaterialDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.dao.UseMaterialDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.ReturnMaterial;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.commons.module.UseMaterial;
import com.cabletech.linepatrol.material.dao.MaterialInfoDao;
import com.cabletech.linepatrol.material.dao.MaterialStockDao;
import com.cabletech.linepatrol.overhaul.beans.OverHaulQueryBean;
import com.cabletech.linepatrol.project.beans.RemedyApplyBean;
import com.cabletech.linepatrol.project.constant.ExecuteCode;
import com.cabletech.linepatrol.project.constant.RemedyConstant;
import com.cabletech.linepatrol.project.dao.CountyInfoDao;
import com.cabletech.linepatrol.project.dao.RemedyApplyDao;
import com.cabletech.linepatrol.project.dao.RemedyApplyItemDao;
import com.cabletech.linepatrol.project.domain.ProjectRemedyApply;
import com.cabletech.linepatrol.project.workflow.ProjectRemedyWorkflowBO;
import com.cabletech.utils.StringUtil;

@Service
@Transactional
public class RemedyApplyManager extends
		EntityManager<ProjectRemedyApply, String> {
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Resource(name = "remedyApplyDao")
	private RemedyApplyDao applyDao;
	@Resource(name = "remedyApplyItemDao")
	private RemedyApplyItemDao applyItemDao;
	@Resource(name = "materialInfoDao")
	private MaterialInfoDao materialDao;
	@Resource(name = "materialStockDao")
	private MaterialStockDao materialStorageDao;
	@Resource(name = "useMaterialDAO")
	private UseMaterialDAO useMaterialDao;
	@Resource(name = "returnMaterialDAO")
	private ReturnMaterialDAO returnMaterialDAO;
	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;
	@Resource(name = "contractorDao")
	private ContractorDAO contractorDao;
	@Resource(name = "countyInfoDao")
	private CountyInfoDao countyInfoDao;
	@Resource(name = "approveDAO")
	private ApproveDAO approveDao;
	@Resource(name = "uploadFileService")
	private UploadFileService uploadFile;
	@Resource(name = "projectRemedyWorkflowBO")
	private ProjectRemedyWorkflowBO workflowBo;
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;

	private ConditionGenerate conditionGenerate;

	private ProjectRemedyApply oneApply;
	private List applyItemList;
	private List approveList;

	// 使用材料
	private List materialIdList;
	private List materialStorageTypeList;
	private List materialUseNumberList;
	private List materialStorageAddrList;
	private List materialStorageNumberList;
	private List materialUnitPriceList;

	// 回收材料
	private List materialRecycleList;
	private List materialStorageAddrRecycleList;
	private List materialStorageTypeRecycleList;
	private List materialUseNumberRecycleList;

	public String getRemedySerialPrex(UserInfo userInfo) {
		String deptname = userInfo.getDeptName();
		String dept = Hanzi2PinyinUtil.getPinYinHeadChar(deptname);
		String bdept = dept.toUpperCase();
		String nowDate = DateUtil.DateToString(new Date(), "yyyyMMdd");
		return bdept + nowDate;
	}

	/**
	 * 执行插入修缮申请信息
	 * 
	 * @param request
	 *            HttpServletRequest 用户输入请求
	 * @param form
	 *            ActionForm 用户输入表单
	 * @return String 执行操作信息编码
	 * @throws Exception
	 */
	public String insertApply(UserInfo userInfo, RemedyApplyBean applyBean,
			List<FileItem> files) throws Exception {
		ProjectRemedyApply apply = new ProjectRemedyApply();
		BeanUtil.objectCopy(applyBean, apply);
		// List itemIdList = StringUtil.convertStringArrayToList(applyBean
		// .getItemId());
		// List itemTypeIdList = StringUtil.convertStringArrayToList(applyBean
		// .getItemTypeId());
		materialIdList = StringUtil.convertStringArrayToList(applyBean
				.getMaterialUse());
		materialStorageTypeList = StringUtil.convertStringArrayToList(applyBean
				.getMaterialStorageTypeUse());
		materialUseNumberList = StringUtil.convertStringArrayToList(applyBean
				.getMaterialUseNumberUse());
		materialStorageAddrList = StringUtil.convertStringArrayToList(applyBean
				.getMaterialStorageAddrUse());
		materialStorageNumberList = StringUtil
				.convertStringArrayToList(applyBean
						.getMaterialStorageNumberUse());
		materialUnitPriceList = StringUtil.convertStringArrayToList(applyBean
				.getMaterialUnitPriceUse());
		materialRecycleList = StringUtil.convertStringArrayToList(applyBean
				.getMaterialRecycle());
		materialStorageAddrRecycleList = StringUtil
				.convertStringArrayToList(applyBean
						.getMaterialStorageAddrRecycle());
		materialStorageTypeRecycleList = StringUtil
				.convertStringArrayToList(applyBean
						.getMaterialStorageTypeRecycle());
		materialUseNumberRecycleList = StringUtil
				.convertStringArrayToList(applyBean
						.getMaterialUseNumberRecycle());

		if (applyDao.judgeExistSameApply(apply)) {
			return ExecuteCode.EXIST_SAME_APPLY_ERROR;
		}
		// if (!applyItemDao.judgeExistItem(itemIdList)) {
		// return ExecuteCode.NOT_EXIST_ITEM_ERROR;
		// }
		// if (!applyItemDao.judgeExistItemType(itemTypeIdList)) {
		// return ExecuteCode.NOT_EXIST_ITEM_ERROR;
		// }
		if (!materialDao.judgeExistMaterial(materialIdList)) {
			return ExecuteCode.NOT_EXIST_MATERIAL_ERROR;
		}
		if (!materialStorageDao.judgeEnoughStorage(materialIdList,
				materialUseNumberList, materialStorageAddrList,
				materialStorageTypeList)) {
			return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
		}

		if (RemedyConstant.SUBMITED_ACTION_TYPE.equals(applyBean
				.getIsSubmited())) {
			apply.setState(RemedyConstant.NEEDAPPROVE);
		} else {
			apply.setState(RemedyConstant.NEEDCOMPLETEAPPLY);
		}
		// 保存修缮信息
		applyDao.saveOneApply(apply, ExecuteCode.INSERT_ACTION_TYPE);
		// 保存修缮定额项信息
		applyItemDao.saveItemList(apply.getId(), applyBean.packageInfo());
		// 保存修缮使用材料信息
		List useMaterilList = packageMaterialList(apply.getId());
		useMaterialDao.saveList(useMaterilList);
		// 保存回收材料
		List returnMaterialList = packageReturnMaterailList(apply.getId());
		returnMaterialDAO.saveList(returnMaterialList);
		// 保存修缮附件信息
		saveFiles(files, apply.getId(), userInfo);
		// 保存审核人抄送人
		addApprover(apply.getId(), applyBean.getApproverId(), applyBean
				.getReader());

		// 执行工作流转
		Map variables = new HashMap();
		variables.put("assignee", userInfo.getUserID());
		workflowBo.createProcessInstance(ProjectRemedyWorkflowBO.WORKFLOWNAME,
				apply.getId(), variables);
		if (RemedyConstant.SUBMITED_ACTION_TYPE.equals(applyBean
				.getIsSubmited())) {
			Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
					apply.getId());
			if (task != null
					&& ProjectRemedyWorkflowBO.APPLY_TASK
							.equals(task.getName())) {
				logger.info("填写工程申请开始........................");
				String next = applyBean.getApproverId();
				if (StringUtils.isNotBlank(applyBean.getReader())) {
					next += "," + applyBean.getReader();
				}
				workflowBo.setVariables(task, "assignee", next);
				workflowBo.completeTask(task.getId(), "approve");
				logger.info("填写工程申请结束........................");
			}
			applyBean.setId(apply.getId());
			// 设置短信发送的修缮项目名称和发送目标人编号
			if (applyBean.getApproverId() != null
					&& !"".equals(applyBean.getApproverId())) {
				String[] userId = applyBean.getApproverId().split(",");
				String msg = "您有一个名称为“" + applyBean.getProjectName()
						+ "”的工程申请等待您的审核！";
				sendMsg(userInfo, applyBean, userId, msg);
			}
			if (applyBean.getReader() != null
					&& !"".equals(applyBean.getReader())) {
				String[] reader = applyBean.getReader().split(",");
				String msgr = "您有一个名称为“" + applyBean.getProjectName()
						+ "”的工程申请等待您的查看！";
				sendMsg(userInfo, applyBean, reader, msgr);
			}

			// 流程历史
			workflowBo.saveProcessHistory(userInfo, apply, null, applyBean
					.getApproverId(), "start", "工程申请", "start");
		}

		return ExecuteCode.SUCCESS_CODE;
	}

	public void addApprover(String id, String approver, String reader) {
		// 删除以前的审核人，抄送人
		replyApproverDAO.deleteList(id, RemedyConstant.LP_REMEDY);

		// 保存审核人，抄送人
		if (StringUtils.isNotBlank(approver))
			replyApproverDAO.saveApproverOrReader(approver, id,
					CommonConstant.APPROVE_MAN, RemedyConstant.LP_REMEDY);
		if (StringUtils.isNotBlank(reader))
			replyApproverDAO.saveApproverOrReader(reader, id,
					CommonConstant.APPROVE_READ, RemedyConstant.LP_REMEDY);
	}

	public void saveFiles(List<FileItem> files, String id, UserInfo userInfo) {
		uploadFile.saveFiles(files, ModuleCatalog.PROJECT, userInfo
				.getRegionName(), id, RemedyConstant.LP_REMEDY, userInfo
				.getUserID());
	}

	/**
	 * 执行修改修缮申请信息
	 * 
	 * @param request
	 *            HttpServletRequest 用户输入请求
	 * @param form
	 *            ActionForm 用户输入表单
	 * @return String 执行操作信息编码
	 * @throws Exception
	 */
	public String updateApply(UserInfo userInfo, RemedyApplyBean applyBean,
			List<FileItem> files) throws Exception {
		ProjectRemedyApply apply = applyDao.viewOneApply(applyBean.getId());
		BeanUtil.objectCopy(applyBean, apply);
		apply.setState("10");
		// List itemList =
		// StringUtil.convertStringArrayToList(applyBean.getItems());
		// List itemIdList = StringUtil.convertStringArrayToList(applyBean
		// .getItemId());
		// List itemTypeIdList = StringUtil.convertStringArrayToList(applyBean
		// .getItemTypeId());
		materialIdList = StringUtil.convertStringArrayToList(applyBean
				.getMaterialUse());
		materialStorageTypeList = StringUtil.convertStringArrayToList(applyBean
				.getMaterialStorageTypeUse());
		materialUseNumberList = StringUtil.convertStringArrayToList(applyBean
				.getMaterialUseNumberUse());
		materialStorageAddrList = StringUtil.convertStringArrayToList(applyBean
				.getMaterialStorageAddrUse());
		materialStorageNumberList = StringUtil
				.convertStringArrayToList(applyBean
						.getMaterialStorageNumberUse());
		materialUnitPriceList = StringUtil.convertStringArrayToList(applyBean
				.getMaterialUnitPriceUse());
		materialRecycleList = StringUtil.convertStringArrayToList(applyBean
				.getMaterialRecycle());
		materialStorageAddrRecycleList = StringUtil
				.convertStringArrayToList(applyBean
						.getMaterialStorageAddrRecycle());
		materialStorageTypeRecycleList = StringUtil
				.convertStringArrayToList(applyBean
						.getMaterialStorageTypeRecycle());
		materialUseNumberRecycleList = StringUtil
				.convertStringArrayToList(applyBean
						.getMaterialUseNumberRecycle());

		if (!applyDao.judgeExistApply(apply.getId())) {
			return ExecuteCode.NO_DATA_ERROR;
		}
		if (applyDao.judgeExistSameApply(apply)) {
			return ExecuteCode.EXIST_SAME_APPLY_ERROR;
		}
		// if (!applyItemDao.judgeExistItem(itemIdList)) {
		// return ExecuteCode.NOT_EXIST_ITEM_ERROR;
		// }
		// if (!applyItemDao.judgeExistItemType(itemTypeIdList)) {
		// return ExecuteCode.NOT_EXIST_ITEM_ERROR;
		// }
		if (!materialDao.judgeExistMaterial(materialIdList)) {
			return ExecuteCode.NOT_EXIST_MATERIAL_ERROR;
		}
		if (!materialStorageDao.judgeEnoughStorage(materialIdList,
				materialUseNumberList, materialStorageAddrList,
				materialStorageTypeList)) {
			return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
		}
		// if (!applyDao.judgeExistMtUsed(apply)) {
		// return ExecuteCode.NOT_EXIST_MT_USED_ERROR;
		// }

		String applyId = apply.getId();
		// 删除旧的定额项信息
		applyItemDao.deleteItemList(applyId);
		// 删除旧的修缮材料信息
		useMaterialDao.deleteList(applyId, RemedyConstant.MATERIAL_USE_TYPE);
		// 删除旧的回收材料信息
		returnMaterialDAO.deleteList(applyId, RemedyConstant.MATERIAL_USE_TYPE);

		// 状态
		if (RemedyConstant.SUBMITED_ACTION_TYPE.equals(applyBean
				.getIsSubmited())) {
			apply.setState(RemedyConstant.NEEDAPPROVE);
		}
		// 保存修缮信息
		applyDao.saveOneApply(apply, ExecuteCode.UPDATE_ACTION_TYPE);
		// 保存修缮定额项信息
		applyItemDao.saveItemList(applyId, applyBean.packageInfo());
		// 保存修缮使用材料信息
		List useMaterilList = packageMaterialList(applyId);
		useMaterialDao.saveList(useMaterilList);
		// 保存回收材料
		List returnMaterialList = packageReturnMaterailList(apply.getId());
		returnMaterialDAO.saveList(returnMaterialList);
		// 保存修缮附件信息
		saveFiles(files, applyId, userInfo);
		// 保存审核人抄送人
		addApprover(apply.getId(), applyBean.getApproverId(), applyBean
				.getReader());

		// 执行工作流转
		if (RemedyConstant.SUBMITED_ACTION_TYPE.equals(applyBean
				.getIsSubmited())) {
			Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
					apply.getId());
			if (task != null
					&& ProjectRemedyWorkflowBO.APPLY_TASK
							.equals(task.getName())) {
				logger.info("修改工程申请开始........................");

				String next = applyBean.getApproverId();
				if (StringUtils.isNotBlank(applyBean.getReader())) {
					next += "," + applyBean.getReader();
				}

				workflowBo.setVariables(task, "assignee", next);
				workflowBo.completeTask(task.getId(), "approve");
				logger.info("修改工程申请结束........................");
			}
			// 设置短信发送的修缮项目名称和发送目标人编号
			if (applyBean.getApproverId() != null
					&& !"".equals(applyBean.getApproverId())) {
				String[] userId = applyBean.getApproverId().split(",");
				String msg = "您有一个名称为“" + applyBean.getProjectName()
						+ "”的工程申请等待您的审核！";
				sendMsg(userInfo, applyBean, userId, msg);
			}
			if (applyBean.getReader() != null
					&& !"".equals(applyBean.getReader())) {
				String[] reader = applyBean.getReader().split(",");
				String msgr = "您有一个名称为“" + applyBean.getProjectName()
						+ "”的工程申请等待您的查看！";
				sendMsg(userInfo, applyBean, reader, msgr);
			}

			// 流程历史
			workflowBo.saveProcessHistory(userInfo, apply, null, applyBean
					.getApproverId(), "start", "工程申请", "start");
		}

		return ExecuteCode.SUCCESS_CODE;
	}

	/**
	 * 执行删除修缮申请信息
	 * 
	 * @param request
	 *            HttpServletRequest 用户输入请求
	 * @param form
	 *            ActionForm 用户输入表单
	 * @return String 执行操作信息编码
	 * @throws Exception
	 */
	public String deleteApply(HttpServletRequest request, ActionForm form)
			throws Exception {
		String applyId = request.getParameter("apply_id");
		if (!applyDao.judgeExistApply(applyId)) {
			return ExecuteCode.NO_DATA_ERROR;
		}
		// 删除定额项信息
		applyItemDao.deleteItemList(applyId);
		// 删除修缮使用材料信息
		useMaterialDao.deleteList(applyId, RemedyConstant.MATERIAL_USE_TYPE);
		// 删除修缮附件信息

		// 保存修缮信息
		applyDao.delete(applyId);
		return ExecuteCode.SUCCESS_CODE;
	}

	/**
	 * 执行判断是否存在相同的修缮申请信息
	 * 
	 * @param request
	 *            HttpServletRequest 用户输入请求
	 * @return boolean 是否存在相同的修缮申请信息
	 * @throws Exception
	 */
	public boolean judgeExistSameApply(HttpServletRequest request)
			throws Exception {
		ProjectRemedyApply apply = new ProjectRemedyApply();
		apply.setProjectName(request.getParameter("project_name").trim());
		if (request.getParameter("apply_id") != null
				&& !request.getParameter("apply_id").equals("")) {
			apply.setId(request.getParameter("apply_id").trim());
		} else {
			apply.setId("-1");
		}
		if (applyDao.judgeExistSameApply(apply)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 执行根据条件查询修缮申请列表
	 * 
	 * @param condition
	 *            String 查询条件
	 * @return List 修缮申请结果列表
	 * @throws Exception
	 */
	public List queryList(UserInfo userInfo, RemedyApplyBean bean)
			throws Exception {
		String condition = " and remedy.state = '30' ";
		conditionGenerate = new ConditionGenerate();
		condition += conditionGenerate.getInputCondition(bean);
		condition += conditionGenerate.getUserQueryCondition(userInfo);
		List list = applyDao.queryList(condition);
		return list;
	}

	/**
	 * 执行根据条件查询修缮申请列表
	 * 
	 * @param condition
	 *            String 查询条件
	 * @return List 修缮申请结果列表
	 * @throws Exception
	 */
	public List queryWaitHandleList(UserInfo userInfo, RemedyApplyBean bean,
			String taskName) throws Exception {
		String condition = "";
		List list = workflowBo.queryForHandleListBean(userInfo.getUserID(),
				condition, taskName);
		return list;
	}

	/**
	 * 执行根据修缮申请编号查询修缮申请信息
	 * 
	 * @param applyId
	 *            String 修缮申请编号
	 * @return Map 修缮申请信息包
	 * @throws Exception
	 */
	public Map viewApply(String applyId) throws Exception {
		if (!applyDao.judgeExistApply(applyId)) {
			return null;
		}
		if (!judge(applyId)) {
			return null;
		}
		String condition = " and remedyid='" + applyId + "' ";
		String condition1 = " and approve.object_id='" + applyId + "' ";
		oneApply = (ProjectRemedyApply) applyDao.viewOneApply(applyId);
		String cancelUserName = userInfoDao.getUserName(oneApply
				.getCancelUserId());
		oneApply.setCancelUserName(cancelUserName);
		// String statusName = applyDao.getRemedyApplyStatus(oneApply
		// .getState());
		String contractorName = contractorDao.getContractorName(oneApply
				.getContractorId());
		String townName = countyInfoDao.getTownName(oneApply.getTownId() + "");
		applyItemList = applyItemDao.queryList(condition);
		// approveList = approveDao.queryList(condition1);
		Map oneMap = packageInfo();
		return oneMap;
	}

	/**
	 * 执行根据修缮申请编号判断是否可以执行修缮申请的查看操作
	 * 
	 * @param applyId
	 *            String 修缮申请编号
	 * @return boolean 判断结果
	 */
	public boolean judge(String applyId) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 根据当前用户发送修缮管理的短信
	 * 
	 * @param user
	 *            UserInfo 当前用户
	 * @param userId
	 */
	public void sendMsg(UserInfo user, RemedyApplyBean bean, String[] userId,
			String msg) {
		String nextProcessManId = "";
		String simId = "";
		if (userId != null) {
			logger.info("修缮管理的短信内容：" + msg);
			logger.info("短信发送的目标手机号：" + simId);
			String sim = "";
			String mobiles = "";
			for (int i = 0; i < userId.length; i++) {
				logger.info("短信发送的目标人：" + userId[i]);
				sim = applyDao.getSendPhone(userId[i]);
				mobiles = mobiles + sim + ",";
				super.sendMessage(msg, sim);
				// if (sim != null && !sim.equals("")) {
				// smSendProxy.simpleSend(sim, msg, null, null, true);
				// }
			}
			// 保存短信记录
			SMHistory history = new SMHistory();
			history.setSimIds(mobiles);
			history.setSendContent(msg);
			history.setSendTime(new Date());
			// history.setSendState(sendState);
			history.setSmType(RemedyConstant.LP_REMEDY);
			history.setObjectId(bean.getId());
			history.setBusinessModule("sendtask");
			historyDAO.save(history);
		}
	}

	public List packageMaterialList(String applyId) {
		List list = new ArrayList();
		UseMaterial material;
		for (int i = 0; materialIdList != null && i < materialIdList.size(); i++) {
			material = new UseMaterial();
			material.setMaterialId((String) materialIdList.get(i));
			material.setObjectId(applyId);
			material.setStorageAddressId((String) materialStorageAddrList
					.get(i));
			material.setStorageType((String) materialStorageTypeList.get(i));
			material.setUsedNumber(Double
					.parseDouble((String) materialUseNumberList.get(i)));
			material.setUseType(RemedyConstant.MATERIAL_USE_TYPE);
			list.add(material);
		}
		return list;
	}

	public List packageReturnMaterailList(String applyId) {
		List list = new ArrayList();
		for (int i = 0; materialRecycleList != null
				&& i < materialRecycleList.size(); i++) {
			ReturnMaterial returnMaterial = new ReturnMaterial();
			returnMaterial.setObjectId(applyId);
			returnMaterial.setUseType(RemedyConstant.MATERIAL_USE_TYPE);
			returnMaterial.setMaterialId((String) materialRecycleList.get(i));
			returnMaterial
					.setStorageType((String) materialStorageTypeRecycleList
							.get(i));
			returnMaterial
					.setStorageAddressId((String) materialStorageAddrRecycleList
							.get(i));
			returnMaterial.setReturnNumber(Double
					.valueOf((String) materialUseNumberRecycleList.get(i)));
			list.add(returnMaterial);
		}
		return list;
	}

	/**
	 * 执行根据当前用户查询当前月份的修缮申请数量信息
	 * 
	 * @param request
	 *            HttpServletRequest 用户输入请求
	 * @param userInfo
	 * @param form
	 *            ActionForm 用户输入表单
	 * @return Integer 当前月份的修缮申请数量信息
	 */
	public Integer getRemedyApplyNumber(UserInfo userInfo) {
		ConditionGenerate conditionGenerate = new ConditionGenerate();
		String condition = conditionGenerate.getUserQueryCondition(userInfo);
		condition = condition + conditionGenerate.getSysDateCondition();
		List list = applyDao.queryList(condition);
		if (list != null && list.size() > 0) {
			return new Integer(list.size() + 1);
		}
		return new Integer(1);
	}

	/**
	 * 执行修缮申请信息的打包
	 * 
	 * @return Map 修缮申请信息包
	 * @throws Exception
	 */
	public Map packageInfo() throws Exception {
		Map oneMap = new HashMap();
		RemedyApplyBean applyBean = new RemedyApplyBean();
		BeanUtil.objectCopy(oneApply, applyBean);

		applyBean.setRemedyDate(DateUtil.UtilDate2Str(oneApply.getRemedyDate(),
				"yyyy/MM/dd")); // 去掉时分秒
		oneMap.put("one_apply", applyBean);
		oneMap.put("apply_item_list", applyItemList);
		// oneMap.put("reason_file_list", UploadUtil.getFileIdsList(
		// reasonFileList, "attachid"));
		// oneMap.put("reason_file_name_list", UploadUtil.getFileIdsList(
		// reasonFileList, "attachname"));
		// oneMap.put("solve_file_list",
		// UploadUtil.getFileIdsList(solveFileList,
		// "attachid"));
		// oneMap.put("solve_file_name_list", UploadUtil.getFileIdsList(
		// solveFileList, "attachname"));
		// if (approveList != null) {
		// oneMap.put("approve_list", approveList);
		// } else {
		// oneMap.put("approve_list", new ArrayList());
		// }
		return oneMap;
	}

	public List<ProjectRemedyApply> getToDoWork(UserInfo userInfo,
			String taskName) {
		List<ProjectRemedyApply> list = workflowBo.queryForHandleListBean(
				userInfo.getUserID(), "", taskName);
		list = setHadRead(list, userInfo);
		return setReadOnly(list, userInfo);
	}

	public List<ProjectRemedyApply> setHadRead(List<ProjectRemedyApply> list,
			UserInfo userInfo) {
		List<ProjectRemedyApply> remove = new ArrayList<ProjectRemedyApply>();
		for (ProjectRemedyApply apply : list) {
			if (apply.getState().equals(RemedyConstant.NEEDAPPROVE)) {
				// 是抄送人
				if (replyApproverDAO.isReadOnly(apply.getId(), userInfo
						.getUserID(), "LP_REMEDY")) {
					// 且抄送人已阅读--排除
					if (replyApproverDAO.isReaded(apply.getId(), userInfo
							.getUserID(), "LP_REMEDY")) {
						remove.add(apply);
					}
				}
			}
		}
		list.removeAll(remove);
		return list;
	}

	public List<ProjectRemedyApply> getHandeledWorks(UserInfo userInfo) {
		List<ProjectRemedyApply> list = applyDao.getHandeledWorks(userInfo);
		return list;
	}

	public List<ProjectRemedyApply> setReadOnly(List<ProjectRemedyApply> list,
			UserInfo userInfo) {
		boolean flag = true;
		for (ProjectRemedyApply project : list) {
			if (project.getState().equals(RemedyConstant.NEEDAPPROVE)) {
				if (!replyApproverDAO.isReadOnly(project.getId(), userInfo
						.getUserID(), "LP_REMEDY")) {
					flag = false;
				}
			}
			project.setFlag(flag);
		}
		return list;
	}

	public List<ProjectRemedyApply> getProjectForOverHaul(String deptId,
			OverHaulQueryBean queryBean) {
		return applyDao.getProjectForOverHaul(deptId, queryBean);
	}

	public void read(String id, String type, UserInfo userInfo) {
		replyApproverDAO.updateReader(userInfo.getUserID(), id, type);

		// 流程历史
		ProjectRemedyApply apply = applyDao.viewOneApply(id);
		workflowBo.saveProcessHistory(userInfo, apply, null, "", "", "已阅读", "");
	}

	@Override
	protected HibernateDao<ProjectRemedyApply, String> getEntityDao() {
		return applyDao;
	}

	public List queryForHandleRemedyNum(String condition, UserInfo userInfo,
			String taskName) {
		// TODO Auto-generated method stub
		List<ProjectRemedyApply> applys = getToDoWork(userInfo, taskName);
		List<Integer> list = new ArrayList<Integer>();
		int waitApplyTaskNum = 0;
		int waitApproveTaskNum = 0;
		if (applys != null && !applys.isEmpty()) {
			for (ProjectRemedyApply apply : applys) {
				if (ProjectRemedyWorkflowBO.APPLY_TASK.equals(apply
						.getFlowTaskName())) {
					waitApplyTaskNum++;
				}
				if (ProjectRemedyWorkflowBO.APPLY_APPROVE_TASK.equals(apply
						.getFlowTaskName())) {
					waitApproveTaskNum++;
				}
			}
		}
		list.add(waitApplyTaskNum);
		list.add(waitApproveTaskNum);
		return list;
	}

	public void cancelRemedy(RemedyApplyBean bean, UserInfo userInfo) {
		// TODO Auto-generated method stub
		ProjectRemedyApply apply = applyDao.get(bean.getId());
		applyDao.initObject(apply);
		apply.setCancelReason(bean.getCancelReason());
		apply.setCancelTime(new Date());
		apply.setCancelUserId(userInfo.getUserID());
		apply.setState(ProjectRemedyApply.CANCELED_STATE);
		applyDao.save(apply);
		String processInstanceId = "";
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processInstanceId = ProjectRemedyWorkflowBO.WORKFLOWNAME + "."
				+ apply.getId();
		processHistoryBean.initial(null, userInfo, processInstanceId,
				ModuleCatalog.PROJECT);
		processHistoryBean.setHandledTaskName("any");
		processHistoryBean.setNextOperateUserId("");
		processHistoryBean.setObjectId(apply.getId());
		processHistoryBean.setProcessAction("工程申请流程取消");
		processHistoryBean.setTaskOutCome("cancel");
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
			workflowBo.endProcessInstance(processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
	/**
	 * 查询代维
	 * 
	 * @param user
	 * @return
	 */
	public List<Contractor> getContractors(UserInfo user) {
		return applyDao.getContractors(user);
	}
}
