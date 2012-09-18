package com.cabletech.linepatrol.maintenance.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.dao.UserInfoDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.maintenance.beans.TestYearPlanBean;
import com.cabletech.linepatrol.maintenance.dao.TestYearPlanDAO;
import com.cabletech.linepatrol.maintenance.dao.TestYearPlanTaskDAO;
import com.cabletech.linepatrol.maintenance.dao.TestYearPlanTrunkDAO;
import com.cabletech.linepatrol.maintenance.module.TestData;
import com.cabletech.linepatrol.maintenance.module.TestPlan;
import com.cabletech.linepatrol.maintenance.module.TestYearPlan;
import com.cabletech.linepatrol.maintenance.module.TestYearPlanTask;
import com.cabletech.linepatrol.maintenance.module.TestYearPlanTrunk;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;

@Service
@Transactional
public class TestYearPlanBO extends EntityManager<TestYearPlan, String> {
	@Resource(name = "testYearPlanDAO")
	private TestYearPlanDAO dao;
	@Resource(name = "testYearPlanTaskDAO")
	private TestYearPlanTaskDAO taskDAO;
	@Resource(name = "testYearPlanTrunkDAO")
	private TestYearPlanTrunkDAO trunkDAO;
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDAO;
	@Resource(name="approveDAO")
	private ApproveDAO approveDAO;
	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;

	/**
	 * ��ѯ��ƻ�
	 * 
	 * @param id
	 *            ��ƻ�ϵͳid
	 * @return
	 */
	public TestYearPlan getYearPlanById(String id) {
		return dao.get(id);
	}

	/**
	 * ��ѯ��ƻ��Ƿ����
	 * @param year
	 * @param conid
	 * @return
	 */
	public boolean judgeYearPlanIsHave(String year,String conid,String planid){
		return dao.judgeYearPlanIsHave(year, conid,planid);
	}

	/**
	 * ������ƻ�
	 * @param bean
	 * @param userinfo
	 * @param planLines
	 *            �м̶���Ϣ
	 * @param planStations
	 *            ��վ��Ϣ
	 * @return
	 * @throws Exception
	 */
	public TestYearPlan addTestYearPlan(TestYearPlanBean bean, UserInfo userinfo,
			Map<String,TestYearPlanTask> planTasks) throws Exception {
		String userid = userinfo.getUserID();
		TestYearPlan plan = new TestYearPlan();
		BeanUtil.objectCopy(bean, plan);
		plan.setCreateTime(new Date());
		plan.setContractorId(userinfo.getDeptID());
		plan.setCreatorId(userid);
		plan.setState(MainTenanceConstant.YEAR_PLAN_WAIT_APPROVE);
		dao.save(plan);
		String planid = plan.getId();
		String approver = bean.getApprover();
		String reads = bean.getReads();
		approverDAO.deleteList(planid,MainTenanceConstant.LP_TEST_YEAR_PLAN);
		approverDAO.saveApproverOrReader(approver, planid,
				MainTenanceConstant.APPROVE_MAN,
				MainTenanceConstant.LP_TEST_YEAR_PLAN);
		approverDAO.saveApproverOrReader(reads, planid,
				MainTenanceConstant.APPROVE_READ,
				MainTenanceConstant.LP_TEST_YEAR_PLAN);
		String mobiles = bean.getMobile();
		String rmobiles = bean.getRmobiles();
		saveYearTask(planTasks,planid);
		sendMsg(plan, mobiles, rmobiles);
		return plan;
	}

	/**
	 * ������ƻ�����
	 * @param planTasks
	 * @param yearPlanid
	 */
	public void saveYearTask(Map<String,TestYearPlanTask> planTasks,String yearPlanid){
		Set set = planTasks.keySet();
		Iterator ite = set.iterator();
		taskDAO.deleteTasksByPlanId(yearPlanid);
		while(ite.hasNext()){
			String key = (String) ite.next();
			TestYearPlanTask planTask = planTasks.get(key);
			planTask.setId(null);
			planTask.setYearPlanId(yearPlanid);
			taskDAO.save(planTask);
			String taskid = planTask.getId();
			trunkDAO.deleteTrunksByTaskId(taskid);
			String trunkIds = planTask.getTrunkIds();
			List<String> trunkList = new ArrayList<String>();
			if (StringUtils.isNotBlank(trunkIds)) {
				trunkList = Arrays.asList(trunkIds.split(","));
				for(int i = 0;i<trunkList.size();i++){
					TestYearPlanTrunk trunk = new TestYearPlanTrunk();
					String trunkid = trunkList.get(i);
					trunk.setYearTaskId(taskid);
					trunk.setTrunkid(trunkid);
					trunkDAO.save(trunk);
				}
			}
		}
	}

	/**
	 * ���Ͷ��� ���ƻ������
	 */
	public void sendMsg(TestYearPlan plan, String mobile, String rmobiles) {
		String planid = plan.getId();
		String content = "������ά��������һ������Ϊ\"" + plan.getPlanName()
		+ "\"����ƻ��ȴ����ļ�ʱ��ˡ�";
		// ���Լƻ������
		logger.info("��������: " + mobile + ":" + content);
		super.sendMessage(content, mobile);
		// ������
		if (rmobiles != null && !"".equals(rmobiles)) {
			String msg = "������ά��������һ������Ϊ\"" + plan.getPlanName() + "\"����ƻ��ȴ������ա�";
			logger.info("��������: " + msg + ":" + rmobiles);
			super.sendMessage(msg, rmobiles);
		}

		// ������ż�¼
		SMHistory history = new SMHistory();
		history.setSimIds(mobile + "," + rmobiles);
		history.setSendContent(content);
		history.setSendTime(new Date());
		history.setSmType(MainTenanceConstant.LP_TEST_YEAR_PLAN);
		history.setObjectId(planid);
		history.setBusinessModule(MainTenanceConstant.MAINTENANCE_MODULE);
		historyDAO.save(history);
	}
	/**
	 * ��ѯ������ƻ�
	 * @param user
	 * @return
	 */
	public List getWaitHandelYearPlans(UserInfo user){
		List list = dao.getWaitHandelYearPlans(user);
		String userid = user.getUserID();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String objectid = (String) bean.get("id");
				boolean read = approverDAO.isReadOnly(objectid, userid,
						MainTenanceConstant.LP_TEST_YEAR_PLAN);
				bean.set("isread", read + "");
			}
		}
		return list;
	}

	/**
	 * ��ѯ�����ֵ�����
	 * @param code
	 * @return
	 */
	public String getCableLevelNameByCode(String code){
		return dao.getCableLevelNameByCode(code);
	}
	

	/**
	 * ��ѯ��ά��λ����
	 * @param code
	 * @return
	 */
	public String getConNameByContractId(String contracId){
		return dao.getConNameByContractId(contracId);
	}
	
	/**
	 * ��ѯ�û�����
	 * @param code
	 * @return
	 */
	public String getUserNameByUserId(String userid){
		return dao.getUserNameByUserId(userid);
	}

	public Map<String,TestYearPlanTask> getPlanTasksByPlanId(String planId){
		//return taskDAO.getPlanTasksByPlanId(planId);
		Map<String,TestYearPlanTask> map = new 	HashMap<String,TestYearPlanTask>();			
		List<TestYearPlanTask> tasks = taskDAO.getPlanTasksByPlanId(planId);
		if(tasks!=null && tasks.size()>0){
			for(int i = 0;i<tasks.size();i++){
				TestYearPlanTask task = (TestYearPlanTask)tasks.get(i);
				String taskId = task.getId();
				String code = task.getCableLevel();
				String cableLable = getCableLevelNameByCode(code);
				task.setCableLable(cableLable);
				task.setTrunkNum(0);
				List<TestYearPlanTrunk> trunkLists = trunkDAO.getTrunksByTaskId(taskId);
				if(trunkLists!=null && trunkLists.size()>0){
					task.setTrunkNum(trunkLists.size());
					String trunks = trunksListTOString(trunkLists);
					String trunkNames = trunksListTOTrunkName(trunkLists);
					task.setTrunkIds(trunks);
					task.setTrunkNames(trunkNames);
				}
				map.put(code,task);
			}
		}
		return map;
	}

	/**
	 * �м̶�listת���ַ���
	 * @param trunks
	 * @return
	 */
	public String trunksListTOString(List<TestYearPlanTrunk> trunks){
		String str="";
		for(int i = 0;i<trunks.size();i++){
			TestYearPlanTrunk trunk = trunks.get(i);
			String trunkid = trunk.getTrunkid();
			if(i<trunks.size()-1){
				str+=trunkid+",";
			}else{
				str+=trunkid;
			}
		}
		return str;
	}

	/**
	 * �м̶�ת������
	 * @param trunks
	 * @return
	 */
	public String trunksListTOTrunkName(List<TestYearPlanTrunk> trunks){
		String str="";
		for(int i = 0;i<trunks.size();i++){
			TestYearPlanTrunk trunk = trunks.get(i);
			String trunkid = trunk.getTrunkid();
			String trunkName = dao.getTrunkNameByTrunkId(trunkid);
			if(i<trunks.size()-1){
				str+=trunkName+",";
			}else{
				str+=trunkName;
			}
		}
		return str;
	}
	
	/**
	 * ��ѯ��ƻ������ʷ��¼
	 * @param planid
	 * @return
	 */
	public List getApproveHistorys(String planid){
		String condition = " and approve.object_type='"+MainTenanceConstant.LP_TEST_YEAR_PLAN+
		"' and approve.object_id='"+planid+"'";
		return approveDAO.queryList(condition);
	}
	
	/**
	 * ������ƻ�����õ�����м̶�
	 * @param taskId
	 * @return
	 */
	public List<TestYearPlanTrunk> getTrunksByTaskId(String taskId){
		return trunkDAO.getTrunksByTaskId(taskId);
	}


	/**
	 * ɾ����ƻ�
	 * @param planId
	 */
	public void deleteYearPlan(String planId){
		approverDAO.deleteList(planId, MainTenanceConstant.LP_TEST_YEAR_PLAN);
		dao.deleteYearPlan(planId);
	}
	
	
	public List<ReplyApprover> getApprovers(String planId){
		List<ReplyApprover> approvers =  approverDAO.getApprovers(planId,
				MainTenanceConstant.LP_TEST_YEAR_PLAN);
		List<ReplyApprover> apps = new ArrayList<ReplyApprover>();
		if(approvers!=null && approvers.size()>0){
			for(int i = 0;i<approvers.size();i++){
				ReplyApprover app = approvers.get(i);
				String userid = app.getApproverId();
				String userName = dao.getUserNameByUserId(userid);
				app.setApproverName(userName);
				apps.add(app);
			}
		}
		return apps;
	}
	
	
	/**
	 * ������ƻ������Ϣ
	 * @param user
	 * @param bean
	 * @param troubleId
	 * @param attachids
	 * @throws Exception
	 */
	public void addApproveYearPlan(UserInfo user,ApproveInfo approve,
			String planid,String mobiles) throws ServiceException{
		approve.setObjectType(MainTenanceConstant.LP_TEST_YEAR_PLAN);
		approve.setApproveTime(new Date());
		TestYearPlan plan = dao.get(planid);
		String approveResult = approve.getApproveResult();
		approveDAO.saveApproveInfo(approve);
		if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_NO)){//��ͨ��
			plan.setState(MainTenanceConstant.YEAR_PLAN_APPROVE_NO);
			dao.save(plan);
		}
		if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_PASS)){//ͨ��
			plan.setState(MainTenanceConstant.YEAR_PLAN_APPROVE_PASS);
			dao.save(plan);
		}
		if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_TRANSFER)){//ת��
			String transfer = approve.getApproverId();
			//String reads = approverDAO.getApprover(planid,MainTenanceConstant.APPROVE_READ,MainTenanceConstant.LP_TEST_YEAR_PLAN);
			approverDAO.saveApproverOrReader(transfer,planid,MainTenanceConstant.APPROVE_TRANSFER_MAN,MainTenanceConstant.LP_TEST_YEAR_PLAN);
		}
		sendApproveMsg(approve,plan,approveResult,mobiles);
	}
	
	/**
	 * ���¼�����ݽ������Ͷ���
	 */
	public void sendApproveMsg(ApproveInfo approve,TestYearPlan plan,
			String approveResult,String mobiles){
		//���Ͷ���
		try{
			String mobile="";
			String content="������ά��������һ������Ϊ";
			content+="\""+plan.getPlanName()+" \" ����ƻ����Խ��";
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_NO)){//��ͨ��
				content+="δͨ����ˣ�";
			}
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_PASS)){//ͨ��
				content+="�Ѿ�ͨ����ˣ�";
			}
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_TRANSFER)){//ת��
				content+="�ȴ����ļ�ʱ����";
				mobile=mobiles;
			}else{
				UserInfoDAOImpl userdao = new UserInfoDAOImpl();
				UserInfo userinfo = userdao.findById(plan.getCreatorId());
				mobile = userinfo.getPhone();
			}
			//����֪ͨ  ��ƻ���д��Ա
			super.sendMessage(content, mobile);
			logger.info("��������: "+mobile +":"+content);
		}catch(Exception e){
			logger.error("�����ƻ����Ͷ���ʧ��:"+e.getMessage());
			e.printStackTrace();
		}
	}
	

	/**
	 * ��ѯ��ƻ�
	 * @param user
	 * @return
	 */
	public List getYearPlans(UserInfo user,TestYearPlanBean bean ){
		return dao.getYearPlans(user, bean);
	}
	
	/**
	 * ���³����˵��Ѿ����ı��
	 */
	public void updateReader(UserInfo user,String planid) {
		String approverId = user.getUserID();
		String objectType = MainTenanceConstant.LP_TEST_YEAR_PLAN;
		approverDAO.updateReader(approverId,planid,objectType);
	}

	@Override
	protected HibernateDao<TestYearPlan, String> getEntityDao() {
		return dao;
	}


}
