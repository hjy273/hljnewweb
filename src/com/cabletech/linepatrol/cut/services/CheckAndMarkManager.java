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
 * 考核评分
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
	 * 获得割接评分列表
	 * @return
	 */
	public List getCheckAndMarkList(){
		List list = cutDao.getCheckAndMarkList();
		return list;
	}
	
	/**
	 * 由割接ID获得割接评分前加载数据
	 * @param cutId：割接ID
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
	 * 割接评分
	 * @param score：分数
	 * @param remark：评论
	 * @param userInfo
	 * @param cutId：割接ID
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
		
		//保存日常考核信息
		appraiseDailyBO.saveAppraiseDailyByBean(appraiseDailyBean);
		//保存专项日常考核信息
		appraiseDailySpecialBO.saveAppraiseDailyByBean(speicalBeans);
		cutDao.updateStateById(cutId, Cut.FINISH);
		
		Cut cut = cutDao.findByUnique("id", cutId);
		String proposer = cut.getProposer();
		String phone = getPhoneByUserId(proposer);

		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), cutId);
		if (task != null
				&& CutWorkflowBO.EVALUATE_TASK.equals(task.getName())) {
			System.out.println("割接待考核：" + task.getName());
			workflowBo.completeTask(task.getId(), "end");
			System.out.println("割接已经考核！");
			
			//保存流程历史
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.setProcessAction("割接评分");//添加流程处理动作说明
			processHistoryBean.setTaskOutCome("end");//添加工作流流向信息
			processHistoryBean.initial(task, userInfo, "",ModuleCatalog.LINECUT);
			processHistoryBean.setNextOperateUserId("");//添加下一步处理人的编号
			processHistoryBean.setObjectId(cutId);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		String content = "【线路割接】贵单位的名称为\"" + cut.getCutName() + "\"的割接已被考核!";
		sendMessage(content, phone);
		
		saveMessage(content, phone, cutId, CutConstant.LP_CUT, ModuleCatalog.LINECUT);
	}
	
	/**
	 * 发送短信
	 * 
	 * @param content：短信内容
	 * @param mobiles：接收短信手机号码
	 */
	public void sendMessage(String content, String mobiles) {
		if(mobiles != null && !"".equals(mobiles)){
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			super.sendMessage(content, mobileList);
		}
	}

	/**
	 * 保存短信记录
	 * 
	 * @param content：短信内容
	 * @param mobiles：接收短信手机号码
	 * @param entityId：实体ID
	 * @param entityType：实体类型
	 * @param entityModule：实体模型
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
	 * 通过用户ID查询用户的手机号码
	 * 
	 * @param userId：用户ID
	 * @return：返回用户手机号码
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
