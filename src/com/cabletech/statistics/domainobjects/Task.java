package com.cabletech.statistics.domainobjects;

public class Task{
    private String taskid;
    private String taskname;
    private String times;
    private String taskdisc = "";
    private String executes = "";

    public Task(){
    }


    public void setTaskid( String taskid ){
        this.taskid = taskid;
    }


    public void setTaskname( String taskname ){
        this.taskname = taskname;
    }


    public void setTimes( String times ){
        this.times = times;
    }


    public void setTaskdisc( String taskdisc ){
        this.taskdisc = taskdisc;
    }


    public void setExecutes( String executes ){
        this.executes = executes;
    }


    public String getTaskid(){
        return taskid;
    }


    public String getTaskname(){
        return taskname;
    }


    public String getTimes(){
        return times;
    }


    public String getTaskdisc(){
        return taskdisc;
    }


    public String getExecutes(){
        return executes;
    }
}
