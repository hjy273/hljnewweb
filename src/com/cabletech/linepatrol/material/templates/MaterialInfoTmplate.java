package com.cabletech.linepatrol.material.templates;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;

public class MaterialInfoTmplate extends Template {
	private static Logger logger = Logger.getLogger("Template");

	public MaterialInfoTmplate(String urlPath) {
		super(urlPath);
	}




	/**
	 * 执行导出动作
	 * 
	 * @param list
	 *            List
	 * @param excelstyle
	 *            ExcelStyle
	 */
	public void doExportType(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // 行索引
		if(list != null && list.size()>0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("typename") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("typename").toString());
				}
				if (record.get("regionname") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("regionname").toString());
				}				
				r++;
			}
		}
		}


	public void doExportModel(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // 行索引
		if(list != null && list.size()>0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("modelname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("modelname").toString());
				}
				if (record.get("typename") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("typename").toString());
				}
				if (record.get("unit") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("unit").toString());
				}
				r++;
			}
		}
		}
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
	                else{setCellValue( 1, record.get( "remark" ).toString() );}

	                r++; //下一行
	            }
	        }
	        logger.info( "成功写入" );
	    }
		
	}
