package com.cabletech.planinfo.domainobjects;

public class StencilSubTask {
	private String ID;

	private String stencilid;//模板id

	private String description;//任务描述

	private String linelevel;//线路级别

	private Integer excutetimes;//执行次数
	
    private String evaluatepoint;
    
    private String factpointsnum ; // 实际点数 add by guixy 2009-8-28



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

	public String getEvaluatepoint() {
		return evaluatepoint;
	}

	public void setEvaluatepoint(String evaluatepoint) {
		this.evaluatepoint = evaluatepoint;
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
		return ID+","+stencilid+","+ description+","+linelevel+","+excutetimes;
	}
}
