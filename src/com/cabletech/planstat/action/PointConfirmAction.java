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
import com.cabletech.planstat.services.PointConfirmBO;
import com.cabletech.planstat.services.SublineRealTimeBO;
import com.cabletech.planstat.util.HttpClientAgent;

public class PointConfirmAction extends BaseDispatchAction {
	private Logger logger = Logger.getLogger( PointConfirmAction.class.getName() );
	public ActionForward queryPointRealTime( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
		if( userinfo.getType().equals("22") ){
		   return mapping.findForward("inputSublineForPointConfirm");
		}
		return forwardInfoPage( mapping, request, "1204invalid" );
	}
	
	public ActionForward showSublineList( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		String sublineName = request.getParameter("sublineName");
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		if( userinfo.getType().equals("22") ){
			PointConfirmBO bo = new PointConfirmBO();
			List sublineList = bo.getSublineListByInput(sublineName,userinfo);
			request.getSession().setAttribute( "sublineForPointConfirm", sublineList );
		}else{
			request.getSession().setAttribute( "sublineForPointConfirm", null );
		}
		return mapping.findForward("showSublineForPointConfirm");
	}
	
	public ActionForward showPointInfo( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
		if( !userinfo.getType().equals("22") ){
		   return forwardInfoPage( mapping, request, "1204invalid" );
		}
		String sublineID = request.getParameter("id");
		String sublineName = request.getParameter("sublinename");
		PointConfirmBO bo = new PointConfirmBO();
		List list = bo.getPointInfo(sublineID);
		request.getSession().setAttribute( "requestSublinePoint", list );
		request.setAttribute( "RealiTimeSublineName", sublineName );
		request.getSession().setAttribute("backSublineName", sublineName);
		request.getSession().setAttribute("backSublineID", sublineID);
		return mapping.findForward("showRealTimePointInfo");
	}
	
	public ActionForward checkPointPatroled( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
		if( !userinfo.getType().equals("22") ){
		   return forwardInfoPage( mapping, request, "1204invalid" );
		}
		String pointID = request.getParameter("pointid");
        //����httpclient����,������ID������̨ͳ�Ƴ���
        HttpClientAgent httpClientAgent = new HttpClientAgent();
        //���صĽ��Ϊ�ַ���,��������,��","����,��һ������Ϊsim����,�ڶ���ΪѲ��ʱ��(ʱ����)
        String param = httpClientAgent.SendPointHttpClient(pointID); 
        log( request, " �ύѲ����Ƿ�Ѳ������ ", " Ѳ����� " );
        logger.info("param is : " + param);
    	String sublineName = (String)request.getSession().getAttribute("backSublineName");
    	String sublineID = (String)request.getSession().getAttribute("backSublineID");
    	Object[] args=new Object[1];
    	args[0]= "/WebApp/PointConfirmAction.do?method=showPointInfo&id=" + sublineID + "&sublinename=" + sublineName;
    	if ("".equals(param)){
        	return forwardInfoPage( mapping, request, "120501failure",null,args);
        }
        StringTokenizer token = new StringTokenizer(param,",");
	    String[] paramlist = new String[token.countTokens()]; 
	    int i=0;
	    while(token.hasMoreTokens()){
	    	paramlist[i]=token.nextToken(); 
	        i++;   
	    }
	    /*
        String[] paramlist = new String[2];
        paramlist[0]="13800000000";
        paramlist[1]="12:00:00";
        */
        return forwardInfoPage( mapping, request, "120501ok",paramlist,args);
	}
	
}