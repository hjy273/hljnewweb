package com.cabletech.baseinfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.beans.TroubleCodeBean;
import com.cabletech.baseinfo.beans.TroubleTypeBean;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.TroubleCodeBo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.power.CheckPower;

public class TroubleCodeAction extends BaseInfoBaseDispatchAction{
    private static Logger logger = Logger.getLogger( TroubleCodeAction.class );

    private TroubleCodeBo troublecodemgr = new TroubleCodeBo();


    /**
     * ��������������͡����������
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward load_TroubleCode( ActionMapping mapping,
        ActionForm form, HttpServletRequest request,
        HttpServletResponse response ) throws ClientException, Exception{
    	TroubleCodeBean bean = (TroubleCodeBean)form;
    	request.getSession().setAttribute("S_BACK_URL",null); //
        request.getSession().setAttribute("theQueryBean", bean);//
        if( !CheckPower.checkPower( request.getSession(), "72201" ) ){
            return mapping.findForward( "powererror" );
        }

        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        List list = troublecodemgr.get_TroubleTypeObject( userinfo.getRegionid() );
        request.getSession().setAttribute( "Troublecode", list );
        super.setPageReset(request);
        return mapping.findForward( "showTroubleCode" );

    }


    //
    /***
     * ��ʾ���������ҳ��     *
     */
    public ActionForward show_add_TroubleCode( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "72202" ) ){
            return mapping.findForward( "powererror" );
        }

        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        List type = troublecodemgr.getTroubleType( userinfo.getRegionID() );
        List allCode = troublecodemgr.getAllTroubleCode();
        request.setAttribute( "type", type );
        request.setAttribute( "allcode", allCode );

        return mapping.findForward( "showadd" );
    }


    /***
     * ���������     *
     */
    public ActionForward add_TroubleCode( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "72202" ) ){
            return mapping.findForward( "powererror" );
        }

        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        TroubleCodeBean bean = ( TroubleCodeBean )form;
        String id = super.getDbService().getSeq( "troublecode", 10 );
        String regionid = userinfo.getRegionID();
        bean.setRegionid( regionid );
        bean.setId( id );
        if( !troublecodemgr.add_TroubleCode( bean ) ){
            return forwardErrorPage( mapping, request, "e72202" );
        }
        log(request,"��������루���������ƣ�"+bean.getTroublename()+"��","ϵͳ����");
        return forwardInfoPage( mapping, request, "72202" );
    }


    /***
     * �༭��������ʾҳ��     *
     */
    public ActionForward show_edit_TroubleCode( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "72204" ) ){
            return mapping.findForward( "powererror" );
        }

        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String id = request.getParameter( "id" );

        TroubleCodeBean bean = troublecodemgr.get_TroubleCode( id );
        List type = troublecodemgr.getTroubleType( userinfo.getRegionID() );
        List allCode = troublecodemgr.getAllTroubleCode();

        request.setAttribute( "type", type );
        request.setAttribute( "allcode", allCode );
        request.setAttribute( "bean", bean );

        return mapping.findForward( "showedit" );
    }

    
    public String getTotalQueryString(String queryString,TroubleCodeBean bean){
    	if (bean.getTroublecode() != null){
    		queryString = queryString + bean.getTroublecode();
    	}
    	if (bean.getId()!= null){
    		queryString = queryString + "&id=" + bean.getId();
    	}
    	if (bean.getTroubletype() != null){
    		queryString = queryString + "&troubletype=" + bean.getTroubletype();
    	}
    	if (bean.getRegionid() != null){
    		queryString = queryString + "&regionid=" + bean.getRegionid();
    	}
    	if (bean.getTroublename() != null){
    		queryString = queryString + "&troublename=" + bean.getTroublename();
    	}
    	return queryString;
    }
    
    /***
     * �༭������     *
     */
    public ActionForward edit_TroubleCode( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "72204" ) ){
            return mapping.findForward( "powererror" );
        }

        TroubleCodeBean bean = ( TroubleCodeBean )form;

        if( !troublecodemgr.edit_TroubleCode( bean ) ){
            return forwardErrorPage( mapping, request, "e72204" );
        }

		 String strQueryString = getTotalQueryString("method=TroubleCode&troublecod=",(TroubleCodeBean)request.getSession().getAttribute("theQueryBean"));
		 Object[] args = getURLArgs("/WebApp/TroubleCodeAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
		 log(request,"�༭�����루���������ƣ�"+bean.getTroublename()+"��","ϵͳ����");
		 return forwardInfoPage( mapping, request, "72204",null,args);

    }


    /***
     *ɾ��������     *
     */
    public ActionForward delete_TroubleCode( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	TroubleCodeBo troubleCodeBo=new TroubleCodeBo();
        if( !CheckPower.checkPower( request.getSession(), "72205" ) ){
            return mapping.findForward( "powererror" );
        }

        TroubleCodeBean bean = ( TroubleCodeBean )form;
        String name=troubleCodeBo.get_TroubleCode(bean.getId()).getTroublename();
        if( !troublecodemgr.delete_TroubleCode( bean ) ){
            return forwardErrorPage( mapping, request, "e72205" );
        }
		 String strQueryString = getTotalQueryString("method=load_TroubleCode&troublecod=",(TroubleCodeBean)request.getSession().getAttribute("theQueryBean"));
		 Object[] args = getURLArgs("/WebApp/TroubleCodeAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
		 log(request,"ɾ�������루���������ƣ�"+name+"��","ϵͳ����");
		 return forwardInfoPage( mapping, request, "72205",null,args);
    }


    ///////////////////////////��������/////////////////
    /**
     * ��ʾ������������
     */
    public ActionForward load_TroubleType( ActionMapping mapping,
        ActionForm form, HttpServletRequest request,
        HttpServletResponse response ) throws ClientException, Exception{
        if( !CheckPower.checkPower( request.getSession(), "72206" ) ){
            return mapping.findForward( "powererror" );
        }

        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        List list = troublecodemgr.get_AllTroubleType( userinfo.getRegionid() );
        request.getSession().setAttribute( "Troubletype", list );
        return mapping.findForward( "showTroubleType" );

    }


    /***
     * ��ʾ�����������ҳ��     *
     */
    public ActionForward show_Add_TroubleType( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "72207" ) ){
            return mapping.findForward( "powererror" );
        }

        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        List alltype = troublecodemgr.getAllTroubleType();
        request.setAttribute( "alltype", alltype );

        return mapping.findForward( "showaddType" );

    }


    /***
     * �����������*
     */
    public ActionForward add_TroubleType( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "72207" ) ){
            return mapping.findForward( "powererror" );
        }

        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        TroubleCodeBean bean = ( TroubleCodeBean )form;
        String id = super.getDbService().getSeq( "troubletype", 10 );
        String regionid = userinfo.getRegionID();
        bean.setRegionid( regionid );
        bean.setId( id );
        if( !troublecodemgr.add_TroubleType( bean ) ){
            return forwardErrorPage( mapping, request, "e72207" );
        }
        log(request,"������������ͣ���������Ϊ��"+bean.getTroublename()+"��","ϵͳ����");
        return forwardInfoPage( mapping, request, "72207" );
    }


    /***
     * �༭����������ʾҳ��     *
     */
    public ActionForward show_edit_TroubleType( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "72208" ) ){
            return mapping.findForward( "powererror" );
        }

        String id = request.getParameter( "id" );

        List alltype = troublecodemgr.getAllTroubleType();
        request.setAttribute( "alltype", alltype );

        TroubleCodeBean bean = troublecodemgr.get_TroubleType( id );
        request.setAttribute( "bean", bean );

        return mapping.findForward( "showeditType" );
    }


    /***
     * �༭������     *
     */
    public ActionForward edit_TroubleType( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "72208" ) ){
            return mapping.findForward( "powererror" );
        }

        TroubleCodeBean bean = ( TroubleCodeBean )form;

        if( !troublecodemgr.edit_TroubleType( bean ) ){
            return forwardErrorPage( mapping, request, "e72208" );
        }
        log(request,"�༭���������ͣ���������Ϊ��"+bean.getTroublename()+"��","ϵͳ����");
        return forwardInfoPage( mapping, request, "72208" );
    }


    /***
     *ɾ��������     *
     */
    public ActionForward delete_TroubleType( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "72209" ) ){
            return mapping.findForward( "powererror" );
        }

        TroubleCodeBean bean = ( TroubleCodeBean )form;

        if( !troublecodemgr.delete_TroubleType( bean ) ){
            return forwardErrorPage( mapping, request, "e72209" );
        }
        log(request,"ɾ�����������ͣ���������Ϊ��"+bean.getTroublename()+"��","ϵͳ����");
        return forwardInfoPage( mapping, request, "72209" );
    }


    //////////////////////////////////�·�������////////////////
    //showSend_TroubleCode
    //�·������룭����ʾ
    public ActionForward showSend_TroubleCode( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        List lSimNumber = super.getService().getSimNumber( userinfo );

        request.setAttribute( "lSimNumber", lSimNumber );
        return mapping.findForward( "showSend_TroubleCode" );
    }


    //�·�������
    public ActionForward send_TroubleCode( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String[] simnumber = request.getParameterValues("simnumber");
        String setnumber = request.getParameter("setnumber");
        if(!troublecodemgr.sendTroubleCodeNew(userinfo,simnumber,setnumber)){
            return forwardErrorPage( mapping, request, "e72210" );
        }
        return forwardInfoPage( mapping, request, "72210" );
    }

}
