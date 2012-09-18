package com.cabletech.sendtask.templates;

//
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.partmanage.action.PartRequisitionAction;
import com.cabletech.sendtask.beans.SendTaskBean;

public class SendTaskTemplate extends Template{
    private static Logger logger = Logger.getLogger( PartRequisitionAction.class.
                                   getName() );
    public SendTaskTemplate( String urlPath ) throws IOException{
        super( urlPath );

    }


    /**
     * 使用 DynaBean
     * @param list List
     */
    public void exportSendTaskResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                if( record.get( "serialnumber" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "serialnumber" ).toString() );}

                if( record.get( "sendtopic" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "sendtopic" ).toString() );}

                if( record.get( "sendtype" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "sendtype" ).toString() );}

                if( record.get( "senddeptname" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "senddeptname" ).toString() );}

                if( record.get( "processterm" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "processterm" ).toString() );}

                if( record.get( "usernameacce" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "usernameacce" ).toString() );}

                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }

    /**
     * 使用 DynaBean
     * @param list List
     */
    public void exportQuerytotalResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        
        if(list != null){
            Iterator iter = list.iterator();
            String overtimenum;
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                if( record.get( "serialnumber" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "serialnumber" ).toString() );}
                
                if( record.get( "sendtopic" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "sendtopic" ).toString() );}

                if( record.get( "sendtype" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "sendtype" ).toString() );}

                if( record.get( "senddeptname" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "senddeptname" ).toString() );}   

                if( record.get( "acceusername" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "acceusername" ).toString() );}
                
                if( record.get( "processterm" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "processterm" ).toString() );}

                if( record.get( "replyovertime" ) == null ){setCellValue( 6, "" );}
                else{setCellValue( 6, record.get( "replyovertime" ).toString() );}                
                
                if( record.get( "validovertime" ) == null ){setCellValue( 7, "" );}
                else{setCellValue( 7, record.get( "validovertime" ).toString() );}

                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }
    
    
    /**
     * 个人工单信息
     * @param list List
     */
    public void exportQueryPersontotalResult( List list,String queryFlg , String username,SendTaskBean bean, String dataCount, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 1; //行索引        
        activeRow( r );
        String titleStr = username + " "; 
        // 开始时间
        if(bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
        	titleStr += bean.getBegintime();
        } else {
        	titleStr += "开始";
        }
        titleStr += " -- ";
        // 结束时间
        if(bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
        	titleStr += bean.getEndtime();
        } else {
        	titleStr += "至今";
        }
        
        titleStr += " 类型为";
        // 工单类型 
        if(bean.getSendtype() != null && !"".equals(bean.getSendtype()) ) {
        	titleStr += bean.getSendtype();
        } else {
        	titleStr += "全部";
        }
        if("0".equals(queryFlg)) {
        	titleStr += "的派出工单：";
        } else if("1".equals(queryFlg)){
        	titleStr += "的待签收工单：";
        } else if("2".equals(queryFlg)){
        	titleStr += "的待回复工单：";
        } else if("3".equals(queryFlg)){
        	titleStr += "的待验证工单：";
        } else if("11".equals(queryFlg)){
        	titleStr += "的签收工单：";
        } else if("12".equals(queryFlg)){
        	titleStr += "的回复工单：";
        } else if("13".equals(queryFlg)){
        	titleStr += "的验证工单：";
        } else if("21".equals(queryFlg)){
        	titleStr += "的回复超时工单：";
        } else if("22".equals(queryFlg)){
        	titleStr += "的验证超时工单：";
        } 
        titleStr += dataCount + "个";
        setCellValue( 0, titleStr );
        
        logger.info( "得到list" );
        r=3;
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                if( record.get( "serialnumber" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "serialnumber" ).toString() );}
                
                if( record.get( "sendtopic" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "sendtopic" ).toString() );}

                if( record.get( "sendtype" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "sendtype" ).toString() );}

                if( record.get( "senddeptname" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "senddeptname" ).toString() );}   

                if( record.get( "acceusername" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "acceusername" ).toString() );}
                
                if( record.get( "processterm" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "processterm" ).toString() );}

                if( record.get( "overtimenum" ) == null ){setCellValue( 6, "" );}
                else{setCellValue( 6, record.get( "overtimenum" ).toString() );}                
                

                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }
    

    /**
     * 个人工单统计
     * @param list List
     */
    public void exportPersonnumtotalResult( List list, SendTaskBean bean, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 1; //行索引
        activeRow( r );
        String titleStr = "";
        // 部门
        if( bean.getDeptid() !=  null && !"".equals(bean.getDeptid()) ) {
        	titleStr = bean.getDeptname();
        } else {
        	titleStr = "所有部门";
        }
        titleStr += " ";
        // 开始时间
        if(bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
        	titleStr += bean.getBegintime();
        } else {
        	titleStr += "开始";
        }
        titleStr += " -- ";
        // 结束时间
        if(bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
        	titleStr += bean.getEndtime();
        } else {
        	titleStr += "至今";
        }
        titleStr += " 类型为";
        // 工单类型 
        if(bean.getSendtype() != null && !"".equals(bean.getSendtype()) ) {
        	titleStr += bean.getSendtype();
        } else {
        	titleStr += "全部";
        }
       
        titleStr += " 工单统计";
        setCellValue( 0, titleStr );
        
        logger.info( "得到list" );
        r = 3;
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                if( record.get( "username" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "username" ).toString() );}
                
                if( record.get( "sendnum" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "sendnum" ).toString() );}

                if( record.get( "endorsenum" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "endorsenum" ).toString() );}

                if( record.get( "replynum" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "replynum" ).toString() );}   

                if( record.get( "valitnum" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "valitnum" ).toString() );}
                
                if( record.get( "ennum" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "ennum" ).toString() );}

                if( record.get( "renum" ) == null ){setCellValue( 6, "" );}
                else{setCellValue( 6, record.get( "renum" ).toString() );} 
                
                if( record.get( "vanum" ) == null ){setCellValue( 7, "" );}
                else{setCellValue( 7, record.get( "vanum" ).toString() );} 
                
                if( record.get( "overtimereply" ) == null ){setCellValue( 8, "" );}
                else{setCellValue( 8, record.get( "overtimereply" ).toString() );} 
                
                if( record.get( "overtimevalid" ) == null ){setCellValue( 9, "" );}
                else{setCellValue( 9, record.get( "overtimevalid" ).toString() );} 
                
                
                

                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }

    public void exportEndorseResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);

        int r = 2; //行索引
        logger.info( "得到list" );
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                if( record.get( "serialnumber" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "serialnumber" ).toString() );}

                if( record.get( "sendtopic" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "sendtopic" ).toString() );}

                if( record.get( "sendtype" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "sendtype" ).toString() );}

                if( record.get( "senddeptname" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "senddeptname" ).toString() );}

                if( record.get( "processterm" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "processterm" ).toString() );}

                if( record.get( "usernameacce" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "usernameacce" ).toString() );}

                

                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }


    public void exportReplyResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);

        int r = 2; //行索引
        logger.info( "得到list" );
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "serialnumber" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "serialnumber" ).toString() );}

                if( record.get( "sendtopic" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "sendtopic" ).toString() );}

                if( record.get( "sendtype" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "sendtype" ).toString() );}

                if( record.get( "senddeptname" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "senddeptname" ).toString() );}

                if( record.get( "processterm" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "processterm" ).toString() );}

                if( record.get( "usernameacce" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "usernameacce" ).toString() );}
               
                //下一行
                r++;
            }
            logger.info( "成功写入" );
        }
    }


    public void exportValidateResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);

        int r = 2; //行索引
        logger.info( "得到list" );
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "serialnumber" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "serialnumber" ).toString() );}

                if( record.get( "sendtopic" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "sendtopic" ).toString() );}
                
                if( record.get( "sendtype" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "sendtype" ).toString() );}

                if( record.get( "senddeptname" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "senddeptname" ).toString() );}

                if( record.get( "processterm" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "processterm" ).toString() );}

                if( record.get( "sendusername" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "sendusername" ).toString() );}

                

                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }
    
    public void exportDeptTotalResult( List list, SendTaskBean deptquerybean, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);

        activeRow( 1 );
        String titleStr = "";
        // 部门
        if( deptquerybean.getDeptid() !=  null && !"".equals(deptquerybean.getDeptid()) ) {
        	titleStr = deptquerybean.getDeptname();
        } else {
        	titleStr = "所有部门";
        }
        titleStr += " ";
        // 工单类型 
        if(deptquerybean.getSendtype() != null && !"".equals(deptquerybean.getSendtype()) ) {
        	titleStr += deptquerybean.getSendtype();
        } else {
        	titleStr += "所有类型";
        }
        titleStr += " ";
        // 开始时间
        if(deptquerybean.getBegintime() != null && !"".equals(deptquerybean.getBegintime())) {
        	titleStr += deptquerybean.getBegintime();
        } else {
        	titleStr += "开始";
        }
        titleStr += " - ";
        // 结束时间
        if(deptquerybean.getEndtime() != null && !"".equals(deptquerybean.getEndtime())) {
        	titleStr += deptquerybean.getEndtime();
        } else {
        	titleStr += "至令";
        }
        titleStr += "部门工单统计结果";
        setCellValue( 0, titleStr );
        
        int r = 3; //行索引
        logger.info( "得到list" );
        if(list != null){
            Iterator iter = list.iterator();
            float replypercent;
            float validpercent;
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                
                
                if( record.get( "deptname" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "deptname" ).toString() );}

                if( record.get( "sendnum" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "sendnum" ).toString() );}
                
                if( record.get( "replynum" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "replynum" ).toString() );}

                if( record.get( "validnum" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "validnum" ).toString() );}

                if( record.get( "replypercent" ) == null ){setCellValue( 4, "" );}
                else {
                	replypercent = Float.parseFloat(record.get( "replypercent" ).toString());
                	setCellValue( 4, ((int)(replypercent*100))+"%" );
                	
                }

                if( record.get( "validpercent" ) == null ){setCellValue( 5, "" );}
                else {
                	validpercent = Float.parseFloat(record.get( "validpercent" ).toString());
                	setCellValue( 5, ((int)(validpercent*100))+"%" );
                }

                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }
}
