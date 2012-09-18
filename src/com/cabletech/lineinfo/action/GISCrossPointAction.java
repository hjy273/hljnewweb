package com.cabletech.lineinfo.action;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.web.*;
import com.cabletech.lineinfo.beans.*;
import com.cabletech.lineinfo.domainobjects.*;

public class GISCrossPointAction extends LineInfoBaseDispatchAction{

    /**
     * 增加标志点
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward addGISCrossPoint( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        GISCrossPointBean bean = ( GISCrossPointBean )form;
        GISCrossPoint data = new GISCrossPoint();
        BeanUtil.objectCopy( bean, data );
        data.setId( super.getDbService().getSeq( "crosspointpoint", 8 ) );

        super.getService().createGISCrossPoint( data );

        //Log
        log( request, " 增加标记点 ", " 实时监控 " );

        return mapping.findForward( "commonSuc" );
    }


    /**
     *载入选定的标志点
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadGISCrossPoint( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        GISCrossPoint data = super.getService().loadGISCrossPoint( request.
                             getParameter( "sPID" ) );
        GISCrossPointBean bean = new GISCrossPointBean();
        BeanUtil.objectCopy( data, bean );
        request.setAttribute( "GISCrossPointBean", bean );
        return mapping.findForward( "toEditGISCrossPoint" );
    }


    /**
     * 删除标志点
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward deleteGISCrossPoint( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        GISCrossPoint data = super.getService().loadGISCrossPoint( request.
                             getParameter( "id" ) );
        super.getService().removeGISCrossPoint( data );
        //Log
        log( request, " 删除标记点 ", " 实时监控 " );
        return mapping.findForward( "commonSuc" );
    }


    /**
     * 更新标志点
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward updateGISCrossPoint( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //bean.v
        GISCrossPoint data = new GISCrossPoint();
        BeanUtil.objectCopy( ( GISCrossPointBean )form, data );

        super.getService().updateGISCrossPoint( data );
        //Log
        log( request, " 更新标记点 ", " 实时监控 " );
        return mapping.findForward( "commonSuc" );
    }

}
