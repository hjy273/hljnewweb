package com.cabletech.planstat.services;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;

import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.chart.parameter.ChartParameter;
import com.cabletech.commons.chart.utils.ChartUtils;
import com.cabletech.planstat.beans.MonthlyStatCityMobileConBean;
import com.cabletech.planstat.dao.MonthlyStatCityMobileDAOImpl;
import com.cabletech.planstat.dao.PlanStatBaseDAO;

/**
 * ���ƶ���ͳ�Ƶ�ҵ���߼���
 */
public class CityMobileYearStatBO extends BaseBisinessObject {
    private Logger logger = Logger.getLogger(this.getClass().getName()); // ����logger�������;

    private PlanStatBaseDAO dao = new MonthlyStatCityMobileDAOImpl();

    private String sql = "";

    public static final String MONTH_SPACE = "-2"; // ��ǰѡ���·���ǰ������Ƶ��·�����,"-"�ű�ʾ��ǰ��

    /**
     * �õ���·�����ֵ�ֵMap
     * 
     * @return lineTypeDictMap
     */
    public Map getLineTypeDictMap() {
        // sql = "select distinct code,lable as name from dictionary_formitem
        // where assortment_id='cabletype' order by code";
        sql = "select code,name from lineclassdic order by code";
        Map lineTypeDictMap = dao.queryLineTypeDictMap(sql);
        logger.info("�õ���·�����ֵ�ֵ��SQL:" + sql);
        return lineTypeDictMap;
    }

    /**
     * ���ݵõ��ļƻ���Ϣ��֯���յ���ʾ���
     * 
     * @param bean
     *            Sql�Ĳ�ѯ��������һ����֯
     * @return List
     */
    public List getContractorPlanInfoList(MonthlyStatCityMobileConBean bean, Map lineTypeDictMap) {
        sql = " SELECT c.contractorname,c.contractorid,t.excutetimes, t.linelevel,l.name ";
        sql += " FROM yearmonthplan y, plantasklist p, taskinfo t,lineclassdic l,contractorinfo c ";
        sql += " WHERE y.ID = p.planid ";
        sql += " AND p.taskid = t.ID and t.LINELEVEL = l.code and y.DEPTID = c.CONTRACTORID ";
        sql += " AND y.plantype = '1' ";
        sql += " AND y.regionid = '";
        sql += bean.getRegionID();
        sql += "' ";
        sql += " AND y.YEAR = '";
        sql += bean.getEndYear();
        sql += "' ";
        sql += " order by y.deptid,t.linelevel";
        String strLastConName = "";
        String strConID = "";
        String strLastConID = "";
        String strConName = "";
        String strLevel = "";
        int intExeTimes = 0;
        int intTotal = 0;
        boolean equalFlag = true;
        Map map; // ����ת����ļƻ����������Ϣ
        // ��ʱmap,ֻ����һ����ά��˾����Ϣ��������ô�ά��˾ֻ��һ���мƻ�����ֻ��һ��һ��key-value��
        Map tempMap = new HashMap();
        List tmpList = dao.queryInfo(sql);
        List list = new ArrayList(); // ����map����Ϣ��֯���ص�list
        DynaBean tmpBean;
        for (int i = 0; tmpList != null && i < tmpList.size(); i++) {
            tmpBean = (DynaBean) tmpList.get(i);
            strConName = (String) tmpBean.get("contractorname");
            strConID = (String) tmpBean.get("contractorid");
            intExeTimes = Integer.parseInt((String) tmpBean.get("excutetimes"));
            strLevel = (String) tmpBean.get("linelevel");
            if (equalFlag == true || strConID.equals(strLastConID)) {
                equalFlag = false;
            } else {
                map = organizeMap(tempMap, strLastConName, intTotal, lineTypeDictMap, new HashMap());
                list.add(map);
                intTotal = 0;
                tempMap = new HashMap();
            }
            tempMap.put(strLevel, new Integer(intExeTimes));
            intTotal += intExeTimes;
            strLastConID = strConID;
            strLastConName = strConName;
        }
        map = organizeMap(tempMap, strLastConName, intTotal, lineTypeDictMap, new HashMap());
        list.add(map);
        return list;
    }

    /**
     * �õ����ƶ�ĳ����ͳ���еĴ�άִ����Ϣ
     * 
     * @param bean
     *            ��֯��Ľ�������bean
     * @return List
     */
    public List getContractorExecuteResultInfo(MonthlyStatCityMobileConBean bean) {
        sql = " SELECT to_char(sum(pc.planpoint)) as planpoint,to_char(sum(pc.factpoint)) as factpoint, ";
        sql += " to_char(sum(pc.sublinekm)) as sublinekm,to_char(sum(pc.patrolkm)) as patrolkm, ";
        sql += " to_char(sum(pc.plannum)) as plannum,to_char(sum(pc.noexecuteplannum)) as noexecuteplannum, ";
        sql += " to_char(sum(pc.nocompleteplannum)) as nocompleteplannum,to_char(sum(pc.completeplannum)) as completeplannum, ";
        sql += " to_char(decode(sum(pc.planpoint),null,0,0,0,100*sum(pc.factpoint)/sum(pc.planpoint)),'FM990.09') as patrolp, ";
        sql += " '0' as examineresult,ci.contractorname ";
        sql += " FROM plan_cstat pc, contractorinfo ci 	";
        sql += " WHERE pc.cdeptid = ci.contractorid ";
        sql += " AND pc.regionid = '" + bean.getRegionID() + "' ";
        sql += " AND to_char(pc.factdate,'YYYY') = '" + bean.getEndYear() + "' ";
        sql += " group by ci.contractorid,ci.contractorname ";
        sql += " order by to_number(patrolp) desc";
        List tmpList = dao.queryInfo(sql);
        logger.info("��ѯ���ƶ���ͳ��֮��άִ����ϢSQL:" + sql);
        if (tmpList == null) {
            logger.info("��ѯ���ƶ���ͳ��֮��άִ����Ϣ���Ϊ��");
            return null;
        }
        List list = new ArrayList();
        List stList;
        DynaBean tmpBean;
        double patrolp;
        sql = " select st.examinegrade from examine_standard st where st.min<=patrolp and st.max>patrolp ";
        for (int i = 0; tmpList != null && i < tmpList.size(); i++) {
            tmpBean = (DynaBean) tmpList.get(i);
            if (tmpBean.get("patrolp") != null) {
                patrolp = Double.parseDouble((String) tmpBean.get("patrolp"));
                if (patrolp == 0) {
                    tmpBean.set("examineresult", "0");
                    continue;
                }
                if (patrolp == 100) {
                    tmpBean.set("examineresult", "5");
                    continue;
                }
                sql = sql.replaceAll("patrolp", (String) tmpBean.get("patrolp"));
                stList = dao.queryInfo(sql);
                if (stList != null && !stList.isEmpty()) {
                    tmpBean.set("examineresult", ((DynaBean) stList.get(0)).get("examinegrade"));
                }
            }
            list.add(tmpBean);
        }
        return list;
    }

    /**
     * ��֯�ƻ���Ϣ������м�¼map
     * 
     * @param tempMap
     *            ��ʱmap,ֻ����һ����ά��˾����Ϣ��������ô�ά��˾ֻ��һ���мƻ�����ֻ��һ��һ��key-value��
     * @param strLastConName
     *            ѭ���е����һ����ά��˾����
     * @param intTotal
     *            �ܴ���
     * @param dictMap
     *            �������·�����ֵ�ֵmap
     * @param backMap
     *            ���ص�map
     * @return
     */
    private Map organizeMap(Map tempMap, String strLastConName, int intTotal, Map dictMap,
            Map backMap) {
        Iterator itDict = dictMap.keySet().iterator();
        Integer zero = new Integer(0);
        while (itDict.hasNext()) {
            String dictKey = itDict.next().toString();
            Integer tempValue = (Integer) tempMap.get(dictKey);
            if (tempValue != null) {
                backMap.put(dictKey, tempValue);
            } else {
                backMap.put(dictKey, zero);
            }
        }
        backMap.put("conname", strLastConName);
        backMap.put("total", new Integer(intTotal));
        return backMap;
    }

    public JFreeChart createContractorPatrolRateChart(MonthlyStatCityMobileConBean bean) {
        // TODO Auto-generated method stub
        String sql = " select ci.contractorname,'0' as patrolp from contractorinfo ci ";
        sql += " where (ci.state is null or ci.state<>'1') ";
        List allList = dao.queryInfo(sql);
        sql = " select ci.contractorname, ";
        sql += " to_char(decode(sum(pcs.planpoint),null,0,0,0,100*sum(pcs.factpoint)/sum(pcs.planpoint)),'FM990.09') as patrolp ";
        sql += " from plan_cstat pcs,contractorinfo ci ";
        sql += " where pcs.cdeptid=ci.contractorid ";
        sql += " and (ci.state is null or ci.state<>'1') ";
        sql += " and to_char(pcs.factdate,'YYYY')='";
        sql += bean.getEndYear();
        sql += "'";
        sql += " group by ci.contractorid,ci.contractorname ";
        sql += " order by to_number(patrolp) desc ";
        List list = dao.queryInfo(sql);
        list = processList(allList, list);
        ChartParameter parameter = new ChartParameter();
        parameter.setChartType(ChartParameter.BAR_CHART_TYPE);
        parameter.setTitle(bean.getEndYear() + "�����ά��˾Ѳ���ʶԱȷ���");
        parameter.setXTitle("��ά��˾");
        parameter.setYTitle("Ѳ����");
        List chartColors = new ArrayList();
        List chartLabels = new ArrayList();
        chartColors.add(ChartParameter.REF_COLORS[1]);
        chartLabels.add("Ѳ����");
        parameter.setChartColors(chartColors);
        parameter.setChartLabels(chartLabels);
        parameter.setLabelDisplayFlag(true);
        parameter.setDataSet(ChartUtils.createDataSet(list, parameter));
        parameter.setYMinValue(ChartParameter.Y_MIN_RATE_VALUE);
        parameter.setYMaxValue(ChartParameter.Y_MAX_RATE_VALUE);
        JFreeChart chart = ChartUtils.generateBarChart(parameter);
        return chart;
    }

    public JFreeChart createContractorLineLevelPatrolRateChart(MonthlyStatCityMobileConBean bean) {
        // TODO Auto-generated method stub
        String sql = " select lc.code,lc.name,'0' as patrolp from lineclassdic lc ";
        sql += " order by lc.code ";
        List lineLevelList = dao.queryInfo(sql);
        sql = " select ci.contractorname,'0' as patrolp from contractorinfo ci ";
        sql += " where (ci.state is null or ci.state<>'1') ";
        List allList = dao.queryInfo(sql);
        List list = new ArrayList();
        List resultList = new ArrayList();
        DynaBean tmpBean;
        sql = "select ci.contractorname,";
        sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') ";
        sql += " from plan_sublinestat pss,sublineinfo si,lineinfo li,lineclassdic lc,patrolmaninfo pi,contractorinfo ci ";
        sql += " where pss.sublineid=si.sublineid and si.lineid=li.lineid ";
        sql += " and ci.contractorid=pi.parentid and li.linetype=lc.code ";
        sql += " and si.patrolid=pi.patrolid and lc.code=':code' ";
        sql += " and to_char(pss.factdate,'YYYY')='";
        sql += bean.getEndYear();
        sql += "'";
        sql += " group by pi.parentid,ci.contractorname ";
        sql += " order by pi.parentid";
        List chartColors = new ArrayList();
        List chartLabels = new ArrayList();
        for (int i = 0; lineLevelList != null && i < lineLevelList.size(); i++) {
            tmpBean = (DynaBean) lineLevelList.get(i);
            list = dao.queryInfo(sql.replaceAll(":code", (String) tmpBean.get("code")));
            resultList.addAll(processList(allList, list));
            chartColors.add(ChartParameter.REF_COLORS[i]);
            chartLabels.add(tmpBean.get("name") + "Ѳ����");
        }
        ChartParameter parameter = new ChartParameter();
        parameter.setChartType(ChartParameter.LINE_CHART_TYPE);
        parameter.setTitle(bean.getEndYear() + "�����ά��˾��ͬ��·����Ѳ���ʶԱȷ���");
        parameter.setXTitle("��ά��˾");
        parameter.setYTitle("Ѳ����");
        parameter.setChartColors(chartColors);
        parameter.setChartLabels(chartLabels);
        parameter.setLabelDisplayFlag(true);
        parameter.setDataSet(ChartUtils.createDataSet(resultList, parameter));
        parameter.setYMinValue(ChartParameter.Y_MIN_RATE_VALUE);
        parameter.setYMaxValue(ChartParameter.Y_MAX_RATE_VALUE);
        JFreeChart chart = ChartUtils.generateLineChart(parameter);
        return chart;
    }

    public List processList(List allList, List tmpList) {
        List list = new ArrayList();
        DynaBean bean;
        DynaProperty[] properties;
        String key = "";
        String value = "";
        Map map = new LinkedHashMap();
        for (int i = 0; allList != null && i < allList.size(); i++) {
            bean = (DynaBean) allList.get(i);
            properties = bean.getDynaClass().getDynaProperties();
            if (properties != null && properties.length >= 2) {
                key = (String) bean.get(properties[0].getName());
                value = (String) bean.get(properties[1].getName());
                map.put(key, value);
            }
        }
        for (int i = 0; tmpList != null && i < tmpList.size(); i++) {
            bean = (DynaBean) tmpList.get(i);
            properties = bean.getDynaClass().getDynaProperties();
            if (properties != null && properties.length >= 2) {
                key = (String) bean.get(properties[0].getName());
                value = (String) bean.get(properties[1].getName());
                map.put(key, value);
            }
        }
        list.add(map);
        return list;
    }

    public List getYearLineLevelExecuteResultInfo(MonthlyStatCityMobileConBean bean) {
        // TODO Auto-generated method stub
        String sql = " select lc.code,lc.name, ";
        sql += " sum(pss.planpoint) as planpoint,sum(pss.factpoint) as factpoint, ";
        sql += " sum(pss.sublinekm) as sublinekm,sum(pss.factkm) as patrolkm, ";
        sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') as patrolp, ";
        sql += " '0' as examineresult ";
        sql += " from plan_sublinestat pss,lineinfo li,lineclassdic lc ";
        sql += " where li.lineid=pss.lineid and lc.code=li.linetype ";
        sql += " and to_char(pss.factdate,'YYYY')='" + bean.getEndYear() + "' ";
        sql += " group by lc.code,lc.name ";
        sql += " order by lc.code ";
        List list = dao.queryInfo(sql);
        return list;
    }

    public List showYearLayingMethodExecuteResultInfo(MonthlyStatCityMobileConBean bean) {
        // TODO Auto-generated method stub
        String sql = " select lc.code,lc.lable as name, ";
        sql += " decode(sum(pss.planpoint),null,0,sum(pss.planpoint)) as planpoint, ";
        sql += " decode(sum(pss.factpoint),null,0,sum(pss.factpoint)) as factpoint, ";
        sql += " decode(sum(pss.sublinekm),null,0,sum(pss.sublinekm)) as sublinekm, ";
        sql += " decode(sum(pss.factkm),null,0,sum(pss.factkm)) as patrolkm, ";
        sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') as patrolp ";
        sql += " from plan_sublinestat pss,sublineinfo si,lineclassdic lc ";
        sql += " where pss.sublineid=si.sublineid and si.linetype=lc.code and lc.assortment_id='layingmethod'";
        sql += " and to_char(pss.factdate,'yyyy')='" + bean.getEndYear() + "' ";
        sql += " group by lc.code,lc.lable ";
        sql += " order by lc.code ";
        List list = dao.queryInfo(sql);
        return list;
    }
}
