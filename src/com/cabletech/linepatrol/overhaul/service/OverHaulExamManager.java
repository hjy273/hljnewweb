package com.cabletech.linepatrol.overhaul.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyDailyBO;
import com.cabletech.linepatrol.appraise.service.AppraiseDailySpecialBO;
import com.cabletech.linepatrol.overhaul.beans.OverHaulExamBean;
import com.cabletech.linepatrol.overhaul.dao.OverHaulApplyDao;
import com.cabletech.linepatrol.overhaul.dao.OverHaulExamDao;
import com.cabletech.linepatrol.overhaul.model.Constant;
import com.cabletech.linepatrol.overhaul.model.OverHaulApply;

/**
 * 大修项目日常考核业务类
 * 
 * @author liusq
 * 
 */
@Service
@Transactional
public class OverHaulExamManager extends EntityManager<OverHaulApply, String> {

	@Resource(name="overHaulExamDao")
	private OverHaulExamDao dao;
	
	@Resource(name="overHaulApplyDao")
	private OverHaulApplyDao overHaulApplyDao;
	
	@Autowired
	private AppraiseDailyDailyBO appraiseDailyBO;
	@Autowired
	private AppraiseDailySpecialBO appraiseDailySpecialBO;
	
	@Override
	protected HibernateDao<OverHaulApply, String> getEntityDao() {
		return dao;
	}

	/**
	 * 根据条件查询待考核的列表信息
	 * @param bean
	 * @return
	 */
	public List<DynaBean> getExamListByCondition(OverHaulExamBean bean, UserInfo userInfo) {
		return dao.getExamListByCondition(bean, userInfo);
	}
	
	/**
	 * 考核大修项目
	 * @param appraiseDailyBean
	 */
	public void examOverHaul(AppraiseDailyBean appraiseDailyBean,List<AppraiseDailyBean> speicalBeans) {
		//保存日常考核信息
		appraiseDailyBO.saveAppraiseDailyByBean(appraiseDailyBean);
		//保存专项日常考核信息
		appraiseDailySpecialBO.saveAppraiseDailyByBean(speicalBeans);
		//改变申请状态
		overHaulApplyDao.changeApplyStateById(appraiseDailyBean.getBusinessId(), Constant.EXAMED);
	}
}
