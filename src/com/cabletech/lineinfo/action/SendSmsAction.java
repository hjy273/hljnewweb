package com.cabletech.lineinfo.action;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.web.*;
import com.cabletech.lineinfo.beans.*;
import com.cabletech.lineinfo.domainobjects.*;
import com.cabletech.utils.*;

public class SendSmsAction extends LineInfoBaseDispatchAction{
    public SendSmsAction(){
    }


    /**
     * ·¢ËÍ¶ÌÏûÏ¢
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward sendSMS( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        SmsSendLogBean bean = ( SmsSendLogBean )form;

        //preset the data
        bean.setMsg_id( getDbService().getSeq( "localizermsglog", 10 ) );
        bean.setSendtime( DateUtil.getNowDateString() );
        bean.setSenttime( DateUtil.getNowDateString() );
        bean.setIsresponded( "0" );
        bean.setIssent( "0" );

        //bean.setContent("OK");
        //bean.setSimid("56");
        bean.setType( "1" );

        SmsSendLog data = new SmsSendLog();
        BeanUtil.objectCopy( bean, data );

        //do save
        super.getService().createSmsSendLog( data );

        //return forwardInfoPage(mapping, request, "0202");
        return mapping.findForward( "smsSendResult" );
    }

}
