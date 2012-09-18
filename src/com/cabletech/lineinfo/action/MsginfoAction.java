package com.cabletech.lineinfo.action;

import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.web.*;
import com.cabletech.lineinfo.beans.*;
import com.cabletech.lineinfo.domainobjects.*;

public class MsginfoAction extends LineInfoBaseDispatchAction{
    private Logger logger = Logger.getLogger( MsginfoAction.class );

    public MsginfoAction(){
    }


    public ActionForward addMsginfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        logger.info( "addMsginfo" );
        MsginfoBean bean = ( MsginfoBean )form;

        Msginfo data = new Msginfo();
        BeanUtil.objectCopy( bean, data );

        //MsginfoDAOImpl dao = new MsginfoDAOImpl();
        //dao.addMsginfo(data);
        super.getService().createMsginfo( data );
        return forwardInfoPage( mapping, request, "0001" );
    }

}
