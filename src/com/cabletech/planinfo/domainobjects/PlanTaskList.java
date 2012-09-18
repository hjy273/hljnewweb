package com.cabletech.planinfo.domainobjects;

public class PlanTaskList{
    private String id;
    private String planid;
    private String taskid;
    public PlanTaskList(){
    }


    public void setId( String id ){
        this.id = id;
    }


    public void setPlanid( String planid ){
        this.planid = planid;
    }


    public void setTaskid( String taskid ){
        this.taskid = taskid;
    }


    public String getId(){
        return id;
    }


    public String getPlanid(){
        return planid;
    }


    public String getTaskid(){
        return taskid;
    }
}
