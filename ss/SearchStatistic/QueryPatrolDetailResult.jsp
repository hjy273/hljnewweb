<%@include file="/common/header.jsp"%>

<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<template:titile value="巡检明细"/>
<display:table name="sessionScope.QueryResult" requestURI="${ctx}/StatisticAction.do?method=getPatrolDetail" pagesize="18" >
	<logic:equal value="group" name="PMType">
    		<display:column property="patrolname" title="巡检维护组" sortable="true"/>
	</logic:equal>
    <logic:notEqual value="group" name="PMType">
    		<display:column property="patrolname" title="巡检维护人" sortable="true"/>
    </logic:notEqual>

    <display:column property="contractorname" title="巡检单位" sortable="true"/>
    <display:column property="sublinename" title="巡检线段" sortable="true"/>

    <display:column property="pointname" title="巡检点名称" sortable="true"/>

    <display:column property="position" title="巡检点位置" sortable="true"/>
    <display:column property="executetime" title="巡检时间" sortable="true"/>
</display:table>
<br>
<html:link action="/export2Excel.do?reportType=patrolDetail">导出为Excel文件</html:link>
