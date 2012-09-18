package com.cabletech.planinfo.domainobjects;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Task{
    public Task(){
    }


    private String describtion; //巡检操作类型
    private String id;
    private String regionid;
    private String excutetimes; //执行次数
    private String linelevel; //线路级别
    private String taskpoint;//巡检点数
    private String evaluatepoint;//评估巡检点数
    
    private String factpointsnum; // 实际点数
    
    public String getEvaluatepoint() {
		return evaluatepoint;
	}


	public void setEvaluatepoint(String evaluatepoint) {
		this.evaluatepoint = evaluatepoint;
	}


	public String getTaskpoint() {
		return taskpoint;
	}


	public void setTaskpoint(String taskpoint) {
		this.taskpoint = taskpoint;
	}


	public String getDescribtion(){
        return describtion;
    }


    public void setDescribtion( String describtion ){
        this.describtion = describtion;
    }


    public String getId(){
        return id;
    }


    public void setId( String id ){
        this.id = id;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getExcutetimes(){
        return excutetimes;
    }


    public String getLinelevel(){
        return linelevel;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setExcutetimes( String excutetimes ){
        this.excutetimes = excutetimes;
    }


    public void setLinelevel( String linelevel ){
        this.linelevel = linelevel;
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
