package com.cabletech.linepatrol.material.beans;

import org.apache.struts.upload.FormFile;

import com.cabletech.commons.base.BaseBean;

public class LinePatrolManagerBean extends BaseBean {
	private FormFile file;
	
    private String id;// �������id

    private String contractorid;// ����λid

    private String cerator;// ������

    private String createdata;// ����ʱ��
    
    private String approverId;//�����
    
    private String approverName;//�����

    private String remark;// ��;

    private String type;// ���뵥����

    private String title;// ���뵥����

    private String[] materialid;// ����id

    private String[] addressid;// ��ŵ�ַid

    private String[] count;// ����

    private String state;// �������� ������������

    private String[] modelname;// �������

    private String[] typename;// ��������

    private String[] modelunit;// ���ϵ�λ

    private String begintime;// ��ʼʱ��

    private String endtime;// ����ʱ��

    private String astate[];// �������

    private String aremark[];// �������

    private String assesor[];// ������

    private String assessdate[];// ��������

    private String materialaddid;// ���뵥id

    private String contractorname[];// ����λ����

    public String[] getAddressid() {
        return addressid;
    }

    public void setAddressid(String[] addressid) {
        this.addressid = addressid;
    }

    public String getCerator() {
        return cerator;
    }

    public void setCerator(String cerator) {
        this.cerator = cerator;
    }

    public String getContractorid() {
        return contractorid;
    }

    public void setContractorid(String contractorid) {
        this.contractorid = contractorid;
    }

    public String[] getCount() {
        return count;
    }

    public void setCount(String[] count) {
        this.count = count;
    }

    public String getCreatedata() {
        return createdata;
    }

    public void setCreatedata(String createdata) {
        this.createdata = createdata;
    }

    @Override
	public String getId() {
        return id;
    }

    @Override
	public void setId(String id) {
        this.id = id;
    }

    public String[] getMaterialid() {
        return materialid;
    }

    public void setMaterialid(String[] materialid) {
        this.materialid = materialid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String[] getModelname() {
        return modelname;
    }

    public void setModelname(String[] modelname) {
        this.modelname = modelname;
    }

    public String[] getTypename() {
        return typename;
    }

    public void setTypename(String[] typename) {
        this.typename = typename;
    }

    public String[] getAremark() {
        return aremark;
    }

    public void setAremark(String aremark[]) {
        this.aremark = aremark;
    }

    public String[] getAssesor() {
        return assesor;
    }

    public void setAssesor(String assesor[]) {
        this.assesor = assesor;
    }

    public String[] getAssessdate() {
        return assessdate;
    }

    public void setAssessdate(String assessdate[]) {
        this.assessdate = assessdate;
    }

    public String[] getAstate() {
        return astate;
    }

    public void setAstate(String astate[]) {
        this.astate = astate;
    }

    public String getMaterialaddid() {
        return materialaddid;
    }

    public void setMaterialaddid(String materialaddid) {
        this.materialaddid = materialaddid;
    }

    public String[] getContractorname() {
        return contractorname;
    }

    public void setContractorname(String contractorname[]) {
        this.contractorname = contractorname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getModelunit() {
        return modelunit;
    }

    public void setModelunit(String[] modelunit) {
        this.modelunit = modelunit;
    }

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

}
