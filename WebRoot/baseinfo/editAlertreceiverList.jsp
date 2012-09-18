<%@page import="com.cabletech.baseinfo.beans.*"%>
<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>

<jsp:useBean id="selectForTag" class="com.cabletech.commons.tags.SelectForTag" scope="request"/>
<%
AlertreceiverListBean bean=(AlertreceiverListBean)request.getAttribute("AlertreceiverListBean");
String beanId=bean.getId();
String beanContractorId=bean.getContractorid();
String beanUserId=bean.getUserid();
String beanSimCode=bean.getSimcode();
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
//市移动
if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
   condition = " WHERE regionid IN ('"+userinfo.getRegionID()+"') AND state IS NULL";
}
//市代维
if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL  and contractorid='"+userinfo.getDeptID()+"' ";
}
//省移动
if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL";
}
//省代维
if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"')";
}
List deptCollection = selectForTag.getSelectForTag("contractorname","contractorid","contractorinfo",condition);
request.setAttribute("deptCollection",deptCollection);
%>
<!-- ZSH 2004-10-13 -->
<input type="hidden" name="contractor_id" value="<%=beanContractorId%>">
<input type="hidden" name="user_id" value="<%=beanUserId%>">
<input type="hidden" name="simcode_v" value="<%=beanSimCode%>">
<script language="javascript">
<!---->
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function isValidForm() {

	if(document.AlertreceiverListBean.simcode.value.length == 0||document.AlertreceiverListBean.simcode.value.trim().length==0){
        alert("接收报警号码不能为空或者空格！！");
        document.AlertreceiverListBean.simcode.value="";
        return false;
    }
    document.AlertreceiverListBean.simcode.value=document.AlertreceiverListBean.simcode.value.trim();
	if(document.AlertreceiverListBean.simcode.value.length != 11  || isNaN(AlertreceiverListBean.simcode.value)){
        alert("请输入有效接收报警号码！！");
        document.AlertreceiverListBean.simcode.value="";
        return false;
    }
    if(document.AlertreceiverListBean.userid.value.length == 0){
        alert("接收者不能为空！！");
        return false;
    }
    //alert(document.AlertreceiverListBean.userid.value);
    return true;
}


function toGetBack(){
        var url = "${ctx}/AlertreceiverListAction.do?method=queryAlertreceiverList";
        self.location.replace(url);

}
function getUser(){

	var depV = AlertreceiverListBean.contractorid.value;
	var URL = "${ctx}/baseinfo/getSelectForPatrolman.jsp?selectname=userid&contractorid=" + depV;

	hiddenInfoFrame.location.replace(URL);
}
function onChangeCon(){
  k=0;
  for( i=0;i<document.all.userworkId.options.length;i++){
    if(document.all.userworkId.options[i].text.substring(0,document.all.userworkId.options[i].text.indexOf("-"))== document.all.rId.value){
      k++;
      document.all.cId.options.length=k;
      document.all.cId.options[k-1].value=document.all.userworkId.options[i].value;
      document.all.cId.options[k-1].text=document.all.userworkId.options[i].text.substring(document.all.userworkId.options[i].text.lastIndexOf("-")+1,document.all.userworkId.options[i].text.length);
    }
  }
  if(k==0)
    document.all.cId.options.length=0;
  for(i=0;i<document.all.phoneworkId.options.length;i++){
    if(document.all.phoneworkId.options[i].value==document.all.cId.options[0].value){
      document.all.simcode.value=document.all.phoneworkId.options[i].text;
    }
  }
}
function onChangePhone(){
  //var parentid=document.all.cId.options[0].value;
  k=0;
  for(i=0;i<document.all.phoneworkId.options.length;i++){
    if(document.all.phoneworkId.options[i].value==document.all.cId.value){
      document.all.simcode.value=document.all.phoneworkId.options[i].text;
    }
  }
}
</script>
<template:titile value="修改报警号码信息"/>
<body>
<html:form onsubmit="return isValidForm()" method="Post" action="/AlertreceiverListAction.do?method=updateAlertreceiverList"><!---->
  <html:hidden property="id" value="<%=beanId%>"/>
  <template:formTable>
    <template:formTr name="接收事故级别">
      <html:select property="emergencylevel" styleClass="inputtext" style="width:160">
          <html:option value="1">轻微</html:option>
          <html:option value="2">普通</html:option>
          <html:option value="3">严重</html:option>
      </html:select>
    </template:formTr>
        <logic:notEmpty name="conlist">
          <template:formTr name="事故所属代维单位" >
            <select name="contractorid" class="inputtext" style="width:160px" id="rId"  onchange="onChangeCon()" >
              <logic:present name="conlist">
                <logic:iterate id="contractorId" name="conlist">
                  <option value="<bean:write name="contractorId" property="contractorid"/>">
                  <bean:write name="contractorId" property="contractorname"/>
                  </option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>
        </logic:notEmpty>


        <logic:notEmpty name="reclist">
          <template:formTr name="接收者" tagID="conTr">
            <select name="userid" class="inputtext" style="width:160px" id="cId" onchange="onChangePhone()">
              <logic:present name="reclist">
                <logic:iterate id="receiverId" name="reclist">
                  <option value="<bean:write name="receiverId" property="userid"/>">
                  <bean:write name="receiverId" property="username"/></option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>

          <logic:present name="reclist">
              <select name="userworkId"   style="display:none;width:160">
                <logic:iterate id="receiverId" name="reclist">
                    <option value="<bean:write name="receiverId" property="userid"/>"><bean:write name="receiverId" property="deptid"/>--<bean:write name="receiverId" property="username"/></option>
                </logic:iterate>
              </select>
          </logic:present>

          <logic:present name="reclist">
          <template:formTr name="接收报警号码" tagID="conTr" >
               <input name="simcode" class="inputtext" style="width:160" maxlength="11"/>
          </template:formTr>
          </logic:present>

          <logic:present name="reclist">
              <select name="phoneworkId"   style="display:none">
                <logic:iterate id="uinfoId" name="reclist">
                    <option value="<bean:write name="uinfoId" property="userid"/>"><bean:write name="uinfoId" property="phone"/></option>
                </logic:iterate>
              </select>
          </logic:present>
        </logic:notEmpty>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">提交</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
</body>
<script language="javascript">
<!--
var contractor_id=document.all.contractor_id.value;
var user_id=document.all.user_id.value;
var simcode_v=document.all.simcode_v.value;
for(i=0;i<document.all.contractorid.options.length;i++){
  if(contractor_id==document.all.contractorid.options[i].value)
    document.all.contractorid.options[i].selected=true;
}
onChangeCon();
for(i=0;i<document.all.userid.options.length;i++){
  if(user_id==document.all.userid.options[i].value)
    document.all.userid.options[i].selected=true;
}
document.all.simcode.value=simcode_v;
/-->
</script>
