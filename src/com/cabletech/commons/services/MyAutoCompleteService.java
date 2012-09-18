package com.cabletech.commons.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.hb.ConnectionManager;

public class MyAutoCompleteService extends BaseBisinessObject {
	private int listLength = 0;
	private ConnectionManager connManager;

	private Connection conn;

	private PreparedStatement stmt;

	private ResultSet rs;
	private Logger logger = Logger.getLogger(MyAutoCompleteService.class
			.getName());
	public List getResultSet(String sql, List paramList,String inputName) {
		if (paramList == null) {
			listLength = 0;
		} else {
			listLength = paramList.size();
		}
		try {
			connManager = new ConnectionManager();
			conn = connManager.getCon();
			//logger.info(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + inputName + "%");
			for (int i = 0; i < listLength; i++) {
				//logger.info("param in for loop:" + paramList.get(i).toString());
				stmt.setString(i + 2, paramList.get(i).toString());
			}
			rs = stmt.executeQuery();
			RowSetDynaClass rsdc = new RowSetDynaClass(rs);
			stmt.close();
			connManager.closeCon(conn);
			return rsdc.getRows();
		} catch (Exception ex) {
			logger.error("In MyAutoCompleteService", ex);
			return null;
		}
	}
	public List getResultSetNonLike(String sql, String keyVaule) {
		if (keyVaule == null) {
			listLength = 0;
		} 
		try {
			connManager = new ConnectionManager();
			conn = connManager.getCon();
			//logger.info(sql);
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, keyVaule);
			rs = stmt.executeQuery();
			RowSetDynaClass rsdc = new RowSetDynaClass(rs);
			stmt.close();
			connManager.closeCon(conn);
			return rsdc.getRows();
		} catch (Exception ex) {
			logger.error("In MyAutoCompleteService", ex);
			return null;
		}
	}
}
