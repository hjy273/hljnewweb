package com.cabletech.linepatrol.hiddanger.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.dao.ContractorDAOImpl;
import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.util.Hanzi2PinyinUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyDailyBO;
import com.cabletech.linepatrol.appraise.service.AppraiseDailySpecialBO;
import com.cabletech.linepatrol.commons.dao.EvaluateDao;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.Evaluate;
import com.cabletech.linepatrol.hiddanger.beans.QueryBean;
import com.cabletech.linepatrol.hiddanger.beans.RegistBean;
import com.cabletech.linepatrol.hiddanger.dao.HiddangerRegistDao;
import com.cabletech.linepatrol.hiddanger.dao.HiddangerReportDao;
import com.cabletech.linepatrol.hiddanger.model.HiddangerRegist;
import com.cabletech.linepatrol.hiddanger.model.HiddangerReport;
import com.cabletech.linepatrol.hiddanger.workflow.HiddangerWorkflowBO;
import com.cabletech.linepatrol.specialplan.dao.SpecialPlanDao;
import com.cabletech.linepatrol.specialplan.module.SpecialPlan;
import com.cabletech.linepatrol.specialplan.module.WatchPlanStat;
import com.cabletech.linepatrol.specialplan.service.SpecialPlanStatManager;

@Service
@Transactional
public class HiddangerRegistManager extends
		EntityManager<HiddangerRegist, String> {
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Resource(name = "hiddangerRegistDao")
	private HiddangerRegistDao dao;
	@Resource(name = "hiddangerReportDao")
	private HiddangerReportDao reportDao;
	@Autowired
	private HiddangerWorkflowBO workflowBo;
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	@Resource(name = "evaluateDao")
	private EvaluateDao evaluateDao;
	@Resource(name = "specialPlanDao")
	private SpecialPlanDao spDao;
	@Resource(name = "specialPlanStatManager")
	private SpecialPlanStatManager hs;

	@Autowired
	private AppraiseDailyDailyBO appraiseDailyBO;
	@Autowired
	private AppraiseDailySpecialBO appraiseDailySpecialBO;
	
	/**
	 *  根据已有数据生成序列编号，并检查是否与重复
	 *  生成规则代维公司+年月+编号
	 * @param deptName
	 * @param deptid
	 * @return
	 */
	public String generateCode(String deptName,String deptid){
		List<HiddangerRegist> hiddangerRegists = dao.getHiddangers4dept(deptid);
		String code = Hanzi2PinyinUtil.generateCode4Prefix(deptName);
		int lenght = hiddangerRegists.size()+1;
		String number = String.format("%04d", lenght); 
		for(HiddangerRegist hidanger:hiddangerRegists){
			if(number.indexOf(hidanger.getId()) != -1){
				number = String.format("%04d", ++lenght);
			}
		}
		logger.info("业务ID："+code+number);
		
		return code+number;
	}

	/**
	 * 生成编号中的年月部分
	 * 
	 * @return 年月字符串
	 */
	public String getYearMonth() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		return format.format(date);
	}

	/**
	 * 根据隐患分类id得到隐患编码列表
	 * 
	 * @param typeId
	 *            隐患分类id
	 * @return 隐患编码key value字符串
	 */
	public String getTroubleCodeString(String typeId) {
		List<Map> codeList = dao.getTroubleCodeList(typeId);
		String html = "";
		if (!codeList.isEmpty()) {
			for (Map map : codeList) {
				if (html.equals("")) {
					html = (String) map.get("id") + ","
							+ (String) map.get("troublename");
				} else {
					html += "&" + (String) map.get("id") + ","
							+ (String) map.get("troublename");
				}
			}
		}
		return html;
	}

	public List<HiddangerRegist> getTerminalResult(UserInfo userInfo) {
		String deptId = userInfo.getDeptID();
		return dao.getTerminalResult(deptId);
	}

	/**
	 * 得到代维部门列表
	 * 
	 * @param userInfo
	 *            用户信息
	 * @return list
	 */
	public List<LabelValueBean> getDeptOptions(UserInfo userInfo) {
		List<Map> deptOptions = dao.getDeptOptions(userInfo.getDeptype(),
				userInfo.getDeptID());
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		if (!deptOptions.isEmpty()) {
			for (Map<String, Object> map : deptOptions) {
				LabelValueBean label = new LabelValueBean();
				label.setValue((String) map.get("contractorid"));
				label.setLabel((String) map.get("contractorname"));
				list.add(label);
			}
		}
		return list;
	}

	/**
	 * 得到盯防负责人列表
	 * 
	 * @param id
	 *            隐患id
	 * @return list
	 */
	public List<LabelValueBean> getPrincipalOptions(String id) {
		String deptId = dao.findUniqueByProperty("id", id).getTreatDepartment();
		List<Map> priOptions = dao.getPrincipalOptions(deptId);
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		LabelValueBean choose = new LabelValueBean();
		choose.setValue(" ");
		choose.setLabel("请选择");
		list.add(choose);
		if (!priOptions.isEmpty()) {
			for (Map<String, Object> map : priOptions) {
				LabelValueBean label = new LabelValueBean();
				label.setValue((String) map.get("mobile"));
				label.setLabel((String) map.get("name"));
				list.add(label);
			}
		}
		return list;
	}

	/**
	 * 保存登记信息
	 * 
	 * @param registBean
	 *            登记信息bean
	 * @throws Exception
	 */
	public void saveRegist(RegistBean registBean, UserInfo userInfo,
			boolean isTerminal) {
		HiddangerRegist regist = new HiddangerRegist();

		// 登记信息对象填充
		if (isTerminal) {
			regist = dao.get(registBean.getId());
			BeanUtil.copyProperties(registBean, regist);
			regist.setFindType("巡查");
		} else {
			BeanUtil.copyProperties(registBean, regist);
			regist.setId(null);
			regist.setFindType("告知");
			regist.setRegionId(userInfo.getRegionID());
		}
		regist.setHiddangerState(HiddangerConstant.REGIST);
		regist.setCreater(userInfo.getUserID());
		regist.setCreaterDept(userInfo.getDeptName());
		regist.setCreateTime(new Date());
		regist.setTerminnalState("1");

		// 保存登记信息
		dao.save(regist);

		// 启动流程
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("assignee", regist.getTreatDepartment());
		String processId = workflowBo.createProcessInstance("hiddanger", regist
				.getId(), variables);

		// 更新登记信息中的流程编号
		regist.setProcessInstanceId(processId);
		dao.save(regist);

		// 流程历史
		workflowBo.saveProcessHistory(userInfo, regist, null, regist
				.getTreatDepartment(), "start", "隐患登记", "start");

		// 短信提醒
		// 发送给处理部门负责人
		String content = "【隐患盯防】您有一个名称为\"" + regist.getName()
				+ "\"的隐患登记单等待您的及时处理！登记人" + getUserName(regist.getCreater());
		List<String> mobiles = getHiddangerPrincipal(regist.getId());
		try{
		super.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("短信发送失败：",ex);
        }
	}

	public void saveRegistForTerminal(String id, UserInfo userInfo) {
		HiddangerRegist regist = dao.get(id);

		regist.setHiddangerState(HiddangerConstant.REGIST);
		regist.setCreater(userInfo.getUserID());
		regist.setCreaterDept(userInfo.getDeptName());
		regist.setRegionId(userInfo.getRegionID());
		regist.setCreateTime(new Date());

		// 保存登记信息
		dao.save(regist);

		// 启动流程
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("assignee", regist.getTreatDepartment());
		String processId = workflowBo.createProcessInstance("hiddanger", regist
				.getId(), variables);

		// 更新登记信息中的流程编号
		regist.setProcessInstanceId(processId);
		dao.save(regist);

		// 流程历史
		workflowBo.saveProcessHistory(userInfo, regist, null, regist
				.getTreatDepartment(), "start", "隐患登记", "start");

		// 短信提醒
		// 发送给处理部门负责人；
		String content = "【隐患盯防】您有一个名称为\"" + regist.getName()
				+ "\"的隐患登记单等待您的及时处理！登记人" + getUserName(regist.getCreater());
		List<String> mobiles = getHiddangerPrincipal(regist.getId());
		try{
		super.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("短信发送失败：",ex);
        }
	}

	/**
	 * 修改登记信息
	 * 
	 * @param registBean
	 *            登记信息bean
	 * @param userInfo
	 *            用户信息
	 */
	public void editRegist(RegistBean registBean, UserInfo userInfo) {
		HiddangerRegist regist = dao.get(registBean.getId());
		regist.setX(registBean.getX());
		regist.setY(registBean.getY());
		regist.setName(registBean.getName());
		regist.setReporter(registBean.getReporter());
		regist.setType(registBean.getType());
		regist.setCode(registBean.getCode());
		regist.setFindTime(DateUtil.Str2UtilDate(registBean.getFindTime(),
				"yyyy/MM/dd HH:mm:ss"));
		regist.setHiddangerNumber(registBean.getHiddangerNumber());
		dao.save(regist);
	}

	/**
	 * 得到登记信息
	 * 
	 * @param id
	 *            登记信息id
	 * @return 登记信息bean
	 */
	public RegistBean getRegistInfo(String id) {
		HiddangerRegist regist = dao.get(id);
		String cancelUserName = userInfoDao.getUserName(regist
				.getCancelUserId());
		regist.setCancelUserName(cancelUserName);
		RegistBean registBean = null;
		if (regist != null) {
			registBean = new RegistBean();
			BeanUtil.copyProperties(regist, registBean);
		}
		return registBean;
	}

	/**
	 * 得到登记信息
	 * 
	 * @param id
	 *            登记信息id
	 * @param userInfo
	 *            用户信息
	 * @return 登记信息bean
	 */
	public RegistBean getRegistInfo(String id, UserInfo userInfo) {
		HiddangerRegist regist = dao.get(id);
		settingRegist(regist, userInfo);

		RegistBean registBean = null;
		if (regist != null) {
			registBean = new RegistBean();
			BeanUtil.copyProperties(regist, registBean);
		}
		return registBean;
	}

	/**
	 * 隐患评级
	 * 
	 * @param registBean
	 *            登记信息bean
	 * @param userInfo
	 *            用户信息
	 * @return 登记信息bean
	 */
	public RegistBean saveBegintreat(RegistBean registBean, UserInfo userInfo) {
		HiddangerRegist regist = dao.get(registBean.getId());
		regist.setHiddangerLevel(registBean.getHiddangerLevel());
		regist.setRemark(registBean.getRemark());

		// 如果是更改评级
		changerLevel(regist);

		// 流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getDeptID(), regist
				.getId());
		String transition = "";
		if (task != null
				&& HiddangerWorkflowBO.CONFIRM_TASK.equals(task.getName())) {
			if (HiddangerConstant.FOURTH.equals(regist.getHiddangerLevel())) {
				transition = "self_process";
				regist.setHiddangerState(HiddangerConstant.TREAT);
			} else if (HiddangerConstant.IGNORE.equals(regist
					.getHiddangerLevel())) {
				transition = "ignore";
				regist.setHiddangerState(HiddangerConstant.IGNORE);
			} else {
				transition = "report";
				regist.setHiddangerState(HiddangerConstant.GENERAL);
			}
			workflowBo.setVariables(task, "assignee", regist
					.getTreatDepartment());
			workflowBo.completeTask(task.getId(), transition);
		}

		// 流程历史
		workflowBo.saveProcessHistory(userInfo, regist, task, regist
				.getTreatDepartment(), transition, "隐患评级", "");

		// 更新隐患对象
		dao.save(regist);

		BeanUtil.copyProperties(regist, registBean);
		return registBean;
	}

	public void changerLevel(HiddangerRegist regist) {
		if (HiddangerConstant.FOURTH.equals(regist.getHiddangerLevel())
				|| HiddangerConstant.IGNORE.equals(regist.getHiddangerLevel())) {
			HiddangerReport report = reportDao.getReportFromHiddangerId(regist
					.getId());
			if (report != null) {
				reportDao.delete(report);
			}
		}
	}

	/**
	 * 得到查询隐患结果
	 * 
	 * @param queryBean
	 *            查询对象
	 * @return 隐患列表
	 */
	public List<HiddangerRegist> getQueryResult(QueryBean queryBean,
			UserInfo userInfo) {
		List<HiddangerRegist> list = dao.getQueryResult(queryBean, userInfo);
		for (HiddangerRegist regist : list) {
			if (StringUtils.isNotBlank(regist.getTreatDepartment())) {
				if (regist.getTreatDepartment().equals(userInfo.getDeptID())) {
					regist.setEdit(true);
				}
			}
		}
		return list;
	}

	public List<HiddangerRegist> getQueryStat(QueryBean queryBean) {
		return dao.getQueryStat(queryBean);
	}

	public List<HiddangerRegist> getHandeledWorks(UserInfo userInfo) {
		return dao.getHandeledWorks(userInfo);
	}

	/**
	 * 得到待办工作列表,包括两部分以部门ID获得和以用户ID获得
	 * 
	 * @return 隐患列表
	 */
	public List<HiddangerRegist> getToDoWork(UserInfo userInfo, String taskName) {
		// 以部门ID获得
		List<HiddangerRegist> forDeptList = workflowBo.queryForHandleList(
				userInfo.getDeptID(), taskName);
		// 以用户ID获得
		List<HiddangerRegist> forUseridList = workflowBo.queryForHandleList(
				userInfo.getUserID(), taskName);
		// 合并以上两个列表
		List<HiddangerRegist> list = new ArrayList<HiddangerRegist>();
		list = addList(list, forDeptList);
		list = addList(list, forUseridList);

		return settingToDoWork(list, userInfo);
	}

	public List<HiddangerRegist> addList(List<HiddangerRegist> list,
			List<HiddangerRegist> destList) {
		if (destList != null && !destList.isEmpty()) {
			for (HiddangerRegist h : destList) {
				list.add(h);
			}
		}
		return list;
	}

	/**
	 * 取得所有待考核评估的列表，当当前用户为代维用户，则返回空
	 * 
	 * @param userInfo
	 *            用户信息
	 * @return 制定的隐患列表
	 */
	public List<HiddangerRegist> getHiddangerList(UserInfo userInfo) {
		if (userInfo.getDeptype().equals("1")) {
			return dao.getHiddangerList();
		} else {
			return null;
		}
	}

	/**
	 * 保存考核评估信息并更新主表状态
	 * 
	 * @param userInfo
	 *            用户信息
	 * @param score
	 *            分数
	 * @param remark
	 *            考核意见
	 * @param id
	 *            隐患id
	 */
	public void saveEvaluate(UserInfo userInfo, String id,AppraiseDailyBean appraiseDailyBean,List<AppraiseDailyBean> speicalBeans) {
		HiddangerRegist regist = dao.findUniqueByProperty("id", id);

		/*Evaluate evaluate = new Evaluate();
		evaluate.setEntityScore(score);
		evaluate.setEntityRemark(remark);
		evaluate.setEvaluator(userInfo.getUserID());
		evaluate.setEntityType(HiddangerConstant.EVALUATETYPE);
		evaluate.setEntityId(id);*/
		
		//保存日常考核信息
		appraiseDailyBO.saveAppraiseDailyByBean(appraiseDailyBean);
		//保存专项日常考核信息
		appraiseDailySpecialBO.saveAppraiseDailyByBean(speicalBeans);
		// 保存评估记录
		//evaluateDao.savaEvaluate(evaluate);

		// 流程
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), id);
		workflowBo.completeTask(task.getId(), "end");

		// 流程历史
		workflowBo.saveProcessHistory(userInfo, regist, task, regist
				.getTreatDepartment(), "end", "隐患评估", "");

		// 更新状态
		regist.setHiddangerState(HiddangerConstant.COMPLETE);
		dao.save(regist);

		// 短信提醒
		String content = "【隐患盯防】\"" + regist.getName() + "\"的隐患已考核结束！";
				//+ evaluate.getEntityScore() + "分。";
		List<String> mobiles = getHiddangerPrincipal(id);
		try{
		super.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("短信发送失败：",ex);
        }
	}

	/**
	 * 通过隐患id得到考核对象
	 * 
	 * @param id
	 *            隐患id
	 * @return 考核对象
	 */
	public Evaluate getEvaluate(String id) {
		return evaluateDao.getEvaluate(id, HiddangerConstant.EVALUATETYPE);
	}

	/**
	 * 设置待办工作列表
	 * 
	 * @param list
	 *            待办工作列表
	 */
	public List<HiddangerRegist> settingToDoWork(List<HiddangerRegist> list,
			UserInfo userInfo) {
		list = setHadRead(list, userInfo);
		for (HiddangerRegist regist : list) {
			settingRegist(regist, userInfo);
		}
		return list;
	}

	/**
	 * 排除已阅读
	 * 
	 * @param list
	 *            待办工作列表
	 */
	public List<HiddangerRegist> setHadRead(List<HiddangerRegist> list,
			UserInfo userInfo) {
		Map<String, String> map = getMaps();
		List<HiddangerRegist> remove = new ArrayList<HiddangerRegist>();
		for (HiddangerRegist regist : list) {
			String state = regist.getHiddangerState();
			if (map.containsKey(state)) {
				// 是抄送人
				if (replyApproverDAO.isReadOnly(regist.getId(), userInfo
						.getUserID(), map.get(state))) {
					// 且抄送人已阅读--排除
					if (replyApproverDAO.isReaded(regist.getId(), userInfo
							.getUserID(), map.get(state))) {
						remove.add(regist);
					}
				}
			}
		}
		for (HiddangerRegist regist : remove) {
			list.remove(regist);
		}
		return list;
	}

	public Map<String, String> getMaps() {
		Map<String, String> stateMap = new HashMap<String, String>();
		stateMap.put(HiddangerConstant.NEEDAPPROVE, "LP_HIDDANGER_REPORT");
		stateMap.put(HiddangerConstant.PLANAPPROVE, "LP_HIDDANGER_MAKEPLAN");
		stateMap.put(HiddangerConstant.ENDPLAN, "LP_HIDDANGER_ENDPLAN");
		stateMap.put(HiddangerConstant.CLOSEAPPROVED, "LP_HIDDANGER_CLOSE");

		return stateMap;
	}

	public void settingRegist(HiddangerRegist hiddangerRegist, UserInfo userInfo) {
		setReadOnlys(hiddangerRegist, userInfo);
		setIsPlanComplete(hiddangerRegist, userInfo);
	}

	/**
	 * 设置隐患信息，当隐患处于审核状态时，当前用户为抄送人则只能查看。
	 */
	public void setReadOnlys(HiddangerRegist hiddangerRegist, UserInfo userInfo) {
		setReadOnly(hiddangerRegist, getMaps(), userInfo);
	}

	public void setReadOnly(HiddangerRegist hiddangerRegist,
			Map<String, String> stateMap, UserInfo userInfo) {
		List<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> e : stateMap.entrySet()) {
			list.add(e.getKey());
		}

		boolean flag = true;
		String state = hiddangerRegist.getHiddangerState();
		if (list.contains(state)) {
			if (!replyApproverDAO.isReadOnly(hiddangerRegist.getId(), userInfo
					.getUserID(), stateMap.get(state))) {
				flag = false;
			}
		}
		hiddangerRegist.setReadOnly(flag);
	}

	/**
	 * 设置隐患信息，当盯防计划的结束时间小于当前时间时，隐患可以关闭
	 */
	public void setIsPlanComplete(HiddangerRegist regist, UserInfo userInfo) {
		String state = regist.getHiddangerState();
		if (state.equals(HiddangerConstant.WAIT)) {
			String splanId = regist.getSplanId();
			List<SpecialPlan> list = spDao.getCompletedPlan(splanId);
			if (list.size() > 0) {
				// 更新状态
				saveState(regist.getId(), HiddangerConstant.CLOSE);

				// 流程
				Task task = workflowBo.getHandleTaskForId(userInfo.getDeptID(),
						regist.getId());
				workflowBo.setVariables(task, "assignee", getDept(regist
						.getId()));
				workflowBo.completeTask(task.getId(), "toclose");

				// 流程历史
				workflowBo.saveProcessHistory(userInfo, regist, task, regist
						.getTreatDepartment(), "toclose", "隐患关闭", "");
			}
		}
	}

	public List<Map> getSplans(String hiddangerId) {
		return spDao.getPlanForHiddangerStat(hiddangerId);
	}

	public List<WatchPlanStat> getWatchPlanStats(List<Map> plans) {
		List<WatchPlanStat> list = new ArrayList<WatchPlanStat>();
		for (Map map : plans) {
			String planId = (String) map.get("plan_id");
			WatchPlanStat watchPlanStat = hs.getWatchPlanStat(planId);
			if (watchPlanStat != null)
				list.add(watchPlanStat);
		}
		return list;
	}

	public String getSplanIds(List<Map> plans) {
		List<String> list = new ArrayList<String>();
		for (Map map : plans) {
			list.add((String) map.get("plan_id"));
		}
		return StringUtils.join(list.iterator(), ",");
	}

	public List<String> getHiddangerPrincipal(String id) {
		HiddangerRegist regist = dao.findUniqueByProperty("id", id);
		String deptId = regist.getTreatDepartment();
		List<Map> list = dao.getHiddangerPrincipal(deptId);
		List<String> principals = new ArrayList<String>();
		if (!list.isEmpty()) {
			for (Map map : list) {
				principals.add((String) map.get("linkmaninfo"));
			}
		}
		return principals;
	}

	public String getDept(String id) {
		HiddangerRegist regist = dao.findUniqueByProperty("id", id);
		return regist.getTreatDepartment();
	}

	public HiddangerRegist getRegist(String id) {
		return dao.findUniqueByProperty("id", id);
	}

	public void saveState(String id, String state) {
		HiddangerRegist regist = dao.get(id);
		regist.setHiddangerState(state);
		dao.save(regist);
	}

	public Map<String, String> getCodes() {
		Map<String, String> map = new HashMap<String, String>();
		List<Map> list = dao.getCodes();
		for (Map m : list) {
			map.put((String) m.get("id"), (String) m.get("troublename"));
		}
		return map;
	}

	public Map<String, String> getTypes() {
		Map<String, String> map = new HashMap<String, String>();
		List<Map> list = dao.getTypes();
		for (Map m : list) {
			map.put((String) m.get("id"), (String) m.get("typename"));
		}
		return map;
	}

	public Map<String, String> getDepts() {
		Map<String, String> map = new HashMap<String, String>();
		List<Map> list = dao.getDepts();
		for (Map m : list) {
			map.put((String) m.get("contractorid"), (String) m
					.get("contractorname"));
		}
		return map;
	}

	public Map<String, String> getUsers() {
		Map<String, String> map = new HashMap<String, String>();
		List<Map> list = dao.getUsers();
		for (Map m : list) {
			map.put((String) m.get("userid"), (String) m.get("username"));
		}
		return map;
	}

	public String getUserName(String userId) {
		return dao.getUserName(userId);
	}

	public void read(String id, String type, UserInfo userInfo) {
		replyApproverDAO.updateReader(userInfo.getUserID(), id, type);

		// 流程历史
		HiddangerRegist regist = getRegist(id);
		workflowBo
				.saveProcessHistory(userInfo, regist, null, "", "", "已阅读", "");
	}

	@Override
	protected HibernateDao<HiddangerRegist, String> getEntityDao() {
		return dao;
	}

	public List<Integer> queryForHandleHideDangerNum(String condition,
			UserInfo userInfo) {
		List<HiddangerRegist> regists = getToDoWork(userInfo, condition);
		List<Integer> list = new ArrayList<Integer>();

		int waitConfirmTaskNum = 0;
		int waitReportTaskNum = 0;
		int waitAddApproveTaskNum = 0;
		int waitMakePlanTaskNum = 0;
		int waitMakePlanApproveTaskNum = 0;
		int waitEndPlanTaskNum = 0;
		int waitEndPlanApproveTaskNum = 0;
		int waitCloseTaskNum = 0;
		int waitCloseApproveTaskNum = 0;
		int waitEvaluateTaskNum = 0;

		if (regists != null && !regists.isEmpty()) {
			for (HiddangerRegist regist : regists) {
				if (HiddangerWorkflowBO.CONFIRM_TASK.equals(regist
						.getFlowTaskName())) {
					waitConfirmTaskNum++;
				}
				if (HiddangerWorkflowBO.REPORT_TASK.equals(regist
						.getFlowTaskName())) {
					waitReportTaskNum++;
				}
				if (HiddangerWorkflowBO.ADD_APPROVE_TASK.equals(regist
						.getFlowTaskName())) {
					waitAddApproveTaskNum++;
				}
				if (HiddangerWorkflowBO.MAKE_PLAN_TASK.equals(regist
						.getFlowTaskName())) {
					waitMakePlanTaskNum++;
				}
				if (HiddangerWorkflowBO.MAKE_PLAN_APPROVE_TASK.equals(regist
						.getFlowTaskName())) {
					waitMakePlanApproveTaskNum++;
				}
				if (HiddangerWorkflowBO.END_PLAN_TASK.equals(regist
						.getFlowTaskName())) {
					waitEndPlanTaskNum++;
				}
				if (HiddangerWorkflowBO.END_PLAN_APPROVE_TASK.equals(regist
						.getFlowTaskName())) {
					waitEndPlanApproveTaskNum++;
				}
				if (HiddangerWorkflowBO.CLOSE_TASK.equals(regist
						.getFlowTaskName())) {
					waitCloseTaskNum++;
				}
				if (HiddangerWorkflowBO.CLOSE_APPROVE_TASK.equals(regist
						.getFlowTaskName())) {
					waitCloseApproveTaskNum++;
				}
				if (HiddangerWorkflowBO.EVALUATE_TASK.equals(regist
						.getFlowTaskName())) {
					waitEvaluateTaskNum++;
				}
			}
		}

		list.add(waitConfirmTaskNum);
		list.add(waitReportTaskNum);
		list.add(waitAddApproveTaskNum);
		list.add(waitMakePlanTaskNum);
		list.add(waitMakePlanApproveTaskNum);
		list.add(waitEndPlanTaskNum);
		list.add(waitEndPlanApproveTaskNum);
		list.add(waitCloseTaskNum);
		list.add(waitCloseApproveTaskNum);
		list.add(waitEvaluateTaskNum);

		return list;
	}

	public void cancelHideDanger(RegistBean bean, UserInfo userInfo) {
		// TODO Auto-generated method stub
		HiddangerRegist regist = dao.get(bean.getId());
		dao.initObject(regist);
		regist.setCancelReason(bean.getCancelReason());
		regist.setCancelTime(new Date());
		regist.setCancelUserId(userInfo.getUserID());
		regist.setHiddangerState(HiddangerRegist.CANCELED_STATE);
		regist.setHideDangerState(HiddangerRegist.CANCELED_STATE);
		dao.save(regist);
		String processInstanceId = "";
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processInstanceId = HiddangerWorkflowBO.HIDDANGER_WORKFLOW + "."
				+ regist.getId();
		processHistoryBean.initial(null, userInfo, processInstanceId,
				ModuleCatalog.HIDDTROUBLEWATCH);
		processHistoryBean.setHandledTaskName("any");
		processHistoryBean.setNextOperateUserId("");
		processHistoryBean.setObjectId(regist.getId());
		processHistoryBean.setProcessAction("隐患流程取消");
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
	 * 通过PDA查询本月隐患的处理情况
	 * @param userInfo
	 * @return
	 */
	public List<Map> getHiddangerNumFromPDA(UserInfo userInfo,String hiddangerLevel) {
		ContractorDAOImpl contractorDAOImpl=new ContractorDAOImpl();
		
		List<Map> hiddangerNums = dao.getHiddangerNumFromPDA(userInfo,hiddangerLevel);
		List<Contractor> contractorList = contractorDAOImpl.getAllContractor(userInfo);
		List<Map> num = new ArrayList<Map>();
		Map<String,Integer> sum=new HashMap<String,Integer>();
		sum.put("nowHiddanger", 0);
		sum.put("markHiddanger", 0);
		sum.put("monthApprove", 0);
		sum.put("colseApproved", 0);
		for (Contractor contractor : contractorList) {
			Map<String, Object> hiddanger = new HashMap<String, Object>();
			setHiddangerNum(hiddangerNums, contractor, hiddanger,num,sum);
		}
		num.add(sum);
		return num;
	}

	private void setHiddangerNum(List<Map> hiddangerNums, Contractor contractor, Map<String, Object> hiddanger,List<Map> num,Map<String,Integer> sum) {
		int nowHiddanger = 0;
		int markHiddanger = 0;
		int monthApprove = 0;
		int colseApproved = 0;
		for (Map hidddangerNum : hiddangerNums) {
			if (contractor.getContractorName().equals(hidddangerNum.get("contractorName"))) {
				nowHiddanger = setNumber(hidddangerNum, "0",nowHiddanger);
				markHiddanger = setNumber(hidddangerNum, "1",markHiddanger);
				monthApprove = setNumber(hidddangerNum, "2",monthApprove);
				colseApproved = setNumber(hidddangerNum, "3",colseApproved);
			}
		}
		hiddanger.put("contractorName", contractor.getContractorName());
		hiddanger.put("0", nowHiddanger);
		hiddanger.put("1", markHiddanger);
		hiddanger.put("2", monthApprove);
		hiddanger.put("3", colseApproved);
		sum.put("nowHiddanger", sum.get("nowHiddanger")+nowHiddanger);
		sum.put("markHiddanger", sum.get("markHiddanger")+markHiddanger);
		sum.put("monthApprove", sum.get("monthApprove")+monthApprove);
		sum.put("colseApproved", sum.get("colseApproved")+colseApproved);
		num.add(hiddanger);
	}

	private int setNumber(Map hidddangerNum, String state,int value) {
		int nowHiddanger = value;
		if (hidddangerNum.get("state").equals(state)) {
			nowHiddanger = Integer.valueOf(hidddangerNum.get("num").toString());
		}
		return nowHiddanger;
	}

	/**
	 * 通过PDA查询现在正在执行的盯防
	 * @param userInfo
	 * @return
	 */
	public List<Map> getSpecialFromPDA(UserInfo userInfo,String contractorId,String hiddangerLevel) {
		return dao.getSpecialFromPDA(userInfo,contractorId,hiddangerLevel);
	}
	
}
