package com.cabletech.linepatrol.maintenance.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.maintenance.beans.TestCableDataBean;
import com.cabletech.linepatrol.maintenance.dao.TestChipDataDAO;
import com.cabletech.linepatrol.maintenance.dao.TestConnectorWasteDAO;
import com.cabletech.linepatrol.maintenance.dao.TestCoreLengthDAO;
import com.cabletech.linepatrol.maintenance.dao.TestCoredataDAO;
import com.cabletech.linepatrol.maintenance.dao.TestDataDAO;
import com.cabletech.linepatrol.maintenance.dao.TestDecayconstantDAO;
import com.cabletech.linepatrol.maintenance.dao.TestEndWasteDAO;
import com.cabletech.linepatrol.maintenance.dao.TestExceptionEventDAO;
import com.cabletech.linepatrol.maintenance.dao.TestLineDataDAO;
import com.cabletech.linepatrol.maintenance.dao.TestOtherAnalyseDAO;
import com.cabletech.linepatrol.maintenance.dao.TestPlanDAO;
import com.cabletech.linepatrol.maintenance.dao.TestPlanLineDAO;
import com.cabletech.linepatrol.maintenance.dao.TestProblemDAO;
import com.cabletech.linepatrol.maintenance.module.TestCableData;
import com.cabletech.linepatrol.maintenance.module.TestChipData;
import com.cabletech.linepatrol.maintenance.module.TestConnectorWaste;
import com.cabletech.linepatrol.maintenance.module.TestCoreData;
import com.cabletech.linepatrol.maintenance.module.TestCoreLength;
import com.cabletech.linepatrol.maintenance.module.TestData;
import com.cabletech.linepatrol.maintenance.module.TestDecayConstant;
import com.cabletech.linepatrol.maintenance.module.TestEndWaste;
import com.cabletech.linepatrol.maintenance.module.TestExceptionEvent;
import com.cabletech.linepatrol.maintenance.module.TestOtherAnalyse;
import com.cabletech.linepatrol.maintenance.module.TestPlan;
import com.cabletech.linepatrol.maintenance.module.TestPlanLine;
import com.cabletech.linepatrol.maintenance.module.TestProblem;
import com.cabletech.linepatrol.maintenance.workflow.MaintenanceWorkflowBO;
@Service
@Transactional
public class TestPlanLineDataBO extends EntityManager<TestCableData,String> {
	@Resource(name="testLineDataDAO")
	private TestLineDataDAO lineDataDAO;
	@Resource(name="testPlanDAO")
	private TestPlanDAO planDAO;
	@Resource(name="testDataDAO")
	private TestDataDAO dataDAO;
	@Resource(name="testPlanLineDAO")
	private TestPlanLineDAO lineDAO;
	@Resource(name="testChipDataDAO")
	private TestChipDataDAO chipDataDAO;
	@Resource(name="testProblemDAO")
	private TestProblemDAO problemDAO;

	@Resource(name="testCoredataDAO")
	private TestCoredataDAO coredataDAO;
	@Resource(name="testConnectorWasteDAO")
	private TestConnectorWasteDAO connectorWasteDAO;
	@Resource(name="testCoreLengthDAO")
	private TestCoreLengthDAO coreLengthDAO;
	@Resource(name="testDecayconstantDAO")
	private TestDecayconstantDAO decayconstantDAO;
	@Resource(name="testEndWasteDAO")
	private TestEndWasteDAO endWasteDAO;
	@Resource(name="testExceptionEventDAO")
	private TestExceptionEventDAO exceptionEventDAO;
	@Resource(name="testOtherAnalyseDAO")
	private TestOtherAnalyseDAO otherAnalyseDAO;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Resource(name="replyApproverDAO")
	private ReplyApproverDAO approverDAO;
	@Resource(name="uploadFileService")
	private UploadFileService uploadFile;
	@Resource(name="smHistoryDAO")
	private SmHistoryDAO historyDAO;
	@Autowired
	private MaintenanceWorkflowBO workflowBo;


	public TestCableData getLineDataById(String id){
		return lineDataDAO.getLineDataById(id);
	}


	/**
	 * 保存备纤录入数据
	 * @param bean
	 * @param dataID
	 */
	public TestCableData saveLineData(TestCableDataBean bean,UserInfo user,
			Map<Object,TestChipData> chips,List<TestProblem> problems,String tempstate){
		try{
			String planid = bean.getTestPlanId();
			TestPlan plan = planDAO.getTestPlanById(planid);
			String planstate = plan.getTestState();
			String dataID = "";
			if(planstate.equals(MainTenanceConstant.LOGGING_DATA_WAIT)){
				TestData data = new TestData();
				data.setTestPlanId(planid);
				data.setApproveTimes(0);
				data.setRecordTime(new Date());
				data.setCreateTime(new Date());
				data.setRecordManId(user.getUserID());
				dataDAO.save(data);
				dataID = data.getId();
				plan.setTestState(MainTenanceConstant.LOGGING_DATA_NO_OVER);
			}else{
				TestData data = dataDAO.getDataByPlanId(planid);
				dataID = data.getId();
			}
			String lineid = bean.getLineId();
			TestPlanLine line =  lineDAO.getTestPlanLineById(lineid);
			if(tempstate!=null && tempstate.equals("tempsave")){
				line.setState(MainTenanceConstant.ENTERING_TEMP);
			}else{
				line.setState(MainTenanceConstant.ENTERING_Y);
			}
			
			lineDAO.save(line);
			planDAO.save(plan);
			TestCableData cableData = new TestCableData();
			BeanUtil.objectCopy(bean,cableData);
			cableData.setTestDataId(dataID);
			cableData.setCreateTime(new Date());
			lineDataDAO.save(cableData);
			String cableDataId = cableData.getId();
			saveChips(chips,cableDataId,user);
			saveProblems(problems,cableData);
			return cableData;
		}catch(Exception e){
			logger.info("保存备纤数据出现异常:"+e.getMessage());
			e.getStackTrace();
		}
		return null;
	}

	/**
	 * 纤芯录入数据信息
	 */
	public void saveChips(Map<Object,TestChipData> chips,String cableDataId,UserInfo user){
		String userid = user.getUserID();
		String regionName = user.getRegionName();
		if(chips!=null && chips.size()>0){
			Set set = chips.keySet();
			Iterator iteartor = set.iterator();
			while(iteartor.hasNext()){
				Object key =  iteartor.next();
				TestChipData data = chips.get(key);
				data.setTestCableDataId(cableDataId);
				data.setCreateTime(new Date());
				String id = data.getId();
				if(id!=null && !"".equals(id.trim())){
					chipDataDAO.mergeChipData(data);
				}else{
					data.setId(null);
					data = chipDataDAO.saveChipData(data);
				}
				String dataid = data.getId();
				saveDataAnalyse(data);
				List<FileItem> files = data.getAttachments();
				uploadFile.saveFiles(files,ModuleCatalog.FIBRECORETEST, 
						regionName,dataid,MainTenanceConstant.LP_TEST_CHIP_DATA,userid);
			}
		}
	}

	/**
	 * 保存数据分析内容到数据库
	 * @param data
	 */
	public void saveDataAnalyse(TestChipData data){
		String dataid = data.getId();
		String seq = data.getChipSeq();
		TestCoreData coreData = data.getCoreData();
		if(coreData!=null){
			String station = coreData.getBaseStation();
			if(station!=null && !"".equals(station)){
				coreData.setId(null);
				coreData.setCoreId(dataid);
				coreData.setCoreOrder(seq);
				coredataDAO.save(coreData);
				String coreDataId = coreData.getId();
				TestCoreLength coreLength = data.getCorelength();
				if(coreLength==null){
					coreLength = new TestCoreLength();
				}
				coreLength.setId(null);
				coreLength.setAnaylseId(coreDataId);
				coreLengthDAO.save(coreLength);
				TestDecayConstant decay = data.getDecay();
				if(decay==null){
					decay = new TestDecayConstant();
				}
				decay.setId(null);
				decay.setAnaylseId(coreDataId);
				decayconstantDAO.save(decay);
				TestEndWaste endWaste = data.getEndwaste();
				if(endWaste==null){
					endWaste = new TestEndWaste();
				}
				endWaste.setAnaylseId(coreDataId);
				endWaste.setId(null);
				endWasteDAO.save(endWaste);
				TestOtherAnalyse otherAnalyse = data.getOtherAnalyse();
				if(otherAnalyse==null){
					otherAnalyse = new TestOtherAnalyse();
				}
				otherAnalyse.setAnaylseId(coreDataId);
				otherAnalyse.setId(null);
				otherAnalyseDAO.save(otherAnalyse);
				List<TestConnectorWaste> wasteList = data.getConwaste();
				connectorWasteDAO.saveList(wasteList,coreDataId);
				List<TestExceptionEvent> exeEventList = data.getExceptionEvent();
				exceptionEventDAO.saveList(exeEventList,coreDataId);
			}
		}
	}


	/**
	 * 问题中继段
	 */
	public void saveProblems(List<TestProblem> problems,TestCableData cableData ){
		if(problems!=null && problems.size()>0){
			String testPlanId = cableData.getTestPlanId();
			String testCablelineId = cableData.getTestCablelineId();
			String tester = cableData.getTestMan();
			for(TestProblem problem : problems){
				problem.setTestPlanId(testPlanId);
				problem.setTestCablelineId(testCablelineId);
				//problem.setProblemState(MainTenanceConstant.PROBLEM_STATE_N);
				problem.setTester(tester);
				problem.setId(null);
				problemDAO.save(problem);
			}
		}
	}

	/**
	 * 修改备纤录入数据
	 * @param bean
	 * @param dataID
	 */
	public void updateCableData(TestCableDataBean bean,
			Map<Object,TestChipData> chips,List<TestProblem> problems,
			UserInfo user,String tempstate,String lineId){
		try{
			TestCableData cableData = new TestCableData();
			BeanUtil.objectCopy(bean,cableData);
			cableData.setCreateTime(new Date());
			lineDataDAO.save(cableData);
			String planid = cableData.getTestPlanId();
			String cableid = cableData.getTestCablelineId();
			TestPlanLine line =  lineDAO.getTestPlanLineById(lineId);
			if(tempstate!=null && tempstate.equals("tempsave")){
				line.setState(MainTenanceConstant.ENTERING_TEMP);
			}else{
				line.setState(MainTenanceConstant.ENTERING_Y);
			} 
			lineDAO.saveOrUpdateLine(line);
			String cableDataId = cableData.getId();
			Map<Object,TestChipData> chipDatas = chipDataDAO.getChipsByCableDataId(cableDataId);
			Map<Object,TestChipData> datas = chipDatas;
			deleteAnalyseData(datas,cableDataId);
		//	chipDataDAO.deleteChipsByCableDataId(cableDataId);
			problemDAO.deleteProblemsByPlanIdAndLineId(planid,cableid);
			saveChips(chips,cableDataId,user);
			saveProblems(problems,cableData);
		}catch(Exception e){
			logger.info("修改录入数据出现异常:"+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 删除数据分析内容
	 * @param chipdatas
	 * @param cableDataId
	 */
	public void deleteAnalyseData(Map<Object,TestChipData> chips,String cableDataId){
		if(chips!=null && chips.size()>0){
			Set set = chips.keySet();
			Iterator iteartor = set.iterator();
			while(iteartor.hasNext()){
				Object key =  iteartor.next();
				TestChipData data = chips.get(key);
				String chipDataId = data.getId();
				TestCoreData coreData = coredataDAO.getCoreData(chipDataId);
				if(coreData!=null){
					//System.out.println(coreData+" ====delete========================coreData");
					String anaylseId = coreData.getId();
					coredataDAO.delete(coreData);
					coreLengthDAO.deletCoreLengthByAnaylseId(anaylseId);
					decayconstantDAO.deletDecayconstantByAnaylseId(anaylseId);
					endWasteDAO.deletEndWasteByAnaylseId(anaylseId);
					otherAnalyseDAO.deletOtherByAnaylseId(anaylseId);
					connectorWasteDAO.deleteWasteByAnaylseId(anaylseId);
					exceptionEventDAO.deletEventByAnaylseId(anaylseId);
				}
			}
		}
	}

	/**
	 * 保存数据录入的审核信息
	 * @param id 录入数据系统编号
	 * @param approvers 审核人
	 * @param mobiles 审核人电话
	 * @param reads 抄送人
	 * @param rmobiles 抄送人电话
	 * @throws Exception 
	 */
	public void saveApprove(UserInfo user,String id,String planid,String approvers,
			String mobiles,String reads,String rmobiles) throws Exception{
		TestData data = new TestData();
		if(id!=null && !id.equals("")){
			data = dataDAO.getDataById(id);
		}else{
			data.setTestPlanId(planid);
			data.setApproveTimes(0);
			data.setRecordTime(new Date());
			data.setCreateTime(new Date());
			data.setRecordManId(user.getUserID());
			dataDAO.save(data);
			id = data.getId();
		}
		data.setState(MainTenanceConstant.TEST_DATA_APPROVE_WAIT);
		dataDAO.save(data);
		approverDAO.deleteList(id,MainTenanceConstant.LP_TEST_DATA);
		approverDAO.saveApproverOrReader(approvers,id,MainTenanceConstant.APPROVE_MAN,MainTenanceConstant.LP_TEST_DATA);
		approverDAO.saveApproverOrReader(reads,id,MainTenanceConstant.APPROVE_READ,MainTenanceConstant.LP_TEST_DATA);
		TestPlan plan = planDAO.getTestPlanById(planid);
		plan.setTestState(MainTenanceConstant.LOGGING_WAIT_APPROVE);
		planDAO.save(plan);
		String mans = approvers+","+reads;
		sendMsg(plan,id,mobiles,rmobiles);
		dataWorkFlow(plan,user,mans);
	}

	/**
	 * 发送短信 给录入数据审核人
	 */
	public void sendMsg(TestPlan plan,String dataid,String mobile,String rmobiles){
		String content = "【技术维护】您有一个名称为\""+plan.getTestPlanName()+"\"的测试计划结果等待您的及时审核。";
		logger.info("短信内容: "+mobile +":"+content);
		//测试计划审核人。
		super.sendMessage(content, mobile);
//		smSendProxy.simpleSend(mobile,content, null, null, true);
		//抄送人手机号码
		if(rmobiles!=null && rmobiles.length()>0){
			String msg = "【技术维护】您有一个名称为\""+plan.getTestPlanName()+"\"的测试计划结果等待您查收。";
			List<String> mobileList = StringUtils.string2List(rmobiles, ",");
			super.sendMessage(msg, mobileList);
//			for(String rmobile:mobileList){
//				System.out.println("抄送人:"+rmobile);
//				smSendProxy.simpleSend(rmobile,msg, null, null, true);
//			}
		}
		//保存短信记录
		SMHistory history = new SMHistory();
		history.setSimIds(mobile+","+rmobiles);
		history.setSendContent(content);
		history.setSendTime(new Date());
		history.setSmType(MainTenanceConstant.LP_TEST_DATA);
		history.setObjectId(dataid);
		history.setBusinessModule(MainTenanceConstant.MAINTENANCE_MODULE);
		historyDAO.save(history);
	}


	// 执行保存录入数据的工作流处理过程
	public void dataWorkFlow(TestPlan plan,UserInfo user,String approvers) throws Exception{
		String planid = plan.getId();
		String userid = user.getUserID();
		Task task = workflowBo.getHandleTaskForId(userid, planid);
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		if (task != null && MaintenanceWorkflowBO.RECORD_TASK.equals(task.getName())) {
			workflowBo.setVariables(task, "assignee", approvers);
			workflowBo.setVariables(task, "transition", "submited");
			workflowBo.completeTask(task.getId(), "record");
			System.out.println("录入数据保存成功！");	
			processHistoryBean.setProcessAction("提交数据录入");
			processHistoryBean.setTaskOutCome("submited");
			processHistoryBean.initial(task, user, "",
					ModuleCatalog.MAINTENANCE);
			processHistoryBean.setNextOperateUserId(approvers);
			processHistoryBean.setObjectId(planid);
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
	}

	/**
	 * 保存数据分析信息到纤芯信息
	 * @param bean
	 * @param data
	 * @return
	 */
	public TestChipData addAnalyToChipData(TestCableDataBean bean,TestChipData data){
		try{
			TestCoreData coreData = new TestCoreData();
			TestCoreLength coreLength = new TestCoreLength();
			TestDecayConstant decay = new TestDecayConstant();
			TestEndWaste endWaste = new TestEndWaste();
			TestOtherAnalyse otherAnalyse = new  TestOtherAnalyse();
			BeanUtil.objectCopy(bean, coreData);
			BeanUtil.objectCopy(bean, coreLength);
			BeanUtil.objectCopy(bean, decay);
			BeanUtil.objectCopy(bean, endWaste);
			BeanUtil.objectCopy(bean, otherAnalyse);
			List<TestConnectorWaste> wasteList = getConWasteListByBean(bean);
			List<TestExceptionEvent> exeEventList = getExeEventListByBean(bean);
			data.setCoreData(coreData);
			data.setCorelength(coreLength);
			data.setDecay(decay);
			data.setEndwaste(endWaste);
			data.setOtherAnalyse(otherAnalyse);
			data.setConwaste(wasteList);
			data.setExceptionEvent(exeEventList);
		}catch(Exception e){
			logger.error("保存数据分析到session出现异常:"+e.getMessage());
		}
		return data;
	}

	public List<TestConnectorWaste> getConWasteListByBean(TestCableDataBean bean){
		List<TestConnectorWaste> wasteList = new ArrayList<TestConnectorWaste>();
		int[] orderNumber = bean.getOrderNumber();
		String[] connectorStation = bean.getConnectorStation();
		float[] waste = bean.getWaste();
		String[] problemAnalyse = bean.getProblemAnalyse();
		String[] remark = bean.getRemark();
		TestConnectorWaste conwaste ;
		for(int i = 0;orderNumber!=null&&i<orderNumber.length;i++){
			conwaste = new TestConnectorWaste();
			conwaste.setOrderNumber(orderNumber[i]);
			conwaste.setConnectorStation(connectorStation[i]);
			conwaste.setProblemAnalyse(problemAnalyse[i]);
			conwaste.setWaste(waste[i]);
			conwaste.setRemark(remark[i]);
			wasteList.add(conwaste);
		}
		return wasteList;
	}

	public List<TestExceptionEvent> getExeEventListByBean(TestCableDataBean bean){
		List<TestExceptionEvent> exeEventList = new ArrayList<TestExceptionEvent>();
		int[] orderNumberExe = bean.getOrderNumberExe();
		String[] eventStation = bean.getEventStation();
		float[] wasteExe = bean.getWasteExe();
		String[] problemAnalyseExe = bean.getProblemAnalyseExe();
		String[] remarkExe = bean.getRemarkExe();
		TestExceptionEvent exceptionEvent;
		for(int i = 0;orderNumberExe!=null&&i<orderNumberExe.length;i++){
			exceptionEvent = new TestExceptionEvent();
			exceptionEvent.setOrderNumberExe(orderNumberExe[i]);
			exceptionEvent.setEventStation(eventStation[i]);
			exceptionEvent.setWasteExe(wasteExe[i]);
			exceptionEvent.setProblemAnalyseExe(problemAnalyseExe[i]);
			exceptionEvent.setRemarkExe(remarkExe[i]);
			exeEventList.add(exceptionEvent);
		}
		return exeEventList;
	}



	/**
	 * 获取文件的名称
	 * @param files
	 * @return
	 */
	public String getFileNames(List<FileItem> files){
		String name="";
		if(files!=null && files.size()>0){
			for(int i = 0;i<files.size();i++){
				FileItem file = files.get(i);
				String filename = file.getName();
				if(filename.indexOf(File.separator) != -1){
					filename = filename.substring(filename.lastIndexOf(File.separator)+1);
				}
				if(i==files.size()-1){
					name +=filename ;
				}else{
					name +=filename+" 、" ;
				}
			}
		}
		return name;
	}

	/**
	 * 根据计划得到录入数据
	 * @param planid
	 * @return
	 */
	public TestData getDataByPlanId(String planid){
		return dataDAO.getDataByPlanId(planid);
	}

	public TestCableData getLineDataByPlanIdAndLineId(String planid,String lineId){
		return lineDataDAO.getLineDataByPlanIdAndLineId(planid, lineId);
	}

	public TestProblem getTestProblemById(String id){
		return problemDAO.get(id);
	}

	public TestChipData getTestChipDataById(String id){
		return chipDataDAO.get(id);
	}

	/**
	 * 根据年计划id与线路id查询呢中继段问题信息
	 * @param planId
	 * @param lineid
	 * @return
	 */
	public List<TestProblem> getProblemsByPlanIdAndLineId(String planId,String lineid){
		return problemDAO.getProblemsByPlanIdAndLineId(planId, lineid);
	}

	/*
	 * 根据备纤录入系统编号查询纤芯录入信息
	 */
	public Map<Object,TestChipData> getChipsByCableDataId(String cableDataId){
		Map<Object,TestChipData> chips = chipDataDAO.getChipsByCableDataId(cableDataId);
		if(chips!=null && chips.size()>0){
			Set set = chips.keySet();
			Iterator iteartor = set.iterator();
			while(iteartor.hasNext()){
				Object key =  iteartor.next();
				TestChipData data = chips.get(key);
				String chipDataId = data.getId();
				TestCoreData coreData = coredataDAO.getCoreData(chipDataId);
				if(coreData!=null){
					String anaylseId = coreData.getId();
					TestCoreLength coreLength = coreLengthDAO.getCoreLengthByAnaylseId(anaylseId);
					TestDecayConstant decay = decayconstantDAO.getDecayConstantByAnaylseId(anaylseId);
					TestEndWaste endWaste = endWasteDAO.getEndWasteByAnaylseId(anaylseId);
					TestOtherAnalyse otherAnalyse = otherAnalyseDAO.getOtherAnalyseByAnaylseId(anaylseId);
					List<TestConnectorWaste> wasteList = connectorWasteDAO.getConnectorWasteByAnaylseId(anaylseId);
					List<TestExceptionEvent> exeEventList = exceptionEventDAO.getExceptionEventByAnaylseId(anaylseId);
					data.setCoreData(coreData);
					data.setCorelength(coreLength);
					data.setDecay(decay);
					data.setEndwaste(endWaste);
					data.setOtherAnalyse(otherAnalyse);
					data.setConwaste(wasteList);
					data.setExceptionEvent(exeEventList);
				}
			}
		}
		return chips;
	}

	public int getNonConformityChipNum(String cableDataId){
		return chipDataDAO.getNonConformityChipNum(cableDataId);
	}

	@Override
	protected HibernateDao<TestCableData, String> getEntityDao() {
		return null;
	}
}

