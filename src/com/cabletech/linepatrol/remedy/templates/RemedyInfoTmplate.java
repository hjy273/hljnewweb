package com.cabletech.linepatrol.remedy.templates;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.linepatrol.remedy.beans.RemedyDistriStatBean;

/**
 * ���ɲ������ݵĵ���excel��
 * @author fjj
 *
 */
public class RemedyInfoTmplate extends Template {
	private static Logger logger = Logger.getLogger("Template");

	public RemedyInfoTmplate(String urlPath) {
		super(urlPath);
	}


	/**
	 * ִ�е�������
	 * 
	 * @param list
	 *            List
	 * @param excelstyle
	 *            ExcelStyle
	 */
	public void doExportItem(List list, ExcelStyle excelstyle) {
		logger.info("����������Ŀ=========teplate===");
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // ������
		if(list != null && list.size()>0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("itemname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("itemname").toString());
				}
				if (record.get("regionname") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("regionname").toString());
				}
				r++;
			}
		}
	}


	public void doExportType(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // ������
		if(list != null && list.size()>0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("typename") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("typename").toString());
				}
				if (record.get("itemname") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("itemname").toString());
				}
				if (record.get("price") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("price").toString());
				}
				if (record.get("unit") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("unit").toString());
				}
				r++;
			}
		}
	}

	/**
	 * �����ֲ�ͳ���ܱ�
	 * @param sumfee//�ܶ�
	 * @param time//��ѯѡ���ʱ��
	 * @param size//��¼�����ϲ���Ԫ��
	 * @param map 
	 * @param excelstyle
	 */

	public void doExportReprot(String sumfee,String time,int size,Map map, ExcelStyle excelstyle) {
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 0; // ������
		if(map !=null){
			activeRow(r);
			setCellValue(0, "ά����λ");
			( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( this.twFourLineCenter(this.workbook) );
			setCellValue(1, "ά������");
			( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( this.twFourLineCenter(this.workbook) );
			setCellValue(2, time+"�·ݹ������ֲ�ͳ���ܱ�");
			( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( this.twFourLineCenter(this.workbook) );
			setCellValue(3, " �ƶ���ά������Ԫ��");
			( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( this.twFourLineCenter(this.workbook) );
			this.curRow.setHeight( ( short )650 );
			r++;
			Set set = map.keySet();
			Iterator iter = set.iterator();
			int customBeginLine=1;
			int beginLine = 1;
			int t = 0;
			while (iter.hasNext()) {
				beginLine = customBeginLine+t;
				activeRow(r);
				Object key =  iter.next();
				setCellValue(0,key.toString());
				( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( this.niFourLineCenter(this.workbook) );
				if(r>0){
					setCellValue(3,sumfee);
					( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( this.twFourLineCenter(this.workbook) );
				}
				List listvalue = (List) map.get(key);
				System.out.println("listvalue:"+listvalue.size());
				if(listvalue!=null && listvalue.size()>0){
					for(int i = 0;i<listvalue.size();i++){
						activeRow(r);
						super.curStyle = excelstyle.defaultStyle(super.workbook);
						RemedyDistriStatBean bean = (RemedyDistriStatBean) listvalue.get(i);
						setCellValue(1,bean.getTown());
						setCellValue(2,bean.getTotalfee());
						setCellValue(3,sumfee);
						( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( this.twFourLineCenter(this.workbook) );
						r++;
						t++;
					}
				}
				System.out.println("beginLine========== "+beginLine+" customBeginLine======== "+customBeginLine+" t== "+t);
				this.curSheet.addMergedRegion( new Region( beginLine, ( short )0, customBeginLine + t - 1, ( short )0 ) );
			}
			
			this.curSheet.addMergedRegion( new Region( 1, ( short )3, size, ( short )3 ) );
		}
	}
	
	
	/**
	 * �������ձ���
	 * @param list
	 * @param excelstyle
	 */

	public void doExportReport(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 1; // ������
		if(list != null && list.size()>0) {
			activeRow(r);
			String conname = (String) list.get(0);
			String townName = (String) list.get(1);
			String remedytime = (String) list.get(2);
			float sumfee = Float.parseFloat(list.get(3).toString());
			setCellValue(0, "         ʩ����λ:    "+conname+"               ʩ���·�:    "+remedytime+"                  ά������:    "+townName);
			setCellValue(1, "");
			setCellValue(2, "");
			setCellValue(3, "");
			setCellValue(4, "");
			this.curSheet.addMergedRegion(new Region( 1, ( short )0, 1, ( short )4 ) );
			this.curRow.setHeight((short)500);
			
			r++;
			activeRow(r);
			this.curStyle = this.niFourLineCenter(workbook);
			setCellValue(0, "���");
			setCellValue(1, "�������� ");
			setCellValue(2, "������Ŀ����");
			setCellValue(3, "�������");
			setCellValue(4, "��ע˵��");
			this.curRow.setHeight((short)600);
			r++;
			List reports = (List) list.get(4);
			Iterator iter = reports.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				setCellValue(0, (r-2)+"");
				if (record.get("remedycode") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("remedycode").toString());
				}
				if (record.get("projectname") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("projectname").toString());
				}
				if (record.get("totalfee") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("totalfee").toString());
				}
				if (record.get("remark") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("remark").toString());
				}
				this.curRow.setHeight((short)450);
				r++;
			}
			
			activeRow(r);
			setCellValue(0, "  �ϼ�:   "+sumfee);
			setCellValue(1, "");
			setCellValue(2, "");
			setCellValue(3, "");
			setCellValue(4, "");
			this.curSheet.addMergedRegion(new Region( r, ( short )0, r, ( short )4 ) );
			this.curRow.setHeight((short)500);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	

	//10�����壬���߿򣬴�ֱ����
    public HSSFCellStyle twFourLineCenter( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont(); //��������
        font.setFontName( "����" );
        font.setFontHeightInPoints( ( short )12 );
        font.setBoldweight( ( short )1000000 );
        style.setFont( font );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        style.setBorderRight( HSSFCellStyle.BORDER_THIN );
        style.setRightBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setTopBorderColor( HSSFColor.BLACK.index );
        style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
        style.setAlignment( HSSFCellStyle.ALIGN_CENTER );
        style.setWrapText(true);
        return style;

    }

	//9�����壬���߿򣬴�ֱ����
    public HSSFCellStyle niFourLineCenter( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont(); //��������
        font.setFontName( "����" );
        font.setFontHeightInPoints( ( short )10 );
        font.setBoldweight( ( short )100000 );
        style.setFont( font );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        style.setBorderRight( HSSFCellStyle.BORDER_THIN );
        style.setRightBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setTopBorderColor( HSSFColor.BLACK.index );
        style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
        style.setAlignment( HSSFCellStyle.ALIGN_CENTER );
        style.setWrapText(true);
        return style;

    }
    
}
