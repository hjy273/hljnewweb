/**
 * �������ڹ����û������url���������û���ģ��ķ�������Ϊģ��������ṩ������Դ��
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
		Map menuMap = (Map)context.getAttribute("MenuMap");//menuMap��ŵ��ǲ˵�����id
		Map regionMap = (Map)context.getAttribute("RegionMap");//regionMap��ŵ��������id
		//��һ�γ�ʼ��menuMap��regionMap����
		if(regionMap == null){
			regionMap =getRegion();
			context.setAttribute("regionMap", regionMap);
		}
		if(menuMap == null){
			menuMap = initMenu();//��ʼ���˵���
			logger.info("menuMap size :"+menuMap.size());
			context.setAttribute("MenuMap", menuMap);
		}
		
		UserInfo user = (UserInfo)rq.getSession().getAttribute("LOGIN_USER");//��ǰ��¼�û�
		SysLoggerService logservice = new SysLoggerService();
		/*����url�������*/
		String url = rq.getRequestURL().toString() ;
		if(rq.getQueryString() != null){
			url += "?"+ rq.getQueryString();
		}
		/*prenUrl������֤����������ͬһ��url���Ӳ����Ķ�������*/
		if(rq.getSession().getAttribute("RequestURL") != null)
			prevUrl = (String)rq.getSession().getAttribute("RequestURL");
		//logger.info("prevurl ��"+prevUrl);
		//��֤��ǰurl���ϴε�url��ͬʱ�����ǲ�ȥ��¼�û���������ĸ��˵����ܡ�
		if(!prevUrl.equals(url)){
			/*�Ƚ������url ʵʱ��ء�����*/
			if(url.indexOf("index_gis.jsp") != -1){
				logger.info("ʵʱ��� "+rq.getQueryString());
				//logger.info("url"+url);
				rq.getSession().setAttribute("RequestURL", url);
				String query = rq.getQueryString();
				String region = query.substring(query.indexOf("=")+1);
				logservice.log(user,"1",region);
			}else
			if(url.indexOf("accidentAction.do?method=loadAllTrouble") != -1){
				logger.info("������ѯ�б�");
				//logger.info("url"+url);
				rq.getSession().setAttribute("RequestURL", url);
				String query = rq.getQueryString();
				String region = query.substring(query.indexOf("regionid=")+9);
				logservice.log(user,"6",region);
			}else
			if(url.indexOf("accidentAction.do?method=loadUndoenTrouble") != -1){
				logger.info("����������");
				//logger.info("url"+url);
				rq.getSession().setAttribute("RequestURL", url);
				String query = rq.getQueryString();
				String region = query.substring(query.indexOf("regionid=")+9);
				logservice.log(user,"6",region);
			}else
			if(url.indexOf("accidentAction.do?method=loadAllDoingTrouble") != -1){
				logger.info("��������");
				//logger.info("url"+url);
				rq.getSession().setAttribute("RequestURL", url);
				String query = rq.getQueryString();
				String region = query.substring(query.indexOf("regionid=")+9);
				logservice.log(user,"6",region);
			}else
			if(url.indexOf("queryTrouble.jsp") != -1){
				logger.info("������ѯ");
				//logger.info("url"+url);
				rq.getSession().setAttribute("RequestURL", url);
				String query = rq.getQueryString();
				String region = query.substring(query.indexOf("regionid=")+9);
				logservice.log(user,"6",region);
			}else{
				//��url�н�ȡ/WebApp�Ժ��url����Ȼ���menuMap��ȡ���ô���Ӧ�Ĳ˵�id��û���ҵ�����null��
				String tempurl = url.substring(url.indexOf("/WebApp"));
				MenuBean menu = (MenuBean) menuMap.get(tempurl);
				if(menu != null){
					logger.info("�˵����ƣ�"+menu.toString());
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
	//��ʼ��MenuMap
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
	//��ʼ��RegionMap
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
