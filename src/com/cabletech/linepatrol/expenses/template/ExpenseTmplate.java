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
 *����ģ��
 * @author fjj
 *
 */
public class ExpenseTmplate extends Template {
	private static Logger logger = Logger.getLogger("Template");

	public ExpenseTmplate(String urlPath) {
		super(urlPath);
	}


	/**
	 * �������·���
	 * @param map
	 * @param excelstyle
	 */
	public void exportExpense(Map<String,Map> map,String yearmonth,ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		int r = 0; // ������
		try{
			this.curStyle=rowLineStyle(this.workbook);
			activeRow(r);
			setCellValue(0,yearmonth+"���·���");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )21 ) );
			this.curRow.setHeight( ( short )480 );
			r++;
			//activeRow(r);
			/*setCellValue(0,"��ά");
			setCellValue(1,"�ּ�ȡ��ϵ��");
			//	setCellValue(2,"��������");
			setCellValue(2,"һ�ɳ���");
			setCellValue(3,"һ���м̶���");
			setCellValue(4,"һ�ɵ���");
			setCellValue(5,"һ��ά������");
			setCellValue(6,"�Ǹɳ���");
			setCellValue(7,"�Ǹ��м̶���");
			setCellValue(8,"�Ǹɵ���");
			setCellValue(9,"�Ǹ�ά������");
			setCellValue(10,"��۳���");
			setCellValue(11,"����м̶���");
			setCellValue(12,"��۵���");
			setCellValue(13,"���ά������");
			setCellValue(14,"���볤��");
			setCellValue(15,"�����м̶���");
			setCellValue(16,"���뵥��");
			setCellValue(17,"����ά������");
			setCellValue(18,"����144о����");
			setCellValue(19,"����144о�����м̶���");
			setCellValue(20,"����144о���뵥��");
			setCellValue(21,"����144о����ά������");
			this.curRow.setHeight( ( short )480 );*/
			//r++;

			//	super.curStyle = excelstyle.defaultStyle(super.workbook);
			Set set = map.keySet();
			Iterator ite = set.iterator();
			while(ite.hasNext()){
				int m = 0;
				String key = (String) ite.next();//��ά
				Map<String,List> expenseMap = map.get(key);
				int rownum = r+expenseMap.size()-1;
				Set mapSet = expenseMap.keySet();
				Iterator itetrator = mapSet.iterator();
				while(itetrator.hasNext()){
					activeRow(r);
					if(m == 0){
						this.curStyle=rowLineStyle(this.workbook);
						setCellValue(0,"��ά");
						setCellValue(1,"�ּ�ȡ��ϵ��");
						//	setCellValue(2,"��������");
						setCellValue(2,"һ�ɳ���");
						setCellValue(3,"һ���м̶���");
						setCellValue(4,"һ�ɵ���(Ԫ)");
						setCellValue(5,"һ��ά������(Ԫ)");
						setCellValue(6,"�Ǹɳ���");
						setCellValue(7,"�Ǹ��м̶���");
						setCellValue(8,"�Ǹɵ���(Ԫ)");
						setCellValue(9,"�Ǹ�ά������(Ԫ)");
						setCellValue(10,"��۳���");
						setCellValue(11,"����м̶���");
						setCellValue(12,"��۵���(Ԫ)");
						setCellValue(13,"���ά������(Ԫ)");
						setCellValue(14,"���볤��");
						setCellValue(15,"�����м̶���");
						setCellValue(16,"���뵥��(Ԫ)");
						setCellValue(17,"����ά������(Ԫ)");
						setCellValue(18,"����144о����");
						setCellValue(19,"����144о�����м̶���");
						setCellValue(20,"����144о���뵥��(Ԫ)");
						setCellValue(21,"����144о����ά������(Ԫ)");
						this.curRow.setHeight( ( short )480 );
						r++;
						activeRow(r);
						this.curStyle = excelstyle.tenFourLineCenter(super.workbook);
						setCellValue(0,key);
						this.curSheet.addMergedRegion( new Region(r , ( short )0, rownum, ( short )0 ) );	
						m++;
					}
					Object factor = itetrator.next();
					//if(factor.equals("�ϼ�") || factor.equals("�·���")){
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
							if(cableLevel.equals("1") && factor.equals("�ϼ�")){
								setAmountValue(record,cableLevel,2);
							}else if(cableLevel.equals("1") && factor.equals("�·���")){
								setMonthPriceValue(record,2);
							}else if(cableLevel.equals("1")){
								setValue(record,2);
							}
							if(cableLevel.equals("2") && factor.equals("�ϼ�")){
								setAmountValue(record,cableLevel,6);
							}else if(cableLevel.equals("2") && factor.equals("�·���")){
								Object cableLen = record.get("cable_length");
								setMonthPriceValueIsSpace(6);
								setCellValue(6, cableLen+"");
							}else if(cableLevel.equals("2")){
								setValue(record,6);
							}
							if(cableLevel.equals("3") && factor.equals("�ϼ�")){
								setAmountValue(record,cableLevel,10);
							}else if(cableLevel.equals("3") && factor.equals("�·���")){
								setMonthPriceValueIsSpace(10);
							}else if(cableLevel.equals("3")){
								setValue(record,10);
							}

							if(cableLevel.equals("4") && factor.equals("�ϼ�")){
								setAmountValue(record,cableLevel,14);
							}else if(cableLevel.equals("4") && factor.equals("�·���")){
								setMonthPriceValueIsSpace(14);
							}else if(cableLevel.equals("4")){
								setValue(record,14);
							}

							if(cableLevel.equals("4A") && factor.equals("�ϼ�")){
								setAmountValue(record,cableLevel,18);
							}else if(cableLevel.equals("4A") && factor.equals("�·���")){
								setMonthPriceValueIsSpace(18);
							}else if(cableLevel.equals("4A")){//4A��ʾ��������144о��
								setValue(record,18);
							}
						}
					}else{//���û������������Ϊ��
						for(int i = 2;i<=21;i++){
							setCellValue(i, " ");
						}
					}
					r++;
				}
			}
		}catch(Exception e){
			logger.error("��������ʧ��:"+e.getMessage());
			e.printStackTrace();
		}
	}




	/**
	 * �����ܵ��ķ���
	 * @param map
	 * @param excelstyle
	 */
	public void exportPipeExpense(Map<String,Object> map,String yearmonth,ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		int r = 0; // ������
		try{
			this.curStyle=rowLineStyle(this.workbook);
			activeRow(r);
			setCellValue(0,yearmonth+"�ܵ�����");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )4 ) );
			this.curRow.setHeight( ( short )480 );
			r++;
			activeRow(r);
			setCellValue(0,"��ά��˾");
			setCellValue(1,"ά������(Ԫ)");
			setCellValue(2,"ȡ�ѵ���(Ԫ)");
			setCellValue(3,"�ܵ�����");
			setCellValue(4,"�ܵ����� (KM)");
		//	setCellValue(5,"��������(Ԫ) ");
		//	setCellValue(6,"�ۼ�����(Ԫ) ");
			//setCellValue(7,"�ۼ�ԭ�� ");
			this.curRow.setHeight( ( short )480 );
			r++;

			super.curStyle = excelstyle.defaultStyle(super.workbook);
			Set set = map.keySet();
			Iterator ite = set.iterator();
			while(ite.hasNext()){
				activeRow(r);
				String key = (String) ite.next();//��ά
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
			logger.error("�����ܵ�����ʧ��:"+e.getMessage());
			e.printStackTrace();
		}
	}

	
	/**
	 * �����ܵ��ķ���ȷ��
	 * @param map
	 * @param excelstyle
	 */
	public void exportPipeSettlementExpense(Map<String,Object> map,
			ExpenseAffirm affrim,Contractor c ,
			String beginmonth,String endmonth,ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		int r = 0; // ������
		try{
			this.curStyle=rowLineStyle(this.workbook);
			activeRow(r);
			setCellValue(0,beginmonth+"-"+endmonth+c.getContractorName()+"���ý���ȷ�Ϻ�");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )7 ) );
			this.curRow.setHeight( ( short )480 );
			r++;
			activeRow(r);
			setCellValue(0,"ʱ��");
			setCellValue(1,"ά������(Ԫ)");
			setCellValue(2,"ȡ�ѵ���(Ԫ)");
			setCellValue(3,"�ܵ�����");
			setCellValue(4,"�ܵ����� (KM)");
			setCellValue(5,"��������(Ԫ) ");
			setCellValue(6,"�ۼ�����(Ԫ) ");
			setCellValue(7,"�ۼ�ԭ�� ");
			this.curRow.setHeight( ( short )480 );
			r++;

			super.curStyle = excelstyle.defaultStyle(super.workbook);
			Set set = map.keySet();
			Iterator ite = set.iterator();
			while(ite.hasNext()){
				activeRow(r);
				String key = (String) ite.next();//ʱ��
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
			setCellValue(0, "ά������(Ԫ)��");
			setCellValue(1, monthMoney+"");
			for(int m = 2;m<8;m++){
				setCellValue(m, "");
			}
			this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );	
			this.curRow.setHeight( ( short )400 );
			r++;
			activeRow(r);
			setCellValue(0, "�ۼ�(Ԫ)��");
			setCellValue(1, deductionPrice+"");
			for(int m = 2;m<8;m++){
				setCellValue(m, "");
			}
			this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );
			this.curRow.setHeight( ( short )400 );
			r++;
			activeRow(r);
			setCellValue(0, "�������(Ԫ)��");
			setCellValue(1, balancePrice+"");
			for(int m = 2;m<8;m++){
				setCellValue(m, "");
			}
			this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );	
			this.curRow.setHeight( ( short )400 );
			r++;
			activeRow(r);
			setCellValue(0, "�ۼ�ԭ��");
			setCellValue(1, reamrk);
			for(int m = 2;m<8;m++){
				setCellValue(m, "");
			}
			this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );	
			this.curRow.setHeight( ( short )750 );
			
			activeSheet("������õĹܵ���Ϣ");
			int k=0;
			activeRow(k);
			String expensmonthsql = "  select m.id from lp_expense_month m " +
			" where m.yearmonth<=to_date('"+endmonth+"','yyyy/MM')" +
			" and m.yearmonth>=to_date('"+beginmonth+"','yyyy/MM') " +
			" and m.contractor_id='"+c.getContractorID()+"' and m.expense_type=1";
			this.curStyle=rowLineStyle(this.workbook);
			setCellValue(0,"������õĹܵ���Ϣ");
			this.curSheet.addMergedRegion( new Region(k , ( short )0, k, ( short )5) );
			this.curRow.setHeight( ( short )480 );
			k++;
			activeRow(k);
			setCellValue(0, "���");
			setCellValue(1, "��������");
			setCellValue(2, "�ܵ�λ��");
			setCellValue(3, "����");
			setCellValue(4, "��άʱ��");
			setCellValue(5, "�ܵ�����(KM)");
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
			logger.error("�����ܵ�����ʧ��:"+e.getMessage());
			e.printStackTrace();
		}
	}



	/**
	 * �������·��ý���ȷ��
	 * @param map
	 * @param excelstyle
	 */
	public void exportSettlementExpense(Map<String,Map> map,
			ExpenseAffirm affrim,Contractor c ,
			String beginmonth,String endmonth,ExcelStyle excelstyle) {
		String contractorName = c.getContractorName();
		DynaBean record;
		activeSheet(0);
		int r = 0; // ������
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
			setCellValue(0,beginmonth+"-"+endmonth+"���ý���ȷ�Ϻ�");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )9) );
			this.curRow.setHeight( ( short )480 );
			r++;
			activeRow(r);
			this.curStyle = excelstyle.tenFourLineCenter(super.workbook);
			setCellValue(0,"ʱ��");
			this.curSheet.addMergedRegion( new Region(r , ( short )0, r+1, ( short )0) );
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			setCellValue(1,"��˾����");
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
			setCellValue(1,"���¼���");
			setCellValue(2,"���³��ȣ�KM��");
			setCellValue(3,"��������������");
			setCellValue(4,"���Ⱥϼƣ�KM��");
			setCellValue(5,"�����ϼƣ�����");
			setCellValue(6,"�·��úϼƣ�Ԫ��");
			setCellValue(7,"�ۼ�����");
			setCellValue(8,"ϵͳ�����·��ã�Ԫ��");
			setCellValue(9,"�ۼ�ԭ��");
			this.curRow.setHeight( ( short )480 );
			r++;

			Set set = map.keySet();
			Iterator ite = set.iterator();
			while(ite.hasNext()){
				String key = (String) ite.next();//��ά
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
				setCellValue(0, "ά������(Ԫ)��");
				setCellValue(1, monthMoney+"");
				for(int m = 2;m<10;m++){
					setCellValue(m, "");
				}
				this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );	
				this.curRow.setHeight( ( short )400 );
				r++;
				activeRow(r);
				setCellValue(0, "�ۼ�(Ԫ)��");
				setCellValue(1, deductionPrice+"");
				for(int m = 2;m<10;m++){
					setCellValue(m, "");
				}
				this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );
				this.curRow.setHeight( ( short )400 );
				r++;
				activeRow(r);
				setCellValue(0, "�������(Ԫ)��");
				setCellValue(1, balancePrice+"");
				for(int m = 2;m<10;m++){
					setCellValue(m, "");
				}
				this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );	
				this.curRow.setHeight( ( short )400 );
				r++;
				activeRow(r);
				setCellValue(0, "�ۼ�ԭ��");
				setCellValue(1, reamrk);
				for(int m = 2;m<10;m++){
					setCellValue(m, "");
				}
				this.curSheet.addMergedRegion( new Region(r , ( short )1, r, ( short )9 ) );	
				this.curRow.setHeight( ( short )750 );
				r++;
				activeSheet("������õĹ�����Ϣ");
				int k=0;
				activeRow(k);
				String expensmonthsql = "  select m.id from lp_expense_month m " +
				" where m.yearmonth<=to_date('"+endmonth+"','yyyy/MM')" +
				" and m.yearmonth>=to_date('"+beginmonth+"','yyyy/MM') " +
				" and m.contractor_id='"+c.getContractorID()+"' and m.expense_type=0";
				this.curStyle=rowLineStyle(this.workbook);
				setCellValue(0,"������õĹ�����Ϣ");
				this.curSheet.addMergedRegion( new Region(k , ( short )0, k, ( short )6) );
				this.curRow.setHeight( ( short )480 );
				k++;
				activeRow(k);
				setCellValue(0, "���");
				setCellValue(1, "��������");
				setCellValue(2, "���¼���");
				setCellValue(3, "����");
				setCellValue(4, "��άʱ��");
				setCellValue(5, "���³��ȣ�KM��");
				setCellValue(6, "��о����");
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
			logger.error("��������ʧ��:"+e.getMessage());
			e.printStackTrace();
		}
	}


	/**
	 * ��������
	 * @param expenseid �·�������
	 * @return
	 */
	public String getExpenseCableByExpenseId(String expenseid){
		StringBuffer sb = new StringBuffer();
		sb.append(" select distinct cable.cable_id,rep.segmentname,");
		sb.append(" decode(rep.cable_level,'1','һ��','2','�Ǹ�','3','��۲�',");
		sb.append(" '4','�����') cable_level,");
		sb.append(" decode(rep.scetion,'1','����','2','����') scetion,");
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
	 * �����ܵ�
	 * @param expenseid �·�������
	 * @return
	 */
	public String getExpensePipeByExpenseId(String expenseid){
		StringBuffer sb = new StringBuffer();
		sb.append(" select distinct cable.cable_id,rep.work_name,");
		sb.append(" decode(rep.scetion,'1','����','2','����') scetion,");
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
	 * ���õ�Ԫ�������
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
	 * ���úϼ�����
	 * @param record
	 * @param i ָexcel��Ԫ��ĵڼ���
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
	 * �����·�������
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
		setCellValue(i+1,"�м̶���");
		setCellValue(i+2, cableNum+"");
		setCellValue(i+3, "���³���");
	}

	/**
	 * �����·�������Ϊ�ո�(�·��ô���ֻ�г��ȡ����������ü���ֵ����excel������Ϊ��)
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
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);    //���ı�����ɫ
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);   
		style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
		style.setAlignment( HSSFCellStyle.ALIGN_CENTER );
		style.setBorderRight((short)1);
		style.setWrapText(true);
		return style;
	}


}
