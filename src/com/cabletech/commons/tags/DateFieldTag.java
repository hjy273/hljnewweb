package com.cabletech.commons.tags;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * Convenience base class for the various input tags for text fields.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 2.7 $ $Date: 2007/04/06 08:21:45 $
 */

public class DateFieldTag extends TagSupport{

    private String property;

    /**
     * Generate the required input tag.
     * <p>
     * Support for indexed property since Struts 1.1
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException{
        StringBuffer results = new StringBuffer();
        results.append( "<INPUT TYPE='BUTTON' VALUE='\u25BC' ID='btn'  onclick=\"JavaScript:GetSelectDate('" + property
            + "')\" STYLE=\"font:'normal small-caps 6pt serif';\" >" );
        try{
            this.pageContext.getOut().print( results.toString() );
        }
        catch( IOException ex ){
            ex.printStackTrace();
        }
        return this.SKIP_BODY;
    }


    /**
     * Release any acquired resources.
     */
    public void release(){
        super.release();
    }


    public void setProperty( String newproperty ){
        this.property = newproperty;
    }


    public String getProperty(){
        return property;
    }
}
