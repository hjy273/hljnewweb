package com.cabletech.linepatrol.appraise.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.appraise.beans.AppraiseTableBean;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;
import com.cabletech.linepatrol.appraise.service.AppraiseTableBO;
import com.cabletech.linepatrol.appraise.util.AppraiseUtil;

public abstract class AppraiseTableAction extends BaseInfoBaseDispatchAction {
	private static final long serialVersionUID = 1L;
	private static AppraiseUtil util=new AppraiseUtil();
	String tableType;
	String typeName;
	
	/**
	 * ģ�嵼��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward importExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		
		AppraiseTableBO appraiseTableBO = (AppraiseTableBO) ctx.getBean("appraiseTableBO");
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		AppraiseTableBean bean = (AppraiseTableBean) form;
		if(appraiseTableBO.getTableByYear(bean.getYear(), tableType).size()>0){
			//�����ģ���Ѿ�����
			return forwardInfoPage(mapping, request, "addAppraise"+typeName+"ExcelRepeatError",bean.getYear());
		}
		initAppraiseTableBean(userInfo, bean);
		AppraiseTable appraiseTable=new AppraiseTable();
		try {
			appraiseTable = appraiseTableBO.setTableFromExcel(bean);
		} catch (Exception e) {
			//ģ��ĸ�ʽ����
			return forwardInfoPage(mapping, request, "excel"+typeName+"FormatError",bean.getYear());
		}
		request.getSession().setAttribute("AppraiseTable", appraiseTable);
		Map<String,Object> context=new HashMap<String,Object>();
		initContent(appraiseTable, context,AppraiseConstant.FLAG_IMPORT);
		util.PrintVM(response,context,AppraiseConstant.APPRAISE_TABLE_VM_PATH);
		return null;
		
	}
	/**
	 * ��ʼ��content
	 * @param appraiseTable
	 * @param context
	 * @param flag
	 */
	private void initContent(AppraiseTable appraiseTable, Map<String,Object> context,String flag) {
		context.put("table", appraiseTable);
		context.put("flag", flag);
		context.put("allWeight", appraiseTable.getAllWeight().get("tableWeight"));
		context.put("typeName", typeName);
	}
	/**
	 * ��ʼ��bean
	 * @param userInfo
	 * @param bean
	 */
	private void initAppraiseTableBean(UserInfo userInfo, AppraiseTableBean bean) {
		bean.setCreater(userInfo.getUserName());
		bean.setCreateDate(new Date());
	}
	/**
	 * ����ģ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveTable(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response){
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseTableBO appraiseTableBO = (AppraiseTableBO) ctx.getBean("appraiseTableBO");
		AppraiseTable appraiseTable=(AppraiseTable)request.getSession().getAttribute("AppraiseTable");
		request.getSession().removeAttribute("AppraiseTable");
		appraiseTable.setType(tableType);
		appraiseTableBO.saveAppraiseTable(appraiseTable);
		return forwardInfoPage(mapping, request, "addAppraise"+typeName+"ExcelSuccess", appraiseTable.getTableName());
	}
	
	/**
	 * ģ���б�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward appraiseTableList(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response){
		WebApplicationContext ctx=getWebApplicationContext();
		setTableType();
		setTypeName();
		AppraiseTableBO appraiseTableBO=(AppraiseTableBO)ctx.getBean("appraiseTableBO");
		List<AppraiseTable> tables=appraiseTableBO.getAllTable(tableType);
		request.getSession().setAttribute("tables", tables);
		String year=DateUtil.DateToString(new Date(), "yyyy");
		request.setAttribute("years", year);
		request.setAttribute("nowTime", new Date());
		return mapping.findForward("appraiseTableList");
	}
	
	/**
	 * ɾ��ģ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteTable(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response){
		setTableType();
		setTypeName();
		WebApplicationContext ctx=getWebApplicationContext();
		AppraiseTableBO appraiseTableBO=(AppraiseTableBO)ctx.getBean("appraiseTableBO");
		String id=request.getParameter("id");
		AppraiseTable table=appraiseTableBO.getTableById(id);
		appraiseTableBO.deleteTableById(id);
		return forwardInfoPage(mapping, request, "deleteAppraise"+typeName+"ExcelSuccess",table.getTableName());
	}
	
	/**
	 * ģ��鿴
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewTable(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response){
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseTableBO appraiseTableBO = (AppraiseTableBO) ctx.getBean("appraiseTableBO");
		String id=request.getParameter("id");
		AppraiseTable appraiseTable=appraiseTableBO.getTableById(id);
		Map<String,Object> context=new HashMap<String,Object>();
		initContent(appraiseTable, context, AppraiseConstant.FLAG_VIEW);
		util.PrintVM(response,context,AppraiseConstant.APPRAISE_TABLE_VM_PATH);
		return null;
	}

	/**
	 * �����¿��˱�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTable(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseTableBO bo = (AppraiseTableBO) ctx.getBean("appraiseTableBO");
		String id = request.getParameter("id");
		bo.exprotTable(id, response);
		return null;
	}
	/**
	 * �����¿��˱�ģ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTableTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseTableBO bo = (AppraiseTableBO) ctx.getBean("appraiseTableBO");
		String templateName = request.getParameter("templateName");
		bo.exprotTableTemplate(templateName, response);
		return null;
	}
	/**
	 * ���ñ������
	 */
	public abstract void setTableType();
	/**
	 * ���ñ���������
	 */
	public abstract void setTypeName();
}
