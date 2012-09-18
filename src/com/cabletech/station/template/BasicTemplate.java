package com.cabletech.station.template;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;

/**
 * 中继站导出的通用模板抽象类
 * 
 * @author yangjun
 * 
 */
public abstract class BasicTemplate extends Template {
    protected static Logger logger = Logger.getLogger("Template");

    public BasicTemplate(String urlPath) {
        super(urlPath);
    }

    /**
     * 执行导出动作
     * 
     * @param list
     *            List 要导出的列表信息
     * @param excelstyle
     *            ExcelStyle 导出的Excel样式
     */
    public abstract void doExport(List list, ExcelStyle excelstyle);

}
