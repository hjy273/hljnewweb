package com.cabletech.linepatrol.resource.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.resource.template.PipeTemplate;
import com.cabletech.linepatrol.resource.template.RepeaterSectionTemplate;
import com.cabletech.linepatrol.trouble.module.TroubleNormGuide;
import com.cabletech.linepatrol.trouble.templates.TroubleTmplate;

public class ResourceExportBO {

    private static String CONTENT_TYPE = "application/vnd.ms-excel";

    /**
     * 导出光缆信息
     * 
     * @param list
     *            List
     * @param response
     *            HttpServletResponse
     * @throws IOException
     */
    @Transactional(readOnly = true)
    public void exportRepeaters(List list, Map<String, String> contractor,
            Map<String, String> places, Map<String, String> sections,
            Map<String, String> cabletype, Map<String, String> layingmethod,
            HttpServletResponse response) throws ServiceException {
        try {
            initResponse(response, "光缆信息.xls");
            OutputStream out = response.getOutputStream();
            String urlPath = ReportConfig.getInstance().getUrlPath("report.repeatsection");
            RepeaterSectionTemplate template = new RepeaterSectionTemplate(urlPath);
            ExcelStyle excelstyle = new ExcelStyle(urlPath);
            template.doExportRepeaters(list, contractor, places, sections, cabletype, layingmethod,
                    excelstyle);
            template.write(out);
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    /**
     * 导出管道信息
     * 
     * @param list
     * @param contractor
     * @param pipeType
     * @param terminalAddress
     * @param propertyRight
     * @param response
     */
    @Transactional(readOnly = true)
    public void exportPipes(List list, Map<String, String> contractor,
            Map<String, String> pipeType, Map<String, String> terminalAddress,
            Map<String, String> propertyRight, HttpServletResponse response) {
        try {
            initResponse(response, "管道信息.xls");
            OutputStream out = response.getOutputStream();
            String urlPath = ReportConfig.getInstance().getUrlPath("report.pipeline");
            PipeTemplate template = new PipeTemplate(urlPath);
            ExcelStyle excelstyle = new ExcelStyle(urlPath);
            template.doExportPipes(list, contractor, pipeType, terminalAddress, propertyRight,
                    excelstyle);
            template.write(out);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * 导出查询的故障指标信息
     * 
     * @param list
     *            List
     * @param response
     *            HttpServletResponse
     * @throws IOException
     */
    @Transactional(readOnly = true)
    public void exportTroubleQuota(List list, TroubleNormGuide guide, String month,
            HttpServletResponse response) throws ServiceException {
        try {
            initResponse(response, "故障月指标.xls");
            OutputStream out = response.getOutputStream();
            String urlPath = ReportConfig.getInstance().getUrlPath("report.troublequota");
            TroubleTmplate template = new TroubleTmplate(urlPath);
            ExcelStyle excelstyle = new ExcelStyle(urlPath);
            template.doExportTroubleQuotas(list, guide, month, excelstyle);
            template.write(out);
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    /**
     * 导出故障年计划
     * 
     * @param list
     * @param guide
     * @param year
     * @param response
     * @throws ServiceException
     */
    @Transactional(readOnly = true)
    public void exportYearTroubleQuota(List list, TroubleNormGuide guide, String year,
            HttpServletResponse response) throws ServiceException {
        try {
            initResponse(response, "故障年指标.xls");
            OutputStream out = response.getOutputStream();
            String urlPath = ReportConfig.getInstance().getUrlPath("report.troubleyearquota");
            TroubleTmplate template = new TroubleTmplate(urlPath);
            ExcelStyle excelstyle = new ExcelStyle(urlPath);
            template.doExportYearTroubleQuotas(list, guide, year, excelstyle);
            template.write(out);
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    /**
     * 导出故障年计划
     * 
     * @param list
     * @param guide
     * @param year
     * @param response
     * @throws ServiceException
     */
    @Transactional(readOnly = true)
    public void exportYearTroubleQuota(Map map, TroubleNormGuide guide, String year,
            HttpServletResponse response) throws ServiceException {
        try {
            initResponse(response, "故障年指标.xls");
            OutputStream out = response.getOutputStream();
            String urlPath = ReportConfig.getInstance().getUrlPath("report.troubleyearquota");
            TroubleTmplate template = new TroubleTmplate(urlPath);
            ExcelStyle excelstyle = new ExcelStyle(urlPath);
            template.doExportYearTroubleQuotas(map, guide, year, excelstyle);
            template.write(out);
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    /**
     * 导出故障年计划
     * 
     * @param list
     * @param guide
     * @param year
     * @param response
     * @throws ServiceException
     */
    @Transactional(readOnly = true)
    public void exportTimeAreaTroubleQuota(Map map, TroubleNormGuide guide, String beginTime,
            String endTime, HttpServletResponse response) throws ServiceException {
        try {
            initResponse(response, "故障指标列表.xls");
            OutputStream out = response.getOutputStream();
            String urlPath = ReportConfig.getInstance().getUrlPath("report.troubleyearquota");
            TroubleTmplate template = new TroubleTmplate(urlPath);
            ExcelStyle excelstyle = new ExcelStyle(urlPath);
            template.doExportTimeAreaTroubleQuotas(map, guide, beginTime, endTime, excelstyle);
            template.write(out);
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    /**
     * 创建输出流的头
     * 
     * @param response
     *            HttpServletResponse
     * @param fileName
     *            String
     * @throws UnsupportedEncodingException
     */
    @Transactional(readOnly = true)
    private void initResponse(HttpServletResponse response, String fileName)
            throws UnsupportedEncodingException {
        response.reset();
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String(fileName.getBytes(), "iso-8859-1"));
    }
}
