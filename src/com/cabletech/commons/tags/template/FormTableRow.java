package com.cabletech.commons.tags.template;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class FormTableRow extends BodyTagSupport{

	private String name = "";
	public String getName(){
		return name;
	}


	private String isOdd = "true";
	private String tagID;
	private String style;

	private String colspan; //ºÏ²¢ÁÐ

	public void setIsOdd( String isOdd ){
		this.isOdd = isOdd;
	}


	public void setName( String name ){
		this.name = name;
	}


	public int doEndTag() throws JspException{
		try{
			StringBuffer strBuf = new StringBuffer();
			String css = isOdd.equals( "true" ) ? "trcolor" : "trwhite";

			strBuf.append( "<tr class=" + css );
			if( getTagID() != null ){
				strBuf.append( " id=" + getTagID() );
			}
			if( getStyle() != null ){
				strBuf.append( " style=" + getStyle() );
			}
			strBuf.append( " >" );

			if(colspan !=null && colspan.equals("colspan")){
				strBuf.append( "<td colspan='2' class=\"tdulright\">" );            	
			}else{
				strBuf.append( "<td class=\"tdulleft\">" + getName() + "£º</td>" );
				strBuf.append( "<td class=\"tdulright\">" );
			}
			String content = null;
			if( bodyContent != null ){
				content = bodyContent.getString();
				bodyContent.clearBody();
			}
			strBuf.append( content );
			strBuf.append( "</td>" );
			strBuf.append( "</tr>" );
			//System.out.println(strBuf.toString());
			this.pageContext.getOut().print( strBuf.toString() );

		}
		catch( Exception ex ){
			ex.printStackTrace();
		}
		return this.EVAL_PAGE;
	}


	public String getTagID(){
		return tagID;
	}


	public void setTagID( String tagID ){
		this.tagID = tagID;
	}


	public String getStyle(){
		return style;
	}


	public void setStyle( String style ){
		this.style = style;
	}


	public String getColspan() {
		return colspan;
	}


	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

}
