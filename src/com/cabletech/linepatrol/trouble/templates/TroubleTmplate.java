package com.cabletech.linepatrol.trouble.templates;

import java.math.BigDecimal;
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

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.trouble.module.TroubleNormGuide;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;

/**
 *故障模块
 * @author fjj
 *
 */
public class TroubleTmplate extends Template {
	private static Logger logger = Logger.getLogger("Template");

	public TroubleTmplate(String urlPath) {
		super(urlPath);
	}

	/**
	 * 导出故障统计
	 * @param list
	 * @param excelstyle
	 */
	public void doExportTroubles(List list, ExcelStyle excelstyle) {
		//System.out.println("导出故障统计================= ");
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // 行索引
		try{
			if(list != null && list.size()>0) {
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					record = (DynaBean) iter.next();
					activeRow(r);
					Object troubleid = record.get("id");
					setCellValue(0, r-1+"");
					if (record.get("trouble_id") == null) {
						setCellValue(1, "");
					} else {
						setCellValue(1, record.get("trouble_id").toString());
					}
					if (record.get("trouble_name") == null) {
						setCellValue(2, "");
					} else {
						setCellValue(2, record.get("trouble_name").toString());
					}
					if (record.get("trouble_send_time") == null) {
						setCellValue(3, "");
					} else {
						setCellValue(3, record.get("trouble_send_time").toString());
					}
					if (record.get("trouble_end_time") == null) {
						setCellValue(4, "");
					} else {
						setCellValue(4, record.get("trouble_end_time").toString());
					}

					if (record.get("etime") == null) {
						this.curRow.setHeight((short)420);
						setCellValue(5, "");
					} else {
						this.curRow.setHeight((short)420);
						setCellValue(5, record.get("etime").toString());
					}

					if (record.get("trunk") == null) {
						this.curRow.setHeight((short)420);
						setCellValue(6, "");
					} else {
						this.curRow.setHeight((short)420);
						setCellValue(6, record.get("trunk").toString());
					}

					String cableType ="";
					if(record.get("trunk") != null){
						String cablesql = "select distinct line.name" +
						" from lp_trouble_info t,lp_trouble_reply reply,lp_trouble_cable_info cable,repeatersection cseg,lineclassdic line" +
						" where t.id = reply.trouble_id and cable.trouble_reply_id=reply.id and cseg.kid=cable.trouble_cable_line_id" +
						" and cseg.netlayer=line.code and t.id='"+troubleid+"'";
						QueryUtil query = new QueryUtil();
						//	logger.info("cablesql===== :"+cablesql);
						List cables = query.queryBeans(cablesql);
						if(cables!=null && cables.size()>0){
							for(int i = 0;i<cables.size();i++){
								BasicDynaBean bean = (BasicDynaBean) cables.get(i);
								String type = (String) bean.get("name");
								if(i==0){
									cableType+=type;
								}else{
									cableType+=","+type;
								}
							}
						}
					}

					setCellValue(7, cableType);
					if (record.get("impress_type") == null) {
						this.curRow.setHeight((short)420);
						setCellValue(8, "");
					} else {
						this.curRow.setHeight((short)420);
						setCellValue(8, record.get("impress_type").toString());
					}

					String reasonName="";
					String reasonsql = "select df.lable" +
					" from lp_trouble_info t,dictionary_formitem df" +
					" where df.code=t.trouble_reason_id and t.id='"+troubleid+"'"+
					"  and df.assortment_id='"+TroubleConstant.ASSORTMENT_TROUBLE_REASON+"'";
					QueryUtil query = new QueryUtil();
					List reasons = query.queryBeans(reasonsql);
					if(reasons!=null&&reasons.size()>0){
						BasicDynaBean bean = (BasicDynaBean) reasons.get(0);
						reasonName = (String) bean.get("lable");
					}
					/*	if (record.get("trouble_reason") == null) {
					this.curRow.setHeight((short)420);
					setCellValue(7, "");
				} else {
					this.curRow.setHeight((short)420);
					setCellValue(7, record.get("trouble_reason").toString());
				}
					 */
					setCellValue(9,reasonName);
					if (record.get("contractorname") == null) {
						this.curRow.setHeight((short)420);
						setCellValue(10, "");
					} else {
						this.curRow.setHeight((short)420);
						setCellValue(10, record.get("contractorname").toString());
					}

					String approversql = "select u.username from lp_approve_info app,lp_trouble_reply reply,userinfo u" +
					" where app.object_id=reply.id and reply.trouble_id='" +troubleid+"'"+
					" and app.object_type='"+TroubleConstant.LP_TROUBLE_REPLY+"'"+
					" and  app.approve_result='"+TroubleConstant.APPROVE_RESULT_PASS+"'"+
					" and u.userid=app.approver_id";
					// QueryUtil query = new QueryUtil();
					//	logger.info("approver===== :"+approversql);
					String approver="";
					List approvers = query.queryBeans(approversql);
					if(approvers!=null && approvers.size()>0){
						BasicDynaBean bean = (BasicDynaBean) approvers.get(0);
						approver = (String) bean.get("username");

					}
					setCellValue(11,approver);

					if (record.get("trouble_remark") == null) {
						this.curRow.setHeight((short)420);
						setCellValue(12, "");
					} else {
						this.curRow.setHeight((short)420);
						setCellValue(12 , record.get("trouble_remark").toString());
					}


					r++;
				}
			}
		}catch(Exception e){
			logger.error("导出故障统计失败:"+e.getMessage());
			e.getStackTrace();
		}
	}


	/**
	 * 导出故障年指标
	 * @param list
	 * @param excelstyle
	 */
	public void doExportYearTroubleQuotas(List list,TroubleNormGuide guide,
			String year,ExcelStyle excelstyle) {
		double normtime = guide.getInterdictionTimeNormValue();
		double normtimes = guide.getInterdictionTimesNormValue();
		double daretime = guide.getInterdictionTimeDareValue();
		double daretimes = guide.getInterdictionTimesDareValue();
		String type = guide.getGuideType();
		String guidetype = "";
		if(type.equals(TroubleConstant.QUOTA)){
			guidetype = "一干故障指标";
		}else{
			guidetype = "城域网故障指标";
		}
		DynaBean record;
		activeSheet(0);
		int r = 0; // 行索引
		try{
			if(list != null && list.size()>0) {
				this.curStyle=rowLineStyle(this.workbook);
				activeRow(r);
				setCellValue(0,year+"年 "+guidetype );
				if(!type.equals(TroubleConstant.QUOTA)){
				this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )26 ) );
				}else{
					this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )25 ) );
				}
				this.curRow.setHeight( ( short )480 );
				r++;
				activeRow(r);
				setCellValue(0,"序号");
				setCellValue(1,"代维");
				setCellValue(2,"千公里阻断次数(次)");
				setCellValue(3,"");
				setCellValue(4,"千公里阻断时长(小时)");
				setCellValue(5,"");
				setCellValue(6,"光缆每次故障平均历时(小时)");
				setCellValue(7,"");
				setCellValue(8,"1月维护长度(公里)");
				setCellValue(9,"2月维护长度(公里)");
				setCellValue(10,"3月维护长度(公里)");
				setCellValue(11,"4月维护长度(公里)");
				setCellValue(12,"5月维护长度(公里)");
				setCellValue(13,"6月维护长度(公里)");
				setCellValue(14,"7月维护长度(公里)");
				setCellValue(15,"8月维护长度(公里)");
				setCellValue(16,"9月维护长度(公里)");
				setCellValue(17,"10月维护长度(公里)");
				setCellValue(18,"11月维护长度(公里)");
				setCellValue(19,"12月维护长度(公里)");
				setCellValue(20,normtime+"小时/千公里(基准值)");
				setCellValue(21,normtimes+"次/千公里(基准值)");
				setCellValue(22,daretime+"小时/千公里(挑战值)");
				setCellValue(23,daretimes+"次/千公里(挑战值)");
				setCellValue(24,"全年千公里阻断次数模拟值(次)");
				setCellValue(25,"全年千公里阻断时长模拟值(小时)");
				if(!type.equals(TroubleConstant.QUOTA)){
					setCellValue(26,"城域网单次抢修历时超出指标值的故障次数");
				}
				this.curSheet.addMergedRegion( new Region(r , ( short )0, r+1, ( short )0 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )1, r+1, ( short )1 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )3 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )4, r, ( short )5 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )6, r, ( short )7 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )8, r+1, ( short )8 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )9, r+1, ( short )9) );
				this.curSheet.addMergedRegion( new Region(r , ( short )10, r+1, ( short )10 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )11, r+1, ( short )11 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )12, r+1, ( short )12 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )13, r+1, ( short )13 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )14, r+1, ( short )14 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )15, r+1, ( short )15 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )16, r+1, ( short )16 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )17, r+1, ( short )17 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )18, r+1, ( short )18 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )19, r+1, ( short )19 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )20, r+1, ( short )20 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )21, r+1, ( short )21 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )22, r+1, ( short )22 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )23, r+1, ( short )23 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )24, r+1, ( short )24 ) );
				if(!type.equals(TroubleConstant.QUOTA)){
					this.curSheet.addMergedRegion( new Region(r , ( short )25, r+1, ( short )25 ) );
					this.curSheet.addMergedRegion( new Region(r , ( short )26, r+1, ( short )26 ) );
				}else{
				this.curSheet.addMergedRegion( new Region(r , ( short )25, r+1, ( short )25 ) );
				}
				this.curRow.setHeight( ( short )480 );
				r++;
				activeRow(r);
				setCellValue(0,"");
				setCellValue(1,"");
				setCellValue(2,"指标");
				setCellValue(3,"故障次数");
				setCellValue(4,"指标");
				setCellValue(5,"故障总时长");
				setCellValue(6,"指标");
				setCellValue(7,"故障平均时长");
				if(!type.equals(TroubleConstant.QUOTA)){
				for(int m = 8;m<27;m++){
					setCellValue(m,"");
				}
				}else{
					for(int m = 8;m<26;m++){
						setCellValue(m,"");
					}
				}
				r++;
				super.curStyle = excelstyle.defaultStyle(super.workbook);
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					record = (DynaBean) iter.next();
					activeRow(r);
					setCellValue(0, r-2+"");
					if (record.get("contractorname") == null) {
						setCellValue(1, "");
					} else {
						setCellValue(1, record.get("contractorname").toString());
					}
					if (record.get("standard_times") == null) {
						setCellValue(2, "");
					} else {
						setCellValue(2, record.get("standard_times").toString());
					}
					if (record.get("trouble_times") == null) {
						setCellValue(3, "");
					} else {
						setCellValue(3, record.get("trouble_times").toString());
					}
					if (record.get("standard_time") == null) {
						setCellValue(4, "");
					} else {
						setCellValue(4, record.get("standard_time").toString());
					}
					if (record.get("interdiction_time") == null) {
						setCellValue(5, "");
					} else {
						setCellValue(5, record.get("interdiction_time").toString());
					}
					double singletime = guide.getRtrTimeNormValue();
					setCellValue(6, singletime+"");
					Object intertime = record.get("interdiction_time");
					Object troubletimes = record.get("trouble_times");
					double avgtime = 0;
					if(intertime!=null && troubletimes!=null){
						double time = Double.parseDouble(intertime.toString());
						double times = Double.parseDouble(troubletimes.toString());
						if(times>0){
							avgtime = div(time,times,2);
						}
					}
					setCellValue(7, avgtime+"");
					if (record.get("jan") == null) {
						setCellValue(8, "");
					} else {
						setCellValue(8, record.get("jan").toString());
					}
					if (record.get("feb") == null) {
						setCellValue(9, "");
					} else {
						setCellValue(9, record.get("feb").toString());
					}
					if (record.get("mar") == null) {
						setCellValue(10, "");
					} else {
						setCellValue(10, record.get("mar").toString());
					}
					if (record.get("apr") == null) {
						setCellValue(11, "");
					} else {
						setCellValue(11, record.get("apr").toString());
					}
					if (record.get("may") == null) {
						setCellValue(12, "");
					} else {
						setCellValue(12, record.get("may").toString());
					}
					if (record.get("june") == null) {
						setCellValue(13, "");
					} else {
						setCellValue(13, record.get("june").toString());
					}
					if (record.get("july") == null) {
						setCellValue(14, "");
					} else {
						setCellValue(14, record.get("july").toString());
					}
					if (record.get("aug") == null) {
						setCellValue(15, "");
					} else {
						setCellValue(15, record.get("aug").toString());
					}
					if (record.get("sep") == null) {
						setCellValue(16, "");
					} else {
						setCellValue(16, record.get("sep").toString());
					}
					if (record.get("oct") == null) {
						setCellValue(17, "");
					} else {
						setCellValue(17, record.get("oct").toString());
					}
					if (record.get("nov") == null) {
						setCellValue(18, "");
					} else {
						setCellValue(18, record.get("nov").toString());
					}
					if (record.get("dece") == null) {
						setCellValue(19, "");
					} else {
						setCellValue(19, record.get("dece").toString());
					}
					if (record.get("standard_time") == null) {
						setCellValue(20, "");
					} else {
						setCellValue(20, record.get("standard_time").toString());
					}
					if (record.get("standard_times") == null) {
						setCellValue(21, "");
					} else {
						setCellValue(21, record.get("standard_times").toString());
					}
					if (record.get("dare_time") == null) {
						setCellValue(22, "");
					} else {
						setCellValue(22, record.get("dare_time").toString());
					}
					if (record.get("dare_times") == null) {
						setCellValue(23, "");
					} else {
						setCellValue(23, record.get("dare_times").toString());
					}
					if (record.get("whole_year_standard_times") == null) {
						setCellValue(24, "");
					} else {
						setCellValue(24, record.get("whole_year_standard_times").toString());
					}
					if (record.get("whole_year_standard_time") == null) {
						setCellValue(25, "");
					} else {
						setCellValue(25, record.get("whole_year_standard_time").toString());
					}
					if(!type.equals(TroubleConstant.QUOTA)){
						if (record.get("city_area_out_standard_number") == null) {
							setCellValue(26, "");
						} else {
							setCellValue(26, record.get("city_area_out_standard_number").toString());
						}
					}
					r++;
				}
			}
		}catch(Exception e){
			logger.error("导出故障指标失败:"+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 导出故障年指标
	 * @param list
	 * @param excelstyle
	 */
	public void doExportYearTroubleQuotas(Map map,TroubleNormGuide guide,
			String year,ExcelStyle excelstyle) {
		double normtime = guide.getInterdictionTimeNormValue();
		double normtimes = guide.getInterdictionTimesNormValue();
		double daretime = guide.getInterdictionTimeDareValue();
		double daretimes = guide.getInterdictionTimesDareValue();
		String type = guide.getGuideType();
		String guidetype = "";
		if(type.equals(TroubleConstant.QUOTA)){
			guidetype = "一干故障指标";
		}else{
			guidetype = "城域网故障指标";
		}
		DynaBean record;
		activeSheet(0);
		int r = 0; // 行索引
		try{
			if(map != null ) {
				this.curStyle=rowLineStyle(this.workbook);
				activeRow(r);
				setCellValue(0,year+"年 "+guidetype );
				if(!type.equals(TroubleConstant.QUOTA)){
				this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )26 ) );
				}else{
					this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )25 ) );
				}
				this.curRow.setHeight( ( short )480 );
				r++;
				activeRow(r);
				setCellValue(0,"序号");
				setCellValue(1,"代维");
				setCellValue(2,"千公里阻断次数(次)");
				setCellValue(3,"");
				setCellValue(4,"千公里阻断时长(小时)");
				setCellValue(5,"");
				setCellValue(6,"光缆每次故障平均历时(小时)");
				setCellValue(7,"");
				setCellValue(8,"1月维护长度(公里)");
				setCellValue(9,"2月维护长度(公里)");
				setCellValue(10,"3月维护长度(公里)");
				setCellValue(11,"4月维护长度(公里)");
				setCellValue(12,"5月维护长度(公里)");
				setCellValue(13,"6月维护长度(公里)");
				setCellValue(14,"7月维护长度(公里)");
				setCellValue(15,"8月维护长度(公里)");
				setCellValue(16,"9月维护长度(公里)");
				setCellValue(17,"10月维护长度(公里)");
				setCellValue(18,"11月维护长度(公里)");
				setCellValue(19,"12月维护长度(公里)");
				setCellValue(20,normtime+"小时/千公里(基准值)");
				setCellValue(21,normtimes+"次/千公里(基准值)");
				setCellValue(22,daretime+"小时/千公里(挑战值)");
				setCellValue(23,daretimes+"次/千公里(挑战值)");
				setCellValue(24,"全年千公里阻断次数模拟值(次)");
				setCellValue(25,"全年千公里阻断时长模拟值(小时)");
				if(!type.equals(TroubleConstant.QUOTA)){
					setCellValue(26,"城域网单次抢修历时超出指标值的故障次数");
				}
				this.curSheet.addMergedRegion( new Region(r , ( short )0, r+1, ( short )0 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )1, r+1, ( short )1 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )3 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )4, r, ( short )5 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )6, r, ( short )7 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )8, r+1, ( short )8 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )9, r+1, ( short )9) );
				this.curSheet.addMergedRegion( new Region(r , ( short )10, r+1, ( short )10 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )11, r+1, ( short )11 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )12, r+1, ( short )12 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )13, r+1, ( short )13 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )14, r+1, ( short )14 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )15, r+1, ( short )15 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )16, r+1, ( short )16 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )17, r+1, ( short )17 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )18, r+1, ( short )18 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )19, r+1, ( short )19 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )20, r+1, ( short )20 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )21, r+1, ( short )21 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )22, r+1, ( short )22 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )23, r+1, ( short )23 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )24, r+1, ( short )24 ) );
				if(!type.equals(TroubleConstant.QUOTA)){
					this.curSheet.addMergedRegion( new Region(r , ( short )25, r+1, ( short )25 ) );
					this.curSheet.addMergedRegion( new Region(r , ( short )26, r+1, ( short )26 ) );
				}else{
				this.curSheet.addMergedRegion( new Region(r , ( short )25, r+1, ( short )25 ) );
				}
				this.curRow.setHeight( ( short )480 );
				r++;
				activeRow(r);
				setCellValue(0,"");
				setCellValue(1,"");
				setCellValue(2,"指标");
				setCellValue(3,"故障次数");
				setCellValue(4,"指标");
				setCellValue(5,"故障总时长");
				setCellValue(6,"指标");
				setCellValue(7,"故障平均时长");
				if(!type.equals(TroubleConstant.QUOTA)){
				for(int m = 8;m<27;m++){
					setCellValue(m,"");
				}
				}else{
					for(int m = 8;m<26;m++){
						setCellValue(m,"");
					}
				}
				r++;
				super.curStyle = excelstyle.defaultStyle(super.workbook);
				Set keySet=((Map)map.get("resultMap")).keySet();
				Iterator iter = keySet.iterator();
				while (iter.hasNext()) {
					String key=(String)iter.next();
					Map contractorMap=(Map)((Map)map.get("resultMap")).get(key);
					Map monthMap=(Map)contractorMap.get("monthMap");
					activeRow(r);
					setCellValue(0, r-2+"");
					if (key == null) {
						setCellValue(1, "");
					} else {
						setCellValue(1, key.toString());
					}
						setCellValue(2, contractorMap.get("standard_times").toString());
						setCellValue(3, contractorMap.get("trouble_times").toString());
						setCellValue(4, contractorMap.get("standard_time").toString());
						setCellValue(5, contractorMap.get("trouble_time").toString());
					double singletime = guide.getRtrTimeNormValue();
					setCellValue(6, singletime+"");
					Object intertime = contractorMap.get("trouble_time");
					Object troubletimes = contractorMap.get("trouble_times");
					double avgtime = 0;
					if(intertime!=null && troubletimes!=null){
						double time = Double.parseDouble(intertime.toString());
						double times = Double.parseDouble(troubletimes.toString());
						if(times>0){
							avgtime = div(time,times,2);
						}
					}
					setCellValue(7, avgtime+"");
					Set monthKeySet=monthMap.keySet();
					Iterator monthIter = monthKeySet.iterator();
					int k=0;
					while(monthIter!=null&&monthIter.hasNext()){
						String monthKey=(String)monthIter.next();
						if(monthMap.get(monthKey)!=null){
							DynaBean tmpBean=(DynaBean)monthMap.get(monthKey);
						setCellValue(8+k, tmpBean.get("maintenance_length").toString());
						}else{
							setCellValue(8+k, "0");
						}
						k++;
					}
						setCellValue(20, contractorMap.get("standard_time").toString());
						setCellValue(21, contractorMap.get("standard_times").toString());
						setCellValue(22, contractorMap.get("dare_time").toString());
						setCellValue(23, contractorMap.get("dare_times").toString());
						setCellValue(24, contractorMap.get("whole_year_standard_times").toString());
						setCellValue(25, contractorMap.get("whole_year_standard_time").toString());
					if(!type.equals(TroubleConstant.QUOTA)){
							setCellValue(26, contractorMap.get("city_area_out_standard_number").toString());
					}
					r++;
				}
			}
		}catch(Exception e){
			logger.error("导出故障指标失败:"+e.getMessage());
			e.printStackTrace();
		}
	}

	
	/**
	 * 导出故障年指标
	 * @param list
	 * @param excelstyle
	 */
	public void doExportTimeAreaTroubleQuotas(Map map,TroubleNormGuide guide,
			String beginTime,String endTime,ExcelStyle excelstyle) {
		double normtime = guide.getInterdictionTimeNormValue();
		double normtimes = guide.getInterdictionTimesNormValue();
		double daretime = guide.getInterdictionTimeDareValue();
		double daretimes = guide.getInterdictionTimesDareValue();
		String type = guide.getGuideType();
		String guidetype = "";
		if(type.equals(TroubleConstant.QUOTA)){
			guidetype = "一干故障指标";
		}else{
			guidetype = "城域网故障指标";
		}
		DynaBean record;
		activeSheet(0);
		int r = 0; // 行索引
		try{
			if(map != null ) {
				List monthList=(List)map.get("monthList");
				if(monthList!=null){
				this.curStyle=rowLineStyle(this.workbook);
				activeRow(r);
				setCellValue(0,beginTime+"月到"+endTime+"月 "+guidetype );
				if(!type.equals(TroubleConstant.QUOTA)){
				this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )(12+monthList.size()) ) );
				}else{
					this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )(11+monthList.size()) ) );
				}
				this.curRow.setHeight( ( short )480 );
				r++;
				activeRow(r);
				setCellValue(0,"序号");
				setCellValue(1,"代维");
				setCellValue(2,"千公里阻断次数(次)");
				setCellValue(3,"");
				setCellValue(4,"千公里阻断时长(小时)");
				setCellValue(5,"");
				setCellValue(6,"光缆每次故障平均历时(小时)");
				setCellValue(7,"");
				for(int i=0;i<monthList.size();i++){
					setCellValue(7+i+1,monthList.get(i)+"月维护长度(公里)");
				}
				setCellValue(7+monthList.size()+1,normtime+"小时/千公里(基准值)");
				setCellValue(7+monthList.size()+2,normtimes+"次/千公里(基准值)");
				setCellValue(7+monthList.size()+3,daretime+"小时/千公里(挑战值)");
				setCellValue(7+monthList.size()+4,daretimes+"次/千公里(挑战值)");
				if(!type.equals(TroubleConstant.QUOTA)){
					setCellValue(7+monthList.size()+5,"城域网单次抢修历时超出指标值的故障次数");
				}
				this.curSheet.addMergedRegion( new Region(r , ( short )0, r+1, ( short )0 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )1, r+1, ( short )1 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )3 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )4, r, ( short )5 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )6, r, ( short )7 ) );
				for(int i=0;i<monthList.size();i++){
				this.curSheet.addMergedRegion( new Region(r , ( short )(7+i+1), r+1, ( short )(7+i+1) ) );
				}
				this.curSheet.addMergedRegion( new Region(r , ( short )(7+monthList.size()+1), r+1, ( short )(7+monthList.size()+1) ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )(7+monthList.size()+2), r+1, ( short )(7+monthList.size()+2) ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )(7+monthList.size()+3), r+1, ( short )(7+monthList.size()+3) ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )(7+monthList.size()+4), r+1, ( short )(7+monthList.size()+4) ) );
				if(!type.equals(TroubleConstant.QUOTA)){
					this.curSheet.addMergedRegion( new Region(r , ( short )(7+monthList.size()+5), r+1, ( short )(7+monthList.size()+5) ) );
				}else{
				}
				this.curRow.setHeight( ( short )480 );
				r++;
				activeRow(r);
				setCellValue(0,"");
				setCellValue(1,"");
				setCellValue(2,"指标");
				setCellValue(3,"故障次数");
				setCellValue(4,"指标");
				setCellValue(5,"故障总时长");
				setCellValue(6,"指标");
				setCellValue(7,"故障平均时长");
				if(!type.equals(TroubleConstant.QUOTA)){
				for(int m = 8;m<13+monthList.size();m++){
					setCellValue(m,"");
				}
				}else{
					for(int m = 8;m<12+monthList.size();m++){
						setCellValue(m,"");
					}
				}
				r++;
				super.curStyle = excelstyle.defaultStyle(super.workbook);
				Set keySet=((Map)map.get("resultMap")).keySet();
				Iterator iter = keySet.iterator();
				while (iter.hasNext()) {
					String key=(String)iter.next();
					Map contractorMap=(Map)((Map)map.get("resultMap")).get(key);
					Map monthMap=(Map)contractorMap.get("monthMap");
					activeRow(r);
					setCellValue(0, r-2+"");
					if (key == null) {
						setCellValue(1, "");
					} else {
						setCellValue(1, key.toString());
					}
						setCellValue(2, contractorMap.get("standard_times").toString());
						setCellValue(3, contractorMap.get("trouble_times").toString());
						setCellValue(4, contractorMap.get("standard_time").toString());
						setCellValue(5, contractorMap.get("trouble_time").toString());
					double singletime = guide.getRtrTimeNormValue();
					setCellValue(6, singletime+"");
					Object intertime = contractorMap.get("trouble_time");
					Object troubletimes = contractorMap.get("trouble_times");
					double avgtime = 0;
					if(intertime!=null && troubletimes!=null){
						double time = Double.parseDouble(intertime.toString());
						double times = Double.parseDouble(troubletimes.toString());
						if(times>0){
							avgtime = div(time,times,2);
						}
					}
					setCellValue(7, avgtime+"");
					Set monthKeySet=monthMap.keySet();
					Iterator monthIter = monthKeySet.iterator();
					int k=0;
					while(monthIter!=null&&monthIter.hasNext()){
						String monthKey=(String)monthIter.next();
						if(monthMap.get(monthKey)!=null){
							DynaBean tmpBean=(DynaBean)monthMap.get(monthKey);
						setCellValue(8+k, tmpBean.get("maintenance_length").toString());
						}else{
							setCellValue(8+k, "0");
						}
						k++;
					}
						setCellValue(7+monthList.size()+1, contractorMap.get("standard_time").toString());
						setCellValue(7+monthList.size()+2, contractorMap.get("standard_times").toString());
						setCellValue(7+monthList.size()+3, contractorMap.get("dare_time").toString());
						setCellValue(7+monthList.size()+4, contractorMap.get("dare_times").toString());
					if(!type.equals(TroubleConstant.QUOTA)){
							setCellValue(7+monthList.size()+5, contractorMap.get("city_area_out_standard_number").toString());
					}
					r++;
				}
				}
			}
		}catch(Exception e){
			logger.error("导出故障指标失败:"+e.getMessage());
			e.printStackTrace();
		}
	}

	
	
	/**
	 * 导出故障指标
	 * @param list
	 * @param excelstyle
	 */
	public void doExportTroubleQuotas(List list,TroubleNormGuide guide,
			String month,ExcelStyle excelstyle) {
		double normtime = guide.getInterdictionTimeNormValue();
		double normtimes = guide.getInterdictionTimesNormValue();
		double daretime = guide.getInterdictionTimeDareValue();
		double daretimes = guide.getInterdictionTimesDareValue();
		String type = guide.getGuideType();
		String guidetype = "";
		if(type.equals(TroubleConstant.QUOTA)){
			guidetype = "一干故障指标";
		}else{
			guidetype = "城域网故障指标";
		}
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 0; // 行索引
		try{
			if(list != null && list.size()>0) {
				this.curStyle=rowLineStyle(this.workbook);
				activeRow(r);
				setCellValue(0,month+guidetype );
				setCellValue(1,"");
				setCellValue(2,"");
				setCellValue(3,"");
				setCellValue(4,"");
				setCellValue(5,"");
				setCellValue(6,"");
				setCellValue(7,"");
				setCellValue(8,"");
				setCellValue(9,"");
				setCellValue(10,"");
				setCellValue(11,"");
				setCellValue(12,"");
				setCellValue(13,"");
				setCellValue(14,"");
				if(!type.equals(TroubleConstant.QUOTA)){
					setCellValue(15,"");
				this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )15 ) );
				}else{
					this.curSheet.addMergedRegion( new Region(r , ( short )0, r, ( short )14 ) );
				}
				this.curRow.setHeight( ( short )480 );
				r++;
				activeRow(r);
				setCellValue(0,"序号");
				setCellValue(1,"代维");
				setCellValue(2,"千公里阻断次数(次)");
				setCellValue(3,"");
				setCellValue(4,"千公里阻断时长(小时)");
				setCellValue(5,"");
				setCellValue(6,"光缆每次故障平均历时(小时)");
				setCellValue(7,"");
				setCellValue(8,"维护长度(公里)");
				setCellValue(9,normtime+"小时/千公里(基准值)");
				setCellValue(10,normtimes+"次/千公里(基准值)");
				setCellValue(11,daretime+"小时/千公里(挑战值)");
				setCellValue(12,daretimes+"次/千公里(挑战值)");
				setCellValue(13,"抢修及时率");
				setCellValue(14,"反馈及时率");
				if(!type.equals(TroubleConstant.QUOTA)){
				setCellValue(15,"城域网单次抢修历时超出指标值的故障次数");
				}
				this.curSheet.addMergedRegion( new Region(r , ( short )0, r+1, ( short )0 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )1, r+1, ( short )1 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )2, r, ( short )3 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )4, r, ( short )5 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )6, r, ( short )7 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )8, r+1, ( short )8 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )9, r+1, ( short )9) );
				this.curSheet.addMergedRegion( new Region(r , ( short )10, r+1, ( short )10 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )11, r+1, ( short )11 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )12, r+1, ( short )12 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )13, r+1, ( short )13 ) );
				if(!type.equals(TroubleConstant.QUOTA)){
				this.curSheet.addMergedRegion( new Region(r , ( short )14, r+1, ( short )14 ) );
				this.curSheet.addMergedRegion( new Region(r , ( short )15, r+1, ( short )15 ) );
				}else{
					this.curSheet.addMergedRegion( new Region(r , ( short )14, r+1, ( short )14 ) );
				}
				this.curRow.setHeight( ( short )480 );
				r++;
				activeRow(r);
				setCellValue(0,"");
				setCellValue(1,"");
				setCellValue(2,"指标");
				setCellValue(3,"故障次数");
				setCellValue(4,"指标");
				setCellValue(5,"故障总时长");
				setCellValue(6,"指标");
				setCellValue(7,"故障平均时长");
				setCellValue(8,"");
				setCellValue(9,"");
				setCellValue(10,"");
				setCellValue(11,"");
				setCellValue(12,"");
				setCellValue(13,"");
				setCellValue(14,"");
				if(!type.equals(TroubleConstant.QUOTA)){
				setCellValue(15,"");
				}
				r++;
				super.curStyle = excelstyle.defaultStyle(super.workbook);
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					record = (DynaBean) iter.next();
					activeRow(r);
					setCellValue(0, r-2+"");
					if (record.get("contractorname") == null) {
						setCellValue(1, "");
					} else {
						setCellValue(1, record.get("contractorname").toString());
					}
					if (record.get("standard_times") == null) {
						setCellValue(2, "");
					} else {
						setCellValue(2, record.get("standard_times").toString());
					}
					if (record.get("trouble_times") == null) {
						setCellValue(3, "");
					} else {
						setCellValue(3, record.get("trouble_times").toString());
					}
					if (record.get("standard_time") == null) {
						setCellValue(4, "");
					} else {
						setCellValue(4, record.get("standard_time").toString());
					}
					if (record.get("interdiction_time") == null) {
						setCellValue(5, "");
					} else {
						setCellValue(5, record.get("interdiction_time").toString());
					}
					double singletime = guide.getRtrTimeNormValue();
					setCellValue(6, singletime+"");
					Object intertime = record.get("interdiction_time");
					Object troubletimes = record.get("trouble_times");
					double avgtime = 0;
					if(intertime!=null && troubletimes!=null){
						double time = Double.parseDouble(intertime.toString());
						double times = Double.parseDouble(troubletimes.toString());
						if(times>0){
							avgtime = div(time,times,2);
						}
					}
					setCellValue(7, avgtime+"");
					if (record.get("maintenance_length") == null) {
						setCellValue(8, "");
					} else {
						setCellValue(8, record.get("maintenance_length").toString());
					}
					if (record.get("standard_time") == null) {
						setCellValue(9, "");
					} else {
						setCellValue(9, record.get("standard_time").toString());
					}
					if (record.get("standard_times") == null) {
						setCellValue(10, "");
					} else {
						setCellValue(10, record.get("standard_times").toString());
					}
					if (record.get("dare_time") == null) {
						setCellValue(11, "");
					} else {
						setCellValue(11, record.get("dare_time").toString());
					}
					if (record.get("dare_times") == null) {
						setCellValue(12, "");
					} else {
						setCellValue(12, record.get("dare_times").toString());
					}
					if (record.get("rtr_in_time") == null) {
						setCellValue(13, "");
					} else {
						setCellValue(13, record.get("rtr_in_time").toString());
					}
					if (record.get("feedback_in_time") == null) {
						setCellValue(14, "");
					} else {
						setCellValue(14, record.get("feedback_in_time").toString());
					}
					if(!type.equals(TroubleConstant.QUOTA)){
					if (record.get("city_area_out_standard_number") == null) {
						setCellValue(15, "");
					} else {
						setCellValue(15, record.get("city_area_out_standard_number").toString());
					}
					}
					r++;
				}
			}
		}catch(Exception e){
			logger.error("导出故障指标失败:"+e.getMessage());
			e.printStackTrace();
		}
	}

	
	
	public double div(double d1,double d2,int scale){ 
		BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
		BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
		return bd1.divide 
		(bd2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
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
