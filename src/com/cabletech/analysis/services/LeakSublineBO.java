package com.cabletech.analysis.services;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.analysis.dao.LeakSublineDAOImpl;

public class LeakSublineBO extends BaseBisinessObject {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private String sql = null;

    private LeakSublineDAOImpl leakSublineDAOImpl = null;

    private String monthHead;

    private List allSublineList;

    private List planedSublineList;

    private ArrayList leakSublineList;

    public LeakSublineBO() {
        leakSublineDAOImpl = new LeakSublineDAOImpl();
    }

    // 得到市移动用户显示市内各代维公司漏做巡检任务结果
    public List getLeakSublineList412(String regionID) {
        String condition = " and c.regionid='" + regionID + "'";
        getLeakSublineList(condition, "12");
        sql = "select '' AS contractorid,'' AS contractorname,0 AS tnumber,0 AS pnumber,0 AS leaknumber from dual";
        List list = leakSublineDAOImpl.getResultList(sql);
        List contratorsList = getContractors(condition);
        try {
            if (list != null && list.size() > 0) {
                DynaBean bean = (BasicDynaBean) list.get(0);
                DynaBean oneBean;
                list = new ArrayList();
                String contractorId;
                String contractorName;
                int allSublineNumber = 0;
                int planedSublineNumber = 0;
                int leakSublineNumber = 0;
                // 对每个巡检组的所有线段数量、已经计划线段数量、漏做任务线段数量进行统计
                Map allSublineMap = calculateSublineNumber(allSublineList, "12");
                Map planedSublineMap = calculateSublineNumber(planedSublineList, "12");
                Map leakSublineMap = calculateSublineNumber(leakSublineList, "12");
                if (allSublineMap != null) {
                    for (int i = 0; contratorsList != null && i < contratorsList.size(); i++) {
                        DynaBean newBean = new BasicDynaBean(bean.getDynaClass());
                        oneBean = (DynaBean) contratorsList.get(i);
                        contractorId = (String) oneBean.get("contractorid");
                        contractorName = (String) oneBean.get("contractorname");
                        if (allSublineMap.get(contractorId) != null) {
                            allSublineNumber = ((Integer) allSublineMap.get(contractorId))
                                    .intValue();
                            if (planedSublineMap.get(contractorId) != null) {
                                planedSublineNumber = ((Integer) planedSublineMap.get(contractorId))
                                        .intValue();
                            } else {
                                planedSublineNumber = 0;
                            }
                            if (leakSublineMap.get(contractorId) != null) {
                                leakSublineNumber = ((Integer) leakSublineMap.get(contractorId))
                                        .intValue();
                            } else {
                                leakSublineNumber = 0;
                            }
                            newBean.set("contractorid", contractorId);
                            newBean.set("contractorname", contractorName);
                            newBean.set("tnumber", BigDecimal.valueOf(allSublineNumber));
                            newBean.set("pnumber", BigDecimal.valueOf(planedSublineNumber));
                            newBean.set("leaknumber", BigDecimal.valueOf(leakSublineNumber));
                            list.add(newBean);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            return list;
        }
    }

    // 得到代维用户显示市内各巡检组漏做巡检任务结果
    public List getLeakSublineList422(String regionID, String contractorID) {
        String condition = " and p.parentid='" + contractorID + "' and p.regionid='" + regionID
                + "'";
        getLeakSublineList(condition, "22");
        sql = "select '' AS patrolid,'' AS patrolname,0 AS tnumber,0 AS pnumber,0 AS leaknumber from dual";
        List list = leakSublineDAOImpl.getResultList(sql);
        List patrolList = getPatrolmans(condition);
        try {
            if (list != null && list.size() > 0) {
                DynaBean bean = (DynaBean) list.get(0);
                DynaBean oneBean;
                list = new ArrayList();
                String patrolId;
                String patrolName;
                int allSublineNumber = 0;
                int planedSublineNumber = 0;
                int leakSublineNumber = 0;
                // 对每个巡检组的所有线段数量、已经计划线段数量、漏做任务线段数量进行统计
                Map allSublineMap = calculateSublineNumber(allSublineList, "22");
                Map planedSublineMap = calculateSublineNumber(planedSublineList, "22");
                Map leakSublineMap = calculateSublineNumber(leakSublineList, "22");
                if (allSublineMap != null) {
                    for (int i = 0; patrolList != null && i < patrolList.size(); i++) {
                        DynaBean newBean = new BasicDynaBean(bean.getDynaClass());
                        oneBean = (DynaBean) patrolList.get(i);
                        patrolId = (String) oneBean.get("patrolid");
                        patrolName = (String) oneBean.get("patrolname");
                        if (allSublineMap.get(patrolId) != null) {
                            allSublineNumber = ((Integer) allSublineMap.get(patrolId)).intValue();
                            if (planedSublineMap.get(patrolId) != null) {
                                planedSublineNumber = ((Integer) planedSublineMap.get(patrolId))
                                        .intValue();
                            } else {
                                planedSublineNumber = 0;
                            }
                            if (leakSublineMap.get(patrolId) != null) {
                                leakSublineNumber = ((Integer) leakSublineMap.get(patrolId))
                                        .intValue();
                            } else {
                                leakSublineNumber = 0;
                            }
                            newBean.set("patrolid", patrolId);
                            newBean.set("patrolname", patrolName);
                            newBean.set("tnumber", BigDecimal.valueOf(allSublineNumber));
                            newBean.set("pnumber", BigDecimal.valueOf(planedSublineNumber));
                            newBean.set("leaknumber", BigDecimal.valueOf(leakSublineNumber));
                            list.add(newBean);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            return list;
        }
    }

    // 得到选定巡检组具体漏做计划的线段列表

    // 得到选定代维公司具体漏做计划的线段列表
    public List getLeakSublineDetail412(String conID) {
        String condition = " and c.contractorid='" + conID + "'";
        getLeakSublineList(condition, "12");
        return leakSublineList;
    }

    public List getLeakSublineDetail422(String patrolID) {
        String condition = " and p.patrolid='" + patrolID + "'";
        getLeakSublineList(condition, "22");
        return leakSublineList;
    }

    public String getRegionName(String regionID) {
        sql = "select regionname i from region where regionid='" + regionID + "' and state is null";
        String regionName = leakSublineDAOImpl.getStringBack(sql);
        return regionName;
    }

    public String getConName(String conID) {
        sql = "select contractorname i from contractorinfo where contractorid='" + conID
                + "' and state is null";
        String conName = leakSublineDAOImpl.getStringBack(sql);
        return conName;
    }

    private List getContractors(String condition) {
        sql = "select contractorname,contractorid from contractorinfo c where (state is null or state<>'1') "
                + condition;
        return leakSublineDAOImpl.getResultList(sql);
    }

    private List getPatrolmans(String condition) {
        sql = "select patrolname,patrolid from patrolmaninfo p where (state is null or state<>'1') "
                + condition;
        return leakSublineDAOImpl.getResultList(sql);
    }

    private Map calculateSublineNumber(List list, String userType) {
        Map map = new HashMap();
        String key = "";
        DynaBean bean;
        Integer sublineNumber = new Integer(0);
        if ("12".equals(userType)) {
            key = "contractorid";
        }
        if ("22".equals(userType)) {
            key = "patrolid";
        }
        for (int i = 0; list != null && i < list.size(); i++) {
            sublineNumber = new Integer(0);
            bean = (DynaBean) list.get(i);
            String mappedKey = (String) bean.get(key);
            if (map.containsKey(mappedKey)) {
                sublineNumber = (Integer) map.get(mappedKey);
            }
            sublineNumber = new Integer(sublineNumber.intValue() + 1);
            map.put(mappedKey, sublineNumber);
        }
        return map;
    }

    /**
     * 根据条件获取漏做巡检任务的巡检线段列表
     * 
     * @param condition
     *            String
     * @param userType
     *            String
     */
    private void getLeakSublineList(String condition, String userType) {
        allSublineList = getAllSublineList(condition, userType);
        planedSublineList = getPlanedSublineList(condition, userType);
        leakSublineList = new ArrayList();
        DynaBean allSublineBean = null;
        DynaBean planedSublineBean = null;
        boolean flag = false;
        for (int i = 0; allSublineList != null && i < allSublineList.size(); i++) {
            flag = false;
            allSublineBean = (DynaBean) allSublineList.get(i);
            String sublineId = (String) allSublineBean.get("sublineid");
            planedSublineBean = null;
            if (sublineId != null && !sublineId.equals("")) {
                for (int j = 0; planedSublineList != null && j < planedSublineList.size(); j++) {
                    planedSublineBean = (DynaBean) planedSublineList.get(j);
                    if (sublineId.equals(planedSublineBean.get("sublineid"))) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                leakSublineList.add(allSublineBean);
            }
        }
    }

    /**
     * 获取每个月的月头
     * 
     * @return String
     */
    private String getMonthHead() {
        Date date = new Date();
        int intYear = 1900 + date.getYear();
        int intMonth = date.getMonth() + 1;
        String theYearMonth = intYear + "-" + intMonth;
        // String theYearMonth ="2007-6";
        String theDate = theYearMonth + "-01";
        return theDate;
    }

    /**
     * 根据条件获取所有的巡检线段信息
     * 
     * @param condition
     *            String
     * @param userType
     *            String
     * @return List
     */
    private List getAllSublineList(String condition, String userType) {
        String sqlBuffer = generateAllSublineSql(condition, userType);
        List allSublineList = leakSublineDAOImpl.getResultList(sqlBuffer);
        return allSublineList;
    }

    /**
     * 根据条件获取所有制定计划的巡检线段信息
     * 
     * @param condition
     *            String
     * @param userType
     *            String
     * @return List
     */
    private List getPlanedSublineList(String condition, String userType) {
        String sqlBuffer = generatePlanedSublineSql(condition, userType);
        List planedSublineList = leakSublineDAOImpl.getResultList(sqlBuffer);
        return planedSublineList;
    }

    /**
     * 根据输入产生对应的sql语句
     * 
     * @param condition
     * @param userType
     * @return String
     */
    private String generateAllSublineSql(String condition, String userType) {
        StringBuffer sqlBuffer = new StringBuffer();
        if ("12".equals(userType)) {
            sqlBuffer
                    .append("select c.contractorid,c.contractorname,s.sublineid,s.lineid,s.sublinename,s.ruledeptid,s.linetype,s.checkpoints,s.regionid,s.state,s.patrolid,s.remark,l.linename from sublineinfo s,lineinfo l,contractorinfo c,patrolmaninfo p ");
            sqlBuffer.append("where c.contractorid=p.parentid and p.patrolid=s.patrolid ");
            sqlBuffer
                    .append("and (p.state is null or p.state<>'1') and (c.state is null or c.state<>'1') ");
        }
        if ("22".equals(userType)) {
            sqlBuffer
                    .append("select p.patrolid,p.patrolname,s.sublineid,s.lineid,s.sublinename,s.ruledeptid,s.linetype,s.checkpoints,s.regionid,s.state,s.patrolid,s.remark,l.linename from sublineinfo s,lineinfo l,patrolmaninfo p ");
            sqlBuffer.append("where p.patrolid=s.patrolid and (p.state is null or p.state<>'1') ");
        }
        sqlBuffer.append("and (s.state is null or s.state<>'1') and s.lineid=l.lineid ");
        sqlBuffer.append(condition);
        if ("12".equals(userType)) {
            //sqlBuffer.append(" order by c.contractorid");
        }
        if ("22".equals(userType)) {
            //sqlBuffer.append(" order by p.patrolid");
        }
        return sqlBuffer.toString();
    }

    /**
     * 根据输入产生对应的sql语句
     * 
     * @param condition
     * @param userType
     * @return String
     */
    private String generatePlanedSublineSql(String condition, String userType) {
        StringBuffer sqlBuffer = new StringBuffer();
        String monthHead = getMonthHead();
        if ("12".equals(userType)) {
            sqlBuffer
                    .append("select distinct c.contractorid,s.sublineid,s.lineid,s.sublinename,s.ruledeptid,s.linetype,s.checkpoints,s.regionid,s.state,s.patrolid,s.remark from plantasksubline ps,sublineinfo s,contractorinfo c,patrolmaninfo p ");
            sqlBuffer
                    .append("where ps.sublineid=s.sublineid and s.patrolid=p.patrolid and p.parentid=c.contractorid ");
            sqlBuffer
                    .append("and (p.state is null or p.state<>'1') and (c.state is null or c.state<>'1') ");
        }
        if ("22".equals(userType)) {
            sqlBuffer
                    .append("select distinct p.patrolid,s.sublineid,s.lineid,s.sublinename,s.ruledeptid,s.linetype,s.checkpoints,s.regionid,s.state,s.patrolid,s.remark from plantasksubline ps,sublineinfo s,patrolmaninfo p ");
            sqlBuffer
                    .append("where ps.sublineid=s.sublineid and s.patrolid=p.patrolid and (p.state is null or p.state<>'1') ");
        }
        sqlBuffer.append("and (s.state is null or s.state<>'1') ");
        sqlBuffer.append(condition);
        sqlBuffer.append("and ps.taskid in ( ");
        sqlBuffer.append("  select taskid from plantasklist where planid in ( ");
        sqlBuffer.append("    select id from plan where executorid=s.patrolid ");
        sqlBuffer.append("    and begindate>=to_date('");
        sqlBuffer.append(monthHead);
        sqlBuffer.append("    ','yyyy-mm-dd') and enddate<=last_day(to_date('");
        sqlBuffer.append(monthHead);
        sqlBuffer.append("    ','yyyy-mm-dd'))");
        sqlBuffer.append("  ) ");
        sqlBuffer.append(") ");
        if ("12".equals(userType)) {
            //sqlBuffer.append(" order by c.contractorid");
        }
        if ("22".equals(userType)) {
            //sqlBuffer.append(" order by p.patrolid");
        }
        return sqlBuffer.toString();
    }

    public List getLeakSublineList412Waste(String regionID) {
        Date date = new Date();
        int intYear = 1900 + date.getYear();
        int intMonth = date.getMonth() + 1;
        String theYearMonth = intYear + "-" + intMonth;
        // String theYearMonth ="2007-6";
        String theDate = theYearMonth + "-01";
        sql = "select ttotal.contractorid,ttotal.contractorname,decode(ttotal.totalnumber,'',0,ttotal.totalnumber) tnumber,decode(tplan.plannumber,'',0,tplan.plannumber) pnumber,(totalnumber - decode(tplan.plannumber,'',0,tplan.plannumber)) as leaknumber "
                + "from (SELECT   cp.contractorid contractorid,cp.contractorname contractorname,count(s.sublineid) totalnumber "
                + "FROM sublineinfo s, "
                + "( "
                + "SELECT contractorid, contractorname,p.patrolid "
                + "FROM contractorinfo c, patrolmaninfo p "
                + "WHERE c.CONTRACTORID = p.PARENTID and p.regionid = c.regionid and  "
                + "p.regionid = '"
                + regionID
                + "' AND p.state IS NULL and c.state is null "
                + " ) cp "
                + "WHERE s.patrolid = cp.patrolid and s.regionid = '"
                + regionID
                + "' AND state IS NULL "
                + "group by cp.contractorid,cp.contractorname "
                + "order by cp.contractorid ) ttotal,  "
                + "(SELECT   ps.parentid parentid, COUNT (distinct pts.sublineid) plannumber "
                + "FROM plantasksubline pts, "
                + "taskinfo t, "
                + "plantasklist ptl, "
                + "PLAN p, "
                + "(SELECT p.patrolid,p.parentid,p.regionid,c.contractorname from patrolmaninfo p,contractorinfo c where p.parentid = c.contractorid and p.regionid = '"
                + regionID
                + "' and c.state is null and p.state is null) ps "
                + "WHERE p.EXECUTORID = ps.patrolid "
                + "and t.ID = ptl.taskid "
                + "AND ptl.planid = p.ID "
                + "AND pts.taskid = t.ID "
                + "AND t.regionid = '"
                + regionID
                + "' "
                + "AND p.begindate >= TO_DATE ('"
                + theDate
                + "', 'yyyy-mm-dd') "
                + "AND p.enddate <= last_day(to_date('"
                + theYearMonth
                + "', 'yyyy-mm')) "
                + "GROUP BY ps.parentid order by ps.parentid) tplan "
                + "where ttotal.contractorid = tplan.parentid(+)";
        logger.info("为市移动用户显示市内各代维公司漏做巡检任务结果SQL:" + sql);
        List list = leakSublineDAOImpl.getResultList(sql);
        return list;
    }

    public List getLeakSublineDetail412Waste(String conID) {
        Date date = new Date();
        int intYear = 1900 + date.getYear();
        int intMonth = date.getMonth() + 1;
        String theYearMonth = intYear + "-" + intMonth;
        // String theYearMonth ="2007-6";
        String theDate = theYearMonth + "-01";
        sql = "select distinct s.sublineid,s.sublinename,l.linename from sublineinfo s,lineinfo l where s.lineid = l.lineid and s.patrolid in (select distinct patrolid from patrolmaninfo where parentid='"
                + conID
                + "') and s.state is null and l.state is null "
                + "minus "
                + "SELECT distinct pts.sublineid,s.sublinename,l.linename FROM plantasksubline pts,sublineinfo s,lineinfo l "
                + "WHERE pts.sublineid = s.sublineid and s.lineid= l.lineid and s.state is null and l.state is null "
                + "and pts.taskid IN ( "
                + "SELECT taskid "
                + "FROM plantasklist "
                + "WHERE planid IN ( "
                + "SELECT ID "
                + "FROM PLAN p "
                + "WHERE p.executorid in (select distinct patrolid from patrolmaninfo where parentid='"
                + conID
                + "') "
                + "AND p.begindate >= TO_DATE ('"
                + theDate
                + "', 'yyyy-mm-dd') "
                + "AND p.enddate <= " + "LAST_DAY (TO_DATE ('" + theYearMonth + "', 'yyyy-mm')))) ";
        logger.info("得到选定代维公司具体漏做计划的线段列表SQL:" + sql);
        List list = leakSublineDAOImpl.getResultList(sql);
        return list;
    }

    public List getLeakSublineDetail422Waste(String patrolID) {
        Date date = new Date();
        int intYear = 1900 + date.getYear();
        int intMonth = date.getMonth() + 1;
        String theYearMonth = intYear + "-" + intMonth;
        // String theYearMonth ="2007-6";
        String theDate = theYearMonth + "-01";
        sql = "select distinct s.sublineid,s.sublinename,l.linename from sublineinfo s,lineinfo l where s.lineid = l.lineid and s.patrolid = '"
                + patrolID
                + "' and s.state is null and l.state is null "
                + "minus "
                + "SELECT distinct pts.sublineid,s.sublinename,l.linename FROM plantasksubline pts,sublineinfo s,lineinfo l "
                + "WHERE pts.sublineid = s.sublineid and s.lineid= l.lineid and s.state is null and l.state is null "
                + "and pts.taskid IN ( "
                + "SELECT taskid "
                + "FROM plantasklist "
                + "WHERE planid IN ( "
                + "SELECT ID "
                + "FROM PLAN p "
                + "WHERE p.executorid = '"
                + patrolID
                + "' "
                + "AND p.begindate >= TO_DATE ('"
                + theDate
                + "', 'yyyy-mm-dd') "
                + "AND p.enddate <= " + "LAST_DAY (TO_DATE ('" + theYearMonth + "', 'yyyy-mm')))) ";
        logger.info("得到选定巡检组具体漏做计划的线段列表SQL:" + sql);
        List list = leakSublineDAOImpl.getResultList(sql);
        return list;
    }

    public List getLeakSublineList422Waste(String regionID, String contractorID) {
        Date date = new Date();
        int intYear = 1900 + date.getYear();
        int intMonth = date.getMonth() + 1;
        String theYearMonth = intYear + "-" + intMonth;
        // String theYearMonth ="2007-6";
        String theDate = theYearMonth + "-01";
        sql = "select ttotal.patrolid,ttotal.patrolname,decode(ttotal.totalnumber,'',0,ttotal.totalnumber) tnumber,decode(tplan.plannumber,'',0,tplan.plannumber) pnumber,(totalnumber - decode(tplan.plannumber,'',0,tplan.plannumber)) as leaknumber "
                + "from (select p.patrolid,p.patrolname,count(s.sublineid) totalnumber from sublineinfo s,patrolmaninfo p where s.PATROLID = p.PATROLID and p.parentid='"
                + contractorID
                + "' "
                + "and s.REGIONID='"
                + regionID
                + "' and p.REGIONID = s.regionid and p.state is null and s.state is null "
                + "group by p.patrolid,p.patrolname order by p.patrolid) ttotal,  "
                + "(SELECT   pm.patrolid patrolid, COUNT (distinct pts.sublineid) plannumber "
                + "FROM plantasksubline pts, "
                + "taskinfo t, "
                + "plantasklist ptl, "
                + "PLAN p, "
                + "patrolmaninfo pm "
                + "WHERE p.EXECUTORID = pm.patrolid "
                + "and t.ID = ptl.taskid "
                + "AND ptl.planid = p.ID "
                + "AND pts.taskid = t.ID "
                + "AND t.regionid = '"
                + regionID
                + "' "
                + "and p.regionid = t.regionid "
                + "and pm.parentid='"
                + contractorID
                + "' "
                + "and pm.state is null "
                + "AND p.begindate >= TO_DATE ('"
                + theDate
                + "', 'yyyy-mm-dd') "
                + "AND p.enddate <= last_day(to_date('"
                + theYearMonth
                + "', 'yyyy-mm')) "
                + "GROUP BY pm.patrolid order by pm.patrolid) tplan "
                + "where ttotal.patrolid = tplan.patrolid(+)";
        logger.info("为代维用户显示市内各巡检组漏做巡检任务结果SQL:" + sql);
        List list = leakSublineDAOImpl.getResultList(sql);
        return list;
    }

}
