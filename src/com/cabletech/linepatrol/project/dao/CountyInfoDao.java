package com.cabletech.linepatrol.project.dao;

import java.util.List;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.project.domain.ProjectCountyInfo;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class CountyInfoDao extends HibernateDao<ProjectCountyInfo, String> {

	private Logger logger = Logger.getLogger("CountyDAOImpl");

	public ProjectCountyInfo addCounty(ProjectCountyInfo countyInfo)
			throws Exception {
		super.initObject(countyInfo);
		super.getSession().save(countyInfo);
		return countyInfo;
	}

	public ProjectCountyInfo findById(String id) throws Exception {
		return this.findUnique("id", id);
	}

	public ProjectCountyInfo updateCounty(ProjectCountyInfo countyInfo)
			throws Exception {
		super.initObject(countyInfo);
		super.getSession().saveOrUpdate(countyInfo);
		return countyInfo;
	}

	public void deleteCounty(ProjectCountyInfo countyInfo) throws Exception {
		super.delete(countyInfo);
	}

	public List queryForList(String condition) {
		// TODO Auto-generated method stub
		String sql = "select a.id, a.town, a.remark, b.regionname regionid ";
		sql += " from LINEPATROL_TOWNINFO a,contractorinfo c, region b ";
		sql += " where a.regionid = b.regionid ";
		sql += " and b.regionid=c.regionid ";
		sql += condition;
		sql += " order by a.id desc";
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 执行根据区县编号获取区县名称信息
	 * 
	 * @param townId
	 *            区县编号
	 * @return String 区县名称信息
	 * @throws Exception
	 */
	public String getTownName(String townId) throws Exception {
		String sql = "select town from LINEPATROL_TOWNINFO where id=" + townId;
		List list = super.getJdbcTemplate().queryForBeans(sql);
		if (list != null && list.size() > 0) {
			DynaBean bean = (DynaBean) list.get(0);
			String townName = (String) bean.get("town");
			return townName;
		}
		return "";
	}
}
