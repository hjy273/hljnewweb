package com.cabletech.linepatrol.drill.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.EvaluateDao;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.Evaluate;
import com.cabletech.linepatrol.drill.dao.DrillPlanDao;
import com.cabletech.linepatrol.drill.dao.DrillPlanModifyDao;
import com.cabletech.linepatrol.drill.dao.DrillSummaryDao;
import com.cabletech.linepatrol.drill.dao.DrillTaskDao;
import com.cabletech.linepatrol.drill.dao.QueryStatDao;
import com.cabletech.linepatrol.drill.module.DrillConstant;
import com.cabletech.linepatrol.drill.module.DrillPlan;
import com.cabletech.linepatrol.drill.module.DrillPlanModify;
import com.cabletech.linepatrol.drill.module.DrillQueryCondition;
import com.cabletech.linepatrol.drill.module.DrillSummary;
import com.cabletech.linepatrol.drill.module.DrillTask;
import com.cabletech.linepatrol.drill.templates.DrillTemplates;

@Service
@Transactional
public class QueryStatBo extends EntityManager<DrillTask, String> {
	private static String CONTENT_TYPE = "application/vnd.ms-excel";
	private static Logger logger = Logger
			.getLogger(QueryStatBo.class.getName());
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;

	@Override
	protected HibernateDao<DrillTask, String> getEntityDao() {
		return queryStatDao;
	}

	@Resource(name = "queryStatDao")
	private QueryStatDao queryStatDao;

	@Resource(name = "drillTaskDao")
	private DrillTaskDao drillTaskDao;

	@Resource(name = "drillPlanDao")
	private DrillPlanDao drillPlanDao;

	@Resource(name = "drillPlanModifyDao")
	private DrillPlanModifyDao drillPlanModifyDao;

	@Resource(name = "drillSummaryDao")
	private DrillSummaryDao drillSummaryDao;

	@Resource(name = "evaluateDao")
	private EvaluateDao evaluateDao;

	public Map queryStatForm(UserInfo userInfo) {
		String deptype = userInfo.getDeptype();
		String reginId = userInfo.getRegionID();
		String conId = userInfo.getDeptID();
		String conName = userInfo.getDeptName();
		Map map = new HashMap();
		if ("1".equals(deptype)) {
			List list = queryStatDao.getContractorInfo(reginId);
			map.put("list", list);
		}
		map.put("conId", conId);
		map.put("conName", conName);
		map.put("deptype", deptype);
		return map;
	}

	/**
	 * 获得演练查询列表
	 * 
	 * @param drillQueryCondition
	 * @param userInfo
	 * @return
	 */
	public List queryStat(DrillQueryCondition drillQueryCondition,
			UserInfo userInfo) {
		List list = queryStatDao.drillQuery(drillQueryCondition, userInfo);
		List list2 = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String flagPos = (String) bean.get("flag_pos");
				String taskconId = (String) bean.get("taskcon_id");
				if ("3".equals(flagPos)) {
					list2.add(bean);
					continue;
				}
				if ("2".equals(flagPos)) {
					if (judgePlan(list, taskconId)) {
						list2.add(bean);
						continue;
					}
				}
				if ("1".equals(flagPos)) {
					if (judgeTask(list, taskconId)) {
						list2.add(bean);
						continue;
					}
				}
			}
		}
		return list2;
	}

	public BasicDynaBean judgeDate(BasicDynaBean basicDynaBean, List listOrg) {
		Object taskIdOrg = basicDynaBean.get("task_id");
		Object conIdOrg = basicDynaBean.get("taskcon_id");
		Object planIdOrg = basicDynaBean.get("plan_id");
		Object summaryIdOrg = basicDynaBean.get("summary_id");
		if (summaryIdOrg != null && !"".equals(summaryIdOrg)) {
			return basicDynaBean;
		} else {
			if (planIdOrg != null && !"".equals(planIdOrg)) {
				for (int i = 0; i < listOrg.size(); i++) {
					BasicDynaBean bean = (BasicDynaBean) listOrg.get(i);
					Object planId = bean.get("plan_id");
					Object summaryId = basicDynaBean.get("summary_id");
					if (planIdOrg.equals(planId) && "".equals(summaryId)) {
						return basicDynaBean;
					}
				}
			} else {
				for (int i = 0; i < listOrg.size(); i++) {
					BasicDynaBean bean = (BasicDynaBean) listOrg.get(i);
					Object taskId = bean.get("task_id");
					Object conId = bean.get("taskcon_id");
					if (taskIdOrg.equals(taskId) && conIdOrg.equals(conId)) {
						return basicDynaBean;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 演练统计
	 * 
	 * @param drillQueryCondition
	 * @param userInfo
	 * @return
	 */
	public List drillStat(DrillQueryCondition drillQueryCondition,
			UserInfo userInfo) {
		return queryStatDao.drillStat(drillQueryCondition, userInfo);
	}

	public boolean judgePlan(List list, String taskconId) {
		for (int i = 0; i < list.size(); i++) {
			BasicDynaBean bean = (BasicDynaBean) list.get(i);
			String taskconIdOrg = (String) bean.get("taskcon_id");
			Object summaryIdOrg = bean.get("summary_id");
			if (summaryIdOrg != null) {
				if (taskconIdOrg.equals(taskconId)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean judgeTask(List list, String taskconId) {
		for (int i = 0; i < list.size(); i++) {
			BasicDynaBean bean = (BasicDynaBean) list.get(i);
			String taskconIdOrg = (String) bean.get("taskcon_id");
			Object planIdOrg = bean.get("plan_id");
			if (planIdOrg != null) {
				if (taskconIdOrg.equals(taskconId)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 导出演练列表
	 * 
	 * @param list
	 * @param response
	 */
	public void exportDrillList(List list, HttpServletResponse response) {
		try {
			initResponse(response, "演练查询列表.xls");
			OutputStream out = response.getOutputStream();
			Properties config = getReportConfig();
			String urlPath = getUrlPath(config, "report.drillQueryList");
			DrillTemplates template = new DrillTemplates(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportDrillQuery(list, excelstyle);
			template.write(out);
		} catch (Exception e) {
			e.getStackTrace();
			logger.error("导出出现异常:" + e.getMessage());
			throw new ServiceException();
		}
	}

	@Transactional(readOnly = true)
	private void initResponse(HttpServletResponse response, String fileName)
			throws UnsupportedEncodingException {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));
	}


	/**
	 * 查看演练信息
	 * 
	 * @param taskId
	 * @param planId
	 * @param summaryId
	 * @return
	 */
	public Map viewDrill(String taskId, String planId, String summaryId) {
		DrillTask drillTask = drillTaskDao.findByUnique("id", taskId);
		String cancelUserName = userInfoDao.getUserName(drillTask
				.getCancelUserId());
		drillTask.setCancelUserName(cancelUserName);
		DrillPlan drillPlan = null;
		// DrillPlanModify drillPlanModify = null;
		List list = null;
		DrillSummary drillSummary = null;
		Evaluate evaluate = null;
		if (planId != null && !"".equals(planId)) {
			drillPlan = drillPlanDao.findByUnique("id", planId);
			evaluate = evaluateDao.getEvaluate(planId,
					DrillConstant.LP_DRILL_EVALUATE);
			list = drillPlanModifyDao.findByProperty("planId", planId);
		}
		if (summaryId != null && !"".equals(summaryId)) {
			drillSummary = drillSummaryDao.findByUnique("id", summaryId);
		}
		Map map = new HashMap();
		map.put("drillTask", drillTask);
		map.put("drillPlan", drillPlan);
		map.put("drillSummary", drillSummary);
		map.put("evaluate", evaluate);
		map.put("list", list);
		return map;
	}
}
