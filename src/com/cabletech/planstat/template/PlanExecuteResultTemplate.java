package com.cabletech.planstat.template;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.planstat.beans.PatrolStatConditionBean;

public class PlanExecuteResultTemplate extends Template {
	private static Logger logger = Logger
			.getLogger(PlanExecuteResultTemplate.class.getName());

	public PlanExecuteResultTemplate(String urlPath) {
		super(urlPath);
	}

	/**
	 * ʹ�� DynaBean
	 * 
	 * @param list
	 *            List
	 * @param bean
	 */
	public void exportPlanExecuteResult(List list,
			PatrolStatConditionBean bean, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		String title = bean.getConName() + bean.getEndYear() + "��"
				+ bean.getEndMonth() + "�¼ƻ�ִ�н��һ����";
		this.curStyle = excelstyle.titleBoldFont(this.workbook);
		int r = 0;
		activeRow(r);
		setCellValue(0, title);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		r = 2; // ������
		logger.info("�õ�list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("��ʼѭ��д������");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);

				if (record.get("planname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("planname").toString());
				}

				if (record.get("executorid") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("executorid").toString());
				}

				if (record.get("planpoint") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, ((BigDecimal)record.get("planpoint")).toString());
				}

				if (record.get("factpoint") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, ((BigDecimal)record.get("factpoint")).toString());
				}

				if (record.get("patrolp") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, ((BigDecimal)record.get("patrolp")).toString());
				}

				if (record.get("examineresult") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("examineresult").toString());
				}

				r++; // ��һ��
			}
			logger.info("�ɹ�д��");
		}
	}

}
