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
 * �����ɵ��ճ�����ҵ����
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
	 * ͨ����ѯ������ѯδ���ˡ��ѿ��˵��б�
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
	 * �ɵ��ճ�����
	 * @param appraiseDailyBean
	 */
	public void examDispatch(AppraiseDailyBean appraiseDailyBean,List<AppraiseDailyBean> speicalBeans) {
		// �����ճ�������Ϣ
		appraiseDailyBO.saveAppraiseDailyByBean(appraiseDailyBean);
		//����ר���ճ�������Ϣ
		appraiseDailySpecialBO.saveAppraiseDailyByBean(speicalBeans);
		// �޸��Ƿ񿼺�״̬
		checkTaskDao.updateExamFlagByReplyIdAndFinishState(appraiseDailyBean
				.getBusinessId(), DispatchTaskConstant.PASSED);
	}
}
