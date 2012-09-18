<%@ include file="/common/header.jsp"%>
<%
String conStr = "";
if(request.getAttribute("conStr") != null){
	conStr = (String)request.getAttribute("conStr");
}
%>

<script language="javascript">
<!--
function toSetPa(){
	parent.startPSpan.innerHTML = startPSpan.innerHTML ;
	//parent.endPSpan.innerHTML = endPSpan.innerHTML ;

	parent.opSelect(false);

}
//-->
</script>
<body onload="toSetPa()">

<html:form   action="/watchAction.do?method=addWatch" >
<template:formTable>

	<template:formTr name="起始点" isOdd="false">
		<apptag:setSelectOptions valueName="pointcollection"
			tableName="pointinfo" columnName1="pointname" columnName2="pointid" condition="<%=conStr%>"/>
		 <span id="startPSpan">
		 <html:select property="startpointid" styleClass="inputtext" style="width:160" >
			<html:options collection="pointcollection" property="value" labelProperty="label"/>
		 </html:select>
		 </span>
	</template:formTr>

	<template:formTr name="结束点" isOdd="false">
		<apptag:setSelectOptions valueName="pointcollection"
			tableName="pointinfo" columnName1="pointname" columnName2="pointid" condition="<%=conStr%>"/>
		 
		 <span id="endPSpan">		 
		 <html:select property="endpointid" styleClass="inputtext" style="width:160" >
			<html:options collection="pointcollection" property="value" labelProperty="label"/>
		 </html:select>
		 </span>
	</template:formTr>

</template:formTable>
</html:form>

</body>