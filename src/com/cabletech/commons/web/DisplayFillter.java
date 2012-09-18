package com.cabletech.commons.web;

import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import org.apache.log4j.Logger;
import javax.servlet.Filter;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Enumeration;

public class DisplayFillter implements Filter{

    private  static Logger log = Logger.getLogger( "DisplayFillter" );
    public void init( FilterConfig filterConfig ) throws ServletException{}
    public void destroy(){}
    public void doFilter( ServletRequest request, ServletResponse response,
        FilterChain chain ) throws IOException,
        ServletException{
        String strTotalURL="";
        try{
            HttpServletRequest rq = ( HttpServletRequest )request;
            strTotalURL = rq.getRequestURL() + "?" + rq.getQueryString();
            log.info("filter:" + strTotalURL);
            if (strTotalURL != null && strTotalURL.indexOf("?d-") != -1 && strTotalURL.indexOf("-p")!=-1){
               rq.getSession().setAttribute( "S_BACK_URL", strTotalURL );
               log.info("filter of page:" + strTotalURL);
            }
           chain.doFilter( request, response );
        }catch(Exception e){
            log.warn("URL¥¶¿Ì“Ï≥£:" + e.getMessage());
        }
    }

}
