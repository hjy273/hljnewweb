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
	 * ʹ�� DynaBean
	 * 
	 * @param list
	 *            List
	 */
	public void exportQueryTotalResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 2; // ������
		logger.info("�õ�list");

		if (list != null) {
			Iterator iter = list.iterator();
			String overtimenum;
			logger.info("��ʼѭ��д������");
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

				r++; // ��һ��
			}
			logger.info("�ɹ�д��");
		}
	}

	/**
	 * ���˹�����Ϣ
	 * 
	 * @param list
	 *            List
	 */
	public void exportQueryPersonTotalResult(List list) {
	}

	/**
	 * ���˹���ͳ��
	 * 
	 * @param list
	 *            List
	 */
	public void exportPersonNumTotalResult(List list, DispatchTaskBean bean,
			ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 1; // ������
		activeRow(r);
		String titleStr = "";
		// ����
		if (bean.getDeptid() != null && !"".equals(bean.getDeptid())) {
			titleStr = bean.getDeptname();
		} else {
			titleStr = "���в���";
		}
		titleStr += " ";
		// ��ʼʱ��
		if (bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
			titleStr += bean.getBegintime();
		} else {
			titleStr += "��ʼ";
		}
		titleStr += " -- ";
		// ����ʱ��
		if (bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
			titleStr += bean.getEndtime();
		} else {
			titleStr += "����";
		}
		titleStr += " ����Ϊ";
		// ��������
		if (bean.getSendtype() != null && !"".equals(bean.getSendtype())) {
			titleStr += bean.getSendtypelable();
		} else {
			titleStr += "ȫ��";
		}

		titleStr += " ����ͳ��";
		setCellValue(0, titleStr);

		logger.info("�õ�list");
		r = 3;
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("��ʼѭ��д������");
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

				r++; // ��һ��
			}
			logger.info("�ɹ�д��");
		}
	}

	public void exportDepartTotalResult(List list,
			DispatchTaskBean deptquerybean, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);

		activeRow(1);
		String titleStr = "";
		// ����
		if (deptquerybean.getDeptid() != null
				&& !"".equals(deptquerybean.getDeptid())) {
			titleStr = deptquerybean.getDeptname();
		} else {
			titleStr = "���в���";
		}
		titleStr += " ";
		// ��������
		if (deptquerybean.getSendtype() != null
				&& !"".equals(deptquerybean.getSendtype())) {
			titleStr += deptquerybean.getSendtypelable();
		} else {
			titleStr += "��������";
		}
		titleStr += " ";
		// ��ʼʱ��
		if (deptquerybean.getBegintime() != null
				&& !"".equals(deptquerybean.getBegintime())) {
			titleStr += deptquerybean.getBegintime();
		} else {
			titleStr += "��ʼ";
		}
		titleStr += " - ";
		// ����ʱ��
		if (deptquerybean.getEndtime() != null
				&& !"".equals(deptquerybean.getEndtime())) {
			titleStr += deptquerybean.getEndtime();
		} else {
			titleStr += "����";
		}
		titleStr += "���Ź���ͳ�ƽ��";
		setCellValue(0, titleStr);

		int r = 3; // ������
		logger.info("�õ�list");
		if (list != null) {
			Iterator iter = list.iterator();
			float replypercent;
			float validpercent;
			logger.info("��ʼѭ��д������");
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

				r++; // ��һ��
			}
			logger.info("�ɹ�д��");
		}
	}

	/**
	 * ʹ�� DynaBean
	 * 
	 * @param list
	 *            List
	 */
	public void exportDispatchTaskResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 2; // ������
		logger.info("�õ�list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("��ʼѭ��д������");
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

				r++; // ��һ��
			}
			logger.info("�ɹ�д��");
		}
	}

	public void exportSignInTaskResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);

		int r = 2; // ������
		logger.info("�õ�list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("��ʼѭ��д������");
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

				r++; // ��һ��
			}
			logger.info("�ɹ�д��");
		}
	}

	public void exportReplyTaskResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);

		int r = 2; // ������
		logger.info("�õ�list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("��ʼѭ��д������");
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

				// ��һ��
				r++;
			}
			logger.info("�ɹ�д��");
		}
	}

	public void exportCheckTaskResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);

		int r = 2; // ������
		logger.info("�õ�list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("��ʼѭ��д������");
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

				r++; // ��һ��
			}
			logger.info("�ɹ�д��");
		}
	}

}
