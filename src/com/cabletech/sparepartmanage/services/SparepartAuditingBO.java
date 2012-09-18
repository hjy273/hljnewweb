package com.cabletech.sparepartmanage.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.generatorID.GeneratorID;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.sparepartmanage.beans.SparepartAuditingBean;
import com.cabletech.sparepartmanage.dao.SparepartAuditingDAOImpl;
import com.cabletech.sparepartmanage.domainobjects.SparepartApplyF;
import com.cabletech.sparepartmanage.domainobjects.SparepartApplyS;
import com.cabletech.sparepartmanage.domainobjects.SparepartAuditing;
import com.cabletech.sparepartmanage.domainobjects.SparepartConstant;

/**
 * SparepartStorageAuditingBO ����������˵�ҵ������������������ˡ��鿴��˼�¼
 * 
 * ע�⣺�ڱ����������ͨ��ʱ���б��������޸ģ�����
 */
public class SparepartAuditingBO extends BaseBisinessObject {
	SparepartAuditingDAOImpl dao = new SparepartAuditingDAOImpl();

	private GeneratorID generatorID = new OracleIDImpl();

	SparepartStorageBO sbo = new SparepartStorageBO();

	private SparepartApplyBO applybo = new SparepartApplyBO();

	/**
	 * ����������ѯ�����������˼�¼
	 * 
	 * @param condition
	 *            String
	 * @return List
	 * @throws Exception
	 */
	public List list(String condition) throws Exception {
		List list = new ArrayList();
		String sql = "select auditing.auditing_person,auditing.auditing_result,auditing.auditing_opnion,auditing.auditing_remark, ";
		sql += "to_char(auditing.auditing_date,'yyyy-mm-dd hh24:mi:ss') AS auditing_date ";
		sql += "from spare_part_auditingapply auditing ";
		sql += " where 1=1 ";
		sql += condition;
		QueryUtil util = new QueryUtil();
		list = util.queryBeans(sql);
		return list;
	}

	
	/**
	 *���� �������������Ϣ
	 */	
	public void addAuditngInfo(SparepartAuditingBean bean){
		SparepartAuditing au = new SparepartAuditing();
		try {
			BeanUtil.objectCopy(bean,au);
			String tid = generatorID.getSeq("auditingapply",30);
			au.setTid(tid);
			au.setAuditingDate(new Date());
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		dao.add(au);
	}

	/**
	 * �����������
	 */
	public boolean updateAuditngState(String applyfid,SparepartAuditingBean bean,String sparepartid) throws Exception{
		String state = bean.getAuditingResult();
		List applySs = applybo.getApplySs(applyfid);
		SparepartApplyF applyf = applybo.getApplyF(applyfid);
		String useMode = applyf.getUseMode();
		List serials = new ArrayList();
		List usedSerials = new ArrayList();		
		for(int i = 0;i<applySs.size();i++){
			SparepartApplyS applys = (SparepartApplyS) applySs.get(i);
			usedSerials.add(applys.getUsedSerialNumber());
			serials.add(applys.getSerialNumber());
		}		
		dao.updateAuditngState(applyfid,state);
		if (SparepartConstant.AUDITING_PASSED.equals(state)) {//���ͨ��
			dao.updateSparepartState(serials,applyf.getApplyUsePosition(),SparepartConstant.IS_RUNNING);
			if (SparepartConstant.USE_UPDATE.equals(useMode)) {//����ʹ��
				String stateUsed="";
				String replaceType = applyf.getReplaceType();
				if(replaceType.equals(SparepartConstant.REPLACE_NO_ACT)){//�˻�
					stateUsed = SparepartConstant.MOBILE_WAIT_USE;
					dao.updateUsedSparepartState(usedSerials,applyf.getUsedPosition(),stateUsed);
				}else if(replaceType.equals(SparepartConstant.REPLACE_MEND)){//����
					stateUsed = SparepartConstant.IS_MENDING;
					dao.updateUsedSparepartStateRepair(usedSerials,applyf.getUsedPosition(),stateUsed);
				}else if(replaceType.equals(SparepartConstant.REPLACE_DISCARD)){//����
					stateUsed = SparepartConstant.IS_DISCARDED;
					dao.updateSparepartState(usedSerials,SparepartConstant.IS_DISCARDED);
				}
			}
			return true;
		}else{//���δͨ��
			dao.updateSparepartState(serials,SparepartConstant.CONTRACTOR_WAIT_USE);
			if(usedSerials!=null && usedSerials.size()>0){
				dao.updateSparepartState(usedSerials,SparepartConstant.IS_RUNNING);
			}
			return true;

		} 
	}

	
}
