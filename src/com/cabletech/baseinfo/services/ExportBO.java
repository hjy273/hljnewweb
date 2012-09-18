package com.cabletech.baseinfo.services;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.apache.log4j.*;
import com.cabletech.baseinfo.Templates.*;
import com.cabletech.commons.config.*;
import com.cabletech.commons.exceltemplates.ExcelStyle;

public class ExportBO{

    private static Logger logger = Logger.getLogger( "ExportBO" );
    private static String CONTENT_TYPE = "application/vnd.ms-excel";
    public ExportBO(){
    }


    private String getUrlPath( Properties config, String propertyItemName ) throws
        IOException{
        String fileName = config.getProperty( propertyItemName );
        logger.info("filename:" + fileName);
        if( fileName != null && fileName != "" ){
            String urlPath = ConfigPathUtil.getClassPathConfigFile( fileName );
            logger.info( "ExcelFile:" + urlPath );
            return urlPath;

        }
        else{
            return null;
        }
    }


    private void initResponse( HttpServletResponse response, String outfilename ) throws
        Exception{
        response.reset();
        response.setContentType( CONTENT_TYPE );
        response.setHeader( "Content-Disposition",
            "attachment;filename="
            + new String( outfilename.getBytes(), "iso-8859-1" ) );

    }

    public void exportRegionResult( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "������Ϣһ����.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.regionresult" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportRegionResult( list, excelstyle );
        template.write( out );

    }


    public void exportDepartResult( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "������Ϣһ����.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.departresult" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportDepartResult( list, excelstyle );
        template.write( out );

    }
    //����·����Ϣ����
    public void exportRouteInfoResult( List list,
            HttpServletResponse response ) throws
            Exception{

            OutputStream out;
            initResponse( response, "·����Ϣһ����.xls" );
            out = response.getOutputStream();

            String urlPath = ReportConfig.getInstance().getUrlPath( "report.routeinforesult" );

            SublineTemplate template = new SublineTemplate( urlPath );
            ExcelStyle excelstyle = new ExcelStyle( urlPath );
            template.exportRouteInfoResult( list, excelstyle );
            template.write( out );

        }


    public void exportPatrolSonResult( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "Ѳ��Ա��Ϣһ����.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.patrolsonresult" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportPatrolSonResult( list, excelstyle );
        template.write( out );

    }


    public void exportTerminalResult( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "�ֳ��ն��豸��Ϣһ����.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.terminalresult" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportTerminalResult( list, excelstyle );
        template.write( out );

    }


    public void exportLineResult( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "Ѳ����·��Ϣһ����.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.lineresult" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportLineResult( list, excelstyle );
        template.write( out );

    }


    public void exportContractorResult( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "��ά��λ��Ϣһ����.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.contractorresult" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportContractorResult( list, excelstyle );
        template.write( out );

    }


    public void exportPointResult( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "Ѳ�����Ϣһ����.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.pointresult" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportPointResult( list, excelstyle );
        template.write( out );

    }


    public void exportUserinfoResult( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "�û���Ϣһ����.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.userinforesult" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportUserinfoResult( list, excelstyle );
        template.write( out );

    }


    public void exportUsergroupResult( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "�û�����Ϣһ����.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.usergroupresult" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportUsergroupResult( list, excelstyle );
        template.write( out );

    }


    public void exportAlertreceiverListResult( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "����������Ϣһ����.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.alertreceiverlistresult" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportAlertreceiverListResult( list, excelstyle );
        template.write( out );

    }


    public void exportTaskOperationResult( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "���������Ϣ���һ����.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.taskoperationresult" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportTaskOperationResult( list, excelstyle );
        template.write( out );

    }


    public void exportPatrolOpResult( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "Ѳ���¹�����Ϣ���.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.patrolopresult" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportPatrolOpResult( list, excelstyle );
        template.write( out );

    }


    public void exportSysLog( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "ϵͳ������־��Ϣ���.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.syslog" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportSysLog( list, excelstyle );
        template.write( out );

    }


    public void exportSmsReceiveLog( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "���ն�����־���.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.smsreceivelog" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportSmsReceiveLog( list, excelstyle );
        template.write( out );

    }


    public void exportSmsSendLog( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "���Ͷ�����־���.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.smssendlog" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportSmsSendLog( list, excelstyle );
        template.write( out );

    }


    public void exportCableSegment( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "������Ϣһ����.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.cablesegment" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportCableSegment( list, excelstyle );
        template.write( out );

    }
    
    public void exportPipeSegment( List list,
            HttpServletResponse response ) throws
            Exception{

            OutputStream out;
            initResponse( response, "�ܵ���һ����.xls" );
            out = response.getOutputStream();

            String urlPath = ReportConfig.getInstance().getUrlPath( "report.piplesegment" );

            SublineTemplate template = new SublineTemplate( urlPath );
            ExcelStyle excelstyle = new ExcelStyle( urlPath );
            template.exportPipeSegment( list, excelstyle );
            template.write( out );

        }
    
    public void exportPatrolMan( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "Ѳ��ά����һ����.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.patrolman" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportPatrolMan( list, excelstyle );
        template.write( out );

    }
    
    public void exportPatrolPointInfoResult( String sql,
            HttpServletResponse response ) throws
            Exception{

            OutputStream out;
            initResponse( response, "Ѳ��㼰�����Ϣһ����.xls" );
            out = response.getOutputStream();
            String urlPath = ReportConfig.getInstance().getUrlPath( "report.patrolpointresult" );
            SublineTemplate template = new SublineTemplate( urlPath );
            ExcelStyle excelstyle = new ExcelStyle( urlPath );
            template.exportPatrolPointResult( sql, excelstyle );
            template.write( out );

        }
/**
 * �������ϴ�ŵص���Ϣ
 * @param list
 * @param response
 * @throws Exception
 */
public void exportMaterialAddressResult( List list,
           HttpServletResponse response ) throws
           Exception{

           OutputStream out;
           initResponse( response, "���ϴ�ŵص���Ϣһ����.xls" );
           out = response.getOutputStream();

           String urlPath = ReportConfig.getInstance().getUrlPath( "report.materialaddressresult" );

           SublineTemplate template = new SublineTemplate( urlPath );
           ExcelStyle excelstyle = new ExcelStyle( urlPath );
           template.exportMaterialAddressResult( list, excelstyle );
           template.write( out );

       }
public void exportMaterialInfoResult( List list,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "������Ϣһ����.xls" );
        out = response.getOutputStream();

        String urlPath = ReportConfig.getInstance().getUrlPath( "report.materialinforesult" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportMaterialInfoResult( list, excelstyle );
        template.write( out );

    }
public void exportMaterialMonthStat( List baseInfo,List Intendance,List RegionPrincipal,List MaterialName,List MaterialCount,
        HttpServletResponse response ) throws
        Exception{

        OutputStream out;
        initResponse( response, "����ʹ����ͳ����Ϣһ����.xls" );
        out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.materialmonthstatresult" );

        SublineTemplate template = new SublineTemplate( urlPath );
        ExcelStyle excelstyle = new ExcelStyle( urlPath );
        template.exportMaterialMonthStat( baseInfo,Intendance,RegionPrincipal,MaterialName,MaterialCount,excelstyle);
        template.write( out );

    }
}
