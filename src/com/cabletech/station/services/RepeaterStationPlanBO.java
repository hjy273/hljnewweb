package com.cabletech.station.services;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.station.beans.FlowWorkInfoBean;
import com.cabletech.station.beans.RepeaterStationPlanBean;
import com.cabletech.station.dao.AuditingHistoryDAO;
import com.cabletech.station.dao.RepeaterStationDAO;
import com.cabletech.station.dao.RepeaterStationInPlanDAO;
import com.cabletech.station.dao.RepeaterStationPlanDAO;
import com.cabletech.station.domainobjects.FlowWorkInfo;
import com.cabletech.station.domainobjects.RepeaterStationPlan;
import com.cabletech.station.domainobjects.RepeaterStationPlanItem;
import com.cabletech.station.template.BasicTemplate;
import com.cabletech.station.template.RepeaterStationPlanListTemplate;

/**
 * 中继站计划的业务操作类
 * 
 * @author yangjun
 * 
 */
public class RepeaterStationPlanBO extends BaseBO {
	public RepeaterStationPlanBO() {
		super.setBaseDao(new RepeaterStationPlanDAO());
	}

	/**
	 * 插入中继站计划信息
	 * 
	 * @param stationPlanBean
	 *            RepeaterStationPlanBean 中继站计划信息Bean
	 * @param stationListInPlan
	 *            List 中继站计划中的中继站编号列表
	 * @return String 返回执行动作状态编号
	 * @throws Exception
	 */
	public String insertRepeaterStationPlan(RepeaterStationPlanBean stationPlanBean, List stationListInPlan)
			throws Exception {
		super.setBaseDao(new RepeaterStationPlanDAO());
		RepeaterStationPlan stationPlan = new RepeaterStationPlan();
		BeanUtil.objectCopy(stationPlanBean, stationPlan);

		if (super.queryExistByName("RepeaterStationPlan", "plan_name", stationPlan.getPlanName())) {
			return ExecuteCode.EXIST_PLAN_ERR_CODE;
		}
		if (!judgeStationIsExist(stationListInPlan)) {
			return ExecuteCode.NOT_EXIST_STATION_ERR_CODE;
		}

		return writeRepeaterStationPlan(stationListInPlan, stationPlan, "insert");
	}

	/**
	 * 更新中继站计划信息
	 * 
	 * @param stationPlanBean
	 *            RepeaterStationPlanBean 中继站计划信息Bean
	 * @param stationListInPlan
	 *            List 中继站计划中的中继站编号列表
	 * @return String 返回执行动作状态编号
	 * @throws Exception
	 */
	public String updateRepeaterStationPlan(RepeaterStationPlanBean stationPlanBean, List stationListInPlan)
			throws Exception {
		super.setBaseDao(new RepeaterStationPlanDAO());

		if (!super.queryExistById(stationPlanBean.getTid(), RepeaterStationPlan.class)) {
			return ExecuteCode.NOT_EXIST_PLAN_ERR_CODE;
		}
		if (!isAllowPlan(stationPlanBean.getTid(), ExecuteCode.EDIT_ACTION)) {
			return ExecuteCode.NOT_EDIT_PLAN_ERR_CODE;
		}
		if (!judgeStationIsExist(stationListInPlan)) {
			return ExecuteCode.NOT_EXIST_STATION_ERR_CODE;
		}

		RepeaterStationPlan stationPlan = new RepeaterStationPlan();
		stationPlan = (RepeaterStationPlan) super.baseDao.load(stationPlanBean.getTid(), RepeaterStationPlan.class);
		if (super.queryExistByName("RepeaterStationPlan", "plan_name", stationPlanBean.getPlanName())) {
			if (!stationPlan.getPlanName().equals(stationPlanBean.getPlanName())) {
				return ExecuteCode.EXIST_PLAN_ERR_CODE;
			}
		}
		Date createDate = stationPlan.getCreateDate();
		BeanUtil.objectCopy(stationPlanBean, stationPlan);
		stationPlan.setCreateDate(createDate);

		return writeRepeaterStationPlan(stationListInPlan, stationPlan, "update");
	}

	/**
	 * 删除中继站计划信息
	 * 
	 * @param stationPlanBean
	 *            RepeaterStationPlanBean 中继站计划信息Bean
	 * @return String 返回执行动作状态编号
	 * @throws Exception
	 */
	public String deleteRepeaterStationPlan(RepeaterStationPlanBean stationPlanBean) throws Exception {
		super.setBaseDao(new RepeaterStationPlanDAO());

		if (!super.queryExistById(stationPlanBean.getTid(), RepeaterStationPlan.class)) {
			return ExecuteCode.NOT_EXIST_PLAN_ERR_CODE;
		}
		if (!isAllowPlan(stationPlanBean.getTid(), ExecuteCode.EDIT_ACTION)) {
			return ExecuteCode.NOT_EDIT_PLAN_ERR_CODE;
		}

		RepeaterStationPlan stationPlan = new RepeaterStationPlan();
		stationPlan = (RepeaterStationPlan) super.baseDao.load(stationPlanBean.getTid(), RepeaterStationPlan.class);
		BeanUtil.objectCopy(stationPlanBean, stationPlan);

		String operationCode = ExecuteCode.FAIL_CODE;
		operationCode = deleteAllStationInPlan(stationPlan.getTid());
		if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
			super.setBaseDao(new RepeaterStationPlanDAO());
			Object object = super.baseDao.delete(stationPlan);
			if (object != null) {
				return ExecuteCode.SUCCESS_CODE;
			} else {
				return ExecuteCode.FAIL_CODE;
			}
		}
		return ExecuteCode.FAIL_CODE;
	}

	/**
	 * 审核中继站计划信息
	 * 
	 * @param planId
	 *            String 中继站计划编号
	 * @param auditingHistory
	 *            FlowWorkInfoBean 中继站计划审核信息Bean
	 * @return String 返回执行动作状态编号
	 * @throws Exception
	 */
	public String auditingPlan(String planId, FlowWorkInfoBean auditingHistoryBean) throws Exception {
		super.setBaseDao(new RepeaterStationPlanDAO());
		String operationCode = ExecuteCode.FAIL_CODE;

		if (!super.queryExistById(planId, RepeaterStationPlan.class)) {
			return ExecuteCode.NOT_EXIST_PLAN_ERR_CODE;
		}
		if (!isAllowPlan(planId, ExecuteCode.AUDITING_ACTION)) {
			return ExecuteCode.NOT_AUDITING_PLAN_ERR_CODE;
		}

		operationCode = insertAuditingHistory(planId, auditingHistoryBean);
		if (ExecuteCode.SUCCESS_CODE.equals(operationCode)) {
			return updateRepeaterStationPlanAuditingInfo(planId, auditingHistoryBean);
		}
		return ExecuteCode.FAIL_CODE;
	}

	/**
	 * 根据中继站计划编号查看中继站计划信息
	 * 
	 * @param planId
	 *            String 中继站计划编号
	 * @return RepeaterStationPlanBean 返回中继站计划信息Bean
	 * @throws Exception
	 */
	public RepeaterStationPlanBean viewRepeaterStationPlan(String planId) throws Exception {
		super.setBaseDao(new RepeaterStationPlanDAO());
		RepeaterStationPlanBean stationPlanBean = new RepeaterStationPlanBean();
		RepeaterStationPlan stationPlan = (RepeaterStationPlan) super.baseDao.load(planId, RepeaterStationPlan.class);
		if (stationPlan != null) {
			BeanUtil.objectCopy(stationPlan, stationPlanBean);
		} else {
			stationPlanBean = null;
		}
		return stationPlanBean;
	}

	/**
	 * 根据查询条件获取中继站计划信息列表
	 * 
	 * @param condition
	 *            String 查询条件
	 * @return List 返回中继站计划信息列表
	 * @throws Exception
	 */
	public List queryRepeaterStationPlanList(String condition) throws Exception {
		super.setBaseDao(new RepeaterStationPlanDAO());
		List list = new ArrayList();
		List planList = super.baseDao.queryByCondition(condition);

		super.setBaseDao(new RepeaterStationInPlanDAO());
		List stationList = new ArrayList();
		DynaBean bean;
		String planId;
		StringBuffer stations = new StringBuffer("");
		int index = -1;
		for (int i = 0; planList != null && i < planList.size(); i++) {
			bean = (DynaBean) planList.get(i);
			planId = (String) bean.get("tid");
			stations = new StringBuffer("");
			stationList = super.baseDao.queryByCondition(" and plan_id='" + planId + "'");
			for (int j = 0; stationList != null && j < stationList.size() - 1; j++) {
				stations.append(((DynaBean) stationList.get(j)).get("station_name"));
				stations.append("，");
			}
			if (stationList != null && stationList.size() > 0) {
				index = stationList.size() - 1;
				stations.append(((DynaBean) stationList.get(index)).get("station_name"));
			}
			bean.set("station_name_dis", stations.toString());
			list.add(bean);
		}
		return list;
	}

	/**
	 * 根据中继站计划信息列表和响应对象进行中继站基本信息列表的导出
	 * 
	 * @param response
	 *            HttpServletResponse 响应对象
	 * @param list
	 *            List 中继站计划信息列表
	 * @throws Exception
	 */
	public void exportRepeaterStationPlanList(HttpServletResponse response, List list) throws Exception {
		initResponse(response, "中继站计划列表.xls");
		OutputStream out = response.getOutputStream();
		String urlPath = ReportConfig.getInstance().getUrlPath("report.repeaterstationplanlist");
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		BasicTemplate template = new RepeaterStationPlanListTemplate(urlPath);
		template.doExport(list, excelstyle);
		template.write(out);
	}

	/**
	 * 进行中继站计划信息的保存动作
	 * 
	 * @param stationListInPlan
	 *            List 中继站计划中中继站编号列表
	 * @param stationPlanBean
	 *            RepeaterStationPlanBean 中继站计划信息Bean
	 * @param actionType
	 *            String 保存动作的类型
	 * @return String 返回执行动作状态编号
	 * @throws Exception
	 */
	public String writeRepeaterStationPlan(List stationListInPlan, RepeaterStationPlan stationPlan, String actionType)
			throws Exception {
		super.setBaseDao(new RepeaterStationPlanDAO());
		String operationCode = ExecuteCode.FAIL_CODE;
		if ("update".equals(actionType)) {
			operationCode = deleteAllStationInPlan(stationPlan.getTid());
			if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
				return ExecuteCode.FAIL_CODE;
			}
		}
		if ("insert".equals(actionType)) {
			stationPlan.setTid(ora.getSeq("REPEATER_STATION_PLAN", 18));
		}

		operationCode = insertStationInPlan(stationListInPlan, stationPlan.getTid());
		if (ExecuteCode.FAIL_CODE.equals(operationCode)) {
			return ExecuteCode.FAIL_CODE;
		}

		super.setBaseDao(new RepeaterStationPlanDAO());
		Object object = null;
		if ("insert".equals(actionType)) {
			stationPlan.setCreateDate(new Date());
			object = super.baseDao.insert(stationPlan);
		}
		if ("update".equals(actionType)) {
			object = super.baseDao.update(stationPlan);
		}
		if (object != null) {
			return ExecuteCode.SUCCESS_CODE;
		} else {
			return ExecuteCode.FAIL_CODE;
		}
	}

	/**
	 * 更新指定中继站计划编号的中继站计划的审核信息
	 * 
	 * @param planId
	 *            String 中继站计划编号
	 * @param auditingHistoryBean
	 *            FlowWorkInfoBean 中继站计划审核信息Bean
	 * @return String 返回执行动作状态编号
	 * @throws Exception
	 */
	public String updateRepeaterStationPlanAuditingInfo(String planId, FlowWorkInfoBean auditingHistoryBean)
			throws Exception {
		super.setBaseDao(new RepeaterStationPlanDAO());
		String operationCode = ExecuteCode.FAIL_CODE;

		RepeaterStationPlan plan = (RepeaterStationPlan) super.baseDao.find("RepeaterStationPlan", " and tid='"
				+ planId + "'");
		plan.setValidateRemark(auditingHistoryBean.getValidateRemark());
		plan.setValidateResult(auditingHistoryBean.getValidateResult());
		plan.setValidateTime(new Date());
		plan.setValidateUserid(auditingHistoryBean.getValidateUserid());
		if (ExecuteCode.PASSED_ACTION.equals(auditingHistoryBean.getValidateResult())) {
			plan.setPlanState(StationConstant.PASSED_STATE);
		}
		if (ExecuteCode.NOT_PASSED_ACTION.equals(auditingHistoryBean.getValidateResult())) {
			plan.setPlanState(StationConstant.NOT_PASSED_STATE);
		}
		Object object = super.baseDao.update(plan);
		if (object != null) {
			operationCode = ExecuteCode.SUCCESS_CODE;
		}

		return operationCode;
	}

	/**
	 * 更新指定中继站计划编号的中继站计划状态
	 * 
	 * @param planId
	 *            String 中继站计划编号
	 * @param state
	 *            String 中继站计划状态
	 * @return String 返回执行动作状态编号
	 * @throws Exception
	 */
	public String updateRepeaterStationPlanState(String planId, String state) throws Exception {
		super.setBaseDao(new RepeaterStationPlanDAO());
		String operationCode = ExecuteCode.FAIL_CODE;

		RepeaterStationPlan plan = (RepeaterStationPlan) super.baseDao.load(planId, RepeaterStationPlan.class);
		plan.setPlanState(StationConstant.FINISHED_STATE);
		Object object = super.baseDao.update(plan);
		if (object != null) {
			operationCode = ExecuteCode.SUCCESS_CODE;
		}
		return operationCode;
	}

	/**
	 * 判断中继站计划是否可以进行某种类型的动作
	 * 
	 * @param planId
	 *            String 中继站计划编号
	 * @param actionType
	 *            String 动作的类型
	 * @return boolean 返回是否可以进行某种类型的动作的标记
	 * @throws Exception
	 */
	public boolean isAllowPlan(String planId, String actionType) throws Exception {
		super.setBaseDao(new RepeaterStationPlanDAO());
		boolean flag = false;

		StringBuffer condition = new StringBuffer("");
		condition.append(" and ");
		if (ExecuteCode.EDIT_ACTION.equals(actionType)) {
			condition.append(" (plan_state='");
			condition.append(StationConstant.WAIT_SUBMIT_STATE);
			condition.append("' or plan_state='");
			condition.append(StationConstant.NOT_PASSED_STATE);
			condition.append("') ");
		}
		if (ExecuteCode.AUDITING_ACTION.equals(actionType)) {
			condition.append(" plan_state='");
			condition.append(StationConstant.WAIT_AUDITING_STATE);
			condition.append("' ");
		}
		condition.append(" and tid='");
		condition.append(planId);
		condition.append("'");
		List list = super.baseDao.queryByCondition(condition.toString());
		if (list != null && list.size() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断中继站计划中的中继站是否都已经存在
	 * 
	 * @param stationList
	 *            List 中继站计划中的中继站编号列表
	 * @return boolean 返回中继站计划中的中继站是否都已经存在的标记
	 * @throws Exception
	 */
	public boolean judgeStationIsExist(List stationList) throws Exception {
		super.setBaseDao(new RepeaterStationDAO());
		boolean flag = false;

		String condition = "";
		flag = true;
		for (int i = 0; stationList != null && i < stationList.size(); i++) {
			condition = " and tid='" + stationList.get(i) + "' and station_state='" + StationConstant.IS_ACTIVED + "' ";
			List list = super.baseDao.queryByCondition(condition);
			System.out.println(list);
			if (list == null || list.size() == 0) {
				flag = false;
				break;
			}
		}

		return flag;
	}

	/**
	 * 插入中继站计划的审核历史信息
	 * 
	 * @param planId
	 *            String 中继站计划编号
	 * @param auditingHistory
	 *            FlowWorkInfoBean 中继站计划的审核历史实体
	 * @return String 返回执行动作状态编号
	 * @throws Exception
	 */
	public String insertAuditingHistory(String planId, FlowWorkInfoBean auditingHistoryBean) throws Exception {
		super.setBaseDao(new AuditingHistoryDAO());

		FlowWorkInfo auditingHistory = new FlowWorkInfo();
		BeanUtil.objectCopy(auditingHistoryBean, auditingHistory);
		auditingHistory.setValidateTime(new Date());
		auditingHistory.setSonmenuId(StationConstant.SONMENU);

		auditingHistory.setTid(ora.getSeq("FLOW_WORK_INFO", 18));
		Object object = super.baseDao.insert(auditingHistory);
		if (object != null) {
			return ExecuteCode.SUCCESS_CODE;
		}
		return ExecuteCode.FAIL_CODE;
	}

	/**
	 * 插入中继站计划的中继站信息
	 * 
	 * @param stationListInPlan
	 *            List 中继站计划的中继站编号列表
	 * @param planId
	 *            String 中继站计划编号
	 * @return String 返回执行动作状态编号
	 * @throws Exception
	 */
	public String insertStationInPlan(List stationListInPlan, String planId) throws Exception {
		super.setBaseDao(new RepeaterStationInPlanDAO());
		for (int i = 0; stationListInPlan != null && i < stationListInPlan.size(); i++) {
			RepeaterStationPlanItem item = new RepeaterStationPlanItem();
			item.setTid(ora.getSeq("REPEATER_STATION_PLAN_ITEM", "STATION_PLAN_ITEM", 18));
			item.setPlanId(planId);
			item.setRepeaterStationId((String) stationListInPlan.get(i));
			Object object = super.baseDao.insert(item);
			if (object == null) {
				return ExecuteCode.FAIL_CODE;
			}
		}
		return ExecuteCode.SUCCESS_CODE;
	}

	/**
	 * 删除某个中继站计划的所有中继站信息
	 * 
	 * @param planId
	 *            String 中继站计划编号
	 * @return String 返回执行动作状态编号
	 * @throws Exception
	 */
	public String deleteAllStationInPlan(String planId) throws Exception {
		super.setBaseDao(new RepeaterStationInPlanDAO());
		String condition = " and plan_id='" + planId + "'";
		List list = super.baseDao.findAll("RepeaterStationPlanItem", condition);
		super.baseDao.getHibernateTemplate().deleteAll(list);
		return ExecuteCode.SUCCESS_CODE;
	}

	/**
	 * 根据中继站计划编号获取中继站计划的中继站编号列表
	 * 
	 * @param planId
	 *            String 中继站计划编号
	 * @return List 返回中继站计划的中继站编号列表
	 * @throws Exception
	 */
	public List getStationListInPlan(String planId) throws Exception {
		super.setBaseDao(new RepeaterStationInPlanDAO());
		List list = new ArrayList();
		String condition = " and plan_id='" + planId + "'";
		list = super.baseDao.queryByCondition(condition);
		return list;
	}

	/**
	 * 根据中继站计划编号、中继站计划类型和中继站计划填写类型获取中继站计划中中继站的列表信息
	 * 
	 * @param planId
	 *            String 中继站计划编号
	 * @param tableType
	 *            String 中继站计划类型
	 * @param writeType
	 *            String 中继站计划填写类型
	 * @return List 返回中继站计划中中继站的列表信息
	 * @throws Exception
	 */
	public List getStationListForWriteInPlan(String planId, String tableType, String writeType) throws Exception {
		super.setBaseDao(new RepeaterStationInPlanDAO());
		List list = new ArrayList();

		String operationCode = "";
		if (StationConstant.NOT_WRITED_STATE.equals(writeType)) {
			operationCode = " not ";
		}

		String condition = " and repeater_station_id " + operationCode
				+ " in (select repeater_station_id from plan_patrol_result_" + tableType + " where plan_id='" + planId
				+ "') and plan_id='" + planId + "'";
		list = super.baseDao.queryByCondition(condition);
		return list;
	}

	/**
	 * 根据中继站计划编号获取中继站计划的审核历史信息列表
	 * 
	 * @param planId
	 *            String 中继站计划编号
	 * @return List 返回中继站计划的审核历史信息列表
	 * @throws Exception
	 */
	public List getAuditingHistoryList(String planId) throws Exception {
		super.setBaseDao(new AuditingHistoryDAO());
		List list = new ArrayList();
		String condition = " and object_id='" + planId + "' order by validate_time,tid";
		list = super.baseDao.queryByCondition(condition);
		return list;
	}

}
