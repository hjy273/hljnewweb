package com.cabletech.planstat.dao;

import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;

public class PlanProgressDAOImpl extends HibernateDaoSupport{
	private Logger logger = Logger.getLogger(PlanProgressDAOImpl.class.getName());
    public PlanProgressDAOImpl(){
    }
    public List getResultList( String sql ){
        QueryUtil query = null;
        List list = null;
            try{
                query = new QueryUtil();
                list = query.queryBeans( sql );
            }
            catch( Exception ex ){
                logger.error("查询异常："+ex.getMessage());
                ex.printStackTrace();
            }
            return list;


    }
    public ResultSet getResultListJDBC( String sql ){
       QueryUtil query = null;
       ResultSet resultset = null;
           try{
               query = new QueryUtil();
               resultset = query.executeQuery(sql);
           }
           catch( Exception ex ){
               logger.error("查询异常："+ex.getMessage());
               ex.printStackTrace();
           }
           return resultset;
   }
    
   /**
    * 通过PDA获得当前计划的执行情况
    * @param userInfo
    * @return
    */
	public List getPlanProgressFromPDA(UserInfo userInfo) {
		String sql = "SELECT p.planname as planName,ps.planpointtimes,ps.actualpointtimes,p.id AS planid,con.contractorname contractorName,ps.PERCENTAGE || '%' AS percentage "
				+ "FROM plancurrent_stat ps, plan p,contractorinfo con "
				+ "WHERE ps.curplanid = p.id AND p.regionid = '"+userInfo.getRegionID()+"' AND ps.contractorid in (select contractorid from contractorinfo con) and ps.contractorid = con.contractorid  AND ps.patroldate = TRUNC (SYSDATE - 1, 'dd') "
				+ "ORDER BY   ps.contractorid DESC";
		return this.getResultList(sql);
	}
}
