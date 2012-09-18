package com.cabletech.linecut.Templates;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.partmanage.action.PartRequisitionAction;

public class StatTemplate extends Template {
	private static Logger logger = Logger.getLogger(PartRequisitionAction.class
			.getName());

	public StatTemplate(String urlPath) {
		super(urlPath);
		// TODO Auto-generated constructor stub
	}

	public void doExportForCountByType(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 3; // 行索引
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("contractorname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("contractorname").toString());
				}
				if (record.get("newcut") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("newcut").toString());
				}
				if (record.get("optimizecut") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("optimizecut").toString());
				}
				if (record.get("changecut") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("changecut").toString());
				}
				if (record.get("repairecut") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("repairecut").toString());
				}
				if (record.get("totalnum") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("totalnum").toString());
				}
				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}
	
	public void doExportForCountByLevel(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 3; // 行索引
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("contractorname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("contractorname").toString());
				}
				if (record.get("one") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("one").toString());
				}
				if (record.get("two") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("two").toString());
				}
				if (record.get("three") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("three").toString());
				}
				if (record.get("four") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("four").toString());
				}
				if (record.get("five") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("five").toString());
				}
				if (record.get("totalnum") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("totalnum").toString());
				}
				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}
	
	
	public void doExportForQueryStat(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 3; // 行索引
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("contractorname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("contractorname").toString());
				}
				if (record.get("reuser") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("reuser").toString());
				}
				if (record.get("retime") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("retime").toString());
				}
				if (record.get("name") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("name").toString());
				}
				if (record.get("sublinename") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("sublinename").toString());
				}
				if (record.get("reason") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("reason").toString());
				}
				if (record.get("address") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("address").toString());
				}
				if (record.get("cuttype") == null) {
					setCellValue(7, "");
				} else {
					setCellValue(7, record.get("cuttype").toString());
				}
				if (record.get("protime") == null) {
					setCellValue(8, "");
				} else {
					setCellValue(8, record.get("protime").toString());
				}
				if (record.get("essetime") == null) {
					setCellValue(9, "");
				} else {
					setCellValue(9, record.get("essetime").toString());
				}
				if (record.get("prousetime") == null) {
					setCellValue(10, "");
				} else {
					setCellValue(10, record.get("prousetime").toString());
				}
				if (record.get("usetime") == null) {
					setCellValue(11, "");
				} else {
					setCellValue(11, record.get("usetime").toString());
				}
				if (record.get("provalue") == null) {
					setCellValue(12, "");
				} else {
					setCellValue(12, record.get("provalue").toString());
				}
				if (record.get("endvalue") == null) {
					setCellValue(13, "");
				} else {
					setCellValue(13, record.get("endvalue").toString());
				}
				if (record.get("efsystem") == null) {
					setCellValue(14, "");
				} else {
					setCellValue(14, record.get("efsystem").toString());
				}
				if (record.get("updoc") == null) {
					setCellValue(15, "");
				} else {
					setCellValue(15, record.get("updoc").toString());
				}
				if (record.get("reremark") == null) {
					setCellValue(16, "");
				} else {
					setCellValue(16, record.get("reremark").toString());
				}
				if (record.get("reacce") == null) {
					setCellValue(17, "没有");
				} else {
					setCellValue(17, "有");
				}
				if (record.get("auditresult") == null) {
					setCellValue(18, "");
				} else {
					setCellValue(18, record.get("auditresult").toString());
				}
				if (record.get("audittime") == null) {
					setCellValue(19, "");
				} else {
					setCellValue(19, record.get("audittime").toString());
				}
				if (record.get("audituser") == null) {
					setCellValue(20, "");
				} else {
					setCellValue(20, record.get("audituser").toString());
				}
				if (record.get("auditremark") == null) {
					setCellValue(21, "");
				} else {
					setCellValue(21, record.get("auditremark").toString());
				}
				if (record.get("auditacce") == null) {
					setCellValue(22, "没有");
				} else {
					setCellValue(22, "有");
				}
				
				if (record.get("workremark") == null) {
					setCellValue(23, "");
				} else {
					setCellValue(23, record.get("workremark").toString());
				}
				
				if ("未通过审批".equals(record.get("type"))) {
						setCellValue(24, "");
						setCellValue(25, "");
						setCellValue(26, "");
						setCellValue(27, "");
				} else {

					if (record.get("auusername") == null) {
						setCellValue(24, "");
					} else {
						setCellValue(24, record.get("auusername").toString());
					}
					if (record.get("paudittime") == null) {
						setCellValue(25, "");
					} else {
						setCellValue(25, record.get("paudittime").toString());
					}
					if (record.get("type") == null) {
						setCellValue(26, "");
					} else {
						setCellValue(26, record.get("type").toString());
					}
					if (record.get("aauditremark") == null) {
						setCellValue(27, "");
					} else {
						setCellValue(27, record.get("aauditremark").toString());
					}
				}
				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}
	
	
	public void doExportForTimeByLevel(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 3; // 行索引
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("contractorname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("contractorname").toString());
				}
				if (record.get("one") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("one").toString());
				}
				if (record.get("two") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("two").toString());
				}
				if (record.get("three") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("three").toString());
				}
				if (record.get("four") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("four").toString());
				}
				if (record.get("five") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("five").toString());
				}
				if (record.get("totalnum") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("totalnum").toString());
				}
				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}
	
	public void doExportForTimeByType(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 3; // 行索引
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("contractorname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("contractorname").toString());
				}
				if (record.get("newcut") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("newcut").toString());
				}
				if (record.get("optimizecut") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("optimizecut").toString());
				}
				if (record.get("changecut") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("changecut").toString());
				}
				if (record.get("repairecut") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("repairecut").toString());
				}
				if (record.get("totalnum") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("totalnum").toString());
				}
				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}
	
}
