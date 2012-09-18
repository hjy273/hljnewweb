package com.cabletech.linepatrol.appraise.tags;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.module.AppraiseContent;
import com.cabletech.linepatrol.appraise.module.AppraiseItem;
import com.cabletech.linepatrol.appraise.module.AppraiseRule;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyBO;

/**
 * 日常考核标签 实现移动对代维单位的日常考核，功能实现包括填写考核和查看考核信息
 * 当displayType为input时，为填写日常考核。为view时，为查看日常考核信息
 * 此时需要传入的参数有：contractorId(代维单位编号)、businessModule(业务模型)、businessId(业务模型编号)、contractNO(标包号)
 * 填写日常考核时，contractorId、businessModule、businessId为必填项，其他为非必填项
 * 查看日常考核时，businessModule、businessId为比填项，其他为非必填项
 * 
 * @author liusq
 * 
 */
public abstract class AppraiseDailyTag extends BodyTagSupport {

    private static final long serialVersionUID = 6941722957074015695L;

    Logger logger = Logger.getLogger(AppraiseDailyTag.class);

    public String INPUT_DISPLAY_TYPE = "input";

    public String VIEW_DISPLAY_TYPE = "view";

    String contractorId; // 代维单位

    String businessModule; // 业务模块

    String businessId; // 业务编号

    String contractNo; // 标包号

    String appraiseType;

    String appriaseName;

    String id;// 日常考核编号

    ApplicationContext applicationContext;

    List<AppraiseTable> appraiseTables;

    AppraiseTable appraiseTable; // 考核内容

    String[] contractNoArray; // 合同号

    String contractorName = "";// 代维单位名称

    UserInfo userInfo;// 用户信息

    String tableId;// 月考核编号

    // 定义各种样式
    String tableClass = "tabout";// 表格样式

    String trClass = "trcolor";// 行样式

    String tdClass = "";// 列样式

    String tableAlign = "center";// table排列

    String tableStyle = "";// 设置表格的Style样式

    String flagName;

    // 标签的显示方式
    String displayType = INPUT_DISPLAY_TYPE;

    @SuppressWarnings("static-access")
    public int doEndTag() throws JspException {
        init();
        StringBuilder html = new StringBuilder();
        // 考核表保存状态标记
        for (AppraiseTable aTable : appraiseTables) {
            trClass = "trcolor";
            appraiseTable = aTable;
            tableId = appraiseTable.getId();
            flagName = appraiseType + tableId;
            html.append("<form action=\"\" id=\"form\">");
            // 隐藏域保存参数值
            html.append(savePageParameters());

            // 引入外部的js文件
            html.append(inportExternalFile());

            if (appraiseTable != null && StringUtils.isNotBlank(appraiseTable.getId())) {
                // 创建一个表格
                getTable(html);
            }
            html.append("</form><br />");
        }
        try {
            super.pageContext.getOut().print(html.toString());
        } catch (IOException e) {
            logger.error("渲染标签出错：" + e.getMessage());
        }
        return super.EVAL_PAGE;
    }

    /**
     * 获得考核表
     * 
     * @param html
     */
    private void getTable(StringBuilder html) {
        html.append("<table class=\"");
        html.append(tableClass);
        html.append("\" style=\"");
        html.append(tableStyle);
        html.append("\" align=\"");
        html.append(tableAlign);
        html.append("\">");
        int size = 4;
        if (appraiseType.equals(AppraiseConstant.TYPE_YEAREND)) {
            size = 5;
        }
        if(!appraiseType.equals(AppraiseConstant.TYPE_YEAREND)){
        html
                .append("<tr class=\"trcolorgray\"><td colspan=\""
                        + size
                        + "\"><div style=\"display: inline ;font-size:13pt;font-weight:bold\">"
                        + appriaseName
                        + "</div>&nbsp;<div style=\"display: inline ;float:right;text-align: right;\"><a onclick=\"showOrHidde(\'"
                        + flagName + "\');\" style=\"cursor: pointer;\">显示/隐藏</a></div></td></tr>");
        }
        // 输出日考核头一行信息
        html.append(dailyAppriaseGeneral());

        // 输入月考核的头部信息
        html.append(exportTableHeader());

        List<AppraiseItem> appraiseItemList = appraiseTable.getAppraiseItems();
        for (int itemNum = 0; itemNum < appraiseItemList.size(); itemNum++) {
            outputTable(html, appraiseItemList, itemNum);
        }
        if (displayType.equals(AppraiseConstant.FLAG_INPUT)) {
            // html.append(getTrFront()+"<td style=\"text-align:right;\"
            // colspan=\"4\"><input type=\"button\" class=\"button\"
            // value=\"保存\" onclick=\"javascript:saveForm(\'"+ appraiseType +
            // "\',\'"+ tableId + "\')\"></td></tr>");
            // html.append("<div
            // style=\"float:right;margin-right:103px;\"><input type=\"button\"
            // class=\"button\" value=\"保存\" onclick=\"javascript:saveForm(\'"+
            // appraiseType + "\',\'"+ tableId + "\')\"></div><br />");
        }
        html.append("</tbody>");
        html.append("</table>");
    }

    /**
     * 将表格输出
     * 
     * @param html
     * @param appraiseItemList
     * @param itemNum
     */
    private void outputTable(StringBuilder html, List<AppraiseItem> appraiseItemList, int itemNum) {
        AppraiseItem appraiseItem = appraiseItemList.get(itemNum);

        // 输入第一列信息
        html.append(exportFirstColumnInfo(appraiseItem));

        // 获得Content列表
        List<AppraiseContent> appraiseContentList = appraiseItem.getAppraiseContents();
        for (int contentNum = 0; contentNum < appraiseContentList.size(); contentNum++) {
            AppraiseContent appraiseContent = appraiseContentList.get(contentNum);
            // 输出第二列信息
            html.append(exportSecondColumnInfo(contentNum, appraiseContent));

            // 输出第三、四列信息
            html.append(exportThirdFourInfo(appraiseContent));
        }
    }

    /**
     * 输出第二列的月考核内容信息
     * 
     * @param contentNum
     *            第几条
     * @param appraiseContent
     *            月考核内容实体
     * @return
     */
    private String exportSecondColumnInfo(int contentNum, AppraiseContent appraiseContent) {
        StringBuffer html = new StringBuffer("");
        if (contentNum != 0) {
            html.append(getTrFront());
        }
        html.append("<td valign=\"middle\" class=\"");
        html.append(tdClass);
        html.append("\" rowspan=\"");
        html.append(appraiseContent.getAppraiseRules().size());
        html.append("\">");
        html.append(appraiseContent.getContentDescription());
        html.append("(");
        html.append(appraiseContent.getWeight());
        html.append(")");
        html.append("</td>");
        return html.toString();
    }

    /**
     * 输出表格第一列的月考核项目信息
     * 
     * @param appraiseItem
     *            月考核项目对象
     * @return
     */
    private String exportFirstColumnInfo(AppraiseItem appraiseItem) {
        StringBuffer html = new StringBuffer("");
        if (trClass.equals("trcolor")) {
            trClass = "trwhite";
        } else {
            trClass = "trcolor";
        }
        html.append(getTrFront());
        html.append("<td valign=\"middle\" class=\"");
        html.append(tdClass);
        html.append("\" rowspan=\"");
        html.append("" + appraiseItem.getItemSize());// 内容所占行数
        html.append("\">");
        html.append(appraiseItem.getItemName());
        html.append("(");
        html.append(appraiseItem.getWeight());
        html.append(")");
        html.append("</td>");
        return html.toString();
    }

    /**
     * 输入月考核的头部静态信息
     */
    String exportTableHeader() {
        StringBuffer html = new StringBuffer("");
        html.append(getTrFront());
        html.append("<td style=\"font-weight: bold;text-align: center;\">项目</td>");
        html.append("<td style=\"font-weight: bold;text-align: center;\">内容</td>");
        html.append("<td style=\"font-weight: bold;text-align: center;\">细则</td>");
        html.append("<td style=\"font-weight: bold;text-align: center;\">扣分说明（请选择细则后输入）</td>");
        html.append("</tr>");
        return html.toString();
    }

    /**
     * 引入外部的js文件
     * 
     * @return
     */
    private String inportExternalFile() {
        StringBuffer html = new StringBuffer("");
        String contextPath = super.pageContext.getServletContext().getContextPath();
        html.append("<script type=\"text/javascript\" ");
        html.append(" src=\"");
        html.append(contextPath);
        html.append("/linepatrol/js/appraise_daily.js\">");
        html.append("</script>");
        return html.toString();
    }

    /**
     * 保持页面需要的参数值
     * 
     * @return
     */
    private String savePageParameters() {
        StringBuffer html = new StringBuffer("");
        html.append("<input type=\"hidden\" id=\"" + flagName + "ruleIds\" name=\"" + flagName
                + "ruleIds\" />");
        html.append("<input type=\"hidden\" id=\"" + flagName + "remarks\" name=\"" + flagName
                + "remarks\" />");
        html.append("<input type=\"hidden\" name=\"" + flagName + "tableId\" id=\"" + flagName
                + "tableId\" value=\"");
        html.append(tableId);
        html.append("\" />");
        html.append("<input type=\"hidden\" name=\"" + flagName + "businessModule\" id=\""
                + flagName + "businessModule\" value=\"");
        html.append(appraiseType + "_" + businessModule);
        html.append("\" />");
        html.append("<input type=\"hidden\" name=\"" + flagName + "businessId\" id=\"" + flagName
                + "businessId\" value=\"");
        html.append(businessId);
        html.append("\" />");
        html.append("<input type=\"hidden\" name=\"" + flagName + "appraiser\" id=\"" + flagName
                + "appraiser\" value=\"");
        html.append(userInfo.getUserID());
        html.append("\" />");
        return html.toString();
    }

    /**
     * 输出表格的头一行信息：包括代维公司、中标合同号、考核人、考核日期
     */
    private String dailyAppriaseGeneral() {
        StringBuffer html = new StringBuffer("");
        String appraiseDate = DateUtil.DateToString(new Date(), "yyyy-MM-dd");
        html.append("<tbody id=\"" + flagName + "\" style=\"display: block;\">");
        html.append("<tr class=\"trwhite\">");
        html.append("<td class=\"");
        html.append(tdClass);
        html.append("\" width=\"25%\">代维公司 : ");
        html.append("<input type=\"hidden\" name=\"" + flagName + "contractorId\" id=\"" + flagName
                + "contractorId\" value=\"");
        html.append(contractorId);
        html.append("\"/>");
        html.append(addUnderline(contractorName));
        html.append("</td>");
        html.append("<td width=\"25%\">考核人 : ");
        if (StringUtils.equals("input", displayType)) {
            html.append(addUnderline(userInfo.getUserName()));
        } else {
            html.append(addUnderline(appraiseTable.getCreater()));
        }
        addContractNo(html);
        html.append("</td><td width=\"25%\">考核日期 : ");
        if (StringUtils.equals("input", displayType)) {
            html.append(addUnderline(appraiseDate));
        } else {
            Date appraiseDateLocal = appraiseTable.getCreateDate();
            html.append(addUnderline(DateUtil.DateToString(appraiseDateLocal, "yyyy-MM-dd")));
        }
        html.append("</td>");
        html.append("</tr>");
        return html.toString();
    }

    /**
     * 获得子项的输出HTML，第三列和第四列信息
     * 
     * @param appraiseContent
     * @return
     */
    String exportThirdFourInfo(AppraiseContent appraiseContent) {
        StringBuffer html = new StringBuffer();
        // 获得具体想列表
        List<AppraiseRule> appraiseRuleList = appraiseContent.getAppraiseRules();
        for (int i = 0; i < appraiseRuleList.size(); i++) {
            AppraiseRule appraiseRule = appraiseRuleList.get(i);
            if (i != 0) {
                html.append(getTrFront());
            }
            html.append(getTdFront());
            exportThirdInfo(html, appraiseRule);
            exportFourInfo(html, appraiseRule);
            html.append("</tr>");
        }
        return html.toString();
    }

    /**
     * 获得子项的输出HTML，第三列信息
     * 
     * @param html
     * @param appraiseRule
     */
    void exportThirdInfo(StringBuffer html, AppraiseRule appraiseRule) {
        // 当为输入的时候显示checkBox
        if (StringUtils.equals("input", displayType)) {
            html.append("<input type=\"checkbox\" name=\"" + flagName + "ruleCheckbox\" id=\""
                    + appraiseRule.getId() + "ruleCheckbox\" onclick=\"setTextWriteState(\'"
                    + appraiseRule.getId() + "\','',\'" + tableId + "\',\'" + appraiseType
                    + "\')\" value=\"");
            html.append(appraiseRule.getId());
            html.append("\"/>");
        }
        html.append(appraiseRule.getRuleDescription());
        html.append("(");
        html.append(appraiseRule.getWeight());
        html.append(")");
        html.append("</td>");
    }

    /**
     * 获得子项的输出HTML，第四列信息
     * 
     * @param html
     * @param appraiseRule
     */
    void exportFourInfo(StringBuffer html, AppraiseRule appraiseRule) {
        if (StringUtils.equals("input", displayType)) {
            html.append(getTdFront());
            html.append("<input type=\"text\" disabled=\"disabled\" readonly=\"readonly\" id=\"");
            html.append(appraiseRule.getId());
            html.append("\" ondblclick=\"editRemark(\'" + appraiseRule.getId() + "\',$('"
                    + appraiseRule.getId() + "').value,\'" + tableId + "\',\'" + appraiseType
                    + "\',\'2\')\"/>");
        } else {
            html.append("<td class=\"" + tdClass + "\" ondblclick=\"viewRemark(this.innerText)\">");
            html.append(appraiseRule.getGradeDescription());
        }
        html.append("</td>");
    }

    /**
     * 获得TR前端
     * 
     * @return
     */
    String getTrFront() {
        StringBuffer html = new StringBuffer();
        html.append("<tr class=\"");
        html.append(trClass);
        html.append("\">");
        return html.toString();
    }

    /**
     * 获得TD前端
     * 
     * @return
     */
    String getTdFront() {
        StringBuffer html = new StringBuffer();
        html.append("<td class=\"");
        html.append(tdClass);
        html.append("\">");
        return html.toString();
    }

    /**
     * 将数据appraiseTable（List），contractNoArray(String[])，contractorName（String），
     * contractNo（String）存入到map中
     * 
     * @param bean
     * @param appraiseDailyBO
     * @param map
     * @return
     */
    Map<String, Object> getAppraiseDailyDataMap(AppraiseDailyBean bean,
            AppraiseDailyBO appraiseDailyBO, Map<String, Object> map) {
        try {
            map = appraiseDailyBO.getAppraiseDailyData(contractorId, displayType, bean);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查找对象出错：" + e.getMessage());
            return null;
        }
        return map;
    }

    AppraiseDailyBean initAppraiseDailyBean() {
        AppraiseDailyBean bean = new AppraiseDailyBean();
        setAppraiseTypeValue();
        setAppraiseName();
        bean.setContractorId(contractorId);
        bean.setBusinessModule(appraiseType + "_" + businessModule);
        bean.setBusinessId(businessId);
        bean.setId(id);
        return bean;
    }

    AppraiseDailyBO getAppraiseDailyBO(String boName) {
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(super.pageContext
                .getServletContext());
        AppraiseDailyBO appraiseDailyBO = (AppraiseDailyBO) applicationContext.getBean(boName);
        return appraiseDailyBO;
    }

    /**
     * 添加下划线
     * 
     * @param str
     * @return
     */
    String addUnderline(String str) {
        return "<u>" + str + "</u>";
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public String getBusinessModule() {
        return businessModule;
    }

    public void setBusinessModule(String businessModule) {
        this.businessModule = businessModule;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String getTableClass() {
        return tableClass;
    }

    public void setTableClass(String tableClass) {
        this.tableClass = tableClass;
    }

    public String getTrClass() {
        return trClass;
    }

    public void setTrClass(String trClass) {
        this.trClass = trClass;
    }

    public String getTdClass() {
        return tdClass;
    }

    public void setTdClass(String tdClass) {
        this.tdClass = tdClass;
    }

    public String getTableStyle() {
        return tableStyle;
    }

    public void setTableStyle(String tableStyle) {
        this.tableStyle = tableStyle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppraiseType() {
        return appraiseType;
    }

    public void setAppraiseType(String appraiseType) {
        this.appraiseType = appraiseType;
    }

    abstract void setAppraiseTypeValue();

    /**
     * 初始化数据
     * 所需要初始化的数据有appraiseTable（List），contractNoArray(String[])，contractorName（String），userInfo（UserInfo）
     * contractNo（String）
     */
    public abstract void init();

    abstract void addContractNo(StringBuffer html);

    abstract void setAppraiseName();
}
