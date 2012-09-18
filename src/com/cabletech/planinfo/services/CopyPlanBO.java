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
 * �ƻ�����ʱ���ֵĴ�������壺
 * -1:�ڼƻ�����ʱӦ�ó�������쳣��
 * 0����ʾ�������أ��ƻ������ɹ���
 * 1���ƻ�����ʧ�ܣ�ԭ��û���ҵ�Ѳ���߶Σ�
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
			plan = pbo.loadPlan(cb.getPrePlanId());//��ȡ�ƻ�.
			taskList = pbo.getTaskList(plan.getId());
		}catch(Exception e){
			logger.error("�ƻ����� -�ڼ��ش����Ƽƻ�ʱ�쳣��"+e.getMessage());
			plan =null;
			log.setResult("�ƻ�����ʧ�ܣ�ԭ���ڼ��ش����Ƽƻ�ʱ���� "+e.getMessage()+"�쳣");
			log.setException("�ڼ��ش����Ƽƻ�ʱ���� "+e.getMessage()+"�쳣");
		}
		
		if(plan != null && !taskList.isEmpty()){
			paste(plan, taskList,cb,log);
		}
		return log;
		
	}
	
	public int paste(Plan plan,List taskList,CopyBean cb,ExecuteLog log){
			DBService dbs = new DBService();
			UpdateUtil upd = null;
			String result = "��"+plan.getPlanname()+"�����˸���";
			try {
				upd = new UpdateUtil();
				upd.setAutoCommitFalse();
				String planid=dbs.getSeq("PLAN", 20);
				int endIndex = plan.getPlanname().indexOf("20");
				int length = cb.getEndDate().length();
				
				String planname = (plan.getPlanname().substring(0, endIndex)+""+cb.getStartDate()+"��"+cb.getEndDate().substring(length-2,length)+"Ѳ��ƻ�");
				String sql = "insert into plan(id,planname,executorid,creator,createdate,begindate,enddate,regionid,ifinnercheck,patrolmode) values " +
						"('"+planid+"','"+planname+"','"+plan.getExecutorid()+"','"+plan.getCreator()+"',sysdate,to_date('"+cb.getStartDate()+"','yyyy/mm/dd'),to_date('"+cb.getEndDate()+"','yyyy/mm/dd'),'"+plan.getRegionid()+"','"+plan.getIfinnercheck()+"','"+plan.getPatrolmode()+"')";
				result +="������"+planname+", ����"+taskList.size()+"������";
				logger.info("����"+planname+"�ƻ�,�ƻ�idΪ "+planid +", �����������У�");
				upd.executeUpdate(sql);
				//��������
				for (int i = 0; i < taskList.size(); i++) {
					TaskBean taskbean = (TaskBean) taskList.get(i);
					Task task = new Task();
					BeanUtil.objectCopy(taskbean, task);
					task.setTaskpoint(taskbean.getTaskPoint().size()+"");
					// ɾ���ƻ����������
					
					logger.info("	������" + task.getId() +";"+task.getLinelevel()+";"+ task.getDescribtion()+";");
					PlanTaskList ptList = new PlanTaskList();
					
					task.setId(dbs.getSeq("taskinfo", 20));	
					
					//�������
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
//					���������
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
					
//					�����߶�
					String[] SidArr = idFactory.getStrSeqs(taskbean.getTaskSubline()
							.size(), "plantasksubline", 10,"tsblid");
					int sublinesize =taskbean.getTaskSubline().size();
					if(sublinesize <=0){
						try {
							upd.rollback();
						} catch (Exception e1) {
							logger.error("û���ҵ�Ѳ���߶�ʱ����������ع������쳣��" + e1.getMessage());
							//��¼��־
							e1.printStackTrace();
							log.setResult("�ƻ�����ʧ�ܣ���������Ի��������Ա��ϵ��");
							log.setException("û���ҵ�Ѳ���߶�ʱ����������ع������쳣��" + e1.getMessage());
							return -1;
						}
//						��¼��־
						logger.error("�ƻ�����ʧ�ܣ�ԭ��û���ҵ�Ѳ���߶Σ�");
						log.setResult("�ƻ�����ʧ�ܣ�ԭ��û���ҵ�Ѳ���߶Σ�");
						log.setException("�п����ϸ��ƻ��ƶ������⣬û���ҵ�Ѳ���߶Σ����п������ݼ���ʱ�������⡣");
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
				log.setResult("�ƻ����Ƴɹ�."+result);
				upd.commit();
				upd.setAutoCommitTrue();
				return 0;
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("��CopyPlanBO.java�ļ���42-144�м䣬����"+e.getMessage()+"�쳣��");
				log.setResult("�ƻ�����ʧ�ܣ���������Ի��������Ա��ϵ��");
				log.setException("��CopyPlanBO.java�ļ���42-144�м䣬����"+e.getMessage()+"�쳣��");
				logger.error(e.getStackTrace());
//				��¼��־
				return -1;
			}
	}
}
