package com.cabletech.linepatrol.specialplan.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.dao.jdbc.mapper.KeyValue;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSpplanDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardSpplan;
import com.cabletech.linepatrol.safeguard.services.SafeguardPlanBo;
import com.cabletech.linepatrol.specialplan.beans.CircuitTaskBean;
import com.cabletech.linepatrol.specialplan.beans.SpecialPlanBean;
import com.cabletech.linepatrol.specialplan.beans.TaskRoute;
import com.cabletech.linepatrol.specialplan.beans.WatchTaskBean;
import com.cabletech.linepatrol.specialplan.dao.SpecialPlanDao;
import com.cabletech.linepatrol.specialplan.module.SpecialCircuit;
import com.cabletech.linepatrol.specialplan.module.SpecialPlan;
import com.cabletech.linepatrol.specialplan.module.SpecialTaskRoute;
import com.cabletech.linepatrol.specialplan.module.SpecialWatch;
import com.cabletech.linepatrol.watch.service.WatchManager;
@Service

public class SpecialPlanManager extends EntityManager{
	public static final String OBJECT_TYPE="LP_SPECIAL_PLAN";
	public static final String WATCH_PLAN="001";
	public static final String SAFEGUARD_PLAN="002";
	@Resource(name="specialPlanDao")
	private SpecialPlanDao spDao;
	@Resource(name="replyApproverDAO")
	private ReplyApproverDAO replyDao;
	@Resource(name="watchManager")
	private WatchManager watchManager;
	@Resource(name="safeguardPlanBo")
	private SafeguardPlanBo safeguardPlanBo;
	@Resource(name="safeguardSpplanDao")
	private SafeguardSpplanDao safeguardSpplanDao;
	@Override
	protected HibernateDao getEntityDao() {
		return spDao;
	}
	
	@Transactional
	public void saveSpecialPlan(SpecialPlan specialPlan)  throws ServiceException{
		spDao.save(specialPlan);
	}
	/**
	 * 保存特巡计划
	 * @param specialPlan   特巡计划基本信息
	 * @param circuitTasks  特巡巡回任务
	 * @param watchTasks    特巡盯防任务
	 * @param isSet 		统一设置维护组
	 * @param patrolGroupId 维护组ID
	 */
	@Transactional
	public void saveSpecialPlan(SpecialPlan specialPlan,List<SpecialCircuit> circuitTasks,List<SpecialWatch> watchTasks,boolean isSet,String patrolGroupId)  throws ServiceException{
		//巡回任务
		for(SpecialCircuit ct:circuitTasks){
			if(isSet){
				ct.setPatrolGroupId(patrolGroupId);
			}
			for(SpecialTaskRoute str :ct.getTaskRoutes()){
				str.setCircuitTask(ct);// 关联巡回任务
			}
			ct.setSpecialPlan(specialPlan);
			specialPlan.addCircuitTask(ct);
		}
		//盯防任务
		for(SpecialWatch sw:watchTasks){
			if(isSet){
				sw.setPatrolGroupId(patrolGroupId);
			}
			specialPlan.addWatchTask(sw);
		}
		spDao.save(specialPlan);
	}
	/**
	 
	 * @param splanBean
	 * @param circuitTasks
	 * @param watchTasks
	 * @param isSet
	 * @param user
	 * @return
	 */
	/**
	 * 保存特巡计划
	 * 当特巡计划没有任何类型任务时不进行保存；当巡回任务次数为0，或者巡回任务线段数为0时不对该任务进行保存
	 * @param splanBean  特巡计划基本信息
	 * @param circuitTasks  巡回任务
	 * @param watchTasks  盯防任务
	 * @param isSet 统一设置巡检维护组
	 * @param flag  是否需要审核
	 * @param user  当前用户
	 * @return
	 * @throws ServiceException
	 */
	@Transactional
	public SpecialPlan saveSpecialPlan(SpecialPlanBean splanBean,Map<String,CircuitTaskBean> circuitTasks,Map<String,WatchTaskBean> watchTasks,boolean isSet,boolean flag,UserInfo user)  throws ServiceException{
		SpecialPlan specialPlan = new SpecialPlan();
		specialPlan.setPlanName(splanBean.getPlanName());
		specialPlan.setPlanType(splanBean.getPlanType());
		specialPlan.setStartDate(splanBean.getStartDate());
		specialPlan.setId(splanBean.getId());
		if(splanBean.getEndDate() != null && !"".equals(splanBean.getEndDate())){
			specialPlan.setEndDate(splanBean.getEndDate());
		}
		specialPlan.setRegionId(user.getRegionid());
		specialPlan.setCreator(user.getUserID());
		specialPlan.setCreateDate(new Date());
		String patrolGroupId = splanBean.getPatrolGroupId();
		if(patrolGroupId == null || "".equals(patrolGroupId)){
			logger.info("巡检组为空");
			return specialPlan;
		}
		logger.info("SpecialPlanBean:"+splanBean);
		
		//巡回任务
		Iterator ctask_it = circuitTasks.keySet().iterator();
		SpecialCircuit ct = null;
		SpecialTaskRoute str = null;
		while(ctask_it.hasNext()){
			CircuitTaskBean ctaskBean = circuitTasks.get(ctask_it.next().toString());
			if(ctaskBean.getPatrolNum().intValue()!=0 && ctaskBean.getTaskSubline().size() != 0){//验证任务巡回次数是否为0，以及巡回任务线段数是否为0
				ct = new SpecialCircuit();
				ct.setId(ctaskBean.getId());
				ct.setLineLevel(ctaskBean.getLineLevel());
				ct.setPatrolNum(ctaskBean.getPatrolNum());
				ct.setTaskName(ctaskBean.getTaskName());
				ct.setStartTime(ctaskBean.getStartTime());
				ct.setEndTime(ctaskBean.getEndTime());
				if(isSet){
					ct.setPatrolGroupId(splanBean.getPatrolGroupId());
				}
				
				Iterator taskSubline_it = ctaskBean.getTaskSubline().keySet().iterator();
				TaskRoute route = null;
				while(taskSubline_it.hasNext()){
					String sublineid = taskSubline_it.next().toString();
					route = ctaskBean.getTaskSubline().get(sublineid);
					str = new SpecialTaskRoute();
					str.setId(route.getId());
					str.setSublineId(route.getSublineId());
					
					str.setCircuitTask(ct);
					ct.addTaskRoutes(str);
				}
				logger.info("SpecialCircuit:"+ct);
				ct.setSpecialPlan(specialPlan);
				specialPlan.addCircuitTask(ct);
			}
		}
		
		
		
		//盯防任务
		Iterator wtask_it = watchTasks.keySet().iterator();
		SpecialWatch sw = null;
		while(wtask_it.hasNext()){
			String areaId = wtask_it.next().toString();
			WatchTaskBean wtaskBean = watchTasks.get(areaId);
			sw = new SpecialWatch();
			sw.setAreaId(wtaskBean.getAreaId());
			sw.setAreaName(wtaskBean.getAreaName());
			sw.setSpace(wtaskBean.getSpace());
			sw.setStartTime(wtaskBean.getStartTime());
			sw.setEndTime(wtaskBean.getEndTime());
			if(isSet){
				sw.setPatrolGroupId(splanBean.getPatrolGroupId());
			}
			logger.info("SpecialWatch:"+sw);
			sw.setSpecialPlan(specialPlan);
			specialPlan.addWatchTask(sw);
		}
		logger.info("SpecialPlan:"+specialPlan);
		//巡回任务或者盯防任务数为0时不进行保存
		if(specialPlan.getCircuitTasks()!= null || specialPlan.getWatchTasks() != null){
			spDao.save(specialPlan);
			if(WATCH_PLAN.equals(splanBean.getPlanType())){
				String approver = "";
				String reader = "";
				if(flag){
					approver = splanBean.getApprover();
					reader = splanBean.getReader();
				}
				watchManager.makePlanWorkFlow(user, approver, reader, splanBean.getBusinessId(), flag, specialPlan);
			}else if(SAFEGUARD_PLAN.equals(splanBean.getPlanType())){
				String safeguardPlanId = splanBean.getBusinessId();
				String specialPlanId = specialPlan.getId();
				if(safeguardSpplanDao.findBySafeguardAndSp(safeguardPlanId, specialPlanId).size() == 0){
					SafeguardSpplan safeguardSpplan = new SafeguardSpplan();
					safeguardSpplan.setPlanId(safeguardPlanId);
					safeguardSpplan.setSpplanId(specialPlanId);
					safeguardSpplanDao.save(safeguardSpplan);
				}
				//safeguardPlanBo.saveSpecPlan(safeguardPlanId,specialPlanId,approver,reader,user);
			}
			//保存审核人,保存抄送人
			if("true".equals(splanBean.getIsApply())){
				replyDao.saveApproverOrReader(splanBean.getApprover(), specialPlan.getId(), CommonConstant.APPROVE_MAN,OBJECT_TYPE);
				replyDao.saveApproverOrReader(splanBean.getReader(), specialPlan.getId(), CommonConstant.APPROVE_READ,OBJECT_TYPE);
			}
		}
		return specialPlan;
	}
	
	/***
	 * 根据特巡计划类型查询某类型的所有计划信息
	 * @param user
	 * @param type
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<SpecialPlan> getPlanByType(UserInfo user,String type){
		return spDao.getPlanByType(user.getRegionid(),type);
		 
	}
	@Transactional(readOnly=true)
	public SpecialPlan getSpecialPlan(String planId){
		SpecialPlan sp = spDao.findUniqueByProperty("id",planId );
		spDao.initObjects(sp.getCircuitTasks());
		sp.getCircuitTasks().iterator();
		Iterator it =sp.getCircuitTasks().iterator();
		while(it.hasNext()){
			SpecialCircuit sc =(SpecialCircuit)it.next();
			spDao.initObjects(sc.getTaskRoutes());
		}
		spDao.initObjects(sp.getWatchTasks());
		
		for(SpecialWatch s : sp.getWatchTasks()){
			sp.setPatrolGroupId(s.getPatrolGroupId());
			break;
		}
		return sp;
	}
	/**
	 * 获得线路级别
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<KeyValue> getCableLevel() {
		return spDao.getCableLevel();
	}
	/**
	 * 获得当前用户负责的某个级别的指定路由段名称的所有的路由段，
	 * 并对用户已经选择的路由段进行勾选
	 * @param taskSublines
	 * @param user
	 * @param lineLevel
	 * @param lineNameKey
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getTaskSubline(Map<String, TaskRoute> taskSublines,UserInfo user,String lineLevel,String lineNameKey){
		List<KeyValue> allSubline = spDao.getTaskSublines(user.getRegionid(),user.getDeptID(),lineLevel,"%"+lineNameKey+"%");
		return compareTaskSubline(taskSublines,allSubline);
	}
	/**
	 * 对用户已经选择的路由段进行勾选。
	 * @param selSubline 
	 * @param allSubline 
	 * @return
	 */
	private String compareTaskSubline(Map<String,TaskRoute> selSubline,List<KeyValue> allSubline){
		StringBuilder strHtml = new StringBuilder();
		for(KeyValue kv: allSubline){
			strHtml.append("<input type='checkbox' name='tasksubline'  value='"+kv.getKey()+"'");
			
			if(selSubline!= null && selSubline.get(kv.getKey()) != null){
				strHtml.append(" checked='checked' ");
			}
			strHtml.append("/>"+kv.getValue()+"<br>");
		}
		return strHtml.toString();		
	}
	@Transactional(readOnly=true)
	public CircuitTaskBean addTask2Map(CircuitTaskBean circuitTask,List<String>taskSubline,UserInfo user) {
		Map<Object, Object> sublines = spDao.getSublines(user,user.getRegionid(),circuitTask.getLineLevel());
		
		Map<String,TaskRoute> taskSublineMap = null;
		if(circuitTask.getTaskSubline() != null){
			taskSublineMap = circuitTask.getTaskSubline();
		}else{
			taskSublineMap = new HashMap<String,TaskRoute>();
		}
		TaskRoute route = null;
		for(String sublineid:taskSubline){
			route = new TaskRoute();
			route.setSublineId(sublineid);
			route.setSublineName(sublines.get(sublineid).toString());
			taskSublineMap.put(sublineid, route);
		}
		circuitTask.setTaskSubline(taskSublineMap);
		return circuitTask;
	}
	/**
	 * 获得通过gis制定好的任务区域
	 * @param user
	 * @param selTask
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getTaskArea(UserInfo user,Map<String,WatchTaskBean> selTask) {
		StringBuilder strHtml = new StringBuilder();
		List<KeyValue> taskAreas = spDao.getTaskArea(user.getRegionid(),user.getDeptID());
		for(KeyValue kv:taskAreas){
			strHtml.append("<input type='checkbox' name='taskArea'  value='"+kv.getKey()+"'");
			if(selTask!= null){
				WatchTaskBean a = selTask.get(kv.getKey());
			}
			if(selTask!= null && selTask.get(kv.getKey()) != null){
				strHtml.append(" checked='checked' ");
			}
			strHtml.append("/>"+kv.getValue()+"<br>");
		}
		return strHtml.toString();		
	}
	/**
	 * 将盯防任务添加到map中
	 * @param user
	 * @param startTime
	 * @param endTime
	 * @param space
	 * @param taskArea
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<String, WatchTaskBean> addWatchTask2Map(UserInfo user,String startTime, String endTime, String space, String taskArea) {
		List<String> taskAreaList = StringUtils.string2List(taskArea,",");
		Map<Object, Object> allwatchtasks = spDao.getWatchArea(user.getRegionid(),user.getDeptID());
		WatchTaskBean wtb = null;
		Map<String,WatchTaskBean> watchTasks = new HashMap<String,WatchTaskBean>();
		String areaName = null;
		for(String areaid:taskAreaList){
			areaName =  allwatchtasks.get(areaid).toString();
			wtb = new WatchTaskBean(null,startTime,endTime,space,areaid,areaName);
			watchTasks.put(areaid, wtb);
		}
		return watchTasks;
	}
	/**
	 * 获得巡检组
	 * @param user
	 * @return
	 */
	public List<KeyValue> getPatrolGroups(UserInfo user) {
		return spDao.getPatrolGroups(user.getRegionid(),user.getDeptID());
	}
	@Transactional
	public void setWatchEndDate(String planid,String endDate) throws ServiceException{
		SpecialPlan sPlan = spDao.get(planid);
		sPlan.setEndDate(DateUtil.Str2UtilDate(endDate, "yyyy/MM/dd HH:mm:ss"));
		spDao.save(sPlan);
	}
	/**
	 * 重组circuitTask对象
	 * @param sp
	 * @param circuitTasks
	 * @param user
	 */
	@Transactional(readOnly=true)
	public void getCircuitTask(SpecialPlan sp,Map<String,CircuitTaskBean> circuitTasks,UserInfo user) throws ServiceException {
		java.util.Set<SpecialCircuit> ctSet = sp.getCircuitTasks();
		CircuitTaskBean ctb = null;
		Map<String,TaskRoute> taskRoutesMap = null;
		Map<Object, Object> sublines = null;
		TaskRoute tr = null;
		for(SpecialCircuit ct:ctSet){
			ctb = new CircuitTaskBean();
			ctb.setId(ct.getId());
			ctb.setLineLevel(ct.getLineLevel());
			ctb.setPatrolNum(ct.getPatrolNum());
			ctb.setStartTime(ct.getStartTime());
			ctb.setEndTime(ct.getEndTime());
			ctb.setTaskName(ct.getTaskName());
			sublines = spDao.getSublines(user,user.getRegionid(),ct.getLineLevel());
			taskRoutesMap = new HashMap<String,TaskRoute>();
			for(SpecialTaskRoute str : ct.getTaskRoutes()){
				tr = new TaskRoute(str.getSublineId(),sublines.get(str.getSublineId()).toString());
				tr.setId(str.getId());
				taskRoutesMap.put(str.getSublineId(), tr);
			}
			ctb.setTaskSubline(taskRoutesMap);
			sp.setPatrolGroupId(ct.getPatrolGroupId());
			circuitTasks.put(ct.getLineLevel(), ctb);
		}
		
	}
	/**
	 * 重组watchTasks对象
	 * @param sp
	 * @param circuitTasks
	 * @param user
	 */
	@Transactional(readOnly=true)
	public void getWatchtTask(SpecialPlan sp, Map<String, WatchTaskBean> watchTasks, UserInfo user) {
		java.util.Set<SpecialWatch> wtSet = sp.getWatchTasks();
		WatchTaskBean wtb = null;
		
		for(SpecialWatch sw :wtSet){
			wtb = new WatchTaskBean();
			wtb.setAreaId(sw.getAreaId());
			wtb.setAreaName(sw.getAreaName());
			wtb.setId(sw.getId());
			wtb.setSpace(sw.getSpace().toString());
			wtb.setStartTime(sw.getStartTime());
			wtb.setEndTime(sw.getEndTime());
			
			watchTasks.put(sw.getAreaId(), wtb);
		}
		
	}
	
	@Transactional
	public void deleteSpecialPlan(String spplanId) throws ServiceException {
		safeguardSpplanDao.delteSafeguardSpplanBySsplanId(spplanId);
		spDao.deleteSpecialPlan(spplanId);
	}
}
