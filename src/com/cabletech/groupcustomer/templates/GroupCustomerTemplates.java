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
     * 导出客户资料
     * @param list 客户资料信息
     * @param excelstyle Excel对象
     */
    public void exportGroupCustomerResult( List list, ExcelStyle excelstyle ){

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

                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }
    

    /**
     * 客户分析的导出
     * @param list 分析的客户信息
     * @param excelstyle Excel对象
     * @param customerNumStr 客户总数
     * @param parserpercent 预覆盖率
     * @param bean 查询的bean
     */
    public void exportGroupCustomerParserResult( List list, ExcelStyle excelstyle ,String customerNumStr, String parserpercent , GroupCustomerBean bean){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        // 标题
        activeRow( 1 );
        String titleValue = "客户总数：" + customerNumStr + "; 预覆盖率：" + list.size() + " / " + customerNumStr + " = " + parserpercent;
        setCellValue( 0, titleValue );
        
        // 查询条件内容
        activeRow( 2 );
        String strValue = "";;
        if(bean.getCity() != null && !"".equals(bean.getCity())) {
        	strValue += bean.getCity() + "区";
        } else {
        	strValue += "所有区域";
        }
        if(bean.getGrouptype() != null && !"".equals(bean.getGrouptype())) {
        	strValue += bean.getGrouptype() + "类";
        } else {
        	strValue += "所有类型";
        }

        strValue = strValue + "的集团客户的预覆盖范围为" + bean.getBestrowrange() + "米的预覆盖率为" + parserpercent;       
        setCellValue( 0, strValue);
        
        
        int r = 4; //行索引
        logger.info( "得到list" );
        
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
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
                

                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }
}
