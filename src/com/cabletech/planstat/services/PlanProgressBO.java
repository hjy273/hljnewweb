package com.cabletech.planstat.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.planstat.beans.PatrolStatConditionBean;
import com.cabletech.planstat.dao.PlanProgressDAOImpl;

public class PlanProgressBO extends BaseBisinessObject{
	Logger logger = Logger.getLogger( this.getClass().getName() );
    private String sql = null;
    private PlanProgressDAOImpl progressDAOImpl = null;
    public PlanProgressBO(){
    	progressDAOImpl = new PlanProgressDAOImpl();
    }
    
    //得到区域列表 
    public List getRegionInfoList(){
        String sql = "select  r.REGIONNAME, r.REGIONID "
                           + " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
        logger.info( "区域SQL:" + sql );
        List list = progressDAOImpl.getResultList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
    //得到符合条件的市代维单位列表
    public List getContractorInfoList( UserInfo userinfo ){
        if( userinfo.getType().equals( "12" ) ){
            sql = "select con.contractorid,con.contractorname,con.parentcontractorid,con.regionid " +
                  "from contractorinfo con " +
                  "where con.STATE is null " +
                  "and con.regionid='" + userinfo.getRegionID() + "' " +
                  "order by con.contractorname";
        }
        else if( userinfo.getType().equals( "21" ) ){
                sql = "SELECT c.contractorid, c.contractorname,c.parentcontractorid,con.regionid  " +
                      "FROM contractorinfo c " +
                      "where c.regionid not like '%0000' " +
                      "and c.state is null " +
                      "CONNECT BY PRIOR contractorid = parentcontractorid " +
                      "START WITH contractorid in " +
                      " (select u.deptid from userinfo u where u.userid = '" + userinfo.getUserID() + "') " +
                      "order by c.regionid";
            }
        else if ( userinfo.getType().equals( "11" ) ){
            sql = "select con.contractorid,con.contractorname,con.parentcontractorid,con.regionid " +
                  "from contractorinfo con " +
                  "where con.STATE is null " +
                  "order by con.contractorname";
        }else if( userinfo.getType().equals( "22" ) ){
            sql = "select con.contractorid,con.contractorname,con.parentcontractorid,con.regionid " +
                  "from contractorinfo con " +
                  "where con.STATE is null " +
                  "and con.regionid='" + userinfo.getRegionID() + "' " +
                  "and con.contractorid='" + userinfo.getDeptID() + "' " +
                  "order by con.contractorname";
        }
        logger.info( "市代维单位列表SQL:" + sql );
        List list = progressDAOImpl.getResultList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }
    
    //得到符合条件的正在执行的计划进度相关信息列表
    public List getPlanProgressList(UserInfo userinfo,PatrolStatConditionBean bean){
    	ResultSet rs = null;
    	if( userinfo.getType().equals( "22" ) ){
	        sql = "select p.planname,to_char(p.begindate,'YYYY-MM-DD') begindate,to_char(p.enddate,'YYYY-MM-DD') enddate,ps.examineresult," +
    		             "ps.planpointtimes,ps.actualpointtimes,pm.patrolname,p.id as planid,ps.PERCENTAGE ||'%' as percentage " +
                  "from plancurrent_stat ps,plan p,patrolmaninfo pm " +
                  "where ps.curplanid = p.id " +
		            "and p.executorid = pm.patrolid " +
		            "and pm.parentid ='" + userinfo.getDeptID() + "' " +
		            "and ps.patroldate = trunc(sysdate-1, 'dd') " +  //for running
		            "and pm.state is null " +
		            "order by ps.percentage desc";
    	}else if( userinfo.getType().equals( "12" ) ){
    		sql = "select p.planname,to_char(p.begindate,'YYYY-MM-DD') begindate,to_char(p.enddate,'YYYY-MM-DD') enddate,ps.examineresult," +
			             "ps.planpointtimes,ps.actualpointtimes,pm.patrolname,p.id as planid,ps.PERCENTAGE ||'%' as percentage " +
			      "from plancurrent_stat ps,plan p,patrolmaninfo pm " +
			      "where ps.curplanid = p.id " +
			        "and p.executorid = pm.patrolid " +
			        "and p.regionid='" + userinfo.getRegionID() + "' " +
			        "and pm.parentid ='" + bean.getConID() + "' " +
		            "and ps.patroldate = trunc(sysdate-1, 'dd') " +  //for running
			        "order by ps.percentage desc";
    	}else if( userinfo.getType().equals( "11" ) ){
    		sql = "select p.planname,to_char(p.begindate,'YYYY-MM-DD') begindate,to_char(p.enddate,'YYYY-MM-DD') enddate,ps.examineresult," +
			             "ps.planpointtimes,ps.actualpointtimes,pm.patrolname,p.id as planid,ps.PERCENTAGE ||'%' as percentage " +
			      "from plancurrent_stat ps,plan p,patrolmaninfo pm " +
			      "where ps.curplanid = p.id " +
			        "and p.executorid = pm.patrolid " +
			        "and p.regionid='" + bean.getRegionId() + "' " +
			        "and pm.parentid ='" + bean.getConID() + "' " +
		            "and ps.patroldate = trunc(sysdate-1, 'dd') " +  //for running
			        "order by ps.percentage desc";
    	}else if( userinfo.getType().equals( "21" ) ){
    		sql = "select p.planname,to_char(p.begindate,'YYYY-MM-DD') begindate,to_char(p.enddate,'YYYY-MM-DD') enddate,ps.examineresult," +
			             "ps.planpointtimes,ps.actualpointtimes,pm.patrolname,p.id as planid,ps.PERCENTAGE ||'%' as percentage " +
			      "from plancurrent_stat ps,plan p,patrolmaninfo pm " +
			      "where ps.curplanid = p.id " +
			        "and p.executorid = pm.patrolid " +
			        "and p.regionid='" + bean.getRegionId() + "' " +
			        "and pm.parentid ='" + bean.getConID() + "' " +
		            "and ps.patroldate = trunc(sysdate-1, 'dd') " +  //for running
			        "order by ps.percentage desc";
    	}
		logger.info( "正在执行的计划进度相关信息SQL:" + sql );
        if (sql != null ||!"".equals(sql)){
            rs = progressDAOImpl.getResultListJDBC( sql );
        } 
        if( rs == null ){
            logger.info( "rs is null" );
            return null;
        }
        Map map;
        List list = new ArrayList();
        try {
			while(rs.next()){				
				map = new HashMap();
				map.put("planname", rs.getString(1));
				map.put("begindate", rs.getString(2));
			    map.put("enddate", rs.getString(3));
			    map.put("examineresult", rs.getString(4));
			    map.put("planpointtimes", rs.getString(5));
			    map.put("actualpointtimes", rs.getString(6));
			    map.put("patrolname", rs.getString(7));
			    map.put("planid", rs.getString(8));
			    map.put("percentage", rs.getString(9));
			    list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return list;
    }
    
    //得到所选的正在进行的计划所涉及到的尚未合格的巡检线段详细信息
    public List getSublineDetailUnfinished(String planid){
        String sql = "SELECT pu.sublineid,s.sublinename, lt.NAME linetype,pu.planpointtimes," +
                       "pu.factpointtimes,pu.examineresult,pu.percentage || '%' as percentage " +
                     "FROM PLAN_UNDERWAYSUBLINE pu, SUBLINEINFO s, LINEINFO l, LINECLASSDIC lt " +
					 "WHERE s.lineid = l.lineid AND l.linetype = lt.code AND pu.sublineid = s.sublineid " +
					   "AND pu.curplanid = '" + planid + "' and (pu.examineresult>='1' and pu.examineresult<='2') " +
					   "AND s.state IS NULL AND l.state IS NULL " +
					 "ORDER BY s.sublinename";
        logger.info( "得到所选的正在进行的计划所涉及到的尚未合格的巡检线段详细信息SQL:" + sql );
        List list = progressDAOImpl.getResultList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    } 
  
    //得到所选的正在进行的计划所涉及到的已经合格的巡检线段详细信息
    public List getSublineDetailFinished(String planid){
        String sql = "SELECT pu.sublineid,s.sublinename, lt.NAME linetype,pu.examineresult,pu.percentage || '%' as percentage " +
                     "FROM PLAN_UNDERWAYSUBLINE pu, SUBLINEINFO s, LINEINFO l, LINECLASSDIC lt " +
					 "WHERE s.lineid = l.lineid AND l.linetype = lt.code AND pu.sublineid = s.sublineid " +
					   "AND pu.curplanid = '" + planid + "' and pu.examineresult>='3' " +
					   "AND s.state IS NULL AND l.state IS NULL " +
					 "ORDER BY s.sublinename";
        logger.info( "得到所选的正在进行的计划所涉及到的已经合格的巡检线段详细信息SQL:" + sql );
        List list = progressDAOImpl.getResultList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }    

    //得到所选的正在进行的计划所涉及到的尚未开始的巡检线段详细信息
    public List getSublineDetailNeverStart(String planid){
        String sql = "SELECT pu.sublineid,s.sublinename, lt.NAME linetype,pu.planpointtimes " +
                     "FROM PLAN_UNDERWAYSUBLINE pu, SUBLINEINFO s, LINEINFO l, LINECLASSDIC lt " +
					 "WHERE s.lineid = l.lineid AND l.linetype = lt.code AND pu.sublineid = s.sublineid " +
					   "AND pu.curplanid = '" + planid + "' and pu.examineresult='0' " +
					   "AND s.state IS NULL AND l.state IS NULL " +
					 "ORDER BY s.sublinename";
        logger.info( "得到所选的正在进行的计划所涉及到的尚未开始的巡检线段详细信息SQL:" + sql );
        List list = progressDAOImpl.getResultList( sql );
        if( list == null ){
            logger.info( "list is null" );
        }
        return list;
    }    
    
    
    /**
     * 通过PDA获得当前计划的执行情况
     * @param userInfo
     * @return
     */
	public String getPlanProgressFromPDA(UserInfo userInfo) {
		List<BasicDynaBean> list = progressDAOImpl.getPlanProgressFromPDA(userInfo);
		StringBuffer html = new StringBuffer();
		if (list.size() > 0) {
			html.append("<table cellspacing=\"1\" cellpadding=\"0\" class=\"tabout\" width=\"100%\"><tr class=\"trcolor\"><td>计划名称</td><td>计划巡检点次</td><td>实际巡检点次</td><td>巡检进度</td><td>代维单位</td></tr>");
			for (BasicDynaBean bd : list) {
				System.out.println(bd);
				html.append("<tr class=\"trcolor\"><td>" + bd.get("planname") + "</td><td>" + bd.get("planpointtimes") + "</td><td>"
						+ bd.get("actualpointtimes") + "</td><td>" + bd.get("percentage") + "</td><td>"
						+ bd.get("contractorname") + "</td></tr>");
			}
			html.append("</table>");
		} else {
			html.append("<div>   没有正在执行的计划！     </div>");
		}
		return html.toString();
	}
}
