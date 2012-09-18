package com.cabletech.planstat.action;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.planstat.services.SMNoticePlanEndBO;

public class SMNoticePlanEndAction  extends BaseDispatchAction{
	private Logger logger = Logger.getLogger( SMNoticePlanEndAction.class.getName() );
	public ActionForward showNoticeList( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		SMNoticePlanEndBO sMBO = new SMNoticePlanEndBO();
		if( userinfo.getType().equals("22") ){
            List smReceiverList = sMBO.getReceiverInfoList(userinfo);
            request.getSession().setAttribute( "smreceiverlist", smReceiverList );
        }
		return mapping.findForward( "smreceiverlistforplanend" );
	}
	
	public ActionForward sendMessage( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
        //获得当前用户的单位名称
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
        String strSimNumberList = request.getParameter( "strsimnumberlist" ).trim();
        boolean isDigit = true;
        if (strSimNumberList.endsWith("|")){
        	strSimNumberList = strSimNumberList.substring(0, strSimNumberList.length()-1);
        }
        StringTokenizer token=new StringTokenizer(strSimNumberList,"|");
        String[] simNumberList=new String[token.countTokens()];
        int i=0;
        while(token.hasMoreTokens()){
        	simNumberList[i]=token.nextToken(); 
            i++; 
        }
        try{
	        //发送短信
	       if( request.getSession().getAttribute( "isSendSm" ).equals( "send" ) ){
	        	for (int j=0;j<i;j++){
	        		String[] eachrecord =simNumberList[j].split(",");
	        		String simnumber = eachrecord[0];
	        		String enddate = eachrecord[1];
	        		String planname = eachrecord[2];
	        		//logger.info(simnumber + "--" + enddate + "--" + planname);
	        		for(int digit =0;digit < simnumber.length();digit++){
	        			if(!Character.isDigit(simnumber.charAt(digit))){
	        			  isDigit = false;
	        			  break;
	        			}
	        		}
		            if( simnumber != null && !simnumber.equals( "" ) && simnumber.length()>=11 && isDigit){
		                String msg = "您刚刚结束了一个计划,结束日期为:" + enddate  + ",计划名称为:" + planname;
		                
		                //SendSMRMI.sendNormalMessage( userinfo.getUserID(), strSimNumberList, msg, "00" );
		                //UPDATE At 2007-12-20
		                SendSMRMI.sendNormalMessage( userinfo.getUserID(), simnumber, msg, "00" );
		                
		                logger.info( msg );
		                return forwardInfoPage( mapping, request, "22001ok" );
		            }else{
		            	return forwardErrorPage( mapping, request, "22001error" );
		            }
	        	}
	        	return forwardInfoPage( mapping, request, "22001error" );
	       }
	       return forwardInfoPage( mapping, request, "22001forbidden" );
        }catch( Exception e ){
            logger.error( "发送短信息通知计划完成出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "22001error" );
        }
	}
}
