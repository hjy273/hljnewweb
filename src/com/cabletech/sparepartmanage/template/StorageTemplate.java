package com.cabletech.sparepartmanage.template;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.sparepartmanage.domainobjects.SparepartConstant;

/**
 * StorageTemplate 备件库存Excel模板类
 * 
 */
public class StorageTemplate extends Template {
    private static Logger logger = Logger.getLogger("Template");

    private Map stateMap = new HashMap();

    public StorageTemplate(String urlPath) {
        super(urlPath);
        stateMap.put(SparepartConstant.MOBILE_WAIT_USE, "移动备用");
        stateMap.put(SparepartConstant.CONTRACTOR_WAIT_USE, "代维备用");
        stateMap.put(SparepartConstant.IS_RUNNING, "运行");
        stateMap.put(SparepartConstant.IS_MENDING, "送修");
        stateMap.put(SparepartConstant.IS_DISCARDED, "报废");
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
                if (record.get("product_factory") == null) {
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
                if (record.get("spare_part_model") == null) {
                    setCellValue(3, "");
                } else {
                    setCellValue(3, record.get("spare_part_model").toString());
                }
                if (record.get("software_version") == null) {
                    setCellValue(4, "");
                } else {
                    setCellValue(4, record.get("software_version").toString());
                }
                if (record.get("storage_position") == null) {
                    setCellValue(5, "");
                } else {
                    setCellValue(5, record.get("storage_position").toString());
                }
                if (record.get("storage_number") == null) {
                    setCellValue(6, "");
                } else {
                    setCellValue(6, record.get("storage_number").toString());
                }
                r++;
            }
        }
    }

}
