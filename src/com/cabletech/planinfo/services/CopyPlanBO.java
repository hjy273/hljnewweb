package com.cabletech.planinfo.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.beans.String2Date;
import com.cabletech.commons.generatorID.CustomID;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.services.DBService;
import com.cabletech.planinfo.bean.CopyBean;
import com.cabletech.planinfo.beans.TaskBean;
import com.cabletech.planinfo.domainobjects.ExecuteLog;
import com.cabletech.planinfo.domainobjects.Plan;
import com.cabletech.planinfo.domainobjects.PlanTaskList;
import com.cabletech.planinfo.domainobjects.PlanTaskSubline;
import com.cabletech.planinfo.domainobjects.SubTask;
import com.cabletech.planinfo.domainobjects.Task;
import com.cabletech.planinfo.domainobjects.TaskOperationList;
/**
 * 计划复制时出现的错误规则定义：
 * -1:在计划复制时应用程序出现异常。
 * 0：表示正常返回，计划创建成功；
 * 1：计划复制失败，原因：没有找到巡检线段！
 * 
 *
 */
public class CopyPlanBO extends PlanBaseService{
	private Logger logger = Logger.getLogger("CopyPlanBO");
	
	public ExecuteLog copyPlan(CopyBean cb,ExecuteLog log) {
		PlanBO pbo = new PlanBO();
		Plan plan = null;
		List taskList = new ArrayList();
		try{
			plan = pbo.loadPlan(cb.getPrePlanId());//获取计划.
			taskList = pbo.getTaskList(plan.getId());
		}catch(Exception e){
			logger.error("计划复制 -在加载待复制计划时异常："+e.getMessage());
			plan =null;
			log.setResult("计划复制失败，原因：在加载待复制计划时出现 "+e.getMessage()+"异常");
			log.setException("在加载待复制计划时出现 "+e.getMessage()+"异常");
		}
		
		if(plan != null && !taskList.isEmpty()){
			paste(plan, taskList,cb,log);
		}
		return log;
		
	}
	
	public int paste(Plan plan,List taskList,CopyBean cb,ExecuteLog log){
			DBService dbs = new DBService();
			UpdateUtil upd = null;
			String result = "对"+plan.getPlanname()+"进行了复制";
			try {
				upd = new UpdateUtil();
				upd.setAutoCommitFalse();
				String planid=dbs.getSeq("PLAN", 20);
				int endIndex = plan.getPlanname().indexOf("20");
				int length = cb.getEndDate().length();
				
				String planname = (plan.getPlanname().substring(0, endIndex)+""+cb.getStartDate()+"至"+cb.getEndDate().substring(length-2,length)+"巡检计划");
				String sql = "insert into plan(id,planname,executorid,creator,createdate,begindate,enddate,regionid,ifinnercheck,patrolmode) values " +
						"('"+planid+"','"+planname+"','"+plan.getExecutorid()+"','"+plan.getCreator()+"',sysdate,to_date('"+cb.getStartDate()+"','yyyy/mm/dd'),to_date('"+cb.getEndDate()+"','yyyy/mm/dd'),'"+plan.getRegionid()+"','"+plan.getIfinnercheck()+"','"+plan.getPatrolmode()+"')";
				result +="创建了"+planname+", 包含"+taskList.size()+"子任务";
				logger.info("创建"+planname+"计划,计划id为 "+planid +", 包含子任务有：");
				upd.executeUpdate(sql);
				//处理任务
				for (int i = 0; i < taskList.size(); i++) {
					TaskBean taskbean = (TaskBean) taskList.get(i);
					Task task = new Task();
					BeanUtil.objectCopy(taskbean, task);
					task.setTaskpoint(taskbean.getTaskPoint().size()+"");
					// 删除计划任务关联表
					
					logger.info("	子任务：" + task.getId() +";"+task.getLinelevel()+";"+ task.getDescribtion()+";");
					PlanTaskList ptList = new PlanTaskList();
					
					task.setId(dbs.getSeq("taskinfo", 20));	
					
					//任务操作
					for (int j = 0; j < taskbean.getTaskOpList().size(); j++) {
						TaskOperationList toList = (TaskOperationList) taskbean
								.getTaskOpList().get(j);
						toList.setTaskid(task.getId());
						toList.setId(dbs.getSeq("TASKOPERATIONLIST", 20));
						String tol = "insert into TASKOPERATIONLIST(id,taskid,operationid) values('"
								+ toList.getId()
								+ "','"
								+ toList.getTaskid()
								+ "','" + toList.getOperationid() + "')";
						upd.executeUpdate(tol);
					}
//					处理任务点
					CustomID idFactory = new CustomID();
					String[] idArr = idFactory.getStrSeqs(taskbean.getTaskPoint()
							.size(), "subtaskinfo", 20);
					for (int k = 0; k < taskbean.getTaskPoint().size(); k++) {
						SubTask subtask = (SubTask) taskbean.getTaskPoint().get(k);
						subtask.setId(idArr[k]);
						subtask.setTaskid(task.getId());
						String sp_sql = "insert into subtaskinfo(id,taskid,objectid) values('"
								+ subtask.getId()
								+ "','"
								+ subtask.getTaskid()
								+ "','" + subtask.getObjectid() + "') ";
						upd.executeUpdate(sp_sql);
					}
					
//					任务线段
					String[] SidArr = idFactory.getStrSeqs(taskbean.getTaskSubline()
							.size(), "plantasksubline", 10,"tsblid");
					int sublinesize =taskbean.getTaskSubline().size();
					if(sublinesize <=0){
						try {
							upd.rollback();
						} catch (Exception e1) {
							logger.error("没有找到巡检线段时，处理事务回滚出现异常：" + e1.getMessage());
							//记录日志
							e1.printStackTrace();
							log.setResult("计划复制失败，你可以重试或请与管理员联系。");
							log.setException("没有找到巡检线段时，处理事务回滚出现异常：" + e1.getMessage());
							return -1;
						}
//						记录日志
						logger.error("计划复制失败，原因：没有找到巡检线段！");
						log.setResult("计划复制失败，原因：没有找到巡检线段！");
						log.setException("有可能上个计划制定有问题，没有找到巡检线段；还有可能数据加载时出现问题。");
						return 1;
					}
					
					for(int l=0; l<sublinesize;l++){
						PlanTaskSubline tasksubline = (PlanTaskSubline)taskbean.getTaskSubline().get(l);
						tasksubline.setTaskid(task.getId());
						tasksubline.setID(SidArr[l]);
						upd.executeUpdate("insert into  plantasksubline(tsblid,sublineid,taskid) values ('"+tasksubline.getID()+"','"+tasksubline.getSublineid()+"','"+tasksubline.getTaskid()+"')");
					}
					ptList.setId(dbs.getSeq("plantasklist", 50));
					ptList.setPlanid(planid);
					ptList.setTaskid(task.getId());
					upd.executeUpdate("insert into Plantasklist(id,planid,taskid) values('"
									+ ptList.getId()
									+ "','"
									+ ptList.getPlanid()
									+ "','" + ptList.getTaskid() + "')");
					
					upd.executeUpdate("insert into taskinfo (id,excutetimes,describtion,regionid,linelevel,taskpoint) values('"
										+ task.getId()
										+ "','"
										+ task.getExcutetimes()
										+ "','"
										+ task.getDescribtion()
										+ "','"
										+ task.getRegionid()
										+ "','"
										+ task.getLinelevel() 
										+ "','" 
										+ task.getTaskpoint()+ "')");
					
				}
				log.setResult("计划复制成功."+result);
				upd.commit();
				upd.setAutoCommitTrue();
				return 0;
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("在CopyPlanBO.java文件的42-144行间，出现"+e.getMessage()+"异常。");
				log.setResult("计划复制失败，你可以重试或请与管理员联系。");
				log.setException("在CopyPlanBO.java文件的42-144行间，出现"+e.getMessage()+"异常。");
				logger.error(e.getStackTrace());
//				记录日志
				return -1;
			}
	}
}
