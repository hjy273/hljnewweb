package com.cabletech.datum.bean;

import java.util.*;

/**
 * �ĵ����� v 1.0 simon zhang
 */
public class DocumentType {
	/** �ĵ����ʹ��� */
	public java.lang.String code;

	/**
	 * �ĵ���������
	 */
	public java.lang.String typename;

	/**
	 * 1����ά�ƶ� 2���������� ��3����ά����
	 */
	public java.lang.String category;

	public java.lang.String getCategory() {
		return category;
	}

	public void setCategory(java.lang.String category) {
		this.category = category;
	}

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.String getTypename() {
		return typename;
	}

	public void setTypename(java.lang.String typename) {
		this.typename = typename;
	}

}