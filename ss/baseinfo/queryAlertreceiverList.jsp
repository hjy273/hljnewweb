<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<jsp:useBean id="statdao" class="com.cabletech.statistics.dao.StatDao" scope="request"/>
<%
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
List deptCollection = statdao.getSelectForTag("contractorname","contractorid","contractorinfo",condition);
request.setAttribute("deptCollection",deptCollection);
%>
<script>
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function getUser(){
//
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
  for(i=0;i<document.all.phoneworkId.options.length&&k!=0;i++){
    if(document.all.phoneworkId.options[i].value==document.all.cId.options[0].value){
      document.all.simcode.value=document.all.phoneworkId.options[i].text;
    }
  }
  if(document.all.cId.options.length==0)
    document.all.simcode.value="";
}
function onChangeContractor(){
  k=0;
  for( i=0;i<document.all.conworkId.options.length;i++){
    if(document.all.conworkId.options[i].text.substring(0,document.all.conworkId.options[i].text.indexOf("-"))== document.all.reId.value){
      k++;
      document.all.rId.options.length=k;
      document.all.rId.options[k-1].value=document.all.conworkId.options[i].value;
      document.all.rId.options[k-1].text=document.all.conworkId.options[i].text.substring(document.all.conworkId.options[i].text.lastIndexOf("-")+1,document.all.conworkId.options[i].text.length);
    }
  }
  if(k==0)
    document.all.rId.options.length=0;
  onChangeCon();
}
function onChangePhone(){
  var parentid=document.all.cId.options[0].value;
  k=0;
  for(i=0;i<document.all.phoneworkId.options.length;i++){
    if(document.all.phoneworkId.options[i].value==document.all.cId.value){
      document.all.simcode.value=document.all.phoneworkId.options[i].text;
    }
  }
  if(document.all.cId.options.length==0)
    document.all.simcode.value="";
}
function isValidForm() {
    var pv = /^\d{1,16}$/;
    var len=0;

	if(document.AlertreceiverListBean.simcode.value.length != 0&&document.AlertreceiverListBean.simcode.value.trim().length==0){
        alert("接收报警号码不能为空或者空格！！");
        document.AlertreceiverListBean.simcode.value="";
        return false;
    }
    if(document.all.simcode.value.length!=0&&document.all.simcode.value.length <11){
        alert("接收报警号码长度不得小于11位!! ");
        document.all.simcode.focus();
		return false;
    }
    if(document.all.simcode.value.length!=0&&!pv.test(document.all.simcode.value)){
       alert("接收报警号码不能含有字母,请采用数字方式!!");
       return false;
    }
	return true;
}
</script>
<template:titile value="查询报警号码信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/AlertreceiverListAction.do?method=queryAlertreceiverList">
  <template:formTable >
    <template:formTr name="接收事故级别" isOdd="false">
       <html:select property="emergencylevel" styleClass="inputtext" style="width:160">
			<html:option value="">不限</html:option>
          <html:option value="1">轻微</html:option>
          <html:option value="2">普通</html:option>
          <html:option value="3">严重</html:option>
      </html:select>
    </template:formTr>

<%
if(userinfo.getType().substring(1,2).equals("1")){
%>
        <logic:notEmpty name="reglist">
          <template:formTr name="所属区域">
            <select name="regionid" class="inputtext" style="width:160px" id="reId"  onchange="onChangeContractor()" >
              <logic:present name="reglist">
                <logic:iterate id="regionId" name="reglist">
                  <option value="<bean:write name="regionId" property="regionid"/>">
                  <bean:write name="regionId" property="regionname"/>
                  </option>
                </logic:iterate>
              </logic:present>
            </select>
          </template:formTr>
        </logic:notEmpty>
<%
}
else{
%>
        <logic:notEmpty name="reglist">
          <template:formTr name="所属区域" style="display:none">
            <input type="hidden" value="<%=userinfo.getRegionID()%>" name="regionid"/>
          </template:formTr>
        </logic:notEmpty>
<%
}
%>
        <logic:notEmpty name="conlist">
          <template:formTr name="事故所属代维单位" isOdd="false">
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

          <logic:present name="conlist">
              <select name="conworkId"   style="display:none;width:160">
                <logic:iterate id="contractorId" name="conlist">
                    <option value="<bean:write name="contractorId" property="contractorid"/>"><bean:write name="contractorId" property="regionid"/>--<bean:write name="contractorId" property="contractorname"/></option>
                </logic:iterate>
              </select>
          </logic:present>

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
          <template:formTr name="接收报警号码" tagID="conTr">
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
        <html:submit styleClass="button">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
<script language="javascript">onChangeCon();</script>
