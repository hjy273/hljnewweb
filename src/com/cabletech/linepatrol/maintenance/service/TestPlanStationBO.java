package com.cabletech.linepatrol.maintenance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.dao.UserInfoDAOImpl;
import com.cabletech.baseinfo.domainobjects.Point;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.maintenance.beans.TestPlanBean;
import com.cabletech.linepatrol.maintenance.dao.TestPlanDAO;
import com.cabletech.linepatrol.maintenance.dao.TestPlanLineDAO;
import com.cabletech.linepatrol.maintenance.dao.TestPlanStationDAO;
import com.cabletech.linepatrol.maintenance.module.TestPlan;
import com.cabletech.linepatrol.maintenance.module.TestPlanLine;
import com.cabletech.linepatrol.maintenance.module.TestPlanStation;
import com.cabletech.linepatrol.resource.dao.RepeaterSectionDao;
import com.cabletech.linepatrol.resource.model.RepeaterSection;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;
@Service
@Transactional
public class TestPlanStationBO extends EntityManager<TestPlanStation,String> {


	@Resource(name="testPlanStationDAO")
	private TestPlanStationDAO dao;
	
	
	
	public TestPlanStation getTestPlanStationById(String id){
		return dao.getTestPlanStationById(id);
	}
	
	

	@Override
	protected HibernateDao<TestPlanStation, String> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
