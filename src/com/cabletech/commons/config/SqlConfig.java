package com.cabletech.commons.config;

import java.io.*;
import java.util.*;

import org.apache.log4j.*;

import com.cabletech.commons.config.ConfigPathUtil;

public class SqlConfig {

	private static SqlConfig config;

	private Logger logger = Logger.getLogger(SqlConfig.class);

	private String filePath;

	private Properties prop = new Properties();

	private FileInputStream fis;

	/**
	 * 初始化GIS 信息
	 */
	public static SqlConfig newInstance() {
		try {
			if (config == null) {
				config = new SqlConfig();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return config;
	}

	/**
	 * 读具体属性
	 */
	public SqlConfig() {
		filePath = ConfigPathUtil
				.getClassPathConfigFile("sqlForCustomTag.properties");
		try {
			logger.info(filePath);
			fis = new FileInputStream(filePath);
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getGeneralSql(String sqlKeyName) {
		String sql = prop.getProperty(sqlKeyName);
		logger.info("sql from SqlConfig:" + sql);
		return sql;
	}
}

