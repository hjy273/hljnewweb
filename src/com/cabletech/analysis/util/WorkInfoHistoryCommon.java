package com.cabletech.analysis.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.analysis.dao.WorkInfoHistoryDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.config.UserType;

public class WorkInfoHistoryCommon extends BaseBisinessObject {
	private WorkInfoHistoryDAOImpl workInfoHistoryDAOImpl = new WorkInfoHistoryDAOImpl();

	private Logger logger = Logger.getLogger(this.getClass().getName()); // 建立logger输出对象;

	private String sql = "";

	/**
	 * 得到查询范围列表， 省移动用户：显示“全省区域”和各地市;市移动用户：显示用户所在市及市辖各代维; 市代维用户:显示所属代维公司及其所管各巡检组
	 * 
	 * @param userInfo
	 *            用户信息。
	 * @return 返回查询范围列表，List类型，各行为动态bean
	 */
	public List getRangeList(UserInfo userInfo) {
		sql = createRangeSql(userInfo);
		logger.info("查询范围SQL:" + sql);
		List rangeList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (rangeList == null) {
			logger.info("查询范围rangeList为空");
		}
		return rangeList;
	}

	/**
	 * 构建得到用户范围的SQL
	 * 
	 * @param userInfo
	 *            用户信息。
	 * @return 返回构建得到用户范围的SQL
	 */
	public String createRangeSql(UserInfo userInfo) {
		String userType = userInfo.getType();
		if (UserType.PROVINCE.equals(userType)) { // 省移动用户
			sql = "select regionid rangeid, regionname rangename "
					+ "from region r " + "where r.state is null"
					+ " and substr (r.regionid, 3, 4) != '1111' "
					+ " and substr (r.regionid, 3, 4) != '0000' "
					+ "order by r.regionid";
		} else if (UserType.SECTION.equals(userType)) { // 市移动用户
			sql = "select c.contractorid rangeid,c.contractorname rangename "
					+ "from contractorinfo c " + "where c.regionid = '"
					+ userInfo.getRegionid() + "'" + " and c.state is null "
					+ "order by c.contractorname";
		} else if (UserType.CONTRACTOR.equals(userType)) { // 市代维用户
			sql = "select p.patrolid rangeid,p.patrolname rangename "
					+ "from patrolmaninfo p,userinfo u "
					+ "where p.parentid = u.deptid" + " and u.userid = '"
					+ userInfo.getUserID() + "'" + " and p.state is null "
					+ "order by p.patrolname";
		}
		return sql;
	}
}
