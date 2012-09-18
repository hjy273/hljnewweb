package com.cabletech.linepatrol.remedy.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linepatrol.remedy.beans.RemedyApplyBean;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.constant.RemedyConstant;
import com.cabletech.linepatrol.remedy.constant.RemedyWorkflowConstant;
import com.cabletech.linepatrol.remedy.dao.MaterialDao;
import com.cabletech.linepatrol.remedy.dao.MaterialStorageDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyAttachDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyItemDao;
import com.cabletech.linepatrol.remedy.dao.RemedyApplyMaterialDao;
import com.cabletech.linepatrol.remedy.domain.RemedyApply;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.utils.StringUtil;

public class RemedyApplyManager extends RemedyBaseBO {
	private RemedyApplyDao applyDao;

	private RemedyApplyItemDao applyItemDao;

	private RemedyApplyMaterialDao applyMaterialDao;

	private RemedyApplyAttachDao applyAttachDao;

	private MaterialDao materialDao;

	private MaterialStorageDao materialStorageDao;

	public RemedyApplyManager() {
		logger.info("init start..............");
		applyDao = new RemedyApplyDao();
		applyItemDao = new RemedyApplyItemDao();
		applyMaterialDao = new RemedyApplyMaterialDao();
		applyAttachDao = new RemedyApplyAttachDao();
		materialDao = new MaterialDao();
		materialStorageDao = new MaterialStorageDao();
		logger.info("init end..............");
	}

	/**
	 * 执行根据用户输入组织具体查询条件
	 * 
	 * @param request
	 *            HttpServletRequest 用户输入请求
	 * @param form
	 *            ActionForm 用户输入表单
	 * @return String 查询条件
	 */
	public String compositeCondition(HttpServletRequest request, ActionForm form) {
		// TODO Auto-generated method stub
		logger(RemedyApplyManager.class);
		super.setConditionGenerate(new ConditionGenerate());
		String userQueryCondition = super.getConditionGenerate()
				.getUserQueryCondition(request, form);
		String inputCondition = super.getConditionGenerate().getInputCondition(
				request, form);
		String orderCondition = super.getConditionGenerate()
				.getOrderCondition();
		StringBuffer condition = new StringBuffer();
		condition.append(userQueryCondition);
		condition.append(inputCondition);
		condition.append(orderCondition);
		return condition.toString();
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
	public String insertApply(HttpServletRequest request, ActionForm form)
			throws Exception {
		logger(RemedyApplyManager.class);

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");

		RemedyApplyBean applyBean = (RemedyApplyBean) form;
		RemedyApply apply = new RemedyApply();
		try {
			BeanUtil.objectCopy(applyBean, apply);
			// 生成oracle的序号
			String applyId = ora.getSeq("LINEPATROL_REMEDY", 20);
			apply.setId(applyId);

			// List itemList =
			// StringUtil.convertStringArrayToList(applyBean.getItems());

			List itemIdList = StringUtil.convertStringArrayToList(applyBean
					.getItemId());
			List itemTypeIdList = StringUtil.convertStringArrayToList(applyBean
					.getItemTypeId());

			List materialIdList = StringUtil.convertStringArrayToList(applyBean
					.getMaterial());
			List materialStorageTypeList = StringUtil
					.convertStringArrayToList(applyBean
							.getMaterialStorageType());
			List materialUseNumberList = StringUtil
					.convertStringArrayToList(applyBean.getMaterialUseNumber());
			List materialStorageAddrList = StringUtil
					.convertStringArrayToList(applyBean
							.getMaterialStorageAddr());
			List materialStorageNumberList = StringUtil
					.convertStringArrayToList(applyBean
							.getMaterialStorageNumber());
			List materialUnitPriceList = StringUtil
					.convertStringArrayToList(applyBean.getMaterialUnitPrice());
			List materialPriceList = StringUtil
					.convertStringArrayToList(applyBean.getMaterialPrice());

			List reasonFileList = uploadFile(form, new ArrayList(),
					RemedyConstant.REASON_FILE_NAME);
			List solveFileList = uploadFile(form, new ArrayList(),
					RemedyConstant.SOLVE_FILE_NAME);
			if (applyDao.judgeExistSameApply(apply)) {
				return ExecuteCode.EXIST_SAME_APPLY_ERROR;
			}
			if (!applyItemDao.judgeExistItem(itemIdList)) {
				return ExecuteCode.NOT_EXIST_ITEM_ERROR;
			}
			if (!applyItemDao.judgeExistItemType(itemTypeIdList)) {
				return ExecuteCode.NOT_EXIST_ITEM_ERROR;
			}
			if (!materialDao.judgeExistMaterial(materialIdList)) {
				return ExecuteCode.NOT_EXIST_MATERIAL_ERROR;
			}
			if (!materialStorageDao.judgeEnoughStorage(materialIdList,
					materialUseNumberList, materialStorageAddrList,
					materialStorageTypeList)) {
				return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
			}
			if (!applyDao.judgeExistMtUsed(apply)) {
				return ExecuteCode.NOT_EXIST_MT_USED_ERROR;
			}

			String operationCode;
			// 保存修缮定额项信息
			operationCode = applyItemDao.saveItemList(applyId, applyBean
					.packageInfo());
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				applyDao.rollbackTransaction();
				return ExecuteCode.FAIL_CODE;
			}
			// 保存修缮使用材料信息
			operationCode = applyMaterialDao.saveMaterialList(applyId,
					materialIdList, materialUseNumberList,
					materialStorageAddrList, materialStorageTypeList,
					materialStorageNumberList, materialUnitPriceList,
					materialPriceList);
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				applyDao.rollbackTransaction();
				return ExecuteCode.FAIL_CODE;
			}
			// 保存修缮附件信息
			operationCode = applyAttachDao.saveAttachList(applyId,
					reasonFileList, RemedyConstant.REASON_FILE_TYPE);
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				applyDao.rollbackTransaction();
				return ExecuteCode.FAIL_CODE;
			}
			operationCode = applyAttachDao.saveAttachList(applyId,
					solveFileList, RemedyConstant.SOLVE_FILE_TYPE);
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				applyDao.rollbackTransaction();
				return ExecuteCode.FAIL_CODE;
			}
			// 执行工作流转
			String stepId = "";
			if (RemedyWorkflowConstant.SUBMITED_ACTION_TYPE.equals(applyBean
					.getIsSubmited())) {
				apply.setPrevState(RemedyWorkflowConstant.INIT_STEP_ID);
				operationCode = workflowBo.saveApply(apply, userInfo
						.getUserID());
				if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
					applyDao.rollbackTransaction();
					return ExecuteCode.FAIL_CODE;
				}
				apply.setPrevState(RemedyWorkflowConstant.NOT_SUBMITED_STATE);
				operationCode = workflowBo.submitApply(apply, userInfo
						.getUserID());
				if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
					applyDao.rollbackTransaction();
					return ExecuteCode.FAIL_CODE;
				}
				stepId = operationCode;
			}
			if (RemedyWorkflowConstant.NOT_SUBMITED_ACTION_TYPE
					.equals(applyBean.getIsSubmited())) {
				operationCode = workflowBo.saveApply(apply, userInfo
						.getUserID());
				if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
					applyDao.rollbackTransaction();
					return ExecuteCode.FAIL_CODE;
				}
				stepId = operationCode;
			}
			apply.setCurrentStepId(stepId);
			apply.setFirstStepApproveMan(apply.getNextProcessMan());
			// 保存修缮信息
			operationCode = applyDao.saveOneApply(apply,
					ExecuteCode.INSERT_ACTION_TYPE);
			if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
				applyDao.commitTransaction();
			} else {
				applyDao.rollbackTransaction();
			}
			// 设置短信发送的修缮项目名称和发送目标人编号
			if (RemedyWorkflowConstant.SUBMITED_ACTION_TYPE.equals(applyBean
					.getIsSubmited())) {
				super.setApplyProjectName(apply.getProjectName());
				super.setNextProcessManId(apply.getNextProcessMan());
			}
			return operationCode;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			applyDao.rollbackTransaction();
			logger.error(e);
			throw e;
		}
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
	public String updateApply(HttpServletRequest request, ActionForm form)
			throws Exception {
		logger(RemedyApplyManager.class);

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");

		RemedyApplyBean applyBean = (RemedyApplyBean) form;
		RemedyApply apply = new RemedyApply();
		try {
			BeanUtil.objectCopy(applyBean, apply);
			// List itemList =
			// StringUtil.convertStringArrayToList(applyBean.getItems());

			List itemIdList = StringUtil.convertStringArrayToList(applyBean
					.getItemId());
			List itemTypeIdList = StringUtil.convertStringArrayToList(applyBean
					.getItemTypeId());

			List materialIdList = StringUtil.convertStringArrayToList(applyBean
					.getMaterial());
			List materialStorageTypeList = StringUtil
					.convertStringArrayToList(applyBean
							.getMaterialStorageType());
			List materialUseNumberList = StringUtil
					.convertStringArrayToList(applyBean.getMaterialUseNumber());
			List materialStorageAddrList = StringUtil
					.convertStringArrayToList(applyBean
							.getMaterialStorageAddr());
			List materialStorageNumberList = StringUtil
					.convertStringArrayToList(applyBean
							.getMaterialStorageNumber());
			List materialUnitPriceList = StringUtil
					.convertStringArrayToList(applyBean.getMaterialPrice());
			List materialPriceList = StringUtil
					.convertStringArrayToList(applyBean.getMaterialPrice());
			String attachType = RemedyConstant.REASON_FILE_NAME;
			List reasonFileList = getUploadedAttachIdList(request, form,
					attachType);

			attachType = RemedyConstant.SOLVE_FILE_NAME;
			List solveFileList = getUploadedAttachIdList(request, form,
					attachType);

			if (!applyDao.judgeExistApply(apply.getId())) {
				return ExecuteCode.NO_DATA_ERROR;
			}
			if (!applyDao.judgeAllowEdited(apply.getId())) {
				return ExecuteCode.NOT_EDITED_APPLY_ERROR;
			}
			if (applyDao.judgeExistSameApply(apply)) {
				return ExecuteCode.EXIST_SAME_APPLY_ERROR;
			}
			if (!applyItemDao.judgeExistItem(itemIdList)) {
				return ExecuteCode.NOT_EXIST_ITEM_ERROR;
			}
			if (!applyItemDao.judgeExistItemType(itemTypeIdList)) {
				return ExecuteCode.NOT_EXIST_ITEM_ERROR;
			}
			if (!materialDao.judgeExistMaterial(materialIdList)) {
				return ExecuteCode.NOT_EXIST_MATERIAL_ERROR;
			}
			if (!materialStorageDao.judgeEnoughStorage(materialIdList,
					materialUseNumberList, materialStorageAddrList,
					materialStorageTypeList)) {
				return ExecuteCode.NOT_ENOUGH_STORAGE_ERROR;
			}
			if (!applyDao.judgeExistMtUsed(apply)) {
				return ExecuteCode.NOT_EXIST_MT_USED_ERROR;
			}

			String applyId = apply.getId() + "";
			String operationCode;
			// 删除旧的定额项信息
			operationCode = applyItemDao.deleteItemList(applyId);
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				return ExecuteCode.FAIL_CODE;
			}
			// 删除旧的修缮材料信息
			operationCode = applyMaterialDao.deleteMaterialList(applyId);
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				return ExecuteCode.FAIL_CODE;
			}
			// 删除旧的修缮附件信息
			operationCode = applyAttachDao.deleteAttachList(applyId,
					RemedyConstant.REASON_FILE_TYPE);
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				return ExecuteCode.FAIL_CODE;
			}
			operationCode = applyAttachDao.deleteAttachList(applyId,
					RemedyConstant.SOLVE_FILE_TYPE);
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				return ExecuteCode.FAIL_CODE;
			}
			// 保存修缮定额项信息
			operationCode = applyItemDao.saveItemList(applyId, applyBean
					.packageInfo());
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				return ExecuteCode.FAIL_CODE;
			}
			// 保存修缮使用材料信息
			operationCode = applyMaterialDao.saveMaterialList(applyId,
					materialIdList, materialUseNumberList,
					materialStorageAddrList, materialStorageTypeList,
					materialStorageNumberList,materialUnitPriceList,materialPriceList);
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				return ExecuteCode.FAIL_CODE;
			}
			// 保存修缮附件信息
			operationCode = applyAttachDao.saveAttachList(applyId,
					reasonFileList, RemedyConstant.REASON_FILE_TYPE);
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				return ExecuteCode.FAIL_CODE;
			}
			operationCode = applyAttachDao.saveAttachList(applyId,
					solveFileList, RemedyConstant.SOLVE_FILE_TYPE);
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				return ExecuteCode.FAIL_CODE;
			}
			// 执行工作流转
			String stepId = "";
			if (RemedyWorkflowConstant.SUBMITED_ACTION_TYPE.equals(applyBean
					.getIsSubmited())) {
				apply.setState(RemedyWorkflowConstant.SUBMITED_STATE);
				operationCode = workflowBo.submitApply(apply, userInfo
						.getUserID());
				if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
					return ExecuteCode.FAIL_CODE;
				}
				stepId = operationCode;
			}
			if (RemedyWorkflowConstant.NOT_SUBMITED_ACTION_TYPE
					.equals(applyBean.getIsSubmited())) {
				apply.setState(RemedyWorkflowConstant.NOT_SUBMITED_STATE);
				operationCode = workflowBo.saveApply(apply, userInfo
						.getUserID());
				if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
					return ExecuteCode.FAIL_CODE;
				}
				stepId = operationCode;
			}
			apply.setCurrentStepId(stepId);
			apply.setFirstStepApproveMan(apply.getNextProcessMan());

			// 保存修缮信息
			operationCode = applyDao.saveOneApply(apply,
					ExecuteCode.UPDATE_ACTION_TYPE);
			if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
				applyDao.commitTransaction();
			} else {
				applyDao.rollbackTransaction();
			}

			// 设置短信发送的修缮项目名称和发送目标人编号
			if (RemedyWorkflowConstant.SUBMITED_ACTION_TYPE.equals(applyBean
					.getIsSubmited())) {
				super.setApplyProjectName(apply.getProjectName());
				super.setNextProcessManId(apply.getNextProcessMan());
			}
			return operationCode;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			applyDao.rollbackTransaction();
			logger.error(e);
			throw e;
		}
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
		logger(RemedyApplyManager.class);
		try {
			String applyId = request.getParameter("apply_id");
			if (!applyDao.judgeExistApply(applyId)) {
				return ExecuteCode.NO_DATA_ERROR;
			}
			if (!applyDao.judgeAllowDeleted(applyId)) {
				return ExecuteCode.NOT_DELETED_APPLY_ERROR;
			}
			String operationCode;
			// 删除定额项信息
			operationCode = applyItemDao.deleteItemList(applyId);
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				return ExecuteCode.FAIL_CODE;
			}
			// 删除修缮使用材料信息
			operationCode = applyMaterialDao.deleteMaterialList(applyId);
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				return ExecuteCode.FAIL_CODE;
			}
			// 删除修缮附件信息
			operationCode = applyAttachDao.deleteAttachList(applyId,
					RemedyConstant.REASON_FILE_TYPE);
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				return ExecuteCode.FAIL_CODE;
			}
			operationCode = applyAttachDao.deleteAttachList(applyId,
					RemedyConstant.SOLVE_FILE_TYPE);
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				return ExecuteCode.FAIL_CODE;
			}
			// 保存修缮信息
			RemedyApply apply = (RemedyApply) applyDao.viewOneObject(
					RemedyApply.class, applyId);
			operationCode = applyDao.deleteOneApply(apply);
			if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
				applyDao.commitTransaction();
			} else {
				applyDao.rollbackTransaction();
			}
			return operationCode;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			applyDao.rollbackTransaction();
			logger.error(e);
			throw e;
		}
	}

	/**
	 * 文件上传
	 * 
	 * @param form
	 *            ActionForm
	 * @return String
	 */
	public List uploadFile(ActionForm form, ArrayList fileIdList,
			String paramName) {
		FormFile file = null;
		String fileId;
		String fileName;
		Map oneMap;
		// 取得上传的文件
		Hashtable files = form.getMultipartRequestHandler().getFileElements();
		if (files != null) {
			// 得到files的keys
			Enumeration enums = files.keys();
			String fileKey = null;
			// 遍历枚举
			while (enums.hasMoreElements()) {
				oneMap = new HashMap();
				// 取得key
				fileKey = (String) (enums.nextElement());
				if (fileKey.indexOf(paramName) != -1) {
					// 初始化每一个FormFile(接口)
					file = (FormFile) files.get(fileKey);

					if (file == null) {
						logger.error("file is null");
					} else {
						// 将文件存储到服务器并将路径写入数据库，返回记录ID
						fileId = SaveUploadFile.saveFile(file);
						if (fileId != null) {
							fileName = file.getFileName();
							oneMap.put("file_id", fileId);
							oneMap.put("file_name", fileName);
							fileIdList.add(oneMap);
						}
					}
				}
			}

		}
		// 处理上传文件结束=======================================

		return fileIdList;
	}

	/**
	 * 执行根据已经上传的附件获取新上传附件列表
	 * 
	 * @param request
	 *            HttpServletRequest 用户输入请求
	 * @param form
	 *            ActionForm 用户输入表单
	 * @param attachType
	 *            String 附件类型
	 * @return List 新上传附件列表
	 */
	public List getUploadedAttachIdList(HttpServletRequest request,
			ActionForm form, String attachType) {
		ArrayList fileIdList = new ArrayList();
		String[] delfileid = request.getParameterValues("delfileid"
				+ attachType); // 要删除的文件id号数组
		StringTokenizer st1 = new StringTokenizer(request
				.getParameter(attachType), ",");
		StringTokenizer st2 = new StringTokenizer(request
				.getParameter(attachType + "_name"), ",");
		Map oneMap = new HashMap();
		while (st1.hasMoreTokens() && st2.hasMoreTokens()) {
			oneMap = new HashMap();
			oneMap.put("file_id", st1.nextToken());
			oneMap.put("file_name", st2.nextToken());
			fileIdList.add(oneMap);
		}
		if (delfileid != null && fileIdList != null) {
			for (int i = fileIdList.size() - 1; i >= 0; i--) {
				oneMap = (Map) fileIdList.get(i);
				for (int j = 0; j < delfileid.length; j++) {
					if (oneMap != null
							&& oneMap.get("file_id").equals(delfileid[j])) {
						fileIdList.remove(i);
					}
				}
			}
		}
		List fileList = uploadFile(form, fileIdList, attachType);
		return fileList;
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
		logger(RemedyApplyManager.class);

		RemedyApply apply = new RemedyApply();
		try {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		return false;
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
		logger(RemedyApplyManager.class);
		return true;
	}

	/**
	 * 执行获取监理公司用户列表
	 * 
	 * @param applyState
	 *            String 修缮申请的状态
	 * @return boolean 获取监理公司用户列表
	 */
	public List getNextProcessManList(String applyState) {
		// TODO Auto-generated method stub
		try {
			super.setRemedyBaseDao(new RemedyApplyDao());
			List list = super.getRemedyBaseDao().getSuperviseUser();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		return null;
	}

}
