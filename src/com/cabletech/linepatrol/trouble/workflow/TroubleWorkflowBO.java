package com.cabletech.linepatrol.trouble.workflow;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.jbpm.api.Execution;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.workflow.BaseWorkFlow;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleReplyApproveDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleReplyDAO;
import com.cabletech.linepatrol.trouble.module.TroubleReply;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;

@Service
public class TroubleWorkflowBO extends BaseWorkFlow {
	public static final String EVALUATE_TASK = "evaluate_task";
	public static final String REPLY_TASK = "reply_task";
	public static final String EOMS_APPROVE = "edit_dispatch_task";
	public static final String APPROVE_TASK = "approve_task";
	public static final String TROUBLE_WORKFLOW = "trouble";
	@Resource(name = "troubleReplyDAO")
	private TroubleReplyDAO dao;
	
	@Resource(name="replyApproverDAO")
	private ReplyApproverDAO approverDAO;
	
	@Resource(name="troubleReplyApproveDAO")
	private TroubleReplyApproveDAO troubleApproveDAO;
	
	public TroubleWorkflowBO(){
		super(TROUBLE_WORKFLOW);
	}
	/**
	 * 根据某个特定任务完成人查询该人可以完成任务的某些对象实体列表
	 * 
	 * @param assignee
	 *            String 特定任务完成人
	 * @return List<T> 返回该人可以完成任务的某些对象实体列表
	 */
	public List queryForHandleList(String assignee) {
		List resultList = new ArrayList();
		List taskList = taskService.findPersonalTasks(assignee);
		if (taskList == null || taskList.size() < 1) {
			taskList = taskService.findGroupTasks(assignee);
			if (taskList == null || taskList.size() < 1) {
				return resultList;
			}
		}
		Task task;
		for (int i = 0; taskList != null && i < taskList.size(); i++) {
			task = (Task) taskList.get(i);
			String executionId = task.getExecutionId();
			Execution execution = executionService
					.findExecutionById(executionId);
			Object object = getEntityObject(task, execution);
			if (resultList != null && !resultList.contains(object)
					&& object != null) {
				resultList.add(object);
			}
		}

		return resultList;
	}
	
	public List findWaitReplys(UserInfo user,String userid, String taskName){
		String userID = user.getUserID();
		String objectType=TroubleConstant.LP_TROUBLE_REPLY;
		String condition="";
		List list = queryForHandleListBean(user,condition,taskName);
		List troubles = new ArrayList();
		if(list!=null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String troubleid = (String) bean.get("id");
				String flow_state = (String) bean.get("flow_state");
				List<TroubleReply> replys = dao.getReplysByTroubleId(troubleid,"");
				String objectid="";
				boolean flag=true;
				if(replys!=null && replys.size()>0){
					for(int m = 0;m<replys.size();m++){
						TroubleReply reply = replys.get(m);
						objectid = reply.getId();
						boolean read = approverDAO.isReadOnly(objectid, userID, objectType);
						if(read==false){
							flag = false;
							break;
						}
					}
				}
				bean.set("isread", flag+"");
				List wlist = getWaitHandelTroubles(user,bean,troubleid,userid,flag,flow_state);
				if(wlist!=null && wlist.size()>0){
					troubles.add(wlist.get(0));
				}
			}
		}
		//return list;
		return troubles;
	}

	
	public boolean ishaveReply(UserInfo user,String troubleid){
		List replys = troubleApproveDAO.viewReplysByReads(user, troubleid);
		if(replys!=null && replys.size()>0){
			return true;
		}
		return false;
	}


	public List getWaitHandelTroubles(UserInfo user,BasicDynaBean bean,
			String troubleid,String userid,boolean flag,String taskName){
		List troubles = new ArrayList();
		String depttype = user.getDeptype();
		if(depttype.equals("1")){
			if(flag){
				if(ishaveReply(user,troubleid)){//抄送人
					troubles.add(bean);
				}
				if(troubleApproveDAO.getDispatchTrouble(user,troubleid)){//派单人
					List vlist = troubleApproveDAO.viewReplysByApproves(user, troubleid);
					if(vlist!=null && vlist.size()>0){
						troubles.add(bean);
					}else{
						List rlist = troubleApproveDAO.getReplyInfos(troubleid, userid, "dispatch");
						if(rlist!=null && rlist.size()>0){
							troubles.add(bean);
						}
					}
				}
			}

			if(!flag){
				if(taskName.equals(TroubleWorkflowBO.APPROVE_TASK)){//审核人(审核人、既是审核人也是抄送人的情况)
					List waitReplys = troubleApproveDAO.getReplyInfos(troubleid,userid,"");
					int num=0;
					if(waitReplys!=null && waitReplys.size()>0){
						for(int i = 0;i<waitReplys.size();i++){
							BasicDynaBean replybean = (BasicDynaBean) waitReplys.get(i);
							String replyid = (String) replybean.get("id");
							String state = (String) replybean.get("state");
							//	boolean flag=true;
							boolean read = approverDAO.isReadOnly(replyid, userid, TroubleConstant.LP_TROUBLE_REPLY);
							if(read==false){//待审核的
								if(state!=null && state.equals(TroubleConstant.REPLY_APPROVE_WAIT)){
									num++;
									break;
								}
							}
						}
					}
					if(num>0){
						troubles.add(bean);
					}
					if(num<=0){
						boolean ishave = ishaveReply(user,troubleid);
						if(ishave){
							troubles.add(bean);
						}
					}
				}
			}
			if(!taskName.equals(TroubleWorkflowBO.APPROVE_TASK)){//考核
				troubles.add(bean);
			}
		}

		if(depttype.equals("2")){
			troubles.add(bean);
		}
		return troubles;
	}

	/**
	 * 根据某个特定任务完成人和部门编号查询该人可以完成任务的某些对象Bean列表
	 * 
	 * @param assignee
	 *            String 特定任务完成人
	 * @param condition
	 *            String 查询条件
	 * @param taskName
	 * @return List<DynaBean>返回该人可以完成任务的某些对象Bean列表
	 */
	public List queryForHandleListBean(UserInfo user, String condition,
			String taskName) {
		String deptype = user.getDeptype();
		String userid = user.getUserID();
		String deptid = user.getDeptID();
		Task task;
		String executionId;
		Execution execution;
		ProcessDefinitionQuery query;
		List<ProcessDefinition> list;
		ProcessDefinition pdf;
		DynaBean bean;
		DynaBean tmpBean;
		String keyId;
		boolean flag = false;
		List taskList = new ArrayList();
		List resultList = new ArrayList();
		if (deptype.equals("1")) {// 移动待审核
			taskList = super.getTaskList(userid, taskName);
		}
		if (deptype.equals("2")) {// 代维待反馈
			List tasklist = super.getTaskList(userid, taskName);
			List replyTasks = super.getTaskList(deptid, taskName);
			if(replyTasks==null || replyTasks.size()<=0 ){
				taskList = tasklist;
			}else if(tasklist==null || tasklist.size()<=0){
				taskList = replyTasks;
			}else{
				for(int i = 0;i<replyTasks.size();i++){
					int num = 0;
					Task replytask = (Task) replyTasks.get(i);
					String rtaskid = replytask.getId();
					for(int j = 0;j<tasklist.size();j++){
						Task t = (Task) tasklist.get(j);
						String taskid = t.getId();
						if(rtaskid.equals(taskid)){
							num++;
						}
					}
					if(num==0){
						tasklist.add(replytask);
					}
				}
				taskList = tasklist;
			}
		}

		if (taskList == null || taskList.size() == 0) {
			return resultList;
		}
		List prevResultList = dao.findWaitReplys("  ");
		for (int i = 0; prevResultList != null && i < prevResultList.size(); i++) {
			bean = (DynaBean) prevResultList.get(i);
			flag = false;
			task = null;
			for (int j = 0; taskList != null && j < taskList.size(); j++) {
				task = (Task) taskList.get(j);
				executionId = task.getExecutionId();
				// execution = executionService.findExecutionById(executionId);
				if (executionId != null
						&& executionId.substring(0, executionId.indexOf("."))
						.equals(TROUBLE_WORKFLOW)) {
					keyId = executionId.substring(executionId.indexOf(".") + 1);
					if (keyId != null && keyId.equals(bean.get("unitid"))) {
						flag = true;
						break;
					}
					if (deptype.equals("2")) {// 代维待反馈
						if (keyId != null && keyId.equals(bean.get("send_man_id"))) {
							flag = true;
							break;
						}
					}
				}
			}
			if (flag) {
				flag = true;
				for (int j = 0; resultList != null && j < resultList.size(); j++) {
					tmpBean = (DynaBean) resultList.get(j);
					if (bean != null
							&& bean.get("id").equals(tmpBean.get("id"))) {
						flag = false;
						break;
					}
				}
				if (flag) {
					bean.set("flow_state", task.getName());
					resultList.add(bean);
				}
			}
		}
		return resultList;
	}

	/**
	 * 根据某个特定任务完成人和部门编号查询该人可以完成任务的对象数量
	 * 
	 * @param assignee
	 *            String 特定任务完成人
	 * @param condition
	 *            String 查询条件
	 * @param taskName 
	 * @return int 可以完成任务的对象数量
	 */
	public int queryForHandleNumber(UserInfo user, String condition, String taskName) {
		List list = queryForHandleListBean(user, condition, taskName);
		if (list != null) {
			return list.size();
		} else {
			return 0;
		}
	}

	/**
	 * 根据某个特定任务完成人和某个特定对象编号查询该人可以完成这个特定对象的工作流任务
	 * 
	 * @param assignee
	 *            String 特定任务完成人
	 * @param objectId
	 *            String 某个特定对象编号
	 * @return Task 返回该人可以完成这个特定对象的工作流任务
	 */
	public Task getHandleTaskForId(String assignee, String objectId) {
		if (objectId != null && !objectId.equals("")) {
			List taskList = taskService.findPersonalTasks(assignee);
			if (taskList == null || taskList.size() < 1) {
				taskList = taskService.findGroupTasks(assignee);
				if (taskList == null || taskList.size() < 1) {
					return null;
				}
			}
			Execution execution;
			Task task;
			ProcessDefinitionQuery query;
			List<ProcessDefinition> list;
			ProcessDefinition pdf;
			String executionId;
			for (int i = 0; taskList != null && i < taskList.size(); i++) {
				task = (Task) taskList.get(i);
				if(task.getExecutionId().indexOf(TROUBLE_WORKFLOW)!= -1){
					execution = executionService.findExecutionById(task
							.getExecutionId());
					executionId = execution.getKey();
					if (objectId.equals(executionId)) {
						return task;
					}
//					query = repositoryService
//							.createProcessDefinitionQuery()
//							.processDefinitionId(execution.getProcessDefinitionId());
//					list = query.list();
//					for (int j = 0; list != null && j < list.size(); j++) {
//						pdf = list.get(j);
//						if (pdf.getKey().equals(TROUBLE_WORKFLOW)) {
//							executionId = execution.getKey();
//							if (objectId.equals(executionId)) {
//								return task;
//							}
//						}
//					}
				}
			}
		}
		return null;
	}

	/**
	 * 根据工作流任务和工作流整体信息获取对应的实体对象
	 * 
	 * @param task
	 *            Task 工作流任务
	 * @param execution
	 *            Execution 工作流整体信息
	 * @return T 返回对应的实体对象
	 */
	public Object getEntityObject(Task task, Execution execution) {
		// SendAcceptDept dept = dao.get(execution.getKey());
		// return dept;
		return null;
	}

	/**
	 * 根据工作流任务、工作流整体信息和用户部门编号获取对应的对象Bean
	 * 
	 * @param task
	 *            Task 工作流任务
	 * @param execution
	 *            Execution 工作流整体信息
	 * @param condition
	 *            String 查询条件
	 * @return DynaBean 返回对应的对象Bean
	 */
	public DynaBean getEntityBean(Task task, Execution execution,
			String condition) {
		String keyId = execution.getKey();
		DynaBean bean;
		condition = condition + " and unit.id='" + keyId + "' ";
		List list;
		list = new ArrayList();
		list = dao.findWaitReplys(condition);
		if (list != null && list.size() > 0) {
			bean = (DynaBean) list.get(0);
			bean.set("flow_state", task.getName());
			return bean;
		}
		return null;
	}
}
