package com.cabletech.datum.dao;

import java.sql.ResultSet;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.datum.bean.BaseDatum;
import com.cabletech.datum.bean.DatumExperience;

public class DatumExperienceDao extends BaseDatumDao{

	public BaseDatum queryData(String sql) {
		QueryUtil query = null;
		BaseDatum datum = new DatumExperience();
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while(rs.next()){
				datum.setDocumentname(rs.getString("filename"));
				datum.setDocumenttype(rs.getString("documenttype"));
				datum.setDescription(rs.getString("description"));
				datum.setId(rs.getString("id"));
				datum.setUserid(rs.getString("userid"));
				datum.setCreatedate(rs.getString("createdate"));
				datum.setValidatetime(rs.getString("validatetime"));
				datum.setAdjunct(rs.getString("adjunct"));
			}
			return  datum;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	

}
