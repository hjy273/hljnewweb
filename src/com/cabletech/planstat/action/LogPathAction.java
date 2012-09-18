package com.cabletech.planstat.action;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.planstat.beans.LogPathBean;
import com.cabletech.planstat.services.LogPathBO;
import com.cabletech.power.CheckPower;

public class LogPathAction   extends BaseDispatchAction{
	private Logger logger = Logger.getLogger(LogPathAction.class.getName());
	public LogPathAction(){
		
	}
    //��ѯ����һ��ģ�������
    public ActionForward queryVisitorsTraffic( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "72401" ) ){
            return mapping.findForward( "powererror" );
        }
    	try{
	    	LogPathBO logPathBO = new LogPathBO();
	    	List regionList = logPathBO.getRegionInfoList();
	        request.getSession().setAttribute( "reginfo", regionList );
	        return mapping.findForward( "queryVisitorsTraffic" );
	        
    	}catch(Exception e){
    		logger.error( "��ѯ����һ��ģ�����������:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
    	}
    }
    
    //���ݽ�����ѡ����������ж��Ƿ�װ�ش�ά��˾�б�(ֻ����"������"ѡ����"��ά��˾"ʱ,�Ż���ִ�ά��˾�б�������)
    public ActionForward loadCon(ActionMapping mapping, ActionForm inForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    String queryType = (String)request.getParameter("queryType");
	    logger.info("in action ,the queryType is:" + queryType);
	    if (queryType.equals("2")){
	    	String regionId = (String)request.getParameter("regionid");
	    	logger.info("in action ,the regionId is:" + regionId);
		    List ConList = null;
		    String html ;
		    logger.info("queryType:" + queryType );
		    LogPathBO logPathBO = new LogPathBO();
		    ConList = logPathBO.getConByTypeAjax(regionId);
	        request.getSession().setAttribute( "coninfo", ConList );
		    html = "<select name=\"conID\" class=\"inputtext\" style=\"width:180px\" id=\"cId\">";
		    html += "<option value=\"0\">����</option>";
		    Iterator itr = ConList.iterator();
		    Object conID,conName;
		    Map row;
		    while(itr.hasNext()){
		    	row=(Map)itr.next();
		    	conID=(String)row.get("CONTRACTORID");
		    	conName=(String)row.get("CONTRACTORNAME");
		    	html += "<option value=\"" + conID + "\">" + conName + "</option>";
		    }
		    html += "</select>";
		    logger.info("html:" + html);
		    // Write the HTML to response
		    response.setContentType("text/html; charset=GBK");
		    PrintWriter out = response.getWriter();
		    out.println(html);
		    out.flush();
	    }
	    return null; // Not forwarding to anywhere, response is fully-cooked

	} // End execute()

    //��ʾ����һ��ģ����������
    public ActionForward showVisitorsTraffic( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response){
    	LogPathBean bean = ( LogPathBean )form;
    	request.getSession().setAttribute("theTrafficBean", bean);
    	LogPathBO logPathBO = new LogPathBO();
    	List VisitorsTrafficList = null;
    	if (!bean.getQueryType().equals("0")){
    		VisitorsTrafficList = logPathBO.getVisitorsTraffic(bean);
    	}else{
    		VisitorsTrafficList = logPathBO.getVisitorsTrafficNoType(bean);
    	}
        if( VisitorsTrafficList.size() == 0 ){
            return forwardInfoPage( mapping, request, "724noresult" );
        }
        request.getSession().setAttribute("SelectedType", bean.getQueryType());
        request.getSession().setAttribute( "visitorsTrafficList", VisitorsTrafficList );
        return mapping.findForward( "showVisitorsTraffic" );
    }

    //��ʾ��ѡ��ĳ��һ��ģ���µĶ���ģ����������
    public ActionForward showSubMenuTraffic( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	String mainMenuID = (String)request.getParameter("mainmenuid");
    	String mainMenuName = (String)request.getParameter("mainmenuname");
        LogPathBean originalBean = (LogPathBean)request.getSession().getAttribute("theTrafficBean");
    	LogPathBO logPathBO = new LogPathBO();
    	List SubTrafficList = null;
    	if (mainMenuID.equals("1") || mainMenuID.equals("6")){  //��һ���˵�Ϊ1��6ʱ,�����˵��Ƕ�̬���ɵ�,����д�����ݿ����
        	if (!originalBean.getQueryType().equals("0")){
        		SubTrafficList = logPathBO.getSubTrafficSpecial(originalBean,mainMenuID);
        	}else{
        		SubTrafficList = logPathBO.getSubTrafficNoTypeSpecial(originalBean,mainMenuID);
        	}
    	}else{
        	if (!originalBean.getQueryType().equals("0")){
        		SubTrafficList = logPathBO.getSubTraffic(originalBean,mainMenuID);
        	}else{
        		SubTrafficList = logPathBO.getSubTrafficNoType(originalBean,mainMenuID);
        	}
    	}
        if( SubTrafficList.size() == 0 ){
            return forwardInfoPage( mapping, request, "724noresult" );
        }
        request.getSession().setAttribute( "subTrafficList", SubTrafficList );
        request.getSession().setAttribute( "mainMenuName", mainMenuName );
        
        return mapping.findForward( "showSubTraffic" );
    } 
}
