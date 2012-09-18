package com.cabletech.machine.teplates;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.partmanage.action.PartRequisitionAction;

public class MobileTaskTemplate extends Template {
    private static Logger logger = Logger.getLogger( PartRequisitionAction.class.
            getName() );
    public MobileTaskTemplate( String urlPath ) throws IOException{
    	super( urlPath );
    }
    
    /**
     * ���Ĳ������SDH
     */
    public void exportContentResult( List list,  int sheetIndex,  ExcelStyle excelstyle ){

        DynaBean record;
      //  if("1".equals(type)) {
       // 	activeSheet( 0 );
  //      } else {
        	activeSheet( sheetIndex);
   //     }
     
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 4; //������
        logger.info( "�õ�list" );
        
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                // ���
                setCellValue( 0, (r - 3) + "");
                		
                // �豸����              
                if( record.get( "equipment_name" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "equipment_name" ).toString() );}

                // ��ά��˾ 
                if( record.get( "contractorname" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "contractorname" ).toString() );}

                // ִ��������
                if( record.get( "username" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "username" ).toString() );}

                // ִ������
                if( record.get( "executetime" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "executetime" ).toString() );}
                
                // �豸������� is_clean
                if( record.get( "is_clean" ) == null ){setCellValue( 5, "" );}
                else if("0".equals(record.get( "is_clean" ).toString())) {
                	setCellValue( 5, "��" );
                }else{setCellValue( 5, "��");}
                
                // �豸�����¶�
                if( record.get( "obve_temperature" ) == null ){setCellValue( 6, "" );}
                else{setCellValue( 6, record.get( "obve_temperature" ).toString() );}
                
                // ���񶥶�ָʾ��״̬
                if( record.get( "is_top_pilotlamp" ) == null ){setCellValue( 7, "" );}
                else if("1".equals(record.get( "is_top_pilotlamp" ).toString())) {
                	setCellValue( 7, "��" );
                }
                else{setCellValue( 7, "��" );}
                
                // ����ָʾ��״̬
                if( record.get( "is_veneer_pilotlamp" ) == null ){setCellValue( 8, "" );} 
                else if ("1".equals(record.get( "is_veneer_pilotlamp" ).toString())) {
                	setCellValue( 8, "��" );
                }
                else{setCellValue( 8, "��");}
                
                // �豸���������
                if( record.get( "is_dustproof_clean" ) == null ){setCellValue( 9, "" );} 
                else if("1".equals(record.get( "is_dustproof_clean" ).toString())) {
                	setCellValue( 9, "��");
                }
                else{setCellValue( 9, "��");;}
                
                // ��������״̬
                if( record.get( "fan_state" ) == null ){setCellValue( 10, "" );}
                else{setCellValue( 10, record.get( "fan_state" ).toString() );}
                
                // �����������
                if( record.get( "machine_floor_clean" ) == null ){setCellValue( 11, "" );}
                else{setCellValue( 11, record.get( "machine_floor_clean" ).toString() );}
                
                // �����¶�
                if( record.get( "machine_temperature" ) == null ){setCellValue( 12, "" );}
                else{setCellValue( 12, record.get( "machine_temperature" ).toString() );}
                
                // ����ʪ�� machine_humidity
                if( record.get( "machine_humidity" ) == null ){setCellValue( 13, "" );}
                else{setCellValue( 13, record.get( "machine_humidity" ).toString() );}
                
                // DDF��/���߼����°���
                if( record.get( "ddf_colligation" ) == null ){setCellValue( 14, "" );}
                else{setCellValue( 14, record.get( "ddf_colligation" ).toString() );}
                
                // β�˱��� fiber_protect
                if( record.get( "fiber_protect" ) == null ){setCellValue( 15, "" );}
                else{setCellValue( 15, record.get( "fiber_protect" ).toString() );}
                
                // DDF�����½�ͷ������
                if( record.get( "ddf_cable_fast" ) == null ){setCellValue( 16, "" );}
                else{setCellValue( 16, record.get( "ddf_cable_fast" ).toString() );}
                
                // ODF��β�˽�ͷ������
                if( record.get( "odf_interface_fast" ) == null ){setCellValue( 17, "" );}
                else{setCellValue( 17, record.get( "odf_interface_fast" ).toString() );}
                
                // ODF/�豸��β�˱�ǩ�˲鲹��
                if( record.get( "odf_label_check" ) == null ){setCellValue( 18, "" );}
                else{setCellValue( 18, record.get( "odf_label_check" ).toString() );}
                
                // DDF�����±�ǩ�˲鲹��
                if( record.get( "ddf_cabel_check" ) == null ){setCellValue( 19, "" );}
                else{setCellValue( 19, record.get( "ddf_cabel_check" ).toString() );}
                
                // ά������ʦ�˲����
                if( record.get( "auditresult" ) == null ){setCellValue( 20, "" );}
                else{setCellValue( 20, record.get( "auditresult" ).toString() );}
                
                // ��ע
                if( record.get( "checkremark" ) == null ){setCellValue( 21, "" );}
                else{setCellValue( 21, record.get( "checkremark" ).toString() );}
                
                
                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }
    
    
    /**
     * ���Ĳ������SDH
     */
    public void exportMicroResult( List list,   int sheetIndex, ExcelStyle excelstyle ){

        DynaBean record;
       
        activeSheet( sheetIndex );
     
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 4; //������
        logger.info( "�õ�list" );
        
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                // ���
                setCellValue( 0, (r - 3) + "");
                		
                // �豸����              
                if( record.get( "equipment_namea" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "equipment_namea" ).toString() );}
                
                if( record.get( "equipment_nameb" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "equipment_nameb" ).toString() );}

                // ��ά��˾ 
                if( record.get( "contractorname" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "contractorname" ).toString() );}

                // ִ��������
                if( record.get( "username" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "username" ).toString() );}

                // ִ������
                if( record.get( "executetime" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "executetime" ).toString() );}
                
                // �豸������� is_clean
                if( record.get( "is_clean" ) == null ){setCellValue( 6, "" );}
                else if("0".equals(record.get( "is_clean" ).toString())) {
                	setCellValue( 6, "��" );
                }else{setCellValue( 6, "��");}
                
                // �豸�����¶�
                if( record.get( "obve_temperature" ) == null ){setCellValue( 7, "" );}
                else{setCellValue( 7, record.get( "obve_temperature" ).toString() );}
                
                // ���񶥶�ָʾ��״̬
                if( record.get( "is_top_pilotlamp" ) == null ){setCellValue( 8, "" );}
                else if("1".equals(record.get( "is_top_pilotlamp" ).toString())) {
                	setCellValue( 8, "��" );
                }
                else{setCellValue( 8, "��" );}
                
                // ����ָʾ��״̬
                if( record.get( "is_veneer_pilotlamp" ) == null ){setCellValue( 9, "" );} 
                else if ("1".equals(record.get( "is_veneer_pilotlamp" ).toString())) {
                	setCellValue( 9, "��" );
                }
                else{setCellValue( 9, "��");}
                
                // �豸���������
                if( record.get( "is_dustproof_clean" ) == null ){setCellValue( 10, "" );} 
                else if("1".equals(record.get( "is_dustproof_clean" ).toString())) {
                	setCellValue( 10, "��");
                }
                else{setCellValue( 10, "��");;}
                
                // ��������״̬
                if( record.get( "fan_state" ) == null ){setCellValue( 11, "" );}
                else{setCellValue( 11, record.get( "fan_state" ).toString() );}
                
                // ���ⵥԪ���ӹ�
                if( record.get( "outdoor_fast" ) == null ){setCellValue( 12, "" );}
                else{setCellValue( 12, record.get( "outdoor_fast" ).toString() );}
                
                // �����¶�
                if( record.get( "machine_temperature" ) == null ){setCellValue( 13, "" );}
                else{setCellValue( 13, record.get( "machine_temperature" ).toString() );}
                
                // ����ʪ�� machine_humidity
                if( record.get( "machine_humidity" ) == null ){setCellValue( 14, "" );}
                else{setCellValue( 14, record.get( "machine_humidity" ).toString() );}
                
                // DDF��/���߼����°���
                if( record.get( "ddf_colligation" ) == null ){setCellValue( 15, "" );}
                else{setCellValue( 15, record.get( "ddf_colligation" ).toString() );}
                
                // 2M���¡����߽�ͷ������
                if( record.get( "label_check" ) == null ){setCellValue( 16, "" );}
                else{setCellValue( 16, record.get( "label_check" ).toString() );}
                
                // 2M���±�ǩ�˲鲹��
                if( record.get( "cabel_check" ) == null ){setCellValue( 17, "" );}
                else{setCellValue( 17, record.get( "cabel_check" ).toString() );}                
                
                // ά������ʦ�˲����
                if( record.get( "auditresult" ) == null ){setCellValue( 18, "" );}
                else{setCellValue( 18, record.get( "auditresult" ).toString() );}
                
                // ��ע
                if( record.get( "checkremark" ) == null ){setCellValue( 19, "" );}
                else{setCellValue( 19, record.get( "checkremark" ).toString() );}
                
                
                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }
    
    /**
     * �����FSO
     */
    public void exportFsoResult( List list,  int sheetIndex, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( sheetIndex );
     
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 4; //������
        logger.info( "�õ�list" );
        
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                // ���
                setCellValue( 0, (r - 3) + "");
                
                // ��ά��˾ 
                if( record.get( "contractorname" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "contractorname" ).toString() );}

                // ִ��������
                if( record.get( "username" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "username" ).toString() );}

                // ִ������
                if( record.get( "executetime" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "executetime" ).toString() );}
                		
                // �豸����              
                if( record.get( "equipment_namea" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "equipment_namea" ).toString() );}
                
                
                if( record.get( "equipment_nameb" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "equipment_nameb" ).toString() );}

                // �豸�ͺ�
                if( record.get( "equipment_model" ) == null ){setCellValue( 6, "" );}
                else{setCellValue( 6, record.get( "equipment_model" ).toString() );}
                
                // �����
                if( record.get( "machine_no" ) == null ){setCellValue( 7, "" );}
                else{setCellValue( 7, record.get( "machine_no" ).toString() );}
                // �豸/��Դ����
                if( record.get( "power_type" ) == null ){setCellValue( 8, "" );}
                else{setCellValue( 8, record.get( "power_type" ).toString() );}
                
                // �豸������� is_clean
                if( record.get( "is_clean" ) == null ){setCellValue( 9, "" );}
                else if("0".equals(record.get( "is_clean" ).toString())) {
                	setCellValue( 9, "��" );
                }else{setCellValue( 9, "��");}
                
                // �豸�����¶�
                if( record.get( "obve_temperature" ) == null ){setCellValue( 10, "" );}
                else{setCellValue( 10, record.get( "obve_temperature" ).toString() );}
                
                // �豸ָʾ��״̬
                if( record.get( "is_machine_pilotlamp" ) == null ){setCellValue( 11, "" );} 
                else if("1".equals(record.get( "is_machine_pilotlamp" ).toString())) {
                	setCellValue( 11, "��" );
                }
                else{setCellValue( 11, "��" );}
                
                // �⹦�ʲ�ѯ
                if( record.get( "search_light_power" ) == null ){setCellValue( 12, "" );}
                else{setCellValue( 12, record.get( "search_light_power" ).toString() );}
                
                // β��/��Դ�߰���
                if( record.get( "power_colligation" ) == null ){setCellValue( 13, "" );}
                else{setCellValue( 13, record.get( "power_colligation" ).toString() );}
                
                // ��Դ��/β�˱�ǩ�˲鲹��
                if( record.get( "power_label_check" ) == null ){setCellValue( 14, "" );}
                else{setCellValue( 14, record.get( "power_label_check" ).toString() );}
                
                // ά������ʦ�˲����
                if( record.get( "auditresult" ) == null ){setCellValue( 15, "" );}
                else{setCellValue( 15, record.get( "auditresult" ).toString() );}
                
                // ��ע
                if( record.get( "checkremark" ) == null ){setCellValue( 16, "" );}
                else{setCellValue( 16, record.get( "checkremark" ).toString() );}
                
                
                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }
    
    /**
     * �⽻ά��
     */
    public void exportFiberResult( List list,  int sheetIndex, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( sheetIndex );
     
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 4; //������
        logger.info( "�õ�list" );
        
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                // ���
                setCellValue( 0, (r - 3) + "");
                
                // �豸����              
                if( record.get( "equipment_name" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "equipment_name" ).toString() );}

                // ��ά��˾ 
                if( record.get( "contractorname" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "contractorname" ).toString() );}

                // ִ��������
                if( record.get( "username" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "username" ).toString() );}

                // ִ������
                if( record.get( "executetime" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "executetime" ).toString() );}
                
                //ODF���ͼ�Ƿ����
                if( record.get( "is_update" ) == null ){setCellValue( 5, "" );}
                else if("0".equals(record.get( "is_update" ).toString())) {
                	setCellValue( 5, "��" );
                }else{setCellValue( 5, "��");}
                
                //�������̶�
                if( record.get( "is_clean" ) == null ){setCellValue( 6, "" );}
                else if(("0").equals(record.get("is_clean").toString()))
                {
                	setCellValue( 6, "һ��" );
                }else if(("1").equals(record.get("is_clean").toString()))
                {
                	setCellValue( 6, "����" );
                }else if(("2").equals(record.get("is_clean").toString())){
                	setCellValue( 6, "����" );
                }
                
                //�⽻���⻷�����̶�
                if( record.get( "is_fiberbox_clean" ) == null ){setCellValue( 7, "" );} 
                else if("0".equals(record.get( "is_fiberbox_clean" ).toString())) {
                	setCellValue( 7, "һ��" );
                }else if("1".equals(record.get( "is_fiberbox_clean" ).toString())){
                	setCellValue( 7, "����" );
                }else{setCellValue( 7, "����" );}
                
                //β���Ƿ��������
                if( record.get( "is_colligation" ) == null ){setCellValue( 8, "" );}
                else if("0".equals(record.get( "is_colligation" ).toString())){
                	setCellValue( 8, "��" );
                }else{
                	setCellValue( 8, "��" );
                }
                
                // ���±�ʶ�ƺ˲鲹��
                if( record.get( "is_fiber_check" ) == null ){setCellValue( 9, "" );}
                else if("0".equals(record.get( "is_fiber_check" ).toString())){
                	setCellValue( 9, "δ�˲鲹��" );
                }else{
                	setCellValue( 9, "�Ѻ˲鲹��" );
                }
                
                // β�˱�ʶ�ƺ˲鲹��
                if( record.get( "is_tailfiber_check" ) == null ){setCellValue( 10, "" );}
                else if("0".equals(record.get( "is_tailfiber_check" ).toString())){
                	setCellValue( 10, "δ�˲鲹��" );
                }else{
                	setCellValue( 10, "�Ѻ˲鲹��" );
                }
                
                
                // ά������ʦ�˲����
                if( record.get( "auditresult" ) == null ){setCellValue( 11, "" );}
                else{setCellValue( 11, record.get( "auditresult" ).toString() );}
                
                // ��ע
                if( record.get( "checkremark" ) == null ){setCellValue( 12, "" );}
                else{setCellValue( 12, record.get( "checkremark" ).toString() );}
                
                
                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }
}
