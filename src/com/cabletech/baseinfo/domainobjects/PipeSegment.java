package com.cabletech.baseinfo.domainobjects;

import com.cabletech.commons.base.BaseBean;

public class PipeSegment extends BaseBean{
    private String id = "";     //�Զ���������
    private String pipeno = "";   //�ܵ��α��
    private String contractorid = "";//��άID
    private String county = "";//ά������
    private String area = "";//Ƭ��
    private String town = "";//��������
    private String pipename = "";//�ܵ�������
    private String isaccept = "";//�Ƿ�����
    private String apoint ;//A��վID
    private String zpoint = "";//Z��վID
    private Float length;//����
    private String material = "";//�ܵ�����
    private String right = "";//��Ȩ
    private String pipehole = "";//
    private String pipetype = "";//�ܿ׹��
    private int subpipehole;//�ӹ�����
    private int unuserpipe;//δ���ӹ���
    private String remark1 = "";//��ע
    private String remark2 = "";//��ע2
    private String blueprintno = "";//ͼֽ���

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
