package com.cabletech.planinfo.beans;

import java.util.ArrayList;
import java.util.List;

import com.cabletech.commons.base.BaseBean;
import com.cabletech.planinfo.domainobjects.StencilSubTaskOper;
import com.cabletech.planinfo.domainobjects.StencilTaskPoint;

public class StencilSubTaskBean extends BaseBean {
	private String ID;

	private String stencilid;//模板id

	private String description;//任务描述

	private String linelevel;//线路级别

	private Integer excutetimes;//执行次数
	private String lineleveltext;
	private List stenciltaskpoint = new ArrayList(); //巡检任务点
	private List stencilsubtaskop = new ArrayList();;//操作类型
	private String taskpoint;
	private String subline;
	
    private String factpointsnum ; // 实际点数 add by guixy 2009-8-28
	
    private String evaluatepoint;
    

	public String getEvaluatepoint() {
		return evaluatepoint;
	}

	public void setEvaluatepoint(String evaluatepoint) {
		this.evaluatepoint = evaluatepoint;
	}

	public String getSubline() {
		return subline;
	}

	public void setSubline(String subline) {
		this.subline = subline;
	}

	public String getTaskpoint() {
		return taskpoint;
	}

	public void setTaskpoint(String taskpoint) {
		this.taskpoint = taskpoint;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getExcutetimes() {
		return excutetimes;
	}

	public void setExcutetimes(Integer excutetimes) {
		this.excutetimes = excutetimes;
	}

	public String getLinelevel() {
		return linelevel;
	}

	public void setLinelevel(String linelevel) {
		this.linelevel = linelevel;
	}

	public String getStencilid() {
		return stencilid;
	}

	public void setStencilid(String stencilid) {
		this.stencilid = stencilid;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}
	public String toString (){
		return ID+","+stencilid+","+ description+","+lineleveltext+","+excutetimes;
	}

	

	
	public List getStenciltaskpoint() {
		return stenciltaskpoint;
	}

	public void setStenciltaskpoint(List stenciltaskpoint) {
		this.stenciltaskpoint = stenciltaskpoint;
	}

	public void add(StencilTaskPoint taskpoint){
		this.getStenciltaskpoint().add(taskpoint);
	}

	public List getStencilsubtaskop() {
		return stencilsubtaskop;
	}

	public void setStencilsubtaskop(List stencilsubtaskop) {
		this.stencilsubtaskop = stencilsubtaskop;
	}
	public void add(StencilSubTaskOper subtaskop){
		this.getStencilsubtaskop().add(subtaskop);
	}

	public void clear() {
		this.ID="";
		this.description="";
		this.excutetimes = new Integer(0);
		this.linelevel =  "";
		this.lineleveltext="";
		
	}

	public String getLineleveltext() {
		return lineleveltext;
	}

	public void setLineleveltext(String lineleveltext) {
		this.lineleveltext = lineleveltext;
	}

	/**
	 * @return the factpointsnum
	 */
	public String getFactpointsnum() {
		return factpointsnum;
	}

	/**
	 * @param factpointsnum the factpointsnum to set
	 */
	public void setFactpointsnum(String factpointsnum) {
		this.factpointsnum = factpointsnum;
	}
}
