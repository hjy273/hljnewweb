package com.cabletech.linepatrol.appraise.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.cabletech.commons.util.VelocityUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.dao.AppraiseResultDao;
import com.cabletech.linepatrol.appraise.module.AppraiseResult;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
@Service
public class AppraiseGradeBO extends EntityManager<AppraiseResult, String> {
	@Resource(name="appraiseResultDao")
	private AppraiseResultDao appraiseResultDao;
	@Override
	protected HibernateDao<AppraiseResult, String> getEntityDao() {
		// TODO Auto-generated method stub
		return appraiseResultDao;
	}
}
