package com.cabletech.linepatrol.cut.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.cut.module.CutAcceptance;

/**
 * 割接验收操作类
 * @author Administrator
 *
 */
@Repository
public class CutAcceptanceDao extends HibernateDao<CutAcceptance, String> {
	
	/**
	 * 获得未通过割接验收次数
	 * @param cutAcceptanceId
	 * @return
	 */
	public int getUnapproveNumberByCutAcceptanceId(String cutAcceptanceId){
		return this.get(cutAcceptanceId).getUnapproveNumber();
	}
	
	/**
	 * 保存验收结算信息
	 * @param cutAcceptance：验收结算实体
	 * @return
	 */
	public CutAcceptance saveCutAcceptance(CutAcceptance cutAcceptance){
		this.save(cutAcceptance);
		this.initObject(cutAcceptance);
		return cutAcceptance;
	}
	
	/**
	 * 设置验收结算未通过次数
	 * @param acceptanceId:验收结算ID
	 */
	public void setUnapproveNumberByAcceptanceId(String acceptanceId){
		CutAcceptance cutAcceptance = this.findByUnique("id", acceptanceId);
		int unapproveNumber = cutAcceptance.getUnapproveNumber();
		cutAcceptance.setUnapproveNumber(++unapproveNumber);
		this.save(cutAcceptance);
	}
	
}
