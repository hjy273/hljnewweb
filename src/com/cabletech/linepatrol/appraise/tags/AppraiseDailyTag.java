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
 * �ճ����˱�ǩ ʵ���ƶ��Դ�ά��λ���ճ����ˣ�����ʵ�ְ�����д���˺Ͳ鿴������Ϣ
 * ��displayTypeΪinputʱ��Ϊ��д�ճ����ˡ�Ϊviewʱ��Ϊ�鿴�ճ�������Ϣ
 * ��ʱ��Ҫ����Ĳ����У�contractorId(��ά��λ���)��businessModule(ҵ��ģ��)��businessId(ҵ��ģ�ͱ��)��contractNO(�����)
 * ��д�ճ�����ʱ��contractorId��businessModule��businessIdΪ���������Ϊ�Ǳ�����
 * �鿴�ճ�����ʱ��businessModule��businessIdΪ���������Ϊ�Ǳ�����
 * 
 * @author liusq
 * 
 */
public abstract class AppraiseDailyTag extends BodyTagSupport {

    private static final long serialVersionUID = 6941722957074015695L;

    Logger logger = Logger.getLogger(AppraiseDailyTag.class);

    public String INPUT_DISPLAY_TYPE = "input";

    public String VIEW_DISPLAY_TYPE = "view";

    String contractorId; // ��ά��λ

    String businessModule; // ҵ��ģ��

    String businessId; // ҵ����

    String contractNo; // �����

    String appraiseType;

    String appriaseName;

    String id;// �ճ����˱��

    ApplicationContext applicationContext;

    List<AppraiseTable> appraiseTables;

    AppraiseTable appraiseTable; // ��������

    String[] contractNoArray; // ��ͬ��

    String contractorName = "";// ��ά��λ����

    UserInfo userInfo;// �û���Ϣ

    String tableId;// �¿��˱��

    // ���������ʽ
    String tableClass = "tabout";// �����ʽ

    String trClass = "trcolor";// ����ʽ

    String tdClass = "";// ����ʽ

    String tableAlign = "center";// table����

    String tableStyle = "";// ���ñ���Style��ʽ

    String flagName;

    // ��ǩ����ʾ��ʽ
    String displayType = INPUT_DISPLAY_TYPE;

    @SuppressWarnings("static-access")
    public int doEndTag() throws JspException {
        init();
        StringBuilder html = new StringBuilder();
        // ���˱���״̬���
        for (AppraiseTable aTable : appraiseTables) {
            trClass = "trcolor";
            appraiseTable = aTable;
            tableId = appraiseTable.getId();
            flagName = appraiseType + tableId;
            html.append("<form action=\"\" id=\"form\">");
            // �����򱣴����ֵ
            html.append(savePageParameters());

            // �����ⲿ��js�ļ�
            html.append(inportExternalFile());

            if (appraiseTable != null && StringUtils.isNotBlank(appraiseTable.getId())) {
                // ����һ�����
                getTable(html);
            }
            html.append("</form><br />");
        }
        try {
            super.pageContext.getOut().print(html.toString());
        } catch (IOException e) {
            logger.error("��Ⱦ��ǩ����" + e.getMessage());
        }
        return super.EVAL_PAGE;
    }

    /**
     * ��ÿ��˱�
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
                        + flagName + "\');\" style=\"cursor: pointer;\">��ʾ/����</a></div></td></tr>");
        }
        // ����տ���ͷһ����Ϣ
        html.append(dailyAppriaseGeneral());

        // �����¿��˵�ͷ����Ϣ
        html.append(exportTableHeader());

        List<AppraiseItem> appraiseItemList = appraiseTable.getAppraiseItems();
        for (int itemNum = 0; itemNum < appraiseItemList.size(); itemNum++) {
            outputTable(html, appraiseItemList, itemNum);
        }
        if (displayType.equals(AppraiseConstant.FLAG_INPUT)) {
            // html.append(getTrFront()+"<td style=\"text-align:right;\"
            // colspan=\"4\"><input type=\"button\" class=\"button\"
            // value=\"����\" onclick=\"javascript:saveForm(\'"+ appraiseType +
            // "\',\'"+ tableId + "\')\"></td></tr>");
            // html.append("<div
            // style=\"float:right;margin-right:103px;\"><input type=\"button\"
            // class=\"button\" value=\"����\" onclick=\"javascript:saveForm(\'"+
            // appraiseType + "\',\'"+ tableId + "\')\"></div><br />");
        }
        html.append("</tbody>");
        html.append("</table>");
    }

    /**
     * ��������
     * 
     * @param html
     * @param appraiseItemList
     * @param itemNum
     */
    private void outputTable(StringBuilder html, List<AppraiseItem> appraiseItemList, int itemNum) {
        AppraiseItem appraiseItem = appraiseItemList.get(itemNum);

        // �����һ����Ϣ
        html.append(exportFirstColumnInfo(appraiseItem));

        // ���Content�б�
        List<AppraiseContent> appraiseContentList = appraiseItem.getAppraiseContents();
        for (int contentNum = 0; contentNum < appraiseContentList.size(); contentNum++) {
            AppraiseContent appraiseContent = appraiseContentList.get(contentNum);
            // ����ڶ�����Ϣ
            html.append(exportSecondColumnInfo(contentNum, appraiseContent));

            // ���������������Ϣ
            html.append(exportThirdFourInfo(appraiseContent));
        }
    }

    /**
     * ����ڶ��е��¿���������Ϣ
     * 
     * @param contentNum
     *            �ڼ���
     * @param appraiseContent
     *            �¿�������ʵ��
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
     * �������һ�е��¿�����Ŀ��Ϣ
     * 
     * @param appraiseItem
     *            �¿�����Ŀ����
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
        html.append("" + appraiseItem.getItemSize());// ������ռ����
        html.append("\">");
        html.append(appraiseItem.getItemName());
        html.append("(");
        html.append(appraiseItem.getWeight());
        html.append(")");
        html.append("</td>");
        return html.toString();
    }

    /**
     * �����¿��˵�ͷ����̬��Ϣ
     */
    String exportTableHeader() {
        StringBuffer html = new StringBuffer("");
        html.append(getTrFront());
        html.append("<td style=\"font-weight: bold;text-align: center;\">��Ŀ</td>");
        html.append("<td style=\"font-weight: bold;text-align: center;\">����</td>");
        html.append("<td style=\"font-weight: bold;text-align: center;\">ϸ��</td>");
        html.append("<td style=\"font-weight: bold;text-align: center;\">�۷�˵������ѡ��ϸ������룩</td>");
        html.append("</tr>");
        return html.toString();
    }

    /**
     * �����ⲿ��js�ļ�
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
     * ����ҳ����Ҫ�Ĳ���ֵ
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
     * �������ͷһ����Ϣ��������ά��˾���б��ͬ�š������ˡ���������
     */
    private String dailyAppriaseGeneral() {
        StringBuffer html = new StringBuffer("");
        String appraiseDate = DateUtil.DateToString(new Date(), "yyyy-MM-dd");
        html.append("<tbody id=\"" + flagName + "\" style=\"display: block;\">");
        html.append("<tr class=\"trwhite\">");
        html.append("<td class=\"");
        html.append(tdClass);
        html.append("\" width=\"25%\">��ά��˾ : ");
        html.append("<input type=\"hidden\" name=\"" + flagName + "contractorId\" id=\"" + flagName
                + "contractorId\" value=\"");
        html.append(contractorId);
        html.append("\"/>");
        html.append(addUnderline(contractorName));
        html.append("</td>");
        html.append("<td width=\"25%\">������ : ");
        if (StringUtils.equals("input", displayType)) {
            html.append(addUnderline(userInfo.getUserName()));
        } else {
            html.append(addUnderline(appraiseTable.getCreater()));
        }
        addContractNo(html);
        html.append("</td><td width=\"25%\">�������� : ");
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
     * �����������HTML�������к͵�������Ϣ
     * 
     * @param appraiseContent
     * @return
     */
    String exportThirdFourInfo(AppraiseContent appraiseContent) {
        StringBuffer html = new StringBuffer();
        // ��þ������б�
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
     * �����������HTML����������Ϣ
     * 
     * @param html
     * @param appraiseRule
     */
    void exportThirdInfo(StringBuffer html, AppraiseRule appraiseRule) {
        // ��Ϊ�����ʱ����ʾcheckBox
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
     * �����������HTML����������Ϣ
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
     * ���TRǰ��
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
     * ���TDǰ��
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
     * ������appraiseTable��List����contractNoArray(String[])��contractorName��String����
     * contractNo��String�����뵽map��
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
            logger.info("���Ҷ������" + e.getMessage());
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
     * ����»���
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
     * ��ʼ������
     * ����Ҫ��ʼ����������appraiseTable��List����contractNoArray(String[])��contractorName��String����userInfo��UserInfo��
     * contractNo��String��
     */
    public abstract void init();

    abstract void addContractNo(StringBuffer html);

    abstract void setAppraiseName();
}
