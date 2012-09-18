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
     * ʹ�� DynaBean
     * @param list List
     */
    public void exportSendTaskResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //������
        logger.info( "�õ�list" );
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
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

                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }

    /**
     * ʹ�� DynaBean
     * @param list List
     */
    public void exportQuerytotalResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //������
        logger.info( "�õ�list" );
        
        if(list != null){
            Iterator iter = list.iterator();
            String overtimenum;
            logger.info( "��ʼѭ��д������" );
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

                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }
    
    
    /**
     * ���˹�����Ϣ
     * @param list List
     */
    public void exportQueryPersontotalResult( List list,String queryFlg , String username,SendTaskBean bean, String dataCount, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 1; //������        
        activeRow( r );
        String titleStr = username + " "; 
        // ��ʼʱ��
        if(bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
        	titleStr += bean.getBegintime();
        } else {
        	titleStr += "��ʼ";
        }
        titleStr += " -- ";
        // ����ʱ��
        if(bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
        	titleStr += bean.getEndtime();
        } else {
        	titleStr += "����";
        }
        
        titleStr += " ����Ϊ";
        // �������� 
        if(bean.getSendtype() != null && !"".equals(bean.getSendtype()) ) {
        	titleStr += bean.getSendtype();
        } else {
        	titleStr += "ȫ��";
        }
        if("0".equals(queryFlg)) {
        	titleStr += "���ɳ�������";
        } else if("1".equals(queryFlg)){
        	titleStr += "�Ĵ�ǩ�չ�����";
        } else if("2".equals(queryFlg)){
        	titleStr += "�Ĵ��ظ�������";
        } else if("3".equals(queryFlg)){
        	titleStr += "�Ĵ���֤������";
        } else if("11".equals(queryFlg)){
        	titleStr += "��ǩ�չ�����";
        } else if("12".equals(queryFlg)){
        	titleStr += "�Ļظ�������";
        } else if("13".equals(queryFlg)){
        	titleStr += "����֤������";
        } else if("21".equals(queryFlg)){
        	titleStr += "�Ļظ���ʱ������";
        } else if("22".equals(queryFlg)){
        	titleStr += "����֤��ʱ������";
        } 
        titleStr += dataCount + "��";
        setCellValue( 0, titleStr );
        
        logger.info( "�õ�list" );
        r=3;
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
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
                

                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }
    

    /**
     * ���˹���ͳ��
     * @param list List
     */
    public void exportPersonnumtotalResult( List list, SendTaskBean bean, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 1; //������
        activeRow( r );
        String titleStr = "";
        // ����
        if( bean.getDeptid() !=  null && !"".equals(bean.getDeptid()) ) {
        	titleStr = bean.getDeptname();
        } else {
        	titleStr = "���в���";
        }
        titleStr += " ";
        // ��ʼʱ��
        if(bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
        	titleStr += bean.getBegintime();
        } else {
        	titleStr += "��ʼ";
        }
        titleStr += " -- ";
        // ����ʱ��
        if(bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
        	titleStr += bean.getEndtime();
        } else {
        	titleStr += "����";
        }
        titleStr += " ����Ϊ";
        // �������� 
        if(bean.getSendtype() != null && !"".equals(bean.getSendtype()) ) {
        	titleStr += bean.getSendtype();
        } else {
        	titleStr += "ȫ��";
        }
       
        titleStr += " ����ͳ��";
        setCellValue( 0, titleStr );
        
        logger.info( "�õ�list" );
        r = 3;
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
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
                
                
                

                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }

    public void exportEndorseResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);

        int r = 2; //������
        logger.info( "�õ�list" );
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
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

                

                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }


    public void exportReplyResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);

        int r = 2; //������
        logger.info( "�õ�list" );
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
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
               
                //��һ��
                r++;
            }
            logger.info( "�ɹ�д��" );
        }
    }


    public void exportValidateResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);

        int r = 2; //������
        logger.info( "�õ�list" );
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
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

                

                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }
    
    public void exportDeptTotalResult( List list, SendTaskBean deptquerybean, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);

        activeRow( 1 );
        String titleStr = "";
        // ����
        if( deptquerybean.getDeptid() !=  null && !"".equals(deptquerybean.getDeptid()) ) {
        	titleStr = deptquerybean.getDeptname();
        } else {
        	titleStr = "���в���";
        }
        titleStr += " ";
        // �������� 
        if(deptquerybean.getSendtype() != null && !"".equals(deptquerybean.getSendtype()) ) {
        	titleStr += deptquerybean.getSendtype();
        } else {
        	titleStr += "��������";
        }
        titleStr += " ";
        // ��ʼʱ��
        if(deptquerybean.getBegintime() != null && !"".equals(deptquerybean.getBegintime())) {
        	titleStr += deptquerybean.getBegintime();
        } else {
        	titleStr += "��ʼ";
        }
        titleStr += " - ";
        // ����ʱ��
        if(deptquerybean.getEndtime() != null && !"".equals(deptquerybean.getEndtime())) {
        	titleStr += deptquerybean.getEndtime();
        } else {
        	titleStr += "����";
        }
        titleStr += "���Ź���ͳ�ƽ��";
        setCellValue( 0, titleStr );
        
        int r = 3; //������
        logger.info( "�õ�list" );
        if(list != null){
            Iterator iter = list.iterator();
            float replypercent;
            float validpercent;
            logger.info( "��ʼѭ��д������" );
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

                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }
}
