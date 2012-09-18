package com.cabletech.planinfo.dao;

import java.sql.ResultSet;

import com.cabletech.commons.hb.*;
import com.cabletech.planinfo.domainobjects.*;

public class YearMonthPlanDAOImpl extends HibernateDaoSupport implements PlanDAO{
    public YearMonthPlanDAOImpl(){
    }


    public YearMonthPlan addPlan( YearMonthPlan yearmonthPlan ) throws Exception{
        this.getHibernateTemplate().save( yearmonthPlan );
        return yearmonthPlan;
    }


    /**
     * 增加一条plantasklist记录
     * @param plantasklist PlanTaskList
     * @return PlanTaskList
     * @throws Exception
     */
    public PlanTaskList addPlanTaskList( PlanTaskList plantasklist ) throws
        Exception{
        this.getHibernateTemplate().save( plantasklist );
        return plantasklist;
    }


    public YearMonthPlan loadYMPlan( String id ) throws Exception{
        return( YearMonthPlan )this.getHibernateTemplate().load( YearMonthPlan.class, id );
    }


    /**
     * 更新一个ym计划
     * @param plan YearMonthPlan
     * @return YearMonthPlan
     * @throws Exception
     */
    public YearMonthPlan updateYMPlan( YearMonthPlan plan ) throws Exception{
        this.getHibernateTemplate().saveOrUpdate( plan );
        return plan;
    }


    /**
     * 删除一个计划
     * @param plan YearMonthPlan
     * @throws Exception
     */
    public void removeYMPlan( YearMonthPlan plan ) throws Exception{
        this.getHibernateTemplate().delete( plan );
    }

    /**
     * 获取线路级别
     * @param code
     * @return
     */
	public String getLineLevel(String code) {
		String name = "";
		QueryUtil qu;
		try {
			qu = new QueryUtil();
			ResultSet rs = qu.executeQuery("SELECT name FROM LINECLASSDIC WHERE code='"+code+"'");
			while(rs.next()){
				name = rs.getString("name");
			}
			return name;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
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

}
