package com.cabletech.linepatrol.remedy.templates;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.partmanage.action.PartRequisitionAction;

public class StatByTownTemplates extends Template {
    private static Logger logger = Logger.getLogger(PartRequisitionAction.class.getName());

    public StatByTownTemplates(String urlPath) throws IOException {
        super(urlPath);

    }

    /**
     * 使用 DynaBean
     * 
     * @param list
     *            List
     */
    public void exportReport(List statList, ExcelStyle excelstyle) {

        activeSheet(0);
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 0; // 行索引
        int i = 1; // 列索引
        if (statList != null) {
            logger.info("创建第" + r + "行");
            activeRow(r);
            Map townMap = (Map) statList.get(0);
            Map labelMap = (Map) statList.get(1);
            Map scaleMap = (Map) statList.get(2);
            Iterator iterator1 = townMap.keySet().iterator();
            while (iterator1.hasNext()) {
                String townId = (String) iterator1.next();
                if (townId == null) {
                    setCellValue(i, "");
                } else {
                    logger.info("开始循环写入区域数据:" + townMap.get(townId));
                    setCellValue(i, townMap.get(townId).toString());// 区域
                }
                i++;
            }
            setCellValue(i, "合计");
            r += 1;
            logger.info("创建第" + r + "行");
            activeRow(r);
            i = 1;
            Iterator iterator2 = townMap.keySet().iterator();
            double sum = 0.0;
            while (iterator2.hasNext()) {
                String townId = (String) iterator2.next();
                if (townId == null) {
                    setCellValue(i, "");
                } else {
                    logger.info("开始循环写入各项迁改费用数据:" + labelMap.get(townId));
                    setCellValue(i, labelMap.get(townId).toString() + "元");// 各项迁改费用
                    sum = sum + Double.parseDouble(labelMap.get(townId).toString());
                }
                i++;
            }
            setCellValue(i, Double.toString(sum) + "元");
            r += 1;
            logger.info("创建第" + r + "行");
            activeRow(r);
            i = 1;
            Iterator iterator3 = townMap.keySet().iterator();
            while (iterator3.hasNext()) {
                activeRow(r);
                String townId = (String) iterator3.next();
                if (townId == null) {
                    setCellValue(i, "");
                } else {
                    logger.info("开始循环写入占总费用比例数据:" + scaleMap.get(townId));
                    setCellValue(i, scaleMap.get(townId).toString());// 占总费用比例
                }
                i++;
            }
            setCellValue(i, "100%");
            logger.info("成功写入");
        }
    }
}