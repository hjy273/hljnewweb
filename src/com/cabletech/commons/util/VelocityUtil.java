package com.cabletech.commons.util;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VelocityUtil {
	//private static Map<String,Template> templates=new HashMap<String,Template>();
	private static VelocityEngine ve=new VelocityEngine();
	private static final String GBK="GBK";
	
	static{
		try {
			Properties p = new Properties();   
	        p.setProperty("resource.loader", "class");   
	        p.setProperty("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");   
	        p.setProperty("input.encoding",GBK);
	        ve.init(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public static void rendWebResponse(String templateName,Map context,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=GBK");
		VelocityContext velocityContext=new VelocityContext(context);
		Template template=ve.getTemplate(templateName);	
		PrintWriter out=response.getWriter();		
		template.merge(velocityContext,out);		
		out.flush();
		out.close();
	}
}
