package com.cabletech.commons.tags.displaytag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//PaginatedList和SortOrderEnum为displaytag-1.1.1.jar提供的jar包中提供的类
import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

/**
* 针对displaytag标签扩展的后台分页功能
* display1.1的发布已经能够支持后台数据库分页，但必须实现其提供的PaginatedList接口
*/
public class PaginatedListHelper implements PaginatedList  {
	private List list; // 数据集

	private int pageNumber = 1;// 页号

	private int objectsPerPage = 18;// 每页显示的记录数

	private int fullListSize = 0;// 全部记录数

	private String sortCriterion;

	private SortOrderEnum sortDirection;

	private String searchId;

	public List getList() {

		return list;

	}

	public void setList(List list) {

		this.list = list;

	}

	public int getPageNumber() {

		return pageNumber;

	}

	public void setPageNumber(int pageNumber) {

		this.pageNumber = pageNumber;

	}

	public int getObjectsPerPage() {

		return objectsPerPage;

	}

	public void setObjectsPerPage(int objectsPerPage) {

		this.objectsPerPage = objectsPerPage;

	}

	public int getFullListSize() {

		return fullListSize;

	}

	public void setFullListSize(int fullListSize) {

		this.fullListSize = fullListSize;

	}

	public String getSortCriterion() {

		return sortCriterion;

	}

	public void setSortCriterion(String sortCriterion) {

		this.sortCriterion = sortCriterion;

	}

	public SortOrderEnum getSortDirection() {

		return sortDirection;

	}

	public void setSortDirection(SortOrderEnum sortDirection) {

		this.sortDirection = sortDirection;

	}

	public String getSearchId() {

		return searchId;

	}

	public void setSearchId(String searchId) {

		this.searchId = searchId;
	}
	/**

	 * 根据总的数据集和每页需要的首记录、尾记录索引，从总记录集中得到需要的记录集

	 * 这是一个取巧的分页方法，并非真正的根据每页请求拼sql进行查询，而是对于整个数据查询的结果从中取各页需要的数据集合，对于超大数据量的查询效率可能不会太高

	 */
	public void getCurrentPageRecord() {
		ArrayList v = new ArrayList();
		Iterator iterator = null;
		try {
			if (this.getList() != null) {
				iterator = this.getList().iterator();
				int i = 0;
				int fromRow = 0;
				int toRow = 0;
				
				if (this.getPageNumber() == 1) { // 首页
					fromRow = 1; // 从第一行开始
					toRow = this.getObjectsPerPage();
				} else {
					fromRow = (this.getPageNumber() - 1)
					* this.getObjectsPerPage() + 1;
					toRow = fromRow + this.getObjectsPerPage();
				}
				if (toRow > this.getFullListSize()) // 如果尾行数超过list的大小
					toRow = this.getFullListSize();
				while (iterator.hasNext()) {
					i++;
					if (i < fromRow) {
						iterator.next();
						continue;
					} else if (i > toRow) {
						iterator.next();
						continue;
					} else {
						v.add(iterator.next());
					}
				}
				this.setList(v);
			}
		} catch (Exception ex) {

			ex.printStackTrace();

		}

	}
}
