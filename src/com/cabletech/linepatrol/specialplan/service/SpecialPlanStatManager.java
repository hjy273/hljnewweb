package com.cabletech.linepatrol.specialplan.service;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.hiddanger.dao.HiddangerRegistDao;
import com.cabletech.linepatrol.hiddanger.model.HiddangerRegist;
import com.cabletech.linepatrol.specialplan.dao.AreaStatDao;
import com.cabletech.linepatrol.specialplan.dao.SublineStatDao;
import com.cabletech.linepatrol.specialplan.dao.WatchPlanStatDao;
import com.cabletech.linepatrol.specialplan.module.AreaStat;
import com.cabletech.linepatrol.specialplan.module.SublineStat;
import com.cabletech.linepatrol.specialplan.module.WatchPlanStat;

@Service
@Transactional
public class SpecialPlanStatManager extends EntityManager<HiddangerRegist, String>{
	
	@Resource(name="hiddangerRegistDao")
	private HiddangerRegistDao dao;
	@Resource(name="watchPlanStatDao")
	private WatchPlanStatDao watchPlanStatDao;
	@Resource(name="areaStatDao")
	private AreaStatDao areaStatDao;
	@Resource(name="sublineStatDao")
	private SublineStatDao sublineStatDao;
	
	public WatchPlanStat getWatchPlanStat(String id){
		WatchPlanStat watchPlanStat = watchPlanStatDao.getWatchPlanStatFromPlanId(id);
		if(watchPlanStat != null)
			watchPlanStat.setLeakRatio(sub(watchPlanStat.getPatrolRatio()));
		return watchPlanStat;
	}
	
	public List<WatchPlanStat> getPlansStat(String planIds){
		return watchPlanStatDao.getPlansStat(planIds);
	}
	
	public WatchPlanStat getWatchPlansStat(String id){
		WatchPlanStat watchPlanStat = new WatchPlanStat();
		
		List<String> planIds = Arrays.asList(id.split(","));
		for(String planId : planIds){
			WatchPlanStat addition = getWatchPlanStat(planId);
			if(addition != null)
				watchPlanStat = add(watchPlanStat, addition);
		}
		return watchPlanStat;
	}
	
	public WatchPlanStat add(WatchPlanStat base, WatchPlanStat addition){
		base.setAllPlanPointNumbers(base.getAllPlanPointNumbers() + addition.getPlanPointNumber() * addition.getPlanPointTimes());
		base.setAllFactPointNumbers(base.getAllFactPointNumbers() + addition.getFactPointNumber() * addition.getFactPointTimes());
		base.setPlanWatchNumber(base.getPlanWatchNumber() + addition.getPlanWatchNumber());
		base.setFactWatchNumber(base.getFactWatchNumber() + addition.getFactWatchNumber());
		base.setPlanWatchAreaNumber(base.getPlanWatchAreaNumber() + addition.getPlanWatchAreaNumber());
		base.setPlanSublineNumber(base.getPlanSublineNumber() + addition.getPlanSublineNumber());
		base.setNoPatrolSublineNumber(base.getNoPatrolSublineNumber() + addition.getNoPatrolSublineNumber());
		base.setNoCompleteSublineNumber(base.getNoCompleteSublineNumber() + addition.getNoCompleteSublineNumber());
		base.setCompleteSublineNumber(base.getCompleteSublineNumber() + addition.getCompleteSublineNumber());
		base.setNoWatchAreaNumber(base.getNoWatchAreaNumber() + addition.getNoWatchAreaNumber());
		base.setNoCompleteAreaNumber(base.getNoCompleteAreaNumber() + addition.getNoCompleteAreaNumber());
		base.setCompleteAreaNumber(base.getCompleteAreaNumber() + addition.getCompleteAreaNumber());
		
		base.setPlanPatrolMileage(add(base.getPlanPatrolMileage(), addition.getPlanPatrolMileage()));
		base.setFactPatrolMileage(add(base.getFactPatrolMileage(), addition.getFactPatrolMileage()));
		
		base.setWatchRatio(div(base.getFactWatchNumber(), base.getPlanWatchNumber()));
		base.setPatrolRatio(div(base.getAllFactPointNumbers(), base.getAllPlanPointNumbers()));
		base.setLeakRatio(sub(base.getPatrolRatio()));
		return base;
	}
	
	public String add(String base, String addition){
		double sum = 0;
		if(base != null){
			sum += Double.valueOf(base.replace(",", ""));
		}
		sum += Double.valueOf(addition.replace(",", ""));
		
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		return df.format(sum);
	}
	
	public String div(Integer fact, Integer plan){
		double f = Double.valueOf(fact);
		double p = Double.valueOf(plan);
		double result = 100 * f / p;
		
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		return df.format(result);
	}
	
	public String sub(String res){
		double f = Double.valueOf(res);
		double result = 100 -f;
		
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		return df.format(result);
	}
	
	public List<SublineStat> getSublineStat(String id){
		return sublineStatDao.findByProperty("specPlanId", id);
	}
	
	public List<Map> getPatrolDetail(String id){
		return sublineStatDao.getPatrolDetail(id);
	}
	
	public List<Map> getLeakDetail(String id){
		return sublineStatDao.getLeakDetail(id);
	}
	
	public List<AreaStat> getAreaStat1(String id){
		return areaStatDao.findByProperty("specPlanId", id);
	}
	
	public List<Map> getValidWatch(String id){
		return areaStatDao.getValidWatch(id);
	}
	
	public List<Map> getInvalidWatch(String id){
		return areaStatDao.getInvalidWatch(id);
	}
	
	@Override
	protected HibernateDao<HiddangerRegist, String> getEntityDao() {
		return dao;
	}
}
