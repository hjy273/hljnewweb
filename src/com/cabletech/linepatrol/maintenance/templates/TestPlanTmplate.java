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
 *技术维护
 * @author fjj
 *
 */
public class TestPlanTmplate extends Template {
	private static Logger logger = Logger.getLogger("Template");

	public TestPlanTmplate(String urlPath) {
		super(urlPath);
	}

	/**
	 * 导出年计划统计
	 * @param list
	 * @param excelstyle
	 */
	public void doExportTestPlans(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // 行索引
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
			logger.error("导出年计划统计失败:"+e.getMessage());
			e.getStackTrace();
		}
	}


	/**
	 * 导出光缆录入数据
	 * @param excelstyle
	 */
	public void doExportCableData(TestPlanLine line,RepeaterSection res,TestCableData data,
			List<TestProblem> problems ,Map<Object,TestChipData> chips,
			ExcelStyle excelstyle) {
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 1; // 行索引
		try{
			activeRow(r);
			setCellValue(0,"中继段：");
			setCellValue(1,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )1 ) );
			setCellValue(2,res.getSegmentname());
			setCellValue(3,"");
			setCellValue(4,"");
			setCellValue(5,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )5 ) );
			r++;
			activeRow(r);
			setCellValue(0,"测试端：");
			setCellValue(1,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )1 ) );
			setCellValue(2,data.getFactTestPort()+"端");
			setCellValue(3,"");
			setCellValue(4,"");
			setCellValue(5,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )5 ) );
			r++;
			activeRow(r);
			setCellValue(0,"测试时间：");
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
			setCellValue(0,"测试地点：");
			setCellValue(1,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )1 ) );
			setCellValue(2,data.getTestAddress());
			setCellValue(3,"");
			setCellValue(4,"");
			setCellValue(5,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )5 ) );
			r++;
			activeRow(r);
			setCellValue(0,"测试人员：");
			setCellValue(1,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )1 ) );
			setCellValue(2,data.getTestMan());
			setCellValue(3,"");
			setCellValue(4,"");
			setCellValue(5,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )5 ) );
			r++;
			activeRow(r);
			setCellValue(0,"测试数据("+res.getCoreNumber()+"条纤芯)");
			setCellValue(1,"");
			setCellValue(2,"");
			setCellValue(3,"");
			setCellValue(4,"");
			setCellValue(5,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			r++;
			activeRow(r);
			setCellValue(0,"纤序");
			setCellValue(1,"衰减常数dB/km");
			setCellValue(2,"是否合格");
			setCellValue(3,"是否保存");
			setCellValue(4,"是否在用");
			setCellValue(5," 说明");
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
				String elig = isEligible.equals("1")?"合格":"不合格";
				String used = isused.equals("1")?"在用":"不在用";
				String save = chipdata.getIsSave().equals("1")?"保存":"未保存";
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
			setCellValue(0,"现场测试问题记录及跟踪 ");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			r++;
			activeRow(r);
			setCellValue(0,"序号 ");
			setCellValue(1,"问题描述");
			setCellValue(2," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )2 ) );
			setCellValue(3,"处理跟踪说明");
			setCellValue(4," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )3, r, ( short )4 ) );
			setCellValue(5,"状态");
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
				String state = problem.getProblemState().equals("1")?"已解决":"未解决";
				setCellValue(5,state);
			}
			
		}catch(Exception e){
			logger.error("导出光缆失败:"+e.getMessage());
			e.getStackTrace();
		}
	}

	
	/**
	 * 导出光缆录入数据
	 * @param excelstyle
	 */
	public void exportAnaylseData(TestChipData chipdata,String turnkName,
			ExcelStyle excelstyle) {
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 1; // 行索引
		try{
			activeRow(r);
			setCellValue(0,"中继段：");
			setCellValue(1,turnkName);
			setCellValue(2," ");
			setCellValue(3,"纤序：");
			setCellValue(4,chipdata.getChipSeq());
			setCellValue(5," ");
			r++;
			activeRow(r);
			setCellValue(0,"测试端：");
			setCellValue(1,chipdata.getCoreData().getAbEnd()+"端");
			setCellValue(2,"测试基站：");
			setCellValue(3,chipdata.getCoreData().getBaseStation());
			setCellValue(4," ");
			setCellValue(5," ");
			
			r++;
			activeRow(r);
			setCellValue(0,"记录日期：");
			setCellValueNull();
			this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )5 ) );
			Date date = chipdata.getCoreData().getTestDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			setCellValue(1,sdf.format(date));
			r++;
			this.curStyle=rowLineStyle(this.workbook);
			activeRow(r);
			setCellValue(0,"纤芯长度分析");
			setCellValueNull();
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			r++;
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			activeRow(r);
			setCellValue(0,"测试折射率");
			setCellValue(1,"测试脉宽");
			setCellValue(2,"芯长km");
			setCellValue(3,"是否有问题");
			setCellValue(4,"问题分析");
			setCellValue(5,"备注");
			
			r++;
			activeRow(r);
			setCellValue(0,chipdata.getCorelength().getRefractiveIndex()+"");
			setCellValue(1,chipdata.getCorelength().getPulseWidth()+"");
			setCellValue(2,chipdata.getCorelength().getCoreLength()+"");
			String pro = chipdata.getCorelength().getIsProblem().equals("1")?"有":"没有";
			setCellValue(3,pro);
			setCellValue(4,chipdata.getCorelength().getProblemAnalyseLen());
			setCellValue(5,chipdata.getCorelength().getLengremark());
			
			
			r++;
			this.curStyle=rowLineStyle(this.workbook);
			activeRow(r);
			setCellValue(0,"衰减常数分析");
			setCellValueNull();
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			r++;
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			activeRow(r);
			setCellValue(0,"衰减常数dB/km");
			setCellValue(1,"是否合格");
			setCellValue(2,"问题分析");
			setCellValue(3," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )3 ) );
			setCellValue(4,"备注");
			this.curSheet.addMergedRegion( new Region(r , ( short )4, r, ( short )5 ) );
			setCellValue(5," ");
			
			r++;
			activeRow(r);
			setCellValue(0,chipdata.getDecay().getDecayConstant()+"");
			String standardDec = chipdata.getDecay().getIsStandardDec().equals("1")?"合格":"不合格";
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
			setCellValue(0,"成端损耗分析");
			setCellValueNull();
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			
			r++;
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			activeRow(r);
			setCellValue(0,"成端损耗dB");
			setCellValue(1,"是否合格");
			setCellValue(2,"问题分析");
			setCellValue(3," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )3 ) );
			setCellValue(4,"备注");
			setCellValue(5," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )4, r, ( short )5 ) );
			
			r++;
			activeRow(r);
			setCellValue(0,chipdata.getEndwaste().getEndWaste()+"");
			String standard = chipdata.getEndwaste().getIsStandardEnd().equals("1")?"合格":"不合格";
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
			setCellValue(0,"接头损耗分析（记录分析存在问题或损值超过0.5dB的接头）");
			setCellValueNull();
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			
			r++;
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			activeRow(r);
			setCellValue(0,"序号");
			setCellValue(1,"接头位置");
			setCellValue(2,"损耗值dB");
			setCellValue(3,"问题分析");
			setCellValue(4,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )3, r, ( short )4 ) );
			setCellValue(5,"备注");
			
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
			setCellValue(0,"异常事件分析（包括反射或非反射事件等）");
			setCellValueNull();
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			
			r++;
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			activeRow(r);
			setCellValue(0,"序号");
			setCellValue(1,"事件位置");
			setCellValue(2,"损耗值dB");
			setCellValue(3,"问题分析");
			setCellValue(4,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )3, r, ( short )4 ) );
			setCellValue(5,"备注");
			
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
			setCellValue(0,"其他分析");
			setCellValueNull();
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )5 ) );
			
			r++;
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			activeRow(r);
			setCellValue(0,"分析简述");
			setCellValue(1," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )1 ) );
			setCellValue(2,"分析结果");
			setCellValue(3," ");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )3 ) );
			setCellValue(4,"说明");
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
			logger.error("导出数据分析:"+e.getMessage());
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
	 * 定义行样式 
	 * @param workbook
	 * @return
	 */
	public HSSFCellStyle rowLineStyle( HSSFWorkbook workbook ){
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints( ( short )10 );
		font.setFontName( "宋体" );
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
		style.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);    //填充的背景颜色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);   //填充的背景颜色
		style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
		style.setAlignment( HSSFCellStyle.ALIGN_CENTER );
		style.setBorderRight((short)1);
		style.setWrapText(true);
		return style;
	}

}
