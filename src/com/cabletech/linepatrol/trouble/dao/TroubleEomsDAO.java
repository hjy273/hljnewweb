package com.cabletech.linepatrol.trouble.dao;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.trouble.module.TroubleEoms;

@Repository
public class TroubleEomsDAO extends HibernateDao<TroubleEoms, String> {
	
	
	/**
	 * 根据故障id查询eoms单号
	 * @param troubleid 故障id
	 */ 
	public List<TroubleEoms> getEomsByTroubleId(String troubleid){
		String hql="  from TroubleEoms e where e.troubleId='"+troubleid+"'";
		return find(hql);
	}
	
	
	/**
	 * 根据故障id删除故障对应的eoms单号
	 * @param troubleid 故障id
	 */ 
	public void deleteByTroubleId(String troubleid){
		String sql=" delete from lp_trouble_eoms_code e where e.trouble_id='"+troubleid+"'";
		this.getJdbcTemplate().execute(sql);		
	}
	
	/**
	 * 保存eoms单号
	 * @param eoms
	 * @param troubleid
	 */
	public void saveEoms(String eoms,String troubleid){
		deleteByTroubleId(troubleid);
		if(StringUtils.isNotBlank(eoms)){
			List<String> list = Arrays.asList(eoms.split(","));
			TroubleEoms e = null;
			for(String s : list){
				e = new TroubleEoms();
				e.setEomsCode(s);
				e.setTroubleId(troubleid);
				save(e);
			}
		}
	}
	
}
