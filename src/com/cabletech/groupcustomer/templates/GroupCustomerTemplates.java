package com.cabletech.groupcustomer.templates;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.groupcustomer.bean.GroupCustomerBean;

public class GroupCustomerTemplates extends Template{
    private static Logger logger = Logger.getLogger( GroupCustomerTemplates.class.
            getName() );
    public GroupCustomerTemplates( String urlPath ) throws IOException{
    	super( urlPath );
    }
    

    /**
     * �����ͻ�����
     * @param list �ͻ�������Ϣ
     * @param excelstyle Excel����
     */
    public void exportGroupCustomerResult( List list, ExcelStyle excelstyle ){

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

                if( record.get( "groupname" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "groupname" ).toString() );}

                if( record.get( "city" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "city" ).toString() );}

                if( record.get( "operationtype" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "operationtype" ).toString() );}

                if( record.get( "grouptype" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "grouptype" ).toString() );}

                if( record.get( "x" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "x" ).toString() );}
                
                if( record.get( "y" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "y" ).toString() );}

                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }
    

    /**
     * �ͻ������ĵ���
     * @param list �����Ŀͻ���Ϣ
     * @param excelstyle Excel����
     * @param customerNumStr �ͻ�����
     * @param parserpercent Ԥ������
     * @param bean ��ѯ��bean
     */
    public void exportGroupCustomerParserResult( List list, ExcelStyle excelstyle ,String customerNumStr, String parserpercent , GroupCustomerBean bean){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        // ����
        activeRow( 1 );
        String titleValue = "�ͻ�������" + customerNumStr + "; Ԥ�����ʣ�" + list.size() + " / " + customerNumStr + " = " + parserpercent;
        setCellValue( 0, titleValue );
        
        // ��ѯ��������
        activeRow( 2 );
        String strValue = "";;
        if(bean.getCity() != null && !"".equals(bean.getCity())) {
        	strValue += bean.getCity() + "��";
        } else {
        	strValue += "��������";
        }
        if(bean.getGrouptype() != null && !"".equals(bean.getGrouptype())) {
        	strValue += bean.getGrouptype() + "��";
        } else {
        	strValue += "��������";
        }

        strValue = strValue + "�ļ��ſͻ���Ԥ���Ƿ�ΧΪ" + bean.getBestrowrange() + "�׵�Ԥ������Ϊ" + parserpercent;       
        setCellValue( 0, strValue);
        
        
        int r = 4; //������
        logger.info( "�õ�list" );
        
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                if( record.get( "city" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "city" ).toString() );}

                if( record.get( "groupname" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "groupname" ).toString() );}


                if( record.get( "grouptype" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "grouptype" ).toString() );}

                if( record.get( "len" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "len" ).toString() );}
                

                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }
}
