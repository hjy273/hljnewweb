package com.cabletech.linepatrol.trouble.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.fileupload.FileItem;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Depart;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.ReturnMaterialDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.dao.UseMaterialDAO;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.commons.module.ReturnMaterial;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.commons.module.UseMaterial;
import com.cabletech.linepatrol.trouble.beans.TroubleReplyBean;
import com.cabletech.linepatrol.trouble.dao.TroubleAccidentsDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleCableInfoDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleEomsDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleInfoDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleProcessDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleProcessUnitDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleProcesserDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleQueryStatDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleReplyApproveDAO;
import com.cabletech.linepatrol.trouble.dao.TroubleReplyDAO;
import com.cabletech.linepatrol.trouble.module.TroubleAccidents;
import com.cabletech.linepatrol.trouble.module.TroubleCableInfo;
import com.cabletech.linepatrol.trouble.module.TroubleEoms;
import com.cabletech.linepatrol.trouble.module.TroubleInfo;
import com.cabletech.linepatrol.trouble.module.TroubleProcess;
import com.cabletech.linepatrol.trouble.module.TroubleProcessUnit;
import com.cabletech.linepatrol.trouble.module.TroubleProcesser;
import com.cabletech.linepatrol.trouble.module.TroubleReply;
import com.cabletech.linepatrol.trouble.workflow.TroubleWorkflowBO;

@Service
@Transactional
public class TroubleReplyBO extends EntityManager<TroubleReply, String> {
	public final static int HOUR_TIME_LENGTH = (1000 * 3600);
	public final static int MINUTE_TIME_LENGTH = (1000 * 60);
	public final static int SECOND_TIME_LENGTH = (1000);

	@Resource(name = "troubleReplyDAO")
	private TroubleReplyDAO dao;

	@Resource(name = "troubleInfoDAO")
	private TroubleInfoDAO troubleinfoDAO;

	@Resource(name = "troubleCableInfoDAO")
	private TroubleCableInfoDAO cableDAO;

	@Resource(name = "troubleProcessDAO")
	private TroubleProcessDAO processDAO;

	@Resource(name = "troubleProcesserDAO")
	private TroubleProcesserDAO processerDAO;

	@Resource(name = "troubleProcessUnitDAO")
	private TroubleProcessUnitDAO unitDAO;

	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDAO;

	@Resource(name = "approveDAO")
	private ApproveDAO approveDAO;

	@Resource(name = "troubleReplyApproveDAO")
	private TroubleReplyApproveDAO troubleApproveDAO;

	@Resource(name = "useMaterialDAO")
	private UseMaterialDAO useMaterialDAO;

	@Resource(name = "returnMaterialDAO")
	private ReturnMaterialDAO returnMaterialDAO;

	@Resource(name = "troubleAccidentsDAO")
	private TroubleAccidentsDAO troubleAccidentsDAO;

	@Resource(name = "uploadFileService")
	private UploadFileService uploadFile;

	@Resource(name = "troubleEomsDAO")
	private TroubleEomsDAO eomsDAO;
	@Resource(name = "troubleQueryStatDAO")
	private TroubleQueryStatDAO querydao;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;

	@Autowired
	private TroubleWorkflowBO workflowBo;

	/**
	 * 平台核准修改 受理时间
	 * 
	 * @param bean
	 * @param userinfo
	 * @throws Exception
	 */
	public void updateTroubleAcceptTime(TroubleReplyBean bean, UserInfo userinfo, List<FileItem> files, String unitid)
			throws Exception {
		String replyid = bean.getId();
		String troubleid = bean.getTroubleId();
		String confirmResult = bean.getConfirmResult();
		String appprove = bean.getApprover();
		TroubleReply reply = dao.get(replyid);
		if (confirmResult.equals(TroubleConstant.REPLY_ROLE_HOST)) {// 主办
			String acctime = bean.getTroubleAcceptTime();
			Date time = DateUtil.parseDate(acctime, "yyyy/MM/dd HH:mm:ss");
			reply.setTroubleAcceptTime(time);
		}
		reply.setApproveState(TroubleConstant.REPLY_APPROVE_WAIT);// 移动审核
		dao.save(reply);
		TroubleInfo trouble = toTrouble(troubleid, bean);
		trouble.setTroubleState(TroubleConstant.TROUBLE_FEEDBACK);
		troubleinfoDAO.save(trouble);
		saveEoms(bean.getEomsCodes(), troubleid);
		String reads = bean.getReads();
		approverDAO.deleteList(replyid, TroubleConstant.LP_TROUBLE_REPLY);
		approverDAO.saveApproverOrReader(appprove, replyid, TroubleConstant.APPROVE_MAN,
				TroubleConstant.LP_TROUBLE_REPLY);
		approverDAO
				.saveApproverOrReader(reads, replyid, TroubleConstant.APPROVE_READ, TroubleConstant.LP_TROUBLE_REPLY);
		if (files != null && files.size() > 0) {
			String userid = userinfo.getUserID();
			uploadFile.saveFiles(files, TroubleConstant.TROUBLE_MODULE, userinfo.getRegionName(), troubleid,
					TroubleConstant.LP_TROUBLE_INFO, userid);
		}
		sendMsg(bean, trouble, userinfo);
		String mans = appprove + "," + reads;
		dispatchWorkFlow(reply, userinfo, unitid, mans);
	}

	/**
	 * 保存协办
	 * 
	 * @param reply
	 * @param userinfo
	 * @return
	 * @throws ServiceException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TroubleReply addReply(TroubleReplyBean bean, UserInfo userinfo, String unitid, List<FileItem> files)
			throws Exception {
		String id = bean.getId();
		TroubleReply reply = new TroubleReply();
		if (id != null && !"".equals(id)) {
			reply = dao.get(id);
		}
		String savestate = bean.getTempsave();
		String troubleid = bean.getTroubleId();
		reply.setTroubleId(troubleid);
		reply.setConfirmResult(bean.getConfirmResult());
		reply.setReplyRemark(bean.getReplyRemark());
		if (savestate != null && savestate.equals("tempsaveReply")) {
			reply.setApproveState(TroubleConstant.TEMP_SAVE);// 临时保存
			reply.setContractorId(userinfo.getDeptID());
			reply.setReplyManId(userinfo.getUserID());
			reply.setReplySubmitTime(new Date());
			dao.save(reply);
		} else {
			reply.setContractorId(userinfo.getDeptID());
			reply.setReplyManId(userinfo.getUserID());
			reply.setReplySubmitTime(new Date());
			reply.setApproveState(TroubleConstant.REPLY_APPROVE_DISPATCH);
			dao.save(reply);
			String replyid = reply.getId();
			TroubleInfo troubleInfo = troubleinfoDAO.get(troubleid);
			troubleInfo.setTroubleState(TroubleConstant.TROUBLE_REPLY_WAIT_APPROVE);
			troubleinfoDAO.save(troubleInfo);
			TroubleProcessUnit unit = unitDAO.getUnitById(unitid);
			unit.setState(TroubleConstant.PROCESSUNIT_END);
			unitDAO.save(unit);
			String sendMan = troubleInfo.getSendManId();
			UserInfo dispater = dao.getUserInfoByUserId(sendMan);
			String phone = dispater.getPhone();
			sendMsgToDispatch(troubleInfo, phone, replyid);
			replyWorkFlow(reply, userinfo, unitid, sendMan);
		}
		return reply;
	}

	public TroubleInfo toTrouble(String troubleid, TroubleReplyBean bean) {
		TroubleInfo trouble = troubleinfoDAO.get(troubleid);
		String troubleName = bean.getTroubleName();
		String troubleStartTime = bean.getTroubleStartTime();
		String troubleSendMan = bean.getTroubleSendMan();
		String troubleRemark = bean.getTroubleRemark();
		String isGreatTrouble = bean.getIsGreatTrouble();
		String connector = bean.getConnector();
		String connectorTel = bean.getConnectorTel();
		trouble.setTroubleName(troubleName);
		Date startDate = DateUtil.parseDate(troubleStartTime, "yyyy/MM/dd HH:mm:ss");
		trouble.setTroubleStartTime(startDate);
		trouble.setTroubleSendMan(troubleSendMan);
		trouble.setTroubleRemark(troubleRemark);
		trouble.setIsGreatTrouble(isGreatTrouble);
		trouble.setConnector(connector);
		trouble.setConnectorTel(connectorTel);
		return trouble;
	}

	/**
	 * 保存主办
	 * 
	 * @param bean
	 * @param userinfo
	 * @param unitid
	 *            故障与处理故障单位表的系统id
	 * @param responsible
	 *            负责人
	 * @param testman
	 *            故障测试人员
	 * @param mendman
	 *            现场接续抢修人员
	 * @param files
	 *            附件信息
	 * @return
	 * @throws Exception
	 */
	public TroubleReply addTroubleReply(TroubleReplyBean bean, UserInfo userinfo, String unitid, String responsible,
			String testman, String mendman, List<FileItem> files) throws Exception {
		String savestate = bean.getTempsave();
		String userid = userinfo.getUserID();
		String troublexy = bean.getTroubleGpsX();
		if (troublexy != null) {
			List<String> xy = StringUtils.string2List(troublexy, ",");
			if (xy != null && xy.size() > 0) {
				bean.setTroubleGpsX(xy.get(0));
			}
			if (xy != null && xy.size() > 1) {
				bean.setTroubleGpsY(xy.get(1));
			}
		}
		TroubleReply reply = new TroubleReply();
		String id = bean.getId();
		if (id != null && !"".equals(id)) {
			reply = dao.get(id);
		}
		BeanUtil.objectCopy(bean, reply);
		if (savestate != null && savestate.equals("tempsaveReply")) {
			reply.setApproveState(TroubleConstant.TEMP_SAVE);// 临时保存
			reply.setContractorId(userinfo.getDeptID());
			reply.setReplyManId(userinfo.getUserID());
			reply.setReplySubmitTime(new Date());
		} else {
			reply.setApproveState(TroubleConstant.REPLY_APPROVE_DISPATCH);
			reply.setReplyManId(userid);
			reply.setContractorId(userinfo.getDeptID());
			reply.setEvaluateState(TroubleConstant.REPLY_EXAM_STATE_NO);
			if (id == null) {// 表示是添加反馈单
				reply.setReplySubmitTime(new Date());
				reply.setNotPassedNum(0);
			}
		}
		String impressType[] = bean.getImpressTypeArray();
		if (impressType != null && impressType.length > 0) {
			String type = StringUtils.array2String(impressType);
			reply.setImpressType(type);
		}
		dao.save(reply);
		String replyid = reply.getId();
		String troubleid = reply.getTroubleId();
		TroubleInfo troubleInfo = troubleinfoDAO.get(troubleid);
		troubleInfo.setTroubleState(TroubleConstant.TROUBLE_REPLY_WAIT_APPROVE);
		troubleinfoDAO.save(troubleInfo);
		String sendMan = troubleInfo.getSendManId();
		List<TroubleProcesser> responsibles = initProcessResponsible(replyid, responsible);
		List<TroubleProcesser> testmans = initProcessTestMan(replyid, testman);
		List<TroubleProcesser> mendmans = initProcessMendMan(replyid, mendman);
		processerDAO.deleteProcesser(replyid);
		processerDAO.saveProcesser(responsibles);
		processerDAO.saveProcesser(testmans);
		processerDAO.saveProcesser(mendmans);
		List<String> trunks = bean.getTrunks();
		saveTrunk(trunks, replyid);
		bean.setObjectId(replyid);
		List<UseMaterial> materials = UseMaterial.packageList(TroubleConstant.TROUBLE_MODULE, bean);
		useMaterialDAO.deleteList(replyid, TroubleConstant.TROUBLE_MODULE);
		useMaterialDAO.saveList(materials);
		List<ReturnMaterial> returnMaterials = ReturnMaterial.packageList(bean, TroubleConstant.TROUBLE_MODULE);
		returnMaterialDAO.deleteList(replyid, TroubleConstant.TROUBLE_MODULE);
		returnMaterialDAO.saveList(returnMaterials);
		String hiddenid = bean.getHiddentrouble();
		if (hiddenid != null && !"".equals(hiddenid)) {
			troubleAccidentsDAO.deleteByReplyId(replyid);
			TroubleAccidents accident = new TroubleAccidents();
			accident.setAccidentId(hiddenid);
			accident.setTroubleReplyId(replyid);
			troubleAccidentsDAO.save(accident);
		}
		if (savestate == null || !savestate.equals("tempsaveReply")) {
			TroubleProcessUnit unit = unitDAO.getUnitById(unitid);
			unit.setState(TroubleConstant.PROCESSUNIT_END);
			unitDAO.save(unit);
			UserInfo dispater = dao.getUserInfoByUserId(sendMan);
			String phone = dispater.getPhone();
			sendMsgToDispatch(troubleInfo, phone, replyid);
			replyWorkFlow(reply, userinfo, unitid, sendMan);
		}
		uploadFile.saveFiles(files, TroubleConstant.TROUBLE_MODULE, userinfo.getRegionName(), replyid,
				TroubleConstant.LP_TROUBLE_REPLY, userid);
		return reply;
	}

	/**
	 * 初始化故障负责人员信息
	 * 
	 * @param replyid
	 * @param responsible
	 * @return
	 */
	public List<TroubleProcesser> initProcessResponsible(String replyid, String responsible) {
		Map<String, String> map = parseString(responsible);
		String type = TroubleConstant.REPLY_PROCESSER_RESPONSIBLE;
		List<TroubleProcesser> list = initProcesser(map, replyid, type);
		return list;
	}

	/**
	 * 初始化故障测试人员信息
	 * 
	 * @param replyid
	 * @param testman
	 * @return
	 */
	public List<TroubleProcesser> initProcessTestMan(String replyid, String testman) {
		Map<String, String> map = parseString(testman);
		String type = TroubleConstant.REPLY_PROCESSER_TEST_MAN;
		List<TroubleProcesser> list = initProcesser(map, replyid, type);
		return list;
	}

	/**
	 * 初始化故障现场接续抢修人员信息
	 * 
	 * @param replyid
	 * @param mendman
	 * @return
	 */
	public List<TroubleProcesser> initProcessMendMan(String replyid, String mendman) {
		Map<String, String> map = parseString(mendman);
		String type = TroubleConstant.REPLY_PROCESSER_MEND_MAN;
		List<TroubleProcesser> list = initProcesser(map, replyid, type);
		return list;
	}

	public List<TroubleProcesser> initProcesser(Map<String, String> map, String replyid, String type) {
		List<TroubleProcesser> list = new ArrayList<TroubleProcesser>();
		if (map != null && map.size() > 0) {
			Set set = map.keySet();
			Iterator key = set.iterator();
			while (key.hasNext()) {
				String man = (String) key.next();
				String tel = map.get(man);
				TroubleProcesser processer = new TroubleProcesser();
				processer.setTroubleReplyId(replyid);
				processer.setProcessManType(type);
				if (man != null) {
					processer.setProcessManId(man.trim());
				}
				if (tel != null) {
					processer.setProcessManTel(tel.trim());
				}
				list.add(processer);
			}
		}
		return list;
	}

	public void saveTrunk(List<String> trunks, String replyid) {
		cableDAO.deleteList(replyid);
		if (trunks != null && trunks.size() > 0) {
			for (int i = 0; i < trunks.size(); i++) {
				TroubleCableInfo cable = new TroubleCableInfo();
				cable.setTroubleCableLineId(trunks.get(i));
				cable.setTroubleReplyId(replyid);
				cableDAO.save(cable);
			}
		}
	}

	/**
	 * 发送短信给派单人
	 */
	public void sendMsgToDispatch(TroubleInfo trouble, String sendManPhone, String replyid) {
		// 发送短信
		String content = "【线路故障】您有一个故障名称为\"" + trouble.getTroubleName() + "\"的反馈单等待您的核准。";
		System.out.println(content);
		// 短信通知 填写反馈单后，返回派单人平台审核
		if (sendManPhone != null && sendManPhone.length() > 0) {
			logger.info("短信内容: " + sendManPhone + ":" + content);
			// if(mobile.length() == 13){
			super.sendMessage(content, sendManPhone);
			// smSendProxy.simpleSend(sendManPhone,content, null, null, true);
			// }
		}
		// 保存短信记录
		SMHistory history = new SMHistory();
		history.setSimIds(sendManPhone);
		history.setSendContent(content);
		history.setSendTime(new Date());
		history.setSmType(TroubleConstant.LP_TROUBLE_REPLY);
		history.setObjectId(replyid);
		history.setBusinessModule(TroubleConstant.TROUBLE_MODULE);
		historyDAO.save(history);
	}

	/**
	 * 发送短信给审核人或者抄送人
	 */
	public void sendMsg(TroubleReplyBean bean, TroubleInfo trouble, UserInfo user) {
		String mobiles = bean.getMobiles();
		String rmobiles = bean.getRmobiles();
		// 发送短信
		String content = "【线路故障】您有一个故障名称为\"" + trouble.getTroubleName() + "\"的反馈单等待您的审核。";
		System.out.println(content);
		// 短信通知 平台审核指定的审核人
		if (mobiles != null && mobiles.length() > 0) {
			super.sendMessage(content, mobiles);
			// List<String> mobileList = StringUtils.string2List(mobiles, ",");

			// for(String mobile:mobileList){
			// logger.info("短信内容: "+mobile +":"+content);
			// //if(mobile.length() == 13){
			// smSendProxy.simpleSend(mobile,content, null, null, true);
			// //}
			// }
		}
		// 抄送人手机号码
		if (rmobiles != null && rmobiles.length() > 0) {
			String msg = "【线路故障】您有一个故障名称为\"" + trouble.getTroubleName() + "\"的反馈单等待您的查收。";
			List<String> mobileList = StringUtils.string2List(rmobiles, ",");
			super.sendMessage(msg, mobileList);
			System.out.println(msg);
			// for(String mobile:mobileList){
			// System.out.println("抄送人:"+mobile);
			// smSendProxy.simpleSend(mobile,msg, null, null, true);
			// }
		}
		// 保存短信记录
		SMHistory history = new SMHistory();
		history.setSimIds(mobiles + "," + rmobiles);
		history.setSendContent(content);
		history.setSendTime(new Date());
		history.setSmType(TroubleConstant.LP_TROUBLE_REPLY);
		history.setObjectId(bean.getId());
		history.setBusinessModule(TroubleConstant.TROUBLE_MODULE);
		historyDAO.save(history);
	}

	/**
	 * 故障反馈后流程
	 * 
	 * @param reply
	 * @param user
	 * @param unitid
	 * @param sendMan
	 * @throws Exception
	 */
	private void replyWorkFlow(TroubleReply reply, UserInfo user, String unitid, String sendMan) throws Exception {
		// 进行故障反馈处理（主办）
		// 执行故障反馈处理业务逻辑
		// 执行故障反馈处理的工作流处理过程（需要迁移到业务类中）
		// user来源于系统登录用户，unit.getId()可以用unitId替换，该unitId为lp_trouble_process_unit的主键
		Task task = workflowBo.getHandleTaskForId(user.getDeptID(), unitid);
		if (task != null && TroubleWorkflowBO.REPLY_TASK.equals(task.getName())) {
			System.out.println("故障待反馈：" + task.getName());
			workflowBo.setVariables(task, "assignee", sendMan);
			workflowBo.completeTask(task.getId(), "submit");
			System.out.println("故障已经反馈！");
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.initial(task, user, "", ModuleCatalog.TROUBLE);
			processHistoryBean.setNextOperateUserId(sendMan);
			processHistoryBean.setObjectId(unitid);
			processHistoryBean.setProcessAction("故障反馈");
			processHistoryBean.setTaskOutCome("submit");
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
	}

	private void dispatchWorkFlow(TroubleReply reply, UserInfo user, String unitid, String approvers) throws Exception {
		Task task = workflowBo.getHandleTaskForId(user.getUserID(), unitid);
		if (task != null && TroubleWorkflowBO.EOMS_APPROVE.equals(task.getName())) {
			System.out.println("故障待平台核准：" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers);
			workflowBo.completeTask(task.getId(), "approve");
			System.out.println("故障已经通过平台核准！");
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.initial(task, user, "", ModuleCatalog.TROUBLE);
			processHistoryBean.setNextOperateUserId(approvers);
			processHistoryBean.setObjectId(unitid);
			processHistoryBean.setProcessAction("平台核准");
			processHistoryBean.setTaskOutCome("approve");
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
	}

	public void saveEoms(String eoms, String troubleid) {
		eomsDAO.saveEoms(eoms, troubleid);
	}

	public List string2List(String str) {
		List list = StringUtils.string2List(str, ",");
		return list;
	}

	@Transactional(readOnly = true)
	public TroubleReply getTroubleReply(String id) {
		TroubleReply reply = dao.get(id);
		dao.initObject(reply);
		return reply;
	}

	@Transactional(readOnly = true)
	public TroubleReplyBean getReplyDetail(String id) throws Exception {
		TroubleReply reply = dao.get(id);
		dao.initObject(reply);
		TroubleReplyBean bean = new TroubleReplyBean();
		BeanUtil.objectCopy(bean, reply);
		return bean;
	}

	/**
	 * 根据故障id查询主办的反馈单
	 * 
	 * @param troubleid
	 * @return
	 */
	@Transactional(readOnly = true)
	public TroubleReply getReplyByTroubleId(String troubleid) {
		return dao.getReplyByTroubleId(troubleid);
	}

	/**
	 * 查询是否填写反馈单
	 * 
	 * @param troubleid
	 *            故障id
	 * @param contractorId
	 *            处理单位id
	 * @return
	 */
	@Transactional(readOnly = true)
	public TroubleReply getReplyByTroubleIAndContractorID(String troubleid, String contractorId) {
		return dao.getReplyByTroubleIAndContractorID(troubleid, contractorId);
	}

	/**
	 * 保存审核人抄送人信息
	 * 
	 * @param cableinfo
	 */
	public void addApprover(ReplyApprover approver) {
		approverDAO.save(approver);
	}

	/**
	 * 保存光缆故障信息
	 * 
	 * @param cableinfo
	 */
	public void addCable(TroubleCableInfo cableinfo) {
		cableDAO.save(cableinfo);
	}

	/**
	 * 保存故障处理过程
	 * 
	 * @param process
	 */
	public void addProcess(TroubleProcess process) {
		processDAO.save(process);
	}

	/**
	 * 保存处理人员
	 * 
	 * @param processer
	 */
	public void addProcesser(TroubleProcesser processer) {
		processerDAO.save(processer);
	}

	/**
	 * 保存使用材料
	 * 
	 * @param
	 */
	public void addMaterialUsed() {
	}

	/**
	 * 保存回收材料
	 * 
	 * @param
	 */
	public void addMaterialReturn() {
	}

	/**
	 * 根据反馈单id查询故障处理单位信息
	 * 
	 * @param replyid
	 * @return
	 */
	public TroubleProcessUnit getProcessUnitByReplyId(String replyid) {
		return unitDAO.getProcessUnitByReplyId(replyid);
	}

	/**
	 * 根据故障id查询eoms单号
	 * 
	 * @param troubleid
	 *            故障id
	 */
	public List<TroubleEoms> getEomsByTroubleId(String troubleid) {
		return eomsDAO.getEomsByTroubleId(troubleid);
	}

	/**
	 * 光缆解析字符串为list
	 * 
	 * @param str
	 * @return
	 */
	public String emosListToString(List<TroubleEoms> list) {
		String str = "";
		for (int i = 0; list != null && i < list.size(); i++) {
			TroubleEoms eoms = list.get(i);
			String eomscode = eoms.getEomsCode();
			if (i == 0) {
				str += eomscode;
			} else {
				str += "," + eomscode;
			}
		}
		return str;
	}

	@Transactional(readOnly = true)
	public TroubleReply loadTroubleReply(String ID) throws ServiceException {
		return dao.findByUnique("id", ID);
	}

	/*
	 * @Transactional(readOnly=true) public List queryTroubleInfo(String
	 * operationName) throws ServiceException{ return
	 * dao.queryTaskOperation(operationName); }
	 */

	public void delTroubleReply(String id) {
		dao.delete(id);
	}

	public void updateTroubleReply(TroubleReply reply) throws ServiceException {
		dao.save(reply);
	}

	public List<TroubleReply> getAll() {
		return dao.getAll();
	}

	/**
	 * 查询待反馈故障单
	 * 
	 * @param taskName
	 * @return
	 */
	@Transactional(readOnly = true)
	public List findWaitReplys(UserInfo user, String userid, String taskName) {
		String userID = user.getUserID();
		String objectType = TroubleConstant.LP_TROUBLE_REPLY;
		String condition = "";
		List list = workflowBo.queryForHandleListBean(user, condition, taskName);
		List troubles = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String troubleid = (String) bean.get("id");
				String flow_state = (String) bean.get("flow_state");
				List<TroubleReply> replys = dao.getReplysByTroubleId(troubleid, "");
				String objectid = "";
				boolean flag = true;
				if (replys != null && replys.size() > 0) {
					for (int m = 0; m < replys.size(); m++) {
						TroubleReply reply = replys.get(m);
						objectid = reply.getId();
						boolean read = approverDAO.isReadOnly(objectid, userID, objectType);
						if (read == false) {
							flag = false;
							break;
						}
					}
				}
				bean.set("isread", flag + "");
				List wlist = getWaitHandelTroubles(user, bean, troubleid, userid, flag, flow_state);
				if (wlist != null && wlist.size() > 0) {
					troubles.add(wlist.get(0));
				}
			}
		}
		// return list;
		return troubles;
	}

	public List getWaitHandelTroubles(UserInfo user, BasicDynaBean bean, String troubleid, String userid, boolean flag,
			String taskName) {
		List troubles = new ArrayList();
		String depttype = user.getDeptype();
		if (depttype.equals("1")) {
			if (flag) {
				if (ishaveReply(user, troubleid)) {// 抄送人
					troubles.add(bean);
				}
				if (getDispatchTrouble(user, troubleid)) {// 派单人
					List vlist = troubleApproveDAO.viewReplysByApproves(user, troubleid);
					if (vlist != null && vlist.size() > 0) {
						troubles.add(bean);
					} else {
						List rlist = troubleApproveDAO.getReplyInfos(troubleid, userid, "dispatch");
						if (rlist != null && rlist.size() > 0) {
							troubles.add(bean);
						}
					}
				}
			}

			if (!flag) {
				if (taskName.equals(TroubleWorkflowBO.APPROVE_TASK)) {// 审核人(审核人、既是审核人也是抄送人的情况)
					List waitReplys = troubleApproveDAO.getReplyInfos(troubleid, userid, "");
					int num = 0;
					if (waitReplys != null && waitReplys.size() > 0) {
						for (int i = 0; i < waitReplys.size(); i++) {
							BasicDynaBean replybean = (BasicDynaBean) waitReplys.get(i);
							String replyid = (String) replybean.get("id");
							String state = (String) replybean.get("state");
							// boolean flag=true;
							boolean read = approverDAO.isReadOnly(replyid, userid, TroubleConstant.LP_TROUBLE_REPLY);
							if (read == false) {// 待审核的
								if (state != null && state.equals(TroubleConstant.REPLY_APPROVE_WAIT)) {
									num++;
									bean.set("havereply", "y");
									break;
								}
							}
						}
					}
					if (num > 0) {
						troubles.add(bean);
					}
					if (num <= 0) {
						boolean ishave = ishaveReply(user, troubleid);
						if (ishave) {
							troubles.add(bean);
						}
					}
				}
			}
			if (!taskName.equals(TroubleWorkflowBO.APPROVE_TASK)) {// 考核
				troubles.add(bean);
			}
		}

		if (depttype.equals("2")) {
			troubles.add(bean);
		}
		return troubles;
	}

	/**
	 * 在审核时查询反馈单信息
	 * 
	 * @param troubleid
	 * @param userid
	 * @param act
	 * @return
	 */
	public List getReplys(String troubleid, String userid, String act) {
		List list = troubleApproveDAO.getReplyInfos(troubleid, userid, act);
		;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String replyid = (String) bean.get("id");
				boolean flag = true;
				boolean read = approverDAO.isReadOnly(replyid, userid, TroubleConstant.LP_TROUBLE_REPLY);
				if (read == false) {
					flag = false;
				}
				bean.set("isread", flag + "");
			}
		}
		return list;
	}

	public boolean ishaveReply(UserInfo user, String troubleid) {
		List replys = troubleApproveDAO.viewReplysByReads(user, troubleid);
		if (replys != null && replys.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是不是自己派发的单子
	 * 
	 */
	public boolean getDispatchTrouble(UserInfo user, String troubleid) {
		return troubleApproveDAO.getDispatchTrouble(user, troubleid);
	}

	@Transactional(readOnly = true)
	public String getDeptNameByUserId(String userid) {
		return dao.getDeptNameByUserId(userid);

	}

	/**
	 * 把根据反馈单查询的处理人员按照不同类型整理为字符串
	 * 
	 * @param replyid
	 * @return
	 */
	public List<String> getProcessers(String replyid) {
		List<TroubleProcesser> processer = getProcesserByReplyId(replyid);
		List<String> processers = new ArrayList<String>();
		String responsibles = "";
		String testmans = "";
		String mendmans = "";
		if (processer != null && processer.size() > 0) {
			int rnum = 0;
			int tnum = 0;
			int mnum = 0;
			for (int i = 0; i < processer.size(); i++) {
				TroubleProcesser pro = processer.get(i);
				String man = pro.getProcessManId();
				if (man == null) {
					man = "";
				}
				String tel = pro.getProcessManTel();
				if (tel == null) {
					tel = "";
				}
				String type = pro.getProcessManType();
				if (type.equals(TroubleConstant.REPLY_PROCESSER_RESPONSIBLE)) {
					if (rnum == 0) {
						if (tel == null || tel.trim().equals("")) {
							responsibles += man;
						} else {
							responsibles += man + "," + tel;
						}
					} else {
						if (tel == null || tel.trim().equals("")) {
							responsibles += ";" + man;
						} else {
							responsibles += ";" + man + "," + tel;
						}
					}
					rnum++;
				}
				if (type.equals(TroubleConstant.REPLY_PROCESSER_TEST_MAN)) {
					if (tnum == 0) {
						if (tel == null || tel.trim().equals("")) {
							testmans += man.trim();
						} else {
							testmans += man.trim() + "," + tel.trim();
						}
					} else {
						if (tel == null || tel.trim().equals("")) {
							testmans += ";" + man.trim();
						} else {
							testmans += ";" + man.trim() + "," + tel.trim();
						}
					}
					tnum++;
				}
				if (type.equals(TroubleConstant.REPLY_PROCESSER_MEND_MAN)) {
					if (mnum == 0) {
						if (tel == null || tel.trim().equals("")) {
							mendmans += man.trim();
						} else {
							mendmans += man.trim() + "," + tel.trim();
						}
					} else {
						if (tel == null || tel.trim().equals("")) {
							mendmans += ";" + man;
						} else {
							mendmans += ";" + man + "," + tel;
						}
					}
					mnum++;
				}
			}
		}
		processers.add(responsibles.trim());
		processers.add(testmans.trim());
		processers.add(mendmans.trim());
		return processers;
	}

	/**
	 * 根据反馈单的id查询处理人员信息
	 * 
	 * @param replyid
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<TroubleProcesser> getProcesserByReplyId(String replyid) {
		return processerDAO.getProcesserByReplyId(replyid);
	}

	/**
	 * 得到故障原因名称
	 * 
	 * @param troubleid
	 *            故障id
	 * @return
	 */
	@Transactional(readOnly = true)
	public String getTroubleReasonName(String troubleid) {
		return dao.getTroubleReasonName(troubleid);
	}

	/**
	 * 得到故障类型名称
	 * 
	 * @param replyid
	 *            反馈单
	 * @return
	 */
	/*
	 * @Transactional(readOnly = true) public String getTroubleType(String
	 * replyid){ return dao.getTroubleType(replyid); }
	 */
	/**
	 * 反馈单对应隐患信息列表
	 * 
	 * @param replyid
	 * @return
	 */
	@Transactional(readOnly = true)
	public BasicDynaBean getAccidentByReplyId(String replyid) {
		return dao.getAccidentByReplyId(replyid);
	}

	/**
	 * 反馈单对应的审核信息列表
	 * 
	 * @param replyid
	 * @return
	 */
	public List getApproveResultList(String replyid) {
		return null;
	}

	/**
	 * 反馈单对应的审核人信息列表
	 * 
	 * @param replyid
	 * @return
	 */
	public List approverList(String replyid) {
		return dao.approverList(replyid);
	}

	/**
	 * 反馈单对应的光缆信息列表
	 * 
	 * @param replyid
	 * @return
	 */
	public List cableList(String replyid) {
		return dao.cableList(replyid);
	}

	/**
	 * 反馈单对应的使用材料信息列表
	 * 
	 * @return
	 */
	public List getMaterialList(String replyid) {
		return dao.getMaterialList(replyid);
	}

	/**
	 * 反馈单对应的回收材料信息列表
	 * 
	 * @return
	 */
	public List getMaterialListReturn(String replyid) {
		return dao.getMaterialListReturn(replyid);
	}

	/**
	 * 故障派单对应的处理过程信息列表
	 * 
	 * @param troubleid
	 *            故障id
	 * @param conid
	 *            代维id
	 * @return
	 */
	public List getTroubleProcessByCondition(String troubleid, String conid) {
		return dao.getTroubleProcessByCondition(troubleid, conid);
	}

	/**
	 * 根据处理过程，设置默认的故障坐标
	 * 
	 * @param process
	 * @return
	 */
	public String getTroubleXY(List process) {
		if (process != null && process.size() > 0) {
			for (int i = 0; i < process.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) process.get(i);
				Object arriveX = bean.get("arrive_trouble_gps_x");
				Object arriveY = bean.get("arrive_trouble_gps_y");
				if (arriveX != null && !"".equals(arriveX) && arriveY != null && !"".equals(arriveY)) {
					String xy = arriveX + "," + arriveY;
					return xy;
				}
			}
		}
		return "";
	}

	/**
	 * 故障派单对应的处理过程信息列表
	 * 
	 * @param troubleid
	 * @return
	 */
	public List getTroubleProcessList(String troubleid) {
		return dao.getTroubleProcessList(troubleid);
	}

	/**
	 * 反馈单对应的处理过程信息列表
	 * 
	 * @param replyid
	 * @return
	 */
	public List getProcessList(String replyid) {
		return dao.getProcessList(replyid);
	}

	/**
	 * 反馈单对应的处理人员信息列表
	 * 
	 * @param replyid
	 * @return
	 */
	public List getProcessManList(String replyid) {
		return dao.getProcessManList(replyid);
	}

	/**
	 * 根据指定的故障派单编号来 判断主办反馈单是否审核通过
	 * 
	 * @param troubleid
	 *            故障派单编号
	 * @return true 审核通过
	 */
	public boolean judgeAllApproved(String troubleid) {
		return dao.judgeAllApproved(troubleid);
	}

	/**
	 * 根据指定的故障派单编号来判断是否所有的反馈单都已经考核评估。
	 * 
	 * @param troubleid
	 *            故障派单编号
	 * @return
	 */
	public boolean judgeAllEvaluated(String troubleid) {
		return dao.judgeAllEvaluated(troubleid);
	}

	/**
	 * 根据指定的故障派单编号来判断反馈单是否全部反馈
	 * 
	 * @param toroubleid
	 * @return true:表示全部反馈
	 */
	public boolean judgeExistReplyAll(String toroubleid) {
		return dao.judgeExistReplyAll(toroubleid);
	}

	/**
	 * 判断故障反馈单是否有主办
	 * 
	 * @param troubleid
	 * @return true 有主办
	 */
	public boolean judgeReplyAllRoleHaveHost(String troubleid) {
		return dao.judgeReplyAllRoleHaveHost(troubleid);
	}

	/**
	 * 根据故障反馈信息来判断该故障反馈信息是否已经存在。
	 * 
	 * @param replyInfo
	 * @return
	 */
	public boolean judgeExistReply(TroubleReply replyInfo) {
		return dao.judgeExistReply(replyInfo);
	}

	/**
	 * 根据指定的故障反馈信息来判断是否对该反馈信息进行考核评估。
	 * 
	 * @param replyInfo
	 * @return
	 */
	public boolean judgeNeedEvaluate(TroubleReply replyInfo) {
		return dao.judgeNeedEvaluate(replyInfo);

	}

	/**
	 * 根据指定的反馈单编号来写入需要考核评估信息。
	 * 
	 * @param replyid
	 * @return
	 */
	public boolean writeNeedEvaluate(String replyid) {
		return dao.writeNeedEvaluate(replyid);
	}

	/**
	 * 根据指定的反馈单编号来写入不需要考核评估信息。
	 * 
	 * @param replyid
	 * @return
	 */
	public boolean writeNotNeedEvaluate(String replyid) {
		return dao.writeNotNeedEvaluate(replyid);
	}

	/**
	 * 光缆解析字符串为list
	 * 
	 * @param str
	 * @return
	 */
	public String listToString(List list) {
		String str = "";
		for (int i = 0; list != null && i < list.size(); i++) {
			BasicDynaBean bean = (BasicDynaBean) list.get(i);
			String lineid = (String) bean.get("lineid");
			if (i == 0) {
				str += lineid;
			} else {
				str += "," + lineid;
			}
		}
		return str;
	}

	/**
	 * 隐患解析字符串为list
	 * 
	 * @param str
	 * @return
	 */
	public String listToStringForHidden(List list) {
		String str = "";
		for (int i = 0; list != null && i < list.size(); i++) {
			BasicDynaBean bean = (BasicDynaBean) list.get(i);
			String hiddenid = (String) bean.get("id");
			if (i == 0) {
				str += hiddenid;
			} else {
				str += "," + hiddenid;
			}
		}
		return str;
	}

	/**
	 * 解析字符串
	 * 
	 * @param str
	 * @return
	 */
	public Map<String, String> parseString(String str) {
		// String str = "张三,13900000000;李四,13511111111";
		List<String> list = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();
		String[] processer = str.split(";");
		if (processer != null && processer.length > 0) {
			for (int i = 0; i < processer.length; i++) {
				if (processer[i] != null && !processer[i].equals("")) {
					String[] userArray = processer[i].split(",");
					if (userArray != null && userArray.length > 0) {
						String man = "";
						String tel = "";
						for (int j = 0; j < userArray.length; j++) {
							if (j == 0) {
								if (userArray[j] != null) {
									man = userArray[j].trim();
								}
							}
							if (j == 1) {
								if (userArray[j] != null) {
									tel = userArray[j].trim();
								}
							}
						}
						map.put(man, tel);
					}
				}
			}
		}
		return map;
	}

	/**
	 * 更新抄送人的已经批阅标记
	 * 
	 * @param approvers
	 * @param objectid
	 * @param ObjectType
	 * @throws Exception
	 */
	public void updateReader(UserInfo user, String replyid) throws Exception {
		String approverId = user.getUserID();
		String objectType = TroubleConstant.LP_TROUBLE_REPLY;
		approverDAO.updateReader(approverId, replyid, objectType);
		TroubleProcessUnit unitid = unitDAO.getProcessUnitByReplyId(replyid);
		replyRead(user, unitid.getId());
	}

	private void replyRead(UserInfo user, String unitid) throws Exception {
		Task task = workflowBo.getHandleTaskForId(user.getUserID(), unitid);
		if (task != null && TroubleWorkflowBO.APPROVE_TASK.equals(task.getName())) {
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.initial(task, user, "", ModuleCatalog.TROUBLE);
			processHistoryBean.setNextOperateUserId("");
			processHistoryBean.setObjectId(unitid);
			processHistoryBean.setProcessAction("故障反馈单批阅");
			processHistoryBean.setTaskOutCome("read");
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
	}

	@Override
	protected HibernateDao<TroubleReply, String> getEntityDao() {
		return dao;
	}

	public List queryForHandleTroubleNum(String condition, UserInfo userInfo) {
		// TODO Auto-generated method stub
		String assignee = "";
		String deptype = userInfo.getDeptype();
		String userId = userInfo.getUserID();
		String deptId = userInfo.getDeptID();
		if (deptype.equals("1")) {// 移动待审核
			assignee = userId;
		}
		if (deptype.equals("2")) {// 代维待反馈
			assignee = deptId;
		}
		List list = new ArrayList();
		List handleTaskList = workflowBo.queryForHandleListBean(userInfo, condition, "");
		DynaBean bean;
		int waitReplyTaskNum = 0;
		int waitApproveTaskNum = 0;
		int waitConfirmTaskNum = 0;
		int waitEvaluateTaskNum = 0;
		for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
			bean = (DynaBean) handleTaskList.get(i);
			String troubleid = (String) bean.get("id");
			if (bean != null) {
				String id = (String) bean.get("id");
				if (bean.get("flow_state") != null) {
					if (TroubleWorkflowBO.REPLY_TASK.equals(bean.get("flow_state"))) {
						waitReplyTaskNum++;
					}
					if (TroubleWorkflowBO.EOMS_APPROVE.equals(bean.get("flow_state"))) {
						waitConfirmTaskNum++;
					}
					if (TroubleWorkflowBO.APPROVE_TASK.equals(bean.get("flow_state"))) {
						/*
						 * List replys =
						 * troubleApproveDAO.viewReplysByApproves(userInfo,id);
						 * if(replys!=null && replys.size()>0){
						 * waitApproveTaskNum++; }
						 */
						List waitReplys = troubleApproveDAO.getReplyInfos(troubleid, userId, "");
						int num = 0;
						if (waitReplys != null && waitReplys.size() > 0) {
							for (int j = 0; j < waitReplys.size(); j++) {
								BasicDynaBean replybean = (BasicDynaBean) waitReplys.get(j);
								String replyid = (String) replybean.get("id");
								String state = (String) replybean.get("state");
								// boolean flag=true;
								boolean read = approverDAO
										.isReadOnly(replyid, userId, TroubleConstant.LP_TROUBLE_REPLY);
								if (read == false) {// 待审核的
									if (state != null && state.equals(TroubleConstant.REPLY_APPROVE_WAIT)) {
										num++;
										break;
									}
								}
							}
						}
						if (num > 0) {
							waitApproveTaskNum++;
						}
						if (num <= 0) {
							boolean ishave = ishaveReply(userInfo, troubleid);
							if (ishave) {
								waitApproveTaskNum++;
							}
						}
					}
					if (TroubleWorkflowBO.EVALUATE_TASK.equals(bean.get("flow_state"))) {
						waitEvaluateTaskNum++;
					}
				}
			}
		}
		list.add(waitReplyTaskNum);
		list.add(waitApproveTaskNum);
		list.add(waitConfirmTaskNum);
		list.add(waitEvaluateTaskNum);
		return list;
	}

	/**
	 * 查询已办历史节点数量
	 * 
	 * @param condition
	 * @param userInfo
	 * @return
	 */
	public List queryForHistoryTroubleNum(String condition, UserInfo userInfo) {
		String selcondition = ",handled_task_name,task_out_come,handled_time  ";
		List list = new ArrayList();
		List handleTaskListo = querydao.getHandeledWorks(userInfo, condition, selcondition);
		List handleTaskList = getNoRepeatList(handleTaskListo);
		DynaBean bean;
		int dispatchNum = 0;
		int replyTaskNum = 0;
		int approveTaskNum = 0;
		int confirmTaskNum = 0;
		int evaluateTaskNum = 0;
		for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
			bean = (DynaBean) handleTaskList.get(i);
			// String troubleid = (String) bean.get("id");
			if (bean != null) {
				Object state = bean.get("handled_task_name");
				if (state != null) {
					if ("start".equals(state)) {
						dispatchNum++;
					}
					if (TroubleWorkflowBO.REPLY_TASK.equals(state)) {
						replyTaskNum++;
					}
					if (TroubleWorkflowBO.EOMS_APPROVE.equals(state)) {
						confirmTaskNum++;
					}
					if (TroubleWorkflowBO.APPROVE_TASK.equals(state)) {
						approveTaskNum++;
					}
					if (TroubleWorkflowBO.EVALUATE_TASK.equals(state)) {
						evaluateTaskNum++;
					}
				}
			}
		}
		list.add(dispatchNum);
		list.add(replyTaskNum);
		list.add(confirmTaskNum);
		list.add(approveTaskNum);
		list.add(evaluateTaskNum);
		return list;
	}

	public List getNoRepeatList(List handleTaskList) {
		List list = new ArrayList();
		DynaBean bean;
		for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
			int num = 0;
			bean = (DynaBean) handleTaskList.get(i);
			if (bean != null) {
				String state = (String) bean.get("handled_task_name");
				String troubleid = (String) bean.get("id");
				if (list.size() > 0) {
					for (int m = 0; m < list.size(); m++) {
						DynaBean dbean = (DynaBean) list.get(m);
						String tid = (String) dbean.get("id");
						String folowstate = (String) dbean.get("handled_task_name");
						if (state.equals(folowstate) && troubleid.equals(tid)) {
							num++;
						}
					}
				}
			}
			if (num == 0) {
				list.add(bean);
			}
		}
		return list;
	}

	/**
	 * 根据用户id查询用户
	 * 
	 * @param userid
	 * @return
	 */
	public UserInfo getUserInfoByUserId(String userid) {
		return dao.getUserInfoByUserId(userid);
	}

	public Contractor getContractorByConId(String deptid) {
		return dao.getContractorByConId(deptid);
	}

	public Depart getDepartByDepartId(String deptid) {
		return dao.getDepartByDepartId(deptid);
	}

	/**
	 * 删除故障信息
	 * 
	 * @param troubleid
	 */
	public void deleteTrouble(String troubleid) {
		// 故障
		TroubleInfo trouble = troubleinfoDAO.get(troubleid);
		troubleinfoDAO.delete(trouble);
		// 反馈单
		List<TroubleReply> replys = dao.getReplysByTroubleId(troubleid, "");
		for (int i = 0; replys != null && i < replys.size(); i++) {
			TroubleReply reply = replys.get(i);
			String replyid = reply.getId();
			processerDAO.deleteProcesser(replyid);// 处理人员
			cableDAO.deleteList(replyid);// 故障光缆信息
			troubleAccidentsDAO.deleteByReplyId(replyid);// 反馈单对应的隐患
			approverDAO.deleteList(replyid, TroubleConstant.LP_TROUBLE_REPLY);// 审核人
			useMaterialDAO.deleteList(replyid, TroubleConstant.TROUBLE_MODULE);// 使用材料
			returnMaterialDAO.deleteList(replyid, TroubleConstant.TROUBLE_MODULE);// 回收材料
			approveDAO.deleteApproveInfo(replyid, TroubleConstant.LP_TROUBLE_REPLY);// 审核历史
			dao.delete(reply);
		}
		// eoms单号
		List<TroubleEoms> emos = eomsDAO.getEomsByTroubleId(troubleid);
		for (int j = 0; emos != null && j < emos.size(); j++) {
			TroubleEoms e = emos.get(j);
			eomsDAO.delete(e);
		}
		processDAO.deleteProcess(troubleid, ""); // 处理过程
		unitDAO.deleteTroubleUnit(troubleid);// 故障处理单位
	}

	/**
	 * 根据故障反馈时限、故障派发时间和故障反馈单填写时间计算故障反馈超时/提前时长
	 * 
	 * @param troubleInfo
	 * @param reply
	 * @return
	 */
	public int[] calculateReplyTimeLength(TroubleInfo troubleInfo, TroubleReply reply) {
		// TODO Auto-generated method stub
		Date replyDeadline = troubleInfo.getReplyDeadline();
		Date troubleEndTime = reply.getReplySubmitTime();
		long timeLength = 0;
		int timeLengthHour = 0;
		int timeLengthMinute = 0;
		if (troubleEndTime != null && replyDeadline != null) {
			timeLength = (troubleEndTime.getTime() - replyDeadline.getTime());
			timeLength = timeLength / MINUTE_TIME_LENGTH;
			timeLengthHour = (int) timeLength / (HOUR_TIME_LENGTH / MINUTE_TIME_LENGTH);
			timeLengthMinute = (int) timeLength % (HOUR_TIME_LENGTH / MINUTE_TIME_LENGTH);
		}
		return new int[] { (int) timeLength, timeLengthHour, timeLengthMinute };
	}
}
