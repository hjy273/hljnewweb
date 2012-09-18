package com.cabletech.linepatrol.material.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.material.beans.MaterialPutStorageBean;
import com.cabletech.linepatrol.material.dao.MaterialAddressStorageDao;
import com.cabletech.linepatrol.material.dao.MaterialApplyDao;
import com.cabletech.linepatrol.material.dao.MaterialPutStorageDao;
import com.cabletech.linepatrol.material.dao.MaterialPutStorageItemDao;
import com.cabletech.linepatrol.material.dao.MaterialStorageDao;
import com.cabletech.linepatrol.material.domain.MaterialApply;
import com.cabletech.linepatrol.material.domain.MaterialPutStorage;
import com.cabletech.linepatrol.material.domain.MaterialPutStorageItem;
import com.cabletech.linepatrol.material.workflow.MaterialPutStorageWorkflowBO;

@Service
@Transactional
public class MaterialPutStorageBo extends
		EntityManager<MaterialPutStorage, String> {
	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Resource(name = "materialApplyDao")
	private MaterialApplyDao applyDao;
	@Resource(name = "materialPutStorageDao")
	private MaterialPutStorageDao dao;
	@Resource(name = "materialPutStorageItemDao")
	private MaterialPutStorageItemDao itemDao;
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDao;
	@Resource(name = "approveDAO")
	private ApproveDAO approveDao;
	@Resource(name = "materialAddressStorageDao")
	private MaterialAddressStorageDao addrStorageDao;
	@Resource(name = "materialStorageDao")
	private MaterialStorageDao storageDao;
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userDao;
	@Resource(name = "materialPutStorageWorkflowBO")
	private MaterialPutStorageWorkflowBO workflowBo;

	/**
	 * 执行添加材料入库信息
	 * 
	 * @param bean
	 * @param userInfo
	 * @throws Exception
	 */
	public void addMaterialPutStorage(MaterialPutStorageBean bean,
			UserInfo userInfo) throws Exception {
		bean.setCreator(userInfo.getUserID());
		MaterialPutStorage putStorage = new MaterialPutStorage();
		BeanUtil.objectCopy(bean, putStorage);
		dao.initObject(putStorage);
		putStorage.setContractorId(userInfo.getDeptID());
		putStorage.setCreateDate(new Date());
		dao.save(putStorage);
		MaterialApply apply = applyDao.get(putStorage.getApplyId());
		if(CommonConstant.SUBMITED.equals(bean.getIsSubmited())){
			apply.setState(MaterialApply.LP_PUT_STORAGE_SUBMIT);
		}else{
			apply.setState(MaterialApply.LP_PUT_STORAGE_SAVE);
		}
		applyDao.save(apply);
		bean.setId(putStorage.getId());

		saveItems(bean, putStorage);

		String approverId = bean.getApproverId() + "," + bean.getReaderId();
		approverDao.saveApproverOrReader(bean.getApproverId(), putStorage
				.getId(), CommonConstant.APPROVE_MAN,
				MaterialPutStorage.LP_MT_STORAGE);
		approverDao.saveApproverOrReader(bean.getReaderId(),
				putStorage.getId(), CommonConstant.APPROVE_READ,
				MaterialPutStorage.LP_MT_STORAGE);

		if (CommonConstant.SUBMITED.equals(bean.getIsSubmited())) {
			Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
					bean.getApplyId(),
					MaterialPutStorageWorkflowBO.PUT_STORAGE_TASK);
			if (task != null) {
				workflowBo.setVariables(task, "assignee", approverId);
				workflowBo.completeTask(task.getId(), "approve");
				logger.info("材料入库提交确认.....................");
				// processHistoryBO.saveProcessHistory(task.getExecutionId(),
				// task, approverId, userInfo.getUserID(), bean
				// .getApplyId(), ModuleCatalog.MATERIAL);
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				processHistoryBean.initial(task, userInfo, "",
						ModuleCatalog.MATERIAL);
				processHistoryBean.setNextOperateUserId(approverId);
				processHistoryBean.setObjectId(bean.getApplyId());
				processHistoryBean.setProcessAction("材料入库提交确认");
				processHistoryBean.setTaskOutCome("approve");
				processHistoryBO.saveProcessHistory(processHistoryBean);
			}
			sendMessage(bean.getTitle(), bean.getApplyId(), bean
					.getApproverId(), "审核");
			sendMessage(bean.getTitle(), bean.getApplyId(), bean.getReaderId(),
					"批阅");
		}
	}

	/**
	 * 执行修改材料入库信息
	 * 
	 * @param bean
	 * @param userInfo
	 * @throws Exception
	 */
	public void modMaterialPutStorage(MaterialPutStorageBean bean,
			UserInfo userInfo) throws Exception {
		MaterialPutStorage putStorage = dao.get(bean.getId());
		dao.initObject(putStorage);
		BeanUtil.objectCopy(bean, putStorage);
		dao.initObject(putStorage);
		putStorage.setContractorId(userInfo.getDeptID());
		dao.save(putStorage);
		MaterialApply apply = applyDao.get(putStorage.getApplyId());
		if(CommonConstant.SUBMITED.equals(bean.getIsSubmited())){
			apply.setState(MaterialApply.LP_PUT_STORAGE_SUBMIT);
		}else{
			apply.setState(MaterialApply.LP_PUT_STORAGE_SAVE);
		}
		applyDao.save(apply);
		List itemList = itemDao.find(
				" from MaterialPutStorageItem t where putStorageId=? ",
				putStorage.getId());
		itemDao.getHibernateTemplate().deleteAll(itemList);
		saveItems(bean, putStorage);

		approverDao.deleteList(putStorage.getId(),
				MaterialPutStorage.LP_MT_STORAGE);
		String approverId = bean.getApproverId() + "," + bean.getReaderId();
		approverDao.saveApproverOrReader(bean.getApproverId(), putStorage
				.getId(), CommonConstant.APPROVE_MAN,
				MaterialPutStorage.LP_MT_STORAGE);
		approverDao.saveApproverOrReader(bean.getReaderId(),
				putStorage.getId(), CommonConstant.APPROVE_READ,
				MaterialPutStorage.LP_MT_STORAGE);

		if (CommonConstant.SUBMITED.equals(bean.getIsSubmited())) {
			Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
					putStorage.getApplyId(),
					MaterialPutStorageWorkflowBO.PUT_STORAGE_TASK);
			if (task != null) {
				workflowBo.setVariables(task, "assignee", approverId);
				workflowBo.completeTask(task.getId(), "approve");
				logger.info("材料入库提交确认.....................");
				// processHistoryBO.saveProcessHistory(task.getExecutionId(),
				// task, approverId, userInfo.getUserID(), bean
				// .getApplyId(), ModuleCatalog.MATERIAL);
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				processHistoryBean.initial(task, userInfo, "",
						ModuleCatalog.MATERIAL);
				processHistoryBean.setNextOperateUserId(approverId);
				processHistoryBean.setObjectId(bean.getApplyId());
				processHistoryBean.setProcessAction("材料入库提交确认");
				processHistoryBean.setTaskOutCome("approve");
				processHistoryBO.saveProcessHistory(processHistoryBean);
			}
			sendMessage(bean.getTitle(), bean.getApplyId(), bean
					.getApproverId(), "审核");
			sendMessage(bean.getTitle(), bean.getApplyId(), bean.getReaderId(),
					"批阅");
		}
	}

	/**
	 * 执行删除材料入库信息
	 * 
	 * @param putStorageId
	 */
	public void delMaterialPutStorage(String putStorageId) {
		// TODO Auto-generated method stub
		MaterialPutStorage putStorage = dao.get(putStorageId);
		dao.initObject(putStorage);
		dao.delete(putStorage);

		List itemList = itemDao.find(
				" from MaterialPutStorageItem t where putStorageId=? ",
				putStorage.getId());
		itemDao.getHibernateTemplate().deleteAll(itemList);

		approverDao.deleteList(putStorage.getId(),
				MaterialPutStorage.LP_MT_STORAGE);
	}

	/**
	 * 执行查询材料入库信息列表
	 * 
	 * @param bean
	 * @param userInfo
	 * @return
	 */
	public List queryMaterialPutStorageList(MaterialPutStorageBean bean,
			UserInfo userInfo) {
		return null;
	}

	/**
	 * 执行查看材料入库详细信息
	 * 
	 * @param putStorageId
	 * @return
	 * @throws Exception
	 */
	public Map viewMaterialPutStorage(String putStorageId) throws Exception {
		MaterialPutStorage putStorage = dao.get(putStorageId);
		dao.initObject(putStorage);
		MaterialPutStorageBean bean = new MaterialPutStorageBean();
		BeanUtil.objectCopy(putStorage, bean);
		bean.setCreatorName(userDao.getUserName(bean.getCreator()));

		List itemList = itemDao.queryList(" and t.storageid='" + putStorageId
				+ "' ");
		String[] materialTypeId = null;
		String[] materialModelId = null;
		String[] materialId = null;
		String[] materialAddressId = null;
		String[] materialTypeName = null;
		String[] materialModelName = null;
		String[] materialName = null;
		String[] materialAddressName = null;
		String[] materialUnit = null;
		String[] applyCount = null;
		String[] count = null;

		if (itemList != null && !itemList.isEmpty()) {
			materialTypeId = new String[itemList.size()];
			materialModelId = new String[itemList.size()];
			materialId = new String[itemList.size()];
			materialAddressId = new String[itemList.size()];
			materialTypeName = new String[itemList.size()];
			materialModelName = new String[itemList.size()];
			materialName = new String[itemList.size()];
			materialAddressName = new String[itemList.size()];
			materialUnit = new String[itemList.size()];
			applyCount = new String[itemList.size()];
			count = new String[itemList.size()];
			DynaBean tmpBean;
			for (int i = 0; i < itemList.size(); i++) {
				tmpBean = (DynaBean) itemList.get(i);
				materialTypeId[i] = (String) tmpBean.get("typeid");
				materialModelId[i] = (String) tmpBean.get("modelid");
				materialId[i] = (String) tmpBean.get("materialid");
				materialAddressId[i] = (String) tmpBean.get("addressid");
				materialTypeName[i] = (String) tmpBean.get("typename");
				materialModelName[i] = (String) tmpBean.get("modelname");
				materialName[i] = (String) tmpBean.get("name");
				materialAddressName[i] = (String) tmpBean.get("address");
				materialUnit[i] = (String) tmpBean.get("unit");
				applyCount[i] = (String) tmpBean.get("applycount");
				count[i] = (String) tmpBean.get("count");
			}
		}
		bean.setMaterialTypeId(materialTypeId);
		bean.setMaterialModelId(materialModelId);
		bean.setMaterialId(materialId);
		bean.setMaterialTypeName(materialTypeName);
		bean.setMaterialModelName(materialModelName);
		bean.setMaterialName(materialName);
		bean.setMaterialUnit(materialUnit);
		bean.setAddressId(materialAddressId);
		bean.setAddressName(materialAddressName);
		bean.setApplyCount(applyCount);
		bean.setCount(count);

		Map map = new HashMap();
		map.put("put_storage_bean", bean);
		return map;
	}

	/**
	 * 执行审核材料入库信息
	 * 
	 * @param bean
	 * @param userInfo
	 * @throws Exception
	 */
	public void approveMaterialPutStorage(MaterialPutStorageBean bean,
			UserInfo userInfo) throws Exception {
		ApproveInfo approve = new ApproveInfo();
		BeanUtil.objectCopy(bean, approve);
		approve.setApproverId(userInfo.getUserID());
		approve.setApproveTime(new Date());
		approve.setObjectId(bean.getPutStorageId());
		approve.setObjectType(MaterialPutStorage.LP_MT_STORAGE);
		approveDao.initObject(approve);
		approveDao.save(approve);

		MaterialApply apply = applyDao.get(bean.getApplyId());
		dao.initObject(apply);
		if(CommonConstant.APPROVE_RESULT_TRANSFER.equals(approve.getApproveResult())){
			apply.setState(MaterialApply.LP_PUT_STORAGE_TRANS);
		}else{
			if(CommonConstant.APPROVE_RESULT_PASS.equals(approve.getApproveResult())){
				apply.setState(MaterialApply.LP_PUT_STORAGE_PASS);
			}else{
				apply.setState(MaterialApply.LP_PUT_STORAGE_NOT_PASS);
			}
		}
		applyDao.save(apply);
		if (CommonConstant.APPROVE_RESULT_TRANSFER.equals(approve
				.getApproveResult())) {
			approverDao.saveApproverOrReader(bean.getApproverId(), bean
					.getPutStorageId(), CommonConstant.APPROVE_TRANSFER_MAN,
					MaterialPutStorage.LP_MT_STORAGE);
		}

		if (CommonConstant.APPROVE_RESULT_PASS.equals(approve
				.getApproveResult())) {
			List itemList = itemDao.find(
					" from MaterialPutStorageItem t where putStorageId=? ",
					bean.getPutStorageId());
			addrStorageDao.saveList(itemList);
			storageDao.saveList(itemList, bean.getContractorId());
		}

		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), bean
				.getApplyId(),
				MaterialPutStorageWorkflowBO.PUT_STORAGE_CONFIRM_TASK);
		if (task != null) {
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.setObjectId(bean.getApplyId());
			processHistoryBean.initial(task, userInfo, "",
					ModuleCatalog.MATERIAL);
			if (CommonConstant.APPROVE_RESULT_NO.equals(approve
					.getApproveResult())) {
				workflowBo.setVariables(task, "assignee", bean.getCreator());
				workflowBo.completeTask(task.getId(), "not_passed");
				logger.info("材料入库提交确认不通过.....................");
				// processHistoryBO.saveProcessHistory(task.getExecutionId(),
				// task, bean.getCreator(), userInfo.getUserID(), bean
				// .getApplyId(), ModuleCatalog.MATERIAL);
				processHistoryBean.setNextOperateUserId(bean.getCreator());
				processHistoryBean.setProcessAction("材料入库确认");
				processHistoryBean.setTaskOutCome("not_passed");
			}
			if (CommonConstant.APPROVE_RESULT_TRANSFER.equals(approve
					.getApproveResult())) {
				workflowBo.setVariables(task, "assignee", bean.getApproverId());
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				logger.info("材料入库提交确认转审.....................");
				// processHistoryBO.saveProcessHistory(task.getExecutionId(),
				// task, bean.getApproverId(), userInfo.getUserID(), bean
				// .getApplyId(), ModuleCatalog.MATERIAL);
				processHistoryBean.setNextOperateUserId(bean.getApproverId());
				processHistoryBean.setProcessAction("材料入库确认转审");
				processHistoryBean.setTaskOutCome("transfer");
			}
			if (CommonConstant.APPROVE_RESULT_PASS.equals(approve
					.getApproveResult())) {
				workflowBo.setVariables(task, "transition", "end");
				workflowBo.completeTask(task.getId(), "passed");
				logger.info("材料入库提交确认通过.....................");
				// processHistoryBO.saveProcessHistory(task.getExecutionId(),
				// task, "", userInfo.getUserID(), bean.getApplyId(),
				// ModuleCatalog.MATERIAL);
				processHistoryBean.setNextOperateUserId("");
				processHistoryBean.setProcessAction("材料入库确认");
				processHistoryBean.setTaskOutCome("end");
			}
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
		if (CommonConstant.APPROVE_RESULT_PASS.equals(approve
				.getApproveResult())) {
			String content = "【备料】您有一个名称为" + bean.getTitle()
					+ "的材料入库单已经确认审核通过。";
			sendMessageBySelfContent(content, bean.getApplyId(), bean
					.getCreator());
		}
		if (CommonConstant.APPROVE_RESULT_TRANSFER.equals(approve
				.getApproveResult())) {
			sendMessage(bean.getTitle(), bean.getApplyId(), bean
					.getApproverId(), "审核");
		}
		if (CommonConstant.APPROVE_RESULT_NO.equals(approve.getApproveResult())) {
			sendMessage(bean.getTitle(), bean.getApplyId(), bean.getCreator(),
					"重新入库");
		}
	}

	/**
	 * 执行批阅材料入库信息
	 * 
	 * @param bean
	 * @param userInfo
	 * @throws Exception
	 */
	public void readApproveMaterialPutStorage(MaterialPutStorageBean bean,
			UserInfo userInfo) throws Exception {
		String putStorageId = bean.getPutStorageId();
		approverDao.updateReader(userInfo.getUserID(), putStorageId,
				MaterialPutStorage.LP_MT_STORAGE);
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), bean
				.getApplyId(),
				MaterialPutStorageWorkflowBO.PUT_STORAGE_CONFIRM_TASK);
		if (task != null) {
			processHistoryBean.initial(task, userInfo, "",
					ModuleCatalog.MATERIAL);
			processHistoryBean.setObjectId(bean.getApplyId());
			processHistoryBean.setNextOperateUserId("");
			processHistoryBean.setProcessAction("材料申请批阅");
			processHistoryBean.setTaskOutCome("read");
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
	}

	/**
	 * 执行查询材料入库审核信息列表
	 * 
	 * @param putStorageId
	 * @return
	 */
	public List queryMaterialPutStorageApproveInfoList(String putStorageId) {
		String condition = " and approve.object_id='" + putStorageId + "' ";
		condition += " and approve.object_type='LP_MT_STORAGE' ";
		List approveInfoList = approveDao.queryList(condition);
		return approveInfoList;
	}

	/**
	 * 执行发送材料入库短信
	 * 
	 * @param title
	 * @param applyId
	 * @param actionString
	 * @param mobiles
	 */
	public void sendMessage(String title, String applyId, String userIds,
			String actionString) {
		String content = "【备料】您有一个名称为" + title + "的材料入库单等待您的" + actionString
				+ "!";
		sendMessageBySelfContent(content, applyId, userIds);
	}

	/**
	 * 执行发送材料入库短信
	 * 
	 * @param title
	 * @param applyId
	 * @param mobiles
	 */
	public void sendMessageBySelfContent(String content, String applyId,
			String userIds) {
		if (StringUtils.isNotBlank(userIds)) {
			String[] userId;
			if (userIds.indexOf(",") != -1) {
				userId = userIds.split(",");
			} else {
				userId = new String[1];
				userId[0] = userIds;
			}
			String sim = "";
			String mobiles = "";
			for (int i = 0; i < userId.length; i++) {
				sim = applyDao.getSendPhone(userId[i]);
				mobiles = mobiles + sim + ",";
				// smSendProxy.simpleSend(sim, content, null, null, true);
			}
			logger.info("短信内容:" + content);
			logger.info("短信号码:" + mobiles);
			if (mobiles != null && !mobiles.equals("")) {
				super.sendMessage(content, mobiles);
			}
			// for (String mobile : mobileList) {
			// smSendProxy.simpleSend(mobile, content, null, null, true);
			// logger.info("短信内容: " + mobile + ":" + content);
			// }
			// 保存短信记录
			SMHistory history = new SMHistory();
			history.setSimIds(mobiles);
			history.setSendContent(content);
			history.setSendTime(new Date());
			// history.setSendState(sendState);
			history.setSmType(MaterialPutStorage.LP_MT_STORAGE);
			history.setObjectId(applyId);
			history.setBusinessModule(MaterialApply.MATERIAL_MODULE);
			historyDAO.save(history);
		}
	}

	/**
	 * 执行保存材料入库的材料入库项目信息
	 * 
	 * @param bean
	 * @param putStorage
	 */
	public void saveItems(MaterialPutStorageBean bean,
			MaterialPutStorage putStorage) {
		String[] materialIds = bean.getMaterialId();
		MaterialPutStorageItem item;
		int addressId = 0;
		int materialId = 0;
		double count = 0;
		double applyCount = 0;
		for (int i = 0; materialIds != null && i < materialIds.length; i++) {
			addressId = Integer.parseInt(bean.getAddressId()[i]);
			materialId = Integer.parseInt(materialIds[i]);
			count = Double.parseDouble(bean.getCount()[i]);
			applyCount = Double.parseDouble(bean.getApplyCount()[i]);
			item = new MaterialPutStorageItem();
			item.setAddressId(addressId);
			item.setPutStorageId(putStorage.getId());
			item.setCount(count);
			item.setApplyCount(applyCount);
			item.setMaterialId(materialId);
			item.setState(putStorage.getType());
			itemDao.initObject(item);
			itemDao.getSession().save(item);
		}
	}

	@Override
	protected HibernateDao<MaterialPutStorage, String> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
