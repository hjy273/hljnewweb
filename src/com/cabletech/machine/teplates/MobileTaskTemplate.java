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
     * 核心层或接入层SDH
     */
    public void exportContentResult( List list,  int sheetIndex,  ExcelStyle excelstyle ){

        DynaBean record;
      //  if("1".equals(type)) {
       // 	activeSheet( 0 );
  //      } else {
        	activeSheet( sheetIndex);
   //     }
     
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 4; //行索引
        logger.info( "得到list" );
        
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                // 序号
                setCellValue( 0, (r - 3) + "");
                		
                // 设备名称              
                if( record.get( "equipment_name" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "equipment_name" ).toString() );}

                // 代维公司 
                if( record.get( "contractorname" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "contractorname" ).toString() );}

                // 执行人姓名
                if( record.get( "username" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "username" ).toString() );}

                // 执行日期
                if( record.get( "executetime" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "executetime" ).toString() );}
                
                // 设备表面清洁 is_clean
                if( record.get( "is_clean" ) == null ){setCellValue( 5, "" );}
                else if("0".equals(record.get( "is_clean" ).toString())) {
                	setCellValue( 5, "否" );
                }else{setCellValue( 5, "是");}
                
                // 设备表面温度
                if( record.get( "obve_temperature" ) == null ){setCellValue( 6, "" );}
                else{setCellValue( 6, record.get( "obve_temperature" ).toString() );}
                
                // 机柜顶端指示灯状态
                if( record.get( "is_top_pilotlamp" ) == null ){setCellValue( 7, "" );}
                else if("1".equals(record.get( "is_top_pilotlamp" ).toString())) {
                	setCellValue( 7, "亮" );
                }
                else{setCellValue( 7, "灭" );}
                
                // 单板指示灯状态
                if( record.get( "is_veneer_pilotlamp" ) == null ){setCellValue( 8, "" );} 
                else if ("1".equals(record.get( "is_veneer_pilotlamp" ).toString())) {
                	setCellValue( 8, "亮" );
                }
                else{setCellValue( 8, "灭");}
                
                // 设备防尘网清洁
                if( record.get( "is_dustproof_clean" ) == null ){setCellValue( 9, "" );} 
                else if("1".equals(record.get( "is_dustproof_clean" ).toString())) {
                	setCellValue( 9, "是");
                }
                else{setCellValue( 9, "否");;}
                
                // 风扇运行状态
                if( record.get( "fan_state" ) == null ){setCellValue( 10, "" );}
                else{setCellValue( 10, record.get( "fan_state" ).toString() );}
                
                // 机房地面清洁
                if( record.get( "machine_floor_clean" ) == null ){setCellValue( 11, "" );}
                else{setCellValue( 11, record.get( "machine_floor_clean" ).toString() );}
                
                // 机房温度
                if( record.get( "machine_temperature" ) == null ){setCellValue( 12, "" );}
                else{setCellValue( 12, record.get( "machine_temperature" ).toString() );}
                
                // 机房湿度 machine_humidity
                if( record.get( "machine_humidity" ) == null ){setCellValue( 13, "" );}
                else{setCellValue( 13, record.get( "machine_humidity" ).toString() );}
                
                // DDF架/走线架线缆绑扎
                if( record.get( "ddf_colligation" ) == null ){setCellValue( 14, "" );}
                else{setCellValue( 14, record.get( "ddf_colligation" ).toString() );}
                
                // 尾纤保护 fiber_protect
                if( record.get( "fiber_protect" ) == null ){setCellValue( 15, "" );}
                else{setCellValue( 15, record.get( "fiber_protect" ).toString() );}
                
                // DDF架线缆接头检查紧固
                if( record.get( "ddf_cable_fast" ) == null ){setCellValue( 16, "" );}
                else{setCellValue( 16, record.get( "ddf_cable_fast" ).toString() );}
                
                // ODF架尾纤接头检查紧固
                if( record.get( "odf_interface_fast" ) == null ){setCellValue( 17, "" );}
                else{setCellValue( 17, record.get( "odf_interface_fast" ).toString() );}
                
                // ODF/设备侧尾纤标签核查补贴
                if( record.get( "odf_label_check" ) == null ){setCellValue( 18, "" );}
                else{setCellValue( 18, record.get( "odf_label_check" ).toString() );}
                
                // DDF架线缆标签核查补贴
                if( record.get( "ddf_cabel_check" ) == null ){setCellValue( 19, "" );}
                else{setCellValue( 19, record.get( "ddf_cabel_check" ).toString() );}
                
                // 维护工程师核查情况
                if( record.get( "auditresult" ) == null ){setCellValue( 20, "" );}
                else{setCellValue( 20, record.get( "auditresult" ).toString() );}
                
                // 备注
                if( record.get( "checkremark" ) == null ){setCellValue( 21, "" );}
                else{setCellValue( 21, record.get( "checkremark" ).toString() );}
                
                
                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }
    
    
    /**
     * 核心层或接入层SDH
     */
    public void exportMicroResult( List list,   int sheetIndex, ExcelStyle excelstyle ){

        DynaBean record;
       
        activeSheet( sheetIndex );
     
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 4; //行索引
        logger.info( "得到list" );
        
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                // 序号
                setCellValue( 0, (r - 3) + "");
                		
                // 设备名称              
                if( record.get( "equipment_namea" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "equipment_namea" ).toString() );}
                
                if( record.get( "equipment_nameb" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "equipment_nameb" ).toString() );}

                // 代维公司 
                if( record.get( "contractorname" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "contractorname" ).toString() );}

                // 执行人姓名
                if( record.get( "username" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "username" ).toString() );}

                // 执行日期
                if( record.get( "executetime" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "executetime" ).toString() );}
                
                // 设备表面清洁 is_clean
                if( record.get( "is_clean" ) == null ){setCellValue( 6, "" );}
                else if("0".equals(record.get( "is_clean" ).toString())) {
                	setCellValue( 6, "否" );
                }else{setCellValue( 6, "是");}
                
                // 设备表面温度
                if( record.get( "obve_temperature" ) == null ){setCellValue( 7, "" );}
                else{setCellValue( 7, record.get( "obve_temperature" ).toString() );}
                
                // 机柜顶端指示灯状态
                if( record.get( "is_top_pilotlamp" ) == null ){setCellValue( 8, "" );}
                else if("1".equals(record.get( "is_top_pilotlamp" ).toString())) {
                	setCellValue( 8, "亮" );
                }
                else{setCellValue( 8, "灭" );}
                
                // 单板指示灯状态
                if( record.get( "is_veneer_pilotlamp" ) == null ){setCellValue( 9, "" );} 
                else if ("1".equals(record.get( "is_veneer_pilotlamp" ).toString())) {
                	setCellValue( 9, "亮" );
                }
                else{setCellValue( 9, "灭");}
                
                // 设备防尘网清洁
                if( record.get( "is_dustproof_clean" ) == null ){setCellValue( 10, "" );} 
                else if("1".equals(record.get( "is_dustproof_clean" ).toString())) {
                	setCellValue( 10, "是");
                }
                else{setCellValue( 10, "否");;}
                
                // 风扇运行状态
                if( record.get( "fan_state" ) == null ){setCellValue( 11, "" );}
                else{setCellValue( 11, record.get( "fan_state" ).toString() );}
                
                // 室外单元检查加固
                if( record.get( "outdoor_fast" ) == null ){setCellValue( 12, "" );}
                else{setCellValue( 12, record.get( "outdoor_fast" ).toString() );}
                
                // 机房温度
                if( record.get( "machine_temperature" ) == null ){setCellValue( 13, "" );}
                else{setCellValue( 13, record.get( "machine_temperature" ).toString() );}
                
                // 机房湿度 machine_humidity
                if( record.get( "machine_humidity" ) == null ){setCellValue( 14, "" );}
                else{setCellValue( 14, record.get( "machine_humidity" ).toString() );}
                
                // DDF架/走线架线缆绑扎
                if( record.get( "ddf_colligation" ) == null ){setCellValue( 15, "" );}
                else{setCellValue( 15, record.get( "ddf_colligation" ).toString() );}
                
                // 2M线缆、馈线接头检查紧固
                if( record.get( "label_check" ) == null ){setCellValue( 16, "" );}
                else{setCellValue( 16, record.get( "label_check" ).toString() );}
                
                // 2M线缆标签核查补贴
                if( record.get( "cabel_check" ) == null ){setCellValue( 17, "" );}
                else{setCellValue( 17, record.get( "cabel_check" ).toString() );}                
                
                // 维护工程师核查情况
                if( record.get( "auditresult" ) == null ){setCellValue( 18, "" );}
                else{setCellValue( 18, record.get( "auditresult" ).toString() );}
                
                // 备注
                if( record.get( "checkremark" ) == null ){setCellValue( 19, "" );}
                else{setCellValue( 19, record.get( "checkremark" ).toString() );}
                
                
                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }
    
    /**
     * 接入层FSO
     */
    public void exportFsoResult( List list,  int sheetIndex, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( sheetIndex );
     
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 4; //行索引
        logger.info( "得到list" );
        
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                // 序号
                setCellValue( 0, (r - 3) + "");
                
                // 代维公司 
                if( record.get( "contractorname" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "contractorname" ).toString() );}

                // 执行人姓名
                if( record.get( "username" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "username" ).toString() );}

                // 执行日期
                if( record.get( "executetime" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "executetime" ).toString() );}
                		
                // 设备名称              
                if( record.get( "equipment_namea" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "equipment_namea" ).toString() );}
                
                
                if( record.get( "equipment_nameb" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "equipment_nameb" ).toString() );}

                // 设备型号
                if( record.get( "equipment_model" ) == null ){setCellValue( 6, "" );}
                else{setCellValue( 6, record.get( "equipment_model" ).toString() );}
                
                // 机身号
                if( record.get( "machine_no" ) == null ){setCellValue( 7, "" );}
                else{setCellValue( 7, record.get( "machine_no" ).toString() );}
                // 设备/电源类型
                if( record.get( "power_type" ) == null ){setCellValue( 8, "" );}
                else{setCellValue( 8, record.get( "power_type" ).toString() );}
                
                // 设备表面清洁 is_clean
                if( record.get( "is_clean" ) == null ){setCellValue( 9, "" );}
                else if("0".equals(record.get( "is_clean" ).toString())) {
                	setCellValue( 9, "否" );
                }else{setCellValue( 9, "是");}
                
                // 设备表面温度
                if( record.get( "obve_temperature" ) == null ){setCellValue( 10, "" );}
                else{setCellValue( 10, record.get( "obve_temperature" ).toString() );}
                
                // 设备指示灯状态
                if( record.get( "is_machine_pilotlamp" ) == null ){setCellValue( 11, "" );} 
                else if("1".equals(record.get( "is_machine_pilotlamp" ).toString())) {
                	setCellValue( 11, "亮" );
                }
                else{setCellValue( 11, "灭" );}
                
                // 光功率查询
                if( record.get( "search_light_power" ) == null ){setCellValue( 12, "" );}
                else{setCellValue( 12, record.get( "search_light_power" ).toString() );}
                
                // 尾纤/电源线绑扎
                if( record.get( "power_colligation" ) == null ){setCellValue( 13, "" );}
                else{setCellValue( 13, record.get( "power_colligation" ).toString() );}
                
                // 电源线/尾纤标签核查补贴
                if( record.get( "power_label_check" ) == null ){setCellValue( 14, "" );}
                else{setCellValue( 14, record.get( "power_label_check" ).toString() );}
                
                // 维护工程师核查情况
                if( record.get( "auditresult" ) == null ){setCellValue( 15, "" );}
                else{setCellValue( 15, record.get( "auditresult" ).toString() );}
                
                // 备注
                if( record.get( "checkremark" ) == null ){setCellValue( 16, "" );}
                else{setCellValue( 16, record.get( "checkremark" ).toString() );}
                
                
                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }
    
    /**
     * 光交维护
     */
    public void exportFiberResult( List list,  int sheetIndex, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( sheetIndex );
     
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 4; //行索引
        logger.info( "得到list" );
        
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                // 序号
                setCellValue( 0, (r - 3) + "");
                
                // 设备名称              
                if( record.get( "equipment_name" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "equipment_name" ).toString() );}

                // 代维公司 
                if( record.get( "contractorname" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "contractorname" ).toString() );}

                // 执行人姓名
                if( record.get( "username" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "username" ).toString() );}

                // 执行日期
                if( record.get( "executetime" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "executetime" ).toString() );}
                
                //ODF面板图是否更新
                if( record.get( "is_update" ) == null ){setCellValue( 5, "" );}
                else if("0".equals(record.get( "is_update" ).toString())) {
                	setCellValue( 5, "否" );
                }else{setCellValue( 5, "是");}
                
                //箱内清洁程度
                if( record.get( "is_clean" ) == null ){setCellValue( 6, "" );}
                else if(("0").equals(record.get("is_clean").toString()))
                {
                	setCellValue( 6, "一般" );
                }else if(("1").equals(record.get("is_clean").toString()))
                {
                	setCellValue( 6, "良好" );
                }else if(("2").equals(record.get("is_clean").toString())){
                	setCellValue( 6, "优秀" );
                }
                
                //光交箱外环境清洁程度
                if( record.get( "is_fiberbox_clean" ) == null ){setCellValue( 7, "" );} 
                else if("0".equals(record.get( "is_fiberbox_clean" ).toString())) {
                	setCellValue( 7, "一般" );
                }else if("1".equals(record.get( "is_fiberbox_clean" ).toString())){
                	setCellValue( 7, "良好" );
                }else{setCellValue( 7, "优秀" );}
                
                //尾纤是否绑扎整齐
                if( record.get( "is_colligation" ) == null ){setCellValue( 8, "" );}
                else if("0".equals(record.get( "is_colligation" ).toString())){
                	setCellValue( 8, "否" );
                }else{
                	setCellValue( 8, "是" );
                }
                
                // 光缆标识牌核查补贴
                if( record.get( "is_fiber_check" ) == null ){setCellValue( 9, "" );}
                else if("0".equals(record.get( "is_fiber_check" ).toString())){
                	setCellValue( 9, "未核查补贴" );
                }else{
                	setCellValue( 9, "已核查补贴" );
                }
                
                // 尾纤标识牌核查补贴
                if( record.get( "is_tailfiber_check" ) == null ){setCellValue( 10, "" );}
                else if("0".equals(record.get( "is_tailfiber_check" ).toString())){
                	setCellValue( 10, "未核查补贴" );
                }else{
                	setCellValue( 10, "已核查补贴" );
                }
                
                
                // 维护工程师核查情况
                if( record.get( "auditresult" ) == null ){setCellValue( 11, "" );}
                else{setCellValue( 11, record.get( "auditresult" ).toString() );}
                
                // 备注
                if( record.get( "checkremark" ) == null ){setCellValue( 12, "" );}
                else{setCellValue( 12, record.get( "checkremark" ).toString() );}
                
                
                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }
}
