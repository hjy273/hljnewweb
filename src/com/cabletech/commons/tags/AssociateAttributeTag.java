package com.cabletech.commons.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.SpringContext;

/**
 * 用于查看时显示，关联数据加载
 * @author Administrator
 *
 */
public class AssociateAttributeTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2310047772431320117L;
	private Logger logger = Logger.getLogger("AssociateAttributeTag");
	private Object keyValue;
	private String key;
	private String label;
	private String table;
	private String condition;
	private boolean isRegion;

	@Override
	public int doStartTag() throws JspException {
		String results = new String();
		HttpSession session = pageContext.getSession();
		UserInfo user = (UserInfo) session.getAttribute("USER_LOGIN");
		results = getKeyValue(user);
		try {
			pageContext.getOut().print(results);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return SKIP_BODY;
	}

	private String getKeyValue(UserInfo user) {
		if(keyValue == null){
			return "";
		}
		StringBuilder sql = new StringBuilder();
		List<Object> object = new ArrayList<Object>();
		object.add(keyValue);
		sql.append("SELECT " + label + " FROM " + table + " WHERE " + key + "=?");
		if (isRegion) {
			sql.append(" AND regionid='?'");
			object.add(user.getRegionid());
		}
		if(condition != null){
			sql.append(" AND "+condition);
		}
		logger.info("Query " + table + " SQL:" + sql.toString());
		ApplicationContext context = SpringContext.getApplicationContext();
		JdbcTemplate jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
		String result = "";
		try{
			result = (String) jdbcTemplate.queryForObject(sql.toString(), object.toArray(), String.class);
		}catch(Exception e){
			e.printStackTrace();
			result = "";
		}
		return result;
	}

	public Object getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(Object keyValue) {
		this.keyValue = keyValue;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public boolean isRegion() {
		return isRegion;
	}

	public void setRegion(boolean isRegion) {
		this.isRegion = isRegion;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
