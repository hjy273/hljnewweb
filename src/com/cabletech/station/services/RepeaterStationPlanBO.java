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
 * �м�վ�ƻ���ҵ�������
 * 
 * @author yangjun
 * 
 */
public class RepeaterStationPlanBO extends BaseBO {
	public RepeaterStationPlanBO() {
		super.setBaseDao(new RepeaterStationPlanDAO());
	}

	/**
	 * �����м�վ�ƻ���Ϣ
	 * 
	 * @param stationPlanBean
	 *            RepeaterStationPlanBean �м�վ�ƻ���ϢBean
	 * @param stationListInPlan
	 *            List �м�վ�ƻ��е��м�վ����б�
	 * @return String ����ִ�ж���״̬���
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
	 * �����м�վ�ƻ���Ϣ
	 * 
	 * @param stationPlanBean
	 *            RepeaterStationPlanBean �м�վ�ƻ���ϢBean
	 * @param stationListInPlan
	 *            List �м�վ�ƻ��е��м�վ����б�
	 * @return String ����ִ�ж���״̬���
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
	 * ɾ���м�վ�ƻ���Ϣ
	 * 
	 * @param stationPlanBean
	 *            RepeaterStationPlanBean �м�վ�ƻ���ϢBean
	 * @return String ����ִ�ж���״̬���
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
	 * ����м�վ�ƻ���Ϣ
	 * 
	 * @param planId
	 *            String �м�վ�ƻ����
	 * @param auditingHistory
	 *            FlowWorkInfoBean �м�վ�ƻ������ϢBean
	 * @return String ����ִ�ж���״̬���
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
	 * �����м�վ�ƻ���Ų鿴�м�վ�ƻ���Ϣ
	 * 
	 * @param planId
	 *            String �м�վ�ƻ����
	 * @return RepeaterStationPlanBean �����м�վ�ƻ���ϢBean
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
	 * ���ݲ�ѯ������ȡ�м�վ�ƻ���Ϣ�б�
	 * 
	 * @param condition
	 *            String ��ѯ����
	 * @return List �����м�վ�ƻ���Ϣ�б�
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
				stations.append("��");
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
	 * �����м�վ�ƻ���Ϣ�б����Ӧ��������м�վ������Ϣ�б�ĵ���
	 * 
	 * @param response
	 *            HttpServletResponse ��Ӧ����
	 * @param list
	 *            List �м�վ�ƻ���Ϣ�б�
	 * @throws Exception
	 */
	public void exportRepeaterStationPlanList(HttpServletResponse response, List list) throws Exception {
		initResponse(response, "�м�վ�ƻ��б�.xls");
		OutputStream out = response.getOutputStream();
		String urlPath = ReportConfig.getInstance().getUrlPath("report.repeaterstationplanlist");
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		BasicTemplate template = new RepeaterStationPlanListTemplate(urlPath);
		template.doExport(list, excelstyle);
		template.write(out);
	}

	/**
	 * �����м�վ�ƻ���Ϣ�ı��涯��
	 * 
	 * @param stationListInPlan
	 *            List �м�վ�ƻ����м�վ����б�
	 * @param stationPlanBean
	 *            RepeaterStationPlanBean �м�վ�ƻ���ϢBean
	 * @param actionType
	 *            String ���涯��������
	 * @return String ����ִ�ж���״̬���
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
	 * ����ָ���м�վ�ƻ���ŵ��м�վ�ƻ��������Ϣ
	 * 
	 * @param planId
	 *            String �м�վ�ƻ����
	 * @param auditingHistoryBean
	 *            FlowWorkInfoBean �м�վ�ƻ������ϢBean
	 * @return String ����ִ�ж���״̬���
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
	 * ����ָ���м�վ�ƻ���ŵ��м�վ�ƻ�״̬
	 * 
	 * @param planId
	 *            String �м�վ�ƻ����
	 * @param state
	 *            String �м�վ�ƻ�״̬
	 * @return String ����ִ�ж���״̬���
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
	 * �ж��м�վ�ƻ��Ƿ���Խ���ĳ�����͵Ķ���
	 * 
	 * @param planId
	 *            String �м�վ�ƻ����
	 * @param actionType
	 *            String ����������
	 * @return boolean �����Ƿ���Խ���ĳ�����͵Ķ����ı��
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
	 * �ж��м�վ�ƻ��е��м�վ�Ƿ��Ѿ�����
	 * 
	 * @param stationList
	 *            List �м�վ�ƻ��е��м�վ����б�
	 * @return boolean �����м�վ�ƻ��е��м�վ�Ƿ��Ѿ����ڵı��
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
	 * �����м�վ�ƻ��������ʷ��Ϣ
	 * 
	 * @param planId
	 *            String �м�վ�ƻ����
	 * @param auditingHistory
	 *            FlowWorkInfoBean �м�վ�ƻ��������ʷʵ��
	 * @return String ����ִ�ж���״̬���
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
	 * �����м�վ�ƻ����м�վ��Ϣ
	 * 
	 * @param stationListInPlan
	 *            List �м�վ�ƻ����м�վ����б�
	 * @param planId
	 *            String �м�վ�ƻ����
	 * @return String ����ִ�ж���״̬���
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
	 * ɾ��ĳ���м�վ�ƻ��������м�վ��Ϣ
	 * 
	 * @param planId
	 *            String �м�վ�ƻ����
	 * @return String ����ִ�ж���״̬���
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
	 * �����м�վ�ƻ���Ż�ȡ�м�վ�ƻ����м�վ����б�
	 * 
	 * @param planId
	 *            String �м�վ�ƻ����
	 * @return List �����м�վ�ƻ����м�վ����б�
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
	 * �����м�վ�ƻ���š��м�վ�ƻ����ͺ��м�վ�ƻ���д���ͻ�ȡ�м�վ�ƻ����м�վ���б���Ϣ
	 * 
	 * @param planId
	 *            String �м�վ�ƻ����
	 * @param tableType
	 *            String �м�վ�ƻ�����
	 * @param writeType
	 *            String �м�վ�ƻ���д����
	 * @return List �����м�վ�ƻ����м�վ���б���Ϣ
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
	 * �����м�վ�ƻ���Ż�ȡ�м�վ�ƻ��������ʷ��Ϣ�б�
	 * 
	 * @param planId
	 *            String �м�վ�ƻ����
	 * @return List �����м�վ�ƻ��������ʷ��Ϣ�б�
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
