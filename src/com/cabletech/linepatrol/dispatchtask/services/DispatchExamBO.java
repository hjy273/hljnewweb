package com.cabletech.linepatrol.dispatchtask.services;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyDailyBO;
import com.cabletech.linepatrol.appraise.service.AppraiseDailySpecialBO;
import com.cabletech.linepatrol.dispatchtask.beans.DispatchExamBean;
import com.cabletech.linepatrol.dispatchtask.dao.CheckTaskDao;
import com.cabletech.linepatrol.dispatchtask.dao.DispatchExamDao;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTask;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;

/**
 * 任务派单日常考核业务类
 * 
 * @author liusq
 * 
 */
@Service
@Transactional
public class DispatchExamBO extends EntityManager<DispatchTask, String> {

	@Resource(name = "dispatchExamDao")
	private DispatchExamDao dao;
	
	@Resource(name = "checkTaskDao")
	private CheckTaskDao checkTaskDao;
	
	@Autowired
	private AppraiseDailyDailyBO appraiseDailyBO;
	
	@Autowired
	private AppraiseDailySpecialBO appraiseDailySpecialBO;

	@Override
	protected HibernateDao<DispatchTask, String> getEntityDao() {
		return dao;
	}
	
	/**
	 * 通过查询条件查询未考核、已考核的列表
	 * @return
	 */
	public List<DynaBean> getExamListByCondition(DispatchExamBean bean, UserInfo userInfo, String examFlag) {
		List<DynaBean> examList = null;
		if(StringUtils.equals("unexam", examFlag)){
			examList = dao.getExamListByCondition(bean, userInfo, DispatchTaskConstant.UNEXAM);
		}else{
			examList = dao.getExamListByCondition(bean, userInfo, DispatchTaskConstant.EXAMED);
		}
		return examList;
	}
	
	/**
	 * 派单日常考核
	 * @param appraiseDailyBean
	 */
	public void examDispatch(AppraiseDailyBean appraiseDailyBean,List<AppraiseDailyBean> speicalBeans) {
		// 保存日常考核信息
		appraiseDailyBO.saveAppraiseDailyByBean(appraiseDailyBean);
		//保存专项日常考核信息
		appraiseDailySpecialBO.saveAppraiseDailyByBean(speicalBeans);
		// 修改是否考核状态
		checkTaskDao.updateExamFlagByReplyIdAndFinishState(appraiseDailyBean
				.getBusinessId(), DispatchTaskConstant.PASSED);
	}
}
