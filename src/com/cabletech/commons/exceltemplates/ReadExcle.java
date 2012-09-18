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
	// ��־
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
	 * ����:���ָ��sheet,��,�е�ֵ ����:sheet���,�к�,�к� ����:ֵ,�Ѿ�ת��ΪStirng����
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
	 * ����:���ָ��Excel��ĵڶ�,�����е�ֵ,�����һ��List�� ����: ����:List
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
			// System.out.println( "���ָ��Excel��ĵڶ�,�����е�ֵ����:" + e.getMessage() );
			logger.error("���ָ��Excel��ĵڶ�,�����е�ֵ����:", e);
			return null;
		}
	}

	/**
	 * ����:���ָ��Excel���ֵ,�����һ��List�� ����: ����:List
	 */
	public List getExcelTemppoint() {
		activeSheet(0);
		HashMap hItem;
		List lTemp = new ArrayList();
		String tmpCellValue;
		//logger.info("��ʼ��ȡexcel���ݣ���");
		try {
			for (int i = 2; curSheet.getRow(i) != null; i++) {
				
				//logger.info("��ȡexcel����������" + (i-1));
				hItem = new HashMap();
				// ��ȡ��ʱ������
				tmpCellValue = getCellValue(0, i, 0);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("pointname", tmpCellValue);
				} else {
					hItem.put("pointname", "");
				}

				// ��ȡ����
				tmpCellValue = getCellValue(0, i, 1);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("x", tmpCellValue);
				} else {
					hItem.put("x", "0.0000");
				}

				// ��ȡγ��
				tmpCellValue = getCellValue(0, i, 2);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("y", tmpCellValue);
				} else {
					hItem.put("y", "0.0000");
				}

				// ��ȡSIM����
				tmpCellValue = getCellValue(0, i, 3);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("sim", tmpCellValue);
				} else {
					hItem.put("sim", "13000000000");
				}
				
				lTemp.add(hItem);
			}
		} catch (Exception ex) {
			logger.error("������ʱ�����ݳ��� �� ",ex);
		}

		return lTemp;
	}

	/**
	 * ����:���ָ��Excel���ֵ,�����һ��List�� ����: ����:List
	 */
	public List getExcleGroupCustomer() {
		activeSheet(0);
		HashMap hItem;
		List lGroup = new ArrayList();
		String tmpCellValue;
		try {
			for (int i = 3; curSheet.getRow(i) != null; i++) {
				hItem = new HashMap();
				// ���ſͻ����
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

				// ��������
				tmpCellValue = getCellValue(0, i, 2);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("city", tmpCellValue);
				} else {
					hItem.put("city", "");
				}

				// ��������
				tmpCellValue = getCellValue(0, i, 3);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("town", tmpCellValue);
				} else {
					hItem.put("town", "");
				}

				// ��������
				tmpCellValue = getCellValue(0, i, 4);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("grouptype", tmpCellValue);
				} else {
					hItem.put("grouptype", "");
				}

				// ���ſͻ�����
				tmpCellValue = getCellValue(0, i, 5);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("groupname", tmpCellValue);
				} else {
					hItem.put("groupname", "");
				}

				// ���ſͻ���ַ
				tmpCellValue = getCellValue(0, i, 6);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("groupaddr", tmpCellValue);
				} else {
					hItem.put("groupaddr", "");
				}

				// ����
				tmpCellValue = getCellValue(0, i, 7);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("x", tmpCellValue);
				} else {
					hItem.put("x", "");
				}

				// γ��
				tmpCellValue = getCellValue(0, i, 8);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("y", tmpCellValue);
				} else {
					hItem.put("y", "");
				}

				// ����ID
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

				// �ͻ�����
				tmpCellValue = getCellValue(0, i, 10);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("customermanager", tmpCellValue);
				} else {
					hItem.put("customermanager", "");
				}

				// ��ϵ�绰
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

				// �����û��ͻ��绰
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

				// ҵ������
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
			// System.out.println( "���ָ��Excel��ĵڶ�����ʮ���е�ֵ����:" + e.getMessage() );
			logger.error("���ſͻ��������",e);
			return null;
		}
	}
	//��ȡ����excel·����Ϣ
	public List getExcleRouteInfo() {
		activeSheet(0);
		HashMap hItem;
		List lRoute = new ArrayList();
		String tmpCellValue;
		try {
			for (int i = 2; curSheet.getRow(i) != null; i++) {
				hItem = new HashMap();
				// ·������
				tmpCellValue = getCellValue(0, i, 0);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("routename", tmpCellValue);
				} else {
					break;
					// hItem.put( "routename", "" );
				}

				// ��������
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
			logger.info("�ɹ���ȡexcel��Ϣ");
			return lRoute;
		} catch (Exception e) {
			logger.error("·����Ϣ�������",e);
			return null;
		}
	}
	
	public List getExcleCableList(){
		activeSheet(0);
		HashMap hItem;
		List lTemp = new ArrayList();
		String tmpCellValue;
		//logger.info("��ʼ��ȡexcel���ݣ���");
		try {
			for (int i = 2; curSheet.getRow(i) != null; i++) {
				
				//logger.info("��ȡexcel����������" + (i-1));
				hItem = new HashMap();
				// ��ȡ��ʱ������
				tmpCellValue = getCellValue(0, i, 0);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("cableno", tmpCellValue);
				} else {
					hItem.put("cableno", "");
				}

				// ��ȡ����
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

				// ��ȡγ��
				tmpCellValue = getCellValue(0, i, 3);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("area", tmpCellValue);
				} else {
					hItem.put("area", "");
				}

				// ��ȡSIM����
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
			logger.error("������¶����ݳ��� �� ",ex);
		}

		return lTemp;
	}
	
	public List getExclePipeSegmentList(){
		activeSheet(0);
		HashMap hItem;
		List lTemp = new ArrayList();
		String tmpCellValue;
		//logger.info("��ʼ��ȡexcel���ݣ���");
		try {
			for (int i = 2; curSheet.getRow(i) != null; i++) {
				
				//logger.info("��ȡexcel����������" + (i-1));
				hItem = new HashMap();
				// �ܵ��α��
				tmpCellValue = getCellValue(0, i, 0);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("pipeno", tmpCellValue);
				} else {
					hItem.put("pipeno", "");
				}

				// ��ά
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
				// ά������
				tmpCellValue = getCellValue(0, i, 3);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("county", tmpCellValue);
				} else {
					hItem.put("county", "");
				}
				// ��������
				tmpCellValue = getCellValue(0, i, 4);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("town", tmpCellValue);
				} else {
					hItem.put("town", "");
				}
				
				// ����
				tmpCellValue = getCellValue(0, i, 5);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("area", tmpCellValue);
				} else {
					hItem.put("area", "");
				}

				//�ܵ�����
				tmpCellValue = getCellValue(0, i, 6);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("pipename", tmpCellValue);
				} else {
					hItem.put("pipename", "");
				}
				
				//�Ƿ�����
				tmpCellValue = getCellValue(0, i, 7);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("isaccept", tmpCellValue);
				} else {
					hItem.put("isaccept", "");
				}

				//aվ��
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
				
				//����
				tmpCellValue = getCellValue(0, i, 10);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("length", tmpCellValue);
				} else {
					hItem.put("length", "0.0");
				}
				
				//����
				tmpCellValue = getCellValue(0, i, 11);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("material", tmpCellValue);
				} else {
					hItem.put("material", "");
				}
				
				// ��Ȩ
				tmpCellValue = getCellValue(0, i, 12);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("right", tmpCellValue);
				} else {
					hItem.put("right", "");
				}
				
				//�ܿ���
				tmpCellValue = getCellValue(0, i, 13);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("pipehole", tmpCellValue);
				} else {
					hItem.put("pipehole", "");
				}
				
				//�ܿ׹��
				tmpCellValue = getCellValue(0, i, 14);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("pipetype", tmpCellValue);
				} else {
					hItem.put("pipetype", "");
				}
				
				//�ӹ���
				tmpCellValue = getCellValue(0, i, 15);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("subpipehole", tmpCellValue);
				} else {
					hItem.put("subpipehole", "");
				}
				
				//δ���ӹ���
				tmpCellValue = getCellValue(0, i, 16);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("unuserpipe", tmpCellValue);
				} else {
					hItem.put("unuserpipe", "");
				}
				
				//��ע1
				tmpCellValue = getCellValue(0, i, 17);
				if (tmpCellValue != null && !"".equals(tmpCellValue)) {
					hItem.put("remark1", tmpCellValue);
				} else {
					hItem.put("remark1", "");
				}
				
				//��ע2
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
			logger.error("����ܵ������ݳ��� �� ",ex);
		}

		return lTemp;
	}
	public static void main(String[] args) {
		// BigInteger a = new BigInteger("1.382700047E10");
		// System.out.println(a.toString());
		String str = "1.382700047E10";
		BigDecimal d = new BigDecimal(str);

		// double num = 1.382700047E10; //�� long num;
		System.out.println(d.toBigInteger().toString());

	}
}
