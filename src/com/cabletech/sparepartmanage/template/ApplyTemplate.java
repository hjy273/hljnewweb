package com.cabletech.sparepartmanage.template;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.sparepartmanage.domainobjects.SparepartConstant;

/**
 * ApplyTemplate 备件申请Excel模板类
 * 
 */
public class ApplyTemplate extends Template {
    private static Logger logger = Logger.getLogger("Template");

    private Map useModeMap = new HashMap();

    private Map replaceTypeMap = new HashMap();

    private Map auditingStateMap = new HashMap();

    public ApplyTemplate(String urlPath) {
        super(urlPath);
        useModeMap.put(SparepartConstant.USE_DIRECT, "直接使用");
        useModeMap.put(SparepartConstant.USE_UPDATE, "更换使用");
        replaceTypeMap.put(SparepartConstant.REPLACE_NO_ACT, "退还旧备件");
        replaceTypeMap.put(SparepartConstant.REPLACE_MEND, "送修旧备件");
        replaceTypeMap.put(SparepartConstant.REPLACE_DISCARD, "报废旧备件");
        auditingStateMap.put(SparepartConstant.AUDITING_PASSED, "审核通过");
        auditingStateMap.put(SparepartConstant.AUDITING_NOTPASSED, "审核不通过");
        auditingStateMap.put(SparepartConstant.NOT_AUDITING, "待审核");
    }

    /**
     * 执行导出动作
     * 
     * @param list
     *            List
     * @param excelstyle
     *            ExcelStyle
     */
    public void doExport(List list, ExcelStyle excelstyle) {
        DynaBean record;
        activeSheet(0);
        super.curStyle = excelstyle.defaultStyle(super.workbook);
        int r = 3; // 行索引
        if (list != null) {
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                record = (DynaBean) iter.next();
                activeRow(r);
                if(record.get("product_factory") == null) {
                	setCellValue(0, "");
                } else {
                	setCellValue(0, record.get("product_factory").toString());
                }
                if (record.get("spare_part_name") == null) {
                    setCellValue(1, "");
                } else {
                    setCellValue(1, record.get("spare_part_name").toString());
                }
                if (record.get("serial_number") == null) {
                    setCellValue(2, "");
                } else {
                    setCellValue(2, record.get("serial_number").toString());
                }  
                if (record.get("username") == null) {
                    setCellValue(3, "");
                } else {
                	
                    setCellValue(3, record.get("username").toString());
                }
                if (record.get("apply_date") == null) {
                    setCellValue(4, "");
                } else {
                    setCellValue(4, record.get("apply_date").toString());
                }            
                if (record.get("auditing_state") == null) {
                    setCellValue(5, "");
                } else {
                    if (SparepartConstant.AUDITING_NOTPASSED.equals(record.get("auditing_state"))) {
                        HSSFCellStyle style = workbook.createCellStyle();
                        style.setBorderBottom(super.curStyle.getBorderBottom());
                        style.setBorderLeft(curStyle.getBorderLeft());
                        style.setBorderRight(curStyle.getBorderRight());
                        style.setBorderTop(curStyle.getBorderTop());
                        style.setBottomBorderColor(curStyle.getBottomBorderColor());
                        style.setLeftBorderColor(curStyle.getLeftBorderColor());
                        style.setRightBorderColor(curStyle.getRightBorderColor());
                        style.setTopBorderColor(curStyle.getTopBorderColor());
                        style.setWrapText(curStyle.getWrapText());
                        style.setFont(super.createFont(9, "宋体", false, false, 10));
                        setCellValue(5, auditingStateMap.get(record.get("auditing_state"))
                                .toString(), style);
                    } else {
                        setCellValue(5, auditingStateMap.get(record.get("auditing_state")).toString());
                    }
                }
                r++;
            }
        }
    }

}
