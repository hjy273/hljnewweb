package com.cabletech.baseinfo.beans;

import com.cabletech.commons.base.BaseBean;

public class CableSegmentBean extends BaseBean{
    private String kid = "";
    private String segmentid = "";
    private String segmentname = "";//光缆名
    private String segmentdesc = "";//光缆段名
    private String pointa = "";//A点
    private String pointz = "";//Z点
    private String route = "";//路由
    private String laytype = "";//敷设方式
    private Float grosslength ;//皮长
    private int corenumber = 0;//纤芯数量
    private String owner = "";//产权属性
    private String producer = "";//生产厂家
    private String builder = "";//施工单位
    private String cabletype = "";//成缆方式
    private String finishtime = "";//投产日期
    private String fibertype = "";//纤芯型号
    private Float reservedlength;//预留长度
    private String remark = "";//

    public CableSegmentBean(){
    }

    public void setKid( String kid ){
        this.kid = kid;
    }


    public void setSegmentid( String segmentid ){
        this.segmentid = segmentid;
    }


    public void setSegmentname( String segmentname ){
        this.segmentname = segmentname;
    }


    public void setSegmentdesc( String segmentdesc ){
        this.segmentdesc = segmentdesc;
    }


    public void setPointa( String pointa ){
        this.pointa = pointa;
    }


    public void setPointz( String pointz ){
        this.pointz = pointz;
    }


    public void setLaytype( String laytype ){
        this.laytype = laytype;
    }


    public void setRoute( String route ){
        this.route = route;
    }


    public void setCorenumber( int corenumber ){
        this.corenumber = corenumber;
    }


    public void setGrosslength( Float grosslength ){
        this.grosslength = grosslength;
    }


    public void setOwner( String owner ){
        this.owner = owner;
    }


    public void setProducer( String producer ){
        this.producer = producer;
    }


    public void setBuilder( String builder ){
        this.builder = builder;
    }


    public void setCabletype( String cabletype ){
        this.cabletype = cabletype;
    }


    public void setFinishtime( String finishtime ){
        this.finishtime = finishtime;
    }


    public void setFibertype( String fibertype ){
        this.fibertype = fibertype;
    }


    public void setReservedlength( Float reservedlength ){
        this.reservedlength = reservedlength;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public String getKid(){
        return kid;
    }


    public String getSegmentid(){
        return segmentid;
    }


    public String getSegmentname(){
        return segmentname;
    }


    public String getSegmentdesc(){
        return segmentdesc;
    }


    public String getPointa(){
        return pointa;
    }


    public String getPointz(){
        return pointz;
    }


    public String getLaytype(){
        return laytype;
    }


    public String getRoute(){
        return route;
    }


    public int getCorenumber(){
        return corenumber;
    }


    public Float getGrosslength(){
        return grosslength;
    }


    public String getOwner(){
        return owner;
    }


    public String getProducer(){
        return producer;
    }


    public String getBuilder(){
        return builder;
    }


    public String getCabletype(){
        return cabletype;
    }


    public String getFinishtime(){
        return finishtime;
    }


    public String getFibertype(){
        return fibertype;
    }


    public Float getReservedlength(){
        return reservedlength;
    }


    public String getRemark(){
        return remark;
    }

}
