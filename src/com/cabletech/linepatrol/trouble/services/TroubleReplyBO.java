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
	 * ƽ̨��׼�޸� ����ʱ��
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
		if (confirmResult.equals(TroubleConstant.REPLY_ROLE_HOST)) {// ����
			String acctime = bean.getTroubleAcceptTime();
			Date time = DateUtil.parseDate(acctime, "yyyy/MM/dd HH:mm:ss");
			reply.setTroubleAcceptTime(time);
		}
		reply.setApproveState(TroubleConstant.REPLY_APPROVE_WAIT);// �ƶ����
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
	 * ����Э��
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
			reply.setApproveState(TroubleConstant.TEMP_SAVE);// ��ʱ����
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
	 * ��������
	 * 
	 * @param bean
	 * @param userinfo
	 * @param unitid
	 *            �����봦����ϵ�λ���ϵͳid
	 * @param responsible
	 *            ������
	 * @param testman
	 *            ���ϲ�����Ա
	 * @param mendman
	 *            �ֳ�����������Ա
	 * @param files
	 *            ������Ϣ
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
			reply.setApproveState(TroubleConstant.TEMP_SAVE);// ��ʱ����
			reply.setContractorId(userinfo.getDeptID());
			reply.setReplyManId(userinfo.getUserID());
			reply.setReplySubmitTime(new Date());
		} else {
			reply.setApproveState(TroubleConstant.REPLY_APPROVE_DISPATCH);
			reply.setReplyManId(userid);
			reply.setContractorId(userinfo.getDeptID());
			reply.setEvaluateState(TroubleConstant.REPLY_EXAM_STATE_NO);
			if (id == null) {// ��ʾ����ӷ�����
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
	 * ��ʼ�����ϸ�����Ա��Ϣ
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
	 * ��ʼ�����ϲ�����Ա��Ϣ
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
	 * ��ʼ�������ֳ�����������Ա��Ϣ
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
	 * ���Ͷ��Ÿ��ɵ���
	 */
	public void sendMsgToDispatch(TroubleInfo trouble, String sendManPhone, String replyid) {
		// ���Ͷ���
		String content = "����·���ϡ�����һ����������Ϊ\"" + trouble.getTroubleName() + "\"�ķ������ȴ����ĺ�׼��";
		System.out.println(content);
		// ����֪ͨ ��д�������󣬷����ɵ���ƽ̨���
		if (sendManPhone != null && sendManPhone.length() > 0) {
			logger.info("��������: " + sendManPhone + ":" + content);
			// if(mobile.length() == 13){
			super.sendMessage(content, sendManPhone);
			// smSendProxy.simpleSend(sendManPhone,content, null, null, true);
			// }
		}
		// ������ż�¼
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
	 * ���Ͷ��Ÿ�����˻��߳�����
	 */
	public void sendMsg(TroubleReplyBean bean, TroubleInfo trouble, UserInfo user) {
		String mobiles = bean.getMobiles();
		String rmobiles = bean.getRmobiles();
		// ���Ͷ���
		String content = "����·���ϡ�����һ����������Ϊ\"" + trouble.getTroubleName() + "\"�ķ������ȴ�������ˡ�";
		System.out.println(content);
		// ����֪ͨ ƽ̨���ָ���������
		if (mobiles != null && mobiles.length() > 0) {
			super.sendMessage(content, mobiles);
			// List<String> mobileList = StringUtils.string2List(mobiles, ",");

			// for(String mobile:mobileList){
			// logger.info("��������: "+mobile +":"+content);
			// //if(mobile.length() == 13){
			// smSendProxy.simpleSend(mobile,content, null, null, true);
			// //}
			// }
		}
		// �������ֻ�����
		if (rmobiles != null && rmobiles.length() > 0) {
			String msg = "����·���ϡ�����һ����������Ϊ\"" + trouble.getTroubleName() + "\"�ķ������ȴ����Ĳ��ա�";
			List<String> mobileList = StringUtils.string2List(rmobiles, ",");
			super.sendMessage(msg, mobileList);
			System.out.println(msg);
			// for(String mobile:mobileList){
			// System.out.println("������:"+mobile);
			// smSendProxy.simpleSend(mobile,msg, null, null, true);
			// }
		}
		// ������ż�¼
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
	 * ���Ϸ���������
	 * 
	 * @param reply
	 * @param user
	 * @param unitid
	 * @param sendMan
	 * @throws Exception
	 */
	private void replyWorkFlow(TroubleReply reply, UserInfo user, String unitid, String sendMan) throws Exception {
		// ���й��Ϸ����������죩
		// ִ�й��Ϸ�������ҵ���߼�
		// ִ�й��Ϸ�������Ĺ�����������̣���ҪǨ�Ƶ�ҵ�����У�
		// user��Դ��ϵͳ��¼�û���unit.getId()������unitId�滻����unitIdΪlp_trouble_process_unit������
		Task task = workflowBo.getHandleTaskForId(user.getDeptID(), unitid);
		if (task != null && TroubleWorkflowBO.REPLY_TASK.equals(task.getName())) {
			System.out.println("���ϴ�������" + task.getName());
			workflowBo.setVariables(task, "assignee", sendMan);
			workflowBo.completeTask(task.getId(), "submit");
			System.out.println("�����Ѿ�������");
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.initial(task, user, "", ModuleCatalog.TROUBLE);
			processHistoryBean.setNextOperateUserId(sendMan);
			processHistoryBean.setObjectId(unitid);
			processHistoryBean.setProcessAction("���Ϸ���");
			processHistoryBean.setTaskOutCome("submit");
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
	}

	private void dispatchWorkFlow(TroubleReply reply, UserInfo user, String unitid, String approvers) throws Exception {
		Task task = workflowBo.getHandleTaskForId(user.getUserID(), unitid);
		if (task != null && TroubleWorkflowBO.EOMS_APPROVE.equals(task.getName())) {
			System.out.println("���ϴ�ƽ̨��׼��" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers);
			workflowBo.completeTask(task.getId(), "approve");
			System.out.println("�����Ѿ�ͨ��ƽ̨��׼��");
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.initial(task, user, "", ModuleCatalog.TROUBLE);
			processHistoryBean.setNextOperateUserId(approvers);
			processHistoryBean.setObjectId(unitid);
			processHistoryBean.setProcessAction("ƽ̨��׼");
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
	 * ���ݹ���id��ѯ����ķ�����
	 * 
	 * @param troubleid
	 * @return
	 */
	@Transactional(readOnly = true)
	public TroubleReply getReplyByTroubleId(String troubleid) {
		return dao.getReplyByTroubleId(troubleid);
	}

	/**
	 * ��ѯ�Ƿ���д������
	 * 
	 * @param troubleid
	 *            ����id
	 * @param contractorId
	 *            ����λid
	 * @return
	 */
	@Transactional(readOnly = true)
	public TroubleReply getReplyByTroubleIAndContractorID(String troubleid, String contractorId) {
		return dao.getReplyByTroubleIAndContractorID(troubleid, contractorId);
	}

	/**
	 * ��������˳�������Ϣ
	 * 
	 * @param cableinfo
	 */
	public void addApprover(ReplyApprover approver) {
		approverDAO.save(approver);
	}

	/**
	 * ������¹�����Ϣ
	 * 
	 * @param cableinfo
	 */
	public void addCable(TroubleCableInfo cableinfo) {
		cableDAO.save(cableinfo);
	}

	/**
	 * ������ϴ������
	 * 
	 * @param process
	 */
	public void addProcess(TroubleProcess process) {
		processDAO.save(process);
	}

	/**
	 * ���洦����Ա
	 * 
	 * @param processer
	 */
	public void addProcesser(TroubleProcesser processer) {
		processerDAO.save(processer);
	}

	/**
	 * ����ʹ�ò���
	 * 
	 * @param
	 */
	public void addMaterialUsed() {
	}

	/**
	 * ������ղ���
	 * 
	 * @param
	 */
	public void addMaterialReturn() {
	}

	/**
	 * ���ݷ�����id��ѯ���ϴ���λ��Ϣ
	 * 
	 * @param replyid
	 * @return
	 */
	public TroubleProcessUnit getProcessUnitByReplyId(String replyid) {
		return unitDAO.getProcessUnitByReplyId(replyid);
	}

	/**
	 * ���ݹ���id��ѯeoms����
	 * 
	 * @param troubleid
	 *            ����id
	 */
	public List<TroubleEoms> getEomsByTroubleId(String troubleid) {
		return eomsDAO.getEomsByTroubleId(troubleid);
	}

	/**
	 * ���½����ַ���Ϊlist
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
	 * ��ѯ���������ϵ�
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
				if (ishaveReply(user, troubleid)) {// ������
					troubles.add(bean);
				}
				if (getDispatchTrouble(user, troubleid)) {// �ɵ���
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
				if (taskName.equals(TroubleWorkflowBO.APPROVE_TASK)) {// �����(����ˡ����������Ҳ�ǳ����˵����)
					List waitReplys = troubleApproveDAO.getReplyInfos(troubleid, userid, "");
					int num = 0;
					if (waitReplys != null && waitReplys.size() > 0) {
						for (int i = 0; i < waitReplys.size(); i++) {
							BasicDynaBean replybean = (BasicDynaBean) waitReplys.get(i);
							String replyid = (String) replybean.get("id");
							String state = (String) replybean.get("state");
							// boolean flag=true;
							boolean read = approverDAO.isReadOnly(replyid, userid, TroubleConstant.LP_TROUBLE_REPLY);
							if (read == false) {// ����˵�
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
			if (!taskName.equals(TroubleWorkflowBO.APPROVE_TASK)) {// ����
				troubles.add(bean);
			}
		}

		if (depttype.equals("2")) {
			troubles.add(bean);
		}
		return troubles;
	}

	/**
	 * �����ʱ��ѯ��������Ϣ
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
	 * �ж��ǲ����Լ��ɷ��ĵ���
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
	 * �Ѹ��ݷ�������ѯ�Ĵ�����Ա���ղ�ͬ��������Ϊ�ַ���
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
	 * ���ݷ�������id��ѯ������Ա��Ϣ
	 * 
	 * @param replyid
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<TroubleProcesser> getProcesserByReplyId(String replyid) {
		return processerDAO.getProcesserByReplyId(replyid);
	}

	/**
	 * �õ�����ԭ������
	 * 
	 * @param troubleid
	 *            ����id
	 * @return
	 */
	@Transactional(readOnly = true)
	public String getTroubleReasonName(String troubleid) {
		return dao.getTroubleReasonName(troubleid);
	}

	/**
	 * �õ�������������
	 * 
	 * @param replyid
	 *            ������
	 * @return
	 */
	/*
	 * @Transactional(readOnly = true) public String getTroubleType(String
	 * replyid){ return dao.getTroubleType(replyid); }
	 */
	/**
	 * ��������Ӧ������Ϣ�б�
	 * 
	 * @param replyid
	 * @return
	 */
	@Transactional(readOnly = true)
	public BasicDynaBean getAccidentByReplyId(String replyid) {
		return dao.getAccidentByReplyId(replyid);
	}

	/**
	 * ��������Ӧ�������Ϣ�б�
	 * 
	 * @param replyid
	 * @return
	 */
	public List getApproveResultList(String replyid) {
		return null;
	}

	/**
	 * ��������Ӧ���������Ϣ�б�
	 * 
	 * @param replyid
	 * @return
	 */
	public List approverList(String replyid) {
		return dao.approverList(replyid);
	}

	/**
	 * ��������Ӧ�Ĺ�����Ϣ�б�
	 * 
	 * @param replyid
	 * @return
	 */
	public List cableList(String replyid) {
		return dao.cableList(replyid);
	}

	/**
	 * ��������Ӧ��ʹ�ò�����Ϣ�б�
	 * 
	 * @return
	 */
	public List getMaterialList(String replyid) {
		return dao.getMaterialList(replyid);
	}

	/**
	 * ��������Ӧ�Ļ��ղ�����Ϣ�б�
	 * 
	 * @return
	 */
	public List getMaterialListReturn(String replyid) {
		return dao.getMaterialListReturn(replyid);
	}

	/**
	 * �����ɵ���Ӧ�Ĵ��������Ϣ�б�
	 * 
	 * @param troubleid
	 *            ����id
	 * @param conid
	 *            ��άid
	 * @return
	 */
	public List getTroubleProcessByCondition(String troubleid, String conid) {
		return dao.getTroubleProcessByCondition(troubleid, conid);
	}

	/**
	 * ���ݴ�����̣�����Ĭ�ϵĹ�������
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
	 * �����ɵ���Ӧ�Ĵ��������Ϣ�б�
	 * 
	 * @param troubleid
	 * @return
	 */
	public List getTroubleProcessList(String troubleid) {
		return dao.getTroubleProcessList(troubleid);
	}

	/**
	 * ��������Ӧ�Ĵ��������Ϣ�б�
	 * 
	 * @param replyid
	 * @return
	 */
	public List getProcessList(String replyid) {
		return dao.getProcessList(replyid);
	}

	/**
	 * ��������Ӧ�Ĵ�����Ա��Ϣ�б�
	 * 
	 * @param replyid
	 * @return
	 */
	public List getProcessManList(String replyid) {
		return dao.getProcessManList(replyid);
	}

	/**
	 * ����ָ���Ĺ����ɵ������ �ж����췴�����Ƿ����ͨ��
	 * 
	 * @param troubleid
	 *            �����ɵ����
	 * @return true ���ͨ��
	 */
	public boolean judgeAllApproved(String troubleid) {
		return dao.judgeAllApproved(troubleid);
	}

	/**
	 * ����ָ���Ĺ����ɵ�������ж��Ƿ����еķ��������Ѿ�����������
	 * 
	 * @param troubleid
	 *            �����ɵ����
	 * @return
	 */
	public boolean judgeAllEvaluated(String troubleid) {
		return dao.judgeAllEvaluated(troubleid);
	}

	/**
	 * ����ָ���Ĺ����ɵ�������жϷ������Ƿ�ȫ������
	 * 
	 * @param toroubleid
	 * @return true:��ʾȫ������
	 */
	public boolean judgeExistReplyAll(String toroubleid) {
		return dao.judgeExistReplyAll(toroubleid);
	}

	/**
	 * �жϹ��Ϸ������Ƿ�������
	 * 
	 * @param troubleid
	 * @return true ������
	 */
	public boolean judgeReplyAllRoleHaveHost(String troubleid) {
		return dao.judgeReplyAllRoleHaveHost(troubleid);
	}

	/**
	 * ���ݹ��Ϸ�����Ϣ���жϸù��Ϸ�����Ϣ�Ƿ��Ѿ����ڡ�
	 * 
	 * @param replyInfo
	 * @return
	 */
	public boolean judgeExistReply(TroubleReply replyInfo) {
		return dao.judgeExistReply(replyInfo);
	}

	/**
	 * ����ָ���Ĺ��Ϸ�����Ϣ���ж��Ƿ�Ը÷�����Ϣ���п���������
	 * 
	 * @param replyInfo
	 * @return
	 */
	public boolean judgeNeedEvaluate(TroubleReply replyInfo) {
		return dao.judgeNeedEvaluate(replyInfo);

	}

	/**
	 * ����ָ���ķ����������д����Ҫ����������Ϣ��
	 * 
	 * @param replyid
	 * @return
	 */
	public boolean writeNeedEvaluate(String replyid) {
		return dao.writeNeedEvaluate(replyid);
	}

	/**
	 * ����ָ���ķ����������д�벻��Ҫ����������Ϣ��
	 * 
	 * @param replyid
	 * @return
	 */
	public boolean writeNotNeedEvaluate(String replyid) {
		return dao.writeNotNeedEvaluate(replyid);
	}

	/**
	 * ���½����ַ���Ϊlist
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
	 * ���������ַ���Ϊlist
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
	 * �����ַ���
	 * 
	 * @param str
	 * @return
	 */
	public Map<String, String> parseString(String str) {
		// String str = "����,13900000000;����,13511111111";
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
	 * ���³����˵��Ѿ����ı��
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
			processHistoryBean.setProcessAction("���Ϸ���������");
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
		if (deptype.equals("1")) {// �ƶ������
			assignee = userId;
		}
		if (deptype.equals("2")) {// ��ά������
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
								if (read == false) {// ����˵�
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
	 * ��ѯ�Ѱ���ʷ�ڵ�����
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
	 * �����û�id��ѯ�û�
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
	 * ɾ��������Ϣ
	 * 
	 * @param troubleid
	 */
	public void deleteTrouble(String troubleid) {
		// ����
		TroubleInfo trouble = troubleinfoDAO.get(troubleid);
		troubleinfoDAO.delete(trouble);
		// ������
		List<TroubleReply> replys = dao.getReplysByTroubleId(troubleid, "");
		for (int i = 0; replys != null && i < replys.size(); i++) {
			TroubleReply reply = replys.get(i);
			String replyid = reply.getId();
			processerDAO.deleteProcesser(replyid);// ������Ա
			cableDAO.deleteList(replyid);// ���Ϲ�����Ϣ
			troubleAccidentsDAO.deleteByReplyId(replyid);// ��������Ӧ������
			approverDAO.deleteList(replyid, TroubleConstant.LP_TROUBLE_REPLY);// �����
			useMaterialDAO.deleteList(replyid, TroubleConstant.TROUBLE_MODULE);// ʹ�ò���
			returnMaterialDAO.deleteList(replyid, TroubleConstant.TROUBLE_MODULE);// ���ղ���
			approveDAO.deleteApproveInfo(replyid, TroubleConstant.LP_TROUBLE_REPLY);// �����ʷ
			dao.delete(reply);
		}
		// eoms����
		List<TroubleEoms> emos = eomsDAO.getEomsByTroubleId(troubleid);
		for (int j = 0; emos != null && j < emos.size(); j++) {
			TroubleEoms e = emos.get(j);
			eomsDAO.delete(e);
		}
		processDAO.deleteProcess(troubleid, ""); // �������
		unitDAO.deleteTroubleUnit(troubleid);// ���ϴ���λ
	}

	/**
	 * ���ݹ��Ϸ���ʱ�ޡ������ɷ�ʱ��͹��Ϸ�������дʱ�������Ϸ�����ʱ/��ǰʱ��
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
