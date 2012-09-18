package com.cabletech.commons.sm;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import cabletech.sm.message.SmSubmitRespMessage;
import cabletech.sm.rmi.RmiSmProxyService;

import com.cabletech.commons.base.BaseDispatchAction;

public class SendSMAction  extends BaseDispatchAction{
	private Logger logger = Logger.getLogger(SendSMAction.class);
	/**
	 * 手持设备短信调度接口
	 * url：/WebApp/sendsm.do?method=dispatch&mobile=13512345678&content=短信内容
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward dispatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebApplicationContext ctx = getWebApplicationContext();
		RmiSmProxyService sms = (RmiSmProxyService) ctx.getBean("rmiSmProxyService");
		String mobile = request.getParameter("mobile");
		String content = request.getParameter("content");
		logger.info("短信调度:\n\t\t接受设备号码："+mobile+",短信内容："+content);
		SmSubmitRespMessage res = sms.sendToTerminal(mobile, content);
		String result ="信息发送成功！";
		if(res.getResult()!=0){
			result ="信息发送失败！";
		}
		outPrint(response,result);
		return null;
	}
}
