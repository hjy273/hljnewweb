package com.cabletech.commons.tags.template;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*; //

public class FormSubmit extends BodyTagSupport{

    public int doEndTag() throws JspException{
        try{
            StringBuffer strBuf = new StringBuffer();
            strBuf.append( "<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">" );
            strBuf.append( "<tr>" );
            strBuf.append( "<td height=\"6\"></td>" );
            strBuf.append( "</tr>" );
            strBuf.append( "<tr>" );
            strBuf.append( "<td align=\"right\">" );
            strBuf.append( "<table  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">" );
            strBuf.append( "<tr align=\"center\">" );

            String content = null;
            if( bodyContent != null ){
                content = bodyContent.getString();
                bodyContent.clearBody();
            }
            strBuf.append( content );

            strBuf.append( "</tr>" );
            strBuf.append( "</table>" );
            strBuf.append( "</td>" );
            strBuf.append( "</tr>" );
            strBuf.append( "</table>" );
            //System.out.println(strBuf.toString());
            this.pageContext.getOut().print( strBuf.toString() );
        }
        catch( Exception ex ){
            ex.printStackTrace();
        }
        return this.EVAL_PAGE;
    }
}
