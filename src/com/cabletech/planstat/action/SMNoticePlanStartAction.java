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
import com.cabletech.planstat.services.SMNoticePlanStartBO;

public class SMNoticePlanStartAction extends BaseDispatchAction{
	private Logger logger = Logger.getLogger( SMNoticePlanStartAction.class.getName() );
	public ActionForward showNoticeList( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		SMNoticePlanStartBO sMBO = new SMNoticePlanStartBO();
		if( userinfo.getType().equals("22") ){
            List smReceiverList = sMBO.getReceiverInfoList(userinfo);
            request.getSession().setAttribute( "smreceiverlist", smReceiverList );
        }
		return mapping.findForward( "showreceiverlist" );
	}
	
	public ActionForward sendMessage( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
        //��õ�ǰ�û��ĵ�λ����
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
	        //���Ͷ���
	       if( request.getSession().getAttribute( "isSendSm" ).equals( "send" ) ){
	        	for (int j=0;j<i;j++){
	        		String simnumber = simNumberList[j].substring(0, simNumberList[j].indexOf(","));
	        		String begindate = simNumberList[j].substring(simNumberList[j].indexOf(",")+1);
	        		logger.info(simnumber);
	        		for(int digit =0;digit < simnumber.length();digit++){
	        			if(!Character.isDigit(simnumber.charAt(digit))){
	        			  isDigit = false;
	        			  break;
	        			}
	        		}
		            if( simnumber != null && !simnumber.equals( "" ) && simnumber.length()>=11 && isDigit){
		                String msg = "�����µ�Ѳ��ƻ���Ҫִ��,��ʼ����Ϊ:" + begindate  + ",������Ϣ����ϵ��ά��˾����Ա!";
		                //SendSMRMI.sendNormalMessage( userinfo.getUserID(), strSimNumberList, msg, "00" );
                        //UPDATE At 2007-12-20
		                SendSMRMI.sendNormalMessage( userinfo.getUserID(), simnumber, msg, "00" );
		                logger.info( msg );
		                return forwardInfoPage( mapping, request, "21801ok" );
		            }else{
		            	return forwardErrorPage( mapping, request, "21801error" );
		            }
	        	}
	        	return forwardInfoPage( mapping, request, "21801error" );
	       }
	       return forwardInfoPage( mapping, request, "21801forbidden" );
        }catch( Exception e ){
            logger.error( "���Ͷ���Ϣ��ʾ�ƻ���ʼִ�г����쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "21801error" );
        }
	}
}
