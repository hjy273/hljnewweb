package com.cabletech.manager.action;

import com.cabletech.sendtask.action.EndorseAction;

import org.apache.log4j.Logger;
import com.cabletech.commons.base.BaseDispatchAction;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import com.cabletech.manager.dao.*;

public class SortAction extends BaseDispatchAction{
    Logger logger = Logger.getLogger( this.getClass().getName() );

    /**
     * 保存排序的结果
     * */
    public ActionForward saveSort( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            if( !request.getParameter( "password" ).equals( "123" ) ){
                request.setAttribute( "type", "passerror" );
                return mapping.findForward( "success" );
            }
            SortDao dao = new SortDao();
            String[] userid = request.getParameterValues( "userid" );
            String[] positionno = request.getParameterValues( "positionno" );
            if( !dao.saveSort1( userid, positionno ) ){
                request.setAttribute( "type", "exerror" );
                return mapping.findForward( "success" );
            }
            else{
                request.setAttribute( "type", "success" );
                return mapping.findForward( "success" );
            }
        }catch(Exception e){
            //System.out.println( "EX:" + e.getMessage() );
            request.setAttribute( "type", "exerror" );
            return mapping.findForward( "success" );
        }
    }

}
