package com.cabletech.lineinfo.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Cable tech</p>
 * @author not attributable
 * @version 1.0
 */

public class ServletGIS extends HttpServlet{
    private static final String CONTENT_TYPE = "text/html; charset=GB2312";

    //Initialize global variables
    public void init() throws ServletException{
    }


    //Process the HTTP Get request
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        response.setContentType( CONTENT_TYPE );
        String var0 = request.getParameter( "sPID" );
        if( var0 == null ){
            var0 = "";
        }
        String var1 = request.getParameter( "sType" );
        if( var1 == null ){
            var1 = "";
        }
        String var2 = request.getParameter( "sFunID" );
        if( var2 == null ){
            var2 = "";
        }

        PrintWriter out = response.getWriter();
        out.println( "<html>" );
        out.println( "<head><title>ServletGIS</title></head>" );
        out.println( "<body bgcolor=\"#ffffff\">" );

        //request.getRequestDispatcher("/addpoint.jsp").forward(request,response);

        out.println( "<p>The servlet has received a GET. This is the reply.</p>" );
        out.println( "</body></html>" );

    }


    //Clean up resources
    public void destroy(){
    }
}
