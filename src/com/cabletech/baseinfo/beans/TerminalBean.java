package com.cabletech.baseinfo.beans;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;

public class TerminalBean extends BaseBean{
    private String id = "";
    public TerminalBean(){
    }


    private String terminalID;
    private String deviceID;//…Ë±∏±‡∫≈
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
    private String phonebook = "";
    private String flag = "";
    private String name = "";
    private String phone = "";
    private String setnumber = "";
    private String number = "";
    private String holder;
    private String IMEI;
    private String starttime;
    private String endtime;

    
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


    public String getPhonebook(){
        return phonebook;
    }


    public String getFlag(){
        return flag;
    }


    public String getName(){
        return name;
    }


    public String getPhone(){
        return phone;
    }


    public String getSetnumber(){
        return setnumber;
    }


    public String getNumber(){
        return number;
    }


    public void setPassword( String password ){
        this.password = password;
    }


    public void setPhonebook( String phonebook ){
        this.phonebook = phonebook;
    }


    public void setFlag( String flag ){
        this.flag = flag;
    }


    public void setName( String name ){
        this.name = name;
    }


    public void setPhone( String phone ){
        this.phone = phone;
    }


    public void setSetnumber( String setnumber ){
        this.setnumber = setnumber;
    }


    public void setNumber( String number ){
        this.number = number;
    }


    public void reset( ActionMapping mapping,
        javax.servlet.http.HttpServletRequest request ){
        this.setTerminalID( null );

    }


    public String getEndtime() {
        return endtime;
    }


    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }


    public String getStarttime() {
        return starttime;
    }


    public String getId(){
        return id;
    }


    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }


    public void setId( String id ){
        this.id = id;
    }
}
