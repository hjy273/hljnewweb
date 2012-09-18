package com.cabletech.planinfo.domainobjects;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SubTask{
    private String id;
    private String taskid;
    private String objecttype;
    private String objectid;
    private String parentid;
    public SubTask(){
    }
    
    public SubTask(String id,String taskid, String objectid){
    	this.id = id;
    	this.taskid = taskid;
    	this.objectid = objectid;
    }

    public String getId(){
        return id;
    }


    public void setId( String id ){
        this.id = id;
    }


    public String getTaskid(){
        return taskid;
    }


    public void setTaskid( String taskid ){
        this.taskid = taskid;
    }


    public String getObjecttype(){
        return objecttype;
    }


    public void setObjecttype( String objecttype ){
        this.objecttype = objecttype;
    }


    public String getObjectid(){
        return objectid;
    }


    public void setObjectid( String objectid ){
        this.objectid = objectid;
    }


    public String getParentid(){
        return parentid;
    }


    public void setParentid( String parentid ){
        this.parentid = parentid;
    }

}