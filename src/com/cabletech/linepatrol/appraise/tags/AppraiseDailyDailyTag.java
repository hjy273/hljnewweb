package com.cabletech.linepatrol.appraise.tags;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyBO;

public class AppraiseDailyDailyTag extends AppraiseDailyTag {
    @Override
    void addContractNo(StringBuffer html) {
        html.append("<td width=\"25%\">中标合同号 : ");
        if (StringUtils.isBlank(contractNo)) {
            if (StringUtils.equals("input", displayType)) {
                html.append("<select class=\"inputtext\" id=\"contractNo\" name=\"contractNo\">");
                html.append("<option>不限</option>");// 全部为不限
                if (contractNoArray != null) {
                    for (int noNum = 0; noNum < contractNoArray.length; noNum++) {
                        html.append("<option value=\"");
                        html.append(contractNoArray[noNum]);
                        html.append("\">");
                        html.append(contractNoArray[noNum]);
                        html.append("</option>");
                    }
                }
                html.append("</select>");
            }
        } else {
            if (contractNo == null) {
                html.append(addUnderline("无"));
            } else {
                html.append(addUnderline(contractNo));
            }
            html.append("<input type=\"hidden\" id=\"contractNo\" name=\"contractNo\" value=\"");
            html.append(contractNo);
            html.append("\"/>");
        }
        html.append("</td>");
    }

    /**
     * 所需要初始化的数据有appraiseTable（List），contractNoArray(String[])，contractorName（String），userInfo（UserInfo）
     * contractNo（String）
     */
    @Override
    public void init() {
        AppraiseDailyBean bean = initAppraiseDailyBean();
        bean.setContractNo(contractNo);
        AppraiseDailyBO appraiseDailyBO = getAppraiseDailyBO("appraiseDailyDailyBO");
        Map<String, Object> map = null;
        map = getAppraiseDailyDataMap(bean, appraiseDailyBO, map);
        appraiseTables = (List<AppraiseTable>) map.get("appraiseTables");
        contractNoArray = (String[]) map.get("contractNoArray");
        contractorName = (String) map.get("contractorName");
        userInfo = (UserInfo) super.pageContext.getSession().getAttribute("LOGIN_USER");
        if (StringUtils.equals("view", displayType)) {
            contractNo = (String) map.get("contractNo");
        }
    }

    @Override
    void setAppraiseTypeValue() {
        appraiseType = AppraiseConstant.TYPE_DAILY;

    }

    @Override
    void setAppraiseName() {
        appriaseName = AppraiseConstant.APPRAISENAME_DAILY;
    }

}
