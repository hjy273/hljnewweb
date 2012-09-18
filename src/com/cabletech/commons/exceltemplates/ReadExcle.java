package com.cabletech.commons.exceltemplates;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.cabletech.groupcustomer.dao.GroupCustomerDao;

public class ReadExcle extends Template {
	// 日志
	private static Logger logger = Logger.getLogger(ReadExcle.class.getName());

	public ReadExcle(String urlPath) {
		super(urlPath);
	}

	public HSSFSheet getCurSheet() {
		return this.curSheet;
	}

	public void setCurSheet(HSSFSheet curSheet) {
		this.curSheet = curSheet;
	}

	/***************************************************************************
	 * 功能:获得指定sheet,行,列的值 参数:sheet序号,行号,列号 返回:值,已经转换为Stirng类型
	 **************************************************************************/
	protected String getCellValue(int sheet, int rowIdx, int colIdx) {
		curSheet = workbook.getSheetAt(sheet);
		curRow = curSheet.getRow(rowIdx);

		double numValue;
		if (curRow == null) {
			return "";
		}
		// curSheet.getRow(curRow.getRowNum()).getCell((short)colIdx);
		HSSFCell cell = curRow.getCell((short) colIdx);
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_NUMERIC:
			numValue = cell.getNumericCellValue();
			return String.valueOf(numValue);
		default:
			// System.out.println( "Unkown cell type" );
			return "";

		}

	}

	/***************************************************************************
	 * 功能:获得指定Excel表的第二,第三列的值,存放于一个List中 参数: 返回:List
	 **************************************************************************/
	public List getExcleContent() {
		activeSheet(0);
		HashMap hItem = new HashMap(2);
		List lRePart = new ArrayList();
		try {
			for (int i = 2; curSheet.getRow(i) != null; i++) {
				if (!getCellValue(0, i, 1).equals("")
						&& getCellValue(0, i, 1) != null
						&& getCellValue(0, i, 2) != null
						&& !getCellValue(0, i, 2).equals("")) {
					hItem.put("name", getCellValue(0, i, 1));
					hItem.put("number", getCellValue(0, i, 2));
					lRePart.add(hItem);
					hItem = new HashMap(2);
				}
			}
			this.curSheet = null;
			this.curRow = null;
			this.workbook = null;

			return lRePart;
		} catch (Exception e) {
			// System.out.println( "获得指定Excel表的第二,第三列的值出错:" + e.getMessage() );
			logger.error("获得指定Excel表的第二,第三列的值出错:", e);
			return null;
		}
	}

	/**
	 * 功能:获得指定Excel表的值,存放于一个List中 参数: 返回:List
	 */
	public List getExcelTemppoint() {
		activeSheet(0);
		HashMap hItem;
		List lTemp = new ArrayList();
		String tmpCellValue;
		//logger.info("开始读取excel数据！！");
		try {
			for (int i = 2; curSheet.getRow(i) != null; i++) {
				
				//logger.info("读取excel数据条数：" + (i-1));
				hItem = new HashMap();
				// 获取临时点名称
				tmpCellValue = getCellValue(0, i, 0);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("pointname", tmpCellValue);
				} else {
					hItem.put("pointname", "");
				}

				// 获取经度
				tmpCellValue = getCellValue(0, i, 1);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("x", tmpCellValue);
				} else {
					hItem.put("x", "0.0000");
				}

				// 获取纬度
				tmpCellValue = getCellValue(0, i, 2);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("y", tmpCellValue);
				} else {
					hItem.put("y", "0.0000");
				}

				// 获取SIM卡号
				tmpCellValue = getCellValue(0, i, 3);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("sim", tmpCellValue);
				} else {
					hItem.put("sim", "13000000000");
				}
				
				lTemp.add(hItem);
			}
		} catch (Exception ex) {
			logger.error("导入临时点数据出错 ： ",ex);
		}

		return lTemp;
	}

	/**
	 * 功能:获得指定Excel表的值,存放于一个List中 参数: 返回:List
	 */
	public List getExcleGroupCustomer() {
		activeSheet(0);
		HashMap hItem;
		List lGroup = new ArrayList();
		String tmpCellValue;
		try {
			for (int i = 3; curSheet.getRow(i) != null; i++) {
				hItem = new HashMap();
				// 集团客户编号
				tmpCellValue = getCellValue(0, i, 1);
				try {
					tmpCellValue = String.valueOf(new BigDecimal(tmpCellValue)
							.toBigInteger());
				} catch (Exception e) {
				}
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("groupid", tmpCellValue);
				} else {
					break;
					// hItem.put( "groupid", "" );
				}

				// 所属区域
				tmpCellValue = getCellValue(0, i, 2);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("city", tmpCellValue);
				} else {
					hItem.put("city", "");
				}

				// 所属镇区
				tmpCellValue = getCellValue(0, i, 3);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("town", tmpCellValue);
				} else {
					hItem.put("town", "");
				}

				// 集团类型
				tmpCellValue = getCellValue(0, i, 4);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("grouptype", tmpCellValue);
				} else {
					hItem.put("grouptype", "");
				}

				// 集团客户名称
				tmpCellValue = getCellValue(0, i, 5);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("groupname", tmpCellValue);
				} else {
					hItem.put("groupname", "");
				}

				// 集团客户地址
				tmpCellValue = getCellValue(0, i, 6);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("groupaddr", tmpCellValue);
				} else {
					hItem.put("groupaddr", "");
				}

				// 经度
				tmpCellValue = getCellValue(0, i, 7);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("x", tmpCellValue);
				} else {
					hItem.put("x", "");
				}

				// 纬度
				tmpCellValue = getCellValue(0, i, 8);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("y", tmpCellValue);
				} else {
					hItem.put("y", "");
				}

				// 区域ID
				tmpCellValue = getCellValue(0, i, 9);
				try {
					tmpCellValue = String.valueOf((int) Double
							.parseDouble(tmpCellValue));
				} catch (Exception e) {
				}
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("regionid", tmpCellValue);
				} else {
					hItem.put("regionid", "");
				}

				// 客户经理
				tmpCellValue = getCellValue(0, i, 10);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("customermanager", tmpCellValue);
				} else {
					hItem.put("customermanager", "");
				}

				// 联系电话
				tmpCellValue = getCellValue(0, i, 11);
				try {
					tmpCellValue = String.valueOf(new BigDecimal(tmpCellValue)
							.toBigInteger());
				} catch (Exception e) {
				}
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("tel", tmpCellValue);
				} else {
					hItem.put("tel", "");
				}

				// 集团用户客户电话
				tmpCellValue = getCellValue(0, i, 12);
				try {
					tmpCellValue = String.valueOf(new BigDecimal(tmpCellValue)
							.toBigInteger());
				} catch (Exception e) {
				}
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("grouptel", tmpCellValue);
				} else {
					hItem.put("grouptel", "");
				}

				// 业务类型
				tmpCellValue = getCellValue(0, i, 13);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("operationtype", tmpCellValue);
				} else {
					hItem.put("operationtype", "");
				}

				lGroup.add(hItem);
			}
			this.curSheet = null;
			this.curRow = null;
			this.workbook = null;

			return lGroup;
		} catch (Exception e) {
			// System.out.println( "获得指定Excel表的第二到第十四列的值出错:" + e.getMessage() );
			logger.error("集团客户导入出错：",e);
			return null;
		}
	}
	//获取导入excel路由信息
	public List getExcleRouteInfo() {
		activeSheet(0);
		HashMap hItem;
		List lRoute = new ArrayList();
		String tmpCellValue;
		try {
			for (int i = 2; curSheet.getRow(i) != null; i++) {
				hItem = new HashMap();
				// 路由名称
				tmpCellValue = getCellValue(0, i, 0);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("routename", tmpCellValue);
				} else {
					break;
					// hItem.put( "routename", "" );
				}

				// 所属区域
				tmpCellValue = getCellValue(0, i, 1);
				try {
					tmpCellValue = String.valueOf(new BigDecimal(tmpCellValue)
							.toBigInteger());
				} catch (Exception e) {
				}
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("regionid", tmpCellValue);
				} else {
					hItem.put("regionid", "");
				}
				lRoute.add(hItem);
			}
			this.curSheet = null;
			this.curRow = null;
			this.workbook = null;
			logger.info("成功获取excel信息");
			return lRoute;
		} catch (Exception e) {
			logger.error("路由信息导入出错：",e);
			return null;
		}
	}
	
	public List getExcleCableList(){
		activeSheet(0);
		HashMap hItem;
		List lTemp = new ArrayList();
		String tmpCellValue;
		//logger.info("开始读取excel数据！！");
		try {
			for (int i = 2; curSheet.getRow(i) != null; i++) {
				
				//logger.info("读取excel数据条数：" + (i-1));
				hItem = new HashMap();
				// 获取临时点名称
				tmpCellValue = getCellValue(0, i, 0);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("cableno", tmpCellValue);
				} else {
					hItem.put("cableno", "");
				}

				// 获取经度
				tmpCellValue = getCellValue(0, i, 1);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("contractorid", tmpCellValue);
				} else {
					hItem.put("contractorid", "");
				}
				tmpCellValue = getCellValue(0, i, 2);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("regionid", tmpCellValue);
				} else {
					hItem.put("regionid", "");
				}

				// 获取纬度
				tmpCellValue = getCellValue(0, i, 3);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("area", tmpCellValue);
				} else {
					hItem.put("area", "");
				}

				// 获取SIM卡号
				tmpCellValue = getCellValue(0, i, 4);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("county", tmpCellValue);
				} else {
					hItem.put("county", "");
				}
				
				tmpCellValue = getCellValue(0, i, 5);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("systemname", tmpCellValue);
				} else {
					hItem.put("systemname", "");
				}
				tmpCellValue = getCellValue(0, i, 6);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("cablename", tmpCellValue);
				} else {
					hItem.put("cablename", "");
				}
				tmpCellValue = getCellValue(0, i, 7);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("cablelinename", tmpCellValue);
				} else {
					hItem.put("cablelinename", "");
				}
				tmpCellValue = getCellValue(0, i, 8);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("apoint", tmpCellValue);
				} else {
					hItem.put("apoint", "");
				}
				tmpCellValue = getCellValue(0, i, 9);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("zpoint", tmpCellValue);
				} else {
					hItem.put("zpoint", "");
				}
				tmpCellValue = getCellValue(0, i, 10);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("laytype", tmpCellValue);
				} else {
					hItem.put("laytype", "");
				}
				tmpCellValue = getCellValue(0, i, 11);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("company", tmpCellValue);
				} else {
					hItem.put("company", "");
				}
				tmpCellValue = getCellValue(0, i, 12);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("construct", tmpCellValue);
				} else {
					hItem.put("construct", "");
				}
				tmpCellValue = getCellValue(0, i, 13);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("property", tmpCellValue);
				} else {
					hItem.put("property", "");
				}
				tmpCellValue = getCellValue(0, i, 14);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("cabletype", tmpCellValue);
				} else {
					hItem.put("cabletype", "");
				}
				tmpCellValue = getCellValue(0, i, 15);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("createtime", tmpCellValue);
				} else {
					hItem.put("createtime", "2008");
				}
				tmpCellValue = getCellValue(0, i, 16);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("fibertype", tmpCellValue);
				} else {
					hItem.put("fibertype", "");
				}
				tmpCellValue = getCellValue(0, i, 17);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("fibernumber", tmpCellValue);
				} else {
					hItem.put("fibernumber", "");
				}
				tmpCellValue = getCellValue(0, i, 18);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("cablelength", tmpCellValue);
				} else {
					hItem.put("cablelength", "0.0");
				}
				tmpCellValue = getCellValue(0, i, 19);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("unusecable", tmpCellValue);
				} else {
					hItem.put("unusecable", "0.0");
				}
				tmpCellValue = getCellValue(0, i, 20);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("remark", tmpCellValue);
				} else {
					hItem.put("remark", "");
				}
				tmpCellValue = getCellValue(0, i, 21);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("isaccept", tmpCellValue);
				} else {
					hItem.put("isaccept", "");
				}
				tmpCellValue = getCellValue(0, i, 22);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("blueprintno", tmpCellValue);
				} else {
					hItem.put("blueprintno", "");
				}
				tmpCellValue = getCellValue(0, i, 23);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("fiberlength", tmpCellValue);
				} else {
					hItem.put("fiberlength", "0.0");
				}
				
				lTemp.add(hItem);
			}
		} catch (Exception ex) {
			logger.error("导入光缆段数据出错 ： ",ex);
		}

		return lTemp;
	}
	
	public List getExclePipeSegmentList(){
		activeSheet(0);
		HashMap hItem;
		List lTemp = new ArrayList();
		String tmpCellValue;
		//logger.info("开始读取excel数据！！");
		try {
			for (int i = 2; curSheet.getRow(i) != null; i++) {
				
				//logger.info("读取excel数据条数：" + (i-1));
				hItem = new HashMap();
				// 管道段编号
				tmpCellValue = getCellValue(0, i, 0);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("pipeno", tmpCellValue);
				} else {
					hItem.put("pipeno", "");
				}

				// 代维
				tmpCellValue = getCellValue(0, i, 1);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("contractorid", tmpCellValue);
				} else {
					hItem.put("contractorid", "");
				}
				tmpCellValue = getCellValue(0, i, 2);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("regionid", tmpCellValue);
				} else {
					hItem.put("regionid", "");
				}
				// 维护区域
				tmpCellValue = getCellValue(0, i, 3);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("county", tmpCellValue);
				} else {
					hItem.put("county", "");
				}
				// 所属乡镇
				tmpCellValue = getCellValue(0, i, 4);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("town", tmpCellValue);
				} else {
					hItem.put("town", "");
				}
				
				// 分区
				tmpCellValue = getCellValue(0, i, 5);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("area", tmpCellValue);
				} else {
					hItem.put("area", "");
				}

				//管道名称
				tmpCellValue = getCellValue(0, i, 6);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("pipename", tmpCellValue);
				} else {
					hItem.put("pipename", "");
				}
				
				//是否验收
				tmpCellValue = getCellValue(0, i, 7);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("isaccept", tmpCellValue);
				} else {
					hItem.put("isaccept", "");
				}

				//a站点
				tmpCellValue = getCellValue(0, i, 8);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("apoint", tmpCellValue);
				} else {
					hItem.put("apoint", "");
				}
				
				tmpCellValue = getCellValue(0, i, 9);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("zpoint", tmpCellValue);
				} else {
					hItem.put("zpoint", "");
				}
				
				//距离
				tmpCellValue = getCellValue(0, i, 10);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("length", tmpCellValue);
				} else {
					hItem.put("length", "0.0");
				}
				
				//材料
				tmpCellValue = getCellValue(0, i, 11);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("material", tmpCellValue);
				} else {
					hItem.put("material", "");
				}
				
				// 产权
				tmpCellValue = getCellValue(0, i, 12);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("right", tmpCellValue);
				} else {
					hItem.put("right", "");
				}
				
				//管孔数
				tmpCellValue = getCellValue(0, i, 13);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("pipehole", tmpCellValue);
				} else {
					hItem.put("pipehole", "");
				}
				
				//管孔规格
				tmpCellValue = getCellValue(0, i, 14);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("pipetype", tmpCellValue);
				} else {
					hItem.put("pipetype", "");
				}
				
				//子管数
				tmpCellValue = getCellValue(0, i, 15);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("subpipehole", tmpCellValue);
				} else {
					hItem.put("subpipehole", "");
				}
				
				//未用子管数
				tmpCellValue = getCellValue(0, i, 16);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("unuserpipe", tmpCellValue);
				} else {
					hItem.put("unuserpipe", "");
				}
				
				//备注1
				tmpCellValue = getCellValue(0, i, 17);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("remark1", tmpCellValue);
				} else {
					hItem.put("remark1", "");
				}
				
				//备注2
				tmpCellValue = getCellValue(0, i, 18);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("remark2", tmpCellValue);
				} else {
					hItem.put("remark2", "");
				}
				tmpCellValue = getCellValue(0, i, 19);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("bluepointno", tmpCellValue);
				} else {
					hItem.put("bluepointno", "");
				}
				lTemp.add(hItem);
			}
		} catch (Exception ex) {
			logger.error("导入管道段数据出错 ： ",ex);
		}

		return lTemp;
	}
	public static void main(String[] args) {
		// BigInteger a = new BigInteger("1.382700047E10");
		// System.out.println(a.toString());
		String str = "1.382700047E10";
		BigDecimal d = new BigDecimal(str);

		// double num = 1.382700047E10; //或 long num;
		System.out.println(d.toBigInteger().toString());

	}
}
