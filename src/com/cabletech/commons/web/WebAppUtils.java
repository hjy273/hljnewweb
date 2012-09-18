package com.cabletech.commons.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.context.ApplicationContext;

import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.config.SpringContext;
import com.cabletech.sysmanage.services.AppConfigBO;

/**
 * <p>Utility methods used by web applications.</p>
 *
 * @author Copyright (c) 2003 by BEA Systems. All Rights Reserved.
 */
public class WebAppUtils extends HttpServlet{
	public static String approverGroupId = null;
	public static int online_period = 0;// 在线巡检员制定时间范围;单位分钟
	private String appname = "综合代维管理系统";
	private String firstParty = "";
    private static Logger logger = Logger.getLogger( WebAppUtils.class.getName() );


    /**
     * <p>Get servlet name from given mapping.</p>
     *
     * @param mapping
     * @param name
     * @return String
     */
    public static String getServletName( ActionMapping mapping, String name ){
        ActionForward forward = mapping.findForward( name );
        String path = forward.getPath();
        return path.substring( path.indexOf( "/" ) + 1 );
    }

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// 设置应用程序需要的变量
		setAppPath(config);
		ApplicationContext ctx = SpringContext.getApplicationContext();
		//ServletContext sc = config.getServletContext();
		//WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(sc);
		AppConfigBO appConfig = (AppConfigBO)ctx.getBean("appConfigBO");
		approverGroupId = appConfig.findByProperties("approverGroupId");
		if(approverGroupId == null){
			approverGroupId=getInitParameter("approverGroupId");
		}
		online_period = Integer.parseInt(appConfig.findByProperties("online_period"));
		if(online_period == 0){
			online_period=Integer.parseInt(getInitParameter("online_period"));
		}
//		String logoImg = appConfig.findByProperties("logoImg");
		String logoImg = GisConInfo.newInstance().getLogoImg();
		String copyright = GisConInfo.newInstance().getCopyRight();
		getServletContext().setAttribute("LogoImg", logoImg);
		getServletContext().setAttribute("copyright", copyright);
		
	}

	/**
	 * 应用程序外部接口
	 */
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private void setAppPath(ServletConfig config){
		String appPath = getServletContext().getContextPath();
		appname = GisConInfo.newInstance().getAppName();
		firstParty = GisConInfo.newInstance().getFirstParty();
		getServletContext().setAttribute("ctx", appPath);
		getServletContext().setAttribute("AppName", appname);
		getServletContext().setAttribute("FirstParty", firstParty);
	}

}
