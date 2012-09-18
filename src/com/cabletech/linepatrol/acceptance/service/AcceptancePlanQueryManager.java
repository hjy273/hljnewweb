package com.cabletech.linepatrol.acceptance.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.acceptance.beans.ApplyBean;
import com.cabletech.linepatrol.acceptance.dao.AcceptancePlanQueryDao;
import com.cabletech.linepatrol.acceptance.dao.ApplyCableDao;
import com.cabletech.linepatrol.acceptance.dao.ApplyDao;
import com.cabletech.linepatrol.acceptance.dao.ApplyPipeDao;
import com.cabletech.linepatrol.acceptance.dao.PayCableDao;
import com.cabletech.linepatrol.acceptance.dao.PayPipeDao;
import com.cabletech.linepatrol.acceptance.model.Apply;
import com.cabletech.linepatrol.acceptance.model.ApplyCable;
import com.cabletech.linepatrol.acceptance.model.ApplyPipe;

/**
 * ���ս�ά�ܵ�ͳ��ҵ����
 * 
 * @author liusq
 * 
 */

@Service
@Transactional
public class AcceptancePlanQueryManager extends EntityManager<Apply, String> {

	@Resource(name = "acceptancePlanQueryDao")
	private AcceptancePlanQueryDao dao;
	
	@Resource(name = "applyDao")
	private ApplyDao applyDao;
	
	@Resource(name = "applyCableDao")
	private ApplyCableDao applyCableDao;
	
	@Resource(name = "applyPipeDao")
	private ApplyPipeDao applyPipeDao;

	@Override
	protected HibernateDao<Apply, String> getEntityDao() {
		return dao;
	}

	/**
	 * ���ս�ά��ѯͳ�ƽ��
	 * 
	 * @param bean
	 * @return
	 */
	public List<DynaBean> queryAcceptancePlanResult(ApplyBean bean, UserInfo userInfo) {
		// ���ս�ά��ѯ���ͳ���б�
		List<DynaBean> backInfo = dao.queryAcceptancePlan(bean, userInfo);
		return backInfo;
	}
	
	// ��������
	public void saveApply(ApplyBean applyBean, Apply apply) {
		// ��������
		if (apply.getResourceType().equals(AcceptanceConstant.CABLE)) {
			setApplyCable(apply);
		} else {
			setApplyPipe(apply);
		}
		apply.setName(applyBean.getName());
		//apply.setResourceType(applyBean.getResourceType());
		applyDao.save(apply);
	}
	
	// ���ù�����Ϣ
	public void setApplyCable(Apply apply) {
		Set<ApplyCable> set = apply.getCables();
		for (ApplyCable cable : set) {
			//cable.setId(null);
			cable.setIsrecord(AcceptanceConstant.NOTRECORD);
			cable.setIspassed(AcceptanceConstant.NOTPASSED);
			cable.setApply(apply);
		}
	}

	// ���ùܵ���Ϣ
	public void setApplyPipe(Apply apply) {
		Set<ApplyPipe> set = apply.getPipes();
		for (ApplyPipe pipe : set) {
			//pipe.setId(null);
			pipe.setIsrecord(AcceptanceConstant.NOTRECORD);
			pipe.setIspassed(AcceptanceConstant.NOTPASSED);
			pipe.setApply(apply);
		}
	}
	
	/**
	 * ɾ��������Ϣ
	 * 
	 * @param apply
	 * @param cableId
	 * @return
	 */
	public Apply deleteCable(Apply apply, String cableId) {
		apply.removeCable(cableId);
		applyCableDao.delete(cableId);
		return apply;
	}

	/**
	 * ɾ���ܵ���Ϣ
	 * 
	 * @param apply
	 * @param pipeId
	 * @return
	 */
	public Apply deletePipe(Apply apply, String pipeId) {
		apply.removePipe(pipeId);
		applyPipeDao.delete(pipeId);
		return apply;
	}
}
