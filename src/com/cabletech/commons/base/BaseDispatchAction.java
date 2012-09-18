package com.cabletech.commons.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.DispatchActionSupport;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.MsgInfo;
import com.cabletech.commons.config.ParseMsgInfo;
import com.cabletech.commons.services.AppService;
import com.cabletech.commons.services.DBService;
import com.cabletech.commons.web.ClientException;
import com.cabletech.commons.web.MessageProperties;
import com.cabletech.commons.web.WebAppUtils;

/**
 * <p>
 * Base Action encapsulating common lookup dispatch functionality. Use when form
 * page contains mulitple submit buttons.
 * </p>
 * 
 * @author Copyright (c) 2003 by BEA Systems. All Rights Reserved.
 */
public abstract class BaseDispatchAction extends DispatchActionSupport implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	public final static String WAP_ENV = "wap";
    private static Logger logger = Logger.getLogger(BaseDispatchAction.class.getName());

    protected void outPrint(HttpServletResponse response,String outHtml ) throws IOException{
    	response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(outHtml);
		out.flush();
    }
    
    /**
     * <p>
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded. <br>
     * Encapsulates common action functionality including error handling.
     * </p>
     * 
     * @param mapping
     *            The ActionMapping used to select this instance
     * @param form
     *            The optional ActionForm bean for this request (if any)
     * @param request
     *            The HTTP request we are processing
     * @param response
     *            The HTTP response we are creating
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward;
        try {
            // logger.info( "Mapping parm = " + mapping.getParameter() );
            // logger.info( "Button selected = " +
            // request.getParameter( mapping.getParameter() ) );

            // logger.info( "Executing BaseDispatchAction." );
            forward = super.execute(mapping, form, request, response);

        } catch (Exception e) {
            logger.error("出现异常", e);
            e.printStackTrace();
            return handleException(e, request, mapping);
        }
        return forward;
    }

    /**
     * <p>
     * Removes an attribute from HttpSession.
     * </p>
     * 
     * @param req
     *            The HTTP request we are processing
     * @param name
     *            The name of the attribute to be removed.
     */
    protected void removeSessionAttribute(HttpServletRequest req, String name) {
        // logger.debug( "Removing " + name + " from session." );
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute(name);

        }
    }

    /**
     * <p>
     * Sets an object to the HttpSession
     * </p>
     * 
     * @param req
     *            The HTTP request we are processing
     * @param name
     *            The name key of object.
     * @param obj
     *            The object to be set on HttpSession.
     */
    protected void setSessionAttribute(HttpServletRequest req, String name, Object obj) {
        // logger.debug( "Setting " + name + " of type "
        // + obj.getClass().getName() + " on session." );
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.setAttribute(name, obj);
        }
    }

    /**
     * <p>
     * Retrieves an object from HttpSession.
     * </p>
     * 
     * @param req
     *            The HTTP request we are processing
     * @param name
     *            The name of the attribute to be retrieved.
     */
    protected Object getSessionAttribute(HttpServletRequest req, String name) {
        // logger.debug( "Getting " + name + " from session." );
        Object obj = null;
        HttpSession session = req.getSession(false);
        if (session != null) {
            obj = session.getAttribute(name);
        }
        return obj;
    }

    /**
     * <p>
     * Prints values set on the current HttpSession.
     * </p>
     * 
     * @param req
     *            The HTTP request we are processing
     */
    protected void printSession(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Enumeration senum = session.getAttributeNames();
            // logger.debug( "Session contents:" );
            while (senum.hasMoreElements()) {
                logger.debug("     " + senum.nextElement());
            }
        }
    }

//    /**
//     * <p>
//     * Sets user's locale.
//     * </p>
//     * 
//     * @param request
//     *            The HTTP request we are processing
//     */
//    protected void setupLocale(HttpServletRequest request) {
//        // logger.debug( "Setup locale." );
//        Locale locale = WebAppUtils.getLocaleFromCookie(request);
//        if (locale == null) {
//            locale = getLocale(request);
//        }
//        if (!WebAppUtils.isValidLocale(locale)) {
//            // locale = new Locale( "en", "US" );
//        }
//        // logger.debug( "Locale: " + locale );
//        setLocale(request, locale);
//    }

    /**
     * <p>
     * Get localize message.
     * </p>
     * 
     * @param key
     *            The HTTP request we are processing
     */
    protected String getMessage(HttpServletRequest request, String key) {
        Locale locale = getLocale(request);
        return getResources(request).getMessage(locale, key);
    }

    /**
     * <p>
     * Get instance of message properties.
     * </p>
     * 
     * @param request
     *            The HTTP request we are processing
     */
    protected MessageProperties getMessageProps(HttpServletRequest request) {
        Locale locale = getLocale(request);
        return MessageProperties.getInstance(locale, getResources(request));
    }

    /**
     * <p>
     * Formulates and throws client exception.
     * <p>
     * 
     * @param th
     * @param mapping
     * @param redirect
     * @throws ClientException
     */
    protected void throwClientException(Throwable th, ActionMapping mapping, String redirect)
            throws ClientException {
        logger.error(th.getMessage());
        String errorLink = WebAppUtils.getServletName(mapping, redirect);
        // logger.debug( "errorLink: " + errorLink );
        throw new ClientException(th, errorLink);
    }

    /**
     * <p>
     * Uniform way of handling exceptions.
     * <p>
     * 
     * @param th
     * @param mapping
     * @param request
     * 
     * @return ActionForward
     */
    protected ActionForward handleException(Throwable th, HttpServletRequest request,
            ActionMapping mapping) {
        /*
         * if (th instanceof ClientException) { logger.error(th.getMessage());
         * String redirectLink = ( (ClientException) th).getLink();
         * logger.info("Redirect link: " + redirectLink); ErrorBean errorBean =
         * new ErrorBean(MedRecWebAppUtils.getRootErrMsg( th), redirectLink);
         * request.setAttribute("errorBean", errorBean); return
         * mapping.findForward("error"); } else { logger.error(th.getMessage());
         * String link = MedRecWebAppUtils.getServletName(mapping, "home");
         * logger.info("Redirect link: " + link); ErrorBean errorBean = new
         * ErrorBean(MedRecWebAppUtils.getRootErrMsg( th), link);
         * request.setAttribute("errorBean", errorBean); return
         * mapping.findForward("error"); }
         */
        return null;
    }

    private AppService dbfacade;

    public AppService getAppService() {
        if (dbfacade == null) {
            return (dbfacade = new AppService());
        }
        return dbfacade;
    }

    private DBService dbService;

    public DBService getDbService() {
        if (dbService == null) {
            return (dbService = new DBService());
        }
        return dbService;
    }

    /**
     * 系统日志用,日志保存失败时，也要保证业务流程能够顺利的执行下去。
     * 
     * @param request
     *            HttpServletRequest
     * @param msg
     *            String  操作信息
     * @param module
     *            String  业务模块
     * @param memo
     *            String  修改记录数据
     */
    public void log(HttpServletRequest request, String msg, String module, String memo) {
        try{
        String ip = request.getRemoteAddr();
        HttpSession session = request.getSession(true);
        UserInfo UserInfo = (UserInfo) session.getAttribute("LOGIN_USER");
        String username = UserInfo.getUserName();
        com.cabletech.commons.businesslog.LogManager.getLogManager().log(username, module, msg, ip, memo);
        }catch(Exception e){
        	logger.error("日志保存时 出错："+e.getMessage());
        }
    }

    public void log(HttpServletRequest request, String msg) {
        log(request, msg, "", "");
    }

    public void log(HttpServletRequest request, String msg, String module) {
        log(request, msg, module, "");
    }

    /**
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @return ActionForward
     */
    public ActionForward forwardInfoPage(ActionMapping mapping, HttpServletRequest request,
            String strKey) {
        return forwardInfoPage(mapping, request, strKey, null);
    }

    public ActionForward forwardInfoPage(ActionMapping mapping, HttpServletRequest request,
            String strKey, Object arg0) {
        Object args[] = new Object[1];
        args[0] = arg0;
        return forwardInfoPage(mapping, request, strKey, args);
    }

    public ActionForward forwardInfoPage(ActionMapping mapping, HttpServletRequest request,
            String strKey, Object arg0, Object arg1) {
        if (arg1 instanceof Object[]) {
            Object[] msgArgs = new Object[] { arg0 };
            return forwardInfoPage(mapping, request, strKey, msgArgs, (Object[]) arg1);
        }
        Object args[] = new Object[2];
        args[0] = arg0;
        args[1] = arg1;
        return forwardInfoPage(mapping, request, strKey, args);
    }

    public ActionForward forwardInfoPage(ActionMapping mapping, HttpServletRequest request,
            String strKey, Object args[]) {
        return forwardInfoPage(mapping, request, strKey, args, null);
    }

    public ActionForward forwardInfoPage(ActionMapping mapping, HttpServletRequest request,
            String strKey, Object[] msgArgs, Object[] lnkArgs) {
        HashMap mapMsg = (HashMap) super.getServlet().getServletContext()
                .getAttribute("SYSTEM_MSG");

        if (mapMsg == null  || mapMsg.isEmpty()) {
            mapMsg = ParseMsgInfo.getInstance().getMsgs();
            
            getServlet().getServletContext().setAttribute("SYSTEM_MSG", mapMsg);
        }
        // logger.info("Find key:"+strKey);
        // logger.info("mapMsg:"+mapMsg.toString());
        MsgInfo msg = (MsgInfo) mapMsg.get(strKey);
        if (msg != null) {
            msg.setArgs(msgArgs);
            msg.setLinkArg(lnkArgs);
            request.setAttribute("MESSAGEINFO", msg);
        } else {
            logger.error("找不到Key:" + strKey);
        }
        return mapping.findForward("sucessMsg");
    }
    public ActionForward forwardInfoPageUrl(ActionMapping mapping, HttpServletRequest request,
            String strKey, Object[] msgArgs, String link) {
        HashMap mapMsg = (HashMap) super.getServlet().getServletContext()
                .getAttribute("SYSTEM_MSG");

        if (mapMsg == null  || mapMsg.isEmpty()) {
            mapMsg = ParseMsgInfo.getInstance().getMsgs();
            getServlet().getServletContext().setAttribute("SYSTEM_MSG", mapMsg);
        }
        MsgInfo msg = (MsgInfo) mapMsg.get(strKey);
        if (msg != null) {
            msg.setArgs(msgArgs);
            msg.setLink(link);
            request.setAttribute("MESSAGEINFO", msg);
        } else {
            logger.error("找不到Key:" + strKey);
        }
        return mapping.findForward("sucessMsg");
    }


    /**
     * 使用参数Map控制信息页面的返回按钮指向
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @author yangjun
     * @return ActionForward
     */
    public ActionForward forwardInfoPageWithParam(ActionMapping mapping,
            HttpServletRequest request, String strKey, Map args) {
        HashMap mapMsg = (HashMap) super.getServlet().getServletContext()
                .getAttribute("SYSTEM_MSG");

        if (mapMsg == null || mapMsg.isEmpty()) {
            // logger.info("空system_msg >>>>>>>>>>>>>>>>" + mapMsg);
            mapMsg = ParseMsgInfo.getInstance().getMsgs();
            getServlet().getServletContext().setAttribute("SYSTEM_MSG", mapMsg);
            // logger.info(mapMsg.toString());
        }
        // logger.info("Find key:"+strKey);
        // logger.info("mapMsg:"+mapMsg.toString());
        MsgInfo msg = (MsgInfo) mapMsg.get(strKey);
        if (msg != null) {
            if (args != null) {
                Set keySet = args.keySet();
                if (keySet != null) {
                    Iterator it = keySet.iterator();
                    String requestUri = "";
                    if (msg.getLink().indexOf("?") == -1) {
                        requestUri += "?";
                    }
                    while (it != null && it.hasNext()) {
                        String paramName = (String) it.next();
                        String paramValue = (String) args.get(paramName);
                        requestUri += ("&" + paramName + "=" + paramValue);
                    }
                    msg.setRequestUri(requestUri);
                }
            }
            request.setAttribute("MESSAGEINFO", msg);
        } else {
            logger.error("找不到Key:" + strKey);
        }
        return mapping.findForward("sucessMsg");
    }

    /**
     * 使用地址URL控制信息页面的返回按钮指向
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @author yangjun
     * @return ActionForward
     */
    public ActionForward forwardInfoPageWithUrl(ActionMapping mapping, HttpServletRequest request,
            String strKey, String url) {
        HashMap mapMsg = (HashMap) super.getServlet().getServletContext()
                .getAttribute("SYSTEM_MSG");

        if (mapMsg == null || mapMsg.isEmpty()) {
            mapMsg = ParseMsgInfo.getInstance().getMsgs();
            getServlet().getServletContext().setAttribute("SYSTEM_MSG", mapMsg);
        }
        MsgInfo msg = (MsgInfo) mapMsg.get(strKey);
        if (msg != null) {
            msg.setLink(url);
            logger.info(msg.toString());
            request.setAttribute("MESSAGEINFO", msg);
        } else {
            logger.error("找不到Key:" + strKey);
        }
        return mapping.findForward("sucessMsg");
    }

    /**
     * 使用地址URL控制信息页面的返回按钮指向
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @author yangjun
     * @return ActionForward
     */
    public ActionForward forwardWapInfoPageWithUrl(ActionMapping mapping, HttpServletRequest request,
            String strKey, String url) {
        HashMap mapMsg = (HashMap) super.getServlet().getServletContext()
                .getAttribute("SYSTEM_MSG");

        if (mapMsg == null || mapMsg.isEmpty()) {
            mapMsg = ParseMsgInfo.getInstance().getMsgs();
            getServlet().getServletContext().setAttribute("SYSTEM_MSG", mapMsg);
        }
        MsgInfo msg = (MsgInfo) mapMsg.get(strKey);
        if (msg != null) {
            msg.setLink(url);
            logger.info(msg.toString());
            request.setAttribute("MESSAGEINFO", msg);
        } else {
            logger.error("找不到Key:" + strKey);
        }
        return mapping.findForward("sucessWapMsg");
    }

    public UserInfo getLoginUserInfo(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        return (UserInfo) session.getAttribute("LOGIN_USER");
    }

    // ysj add
    public ActionForward forwardErrorPage(ActionMapping mapping, HttpServletRequest request,
            String strKey) {
        return forwardErrorPage(mapping, request, strKey, null);
    }

    public ActionForward forwardErrorPage(ActionMapping mapping, HttpServletRequest request,
            String strKey, Object args[]) {
        HashMap mapMsg = (HashMap) super.getServlet().getServletContext()
                .getAttribute("SYSTEM_MSG");

        if (mapMsg == null || mapMsg.isEmpty()) {
            // logger.info("空system_msg >>>>>>>>>>>>>>>>" + mapMsg);
            mapMsg = ParseMsgInfo.getInstance().getMsgs();
            getServlet().getServletContext().setAttribute("SYSTEM_MSG", mapMsg);
            // logger.info(mapMsg.toString());
        }
        // logger.info("Find key:"+strKey);
        MsgInfo msg = (MsgInfo) mapMsg.get(strKey);
        if (msg != null) {
            logger.info(msg.toString());
            msg.setArgs(args);
            request.setAttribute("MESSAGEINFO", msg);
        } else {
            logger.error("找不到Key:" + strKey);
        }
        return mapping.findForward("errorMsg");
    }

    /**
     * 使用参数Map控制错误页面的返回按钮指向
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @author yangjun
     * @return ActionForward
     */
    public ActionForward forwardErrorPageWithParam(ActionMapping mapping,
            HttpServletRequest request, String strKey, Map args) {
        HashMap mapMsg = (HashMap) super.getServlet().getServletContext()
                .getAttribute("SYSTEM_MSG");

        if (mapMsg == null || mapMsg.isEmpty()) {
            // logger.info("空system_msg >>>>>>>>>>>>>>>>" + mapMsg);
            mapMsg = ParseMsgInfo.getInstance().getMsgs();
            getServlet().getServletContext().setAttribute("SYSTEM_MSG", mapMsg);
            // logger.info(mapMsg.toString());
        }
        // logger.info("Find key:"+strKey);
        MsgInfo msg = (MsgInfo) mapMsg.get(strKey);
        if (msg != null) {
            logger.info(msg.toString());
            if (args != null) {
                Set keySet = args.keySet();
                if (keySet != null) {
                    Iterator it = keySet.iterator();
                    String requestUri = "";
                    if (msg.getLink().indexOf("?") == -1) {
                        requestUri += "?";
                    }
                    while (it != null && it.hasNext()) {
                        String paramName = (String) it.next();
                        String paramValue = (String) args.get(paramName);
                        requestUri += ("&" + paramName + "=" + paramValue);
                    }
                    msg.setRequestUri(requestUri);
                }
            }
            request.setAttribute("MESSAGEINFO", msg);
        } else {
            logger.error("找不到Key:" + strKey);
        }
        return mapping.findForward("errorMsg");
    }

    /**
     * 使用地址URL控制错误页面的返回按钮指向
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @author yangjun
     * @return ActionForward
     */
    public ActionForward forwardErrorPageWithUrl(ActionMapping mapping, HttpServletRequest request,
            String strKey, String url) {
        HashMap mapMsg = (HashMap) super.getServlet().getServletContext()
                .getAttribute("SYSTEM_MSG");

        if (mapMsg == null || mapMsg.isEmpty()) {
            // logger.info("空system_msg >>>>>>>>>>>>>>>>" + mapMsg);
            mapMsg = ParseMsgInfo.getInstance().getMsgs();
            getServlet().getServletContext().setAttribute("SYSTEM_MSG", mapMsg);
            // logger.info(mapMsg.toString());
        }
        // logger.info("Find key:"+strKey);
        MsgInfo msg = (MsgInfo) mapMsg.get(strKey);
        if (msg != null) {
            msg.setLink(url);
            logger.info(msg.toString());
            request.setAttribute("MESSAGEINFO", msg);
        } else {
            logger.error("找不到Key:" + strKey);
        }
        return mapping.findForward("errorMsg");
    }

    /**
     * 使用地址URL控制错误页面的返回按钮指向
     * 
     * @param mapping
     *            ActionMapping
     * @param request
     *            HttpServletRequest
     * @param strKey
     *            String
     * @param args
     *            Map
     * @author yangjun
     * @return ActionForward
     */
    public ActionForward forwardWapErrorPageWithUrl(ActionMapping mapping, HttpServletRequest request,
            String strKey, String url) {
        HashMap mapMsg = (HashMap) super.getServlet().getServletContext()
                .getAttribute("SYSTEM_MSG");

        if (mapMsg == null || mapMsg.isEmpty()) {
            // logger.info("空system_msg >>>>>>>>>>>>>>>>" + mapMsg);
            mapMsg = ParseMsgInfo.getInstance().getMsgs();
            getServlet().getServletContext().setAttribute("SYSTEM_MSG", mapMsg);
            // logger.info(mapMsg.toString());
        }
        // logger.info("Find key:"+strKey);
        MsgInfo msg = (MsgInfo) mapMsg.get(strKey);
        if (msg != null) {
            msg.setLink(url);
            logger.info(msg.toString());
            request.setAttribute("MESSAGEINFO", msg);
        } else {
            logger.error("找不到Key:" + strKey);
        }
        return mapping.findForward("errorWapMsg");
    }

    // 此方法用于有displaytag的页面,调用时,无论相关Session的内容是否为空,都能得到最终需要返回的完整的URL
	public Object[] getURLArgs(String url, String queryString, String totalUrl) {
		Object[] args = new Object[1];
		if (totalUrl != null) {
			if (totalUrl.indexOf(".jsp") != -1) {
				int endIndex = totalUrl.indexOf(".jsp") + 4;
				String FirstPartUrl = totalUrl.substring(0, endIndex);
				args[0] = totalUrl.replaceAll(FirstPartUrl, url);
			}
			else {
				args[0] = url + "?" + queryString;
			}
		} else {
			args[0] = url + "?" + queryString;
		}
		logger.info("args in BaseDispatchAction:" + args[0]);

		return args;
	}

    public String parseParameter(String inputValue, String defaultValue, String prex, String supx) {
        if (inputValue != null && !inputValue.equals("")) {
            return prex + inputValue + supx;
        } else {
            return defaultValue;
        }
    }
    
    public void setPageReset(HttpServletRequest request){
		
		request.getSession().setAttribute("S_BACK_URL", request.getRequestURI()+"?"+request.getQueryString());
		System.out.println(request.getSession().getAttribute("S_BACK_URL"));
    }
    
}
