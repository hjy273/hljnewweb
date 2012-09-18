package com.cabletech.linepatrol.project.service;

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
import com.cabletech.linepatrol.project.beans.RemedyExamBean;
import com.cabletech.linepatrol.project.constant.RemedyConstant;
import com.cabletech.linepatrol.project.dao.RemedyApplyDao;
import com.cabletech.linepatrol.project.dao.RemedyExamDao;
import com.cabletech.linepatrol.project.domain.ProjectRemedyApply;

/**
 * ���������ճ�����ҵ����
 * @author liusq
 *
 */
@Service
@Transactional
public class RemedyExamManager extends EntityManager<ProjectRemedyApply, String> {

	@Resource(name = "remedyExamDao")
	private RemedyExamDao dao;
	
	@Resource(name = "remedyApplyDao")
	private RemedyApplyDao remedyApplyDao;
	
	@Autowired
	private AppraiseDailyDailyBO appraiseDailyBO;
	@Autowired
	private AppraiseDailySpecialBO appraiseDailySpecialBO;
	
	@Override
	protected HibernateDao<ProjectRemedyApply, String> getEntityDao() {
		return dao;
	}

	/**
	 * ��ô������б�
	 * @param bean
	 * @param userInfo
	 * @return
	 */
	public List<DynaBean> getExamListByCondition(RemedyExamBean bean, UserInfo userInfo) {
		return dao.getExamListByCondition(bean, userInfo);
	}
	
	/**
	 * ���˹������뵥
	 * @param appraiseDailyBean
	 */
	public void examRemedy(AppraiseDailyBean appraiseDailyBean,List<AppraiseDailyBean> speicalBeans) {
		//�����ճ�������Ϣ
		appraiseDailyBO.saveAppraiseDailyByBean(appraiseDailyBean);
		//����ר���ճ�������Ϣ
		appraiseDailySpecialBO.saveAppraiseDailyByBean(speicalBeans);
		//�ı�״̬Ϊ�ѿ���
		remedyApplyDao.changeStateById(appraiseDailyBean.getBusinessId(), RemedyConstant.FINISH);
	}
}
