package com.cabletech.linepatrol.cut.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyDailyBO;
import com.cabletech.linepatrol.appraise.service.AppraiseDailySpecialBO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.cut.dao.CutAcceptanceDao;
import com.cabletech.linepatrol.cut.dao.CutDao;
import com.cabletech.linepatrol.cut.dao.CutFeedbackDao;
import com.cabletech.linepatrol.cut.dao.CutHopRelDao;
import com.cabletech.linepatrol.cut.module.Cut;
import com.cabletech.linepatrol.cut.module.CutAcceptance;
import com.cabletech.linepatrol.cut.module.CutConstant;
import com.cabletech.linepatrol.cut.module.CutFeedback;
import com.cabletech.linepatrol.cut.workflow.CutWorkflowBO;

/**
 * ��������
 * 
 * @author liusq
 * 
 */
@Service
@Transactional
public class CheckAndMarkManager extends EntityManager<Cut,String> {

	
	@Autowired
	private CutWorkflowBO workflowBo;
	
	@Resource(name="cutDao")
	private CutDao cutDao;
	
	@Resource(name="cutFeedbackDao")
	private CutFeedbackDao cutFeedbackDao;
	
	@Resource(name="cutAcceptanceDao")
	private CutAcceptanceDao cutAcceptanceDao;
	
	@Resource(name="cutHopRelDao")
	private CutHopRelDao cutHopRelDao;
	
	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;
	
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;

	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	
	@Autowired
	private AppraiseDailyDailyBO appraiseDailyBO;
	@Autowired
	private AppraiseDailySpecialBO appraiseDailySpecialBO;
	
	@Override
	protected HibernateDao<Cut, String> getEntityDao() {
		return cutDao;
	}
	
	/**
	 * ��ø�������б�
	 * @return
	 */
	public List getCheckAndMarkList(){
		List list = cutDao.getCheckAndMarkList();
		return list;
	}
	
	/**
	 * �ɸ��ID��ø������ǰ��������
	 * @param cutId�����ID
	 * @return
	 */
	public Map viewApplyData(String cutId) throws ServiceException, Exception {
		Cut cut = cutDao.get(cutId);
		cutDao.initObject(cut);
		CutFeedback cutFeedback = cutFeedbackDao.findByUnique("cutId", cutId);
		CutAcceptance cutAcceptance = cutAcceptanceDao.findByUnique("cutId", cutId);
		String sublineIds = cutHopRelDao.getSublineIds(cutId);
		String condition = "and approve.object_type='LP_CUT' and approve.object_id='" + cutId + "'";
		List approve_info_list = cutDao.queryApproveList(condition);
		String contractorId = userInfoDao.getDeptIdByUserId(cut.getProposer());
		Map map = new HashMap();
		map.put("cut", cut);
		map.put("cutFeedback", cutFeedback);
		map.put("cutAcceptance", cutAcceptance);
		map.put("sublineIds", sublineIds);
		map.put("approve_info_list", approve_info_list);
		map.put("contractorId", contractorId);
		return map;
	}
	
	/**
	 * �������
	 * @param score������
	 * @param remark������
	 * @param userInfo
	 * @param cutId�����ID
	 * @throws ServiceException
	 */
	public void checkAndMark(UserInfo userInfo, String cutId,AppraiseDailyBean appraiseDailyBean,List<AppraiseDailyBean> speicalBeans) throws ServiceException {
		/*Evaluate evaluate = new Evaluate();
		evaluate.setEntityScore(score);
		evaluate.setEntityRemark(remark);
		evaluate.setEvaluator(userInfo.getUserID());
		evaluate.setEntityType(CutConstant.LP_EVALUATE_CUT);
		evaluate.setEntityId(cutId);
		evaluateDao.savaEvaluate(evaluate);*/
		
		//�����ճ�������Ϣ
		appraiseDailyBO.saveAppraiseDailyByBean(appraiseDailyBean);
		//����ר���ճ�������Ϣ
		appraiseDailySpecialBO.saveAppraiseDailyByBean(speicalBeans);
		cutDao.updateStateById(cutId, Cut.FINISH);
		
		Cut cut = cutDao.findByUnique("id", cutId);
		String proposer = cut.getProposer();
		String phone = getPhoneByUserId(proposer);

		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), cutId);
		if (task != null
				&& CutWorkflowBO.EVALUATE_TASK.equals(task.getName())) {
			System.out.println("��Ӵ����ˣ�" + task.getName());
			workflowBo.completeTask(task.getId(), "end");
			System.out.println("����Ѿ����ˣ�");
			
			//����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.setProcessAction("�������");//������̴�����˵��
			processHistoryBean.setTaskOutCome("end");//��ӹ�����������Ϣ
			processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
			processHistoryBean.setNextOperateUserId("");//�����һ�������˵ı��
			processHistoryBean.setObjectId(cutId);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		String content = "����·��ӡ���λ������Ϊ\"" + cut.getCutName() + "\"�ĸ���ѱ�����!";
		sendMessage(content, phone);
		
		saveMessage(content, phone, cutId, CutConstant.LP_CUT, ModuleCatalog.LINECUT);
	}
	
	/**
	 * ���Ͷ���
	 * 
	 * @param content����������
	 * @param mobiles�����ն����ֻ�����
	 */
	public void sendMessage(String content, String mobiles) {
		if(mobiles != null && !"".equals(mobiles)){
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			super.sendMessage(content, mobileList);
		}
	}

	/**
	 * ������ż�¼
	 * 
	 * @param content����������
	 * @param mobiles�����ն����ֻ�����
	 * @param entityId��ʵ��ID
	 * @param entityType��ʵ������
	 * @param entityModule��ʵ��ģ��
	 */
	public void saveMessage(String content, String mobiles, String entityId,
			String entityType, String entityModule) {
		if(mobiles != null && !"".equals(mobiles)){
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			for (String mobile : mobileList) {
				SMHistory history = new SMHistory();
				history.setSimIds(mobile);
				history.setSendContent(content);
				history.setSendTime(new Date());
				history.setSmType(entityType);
				history.setObjectId(entityId);
				history.setBusinessModule(entityModule);
				historyDAO.save(history);
			}
		}
	}

	/**
	 * ͨ���û�ID��ѯ�û����ֻ�����
	 * 
	 * @param userId���û�ID
	 * @return�������û��ֻ�����
	 */
	public String getPhoneByUserId(String userId) {
		String hql = "from UserInfo userInfo where userInfo.userID=?";
		List list = cutFeedbackDao.getHibernateTemplate().find(hql, userId);
		if (list != null && !list.equals("")) {
			UserInfo userInfo = (UserInfo) list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}
}
