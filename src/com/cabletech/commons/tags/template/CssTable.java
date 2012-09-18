package com.cabletech.commons.tags.template;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * 通过该标签自动设置页面表单的行与行间的渐变样式。
 * 
 * @author zhj
 *
 */
public class CssTable extends TagSupport{
	private String tableId = "formtable";
	public int doStartTag() throws JspException{
	        try{
	            StringBuffer strBuf = new StringBuffer();
	            strBuf.append("<script type=\"text/javascript\">");
	            strBuf.append("function csstable(){");
	            strBuf.append("	if (typeof("+tableId+") != \"undefined\"){");
	    	    strBuf.append("		for(i=0;i<"+tableId+".rows.length;i++) {");
	    	    strBuf.append("			(i%2==0)?("+tableId+".rows(i).className = \"trcolor\"):("+tableId+".rows(i).className = \"trwhite\");");
	    	    strBuf.append("		}");
	    	    strBuf.append("	}");
	    	    strBuf.append("}");
	    	    strBuf.append("csstable();");
	    	    strBuf.append("</script>");
	            this.pageContext.getOut().print( strBuf.toString() );
	        }
	        catch( Exception ex ){
	            ex.printStackTrace();
	        }
	        return this.SKIP_BODY;
	    }

}
