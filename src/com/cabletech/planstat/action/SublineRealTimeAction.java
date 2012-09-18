package com.cabletech.planstat.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.planstat.domainobjects.SublineRequest;
import com.cabletech.planstat.domainobjects.SublineResponse;
import com.cabletech.planstat.services.SublineRealTimeBO;
import com.cabletech.planstat.util.HttpClientAgent;

public class SublineRealTimeAction extends PlanStatBaseDispatchAction{
	private Logger logger = Logger.getLogger( SublineRealTimeAction.class.getName() );
	public SublineRealTimeAction(){
		
	}
	
	public ActionForward querySublineRealTime( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
		if( userinfo.getType().equals("22") ){
		   return mapping.findForward("inputSublineForRequest");
		   //return mapping.findForward("inputSublineForRequestAjax");
		}
		return forwardInfoPage( mapping, request, "1204invalid" );
	}
	
	public ActionForward sendRealTimeRequest( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ) throws ClientException,Exception{
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
        String strSublineList = request.getParameter( "strsublineidlist" ).trim();
        if (strSublineList.endsWith("|")){
        	strSublineList = strSublineList.substring(0, strSublineList.length()-1);
        }
        StringTokenizer token=new StringTokenizer(strSublineList,"|");
        String[] sublineList=new String[token.countTokens()];
        int i=0;
        while(token.hasMoreTokens()){
        	sublineList[i]=token.nextToken(); 
            i++;   
        }
        //检查是否在约定时间内重复提交
        SublineRealTimeBO bo = new SublineRealTimeBO();
        String IsReCommit = bo.checkReCommit(userinfo,sublineList);
        logger.info("IsReCommit : " + IsReCommit);
        if ("1".equals(IsReCommit)){
        	return forwardInfoPage( mapping, request, "120402" );
        }
        //往请求表中插入请求数据
        SublineRequest data = new SublineRequest();
        String dataRequestId = super.getDbService().getSeq( "subline_request", 10 ) ;
        data.setRequestid(dataRequestId);
        data.setRequesttime(new Timestamp(new Date().getTime()));
        data.setUserid(userinfo.getUserID());
        super.getService().addRealTimeSublineRequest(data);
        
        //住回复表中写入相关信息（包括ID，请求ID，巡检线段等信息）
        SublineResponse responseData = null;
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.ENGLISH);
        //logger.info("the sysdate is:" + sdf.format(new Date()));
        String dataResponseId;
        for(i=0;i<sublineList.length;i++){
        	responseData = new SublineResponse();
        	dataResponseId = super.getDbService().getSeq( "subline_response", 10 ) ;
        	responseData.setId(dataResponseId);
        	responseData.setRequestid(dataRequestId);
        	responseData.setSublineid(sublineList[i]); 
        	responseData.setNonpatrolnum(new Integer(0));
        	responseData.setTodaypointnum(new Integer(0));
        	responseData.setPlanpointnum(new Integer(0));
        	responseData.setActualpointnum(new Integer(0));
        	responseData.setState("0");
        	//responseData.setDeadlinedays("");
        	super.getService().addRealTimeSublineResponse(responseData);
        }
        //发送httpclient请求,将请求ID发给后台统计程序
        HttpClientAgent httpClientAgent = new HttpClientAgent();
        String result = httpClientAgent.SendSublineHttpClient(dataRequestId,request);
        log( request, " 提交巡检线段实时查询请求 ", " 巡检分析 " );
        GisConInfo config = GisConInfo.newInstance();
    	String minutesreqandres = config.getMinutesreqandres();
        if ("1".equals(result)){
        	return forwardInfoPage( mapping, request, "120401ok",minutesreqandres);
        }
        return forwardInfoPage( mapping, request, "120401failure" );
	}
	
	public ActionForward showSublineList( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		String sublineName = request.getParameter("sublineName");
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		if( userinfo.getType().equals("22") ){
			SublineRealTimeBO bo = new SublineRealTimeBO();
			List sublineList = bo.getSublineListByInput(sublineName,userinfo);
			request.getSession().setAttribute( "sublineListRealtime", sublineList );
		}else{
			request.getSession().setAttribute( "sublineListRealtime", null );
		}
		return mapping.findForward("showsublinerealtime");
	}
	
	public ActionForward showRequestInfo( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
		if( !userinfo.getType().equals("22") ){
		   return forwardInfoPage( mapping, request, "1204invalid" );
		}
		SublineRealTimeBO bo = new SublineRealTimeBO();
		List list = bo.getRequestSublineList(userinfo);
		request.getSession().setAttribute( "requestSublineList", list );
		return mapping.findForward("showRequestSublineInfo");
	}
	
	public ActionForward showResponsePerSubline( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
		if( !userinfo.getType().equals("22") ){
		   return forwardInfoPage( mapping, request, "1204invalid" );
		}
		String sublineID = request.getParameter("id");
		String requestid = request.getParameter("requestid");
		String sublineName = request.getParameter("sublinename");
		String requestTime = request.getParameter("requesttime");
		SublineRealTimeBO bo = new SublineRealTimeBO();
		List list = bo.getResponsePerSubline(sublineID,requestid);
		request.getSession().setAttribute( "requestSublineList", list );
		request.getSession().setAttribute( "sublineNameRTRequest", sublineName );
		request.getSession().setAttribute( "requestTimeRT", requestTime );
		
		return mapping.findForward("showResponsePerSubline");
	}
	
	public ActionForward showRealTimeUnpatrol( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
		if( !userinfo.getType().equals("22") ){
		   return forwardInfoPage( mapping, request, "1204invalid" );
		}
		String sublineID = request.getParameter("sublineid");
		logger.info("sublineid is:" + sublineID);
		SublineRealTimeBO bo = new SublineRealTimeBO();
		List list = bo.getResponseUnpatrol(sublineID);
		request.getSession().setAttribute( "sublineUnpatrol", list );
		return mapping.findForward("showResponseSublineUnpatrol");
	}
	
	public ActionForward showRealTimeToday( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
		if( !userinfo.getType().equals("22") ){
		   return forwardInfoPage( mapping, request, "1204invalid" );
		}
		String sublineID = request.getParameter("sublineid");
		logger.info("sublineid is:" + sublineID);
		SublineRealTimeBO bo = new SublineRealTimeBO();
		List list = bo.getResponseToday(sublineID);
		request.getSession().setAttribute( "sublineToday", list );
		return mapping.findForward("showResponseSublineToday");
	}
	
	public ActionForward sendRealTimeRequestAjax( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ) throws ClientException,Exception{
        //UserInfo userinfo = ( UserInfo )request.getSession().getAttribute("LOGIN_USER" );
        String sublineName = request.getParameter("inputName");
        logger.info("sublineName:" + sublineName);
        logger.info("sublineID:" + request.getParameter("inputID"));
        return null;
	}

}
