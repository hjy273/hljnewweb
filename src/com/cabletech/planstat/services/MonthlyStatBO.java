package com.cabletech.planstat.services;

import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.chart.parameter.ChartParameter;
import com.cabletech.commons.chart.utils.ChartUtils;
import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import java.util.List;

import com.cabletech.planinfo.beans.YearMonthPlanBean;
import com.cabletech.planinfo.services.YearMonthPlanBO;
import com.cabletech.planstat.dao.MonthlyStatDAOImpl;
import com.cabletech.planstat.template.PlanExecuteResultTemplate;
import com.cabletech.planstat.beans.PatrolStatConditionBean;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

public class MonthlyStatBO extends BaseBisinessObject {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private String sql = null;

    public static final String MONTH_SPACE = "-2"; // ��ǰѡ���·���ǰ������Ƶ��·�����,"-"�ű�ʾ��ǰ��

    private MonthlyStatDAOImpl monthlyStatDAOImpl = null;

    public MonthlyStatBO() {
        monthlyStatDAOImpl = new MonthlyStatDAOImpl();
    }

    // �õ�����������Ѳ��Ա�б�
    public List getPatrolmanInfoList(UserInfo userinfo) {
        if (userinfo.getType().equals("22")) {
            sql = "select p.patrolid, p.patrolname, p.parentid " + " from patrolmaninfo p "
                    + " where p.STATE is null " + " and p.regionid='" + userinfo.getRegionID()
                    + "'" + " and p.parentid='" + userinfo.getDeptID() + "'"
                    + " order by p.parentid,p.patrolname";
        }
        logger.info("Ѳ��Ա�б�SQL:" + sql);

        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    // �õ���ѡѲ��Ա��ѡ���·ݵ���ͳ������Ϣ
    public List getPlanMonthlyStatOverAll(UserInfo userinfo, PatrolStatConditionBean bean) {
        String theStartDate = getFirst2Month(bean);
        sql = "select pp.executorid," + "to_char(pp.factdate, 'YYYY-MM') statdate,pp.planpoint,"
                + "pp.factpoint,pp.examineresult,pp.trouble,pp.overallpatrolp"
                + " from plan_patrolstat pp " + " where pp.regionid='" + userinfo.getRegionID()
                + "'" + " and pp.executorid = '" + bean.getPatrolID() + "'"
                + " and (pp.factdate >= TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd') "
                + "AND pp.factdate <= last_day(TO_DATE ('" + theStartDate + "', 'yyyy-mm-dd'))) "
                + " order by pp.ppid";
        logger.info("Ѳ��Ա��ͳ��������ϢSQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    // �õ���ѡѲ��Ա��ĳ���·���������ɵļƻ��б�
    public List getPlanMonthlyStatDetail(UserInfo userinfo, PatrolStatConditionBean bean) {
        String theStartDate = getFirst2Month(bean);
        // String theEndDate = getLast2Month(bean);
        sql = "select ps.planid,ps.planname," + "to_char(ps.startdate, 'YYYY-MM-DD') startdate,"
                + "to_char(ps.enddate, 'YYYY-MM-DD') enddate,"
                + "ps.planpoint,ps.factpoint,ps.examineresult,"
                + "ps.patrolkm,ps.PATROLP || '%' as patrolp" + " from plan_stat ps"
                + " where ps.planid in" + " ( select psp.planid" + " from planstat_patrolstat psp"
                + " where psp.ppid in" + " ( select pp.ppid" + " from plan_patrolstat pp"
                + " where (pp.factdate >= TO_DATE ('" + theStartDate
                + "', 'yyyy-mm-dd') AND pp.factdate <= last_day(TO_DATE ('" + theStartDate
                + "', 'yyyy-mm-dd')))" + " and pp.executorid = '" + bean.getPatrolID() + "') )"
                + " AND (ps.enddate >= TO_DATE ('" + theStartDate
                + "', 'yyyy-mm-dd') AND ps.enddate <= last_day(TO_DATE ('" + theStartDate
                + "', 'yyyy-mm-dd')))" + " order by ps.planid";
        logger.info("ͳ��Ѳ��Աĳ������������ɵļƻ��б�SQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    // �õ������������д�ά��λ�б�
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
        logger.info("�д�ά��λ�б�SQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    // �õ���ѡ�д�ά��˾��ѡ���·ݵ���ͳ������Ϣ

    // �õ��������������ƶ��б�
    public List getMobileInfoList(UserInfo userinfo) {
        if (userinfo.getType().equals("11")) {
            sql = "select d.deptid,d.deptname " + "from deptinfo d " + "where d.parentid in( "
                    + "select e.deptid " + "from deptinfo e " + "where e.regionid like '%0000' "
                    + "and e.state is null ) " + "and d.regionid not like '%0000' "
                    + "and d.deptname like '%�ƶ�%' " + "and d.state is null "
                    + "order by d.deptname";

        }
        logger.info("���ƶ���˾�б�SQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    // �õ���ѡ�д�ά��˾��ѡ���·ݵ���ͳ������Ϣ
    public List getPlanConMonthlyStat(UserInfo userinfo, PatrolStatConditionBean bean) {
        String theStartDate = getFirst2Month(bean);
        String theEndDate = getLast2Month(bean);
        sql = " select to_char(pc.factdate, 'YYYY-MM') statdate,"
                + " pc.cdeptid,pc.planpoint,pc.factpoint,pc.planpointn,pc.factpointn,pc.patrolp,pc.trouble,pc.keypoint,"
                + " pc.leakkeypoint,pc.leakpoint,pc.sublinekm,pc.patrolkm ,pc.examineresult"
                + " from plan_cstat pc " + " where pc.cdeptid = '" + bean.getConID()
                + "' "
                +
                // "and to_char(pc.factdate, 'YYYY-MM-DD') like '" +
                // bean.getEndYear() + "-" + bean.getEndMonth() + "%' ";
                " and pc.factdate between to_date('" + theStartDate
                + "', 'yyyy-mm-dd') and last_day(to_date('" + theStartDate + "', 'yyyy-mm-dd'))";
        if (userinfo.getType().equals("11")) {
            sql += "and pc.regionid = '" + bean.getRegionId() + "' ";
        } else if (userinfo.getType().equals("12")) {
            sql += "and pc.regionid = '" + userinfo.getRegionID() + "' ";
        }
        logger.info("�д�ά��˾��ͳ��������ϢSQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    // �õ���ѡ���ƶ���˾��ѡ���·ݵ���ͳ������Ϣ
    public List getMobileMonthlyStatOverAll(UserInfo userinfo, PatrolStatConditionBean bean) {
        String theStartDate = getFirst2Month(bean);
        sql = "select to_char(pm.factdate, 'YYYY-MM') statdate, pm.overallpatrolp "
                + "from plan_mstat pm " + " where (pm.factdate >= TO_DATE ('" + theStartDate
                + "', 'yyyy-mm-dd') AND pm.factdate <= last_day(TO_DATE ('" + theStartDate
                + "', 'yyyy-mm-dd'))) " + "and pm.deptid = '" + bean.getMobileID() + "'";
        logger.info("���ƶ���˾��ͳ��������ϢSQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    // �õ���ѡ���ƶ���˾��ĳ���·���������ɵļƻ��б�
    public List getMobileMonthlyStatDetail(UserInfo userinfo, PatrolStatConditionBean bean) {
        String theStartDate = getFirst2Month(bean);
        sql = "select c.contractorname,pc.patrolp,pc.trouble,pc.leakpoint "
                + "from plan_cstat pc,contractorinfo c " + "where pc.cstatid in "
                + "(select pmc.cstatid " + "from planstat_mc pmc " + "where pmc.mstatid in "
                + "(select pm.mstatid " + "from plan_mstat pm " + "where pm.deptid = '"
                + bean.getMobileID() + "' " + " and (pm.factdate >= TO_DATE ('" + theStartDate
                + "', 'yyyy-mm-dd') AND pm.factdate <= last_day(TO_DATE ('" + theStartDate
                + "', 'yyyy-mm-dd')))) " + "and pc.cdeptid = c.contractorid ) and c.state is null "
                + "order by pc.cdeptid";

        logger.info("ͳ�����ƶ���˾ĳ������������ɵļƻ��б�SQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    // �õ�������������·�б�
    public List getLineInfoList(UserInfo userinfo) {
        if (userinfo.getType().equals("12")) {
            sql = "select l.lineid, l.linename,l.regionid " + "from lineinfo l "
                    + "where l.regionid = '" + userinfo.getRegionID() + "' "
                    + "and l.state is null " + "order by l.linename";
        } else {
            if (userinfo.getType().equals("22")) {
                sql = "select l.lineid, l.linename,l.regionid " + "from lineinfo l "
                        + "where l.lineid in " + "( select s.lineid " + "from sublineinfo s "
                        + "where s.patrolid in( select p.patrolid " + "from patrolmaninfo p "
                        + "where p.parentid = '" + userinfo.getDeptID() + "' "
                        + "and p.state is null ) " + "and s.state is null ) "
                        + "and l.state is null " + "order by l.linename";
            } else if (userinfo.getType().equals("11")) {
                sql = "select l.lineid, l.linename,l.regionid " + "from lineinfo l "
                        + "where l.state is null " + "order by l.linename";
            }
        }
        logger.info("��·�б�SQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    // �õ������������߶��б�
    public List getSublineInfoList(UserInfo userinfo) {
        if (userinfo.getType().equals("12")) {
            sql = "select s.sublineid, s.sublinename,s.lineid,s.regionid " + "from sublineinfo s "
                    + "where s.state is null " + "and s.regionid ='" + userinfo.getRegionID()
                    + "' " + "order by s.lineid,s.sublinename";
        } else {
            if (userinfo.getType().equals("22")) {
                sql = "select s.sublineid,s.sublinename,s.lineid,s.regionid "
                        + "from sublineinfo s " + "where s.patrolid in( select p.patrolid "
                        + "from patrolmaninfo p " + "where p.parentid = '" + userinfo.getDeptID()
                        + "' " + "and p.state is null ) " + "and s.state is null "
                        + "order by s.lineid,s.sublinename ";
            } else if (userinfo.getType().equals("11")) {
                sql = "select s.sublineid, s.sublinename,s.lineid,s.regionid "
                        + "from sublineinfo s " + "where s.state is null "
                        + "order by s.lineid,s.sublinename";
            }
        }
        logger.info("�߶��б�SQL:" + sql);
        ResultSet resultset = monthlyStatDAOImpl.getBackInfoListJDBC(sql);
        try {
            List list = this.resultSetToList(resultset);
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List resultSetToList(ResultSet rs) throws java.sql.SQLException {
        if (rs == null)
            return Collections.EMPTY_LIST;
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        List list = new ArrayList();
        Map rowData;
        while (rs != null && rs.next()) {
            rowData = new HashMap(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getString(i));
            }
            list.add(rowData);
        }
        return list;
    }

    // �õ���ѡ�߶���ѡ���·ݵ���ͳ������Ϣ
    public List getSublineMonthlyStat(UserInfo userinfo, PatrolStatConditionBean bean) {
        String theStartDate = getFirst2Month(bean);
        sql = "select ps.sublineid,s.sublinename,p.patrolname,ps.lineid,c.contractorname,"
                + "to_char(ps.factdate, 'YYYY-MM') statdate," + "ps.planpoint," + "ps.patrolp,"
                + "ps.factpoint," + "ps.factkm," + "ps.keypoint," + "ps.leakpoint,"
                + "ps.leakkeypoint," + "ps.sublinekm "
                + "from plan_sublinestat ps,sublineinfo s,patrolmaninfo p,contractorinfo c "
                + "where ps.sublineid=s.sublineid and " + "p.patrolid=s.patrolid and "
                + "c.contractorid=p.parentid and " + "c.state is null and "
                + "p.state is null and " + "s.state is null and " + "ps.sublineid = '"
                + bean.getSublineID() + "' " + "and (ps.factdate >= TO_DATE ('" + theStartDate
                + "', 'yyyy-mm-dd') AND ps.factdate <= last_day(TO_DATE ('" + theStartDate
                + "', 'yyyy-mm-dd')))";
        logger.info("�߶���ͳ��������ϢSQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    // �õ���ѡ�߶���ѡ���·ݵ���ͳ������Ϣ
    public List getLineMonthlyStat(UserInfo userinfo, PatrolStatConditionBean bean) {
        String theStartDate = getFirst2Month(bean);
        sql = "select pl.lineid," + "to_char( pl.factdate, 'YYYY-MM ')statdate," + "pl.sublinen,"
                + "pl.linekm," + "pl.planpoint," + "pl.factpoint," + "pl.patrolp," + "pl.factkm "
                + "from plan_linestat pl " + "where pl.lineid = '" + bean.getLineID() + "' "
                + "and (pl.factdate >= TO_DATE ('" + theStartDate
                + "', 'yyyy-mm-dd') AND pl.factdate <= last_day(TO_DATE ('" + theStartDate
                + "', 'yyyy-mm-dd')))";
        logger.info("��·��ͳ��������ϢSQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    public List getRegionInfoList() {
        String sql = "select  r.REGIONNAME, r.REGIONID "
                + " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
        logger.info("����SQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    // ����ѡ�����·ID�õ������������߶��б�(AJAX��)
    public List getSublineByLineAjax(UserInfo userinfo, String lineID) {
        if (userinfo.getType().equals("12")) {
            sql = "select s.sublineid, s.sublinename,s.lineid,s.regionid " + "from sublineinfo s "
                    + "where s.state is null " + "and s.regionid ='" + userinfo.getRegionID()
                    + "' ";
            if (!"".equals(lineID)) {
                sql += "and s.lineid = '" + lineID + "' ";
            }
            sql += "order by s.lineid,s.sublinename";
        } else {
            if (userinfo.getType().equals("22")) {
                sql = "select s.sublineid,s.sublinename,s.lineid,s.regionid "
                        + "from sublineinfo s " + "where s.patrolid in( select p.patrolid "
                        + "from patrolmaninfo p " + "where p.parentid = '" + userinfo.getDeptID()
                        + "' " + "and p.state is null ) " + "and s.state is null ";
                if (!"".equals(lineID)) {
                    sql += "and s.lineid = '" + lineID + "' ";
                }
                sql += "order by s.lineid,s.sublinename";
            } else if (userinfo.getType().equals("11")) {
                sql = "select s.sublineid, s.sublinename,s.lineid,s.regionid "
                        + "from sublineinfo s " + "and s.lineid = '" + lineID + "' "
                        + "where s.state is null ";
                if (!"".equals(lineID)) {
                    sql += "and s.lineid = '" + lineID + "' ";
                }
                sql += "order by s.lineid,s.sublinename";
            }
        }
        logger.info("�߶��б�(AJAX)SQL:" + sql);
        ResultSet resultset = monthlyStatDAOImpl.getBackInfoListJDBC(sql);
        try {
            List list = this.resultSetToList(resultset);
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // ��ȡѲ��ƻ�ִ�����
    public List getExceuteResult(UserInfo userinfo, PatrolStatConditionBean bean) {
        String theStartDate = getFirst2Month(bean);
        String sql = "select ps.planid,ps.planname,ps.patrolp,pm.patrolname executorid,ps.planpoint,ps.factpoint,ps.planpointn,ps.factpointn,ps.examineresult,ps.troublenum  from plan_stat ps,patrolmaninfo pm where ps.executorid = pm.patrolid and pm.parentid='"
                + bean.getConID()
                + "' and ps.enddate <= last_day(TO_DATE ('"
                + theStartDate
                + "', 'yyyy-mm-dd')) and ps.startdate >= to_date('"
                + theStartDate
                + "','YYYY-MM-DD')";
        if (bean.getPatrolID() != null && !bean.getPatrolID().equals("")) {
            sql += " and pm.patrolid='" + bean.getPatrolID() + "' ";
        }
        sql += " order by ps.patrolp desc ";
        logger.info("sql " + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        return list;

    }

    // ͨ�������߶�Ѳ����
    public List getSubLinePatrol(UserInfo userinfo, PatrolStatConditionBean bean) {
        String theStartDate = getFirst2Month(bean);
        String sql = "select l.linename,pm.patrolname,sl.sublinename,sl.lineid,lc.name as linetype ,pm.patrolid,pm.patrolname,pss.executetimes,pss.factpoint,pss.planpoint,pss.patrolp,pss.examineresult from plan_sublinestat pss,patrolmaninfo pm ,sublineinfo sl,lineinfo l,lineclassdic lc"
                + " where pm.parentid='"
                + bean.getConID()
                + "' and sl.patrolid=pm.patrolid and sl.sublineid=pss.sublineid and l.lineid = sl.lineid "
                + " and l.linetype=lc.code  "
                + " and factdate <= last_day(TO_DATE ('"
                + theStartDate
                + "', 'yyyy-mm-dd')) and factdate >=to_date('"
                + theStartDate
                + "','YYYY-MM-DD') and pss.examineresult >='3'"
                + " order by pm.patrolname,l.linename,l.linetype desc,sl.sublinename";
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        return list;
    }

    // δͨ�������߶�
    public List getNonPassSubline(UserInfo userinfo, PatrolStatConditionBean bean) {
        String theStartDate = getFirst2Month(bean);
        String sql = "select l.linename,pm.patrolname,sl.sublinename,sl.lineid,lc.name linetype ,pm.patrolid,pm.patrolname,pss.executetimes,pss.factpoint,pss.planpoint,pss.patrolp,pss.examineresult from plan_sublinestat pss,patrolmaninfo pm ,sublineinfo sl,lineinfo l,lineclassdic lc"
                + " where pm.parentid='"
                + bean.getConID()
                + "' and sl.patrolid=pm.patrolid and sl.sublineid=pss.sublineid and l.lineid = sl.lineid "
                + " and l.linetype=lc.code  "
                + " and pss.factdate <= last_day(TO_DATE ('"
                + theStartDate
                + "', 'yyyy-mm-dd')) and pss.factdate >=to_date('"
                + theStartDate
                + "','YYYY-MM-DD') and pss.examineresult <'3'"
                + " order by pm.patrolname,l.linename,l.linetype desc,sl.sublinename";
        logger.info("sql" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        return list;
    }

    // δ�ƻ��߶�
    public List getNonPlanSubline(UserInfo userinfo, PatrolStatConditionBean bean) {
        String theStartDate = getFirst2Month(bean);
        String sql = " select  sl.sublinename,lc.name linetype ,pm.patrolname,l.linename "
                + " from nonplansubline nl,sublineinfo sl,lineinfo l,lineclassdic lc,patrolmaninfo pm"
                + " where sl.patrolid=pm.patrolid and sl.sublineid=nl.sublineid and l.lineid = sl.lineid "
                + " and l.linetype=lc.code  " + " and factdate <= last_day(TO_DATE ('"
                + theStartDate + "', 'yyyy-mm-dd')) and factdate >=to_date('" + theStartDate
                + "','YYYY-MM-DD') and  pm.parentid='" + bean.getConID() + "'"
                + " order by pm.patrolname,l.linename,l.linename ,sl.sublinename";
        logger.info("sql" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        return list;
    }

    private String getFirst2Month(PatrolStatConditionBean bean) {
        String theStartDate = bean.getEndYear() + "-" + bean.getEndMonth() + "-1";
        return theStartDate;

    }

    private String getLast2Month(PatrolStatConditionBean bean) {
        // ����û��ڽ�������ѡ���·�Ϊ12������Ҫ�ı����.
        if ("12".equals(bean.getEndMonth())) {
            return String.valueOf(Integer.parseInt(bean.getEndYear()) + 1) + "-1-1";
        } else {
            return bean.getEndYear() + "-"
                    + String.valueOf(Integer.parseInt(bean.getEndMonth()) + 1) + "-1";
        }
    }

    // �¼ƻ���Ϣ
    public YearMonthPlanBean getMonthPlan(UserInfo userinfo, PatrolStatConditionBean bean)
            throws Exception {
        // ��üƻ�id
        String sql = "select id from yearmonthplan where year='" + bean.getEndYear()
                + "' and month='" + bean.getEndMonth() + "' and deptid='" + bean.getConID() + "'";
        logger.info("sql " + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        // ��ȡ�ƻ�
        YearMonthPlanBO ymbo = new YearMonthPlanBO();
        YearMonthPlanBean mbean = null;
        BasicDynaBean dynabean = (BasicDynaBean) list.get(0);
        mbean = ymbo.getYMPlanInfo(dynabean.get("id").toString());
        return mbean;

    }

    // �¼ƻ���Ϣ
    public YearMonthPlanBean getYearPlan(UserInfo userinfo, PatrolStatConditionBean bean)
            throws Exception {
        // ��üƻ�id
        String sql = "select id from yearmonthplan where year='" + bean.getEndYear()
                + "' and deptid='" + bean.getConID() + "'";
        logger.info("sql " + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        // ��ȡ�ƻ�
        YearMonthPlanBO ymbo = new YearMonthPlanBO();
        YearMonthPlanBean mbean = null;
        BasicDynaBean dynabean = (BasicDynaBean) list.get(0);
        mbean = ymbo.getYMPlanInfo(dynabean.get("id").toString());
        return mbean;

    }

    // Ѳ����Ա��Ϣ
    public List getPatrolMan(UserInfo userinfo, PatrolStatConditionBean bean) {
        String theStartDate = getFirst2Month(bean);
        String sql = "select pm.patrolid,pm.patrolname executorid,ps.planpoint,ps.overallpatrolp patrolp,ps.factpoint,ps.trouble,ps.examineresult,(select count(*) from terminalinfo tm where tm.ownerid=pm.patrolid) as terminalnumber  from plan_patrolstat ps,patrolmaninfo pm"
                + " where  pm.patrolid=ps.executorid and ps.contractorid='"
                + bean.getConID()
                + "' and ps.factdate <= last_day(TO_DATE ('"
                + theStartDate
                + "', 'yyyy-mm-dd')) and ps.factdate >= to_date('"
                + theStartDate
                + "','YYYY-MM-DD') order by ps.overallpatrolp desc ";
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        return list;

    }

    // ��ά��˾����Ա�
    public List getMonthPatrolResult(UserInfo userinfo, PatrolStatConditionBean bean) {
        // String theStartDate = bean.getEndYear() + "-"
        // + String.valueOf(Integer.parseInt(bean.getEndMonth()) - 2)
        // + "-1";
        String theStartDate = getStartDate(bean.getEndYear(), bean.getEndMonth());
        String theEndDate = getLast2Month(bean);
        String sql = "select to_char(factdate,'MM') factdate, examineresult from plan_cstat where cdeptid='"
                + bean.getConID()
                + "' and factdate < to_date('"
                + theEndDate
                + "','YYYY-MM-DD') and factdate >= to_date('"
                + theStartDate
                + "','YYYY-MM-DD')  order by factdate";
        logger.info("sql " + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        return list;
    }

    private String getStartDate(String year, String month) {
        int iYear = Integer.parseInt(year);
        int iMonth = Integer.parseInt(month);
        String startDate;
        if ((iMonth - 2) < 1) {
            startDate = (iYear - 1) + "-" + (iMonth + 12 - 2) + "-1";
        } else {
            startDate = iYear + "-" + (iMonth - 2) + "-1";
        }
        return startDate;
    }

    // ��ά��˾��ĺ���Ա�
    public List getConPatrolResult(UserInfo userinfo, PatrolStatConditionBean bean) {
        String theStartDate = getFirst2Month(bean);
        String sql = "select c.contractorname ,p.planpoint,p.factpoint, p.examineresult from plan_cstat p,contractorinfo c where c.contractorid = p.cdeptid and p.regionid='"
                + userinfo.getRegionid()
                + "' and p.factdate <= last_day(TO_DATE ('"
                + theStartDate
                + "', 'yyyy-mm-dd')) and p.factdate >= to_date('"
                + theStartDate
                + "','YYYY-MM-DD')";
        logger.info("sql " + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        return list;
    }

    // �õ�ĳһ����Ѳ��Ա/��һ�����ڵĶ��ŷ������
    public List getWorkdaysInfoList(String patrolid, String strYear, String strMonth) {
        String startDate = strYear + "-" + strMonth + "-1";
        sql = "SELECT TO_CHAR(t.operatedate, 'dd') || '��' AS operatedate,"
                + "t.simid, t.totalnum, t.patrolnum, t.troublenum, t.collectnum,t.watchnum,t.othernum "
                + "FROM STAT_MESSAGEDAILY t " + "WHERE t.patrolmanid = '" + patrolid + "' "
                + "AND t.operatedate >= TO_DATE ('" + startDate + "', 'yyyy-mm-dd') "
                + "AND t.operatedate <= last_day(TO_DATE ('" + startDate + "', 'yyyy-mm-dd')) "
                + "ORDER BY t.operatedate, t.simid ";
        logger.info("�õ�ĳһ����Ѳ��Ա/��һ�����ڵĶ��ŷ������SQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    // �õ�ÿ��SIM��ĳһ�����ϸ������Ϣ
    public List getPerCardPerDayInfoList(String simNumber, String handleState, String strDate) {
        sql = "select to_char(r.arrivetime,'YYYY-MM-DD hh24:mi:ss') arrivetime,r.simid "
                + "from recievemsglog r " + "where r.simid = '" + simNumber + "' "
                + "and r.handlestate in (" + handleState + ") "
                + "and (r.arrivetime between to_date('" + strDate
                + "', 'yyyy-mm-dd hh24:mi:ss') and to_date('" + strDate
                + "', 'yyyy-mm-dd hh24:mi:ss') + 1) " + "order by r.arrivetime";
        logger.info("ÿ��SIM��ĳһ�����ϸ������ϢSQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;
    }

    // �õ����漰��Ѳ���߶ε���Ϣ(�ϸ�,�����˵ȼ�>=3)
    public List getSubLineInfo(String strPatrolid, String strYear, String strMonth) {
        String startDate = strYear + "-" + strMonth + "-1";
        List list = null;
        sql = "select s.sublinename,lt.NAME linetype,ps.executetimes,ps.PLANPOINT,ps.factpoint,ps.examineresult,ps.PATROLP || '%' as patrolp "
                + "FROM plan_sublinestat ps, sublineinfo s, patrolmaninfo p, contractorinfo c,lineinfo l,lineclassdic lt "
                + "where ps.sublineid = s.sublineid AND p.patrolid = s.patrolid AND c.contractorid = p.parentid "
                + "AND s.LINEID = l.LINEID AND l.LINETYPE = lt.code AND c.state IS NULL AND p.state IS NULL "
                + "AND s.state IS NULL AND l.STATE IS NULL AND s.PATROLID='"
                + strPatrolid
                + "' "
                + "AND (ps.factdate >= TO_DATE ('"
                + startDate
                + "', 'yyyy-mm-dd') AND ps.factdate <= last_day(TO_DATE ('"
                + startDate
                + "', 'yyyy-mm-dd'))) AND ps.examineresult >= '3' "
                + "ORDER BY ps.executetimes desc";
        logger.info("�õ�Ѳ��Ա/Ѳ��ά����ѡ���·ݵ�Ѳ���߶�SQL(�ϸ�,�����˵ȼ�>=3):" + sql);
        if (sql != null || !"".equals(sql)) {
            list = monthlyStatDAOImpl.getBackInfoList(sql);
        }
        if (list == null) {
            logger.info("rs is null");
            return null;
        }
        return list;
    }

    // �õ����漰��Ѳ���߶ε���Ϣ(���ϸ�,�����˵ȼ�<3)
    public List getSubLineInfoFailure(String strPatrolid, String strYear, String strMonth) {
        String startDate = strYear + "-" + strMonth + "-01";
        List list = null;
        sql = "select s.sublinename,lt.NAME linetype,ps.executetimes,ps.PLANPOINT,ps.factpoint,ps.examineresult,ps.PATROLP || '%' as patrolp "
                + "FROM plan_sublinestat ps, sublineinfo s, patrolmaninfo p, contractorinfo c,lineinfo l,lineclassdic lt "
                + "where ps.sublineid = s.sublineid AND p.patrolid = s.patrolid AND c.contractorid = p.parentid "
                + "AND s.LINEID = l.LINEID AND l.LINETYPE = lt.code AND c.state IS NULL AND p.state IS NULL "
                + "AND s.state IS NULL AND l.STATE IS NULL AND s.PATROLID='"
                + strPatrolid
                + "' "
                + "AND (ps.factdate >= TO_DATE ('"
                + startDate
                + "', 'yyyy-mm-dd') AND ps.factdate <= last_day(TO_DATE ('"
                + startDate
                + "', 'yyyy-mm-dd'))) AND ps.examineresult<'3' " + "ORDER BY ps.executetimes desc";
        logger.info("�õ�Ѳ��Ա/Ѳ��ά����ѡ���·ݵ�Ѳ���߶�SQL(���ϸ�,�����˵ȼ�<3):" + sql);
        if (sql != null || !"".equals(sql)) {
            list = monthlyStatDAOImpl.getBackInfoList(sql);
        }
        if (list == null) {
            logger.info("rs is null");
            return null;
        }
        return list;
    }

    // �õ����漰��Ѳ���߶ε���Ϣ(��Ѳ��ƻ����߶�)
    public List getSubLineInfoNoPlan(String strPatrolid, String strYear, String strMonth) {
        String startDate = strYear + "-" + strMonth + "-01";
        List list = null;
        sql = "SELECT s.SUBLINENAME, l.LINENAME,lc.NAME linetype "
                + "FROM nonplansubline n,patrolmaninfo pm, sublineinfo s, lineinfo l,lineclassdic lc "
                + "WHERE n.SUBLINEID = s.SUBLINEID AND "
                + "n.LINEID = l.LINEID AND l.linetype=lc.code AND s.patrolid=pm.patrolid "
                + "AND pm.PATROLID='" + strPatrolid + "' "
                + "AND n.factdate <= last_day(TO_DATE ('" + startDate
                + "', 'yyyy-mm-dd')) AND factdate >=TO_DATE('" + startDate + "','YYYY-MM-DD') "
                + "ORDER BY l.LINENAME,s.SUBLINENAME";
        logger.info("�õ�Ѳ��Ա/Ѳ��ά����ѡ���·ݵ�Ѳ���߶�SQL(��Ѳ��ƻ����߶�):" + sql);
        if (sql != null || !"".equals(sql)) {
            list = monthlyStatDAOImpl.getBackInfoList(sql);
        }
        if (list == null) {
            logger.info("rs is null");
            return null;
        }
        return list;
    }

    // �õ�ĳһѡ��Ѳ��ά������ѡ���·ݼ���ǰ���ڶ����µ�Ѳ���������Ա�
    public List getMonthlyStatPmVComp(String patrolID, String endyear, String endmonth) {
        String endYearMonth = endyear + "-" + endmonth;
        sql = "select p.executorid, TO_CHAR (p.factdate, 'mm') startmonth,p.examineresult"
                + " from plan_patrolstat p "
                + "where p.executorid = '"
                + patrolID
                + "' and "
                + "p.factdate between add_months(TO_DATE ('"
                + endYearMonth
                + "', 'yyyy-MM'),"
                + MONTH_SPACE
                + ") and "
                + "last_day(to_date('"
                + endYearMonth
                + "', 'yyyy-mm')) "
                + "group by to_char(p.factdate, 'yyyy-mm'), p.executorid,to_char(p.factdate, 'mm'),p.EXAMINERESULT "
                + "order by to_char(p.factdate,'yyyy-mm') ";
        logger.info("ĳһѡ��Ѳ��ά������ѡ���·ݼ���ǰ���ڶ����µ�Ѳ���������Ա�SQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;

    }

    // �Ƚ�ĳһ����Ѳ��ά������ѡ���·���������ͬһ�Ҵ�ά��˾������Ѳ��ά�����Ѳ�쿼��
    public List getMonthlyStatPmHComp(String conID, String endyear, String endmonth) {
        String theYearMonth = endyear + "-" + endmonth;
        String theDate = theYearMonth + "-01";
        sql = "SELECT   p.executorid, pm.patrolname, p.planpoint,p.factpoint,p.examineresult "
                + "FROM plan_patrolstat p, PATROLMANINFO pm " + "WHERE p.executorid = pm.patrolid "
                + "AND p.contractorid = '" + conID + "' " + "and (p.factdate between to_date('"
                + theDate + "', 'yyyy-mm-dd') and " + "last_day(to_date('" + theYearMonth
                + "', 'yyyy-mm'))) " + "AND pm.state IS NULL "
                + "GROUP BY p.executorid, pm.patrolname,p.planpoint,p.factpoint,p.examineresult ";
        logger.info("ĳһ����Ѳ��ά������ѡ���·���������ͬһ�Ҵ�ά��˾������Ѳ��ά�����Ѳ�쿼��SQL:" + sql);
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        if (list == null) {
            logger.info("list is null");
        }
        return list;

    }

    public Template exportPlanExecuteResult(List list, PatrolStatConditionBean bean)
            throws Exception {
        String urlPath = ReportConfig.getInstance().getUrlPath(
                "report.contractor_patrol_plan_execute_result");
        ExcelStyle excelstyle = new ExcelStyle(urlPath);
        PlanExecuteResultTemplate template = new PlanExecuteResultTemplate(urlPath);
        template.exportPlanExecuteResult(list, bean, excelstyle);
        logger.info("���excel�ɹ�");
        return template;
    }

    public JFreeChart createPatrolManPatrolRateChart(PatrolStatConditionBean bean) {
        // TODO Auto-generated method stub
        String sql = " select pi.patrolname,'0' ";
        sql += " from patrolmaninfo pi ";
        sql += " where pi.parentid='" + bean.getConID() + "' ";
        sql += " and (pi.state is null or pi.state<>'1') ";
        List allList = monthlyStatDAOImpl.getBackInfoList(sql);
        sql = " select pi.patrolname,to_char(pps.overallpatrolp) ";
        sql += " from plan_patrolstat pps,patrolmaninfo pi ";
        sql += " where pps.executorid=pi.patrolid ";
        sql += " and (pi.state is null or pi.state<>'1') ";
        sql += " and pi.parentid='" + bean.getConID() + "' ";
        sql += " and pps.factdate=to_date('";
        sql += bean.getEndYear();
        sql += "-";
        sql += bean.getEndMonth();
        sql += "-1','yyyy-mm-dd') ";
        sql += " order by pps.overallpatrolp desc ";
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        list = processList(allList, list);
        ChartParameter parameter = new ChartParameter();
        parameter.setChartType(ChartParameter.BAR_CHART_TYPE);
        parameter.setTitle("Ѳ����Ѳ���ʶԱȷ���");
        parameter.setXTitle("Ѳ����");
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

    public JFreeChart createLineLevelPatrolRateChart(PatrolStatConditionBean bean) {
        // TODO Auto-generated method stub
        String sql = " select lc.name,'0' as patrolp from lineclassdic lc ";
        // sql += " where lc.assortment_id='cabletype' ";
        sql += " order by lc.code ";
        List allList = monthlyStatDAOImpl.getBackInfoList(sql);
        sql = " select lc.name, ";
        sql += " decode(sum(pss.planpoint),0,'0',to_char(100*sum(pss.factpoint)/sum(pss.planpoint),'FM990.99')) as patrolp, ";
        sql += " lc.code ";
        sql += " from plan_sublinestat pss,lineinfo li,lineclassdic lc,patrolmaninfo pi ";
        sql += " where pss.lineid=li.lineid and li.linetype=lc.code and pss.patrolid=pi.patrolid ";
        sql += " and (pi.state is null or pi.state<>'1') ";
        sql += " and (li.state is null or li.state<>'1') ";
        sql += " and pi.parentid='" + bean.getConID() + "' ";
        sql += " and pss.factdate=to_date('";
        sql += bean.getEndYear();
        sql += "-";
        sql += bean.getEndMonth();
        sql += "-1','yyyy-mm-dd') ";
        sql += " group by lc.name,lc.code ";
        sql += " order by lc.code ";
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        list = processList(allList, list);
        ChartParameter parameter = new ChartParameter();
        parameter.setChartType(ChartParameter.BAR_CHART_TYPE);
        parameter.setTitle("��·����Ѳ���ʶԱȷ���");
        parameter.setXTitle("��·����");
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
        sql += " to_char(decode(sum(pcs.planpoint),null,0,100*sum(pcs.factpoint)/sum(pcs.planpoint)),'FM990.09') as patrolp, ";
        sql += " '' as examineresult,ci.contractorname ";
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

    public List showMonthLayingMethodExecuteResultInfo(PatrolStatConditionBean bean,
            UserInfo userInfo) {
        // TODO Auto-generated method stub
        String sql = " select lc.code,lc.name, ";
        sql += " decode(sum(pss.planpoint),null,0,sum(pss.planpoint)) as planpoint, ";
        sql += " decode(sum(pss.factpoint),null,0,sum(pss.factpoint)) as factpoint, ";
        sql += " decode(sum(pss.sublinekm),null,0,sum(pss.sublinekm)) as sublinekm, ";
        sql += " decode(sum(pss.factkm),null,0,sum(pss.factkm)) as patrolkm, ";
        sql += " to_char(decode(sum(pss.planpoint),null,0,0,0,100*sum(pss.factpoint)/sum(pss.planpoint)),'FM990.09') as patrolp ";
        sql += " from plan_sublinestat pss,sublineinfo si,lineinfo li,patrolmaninfo pi,contractorinfo ci,lineclassdic lc ";
        // sql += " where pss.sublineid=si.sublineid and si.linetype=lc.code and
        // lc.assortment_id='layingmethod'";
        sql += " where pss.sublineid=si.sublineid and si.lineid=li.lineid and li.linetype=lc.code ";
        sql += " and si.patrolid=pi.patrolid and pi.parentid=ci.contractorid ";
        sql += " and ci.contractorid='" + bean.getConID() + "' ";
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
        List list = monthlyStatDAOImpl.getBackInfoList(sql);
        return list;
    }
}
