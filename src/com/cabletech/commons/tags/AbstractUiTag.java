package com.cabletech.commons.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public abstract class AbstractUiTag extends BodyTagSupport{
	static final String BOTTOM = "bottom";
	static final String TOP = "top";
	static final String servlet = "ctagservlet";
	String parameterName = "p";
	protected final void println(Object toPrint) throws JspException {
	    try {
	      this.pageContext.getOut().println(toPrint);
	    }
	    catch (IOException e) {
	      throw new JspException(e);
	    }
	  }
}
