package com.cabletech.planinfo.dao;

import java.sql.ResultSet;
import java.util.List;

import com.cabletech.commons.hb.*;
import com.cabletech.planinfo.domainobjects.*;

public class PlanDAOImpl extends HibernateDaoSupport implements PlanDAO{

    public Plan addPlan( Plan plan ) throws Exception{
        this.getHibernateTemplate().save( plan );
        return plan;
    }


    public Plan loadPlan( String id ) throws Exception{
        return( Plan )this.getHibernateTemplate().load( Plan.class, id );
    }


    public Plan updateWPlan( Plan plan ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( plan );
        return plan;
    }


    public void removeWPlan( Plan plan ) throws Exception{
        this.getHibernateTemplate().delete( plan );
    }
    /**
     * 查询计划信息
     * @param sql
     * @return
     */
    public List queryPlan (String sql){
	    QueryUtil query;
		try {
			query = new QueryUtil();
			List list = query.queryBeans(sql);
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }


	public int queryPlanCount(String sql) {
		QueryUtil query=null;
		int count = 1;
		try {
			query = new QueryUtil();
			ResultSet rs =  query.executeQuery(sql);
			
			while(rs.next()){
				count = rs.getInt("c");
			}
			return count;
		} catch (Exception e) {
			//logger.error(e);
			e.printStackTrace();
			return count;
		}
	}
	public String getTaskPointNum(String sql) {
		QueryUtil query;
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			String taskpoint="";
			while(rs.next()){
				taskpoint = rs.getString("count");
			}
			return taskpoint;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public String getTaskSubline(String sql) {
		QueryUtil query;
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			String subline="";
			while(rs.next()){
				subline += rs.getString("sublinename")+"、";
			}
			return subline.substring(0,subline.length()-1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 取得任务下面的对应的点数
	 * @param patrolid
	 * @param linetype
	 * @return
	 */
	public String getTaskFactNum(String patrolid, String linetype) {
		String sql = "select count(*) num  from sublineinfo sl,lineinfo l,pointinfo p "
				   + " where  p.sublineid = sl.sublineid and sl.lineid = l.lineid "
				   + " and p.state = '1' and l.LINETYPE='" + linetype + "' and sl.patrolid='" + patrolid +  "' ";
		
		QueryUtil query;
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			String countStr = "";
			while(rs.next()){
				countStr = rs.getString("num");
			}
			return countStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
}
