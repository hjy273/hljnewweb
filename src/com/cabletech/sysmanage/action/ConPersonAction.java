package com.cabletech.sysmanage.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.power.CheckPower;
import com.cabletech.sysmanage.beans.ConPersonBean;
import com.cabletech.sysmanage.dao.ExportDao;
import com.cabletech.sysmanage.domainobjects.ConPerson;
import com.cabletech.sysmanage.services.ConPersonBO;

public class ConPersonAction extends BaseInfoBaseDispatchAction {
	
	private static Logger logger = Logger.getLogger(ConPersonAction.class.getName());
	
	//���ConPersonBO����
	private ConPersonBO getConPersonalService(){
		//WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		WebApplicationContext ctx = getWebApplicationContext();
		return (ConPersonBO)ctx.getBean("conPersonBO");
	}

	//ת�������Ա��Ϣ������Ϣ
	public ActionForward addConPersonShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		request.setAttribute("deptname", userinfo.getDeptName());
		request.setAttribute("deptid", userinfo.getDeptID());
		request.setAttribute("regionid", userinfo.getRegionID());
		return mapping.findForward("addconperson");
	}

	//ִ�������Ա��Ϣ
	public ActionForward addConPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ConPersonBO conPersonService = getConPersonalService();
		ConPersonBean bean = (ConPersonBean) form;
		bean.setConditions("0");
		ConPerson data = new ConPerson();
		try {
			BeanUtil.objectCopy(bean, data);
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		try {
			conPersonService.addConPerson(data);
			log(request,"�����Ա������Ϣ����Ա����Ϊ��"+data.getName()+"��","���Ϲ���");
			return forwardInfoPage(mapping, request, "72002");
		} catch (ServiceException e) {
			logger.error("ִ����ӳ���:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	//��ʾ������ְ��Ա
	public ActionForward showConPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		try {
			ConPersonBO conPersonService = getConPersonalService();
			ConPerson data = new ConPerson();
			data.setConditions("0");
			List<BasicDynaBean> lPersonInfo = conPersonService.getAllPersonForSearch(data, userinfo);
			for(BasicDynaBean con:lPersonInfo){
					con.set("conditions", "��ְ");			
			}
			request.getSession().setAttribute("QUERYREUSLT", lPersonInfo);
			return mapping.findForward("showconperson");
		} catch (Exception e) {
			logger.error("��ʾ������ʾ������Ա����:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	//��ʾһ����Ա��ϸ��Ϣ
	public ActionForward showOnePerson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ConPerson conPerson = null;
		String id = request.getParameter("id");
		try {
			ConPersonBO conPersonService = getConPersonalService();
			conPerson = conPersonService.loadConPerson(id);
			request.setAttribute("personinfo", conPerson);
			return mapping.findForward("showoneconperson");
		} catch (Exception e) {
			logger.error("����ʾ��ϸ�г��ִ���:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	//�޸���ʾ
	public ActionForward showUp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ConPerson conPerson = null;
		String id = request.getParameter("id");
		try {
			ConPersonBO conPersonService = getConPersonalService();
			conPerson = conPersonService.loadConPerson(id);
			request.setAttribute("personinfo", conPerson);
			return mapping.findForward("editconperson");
		} catch (Exception e) {
			logger.error("�޸���ʾ����:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	//ִ���޸�
	public ActionForward upPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		if (!CheckPower.checkPower(request.getSession(), "72004"))
			return mapping.findForward("powererror");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		if (userinfo.getDeptype().equals("1"))
			return forwardErrorPage(mapping, request, "partstockerror");
		try {
			ConPersonBO conPersonService = getConPersonalService();
			ConPersonBean bean = (ConPersonBean) form;
			if(bean.getLeaveTime()!=null && !bean.getLeaveTime().equals("")){
				bean.setConditions("1");
			}
			else{
				
				bean.setConditions("0");
			}
			ConPerson data = new ConPerson();
			BeanUtil.objectCopy(bean, data);
			
			if (!conPersonService.updateConPerson(data))
				return forwardInfoPage(mapping, request, "error");
			log(request,"�޸���Ա������Ϣ����Ա����Ϊ��"+data.getName()+"��","���Ϲ���");
			return forwardInfoPage(mapping, request, "72004");
		} catch (Exception e) {
			logger.error("ִ���޸ĳ���:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	//ִ��ɾ��
	public ActionForward deleteInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			ConPersonBean bean = (ConPersonBean) form;
			ConPerson data = new ConPerson();
			BeanUtil.objectCopy(bean, data);
			ConPersonBO conPersonService = getConPersonalService();
			if (true == conPersonService.removeConPerson(data))
				return forwardInfoPage(mapping, request, "72005");
			else
				return forwardErrorPage(mapping, request, "error");
		} catch (Exception e) {
			logger.error("ɾ���쳣:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	//��ת����ѯҳ��
	public ActionForward queryShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return mapping.findForward("queryconperson");
		} catch (Exception e) {
			logger.error("��ѯ��ʾ�쳣:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}
	//��������ѯѲ����Ա��Ϣ
	public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			ConPersonBean bean = (ConPersonBean) form;
			ConPerson data = new ConPerson();
			BeanUtil.objectCopy(bean, data);
			UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
			ConPersonBO conPersonService = getConPersonalService();
			List<BasicDynaBean> lPersonInfo = conPersonService.getAllPersonForSearch(data, userinfo);
			for (BasicDynaBean con : lPersonInfo) {
				if ("1".equals(con.get("conditions"))) {
					con.set("conditions", "��ְ");
				} else
					con.set("conditions", "��ְ");
			}
			request.getSession().setAttribute("QUERYREUSLT", lPersonInfo);
			return mapping.findForward("showconpersonforsearch");
		} catch (Exception e) {
			logger.error("ִ���ۺϲ�ѯ�쳣:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	public ActionForward exportConPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List list = (List) request.getSession().getAttribute("QUERYREUSLT");
			logger.info("�õ�list");
			ExportDao edao = new ExportDao();
			if (edao.exportConPerson(list, response)) {
				logger.info("���excel�ɹ�");
			}
			return null;
		} catch (Exception e) {
			logger.error("����δ�����ϰ��б�����쳣:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}
}
