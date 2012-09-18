package com.cabletech.commons.tags.displaytag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//PaginatedList��SortOrderEnumΪdisplaytag-1.1.1.jar�ṩ��jar�����ṩ����
import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

/**
* ���displaytag��ǩ��չ�ĺ�̨��ҳ����
* display1.1�ķ����Ѿ��ܹ�֧�ֺ�̨���ݿ��ҳ��������ʵ�����ṩ��PaginatedList�ӿ�
*/
public class PaginatedListHelper implements PaginatedList  {
	private List list; // ���ݼ�

	private int pageNumber = 1;// ҳ��

	private int objectsPerPage = 18;// ÿҳ��ʾ�ļ�¼��

	private int fullListSize = 0;// ȫ����¼��

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

	 * �����ܵ����ݼ���ÿҳ��Ҫ���׼�¼��β��¼���������ܼ�¼���еõ���Ҫ�ļ�¼��

	 * ����һ��ȡ�ɵķ�ҳ���������������ĸ���ÿҳ����ƴsql���в�ѯ�����Ƕ����������ݲ�ѯ�Ľ������ȡ��ҳ��Ҫ�����ݼ��ϣ����ڳ����������Ĳ�ѯЧ�ʿ��ܲ���̫��

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
				
				if (this.getPageNumber() == 1) { // ��ҳ
					fromRow = 1; // �ӵ�һ�п�ʼ
					toRow = this.getObjectsPerPage();
				} else {
					fromRow = (this.getPageNumber() - 1)
					* this.getObjectsPerPage() + 1;
					toRow = fromRow + this.getObjectsPerPage();
				}
				if (toRow > this.getFullListSize()) // ���β��������list�Ĵ�С
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
