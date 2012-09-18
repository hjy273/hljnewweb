package com.cabletech.linepatrol.safeguard.dao;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardSegment;

@Repository
public class SafeguardSegmentDao extends HibernateDao<SafeguardSegment, String> {

	/**
	 * 由保障计划ID查询所涉及到的中继段
	 * 
	 * @param planId：保障计划ID
	 * @return
	 */
	public String getSublineIds(String planId) {
		List list = this.findByProperty("planId", planId);
		String sublineIds = "";
		if (list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				sublineIds += ((SafeguardSegment) iterator.next()).getSegmentId() + ",";
			}
			sublineIds = sublineIds.substring(0, sublineIds.length() - 1);
		}
		logger.info("*************sublineIds:" + sublineIds);
		return sublineIds;
	}
}
