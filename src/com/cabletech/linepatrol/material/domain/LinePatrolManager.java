package com.cabletech.linepatrol.material.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class LinePatrolManager extends BaseDomainObject {
	public static final String LP_MT_NEW="LP_MT_NEW";//审核表
	
	public static final String LP_MT_USED = "LP_MT_USED";
	
	public static final String MATERIAL_MODULE="material";//故障模块
	/**
	 * 
	 */
	private static final long serialVersionUID = 1669443644101194002L;

	private String id;// 材料入库id

    private String contractorid;// 监理单位id

    private String cerator;// 申请人

    private String createdata;// 创建时间

    private String remark;// 用途

    private String type;// 申请单类型

    private String title;// 申请单标题

    private String[] materialid;// 材料id

    private String[] addressid;// 存放地址id

    private String[] count;// 数量

    private String state;// 材料类型 新增还是利旧

    private String[] modelname;// 规格名称

    private String[] typename;// 类型名称

    private String[] modelunit;// 材料单位

    private String begintime;// 开始时间

    private String endtime;// 结束时间

    private String astate[];// 审批结果

    private String aremark[];// 审批意见

    private String assesor[];// 审批人

    private String assessdate[];// 审批日期

    private String materialaddid;// 申请单id

    private String contractorname[];// 监理单位名称

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

    public String getId() {
        return id;
    }

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
}
