package com.cabletech.linepatrol.resource.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.resource.dao.RepeaterSectionDao;
/**
 * 计算各代维公司光缆维护长度
 * @author zhj
 *
 */
@Service
public class CableLengthCalcn  extends EntityManager{
	
	@Override
	protected HibernateDao getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}
	@Resource(name="repeaterSectionDao")
	private RepeaterSectionDao rsDao;
	/**
	 * 计算相应级别代维公司维护长度
	 * @param cableLevel  光缆级别
	 * @param finishtime  交维时间
	 * @param regionid	 登陆用户区域ID
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<String,Double> gradeCableLength(String cableLevel,String finishtime,String regionid){
		Map<String,Double> conCableLen = new HashMap<String,Double>();
		double len = 0.00;
		List<String> contractors = rsDao.getJdbcTemplate().queryForList("select contractorid from contractorinfo where state is null", String.class);
		for(String contractorid:contractors){
			len = rsDao.getMaintenaceLength(cableLevel,contractorid,regionid,finishtime);
			conCableLen.put(contractorid,  len);
		}
		return conCableLen;
	}

}
