package com.cabletech.commons.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.hb.HibernateSession;

public class SystemFilter implements Filter{
    private static Logger log = Logger.getLogger( SystemFilter.class );
    protected String encoding = null;
    protected FilterConfig filterConfig = null;
    
//	static{
//		//×¢²áÊôÐÔ×ª»»Æ÷
//        BeanUtil.registerConvert();
//	}
	
    //private static ServletConfig sc = null;
    public void init( FilterConfig filterConfig ) throws ServletException{
        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter( "encoding" );
    }


    public void destroy(){
        this.encoding = null;
        this.filterConfig = null;
    }


    public void doFilter( ServletRequest request, ServletResponse response,
        FilterChain chain ) throws IOException,
        ServletException{
        try{
            // Set Character Encoding
            request.setCharacterEncoding( this.encoding );
            chain.doFilter( request, response );
        }
        finally{

            // commit transaction and close hibernate session
            try{
                HibernateSession.currentSession().connection().commit();
                HibernateSession.commitTransaction();
            }
            catch( Exception e ){
                try{
                    HibernateSession.currentSession().connection().rollback();
                    HibernateSession.rollbackTransaction();
                }
                catch( Exception e1 ){}
                log.error( e );
            }
            finally{
                try{
                    // close Hibernate Session
                    HibernateSession.closeSession();
                }
                catch( HibernateException e ){
                    log.error( e );
                }
            }
        }
    }
}
