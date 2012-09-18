package com.cabletech.linepatrol.trouble.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.trouble.module.TroubleProcess;
import com.cabletech.linepatrol.trouble.module.TroubleProcesser;

@Repository
public class TroubleProcessDAO extends HibernateDao<TroubleProcess, String> {
	
	/**
	 * ���ݹ���idɾ�����������Ϣ
	 * @param processer
	 * @param delAll ��y ��ʾ����ɾ���ֳ��豸���������ģ�����֮
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
