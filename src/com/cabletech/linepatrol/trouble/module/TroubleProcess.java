package com.cabletech.linepatrol.trouble.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTroubleProcess entity. @author MyEclipse Persistence Tools
 */

public class TroubleProcess extends BaseDomainObject {

	// Fields

	private String id;
	private String troubleId;
	private String startAddress;//出发地点
	private String startAddressGpsX;
	private String startAddressGpsY;//出发地点y坐标
	private Date startTime;//出发时间
	private Date startTimeRef;//参考出发时间
	private Date arriveTime;//到达机房时间
	private Date arriveTimeRef;//参考到达机房时间
	private Date arriveTroubleTime;//到达故障现场时间
	private Date arriveTroubleTimeRef;//参考到达故障现场时间
	private Date findTroubleTime;//找到故障点时间
	private Date findTroubleTimeRef;//参考找到故障点时间
	private Date returnTime;//撤离现场时间
	private Date returnTimeRef;//参考撤离现场时间
	private String arriveTroubleGpsX;
	private String arriveTroubleGpsY;//到达现场y坐标
	private String arriveTerminalId;//设备编号
	private String arriveProessMan;//处理人员

	
	private String arriveTimeGpsX;
	private String arriveTimeGpsY;//达机房xy坐标
	
	private String findTroubleX;
	private String findTroubleY;//找到故障点xy坐标
	
	private String returnGpsX;
	private String returnGpsY;//撤离现场xy坐标 
	// Constructors

	/** default constructor */
	public TroubleProcess() {
	}

	/** minimal constructor */
	public TroubleProcess(String id) {
		this.id = id;
	}

	
	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTroubleId() {
		return this.troubleId;
	}

	public void setTroubleId(String troubleId) {
		this.troubleId = troubleId;
	}

	public String getStartAddress() {
		return this.startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public String getStartAddressGpsX() {
		return this.startAddressGpsX;
	}

	public void setStartAddressGpsX(String startAddressGpsX) {
		this.startAddressGpsX = startAddressGpsX;
	}

	public String getStartAddressGpsY() {
		return this.startAddressGpsY;
	}

	public void setStartAddressGpsY(String startAddressGpsY) {
		this.startAddressGpsY = startAddressGpsY;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTimeRef() {
		return this.startTimeRef;
	}

	public void setStartTimeRef(Date startTimeRef) {
		this.startTimeRef = startTimeRef;
	}

	public Date getArriveTime() {
		return this.arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Date getArriveTimeRef() {
		return this.arriveTimeRef;
	}

	public void setArriveTimeRef(Date arriveTimeRef) {
		this.arriveTimeRef = arriveTimeRef;
	}

	public Date getArriveTroubleTime() {
		return this.arriveTroubleTime;
	}

	public void setArriveTroubleTime(Date arriveTroubleTime) {
		this.arriveTroubleTime = arriveTroubleTime;
	}

	public Date getArriveTroubleTimeRef() {
		return this.arriveTroubleTimeRef;
	}

	public void setArriveTroubleTimeRef(Date arriveTroubleTimeRef) {
		this.arriveTroubleTimeRef = arriveTroubleTimeRef;
	}

	public Date getFindTroubleTime() {
		return this.findTroubleTime;
	}

	public void setFindTroubleTime(Date findTroubleTime) {
		this.findTroubleTime = findTroubleTime;
	}

	public Date getFindTroubleTimeRef() {
		return this.findTroubleTimeRef;
	}

	public void setFindTroubleTimeRef(Date findTroubleTimeRef) {
		this.findTroubleTimeRef = findTroubleTimeRef;
	}

	public Date getReturnTime() {
		return this.returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public Date getReturnTimeRef() {
		return this.returnTimeRef;
	}

	public void setReturnTimeRef(Date returnTimeRef) {
		this.returnTimeRef = returnTimeRef;
	}

	public String getArriveTroubleGpsX() {
		return this.arriveTroubleGpsX;
	}

	public void setArriveTroubleGpsX(String arriveTroubleGpsX) {
		this.arriveTroubleGpsX = arriveTroubleGpsX;
	}

	public String getArriveTroubleGpsY() {
		return this.arriveTroubleGpsY;
	}

	public void setArriveTroubleGpsY(String arriveTroubleGpsY) {
		this.arriveTroubleGpsY = arriveTroubleGpsY;
	}

	public String getArriveTerminalId() {
		return this.arriveTerminalId;
	}

	public void setArriveTerminalId(String arriveTerminalId) {
		this.arriveTerminalId = arriveTerminalId;
	}

	public String getArriveProessMan() {
		return this.arriveProessMan;
	}

	public void setArriveProessMan(String arriveProessMan) {
		this.arriveProessMan = arriveProessMan;
	}

	public String getArriveTimeGpsX() {
		return arriveTimeGpsX;
	}

	public void setArriveTimeGpsX(String arriveTimeGpsX) {
		this.arriveTimeGpsX = arriveTimeGpsX;
	}

	public String getArriveTimeGpsY() {
		return arriveTimeGpsY;
	}

	public void setArriveTimeGpsY(String arriveTimeGpsY) {
		this.arriveTimeGpsY = arriveTimeGpsY;
	}

	public String getFindTroubleX() {
		return findTroubleX;
	}

	public void setFindTroubleX(String findTroubleX) {
		this.findTroubleX = findTroubleX;
	}

	public String getFindTroubleY() {
		return findTroubleY;
	}

	public void setFindTroubleY(String findTroubleY) {
		this.findTroubleY = findTroubleY;
	}

	public String getReturnGpsX() {
		return returnGpsX;
	}

	public void setReturnGpsX(String returnGpsX) {
		this.returnGpsX = returnGpsX;
	}

	public String getReturnGpsY() {
		return returnGpsY;
	}

	public void setReturnGpsY(String returnGpsY) {
		this.returnGpsY = returnGpsY;
	}

}