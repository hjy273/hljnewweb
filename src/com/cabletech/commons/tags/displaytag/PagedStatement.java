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
		// ȡ�õ�ǰҳ��
		int pageNo = getPageNo(request);
		// ȡ���ܼ�¼��
		int count = readTotalRecords(sql);
		// ȡ����������
		String sortValue = getSortNameDir(request);		
		
		if(sortValue != null) {
			// ȡ�÷�ҳ��ʾ�ļ�¼
			pageList = getPageSortList(sql, pageNo,perPage, sortValue);
		} else {
			// ȡ�÷�ҳ��ʾ�ļ�¼
			pageList = getPageList(sql, pageNo,perPage);
		}
		
		PaginatedListHelper paginaredList = new PaginatedListHelper();
		paginaredList.setList(pageList); // ȫ�������ݼ�
		paginaredList.setObjectsPerPage(perPage); // Ĭ��ÿҳ��ʾ18����¼���������¸�ֵ
		paginaredList.setPageNumber(pageNo); // ����ҳ��
		paginaredList.setFullListSize(count); // ��¼����
		
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
	 * ȡ�������ֶμ�����ʽ
	 * @return
	 */
	protected String getSortNameDir(HttpServletRequest request) {
		// ȡ������������Լ�����ʽ��
		String sort = null;		
		if (request.getParameter("sort") != null&& !"".equals(request.getParameter("sort"))) {
			// ����ǵ������������
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
	 * ȡ�õ�ǰҳ��
	 * @param request
	 * @return
	 */
	protected int getPageNo(HttpServletRequest request) {
		// Ĭ��ֵ����1ҳ 
		int pageNo = 1; 
		if (request.getParameter("page") != null&& !"".equals(request.getParameter("page"))) {
			pageNo = Integer.parseInt(request.getParameter("page"));
		}
		return pageNo;		
	}	

	/**
	 * ȡ��sql����������һ��from������ַ���
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
	 * ͳ������
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
			logger.error("ȡ�ò�ѯ�����ܼ�¼��error: " + e1);
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
	 * ȡ�÷�ҳ��ѯ��sql�����
	 * @param sql Ҫ��ѯ�����
	 * @param pageNo ��ǰҳ��
	 * @param pageSize ÿҳ��ʾ�ļ�¼����
	 * @return
	 */
	protected List getPageList(String sql, int pageNo, int pageSize ) {
		StringBuffer sb = new StringBuffer();
	
		// ��ʼֵ
		int startIndex = (pageNo - 1) * pageSize + 1; 
		
		sb.append("select * from (select my_table.*,rownum as my_rownum from(").
        	append(sql).
        	append(") my_table where rownum<").
        	append(startIndex + pageSize).
        	append(") where my_rownum>=").
        	append(startIndex);
		
		String pageSql = sb.toString();
		logger.info("��ҳ��ѯ��sql���: " + pageSql);
		
		QueryUtil qu = null;
		List pageList = null;
		try {
			qu = new QueryUtil();
			pageList = qu.queryBeans(pageSql);
		} catch (Exception e) {			
			logger.error("��ҳ��ѯ���쳣: " + e.toString());
			e.printStackTrace();
		} finally {
			if(qu != null) {
				qu.close();
			}
		}

		return pageList;
	}
	
	/**
	 * ȡ���������ѯ��sql��䷽��
	 * @param sql Ҫ��ѯ�����
	 * @param pageNo ��ǰҳ��
	 * @param pageSize��ÿҳ��ʾ�ļ�¼��
	 * @param sortRow ������
	 * @param order ����/����
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
		logger.info("��ҳ�����ѯ��sql���: " + pageSortSql);
		
		QueryUtil qu = null;
		List pageSortList = null;
		try {
			qu = new QueryUtil();
			pageSortList = qu.queryBeans(pageSortSql);
		} catch (Exception e) {			
			logger.error("��ҳ�����ѯ���쳣: " + e.toString());
			e.printStackTrace();
		} finally {
			if(qu != null) {
				qu.close();
			}
		}
		
		return pageSortList;
	}
	
}
