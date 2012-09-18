<%@include file="/common/header.jsp"%>

<%
String parentid = request.getParameter("deptid");
String conStr = " parentid='"+ parentid +"'";
%>

<script language="javascript">
<!--
function init(){
	parent.patrolSpan.innerHTML = patrolSpan.innerHTML;
	parent.setDisable(queryConditionForm.patrolid, false);
}
//-->
</script>

<body onload="init()">

<apptag:setSelectOptions valueName="patrolManCollection" tableName="patrolmaninfo" columnName1="patrolname" columnName2="patrolid" condition="<%=conStr%>" />

<html:form method="Post" action="/StatisticAction.do">
	<span id="patrolSpan">
	<html:select property="patrolid" styleClass="inputtext" style="width:225">
		<html:option value="">²»ÏÞ</html:option>
		<html:options collection="patrolManCollection" property="value" labelProperty="label"/>
	</html:select>
	</span>
</html:form>
</body>
