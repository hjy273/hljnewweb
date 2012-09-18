package com.cabletech.linepatrol.resource.model;

public class DatumInfo {
	public static final String PASSED = "2";
	public static final String NOT_PASSED = "1";
    public static final String SUBMIT_STATE = "0";
    public static String TYPE="LP_DATUM";
	private String id;
	private String typeId;
	private String name;
	private String info;
	private String contractorId;
	private String regionId;
	private String datumState;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
    public String getDatumState() {
        return datumState;
    }
    public void setDatumState(String datumState) {
        this.datumState = datumState;
    }
}
