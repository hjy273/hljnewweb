package com.cabletech.linepatrol.trouble.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.trouble.module.TroubleProcess;
import com.cabletech.linepatrol.trouble.module.TroubleProcesser;

@Repository
public class TroubleProcessDAO extends HibernateDao<TroubleProcess, String> {
	
	/**
	 * 根据故障id删除处理过程信息
	 * @param processer
	 * @param delAll 是y 表示可以删除手持设备发送上来的，否则反之
	 */
	public void deleteProcess(String troubleid,String delAll){
		String sql=" delete LP_TROUBLE_PROCESS tp where tp.trouble_id='"+troubleid+"'";
		if(delAll.equals("n")){
			sql+=" and tp.start_time_ref is null";
		}
		this.getJdbcTemplate().execute(sql);
	}
	
	
	public TroubleProcess getProcessById(String id){
		TroubleProcess process = this.get(id);
		this.initObject(process);
		return process;
	}
	
}
