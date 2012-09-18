package com.cabletech.linepatrol.cut.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.cut.module.CutAcceptance;

/**
 * ������ղ�����
 * @author Administrator
 *
 */
@Repository
public class CutAcceptanceDao extends HibernateDao<CutAcceptance, String> {
	
	/**
	 * ���δͨ��������մ���
	 * @param cutAcceptanceId
	 * @return
	 */
	public int getUnapproveNumberByCutAcceptanceId(String cutAcceptanceId){
		return this.get(cutAcceptanceId).getUnapproveNumber();
	}
	
	/**
	 * �������ս�����Ϣ
	 * @param cutAcceptance�����ս���ʵ��
	 * @return
	 */
	public CutAcceptance saveCutAcceptance(CutAcceptance cutAcceptance){
		this.save(cutAcceptance);
		this.initObject(cutAcceptance);
		return cutAcceptance;
	}
	
	/**
	 * �������ս���δͨ������
	 * @param acceptanceId:���ս���ID
	 */
	public void setUnapproveNumberByAcceptanceId(String acceptanceId){
		CutAcceptance cutAcceptance = this.findByUnique("id", acceptanceId);
		int unapproveNumber = cutAcceptance.getUnapproveNumber();
		cutAcceptance.setUnapproveNumber(++unapproveNumber);
		this.save(cutAcceptance);
	}
	
}
