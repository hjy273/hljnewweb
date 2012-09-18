package com.cabletech.toolsmanage.beans;

import com.cabletech.commons.base.*;

public class ToolsInfoBean extends BaseBean{
    //设备入库报废_设备对应表
    private String stockid = "";
    private String id = "";
    private String enumber = "";
    private String contractorname = "";
    private String username = "";
    //设备入库报废表
    private String userid = "";
    private String contractorid = "";
    private String time = "";
    private String adress = "";
    private String patrolid = "";
    private String patrolname = "";
    private String remark = "";
    private String type = "";
    //设备基本表
    private String unit = "";
    private String name = "";
    private String source = "";
    private String style = "";
    private String factory = "";
    private String factorysn = "";
    private String isasset = "";
    private String assetsn = "";
    private String tooluse = "";
    private String regionid = "";

    //设备库存表
    private String essenumber = "";
    private String shouldnumber = "";
    private String portmainnumber = "";
    private String mainnumber = "";

    //设备报修送修表
    private String mainid = "";
    private String mainname = "";
    private String mainunit = "";
    private String mainadd = "";
    private String mainphone = "";
    private String mainremark = "";
    //设备领用返还表
    private String useid = "";
    private String useremark = "";
    private String usetype = "";
    private String usename = "";
    private String useunit = "";

    private String buserid = "";
    private String bname = "";
    private String bunit = "";
    private String bremark = "";
    private String btime = "";
    private String begintime = "";
    private String endtime = "";
    private String bnumber = "";
    private String back = "";

    //////
    private String esselownumber = "";
    private String essehighnumber = "";
    private String portlownumber = "";
    private String porthighnumber = "";
    private String mainlownumber = "";
    private String mainhighnumber = "";

    public String getEsselownumber(){
        return this.esselownumber;
    }


    public void setEsselownumber( String esselownumber ){
        this.esselownumber = esselownumber;
    }


    public String getEssehighnumber(){
        return this.essehighnumber;
    }


    public void setEssehighnumber( String essehighnumber ){
        this.essehighnumber = essehighnumber;
    }


    public String getPortlownumber(){
        return this.portlownumber;
    }


    public void setPortlownumber( String portlownumber ){
        this.portlownumber = portlownumber;
    }


    public String getPorthighnumber(){
        return this.porthighnumber;
    }


    public void setPorthighnumber( String porthighnumber ){
        this.porthighnumber = porthighnumber;
    }


    public String getMainlownumber(){
        return this.mainlownumber;
    }


    public void setMainlownumber( String mainlownumber ){
        this.mainlownumber = mainlownumber;
    }


    public String getMainhighnumber(){
        return this.mainhighnumber;
    }


    public void setMainhighnumber( String mainhighnumber ){
        this.mainhighnumber = mainhighnumber;
    }


    public String getBack(){
        return this.back;
    }


    public void setBack( String back ){
        this.back = back;
    }


    public String getBnumber(){
        return this.bnumber;
    }


    public void setBnumber( String bnumber ){
        this.bnumber = bnumber;
    }


    public void setBtime( String btime ){
        this.btime = btime;
    }


    public String getBtime(){
        return this.btime;
    }


    public String getBremark(){
        return this.bremark;
    }


    public void setBremark( String bremark ){
        this.bremark = bremark;
    }


    public String getBunit(){
        return this.bunit;
    }


    public void setBunit( String bunit ){
        this.bunit = bunit;
    }


    public String getBname(){
        return this.bname;
    }


    public void setBname( String bname ){
        this.bname = bname;
    }


    public String getBuserid(){
        return this.buserid;
    }


    public void setBuserid( String buserid ){
        this.buserid = buserid;
    }


    public String getEndtime(){
        return this.endtime;
    }


    public void setEndtime( String endtime ){
        this.endtime = endtime;
    }


    public String getBegintime(){
        return this.begintime;
    }


    public void setBegintime( String begintime ){
        this.begintime = begintime;
    }


    public String getUnit(){
        return this.unit;
    }


    public void setUnit( String unit ){
        this.unit = unit;
    }


    public String getUseunit(){
        return this.useunit;
    }


    public void setUseunit( String useunit ){
        this.useunit = useunit;
    }


    public String getUsename(){
        return this.usename;
    }


    public void setUsename( String usename ){
        this.usename = usename;
    }


    public String getUsetype(){
        return this.usetype;
    }


    public void setUsetype( String usetype ){
        this.usetype = usetype;
    }


    public String getUseremark(){
        return this.useremark;
    }


    public void setUseremark( String useremark ){
        this.useremark = useremark;
    }


    public String getUseid(){
        return this.useid;
    }


    public void setUseid( String useid ){
        this.useid = useid;
    }


    public String getMainphone(){
        return this.mainphone;
    }


    public void setMainphone( String mainphone ){
        this.mainphone = mainphone;
    }


    public String getMainadd(){
        return this.mainadd;
    }


    public void setMainadd( String mainadd ){
        this.mainadd = mainadd;
    }


    public String getMainremark(){
        return this.mainremark;
    }


    public void setMainremark( String mainremark ){
        this.mainremark = mainremark;
    }


    public String getMainunit(){
        return this.mainunit;
    }


    public void setMainunit( String mainunit ){
        this.mainunit = mainunit;
    }


    public String getMainname(){
        return this.mainname;
    }


    public void setMainname( String mainname ){
        this.mainname = mainname;
    }


    public String getMainid(){
        return this.mainid;
    }


    public void setMainid( String mainid ){
        this.mainid = mainid;
    }


    public String getMainnumber(){
        return this.mainnumber;
    }


    public void setMainnumber( String mainnumber ){
        this.mainnumber = mainnumber;
    }


    public String getPortmainnumber(){
        return this.portmainnumber;
    }


    public void setPortmainnumber( String portmainnumber ){
        this.portmainnumber = portmainnumber;
    }


    public String getShouldnumber(){
        return this.shouldnumber;
    }


    public void setShouldnumber( String shouldnumber ){
        this.shouldnumber = shouldnumber;
    }


    public String getEssenumber(){
        return this.essenumber;
    }


    public void setEssenumber( String essenumber ){
        this.essenumber = essenumber;
    }


    public String getTooluse(){
        return this.tooluse;
    }


    public void setTooluse( String tooluse ){
        this.tooluse = tooluse;
    }


    public String getAssetsn(){
        return this.assetsn;
    }


    public void setAssetsn( String assetsn ){
        this.assetsn = assetsn;
    }


    public String getIsasset(){
        return this.isasset;
    }


    public void setIsasset( String isasset ){
        this.isasset = isasset;

    }


    public String getFactorysn(){
        return this.factorysn;
    }


    public void setFactorysn( String factorysn ){
        this.factorysn = factorysn;
    }


    public String getFactory(){
        return this.factory;
    }


    public void setFactory( String factory ){
        this.factory = factory;
    }


    public String getStyle(){
        return this.style;
    }


    public void setStyle( String style ){
        this.style = style;
    }


    public String getSource(){
        return this.source;
    }


    public void setSource( String source ){
        this.source = source;
    }


    public String getName(){
        return this.name;
    }


    public void setName( String name ){
        this.name = name;
    }


    public String getType(){
        return this.type;
    }


    public void setType( String type ){
        this.type = type;
    }


    public String getRemark(){
        return this.remark;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public String getPatrolid(){
        return this.patrolid;
    }


    public void setPatrolid( String partrolid ){
        this.patrolid = partrolid;
    }


    public String getPatrolname(){
        return this.patrolname;
    }


    public void setPatrolname( String patrolname ){
        this.patrolname = patrolname;
    }


    public String getAdress(){
        return this.adress;
    }


    public void setAdress( String address ){
        this.adress = address;
    }


    public String getTime(){
        return this.time;
    }


    public void setTime( String time ){
        this.time = time;
    }


    public String getContractorid(){
        return this.contractorid;
    }


    public void setContractorid( String contractorid ){
        this.contractorid = contractorid;
    }


    public String getUserid(){
        return this.userid;
    }


    public void setUserid( String userid ){
        this.userid = userid;
    }


    public String getEnumber(){
        return this.enumber;
    }


    public void setEnumber( String number ){
        this.enumber = number;
    }


    public String getId(){
        return this.id;
    }


    public void setId( String id ){
        this.id = id;
    }


    public String getStockid(){
        return this.stockid;
    }


    public void setStockid( String stockid ){
        this.stockid = stockid;
    }


    public String getContractorname(){
        return this.contractorname;
    }


    public void setContractorname( String contractorname ){
        this.contractorname = contractorname;
    }


    public String getUsername(){
        return this.username;
    }


    public String getRegionid(){
        return regionid;
    }


    public void setUsername( String username ){
        this.username = username;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public ToolsInfoBean(){
    }

}
