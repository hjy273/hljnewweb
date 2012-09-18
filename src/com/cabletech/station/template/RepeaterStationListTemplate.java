package com.cabletech.station.template;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;

import com.cabletech.commons.exceltemplates.ExcelStyle;

/**
 * 中继站基本信息列表导出模板
 * 
 * @author yangjun
 * 
 */
public class RepeaterStationListTemplate extends BasicTemplate {
    private Map matchedMap;

    public RepeaterStationListTemplate(String urlPath) {
        super(urlPath);
        matchedMap = new HashMap();
        matchedMap.put("0", "是");
        matchedMap.put("1", "否");
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
                if (record.get("regionname") == null) {
                    setCellValue(0, "");
                } else {
                    setCellValue(0, record.get("regionname").toString());
                }
                if (record.get("station_name") == null) {
                    setCellValue(1, "");
                } else {
                    setCellValue(1, record.get("station_name").toString());
                }
                if (record.get("is_ground_station") == null) {
                    setCellValue(2, "");
                } else {
                    setCellValue(2, (String) matchedMap.get(record.get("is_ground_station")));
                }
                if (record.get("has_electricity") == null) {
                    setCellValue(3, "");
                } else {
                    setCellValue(3, (String) matchedMap.get(record.get("has_electricity")));
                }
                if (record.get("connect_electricity") == null) {
                    setCellValue(4, "");
                } else {
                    setCellValue(4, (String) matchedMap.get(record.get("connect_electricity")));
                }
                if (record.get("has_air_condition") == null) {
                    setCellValue(5, "");
                } else {
                    setCellValue(5, (String) matchedMap.get(record.get("has_air_condition")));
                }
                if (record.get("connect_air_condition") == null) {
                    setCellValue(6, "");
                } else {
                    setCellValue(6, (String) matchedMap.get(record.get("connect_air_condition")));
                }
                if (record.get("connect_wind_power_generate") == null) {
                    setCellValue(7, "");
                } else {
                    setCellValue(7, (String) matchedMap.get(record.get("connect_wind_power_generate")));
                }
                if (record.get("station_remark") == null) {
                    setCellValue(8, "");
                } else {
                    setCellValue(8, record.get("station_remark").toString());
                }
                r++;
            }
        }
    }

}
