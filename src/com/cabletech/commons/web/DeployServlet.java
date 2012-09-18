package com.cabletech.commons.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.model.ActivityCoordinates;
import org.springframework.context.ApplicationContext;

import com.cabletech.commons.config.SpringContext;
import com.cabletech.commons.services.DeployService;

/**
 * @author zhj
 * 
 * 流程部署
 */
public class DeployServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DeployService deployService = null;
	
	private List list = null;
	
	/* 初始化ProcessEngine. */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ApplicationContext acxt = SpringContext.getApplicationContext();
		deployService = (DeployService)acxt.getBean("deployService");
		
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String deploy = request.getParameter("deploy");
		if (deploy.equals("deploy")) {
			try {
				deploy(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list = getLatestProcessDefinition(request,response);
			request.setAttribute("process", list);
			request.getRequestDispatcher("/listworkflows.jsp").forward(request, response);
		} else if (deploy.equals("query")) {
			list = getProcessInstanceById(request,response);
			request.setAttribute("pi", list);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}else if (deploy.equals("drawImage")) {
			List<ActivityCoordinates> coordinateList = drawImage(request,response);
			request.setAttribute("coordinates", coordinateList);
			request.getRequestDispatcher("trackProcess.jsp").forward(request, response);
		}
	}
	
	protected void deploy(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		String temp = getServletContext().getRealPath("/temp");
		String upload = getServletContext().getRealPath("/upload");
		DiskFileUpload diskFileUpload = new DiskFileUpload();
//		diskFileUpload.setFileSizeMax(1 * 1024 * 1024);
		diskFileUpload.setSizeThreshold(4096);
		diskFileUpload.setRepositoryPath(temp);
		
		List fileItems = diskFileUpload.parseRequest(request);
		deployService.deploy(fileItems);
		
	}
	protected List<ProcessDefinition> getLatestProcessDefinition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return deployService.getLatestProcessDefinition();
	}
	
	protected List<ProcessInstance> getProcessInstanceById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pdId = request.getParameter("id");
		return deployService.getProcessInstanceById(pdId);
	}
	
	protected List<ActivityCoordinates> drawImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String piId = request.getParameter("piId");
		return deployService.drawImage(piId);
	}

}
