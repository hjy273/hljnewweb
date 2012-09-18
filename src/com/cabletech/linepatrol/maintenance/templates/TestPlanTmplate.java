package com.cabletech.linepatrol.maintenance.templates;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.linepatrol.maintenance.module.TestCableData;
import com.cabletech.linepatrol.maintenance.module.TestChipData;
import com.cabletech.linepatrol.maintenance.module.TestConnectorWaste;
import com.cabletech.linepatrol.maintenance.module.TestExceptionEvent;
import com.cabletech.linepatrol.maintenance.module.TestPlanLine;
import com.cabletech.linepatrol.maintenance.module.TestProblem;
import com.cabletech.linepatrol.resource.model.RepeaterSection;

/**
 *����ά��
 * @author fjj
 *
 */
public class TestPlanTmplate extends Template {
	private static Logger logger = Logger.getLogger("Template");

	public TestPlanTmplate(String urlPath) {
		super(urlPath);
	}

	/**
	 * ������ƻ�ͳ��
	 * @param list
	 * @param excelstyle
	 */
	public void doExportTestPlans(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // ������
		try{if(list != null && list.size()>0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				setCellValue(0, r-1+"");
				if (record.get("test_plan_name") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("test_plan_name").toString());
				}
				if (record.get("plantime") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("plantime").toString());
				}
				if (record.get("plantype") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("plantype").toString());
				}
				int num = 0;
				int solvenum=0;
				String rate = "100%";
				if (record.get("num") == null) {
					setCellValue(4, "");
				} else {
					num = Integer.parseInt(record.get("num").toString());
					setCellValue(4, num+"");
				}
				if (record.get("solvenum") == null) {
					setCellValue(5, "");
				} else {
					solvenum = Integer.parseInt(record.get("solvenum").toString());
					setCellValue(5,solvenum+"");
				}
				if(num!=0){
					rate=(solvenum/num)*100+"%";
				}
				setCellValue(6, rate);

				r++;
			}
		}
		}catch(Exception e){
			logger.error("������ƻ�ͳ��ʧ��:"+e.getMessage());
			e.getStackTrace();
		}
	}


	/**
	 * ��������¼������
	 * @param excelstyle
	 */
	public void doExportCableData(TestPlanLine line,RepeaterSection res,TestCableData data,
			List<TestProblem> problems ,Map<Object,TestChipData> chips,
			ExcelStyle excelstyle) {
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 1; // ������
		try{
			activeRow(r);
			setCellValue(0,"�м̶Σ�");
			setCellValue(1,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )1 ) );
			setCellValue(2,res.getSegmentname());
			setCellValue(3,"");
			setCellValue(4,"");
			setCellValue(5,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )5 ) );
			r++;
			activeRow(r);
			setCellValue(0,"���Զˣ�");
			setCellValue(1,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )1 ) );
			setCellValue(2,data.getFactTestPort()+"��");
			setCellValue(3,"");
			setCellValue(4,"");
			setCellValue(5,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )5 ) );
			r++;
			activeRow(r);
			setCellValue(0,"����ʱ�䣺");
			setCellValue(1,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )1 ) );
			//String facttime = DateUtil.formatDate(data.getFactTestTime(),"yyyy/MM/dd");
			Date date = data.getFactTestTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			setCellValue(2,sdf.format(date));
			setCellValue(3,"");
			setCellValue(4,"");
			setCellValue(5,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )5 ) );
			r++;
			activeRow(r);
			setCellValue(0,"���Եص㣺");
			setCellValue(1,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )1 ) );
			setCellValue(2,data.getTestAddress());
			setCellValue(3,"");
			setCellValue(4,"");
			setCellValue(5,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )5 ) );
			r++;
			activeRow(r);
			setCellValue(0,"������Ա��");
			setCellValue(1,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )1 ) );
			setCellValue(2,data.getTestMan());
			setCellValue(3,"");
			setCellValue(4,"");
			setCellValue(5,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )5 ) );
			r++;
			activeRow(r);
			setCellValue(0,"��������("+res.getCoreNumber()+"����о)");
			setCellValue(1,"");
			setCellValue(2,"");
			setCellValue(3,"");
			setCellValue(4,"");
			setCellValue(5,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			r++;
			activeRow(r);
			setCellValue(0,"����");
			setCellValue(1,"˥������dB/km");
			setCellValue(2,"�Ƿ�ϸ�");
			setCellValue(3,"�Ƿ񱣴�");
			setCellValue(4,"�Ƿ�����");
			setCellValue(5," ˵��");
			Set set = chips.keySet();
			Iterator ite = set.iterator();
			while(ite.hasNext()){
				Object key = ite.next();
				TestChipData chipdata = chips.get(key);
				r++;
				activeRow(r);
				String att = chipdata.getAttenuationConstant();
				String isEligible = chipdata.getIsEligible();
				String isused = chipdata.getIsUsed();
				String elig = isEligible.equals("1")?"�ϸ�":"���ϸ�";
				String used = isused.equals("1")?"����":"������";
				String save = chipdata.getIsSave().equals("1")?"����":"δ����";
				if(att==null || att.equals("null")){
					att="";
				}
				setCellValue(0,chipdata.getChipSeq());
				setCellValue(1,att);
				setCellValue(2,elig);
				setCellValue(3,save);
				setCellValue(4,used);
				setCellValue(5,chipdata.getTestRemark());
			}
			r++;
			activeRow(r);
			setCellValue(0,"�ֳ����������¼������ ");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			r++;
			activeRow(r);
			setCellValue(0,"��� ");
			setCellValue(1,"��������");
			setCellValue(2," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )2 ) );
			setCellValue(3,"�������˵��");
			setCellValue(4," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )3, r, ( short )4 ) );
			setCellValue(5,"״̬");
			for(int i = 0;problems!=null && i<problems.size();i++){
				TestProblem problem = problems.get(i);
				r++;
				activeRow(r);
				setCellValue(0,(i+1)+"");
				setCellValue(1,problem.getProblemDescription());
				setCellValue(2," ");
				this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )2 ) );
				setCellValue(3,problem.getProcessComment());
				setCellValue(4," ");
				this.curSheet.addMergedRegion( new Region(r , ( short )3, r, ( short )4 ) );
				String state = problem.getProblemState().equals("1")?"�ѽ��":"δ���";
				setCellValue(5,state);
			}
			
		}catch(Exception e){
			logger.error("��������ʧ��:"+e.getMessage());
			e.getStackTrace();
		}
	}

	
	/**
	 * ��������¼������
	 * @param excelstyle
	 */
	public void exportAnaylseData(TestChipData chipdata,String turnkName,
			ExcelStyle excelstyle) {
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 1; // ������
		try{
			activeRow(r);
			setCellValue(0,"�м̶Σ�");
			setCellValue(1,turnkName);
			setCellValue(2," ");
			setCellValue(3,"����");
			setCellValue(4,chipdata.getChipSeq());
			setCellValue(5," ");
			r++;
			activeRow(r);
			setCellValue(0,"���Զˣ�");
			setCellValue(1,chipdata.getCoreData().getAbEnd()+"��");
			setCellValue(2,"���Ի�վ��");
			setCellValue(3,chipdata.getCoreData().getBaseStation());
			setCellValue(4," ");
			setCellValue(5," ");
			
			r++;
			activeRow(r);
			setCellValue(0,"��¼���ڣ�");
			setCellValueNull();
			this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )5 ) );
			Date date = chipdata.getCoreData().getTestDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			setCellValue(1,sdf.format(date));
			r++;
			this.curStyle=rowLineStyle(this.workbook);
			activeRow(r);
			setCellValue(0,"��о���ȷ���");
			setCellValueNull();
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			r++;
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			activeRow(r);
			setCellValue(0,"����������");
			setCellValue(1,"��������");
			setCellValue(2,"о��km");
			setCellValue(3,"�Ƿ�������");
			setCellValue(4,"�������");
			setCellValue(5,"��ע");
			
			r++;
			activeRow(r);
			setCellValue(0,chipdata.getCorelength().getRefractiveIndex()+"");
			setCellValue(1,chipdata.getCorelength().getPulseWidth()+"");
			setCellValue(2,chipdata.getCorelength().getCoreLength()+"");
			String pro = chipdata.getCorelength().getIsProblem().equals("1")?"��":"û��";
			setCellValue(3,pro);
			setCellValue(4,chipdata.getCorelength().getProblemAnalyseLen());
			setCellValue(5,chipdata.getCorelength().getLengremark());
			
			
			r++;
			this.curStyle=rowLineStyle(this.workbook);
			activeRow(r);
			setCellValue(0,"˥����������");
			setCellValueNull();
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			r++;
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			activeRow(r);
			setCellValue(0,"˥������dB/km");
			setCellValue(1,"�Ƿ�ϸ�");
			setCellValue(2,"�������");
			setCellValue(3," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )3 ) );
			setCellValue(4,"��ע");
			this.curSheet.addMergedRegion( new Region(r , ( short )4, r, ( short )5 ) );
			setCellValue(5," ");
			
			r++;
			activeRow(r);
			setCellValue(0,chipdata.getDecay().getDecayConstant()+"");
			String standardDec = chipdata.getDecay().getIsStandardDec().equals("1")?"�ϸ�":"���ϸ�";
			setCellValue(1,standardDec);
			setCellValue(2,chipdata.getDecay().getProblemAnalyseDec());
			setCellValue(3," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )3 ) );
			setCellValue(4,chipdata.getDecay().getDecayRemark());
			this.curSheet.addMergedRegion( new Region(r , ( short )4, r, ( short )5 ) );
			setCellValue(5," ");
			
			r++;
			this.curStyle=rowLineStyle(this.workbook);
			activeRow(r);
			setCellValue(0,"�ɶ���ķ���");
			setCellValueNull();
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			
			r++;
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			activeRow(r);
			setCellValue(0,"�ɶ����dB");
			setCellValue(1,"�Ƿ�ϸ�");
			setCellValue(2,"�������");
			setCellValue(3," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )3 ) );
			setCellValue(4,"��ע");
			setCellValue(5," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )4, r, ( short )5 ) );
			
			r++;
			activeRow(r);
			setCellValue(0,chipdata.getEndwaste().getEndWaste()+"");
			String standard = chipdata.getEndwaste().getIsStandardEnd().equals("1")?"�ϸ�":"���ϸ�";
			setCellValue(1,standard);
			setCellValue(2,chipdata.getEndwaste().getProblemAnalyseEnd());
			setCellValue(3," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )3 ) );
			setCellValue(4,chipdata.getEndwaste().getEndRemark());
			setCellValue(5," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )4, r, ( short )5 ) );
			
			r++;
			activeRow(r);
			this.curStyle=rowLineStyle(this.workbook);
			setCellValue(0,"��ͷ��ķ�������¼���������������ֵ����0.5dB�Ľ�ͷ��");
			setCellValueNull();
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			
			r++;
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			activeRow(r);
			setCellValue(0,"���");
			setCellValue(1,"��ͷλ��");
			setCellValue(2,"���ֵdB");
			setCellValue(3,"�������");
			setCellValue(4,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )3, r, ( short )4 ) );
			setCellValue(5,"��ע");
			
			List<TestConnectorWaste> wastes = chipdata.getConwaste();
			for(int i = 0;wastes!=null && i<wastes.size();i++){
				TestConnectorWaste waste = wastes.get(i);
				r++;
				activeRow(r);
				setCellValue(0,(i+1)+"");
				setCellValue(1,waste.getConnectorStation());
				setCellValue(2,waste.getWaste()+"");
				setCellValue(3,waste.getProblemAnalyse());
				setCellValue(4," ");
				this.curSheet.addMergedRegion( new Region(r , ( short )3, r, ( short )4 ) );
				setCellValue(5,waste.getRemark());;
			}
			
			
			r++;
			this.curStyle=rowLineStyle(this.workbook);
			activeRow(r);
			setCellValue(0,"�쳣�¼����������������Ƿ����¼��ȣ�");
			setCellValueNull();
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			
			r++;
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			activeRow(r);
			setCellValue(0,"���");
			setCellValue(1,"�¼�λ��");
			setCellValue(2,"���ֵdB");
			setCellValue(3,"�������");
			setCellValue(4,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )3, r, ( short )4 ) );
			setCellValue(5,"��ע");
			
			List<TestExceptionEvent> exceptions = chipdata.getExceptionEvent();
			for(int i = 0;exceptions!=null && i<exceptions.size();i++){
				TestExceptionEvent event = exceptions.get(i);
				r++;
				activeRow(r);
				setCellValue(0,(i+1)+"");
				setCellValue(1,event.getEventStation());
				setCellValue(2,event.getWasteExe()+"");
				setCellValue(3,event.getProblemAnalyseExe());
				setCellValue(4," ");
				this.curSheet.addMergedRegion( new Region(r , ( short )3, r, ( short )4 ) );
				setCellValue(5,event.getRemarkExe());
			}
			
			r++;
			this.curStyle=rowLineStyle(this.workbook);
			activeRow(r);
			setCellValue(0,"��������");
			setCellValueNull();
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			
			r++;
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			activeRow(r);
			setCellValue(0,"��������");
			setCellValue(1," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )1 ) );
			setCellValue(2,"�������");
			setCellValue(3," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )3 ) );
			setCellValue(4,"˵��");
			setCellValue(5," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )4, r, ( short )5 ) );
	       
			r++;
			activeRow(r);
			setCellValue(0,chipdata.getOtherAnalyse().getAnalyseOther());
			setCellValue(1," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )1 ) );
			setCellValue(2,chipdata.getOtherAnalyse().getAnalyseResultOther());
			setCellValue(3," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )3 ) );
			setCellValue(4,chipdata.getOtherAnalyse().getRemarkOther());
			setCellValue(5," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )4, r, ( short )5 ) );
			
		}catch(Exception e){
			logger.error("�������ݷ���:"+e.getMessage());
			e.getStackTrace();
		}
	}
	
	
	
	public void setCellValueNull(){
		setCellValue(1," ");
		setCellValue(2," ");
		setCellValue(3," ");
		setCellValue(4," ");
		setCellValue(5," ");
	}
	
	
	/**
	 * ��������ʽ 
	 * @param workbook
	 * @return
	 */
	public HSSFCellStyle rowLineStyle( HSSFWorkbook workbook ){
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints( ( short )10 );
		font.setFontName( "����" );
		font.setItalic( false );
		font.setStrikeout( false );	
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
		style.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);    //���ı�����ɫ
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);   //���ı�����ɫ
		style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
		style.setAlignment( HSSFCellStyle.ALIGN_CENTER );
		style.setBorderRight((short)1);
		style.setWrapText(true);
		return style;
	}

}
