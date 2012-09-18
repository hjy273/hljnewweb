package com.cabletech.machine.services;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.machine.dao.MobileExcelDao;
import com.cabletech.machine.teplates.MobileTaskTemplate;

public class MobileExcelBo {
    private static Logger logger = Logger.getLogger( "PlanDao" );
    private static String CONTENT_TYPE = "application/vnd.ms-excel";

    private void initResponse( HttpServletResponse response, String outfilename ) throws
        Exception{
        response.reset();
        response.setContentType( CONTENT_TYPE );
        response.setHeader( "Content-Disposition",
            "attachment;filename="
            + new String( outfilename.getBytes(), "iso-8859-1" ) );

    }

    
    public void exportCheckedTaskinfo(HashMap queryMap, HttpServletResponse response) {
    	try {
    		OutputStream out;
            initResponse( response, "按巡检类型导出.xls" );
            out = response.getOutputStream();
            String urlPath = ReportConfig.getInstance().getUrlPath( "report.mobilecheckedinfo" );
          
            MobileTaskTemplate template = new MobileTaskTemplate(urlPath);
            ExcelStyle excelstyle = new ExcelStyle( urlPath );
            
            MobileExcelDao dao = new MobileExcelDao();
            String type = String.valueOf(queryMap.get("type"));
            
            List list1 = null;
            List list2 = null;
            List list3 = null;
            List list4 = null;
            List list5 = null;
            if("0".equals(type)) {
            	list1 = dao.getContentCheckInfo(queryMap, "1");
            	list2 = dao.getContentCheckInfo(queryMap, "2"); 
            	list3 = dao.getMicroCheckInfo(queryMap,"3");
            	list4 = dao.getFsoCheckInfo(queryMap,"4");
            	list5 = dao.getFiberCheckInfo(queryMap,"5");
            } else if("1".equals(type)) {
            	// 核心层
            	list1 = dao.getContentCheckInfo(queryMap, "1");
            } else if("2".equals(type)) {
            	// 接放层
            	list2 = dao.getContentCheckInfo(queryMap, "2"); 
            } else if("3".equals(type)) {
            	// 接入微波层
            	list3 = dao.getMicroCheckInfo(queryMap,"3");
            } else if("4".equals(type)) {
            	// 接入层FSO
            	list4 = dao.getFsoCheckInfo(queryMap,"4");
            }else if("5".equals(type)) {
            	// 光交维护
            	list5 = dao.getFiberCheckInfo(queryMap,"5");
            }
            
            int sheetIndex = 0;
            if(list1 != null) {
            	template.exportContentResult(list1, sheetIndex, excelstyle);
            	sheetIndex++;
            } else {
            	template.removeSheet(sheetIndex);
            }       
            if(list2 != null) {
            	template.exportContentResult(list2,sheetIndex, excelstyle);
            	sheetIndex++;
            } else {
            	template.removeSheet(sheetIndex);
            }       
            if(list3 != null) {
            	template.exportMicroResult(list3,sheetIndex, excelstyle);
            	sheetIndex++;
            } else {
            	template.removeSheet(sheetIndex);
            }
            if(list4 != null) {
            	template.exportFsoResult(list4, sheetIndex, excelstyle);
            	sheetIndex++;
            } else {
            	template.removeSheet(sheetIndex);
            }
            
            if(list5 != null) {
            	template.exportFiberResult(list5, sheetIndex, excelstyle);
            } else {
            	template.removeSheet(sheetIndex);
            }
            
            template.write( out );
    	} catch(Exception e) {
    		logger.error("核查任务导出的异常：" + e.getMessage());
    		e.printStackTrace();
    	}
        
    }
}
