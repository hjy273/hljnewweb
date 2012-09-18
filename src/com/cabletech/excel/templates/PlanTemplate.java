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

		// 设置页眉页脚
		excelstyle.setHeaderFooter(this.curSheet, userinfo);
		int r = 0; // 行索引
		BaseInfoService bis = new BaseInfoService();

		setCellValue(0, r, "查询条件：");
		((HSSFCell) (this.curRow.getCell((short) 0))).setCellStyle(excelstyle
				.tenNoLine(this.workbook));
		if (bean.getRegionid() != null && !bean.getRegionid().equals("")) {
			com.cabletech.baseinfo.domainobjects.Region region = bis
					.loadRegion(bean.getRegionid());
			setCellValue(1, r, "所属区域：" + region.getRegionName());
			((HSSFCell) (this.curRow.getCell((short) 1)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			logger.info("区域：" + region.getRegionName());
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
			logger.info("代维：" + contractor.getContractorName());
			setCellValue(1, r, "代维单位：" + contractor.getContractorName());
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
			logger.info("年度：" + bean.getYear());
			setCellValue(0, r, "");
			((HSSFCell) (this.curRow.getCell((short) 0)))
					.setCellStyle(excelstyle.tenNoLine(this.workbook));
			setCellValue(1, r, "计划年度：" + bean.getYear());
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
				setCellValue(1, r, "移动审批：" + "通过审批");
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
				setCellValue(1, r, "移动审批：" + "未通过审批");
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
				setCellValue(1, r, "移动审批：" + "待审批");
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
			logger.info("状态：" + bean.getStatus());
			r++;
		}

		// this.curSheet.addMergedRegion( new Region( 0, ( short )0, r - 1, (
		// short )0 ) );
		setCellValue(0, r, "计划信息结果表");
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
		setCellValue(0, r, "计划名称");
		setCellValue(1, r, "计划时间");
		setCellValue(2, r, "制定人");
		setCellValue(3, r, "制定日期");
		setCellValue(4, r, "移动审批");
		setCellValue(5, r, "审批人");
		setCellValue(6, r, "审批日期");
		setCellValue(7, r, "任务信息");
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
			logger.info("开始循环写入数据");
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
					r++; // 下一行
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
			logger.info("成功写入");
		}
	}

	/**
	 * 导出查看计划执行进度
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
		// 标题列
		setCellValue(0, 1, "计划名称");
		setCellValue(1, 1, "开始时间");
		setCellValue(2, 1, "结束时间");
		setCellValue(3, 1, "考核结果");
		setCellValue(4, 1, "计划巡检点次");
		setCellValue(5, 1, "实际巡检点次");
		setCellValue(6, 1, "巡检率");
		// 判断pmTyep
		if ("group".equals(pmType)) {
			setCellValue(7, "巡检维护组");
		} else {
			setCellValue(7, "巡检员");
		}

		int r = 2; // 行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
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

				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}

	/**
	 * 导出查看计划执行结果的显示信息
	 * 
	 * @param list
	 * @param excelstyle
	 * @throws Exception
	 */
	public void exportPlanstateResult(PlanStat planStatBean, String pmType,
			String executorname, ExcelStyle excelstyle) throws Exception {
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		logger.info("得到Bean");
		if (planStatBean != null) {

			logger.info("开始写入数据");

			// 标题列
			setCellValue(0, 2, "报表名称:" + planStatBean.getPlanname());
			HSSFFont font1 = this.workbook.createFont(); // 创建字体格式
			font1.setFontHeight((short) 220); // 设置字体大小
			font1.setFontName("宋体"); // 设置单元格字体
			HSSFCellStyle style2 = this.workbook.createCellStyle(); // 创建单元格风格.
			style2.setFont(font1); // 将字体格式加入到单元格风格当中
			style2.setAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // /水平居中
			style2.setBorderTop(HSSFCellStyle.BORDER_NONE); // 上边框不显示
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			this.curRow.getCell((short) 0).setCellStyle(style2);

			int r = 4; // 行索引

			// 计划名称
			String planname = planStatBean.getPlanname();
			if (planname == null) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, planname);
			}
			r++;

			// 巡检员或巡检维护组名
			if ("group".equals(pmType)) {
				setCellValue(0, r, "巡检维护组");
			} else {
				setCellValue(0, r, "巡检人");
			}
			HSSFFont font2 = this.workbook.createFont(); // 创建字体格式
			font2.setFontHeight((short) 220); // 设置字体大小
			font2.setFontName("宋体"); // 设置单元格字体
			HSSFCellStyle style3 = this.workbook.createCellStyle(); // 创建单元格风格.
			style3.setFont(font2); // 将字体格式加入到单元格风格当中
			this.curRow.getCell((short) 0).setCellStyle(style3);
			setCellValue(1, r, executorname);
			r++; // 下一行

			// 开始时间
			String startdate = String.valueOf(planStatBean.getStartdate());
			if ("null".equals(startdate)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, startdate);
			}
			r++;

			// 结束时间
			String enddate = String.valueOf(planStatBean.getEnddate());
			if ("null".equals(enddate)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, enddate);
			}
			r++;

			// 计划巡检点
			String planpointn = String.valueOf(planStatBean.getPlanpointn());
			if ("null".equals(planpointn)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, planpointn);
			}
			r++;

			// 实际巡检点
			String factpointn = String.valueOf(planStatBean.getFactpointn());
			if ("null".equals(factpointn)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, factpointn);
			}
			r++;

			// 漏检点数
			String leakpointn = String.valueOf(planStatBean.getLeakpointn());
			if ("null".equals(leakpointn)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, leakpointn);
			}
			r++;

			// 计划巡检点次
			String planpoint = String.valueOf(planStatBean.getPlanpoint());
			if ("null".equals(planpoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, planpoint);
			}
			r++;

			// 实际巡检点次
			String factpoint = String.valueOf(planStatBean.getFactpoint());
			if ("null".equals(factpoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, factpoint);
			}
			r++;

			// 巡检率
			String patrolp = String.valueOf(planStatBean.getPatrolp());
			if ("null".equals(patrolp)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, patrolp + "%");
			}
			r++;

			// 关键点数
			String keypoint = String.valueOf(planStatBean.getKeypoint());
			if ("null".equals(keypoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, keypoint);
			}
			r++;

			// 漏检关键点数
			String leakkeypoint = String
					.valueOf(planStatBean.getLeakkeypoint());
			if ("null".equals(leakkeypoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, leakkeypoint);
			}
			r++;

			// 巡检总里程(公里
			String patrolkm = String.valueOf(planStatBean.getPatrolkm());
			if ("null".equals(patrolkm)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, patrolkm);
			}
			r++;

			// 巡检方式
			String patrolmode = planStatBean.getPatrolmode();
			if (patrolmode == null) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, patrolmode.equals("01") ? "手动" : "自动");
			}
			r++;

			// 考核结果
//			String examineresult = planStatBean.getExamineresult();

			logger.info("成功写入");
		}
	}

	/**
	 * 导出查看计划执行结果的显示信息
	 * 
	 * @param list
	 * @param excelstyle
	 * @throws Exception
	 */
	public void exportPlanstateSheet(PlanStat planStatBean, String pmType,
			String patrolname, ExcelStyle excelstyle, int index)
			throws Exception {
		logger.info("得到Bean");
		if (planStatBean != null) {
			
			//  执行计划的名称
			String planname = planStatBean.getPlanname();
			String sheetName = planname.replaceAll("/", "-");

			// 创建一个新的sheet页
			if (index == 0) {
				this.activeSheet(0,sheetName);
			} else {
				this.cloneSheet(sheetName);
			}
		
			this.curStyle = excelstyle.defaultStyle(this.workbook);

			// 标题列
			setCellValue(0, 2, "报表名称:" + planStatBean.getPlanname());
			HSSFFont font1 = this.workbook.createFont(); // 创建字体格式
			font1.setFontHeight((short) 220); // 设置字体大小
			font1.setFontName("宋体"); // 设置单元格字体
			HSSFCellStyle style2 = this.workbook.createCellStyle(); // 创建单元格风格.
			style2.setFont(font1); // 将字体格式加入到单元格风格当中
			style2.setAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // /水平居中
			style2.setBorderTop(HSSFCellStyle.BORDER_NONE); // 上边框不显示
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			this.curRow.getCell((short) 0).setCellStyle(style2);

			logger.info("开始写入数据");

			int r = 4; // 行索引

			// 计划名称
			setCellValue(0, r, "计划名称");			
			setCellValue(1, r, planname);
			r++;

			// 巡检员或巡检维护组名
			if ("group".equals(pmType)) {
				setCellValue(0, r, "巡检维护组");
			} else {
				setCellValue(0, r, "巡检人");
			}
			HSSFFont font2 = this.workbook.createFont(); // 创建字体格式
			font2.setFontHeight((short) 220); // 设置字体大小
			font2.setFontName("宋体"); // 设置单元格字体
			HSSFCellStyle style3 = this.workbook.createCellStyle(); // 创建单元格风格.
			style3.setFont(font2); // 将字体格式加入到单元格风格当中
			this.curRow.getCell((short) 0).setCellStyle(style3);

			setCellValue(1, r, patrolname);
			r++; // 下一行

			// 开始时间
			setCellValue(0, r, "开始时间");
			String startdate = String.valueOf(planStatBean.getStartdate());
			if ("null".equals(startdate)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, startdate);
			}
			r++;

			// 结束时间
			setCellValue(0, r, "结束时间");
			String enddate = String.valueOf(planStatBean.getEnddate());
			if ("null".equals(enddate)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, enddate);
			}
			r++;

			// 计划巡检点
			setCellValue(0, r, "计划巡检点");
			String planpointn = String.valueOf(planStatBean.getPlanpointn());
			if ("null".equals(planpointn)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, planpointn);
			}
			r++;

			// 实际巡检点
			setCellValue(0, r, "实际巡检点");
			String factpointn = String.valueOf(planStatBean.getFactpointn());
			if ("null".equals(factpointn)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, factpointn);
			}
			r++;

			// 漏检点数
			setCellValue(0, r, "漏检点数");
			String leakpointn = String.valueOf(planStatBean.getLeakpointn());
			if ("null".equals(leakpointn)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, leakpointn);
			}
			r++;

			// 计划巡检点次
			setCellValue(0, r, "计划巡检点次");
			String planpoint = String.valueOf(planStatBean.getPlanpoint());
			if ("null".equals(planpoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, planpoint);
			}
			r++;

			// 实际巡检点次
			setCellValue(0, r, "实际巡检点次");
			String factpoint = String.valueOf(planStatBean.getFactpoint());
			if ("null".equals(factpoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, factpoint);
			}
			r++;

			// 巡检率
			setCellValue(0, r, "巡检率");
			String patrolp = String.valueOf(planStatBean.getPatrolp());
			if ("null".equals(patrolp)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, patrolp + "%");
			}
			r++;

			// 关键点数
			setCellValue(0, r, "关键点数");
			String keypoint = String.valueOf(planStatBean.getKeypoint());
			if ("null".equals(keypoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, keypoint);
			}
			r++;

			// 漏检关键点数
			setCellValue(0, r, "漏检关键点数");
			String leakkeypoint = String
					.valueOf(planStatBean.getLeakkeypoint());
			if ("null".equals(leakkeypoint)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, leakkeypoint);
			}
			r++;

			// 巡检总里程(公里)
			setCellValue(0, r, "巡检总里程(公里)");
			String patrolkm = String.valueOf(planStatBean.getPatrolkm());
			if ("null".equals(patrolkm)) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, patrolkm);
			}
			r++;

			// 巡检方式
			setCellValue(0, r, "巡检方式");
			String patrolmode = planStatBean.getPatrolmode();
			if (patrolmode == null) {
				setCellValue(1, r, "");
			} else {
				setCellValue(1, r, patrolmode.equals("01") ? "手动" : "自动");
			}
			r++;

			// 考核结果
			setCellValue(0, r, "考核结果");
//			String examineresult = planStatBean.getExamineresult();
//			setCellValue(1, r, "");

			logger.info("成功写入");
		}
	}
}
