/***
 *
 * MtUsedManager.java
 * @auther <a href="kww@mail.cabletech.com.cn">kww</a>
 * 2009-10-10
 **/

package com.cabletech.linepatrol.material.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.HibernateSession;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.material.beans.MaterialUsedInfoBean;
import com.cabletech.linepatrol.material.beans.MtUsedBean;
import com.cabletech.linepatrol.material.dao.MtUsedDao;
import com.cabletech.linepatrol.material.dao.MtUsedStockAddrDao;
import com.cabletech.linepatrol.material.dao.MtUsedStockDao;
import com.cabletech.linepatrol.material.domain.LinePatrolManager;
import com.cabletech.linepatrol.material.domain.MtUsed;

@Service
@Transactional
public class MtUsedBo extends EntityManager<MtUsed, String> {
	private static Logger logger = Logger.getLogger(MtUsedBo.class.getName());

	private OracleIDImpl ora = new OracleIDImpl();

	private final static String CONTRACTOR_TYPE_2 = "2";// 代维公司

	private final static String CONTRACTOR_TYPE_3 = "3";// 监理公司

	@Resource(name = "mtUsedDao")
	private MtUsedDao mtUsedDao;

	@Resource(name = "mtUsedStockDao")
	private MtUsedStockDao mtUsedStockDao;

	@Resource(name = "mtUsedStockAddrDao")
	private MtUsedStockAddrDao mtUsedStockAddrDao;

	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;

	/**
	 * 得到移动用户
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getUserInfos() throws ServiceException {
		return mtUsedDao.getUserInfos();
	}

	/**
	 * 材料申请
	 * 
	 * @param bean
	 * @param request
	 * @return
	 */
	public boolean mtApply(MtUsedBean bean, HttpServletRequest request)
			throws ServiceException {
		try {
			String[] materialId = request.getParameterValues("materialId");
			String[] lastMonthStock = request
					.getParameterValues("lastMonthStock");
			String[] newAddedStock = request
					.getParameterValues("newAddedStock");
			String[] newUsedStock = request.getParameterValues("newUsedStock");
			String[] realStock = request.getParameterValues("realStock");
			List list = (List) request.getSession().getAttribute("STOCK_LIST");
			int tid = ora.getIntSeq("LINEPATROL_MT_USED");

			String[] seq;
			if (materialId != null && materialId.length > 0) {
				seq = ora.getSeqs("MT_USED_STOCK", 30, materialId.length);
				boolean flag = true;
				for (int i = 0; materialId != null && i < materialId.length; i++) {
					try {
						mtUsedStockDao
								.saveMtUsedStock(materialId[i],
										lastMonthStock[i], newAddedStock[i],
										newUsedStock[i], realStock[i], seq[i],
										tid + "");
					} catch (ServiceException e) {
						e.printStackTrace();
					}
				}
			}
			if (list != null && list.size() > 0) {
				MaterialUsedInfoBean oneBean;
				boolean flag = true;
				for (int i = 0; i < list.size(); i++) {
					oneBean = (MaterialUsedInfoBean) list.get(i);
					if (oneBean.getAddrid() != null
							&& oneBean.getAddrid().length > 0) {
						seq = ora.getSeqs("MT_USED_DETAIL", 30, oneBean
								.getAddrid().length);
						String addrId;
						String oldStock;
						String newStock;
						for (int j = 0; j < oneBean.getAddrid().length; j++) {
							addrId = oneBean.getAddrid()[j];
							oldStock = Float.toString(oneBean.getOldstock()[j]);
							newStock = Float.toString(oneBean.getNewstock()[j]);
							flag = mtUsedStockAddrDao.saveMtUsedStock(oneBean
									.getMtid(), addrId, oldStock, newStock,
									seq[j], tid + "");
							if (!flag) {
								HibernateSession.rollbackTransaction();
								return false;
							}
						}
					}
				}
			}
			bean.setMid(tid);
			return mtUsedDao.saveMtUesdBean(bean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据id查询盘点表
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public BasicDynaBean getUsedById(int id) throws ServiceException {
		String sql = "select state,creator,to_char(createtime,'yyyy-MM') createtime from lp_mt_used where id="
				+ id + "";
		QueryUtil query;
		try {
			query = new QueryUtil();
			List list = query.queryBeans(sql);
			if (list != null && list.size() > 0) {
				BasicDynaBean basic = (BasicDynaBean) list.get(0);
				return basic;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据id查询盘点审核表
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public BasicDynaBean getUsedAssessById(int id) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb
				.append(" select ass.assessor,to_char(used.createtime,'yyyy-MM') createtime  ");
		sb
				.append(" from LP_MT_USED_ASSESS ass,LP_MT_USED used ");
		sb
				.append(" where used.id = ass.mtusedid and ass.type='0' and ass.state='1' and ass.mtusedid="
						+ id);
		sb.append(" order by ass.id desc");
		QueryUtil query;
		try {
			query = new QueryUtil();
			List list = query.queryBeans(sb.toString());
			if (list != null && list.size() > 0) {
				BasicDynaBean basic = (BasicDynaBean) list.get(0);
				return basic;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 材料编辑
	 * 
	 * @param bean
	 * @param request
	 * @return
	 */
	public boolean mtApplyEdit(MtUsedBean bean, HttpServletRequest request)
			throws ServiceException {
		try {
			String[] materialId = request.getParameterValues("materialId");
			String[] lastMonthStock = request
					.getParameterValues("lastMonthStock");
			String[] newAddedStock = request
					.getParameterValues("newAddedStock");
			String[] newUsedStock = request.getParameterValues("newUsedStock");
			String[] realStock = request.getParameterValues("realStock");
			List list = (List) request.getSession().getAttribute("STOCK_LIST");

			boolean flag = true;
			try {
				mtUsedStockDao.deleteMtUsedStock(bean.getId());
				mtUsedStockAddrDao.deleteMtUsedStock(bean.getId());
			} catch (ServiceException e) {
				e.printStackTrace();
			}

			String[] seq;
			if (materialId != null && materialId.length > 0) {
				seq = ora.getSeqs("MT_USED_STOCK", 30, materialId.length);
				flag = true;
				for (int i = 0; materialId != null && i < materialId.length; i++) {
					try {
						mtUsedStockDao.saveMtUsedStock(materialId[i],
								lastMonthStock[i], newAddedStock[i],
								newUsedStock[i], realStock[i], seq[i], bean
										.getId());
					} catch (ServiceException e) {
						e.printStackTrace();
					}
				}
			}
			if (list != null && list.size() > 0) {
				MaterialUsedInfoBean oneBean;
				flag = true;
				for (int i = 0; i < list.size(); i++) {
					oneBean = (MaterialUsedInfoBean) list.get(i);
					if (oneBean.getAddrid() != null
							&& oneBean.getAddrid().length > 0) {
						seq = ora.getSeqs("MT_USED_DETAIL", 30, oneBean
								.getAddrid().length);
						String addrId;
						String oldStock;
						String newStock;
						for (int j = 0; j < oneBean.getAddrid().length; j++) {
							addrId = oneBean.getAddrid()[j];
							oldStock = Float.toString(oneBean.getOldstock()[j]);
							newStock = Float.toString(oneBean.getNewstock()[j]);
							flag = mtUsedStockAddrDao.saveMtUsedStock(oneBean
									.getMtid(), addrId, oldStock, newStock,
									seq[j], bean.getId());
							if (!flag) {
								HibernateSession.rollbackTransaction();
								return false;
							}
						}
					}
				}
			}
			return mtUsedDao.editMtUesdBean(bean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 材料删除
	 * 
	 * @param bean
	 * @return
	 */
	public boolean mtApplyDel(int id) throws ServiceException {
		try {
			return mtUsedDao.delMtUesdBean(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public BasicDynaBean getMtUsedBean(int id) throws ServiceException {
		return mtUsedDao.getId(id);
	}

	/**
	 * 获得监理公司信息
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getContractorByTpe3() throws ServiceException {
		return mtUsedDao.getAllContractor(CONTRACTOR_TYPE_3);
	}

	@Transactional(readOnly = true)
	public List getConditionByFinish() throws ServiceException {
		return mtUsedDao.getConditionByState(MtUsedBean.STATE_MOBILE_AGREE);
	}

	@Transactional(readOnly = true)
	public List getConditionByState0(String controler) throws ServiceException {
		return mtUsedDao.getConditionByState0(controler);
	}

	public boolean updateMtUsedBeanState(MtUsedBean bean)
			throws ServiceException {
		return mtUsedDao.updateMtUesdBeanState(bean);
	}

	@Transactional(readOnly = true)
	public BasicDynaBean getApplyAndApproveBeanId(int id)
			throws ServiceException {
		return mtUsedDao.getApplyAndApproveBeanId(id);
	}

	@Transactional(readOnly = true)
	public List getApplyBy0And1(String userid) throws ServiceException {
		return mtUsedDao.getApplyBy0And1(userid);
	}

	@Transactional(readOnly = true)
	public List getConditionByMobile(String contractorname, String createtime)
			throws ServiceException {
		return mtUsedDao.getConditionByMobile(contractorname, createtime,
				MtUsedBean.STATE_MOBILE_AGREE);
	}

	@Transactional(readOnly = true)
	public Map getDetailStorageMap(String month, String contractorId)
			throws ServiceException {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		List lastMonthStorageList = mtUsedDao.getLastMonthStorageList(
				contractorId, month);

		addNewStorageInfo(month, contractorId, map, lastMonthStorageList);
		addAllotInInfo(month, contractorId, map, lastMonthStorageList);
		addAllotOutInfo(month, contractorId, map, lastMonthStorageList);
		addRemedyInfo(month, contractorId, map, lastMonthStorageList);

		return map;
	}

	private void addRemedyInfo(String month, String contractorId, Map map,
			List lastMonthStorageList) throws ServiceException {
		Map oneMap;
		DynaBean bean;
		String materialId;
		String lastMonthStorage;
		String storageNumber;
		String usedNumber;
		String haveNumber;
		String materialName;
		List thisMonthRemedyList = mtUsedDao.getThisMonthRemedyList(
				contractorId, month);
		for (int i = 0; thisMonthRemedyList != null
				&& i < thisMonthRemedyList.size(); i++) {
			bean = (DynaBean) thisMonthRemedyList.get(i);
			materialId = (String) bean.get("materialid");
			materialName = mtUsedDao.getMaterialName(materialId);
			if (map != null && map.containsKey(materialId)) {
				oneMap = (Map) map.get(materialId);
				storageNumber = (String) oneMap.get("storage_number");
				usedNumber = (String) oneMap.get("used_number");
				if (storageNumber == null) {
					storageNumber = "0";
				}
				if (usedNumber == null) {
					usedNumber = "0";
				}
			} else {
				oneMap = new HashMap();
				storageNumber = "0";
				usedNumber = "0";
			}
			lastMonthStorage = getLastMonthStorage(bean, lastMonthStorageList,
					materialId);
			oneMap.put("last_month_storage", lastMonthStorage);
			oneMap.put("storage_number", storageNumber);
			usedNumber = Double.toString(Double.parseDouble(usedNumber)
					+ Double.parseDouble((String) bean.get("remedy_storage")));
			oneMap.put("used_number", usedNumber);
			haveNumber = Double.toString(Double.parseDouble(lastMonthStorage)
					+ Double.parseDouble(storageNumber)
					- Double.parseDouble(usedNumber));
			oneMap.put("have_number", haveNumber);
			oneMap.put("real_number", haveNumber);
			oneMap.put("material_name", materialName);
			map.put(materialId, oneMap);
		}
	}

	private void addAllotOutInfo(String month, String contractorId, Map map,
			List lastMonthStorageList) throws ServiceException {
		Map oneMap;
		DynaBean bean;
		String materialId;
		String lastMonthStorage;
		String storageNumber;
		String usedNumber;
		String haveNumber;
		String materialName;
		List thisMonthAllotOutList = mtUsedDao.getThisMonthAllotOutList(
				contractorId, month);
		for (int i = 0; thisMonthAllotOutList != null
				&& i < thisMonthAllotOutList.size(); i++) {
			bean = (DynaBean) thisMonthAllotOutList.get(i);
			materialId = (String) bean.get("materialid");
			materialName = mtUsedDao.getMaterialName(materialId);
			if (map != null && map.containsKey(materialId)) {
				oneMap = (Map) map.get(materialId);
				storageNumber = (String) oneMap.get("storage_number");
				usedNumber = (String) oneMap.get("used_number");
				if (storageNumber == null) {
					storageNumber = "0";
				}
				if (usedNumber == null) {
					usedNumber = "0";
				}
			} else {
				oneMap = new HashMap();
				lastMonthStorage = "0";
				storageNumber = "0";
				usedNumber = "0";
			}
			lastMonthStorage = getLastMonthStorage(bean, lastMonthStorageList,
					materialId);
			oneMap.put("last_month_storage", lastMonthStorage);
			usedNumber = Double
					.toString(Double.parseDouble(usedNumber)
							+ Double.parseDouble((String) bean
									.get("allot_out_storage")));
			oneMap.put("storage_number", storageNumber);
			oneMap.put("used_number", usedNumber);
			haveNumber = Double.toString(Double.parseDouble(lastMonthStorage)
					+ Double.parseDouble(storageNumber)
					- Double.parseDouble(usedNumber));
			oneMap.put("have_number", haveNumber);
			oneMap.put("real_number", haveNumber);
			oneMap.put("material_name", materialName);
			map.put(materialId, oneMap);
		}
	}

	private void addAllotInInfo(String month, String contractorId, Map map,
			List lastMonthStorageList) throws ServiceException {
		Map oneMap;
		DynaBean bean;
		String materialId;
		String lastMonthStorage;
		String storageNumber;
		String usedNumber;
		String materialName;
		String haveNumber;
		List thisMonthAllotInList = mtUsedDao.getThisMonthAllotInList(
				contractorId, month);
		for (int i = 0; thisMonthAllotInList != null
				&& i < thisMonthAllotInList.size(); i++) {
			bean = (DynaBean) thisMonthAllotInList.get(i);
			materialId = (String) bean.get("materialid");
			materialName = mtUsedDao.getMaterialName(materialId);
			if (map != null && map.containsKey(materialId)) {
				oneMap = (Map) map.get(materialId);
				storageNumber = (String) oneMap.get("storage_number");
				usedNumber = (String) oneMap.get("used_number");
				if (storageNumber == null) {
					storageNumber = "0";
				}
				if (usedNumber == null) {
					usedNumber = "0";
				}
			} else {
				oneMap = new HashMap();
				storageNumber = "0";
				usedNumber = "0";
			}
			lastMonthStorage = getLastMonthStorage(bean, lastMonthStorageList,
					materialId);
			oneMap.put("last_month_storage", lastMonthStorage);
			storageNumber = Double
					.toString(Double.parseDouble(storageNumber)
							+ Double.parseDouble((String) bean
									.get("allot_in_storage")));
			oneMap.put("storage_number", storageNumber);
			oneMap.put("used_number", usedNumber);
			haveNumber = Double.toString(Double.parseDouble(lastMonthStorage)
					+ Double.parseDouble(storageNumber)
					- Double.parseDouble(usedNumber));
			oneMap.put("have_number", haveNumber);
			oneMap.put("real_number", haveNumber);
			oneMap.put("material_name", materialName);
			map.put(materialId, oneMap);
		}
	}

	private void addNewStorageInfo(String month, String contractorId, Map map,
			List lastMonthStorageList) throws ServiceException {
		Map oneMap;
		DynaBean bean;
		String materialId;
		String lastMonthStorage;
		String storageNumber;
		String usedNumber;
		String haveNumber;
		String materialName;
		List thisMonthNewStorageList = mtUsedDao.getThisMonthNewStorageList(
				contractorId, month);
		for (int i = 0; thisMonthNewStorageList != null
				&& i < thisMonthNewStorageList.size(); i++) {
			bean = (DynaBean) thisMonthNewStorageList.get(i);
			materialId = (String) bean.get("materialid");
			materialName = mtUsedDao.getMaterialName(materialId);
			if (map != null && map.containsKey(materialId)) {
				oneMap = (Map) map.get(materialId);
				storageNumber = (String) oneMap.get("storage_number");
				usedNumber = (String) oneMap.get("used_number");
				if (storageNumber == null) {
					storageNumber = "0";
				}
				if (usedNumber == null) {
					usedNumber = "0";
				}
			} else {
				oneMap = new HashMap();
				storageNumber = "0";
				usedNumber = "0";
			}
			lastMonthStorage = getLastMonthStorage(bean, lastMonthStorageList,
					materialId);
			oneMap.put("last_month_storage", lastMonthStorage);
			storageNumber = Double.toString(Double.parseDouble(storageNumber)
					+ Double.parseDouble((String) bean.get("new_storage")));
			oneMap.put("storage_number", storageNumber);
			oneMap.put("used_number", usedNumber);
			haveNumber = Double.toString(Double.parseDouble(lastMonthStorage)
					+ Double.parseDouble(storageNumber)
					- Double.parseDouble(usedNumber));
			oneMap.put("have_number", haveNumber);
			oneMap.put("real_number", haveNumber);
			oneMap.put("material_name", materialName);
			map.put(materialId, oneMap);

		}
	}

	@Transactional(readOnly = true)
	private String getLastMonthStorage(DynaBean bean,
			List lastMonthStorageList, String materialId)
			throws ServiceException {
		DynaBean tmpBean;
		for (int j = 0; lastMonthStorageList != null
				&& j < lastMonthStorageList.size(); j++) {
			tmpBean = (DynaBean) lastMonthStorageList.get(j);
			if (materialId != null
					&& materialId.equals(tmpBean.get("materialid"))) {
				return (String) tmpBean.get("last_month_storage");
			}
		}
		return "0";
	}

	@Transactional(readOnly = true)
	public List getMtUsedStockBean(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return mtUsedStockDao.getMtUsedStockList(id);
	}

	public void processMap(Map detailStorageMap, List list)
			throws ServiceException {
		// TODO Auto-generated method stub
		DynaBean bean;
		String materialId;
		Map oneMap;
		for (int i = 0; list != null && i < list.size(); i++) {
			bean = (DynaBean) list.get(i);
			materialId = (String) bean.get("materialid");
			if (detailStorageMap.containsKey(bean.get("materialid"))) {
				oneMap = (Map) detailStorageMap.get(materialId);
				oneMap.put("real_number", bean.get("real_stock"));
				detailStorageMap.put(materialId, oneMap);
			}
		}
	}

	public boolean judgeExistMtUsed(String createtime, String contractorId,
			String id) throws ServiceException {
		// TODO Auto-generated method stub
		return mtUsedDao.judgeExistMtUsed(createtime, contractorId, id);
	}

	/**
	 * 短信发送给指定监理下用户
	 * 
	 * @param user
	 * @param conid
	 * @param title
	 */
	public void sendConMsg(UserInfo user, String conid, String title)
			throws ServiceException {
		try {
			List simIds = getUserMobileByDeptid(conid);
			if (simIds == null || simIds.size() == 0) {
				return;
			}
			String msg = "[材料模块]  " + title + " 等待您的处理 发送人："
					+ user.getUserName() + " " + SendSMRMI.MSG_NOTE;
			logger.info("材料管理的短信内容：" + msg);
			logger.info("短信发送的部门id：" + conid);
			logger.info("短信发送的目标手机号：" + simIds);
			for (int i = 0; i < simIds.size(); i++) {
				String simId = (String) simIds.get(i);
				logger.info("短信发送的目标手机号：" + simId);
				SendSMRMI.sendNormalMessage(user.getUserID(), simId, msg, "00");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("发送短信业务异常:", e);
		}
	}

	/**
	 * 短信发送给指定用户
	 * 
	 * @param user
	 * @param userid
	 * @param title
	 */
	public void sendOneMsg(UserInfo user, String userid, String title)
			throws ServiceException {
		try {
			String simId = getUserMobile(userid);
			String msg = "[材料模块]  " + title + " 等待您的处理 发送人："
					+ user.getUserName() + " " + SendSMRMI.MSG_NOTE;
			logger.info("材料管理的短信内容：" + msg);
			logger.info("短信发送的用户id：" + userid);
			logger.info("短信发送的目标手机号：" + simId);
			if (simId == null || simId.equals("")) {
				return;
			}
			SendSMRMI.sendNormalMessage(user.getUserID(), simId, msg, "00");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("发送短信业务异常:", e);
		}
	}

	/**
	 * 短信发送给指定用户
	 * 
	 * @param user
	 * @param userid
	 * @param title
	 */
	public void sendMsgForUsers(String id, UserInfo user, String userids,
			String title) throws ServiceException {
		try {
			QueryUtil query = new QueryUtil();
			String sql = "select u.phone ";
			sql = sql + " from userinfo u ";
			sql = sql + " where u.userid in(" + userids
					+ ") and u.phone is not null";
			List simIds = query.queryBeans(sql);
			// String msg = "[材料模块]  " + title + " 等待您的处理 发送人："
			// + user.getUserName() + " " + SendSMRMI.MSG_NOTE;
			String simids = "";
			String msg = "【备料】您有一个名称为" + title + "的材料盘点单等待您的及时处理。";
			logger.info("材料管理的短信内容：" + msg);
			logger.info("短信发送的用户id：" + userids);
			logger.info("短信发送的目标手机号：" + simIds);
			if (simIds != null && simIds.size() > 0) {
				for (int i = 0; i < simIds.size(); i++) {
					DynaBean bean = (DynaBean) simIds.get(i);
					String simId = (String) bean.get("phone");
					simids += simId + ",";
					logger.info("短信内容: "+simId +":"+msg);
					super.sendMessage(msg,simId);
					//smSendProxy.simpleSend(simId, msg, null, null, true);
				}
			}
			// 保存短信记录
			SMHistory history = new SMHistory();
			history.setSimIds(simids);
			history.setSendContent(msg);
			history.setSendTime(new Date());
			// history.setSendState(sendState);
			history.setSmType(LinePatrolManager.LP_MT_USED);
			history.setObjectId(id);
			history.setBusinessModule(LinePatrolManager.MATERIAL_MODULE);
			historyDAO.save(history);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("发送短信业务异常:", e);
		}
	}

	/**
	 * 执行根据用户编号获取用户的手机号码
	 * 
	 * @param conid
	 *            String 存放地点编号
	 * @return String 用户的手机号码
	 * @throws Exception
	 */
	public List getUserMobileByDeptid(String conid) throws Exception {
		List mobiles = new ArrayList();
		String sql = "select u.phone from userinfo u where u.state is null and  u.deptid='"
				+ conid + "' and u.phone is not null";
		try {
			QueryUtil queryUtil = new QueryUtil();
			List list = queryUtil.queryBeans(sql);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					DynaBean bean = (DynaBean) list.get(i);
					String mobile = (String) bean.get("phone");
					System.out.println(mobile);
					mobiles.add(mobile);
				}
				System.out.println("mobiles==== " + mobiles.size());
				return mobiles;
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw e;
		}
	}

	/**
	 * 执行根据用户编号获取用户的手机号码
	 * 
	 * @param userId
	 *            String 存放地点编号
	 * @return String 用户的手机号码
	 * @throws Exception
	 */
	public String getUserMobile(String userId) throws Exception {
		String sql = "select u.phone ";
		sql = sql + " from userinfo u ";
		sql = sql + " where u.userid='" + userId + "'";
		try {
			QueryUtil queryUtil = new QueryUtil();
			List list = queryUtil.queryBeans(sql);
			if (list != null && list.size() > 0) {
				DynaBean bean = (DynaBean) list.get(0);
				String mobile = (String) bean.get("phone");
				return mobile;
			}
			return "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw e;
		}
	}

	@Override
	protected HibernateDao<MtUsed, String> getEntityDao() {
		// TODO Auto-generated method stub
		return mtUsedDao;
	}

	public MtUsedDao getMtUsedDao() {
		return mtUsedDao;
	}

	public void setMtUsedDao(MtUsedDao mtUsedDao) {
		this.mtUsedDao = mtUsedDao;
	}

	public MtUsedStockDao getMtUsedStockDao() {
		return mtUsedStockDao;
	}

	public void setMtUsedStockDao(MtUsedStockDao mtUsedStockDao) {
		this.mtUsedStockDao = mtUsedStockDao;
	}

	public MtUsedStockAddrDao getMtUsedStockAddrDao() {
		return mtUsedStockAddrDao;
	}

	public void setMtUsedStockAddrDao(MtUsedStockAddrDao mtUsedStockAddrDao) {
		this.mtUsedStockAddrDao = mtUsedStockAddrDao;
	}
}
