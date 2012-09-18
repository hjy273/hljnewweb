package com.cabletech.linepatrol.appraise.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.appraise.module.AppraiseContent;
import com.cabletech.linepatrol.appraise.module.AppraiseResult;
import com.cabletech.linepatrol.appraise.module.AppraiseRule;
import com.cabletech.linepatrol.appraise.module.AppraiseRuleResult;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;

public class AppraiseResultTemplate extends AppraiseTemplate {
	private Map ruleResultMap=new HashMap();
	private Map ruleMarkMap=new HashMap();
	private AppraiseResult appraiseResult;
	public AppraiseResultTemplate(String urlPath) {
		super(urlPath);
	}
	public void doExportAppriaseTable(AppraiseTable table, ExcelStyle excelStyle,AppraiseResult appResult) {
		ruleResultMap=getRuleResultMap(appResult);
		ruleMarkMap=getRuleMarkMap(appResult);
		appraiseResult=appResult;
		activeSheet(0);
		super.curStyle = excelStyle.tenFourLineCenter(super.workbook);
		setTitleRow(table, excelStyle,appraiseResult);
		setTimeRow(table, excelStyle);
		int r = 5;
		int tableSize = table.getAppraiseItems().size();
		r = setAppraiseItem(table, r, tableSize);
		setSumRow(table, r);
	}

	/**
	 * 添加模板名称
	 * @param table
	 * @param excelStyle
	 */
	void setTitleRow(AppraiseTable table, ExcelStyle excelStyle,AppraiseResult appraiseResult) {
		HSSFCellStyle titleStyle = excelStyle.tenFourLineCenter(super.workbook);
		setFont(titleStyle, 16);
		activeRow(0);
		String title="";
		if(appraiseResult.getAppraiseTime()!=null){
			if(table.getType().equals(AppraiseConstant.APPRAISE_MONTH)){
				title+=DateUtil.DateToString(appraiseResult.getAppraiseTime(), "yyyy-MM")+"月 ";
			}else{
				title+=DateUtil.DateToString(appraiseResult.getAppraiseTime(), "yyyy")+"年 ";
			}
		}
		if(StringUtils.isNotBlank(appraiseResult.getContractNO())){
			title+=appraiseResult.getContractNO()+"包 ";
		}
		title+=table.getTableName();
		setCellValue(0, title, titleStyle);
	}

	/**
	 * 添加评分细则
	 * @param r
	 * @param content
	 * @return
	 */
	int setAppraiseRule(int r, AppraiseContent content) {
		for (AppraiseRule appraiseRule : content.getAppraiseRules()) {
			activeRow(r);
			setCellValue(4, appraiseRule.getRuleDescription());
			setCellValue(5, String.valueOf(appraiseRule.getWeight()));
			setCellValue(6, ruleResultMap.get(appraiseRule.getId()).toString());
			setCellValue(7, ruleMarkMap.get(appraiseRule.getId()).toString());
			r++;
		}
		return r;
	}

	/**
	 * 添加合计一行
	 * @param table
	 * @param r
	 */
	void setSumRow(AppraiseTable table, int r) {
		activeRow(r);
		setCellValue(0, "合计");
		setCellValue(1, String.valueOf(table.getAllWeight().get("tableWeight")));
		setCellValue(2, "");
		setCellValue(3, String.valueOf(table.getAllWeight().get("itemWeight")));
		setCellValue(4, "");
		setCellValue(5, String.valueOf(table.getAllWeight().get("contentWeight")));
		setCellValue(6, String.valueOf(appraiseResult.getResult()));
		setCellValue(7, "");
	}

	private Map getRuleResultMap(AppraiseResult appraiseResult) {
		List<AppraiseRuleResult> appraiseRuleResults=appraiseResult.getAppraiseRuleResults();
		Map<String,Float> rulesMap=new HashMap<String, Float>();
		for(AppraiseRuleResult appraiseRuleResult:appraiseRuleResults){
			rulesMap.put(appraiseRuleResult.getRuleId(), appraiseRuleResult.getResult());
		}
		return rulesMap;
	}
	private Map getRuleMarkMap(AppraiseResult appResult) {
		List<AppraiseRuleResult> appraiseRuleResults=appResult.getAppraiseRuleResults();
		Map<String,String> rulesMap=new HashMap<String, String>();
		for(AppraiseRuleResult appraiseRuleResult:appraiseRuleResults){
			rulesMap.put(appraiseRuleResult.getRuleId(), appraiseRuleResult.getRemark());
		}
		return rulesMap;
	}
}
