package com.cabletech.report.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.services.DBService;
import com.cabletech.report.beans.Report;
import com.cabletech.report.dao.BaseReportDao;

public class ReportServiceImpl {
	private Logger logger = Logger.getLogger("ReportServiceImpl");
	BaseReportDao reportdao = new BaseReportDao();
	//添加
	public boolean addReport(Report report){
		report.setId(new DBService().getSeq("retport",10));
		String sql = "insert into report(id,reportname,reporttype,remark,reporturl,userid,regionid,createdate) values('" +report.getId()+"','"+report.getReportname()+"','"+report.getReporttype()+"','"+report.getRemark()+"','"+report.getReporturl()+"','"+report.getUserid()+"','"+report.getRegionid()+"',sysdate)";
		logger.info("sql"+sql);
		return reportdao.updateData(sql);
	}
	//修改
	public boolean updateReport(Report report){
		String sql = "update report set reportname='"+report.getReportname()+"',reporttype='"+report.getReporttype()+"',reporturl='"+report.getReporturl()+"',remark='"+report.getRemark()+"',userid='"+report.getUserid()+"' where id='"+report.getId()+"'";
		logger.info("sql"+sql);
		return reportdao.updateData(sql);
	}
	//审批
	public boolean auditingReport(Report report){
		String sql = "update report set auditingresult='"+report.getAuditingResult()+"',auditingremark='"+report.getAuditingRemark()+"',auditingman='"+report.getAuditingman()+"' where id='"+report.getId()+"'";
		logger.info("sql"+sql);
		return reportdao.updateData(sql);
	}
	public List getReportList(UserInfo user,Report condition,String con){
		String sql = "select r.id,r.reportname,r.reporttype,rt.typename ,r.reporturl,r.remark,r.auditingresult,r.auditingremark from report r,reporttype rt where r.reporttype=rt.code and r.regionid in (SELECT RegionID FROM region CONNECT BY PRIOR "
                     + " RegionID=parentregionid START WITH RegionID='" + user.getRegionid() + "') ";
		if(condition != null){
			if(condition.getReportname() !=null && !condition.getReportname().equals("")){
				sql += " and r.reportname like '%"+condition.getReportname()+"%' ";
			}
			if(condition.getReporttype() != null && !condition.getReporttype().equals("")){
				sql += " and r.reporttype = '"+condition.getReporttype()+"' ";
			}
		}
		if(con != null && !con.equals("")){
			if(con.equals("noauditing")){
				sql += " and (r.auditingresult !='通过' or r.auditingresult is null) ";
			}
			if(con.equals("auditing")){
				sql += "and r.auditingresult ='通过'";
			}
			
		}
		sql += " order by r.createdate";
		logger.info("sql"+sql);
		return reportdao.queryList(sql);
	}
	public Report getReport(String id){
		String sql = "select id,reportname,reporttype,reporturl,remark,auditingresult,auditingremark from report where id='"+id+"'";
		logger.info("sql"+sql);
		return reportdao.queryData(sql);
	}
	public boolean delReport(String id) {
		String sql = "delete report where id='"+id+"'";
		logger.info("sql"+sql);
		return reportdao.updateData(sql);
	}
}
