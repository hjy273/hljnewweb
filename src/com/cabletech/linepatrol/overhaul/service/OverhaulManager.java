package com.cabletech.linepatrol.overhaul.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.overhaul.beans.OverHaulBean;
import com.cabletech.linepatrol.overhaul.beans.OverHaulQueryBean;
import com.cabletech.linepatrol.overhaul.dao.OverHaulApplyDao;
import com.cabletech.linepatrol.overhaul.dao.OverHaulConDao;
import com.cabletech.linepatrol.overhaul.dao.OverHaulCutDao;
import com.cabletech.linepatrol.overhaul.dao.OverHaulDao;
import com.cabletech.linepatrol.overhaul.dao.OverHaulProjectDao;
import com.cabletech.linepatrol.overhaul.model.Constant;
import com.cabletech.linepatrol.overhaul.model.OverHaul;
import com.cabletech.linepatrol.overhaul.model.OverHaulApply;
import com.cabletech.linepatrol.overhaul.model.OverHaulCon;
import com.cabletech.linepatrol.overhaul.model.OverHaulCut;
import com.cabletech.linepatrol.overhaul.model.OverHaulProject;
import com.cabletech.linepatrol.overhaul.model.Process;
import com.cabletech.linepatrol.overhaul.workflow.OverhaulWorkflowBO;

@Service
@Transactional
public class OverhaulManager extends EntityManager<OverHaul, String> {
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Resource(name = "overHaulDao")
	private OverHaulDao dao;
	@Resource(name = "overHaulConDao")
	private OverHaulConDao overHaulConDao;
	@Resource(name = "overHaulApplyDao")
	private OverHaulApplyDao overHaulApplyDao;
	@Autowired
	private OverhaulWorkflowBO workflowBo;
	@Resource(name = "overHaulCutDao")
	private OverHaulCutDao overHaulCutDao;
	@Resource(name = "overHaulProjectDao")
	private OverHaulProjectDao overHaulProjectDao;
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	@Resource(name = "overHaulApplyManager")
	private OverHaulApplyManager overHaulApplyManager;

	public void addTask(OverHaulBean overHaulBean, UserInfo userInfo) {
		// 保存大修任务
		OverHaul task = new OverHaul();
		BeanUtil.copyProperties(overHaulBean, task);
		task.setId(null);
		task.setCreator(userInfo.getUserID());
		task.setCreateTime(new Date());
		dao.save(task);

		// 保存代维列表
		List<String> contractors = Arrays.asList(overHaulBean.getContractors()
				.split(","));
		for (String contractor : contractors) {
			OverHaulCon con = new OverHaulCon();
			con.setTaskid(task.getId());
			con.setContractorId(contractor);
			overHaulConDao.save(con);
		}

		// 启动主流程
		Map<String, Object> variables = new HashMap<String, Object>();
		String assignee = overHaulBean.getContractors() + ","
				+ userInfo.getUserID();
		variables.put("assignee", assignee);
		String processId = workflowBo.createProcessInstance("overhaul", task
				.getId(), variables);

		// 更新流程实例编号
		task.setProcessInstanceId(processId);
		task.setState(Constant.TASK);
		dao.save(task);

		// 保存流程历史
		workflowBo.saveProcessHistory(userInfo, task, null, overHaulBean
				.getContractors(), "start", "大修任务申请", "start");

		// 短信提醒
		String content = "【大修项目】您有一个名称为\"" + task.getProjectName()
				+ "\"的大修任务申请等待您的及时处理！任务制定人" + getUserName(task.getCreator());
		List<String> mobiles = getPrincipal(Arrays.asList(overHaulBean
				.getContractors().split(",")));
		super.sendMessage(content, mobiles);
	}

	public void treat(String id, String flag, String checked, UserInfo userInfo) {
		// OverHaul overHaul = loadOverHaulWithPassedApply(id);
		OverHaul overHaul = loadOverHaulWithAllApply(id);

		// 得到所有的id
		List<String> applyCutId = new ArrayList<String>();
		List<String> applyProjectId = new ArrayList<String>();

		for (OverHaulApply apply : overHaul.getApplys()) {
			for (OverHaulCut cut : apply.getOverHaulCuts()) {
				applyCutId.add(cut.getId());
			}
			for (OverHaulProject project : apply.getOverHaulProjects()) {
				applyProjectId.add(project.getId());
			}
		}

		// 得到要保存的id
		List<String> keepList = Arrays.asList(checked.split(","));
		List<String> cuts = new ArrayList<String>();
		List<String> projects = new ArrayList<String>();
		for (String keep : keepList) {
			if (keep.indexOf("cut.") != -1) {
				cuts.add(keep.replace("cut.", ""));
			}
			if (keep.indexOf("project.") != -1) {
				projects.add(keep.replace("project.", ""));
			}
		}

		// 得到要删除的id
		applyCutId.removeAll(cuts);
		applyProjectId.removeAll(projects);

		// 处理未选择的id
		if (!applyCutId.isEmpty()) {
			for (String cutId : applyCutId) {
				OverHaulCut cut = overHaulCutDao.get(cutId); // 解除关系
				overHaulCutDao.initObject(cut.getOverHaulApply());
				OverHaulApply apply = cut.getOverHaulApply();
				cut.setOverHaulApply(null);

				// 重新计算费用
				Float cutFee = Float.valueOf(cut.getCutFee());
				Float keep = apply.getFee() - cutFee;
				apply.setFee(keep);

				apply.getOverHaulCuts().remove(cut);
				overHaulCutDao.delete(cut); // 删除
			}
		}
		if (!applyProjectId.isEmpty()) {
			for (String projectId : applyProjectId) {
				OverHaulProject project = overHaulProjectDao.get(projectId); // 解除关系
				overHaulProjectDao.initObject(project.getOverHaulApply());
				OverHaulApply apply = project.getOverHaulApply();
				project.setOverHaulApply(null);

				// 重新计算费用
				Float projectFee = Float.valueOf(project.getProjectFee());
				Float keep = apply.getFee() - projectFee;
				apply.setFee(keep);

				apply.getOverHaulProjects().remove(project);
				overHaulProjectDao.delete(project); // 删除
			}
		}

		if (flag != null && flag.equals("end")) {
			// 主流程
			Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
					overHaul.getProcessInstanceId());
			workflowBo.completeTask(task.getId(), "toend");

			// 更新主表状态
			overHaul.setState(Constant.OVER);

			// 短信接收人
			List<String> contractors = new ArrayList<String>();

			// 子流程
			List<OverHaulApply> applys = overHaulApplyManager.loadApply(id);
			for (OverHaulApply apply : applys) { // 终止所有申请
				// 强行结束子流程
				String processId = apply.getProcessInstanceId();
				workflowBo.endProcessInstance(processId);

				String contractor = apply.getContractorId();
				if (!contractors.contains(contractor)) {
					contractors.add(contractor);
				}
			}

			dao.save(overHaul);
			// 保存流程历史
			workflowBo.saveProcessHistory(userInfo, overHaul, task, "", "",
					"处理，流程结束", "");

			// 短信提醒
			String content = "【大修项目】名称为\"" + overHaul.getProjectName()
					+ "\"的大修任务已经处理！处理人" + getUserName(userInfo.getUserID());
			List<String> mobiles = getPrincipal(contractors);
			super.sendMessage(content, mobiles);
		}
	}

	public OverHaul loadOverHaul(String id) {
		return dao.findUniqueByProperty("id", id);
	}

	// 得到大修任务，包含已经通过的大修申请
	public OverHaul loadOverHaulWithPassedApply(String id) {
		List<OverHaulApply> list = overHaulApplyManager.loadPassedApply(id);
		return wrapOverHaul(id, list);
	}

	// 得到大修任务，包含所有的大修申请
	public OverHaul loadOverHaulWithAllApply(String id) {
		List<OverHaulApply> list = overHaulApplyManager.loadApply(id);
		return wrapOverHaul(id, list);
	}

	// 得到大修任务，代维只包含本部门的大修申请
	public OverHaul getViewOverHaul(String id, UserInfo userInfo) {
		OverHaul overHaul = loadOverHaulWithPassedApply(id); // 计划所有已通过申请的费用
		String cancelUserName = userInfoDao.getUserName(overHaul
				.getCancelUserId());
		overHaul.setCancelUserName(cancelUserName);
		List<OverHaulApply> list = overHaulApplyManager.loadApplyForDept(
				userInfo, id);
		overHaul.setApplys(list); // 只装入本代维的申请
		return overHaul;
	}

	// 1。得到大修任务对象
	// 2。填充符合条件的申请到大修任务里
	// 3。计算所有申请的总费用，剩余费用
	public OverHaul wrapOverHaul(String id, List<OverHaulApply> list) {
		OverHaul overHaul = loadOverHaul(id);
		overHaul.setApplys(list);
		return loadOverHaulWithFee(overHaul);
	}

	// 计算费用
	public OverHaul loadOverHaulWithFee(OverHaul overHaul) {
		Float useFee = 0.0f;
		Float remainFee = 0.0f;

		for (OverHaulApply overHaulApply : overHaul.getApplys()) {
			useFee += overHaulApply.getFee();
		}
		remainFee = overHaul.getBudgetFee() - useFee;

		overHaul.setUseFee(useFee);
		overHaul.setRemainFee(remainFee);
		return overHaul;
	}

	public OverHaul loadOverHaulFromSubflowId(String subflowId) {
		OverHaulApply overHaulApply = overHaulApplyDao.findUniqueByProperty(
				"processInstanceId", subflowId);
		String taskId = overHaulApply.getTaskId();
		return loadOverHaul(taskId);
	}

	/**
	 * 封装成例如下面的树 +代维一（小计：总费用000元，百分比00%） +日期 +割接信息 +xxx割接（费用 000元） +工程信息
	 * +xxx工程（费用 000元） +代维二（小计：总费用000元，百分比00%） +日期 +割接信息 +xxx割接（费用 000元） +工程信息
	 * +xxx工程（费用 000元） +日期 +割接信息 +xxx割接（费用 000元） +工程信息 +xxx工程（费用 000元）
	 */
	public String getJsonFromOverHaul(OverHaul overHaul) {
		List<OverHaulApply> applys = overHaul.getApplys();
		JSONArray firstLevel = new JSONArray();

		// 第几次，合并代维申请
		Map<String, List<OverHaulApply>> deptMap = new HashMap<String, List<OverHaulApply>>();
		for (OverHaulApply apply : applys) {
			String contractorId = apply.getContractorId();
			if (deptMap.containsKey(contractorId)) {
				List<OverHaulApply> list = deptMap.get(contractorId);
				list.add(apply);
			} else {
				List<OverHaulApply> list = new ArrayList<OverHaulApply>();
				list.add(apply);
				deptMap.put(contractorId, list);
			}
		}

		for (Entry<String, List<OverHaulApply>> e : deptMap.entrySet()) {
			String contractorId = e.getKey();
			List<OverHaulApply> list = e.getValue();
			Float deptFee = 0.0f;

			JSONArray secondLevel = new JSONArray();

			for (int i = 0; i < list.size(); i++) {
				OverHaulApply apply = list.get(i);
				deptFee += apply.getFee();

				int times = i;
				String istr = String.valueOf(++times);

				JSONArray thirdLevel = new JSONArray();
				JSONArray cutLevel = new JSONArray(); // 割接
				JSONArray projectLevel = new JSONArray(); // 工程

				for (OverHaulCut cut : apply.getOverHaulCuts()) {
					cutLevel.add(getJOString("cut." + cut.getId(), cut
							.getCutName()
							+ "（费用：" + cut.getCutFee() + "元）", true,
							getJAString("cut," + cut.getCutId())));
				}

				for (OverHaulProject project : apply.getOverHaulProjects()) {
					projectLevel.add(getJOString("project." + project.getId(),
							project.getProjectName() + "（费用："
									+ project.getProjectFee() + "元）", true,
							getJAString("project," + project.getProjectId())));
				}
				if (!cutLevel.isEmpty())
					thirdLevel.add(getJOString("cuttype." + istr
							+ apply.getId(), "割接信息", cutLevel));
				if (!projectLevel.isEmpty())
					thirdLevel.add(getJOString("projecttype." + istr
							+ apply.getId(), "工程信息", projectLevel));

				secondLevel.add(getJOString(
						"times" + istr + "." + contractorId, DateUtil
								.DateToString(apply.getCreateTime()),
						thirdLevel));
			}

			Float fate = Float.valueOf(100) * deptFee / overHaul.getBudgetFee();
			DecimalFormat df = new DecimalFormat();
			df.setMinimumFractionDigits(2);
			df.setMaximumFractionDigits(2);

			String contractorName = getDeptName(contractorId);
			firstLevel.add(getJOString("dept." + contractorId, contractorName
					+ "（小计：总费用" + deptFee + "，百分比" + df.format(fate) + "%）",
					secondLevel));
		}

		JSONObject allObject = new JSONObject();
		allObject.put("id", "0");
		allObject.put("item", firstLevel);
		return allObject.toString();
	}

	public JSONObject getJOString(String id, String lablename, boolean flag,
			JSONArray userdata) {
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		jo.put("text", lablename);
		jo.put("checked", flag);
		jo.put("userdata", userdata);
		return jo;
	}

	public JSONObject getJOString(String id, String lablename,
			JSONArray children) {
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		jo.put("text", lablename);
		jo.put("item", children);
		return jo;
	}

	public JSONArray getJAString(String uid) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("name", "uid");
		jo.put("content", uid);
		ja.add(jo);
		return ja;
	}

	// 已阅读
	public void read(String id, String type, UserInfo userInfo) {
		replyApproverDAO.updateReader(userInfo.getUserID(), id, type);

		// 保存流程历史
		OverHaulApply apply = overHaulApplyDao.findByUnique("id", id);
		OverHaul overHaul = loadOverHaul(apply.getTaskId());
		workflowBo.saveProcessHistory(userInfo, overHaul, null, "", "", "已阅读",
				"");
	}

	// 待办工作
	public List<OverHaul> getToDoWork(UserInfo userInfo, String taskName) {
		List<OverHaul> byDept = workflowBo.queryForHandleList(userInfo
				.getDeptID(), taskName);
		List<OverHaul> byUser = workflowBo.queryForHandleList(userInfo
				.getUserID(), taskName);
		byDept.addAll(byUser);
		return setReadOnly(byDept, userInfo);
	}

	// 设置待办列表
	public List<OverHaul> setReadOnly(List<OverHaul> list, UserInfo userInfo) {
		Map<String, String> map = getMaps();
		List<OverHaul> remove = new ArrayList<OverHaul>();
		for (OverHaul overHaul : list) {
			if (map.containsKey(overHaul.getState())) {
				String subflowId = overHaul.getSubflowId();
				OverHaulApply overHaulApply = overHaulApplyDao
						.getFromProcessInstanceId(subflowId);
				// 是抄送人
				if (replyApproverDAO.isReadOnly(overHaulApply.getId(), userInfo
						.getUserID(), map.get(overHaul.getState()))) {
					// 抄送人已阅读
					if (replyApproverDAO.isReaded(overHaulApply.getId(),
							userInfo.getUserID(), map.get(overHaul.getState()))) {
						remove.add(overHaul);
					} else { // 未阅读
						overHaul.setReadOnly(true);
					}
				}
			}
		}
		// 移除已阅读
		list.removeAll(remove);
		return list;
	}

	// 设置对象
	public OverHaulApply setReadOnly(OverHaulApply overHaulApply,
			UserInfo userInfo) {
		Map<String, String> map = getMaps();
		if (map.containsKey(overHaulApply.getState())) {
			if (replyApproverDAO.isReadOnly(overHaulApply.getId(), userInfo
					.getUserID(), map.get(overHaulApply.getState()))) {
				overHaulApply.setReadOnly(true);
			}
		}
		return overHaulApply;
	}

	public Map<String, String> getMaps() {
		Map<String, String> stateMap = new HashMap<String, String>();
		stateMap.put(Constant.APPLY, Constant.MODULE);

		return stateMap;
	}

	// 查询统计
	public List<OverHaul> getResult(OverHaulQueryBean queryBean,
			UserInfo userInfo) {
		List<OverHaul> result = dao.getResult(queryBean, userInfo);
		return result;
	}

	// 已办工作
	public List<OverHaul> getFinishedWork(UserInfo userInfo) {
		return dao.getFinishedWork(userInfo);
	}

	// 待办工作流程图（各节点封装成Process对象）
	public Process getProcessNumber(UserInfo userInfo) {
		Process process = new Process();
		List<OverHaul> list = getToDoWork(userInfo, "");
		for (OverHaul overHaul : list) {
			if (overHaul.getFlowTaskName().equals("treat")) { // 制定计划后
				if (userInfo.getDeptype().equals("1")) { // 移动部门，增加处理数量
					int treat = process.getTreatNo();
					process.setTreatNo(++treat);
				} else { // 代维部门，增加申请数量
					int apply = process.getApplyNo();
					process.setApplyNo(++apply);
				}
			} else if (overHaul.getFlowTaskName().equals("apply")) { // 需要修改申请，增加申请数量
				// int editApply = process.getEditApplyNo();
				// process.setEditApplyNo(++editApply);
				int apply = process.getApplyNo();
				process.setApplyNo(++apply);
			} else if (overHaul.getFlowTaskName().equals("approve")) { // 提交申请，增加审核数量
				int approve = process.getApproveNo();
				process.setApproveNo(++approve);
			}
		}
		return process;
	}

	// 得到大修任务分配给代维的名称
	public String loadOverHaulCon(String taskId) {
		List<OverHaulCon> list = overHaulConDao
				.findByProperty("taskid", taskId);
		List<String> names = new ArrayList<String>();
		for (OverHaulCon con : list) {
			String contractorId = con.getContractorId();
			String contractorName = dao.getConName(contractorId);
			names.add(contractorName);
		}
		return StringUtils.join(names.iterator(), ",");
	}

	public String getUserName(String userId) {
		return dao.getUserName(userId);
	}

	public String getDeptName(String userId) {
		return dao.getConName(userId);
	}

	public String getPhoneFromUserid(String userId) {
		return dao.getPhoneFromUserid(userId);
	}

	public List<String> getPrincipal(List<String> deptIds) {
		List<String> principals = new ArrayList<String>();
		for (String deptId : deptIds) {
			List<Map> list = dao.getPrincipal(deptId);
			if (!list.isEmpty()) {
				for (Map map : list) {
					principals.add((String) map.get("linkmaninfo"));
				}
			}
		}
		return principals;
	}

	@Override
	protected HibernateDao<OverHaul, String> getEntityDao() {
		return dao;
	}

	public void cancelOverHaul(OverHaulBean bean, UserInfo userInfo) {
		// TODO Auto-generated method stub
		OverHaul overHaul = dao.get(bean.getId());
		dao.initObject(overHaul);
		overHaul.setCancelReason(bean.getCancelReason());
		overHaul.setCancelTime(new Date());
		overHaul.setCancelUserId(userInfo.getUserID());
		overHaul.setState(OverHaul.CANCELED_STATE);
		dao.save(overHaul);
		List<OverHaulApply> applyList = overHaulApplyDao.getApply(userInfo,
				overHaul.getId());
		String processInstanceId = "";
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processInstanceId = OverhaulWorkflowBO.PROCESSINGNAME + "."
				+ overHaul.getId();
		processHistoryBean.initial(null, userInfo, processInstanceId,
				ModuleCatalog.OVERHAUL);
		processHistoryBean.setHandledTaskName("any");
		processHistoryBean.setNextOperateUserId("");
		processHistoryBean.setObjectId(overHaul.getId());
		processHistoryBean.setProcessAction("大修申请流程取消");
		processHistoryBean.setTaskOutCome("cancel");
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
			String subProcessInstanceId = "";
			OverHaulApply apply;
			for (int i = 0; applyList != null && i < applyList.size(); i++) {
				apply = applyList.get(i);
				subProcessInstanceId = OverhaulWorkflowBO.SUBPROCESSINGNAME
						+ "." + apply.getId();
				workflowBo.endProcessInstance(subProcessInstanceId);
			}
			workflowBo.endProcessInstance(processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
	/**
	 * 查询代维
	 * 
	 * @param user
	 * @return
	 */
	public List<Contractor> getContractors(UserInfo user) {
		return dao.getContractors(user);
	}
}
