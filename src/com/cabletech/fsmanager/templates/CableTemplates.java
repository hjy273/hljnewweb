package com.cabletech.fsmanager.templates;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.partmanage.action.PartRequisitionAction;

public class CableTemplates extends Template{
	private static Logger logger = Logger.getLogger(PartRequisitionAction.class
			.getName());

	public CableTemplates(String urlPath) throws IOException {
		super(urlPath);

	}

	/**
	 * 使用 DynaBean
	 * 
	 * @param list
	 *            List
	 */
	public void exportReport(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 2; // 行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			
			String ins = null;
			String fwjg =null;
			
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				
				// 名称
				if (record.get("cableno") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("cableno").toString());
				}
				
				// 编码
				if (record.get("contractorname") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("contractorname").toString());
				}
				
				// 原名称
				if (record.get("area") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("area").toString());
				}
				// 生厂厂家
				if (record.get("county") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("county").toString());
				}
				
				// 规格型号
				if (record.get("systemname") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("systemname").toString());
				}
				
				// 列数
				if (record.get("cablename") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("cablename").toString());
				}
				
				// 行数
				if (record.get("cablelinename") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("cablelinename").toString());
				}
				
				// 列排列方式
				if (record.get("apoint") == null) {
					setCellValue(7, "");
				} else {
					setCellValue(7, record.get("apoint").toString());
				}
				
				if (record.get("zpoint") == null) {
					setCellValue(8, "");
				} else {
					setCellValue(8, record.get("zpoint").toString());
				}
				
				if (record.get("laytype") == null) {
					setCellValue(9, "");
				} else {
					setCellValue(9, record.get("laytype").toString());
				}
				
				// 产权性质
				if (record.get("company") == null) {
					setCellValue(10, "");
				} else {
					setCellValue(10, record.get("company").toString());
				}
				
				
				// 所属县区
				if (record.get("construct") == null) {
					setCellValue(11, "");
				} else {
					setCellValue(11, record.get("construct").toString());
				}
				
				if (record.get("property") == null) {
					setCellValue(12, "");
				} else {
					setCellValue(12, record.get("property").toString());
				}
						
				
				if (record.get("cabletype") == null) {
					setCellValue(13, "");
				} else {
					setCellValue(13, record.get("cabletype").toString());
				}
				if (record.get("createtime") == null) {
					setCellValue(14, "");
				} else {
					setCellValue(14, record.get("createtime").toString());
				}			
				if (record.get("fibertype") == null) {
					setCellValue(15, "");
				} else {
					setCellValue(15, record.get("fibertype").toString());
				}	
				
				if (record.get("fibernumber") == null) {
					setCellValue(16, "");
				} else {
					setCellValue(16, record.get("fibernumber").toString());
				}	
				if (record.get("cablelength") == null) {
					setCellValue(17, "");
				} else {
					setCellValue(17, record.get("cablelength").toString());
				}	
				if (record.get("unusecable") == null) {
					setCellValue(18, "");
				} else {
					setCellValue(18, record.get("unusecable").toString());
				}	
				if (record.get("remark") == null) {
					setCellValue(19, "");
				} else {
					setCellValue(19, record.get("remark").toString());
				}	
				if (record.get("isaccept") == null) {
					setCellValue(20, "");
				} else {
					setCellValue(20, record.get("isaccept").toString());
				}	
				if (record.get("blueprintno") == null) {
					setCellValue(21, "");
				} else {
					setCellValue(21, record.get("blueprintno").toString());
				}	
				if (record.get("fiberlength") == null) {
					setCellValue(22, "");
				} else {
					setCellValue(22, record.get("fiberlength").toString());
				}	
				
				
				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}
}