package com.cabletech.baseinfo.domainobjects;

import com.cabletech.commons.base.*;

public class Terminal extends BaseBean{

    public Terminal(){
    }


    private String terminalID;//系统id
    private String deviceID;//设备编号
    private String terminalType;
    private String produceMan;
    private String machineSerial;
    private String simNumber;
    private String simType;
    private String ownerID;
    private String regionID;
    private String terminalModel;
    private String state;
	private String contractorID;
    private String password;
    private String holder;
    private String IMEI;

    
    public String getIMEI() {
		return IMEI;
	}
	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}
    public String getHolder() {
		return holder;
	}
	public void setHolder(String holder) {
		this.holder = holder;
	}


	public String getTerminalID(){
        return terminalID;
    }


    public void setTerminalID( String terminalID ){
        this.terminalID = terminalID;
    }


    public String getDeviceID() {
		return deviceID;
	}


	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}


	public String getTerminalType(){
        return terminalType;
    }


    public void setTerminalType( String terminalType ){
        this.terminalType = terminalType;
    }


    public String getProduceMan(){
        return produceMan;
    }


    public void setProduceMan( String produceMan ){
        this.produceMan = produceMan;
    }


    public String getMachineSerial(){
        return machineSerial;
    }


    public void setMachineSerial( String machineSerial ){
        this.machineSerial = machineSerial;
    }


    public String getSimNumber(){
        return simNumber;
    }


    public void setSimNumber( String simNumber ){
        this.simNumber = simNumber;
    }


    public String getSimType(){
        return simType;
    }


    public void setSimType( String simType ){
        this.simType = simType;
    }


    public String getOwnerID(){
        return ownerID;
    }


    public void setOwnerID( String ownerID ){
        this.ownerID = ownerID;
    }


    public String getRegionID(){
        return regionID;
    }


    public void setRegionID( String regionID ){
        this.regionID = regionID;
    }


    public String getTerminalModel(){
        return terminalModel;
    }


    public void setTerminalModel( String terminalModel ){
        this.terminalModel = terminalModel;
    }


    public String getState(){
        return state;
    }


    public void setState( String state ){
        this.state = state;
    }


    public String getContractorID(){
        return contractorID;
    }


    public void setContractorID( String contractorID ){
        this.contractorID = contractorID;
    }


    public String getPassword(){
        return password;
    }


    public void setPassword( String password ){
        this.password = password;
    }

}
