package com.cabletech.baseinfo.Templates;

//
import java.io.*;
import java.sql.ResultSet;
import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.poi.hssf.usermodel.*;
import com.cabletech.commons.exceltemplates.*;
import com.cabletech.commons.hb.QueryUtil;

public class SublineTemplate extends Template{
    private static Logger logger = Logger.getLogger( "Template" );
    public SublineTemplate( String urlPath ) throws IOException{
        super( urlPath );

    }


    /**
     * 使用 DynaBean
     * @param list List
     */
    public void doExport( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 3; //行索引
        if(list != null){
            Iterator iter = list.iterator();
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
              /*  if( record.get( "sublineid" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "sublineid" ).toString() );}*/

                if( record.get( "lineid" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "lineid" ).toString() );}

                if( record.get( "sublinename" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "sublinename" ).toString() );}

                if( record.get( "ruledeptid" ) == null ){setCellValue(2, "" );}
                else{setCellValue( 2, record.get( "ruledeptid" ).toString() );}

                if( record.get( "linetype" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "linetype" ).toString() );}

                if( record.get( "patrolname" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "patrolname" ).toString() );}

                r++; //下一行

            }
        }
    }


    public void exportRegionResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                if( record.get( "regionid" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "regionid" ).toString() );}

                if( record.get( "regionname" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "regionname" ).toString() );}

                if( record.get( "parentregionid" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "parentregionid" ).toString() );}

                if( record.get( "regiondes" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "regiondes" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }


    public void exportDepartResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "deptname" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "deptname" ).toString() );}

                if( record.get( "linkmaninfo" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "linkmaninfo" ).toString() );}

                if( record.get( "parentid" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "parentid" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }
    
    //导出路由信息报表
    public void exportRouteInfoResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "routename" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "routename" ).toString() );}

                if( record.get( "regionid" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "regionid" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }


    public void exportPatrolSonResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "patrolname" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "patrolname" ).toString() );}

                if( record.get( "sex" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "sex" ).toString() );}

                if( record.get( "phone" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "phone" ).toString() );}

                if( record.get( "mobile" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "mobile" ).toString() );}

                if( record.get( "parentid" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "parentid" ).toString() );}

                if( record.get( "jobstate" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "jobstate" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }


    public void exportTerminalResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                if( record.get( "deviceid" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "deviceid" ).toString() );}

                if( record.get( "terminaltype" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "terminaltype" ).toString() );}

                if( record.get( "produceman" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "produceman" ).toString() );}

                if( record.get( "sim" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "sim" ).toString() );}

                if( record.get( "ownerid" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "ownerid" ).toString() );}
                
                if( record.get( "holder" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "holder" ).toString() );}
                
                if( record.get( "contractorid" ) == null ){setCellValue( 6, "" );}
                else{setCellValue( 6, record.get( "contractorid" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }


    public void exportLineResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

               /* if( record.get( "lineid" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "lineid" ).toString() );}*/

                if( record.get( "linename" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "linename" ).toString() );}

                if( record.get( "linetype" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "linetype" ).toString() );}

                if( record.get( "ruledeptid" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "ruledeptid" ).toString() );}

                if( record.get( "regionid" ) == null ){setCellValue(3, "" );}
                else{setCellValue( 3, record.get( "regionid" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }


    public void exportContractorResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "contractorname" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "contractorname" ).toString() );}

                if( record.get( "parentcontractorid" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "parentcontractorid" ).toString() );}

                if( record.get( "linkmaninfo" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "linkmaninfo" ).toString() );}

                if( record.get( "pactbegindate" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "pactbegindate" ).toString() );}

                if( record.get( "pactterm" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "pactterm" ).toString() );}

                if( record.get( "regionid" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "regionid" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }


    public void exportPointResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "pointid" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "pointid" ).toString() );}

                if( record.get( "pointname" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "pointname" ).toString() );}

                if( record.get( "addressinfo" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "addressinfo" ).toString() );}

                if( record.get( "sublineid" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "sublineid" ).toString() );}

                if( record.get( "isfocus" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "isfocus" ).toString() );}

                if( record.get( "regionid" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "regionid" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }


    public void exportUserinfoResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "userid" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "userid" ).toString() );}

                if( record.get( "username" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "username" ).toString() );}

              /*  if( record.get( "usertype" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "usertype" ).toString() );}
*/
                if( record.get( "deptid" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "deptid" ).toString() );}

                if( record.get( "regionid" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "regionid" ).toString() );}

                if( record.get( "position" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "position" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }


    public void exportUsergroupResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "groupname" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "groupname" ).toString() );}

                if( record.get( "region" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "region" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }


    public void exportAlertreceiverListResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "simcode" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "simcode" ).toString() );}

                if( record.get( "emergencylevel" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "emergencylevel" ).toString() );}

                if( record.get( "userid" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "userid" ).toString() );}

                if( record.get( "contractorid" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "contractorid" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }


    public void exportTaskOperationResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook );
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "id" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "id" ).toString() );}

                if( record.get( "operationname" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "operationname" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }


    public void exportPatrolOpResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "operationcode" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "operationcode" ).toString() );}

                if( record.get( "optype" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "optype" ).toString() );}

                if( record.get( "emlevel" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "emlevel" ).toString() );}

                if( record.get( "operationdes" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "operationdes" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }


    public void exportSysLog( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "logdate" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "logdate" ).toString() );}

                if( record.get( "username" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "username" ).toString() );}

                if( record.get( "ip" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "ip" ).toString() );}

                if( record.get( "module" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "module" ).toString() );}

                if( record.get( "msg" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "msg" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }


    public void exportSmsReceiveLog( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "arrivetime" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "arrivetime" ).toString() );}

                if( record.get( "sim" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "sim" ).toString() );}

                if( record.get( "region" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "region" ).toString() );}

                if( record.get( "content" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "content" ).toString() );}

                if( record.get( "handlestate" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "handlestate" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }


    public void exportSmsSendLog( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "sendtime" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "sendtime" ).toString() );}

                if( record.get( "username" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "username" ).toString() );}

                if( record.get( "simid" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "simid" ).toString() );}

                if( record.get( "type" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "type" ).toString() );}

                if( record.get( "content" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "content" ).toString() );}

                if( record.get( "handlestate" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "handlestate" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }

    public void exportCableSegment( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "segmentid" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "segmentid" ).toString() );}

                if( record.get( "segmentname" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "segmentname" ).toString() );}

                if( record.get( "segmentdesc" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "segmentdesc" ).toString() );}

                if( record.get( "pointa" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "pointa" ).toString() );}

                if( record.get( "pointz" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "pointz" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }
    
    public void exportPipeSegment( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "segmentid" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "segmentid" ).toString() );}

                if( record.get( "segmentname" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "segmentname" ).toString() );}

                if( record.get( "segmentdesc" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "segmentdesc" ).toString() );}

                if( record.get( "pointa" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "pointa" ).toString() );}

                if( record.get( "pointz" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "pointz" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }
    
    public void exportPatrolMan( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "patrolname" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "patrolname" ).toString() );}

                if( record.get( "parentid" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "parentid" ).toString() );}

                if( record.get( "jobinfo" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "jobinfo" ).toString() );}


                r++; //下一行
            }
        }
        logger.info( "成功写入" );
    }
   /**
    * export 材料存放地点信息
    * @param list
    * @param excelstyle
    */
    public void exportMaterialAddressResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "address" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "address" ).toString() );}

                if( record.get( "contractorid" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "contractorid" ).toString() );}
                
                if( record.get( "remark" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "remark" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
}
    /**
     * 导出材料信息
     * @param list
     * @param excelstyle
     */
    public void exportMaterialInfoResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "name" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "name" ).toString() );}

                if( record.get( "modelname" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "modelname" ).toString() );}
				if (record.get("typename") == null) {setCellValue(2, "");} 
				else {setCellValue(2, record.get("typename").toString());}
                if( record.get( "factory" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "factory" ).toString() );}
                
                if( record.get( "remark" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "remark" ).toString() );}

                r++; //下一行
            }
        }
        logger.info( "成功写入" );
}
    public void exportMaterialMonthStat( List baseInfo,List Intendance,List RegionPrincipal,List MaterialName,List MaterialCount, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        logger.info( "得到list" );
        if( baseInfo != null ){
        	int r =2;
            Iterator iter = baseInfo.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "remedycode" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "remedycode" ).toString() );}
                
                if( record.get( "projectname" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "projectname" ).toString() );}

                if( record.get( "creator" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "creator" ).toString() );}
                
                if( record.get( "remedyaddress" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "remedyaddress" ).toString() );}
                
                if( record.get( "remedydate" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "remedydate" ).toString() );}
                
                if( record.get( "contractorname" ) == null ){setCellValue( 7, "" );}
                else{setCellValue( 7, record.get( "contractorname" ).toString() );}

                r++; //下一行
            }
        }
        if( Intendance != null ){
        	int r =2;
           for(int i=0;i<Intendance.size();i++){
                activeRow( r );
                if( Intendance.get(i).toString() == null ){setCellValue( 6, "" );}
                else{setCellValue( 6, Intendance.get(i).toString() );}
                
                r++; //下一行
            }
        }
        if( RegionPrincipal != null ){
        	int r =2;
        	for(int i=0;i<RegionPrincipal.size();i++){
                activeRow( r );
                if( RegionPrincipal.get(i).toString() == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, RegionPrincipal.get(i).toString() );}
                
                r++; //下一行
            }
        }
        if(MaterialCount!=null){
			int r1=2;
			int col1=8;
			for(int i=0;i<MaterialCount.size();i++){
                activeRow( r1 );
                r1++;
                if(MaterialName!=null){
               for(int j=0;j<MaterialName.size();j++){
            	   List list = (List)MaterialCount.get(i);
					for(int a=0;a<list.size();a++){
					      HashMap map = (HashMap)list.get(a);
            	   if(map.get(((BasicDynaBean)MaterialName.get(j)).get("materialid").toString())==null){
            		   if(list.size()>=MaterialName.size()){}else{
            		   setCellValue(col1+j,"0");
            		   }
            	   }else{
            		   setCellValue(col1+j,map.get(((BasicDynaBean)MaterialName.get(j)).get("materialid").toString()).toString());
            	   }
					}
               }
            }
			}
		}
        if(MaterialName!=null){
    		int r=1;
    		int col=8;
    		Iterator iter = MaterialName.iterator();
    		logger.info("开始循环写入数据");
    		//String value="";
    		//System.out.println("hh     ");
    		while(iter.hasNext()){
    			record=(DynaBean)iter.next();
    			activeRow(r);
    			//System.out.println("hh     "+record.get("name"));
    			if(record.get("material_name")==null){
    				setCellValue(col,"0");
    				col++;
    			}else{
    				setCellValue(col,record.get("material_name").toString());
    				col++;
    			}
    		}
    	}
        logger.info( "成功写入" );
}
    public void exportPatrolPointResult( String sql, ExcelStyle excelstyle ){
        QueryUtil query = null;
        ResultSet rs = null;
        String pointid = "";
        String pointname = "";
        String linename = "";
        String sublinename = "";
        String sublinetype = "";
        String linestype = "";
        String ptype = "";
        String inumber = "";
        String regionname = "";
        String patrolname = "";
        String pointnums = "";
        String x = "";
        String y = "";
        //String oldSublineID = "xxxxxxxxxxxxx";
        String newSublineID = "";
        //HSSFRow prerow = null;
        //HSSFCell precell = null;
        //int pointnum =0;
        int pNum = 0;
        int sheetnum = 1;
        //List rownumber = new ArrayList();
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        Map map = new HashMap();
        logger.info( "得到list" );
        try{
        	query = new QueryUtil();
            rs = query.executeQuery(sql);
            if( rs != null ){
                logger.info( "开始循环写入数据" );               
                while( rs.next() ){
                	if (rs.getRow()%50001==0){
                		activeSheet( sheetnum++ );
                		r=2;
//                		rownumber.clear();
//                		pointnum=1;
                	}
                	activeRow( r );
                    newSublineID = rs.getString("sublineid");
                    pointid = rs.getString("pointid");
                    pointname = rs.getString("pointname");
                    linename = rs.getString("linename");
                    sublinename = rs.getString("sublinename");
                    sublinetype = rs.getString("sublinetype");
                    linestype = rs.getString("linestype");
                    ptype = rs.getString("ptype");
                    inumber = String.valueOf(rs.getInt("inumber"));
                    regionname = rs.getString("regionname");
                    patrolname = rs.getString("patrolname");
                    pointnums = rs.getString("pointnum");
                    x = String.valueOf(rs.getDouble("x"));
                    y = String.valueOf(rs.getDouble("y"));
                    
                    if( pointid == null ){setCellValue( 0, "" );}
                    else{setCellValue( 0, pointid );}

                    if( pointname == null ){setCellValue( 1, "" );}
                    else{setCellValue( 1, pointname );}
                    
                    if( linename == null ){setCellValue( 2, "" );}
                    else{setCellValue( 2, linename );}
                    
                    if( newSublineID == null ){setCellValue( 3, "" );}
                    else{setCellValue( 3, newSublineID );}
                    
                    if(sublinename == null ){setCellValue( 4, "" );}
                    else{setCellValue( 4, sublinename );}
                    
                    if( sublinetype == null ){setCellValue( 5, "" );}
                    else{setCellValue( 5, sublinetype );}
 
                    if( ptype == null ){setCellValue( 7, "" );}
                    else{setCellValue( 7, ptype );}
                    
                    if( inumber == null ){setCellValue( 8, "" );}
                    else{setCellValue( 8, inumber );}
                    
                    if( regionname == null ){setCellValue( 9, "" );}
                    else{setCellValue( 9, regionname );}
                    
                    if( patrolname == null ){setCellValue( 10, "" );}
                    else{setCellValue( 10, patrolname.toString() );}
                    
                    if( x == null ){setCellValue( 11, "" );}
                    else{setCellValue( 11, x );}

                    if( y == null ){setCellValue( 12, "" );}
                    else{setCellValue( 12, y );}   
                    
                    if( linestype == null ){setCellValue( 13, "" );}
                    else{setCellValue( 13, linestype );}
                    
                    setCellValue(6,pointnums); 
//                    if (r == 2){
//                    	oldSublineID = newSublineID;
//                    	++pointnum;
//                    }else{
//                        if (oldSublineID.equals(newSublineID)){
//                        	rownumber.add(new Integer(r));
//                        	setCellValue( 6, "" );
//                        	++pointnum;
////                        	if (rs.isLast()){
////                            	for(int i=0;i<rownumber.size();i++){
////                            		pNum=((Integer)rownumber.get(i)).intValue();
////                            		prerow = curSheet.getRow(pNum); 
////                                	precell = prerow.getCell((short)6);
////                                	precell.setCellValue(rownumber.size()+1);
////                            	}
////                            	//以下是给最后一条线段的第一个巡检点所在行的“巡检点个数”填充值
////                            	int thelast = pNum - rownumber.size();
////                      			prerow = curSheet.getRow(thelast); 
////                      			precell = prerow.createCell((short)6);
////                      	    	precell.setCellValue(pointnum); 
////                            	rownumber.clear();
////                        	}
//                        }else{	
//                        	for(int i=0;i<rownumber.size();i++){
//                        		pNum=((Integer)rownumber.get(i)).intValue();
//                        		prerow = curSheet.getRow(pNum); 
//                            	precell = prerow.getCell((short)6);
//                            	precell.setCellValue(rownumber.size()+1);
//                            	//setCellValue(6,pNum,String.valueOf(rownumber.size()+1));
//                        	}
//                        	map.put(new Integer(r-rownumber.size()-1), new Integer(rownumber.size()+1));
//                        	oldSublineID = newSublineID;
//                        	rownumber.clear();
//                        	pointnum=1;                        	
//                        }
//
//                    }
                    r++; //下一行
                }
            }
        }catch( Exception ex ){
            logger.error("查询异常："+ex.getMessage());
            ex.printStackTrace();
        }finally{
//        	//给除最后一条线段外的其它所有线段的第一个点所在行填充“巡检点个数”值
//        	 for(Iterator ite=map.keySet().iterator();ite.hasNext();){   
//        		  Integer key = (Integer)ite.next();    
//        		  Integer value = (Integer)map.get(key); 
////      			prerow = curSheet.getRow(key.intValue()); 
////      			precell = prerow.createCell((short)6);
////      	    	precell.setCellValue(value.intValue());  
//        		  
//        	 }

        }
        
        logger.info( "成功写入" );
    }
}
