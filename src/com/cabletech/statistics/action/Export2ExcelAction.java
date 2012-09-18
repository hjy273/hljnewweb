package com.cabletech.statistics.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

public class Export2ExcelAction extends StatisticsBaseAction{

    public ActionForward executeAction( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        Exception{
        String reportType;

        List list = ( List )request.getSession().getAttribute( "QueryResult" );
        reportType = request.getParameter( "reportType" );
        if( reportType.equalsIgnoreCase( "lossDetail" ) ){
            super.getService().ExportLossDetail( list, response );
        }
        if( reportType.equalsIgnoreCase( "patrolDetail" ) ){
            super.getService().ExportPatrolDetail( list, response );
        }
        /*
                 if (reportType.equalsIgnoreCase("patrolRate")) {
            super.getService().ExportPatrolRate(list, response);
                 }
         */

        return null;
    }

}
