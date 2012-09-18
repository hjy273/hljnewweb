package com.cabletech.baseinfo.services;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.http.*;

import org.apache.log4j.*;
import com.cabletech.baseinfo.Templates.*;
import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.config.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.exceltemplates.ExcelStyle;

public class SublineBO extends BaseBisinessObject {
	private static Logger logger = Logger.getLogger("SublineBO");
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	public SublineBO() {
	}

	SublineDAOImpl dao = new SublineDAOImpl();
	SublineCableListDAOImpl listDao = new SublineCableListDAOImpl();

	public void addSubline(Subline data) throws Exception {
		dao.addSubline(data);
	}

	public boolean validateSubLineName(String sublinename, String type) {
		return dao.validateSubLineName(sublinename, type);
	}

	public void addSublineCableList(SublineCableList data) throws Exception {
		listDao.addSublineCableList(data);
	}

	public void removeSubline(Subline data) throws Exception {
		dao.removeSubline(data);
	}

	public void deleteSublineCableList(SublineCableList data) throws Exception {
		listDao.deleteSublineCableList(data);
	}

	public void deleteBySublineID(String sublineid) throws Exception {
		listDao.deleteBySublineID(sublineid);
	}

	public String[] getRelatedList(String sublineid) throws Exception {
		return listDao.getRelatedList(sublineid);
	}

	public String[][] getCableList(String sublineid) throws Exception {
		return listDao.getCableList(sublineid);
	}

	public Subline loadSubline(String id) throws Exception {
		return dao.findById(id);
	}

	public Subline updateSubline(Subline subline) throws Exception {
		return dao.updateSubline(subline);
	}

	public boolean valiSubLineCanDele(String subLineid) {
		return dao.valiSubLineCanDele(subLineid);
	}

	public String sumCheckPoints(String sublineid) {
		return dao.sumCheckPoints(sublineid);
	}

	public boolean valiSublineName(String sublinename) {
		return dao.valiSublineName(sublinename);
	}

	/**
	 * 验证line是否被subline 使用
	 * 
	 * @param lineid
	 *            String
	 * @return boolean 已使用返回 false 未使用(可以删除) true
	 */
	public boolean findByLineid(String lineid) {
		String sql = "select count(*)i from sublineinfo where lineid='" + lineid + "'";
		ResultSet rs = null;
		try {
			QueryUtil query = new QueryUtil();
			logger.info("sql :" + sql);
			rs = query.executeQuery(sql);
			rs.next();
			int i = rs.getInt("i");
			if (i < 1) {
				rs.close();
				return true;
			} else {
				rs.close();
				return false;
			}

		} catch (Exception ex) {
			logger.error("验证line是否被subline 使用: " + ex.getMessage());
			return false;
		}

	}

	/**
	 * 导出完整报表
	 * 
	 * @param planform
	 *            PlanStatisticForm
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 */
	public void ExportSubline(List list, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "线段.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath("report.subline");

		SublineTemplate template = new SublineTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(list, excelstyle);
		template.write(out);

	}

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

	public List queryCableList(String queryValue, String departId, String regionId) {
		// TODO Auto-generated method stub
		String sql = "select t.segmentname,t.segmentid,t.kid from REPEATERSECTION t,contractorinfo c ";
		sql += " where c.contractorid=maintenance_id ";
		sql += " and c.regionid='" + regionId + "' and is_maintenance ='y' ";
		sql += " and maintenance_id='" + departId + "'";
		if (queryValue != null && !queryValue.equals("")) {
			sql += " and SEGMENTNAME like '%" + queryValue + "%'";
		}
		QueryUtil queryUtil;
		try {
			queryUtil = new QueryUtil();
			logger.info("查询关联的光缆SQL:" + sql);
			List list = queryUtil.queryBeans(sql);
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("查询关联光缆出错：", e);
		}

		return null;
	}

	public List queryCableList(String queryValue, String patrolId, UserInfo userInfo) {
		// TODO Auto-generated method stub
		String sql = "select t.segmentname,t.segmentid,t.kid from REPEATERSECTION t,contractorinfo c ";
		sql += " where c.contractorid=maintenance_id ";
		sql += " and c.regionid='" + userInfo.getRegionid() + "' and is_maintenance ='y' ";
		if ("2".equals(userInfo.getDeptype())) {
			sql += " and maintenance_id='" + userInfo.getDeptID() + "'";
		}
		if (patrolId != null && !"".equals(patrolId)) {
			sql += " and maintenance_id=(select parentid from patrolmaninfo where patrolid='" + patrolId + "') ";
		}
		if (queryValue != null && !queryValue.equals("")) {
			sql += " and SEGMENTNAME like '%" + queryValue + "%'";
		}
		sql+=" order by t.segmentname,t.segmentid,t.kid ";
		QueryUtil queryUtil;
		try {
			queryUtil = new QueryUtil();
			logger.info("查询关联的光缆SQL:" + sql);
			List list = queryUtil.queryBeans(sql);
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("查询关联光缆出错：", e);
		}

		return null;
	}

}
