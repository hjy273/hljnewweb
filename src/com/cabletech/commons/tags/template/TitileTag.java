package com.cabletech.commons.tags.template;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class TitileTag extends TagSupport{

    private String value = "";
    public void setValue( String value ){
        this.value = value;
    }


    public int doStartTag() throws JspException{
        try{
            StringBuffer strBuf = new StringBuffer();
            //strBuf.append("<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">");
            //strBuf.append("<tr><td height=\"24\" align=\"center\" class=\"title2\" style=\"font-size:16px; font-weight:500\">");
            //strBuf.append(value);
            //strBuf.append("</td></tr>");
            //strBuf.append("  <tr><td height=\"1\" background=\"/WebApp/images/bg_line.gif\"><img src=\"/WebApp/images/1px.gif\"  height=\"1\"></td> </tr></table>");
            //strBuf.append("</table>");
            strBuf.append( "<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>" );
            strBuf.append( value );
            strBuf.append( "</div>" );
            strBuf.append( "<hr width='98%' size='1'>" );
            this.pageContext.getOut().print( strBuf.toString() );
        }
        catch( Exception ex ){
            ex.printStackTrace();
        }
        return this.SKIP_BODY;
    }


}
