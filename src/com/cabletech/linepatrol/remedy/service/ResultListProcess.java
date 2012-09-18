package com.cabletech.linepatrol.remedy.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

public class ResultListProcess {
    private static Logger logger = Logger.getLogger(ResultListProcess.class.getName());

    /**
     * ������������б�
     * 
     * @param prevList
     *            List δ���д������������б�
     * @return Map<String approveState,List<DynaBean approveBean>> ���д�������������б�
     */
    public Map processApproveList(List prevList) {
        Map oneMap = new LinkedHashMap();
        DynaBean bean;
        String approveState;
        List list;
        for (int i = 0; prevList != null && i < prevList.size(); i++) {
            bean = (DynaBean) prevList.get(i);
            approveState = (String) bean.get("approve_state");
            if (oneMap != null && oneMap.containsKey(approveState)) {
                list = (List) oneMap.get(approveState);
            } else {
                list = new ArrayList();
            }
            list.add(bean);
            oneMap.put(approveState, list);
        }
        logger.info("process map is :" + oneMap);
        return oneMap;
    }
}
