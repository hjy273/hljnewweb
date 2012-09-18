package com.cabletech.linepatrol.material.templates;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.Region;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;

public class MaterialStatTemplate extends Template {
	private static Logger logger = Logger.getLogger("Template");

	public MaterialStatTemplate(String urlPath) {
		super(urlPath);
	}

	/**
	 * 导出材料使用年统计数据
	 * 
	 * @param list
	 * @param excelstyle
	 */
	public void doExportMaterialStat(Map map, ExcelStyle excelstyle) throws Exception {
		activeSheet(0);
		super.curStyle=excelstyle.titleFont(super.workbook);
		int r = 0; // 行索引
		if (map.get("material_id_list") != null) {
			List materialIdList = (List) map.get("material_id_list");
			super.curSheet.addMergedRegion(new Region(r, (short) 0, r,
					(short) materialIdList.size()));
		}
		activeRow(r);
		setCellValue(0, "材料使用一览表");
		r++;
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		if (map != null) {
			if (map.get("contractor_id_list") != null) {
				if (map.get("material_id_list") != null) {
					activeRow(r);
					setCellValue(0, "代维公司");
					List materialIdList = (List) map.get("material_id_list");
					List materialNameList = (List) map
							.get("material_name_list");
					for (int i = 0; materialNameList != null
							&& i < materialNameList.size(); i++) {
						setCellValue(i + 1, (String) materialNameList.get(i));
					}
					r++;
					Map contractorResultMap = (Map) map.get("result_map");
					Map contractorNameMap = (Map) map
							.get("contractor_name_map");
					if (contractorNameMap != null) {
						Set keySet = contractorNameMap.keySet();
						Iterator it = keySet.iterator();
						String contractorId = "";
						String contractorName = "";
						String materialId = "";
						Double materialNumber = new Double(0.0);
						Map oneContractorResultMap;
						while (it != null && it.hasNext()) {
							activeRow(r);
							contractorId = (String) it.next();
							contractorName = (String) contractorNameMap
									.get(contractorId);
							setCellValue(0, contractorName);
							oneContractorResultMap = (Map) contractorResultMap
									.get(contractorId);
							for (int i = 0; materialIdList != null
									&& i < materialIdList.size(); i++) {
								materialId = (String) materialIdList.get(i);
								if (oneContractorResultMap != null
										&& oneContractorResultMap
												.containsKey(materialId)) {
									materialNumber = (Double) oneContractorResultMap
											.get(materialId);
									setCellValue(i + 1, Double
											.toString(materialNumber
													.doubleValue()));
								} else {
									setCellValue(i + 1, "0");
								}
							}
							r++;
						}
					}
				}
			}
		}
	}

}
