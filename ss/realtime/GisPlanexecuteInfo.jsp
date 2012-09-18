<%@page import="com.cabletech.lineinfo.beans.*"%>
<%@include file="/common/header.jsp"%>
<template:titile value="巡检轨迹信息"/>
<html:form method="Post" action="/PlanExeAction.do?method=updateSubline">
  <template:formTable>
	<template:formTr name="巡检单位" isOdd="false">
		<html:hidden property="patrolname"/>
      <html:text readonly="true" property="patrolname" styleClass="inputtext" style="width:100"/>
    </template:formTr>
	<template:formTr name="巡检卡号" isOdd="false">
      <html:hidden property="simid"/>
      <html:text readonly="true" property="simid" styleClass="inputtext" style="width:100"/>
    </template:formTr>
	<template:formTr name="巡检点名称" isOdd="false">
      <html:text  readonly="true" property="pointname" styleClass="inputtext" style="width:100"/>
    </template:formTr>
    <template:formTr name="所在线段">
      <html:text  readonly="true" property="sublinename" styleClass="inputtext" style="width:100"/>
    </template:formTr>
    <template:formTr name="巡检时间">
      <html:text  readonly="true" property="executetime" styleClass="inputtext" style="width:100"/>
    </template:formTr>
</template:formTable>
</html:form>
