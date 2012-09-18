package com.cabletech.commons.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class NoSessionFilter implements Filter {
	private static Logger log = Logger.getLogger(NoSessionFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		try {
			HttpServletRequest rq = (HttpServletRequest) request;
			HttpServletResponse rp = (HttpServletResponse) response;
			HttpSession session = rq.getSession();

			String url = "";
			String page = "/";
			String mobilePageDir = "/WebApp/wap";
			url = rq.getRequestURI();
			//          System.out.println( "URL:" + url );
			if (!url.equals(page) && !url.equals("/WebApp/manager/sortUser.jsp")
					&& !url.equals("/WebApp/SortAction.do") && !url.equals("/WebApp/login.do")
					&& !url.equals("/WebApp/SSOLogin.do") && !url.equals("/WebApp/frames/head.jsp")
					&& !url.equals("/WebApp/frames/menu.jsp") && !url.equals("/WebApp/frames/controlframe.jsp")
					&& !url.equals("/WebApp/frames/main.jsp") && !url.equals("/WebApp/frames/foot.jsp")
					&& !url.equals("/WebApp/login/relogin.jsp") && !url.equals("/WebApp/frames/bj/login.jsp")
					&& !url.equals("/WebApp/frames/login.do") && !url.equals("/WebApp/wap/login.jsp")
					&& !url.equals("/WebApp/wap/login.do") && !url.equals("/WebApp/m")
					&& !url.equals("/WebApp/sso/index.do")) {

				if (rq.getSession(false) == null) {
					if (url.indexOf(mobilePageDir) != -1) {
						rp.sendRedirect(mobilePageDir + "/login.do?method=loginForm");
					} else {
						rp.sendRedirect(page);
					}
				} else {
					String regionName = (String) session.getAttribute("LOGIN_USER_REGION_NAME");
					if (regionName == null || session.isNew()) {
						PrintWriter writer = rp.getWriter();
						writer.append("<script type=\"text/javascript\">");
						if (url.indexOf(mobilePageDir) != -1) {
							writer.append("		location='/WebApp/wap/login.do?method=loginForm';");
						} else {
							writer.append("		top.location='/';");
						}
						//writer.append("	window.parent.location='/WebApp';");
						//writer.append("	window.parent.parent.location='/WebApp';");
						writer.append("</script>");
						writer.flush();
						// rp.sendRedirect( page );
					} else {
						chain.doFilter(request, response);
					}
				}
			} else {
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.warn("session过期处理异常:" + e.getMessage());
		}
	}
}
