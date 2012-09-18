package com.cabletech.datum.bean;

import java.util.*;

/**
 * 文档类型 v 1.0 simon zhang
 */
public class DocumentType {
	/** 文档类型代码 */
	public java.lang.String code;

	/**
	 * 文档类型名称
	 */
	public java.lang.String typename;

	/**
	 * 1：代维制度 2：技术管理 ，3：代维经验
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