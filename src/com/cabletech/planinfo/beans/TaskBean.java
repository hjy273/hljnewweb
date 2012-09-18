package com.cabletech.planinfo.beans;

import java.util.ArrayList;
import java.util.List;

import com.cabletech.commons.base.*;
import com.cabletech.planinfo.domainobjects.PlanTaskSubline;
import com.cabletech.planinfo.domainobjects.SubTask;
import com.cabletech.planinfo.domainobjects.TaskOperationList;

public class TaskBean extends BaseBean{

    private String describtion;//��������
    private String subtask;
    //private String id;
    private String functionid;    //�¼ƻ�
    private String subtaskpoint;//�¼ƻ�
    private String subtaskline;//�¼ƻ�
    private String subtasksubline;//�¼ƻ�
    private String regionid;
    private String excutetimes;//ִ�д���
    private String linelevel;//��·����
    private String lineleveltext;
    private String taskpoint;//Ѳ�����  �ƻ�����
    private List  taskOpList = new ArrayList();
    private List  taskPoint = new ArrayList();
    private List taskSubline = new ArrayList();
    private String subline;
    private String taskop;
    private String evaluatepoint;
    
    private String realPoint; //ʵ��Ӧ�е���
    private String ratio;//�ƻ��������ڱ���
    
    private String factpointsnum;
    
    
    
    
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


	public String getRealPoint() {
		return realPoint;
	}


	public void setRealPoint(String realPoint) {
		this.realPoint = realPoint;
	}


	public String getRatio() {
		return ratio;
	}


	public void setRatio(String ratio) {
		this.ratio = ratio;
	}


	public String getEvaluatepoint() {
		return evaluatepoint;
	}


	public void setEvaluatepoint(String evaluatepoint) {
		this.evaluatepoint = evaluatepoint;
	}


	public String getDescribtion(){
        return describtion;
    }


    public String getSubtask(){
        return subtask;
    }


    public void setDescribtion( String describtion ){
        this.describtion = describtion;
    }


    public void setSubtask( String subtask ){
        this.subtask = subtask;
    }


//    public String getId(){
//        return id;
//    }
//
//
//    public void setId( String id ){
//        this.id = id;
//    }


    public String getFunctionid(){
        return functionid;
    }


    public void setFunctionid( String functionid ){
        this.functionid = functionid;
    }


    public String getSubtaskpoint(){
        return subtaskpoint;
    }


    public void setSubtaskpoint( String subtaskpoint ){
        this.subtaskpoint = subtaskpoint;
    }


    public String getSubtaskline(){
        return subtaskline;
    }


    public void setSubtaskline( String subtaskline ){
        this.subtaskline = subtaskline;
    }


    public String getSubtasksubline(){
        return subtasksubline;
    }


    public void setSubtasksubline( String subtasksubline ){
        this.subtasksubline = subtasksubline;
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


    public String getLineleveltext(){
        return lineleveltext;
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


    public void setLineleveltext( String lineleveltext ){
        this.lineleveltext = lineleveltext;
    }


	public List getTaskOpList() {
		return taskOpList;
	}

	public List getTaskPoint() {
		return taskPoint;
	}


	public void setTaskPoint(List taskPoint) {
		this.taskPoint = taskPoint;
	}

	public void setTaskOpList(List taskOpList) {
		this.taskOpList = taskOpList;
	}
	
	
	public void Add(TaskOperationList taskoplist){
		this.getTaskOpList().add(taskoplist);
	}
	public void Add(SubTask subtask){
		this.getTaskPoint().add(subtask);
	}


	public void Add(PlanTaskSubline tasksubline) {
		this.getTaskSubline().add(tasksubline);
		
	}


	public List getTaskSubline() {
		return taskSubline;
	}


	public void setTaskSubline(List taskSubline) {
		this.taskSubline = taskSubline;
	}


	public String getTaskpoint() {
		return taskpoint;
	}


	public void setTaskpoint(String taskpoint) {
		this.taskpoint = taskpoint;
	}


	public String getSubline() {
		return subline;
	}


	public void setSubline(String subline) {
		this.subline = subline;
	}


	public String getTaskop() {
		return taskop;
	}


	public void setTaskop(String taskop) {
		this.taskop = taskop;
	}
}


	
