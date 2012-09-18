package com.cabletech.linepatrol.hiddanger.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.hiddanger.dao.HiddangerTrunkDao;
import com.cabletech.linepatrol.hiddanger.model.HiddangerTrunk;

@Service
public class HiddangerTrunkManager extends EntityManager<HiddangerTrunk, String>{
	
	@Resource(name="hiddangerTrunkDao")
	private HiddangerTrunkDao dao;
	
	public void saveTrunk(String hiddangerId, String trunkId){
		HiddangerTrunk trunk = new HiddangerTrunk();
		trunk.setHiddangerId(hiddangerId);
		trunk.setTrunkId(trunkId);
		dao.save(trunk);
	}
	
	public void deleteTrunk(String hiddangerId){
		String hql = "delete from HiddangerTrunk t where t.hiddangerId = ?";
		dao.batchExecute(hql, hiddangerId);
	}
	
	public String getTrunkFormHiddangerId(String hiddangerId){
		List<HiddangerTrunk> list = dao.findBy("hiddangerId", hiddangerId);
		String str = "";
		for(HiddangerTrunk trunk : list){
			String id = trunk.getTrunkId();
			if(str.equals("")){
				str = id;
			}else{
				str += "," + id;
			}
		}
		return str;
	}
	
	@Override
	protected HibernateDao<HiddangerTrunk, String> getEntityDao() {
		return dao;
	}
}
