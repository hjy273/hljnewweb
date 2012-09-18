package com.cabletech.planinfo.domainobjects;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: Cable tech</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TaskOperationList{
    private String id;
    private String taskid;
    private String operationid;
    public TaskOperationList(){
    }


    public void setId( String id ){
        this.id = id;
    }


    public void setTaskid( String taskid ){
        this.taskid = taskid;
    }


    public void setOperationid( String operationid ){
        this.operationid = operationid;
    }


    public String getId(){
        return id;
    }


    public String getTaskid(){
        return taskid;
    }


    public String getOperationid(){
        return operationid;
    }
}
