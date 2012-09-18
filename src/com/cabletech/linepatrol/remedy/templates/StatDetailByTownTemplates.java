package com.cabletech.linepatrol.remedy.templates;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.partmanage.action.PartRequisitionAction;

public class StatDetailByTownTemplates extends Template {
    private static Logger logger = Logger.getLogger(PartRequisitionAction.class.getName());

    public StatDetailByTownTemplates(String urlPath) throws IOException {
        super(urlPath);

    }

    /**
     * ʹ�� DynaBean
     * 
     * @param list
     *            List
     */
    public void exportReport(List statList, ExcelStyle excelstyle) {

        activeSheet(0);
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 0; // ������
        int i = 1; // ������
        if (statList != null) {
            logger.info("������" + r + "��");
            activeRow(r);
            Map itemMap = (Map) statList.get(0);
            Map labelMap = (Map) statList.get(1);
            Map scaleMap = (Map) statList.get(2);
            Iterator iterator1 = itemMap.keySet().iterator();
            while (iterator1.hasNext()) {
                String id = (String) iterator1.next();
                if (id == null) {
                    setCellValue(i, "");
                } else {
                    logger.info("��ʼѭ��д����������:" + itemMap.get(id));
                    setCellValue(i, itemMap.get(id).toString());// ����
                }
                i++;
            }
            setCellValue(i, "�ϼ�");
            r += 1;
            logger.info("������" + r + "��");
            activeRow(r);
            i = 1;
            Iterator iterator2 = itemMap.keySet().iterator();
            double sum = 0;
            while (iterator2.hasNext()) {
                String id = (String) iterator2.next();
                if (id == null) {
                    setCellValue(i, "");
                } else {
                    logger.info("��ʼѭ��д�����Ǩ�ķ�������:" + labelMap.get(id));
                    setCellValue(i, labelMap.get(id).toString() + "Ԫ");// ����Ǩ�ķ���
                    sum = sum + Double.parseDouble(labelMap.get(id).toString());
                }
                i++;
            }
            setCellValue(i, Double.toString(sum) + "Ԫ");
            r += 1;
            logger.info("������" + r + "��");
            activeRow(r);
            i = 1;
            Iterator iterator3 = itemMap.keySet().iterator();
            while (iterator3.hasNext()) {
                activeRow(r);
                String id = (String) iterator3.next();
                if (id == null) {
                    setCellValue(i, "");
                } else {
                    logger.info("��ʼѭ��д��ռ�ܷ��ñ�������:" + scaleMap.get(id));
                    setCellValue(i, scaleMap.get(id).toString());// ռ�ܷ��ñ���
                }
                i++;
            }
            setCellValue(i, "100%");
            logger.info("�ɹ�д��");
        }
    }
}