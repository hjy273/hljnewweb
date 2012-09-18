<%@include file="/common/header.jsp"%>
<br>
<template:titile value="设备重新分配确认页面" />
<script language="javascript">
	function check(){
		var patrolID = document.all.uId.value;
		if(patrolID != ""){
			return true;
		}else{
			alert("请选择代维单位");
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
	<br>从 <apptag:setSelectOptions valueName="connCollection" tableName="contractorinfo" columnName1="contractorname" region="true" columnName2="contractorid" order="contractorname" />
     <html:select property="contractor"  name="RABean"  disabled="true" styleClass="inputtext" styleId="rId" style="width:100px" >
        <html:options collection="connCollection" property="value" labelProperty="label"/>
     </html:select>			
	
	
     至 <html:select property="targetContractor" styleClass="inputtext" styleId="rId" style="width:200px">
        <html:options collection="connCollection" property="value" labelProperty="label"/>
     </html:select>
	
</div>
<br>
	<div align="center"><input type="submit" class="button" value="分配" disabled id="btt" onclick="return check()">
	 <input type="button" value="上一步" class="button" onclick="javascript:history.go(-1);"/>
	</div>
</html:form>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<div align="left" style="color:red"> * 以下为待分配设备，请 <a href="javascript:affirm()"> 确认</a></div>
<display:table name="requestScope.queryresult" id="currentRowObject">
  <display:column property="deviceid" title="设备编号"/>
  <display:column property="terminalmodel" title="型号"/>
  <display:column property="simnumber" title="sim卡"/>
  <display:column property="patrolname" title="持有人"/>
</display:table>
</body>