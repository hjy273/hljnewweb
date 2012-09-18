/**
 * 此类用于过滤用户请求的url，来产生用户对模块的访问量，为模块访问量提供数据来源。
 */
package com.cabletech.commons.web;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.MenuBean;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.services.SysLoggerService;

public class LogFilter implements Filter{
	private Logger logger = Logger.getLogger("LogFillter");
	ServletContext context;
	String prevUrl="";
	public void destroy() {
	}

	public void doFilter(ServletRequest srq, ServletResponse srs,
			FilterChain fc) throws IOException, ServletException {
		HttpServletRequest rq = ( HttpServletRequest )srq;
		Map menuMap = (Map)context.getAttribute("MenuMap");//menuMap存放的是菜单名称id
		Map regionMap = (Map)context.getAttribute("RegionMap");//regionMap存放的是区域的id
		//第一次初始化menuMap、regionMap对象。
		if(regionMap == null){
			regionMap =getRegion();
			context.setAttribute("regionMap", regionMap);
		}
		if(menuMap == null){
			menuMap = initMenu();//初始化菜单．
			logger.info("menuMap size :"+menuMap.size());
			context.setAttribute("MenuMap", menuMap);
		}
		
		UserInfo user = (UserInfo)rq.getSession().getAttribute("LOGIN_USER");//当前登录用户
		SysLoggerService logservice = new SysLoggerService();
		/*请求url连接组合*/
		String url = rq.getRequestURL().toString() ;
		if(rq.getQueryString() != null){
			url += "?"+ rq.getQueryString();
		}
		/*prenUrl用于验证多次连续电击同一条url连接产生的多余数据*/
		if(rq.getSession().getAttribute("RequestURL") != null)
			prevUrl = (String)rq.getSession().getAttribute("RequestURL");
		//logger.info("prevurl ："+prevUrl);
		//验证当前url与上次的url不同时，我们采去记录用户点击的是哪个菜单功能。
		if(!prevUrl.equals(url)){
			/*比较特殊的url 实时监控、隐患*/
			if(url.indexOf("index_gis.jsp") != -1){
				logger.info("实时监控 "+rq.getQueryString());
				//logger.info("url"+url);
				rq.getSession().setAttribute("RequestURL", url);
				String query = rq.getQueryString();
				String region = query.substring(query.indexOf("=")+1);
				logservice.log(user,"1",region);
			}else
			if(url.indexOf("accidentAction.do?method=loadAllTrouble") != -1){
				logger.info("隐患查询列表");
				//logger.info("url"+url);
				rq.getSession().setAttribute("RequestURL", url);
				String query = rq.getQueryString();
				String region = query.substring(query.indexOf("regionid=")+9);
				logservice.log(user,"6",region);
			}else
			if(url.indexOf("accidentAction.do?method=loadUndoenTrouble") != -1){
				logger.info("待处理隐患");
				//logger.info("url"+url);
				rq.getSession().setAttribute("RequestURL", url);
				String query = rq.getQueryString();
				String region = query.substring(query.indexOf("regionid=")+9);
				logservice.log(user,"6",region);
			}else
			if(url.indexOf("accidentAction.do?method=loadAllDoingTrouble") != -1){
				logger.info("处理隐患");
				//logger.info("url"+url);
				rq.getSession().setAttribute("RequestURL", url);
				String query = rq.getQueryString();
				String region = query.substring(query.indexOf("regionid=")+9);
				logservice.log(user,"6",region);
			}else
			if(url.indexOf("queryTrouble.jsp") != -1){
				logger.info("隐患查询");
				//logger.info("url"+url);
				rq.getSession().setAttribute("RequestURL", url);
				String query = rq.getQueryString();
				String region = query.substring(query.indexOf("regionid=")+9);
				logservice.log(user,"6",region);
			}else{
				//从url中截取/WebApp以后的url串，然后从menuMap中取出该串对应的菜单id，没有找到返回null。
				String tempurl = url.substring(url.indexOf("/WebApp"));
				MenuBean menu = (MenuBean) menuMap.get(tempurl);
				if(menu != null){
					logger.info("菜单名称："+menu.toString());
					//logger.info("url"+url);
					rq.getSession().setAttribute("RequestURL", url);
					logservice.log(user,menu);
				}
			}
		}
		fc.doFilter( srq, srs);
	}

	public void init(FilterConfig config) throws ServletException {
		context = config.getServletContext();
		
	}
	private Map initMenu(){	
		return getMenuMap();
	}
	//初始化MenuMap
	private Map getMenuMap(){
		QueryUtil query = null;
		MenuBean menu = null;
		Map menuMap = new  HashMap();
		String sql = "select m.lablename mainlable,m.id mainid ,sub.lablename sublable,sub.id subid,son.id sonid,son.lablename sonlable,son.hrefurl url"
			+" from SONMENU son ,submenu sub ,mainmodulemenu m "
			+" where son.parentid=sub.id and sub.parentid=m.id and son.hrefurl is not null";
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while(rs.next()){
				menu = new MenuBean();
				menu.setMainlable(rs.getString("mainlable"));
				menu.setSublable(rs.getString("sublable"));
				menu.setSonlable(rs.getString("sonlable"));
				menu.setMainid(rs.getString("mainid"));
				menu.setSubid(rs.getString("subid"));
				menu.setSonid(rs.getString("sonid"));
				menu.setUrl(rs.getString("url"));
				menuMap.put(menu.getUrl(), menu);
			}
		} catch (Exception e) {
			menuMap= null;
			e.printStackTrace();
		}
		return menuMap;
		
	}
	//初始化RegionMap
	private Map getRegion(){
		QueryUtil query = null;
		Map regionMap = new  HashMap();
		String sql = "select regionname,regionid from region";
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while(rs.next()){
				String key = rs.getString("regionid");
				String area = rs.getString("regionname");
				regionMap.put(key,area);
			}
		} catch (Exception e) {
			regionMap= null;
			e.printStackTrace();
		}
		return regionMap;
	}
	
}
