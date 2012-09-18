package com.cabletech.station.template;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;

/**
 * �м�վ������ͨ��ģ�������
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
     * ִ�е�������
     * 
     * @param list
     *            List Ҫ�������б���Ϣ
     * @param excelstyle
     *            ExcelStyle ������Excel��ʽ
     */
    public abstract void doExport(List list, ExcelStyle excelstyle);

}
