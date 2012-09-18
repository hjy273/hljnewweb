package com.cabletech.linepatrol.acceptance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.util.Hanzi2PinyinUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.acceptance.beans.ApplyBean;
import com.cabletech.linepatrol.acceptance.beans.QueryBean;
import com.cabletech.linepatrol.acceptance.dao.ApplyCableDao;
import com.cabletech.linepatrol.acceptance.dao.ApplyContractorDao;
import com.cabletech.linepatrol.acceptance.dao.ApplyDao;
import com.cabletech.linepatrol.acceptance.dao.ApplyPipeDao;
import com.cabletech.linepatrol.acceptance.dao.ApplyTaskDao;
import com.cabletech.linepatrol.acceptance.dao.CableTaskDao;
import com.cabletech.linepatrol.acceptance.dao.PayCableDao;
import com.cabletech.linepatrol.acceptance.dao.PayPipeDao;
import com.cabletech.linepatrol.acceptance.dao.PipeTaskDao;
import com.cabletech.linepatrol.acceptance.dao.SubflowDao;
import com.cabletech.linepatrol.acceptance.model.Apply;
import com.cabletech.linepatrol.acceptance.model.ApplyCable;
import com.cabletech.linepatrol.acceptance.model.ApplyContractor;
import com.cabletech.linepatrol.acceptance.model.ApplyPipe;
import com.cabletech.linepatrol.acceptance.model.ApplyTask;
import com.cabletech.linepatrol.acceptance.model.CableTask;
import com.cabletech.linepatrol.acceptance.model.PayCable;
import com.cabletech.linepatrol.acceptance.model.PayPipe;
import com.cabletech.linepatrol.acceptance.model.PipeTask;
import com.cabletech.linepatrol.acceptance.model.Subflow;
import com.cabletech.linepatrol.acceptance.workflow.AcceptanceFlow;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.module.AppraiseDaily;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyDailyBO;
import com.cabletech.linepatrol.appraise.service.AppraiseDailySpecialBO;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.resource.dao.RepeaterSectionDao;
import com.cabletech.linepatrol.resource.model.RepeaterSection;

@Service
@Transactional
public class ApplyManager extends EntityManager<Apply, String> {
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Resource(name = "applyDao")
	private ApplyDao dao;
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	@Resource(name = "excelManager")
	private ExcelManager excelManager;
	@Autowired
	private AcceptanceFlow workflowBo;
	@Resource(name = "subflowDao")
	private SubflowDao subflowDao;
	@Resource(name = "applyTaskDao")
	private ApplyTaskDao applyTaskDao;
	@Resource(name = "payCableDao")
	private PayCableDao payCableDao;
	@Resource(name = "payPipeDao")
	private PayPipeDao payPipeDao;
	@Resource(name = "applyCableDao")
	private ApplyCableDao applyCableDao;
	@Resource(name = "applyPipeDao")
	private ApplyPipeDao applyPipeDao;
	@Resource(name = "cableTaskDao")
	private CableTaskDao cableTaskDao;
	@Resource(name = "pipeTaskDao")
	private PipeTaskDao pipeTaskDao;
	@Resource(name = "applyContractorDao")
	private ApplyContractorDao acDao;
	@Resource(name = "repeaterSectionDao")
	private RepeaterSectionDao trunkDao;
	@Autowired
	private AppraiseDailyDailyBO appraiseDailyBO;
	@Autowired
	private AppraiseDailySpecialBO appraiseDailySpecialBO;
	
	/**
	 * ���Ψһ��ҵ�����
	 * ҵ�������ô�ά��˾+����+��ţ���Ų���ҵ�����ͳһ���룬��������һ���ʼʱ���㣩
	 * @param deptName
	 * @return
	 */
	public String generateCode(String deptName,String type) {
		String code = Hanzi2PinyinUtil.generateCode4Prefix(deptName);
		List<Apply> applys = dao.getApply4Type(type);
		int lenght = applys.size()+1;
		
		String number = String.format("%04d", lenght); 
		
		for(Apply apply:applys){
			if(apply.getCode().indexOf(number) != -1){
				number = String.format("%04d", ++lenght);
			}
		}
		logger.info("ҵ��ID��"+code+number);
		return code+number;
		
	}
	

	// ��֤�����Ƿ����
	public boolean invalidApply(String type, Apply apply) {
		boolean flag = true;
		Set set = new HashSet();
		if (type.equals(AcceptanceConstant.CABLE)) {
			set = apply.getCables();
		} else {
			set = apply.getPipes();
		}
		if (!set.isEmpty()) {
			flag = false;
		}
		return flag;
	}

	// ��֤����Ĺ��±���Ƿ����ظ�
	public Apply addCable(Apply apply, ApplyCable cable) {
		if (!excelManager.validateCableNumber(apply, cable.getCableNo())) {
			apply.setErrorMsg("���±��" + cable.getCableNo() + "�Ѵ��ڡ�");
		} else {
			apply.addCable(cable);
			apply.clearPipe();
			apply.clearErrorMsg();
		}
		return apply;
	}
	
	// ��֤����Ĺܵ�����Ƿ����ظ�
	public Apply addPipe(Apply apply, ApplyPipe pipe) {
		// if(!excelManager.validatePipeNumber(apply, pipe.getAssetno())){
		// apply.setErrorMsg("���±��"+pipe.getAssetno()+"�Ѵ��ڡ�");
		// }else{
		apply.addPipe(pipe);
		apply.clearCable();
		apply.clearErrorMsg();
		// }
		return apply;
	}

	// �������»�ܵ�
	public void export(Apply apply, HttpServletResponse response) {
		if (!apply.getCables().isEmpty()) {
			excelManager.exportCable(apply, response);
		} else {
			excelManager.exportPipe(apply, response);
		}
	}

	// ������ά��Ϣ
	public void export(List<RepeaterSection> trunks,
			HttpServletResponse response) {
		excelManager.export(trunks, response);
	}

	// ��������
	public void saveApply(Apply apply, UserInfo userInfo, String assinee) {
		//��ʼ������
		initApply(apply, userInfo);

		// ��������
		if (apply.getResourceType().equals(AcceptanceConstant.CABLE)) {
			setApplyCable(apply);
		} else {
			setApplyPipe(apply);
		}
		// ��������
		dao.save(apply);
		// ���������
		replyApproverDAO.saveApproverOrReader(assinee, apply.getId(),
				CommonConstant.APPROVE_MAN, AcceptanceConstant.MODULE);

		// ����
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("assignee", assinee);
		variables.put("transition", "no");
		String processId = workflowBo.createProcessInstance("acceptance", apply
				.getId(), variables);

		// ��������id��״̬
		apply.setProcessInstanceId(processId);
		apply.setProcessState(AcceptanceConstant.ALLOT);
		dao.save(apply);

		// ������ʷ
		workflowBo.saveProcessHistory(userInfo, apply, null, assinee, "start",
				"��������", "start");

		// ��������: ���Ͷ��Ÿ���ά���ĵ����ս�ά�����ˡ�
		String content = "�����ս�ά������һ������Ϊ\"" + apply.getName() + "\"������ȴ����ĺ�׼��";
		String mobile = getMobileFromUserId(assinee);
		List<String> mobiles = com.cabletech.commons.util.StringUtils
				.string2List(mobile, ",");
		try{
		this.sendMessage(content, mobiles);
		}catch(Exception ex){
		    logger.error("���Ͷ���ʧ��:",ex);
		}
	}

	/**
	 * ��ʼ������
	 * @param apply
	 * @param userInfo
	 */
	private void initApply(Apply apply, UserInfo userInfo) {
		apply.setId(null);
		apply.setApplicant(userInfo.getUserID());
		apply.setApplyDate(new Date());
		apply.setCode(generateCode(userInfo.getDeptName(),apply.getResourceType()));
	}

	// ���渴������
	public void reinspect(ApplyBean applyBean, Apply napply, UserInfo userInfo,
			String approver) {
		// �������벢����
		Apply apply = new Apply();
		BeanUtil.copyProperties(applyBean, apply);
		initApply(apply, userInfo);
		dao.save(apply);

		// ���������
		replyApproverDAO.saveApproverOrReader(approver, apply.getId(),
				CommonConstant.APPROVE_MAN, AcceptanceConstant.MODULE);

		Set<String> contractors = new HashSet<String>();

		if (applyBean.getResourceType().equals(AcceptanceConstant.CABLE)) {
			//������¸���������Ϣ
			saveReinspectCable(napply, apply, contractors);
		} else {
			//����ܵ�����������Ϣ
			saveReinspectPipe(napply, apply, contractors);
		}

		// ���������ά������
		saveApplyContractor(apply, contractors);

		// ������
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("transition", "yes");
		variables.put("assignee", approver);
		String processId = workflowBo.createProcessInstance("acceptance", apply
				.getId(), variables);

		// ��������id��״̬
		apply.setProcessInstanceId(processId);
		apply.setProcessState(AcceptanceConstant.RECHECK);
		dao.save(apply);

		// ������ʷ
		workflowBo.saveProcessHistory(userInfo, apply, null, StringUtils.join(
				contractors.iterator(), ","), "start", "����������", "start");

		// ��������
		String content = "�����ս�ά������һ������Ϊ\"" + apply.getName() + "\"�ĸ�������ȴ����ĺ�׼��";
		String mobile = getMobileFromUserId(approver);
		List<String> mobiles = com.cabletech.commons.util.StringUtils
				.string2List(mobile, ",");
		try{
		this.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("���Ͷ���ʧ��:",ex);
        }
	}

	/**
	 * ���������ά������
	 * @param apply
	 * @param contractors
	 */
	private void saveApplyContractor(Apply apply, Set<String> contractors) {
		for (String contractorId : contractors) {
			ApplyContractor ac = new ApplyContractor();
			ac.setContractorId(contractorId);
			ac.setApplyId(apply.getId());
			acDao.save(ac);
		}
	}

	/**
	 * ����ܵ�������Ϣ
	 * @param napply
	 * @param apply
	 * @param contractors
	 */
	private void saveReinspectPipe(Apply napply, Apply apply, Set<String> contractors) {
		// �õ���ǰ�Ĺܵ�
		Set<ApplyPipe> pipes = napply.getPipes();

		for (ApplyPipe pipe : pipes) {
			// �õ���ǰ������
			ApplyTask applyTask = applyTaskDao.getApplyTaskFromPipeId(pipe
					.getId());
			PipeTask pTask=pipeTaskDao.loadPipeTask(applyTask.getId());
			
			// �õ���ǰ�Ĺܵ���ά��Ϣ
			List<PayPipe> payPipes = payPipeDao.getPayPipesByTaskId(pTask.getId());
			
			// �½�һ������
			ApplyTask newApplyTask = new ApplyTask();
			initApplyTask(apply, applyTask, newApplyTask);
			applyTaskDao.save(newApplyTask);
			
			// �½�һ���ܵ���Ϣ
			ApplyPipe newApplyPipe = new ApplyPipe();
			initApplyPipe(apply, pipe, newApplyPipe);
			contractors.add(pipe.getContractorId());
			applyPipeDao.save(newApplyPipe);

			// �½�һ���ܵ�����������¼
			PipeTask pipeTask = new PipeTask();
			pipeTask.setTaskId(newApplyTask.getId());
			pipeTask.setPipeId(newApplyPipe.getId());
			pipeTaskDao.save(pipeTask);
			for(PayPipe payPipe:payPipes){
				// �½���ά��Ϣ(��ά��Ϣ�����½���������ǰ�ĵ����ݲ���ʹ��)
				PayPipe newPayPipe = new PayPipe();
				BeanUtil.copyProperties(payPipe, newPayPipe);
				newPayPipe.setId(null);
				int times = payPipe.getAcceptanceTimes();
				newPayPipe.setAcceptanceTimes(++times);
				newPayPipe.setTaskId(pipeTask.getId());
				newPayPipe.setPassed(AcceptanceConstant.REINSPECT);
				payPipeDao.save(newPayPipe);
			}
		}
	}

	/**
	 * ��ʼ���ܵ���Ϣ
	 * @param apply
	 * @param pipe
	 * @param newApplyPipe
	 */
	private void initApplyPipe(Apply apply, ApplyPipe pipe, ApplyPipe newApplyPipe) {
		BeanUtil.copyProperties(pipe, newApplyPipe);
		newApplyPipe.setId(null);
		newApplyPipe.setApply(apply);
		newApplyPipe.setIsrecord(AcceptanceConstant.NOTRECORD);
		newApplyPipe.setIspassed(AcceptanceConstant.NOTPASSED);
	}

	/**
	 * ��ʼ��applyTask
	 * @param apply
	 * @param applyTask
	 * @param newApplyTask
	 */
	private void initApplyTask(Apply apply, ApplyTask applyTask, ApplyTask newApplyTask) {
		BeanUtil.copyProperties(applyTask, newApplyTask);
		newApplyTask.setId(null);
		newApplyTask.setApplyId(apply.getId());
		newApplyTask.setIsComplete(AcceptanceConstant.UNCOMPLETED);
	}

	/**
	 * ������¸�����Ϣ
	 * @param napply
	 * @param apply
	 * @param contractors
	 */
	private void saveReinspectCable(Apply napply, Apply apply, Set<String> contractors) {
		// �õ���ǰ�Ĺ���
		Set<ApplyCable> cables = napply.getCables();

		for (ApplyCable cable : cables) {
			// �õ���ǰ������
			ApplyTask applyTask = applyTaskDao
					.getApplyTaskFromCableId(cable.getId());
			//�õ��������ս�ά��׼����������Ϣ
			CableTask cTask=cableTaskDao.loadCableTask(applyTask.getId());
			// �õ���ǰ�Ĺ��½�ά��Ϣ
			PayCable payCable = payCableDao.getPayCableFromTaskId(cTask.getId());

			// �½�һ������
			ApplyTask newApplyTask = new ApplyTask();
			initApplyTask(apply, applyTask, newApplyTask);
			applyTaskDao.save(newApplyTask);
			
			// �½�һ��������Ϣ
			ApplyCable newApplyCable = new ApplyCable();
			BeanUtil.copyProperties(cable, newApplyCable);
			newApplyCable.setId(null);
			newApplyCable.setApply(apply);
			newApplyCable.setIsrecord(AcceptanceConstant.NOTRECORD);
			newApplyCable.setIspassed(AcceptanceConstant.NOTPASSED);
			contractors.add(cable.getContractorId());
			applyCableDao.save(newApplyCable);

			// �½�һ����������������¼
			CableTask cableTask = new CableTask();
			cableTask.setTaskId(newApplyTask.getId());
			cableTask.setCableId(newApplyCable.getId());
			cableTaskDao.save(cableTask);

			// �½���ά��Ϣ(��ά��Ϣ�����½���������ǰ�ĵ����ݲ���ʹ��)
			PayCable newPayCable = new PayCable();
			BeanUtil.copyProperties(payCable, newPayCable);
			newPayCable.setId(null);
			int times = payCable.getAcceptanceTimes();
			newPayCable.setAcceptanceTimes(++times);
			newPayCable.setTaskId(cableTask.getId());
			newPayCable.setPassed(AcceptanceConstant.REINSPECT);
			payCableDao.save(newPayCable);

		}
	}

	// �����׼
	public void recheck(String id, UserInfo userInfo) {
		// ������
		Apply apply = loadApply(id);
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), apply
				.getProcessInstanceId());
		workflowBo.completeTask(task.getId(), "torecord");

		// ��������״̬
		apply.setProcessState(AcceptanceConstant.RECORD);
		dao.save(apply);

		// ���д�ά��˾�������ظ�
		Set<String> cons = new HashSet<String>();
		if (apply.getResourceType().equals(AcceptanceConstant.CABLE)) {
			for (ApplyCable cable : apply.getCables()) {
				// �õ�����
				ApplyTask applyTask = applyTaskDao
						.getApplyTaskFromCableId(cable.getId());

				// �õ���ά
				cons.add(applyTask.getContractorId());
			}
		} else {
			for (ApplyPipe pipe : apply.getPipes()) {
				// �õ�����
				ApplyTask applyTask = applyTaskDao.getApplyTaskFromPipeId(pipe
						.getId());

				// �õ���ά
				cons.add(applyTask.getContractorId());
			}
		}

		// ����������
		for (String s : cons) {
			Subflow subflow = new Subflow();
			subflow.setContractorId(s);
			subflow.setApplyId(apply.getId());
			subflowDao.save(subflow);

			Map<String, Object> variable = new HashMap<String, Object>();
			variable.put("assignee", s);
			String subprocessId = workflowBo.createProcessInstance(
					"acceptancesubflow", subflow.getId(), variable);

			subflow.setProcessInstanceId(subprocessId);
			subflow.setProcessState(AcceptanceConstant.RERECORD);
			subflowDao.save(subflow);
		}

		// ������ʷ
		workflowBo.saveProcessHistory(userInfo, apply, null, StringUtils.join(
				cons.iterator(), ","), "torecord", "�����׼", "");

		// �������� �������������ɵ�����ά��˾���д�ά��˾���и��졣
		String content = "�����ս�ά����λ��һ������Ϊ\"" + apply.getName()
				+ "\"�ĸ�������ȴ����ļ�ʱ����";
		for (String s : cons) {
			List<String> mobileList = getMobileFromDeptId(s);// ���û����еõ��ֻ���
			List<String> mobiles = new ArrayList<String>();
			for (String mobile : mobileList) {
				mobiles.addAll(com.cabletech.commons.util.StringUtils
						.string2List(mobile, ","));
			}
			try{
			this.sendMessage(content, mobiles);
			}catch(Exception ex){
	            logger.error("���Ͷ���ʧ��:",ex);
	        }
		}
	}

	// �õ�����
	public Apply loadApply(String id) {
		Apply apply = dao.get(id);
		dao.initObject(apply);
		dao.initObject(apply.getCables());
		dao.initObject(apply.getPipes());
		return getDetailApply(apply);
	}

	public Apply loadApply(String id, UserInfo userInfo) {
		Apply apply = loadApply(id);
		String cancelUserName = userInfoDao
				.getUserName(apply.getCancelUserId());
		apply.setCancelUserName(cancelUserName);
		if (!userInfo.getDeptype().equals("1")) {
			String deptId = userInfo.getDeptID();
			List<ApplyCable> cables = applyCableDao.getCableForDept(id, deptId);
			List<ApplyPipe> pipes = applyPipeDao.getPipeForDept(id, deptId);
			apply.pushCableList(cables);
			apply.pushPipeList(pipes);
		}
		return getDetailApply(apply);
	}

	public Apply getDetailApply(Apply apply) {
		// �õ�����Դ����ͨ�����մ���
		int resourceNumber = 0;
		int passedNumber = 0;
		int notPassedNumber = 0;
		int recordNumber = 0;
		int notRecordNumber = 0;
		if (apply.getResourceType().equals(AcceptanceConstant.CABLE)) {
			resourceNumber = apply.getCables().size();
			for (ApplyCable c : apply.getCables()) {
				if (c.getIsrecord().equals(AcceptanceConstant.ISRECORD)) {
					recordNumber++;
					if (c.getIspassed().equals(AcceptanceConstant.PASSED)) {
						passedNumber++;
					} else {
						notPassedNumber++;
					}
				} else {
					notRecordNumber++;
				}
			}
		} else {
			resourceNumber = apply.getPipes().size();
			for (ApplyPipe p : apply.getPipes()) {
				if (p.getIsrecord().equals(AcceptanceConstant.ISRECORD)) {
					recordNumber++;
					if (p.getIspassed().equals(AcceptanceConstant.PASSED)) {
						passedNumber++;
					} else {
						notPassedNumber++;
					}
				} else {
					notRecordNumber++;
				}
			}
		}
		apply.setResourceNumber(resourceNumber);
		apply.setPassedNumber(passedNumber);
		apply.setNotPassedNumber(notPassedNumber);
		apply.setRecordNumber(recordNumber);
		apply.setNotRecordNumber(notRecordNumber);

		// �õ���ά���ƣ��ö��ŷָ�
		List<ApplyContractor> contractors = acDao.getContractors(apply.getId());
		if (contractors != null && !contractors.isEmpty()) {
			List<String> contractorNames = new ArrayList<String>();
			for (ApplyContractor contractor : contractors) {
				String contractorName = dao.getDeptName(contractor
						.getContractorId());
				contractorNames.add(contractorName);
			}
			apply.setContractorNames(StringUtils.join(contractorNames
					.iterator(), ","));
		} else {
			apply.setContractorNames("δ�����ά");
		}

		//��ô�ά��λ���
		String contractorId = "";
		List<ApplyTask> applyTaskList = applyTaskDao.getTasksList(apply.getId());
		if(applyTaskList != null && !applyTaskList.isEmpty()) {
			ApplyTask applyTask = applyTaskList.get(0);
			contractorId = applyTask.getContractorId();
			apply.setContractorId(contractorId);
		}
		
		if(!"".equals(contractorId)){
			Subflow subflow = subflowDao.getSubflow(apply.getId(), contractorId);
			if(subflow != null) {
				apply.setSubflowId(subflow.getId());
			}
		}
		return apply;
	}

	public ApplyCable loadCable(String id) {
		return applyCableDao.findByUnique("id", id);
	}

	public ApplyPipe loadPipe(String id) {
		return applyPipeDao.findByUnique("id", id);
	}

	// �õ���ά�б�
	public List<Map> getDeptOptions() {
		return dao.getDeptOptions();
	}

	// ���ù�����Ϣ
	public void setApplyCable(Apply apply) {
		Set<ApplyCable> set = apply.getCables();
		for (ApplyCable cable : set) {
			cable.setId(null);
			cable.setIsrecord(AcceptanceConstant.NOTRECORD);
			cable.setIspassed(AcceptanceConstant.NOTPASSED);
			cable.setApply(apply);
		}
	}

	// ���ùܵ���Ϣ
	public void setApplyPipe(Apply apply) {
		Set<ApplyPipe> set = apply.getPipes();
		for (ApplyPipe pipe : set) {
			pipe.setId(null);
			pipe.setIsrecord(AcceptanceConstant.NOTRECORD);
			pipe.setIspassed(AcceptanceConstant.NOTPASSED);
			pipe.setApply(apply);
		}
	}

	// ��������
	public void saveEvaluate(UserInfo userInfo,String subflowId,AppraiseDailyBean appraiseDailyBean,List<AppraiseDailyBean> speicalBeans) {
		Subflow subflow = subflowDao.findUniqueByProperty("id", subflowId);

		/*Evaluate evaluate = new Evaluate();
		evaluate.setEntityScore(score);
		evaluate.setEntityRemark(remark);
		evaluate.setEvaluator(userInfo.getUserID());
		evaluate.setEntityType(HiddangerConstant.EVALUATETYPE);
		evaluate.setEntityId(subflowId);

		// ����������¼
		evaluateDao.savaEvaluate(evaluate);*/
		
		//�����ճ�������Ϣ
		appraiseDailyBO.saveAppraiseDailyByBean(appraiseDailyBean);
		//����ר���ճ�������Ϣ
		appraiseDailySpecialBO.saveAppraiseDailyByBean(speicalBeans);
		// ������
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), subflow
				.getProcessInstanceId());
		workflowBo.completeTask(task.getId(), "toend");

		// ����״̬
		subflow.setProcessState(AcceptanceConstant.SUBCOMPLETE);
		subflowDao.save(subflow);

		// ������ʷ
		Apply apply = this.loadApply(subflow.getApplyId());
		String other = getOtherSubflowAllCompleted(subflowId);
		workflowBo
				.saveProcessHistory(userInfo, apply, task, "", "toend", "��������"
						+ "(" + getDeptName(subflow.getContractorId()) + ")",
						"");

		// �ж��������ǲ���ȫ������
		// ���ȫ���������������̽���
		if (StringUtils.isBlank(other)) {
			String processInstanceId = apply.getProcessInstanceId();
			workflowBo.signal(processInstanceId, "record", "toend");

			// ����״̬
			apply.setProcessState(AcceptanceConstant.ALLCOMPLETE);
			dao.save(apply);

			// ������ʷ
			workflowBo.saveProcessHistory(userInfo, apply, task, "", "toend",
					"�����̽���", "");
		}

		// �������ѣ���ʾ��ά�û����ս�ά���Ѿ�ͨ������
		String content = "�����ս�ά����λ���յ�����Ϊ\"" + apply.getName()
				+ "\"�������ѱ����ˣ�";
		List<String> mobileList = getMobileFromDeptId(subflow.getContractorId());
		List<String> mobiles = new ArrayList<String>();
		for (String mobile : mobileList) {
			mobiles.addAll(com.cabletech.commons.util.StringUtils.string2List(
					mobile, ","));
		}
		try{
		this.sendMessage(content, mobiles);
		}catch(Exception ex){
            logger.error("���Ͷ���ʧ��:",ex);
        }
	}

	public String getOtherSubflowAllCompleted(String subflowId) {
		List<Subflow> subflows = subflowDao
				.isOtherSubflowAllCompleted(subflowId);
		List<String> list = new ArrayList<String>();
		for (Subflow subflow : subflows) {
			list.add(subflow.getContractorId());
		}
		return StringUtils.join(list.iterator(), ",");
	}

	public boolean validateAllTaskChoosed(String id, Integer size) {
		Apply apply = loadApply(id);
		int number = 0;
		if (apply.getResourceType().equals(AcceptanceConstant.CABLE)) {
			number = apply.getCables().size();
		} else {
			number = apply.getPipes().size();
		}

		return number == size ? true : false;
	}

	public String getMobileFromUserId(String userId) {
		return dao.getMobileFromUserId(userId);
	}

	public List<String> getMobileFromDeptId(String deptId) {
		return dao.getMobileFromDeptId(deptId);
	}

	public List<Apply> getFinishedWork(UserInfo userInfo) {
		return dao.getFinishedWork(userInfo);
	}

	// �õ����칤��
	public List<Apply> getToDoWork(UserInfo userInfo, String taskName,
			String processName) {
		List<Apply> forDeptList = workflowBo.queryForHandleList(userInfo
				.getDeptID(), taskName, processName);
		List<Apply> forUseridList = workflowBo.queryForHandleList(userInfo
				.getUserID(), taskName, processName);
		forDeptList.addAll(forUseridList);
		return forDeptList;
	}

	public List<Apply> query(QueryBean queryBean) {
		return dao.query(queryBean);
	}

	public List<RepeaterSection> queryCable(QueryBean queryBean) {
		return trunkDao.queryFromAcceptance(queryBean);
	}

	public Map<String, String> getUsers() {
		Map<String, String> map = new HashMap<String, String>();
		List<Map> list = dao.getUsers();
		for (Map m : list) {
			map.put((String) m.get("userid"), (String) m.get("username"));
		}
		return map;
	}

//	public Apply saveState(String id, String state) {
//		Apply apply = dao.get(id);
//		apply.setProcessState(state);
//		dao.save(apply);
//		return apply;
//	}
	public void saveApply(Apply apply){
		dao.save(apply);
	}

	public Subflow getSubflow(String id) {
		return subflowDao.findUniqueByProperty("id", id);
	}

	public String getDeptName(String deptId) {
		return dao.getDeptName(deptId);
	}

	public List<RepeaterSection> getTrunks(String applyId) {
		Apply apply = loadApply(applyId);
		Set<ApplyCable> cables = apply.getCables();
		if (cables.isEmpty()) {
			return null;
		} else {
			List<RepeaterSection> list = new ArrayList<RepeaterSection>();
			for (ApplyCable cable : cables) {
				ApplyTask applyTask = applyTaskDao
						.getApplyTaskFromCableId(cable.getId());
				PayCable payCable = payCableDao.getPayCableFromTaskId(applyTask
						.getId());
				if (payCable != null) {
					RepeaterSection trunk = trunkDao.findUniqueByProperty(
							"kid", payCable.getCableId());
					list.add(trunk);
				}
			}
			return list.isEmpty() ? null : list;
		}
	}

	public List<Integer> queryForHandleAcceptanceNum(String condition,
			UserInfo userInfo) {
		List<Apply> applys = getToDoWork(userInfo, condition, "");
		List<Integer> list = new ArrayList<Integer>();

		int waitAllotTaskNum = 0;
		int waitClaimTaskNum = 0;
		int waitTaskApproveNum = 0;
		int waitRecordDataNum = 0;
		int waitRecordApproveNum = 0;
		int waitEvaluateNum = 0;

		if (applys != null && !applys.isEmpty()) {
			for (Apply apply : applys) {
				if (AcceptanceFlow.ALLOT_TASK.equals(apply.getFlowTaskName())) {
					waitAllotTaskNum++;
				}
				if (AcceptanceFlow.CLAIM_TASK.equals(apply.getFlowTaskName())) {
					waitClaimTaskNum++;
				}
				if (AcceptanceFlow.APPROVE_TASK.equals(apply.getFlowTaskName())) {
					waitTaskApproveNum++;
				}

				String subflowId = apply.getSubflowId();
				if (StringUtils.isNotBlank(subflowId)) {
					if (AcceptanceFlow.RECORD_DATA_TASK.equals(apply
							.getFlowTaskName())) {
						waitRecordDataNum++;
					}
					if (AcceptanceFlow.SUBAPPROVE.equals(apply
							.getFlowTaskName())) {
						waitRecordApproveNum++;
					}
					if (AcceptanceFlow.EXAM_TASK
							.equals(apply.getFlowTaskName())) {
						waitEvaluateNum++;
					}
				}
			}
		}

		list.add(waitAllotTaskNum);
		list.add(waitClaimTaskNum);
		list.add(waitTaskApproveNum);
		list.add(waitRecordDataNum);
		list.add(waitRecordApproveNum);
		list.add(waitEvaluateNum);

		return list;
	}

	@Override
	protected HibernateDao<Apply, String> getEntityDao() {
		return dao;
	}

	public void cancelAcceptance(ApplyBean applyBean, UserInfo userInfo) {
		// TODO Auto-generated method stub
		Apply apply = dao.get(applyBean.getId());
		dao.initObject(apply);
		apply.setCancelReason(applyBean.getCancelReason());
		apply.setCancelTime(new Date());
		apply.setCancelUserId(userInfo.getUserID());
		apply.setProcessState(Apply.CANCELED_STATE);
		dao.save(apply);
		String processInstanceId = "";
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processInstanceId = AcceptanceFlow.PROCESSING_NAME + "."
				+ apply.getId();
		processHistoryBean.initial(null, userInfo, processInstanceId,
				ModuleCatalog.INSPECTION);
		processHistoryBean.setHandledTaskName("any");
		processHistoryBean.setNextOperateUserId("");
		processHistoryBean.setObjectId(apply.getId());
		processHistoryBean.setProcessAction("���ս�ά����ȡ��");
		processHistoryBean.setTaskOutCome("cancel");
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
			workflowBo.endProcessInstance(processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	/**
	 * ȡ�øô�����ָ���Ĵ�ά
	 * @return
	 */
	public Object getdeptOptions4Apply(String applyName) {
		return dao.getDeptOptions4Apply(applyName);
	}
}
