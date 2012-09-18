package com.cabletech.linepatrol.expenses.template;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.expenses.model.ExpenseAffirm;

/**
 *费用模块
 * @author fjj
 *
 */
public class ExpenseTmplate extends Template {
	private static Logger logger = Logger.getLogger("Template");

	public ExpenseTmplate(String urlPath) {
		super(urlPath);
	}


	/**
	 * 导出光缆费用
	 * @param map
	 * @param excelstyle
	 */
	public void exportExpense(Map<String,Map> map,String yearmonth,ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		int r = 0; // 行索引
		try{
			this.curStyle=rowLineStyle(this.workbook);
			activeRow(r);
			setCellValue(0,yearmonth+"光缆费用");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )21 ) );
			this.curRow.setHeight( ( short )480 );
			r++;
			//activeRow(r);
			/*setCellValue(0,"代维");
			setCellValue(1,"分级取费系数");
			//	setCellValue(2,"费用月数");
			setCellValue(2,"一干长度");
			setCellValue(3,"一干中继段数");
			setCellValue(4,"一干单价");
			setCellValue(5,"一干维护费用");
			setCellValue(6,"骨干长度");
			setCellValue(7,"骨干中继段数");
			setCellValue(8,"骨干单价");
			setCellValue(9,"骨干维护费用");
			setCellValue(10,"汇聚长度");
			setCellValue(11,"汇聚中继段数");
			setCellValue(12,"汇聚单价");
			setCellValue(13,"汇聚维护费用");
			setCellValue(14,"接入长度");
			setCellValue(15,"接入中继段数");
			setCellValue(16,"接入单价");
			setCellValue(17,"接入维护费用");
			setCellValue(18,"大于144芯接入");
			setCellValue(19,"大于144芯接入中继段数");
			setCellValue(20,"大于144芯接入单价");
			setCellValue(21,"大于144芯接入维护费用");
			this.curRow.setHeight( ( short )480 );*/
			//r++;

			//	super.curStyle = excelstyle.defaultStyle(super.workbook);
			Set set = map.keySet();
			Iterator ite = set.iterator();
			while(ite.hasNext()){
				int m = 0;
				String key = (String) ite.next();//代维
				Map<String,List> expenseMap = map.get(key);
				int rownum = r+expenseMap.size()-1;
				Set mapSet = expenseMap.keySet();
				Iterator itetrator = mapSet.iterator();
				while(itetrator.hasNext()){
					activeRow(r);
					if(m == 0){
						this.curStyle=rowLineStyle(this.workbook);
						setCellValue(0,"代维");
						setCellValue(1,"分级取费系数");
						//	setCellValue(2,"费用月数");
						setCellValue(2,"一干长度");
						setCellValue(3,"一干中继段数");
						setCellValue(4,"一干单价(元)");
						setCellValue(5,"一干维护费用(元)");
						setCellValue(6,"骨干长度");
						setCellValue(7,"骨干中继段数");
						setCellValue(8,"骨干单价(元)");
						setCellValue(9,"骨干维护费用(元)");
						setCellValue(10,"汇聚长度");
						setCellValue(11,"汇聚中继段数");
						setCellValue(12,"汇聚单价(元)");
						setCellValue(13,"汇聚维护费用(元)");
						setCellValue(14,"接入长度");
						setCellValue(15,"接入中继段数");
						setCellValue(16,"接入单价(元)");
						setCellValue(17,"接入维护费用(元)");
						setCellValue(18,"大于144芯接入");
						setCellValue(19,"大于144芯接入中继段数");
						setCellValue(20,"大于144芯接入单价(元)");
						setCellValue(21,"大于144芯接入维护费用(元)");
						this.curRow.setHeight( ( short )480 );
						r++;
						activeRow(r);
						this.curStyle = excelstyle.tenFourLineCenter(super.workbook);
						setCellValue(0,key);
						this.curSheet.addMergedRegion( new Region(r , ( short )0, rownum, ( short )0 ) );	
						m++;
					}
					Object factor = itetrator.next();
					//if(factor.equals("合计") || factor.equals("月费用")){
					//	this.curStyle=rowLineStyle(this.workbook);
					//}else{
					super.curStyle = excelstyle.defaultStyle(super.workbook);
					//}
					List list = expenseMap.get(factor);
					setCellValue(1, factor+"");
					//	setCellValue(2, 1+"");
					if(list!=null && list.size()>0){
						Iterator iter = list.iterator();
						while (iter.hasNext()) {
							record = (DynaBean) iter.next();
							String cableLevel = "";
							Object cabletype = record.get("cable_level");
							if(cabletype!=null){
								cableLevel = cabletype.toString();
							}
							if(cableLevel.equals("1") && factor.equals("合计")){
								setAmountValue(record,cableLevel,2);
							}else if(cableLevel.equals("1") && factor.equals("月费用")){
								setMonthPriceValue(record,2);
							}else if(cableLevel.equals("1")){
								setValue(record,2);
							}
							if(cableLevel.equals("2") && factor.equals("合计")){
								setAmountValue(record,cableLevel,6);
							}else if(cableLevel.equals("2") && factor.equals("月费用")){
								Object cableLen = record.get("cable_length");
								setMonthPriceValueIsSpace(6);
								setCellValue(6, cableLen+"");
							}else if(cableLevel.equals("2")){
								setValue(record,6);
							}
							if(cableLevel.equals("3") && factor.equals("合计")){
								setAmountValue(record,cableLevel,10);
							}else if(cableLevel.equals("3") && factor.equals("月费用")){
								setMonthPriceValueIsSpace(10);
							}else if(cableLevel.equals("3")){
								setValue(record,10);
							}

							if(cableLevel.equals("4") && factor.equals("合计")){
								setAmountValue(record,cableLevel,14);
							}else if(cableLevel.equals("4") && factor.equals("月费用")){
								setMonthPriceValueIsSpace(14);
							}else if(cableLevel.equals("4")){
								setValue(record,14);
							}

							if(cableLevel.equals("4A") && factor.equals("合计")){
								setAmountValue(record,cableLevel,18);
							}else if(cableLevel.equals("4A") && factor.equals("月费用")){
								setMonthPriceValueIsSpace(18);
							}else if(cableLevel.equals("4A")){//4A表示接入层大于144芯的
								setValue(record,18);
							}
						}
					}else{//如果没有数据则设置为空
						for(int i = 2;i<=21;i++){
							setCellValue(i, " ");
						}
					}
					r++;
				}
			}
		}catch(Exception e){
			logger.error("导出费用失败:"+e.getMessage());
			e.printStackTrace();
		}
	}




	/**
	 * 导出管道的费用
	 * @param map
	 * @param excelstyle
	 */
	public void exportPipeExpense(Map<String,Object> map,String yearmonth,ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		int r = 0; // 行索引
		try{
			this.curStyle=rowLineStyle(this.workbook);
			activeRow(r);
			setCellValue(0,yearmonth+"管道费用");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )4 ) );
			this.curRow.setHeight( ( short )480 );
			r++;
			activeRow(r);
			setCellValue(0,"代维公司");
			setCellValue(1,"维护费用(元)");
			setCellValue(2,"取费单价(元)");
			setCellValue(3,"管道数量");
			setCellValue(4,"管道长度 (KM)");
		//	setCellValue(5,"矫正费用(元) ");
		//	setCellValue(6,"扣减费用(元) ");
			//setCellValue(7,"扣减原因 ");
			this.curRow.setHeight( ( short )480 );
			r++;

			super.curStyle = excelstyle.defaultStyle(super.workbook);
			Set set = map.keySet();
			Iterator ite = set.iterator();
			while(ite.hasNext()){
				activeRow(r);
				String key = (String) ite.next();//代维
				record = (DynaBean) map.get(key);
				setCellValue(0, key);
				if(record.get("month_price")==null){
					setCellValue(1, "");
				}else{
					setCellValue(1,record.get("month_price").toString());
				}
				if(record.get("unit_price")==null){
					setCellValue(2, "");
				}else{
					setCellValue(2,record.get("unit_price").toString());
				}
				if(record.get("cable_num")==null){
					setCellValue(3, "");
				}else{
					setCellValue(3,record.get("cable_num").toString());
				}
				if(record.get("cable_length")==null){
					setCellValue(4, "");
				}else{
					setCellValue(4,record.get("cable_length").toString());
				}
				/*if(record.get("rectify_money")==null){
					setCellValue(5, "");
				}else{
					setCellValue(5,record.get("rectify_money").toString());
				}
				if(record.get("deduction_money")==null){
					setCellValue(6, "");
				}else{
					setCellValue(6,record.get("deduction_money").toString());
				}
				if(record.get("remark")==null){
					setCellValue(7, "");
				}else{
					setCellValue(7,record.get("remark").toString());
				}*/
				this.curRow.setHeight( ( short )480 );
				r++;
			}
		}catch(Exception e){
			logger.error("导出管道费用失败:"+e.getMessage());
			e.printStackTrace();
		}
	}

	
	/**
	 * 导出管道的费用确认
	 * @param map
	 * @param excelstyle
	 */
	public void exportPipeSettlementExpense(Map<String,Object> map,
			ExpenseAffirm affrim,Contractor c ,
			String beginmonth,String endmonth,ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		int r = 0; // 行索引
		try{
			this.curStyle=rowLineStyle(this.workbook);
			activeRow(r);
			setCellValue(0,beginmonth+"-"+endmonth+c.getContractorName()+"费用结算确认函");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )7 ) );
			this.curRow.setHeight( ( short )480 );
			r++;
			activeRow(r);
			setCellValue(0,"时间");
			setCellValue(1,"维护费用(元)");
			setCellValue(2,"取费单价(元)");
			setCellValue(3,"管道数量");
			setCellValue(4,"管道长度 (KM)");
			setCellValue(5,"矫正费用(元) ");
			setCellValue(6,"扣减费用(元) ");
			setCellValue(7,"扣减原因 ");
			this.curRow.setHeight( ( short )480 );
			r++;

			super.curStyle = excelstyle.defaultStyle(super.workbook);
			Set set = map.keySet();
			Iterator ite = set.iterator();
			while(ite.hasNext()){
				activeRow(r);
				String key = (String) ite.next();//时间
				record = (DynaBean) map.get(key);
				setCellValue(0, key);
				if(record.get("month_price")==null){
					setCellValue(1, "");
				}else{
					setCellValue(1,record.get("month_price").toString());
				}
				if(record.get("unit_price")==null){
					setCellValue(2, "");
				}else{
					setCellValue(2,record.get("unit_price").toString());
				}
				if(record.get("cable_num")==null){
					setCellValue(3, "");
				}else{
					setCellValue(3,record.get("cable_num").toString());
				}
				if(record.get("cable_length")==null){
					setCellValue(4, "");
				}else{
					setCellValue(4,record.get("cable_length").toString());
				}
				if(record.get("rectify_money")==null){
					setCellValue(5, "");
				}else{
					setCellValue(5,record.get("rectify_money").toString());
				}
				if(record.get("deduction_money")==null){
					setCellValue(6, "");
				}else{
					setCellValue(6,record.get("deduction_money").toString());
				}
				if(record.get("remark")==null){
					setCellValue(7, "");
				}else{
					setCellValue(7,record.get("remark").toString());
				}
				this.curRow.setHeight( ( short )480 );
				r++;
			}
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			Double deductionPrice = affrim.getDeductionPrice();
			Double balancePrice = affrim.getBalancePrice();
			double monthMoney = balancePrice+deductionPrice;
			String reamrk = affrim.getRemark();
			activeRow(r);
			setCellValue(0, "维护费用(元)：");
			setCellValue(1, monthMoney+"");
			for(int m = 2;m<8;m++){
				setCellValue(m, "");
			}
			this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );	
			this.curRow.setHeight( ( short )400 );
			r++;
			activeRow(r);
			setCellValue(0, "扣减(元)：");
			setCellValue(1, deductionPrice+"");
			for(int m = 2;m<8;m++){
				setCellValue(m, "");
			}
			this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );
			this.curRow.setHeight( ( short )400 );
			r++;
			activeRow(r);
			setCellValue(0, "结算费用(元)：");
			setCellValue(1, balancePrice+"");
			for(int m = 2;m<8;m++){
				setCellValue(m, "");
			}
			this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );	
			this.curRow.setHeight( ( short )400 );
			r++;
			activeRow(r);
			setCellValue(0, "扣减原因：");
			setCellValue(1, reamrk);
			for(int m = 2;m<8;m++){
				setCellValue(m, "");
			}
			this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );	
			this.curRow.setHeight( ( short )750 );
			
			activeSheet("计算费用的管道信息");
			int k=0;
			activeRow(k);
			String expensmonthsql = "  select m.id from lp_expense_month m " +
			" where m.yearmonth<=to_date('"+endmonth+"','yyyy/MM')" +
			" and m.yearmonth>=to_date('"+beginmonth+"','yyyy/MM') " +
			" and m.contractor_id='"+c.getContractorID()+"' and m.expense_type=1";
			this.curStyle=rowLineStyle(this.workbook);
			setCellValue(0,"计算费用的管道信息");
			this.curSheet.addMergedRegion( new Region(k , ( short )0, k, ( short )5) );
			this.curRow.setHeight( ( short )480 );
			k++;
			activeRow(k);
			setCellValue(0, "序号");
			setCellValue(1, "工程名称");
			setCellValue(2, "管道位置");
			setCellValue(3, "区域");
			setCellValue(4, "交维时间");
			setCellValue(5, "管道长度(KM)");
			String cablesql = getExpensePipeByExpenseId(expensmonthsql);
			QueryUtil query = new QueryUtil();
			List cablelist = query.queryBeans(cablesql);
			System.out.println("cablelist.size:"+cablelist.size()+" cablesql=====  "+cablesql);
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			for(int j = 0;cablelist!=null && j<cablelist.size();j++){
				k++;
				activeRow(k);
				BasicDynaBean cable = (BasicDynaBean) cablelist.get(j);
				setCellValue(0,(j+1)+"");
				if(cable.get("work_name")==null){
					setCellValue(1, "");
				}else{
					setCellValue(1,cable.get("work_name").toString());
				}
				if(cable.get("pipe_address")==null){
					setCellValue(2, "");
				}else{
					setCellValue(2,cable.get("pipe_address").toString());
				}
				if(cable.get("scetion")==null){
					setCellValue(3, "");
				}else{
					setCellValue(3,cable.get("scetion").toString());
				}
				if(cable.get("finishtime")==null){
					setCellValue(4, "");
				}else{
					setCellValue(4,cable.get("finishtime").toString());
				}
				if(cable.get("grosslength")==null){
					setCellValue(5, "");
				}else{
					setCellValue(5,cable.get("grosslength")+"");
				}
			}
			k++;
		}catch(Exception e){
			logger.error("导出管道费用失败:"+e.getMessage());
			e.printStackTrace();
		}
	}



	/**
	 * 导出光缆费用结算确认
	 * @param map
	 * @param excelstyle
	 */
	public void exportSettlementExpense(Map<String,Map> map,
			ExpenseAffirm affrim,Contractor c ,
			String beginmonth,String endmonth,ExcelStyle excelstyle) {
		String contractorName = c.getContractorName();
		DynaBean record;
		activeSheet(0);
		int r = 0; // 行索引
		try{
			QueryUtil query = new QueryUtil();
			/*String contractorSql="select * from contractorinfo c where c.contractorid='"+contractorID+"'";
			List contractors = query.queryBeans(contractorSql);
			if(contractors!=null && contractors.size()>0){
				BasicDynaBean bean = (BasicDynaBean) contractors.get(0);
				contractorName = (String) bean.get("contractorname");
			}*/
			this.curStyle=rowLineStyle(this.workbook);
			activeRow(r);
			setCellValue(0,beginmonth+"-"+endmonth+"费用结算确认函");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )9) );
			this.curRow.setHeight( ( short )480 );
			r++;
			activeRow(r);
			this.curStyle = excelstyle.tenFourLineCenter(super.workbook);
			setCellValue(0,"时间");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r+1, ( short )0) );
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			setCellValue(1,"公司名称");
			setCellValue(2,contractorName);
			setCellValue(3,"");
			setCellValue(4,"");
			setCellValue(5,"");
			setCellValue(6,"");
			setCellValue(7,"");
			setCellValue(8,"");
			setCellValue(9,"");
			this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )9) );
			r++;
			activeRow(r);
			setCellValue(1,"光缆级别");
			setCellValue(2,"光缆长度（KM）");
			setCellValue(3,"光缆数量（条）");
			setCellValue(4,"长度合计（KM）");
			setCellValue(5,"数量合计（条）");
			setCellValue(6,"月费用合计（元）");
			setCellValue(7,"扣减费用");
			setCellValue(8,"系统计算月费用（元）");
			setCellValue(9,"扣减原因");
			this.curRow.setHeight( ( short )480 );
			r++;

			Set set = map.keySet();
			Iterator ite = set.iterator();
			while(ite.hasNext()){
				String key = (String) ite.next();//代维
				Map<String,List> expenseMap = map.get(key);
				Set mapSet = expenseMap.keySet();
				Iterator itetrator = mapSet.iterator();
				while(itetrator.hasNext()){
					activeRow(r);
					Object yearmonth = itetrator.next();
					List list = expenseMap.get(yearmonth);
					int rownum = r+list.size()-1;
					this.curStyle = excelstyle.tenFourLineCenter(super.workbook);
					setCellValue(0,yearmonth.toString());
					this.curSheet.addMergedRegion( new Region(r , ( short )0, rownum, ( short )0 ) );	
					if(list!=null && list.size()>0){
						int times = 0;
						Iterator iter = list.iterator();
						while (iter.hasNext()) {
							activeRow(r);
							record = (DynaBean) iter.next();
							String cableLevel = (String)record.get("cable_level");
							if(record.get("cable_type")==null){
								setCellValue(1, "");
							}else{
								setCellValue(1,record.get("cable_type").toString());
							}
							if(cableLevel.equals("1")){
								setCellValue(2,record.get("len1").toString());
								setCellValue(3,record.get("num1").toString());
							}
							if(cableLevel.equals("2")){
								setCellValue(2,record.get("len2").toString());
								setCellValue(3,record.get("num2").toString());
							}
							if(cableLevel.equals("3")){
								setCellValue(2,record.get("len3").toString());
								setCellValue(3,record.get("num3").toString());
							}
							if(cableLevel.equals("4")){
								setCellValue(2,record.get("len4").toString());
								setCellValue(3,record.get("num4").toString());
							}
							if(cableLevel.equals("4A")){
								setCellValue(2,record.get("len4a").toString());
								setCellValue(3,record.get("num4a").toString());
							}
							if(record.get("cable_length")==null){
								setCellValue(4, "");
							}else{
								setCellValue(4,record.get("cable_length").toString());
							}
							if(record.get("cable_num")==null){
								setCellValue(5, "");
							}else{
								setCellValue(5,record.get("cable_num").toString());
							}
							if(record.get("rectify_money")==null){
								setCellValue(6, "");
							}else{
								setCellValue(6,record.get("rectify_money").toString());
							}
							if(record.get("month_price")==null){
								setCellValue(8, "");
							}else{
								setCellValue(8,record.get("month_price").toString());
							}
							if(record.get("deduction_money")==null){
								setCellValue(7, "");
							}else{
								setCellValue(7,record.get("deduction_money").toString());
							}
							super.curStyle = excelstyle.defaultStyle(super.workbook);
							if(record.get("remark")==null){
								setCellValue(9, "");
							}else{
								setCellValue(9,record.get("remark").toString());
							}
							this.curSheet.setColumnWidth((short)9, (short)12000);
							if(times == 0){
								this.curSheet.addMergedRegion( new Region(r , ( short )4, rownum, ( short )4 ) );	
								this.curSheet.addMergedRegion( new Region(r , ( short )5, rownum, ( short )5 ) );
								this.curSheet.addMergedRegion( new Region(r , ( short )6, rownum, ( short )6 ) );	
								this.curSheet.addMergedRegion( new Region(r , ( short )7, rownum, ( short )7 ) );	
								this.curSheet.addMergedRegion( new Region(r , ( short )8, rownum, ( short )8 ) );	
								this.curSheet.addMergedRegion( new Region(r , ( short )9, rownum, ( short )9 ) );	
								times++;
							}else{
								setCellValue(4, "");
								setCellValue(5, "");
								setCellValue(6, "");
								setCellValue(7, "");
								setCellValue(8, "");
								setCellValue(9, "");
							}
							r++;
							this.curStyle = excelstyle.tenFourLineCenter(super.workbook);
						}
					}
				}
				super.curStyle = excelstyle.defaultStyle(super.workbook);
				Double deductionPrice = affrim.getDeductionPrice();
				Double balancePrice = affrim.getBalancePrice();
				double monthMoney = balancePrice+deductionPrice;
				String reamrk = affrim.getRemark();
				activeRow(r);
				setCellValue(0, "维护费用(元)：");
				setCellValue(1, monthMoney+"");
				for(int m = 2;m<10;m++){
					setCellValue(m, "");
				}
				this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );	
				this.curRow.setHeight( ( short )400 );
				r++;
				activeRow(r);
				setCellValue(0, "扣减(元)：");
				setCellValue(1, deductionPrice+"");
				for(int m = 2;m<10;m++){
					setCellValue(m, "");
				}
				this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );
				this.curRow.setHeight( ( short )400 );
				r++;
				activeRow(r);
				setCellValue(0, "结算费用(元)：");
				setCellValue(1, balancePrice+"");
				for(int m = 2;m<10;m++){
					setCellValue(m, "");
				}
				this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );	
				this.curRow.setHeight( ( short )400 );
				r++;
				activeRow(r);
				setCellValue(0, "扣减原因：");
				setCellValue(1, reamrk);
				for(int m = 2;m<10;m++){
					setCellValue(m, "");
				}
				this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );	
				this.curRow.setHeight( ( short )750 );
				r++;
				activeSheet("计算费用的光缆信息");
				int k=0;
				activeRow(k);
				String expensmonthsql = "  select m.id from lp_expense_month m " +
				" where m.yearmonth<=to_date('"+endmonth+"','yyyy/MM')" +
				" and m.yearmonth>=to_date('"+beginmonth+"','yyyy/MM') " +
				" and m.contractor_id='"+c.getContractorID()+"' and m.expense_type=0";
				this.curStyle=rowLineStyle(this.workbook);
				setCellValue(0,"计算费用的光缆信息");
				this.curSheet.addMergedRegion( new Region(k , ( short )0, k, ( short )6) );
				this.curRow.setHeight( ( short )480 );
				k++;
				activeRow(k);
				setCellValue(0, "序号");
				setCellValue(1, "光缆名称");
				setCellValue(2, "光缆级别");
				setCellValue(3, "区域");
				setCellValue(4, "交维时间");
				setCellValue(5, "光缆长度（KM）");
				setCellValue(6, "纤芯数量");
				String cablesql = getExpenseCableByExpenseId(expensmonthsql);
				logger.info("cablesql===============  : "+cablesql);
				List cablelist = query.queryBeans(cablesql);
				super.curStyle = excelstyle.defaultStyle(super.workbook);
				for(int j = 0;cablelist!=null && j<cablelist.size();j++){
					k++;
					activeRow(k);
					BasicDynaBean cable = (BasicDynaBean) cablelist.get(j);
					setCellValue(0,(j+1)+"");
					if(cable.get("segmentname")==null){
						setCellValue(1, "");
					}else{
						setCellValue(1,cable.get("segmentname").toString());
					}
					if(cable.get("cable_level")==null){
						setCellValue(2, "");
					}else{
						setCellValue(2,cable.get("cable_level").toString());
					}
					if(cable.get("scetion")==null){
						setCellValue(3, "");
					}else{
						setCellValue(3,cable.get("scetion").toString());
					}
					if(cable.get("finishtime")==null){
						setCellValue(4, "");
					}else{
						setCellValue(4,cable.get("finishtime").toString());
					}
					if(cable.get("grosslength")==null){
						setCellValue(5, "");
					}else{
						setCellValue(5,cable.get("grosslength")+"");
					}
					if(cable.get("corenumber")==null){
						setCellValue(6, "");
					}else{
						setCellValue(6,cable.get("corenumber").toString());
					}
				}
				k++;
				//}
			}
			//}
		}catch(Exception e){
			logger.error("导出费用失败:"+e.getMessage());
			e.printStackTrace();
		}
	}


	/**
	 * 关联光缆
	 * @param expenseid 月费用主键
	 * @return
	 */
	public String getExpenseCableByExpenseId(String expenseid){
		StringBuffer sb = new StringBuffer();
		sb.append(" select distinct cable.cable_id,rep.segmentname,");
		sb.append(" decode(rep.cable_level,'1','一干','2','骨干','3','汇聚层',");
		sb.append(" '4','接入层') cable_level,");
		sb.append(" decode(rep.scetion,'1','城区','2','郊区') scetion,");
		sb.append(" to_char(rep.finishtime,'yyyy/MM') finishtime,rep.corenumber, ");
		sb.append(" trunc(rep.grosslength,2) grosslength ");
		sb.append(" from lp_expense_cable cable,");
		sb.append(" lp_expense_gradem g,repeatersection rep,lp_expense_month m ");
		sb.append(" where g.id=cable.gradem_id and cable.cable_id=rep.kid");
		sb.append(" and m.id in("+expenseid+") and m.id=g.expense_id");
		sb.append(" order by finishtime");
		return sb.toString();
	}
	
	/**
	 * 关联管道
	 * @param expenseid 月费用主键
	 * @return
	 */
	public String getExpensePipeByExpenseId(String expenseid){
		StringBuffer sb = new StringBuffer();
		sb.append(" select distinct cable.cable_id,rep.work_name,");
		sb.append(" decode(rep.scetion,'1','城区','2','郊区') scetion,");
		sb.append(" to_char(rep.finishtime,'yyyy/MM') finishtime, ");
		sb.append(" rep.pipe_address,");
		sb.append(" trunc(rep.pipe_length_hole,2) grosslength ");
		sb.append(" from lp_expense_cable cable,");
		sb.append(" lp_expense_gradem g,pipeline rep,lp_expense_month m ");
		sb.append(" where g.id=cable.gradem_id and cable.cable_id=rep.id");
		sb.append(" and m.id in("+expenseid+") and m.id=g.expense_id");
		sb.append(" order by finishtime");
		return sb.toString();
	}

	/**
	 * 设置单元格的数据
	 * @param record
	 * @param i
	 */
	public void setValue(DynaBean record,int i){
		Object len = record.get("maintenance_length");
		Object cableNum = record.get("maintenance_num");
		Object price = record.get("unit_price");
		Object maintainMoney = record.get("maintain_money");
		setCellValue(i, len+"");
		setCellValue(i+1, cableNum+"");
		setCellValue(i+2, price+"");
		setCellValue(i+3, maintainMoney+"");
	}

	/**
	 * 设置合计数据
	 * @param record
	 * @param i 指excel单元格的第几列
	 */
	public void setAmountValue(DynaBean record,String cablelevel,int i){
		if(cablelevel.equals("4A")){
			cablelevel="4a";
		}
		Object cableNum = record.get("num"+cablelevel);
		Object cablelen = record.get("len"+cablelevel);
		Object price = record.get("price"+cablelevel);
		System.out.println(cableNum+"  ==============");
		if(cableNum==null || cableNum.equals("")){
			System.out.println(cableNum+"  ========ssssssssss======");
			cableNum = "0";
		}
		if(cablelen==null || cablelen.equals("")){
			cablelen = "0";
		}
		if(price==null || price.equals("")){
			price = "0";
		}
		setCellValue(i, cablelen+"");
		setCellValue(i+1, cableNum+"");
		setCellValue(i+2,"");
		setCellValue(i+3, price+"");
	}

	/**
	 * 设置月费用数据
	 * @param record
	 * @param i
	 */
	public void setMonthPriceValue(DynaBean record,int i){
		Object price = record.get("rectify_money");
		Object cableNum = record.get("cable_num");
		if(price==null || price.equals("")){
			price = "0";
		}
		if(cableNum==null || cableNum.equals("")){
			cableNum = "0";
		}
		setCellValue(i, price+"");
		setCellValue(i+1,"中继段数");
		setCellValue(i+2, cableNum+"");
		setCellValue(i+3, "光缆长度");
	}

	/**
	 * 设置月费用数据为空格(月费用此行只有长度、数量、费用几个值其余excel列内容为空)
	 * @param record
	 * @param i
	 */
	public void setMonthPriceValueIsSpace(int i){
		setCellValue(i," ");
		setCellValue(i+1," ");
		setCellValue(i+2, " ");
		setCellValue(i+3, " ");
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
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);    //填充的背景颜色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);   
		style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
		style.setAlignment( HSSFCellStyle.ALIGN_CENTER );
		style.setBorderRight((short)1);
		style.setWrapText(true);
		return style;
	}


}
