package com.cabletech.commons.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ReportConfig {
	private static Logger logger = Logger.getLogger(ReportConfig.class);
	private static ReportConfig eportConfig = null;
	public static final String REPORT_CONFIG = "ReportConfig.properties";
	private static Properties properties=null;
	
	public static ReportConfig getInstance(){
		try {
			if (eportConfig == null) {
				eportConfig = new ReportConfig();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return eportConfig;
	}
	public String getUrlPath(String propertyItemName) throws IOException {
		String fileName = properties.getProperty(propertyItemName);
		if (fileName != null && fileName != "") {
			String urlPath = ConfigPathUtil.getClassPathConfigFile(fileName);
			return urlPath;

		} else {
			return null;
		}
	}
	
	public ReportConfig() {
//		String filePath = ConfigPathUtil.getClassPathConfigFile(REPORT_CONFIG);
		try {
//			properties = PropertiesUtil.loadProperties(filePath);
			properties = PropertiesUtil.loadProperties(REPORT_CONFIG);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	
}
