package com.cabletech.planinfo.dao;

import java.sql.ResultSet;
import java.util.List;

import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.planinfo.domainobjects.StencilTask;

public class StencilDAOImpl extends HibernateDaoSupport implements PlanDAO{
	/**
	 * 传入sql 将返回动态Bean组成的List
	 * @param sql
	 * @return List 结果
	 */
	 public List queryBean (String sql){
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

	public StencilTask loadStencil(String id) throws Exception {
		return (StencilTask)this.getSession().get(StencilTask.class, id);
//		return (StencilTask)this.getHibernateTemplate().load(StencilTask.class, id);
	}

	public int queryPlanCount(String sql) {
		
		return 0;
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
	 * 取得模板任务下面的对应的点数
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
