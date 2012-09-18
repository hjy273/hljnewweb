package com.cabletech.linepatrol.acceptance.service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.acceptance.beans.PipesBean;
import com.cabletech.linepatrol.acceptance.dao.AcceptancePipeStatDao;
import com.cabletech.linepatrol.acceptance.model.Apply;

/**
 * ���ս�ά�ܵ�ͳ��ҵ����
 * @author liusq
 *
 */

@Service
@Transactional
public class AcceptancePipeStatManager extends EntityManager<Apply, String> {

	@Resource(name = "acceptancePipeStatDao")
	private AcceptancePipeStatDao dao;
	
	@Override
	protected HibernateDao<Apply, String> getEntityDao() {
		return dao;
	}

	/**
	 * �ܵ�ͳ���б�
	 * @param bean
	 * @return
	 */
	public List<Object> getPipeStatList(PipesBean bean){
		//ͳ�ƽ����Ϣ
		List<Object> backInfo = null;
		//��ð�������ѯ������
		List<DynaBean> applyList = dao.getStatPipeList(bean);
		if(applyList != null && applyList.size() > 0){
			backInfo = new ArrayList<Object>();
			List<Object> totalList = new ArrayList<Object>();
			//������
			String ids = dao.listToString(applyList);
			
			//��ȡ���е�����
			int pipeNumAll = applyList.size();
			//���ͨ��������
			int pipeNumPassed = dao.getStatPipeNum(ids);
			
			//��������
			int ditchNumAll = dao.getPipeLength("sum(pi.pipe_length_0)", ids, "");
			//��ͨ����
			int ditchNumPassed = dao.getPipeLength("sum(pi.pipe_length_0)", ids, " and pi.ispassed='1'");
			//��������
			int holeNumAll = dao.getPipeLength("sum(pi.pipe_length_1)", ids, "");
			//��ͨ����
			int holeNumPassed = dao.getPipeLength("sum(pi.pipe_length_1)", ids, " and pi.ispassed='1'");
			//��ʽ��������
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(2);
			//�����Ϣ
			totalList.add(pipeNumAll);//��Ŀ����
			totalList.add(pipeNumPassed);//ͨ����Ŀ��
			totalList.add(nf.format(100.0 * pipeNumPassed / pipeNumAll));//��Ŀͨ����
			totalList.add(ditchNumAll);//���ܹ�����
			totalList.add(holeNumAll);//���ܹ�����
			totalList.add(nf.format(100.0 * ditchNumPassed / ditchNumAll));//������ͨ����
			totalList.add(nf.format(100.0 * holeNumPassed / holeNumAll));//�׹���ͨ����
			//���ͳ�Ƶ���ϸ��Ϣ
			List<Object> detailList = dao.getDetailListInfo(ids,bean);
			backInfo.add(totalList);
			backInfo.add(detailList);
		}
		return backInfo;
	}
}
