package com.cabletech.planstat.util;

import java.util.List;

import org.apache.log4j.Logger;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.planstat.dao.MonthlyStatCityMobileDAOImpl;
import com.cabletech.planstat.dao.PlanStatBaseDAO;

public class PlanStatCommon extends BaseBisinessObject {
	private PlanStatBaseDAO dao = new MonthlyStatCityMobileDAOImpl();

	private Logger logger = Logger.getLogger(this.getClass().getName()); // 建立logger输出对象;

	private String sql = "";

	/**
	 * 得到各地市区域范围列表
	 * 
	 * @param userInfo
	 *            用户信息。
	 * @return 返回查询范围列表，List类型，各行为动态bean
	 */
	public List getRegionList() {
		sql = createRegionSql();
		logger.info("查询范围SQL:" + sql);
		List regionList = dao.queryInfo(sql);
		if (regionList == null) {
			logger.info("查询范围RegionList为空");
		}
		return regionList;
	}

	/**
	 * 构建得到区域列表的SQL
	 * 
	 * @param userInfo
	 *            用户信息。
	 * @return 返回构建得到区域列表的SQL
	 */
	public String createRegionSql() {
		sql = "select regionid, regionname "
				+ "from region r " + "where r.state is null"
				+ " and substr (r.regionid, 3, 4) != '1111' "
				+ " and substr (r.regionid, 3, 4) != '0000' "
				+ "order by r.regionid";
		return sql;
	}
}