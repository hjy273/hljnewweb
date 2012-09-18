package com.cabletech.report.dao;

import java.sql.ResultSet;
import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.report.beans.Report;

public class BaseReportDao {
	public boolean updateData(String sql){
		UpdateUtil util = null;
		try {
			util = new UpdateUtil();
			util.executeUpdate(sql);
			//util.close();
			return true;
		} catch (Exception e) {
			util = null;
			e.printStackTrace();
			return false;
		}
	}
	public Report queryData(String sql){
		QueryUtil query = null;
		Report report = new Report();
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while(rs.next()){
				report.setId(rs.getString("id"));
				report.setRemark(rs.getString("remark"));
				report.setReportname(rs.getString("reportname"));
				report.setReporttype(rs.getString("reporttype"));
				report.setReporturl(rs.getString("reporturl"));
				report.setAuditingRemark(rs.getString("auditingRemark"));
				report.setAuditingResult(rs.getString("auditingResult"));
			}
			return  report;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	public List queryList(String sql){
		QueryUtil query;
		try {
			query = new QueryUtil();
			List list = query.queryBeans(sql);
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
