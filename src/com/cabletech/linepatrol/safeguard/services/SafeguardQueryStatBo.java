package com.cabletech.linepatrol.safeguard.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.EvaluateDao;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.Evaluate;
import com.cabletech.linepatrol.safeguard.beans.SafeguardTaskBean;
import com.cabletech.linepatrol.safeguard.dao.SafeguardConDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardPlanDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardQueryStatDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSegmentDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSpplanDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSummaryDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardTaskDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardConstant;
import com.cabletech.linepatrol.safeguard.module.SafeguardPlan;
import com.cabletech.linepatrol.safeguard.module.SafeguardSpplan;
import com.cabletech.linepatrol.safeguard.module.SafeguardSummary;
import com.cabletech.linepatrol.safeguard.module.SafeguardTask;
import com.cabletech.linepatrol.safeguard.templates.SafeguardTemplates;
import com.cabletech.linepatrol.specialplan.dao.SpecialPlanDao;

@Service
@Transactional
public class SafeguardQueryStatBo extends EntityManager<SafeguardTask, String> {
	private static String CONTENT_TYPE = "application/vnd.ms-excel";
	private static Logger logger = Logger.getLogger(SafeguardQueryStatBo.class
			.getName());
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;
	@Override
	protected HibernateDao<SafeguardTask, String> getEntityDao() {
		return safeguardQueryStatDao;
	}

	@Resource(name = "safeguardQueryStatDao")
	private SafeguardQueryStatDao safeguardQueryStatDao;
	
	@Resource(name = "safeguardConDao")
	private SafeguardConDao safeguardConDao;
	
	@Resource(name = "safeguardSpplanDao")
	private SafeguardSpplanDao safeguardSpplanDao;
	
	@Resource(name = "safeguardPlanDao")
	private SafeguardPlanDao safeguardPlanDao;

	@Resource(name = "safeguardTaskDao")
	private SafeguardTaskDao safeguardTaskDao;
	
	@Resource(name = "safeguardSummaryDao")
	private SafeguardSummaryDao safeguardSummaryDao;
	
	@Resource(name = "evaluateDao")
	private EvaluateDao evaluateDao;
	
	@Resource(name = "safeguardSegmentDao")
	private SafeguardSegmentDao safeguardSegmentDao;

	/**
	 * 保障查询前加载数据
	 * @param userInfo
	 * @return
	 */
	public Map safeguardQueryStatForm(UserInfo userInfo) {
		String deptype = userInfo.getDeptype();
		String reginId = userInfo.getRegionID();
		String conId = userInfo.getDeptID();
		String conName = userInfo.getDeptName();
		Map map = new HashMap();
		if ("1".equals(deptype)) {
			List list = safeguardQueryStatDao.getContractorInfo(reginId);
			map.put("list", list);
		}
		map.put("conId", conId);
		map.put("conName", conName);
		map.put("deptype", deptype);
		return map;
	}

	/**
	 * 查询保障方案
	 * @param safeguardTaskBean：封装查询条件
	 * @param userInfo
	 * @return
	 */
	public List safeguardQuery(SafeguardTaskBean safeguardTaskBean, UserInfo userInfo) {
		List list = safeguardQueryStatDao.safeguardQuery(safeguardTaskBean, userInfo);
		List list2 = new ArrayList();
		if(list!=null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String flagPos = (String)bean.get("flag_pos");
				String taskconId = (String)bean.get("taskcon_id");
				if("3".equals(flagPos)){
					list2.add(bean);
					continue;
				}
				if("2".equals(flagPos)){
					if(judgePlan(list, taskconId)){
						list2.add(bean);
						continue;
					}
				}
				if("1".equals(flagPos)){
					if(judgeTask(list, taskconId)){
						list2.add(bean);
						continue;
					}
				}
			}
		}
		return list2;
	}
	
	public boolean judgePlan(List list, String taskconId){
		for(int i = 0;i<list.size();i++){
			BasicDynaBean bean = (BasicDynaBean) list.get(i);
			String taskconIdOrg = (String)bean.get("taskcon_id");
			Object summaryIdOrg = bean.get("summary_id");
			if(summaryIdOrg != null){
				if(taskconIdOrg.equals(taskconId)){
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean judgeTask(List list, String taskconId){
		for(int i = 0;i<list.size();i++){
			BasicDynaBean bean = (BasicDynaBean) list.get(i);
			String taskconIdOrg = (String)bean.get("taskcon_id");
			Object planIdOrg = bean.get("plan_id");
			if(planIdOrg != null){
				if(taskconIdOrg.equals(taskconId)){
					return false;
				}
			}
		}
		return true;
	}

	public void up(String taskId, String conId, String state) {
		safeguardConDao.setStateByTaskIdAndConId(taskId, conId, state);
	}

	/**
	 * 导出数据
	 * @param list
	 * @param response
	 */
	public void exportSafeguardList(List list, HttpServletResponse response) {
		try {
			initResponse(response, "保障查询列表.xls");
			OutputStream out = response.getOutputStream();
			Properties config = getReportConfig();
			String urlPath = getUrlPath(config, "report.safeguardQueryList");
			SafeguardTemplates template = new SafeguardTemplates(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportSafeguardQuery(list, excelstyle);
			template.write(out);
		} catch (Exception e) {
			logger.error("导出出现异常:" + e.getMessage());
			e.getStackTrace();
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
	 * 查看保障信息
	 * @param taskId：保障任务ID
	 * @param planId：保障方案ID
	 * @param summaryId：保障总结ID
	 * @return
	 */
	public Map viewSafeguard(String taskId, String planId, String summaryId){
		SafeguardTask safeguardTask = safeguardTaskDao.findByUnique("id", taskId);
		String cancelUserName = userInfoDao.getUserName(safeguardTask
				.getCancelUserId());
		safeguardTask.setCancelUserName(cancelUserName);
		SafeguardPlan safeguardPlan = null;
		SafeguardSummary safeguardSummary = null;
		Evaluate evaluate = null;
		List list = null;
		List safeguardSps = null;
		List specialPlans = new ArrayList();
		if(planId != null && !"".equals(planId)){
			safeguardPlan = safeguardPlanDao.findByUnique("id", planId);
			evaluate = evaluateDao.getEvaluate(planId,SafeguardConstant.LP_SAFEGUARD_EVALUATE);
			list = safeguardSpplanDao.getSafeguardSpplanByPlanId(planId);
			safeguardSps = safeguardSpplanDao.findByProperty("planId", planId);
			if(safeguardSps != null){
				for (Iterator iterator = safeguardSps.iterator(); iterator.hasNext();) {
					SafeguardSpplan safeguardSp = (SafeguardSpplan) iterator.next();
					String spId = safeguardSp.getSpplanId();
					List<Map> specialPlan = safeguardPlanDao.getSafeguardPlan(spId);
					specialPlans.add(specialPlan);
				}
			}
		}
		if(summaryId != null && !"".equals(summaryId)){
			safeguardSummary = safeguardSummaryDao.findByUnique("id",summaryId);
		}
		String sublineIds = safeguardSegmentDao.getSublineIds(planId);
		Map map = new HashMap();
		map.put("safeguardTask", safeguardTask);
		map.put("safeguardPlan", safeguardPlan);
		map.put("safeguardSummary", safeguardSummary);
		map.put("safeguardSps", safeguardSps);
		map.put("specialPlans", specialPlans);
		map.put("evaluate", evaluate);
		map.put("sublineIds", sublineIds);
		map.put("list", list);
		return map;
	}
	
	/**
	 * 保障统计
	 * @param safeguardTaskBean
	 * @param userInfo
	 * @return
	 */
	public List safeguardStat(SafeguardTaskBean safeguardTaskBean,UserInfo userInfo){
		return safeguardQueryStatDao.safeguardStat(safeguardTaskBean, userInfo);
	}
	
	/**
	 * 通过PDA查询正在执行的保障
	 * @param userInfo
	 * @return
	 */
	public List<Map> QuerySafeguardFromPDA(UserInfo userInfo,String contractorId,String safeguradName){
		return safeguardQueryStatDao.QuerySafeguardFromPDA(userInfo,contractorId,safeguradName);
	}
	
	/**
	 * 获得代维单位
	 * @param userInfo
	 * @return
	 */
	public List<Map<String,String>> getContractorForList(UserInfo userInfo){
		return safeguardQueryStatDao.getContractorForList(userInfo);
	}
}
