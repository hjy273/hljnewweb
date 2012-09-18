package com.cabletech.fsmanager.dao;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.fsmanager.templates.CableTemplates;


public class ExportDao {
	private static Logger logger = Logger.getLogger( "ToolExportDao" );
    private static String CONTENT_TYPE = "application/vnd.ms-excel";
    public ExportDao(){
    }



    private void initResponse( HttpServletResponse response, String outfilename ) throws
        Exception{
        response.reset();
        response.setContentType( CONTENT_TYPE );
        response.setHeader( "Content-Disposition",
            "attachment;filename="
            + new String( outfilename.getBytes(), "iso-8859-1" ) );

    }

    public boolean exportInfo( List list,
        HttpServletResponse response ){
        try{
            OutputStream out;
            initResponse( response, "光缆段信息一览表.xls" );
            out = response.getOutputStream();
            String urlPath = ReportConfig.getInstance().getUrlPath( "report.fscableinfo" );
            CableTemplates template = new CableTemplates( urlPath );
            ExcelStyle excelstyle = new ExcelStyle( urlPath );
            template.exportReport( list, excelstyle );
            template.write( out );
            return true;
        }
        catch( Exception e ){
            logger.error( "导出站点信息一览表异常:" + e.getMessage() );
            e.printStackTrace();
            return false;
        }

    }
    
}
