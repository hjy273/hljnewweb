package com.cabletech.linepatrol.dispatchtask.template;

//
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.linepatrol.dispatchtask.beans.DispatchTaskBean;
import com.cabletech.partmanage.action.PartRequisitionAction;

public class SendTaskTemplate extends Template {
	private static Logger logger = Logger.getLogger(PartRequisitionAction.class
			.getName());

	public SendTaskTemplate(String urlPath) {
		super(urlPath);
	}

	/**
	 * 使用 DynaBean
	 * 
	 * @param list
	 *            List
	 */
	public void exportQueryTotalResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 2; // 行索引
		logger.info("得到list");

		if (list != null) {
			Iterator iter = list.iterator();
			String overtimenum;
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);

				if (record.get("serialnumber") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("serialnumber").toString());
				}

				if (record.get("sendtopic") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("sendtopic").toString());
				}

				if (record.get("sendtypelabel") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("sendtypelabel").toString());
				}

				if (record.get("departname") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("departname").toString());
				}

				if (record.get("username") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("username").toString());
				}

				if (record.get("processterm") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("processterm").toString());
				}

				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}

	/**
	 * 个人工单信息
	 * 
	 * @param list
	 *            List
	 */
	public void exportQueryPersonTotalResult(List list) {
	}

	/**
	 * 个人工单统计
	 * 
	 * @param list
	 *            List
	 */
	public void exportPersonNumTotalResult(List list, DispatchTaskBean bean,
			ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 1; // 行索引
		activeRow(r);
		String titleStr = "";
		// 部门
		if (bean.getDeptid() != null && !"".equals(bean.getDeptid())) {
			titleStr = bean.getDeptname();
		} else {
			titleStr = "所有部门";
		}
		titleStr += " ";
		// 开始时间
		if (bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
			titleStr += bean.getBegintime();
		} else {
			titleStr += "开始";
		}
		titleStr += " -- ";
		// 结束时间
		if (bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
			titleStr += bean.getEndtime();
		} else {
			titleStr += "至今";
		}
		titleStr += " 类型为";
		// 工单类型
		if (bean.getSendtype() != null && !"".equals(bean.getSendtype())) {
			titleStr += bean.getSendtypelable();
		} else {
			titleStr += "全部";
		}

		titleStr += " 工单统计";
		setCellValue(0, titleStr);

		logger.info("得到list");
		r = 3;
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);

				if (record.get("username") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("username").toString());
				}

				if (record.get("sum_num") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("sum_num").toString());
				}

				if (record.get("wait_sign_in_num") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("wait_sign_in_num").toString());
				}

				if (record.get("wait_reply_num") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("wait_reply_num").toString());
				}

				if (record.get("wait_check_num") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("wait_check_num").toString());
				}

				if (record.get("refuse_num") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("refuse_num").toString());
				}

				if (record.get("transfer_num") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("transfer_num").toString());
				}

				if (record.get("complete_num") == null) {
					setCellValue(7, "");
				} else {
					setCellValue(7, record.get("complete_num").toString());
				}

				if (record.get("reply_out_time_num") == null) {
					setCellValue(8, "");
				} else {
					setCellValue(8, record.get("reply_out_time_num").toString());
				}

				if (record.get("check_out_time_num") == null) {
					setCellValue(9, "");
				} else {
					setCellValue(9, record.get("check_out_time_num").toString());
				}

				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}

	public void exportDepartTotalResult(List list,
			DispatchTaskBean deptquerybean, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);

		activeRow(1);
		String titleStr = "";
		// 部门
		if (deptquerybean.getDeptid() != null
				&& !"".equals(deptquerybean.getDeptid())) {
			titleStr = deptquerybean.getDeptname();
		} else {
			titleStr = "所有部门";
		}
		titleStr += " ";
		// 工单类型
		if (deptquerybean.getSendtype() != null
				&& !"".equals(deptquerybean.getSendtype())) {
			titleStr += deptquerybean.getSendtypelable();
		} else {
			titleStr += "所有类型";
		}
		titleStr += " ";
		// 开始时间
		if (deptquerybean.getBegintime() != null
				&& !"".equals(deptquerybean.getBegintime())) {
			titleStr += deptquerybean.getBegintime();
		} else {
			titleStr += "开始";
		}
		titleStr += " - ";
		// 结束时间
		if (deptquerybean.getEndtime() != null
				&& !"".equals(deptquerybean.getEndtime())) {
			titleStr += deptquerybean.getEndtime();
		} else {
			titleStr += "至令";
		}
		titleStr += "部门工单统计结果";
		setCellValue(0, titleStr);

		int r = 3; // 行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			float replypercent;
			float validpercent;
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);

				if (record.get("departname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("departname").toString());
				}

				if (record.get("sum_num") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("sum_num").toString());
				}

				if (record.get("wait_sign_in_num") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("wait_sign_in_num").toString());
				}

				if (record.get("wait_reply_num") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("wait_reply_num").toString());
				}

				if (record.get("wait_check_num") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("wait_check_num").toString());
				}

				if (record.get("refuse_num") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("refuse_num").toString());
				}

				if (record.get("transfer_num") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("transfer_num").toString());
				}

				if (record.get("complete_num") == null) {
					setCellValue(7, "");
				} else {
					setCellValue(7, record.get("complete_num").toString());
				}

				if (record.get("reply_out_time_num") == null) {
					setCellValue(8, "");
				} else {
					setCellValue(8, record.get("reply_out_time_num").toString());
				}

				if (record.get("check_out_time_num") == null) {
					setCellValue(9, "");
				} else {
					setCellValue(9, record.get("check_out_time_num").toString());
				}
				DecimalFormat df=new DecimalFormat("#0.0");
				if (record.get("reply_in_time_ratio") == null) {
					setCellValue(10, "");
				} else {
					setCellValue(10, df.format(Double.parseDouble(record.get("reply_in_time_ratio")
                            .toString())));
				}

				if (record.get("check_in_time_ratio") == null) {
					setCellValue(11, "");
				} else {
					setCellValue(11, df.format(Double.parseDouble(record.get("check_in_time_ratio")
                            .toString())));
				}

				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}

	/**
	 * 使用 DynaBean
	 * 
	 * @param list
	 *            List
	 */
	public void exportDispatchTaskResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 2; // 行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);

				if (record.get("serialnumber") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("serialnumber").toString());
				}

				if (record.get("sendtopic") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("sendtopic").toString());
				}

				if (record.get("sendtypelabel") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("sendtypelabel").toString());
				}

				if (record.get("senddeptname") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("senddeptname").toString());
				}

				if (record.get("processterm") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("processterm").toString());
				}

				if (record.get("sendusername") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("sendusername").toString());
				}

				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}

	public void exportSignInTaskResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);

		int r = 2; // 行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);

				if (record.get("serialnumber") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("serialnumber").toString());
				}

				if (record.get("sendtopic") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("sendtopic").toString());
				}

				if (record.get("sendtype") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("sendtype").toString());
				}

				if (record.get("senddeptname") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("senddeptname").toString());
				}

				if (record.get("processterm") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("processterm").toString());
				}

				if (record.get("usernameacce") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("usernameacce").toString());
				}

				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}

	public void exportReplyTaskResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);

		int r = 2; // 行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("serialnumber") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("serialnumber").toString());
				}

				if (record.get("sendtopic") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("sendtopic").toString());
				}

				if (record.get("sendtype") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("sendtype").toString());
				}

				if (record.get("senddeptname") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("senddeptname").toString());
				}

				if (record.get("processterm") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("processterm").toString());
				}

				if (record.get("usernameacce") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("usernameacce").toString());
				}

				// 下一行
				r++;
			}
			logger.info("成功写入");
		}
	}

	public void exportCheckTaskResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);

		int r = 2; // 行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("serialnumber") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("serialnumber").toString());
				}

				if (record.get("sendtopic") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("sendtopic").toString());
				}

				if (record.get("sendtype") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("sendtype").toString());
				}

				if (record.get("senddeptname") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("senddeptname").toString());
				}

				if (record.get("processterm") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("processterm").toString());
				}

				if (record.get("sendusername") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("sendusername").toString());
				}

				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}

}
