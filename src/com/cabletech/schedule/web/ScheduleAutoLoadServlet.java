package com.cabletech.schedule.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cabletech.schedule.service.SendSmJobBO;

public class ScheduleAutoLoadServlet extends HttpServlet {
	private ApplicationContext applicationContext;
	private Logger logger = Logger.getLogger(ScheduleAutoLoadServlet.class);

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext servletContext = config.getServletContext();
		applicationContext = (ApplicationContext) WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		SendSmJobBO bo = (SendSmJobBO) applicationContext
				.getBean("sendSmJobBO");
		try {
			bo.reloadSchedule();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("重启调度服务异常", e);
		}
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
