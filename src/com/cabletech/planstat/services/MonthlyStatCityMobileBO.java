package com.cabletech.planstat.services;

import java.awt.Color;
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
public class MonthlyStatCityMobileBO extends BaseBisinessObject {
    private Logger logger = Logger.getLogger(this.getClass().getName()); // ����logger�������;

    private PlanStatBaseDAO dao = new MonthlyStatCityMobileDAOImpl();

    private String sql = "";

    public static final String MONTH_SPACE = "-2"; // ��ǰѡ���·���ǰ������Ƶ��·�����,"-"�ű�ʾ��ǰ��

    /**
     * �õ����ƶ�ĳ����ͳ���еļƻ���Ϣ
     * 
     * @param bean
     *            ��֯��Ľ�������bean
     * @param lineTypeDictMap
     *            ��·���������ֵ�map
     * @return List
     */
    public List getPlanInfo(MonthlyStatCityMobileConBean bean, Map lineTypeDictMap) {
        sql = getPlanInfoSql(bean);
        ResultSet rs = dao.queryInfoJDBC(sql);
        logger.info("��ѯ���ƶ���ͳ��֮�ƻ���ϢSQL:" + sql);
        if (rs == null) {
            logger.info("��ѯ���ƶ���ͳ��֮�ƻ���Ϣ���Ϊ��");
            return null;
        }
        try {
            if (!rs.next()) {
                logger.info("��ѯ���ƶ���ͳ��֮�ƻ���Ϣ�������Ϊ0");
                return null;
            } else {
                rs.previous();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            return null;
        }
        List planInfoList = getFinalList(rs, lineTypeDictMap);
        return planInfoList;
    }

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
     * �������ƶ�ĳ����ͳ���еļƻ���ϢSql
     * 
     * @param bean
     *            ��֯��Ľ�������bean
     * @return String
     */
    public String getPlanInfoSql(MonthlyStatCityMobileConBean bean) {
        sql = "SELECT c.contractorname,c.contractorid,t.excutetimes, t.linelevel,l.name "
                + "FROM yearmonthplan y, plantasklist p, taskinfo t,lineclassdic l,contractorinfo c "
                + "WHERE y.ID = p.planid " + "AND p.taskid = t.ID " + "and t.LINELEVEL = l.code "
                + "and y.DEPTID = c.CONTRACTORID " + "AND y.regionid = '" + bean.getRegionID()
                + "' " + "AND y.plantype = '2' " + "AND y.YEAR = '" + bean.getEndYear() + "' "
                + "AND y.MONTH = '" + bean.getEndMonth() + "' " + "order by y.deptid,t.linelevel";
        return sql;
    }

    /**
     * ���ݵõ��ļƻ���Ϣ��֯���յ���ʾ���
     * 
     * @param bean
     *            Sql�Ĳ�ѯ��������һ����֯
     * @return List
     */
    public List getFinalList(ResultSet rs, Map lineTypeDictMap) {
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
        List list = new ArrayList(); // ����map����Ϣ��֯���ص�list
        try {
            while (rs.next()) {
                strConName = rs.getString(1);
                strConID = rs.getString(2);
                intExeTimes = rs.getInt(3);
                strLevel = rs.getString(4);
                if (equalFlag == true || strConID.equals(strLastConID)) {
                    equalFlag = false;
                } else {
                    map = organizeMap(tempMap, strLastConName, intTotal, lineTypeDictMap,
                            new HashMap());
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * �õ����ƶ�ĳ����ͳ���е�������Ϣ
     * 
     * @param bean
     *            ��֯��Ľ�������bean
     * @return List
     */
    public List getGeneralInfo(MonthlyStatCityMobileConBean bean) {
        sql = getGeneralInfoSql(bean);
        List list = dao.queryInfo(sql);
        logger.info("��ѯ���ƶ���ͳ��֮������ϢSQL:" + sql);
        if (list == null) {
            logger.info("��ѯ���ƶ���ͳ��֮������Ϣ���Ϊ��");
            return null;
        }
        return list;
    }

    /**
     * �õ����ƶ�ĳ����ͳ���еĴ�άִ����Ϣ
     * 
     * @param bean
     *            ��֯��Ľ�������bean
     * @return List
     */
    public List getContractorExeInfo(MonthlyStatCityMobileConBean bean) {
        sql = getContractorExeInfoSql(bean);
        List list = dao.queryInfo(sql);
        logger.info("��ѯ���ƶ���ͳ��֮��άִ����ϢSQL:" + sql);
        if (list == null) {
            logger.info("��ѯ���ƶ���ͳ��֮��άִ����Ϣ���Ϊ��");
            return null;
        }
        return list;
    }

    /**
     * �õ����ƶ�ĳ����ͳ���еļƻ�ִ����Ϣ
     * 
     * @param bean
     *            ��֯��Ľ�������bean
     * @return List
     */
    public List getPlanExeInfo(MonthlyStatCityMobileConBean bean) {
        sql = getPlanExeInfoSql(bean);
        List list = dao.queryInfo(sql);
        logger.info("��ѯ���ƶ���ͳ��֮���мƻ�ִ����ϢSQL:" + sql);
        if (list == null) {
            logger.info("��ѯ���ƶ���ͳ��֮���мƻ�ִ����Ϣ���Ϊ��");
            return null;
        }
        return list;
    }

    /**
     * �õ����ƶ�ĳ����ͳ���е�����Աȷ���������
     * 
     * @param bean
     *            ��֯��Ľ�������bean
     * @return List
     */
    public List getVCompAnalysisInfo(MonthlyStatCityMobileConBean bean) {
        sql = getVCompAnalysisSql(bean);
        List list = dao.queryInfo(sql);
        logger.info("��ѯ���ƶ���ͳ��֮����Աȷ���SQL:" + sql);
        if (list == null) {
            logger.info("��ѯ���ƶ���ͳ��֮����Աȷ������Ϊ��");
            return null;
        }
        return list;
    }

    /**
     * �õ����ƶ�ĳ����ͳ���е������߶�����(���ϸ�δ�ϸ�δ�ƻ�)
     * 
     * @param bean
     *            ��֯��Ľ�������bean
     * @return List
     */
    public Map getAllTypeSublinesInfo(MonthlyStatCityMobileConBean bean) {
        sql = getAllTypeSublineSql(bean);
        Map map = dao.getSublineNumMap(sql);
        logger.info("��ѯ���ƶ���ͳ��֮�����߶�(���ϸ�δ�ϸ�δ�ƻ�)SQL:" + sql);
        if (map == null) {
            logger.info("��ѯ���ƶ���ͳ��֮�����߶�(���ϸ�δ�ϸ�δ�ƻ�)���Ϊ��");
            return null;
        }
        return map;
    }

    /**
     * �������ƶ�ĳ����ͳ���е������߶�����Sql(���ϸ�δ�ϸ�δ�ƻ�)
     * 
     * @param bean
     *            ��֯��Ľ�������bean
     * @param IsPass
     *            �ж���Ҫ��ϸ�Ļ��ǲ��ϸ���߶�
     * @return String
     */
    public String getAllTypeSublineSql(MonthlyStatCityMobileConBean bean) {
        String theStartDate = getFirst2Month(bean);
        sql = "SELECT " + "(SELECT COUNT (*) sublinenum " + "FROM plan_sublinestat p "
                + "WHERE p.regionid = '"
                + bean.getRegionID()
                + "' "
                + "AND p.factdate >= TO_DATE ('"
                + theStartDate
                + "', 'yyyy-mm-dd') "
                + "AND p.factdate <= last_day(TO_DATE ('"
                + theStartDate
                + "', 'yyyy-mm-dd')) and p.EXAMINERESULT >= '3') pass,"
                + "(SELECT COUNT (*) sublinenum "
                + "FROM plan_sublinestat p "
                + "WHERE p.regionid = '"
                + bean.getRegionID()
                + "' "
                + "AND p.factdate >= TO_DATE ('"
                + theStartDate
                + "', 'yyyy-mm-dd') "
                + "AND p.factdate <= last_day(TO_DATE ('"
                + theStartDate
                + "', 'yyyy-mm-dd')) and p.EXAMINERESULT < '3') fail,"
                + "(SELECT COUNT (*) sublinenum "
                + "FROM nonplansubline n, sublineinfo s "
                + "WHERE n.sublineid = s.sublineid "
                + "AND s.regionid = '"
                + bean.getRegionID()
                + "' "
                + "AND n.factdate >= TO_DATE ('"
                + theStartDate
                + "', 'yyyy-mm-dd') "
                + "AND n.factdate <= last_day(TO_DATE ('"
                + theStartDate
                + "', 'yyyy-mm-dd'))) noplan " + "from dual";
        return sql;
    }

    /**
     * �������ƶ�ĳ����ͳ���е�����Աȷ���Sql������
     * 
     * @param bean
     *            ��֯��Ľ�������bean
     * @return String
     */
    public String getVCompAnalysisSql(MonthlyStatCityMobileConBean bean) {
        String endYearMonth = bean.getEndYear() + "-" + bean.getEndMonth();
        sql = "SELECT   TO_CHAR (p.factdate, 'MM') startmonth, p.examineresult "
                + "FROM plan_mstat p " + "WHERE p.factdate between add_months(TO_DATE ('"
                + endYearMonth + "', 'yyyy-MM')," + MONTH_SPACE + ") " + "and last_day(to_date('"
                + endYearMonth + "', 'yyyy-mm')) " + "and p.REGIONID='" + bean.getRegionID() + "' "
                + "ORDER BY p.factdate";
        return sql;
    }

    /**
     * �������ƶ�ĳ����ͳ���е�������ϢSql
     * 
     * @param bean
     *            ��֯��Ľ�������bean
     * @return String
     */
    public String getGeneralInfoSql(MonthlyStatCityMobileConBean bean) {
        String theStartDate = getFirst2Month(bean);
        sql = "SELECT   SUM (pc.trouble) trouble, SUM (pc.keypoint) keypoint,"
                + "SUM (pc.leakkeypoint) leakkeypoint,SUM (pc.leakpoint) leakpoint,"
                + "SUM (pc.patrolkm) patrolkm,pm.examineresult,pm.overallpatrolp "
                + "FROM plan_mstat pm, planstat_mc ps, plan_cstat pc "
                + "WHERE pm.mstatid = ps.mstatid " + "AND ps.cstatid = pc.cstatid "
                + "AND pc.regionid = pm.regionid " + "AND pc.regionid = '" + bean.getRegionID()
                + "' " + " and (pc.factdate >= TO_DATE ('" + theStartDate
                + "', 'yyyy-mm-dd') AND pc.factdate <= last_day(TO_DATE ('" + theStartDate
                + "', 'yyyy-mm-dd'))) " + "GROUP BY pc.regionid,pm.examineresult,pm.overallpatrolp";
        return sql;
    }

    /**
     * �������ƶ�ĳ����ͳ���еĴ�άִ����ϢSql
     * 
     * @param bean
     *            ��֯��Ľ�������bean
     * @return String
     */
    public String getContractorExeInfoSql(MonthlyStatCityMobileConBean bean) {
        String theStartDate = getFirst2Month(bean);
        sql = "SELECT c.contractorname, pc.planpoint,pc.factpoint,pc.sublinekm,pc.patrolkm,pc.trouble, pc.examineresult,pc.patrolp || '%' as patrolp "
                + "FROM plan_cstat pc, contractorinfo c "
                + "WHERE pc.cdeptid = c.contractorid "
                + "AND pc.regionid = '"
                + bean.getRegionID()
                + "' "
                + "AND pc.factdate >= TO_DATE ('"
                + theStartDate
                + "', 'yyyy-mm-dd') "
                + "AND pc.factdate <= last_day(TO_DATE ('"
                + theStartDate
                + "', 'yyyy-mm-dd')) "
                + "order by pc.patrolp desc";
        return sql;
    }

    /**
     * �������ƶ�ĳ����ͳ���еļƻ�ִ����ϢSql
     * 
     * @param bean
     *            ��֯��Ľ�������bean
     * @return String
     */
    public String getPlanExeInfoSql(MonthlyStatCityMobileConBean bean) {
        String theStartDate = getFirst2Month(bean);
        sql = "SELECT   p.planname, TO_CHAR (p.startdate, 'YYYY-MM-DD') startdate,"
                + "TO_CHAR (p.enddate, 'YYYY-MM-DD') enddate, p.troublenum, p.examineresult,p.patrolp || '%' as patrolp "
                + "FROM plan_stat p, contractorinfo c " + "WHERE p.contractorid = c.contractorid "
                + "AND c.regionid = '" + bean.getRegionID() + "' "
                + "AND p.startdate >= TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd') "
                + "AND p.enddate <= last_day(TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd')) "
                + "ORDER BY p.contractorid";
        return sql;
    }

    /**
     * ��֯startDate
     * 
     * @param bean
     *            ��֯��Ľ�������bean
     * @return String
     */
    private String getFirst2Month(MonthlyStatCityMobileConBean bean) {
        String theStartDate = bean.getEndYear() + "-" + bean.getEndMonth() + "-1";
        return theStartDate;

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

    public JFreeChart createSublinePatrolRateChart(MonthlyStatCityMobileConBean bean, String type) {
        // TODO Auto-generated method stub
        String sql = " select ci.contractorname,'0' as subline_num from contractorinfo ci ";
        sql += " where (ci.state is null or ci.state<>'1') ";
        sql += " order by ci.contractorid ";
        List allList = dao.queryInfo(sql);
        List list = new ArrayList();
        String title = "";
        if ("3".equals(type)) {
            sql = " select ci.contractorname, ";
            sql += " to_char(100*count(*)/( ";
            sql += "  select count(*) from sublineinfo si,patrolmaninfo pm ";
            sql += "  where (si.state is null or si.state<>'1') ";
            sql += "  and si.patrolid=pm.patrolid and pm.parentid=ci.contractorid ";
            sql += " ),'FM990.09') as subline_num,ci.contractorid ";
            sql += " from nonplansubline nps,patrolmaninfo pi,contractorinfo ci ";
            sql += " where nps.patrolid=pi.patrolid and pi.parentid=ci.contractorid ";
            sql += " and (ci.state is null or ci.state<>'1') ";
            sql += " and nps.factdate=to_date('";
            sql += bean.getEndYear();
            sql += "-";
            sql += bean.getEndMonth();
            sql += "-01','yyyy-mm-dd') ";
            sql += " group by ci.contractorname ,ci.contractorid ";
            sql += " order by to_number(subline_num) ";
            list = dao.queryInfo(sql);
            title = "δ�ƻ�";
        }
        sql = " select ci.contractorname,to_char(100*count(*)/( ";
        sql += "  select count(*) from sublineinfo si,patrolmaninfo pm ";
        sql += "  where (si.state is null or si.state<>'1') ";
        sql += "  and si.patrolid=pm.patrolid and pm.parentid=ci.contractorid ";
        sql += " ),'FM990.09') as subline_num,ci.contractorid ";
        sql += " from plan_sublinestat pss,patrolmaninfo pi,contractorinfo ci";
        sql += " where pss.patrolid=pi.patrolid and pi.parentid=ci.contractorid ";
        sql += " and (ci.state is null or ci.state<>'1') ";
        sql += " and examineresult op_value";
        sql += " and pss.factdate=to_date('";
        sql += bean.getEndYear();
        sql += "-";
        sql += bean.getEndMonth();
        sql += "-01','yyyy-mm-dd') ";
        sql += " group by ci.contractorname,ci.contractorid  ";
        if ("2".equals(type)) {
            sql += " order by to_number(subline_num) ";
            list = dao.queryInfo(sql.replaceAll("op_value", "<'3'"));
            title = "δ�ϸ�";
        }
        if ("1".equals(type)) {
            sql += " order by to_number(subline_num) desc ";
            list = dao.queryInfo(sql.replaceAll("op_value", ">='3'"));
            title = "�ϸ�";
        }
        list = processList(allList, list);
        ChartParameter parameter = new ChartParameter();
        parameter.setChartType(ChartParameter.BAR_CHART_TYPE);
        parameter.setTitle("����ά��˾" + title + "Ѳ���߶�����Աȷ���");
        parameter.setXTitle("��ά��˾");
        parameter.setYTitle(title + "�߶���ռ�ٷֱ�");
        parameter.setYMinValue(ChartParameter.Y_MIN_RATE_VALUE);
        List chartColors = new ArrayList();
        List chartLabels = new ArrayList();
        chartColors.add(ChartParameter.REF_COLORS[1]);
        chartLabels.add("�߶���ռ�ٷֱ�");
        parameter.setChartColors(chartColors);
        parameter.setChartLabels(chartLabels);
        parameter.setLabelDisplayFlag(true);
        parameter.setDataSet(ChartUtils.createDataSet(list, parameter));
        parameter.setYMaxValue(ChartParameter.Y_MAX_RATE_VALUE);
        JFreeChart chart = ChartUtils.generateBarChart(parameter);
        return chart;
    }

    public JFreeChart createLineLevelSublinePatrolRateChart(MonthlyStatCityMobileConBean bean,
            String type) {
        // TODO Auto-generated method stub
        String sql = " select lc.code,lc.name,'0' as patrolp from lineclassdic lc ";
        sql += " order by lc.code ";
        List lineLevelList = dao.queryInfo(sql);
        sql = " select ci.contractorname,'0' as patrolp from contractorinfo ci ";
        sql += " where (ci.state is null or ci.state<>'1') ";
        sql += " order by ci.contractorid ";
        List allList = dao.queryInfo(sql);
        List list = new ArrayList();
        List resultList = new ArrayList();
        DynaBean tmpBean;
        String title = "";
        sql = "select ci.contractorname,";
        sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') ";
        sql += " from plan_sublinestat pss,sublineinfo si,lineinfo li,lineclassdic lc,patrolmaninfo pi,contractorinfo ci ";
        sql += " where pss.sublineid=si.sublineid and si.lineid=li.lineid ";
        sql += " and ci.contractorid=pi.parentid and li.linetype=lc.code ";
        sql += " and si.patrolid=pi.patrolid and lc.code=':code' ";
        sql += " and pss.factdate=to_date('";
        sql += bean.getEndYear();
        sql += "-";
        sql += bean.getEndMonth();
        sql += "-1','yyyy-mm-dd') ";
        sql += " group by pi.parentid,ci.contractorname ";
        sql += " order by pi.parentid ";
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
        parameter.setTitle("����ά��˾" + title + "��ͬ��·����Ѳ���߶�����Աȷ���");
        parameter.setXTitle("��ά��˾");
        parameter.setYTitle(title + "Ѳ����");
        parameter.setYMinValue(ChartParameter.Y_MIN_RATE_VALUE);
        parameter.setChartColors(chartColors);
        parameter.setChartLabels(chartLabels);
        parameter.setLabelDisplayFlag(true);
        parameter.setDataSet(ChartUtils.createDataSet(resultList, parameter));
        parameter.setYMaxValue(ChartParameter.Y_MAX_RATE_VALUE);
        JFreeChart chart = ChartUtils.generateLineChart(parameter);
        return chart;
    }

    public JFreeChart createContractorPatrolRateChart(MonthlyStatCityMobileConBean bean) {
        // TODO Auto-generated method stub
        String sql = " select ci.contractorname,'0' as patrolp from contractorinfo ci ";
        sql += " where (ci.state is null or ci.state<>'1') ";
        List allList = dao.queryInfo(sql);
        sql = " select ci.contractorname,to_char(pcs.patrolp) as patrolp  ";
        sql += " from plan_cstat pcs,contractorinfo ci ";
        sql += " where pcs.cdeptid=ci.contractorid ";
        sql += " and (ci.state is null or ci.state<>'1') ";
        sql += " and pcs.factdate=to_date('";
        sql += bean.getEndYear();
        sql += "-";
        sql += bean.getEndMonth();
        sql += "-01','yyyy-mm-dd')";
        sql += " order by pcs.patrolp desc ";
        List list = dao.queryInfo(sql);
        list = processList(allList, list);
        ChartParameter parameter = new ChartParameter();
        parameter.setChartType(ChartParameter.BAR_CHART_TYPE);
        parameter.setTitle("����ά��˾Ѳ���ʶԱȷ���");
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

    public List showMonthLayingMethodExecuteResultInfo(MonthlyStatCityMobileConBean bean) {
        // TODO Auto-generated method stub
        String sql = " select lc.code,lc.name, ";
        sql += " decode(sum(pss.planpoint),null,0,sum(pss.planpoint)) as planpoint, ";
        sql += " decode(sum(pss.factpoint),null,0,sum(pss.factpoint)) as factpoint, ";
        sql += " decode(sum(pss.sublinekm),null,0,sum(pss.sublinekm)) as sublinekm, ";
        sql += " decode(sum(pss.factkm),null,0,sum(pss.factkm)) as patrolkm, ";
        sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') as patrolp ";
        sql += " from plan_sublinestat pss,sublineinfo si,lineinfo li,lineclassdic lc ";
        sql += " where pss.sublineid=si.sublineid and si.lineid=li.lineid and li.linetype=lc.code ";
        sql += " and pss.factdate>=to_date('";
        sql += bean.getEndYear();
        sql += "-";
        sql += bean.getEndMonth();
        sql += "','yyyy-mm') ";
        sql += " and pss.factdate<=to_date('";
        sql += bean.getEndYear();
        sql += "-";
        sql += bean.getEndMonth();
        sql += "','yyyy-mm') ";
        sql += " group by lc.code,lc.name ";
        sql += " order by lc.code ";
        List list = dao.queryInfo(sql);
        return list;
    }
}
