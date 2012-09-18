package com.cabletech.excel.templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.Region;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.BaseInfoService;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.planinfo.beans.YearMonthPlanBean;
import com.cabletech.planstat.domainobjects.PlanStat;

public class PlanTemplate extends Template {
	private static Logger logger = Logger.getLogger("PlanTemplate");

	public PlanTemplate(String urlPath) throws IOException {
		super(urlPath);
	}

	public void exportYearPlanResult(List list, ExcelStyle excelstyle,
			UserInfo userinfo, YearMonthPlanBean bean) throws Exception {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		HSSFCellStyle style = excelstyle.tenFourLineCenter(this.workbook);
		style.setWrapText(true);

		// ����ҳüҳ��
		excelstyle.setHeaderFooter(this.curSheet, userinfo);
		int r = 0; // ������
		BaseInfoService bis = new BaseInfoService();

		setCellValue(0, r, "��ѯ������");
		((HSSFCell) (this.curRow.getCell((short) 0))).setCellStyle(excelstyle
				.tenNoLine(this.workbook));
		if (bean.getRegionid() != null && !bean.getRegionid().equals("")) {
			com.cabletech.baseinfo.domainobjects.Region region = bis
					.loadRegion(bean.getRegionid());
			setCellValue(1, r, "��������" + region.getRegionName());
			((HSSFCell) (this.curRow.getCell((short) 1)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			logger.info("����" + region.getRegionName());
			setCellValue(2, r, "");
			((HSSFCell) (this.curRow.getCell((short) 2)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(3, r, "");
			((HSSFCell) (this.curRow.getCell((short) 3)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(4, r, "");
			((HSSFCell) (this.curRow.getCell((short) 4)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(5, r, "");
			((HSSFCell) (this.curRow.getCell((short) 5)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(6, r, "");
			((HSSFCell) (this.curRow.getCell((short) 6)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(7, r, "");
			((HSSFCell) (this.curRow.getCell((short) 7)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(8, r, "");
			((HSSFCell) (this.curRow.getCell((short) 8)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(9, r, "");
			((HSSFCell) (this.curRow.getCell((short) 9)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			this.curSheet
					.addMergedRegion(new Region(r, (short) 1, r, (short) 9));
			r++;
		}
		if (bean.getDeptid() != null && !bean.getDeptid().equals("")) {
			com.cabletech.baseinfo.domainobjects.Contractor contractor = bis
					.loadContractor(bean.getDeptid());
			logger.info("��ά��" + contractor.getContractorName());
			setCellValue(1, r, "��ά��λ��" + contractor.getContractorName());
			((HSSFCell) (this.curRow.getCell((short) 1)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(0, r, "");
			((HSSFCell) (this.curRow.getCell((short) 0)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(2, r, "");
			((HSSFCell) (this.curRow.getCell((short) 2)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(3, r, "");
			((HSSFCell) (this.curRow.getCell((short) 3)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(4, r, "");
			((HSSFCell) (this.curRow.getCell((short) 4)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(5, r, "");
			((HSSFCell) (this.curRow.getCell((short) 5)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(6, r, "");
			((HSSFCell) (this.curRow.getCell((short) 6)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(7, r, "");
			((HSSFCell) (this.curRow.getCell((short) 7)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(8, r, "");
			((HSSFCell) (this.curRow.getCell((short) 8)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(9, r, "");
			((HSSFCell) (this.curRow.getCell((short) 9)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			this.curSheet
					.addMergedRegion(new Region(r, (short) 1, r, (short) 9));
			r++;
		}
		if (bean.getYear() != null && !bean.getYear().equals("")) {
			logger.info("��ȣ�" + bean.getYear());
			setCellValue(0, r, "");
			((HSSFCell) (this.curRow.getCell((short) 0)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(1, r, "�ƻ���ȣ�" + bean.getYear());
			((HSSFCell) (this.curRow.getCell((short) 1)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(2, r, "");
			((HSSFCell) (this.curRow.getCell((short) 2)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(3, r, "");
			((HSSFCell) (this.curRow.getCell((short) 3)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(4, r, "");
			((HSSFCell) (this.curRow.getCell((short) 4)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(5, r, "");
			((HSSFCell) (this.curRow.getCell((short) 5)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(6, r, "");
			((HSSFCell) (this.curRow.getCell((short) 6)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(7, r, "");
			((HSSFCell) (this.curRow.getCell((short) 7)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(8, r, "");
			((HSSFCell) (this.curRow.getCell((short) 8)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(9, r, "");
			((HSSFCell) (this.curRow.getCell((short) 9)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			this.curSheet
					.addMergedRegion(new Region(r, (short) 1, r, (short) 9));
			r++;
		}
		if (bean.getStatus() != null && !bean.getStatus().equals("")) {
			if (bean.getStatus().equals("1")) {
				setCellValue(0, r, "");
				setCellValue(1, r, "�ƶ�������" + "ͨ������");
				setCellValue(2, r, "");
				setCellValue(3, r, "");
				setCellValue(4, r, "");
				setCellValue(5, r, "");
				setCellValue(6, r, "");
				setCellValue(7, r, "");
				setCellValue(8, r, "");
				setCellValue(9, r, "");
				this.curSheet.addMergedRegion(new Region(r, (short) 1, r,
						(short) 9));
			}
			if (bean.getStatus().equals("-1")) {
				setCellValue(0, r, "");
				setCellValue(1, r, "�ƶ�������" + "δͨ������");
				setCellValue(2, r, "");
				setCellValue(3, r, "");
				setCellValue(4, r, "");
				setCellValue(5, r, "");
				setCellValue(6, r, "");
				setCellValue(7, r, "");
				setCellValue(8, r, "");
				setCellValue(9, r, "");
				this.curSheet.addMergedRegion(new Region(r, (short) 1, r,
						(short) 9));
			}
			if (bean.getStatus().equals("0")) {
				setCellValue(0, r, "");
				setCellValue(1, r, "�ƶ�������" + "������");
				setCellValue(2, r, "");
				setCellValue(3, r, "");
				setCellValue(4, r, "");
				setCellValue(5, r, "");
				setCellValue(6, r, "");
				setCellValue(7, r, "");
				setCellValue(8, r, "");
				setCellValue(9, r, "");
				this.curSheet.addMergedRegion(new Region(r, (short) 1, r,
						(short) 9));
			}
			((HSSFCell) (this.curRow.getCell((short) 0)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			((HSSFCell) (this.curRow.getCell((short) 1)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			((HSSFCell) (this.curRow.getCell((short) 2)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			((HSSFCell) (this.curRow.getCell((short) 3)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			((HSSFCell) (this.curRow.getCell((short) 4)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			((HSSFCell) (this.curRow.getCell((short) 5)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			((HSSFCell) (this.curRow.getCell((short) 6)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			((HSSFCell) (this.curRow.getCell((short) 7)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			((HSSFCell) (this.curRow.getCell((short) 8)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			((HSSFCell) (this.curRow.getCell((short) 9)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			logger.info("״̬��" + bean.getStatus());
			r++;
		}

		// this.curSheet.addMergedRegion( new Region( 0, ( short )0, r - 1, (
		// short )0 ) );
		setCellValue(0, r, "�ƻ���Ϣ�����");
		((HSSFCell) (this.curRow.getCell((short) 0))).setCellStyle(excelstyle
				.titleBoldFont(this.workbook));
		setCellValue(1, r, "");
		setCellValue(2, r, "");
		setCellValue(3, r, "");
		setCellValue(4, r, "");
		setCellValue(5, r, "");
		setCellValue(6, r, "");
		setCellValue(7, r, "");
		setCellValue(8, r, "");
		setCellValue(9, r, "");
		((HSSFCell) (this.curRow.getCell((short) 1))).setCellStyle(excelstyle
				.tenNoLine(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 2))).setCellStyle(excelstyle
				.tenNoLine(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 3))).setCellStyle(excelstyle
				.tenNoLine(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 4))).setCellStyle(excelstyle
				.tenNoLine(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 5))).setCellStyle(excelstyle
				.tenNoLine(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 6))).setCellStyle(excelstyle
				.tenNoLine(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 7))).setCellStyle(excelstyle
				.tenNoLine(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 8))).setCellStyle(excelstyle
				.tenNoLine(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 9))).setCellStyle(excelstyle
				.tenNoLine(this.workbook));
		this.curSheet.addMergedRegion(new Region(r, (short) 0, r, (short) 9));
		this.curRow.setHeight((short) 720);
		r++;
		setCellValue(0, r, "�ƻ�����");
		setCellValue(1, r, "�ƻ�ʱ��");
		setCellValue(2, r, "�ƶ���");
		setCellValue(3, r, "�ƶ�����");
		setCellValue(4, r, "�ƶ�����");
		setCellValue(5, r, "������");
		setCellValue(6, r, "��������");
		setCellValue(7, r, "������Ϣ");
		setCellValue(8, r, "");
		setCellValue(9, r, "");
		((HSSFCell) (this.curRow.getCell((short) 0))).setCellStyle(excelstyle
				.tenFourLineBoldCenter(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 1))).setCellStyle(excelstyle
				.tenFourLineBoldCenter(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 2))).setCellStyle(excelstyle
				.tenFourLineBoldCenter(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 3))).setCellStyle(excelstyle
				.tenFourLineBoldCenter(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 4))).setCellStyle(excelstyle
				.tenFourLineBoldCenter(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 5))).setCellStyle(excelstyle
				.tenFourLineBoldCenter(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 6))).setCellStyle(excelstyle
				.tenFourLineBoldCenter(this.workbook));
		((HSSFCell) (this.curRow.getCell((short) 7))).setCellStyle(excelstyle
				.tenFourLineBoldCenter(this.workbook));
		this.curSheet.addMergedRegion(new Region(r, (short) 7, r, (short) 9));
		this.curRow.setHeight((short) 380);
		r++;

		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("��ʼѭ��д������");
			YearMonthPlanBean wbean;
			List tasklist = new ArrayList();
			while (iter.hasNext()) {
				wbean = (YearMonthPlanBean) iter.next();
				tasklist = wbean.getTasklist();
				logger.info("planname:" + wbean.getPlanname());

				this.setCellValue(0, r, wbean.getPlanname());
				((HSSFCell) (this.curRow.getCell((short) 0)))
						.setCellStyle(style);
				this.setCellValue(1, r, wbean.getYear());
				((HSSFCell) (this.curRow.getCell((short) 1)))
						.setCellStyle(style);
				this.setCellValue(2, r, wbean.getCreator());
				((HSSFCell) (this.curRow.getCell((short) 2)))
						.setCellStyle(style);
				this.setCellValue(3, r, wbean.getCreatedate());
				((HSSFCell) (this.curRow.getCell((short) 3)))
						.setCellStyle(style);
				this.setCellValue(4, r, wbean.getStatus());
				((HSSFCell) (this.curRow.getCell((short) 4)))
						.setCellStyle(style);
				this.setCellValue(5, r, wbean.getApprover());
				((HSSFCell) (this.curRow.getCell((short) 5)))
						.setCellStyle(style);
				this.setCellValue(6, r, wbean.getApprovedate());
				((HSSFCell) (this.curRow.getCell((short) 6)))
						.setCellStyle(style);

				for (int i = 0; i < tasklist.size(); i++) {
					record = (DynaBean) tasklist.get(i);
					if (i != 0) {
						setCellValue(0, r, "");
						setCellValue(1, r, "");
						setCellValue(2, r, "");
						setCellValue(3, r, "");
						setCellValue(4, r, "");
						setCellValue(5, r, "");
						setCellValue(6, r, "");
					}
					if (record.get("linelevel") != null) {
						this.setCellValue(7, r, record.get("linelevel")
								.toString());
					} else {
						this.setCellValue(7, r, "--");
					}
					if (record.get("taskname") != null) {
						this.setCellValue(8, r, record.get("taskname")
								.toString());
					} else {
						this.setCellValue(8, r, "--");
					}
					if (record.get("excutetimes") != null) {
						this.setCellValue(9, r, record.get("excutetimes")
								.toString());
					} else {
						this.setCellValue(9, r, "--");
					}
					((HSSFCell) (this.curRow.getCell((short) 7)))
							.setCellStyle(style);
					((HSSFCell) (this.curRow.getCell((short) 8)))
							.setCellStyle(style);
					((HSSFCell) (this.curRow.getCell((short) 9)))
							.setCellStyle(style);
					r++; // ��һ��
				}
				this.curSheet.addMergedRegion(new Region(r - tasklist.size(),
						(short) 0, r - 1, (short) 0));
				this.curSheet.addMergedRegion(new Region(r - tasklist.size(),
						(short) 1, r - 1, (short) 1));
				this.curSheet.addMergedRegion(new Region(r - tasklist.size(),
						(short) 2, r - 1, (short) 2));
				this.curSheet.addMergedRegion(new Region(r - tasklist.size(),
						(short) 3, r - 1, (short) 3));
				this.curSheet.addMergedRegion(new Region(r - tasklist.size(),
						(short) 4, r - 1, (short) 4));
				this.curSheet.addMergedRegion(new Region(r - tasklist.size(),
						(short) 5, r - 1, (short) 5));
				this.curSheet.addMergedRegion(new Region(r - tasklist.size(),
						(short) 6, r - 1, (short) 6));
			}
			logger.info("�ɹ�д��");
		}
	}

	/**
	 * �����鿴�ƻ�ִ�н���
	 * 
	 * @param list
	 * @param excelstyle
	 * @throws Exception
	 */
	public void exportPlanprogresstextResult(List list, String pmType,
			ExcelStyle excelstyle) throws Exception {

		HashMap record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		// ������
		setCellValue(0, 1, "�ƻ�����");
		setCellValue(1, 1, "��ʼʱ��");
		setCellValue(2, 1, "����ʱ��");
		setCellValue(3, 1, "���˽��");
		setCellValue(4, 1, "�ƻ�Ѳ����");
		setCellValue(5, 1, "ʵ��Ѳ����");
		setCellValue(6, 1, "Ѳ����");
		// �ж�pmTyep
		if ("group".equals(pmType)) {
			setCellValue(7, "Ѳ��ά����");
		} else {
			setCellValue(7, "Ѳ��Ա");
		}

		int r = 2; // ������
		logger.info("�õ�list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("��ʼѭ��д������");
			while (iter.hasNext()) {
				record = (HashMap) iter.next();
				activeRow(r);

				if (record.get("planname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("planname").toString());
				}

				if (record.get("begindate") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("begindate").toString());
				}

				if (record.get("enddate") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("enddate").toString());
				}

				if (record.get("examineresult") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("examineresult").toString());
				}

				if (record.get("planpointtimes") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("planpointtimes").toString());
				}

				if (record.get("actualpointtimes") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("actualpointtimes").toString());
				}

				if (record.get("percentage") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("percentage").toString());
				}

				if (record.get("patrolname") == null) {
					setCellValue(7, "");
				} else {
					setCellValue(7, record.get("patrolname").toString());
				}

				r++; // ��һ��
			}
			logger.info("�ɹ�д��");
		}
	}

	/**
	 * �����鿴�ƻ�ִ�н������ʾ��Ϣ
	 * 
	 * @param list
	 * @param excelstyle
	 * @throws Exception
	 */
	public void exportPlanstateResult(PlanStat planStatBean, String pmType,
			String executorname, ExcelStyle excelstyle) throws Exception {
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		logger.info("�õ�Bean");
		if (planStatBean != null) {

			logger.info("��ʼд������");

			// ������
			setCellValue(0, 2, "��������:" + planStatBean.getPlanname());
			HSSFFont font1 = this.workbook.createFont(); // ���������ʽ
			font1.setFontHeight((short) 220); // ���������С
			font1.setFontName("����"); // ���õ�Ԫ������
			HSSFCellStyle style2 = this.workbook.createCellStyle(); // ������Ԫ����.
			style2.setFont(font1); // �������ʽ���뵽��Ԫ������
			style2.setAlignment(HSSFCellStyle.VERTICAL_CENTER); // ��ֱ����
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // /ˮƽ����
			style2.setBorderTop(HSSFCellStyle.BORDER_NONE); // �ϱ߿���ʾ
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			this.curRow.getCell((short) 0).setCellStyle(style2);

			int r = 4; // ������

			// �ƻ�����
			String planname = planStatBean.getPlanname();
			if (planname == null) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, planname);
			}
			r++;

			// Ѳ��Ա��Ѳ��ά������
			if ("group".equals(pmType)) {
				setCellValue(0, r, "Ѳ��ά����");
			} else {
				setCellValue(0, r, "Ѳ����");
			}
			HSSFFont font2 = this.workbook.createFont(); // ���������ʽ
			font2.setFontHeight((short) 220); // ���������С
			font2.setFontName("����"); // ���õ�Ԫ������
			HSSFCellStyle style3 = this.workbook.createCellStyle(); // ������Ԫ����.
			style3.setFont(font2); // �������ʽ���뵽��Ԫ������
			this.curRow.getCell((short) 0).setCellStyle(style3);
			setCellValue(1, r, executorname);
			r++; // ��һ��

			// ��ʼʱ��
			String startdate = String.valueOf(planStatBean.getStartdate());
			if ("null".equals(startdate)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, startdate);
			}
			r++;

			// ����ʱ��
			String enddate = String.valueOf(planStatBean.getEnddate());
			if ("null".equals(enddate)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, enddate);
			}
			r++;

			// �ƻ�Ѳ���
			String planpointn = String.valueOf(planStatBean.getPlanpointn());
			if ("null".equals(planpointn)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, planpointn);
			}
			r++;

			// ʵ��Ѳ���
			String factpointn = String.valueOf(planStatBean.getFactpointn());
			if ("null".equals(factpointn)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, factpointn);
			}
			r++;

			// ©�����
			String leakpointn = String.valueOf(planStatBean.getLeakpointn());
			if ("null".equals(leakpointn)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, leakpointn);
			}
			r++;

			// �ƻ�Ѳ����
			String planpoint = String.valueOf(planStatBean.getPlanpoint());
			if ("null".equals(planpoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, planpoint);
			}
			r++;

			// ʵ��Ѳ����
			String factpoint = String.valueOf(planStatBean.getFactpoint());
			if ("null".equals(factpoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, factpoint);
			}
			r++;

			// Ѳ����
			String patrolp = String.valueOf(planStatBean.getPatrolp());
			if ("null".equals(patrolp)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, patrolp + "%");
			}
			r++;

			// �ؼ�����
			String keypoint = String.valueOf(planStatBean.getKeypoint());
			if ("null".equals(keypoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, keypoint);
			}
			r++;

			// ©��ؼ�����
			String leakkeypoint = String
					.valueOf(planStatBean.getLeakkeypoint());
			if ("null".equals(leakkeypoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, leakkeypoint);
			}
			r++;

			// Ѳ�������(����
			String patrolkm = String.valueOf(planStatBean.getPatrolkm());
			if ("null".equals(patrolkm)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, patrolkm);
			}
			r++;

			// Ѳ�췽ʽ
			String patrolmode = planStatBean.getPatrolmode();
			if (patrolmode == null) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, patrolmode.equals("01") ? "�ֶ�" : "�Զ�");
			}
			r++;

			// ���˽��
//			String examineresult = planStatBean.getExamineresult();

			logger.info("�ɹ�д��");
		}
	}

	/**
	 * �����鿴�ƻ�ִ�н������ʾ��Ϣ
	 * 
	 * @param list
	 * @param excelstyle
	 * @throws Exception
	 */
	public void exportPlanstateSheet(PlanStat planStatBean, String pmType,
			String patrolname, ExcelStyle excelstyle, int index)
			throws Exception {
		logger.info("�õ�Bean");
		if (planStatBean != null) {
			
			//  ִ�мƻ�������
			String planname = planStatBean.getPlanname();
			String sheetName = planname.replaceAll("/", "-");

			// ����һ���µ�sheetҳ
			if (index == 0) {
				this.activeSheet(0,sheetName);
			} else {
				this.cloneSheet(sheetName);
			}
		
			this.curStyle = excelstyle.defaultStyle(this.workbook);

			// ������
			setCellValue(0, 2, "��������:" + planStatBean.getPlanname());
			HSSFFont font1 = this.workbook.createFont(); // ���������ʽ
			font1.setFontHeight((short) 220); // ���������С
			font1.setFontName("����"); // ���õ�Ԫ������
			HSSFCellStyle style2 = this.workbook.createCellStyle(); // ������Ԫ����.
			style2.setFont(font1); // �������ʽ���뵽��Ԫ������
			style2.setAlignment(HSSFCellStyle.VERTICAL_CENTER); // ��ֱ����
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // /ˮƽ����
			style2.setBorderTop(HSSFCellStyle.BORDER_NONE); // �ϱ߿���ʾ
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			this.curRow.getCell((short) 0).setCellStyle(style2);

			logger.info("��ʼд������");

			int r = 4; // ������

			// �ƻ�����
			setCellValue(0, r, "�ƻ�����");			
			setCellValue(1, r, planname);
			r++;

			// Ѳ��Ա��Ѳ��ά������
			if ("group".equals(pmType)) {
				setCellValue(0, r, "Ѳ��ά����");
			} else {
				setCellValue(0, r, "Ѳ����");
			}
			HSSFFont font2 = this.workbook.createFont(); // ���������ʽ
			font2.setFontHeight((short) 220); // ���������С
			font2.setFontName("����"); // ���õ�Ԫ������
			HSSFCellStyle style3 = this.workbook.createCellStyle(); // ������Ԫ����.
			style3.setFont(font2); // �������ʽ���뵽��Ԫ������
			this.curRow.getCell((short) 0).setCellStyle(style3);

			setCellValue(1, r, patrolname);
			r++; // ��һ��

			// ��ʼʱ��
			setCellValue(0, r, "��ʼʱ��");
			String startdate = String.valueOf(planStatBean.getStartdate());
			if ("null".equals(startdate)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, startdate);
			}
			r++;

			// ����ʱ��
			setCellValue(0, r, "����ʱ��");
			String enddate = String.valueOf(planStatBean.getEnddate());
			if ("null".equals(enddate)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, enddate);
			}
			r++;

			// �ƻ�Ѳ���
			setCellValue(0, r, "�ƻ�Ѳ���");
			String planpointn = String.valueOf(planStatBean.getPlanpointn());
			if ("null".equals(planpointn)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, planpointn);
			}
			r++;

			// ʵ��Ѳ���
			setCellValue(0, r, "ʵ��Ѳ���");
			String factpointn = String.valueOf(planStatBean.getFactpointn());
			if ("null".equals(factpointn)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, factpointn);
			}
			r++;

			// ©�����
			setCellValue(0, r, "©�����");
			String leakpointn = String.valueOf(planStatBean.getLeakpointn());
			if ("null".equals(leakpointn)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, leakpointn);
			}
			r++;

			// �ƻ�Ѳ����
			setCellValue(0, r, "�ƻ�Ѳ����");
			String planpoint = String.valueOf(planStatBean.getPlanpoint());
			if ("null".equals(planpoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, planpoint);
			}
			r++;

			// ʵ��Ѳ����
			setCellValue(0, r, "ʵ��Ѳ����");
			String factpoint = String.valueOf(planStatBean.getFactpoint());
			if ("null".equals(factpoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, factpoint);
			}
			r++;

			// Ѳ����
			setCellValue(0, r, "Ѳ����");
			String patrolp = String.valueOf(planStatBean.getPatrolp());
			if ("null".equals(patrolp)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, patrolp + "%");
			}
			r++;

			// �ؼ�����
			setCellValue(0, r, "�ؼ�����");
			String keypoint = String.valueOf(planStatBean.getKeypoint());
			if ("null".equals(keypoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, keypoint);
			}
			r++;

			// ©��ؼ�����
			setCellValue(0, r, "©��ؼ�����");
			String leakkeypoint = String
					.valueOf(planStatBean.getLeakkeypoint());
			if ("null".equals(leakkeypoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, leakkeypoint);
			}
			r++;

			// Ѳ�������(����)
			setCellValue(0, r, "Ѳ�������(����)");
			String patrolkm = String.valueOf(planStatBean.getPatrolkm());
			if ("null".equals(patrolkm)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, patrolkm);
			}
			r++;

			// Ѳ�췽ʽ
			setCellValue(0, r, "Ѳ�췽ʽ");
			String patrolmode = planStatBean.getPatrolmode();
			if (patrolmode == null) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, patrolmode.equals("01") ? "�ֶ�" : "�Զ�");
			}
			r++;

			// ���˽��
			setCellValue(0, r, "���˽��");
//			String examineresult = planStatBean.getExamineresult();
//			setCellValue(1, r, "");

			logger.info("�ɹ�д��");
		}
	}
}
