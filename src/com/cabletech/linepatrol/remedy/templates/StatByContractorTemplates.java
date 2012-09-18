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
     * ʹ�� DynaBean
     * 
     * @param list
     *            List
     */
    public void exportReport(List list, ExcelStyle excelstyle) {
        activeSheet(0);
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 0; // ������
        logger.info("�õ�list");
        if (list != null) {
            List itemList = (List) list.get(0);
            List labelList = (List) list.get(1);
            logger.info("��ʼѭ��д������");
            logger.info("������" + r + "��");
            activeRow(r);
            r += 1;
            for (int i = 0; i < itemList.size(); i++) {
                logger.info("д��������:" + itemList.get(i));
                setCellValue(i + 1, itemList.get(i).toString() + "(Ԫ)");
            }
            setCellValue(itemList.size() + 1, "�ϼ�(Ԫ)");
            // double sum = 0;
            for (int i = 0; i < labelList.size(); i++) {
                logger.info("������" + r + "��");
                activeRow(r);
                r++;
                // sum = 0;
                List detailLabelList = (List) labelList.get(i);
                for (int j = 0; j < detailLabelList.size() - 1; j++) {
                    logger.info("д��������Ǩ�ķ���:" + detailLabelList.get(j));
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
            logger.info("�ɹ�д��");
        }
    }
}