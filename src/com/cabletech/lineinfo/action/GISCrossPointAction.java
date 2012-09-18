package com.cabletech.lineinfo.action;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.web.*;
import com.cabletech.lineinfo.beans.*;
import com.cabletech.lineinfo.domainobjects.*;

public class GISCrossPointAction extends LineInfoBaseDispatchAction{

    /**
     * ���ӱ�־��
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
        log( request, " ���ӱ�ǵ� ", " ʵʱ��� " );

        return mapping.findForward( "commonSuc" );
    }


    /**
     *����ѡ���ı�־��
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
     * ɾ����־��
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
        log( request, " ɾ����ǵ� ", " ʵʱ��� " );
        return mapping.findForward( "commonSuc" );
    }


    /**
     * ���±�־��
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
        log( request, " ���±�ǵ� ", " ʵʱ��� " );
        return mapping.findForward( "commonSuc" );
    }

}
