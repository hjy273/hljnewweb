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
 * ϵͳ����ҳ��
 * @author zh
 *
 */
public class SystemConfig extends BaseDispatchAction{
	/**
	 * ��ȡ���ò˵�,��ת��˵�����ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward menuOrderConfigForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//��ȡ���ò˵�
		
		return mapping.findForward("login_form");
	}
	/**
	 * ����˵�˳��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveMenuOrderConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//��ȡ���ò˵�
		
		return mapping.findForward("login_form");
	}
	/**
	 * ����������
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
		
		super.outPrint(response, "����ɹ�");
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
