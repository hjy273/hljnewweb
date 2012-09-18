package com.cabletech.linepatrol.remedy.service;

import java.util.List;

import com.cabletech.linepatrol.remedy.beans.StatRemedyBean;
import com.cabletech.linepatrol.remedy.dao.RemedyStatDao;

public class RemedyStatManager {
	private RemedyStatDao remedyStatDao = new RemedyStatDao();

	/**
	 * 通过区域查询统计已结算修缮信息
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
	 * 通过代维查询统计已结算修缮信息
	 * @param month
	 * @param cantractorId
	 * @param townId
	 * @return
	 */
	public List statRemedyByContractor(String month, String contractorId) {
		return remedyStatDao.statRemedyByContractor(month, contractorId);
	}

	/**
	 * 查询登陆用户所属区域下所有代维
	 * @param regoinid
	 * @return
	 */
	public List queryAllContractor(String regoinId) {
		return remedyStatDao.queryAllContractor(regoinId);
	}

	/**
	 * 查询登陆用户所属区域下所有区县
	 * @return
	 */
	public List queryAllTown(String regionId) {
		return remedyStatDao.queryAllTown(regionId);
	}

	public String queryContractorNameById(String contractorId){
		return remedyStatDao.queryContractorNameById(contractorId);
	}
	
}
