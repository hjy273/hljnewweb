package com.cabletech.baseinfo.domainobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * �ṹ��
 *  +������
 * 		+����A
 * 			+��ά��˾A
 * 					+Ѳ����ԱA simid:13522659305 ����ʱ�䣺2004-12-20 12��02��10
 * 					+Ѳ����ԱB
 * 			+��ά��˾B
 * 					+Ѳ����ԱC
 * 		+����B
 * 			+��ά��˾C
 * 					+Ѳ����ԱD
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
