<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<template:titile value="�豸���·���ȷ��ҳ��" />
<script language="javascript">
	function check(){
		var patrolID = document.all.uId.value;
		if(patrolID != ""){
			return true;
		}else{
			alert("��ѡ���ά��λ");
			return false;
		}
		
	}
	function affirm(){
		document.all.btt.disabled=false;
	}
</script>
<body>
<html:form method="Post" action="/ResourceAssignAction.do?method=assignTerminal">
<div align="center"> 
	<br>�� <apptag:setSelectOptions valueName="connCollection" tableName="contractorinfo" columnName1="contractorname" region="true" columnName2="contractorid" order="contractorname" />
     <html:select property="contractor"  name="RABean"  disabled="true" styleClass="inputtext" styleId="rId" style="width:100" >
        <html:options collection="connCollection" property="value" labelProperty="label"/>
     </html:select>			
	
	
     �� <html:select property="targetContractor" styleClass="inputtext" styleId="rId" style="width:200">
        <html:options collection="connCollection" property="value" labelProperty="label"/>
     </html:select>
	
</div>
<br>
	<div align="center"><input type="submit" class="button" value="����" disabled id="btt" onclick="return check()">
	 <html:reset styleClass="button">ȡ��</html:reset>
	</div>
</html:form>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<div align="left" style="color:red"> * ����Ϊ�������豸���� <a href="javascript:affirm()"> ȷ��</a></div>
<display:table name="requestScope.queryresult" id="currentRowObject">
  <display:column property="terminalid" title="�豸���"/>
  <display:column property="terminalmodel" title="�ͺ�"/>
  <display:column property="simnumber" title="sim��"/>
  <display:column property="patrolname" title="������"/>
</display:table>
</body>