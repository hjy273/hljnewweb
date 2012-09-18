package com.cabletech.commons.tags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.cabletech.power.*;
import org.apache.log4j.Logger;

/**
 * Convenience base class for the
 *  various input tags for text fields.
 *
 * @author Craig R. McClanahan *
 *
 *
 * @version $Revision: 2.7 $ $Date: 2007/04/06 08:21:45 $
 */

public class CheckPowerTag extends BodyTagSupport{
	private Logger logger = Logger.getLogger(CheckPowerTag.class);
    private String thirdmould;
    private String ishead = "0";

    public int doStartTag() throws JspException{
        boolean flag;
        try{
            flag = CheckPower.checkPower( this.pageContext.getSession(), thirdmould );
            if( flag == true && !ishead.equals( "0" ) ){ //有权限并且是文件头部分，跳过正文处理
                return( SKIP_BODY );
            }
            if( flag == true && ishead.equals( "0" ) ){ //有权限并且不是文件头部分，处理正文部分
                return( EVAL_BODY_INCLUDE );
            }
            if( flag == false && !ishead.equals( "0" ) ){ //无权限并且是文件头部分，处理正文部分
                return( EVAL_BODY_INCLUDE );
            }
            if( flag == false && ishead.equals( "0" ) ){ //无权限并且不是文件头部分，跳过处理正文部分
                return( SKIP_BODY );
            }
        }
        catch( Exception e ){
            logger.error( "CheckPower error:" + e.getMessage() );
        }
        return( SKIP_BODY );
    }


    public int doAfterBody(){
        try{
            this.pageContext.getOut().print( this.getBodyContent().getString() ); //输出包含的正文内容
            return( SKIP_BODY ); //终止jsp正文处理
        }
        catch( Exception e ){
            return( SKIP_BODY ); //终止jsp正文处理
        }

    }


    public void release(){
        super.release();
    }


    public void setThirdmould( String thirdmould ){
        this.thirdmould = thirdmould;
    }


    public String getThirdmould(){
        return this.thirdmould;
    }


    public void setIshead( String ishead ){
        this.ishead = ishead;
        return;
    }


    public String getIshead(){
        return this.ishead;
    }

}
