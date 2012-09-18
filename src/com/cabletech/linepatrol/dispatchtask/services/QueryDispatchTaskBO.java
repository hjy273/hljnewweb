package com.cabletech.linepatrol.dispatchtask.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.tags.dao.DictionaryDao;
import com.cabletech.commons.tags.module.Dictionary;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.dispatchtask.beans.DispatchTaskBean;
import com.cabletech.linepatrol.dispatchtask.dao.QueryDispatchTaskDao;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTask;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;
import com.cabletech.linepatrol.dispatchtask.template.SendTaskTemplate;

@Service
@Transactional
public class QueryDispatchTaskBO extends EntityManager<DispatchTask, String> {

	@Resource(name = "queryDispatchTaskDao")
	private QueryDispatchTaskDao dao;
	@Resource(name = "dictionaryDao")
	private DictionaryDao dictionaryDao;

	private Properties config;
	private DecimalFormat df = new DecimalFormat("000.000");

	@Override
	protected HibernateDao<DispatchTask, String> getEntityDao() {
		return dao;
	}

	public QueryDispatchTaskDao getQueryDispatchTaskDao() {
		return dao;
	}

	/**
	 * <br>
	 * 功能:条件查询统计 <br>
	 * 参数:request <br>
	 * 返回值:获得成功返回List,否则返回 NULL;
	 */
	public List queryTotalList(UserInfo userInfo, DispatchTaskBean bean) {
		ConditionGenerate conditionGenerate = new ConditionGenerate();
		String condition = conditionGenerate.getUserQueryCondition(userInfo);
		condition += conditionGenerate.getInputCondition(bean);
		List list = dao.queryTotalList(condition);
		return list;
	}

	/**
	 * 个人工单数量信息统计
	 * 
	 * @param userinfo
	 * @param bean
	 * @return
	 */
	public List queryPersonTotalNumList(UserInfo userInfo,
			DispatchTaskBean formBean) {
		ConditionGenerate conditionGenerate = new ConditionGenerate();
		String condition = conditionGenerate.getUserQueryCondition(userInfo);
		condition += conditionGenerate.getInputCondition(formBean);
		List list = new ArrayList();
		List dispatchTaskList = dao.queryDispatchTaskList(condition,
				DispatchTaskConstant.USER_SUMMIZE_CONDITION);
		List waitHandleDispatchTaskList = dao.queryWaitHandleDispatchTaskList(
				condition, DispatchTaskConstant.USER_SUMMIZE_CONDITION);
		List refuseDispatchTaskList = dao.queryRefuseDispatchTaskList(
				condition, DispatchTaskConstant.USER_SUMMIZE_CONDITION);
		List transferDispatchTaskList = dao.queryTransferDispatchTaskList(
				condition, DispatchTaskConstant.USER_SUMMIZE_CONDITION);
		List checkDispatchTaskList = dao.queryCheckDispatchTaskList(condition,
				DispatchTaskConstant.USER_SUMMIZE_CONDITION);
		List replyOutTimeDispatchTaskList = dao
				.queryReplyOutTimeDispatchTaskList(condition,
						DispatchTaskConstant.USER_SUMMIZE_CONDITION);
		List checkOutTimeDispatchTaskList = dao
				.queryCheckOutTimeDispatchTaskList(condition,
						DispatchTaskConstant.USER_SUMMIZE_CONDITION);
		DynaBean bean;
		DynaBean tmpBean;
		String[] propertyName;
		for (int i = 0; dispatchTaskList != null && i < dispatchTaskList.size(); i++) {
			bean = (DynaBean) dispatchTaskList.get(i);
			if (bean != null) {
				propertyName = new String[] { "wait_sign_in_num",
						"wait_reply_num", "wait_check_num" };
				processBean(waitHandleDispatchTaskList, bean, propertyName,
						"userid");

				propertyName = new String[] { "refuse_num" };
				processBean(refuseDispatchTaskList, bean, propertyName,
						"userid");

				propertyName = new String[] { "transfer_num" };
				processBean(transferDispatchTaskList, bean, propertyName,
						"userid");

				propertyName = new String[] { "complete_num" };
				processBean(checkDispatchTaskList, bean, propertyName, "userid");

				propertyName = new String[] { "reply_out_time_num" };
				processBean(replyOutTimeDispatchTaskList, bean, propertyName,
						"userid");

				propertyName = new String[] { "check_out_time_num" };
				processBean(checkOutTimeDispatchTaskList, bean, propertyName,
						"userid");

				list.add(bean);
			}
		}
		getSendTypeLabel(formBean);
		formBean.setAcceptdeptname(dao
				.getDepartName(formBean.getAcceptdeptid()));
		formBean.setAcceptusername(dao.getUserName(formBean.getAcceptuserid()));
		return list;
	}

	/**
	 * 显示个人工单信息
	 * 
	 * @param bean
	 * @param queryFlg
	 * @param userid
	 * @param session
	 * @return
	 */
	public List queryPersonTotalList(UserInfo userInfo,
			DispatchTaskBean formBean) {
		return null;
	}

	/**
	 * 部门工单信息统计
	 * 
	 * @param userinfo
	 * @param bean
	 * @return
	 */
	public List queryDepartTotalList(UserInfo userInfo,
			DispatchTaskBean formBean) {
		ConditionGenerate conditionGenerate = new ConditionGenerate();
		String condition = conditionGenerate.getUserQueryCondition(userInfo);
		condition += conditionGenerate.getInputCondition(formBean);
		List list = new ArrayList();
		List dispatchTaskList = dao.queryDispatchTaskList(condition,
				DispatchTaskConstant.DEPT_SUMMIZE_CONDITION);
		List waitHandleDispatchTaskList = dao.queryWaitHandleDispatchTaskList(
				condition, DispatchTaskConstant.DEPT_SUMMIZE_CONDITION);
		List refuseDispatchTaskList = dao.queryRefuseDispatchTaskList(
				condition, DispatchTaskConstant.DEPT_SUMMIZE_CONDITION);
		List transferDispatchTaskList = dao.queryTransferDispatchTaskList(
				condition, DispatchTaskConstant.DEPT_SUMMIZE_CONDITION);
		List checkDispatchTaskList = dao.queryCheckDispatchTaskList(condition,
				DispatchTaskConstant.DEPT_SUMMIZE_CONDITION);
		List replyOutTimeDispatchTaskList = dao
				.queryReplyOutTimeDispatchTaskList(condition,
						DispatchTaskConstant.DEPT_SUMMIZE_CONDITION);
		List checkOutTimeDispatchTaskList = dao
				.queryCheckOutTimeDispatchTaskList(condition,
						DispatchTaskConstant.DEPT_SUMMIZE_CONDITION);
		DynaBean bean;
		DynaBean tmpBean;
		String[] propertyName;
		for (int i = 0; dispatchTaskList != null && i < dispatchTaskList.size(); i++) {
			bean = (DynaBean) dispatchTaskList.get(i);
			if (bean != null) {
				propertyName = new String[] { "wait_sign_in_num",
						"wait_reply_num", "wait_check_num" };
				processBean(waitHandleDispatchTaskList, bean, propertyName,
						"departid");

				propertyName = new String[] { "refuse_num" };
				processBean(refuseDispatchTaskList, bean, propertyName,
						"departid");

				propertyName = new String[] { "transfer_num" };
				processBean(transferDispatchTaskList, bean, propertyName,
						"departid");

				propertyName = new String[] { "complete_num" };
				processBean(checkDispatchTaskList, bean, propertyName,
						"departid");

				propertyName = new String[] { "reply_out_time_num" };
				processBean(replyOutTimeDispatchTaskList, bean, propertyName,
						"departid");

				propertyName = new String[] { "check_out_time_num" };
				processBean(checkOutTimeDispatchTaskList, bean, propertyName,
						"departid");

				calculateRatio(bean);

				list.add(bean);
			}
		}
		getSendTypeLabel(formBean);
		formBean.setAcceptdeptname(dao
				.getDepartName(formBean.getAcceptdeptid()));
		return list;
	}

	public void calculateRatio(DynaBean bean) {
		double ratio;
		if (bean.get("sum_num") != null && !bean.get("sum_num").equals("")) {
			int sumNum = Integer.parseInt((String) bean.get("sum_num"));
			if (sumNum != 0) {
				if (bean.get("reply_out_time_num") != null
						&& !bean.get("reply_out_time_num").equals("")) {
					int replyOutTimeNum = Integer.parseInt((String) bean
							.get("reply_out_time_num"));
					ratio = 0;
					ratio = 100.0 * (sumNum - replyOutTimeNum) / sumNum;
					bean.set("reply_in_time_ratio", "" + ratio);
				}
				if (bean.get("check_out_time_num") != null
						&& !bean.get("check_out_time_num").equals("")) {
					int checkOutTimeNum = Integer.parseInt((String) bean
							.get("check_out_time_num"));
					ratio = 0;
					ratio = 100.0 * (sumNum - checkOutTimeNum) / sumNum;
					bean.set("check_in_time_ratio", "" + ratio);
				}
			} else {
				bean.set("reply_in_time_ratio", "0");
				bean.set("check_in_time_ratio", "0");
			}
		}
	}

	public SendTaskTemplate exportTotalResult(List list) throws Exception {
		config = getReportConfig();
		String urlPath = getUrlPath(config, "report.querytotalresult");
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		SendTaskTemplate template = new SendTaskTemplate(urlPath);
		template.exportQueryTotalResult(list, excelstyle);
		if (template != null) {
			logger.info("输出excel成功");
			return template;
		} else {
			return null;
		}
	}

	public SendTaskTemplate exportPersonTotalResult(List list) throws Exception {
		config = getReportConfig();
		String urlPath = getUrlPath(config, "report.querypersontotalresult");
		SendTaskTemplate template = new SendTaskTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportQueryPersonTotalResult(list);
		if (template != null) {
			logger.info("输出excel成功");
			return template;
		} else {
			return null;
		}
	}

	public SendTaskTemplate exportPersonNumTotalResult(List list,
			DispatchTaskBean bean) throws Exception {
		config = getReportConfig();
		String urlPath = getUrlPath(config, "report.querynumtotalresult");
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		SendTaskTemplate template = new SendTaskTemplate(urlPath);
		template.exportPersonNumTotalResult(list, bean, excelstyle);
		if (template != null) {
			logger.info("输出excel成功");
			return template;
		} else {
			return null;
		}
	}

	public SendTaskTemplate exportDepartTotalResult(List list,
			DispatchTaskBean bean) throws Exception {
		config = getReportConfig();
		String urlPath = getUrlPath(config, "report.querydepttotalresult");
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		SendTaskTemplate template = new SendTaskTemplate(urlPath);
		template.exportDepartTotalResult(list, bean, excelstyle);
		if (template != null) {
			logger.info("输出excel成功");
			return template;
		} else {
			return null;
		}
	}

	/**
	 * <br>
	 * 功能:获得受理部门列表 <br>
	 * 参数:用户对象 <br>
	 * 返回:受理部门编号和名称的List
	 */
	public List getAcceptdept(UserInfo userinfo) {
		return dao.getAcceptdept(userinfo);
	}

	/**
	 * <br>
	 * 功能:获得受理人列表 <br>
	 * 参数:用户对象 <br>
	 * 返回:受理人编号和名称的List
	 */
	public List getAcceptUser(UserInfo userinfo) {
		return dao.getAcceptUser(userinfo);
	}

	/**
	 * 处理查询后的DynaBean
	 * 
	 * @param list
	 * @param bean
	 * @param propertyName
	 * @param key
	 */
	private void processBean(List list, DynaBean bean, String[] propertyName,
			String key) {
		DynaBean tmpBean;
		for (int i = 0; list != null && i < list.size(); i++) {
			tmpBean = (DynaBean) list.get(i);
			if (tmpBean != null) {
				if (tmpBean.get(key) != null
						&& tmpBean.get(key).equals(bean.get(key))) {
					for (int j = 0; propertyName != null
							&& j < propertyName.length; j++) {
						bean.set(propertyName[j], tmpBean.get(propertyName[j]));
					}
				}
			}
		}
	}

	/**
	 * @param formBean
	 */
	private void getSendTypeLabel(DispatchTaskBean formBean) {
		if (formBean.getSendtype() != null
				&& !formBean.getSendtype().equals("")) {
			String hql = " from Dictionary d where d.code=? and d.assortmentId='dispatch_task' ";
			List sendTypeLabelList = dictionaryDao.find(hql, formBean
					.getSendtype());
			if (sendTypeLabelList != null && !sendTypeLabelList.isEmpty()) {
				Dictionary d = (Dictionary) sendTypeLabelList.get(0);
				formBean.setSendtypelable(d.getLable());
			}
		}
	}

}
