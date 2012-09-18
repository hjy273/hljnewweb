package com.cabletech.commons.tags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.ajaxtags.tags.OptionsBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * Tag handler for the autocomplete AJAX tag.
 * 
 * @author Darren Spurgeon
 * @version $Revision: 1.1.2.2 $ $Date: 2008/01/30 08:33:18 $
 */
public class StandardAutoCompletedTag extends TagSupport {
	private static Logger logger = Logger
			.getLogger(StandardAutoCompletedTag.class.getName());

	private String var;

	private String attachTo;

	private String baseUrl;

	private String source;

	private String target;

	private String parameters;

	private String minimumCharacters;

	private String appendSeparator;

	private String className;

	private String preFunction;

	private String postFunction;

	private String errorFunction;

	private String parser;

	private String indicator;

	private String sqlKey;

	private String sqlParamListName;

	public String getAppendSeparator() {
		return appendSeparator;
	}

	public void setAppendSeparator(String appendSeparator) {
		this.appendSeparator = appendSeparator;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getErrorFunction() {
		return errorFunction;
	}

	public void setErrorFunction(String errorFunction) {
		this.errorFunction = errorFunction;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getMinimumCharacters() {
		return minimumCharacters;
	}

	public void setMinimumCharacters(String minimumCharacters) {
		this.minimumCharacters = minimumCharacters;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getAttachTo() {
		return attachTo;
	}

	public void setAttachTo(String attachTo) {
		this.attachTo = attachTo;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getParser() {
		return parser;
	}

	public void setParser(String parser) {
		this.parser = parser;
	}

	public String getPreFunction() {
		return preFunction;
	}

	public void setPreFunction(String preFunction) {
		this.preFunction = preFunction;
	}

	public String getPostFunction() {
		return postFunction;
	}

	public void setPostFunction(String postFunction) {
		this.postFunction = postFunction;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int doStartTag() throws JspException {
		// Required Properties
		this.baseUrl = (String) ExpressionEvaluatorManager.evaluate("baseUrl",
				this.baseUrl, String.class, this, super.pageContext);
		this.source = (String) ExpressionEvaluatorManager.evaluate("source",
				this.source, String.class, this, super.pageContext);
		this.target = (String) ExpressionEvaluatorManager.evaluate("target",
				this.target, String.class, this, super.pageContext);
		this.className = (String) ExpressionEvaluatorManager.evaluate(
				"className", this.className, String.class, this,
				super.pageContext);
		this.sqlKey = (String) ExpressionEvaluatorManager.evaluate("sqlKey",
				this.sqlKey, String.class, this, super.pageContext);

		// Optional Properties
		if (this.sqlParamListName != null) {
			this.sqlParamListName = (String) ExpressionEvaluatorManager
					.evaluate("sqlParamListName", this.sqlParamListName,
							String.class, this, super.pageContext);
		}
		if (this.var != null) {
			this.var = (String) ExpressionEvaluatorManager.evaluate("var",
					this.var, String.class, this, super.pageContext);
		}
		if (this.attachTo != null) {
			this.attachTo = (String) ExpressionEvaluatorManager.evaluate(
					"attachTo", this.attachTo, String.class, this,
					super.pageContext);
		}
		if (this.parameters != null) {
			this.parameters = (String) ExpressionEvaluatorManager.evaluate(
					"parameters", this.parameters, String.class, this,
					super.pageContext);
		}
		if (this.minimumCharacters != null) {
			this.minimumCharacters = (String) ExpressionEvaluatorManager
					.evaluate("minimumCharacters", this.minimumCharacters,
							String.class, this, super.pageContext);
		}
		if (this.indicator != null) {
			this.indicator = (String) ExpressionEvaluatorManager.evaluate(
					"indicator", this.indicator, String.class, this,
					super.pageContext);
		}
		if (this.preFunction != null) {
			this.preFunction = (String) ExpressionEvaluatorManager.evaluate(
					"preFunction", this.preFunction, String.class, this,
					super.pageContext);
		}
		if (this.postFunction != null) {
			this.postFunction = (String) ExpressionEvaluatorManager.evaluate(
					"postFunction", this.postFunction, String.class, this,
					super.pageContext);
		}
		if (this.errorFunction != null) {
			this.errorFunction = (String) ExpressionEvaluatorManager.evaluate(
					"errorFunction", this.errorFunction, String.class, this,
					super.pageContext);
		}
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		OptionsBuilder options = new OptionsBuilder();
		options.add("source", this.source, true).add("target", this.target,
				true).add("className", this.className, true).add("sqlKey",
				this.sqlKey, true);
		if (this.sqlParamListName != null)
			options.add("sqlParamListName", this.sqlParamListName, true);
		if (this.parameters != null)
			options.add("parameters", this.parameters, true);
		if (this.indicator != null)
			options.add("indicator", this.indicator, true);
		if (this.minimumCharacters != null)
			options.add("minimumCharacters", this.minimumCharacters, true);
		if (this.appendSeparator != null)
			options.add("appendSeparator", this.appendSeparator, true);
		if (this.preFunction != null)
			options.add("preFunction", this.preFunction, false);
		if (this.postFunction != null)
			options.add("postFunction", this.postFunction, false);
		if (this.errorFunction != null)
			options.add("errorFunction", this.errorFunction, false);
		if (this.parser != null)
			options.add("parser", this.parser, false);

		StringBuffer script = new StringBuffer();
		script.append("<script type=\"text/javascript\">\n");
		if (StringUtils.isNotEmpty(this.var)) {
			if (StringUtils.isNotEmpty(this.attachTo)) {
				script.append(this.attachTo).append(".").append(this.var);
			} else {
				script.append("var ").append(this.var);
			}
			script.append(" = ");
		}
		script.append("new AjaxJspTag.Autocomplete(\n").append("\"").append(
				this.baseUrl).append("\", {\n").append(options.toString())
				.append("});\n").append("</script>\n\n");

		JspWriter writer = pageContext.getOut();
		try {
			writer.println(script);
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return EVAL_PAGE;
	}

	public void release() {
		this.var = null;
		this.attachTo = null;
		this.baseUrl = null;
		this.className = null;
		this.preFunction = null;
		this.errorFunction = null;
		this.minimumCharacters = null;
		this.appendSeparator = null;
		this.parameters = null;
		this.postFunction = null;
		this.source = null;
		this.target = null;
		this.parser = null;
		this.indicator = null;
		this.sqlKey = null;
		this.sqlParamListName = null;
		super.release();
	}

	public String getSqlKey() {
		return sqlKey;
	}

	public void setSqlKey(String sqlKey) {
		this.sqlKey = sqlKey;
	}

	public String getSqlParamListName() {
		return sqlParamListName;
	}

	public void setSqlParamListName(String sqlParamListName) {
		this.sqlParamListName = sqlParamListName;
	}
}
