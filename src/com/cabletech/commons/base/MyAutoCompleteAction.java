package com.cabletech.commons.base;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxAction;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cabletech.commons.config.SqlConfig;
import com.cabletech.commons.services.MyAutoCompleteService;

public class MyAutoCompleteAction extends BaseAjaxAction {
	private Logger logger = Logger.getLogger(MyAutoCompleteAction.class
			.getName());

	private String sql = "";

	private List sqlParamList = null;

	private SqlConfig config = SqlConfig.newInstance();

	private List backList = null;

	private MyAutoCompleteService bo = new MyAutoCompleteService();

	private String inputName;

	public String getXmlContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		// inputName = request.getParameter("subLineName");
		inputName = (String) request.getSession().getAttribute("inputName");
		// logger.info("inputName:" + inputName);
		inputName = request.getParameter(inputName);
		// logger.info("inputName:" + inputName);
		// if (inputName == null || "".equals(inputName)){
		// inputName = (String)request.getSession().getAttribute("inputName");
		// }
		String sqlKey = (String) request.getSession().getAttribute("sqlKey");
		// logger.info("hello:" + inputName);
		// logger.info("hello:" + sqlKey);
		sqlParamList = (List) request.getSession().getAttribute("listParam");
		if (sqlParamList == null) {
			logger.info("hello,sqlParamList in action is null");
		}
		// logger.info("sqlKey:" + sqlKey);
		sql = config.getGeneralSql(sqlKey);
		// logger.info("sql:" + sql);
		backList = bo.getResultSet(sql, sqlParamList, inputName);
		// for (int i = 0; i < backList.size(); i++) {
		// BasicDynaBean row = (BasicDynaBean) backList.get(i);
		// logger.info("row is :" + row.get("inputname"));
		// }
		AjaxXmlBuilder builder = new AjaxXmlBuilder().addItems(backList,
				"inputname", "inputid");
		// logger.info(inputName);
		// logger.info(builder.toString());
		return builder.toString();
	}
}
