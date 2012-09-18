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
<script language="javascript" type="">
function isValidForm() {

	if(document.patrolBean.patrolName.value==""){
		alert("巡检维护组名称不能为空!! ");
        document.patrolBean.patrolName.focus();
		return false;
	}
    strJobInfo = document.patrolBean.jobInfo.value;
    if (strJobInfo.length>50){
      alert("内容过长，工作信息项只能输入50个字符");
      document.patrolBean.jobInfo.focus();
      return false;
    }
	return true;
}

</script>
<br>
<template:titile value="增加巡检维护组信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/patrolAction.do?method=addPatrolMan">
  <template:formTable>
    <template:formTr name="巡检维护组" isOdd="">
      <html:text property="patrolName" styleClass="inputtext" style="width:200px" maxlength="10"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="所属单位" isOdd="false">

      <html:select property="parentID" styleClass="inputtext" style="width:200px">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="工作信息" isOdd="false">
      <textarea cols="" rows="2" name="jobInfo" style="width:280px;" class="textarea" ></textarea>
    </template:formTr>
    <!--
    <template:formTr name="工作信息">
      <html:text property="jobInfo" styleClass="inputtext" style="width:200px" maxlength="50"/>
    </template:formTr>
    -->
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">增加</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
