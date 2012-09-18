package com.cabletech.pipemanage.templates;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.partmanage.action.PartRequisitionAction;

public class PipeSegmentTemplates extends Template{
	private static Logger logger = Logger.getLogger(PartRequisitionAction.class
			.getName());

	public PipeSegmentTemplates(String urlPath) throws IOException {
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
				
				// 管道段编号
				if (record.get("pipeno") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("pipeno").toString());
				}
				
				// 代维单位
				if (record.get("contractorname") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("contractorname").toString());
				}
				
				// 维护区域
				if (record.get("county") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("county").toString());
				}
				
				// 所属乡镇
				if (record.get("town") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("town").toString());
				}
				
				// 分区
				if (record.get("area") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("area").toString());
				}
				
				// 管道名称
				if (record.get("pipename") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("pipename").toString());
				}
				
				// 是否验收
				if (record.get("isaccept") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("isaccept").toString());
				}	
				
				// a站点
				if (record.get("apoint") == null) {
					setCellValue(7, "");
				} else {
					setCellValue(7, record.get("apoint").toString());
				}
				
				//z站点
				if (record.get("zpoint") == null) {
					setCellValue(8, "");
				} else {
					setCellValue(8, record.get("zpoint").toString());
				}
				
				//距离
				if (record.get("length") == null) {
					setCellValue(9, "");
				} else {
					setCellValue(9, record.get("length").toString());
				}
				
				// 材料
				if (record.get("material") == null) {
					setCellValue(10, "");
				} else {
					setCellValue(10, record.get("material").toString());
				}
				
				// 产权
				if (record.get("right") == null) {
					setCellValue(11, "");
				} else {
					setCellValue(11, record.get("right").toString());
				}
				
				//管孔数
				if (record.get("pipehole") == null) {
					setCellValue(12, "");
				} else {
					setCellValue(12, record.get("pipehole").toString());
				}
						
				//管孔规格
				if (record.get("pipetype") == null) {
					setCellValue(13, "");
				} else {
					setCellValue(13, record.get("pipetype").toString());
				}
				
				//子管数
				if (record.get("subpipehole") == null) {
					setCellValue(14, "");
				} else {
					setCellValue(14, record.get("subpipehole").toString());
				}
				
				//未用子管数
				if (record.get("unuserpipe") == null) {
					setCellValue(15, "");
				} else {
					setCellValue(15, record.get("unuserpipe").toString());
				}	
				
				//备注1
				if (record.get("remark1") == null) {
					setCellValue(16, "");
				} else {
					setCellValue(16, record.get("remark1").toString());
				}	
				
				//备注2
				if (record.get("remark2") == null) {
					setCellValue(17, "");
				} else {
					setCellValue(17, record.get("remark2").toString());
				}	
				
				//图纸编号
				if (record.get("bluepointno") == null) {
					setCellValue(18, "");
				} else {
					setCellValue(18, record.get("bluepointno").toString());
				}	
				
				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}
}