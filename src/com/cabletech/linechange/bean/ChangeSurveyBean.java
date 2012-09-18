package com.cabletech.linechange.bean;

import com.cabletech.commons.base.BaseBean;
import com.cabletech.commons.util.DateUtil;

public class ChangeSurveyBean extends BaseBean{
    // fields
    private java.lang.String approvedate;
    private java.lang.String approvedept;
    private java.lang.String approver;
    private java.lang.String approveremark;
    private java.lang.String approveresult;
    private java.lang.Float budget;
    private java.lang.String changeid;
    private java.lang.String fillindate;
    private java.lang.String id;
    private java.lang.String principal;
    private java.lang.String surveydate;
    private java.lang.String surveydatum;
    private java.lang.String surveyremark;
    private String begintime;
    private String endtime;
    private Float maxbudget;
    private String step;
    private String changename;
    private String changepro;
    private String lineclass;
    private String changeaddr;
    private java.lang.String saddr;
    private java.lang.Float sexpense;
    private java.lang.String sgrade;
    private java.lang.String sname;
    private java.lang.String sperson;
    private java.lang.String sphone;
    private String surveytype;

    public ChangeSurveyBean(){
        super();
        approvedate = DateUtil.getNowDateString( "yyyy/MM/dd" );
        fillindate = DateUtil.getNowDateString( "yyyy/MM/dd" );
        surveydate = DateUtil.getNowDateString( "yyyy/MM/dd" );

    }


    /**
     * Return the value associated with the column: APPROVEDATE
     */
    public java.lang.String getApprovedate(){
        return approvedate;
    }


    /**
     * Set the value related to the column: APPROVEDATE
     * @param approvedate the APPROVEDATE value
     */
    public void setApprovedate( java.lang.String approvedate ){
        this.approvedate = approvedate;
    }


    /**
     * Return the value associated with the column: APPROVEDEPT
     */
    public java.lang.String getApprovedept(){
        return approvedept;
    }


    /**
     * Set the value related to the column: APPROVEDEPT
     * @param approvedept the APPROVEDEPT value
     */
    public void setApprovedept( java.lang.String approvedept ){
        this.approvedept = approvedept;
    }


    /**
     * Return the value associated with the column: APPROVER
     */
    public java.lang.String getApprover(){
        return approver;
    }


    /**
     * Set the value related to the column: APPROVER
     * @param approver the APPROVER value
     */
    public void setApprover( java.lang.String approver ){
        this.approver = approver;
    }


    /**
     * Return the value associated with the column: APPROVEREMARK
     */
    public java.lang.String getApproveremark(){
        return approveremark;
    }


    /**
     * Set the value related to the column: APPROVEREMARK
     * @param approveremark the APPROVEREMARK value
     */
    public void setApproveremark( java.lang.String approveremark ){
        this.approveremark = approveremark;
    }


    /**
     * Return the value associated with the column: APPROVERESULT
     */
    public java.lang.String getApproveresult(){
        return approveresult;
    }


    /**
     * Set the value related to the column: APPROVERESULT
     * @param approveresult the APPROVERESULT value
     */
    public void setApproveresult( java.lang.String approveresult ){
        this.approveresult = approveresult;
    }


    /**
     * Return the value associated with the column: BUDGET
     */
    public java.lang.Float getBudget(){
        return budget;
    }


    /**
     * Set the value related to the column: BUDGET
     * @param budget the BUDGET value
     */
    public void setBudget( java.lang.Float budget ){
        this.budget = budget;
    }


    /**
     * Return the value associated with the column: CHANGEID
     */
    public java.lang.String getChangeid(){
        return changeid;
    }


    /**
     * Set the value related to the column: CHANGEID
     * @param changeid the CHANGEID value
     */
    public void setChangeid( java.lang.String changeid ){
        this.changeid = changeid;
    }


    /**
     * Return the value associated with the column: FILLINDATE
     */
    public java.lang.String getFillindate(){
        return fillindate;
    }


    /**
     * Set the value related to the column: FILLINDATE
     * @param fillindate the FILLINDATE value
     */
    public void setFillindate( java.lang.String fillindate ){
        this.fillindate = fillindate;
    }


    /**
     * Return the value associated with the column: ID
     */
    public java.lang.String getId(){
        return id;
    }


    /**
     * Set the value related to the column: ID
     * @param id the ID value
     */
    public void setId( java.lang.String id ){
        this.id = id;
    }


    /**
     * Return the value associated with the column: PRINCIPAL
     */
    public java.lang.String getPrincipal(){
        return principal;
    }


    /**
     * Set the value related to the column: PRINCIPAL
     * @param principal the PRINCIPAL value
     */
    public void setPrincipal( java.lang.String principal ){
        this.principal = principal;
    }


    /**
     * Return the value associated with the column: SURVEYDATE
     */
    public java.lang.String getSurveydate(){
        return surveydate;
    }


    /**
     * Set the value related to the column: SURVEYDATE
     * @param surveydate the SURVEYDATE value
     */
    public void setSurveydate( java.lang.String surveydate ){
        this.surveydate = surveydate;
    }


    /**
     * Return the value associated with the column: SURVEYDATUM
     */
    public java.lang.String getSurveydatum(){
        return surveydatum;
    }


    /**
     * Set the value related to the column: SURVEYDATUM
     * @param surveydatum the SURVEYDATUM value
     */
    public void setSurveydatum( java.lang.String surveydatum ){
        this.surveydatum = surveydatum;
    }


    /**
     * Return the value associated with the column: SURVEYREMARK
     */
    public java.lang.String getSurveyremark(){
        return surveyremark;
    }


    public String getBegintime(){
        return begintime;
    }


    public String getEndtime(){
        return endtime;
    }


    public Float getMaxbudget(){
        return maxbudget;
    }


    public String getStep(){
        return step;
    }


    public String getChangename(){
        return changename;
    }


    public String getChangepro(){
        return changepro;
    }


    public String getLineclass(){
        return lineclass;
    }


    public String getChangeaddr(){
        return changeaddr;
    }


    /**
     * Set the value related to the column: SURVEYREMARK
     * @param surveyremark the SURVEYREMARK value
     */
    public void setSurveyremark( java.lang.String surveyremark ){
        this.surveyremark = surveyremark;
    }


    public void setBegintime( String begintime ){
        this.begintime = begintime;
    }


    public void setEndtime( String endtime ){
        this.endtime = endtime;
    }


    public void setMaxbudget( Float maxbudget ){
        this.maxbudget = maxbudget;
    }


    public void setStep( String step ){
        this.step = step;
    }


    public void setChangename( String changename ){
        this.changename = changename;
    }


    public void setChangepro( String changepro ){
        this.changepro = changepro;
    }


    public void setLineclass( String lineclass ){
        this.lineclass = lineclass;
    }


    public void setChangeaddr( String changeaddr ){
        this.changeaddr = changeaddr;
    }


    public java.lang.String getSaddr() {
        return saddr;
    }


    public void setSaddr(java.lang.String saddr) {
        this.saddr = saddr;
    }


    public java.lang.Float getSexpense() {
        return sexpense;
    }


    public void setSexpense(java.lang.Float sexpense) {
        this.sexpense = sexpense;
    }


    public java.lang.String getSgrade() {
        return sgrade;
    }


    public void setSgrade(java.lang.String sgrade) {
        this.sgrade = sgrade;
    }


    public java.lang.String getSname() {
        return sname;
    }


    public void setSname(java.lang.String sname) {
        this.sname = sname;
    }


    public java.lang.String getSperson() {
        return sperson;
    }


    public void setSperson(java.lang.String sperson) {
        this.sperson = sperson;
    }


    public java.lang.String getSphone() {
        return sphone;
    }


    public void setSphone(java.lang.String sphone) {
        this.sphone = sphone;
    }


    public String getSurveytype() {
        return surveytype;
    }


    public void setSurveytype(String surveytype) {
        this.surveytype = surveytype;
    }

}
