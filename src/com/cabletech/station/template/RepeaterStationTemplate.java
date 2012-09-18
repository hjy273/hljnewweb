package com.cabletech.station.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;

import com.cabletech.commons.exceltemplates.ExcelStyle;

/**
 * �м�վ������Ϣ����ģ��
 * 
 * @author yangjun
 * 
 */
public class RepeaterStationTemplate extends BasicTemplate {
    private static final int LEN = 9;

    private static final int PREX = 2;

    private static final int SUPX = 7;

    private Map matchedMap;

    private static final String[] KEYS = new String[] { "station_name", "regionname",
            "is_ground_station", "has_electricity", "connect_electricity", "has_air_condition",
            "connect_air_condition", "connect_wind_power_generate", "station_remark", "", "" };

    public RepeaterStationTemplate(String urlPath) {
        super(urlPath);
        matchedMap = new HashMap();
        matchedMap.put("0", "��");
        matchedMap.put("1", "��");
    }

    /**
     * ִ�е�������
     * 
     * @param list
     *            List Ҫ�������б���Ϣ
     * @param excelstyle
     *            ExcelStyle ������Excel��ʽ
     */
    public void doExport(List list, ExcelStyle excelstyle) {
        DynaBean record;
        activeSheet(0);
        super.curStyle = excelstyle.defaultStyle(super.workbook);
        int r = 1; // ������
        if (list != null && list.size() > 0) {
            record = (DynaBean) list.get(0);
            for (int i = 0; i < LEN; i++) {
                super.activeRow(r + i);
                if (record.get(KEYS[i]) == null) {
                    setCellValue(1, "");
                } else {
                    if (i < PREX || i > SUPX) {
                        setCellValue(1, record.get(KEYS[i]).toString());
                    } else {
                        setCellValue(1, (String) matchedMap.get(record.get(KEYS[i])));
                    }
                }
            }
        }
    }
}
