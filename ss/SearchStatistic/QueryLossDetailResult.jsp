<%@include file="/common/header.jsp"%>
<META content="text/html;charset=GB2312" http-equiv="Content-Type">

<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<template:titile value="漏检明细"/>

<display:table name="sessionScope.QueryResult" pagesize="18" >
	<logic:equal value="group" name="PMType">
    		<display:column property="patrolname" title="巡检维护组"/>
	</logic:equal>
    <logic:notEqual value="group" name="PMType">
    		<display:column property="patrolname" title="巡检维护人"/>
    </logic:notEqual>
	<display:column property="linename" title="巡检线路"/>
    <display:column property="sublinename" title="巡检线段"/>
    <display:column property="pointname" title="巡检点名称"/>
    <display:column property="addressinfo" title="巡检点位置"/>
    <display:column property="isfocus" title="关键点"/>
    <display:column property="planexe" title="计划次数"/>
	<display:column property="losstime" title="漏检次数"/>
</display:table>
<br>

<html:link action="/exportLoss.do?reportType=lossDetail">导出为Excel文件</html:link>

