package com.cabletech.linepatrol.remedy.templates;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.partmanage.action.PartRequisitionAction;

public class StatByContractorTemplates extends Template {
    private static Logger logger = Logger.getLogger(PartRequisitionAction.class.getName());

    public StatByContractorTemplates(String urlPath) throws IOException {
        super(urlPath);

    }

    /**
     * 使用 DynaBean
     * 
     * @param list
     *            List
     */
    public void exportReport(List list, ExcelStyle excelstyle) {
        activeSheet(0);
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 0; // 行索引
        logger.info("得到list");
        if (list != null) {
            List itemList = (List) list.get(0);
            List labelList = (List) list.get(1);
            logger.info("开始循环写入数据");
            logger.info("创建第" + r + "行");
            activeRow(r);
            r += 1;
            for (int i = 0; i < itemList.size(); i++) {
                logger.info("写入修缮项:" + itemList.get(i));
                setCellValue(i + 1, itemList.get(i).toString() + "(元)");
            }
            setCellValue(itemList.size() + 1, "合计(元)");
            // double sum = 0;
            for (int i = 0; i < labelList.size(); i++) {
                logger.info("创建第" + r + "行");
                activeRow(r);
                r++;
                // sum = 0;
                List detailLabelList = (List) labelList.get(i);
                for (int j = 0; j < detailLabelList.size() - 1; j++) {
                    logger.info("写入修缮项迁改费用:" + detailLabelList.get(j));
                    if (j > 0) {
                        setCellValue(j, detailLabelList.get(j).toString());
                        // sum = sum +
                        // Double.parseDouble(detailLabelList.get(j).toString());
                    } else {
                        setCellValue(j, detailLabelList.get(j).toString());
                    }
                }
                // setCellValue(detailLabelList.size(), Double.toString(sum));
            }
            logger.info("成功写入");
        }
    }
}