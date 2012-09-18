package com.cabletech.linepatrol.project.service;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.*;
import com.cabletech.linepatrol.project.dao.CountyInfoDao;
import com.cabletech.linepatrol.project.domain.ProjectCountyInfo;
import com.cabletech.linepatrol.project.beans.CountyInfoBean;
import com.cabletech.linepatrol.project.service.ConditionGenerate;

@Service
@Transactional
public class CountyInfoBO extends BaseBisinessObject {
	@Resource(name = "countyInfoDao")
	CountyInfoDao dao;

	public void addCounty(ProjectCountyInfo countyInfo) throws Exception {
		dao.addCounty(countyInfo);
	}

	public ProjectCountyInfo loadCounty(String id) throws Exception {
		return dao.findById(id);
	}

	public ProjectCountyInfo updateCounty(ProjectCountyInfo countyInfo)
			throws Exception {
		return dao.updateCounty(countyInfo);
	}

	public void deleteCounty(String id) throws Exception {
		ProjectCountyInfo countyInfo = dao.findById(id);
		dao.deleteCounty(countyInfo);
	}

	public List queryForList(CountyInfoBean bean, UserInfo userInfo) {
		ConditionGenerate conditionGenerate = new ConditionGenerate();
		String condition = conditionGenerate.getCondition("a.town", bean
				.getTown(), "'%'", "like", "'%", "%'");
		condition += conditionGenerate.getCondition("a.regionid", bean
				.getRegionid(), userInfo.getRegionid(), "=");
		condition += conditionGenerate.getCondition("a.id", bean.getId(),
				"a.id", "=");
		return dao.queryForList(condition);
	}

	/**
	 * ִ�л�ȡ�����б���Ϣ
	 * 
	 * @param request
	 *            HttpServletRequest �û���������
	 * @param userInfo
	 * @param form
	 *            ActionForm �û������
	 * @return List �����б���Ϣ
	 * @throws Exception
	 */
	public List getTownList(UserInfo userInfo) {
		ConditionGenerate conditionGenerate = new ConditionGenerate();
		String condition = conditionGenerate.getUserQueryCondition(userInfo);
		condition += " and a.state='1' ";
		return dao.queryForList(condition);
	}

	/**
	 * �ж������Ƿ����
	 * 
	 * @param county
	 * @return
	 * @throws Exception
	 */
	public boolean judgeCountyExist(ProjectCountyInfo county) throws Exception {
		String sql = "select id from linepatrol_towninfo ";
		sql += " where id<>'" + county.getId() + "' ";
		sql += " and town='" + county.getTown() + "' ";
		sql += " and regionid='" + county.getRegionid() + "' ";
		List list = dao.getJdbcTemplate().queryForBeans(sql);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
}
