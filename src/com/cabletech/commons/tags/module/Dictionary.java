package com.cabletech.commons.tags.module;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cabletech.commons.base.BaseDomainObject;
/**
 * 数据字典实体，主要储存常用的下拉列表，checkbox，radio数据
 * @author zh
 *
 */
@Entity
@Table(name="dictionary_formitem")
public class Dictionary extends BaseDomainObject {
	
	private static final long serialVersionUID = -8346865171403913443L;
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 15)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dictionary_formitem_id")
	@SequenceGenerator(name = "seq_dictionary_formitem_id", sequenceName = "seq_dictionary_formitem_id")
	private Integer id;
	@Column(name = "CODE")
	private String code;
	@Column(name="LABLE")
	private String lable;
	@Column(name="ASSORTMENT_ID")
	private String assortmentId;
	@Column(name="SORT")
	private Integer sort;
	@Column(name="parentid")
	private String parentId;
	@Column(name="regionid")
	private String regionid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setIdStr(String id){
		try{
			this.id = Integer.parseInt(id);
		}catch(Exception e){
			this.id=null;
		}
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getAssortmentId() {
		return assortmentId;
	}
	public void setAssortmentId(String assortmentId) {
		this.assortmentId = assortmentId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public void setSortStr(String sort){
		try{
			this.sort = Integer.parseInt(sort);
		}catch(Exception e){
			this.sort=0;
		}
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getRegionid() {
		return regionid;
	}
	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}
}
