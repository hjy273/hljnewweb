package com.cabletech.commons.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.cabletech.commons.hb.QueryUtil;

public class GisConInfo {

	private static final long serialVersionUID = 1L;
	private static GisConInfo config;
	private Logger logger = Logger.getLogger(GisConInfo.class);
	private static final String FILE_NAME = "gisServer.properties";
	/**
     * 配置文件的最后更新时间
     */
    public static Long lastModified = 0l;
	
    private String appName;
	private String firstParty;
    
	private String serverip;
	private String serverport;
	private String serverapp;
	private String logoImg;
	private String copyRight;
	
	private String gismapswitch;
	private String uploadRoot;
	private String rmiUrl = "";
	private String daysfornoticeplanstart;
	private String daysfornoticeplanend;
	private String minutesforresubmit;
	private String minutesreqandres;
	private String statip;
	private String statport;
	private String searchip;
	private String searchport;
	private String searchdir;
	private String reportsip;
	private String reportsport;
	private String patrolStartTime;
	private String patrolEndTime;
	private String spacingTime;
	private String unifyEntryIP;
	private String unifyEntryPort;
	private String unifyEntryUrl;
	private String watchPicIP;
	private String watchPicPort;
	private String watchPicDir;

	private String adServiceUrl;
	private String sdServiceUrl;
	private String hiddenTroubleUrl;
	private String uploadTmp;
	private String gisUrl;
	private String bbsServerIp;
	private String bbsServerPort;

	public String getGisUrl() {
        return gisUrl;
    }

    public void setGisUrl(String gisUrl) {
        this.gisUrl = gisUrl;
    }

    public String getBbsServerIp() {
        return bbsServerIp;
    }

    public void setBbsServerIp(String bbsServerIp) {
        this.bbsServerIp = bbsServerIp;
    }

    public String getBbsServerPort() {
        return bbsServerPort;
    }

    public void setBbsServerPort(String bbsServerPort) {
        this.bbsServerPort = bbsServerPort;
    }

    /**
	 *初始化GIS 信息
	 */
	public static GisConInfo newInstance() {
		try {
			if (config == null || PropertiesUtil.isModFile(FILE_NAME)) {
				config = new GisConInfo();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return config;
	}

	/**
	 * 读具体属性
	 * @throws IOException 
	 */
	private GisConInfo() {
		try{
		Properties prop = PropertiesUtil.loadProperties(FILE_NAME);
			this.setAppName(prop.getProperty("appname"));
			this.setFirstParty(prop.getProperty("firstparty"));
			this.setServerip(prop.getProperty("serverip"));
			this.setServerport(prop.getProperty("serverport"));
			this.setServerapp(prop.getProperty("serverapp"));
			this.setGismapswitch(prop.getProperty("gismapswitch"));
			this.setUploadRoot(prop.getProperty("uploadroot"));
			this.setRmiUrl(prop.getProperty("rmiUrl"));
			this.setDaysfornoticeplanstart(prop.getProperty("daysfornoticeplanstart"));
			this.setDaysfornoticeplanend(prop.getProperty("daysfornoticeplanend"));
			this.setMinutesforresubmit(prop.getProperty("minutesforresubmit"));
			this.setMinutesreqandres(prop.getProperty("minutesreqandres"));
			this.setStatip(prop.getProperty("statip"));
			this.setStatport(prop.getProperty("statport"));
			this.setSearchip(prop.getProperty("searchip"));
			this.setSearchport(prop.getProperty("searchport"));
			this.setReportsip(prop.getProperty("reportsip"));
			this.setReportsport(prop.getProperty("reportsport"));
			this.setPatrolStartTime(prop.getProperty("patrol_starttime"));
			this.setPatrolEndTime(prop.getProperty("patrol_endtime"));
			this.setSpacingTime(prop.getProperty("spacingtime"));
			this.setUnifyEntryIP(prop.getProperty("unifyEntryIP"));
			this.setUnifyEntryPort(prop.getProperty("unifyEntryPort"));
			this.setUnifyEntryUrl(prop.getProperty("unifyEntryUrl"));
			this.setWatchPicIP(prop.getProperty("watchPicIP"));
			this.setWatchPicPort(prop.getProperty("watchPicPort"));
			this.setWatchPicDir(prop.getProperty("watchPicDir"));
			this.setUploadTmp(prop.getProperty("uploadTmp"));
			this.setAdServiceUrl(prop.getProperty("adServiceUrl"));
			this.setSdServiceUrl(prop.getProperty("sdServiceUrl"));
			this.setHiddenTroubleUrl(prop.getProperty("hiddenTroubleUrl"));
			this.setGisUrl(prop.getProperty("gisURL"));
			this.setBbsServerIp(prop.getProperty("bbsserverip"));
			this.setBbsServerPort(prop.getProperty("bbsserverport"));
			this.setLogoImg(prop.getProperty("logoimg"));
			this.setCopyRight(prop.getProperty("copyright"));
		}catch(IOException ioe){
			logger.error(ioe);
		}

	}

	
	public String getCopyRight() {
		return copyRight;
	}

	public void setCopyRight(String copyRight) {
		this.copyRight = copyRight;
	}

	public String getLogoImg() {
		return logoImg;
	}

	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getFirstParty() {
		return firstParty;
	}

	public void setFirstParty(String firstParty) {
		this.firstParty = firstParty;
	}

	public String getUploadTmp() {
		return uploadTmp;
	}

	public void setUploadTmp(String uploadTmp) {
		this.uploadTmp = uploadTmp;
	}

	public String getUnifyEntryUrl() {
		return unifyEntryUrl;
	}

	public void setUnifyEntryUrl(String unifyEntryUrl) {
		this.unifyEntryUrl = unifyEntryUrl;
	}

	public String getReportsip() {
		return reportsip;
	}

	public void setReportsip(String reportsip) {
		this.reportsip = reportsip;
	}

	public String getReportsport() {
		return reportsport;
	}

	public void setReportsport(String reportsport) {
		this.reportsport = reportsport;
	}

	public String getSearchdir() {
		return searchdir;
	}

	public void setSearchdir(String searchdir) {
		this.searchdir = searchdir;
	}

	public String getUploadRoot() {
		return this.uploadRoot;
	}

	public void setUploadRoot(String uploadRoot) {
		this.uploadRoot = uploadRoot;
	}

	public String getServerip() {
		return serverip;
	}

	public void setServerip(String serverip) {
		this.serverip = serverip;
	}

	public String getServerport() {
		return serverport;
	}

	public void setServerport(String serverport) {
		this.serverport = serverport;
	}

	public String getServerapp() {
		return serverapp;
	}

	public String getGismapswitch() {
		return gismapswitch;
	}

	public String getRmiUrl() {
		return rmiUrl;
	}

	public void setServerapp(String serverapp) {
		this.serverapp = serverapp;
	}

	public void setGismapswitch(String gismapswitch) {
		this.gismapswitch = gismapswitch;
	}

	public void setRmiUrl(String rmiUrl) {
		this.rmiUrl = rmiUrl;
	}

	
	/**
	 * 完整路径
	 * @return String
	 */
	public String getWholePath() {
		String wholePath = "http://" + this.getServerip() + ":" + this.getServerport() + "/" + this.getServerapp()
				+ "/";

		//logger.info("完整GIS路径 ：" + wholePath);

		return wholePath;
	}

	/**
	 * 通过数据库读取不同地市不同服务器ip与地图路径
	 * @return String
	 */
	public String getWholePathFromDB(String regionid) throws Exception {
		String wholePath = getWholePath();

		try {

			String sql = "select ip from REGION where REGIONID = '" + regionid + "'";
			QueryUtil queryU = new QueryUtil();

			String[][] resultArr = queryU.executeQueryGetArray(sql, "");
			if (resultArr[0][0].length() > 0) {
				wholePath = resultArr[0][0];
			}
		} catch (Exception e) {

		}
		return wholePath;
	}

	public String getDaysfornoticeplanstart() {
		return daysfornoticeplanstart;
	}

	public void setDaysfornoticeplanstart(String daysfornoticeplanstart) {
		this.daysfornoticeplanstart = daysfornoticeplanstart;
	}

	public String getDaysfornoticeplanend() {
		return daysfornoticeplanend;
	}

	public void setDaysfornoticeplanend(String daysfornoticeplanend) {
		this.daysfornoticeplanend = daysfornoticeplanend;
	}

	public String getMinutesforresubmit() {
		return minutesforresubmit;
	}

	public void setMinutesforresubmit(String minutesforresubmit) {
		this.minutesforresubmit = minutesforresubmit;
	}

	public String getMinutesreqandres() {
		return minutesreqandres;
	}

	public void setMinutesreqandres(String minutesreqandres) {
		this.minutesreqandres = minutesreqandres;
	}

	public String getStatport() {
		return statport;
	}

	public void setStatport(String statport) {
		this.statport = statport;
	}

	public String getStatip() {
		return statip;
	}

	public void setStatip(String statip) {
		this.statip = statip;
	}

	public String getSearchip() {
		return searchip;
	}

	public void setSearchip(String searchip) {
		this.searchip = searchip;
	}

	public String getSearchport() {
		return searchport;
	}

	public void setSearchport(String searchport) {
		this.searchport = searchport;
	}

	public String getPatrolStartTime() {
		return patrolStartTime;
	}

	public void setPatrolStartTime(String patrolStartTime) {
		this.patrolStartTime = patrolStartTime;
	}

	public String getPatrolEndTime() {
		return patrolEndTime;
	}

	public void setPatrolEndTime(String patrolEndTime) {
		this.patrolEndTime = patrolEndTime;
	}

	public String getSpacingTime() {
		return spacingTime;
	}

	public void setSpacingTime(String spacingTime) {
		this.spacingTime = spacingTime;
	}

	public String getUnifyEntryIP() {
		return unifyEntryIP;
	}

	public void setUnifyEntryIP(String unifyEntryIP) {
		this.unifyEntryIP = unifyEntryIP;
	}

	public String getUnifyEntryPort() {
		return unifyEntryPort;
	}

	public void setUnifyEntryPort(String unifyEntryPort) {
		this.unifyEntryPort = unifyEntryPort;
	}

	public String getWatchPicDir() {
		return watchPicDir;
	}

	public void setWatchPicDir(String watchPicDir) {
		this.watchPicDir = watchPicDir;
	}

	public String getWatchPicPort() {
		return watchPicPort;
	}

	public void setWatchPicPort(String watchPicPort) {
		this.watchPicPort = watchPicPort;
	}

	public String getWatchPicIP() {
		return watchPicIP;
	}

	public void setWatchPicIP(String watchPicIP) {
		this.watchPicIP = watchPicIP;
	}

	public String getAdServiceUrl() {
		return adServiceUrl;
	}

	public void setAdServiceUrl(String adServiceUrl) {
		this.adServiceUrl = adServiceUrl;
	}

	public String getSdServiceUrl() {
		return sdServiceUrl;
	}

	public void setSdServiceUrl(String sdServiceUrl) {
		this.sdServiceUrl = sdServiceUrl;
	}

	public String getHiddenTroubleUrl() {
		return hiddenTroubleUrl;
	}

	public void setHiddenTroubleUrl(String hiddenTroubleUrl) {
		this.hiddenTroubleUrl = hiddenTroubleUrl;
	}

}
