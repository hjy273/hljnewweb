package com.cabletech.baseinfo.beans;

import com.cabletech.commons.base.BaseBean;

public class CableSegmentBean extends BaseBean{
    private String kid = "";
    private String segmentid = "";
    private String segmentname = "";//������
    private String segmentdesc = "";//���¶���
    private String pointa = "";//A��
    private String pointz = "";//Z��
    private String route = "";//·��
    private String laytype = "";//���跽ʽ
    private Float grosslength ;//Ƥ��
    private int corenumber = 0;//��о����
    private String owner = "";//��Ȩ����
    private String producer = "";//��������
    private String builder = "";//ʩ����λ
    private String cabletype = "";//���·�ʽ
    private String finishtime = "";//Ͷ������
    private String fibertype = "";//��о�ͺ�
    private Float reservedlength;//Ԥ������
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
