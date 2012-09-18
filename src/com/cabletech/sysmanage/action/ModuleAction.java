package com.cabletech.sysmanage.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.web.*;
import com.cabletech.sysmanage.beans.*;
import com.cabletech.sysmanage.domainobjects.*;

public class ModuleAction extends SystemBaseDispatchAction{

    public ActionForward addModule( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        ModuleBean bean = ( ModuleBean )form;
        Module data = new Module();
        BeanUtil.objectCopy( bean, data );
        //data.setId(super.getDbService().getSeq("systemmodulemaster", 8));

        super.getService().addModule( data );

        return mapping.findForward( "" );
    }


    /**
     * 区的模块列表
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward getModuleList( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        Vector moduleVct = super.getService().getMoList();
        request.setAttribute( "moduleVct", moduleVct );

        return mapping.findForward( "moduleListPage" );
    }

}
