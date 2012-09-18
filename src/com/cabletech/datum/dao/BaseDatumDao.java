package com.cabletech.datum.dao;


import java.util.List;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.datum.bean.BaseDatum;

public abstract class BaseDatumDao {
	
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
	public abstract BaseDatum queryData(String sql);
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
