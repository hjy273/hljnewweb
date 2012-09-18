package com.cabletech.linepatrol.remedy.service;

import java.util.List;

import com.cabletech.linepatrol.remedy.beans.StatRemedyBean;
import com.cabletech.linepatrol.remedy.dao.RemedyStatDao;

public class RemedyStatManager {
	private RemedyStatDao remedyStatDao = new RemedyStatDao();

	/**
	 * ͨ�������ѯͳ���ѽ���������Ϣ
	 * @param month
	 * @param cantractorId
	 * @param townId
	 * @return
	 */
	public List statRemedyByTown(String month) {
		return remedyStatDao.statRemedyByTown(month);
	}

	public List statRemedyDetailByTown(String townId, String month) {
		return remedyStatDao.statRemedyDetailByTown(townId,month);
	}

	/**
	 * ͨ����ά��ѯͳ���ѽ���������Ϣ
	 * @param month
	 * @param cantractorId
	 * @param townId
	 * @return
	 */
	public List statRemedyByContractor(String month, String contractorId) {
		return remedyStatDao.statRemedyByContractor(month, contractorId);
	}

	/**
	 * ��ѯ��½�û��������������д�ά
	 * @param regoinid
	 * @return
	 */
	public List queryAllContractor(String regoinId) {
		return remedyStatDao.queryAllContractor(regoinId);
	}

	/**
	 * ��ѯ��½�û�������������������
	 * @return
	 */
	public List queryAllTown(String regionId) {
		return remedyStatDao.queryAllTown(regionId);
	}

	public String queryContractorNameById(String contractorId){
		return remedyStatDao.queryContractorNameById(contractorId);
	}
	
}
