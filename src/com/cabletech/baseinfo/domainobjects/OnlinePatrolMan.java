package com.cabletech.baseinfo.domainobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * 结构：
 *  +总区域
 * 		+区域A
 * 			+代维公司A
 * 					+巡检人员A simid:13522659305 最近活动时间：2004-12-20 12：02：10
 * 					+巡检人员B
 * 			+代维公司B
 * 					+巡检人员C
 * 		+区域B
 * 			+代维公司C
 * 					+巡检人员D
 * 					
 * 
 * @author Administrator
 *
 */
public class OnlinePatrolMan {
	private String name;
	private String id;
	private String onlineNum;
	private List child = new ArrayList();
	public List getChild() {
		return child;
	}
	public void setChild(List child) {
		this.child = child;
	}
	public String getOnlineNum() {
		return onlineNum;
	}
	public void setOnlineNum(String onlineNum) {
		this.onlineNum = onlineNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void addChild(OnlinePatrolMan onlineMan){
		this.child.add(onlineMan);
	}
	public void addChild(HoldSim holdsim){
		this.child.add(holdsim);
	}
}
