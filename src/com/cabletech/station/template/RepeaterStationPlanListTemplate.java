package com.cabletech.station.template;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.station.services.ExecuteCode;
import com.cabletech.station.services.StationConstant;

/**
 * 中继站计划列表信息导出模板
 * 
 * @author yangjun
 * 
 */
public class RepeaterStationPlanListTemplate extends BasicTemplate {
    private Map stateMap;

    private Map resultMap;

    public RepeaterStationPlanListTemplate(String urlPath) {
        super(urlPath);
        stateMap = new HashMap();
        stateMap.put(StationConstant.WAIT_SUBMIT_STATE, "未提交");
        stateMap.put(StationConstant.WAIT_AUDITING_STATE, "待审核");
        stateMap.put(StationConstant.NOT_PASSED_STATE, "审核不通过");
        stateMap.put(StationConstant.PASSED_STATE, "正在填写");
        stateMap.put(StationConstant.FINISHED_STATE, "填写完毕");
        resultMap = new HashMap();
        resultMap.put(ExecuteCode.NOT_PASSED_ACTION, "不通过");
        resultMap.put(ExecuteCode.PASSED_ACTION, "通过");
    }

    /**
     * 执行导出动作
     * 
     * @param list
     *            List 要导出的列表信息
     * @param excelstyle
     *            ExcelStyle 导出的Excel样式
     */
    public void doExport(List list, ExcelStyle excelstyle) {
        DynaBean record;
        activeSheet(0);
        super.curStyle = excelstyle.defaultStyle(super.workbook);
        int r = 2; // 行索引
        if (list != null) {
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                record = (DynaBean) iter.next();
                activeRow(r);
                if (record.get("begin_date_dis") == null) {
                    setCellValue(0, "");
                } else {
                    setCellValue(0, record.get("begin_date_dis").toString());
                }
                if (record.get("end_date_dis") == null) {
                    setCellValue(1, "");
                } else {
                    setCellValue(1, record.get("end_date_dis").toString());
                }
                if (record.get("regionname") == null) {
                    setCellValue(2, "");
                } else {
                    setCellValue(2, record.get("regionname").toString());
                }
                if (record.get("plan_name") == null) {
                    setCellValue(3, "");
                } else {
                    setCellValue(3, record.get("plan_name").toString());
                }
                if (record.get("station_name_dis") == null) {
                    setCellValue(4, "");
                } else {
                    setCellValue(4, record.get("station_name_dis").toString());
                }
                if (record.get("plan_state") == null) {
                    setCellValue(5, "");
                } else {
                    setCellValue(5, (String) stateMap.get(record.get("plan_state")));
                }
                if (record.get("username") == null) {
                    setCellValue(6, "");
                } else {
                    setCellValue(6, record.get("username").toString());
                }
                if (record.get("validate_result") == null) {
                    setCellValue(7, "");
                } else {
                    setCellValue(7, (String) resultMap.get(record.get("validate_result")));
                }
                if (record.get("validate_remark") == null) {
                    setCellValue(8, "");
                } else {
                    setCellValue(8, record.get("validate_remark").toString());
                }
                r++;
            }
        }
    }

}
