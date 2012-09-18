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
	 * 执行获取区县列表信息
	 * 
	 * @param request
	 *            HttpServletRequest 用户输入请求
	 * @param userInfo
	 * @param form
	 *            ActionForm 用户输入表单
	 * @return List 区县列表信息
	 * @throws Exception
	 */
	public List getTownList(UserInfo userInfo) {
		ConditionGenerate conditionGenerate = new ConditionGenerate();
		String condition = conditionGenerate.getUserQueryCondition(userInfo);
		condition += " and a.state='1' ";
		return dao.queryForList(condition);
	}

	/**
	 * 判断区县是否存在
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
