package com.cabletech.planstat.services;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.chart.parameter.ChartParameter;
import com.cabletech.commons.chart.utils.ChartUtils;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.planinfo.beans.YearMonthPlanBean;
import com.cabletech.planinfo.services.YearMonthPlanBO;
import com.cabletech.planstat.beans.PatrolStatConditionBean;
import com.cabletech.planstat.dao.MonthlyStatDAOImpl;
import com.cabletech.planstat.template.PlanExecuteResultTemplate;

public class ContractorYearStatBO extends BaseBisinessObject {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private String sql = null;

    public static final int YEAR_NUMBER = 5;

    public static final String MONTH_SPACE = "-2"; // 当前选定月份向前或向后推的月份数量,"-"号表示向前推

    private MonthlyStatDAOImpl monthlyStatDAOImpl = null;

    public ContractorYearStatBO() {
        monthlyStatDAOImpl = new MonthlyStatDAOImpl();
    }

    // 得到符合条件的市代维单位列表
    public List getContractorInfoList(UserInfo userinfo) {
        if (userinfo.getType().equals("12")) {
            sql = "select con.contractorid,con.contractorname,con.parentcontractorid "
                    + "from contractorinfo con " + "where con.STATE is null "
                    + "and con.regionid='" + userinfo.getRegionID() + "' "
                    + "order by con.contractorname";
        } else if (userinfo.getType().equals("21")) {
            sql = "SELECT c.contractorid, c.contractorname,c.parentcontractorid "
                    + "FROM contractorinfo c " + "where c.regionid not like '%0000' "
                    + "and c.state is null "
                    + "CONNECT BY PRIOR contractorid = parentcontractorid "
                    + "START WITH contractorid in "
                    + " (select u.deptid from userinfo u where u.userid = '" + userinfo.getUserID()
                    + "') " + "order by c.regionid";
        } else if (userinfo.getType().equals("11")) {
            sql = "select con.contractorid,con.contractorname,con.parentcontractorid,con.regionid "
                    + "from contractorinfo con " + "where con.STATE is null "
                    + "order by con.contractorname";
        } else if (userinfo.getType().equals("22")) {
            sql = "select con.contractorid,con.contractorname,con.parentcontractorid "
                    + "from contractorinfo con " + "where con.STATE is null "
                    + "and con.regionid='" + userinfo.getRegionID() + "' "
                    + "and con.contractorid='" + userinfo.getDeptID() + "' "
                    + "order by con.contractorname";
        }
        logger.info("市代维单位列表SQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    public List getRegionInfoList() {
        String sql = "select  r.REGIONNAME, r.REGIONID "
                + " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
        logger.info("区域SQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    // 月计划信息
    public YearMonthPlanBean getYearPlan(UserInfo userinfo, PatrolStatConditionBean bean)
            throws Exception {
        // 获得计划id
        String sql = "select id from yearmonthplan where year='" + bean.getEndYear()
                + "' and plantype='1' and deptid='" + bean.getConID() + "'";
        logger.info("sql " + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        // 获取计划
        YearMonthPlanBO ymbo = new YearMonthPlanBO();
        YearMonthPlanBean mbean = null;
        BasicDynaBean dynabean = (BasicDynaBean) list.get(0);
        mbean = ymbo.getYMPlanInfo(dynabean.get("id").toString());
        return mbean;

    }

    public List getContractorYearExecuteResult(PatrolStatConditionBean bean) {
        // TODO Auto-generated method stub
        String sql = " select sum(pcs.planpoint) as planpoint,sum(pcs.factpoint) as factpoint,sum(pcs.leakpoint) as leakpoint, ";
        // sql +=
        // " sum(pcs.planpointn) as planpointn,sum(pcs.factpointn) as
        // factpointn, ";
        sql += " sum(pcs.sublinekm) as sublinekm,sum(pcs.patrolkm) as patrolkm, ";
        sql += " sum(pcs.nonplansublinenum) as nonplansublinenum,sum(pcs.nopatrolsublinenum) as nopatrolsublinenum, ";
        sql += " sum(pcs.partpatrolsublinenum) as partpatrolsublinenum,sum(pcs.completesublinenum) as completesublinenum, ";
        sql += " sum(pcs.plannum) as plannum,sum(pcs.noexecuteplannum) as noexecuteplannum, ";
        sql += " sum(pcs.nocompleteplannum) as nocompleteplannum,sum(pcs.completeplannum) as completeplannum, ";
        sql += " to_char(decode(sum(pcs.planpoint),null,0,0,0,100*sum(pcs.factpoint)/sum(pcs.planpoint)),'FM990.09') as patrolp, ";
        sql += " '0' as examineresult,ci.contractorname ";
        sql += " from plan_cstat pcs,contractorinfo ci ";
        sql += " where pcs.cdeptid=ci.contractorid ";
        sql += " and to_char(pcs.factdate,'YYYY')='" + bean.getEndYear() + "' ";
        sql += " and ci.contractorid='" + bean.getConID() + "' ";
        sql += " group by ci.contractorid,ci.contractorname ";
        sql += " order by to_number(patrolp) desc ";
        List tmpList = monthlyStatDAOImpl.getBackInfoList(sql);
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
                stList = monthlyStatDAOImpl.getBackInfoList(sql);
                if (stList != null && !stList.isEmpty()) {
                    tmpBean.set("examineresult", ((DynaBean) stList.get(0)).get("examinegrade"));
                }
            }
            list.add(tmpBean);
        }
        return list;
    }

    public List getContractorYearLineLevelExecuteResult(PatrolStatConditionBean bean) {
        // TODO Auto-generated method stub
        String sql = " select lc.code,lc.name, ";
        sql += " sum(pss.planpoint) as planpoint,sum(pss.factpoint) as factpoint, ";
        sql += " sum(pss.sublinekm) as sublinekm,sum(pss.factkm) as patrolkm, ";
        sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') as patrolp, ";
        sql += " '0' as examineresult ";
        sql += " from plan_sublinestat pss,patrolmaninfo pi,contractorinfo ci,lineinfo li,lineclassdic lc ";
        sql += " where pss.patrolid=pi.patrolid and pi.parentid=ci.contractorid ";
        sql += " and li.lineid=pss.lineid and lc.code=li.linetype ";
        sql += " and to_char(pss.factdate,'YYYY')='" + bean.getEndYear() + "' ";
        sql += " and ci.contractorid='" + bean.getConID() + "' ";
        sql += " group by lc.code,lc.name ";
        sql += " order by lc.code ";
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        return list;
    }

    public Template exportPlanExecuteResult(List list, PatrolStatConditionBean bean)
            throws Exception {
        String urlPath = ReportConfig.getInstance().getUrlPath(
                "report.contractor_patrol_plan_execute_result");
        ExcelStyle excelstyle = new ExcelStyle(urlPath);
        PlanExecuteResultTemplate template = new PlanExecuteResultTemplate(urlPath);
        template.exportPlanExecuteResult(list, bean, excelstyle);
        logger.info("输出excel成功");
        return template;
    }

    public JFreeChart createContractorYearPatrolRateChart(PatrolStatConditionBean bean) {
        // TODO Auto-generated method stub
        String sql = " select lpad(level,2,0),'0' as patrolp from dual ";
        sql += " connect by level<13 ";
        List allList = monthlyStatDAOImpl.getBackInfoList(sql);
        sql = " select to_char(pcs.factdate,'MM') as month,to_char(pcs.patrolp) as patrolp ";
        sql += " from plan_cstat pcs ";
        sql += " where pcs.cdeptid='" + bean.getConID() + "' ";
        sql += " and to_char(pcs.factdate,'YYYY')='" + bean.getEndYear() + "' ";
        sql += " order by pcs.factdate ";
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        list = processList(allList, list);
        ChartParameter parameter = new ChartParameter();
        parameter.setChartType(ChartParameter.LINE_CHART_TYPE);
        parameter.setTitle(bean.getConName() + bean.getEndYear() + "年巡检率统计分析");
        parameter.setXTitle("月份");
        parameter.setYTitle("巡检率");
        List chartColors = new ArrayList();
        List chartLabels = new ArrayList();
        chartColors.add(ChartParameter.REF_COLORS[1]);
        chartLabels.add("巡检率");
        parameter.setChartColors(chartColors);
        parameter.setChartLabels(chartLabels);
        parameter.setLabelDisplayFlag(true);
        parameter.setDataSet(ChartUtils.createDataSet(list, parameter));
        parameter.setYMinValue(ChartParameter.Y_MIN_RATE_VALUE);
        parameter.setYMaxValue(ChartParameter.Y_MAX_RATE_VALUE);
        JFreeChart chart = ChartUtils.generateLineChart(parameter);
        return chart;
    }

    public JFreeChart createContractorFiveYearPatrolRateChart(PatrolStatConditionBean bean) {
        // TODO Auto-generated method stub
        GregorianCalendar calendar = new GregorianCalendar();
        int nowYear = calendar.get(GregorianCalendar.YEAR);
        if (bean.getEndYear() != null) {
            nowYear = Integer.parseInt(bean.getEndYear());
        }
        String sql = " select to_char(" + nowYear + "-level+1) as year,'0' as patrolp from dual ";
        sql += " connect by level<=" + YEAR_NUMBER + " ";
        sql += " order by level desc ";
        List allList = monthlyStatDAOImpl.getBackInfoList(sql);
        sql = " select to_char(pcs.factdate,'YYYY') as year,to_char(pcs.patrolp) as patrolp ";
        sql += " from plan_cstat pcs ";
        sql += " where pcs.cdeptid='" + bean.getConID() + "' ";
        sql += " and to_number(to_char(pcs.factdate,'MM'))=";
        sql += bean.getEndMonth() + " ";
        sql += " and to_number(to_char(pcs.factdate,'YYYY'))<=";
        sql += bean.getEndYear() + " ";
        sql += " order by pcs.factdate ";
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        list = processList(allList, list);
        ChartParameter parameter = new ChartParameter();
        parameter.setChartType(ChartParameter.LINE_CHART_TYPE);
        parameter.setTitle(bean.getConName() + "最近五年" + bean.getEndMonth() + "月份巡检率对比分析");
        parameter.setXTitle("年份");
        parameter.setYTitle("巡检率");
        List chartColors = new ArrayList();
        List chartLabels = new ArrayList();
        chartColors.add(ChartParameter.REF_COLORS[1]);
        chartLabels.add("巡检率");
        parameter.setChartColors(chartColors);
        parameter.setChartLabels(chartLabels);
        parameter.setLabelDisplayFlag(true);
        parameter.setDataSet(ChartUtils.createDataSet(list, parameter));
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

    public List showYearLayingMethodExecuteResultInfo(PatrolStatConditionBean bean,
            UserInfo userInfo) {
        // TODO Auto-generated method stub
        String sql = " select lc.code,lc.lable as name, ";
        sql += " decode(sum(pss.planpoint),null,0,sum(pss.planpoint)) as planpoint, ";
        sql += " decode(sum(pss.factpoint),null,0,sum(pss.factpoint)) as factpoint, ";
        sql += " decode(sum(pss.sublinekm),null,0,sum(pss.sublinekm)) as sublinekm, ";
        sql += " decode(sum(pss.factkm),null,0,sum(pss.factkm)) as patrolkm, ";
        sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') as patrolp ";
        sql += " from plan_sublinestat pss,sublineinfo si,lineinfo li,patrolmaninfo pi,contractorinfo ci,dictionary_formitem lc ";
        sql += " where pss.sublineid=si.sublineid and si.lineid=li.lineid and li.linetype=lc.code and lc.assortment_id='cabletype'";
        sql += " and pi.patrolid=si.patrolid and pi.parentid=ci.contractorid ";
        sql += " and ci.contractorid='" + bean.getConID() + "' ";
        sql += " and to_char(pss.factdate,'yyyy')='" + bean.getEndYear() + "' ";
        sql += " group by lc.code,lc.lable ";
        sql += " order by lc.code ";
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        return list;
    }

}
