package com.cabletech.linepatrol.resource.template;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.linepatrol.resource.model.Pipe;

/**
 * 导出管道
 * 
 * @author 杨隽 2011-08-22
 * 
 */
public class PipeTemplate extends Template {
    private static Logger logger = Logger.getLogger("Template");

    public PipeTemplate(String urlPath) {
        super(urlPath);
    }

    /**
     * 导出管道信息
     * 
     * @param list
     * @param excelstyle
     */
    public void doExportPipes(List list, Map<String, String> contractor,
            Map<String, String> pipeType, Map<String, String> terminalAddress,
            Map<String, String> propertyRight, ExcelStyle excelstyle) {
        activeSheet(0);
        Pipe pipe;
        super.curStyle = excelstyle.defaultStyle(super.workbook);
        int r = 2; // 行索引
        try {
            if (list != null && list.size() > 0) {
                Iterator iter = list.iterator();
                while (iter.hasNext()) {
                    pipe = (Pipe) iter.next();
                    activeRow(r);
                    if (pipe == null) {
                        setCellValue(0, "");
                    } else {
                        setCellValue(0, pipe.getWorkName());
                    }
                    if (pipe == null) {
                        setCellValue(1, "");
                    } else {
                        setCellValue(1, pipe.getPipeAddress());
                    }
                    if (pipe == null || pipe.getPipeLengthChannel() == null) {
                        setCellValue(2, "");
                    } else {
                        setCellValue(2, pipe.getPipeLengthChannel().toString());
                    }
                    if (pipe == null || pipe.getPipeLengthHole() == null) {
                        setCellValue(3, "");
                    } else {
                        setCellValue(3, pipe.getPipeLengthHole().toString());
                    }
                    if (pipe == null || pipe.getMobileScareChannel() == null) {
                        setCellValue(4, "");
                    } else {
                        setCellValue(4, pipe.getMobileScareChannel().toString());
                    }
                    if (pipe == null || pipe.getMobileScareHole() == null) {
                        setCellValue(5, "");
                    } else {
                        setCellValue(5, pipe.getMobileScareHole().toString());
                    }
                    if (pipe == null) {
                        setCellValue(6, "");
                    } else {
                        String onePipeType = pipe.getPipeType();
                        String m = "";
                        if (onePipeType != null && onePipeType.length() > 0) {
                            String[] laytypes = onePipeType.split(";");
                            for (int i = 0; laytypes != null && i < laytypes.length; i++) {
                                if (pipeType.get(laytypes[i]) != null
                                        && !"".equals(pipeType.get(laytypes[i]))) {
                                    m += "  " + pipeType.get(laytypes[i]);
                                }
                            }
                        } else {
                            m = "";
                        }
                        setCellValue(6, m);
                    }
                    if (pipe == null || pipe.getRouteRes() == null) {
                        setCellValue(7, "");
                    } else {
                        String routeRes = propertyRight.get(pipe.getRouteRes());
                        setCellValue(7, routeRes);
                    }
                    if (pipe == null || pipe.getFinishTime() == null) {
                        setCellValue(8, "");
                    } else {
                        Date finishDate = pipe.getFinishTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        String time = sdf.format(finishDate);
                        setCellValue(8, time);
                    }
                    if (pipe == null || pipe.getMaintenanceId() == null) {
                        setCellValue(9, "");
                    } else {
                        String conName = contractor.get(pipe.getMaintenanceId());
                        setCellValue(9, conName);
                    }
                    r++;
                }
            }
        } catch (Exception e) {
            logger.error("导出管道失败:", e);
            e.getStackTrace();
        }
    }

}
