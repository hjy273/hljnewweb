package com.cabletech.sysmanage.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import com.cabletech.commons.web.*;
import com.cabletech.sysmanage.beans.*;
import com.cabletech.utils.SMSkit.*;

public class SMSTimeAction extends SystemBaseDispatchAction{
    private Logger logger = Logger.getLogger("SMSTimeAction");


	public SMSTimeAction(){
    }


    public ActionForward setTime( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        SMSCenterTime bean = ( SMSCenterTime )form;
        setTime( bean );

        return forwardInfoPage( mapping, request, "0196" );
    }


    public ActionForward getTime( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        SMSCenterTime bean = new SMSCenterTime();

        Vector vct = getTime();
        if( vct != null && vct.size() > 0 ){
            bean.setBegintime( ( String )vct.get( 0 ) );
            bean.setEndtime( ( String )vct.get( 1 ) );
        }

        request.setAttribute( "SMSCenterTime", bean );
        return mapping.findForward( "getSMSBean" );
    }


    /**
     * ����setTime�ӿ�
     * @param request HttpServletRequest
     * @throws Exception
     */
    public void setTime( SMSCenterTime bean ) throws Exception{

        try{
            SmsUtil smsutil = SmsUtil.getInstance();
            smsutil.setTime( bean.getBegintime(), bean.getEndtime() );
            logger.info("���Żʱ�����óɹ�");
        }
        catch( Exception e ){
            logger.error( "����ʱ���쳣 ��" + e.toString() );
        }

    }


    public Vector getTime() throws Exception{
        Vector vct = new Vector();
        try{
            SmsUtil smsutil = SmsUtil.getInstance();
            vct = smsutil.getSettingTime();
            logger.info("ȡ�ö��Żʱ�γɹ�");
        }
        catch( Exception e ){
        	logger.error( "ȡ��ʱ���쳣 ��" + e.toString() );
        }

        return vct;

    }

}
