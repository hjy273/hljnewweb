package com.cabletech.linepatrol.commons.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.linepatrol.commons.dao.HideDangerDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;

@Service
@Transactional
public class HideDangerBO extends BaseBisinessObject {
	private static Logger logger = Logger.getLogger(HideDangerBO.class);

	@Resource(name = "hideDangerDAO")
	private HideDangerDAO dao;

	/**
	 * 执行根据查询条件获取最近3个月的隐患信息列表
	 */
	@Transactional(readOnly = true)
	public List loadHideDangers(UserInfo userInfo, String queryValue,
			String beginTime, String endTime, String accidents) {
		String condition = generateCondition(queryValue, userInfo);
		if (beginTime != null && !beginTime.equals("")) {
			condition += " and t.find_time>=to_date('" + beginTime
					+ " 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		} else {
			condition += " and t.find_time>=add_months(sysdate,-3) ";
		}
		if (endTime != null && !endTime.equals("")) {
			condition += " and t.find_time<=to_date('" + endTime
					+ " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		} else {
			condition += " and t.find_time<=sysdate ";
		}
		List list = dao.queryList(condition);
		List resultList = new ArrayList();
		if (accidents != null) {
			String[] accident = accidents.split(",");
			DynaBean bean;
			for (int i = 0; list != null && i < list.size(); i++) {
				bean = (DynaBean) list.get(i);
				for (int j = 0; accident != null && j < accident.length; j++) {
					if (accident[j] != null
							&& accident[j].equals(bean.get("id"))) {
						bean.set("is_checked", "1");
					}
				}
				resultList.add(bean);
			}
			return resultList;
		} else {
			return list;
		}
	}

	/**
	 * 根据用户请求生成查询条件
	 * 
	 * @param queryValue
	 * @param userInfo
	 * @return
	 */
	public String generateCondition(String queryValue, UserInfo userInfo) {
		StringBuffer buf = new StringBuffer("");
		if (queryValue != null && !"".equals(queryValue)) {
			buf.append(" and (");
			buf.append(" (t.name like '%" + queryValue + "%') ");
			// buf.append(" (phone like '%" + queryValue + "%') or ");
			// buf.append(" (find_time like '%" + queryValue + "%') ");
			buf.append(") ");
			buf.append("");
		}
		if ("12".equals(userInfo.getType())) {
			buf.append(" and r.regionid='" + userInfo.getRegionid() + "' ");
		}
		if ("22".equals(userInfo.getType())) {
			buf.append(" and c.contractorid='" + userInfo.getDeptID() + "' ");
		}
		return buf.toString();
	}
}
