package com.cabletech.baseinfo.domainobjects;

import com.cabletech.commons.base.BaseBean;

public class PipeSegment extends BaseBean{
    private String id = "";     //自动增长序列
    private String pipeno = "";   //管道段编号
    private String contractorid = "";//代维ID
    private String county = "";//维护区域
    private String area = "";//片区
    private String town = "";//所属乡镇
    private String pipename = "";//管道段名称
    private String isaccept = "";//是否验收
    private String apoint ;//A基站ID
    private String zpoint = "";//Z基站ID
    private Float length;//距离
    private String material = "";//管道材料
    private String right = "";//产权
    private String pipehole = "";//
    private String pipetype = "";//管孔规格
    private int subpipehole;//子管总数
    private int unuserpipe;//未用子管数
    private String remark1 = "";//备注
    private String remark2 = "";//备注2
    private String blueprintno = "";//图纸编号

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPipeno() {
		return pipeno;
	}

	public void setPipeno(String pipeno) {
		this.pipeno = pipeno;
	}

	public String getContractorid() {
		return contractorid;
	}

	public void setContractorid(String contractorid) {
		this.contractorid = contractorid;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getPipename() {
		return pipename;
	}

	public void setPipename(String pipename) {
		this.pipename = pipename;
	}

	public String getIsaccept() {
		return isaccept;
	}

	public void setIsaccept(String isaccept) {
		this.isaccept = isaccept;
	}

	public String getApoint() {
		return apoint;
	}

	public void setApoint(String apoint) {
		this.apoint = apoint;
	}

	public String getZpoint() {
		return zpoint;
	}

	public void setZpoint(String zpoint) {
		this.zpoint = zpoint;
	}

	public Float getLength() {
		return length;
	}

	public void setLength(Float length) {
		this.length = length;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public String getPipehole() {
		return pipehole;
	}

	public void setPipehole(String pipehole) {
		this.pipehole = pipehole;
	}

	public String getPipetype() {
		return pipetype;
	}

	public void setPipetype(String pipetype) {
		this.pipetype = pipetype;
	}

	public int getSubpipehole() {
		return subpipehole;
	}

	public void setSubpipehole(int subpipehole) {
		this.subpipehole = subpipehole;
	}

	public int getUnuserpipe() {
		return unuserpipe;
	}

	public void setUnuserpipe(int unuserpipe) {
		this.unuserpipe = unuserpipe;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getBlueprintno() {
		return blueprintno;
	}

	public void setBlueprintno(String blueprintno) {
		this.blueprintno = blueprintno;
	}
}
