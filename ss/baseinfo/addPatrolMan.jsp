<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<jsp:useBean id="statdao" class="com.cabletech.statistics.dao.StatDao" scope="request"/>
<%
String condition="";
UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
//���ƶ�
if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
   condition = " WHERE regionid IN ('"+userinfo.getRegionID()+"') AND state IS NULL";
}
//�д�ά
if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL  and contractorid='"+userinfo.getDeptID()+"' ";
}
//ʡ�ƶ�
if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE state IS NULL";
}
//ʡ��ά
if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
  condition = " WHERE contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"')";
}
List deptCollection = statdao.getSelectForTag("contractorname","contractorid","contractorinfo",condition);
request.setAttribute("deptCollection",deptCollection);
%>
<script language="javascript" type="">
function isValidForm() {

	if(document.patrolBean.patrolName.value==""){
		alert("Ѳ��ά�������Ʋ���Ϊ��!! ");
        document.patrolBean.patrolName.focus();
		return false;
	}
    strJobInfo = document.patrolBean.jobInfo.value;
    if (strJobInfo.length>50){
      alert("���ݹ�����������Ϣ��ֻ������50���ַ�");
      document.patrolBean.jobInfo.focus();
      return false;
    }
	return true;
}

</script>
<br>
<template:titile value="����Ѳ��ά������Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/patrolAction.do?method=addPatrolMan">
  <template:formTable>
    <template:formTr name="Ѳ��ά����" isOdd="">
      <html:text property="patrolName" styleClass="inputtext" style="width:200px" maxlength="10"/><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="������λ" isOdd="false">

      <html:select property="parentID" styleClass="inputtext" style="width:200px">
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select><font color="red"> *</font>
    </template:formTr>
    <template:formTr name="������Ϣ" isOdd="false">
      <textarea cols="" rows="2" name="jobInfo" style="width:280px;" class="textarea" ></textarea>
    </template:formTr>
    <!--
    <template:formTr name="������Ϣ">
      <html:text property="jobInfo" styleClass="inputtext" style="width:200px" maxlength="50"/>
    </template:formTr>
    -->
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
