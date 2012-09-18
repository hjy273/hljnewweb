<%@ include file="/common/header.jsp" %>

<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<template:titile value="计划巡检率"/>
<display:table name="sessionScope.QueryResult" pagesize="18">
  <logic:equal value="group" name="PMType">
    		<display:column property="patrolname" title="巡检维护组"/>
	</logic:equal>
    <logic:notEqual value="group" name="PMType">
    		<display:column property="patrolname" title="巡检员"/>
    </logic:notEqual>

  <display:column property="contractorname" title="代维单位"/>
  <display:column property="plancount" title="应检总数"/>
  <display:column property="losscount" title="漏检总数"/>
  <display:column property="patrolrate" title="巡检率"/>
  <display:column property="begindate" title="起始时间"/>
  <display:column property="enddate" title="终止时间"/>

</display:table>
<br>
<html:link action="/StatisticAction.do?method=ExportPlanAppRate">导出为Excel文件</html:link>

