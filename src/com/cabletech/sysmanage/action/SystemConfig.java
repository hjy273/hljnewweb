package com.cabletech.sysmanage.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.sysmanage.domainobjects.AppConfig;
import com.cabletech.sysmanage.services.AppConfigBO;
/**
 * 系统配置页面
 * @author zh
 *
 */
public class SystemConfig extends BaseDispatchAction{
	/**
	 * 获取可用菜单,并转向菜单排序页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward menuOrderConfigForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//获取可用菜单
		
		return mapping.findForward("login_form");
	}
	/**
	 * 保存菜单顺序
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveMenuOrderConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//获取可用菜单
		
		return mapping.findForward("login_form");
	}
	/**
	 * 加载配置项
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward loadAppConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		AppConfigBO appConfig = (AppConfigBO)ctx.getBean("appConfigBO");
		Map<String,AppConfig> appconfigs = appConfig.findByAllConfig();
		request.setAttribute("appconfigs", appconfigs);
		return mapping.findForward("edit_config");
	}
	public ActionForward saveAppConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebApplicationContext ctx = getWebApplicationContext();
		AppConfigBO appConfig = (AppConfigBO)ctx.getBean("appConfigBO");
		String id = request.getParameter("id");
		String value = request.getParameter("value");
		appConfig.save(id,value);
		
		super.outPrint(response, "保存成功");
		return null;
	}
	public ActionForward loadConfigItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		AppConfigBO appConfig = (AppConfigBO)ctx.getBean("appConfigBO");
		String id = request.getParameter("id");
		AppConfig appconfig = appConfig.findById(id);
		return null;
	}
}
