package com.cabletech.commons.base;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.config.*;
import com.cabletech.commons.services.*;
import com.cabletech.commons.web.*;

/**
 * <p>Base servlet encapsulating common servlet functionality.</p>
 *
 * @author Copyright (c) 2003 by 北京 鑫干线. All Rights Reserved.
 * @deprecated
 */
public abstract class BaseAction extends Action{
    private static Logger logger = Logger.getLogger( BaseAction.class.getName() );

    /**
     * <p>Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded.
     * <br>
     * All subclasses override this method with specific action functionality.
     * </p>
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form  The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response  The HTTP response we are creating
     */
    public abstract ActionForward executeAction( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception;


    /**
     * <p>Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded.
     * <br>
     * Encapsulates common action functionality including error handling.
     * </p>
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form  The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response  The HTTP response we are creating
     */
    public ActionForward execute( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        ActionForward forward;
        try{
            forward = executeAction( mapping, form, request, response );
        }
        catch( Exception e ){
            logger.error( "出现异常", e );
            e.printStackTrace();
            return handleException( e, request, mapping );
        }
        return forward;
    }


    /**
     * <p>Removes an attribute from HttpSession.</p>
     *
     * @param req  The HTTP request we are processing
     * @param name  The name of the attribute to be removed.
     */
    protected void removeSessionAttribute( HttpServletRequest req, String name ){
        logger.debug( "Removing " + name + " from session." );
        HttpSession session = req.getSession( false );
        if( session != null ){
            session.removeAttribute( name );
        }
    }


    /**
     * <p>Sets an object to the HttpSession</p>
     *
     * @param req  The HTTP request we are processing
     * @param name  The name key of object.
     * @param obj  The object to be set on HttpSession.
     */
    protected void setSessionAttribute( HttpServletRequest req,
        String name,
        Object obj ){
        logger.debug( "Setting " + name + " of type "
            + obj.getClass().getName() + " on session." );
        HttpSession session = req.getSession( false );
        if( session != null ){
            session.setAttribute( name, obj );
        }
    }


    /**
     * <p>Retrieves an object from HttpSession.</p>
     *
     * @param req  The HTTP request we are processing
     * @param name  The name of the attribute to be retrieved.
     */
    protected Object getSessionAttribute( HttpServletRequest req, String name ){
        logger.debug( "Getting " + name + " from session." );
        Object obj = null;
        HttpSession session = req.getSession( false );
        if( session != null ){
            obj = session.getAttribute( name );
        }
        return obj;
    }


    /**
     * <p>Prints values set on the current HttpSession.</p>
     *
     * @param req  The HTTP request we are processing
     */
    protected void printSession( HttpServletRequest req ){
        HttpSession session = req.getSession( false );
        if( session != null ){
            Enumeration senum = session.getAttributeNames();
            logger.debug( "Session contents:" );
            while( senum.hasMoreElements() ){
                logger.debug( "    " + senum.nextElement() );
            }
        }
    }


    /**
     * <p>Get localize message.</p>
     *
     * @param key  The HTTP request we are processing
     */
    protected String getMessage( HttpServletRequest request, String key ){
        Locale locale = getLocale( request );
        return getResources( request ).getMessage( locale, key );
    }


    /**
     * <p>Get instance of message properties.</p>
     *
     * @param request  The HTTP request we are processing
     */
    protected MessageProperties getMessageProps( HttpServletRequest
        request ){
        Locale locale = getLocale( request );
        return MessageProperties.getInstance( locale, getResources( request ) );
    }


    /**
     * <p>Formulates and throws client exception.<p>
     *
     * @param th
     * @param mapping
     * @param redirect
     * @throws ClientException
     */
    protected void throwClientException( Throwable th,
        ActionMapping mapping,
        String redirect ) throws ClientException{
        logger.error( th.getMessage() );
        String errorLink = WebAppUtils.getServletName( mapping, redirect );
        logger.debug( "errorLink: " + errorLink );
        throw new ClientException( th, errorLink );
    }


    /**
     * <p>Uniform way of handling exceptions.<p>
     *
     * @param th
     * @param mapping
     * @param request
     *
     * @return ActionForward
     */
    protected ActionForward handleException( Throwable th,
        HttpServletRequest request,
        ActionMapping mapping ){
        /*
          if (th instanceof ClientException) {
              logger.error(th.getMessage());
              String redirectLink = ( (ClientException) th).getLink();
              logger.info("Redirect link: " + redirectLink);
         ErrorBean errorBean = new ErrorBean(MedRecWebAppUtils.getRootErrMsg(
                  th),
                                                  redirectLink);
              request.setAttribute("errorBean", errorBean);
              return mapping.findForward("error");
          } else {
              logger.error(th.getMessage());
              String link = MedRecWebAppUtils.getServletName(mapping, "home");
              logger.info("Redirect link: " + link);
         ErrorBean errorBean = new ErrorBean(MedRecWebAppUtils.getRootErrMsg(
                  th),
                                                  link);
              request.setAttribute("errorBean", errorBean);
              return mapping.findForward("error");
          }*/
        return null;
    }


    /**
     * <p>This method determines the next page to which a successful login
     * should be redirected too. If a user navigates to a secure page, security
     * will redirect page to the login page.  If that user provides accurrate
     * login credentials then they are redirect page to their initial page. This
     * is done by ServletAuthentication.getTargetURLForFormAuthentication().</p>
     */
    protected ActionForward getRedirectPage( HttpServletRequest request,
        ActionMapping mapping ){
        logger.debug( "Getting next redirect page." );

        // Declare local variables.
        String urlRedirect;
        ActionForward forward = mapping.findForward( "login.success" );
        /*
                 // Return user to originating page.
                 urlRedirect =
            ServletAuthentication.getTargetURLForFormAuthentication(request.
            getSession());

                 // Determine redirection.
                 if (MedRecWebAppUtils.isNotEmpty(urlRedirect)) {
            // Check to see if use came from logout page. If so, assume relogin.
            if (urlRedirect.indexOf("logout") > -1) {
         urlRedirect = MedRecWebAppUtils.getFullUrlPath(request, mapping,
                    "login.success");
            } else {
                // Prepend redirect page to url path.
                urlRedirect = MedRecWebAppUtils.getUrlRoot(request) +
                    urlRedirect;
            }
            logger.debug("Redirecting to originating page: " + urlRedirect);
            forward = new ActionForward(urlRedirect, true);
                 }*/
        return forward;
    }


    private AppService dbfacade;
    public AppService getAppService(){
        if( dbfacade == null ){
            return( dbfacade = new AppService() );
        }
        return dbfacade;
    }


    private DBService dbService;
    public DBService getDbService(){
        if( dbService == null ){
            return( dbService = new DBService() );
        }
        return dbService;
    }


    /**
     * 系统日志用
     * @param request HttpServletRequest
     * @param msg String
     * @param module String
     * @param memo String
     */
    public void log( HttpServletRequest request, String msg, String module,
        String memo ){
        com.cabletech.commons.businesslog.LogManager logManager = new com.
            cabletech.commons.businesslog.LogManager();
      //  String ip = request.getRemoteHost() + " : " + request.getRemoteAddr();
        String ip = request.getRemoteAddr();
        HttpSession session = request.getSession( true );
        UserInfo UserInfo = ( UserInfo )session.getAttribute( "LOGIN_USER" );
        String username = UserInfo.getUserName();
        com.cabletech.commons.businesslog.LogManager.getLogManager().log( username, module, msg, ip, memo );
    }


    public void log( HttpServletRequest request, String msg ){
        log( request, msg, "", "" );
    }


    public void log( HttpServletRequest request, String msg, String module ){
        log( request, msg, module, "" );
    }


    /**
     *
     * @param mapping ActionMapping
     * @param request HttpServletRequest
     * @param strKey String
     * @return ActionForward
     */
    public ActionForward forwardInfoPage( ActionMapping mapping,
        HttpServletRequest request,
        String strKey ){
        return forwardInfoPage( mapping, request, strKey, null );
    }


    public ActionForward forwardInfoPage( ActionMapping mapping,
        HttpServletRequest request,
        String strKey, Object arg0 ){
        Object args[] = new Object[1];
        args[0] = arg0;
        return forwardInfoPage( mapping, request, strKey, args );
    }


    public ActionForward forwardInfoPage( ActionMapping mapping,
        HttpServletRequest request,
        String strKey, Object arg0,
        Object arg1 ){
        Object args[] = new Object[2];
        args[0] = arg0;
        args[1] = arg1;
        return forwardInfoPage( mapping, request, strKey, args );
    }


    public ActionForward forwardInfoPage( ActionMapping mapping,
        HttpServletRequest request,
        String strKey, Object args[] ){
        HashMap mapMsg = ( HashMap )super.getServlet().getServletContext().
                         getAttribute( "SYSTEM_MSG" );
        if( mapMsg == null  || mapMsg.isEmpty()){
            mapMsg = ParseMsgInfo.getInstance().getMsgs();
            getServlet().getServletContext().setAttribute( "SYSTEM_MSG", mapMsg );
        }
        MsgInfo msg = ( MsgInfo )mapMsg.get( strKey );
        if( msg != null ){
            msg.setArgs( args );
            request.setAttribute( "MESSAGEINFO", msg );
        }
        return mapping.findForward( "sucessMsg" );
    }


    public UserInfo getLoginUserInfo( HttpServletRequest req ){
        HttpSession session = req.getSession( true );
        return( UserInfo )session.getAttribute( "LOGIN_USER" );
    }
}
