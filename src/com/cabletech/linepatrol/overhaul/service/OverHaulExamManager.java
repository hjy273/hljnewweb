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
 * ������Ŀ�ճ�����ҵ����
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
	 * ����������ѯ�����˵��б���Ϣ
	 * @param bean
	 * @return
	 */
	public List<DynaBean> getExamListByCondition(OverHaulExamBean bean, UserInfo userInfo) {
		return dao.getExamListByCondition(bean, userInfo);
	}
	
	/**
	 * ���˴�����Ŀ
	 * @param appraiseDailyBean
	 */
	public void examOverHaul(AppraiseDailyBean appraiseDailyBean,List<AppraiseDailyBean> speicalBeans) {
		//�����ճ�������Ϣ
		appraiseDailyBO.saveAppraiseDailyByBean(appraiseDailyBean);
		//����ר���ճ�������Ϣ
		appraiseDailySpecialBO.saveAppraiseDailyByBean(speicalBeans);
		//�ı�����״̬
		overHaulApplyDao.changeApplyStateById(appraiseDailyBean.getBusinessId(), Constant.EXAMED);
	}
}
