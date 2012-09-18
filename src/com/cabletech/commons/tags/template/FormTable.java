package com.cabletech.commons.tags.template;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class FormTable extends BodyTagSupport{

    private String th1 = "项目";
    private String th2 = "填写";
    //ysj add 目的是能够控制一列的宽度
    private String namewidth = "";
    private String contentwidth = "";

    public String getNamewidth(){
        return this.namewidth;
    }


    public String getContentwidth(){
        return this.contentwidth;
    }


    public void setNamewidth( String namewidth ){
        this.namewidth = namewidth;
    }


    public void setContentwidth( String contentwidth ){
        this.contentwidth = contentwidth;
    }


    //ysj add end


    public int doEndTag() throws JspException{
        try{
            StringBuffer strBuf = new StringBuffer();
            if( ( getNamewidth() != null && !getNamewidth().equals( "" ) ) && ( getContentwidth() != null
                && !getContentwidth().equals( "" ) ) ){
                strBuf.append( "<table id='formtable' width=\"" + String.valueOf( Integer.parseInt( getNamewidth() )
                    + Integer.parseInt( getContentwidth() ) ) + "px\"" );
                strBuf.append( " border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"0\" class=\"tabout\">" );
            }
            else{
                strBuf.append(
                    "<table id='formtable' width=\"90%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"0\" class=\"tabout\">" );
            }

            strBuf.append( "<tr>" );
            strBuf.append( "<td" );
            if( getNamewidth() != null ){
                strBuf.append( " width=\"" + getNamewidth() + "\"" );
            }
            strBuf.append( "></td>" );

            strBuf.append( "<td" );
            if( getContentwidth() != null ){
                strBuf.append( " width=\"" + getContentwidth() + "\"" );
            }
            strBuf.append( "></td>" );
            strBuf.append( "</tr>" );
            String content = null;

            if( bodyContent != null ){
                content = bodyContent.getString();
                bodyContent.clearBody();
            }

            strBuf.append( content );
            strBuf.append( "</table>" );

            //System.out.println(strBuf.toString());
            this.pageContext.getOut().print( strBuf.toString() );
        }
        catch( Exception ex ){
            ex.printStackTrace();
        }
        return this.EVAL_PAGE;
    }


    public String getTh1(){
        return th1;
    }


    public void setTh1( String th1 ){
        this.th1 = th1;
    }


    public String getTh2(){
        return th2;
    }


    public void setTh2( String th2 ){
        this.th2 = th2;
    }

}
