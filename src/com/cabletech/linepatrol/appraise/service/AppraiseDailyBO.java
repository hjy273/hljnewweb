package com.cabletech.linepatrol.appraise.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.baseinfo.services.UserInfoBO;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.dao.AppraiseDailyDao;
import com.cabletech.linepatrol.appraise.dao.AppraiseDailyMarkDao;
import com.cabletech.linepatrol.appraise.module.AppraiseDaily;
import com.cabletech.linepatrol.appraise.module.AppraiseDailyMark;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
import com.cabletech.linepatrol.appraise.module.AppraiseTransTable;

/**
 * 日常考核业务类
 * 
 * @author liusq
 * 
 */
public abstract class AppraiseDailyBO extends EntityManager<AppraiseDaily, String> {

	@Resource(name = "appraiseDailyDao")
	protected AppraiseDailyDao appraiseDailyDao;
	
	@Resource(name = "appraiseDailyMarkDao")
	private AppraiseDailyMarkDao dailyMarkDao;

	@Override
	protected HibernateDao<AppraiseDaily, String> getEntityDao() {
		return appraiseDailyDao;
	}

	/**
	 * 获得日常考核信息表中的数据，包括月考核表、代维单位、签约合同
	 * 
	 * @param contractorId
	 *            代维单位编号
	 * @param type
	 *            类型：是查看还是输入
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public abstract Map<String, Object> getAppraiseDailyData(String contractorId,String type, AppraiseDailyBean bean) throws ServiceException,Exception ;

	String setConId(AppraiseDaily appraiseDaily,String contractorId, AppraiseDailyBean bean) {
		appraiseDaily = appraiseDailyDao.findByUnique("id", bean.getId());
		if(appraiseDaily != null) {
			contractorId = appraiseDaily.getContractorId();
		}
		return contractorId;
	}

	void inputConNameToMap(String contractorId, Map<String, Object> map) throws Exception {
		ContractorBO contractorBO = new ContractorBO();
		String contractorName = "";
		if(StringUtils.isNotBlank(contractorId)){
			contractorName = contractorBO.getContractorNameByContractorById(contractorId);
		}
		map.put("contractorName", contractorName);
	}

	/**
	 * 保存日考核信息
	 * 
	 * @param request
	 */
	@Transactional
	public AppraiseDaily saveAppraiseDaily(AppraiseDaily appraiseDaily) {
		appraiseDailyDao.save(appraiseDaily);
		return appraiseDaily;
	}
	/**
	 * 保存bean
	 * 
	 * @param request
	 */
	public AppraiseDaily saveAppraiseDailyByBean(AppraiseDailyBean appraiseDailyBean){
		AppraiseDaily appraiseDaily = appraiseDailyBean.getAppriseDailyFromAppraiseDailyBean();
		appraiseDailyDao.save(appraiseDaily);
		return appraiseDaily;
	}
	/**
	 * 保存bean
	 * 
	 * @param request
	 */
	public AppraiseDaily saveAppraiseDailyByBean(List<AppraiseDailyBean> speicalBeans) {
		if (speicalBeans != null) {
			for (AppraiseDailyBean appraiseDailyBean : speicalBeans) {
				AppraiseDaily appraiseDaily = appraiseDailyBean.getAppriseDailyFromAppraiseDailyBean();
				appraiseDailyDao.save(appraiseDaily);
			}
		}
		return null;
	}

	/**
	 * 将日常考核中的数据转换为AppraiseTable对象
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<AppraiseTable> getAppraiseTable(AppraiseDailyBean bean) {
		List<AppraiseTable> appraiseTables=new ArrayList<AppraiseTable>();
		List<DynaBean> remarkList = appraiseDailyDao.getAppraiseDailyMarkInfo(bean);
		if (remarkList != null && !remarkList.isEmpty()) {
			addRemarkListIntoTable(appraiseTables, remarkList);
		}
		return appraiseTables;
	}

	private void addRemarkListIntoTable(List<AppraiseTable> appraiseTables, List<DynaBean> remarkList) {
		Set<String> itemSet = new HashSet<String>();
		Set<String> contentSet = new HashSet<String>();
		Set<String> tabSet = new HashSet<String>();
		AppraiseTransTable appraiseTransTable=new AppraiseTransTable();
		for (int i = 0; i < remarkList.size(); i++) {
			DynaBean remarkInfo = remarkList.get(i);
			appraiseTransTable.setRemarkInfo(remarkInfo);
			String itemId = (String) remarkInfo.get("item_id");
			String conId = (String) remarkInfo.get("con_id");
			String tabId=(String) remarkInfo.get("tab_id");
			if(tabSet.add(tabId)){
				appraiseTransTable=addTableIdIsTrue(appraiseTables, appraiseTransTable,remarkInfo);
			}
			if (itemSet.add(itemId)) {
				addItemIdIsTrue(contentSet,appraiseTransTable, conId);
			} else {
				addItemIdIsFalse(contentSet, appraiseTransTable, conId);
			}
			// 创建一个Rule
			appraiseTransTable.newRule();
			appraiseTransTable.addRule();
			// 将最后一个元素添加到Table中
			if (i == (remarkList.size() - 1)) {
				appraiseTransTable.inputTable();
				appraiseTables.add(appraiseTransTable.getAppraiseTable());
			}
		}
	}

	private AppraiseTransTable addTableIdIsTrue(List<AppraiseTable> appraiseTables, AppraiseTransTable appraiseTransTable,DynaBean remarkInfo) {
		if (appraiseTransTable.getAppraiseItem() != null) {
			appraiseTransTable.inputTable();
			appraiseTables.add(appraiseTransTable.getAppraiseTable());
		}
		appraiseTransTable=new AppraiseTransTable();
		appraiseTransTable.setRemarkInfo(remarkInfo);
		appraiseTransTable.newTable();
		return appraiseTransTable;
	}

	private void addItemIdIsFalse(Set<String> contentSet, AppraiseTransTable appraiseTransTable, String conId) {
		// 判断Content是否存在
		if (contentSet.add(conId)) {
			// 创建之前将原先创建的Content放入到Item中
			appraiseTransTable.addContent();
			appraiseTransTable.newContent();
		}
	}

	private void addItemIdIsTrue(Set<String> contentSet,AppraiseTransTable appraiseTransTable, String conId) {
		// 在创建Item项时将Content项放入Item中
		if (appraiseTransTable.getAppraiseItem() != null) {
			appraiseTransTable.inputTable();
		}
		// 创建Item项
		appraiseTransTable.newItem();
		// 创建一个Content
		appraiseTransTable.newContent();
		contentSet.add(conId);
	}
	
//	@Transactional(readOnly=true)
//	public List<AppraiseDaily> getDailyByContractNoAndMonth(String contractorId,String contractNo,String month) {
//		return appraiseDailyDao.getDailyByContractNoAndMonth(contractorId,contractNo,month);
//	}
	/**
	 * 
	 */
	@Transactional(readOnly=true)
	public List<Map<String, String>> getDailyMarkByBean(AppraiseResultBean appraiseResultBean,String ruleId,String typeName) {
		List<Map<String,String>> appraiseDailyMarks = new ArrayList<Map<String,String>>();
		Map<String, String> appMap = null;
		UserInfoBO userInfoBO=new UserInfoBO();
		Map<String,String> allUser=userInfoBO.loadAllUserIdAndName();
		String contractorId=appraiseResultBean.getContractorId();
		String contractNo=appraiseResultBean.getContractNO();
		String appraiseMonth=appraiseResultBean.getAppraiseTime();
		String startDate=appraiseResultBean.getStartDate();
		String endDate=appraiseResultBean.getEndDate();
		List<AppraiseDaily> appraiseDailys = appraiseDailyDao.getDailyByContractNoAndMonth(contractorId,contractNo,appraiseMonth,startDate,endDate,typeName);
		for(AppraiseDaily appraiseDaily:appraiseDailys){
			for(AppraiseDailyMark appraiseDailyMark:appraiseDaily.getAppraiseDailyMarkSet()){
					if(ruleId.equals(appraiseDailyMark.getRuleId())){
						appMap=new HashMap<String, String>();
						//月考核查看考核依据时，呈现考核模块，考核人，评分信息，标包，查看记录。
						appMap.put("id", appraiseDailyMark.getId());
						appMap.put("contractNo", (appraiseDaily.getContractNo().equals("")?"不限":appraiseDaily.getContractNo()));
						appMap.put("remark",appraiseDailyMark.getRemark());
						String module=appraiseDaily.getBusinessModule(); 
						if(!module.equals(appraiseDaily.BUSINESSMODULE_OTHER)){
							module=appraiseDaily.getBusinessModule().split("_")[1];
						}
						appMap.put("markDeductions", appraiseDailyMark.getMarkDeductions());
						appMap.put("businessId", appraiseDaily.getBusinessId());
						appMap.put("module", module);
						appMap.put("appraiser", allUser.get(appraiseDaily.getAppraiser()));
						appraiseDailyMarks.add(appMap);
					}
			}
		}
		return appraiseDailyMarks;
	}
	/**
	 * 通过查询日常考核记录
	 * @param contractorId
	 * @param contractNO
	 * @param appraiseMonth
	 * @param businessmoduleOther
	 * @return List<AppraiseDaily> 
	 */
	@Transactional(readOnly=true)
	public List<AppraiseDaily>  queryAppraiseDaily(String contractorId,String contractNo,String appraiseMonth,String businessmoduleOther) {
		List<AppraiseDaily> appraiseDailys = appraiseDailyDao.getDailyByContractNoAndMonth(contractorId,contractNo,appraiseMonth,null,null,businessmoduleOther);
		return appraiseDailys;
	}
	@Transactional(readOnly=true)
	public Map<String, String> getAppraiseDailyNumByRule(String tableId, AppraiseResultBean appraiseResultBean,String typeName) {
		return dailyMarkDao.queryAppraiseDailyMark(tableId,appraiseResultBean,typeName);
	}
//	@Transactional(readOnly=true)
//	public Map<String, String> getAppraiseDailyNumByRule(String tableId, String appraiseMonth, String contractorId,
//			String contractNO,String startDate,String endDate,String typeName) {
//		return dailyMarkDao.queryAppraiseDailyMark(tableId,contractorId,contractNO,appraiseMonth,startDate,endDate,typeName);
//	}
//	/**
//	 * 保存日常考核和专项日常考核信息
//	 * @param request
//	 */
//	public void saveDailyAndSpeical(HttpServletRequest request){
//		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)request.getSession().getAttribute("appraiseDailyDailyBean");
//		List<AppraiseDailyBean> speicalBeans=(List<AppraiseDailyBean>)request.getSession().getAttribute("appraiseDailySpecialBean");
//		saveAppraiseDailyByBean(appraiseDailyBean);
//		for(AppraiseDailyBean specialBean:speicalBeans){
//			saveAppraiseDailyByBean(specialBean);
//		}
//	}
	@Transactional
	public List<AppraiseDaily> getAppraiseDailyByBean(AppraiseDailyBean appraiseDailyBean,String type){
		return appraiseDailyDao.getAppraiseDailyByBean(appraiseDailyBean,type);
	}
}
