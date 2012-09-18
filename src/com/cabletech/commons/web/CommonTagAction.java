package com.cabletech.commons.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.servlets.BaseAjaxAction;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.cabletech.commons.base.MyAutoCompleteAction;
import com.cabletech.commons.config.SqlConfig;
import com.cabletech.commons.services.MyAutoCompleteService;
import com.cabletech.tags.helpers.AjaxXmlBuilder;

public class CommonTagAction extends BaseAjaxAction{
	private Logger logger = Logger.getLogger(CommonTagAction.class
			.getName());

	private String sql = "";


	private SqlConfig config = SqlConfig.newInstance();

	private List backList = null;

	private MyAutoCompleteService bo = new MyAutoCompleteService();

	private String sqlkey;
	private String keyValue;
	public String getXmlContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		sqlkey = request.getParameter("sqlkey");
		keyValue = request.getParameter("p"); 
		System.out.println("sqlkey "+sqlkey);
		System.out.println("keyValue "+keyValue);
		sql = config.getGeneralSql(sqlkey);
		logger.info("sql:" + sql);
		backList = bo.getResultSetNonLike(sql, keyValue);
		AjaxXmlBuilder builder = new AjaxXmlBuilder().addItems(backList,
				"label", "value");
		System.out.println("builder "+builder.toString());
		return builder.toString();
	}
}
