package com.cabletech.commons.tags.displaytag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.cabletech.commons.hb.QueryUtil;

public class PagedStatement {
	private Logger logger =Logger.getLogger(this.getClass().getName());	
	
	List pageList = null;
	
	public PaginatedListHelper initPaginatedListHelper(String sql,int perPage,HttpServletRequest request ){
		// 取得当前页数
		int pageNo = getPageNo(request);
		// 取得总记录数
		int count = readTotalRecords(sql);
		// 取得排序内容
		String sortValue = getSortNameDir(request);		
		
		if(sortValue != null) {
			// 取得分页显示的记录
			pageList = getPageSortList(sql, pageNo,perPage, sortValue);
		} else {
			// 取得分页显示的记录
			pageList = getPageList(sql, pageNo,perPage);
		}
		
		PaginatedListHelper paginaredList = new PaginatedListHelper();
		paginaredList.setList(pageList); // 全部的数据集
		paginaredList.setObjectsPerPage(perPage); // 默认每页显示18条记录，可以重新赋值
		paginaredList.setPageNumber(pageNo); // 设置页号
		paginaredList.setFullListSize(count); // 记录总数
		
//	    if ("asc".equals(strDir.toLowerCase()))
//    	   paginaredList.setSortDirection(SortOrderEnum.DESCENDING);
//      else
//    	   paginaredList.setSortDirection(SortOrderEnum.ASCENDING);
		return paginaredList;
	}
	
	public List getPageList() {
		return pageList;
	}
	
	/**
	 * 取得排序字段及排序方式
	 * @return
	 */
	protected String getSortNameDir(HttpServletRequest request) {
		// 取得列排序的属性及排序方式　
		String sort = null;		
		if (request.getParameter("sort") != null&& !"".equals(request.getParameter("sort"))) {
			// 如果是点击列排序的情况
			sort = String.valueOf(request.getParameter("sort"));
			HashMap map = (HashMap)request.getSession().getAttribute("sortMap");
			if(map == null) {
				map = new HashMap();
			} 
			String mapValue = String.valueOf(map.get(sort));			
			if("null".equals(mapValue)) {
				map.put(sort, "asc");
				sort += " asc";
			} else if("asc".equals(mapValue)) {
				map.put(sort, "desc");
				sort += " desc";
				
			}
			else if("desc".equals(mapValue)) {
				map.put(sort, "asc");
				sort += " asc";				
			}
			request.getSession().setAttribute("sortMap", map);
		}
		return sort;
	}
	
	/**
	 * 取得当前页面
	 * @param request
	 * @return
	 */
	protected int getPageNo(HttpServletRequest request) {
		// 默认值：第1页 
		int pageNo = 1; 
		if (request.getParameter("page") != null&& !"".equals(request.getParameter("page"))) {
			pageNo = Integer.parseInt(request.getParameter("page"));
		}
		return pageNo;		
	}	

	/**
	 * 取得sql语句从左边起第一个from后面的字符串
	 * @param querySql
	 * @return
	 */
	protected String parseFromBackStr(String querySql) {
		if(querySql == null) {
			return "";
		}
		String tmp = querySql.toLowerCase();
		int beginIndex = tmp.indexOf("from") + "from".length();
		tmp = tmp.substring(beginIndex).trim();	
		
		return tmp;
	}	
	
	
	/**
	 * 统计总数
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	protected int readTotalRecords(String querySql) {
		String fromBackValue = this.parseFromBackStr(querySql);

		String sql = "SELECT count(*) FROM " + fromBackValue;
		int rowCount = 0;	
		QueryUtil qu;
		ResultSet rs = null;
		try {
			qu = new QueryUtil();
			rs = qu.executeQuery(sql);
			if(rs.next()) {
				rowCount = rs.getInt(1);
			}
		} catch (Exception e1) {
			logger.error("取得查询语句的总记录数error: " + e1);
			e1.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					rs = null;
				}
			}
		}
		
		return rowCount;
	}
	
	
	/**
	 * 取得分页查询的sql语句结果
	 * @param sql 要查询的语句
	 * @param pageNo 当前页数
	 * @param pageSize 每页显示的记录条数
	 * @return
	 */
	protected List getPageList(String sql, int pageNo, int pageSize ) {
		StringBuffer sb = new StringBuffer();
	
		// 开始值
		int startIndex = (pageNo - 1) * pageSize + 1; 
		
		sb.append("select * from (select my_table.*,rownum as my_rownum from(").
        	append(sql).
        	append(") my_table where rownum<").
        	append(startIndex + pageSize).
        	append(") where my_rownum>=").
        	append(startIndex);
		
		String pageSql = sb.toString();
		logger.info("分页查询的sql语句: " + pageSql);
		
		QueryUtil qu = null;
		List pageList = null;
		try {
			qu = new QueryUtil();
			pageList = qu.queryBeans(pageSql);
		} catch (Exception e) {			
			logger.error("分页查询的异常: " + e.toString());
			e.printStackTrace();
		} finally {
			if(qu != null) {
				qu.close();
			}
		}

		return pageList;
	}
	
	/**
	 * 取得列排序查询的sql语句方法
	 * @param sql 要查询的语句
	 * @param pageNo 当前页数
	 * @param pageSize　每页显示的记录数
	 * @param sortRow 排序列
	 * @param order 升序/降序
	 * @return
	 */
	protected List getPageSortList(String sql, int pageNo, int pageSize, String sortValue) {
		StringBuffer sb = new StringBuffer();
	
		int startIndex = (pageNo - 1) * pageSize + 1; 
	
		sb.append("select * from (select my_table.*,rownum as my_rownum from(").
        	append(sql).
        	append(") my_table where rownum<").
        	append(startIndex + pageSize).
        	append(") where my_rownum>=").
        	append(startIndex);
		sb.append(" order by " + sortValue);
		
		String pageSortSql = sb.toString();
		logger.info("分页排序查询的sql语句: " + pageSortSql);
		
		QueryUtil qu = null;
		List pageSortList = null;
		try {
			qu = new QueryUtil();
			pageSortList = qu.queryBeans(pageSortSql);
		} catch (Exception e) {			
			logger.error("分页排序查询的异常: " + e.toString());
			e.printStackTrace();
		} finally {
			if(qu != null) {
				qu.close();
			}
		}
		
		return pageSortList;
	}
	
}
